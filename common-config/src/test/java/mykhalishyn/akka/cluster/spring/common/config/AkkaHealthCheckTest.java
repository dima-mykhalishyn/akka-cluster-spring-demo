package mykhalishyn.akka.cluster.spring.common.config;

import mykhalishyn.akka.cluster.spring.common.AbstractActorTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests for {@link  AkkaHealthCheck}
 *
 * @author dmihalishin@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class AkkaHealthCheckTest extends AbstractActorTest {

    @Autowired
    public AkkaHealthCheck healthCheck;

    @Test
    public void health() {
        final Health health = healthCheck.health();
        Assert.assertEquals(Status.UP, health.getStatus());
    }
}
