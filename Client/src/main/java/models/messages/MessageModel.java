package models.messages;


import views.Icon;

public class MessageModel {

    private String title;
    private String description;
    private String date;
    private Icon icon;

    public MessageModel(String title, String description, String date, Icon icon) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Icon getIcon() {
        return icon;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
