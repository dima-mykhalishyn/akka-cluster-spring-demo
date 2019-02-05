package mykhalishyn.akka.cluster.spring.common.support;

import akka.actor.AbstractExtensionId;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.Props;
import org.springframework.context.ApplicationContext;

/**
 * TODO: put proper JavaDoc explaining why is this class needed. Important for Open Source projects.
 *
 * Spring Extension for Akka
 *
 * @author dmihalishin@gmail.com
 * @see AbstractExtensionId
 */
public class SpringExtension extends AbstractExtensionId<SpringExtension.SpringExt> {

    public static final SpringExtension SPRING_EXTENSION_PROVIDER = new SpringExtension();

    @Override
    public SpringExt createExtension(final ExtendedActorSystem system) {
        return new SpringExt();
    }

    public static class SpringExt implements Extension {
        private volatile ApplicationContext applicationContext;

        public void initialize(final ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        public Props props(final String actorBeanName) {
            return Props.create(SpringActorProducer.class, applicationContext, actorBeanName);
        }
    }
}
