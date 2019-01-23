package mykhalishyn.akka.cluster.demo.actor;

import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import com.typesafe.config.ConfigFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * @author dmihalishin@gmail.com
 */
public class AbstractActorTest {

    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create("AkkaTESTSystem", ConfigFactory.parseString(
                        "akka.remote.netty.tcp.port=0\n" +
                        "akka.remote.artery.canonical.port=0"));
    }

    @AfterClass
    public static void teardown() {
        try {
            TestKit.shutdownActorSystem(system, scala.concurrent.duration.Duration.apply("5 seconds"));
        } catch (Exception e) {
            // do nothing
        }
    }
}
