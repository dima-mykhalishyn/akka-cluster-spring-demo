package mykhalishyn.akka.cluster.demo.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.TestProbe;
import akka.testkit.javadsl.TestKit;
import mykhalishyn.akka.cluster.demo.actor.SimpleDemoProtos.PingRequest;
import org.junit.Test;

import java.time.Duration;

/**
 * Tests for {@link PingActor}
 *
 * @author dmihalishin@gmail.com
 * @see AbstractActorTest
 */
public class PingActorTest extends AbstractActorTest {

    @Test
    public void createReceive() {
        new TestKit(system) {{
            final TestProbe probe = new TestProbe(system, "pongActor");
            final Props props = Props.create(PingActor.class, probe.ref().path().toString());
            final ActorRef subject = system.actorOf(props);

            // the run() method needs to finish within 5 seconds
            within(Duration.ofSeconds(5), () -> {
                subject.tell("Go go go", getRef());

                probe.expectMsgClass(PingRequest.class);
                expectNoMessage();
                return null;
            });
        }};
    }
}
