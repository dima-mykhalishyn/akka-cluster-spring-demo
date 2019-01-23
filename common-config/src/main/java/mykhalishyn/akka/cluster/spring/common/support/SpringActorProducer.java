package mykhalishyn.akka.cluster.spring.common.support;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import org.springframework.context.ApplicationContext;

/**
 * Spring Actor Producer, that allow to create {@link Actor} from the Spring been
 *
 * @author dmihalishin@gmail.com
 * @see IndirectActorProducer
 */
public class SpringActorProducer implements IndirectActorProducer {

    private ApplicationContext applicationContext;

    private String beanActorName;

    public SpringActorProducer(final ApplicationContext applicationContext,
                               final String beanActorName) {
        this.applicationContext = applicationContext;
        this.beanActorName = beanActorName;
    }

    /**
     * {@inheritDoc}
     *
     * @see IndirectActorProducer#produce()
     */
    @Override
    public Actor produce() {
        return (Actor) applicationContext.getBean(beanActorName);
    }

    /**
     * {@inheritDoc}
     *
     * @see IndirectActorProducer#actorClass()
     */
    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends Actor> actorClass() {
        return (Class<? extends Actor>) applicationContext
                .getType(beanActorName);
    }
}
