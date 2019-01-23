package mykhalishyn.akka.cluster.spring.common.support;

import akka.actor.AbstractActor;
import mykhalishyn.akka.cluster.spring.common.config.Actor;

/**
 * @author dmihalishin@gmail.com
 */
@Actor
public class TestActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchAny(something -> {
                    sender().tell(SpringExtensionTest.MESSAGE, self());
                })
                .build();
    }
}
