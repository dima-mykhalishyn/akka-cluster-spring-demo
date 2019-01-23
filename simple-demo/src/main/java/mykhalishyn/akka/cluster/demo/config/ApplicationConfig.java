package mykhalishyn.akka.cluster.demo.config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.FromConfig;
import com.typesafe.config.ConfigFactory;
import mykhalishyn.akka.cluster.demo.actor.PongActor;
import mykhalishyn.akka.cluster.spring.common.support.SpringExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Application Config
 *
 * @author dmihalishin@gmail.com
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public ActorSystem actorSystem(final ApplicationContext applicationContext) {
        final ActorSystem system = ActorSystem.create("AkkaClusterSystem", ConfigFactory.load());
        SpringExtension.SPRING_EXTENSION_PROVIDER.get(system).initialize(applicationContext);
        return system;
    }

    @Bean("pingActorRef")
    public ActorRef pingActor(final ActorSystem system) {
        return system.actorOf(SpringExtension.SPRING_EXTENSION_PROVIDER.get(system)
                .props("pingActor"), "pingActor");
    }

    @Bean("pongActorRef")
    public ActorRef pongActor(final ActorSystem system) {
        return system.actorOf(FromConfig.getInstance().props(Props.create(PongActor.class)), "pongActor");
    }

}
