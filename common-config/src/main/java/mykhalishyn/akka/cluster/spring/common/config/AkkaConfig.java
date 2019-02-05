package mykhalishyn.akka.cluster.spring.common.config;

import akka.actor.ActorSystem;
import akka.cluster.Cluster;
import com.typesafe.config.ConfigFactory;
import mykhalishyn.akka.cluster.spring.common.support.SpringExtension;
import mykhalishyn.akka.cluster.spring.common.utils.AkkaClusterUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Akka configuration for Spring
 *
 * @author dmihalishin@gmail.com
 */
@Configuration
@Profile("!test")
public class AkkaConfig {

    /**
     * Initialize {@link ActorSystem}
     *
     * @param applicationContext spring application context. Cannot be {@code null}
     * @return initialized actor system
     */
    @Bean
    public ActorSystem actorSystem(final ApplicationContext applicationContext) {
        final ActorSystem system = ActorSystem.create("AkkaClusterSystem", ConfigFactory.load());
        SpringExtension.SPRING_EXTENSION_PROVIDER.get(system).initialize(applicationContext);
        return system;
    }

    /**
     * Initialize {@link ThreadPoolTaskScheduler}
     *
     * @return initialized {@code ThreadPoolTaskScheduler}
     */
    @Bean
    public ThreadPoolTaskScheduler scheduler() {
        final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        scheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        return scheduler;
    }

    /**
     * Initialize akka Cluster
     *
     * @param system        the actor system. Cannot be {@code null}
     * @param delaySeconds  delay for joining to the cluster programmatically. Cannot be {@code null}
     * @param taskScheduler the task scheduler. Cannot be {@code null}
     * @return the initialized akka cluster
     */
    @Bean("akkaCluster")
    public Cluster cluster(final ActorSystem system,
                           @Value("${akka.cluster.init.delay.seconds: 300}") final Integer delaySeconds,
                           final ThreadPoolTaskScheduler taskScheduler) {
        final Cluster cluster = Cluster.get(system);
        taskScheduler.schedule(
                () -> AkkaClusterUtils.joinToCluster(cluster, System.getenv()),
                new Date(ZonedDateTime.now().plusSeconds(delaySeconds).toInstant().toEpochMilli())
        );
        return cluster;
    }

}
