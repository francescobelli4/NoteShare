Software Engineering course project (2025/26). 

**User stories:**

1) As a student, I want to sell and buy lecture notes using platform’s internal coins, which I earn by
selling my own notes, and search for notes using a search bar, so that I can reduce the workload and
recover missed lectures

2) As a teacher, I want to create invitation-code-only folders to share my lecture notes and slides with my
students, so that I can support students who can’t attend all the lectures

3) As a power user, I want to receive reports on suspicious, overpriced or empty notes and to delete them
after review, so that I can ensure safe transactions and quality content


**Functional requirements:**

1) The system shall force users to upload lecture notes in PDF format.

2) The system shall notify the user when one of their notes is purchased, providing the buyer’s nickname,
the note’s name and the earned credits.

3) The system shall provide the list of lecture notes owned by the logged-in user.

Use cases diagram:


<img width="1207" height="1278" alt="Use Cases drawio" src="https://github.com/user-attachments/assets/1f260ebd-8229-40d1-9944-77121a9c06da" />

**Steps:**
Use case: BUY NOTE
1) The student searches for a note by its name
2) The system shows the student all the notes that match with the name
3) The student requests to buy a note
4) The system checks if the student has enough credits
5) The system executes the payment
6) The student chooses the saving method (local download or cloud(*))
7) The student specifies the saving path
8) The system notifies the user that he successfully bought the note
9) The system saves the transaction data (§)

Extensions:
4a) The student hasn’t got enough credits: System refuses the transaction and notifies
the student that he can’t buy the note.
7a) The student chooses a wrong path: System returns to step 6

(*) CLOUD: the note is not downloaded on the user’s device, but it’s accessed every time
by requesting it to the server. 

(§) TRANSACTION DATA: includes the note saving method, the buyer and seller name
and the note price.

**Activity Diagram:**

<img width="773" height="791" alt="ActivityDiagram drawio" src="https://github.com/user-attachments/assets/9ab80634-3e46-4993-bfd6-534a5927f904" />

