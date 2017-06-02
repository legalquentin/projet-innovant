package app;

public class AddHome {

    private final long id;
    private final String content;

    public AddHome(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
