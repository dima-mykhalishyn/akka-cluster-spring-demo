package mykhalishyn.akka.cluster.spring.common.support;

import akka.actor.ActorRef;
import akka.testkit.javadsl.TestKit;
import mykhalishyn.akka.cluster.spring.common.AbstractActorTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Duration;

/**
 * Tests for {@link SpringExtension}
 *
 * @author dmihalishin@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class SpringExtensionTest extends AbstractActorTest {

    public static final String MESSAGE = "Yo";

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void smokeTest() {
        new TestKit(system) {{
            SpringExtension.SPRING_EXTENSION_PROVIDER.get(system).initialize(applicationContext);
            final ActorRef subject = system.actorOf(SpringExtension.SPRING_EXTENSION_PROVIDER.get(system)
                    .props("testActor"), "testActor");

            // the run() method needs to finish within 5 seconds
            within(Duration.ofSeconds(5), () -> {
                subject.tell("Hello", getRef());

                expectMsg(Duration.ofSeconds(3), MESSAGE);
                expectNoMessage();
                return null;
            });
        }};
    }
}
