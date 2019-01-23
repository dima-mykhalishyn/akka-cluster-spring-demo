package mykhalishyn.akka.cluster.demo;

import mykhalishyn.akka.cluster.demo.config.RouterConfig;
import mykhalishyn.akka.cluster.demo.config.TestConfig;
import mykhalishyn.akka.cluster.demo.dto.WorkRequest;
import mykhalishyn.akka.cluster.demo.dto.WorkResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@RunWith(SpringRunner.class)
@Import({RouterConfig.class, TestConfig.class})
@WebFluxTest
public class WebDemoApplicationTests {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void testWork() {
        this.webClient.post().uri("/work")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(new WorkRequest()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(WorkResponse.class)
                .value(response -> {
                    Assert.assertEquals(1, response.getStatuses().size());
                    Assert.assertEquals(" Finish task #0.", response.getStatuses().get(0));
                });
    }
}

