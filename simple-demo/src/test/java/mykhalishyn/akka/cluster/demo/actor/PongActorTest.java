package mykhalishyn.akka.cluster.demo.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import mykhalishyn.akka.cluster.demo.actor.SimpleDemoProtos.PingRequest;
import mykhalishyn.akka.cluster.demo.actor.SimpleDemoProtos.PongResponse;
import org.junit.Test;

import java.time.Duration;

/**
 * Tests for {@link PongActor}
 *
 * @author dmihalishin@gmail.com
 * @see AbstractActorTest
 */
public class PongActorTest extends AbstractActorTest {

    @Test
    public void createReceive() {
        new TestKit(system) {{
            final Props props = Props.create(PongActor.class);
            final ActorRef subject = system.actorOf(props);

            // the run() method needs to finish within 5 seconds
            within(Duration.ofSeconds(5), () -> {
                subject.tell(PingRequest.newBuilder().setMessage("Go go go").build(), getRef());
                expectMsgClass(PongResponse.class);
                expectNoMessage();
                return null;
            });
        }};
    }
}
