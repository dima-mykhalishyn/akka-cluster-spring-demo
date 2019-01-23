package mykhalishyn.akka.cluster.demo.actor.dto;

import java.io.Serializable;

/**
 * Pong response
 *
 * @author dmihalishin@gmail.com
 */
public class PongResponse implements Serializable {

    private static final long serialVersionUID = 8335572615281593438L;

    private final String message;

    public PongResponse(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
