package mykhalishyn.akka.cluster.demo.actor;

import akka.actor.AbstractLoggingActor;
import mykhalishyn.akka.cluster.demo.actor.dto.PingRequest;
import mykhalishyn.akka.cluster.demo.actor.dto.PongResponse;
import org.apache.commons.lang3.RandomUtils;

/**
 * Pong Actor
 *
 * @author dmihalishin@gmail.com
 * @see AbstractLoggingActor
 */
public class PongActor extends AbstractLoggingActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PingRequest.class, this::processPingRequest)
                .matchAny(this::unhandled)
                .build();
    }

    private void processPingRequest(final PingRequest request) {
        log().info("Processing PingRequest:" + request.getMessage());
        try {
            // simulating the work
            Thread.sleep(RandomUtils.nextInt(1000, 2000));
        } catch (InterruptedException e) {
            log().error(e.getMessage());
            throw new RuntimeException(e);
        }
        final String message = "Pong from " + self().path().uid();
        sender().tell(new PongResponse(message), self());
    }
}
