package mykhalishyn.akka.cluster.spring.common.config;

import akka.actor.ActorSystem;
import akka.cluster.Cluster;
import akka.cluster.MemberStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Akka Health Check - custom implementation of {@link HealthIndicator} for SpringBoot Actuator
 *
 * @author dmihalishin@gmail.com
 * @see HealthIndicator
 */
@Profile("!test")
@Component
public class AkkaHealthCheck implements HealthIndicator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AkkaHealthCheck.class);

    private final Cluster cluster;

    private final AtomicBoolean terminated = new AtomicBoolean(false);

    @Autowired
    public AkkaHealthCheck(final ActorSystem system,
                           @Qualifier("akkaCluster") final Cluster cluster) {
        system.registerOnTermination(() -> {
            LOGGER.info("Akka System Terminated");
            terminated.compareAndSet(false, true);
        });
        this.cluster = cluster;
    }

    /**
     * {@inheritDoc}
     *
     * @see HealthIndicator#health()
     */
    @Override
    public Health health() {
        final Map<String, MemberStatus> status = new HashMap<>();
        cluster.state().getMembers().forEach(member ->
                status.put(member.address().toString(), member.status())
        );
        final boolean up = !terminated.get();
        final Health.Builder builder = up ? Health.up() : Health.down();
        builder.withDetail("akka.cluster.size", status.size());
        status.forEach((key, value) -> builder.withDetail(key, value.toString()));
        return builder.build();
    }
}
