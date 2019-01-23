package mykhalishyn.akka.cluster.demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    private final ActorSystem actorSystem;

    private final ActorRef pingActor;

    @Autowired
    public DemoApplication(final ActorSystem actorSystem,
                           @Qualifier("pingActorRef") final ActorRef pingActor) {
        this.actorSystem = actorSystem;
        this.pingActor = pingActor;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(final String... args) {
        LoggerFactory.getLogger(DemoApplication.class).info("Application started");
        actorSystem.scheduler().schedule(Duration.ZERO,
                Duration.ofSeconds(1),
                pingActor,
                "Tick",
                actorSystem.dispatcher(), null);
    }
}

