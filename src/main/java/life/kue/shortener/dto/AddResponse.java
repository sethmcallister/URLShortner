package life.kue.shortener.dto;

public class AddResponse {
    private final Status status;
    private final String newURL;

    public AddResponse(final Status status, final String newURL) {
        this.status = status;
        this.newURL = newURL;
    }

    public Status getStatus() {
        return status;
    }

    public String getNewURL() {
        return newURL;
    }

    public enum Status {
        OK,
        BAD
    }
}
