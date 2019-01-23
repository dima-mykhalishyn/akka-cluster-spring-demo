package mykhalishyn.akka.cluster.demo.actor.dto;

import java.io.Serializable;

/**
 * Ping Request
 *
 * @author dmihalishin@gmail.com
 */
public class PingRequest implements Serializable {

    private static final long serialVersionUID = 5763902024483534811L;

    private final String message;

    public PingRequest(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
