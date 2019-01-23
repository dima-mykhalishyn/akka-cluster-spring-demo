package mykhalishyn.akka.cluster.demo.config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import mykhalishyn.akka.cluster.demo.actor.WorkerActor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean("workRouterRef")
    public ActorRef workerActor(final ActorSystem system) {
        return system.actorOf(Props.create(WorkerActor.class));
    }

    @Bean
    public ActorSystem system() {
        return ActorSystem.create("AkkaTESTSystem", ConfigFactory.parseString(
                "akka.remote.netty.tcp.port=0\n" +
                        "akka.remote.artery.canonical.port=0"));
    }
}
