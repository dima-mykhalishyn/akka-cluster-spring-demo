package mykhalishyn.akka.cluster.demo.actor;

import akka.actor.AbstractLoggingActor;
import mykhalishyn.akka.cluster.demo.actor.SimpleDemoProtos.PingRequest;
import mykhalishyn.akka.cluster.demo.actor.SimpleDemoProtos.PongResponse;
import mykhalishyn.akka.cluster.spring.common.config.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Ping Actor
 *
 * @author dmihalishin@gmail.com
 * @see AbstractLoggingActor
 */
@Actor
public class PingActor extends AbstractLoggingActor {

    private final String pongActorPath;

    @Autowired
    public PingActor(@Value("${pong.actor.path}") final String pongActorPath) {
        this.pongActorPath = pongActorPath;
    }

    /**
     * {@inheritDoc}
     *
     * @see AbstractLoggingActor#createReceive()
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PongResponse.class, this::processPongResponse)
                .matchAny(something -> this.sendPingRequest())
                .build();
    }

    private void processPongResponse(final PongResponse response) {
        log().info("Processing PongResponse:" + response.getMessage());
    }

    private void sendPingRequest() {
        final String message = "Ping from " + self().path().uid();
        final PingRequest request = PingRequest.newBuilder().setMessage(message).build();
        context().system().actorSelection(pongActorPath).tell(request, self());
    }
}
