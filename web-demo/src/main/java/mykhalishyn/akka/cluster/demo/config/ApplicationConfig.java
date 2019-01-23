package mykhalishyn.akka.cluster.demo.config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.cluster.routing.ClusterRouterGroup;
import akka.cluster.routing.ClusterRouterGroupSettings;
import akka.routing.FromConfig;
import akka.routing.RoundRobinGroup;
import mykhalishyn.akka.cluster.demo.actor.WorkerActor;
import mykhalishyn.akka.cluster.spring.common.support.SpringExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Application Configuration
 *
 * @author dmihalishin@gmail.com
 */
@Configuration
@Profile("!test")
@ComponentScan("mykhalishyn.akka.cluster.spring.common.config")
public class ApplicationConfig {

    @Bean("workerActorRef")
    public ActorRef workerActor(final ActorSystem system) {
        return system.actorOf(FromConfig.getInstance().props(
                SpringExtension.SPRING_EXTENSION_PROVIDER.get(system).props("workerActor")
        ), "workerActor");
    }

    @Bean("workRouterRef")
    public ActorRef workRouter(final ActorSystem system) {
        final Iterable<String> routeesPaths = Collections.singletonList(WorkerActor.ACTOR_NAME);
        final Set<String> useRoles = new HashSet<>(Collections.singletonList("compute"));
        return system.actorOf(
                new ClusterRouterGroup(
                        new RoundRobinGroup(routeesPaths),
                        new ClusterRouterGroupSettings(1000, routeesPaths, true, useRoles)
                ).props(), "workRouter");
    }

}
