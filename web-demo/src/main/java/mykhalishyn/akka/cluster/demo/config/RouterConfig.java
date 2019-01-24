package mykhalishyn.akka.cluster.demo.config;

import akka.actor.ActorRef;
import akka.pattern.Patterns;
import akka.util.Timeout;
import mykhalishyn.akka.cluster.demo.actor.dto.Task;
import mykhalishyn.akka.cluster.demo.dto.WorkRequest;
import mykhalishyn.akka.cluster.demo.dto.WorkResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Router Configuration
 * TODO: add missing explanation
 *
 * @author dmihalishin@gmail.com
 */
@Configuration
public class RouterConfig {

    private static final String WORK_ENDPOINT = "/work";

    private static final FiniteDuration DURATION = FiniteDuration.create(30, TimeUnit.SECONDS);

    private static final Timeout TIMEOUT = Timeout.durationToTimeout(DURATION);

    @Bean
    public RouterFunction<ServerResponse> route(@Qualifier("workRouterRef") final ActorRef workerActor) {
        return RouterFunctions.route(
                RequestPredicates.POST(WORK_ENDPOINT).and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(WorkRequest.class)
                        .map(workRequest -> {
                            final List<Mono<String>> tasks = IntStream.range(0, workRequest.getTasks())
                                    .boxed()
                                    .map(index -> {

                                        final Future<Object> future = Patterns.ask(workerActor, new Task(index), TIMEOUT);
                                        final CompletionStage<String> stage = FutureConverters.toJava(future).thenApply(Object::toString);
                                        return Mono.fromCompletionStage(stage).onErrorReturn("Task #" + index + " failed");
                                    })
                                    .collect(Collectors.toList());

                            return Flux.concat(tasks).reduceWith(WorkResponse::new, (response, status) -> {
                                response.getStatuses().add(status);
                                return response;
                            });
                        }).flatMap(answer -> ServerResponse.ok().body(answer, WorkResponse.class))
                        .switchIfEmpty(ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build())
        );
    }
}
