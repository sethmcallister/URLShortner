package life.kue.shortener.dto;

public class Link {
    private final String incoming;
    private final String outgoing;

    public Link(final String  incoming, final String outgoing) {
        this.incoming = incoming;
        this.outgoing = outgoing;
    }

    public String getIncoming() {
        return incoming;
    }

    public String getOutgoing() {
        return outgoing;
    }
}
