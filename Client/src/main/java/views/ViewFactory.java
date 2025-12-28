package views;

import models.folder.FolderModel;
import models.note.NoteModel;
import views.access.AccessView;
import views.access.forms.AccessFormView;
import views.access.forms.LoginFormView;
import views.access.forms.RegisterFormView;
import views.home.HomeView;
import views.home.folders_container.FolderElementView;
import views.home.folders_container.FoldersContainerView;
import views.home.folders_container.NoteElementView;
import views.home.leftbar.LeftBarMenuOptionsView;
import views.home.leftbar.LeftBarUserDataView;
import views.home.leftbar.LeftBarView;
import views.home.messages.MessageView;
import views.home.messages.MessagesContainerView;
import views.home.toolsbar.FolderCreationDialogueView;
import views.home.toolsbar.NoteCreationDialogueView;
import views.home.toolsbar.ToolsBarView;
import views.notification.NotificationView;
import views.viewnote.ViewNoteView;

public class ViewFactory {

    private static ViewFactory instance;
    public static ViewFactory getInstance() {
        if (instance == null) {
            instance = new ViewFactory();
        }
        return instance;
    }
    private ViewFactory () {}

    public HomeView createHomeView() {
        return new HomeView();
    }

    public LeftBarView createLeftBarView() {
        return new LeftBarView();
    }

    public ToolsBarView createToolsBarView() {
        return new ToolsBarView();
    }

    public MessagesContainerView createMessagesContainerView(double x, double y) {
        return new MessagesContainerView(x, y);
    }

    public LeftBarMenuOptionsView createLeftBarMenuOptionsView() {
        return new LeftBarMenuOptionsView();
    }

    public LeftBarUserDataView createLeftBarUserDataView() {
        return new LeftBarUserDataView();
    }

    public FoldersContainerView createFoldersContainerView() {
        return new FoldersContainerView();
    }

    public FolderElementView createFolderElementView(FolderModel folder) {
        return new FolderElementView(folder);
    }

    public NoteElementView createNoteElementView(NoteModel note) {
        return new NoteElementView(note);
    }

    public FolderCreationDialogueView createFolderCreationDialogueView() {
        return new FolderCreationDialogueView();
    }

    public NoteCreationDialogueView createNoteCreationDialogueView() {
        return new NoteCreationDialogueView();
    }

    public AccessView createAccessView() {
        return new AccessView();
    }

    public AccessFormView createAccessFormView() {
        return new AccessFormView();
    }

    public RegisterFormView createRegisterFormView() {
        return new RegisterFormView();
    }

    public LoginFormView createLoginFormView() {
        return new LoginFormView();
    }

    public NotificationView createNotificationView(String title, String description, Icon icon) {
        return new NotificationView(title, description, icon);
    }

    public MessageView createMessageView(String title, String description, String date, Icon icon) {
        return new MessageView(title, description, date, icon);
    }

    public ViewNoteView createViewNoteView(NoteModel note) {
        return new ViewNoteView(note);
    }
}