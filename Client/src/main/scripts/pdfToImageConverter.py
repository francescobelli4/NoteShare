import json
import pypdfium2 as pdfium
import socket
import struct
import sys
import threading
from io import BytesIO

# Mutex for writing to noteshare
write_lock = threading.Lock()
# Mutex used to avoid parallel accesses to pdf
pdf_lock = threading.Lock()

# This function converts the page to image and sends it to noteshare
def handle_conversion_request(conn: socket, request):
    pageId = int(request['pageId'])
    pdfPath = request['pdfPath']
    dpi = int(request['dpi'])

    try:
        page = None

        with pdf_lock:
            pdf = pdfium.PdfDocument(pdfPath)
            page = pdf.get_page(pageId)

        bitmap = page.render(scale=dpi/72.0)
        img = bitmap.to_pil()
        buffer = BytesIO()
        img.save(buffer, format='PNG')
        img_bytes = buffer.getvalue()
        with write_lock:
            # big endian conversion stuff...
            conn.send(pageId.to_bytes(4, 'big'))
            conn.send(len(img_bytes).to_bytes(4, 'big'))
            
            conn.sendall(img_bytes)
    except Exception as e:
        print(f"Error in conversion {pageId}: {str(e)}")


# This function sets up the socket used to communicate with noteshare
def setup_communication_socket(port: int):

    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind(("127.0.0.1", port))
    s.listen()

    print("Waiting for NoteShare...")
    noteshare, addr = s.accept()
    print("Connected to NoteShare :D")

    while (1):
        try:
            json_str = read_utf_from_java(noteshare)

            if not json_str:
                break

            request = json.loads(json_str)
            # a new thread is launched for every request
            threading.Thread(target=handle_conversion_request, args=(noteshare, request,)).start()
        except (ConnectionResetError, OSError, Exception) as e:
            print("NoteShare was closed! Exiting...")
            break


    # close sockets and close this script
    noteshare.close()
    s.close()
    sys.exit(0)


# This function should read from the socket and return a response object
def read_utf_from_java(conn):
    # Data is sent as UTF from Java.
    # Reading first 2 bytes, which represent the length of the received message (the json string)
    len_bytes = conn.recv(2)

    if len(len_bytes) < 2:
        raise Exception("Connection closed or incomplete length")
    # Parsing message length from big endian bytes
    length = struct.unpack('>H', len_bytes)[0]

    # Reading bytes :D
    data = b''
    while len(data) < length:
        packet = conn.recv(length - len(data))
        if not packet:
            raise Exception("Connection closed while reading message")
        data += packet

    return data.decode('utf-8')


if __name__ == "__main__":
    setup_communication_socket(int(sys.argv[1]))
