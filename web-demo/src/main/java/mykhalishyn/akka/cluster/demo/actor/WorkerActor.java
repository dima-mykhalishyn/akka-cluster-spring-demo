package mykhalishyn.akka.cluster.demo.actor;

import akka.actor.AbstractLoggingActor;
import mykhalishyn.akka.cluster.demo.actor.MessageProto.Task;
import mykhalishyn.akka.cluster.spring.common.config.Actor;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.stream.LongStream;

/**
 * Worker Actor that calculate Factorial of 10000, to simulate the work load
 *
 * @author dmihalishin@gmail.com
 * @see AbstractLoggingActor
 */
@Actor
public class WorkerActor extends AbstractLoggingActor {

    public static final String ACTOR_NAME = "/user/workerActor";

    private static final String RESULT_MESSAGE = "%s Finish task #%d.";

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Task.class, this::processWorkRequest)
                .matchAny(this::unhandled)
                .build();
    }

    private void processWorkRequest(final Task request) {
        log().info("Processing Task #" + request.getIndex());
        final String host = System.getenv("HOSTNAME");
        final BigInteger factorial = LongStream.range(2, 10001)
                .boxed()
                .map(BigInteger::valueOf)
                .reduce(BigInteger.ONE, BigInteger::multiply);
        log().debug("Factorial of 10000 is " + factorial);
        final String message = String.format(RESULT_MESSAGE, StringUtils.isBlank(host) ? "" : host, request.getIndex());
        sender().tell(message, self());
    }
}
