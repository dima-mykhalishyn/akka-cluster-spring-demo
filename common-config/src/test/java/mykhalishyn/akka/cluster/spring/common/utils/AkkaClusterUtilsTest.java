package mykhalishyn.akka.cluster.spring.common.utils;

import akka.actor.Address;
import akka.cluster.Cluster;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tests for {@link AkkaClusterUtils}
 *
 * @author dmihalishin@gmail.com
 */
public class AkkaClusterUtilsTest extends AkkaClusterUtils {

    @SuppressWarnings("unchecked")
    @Test
    public void joinToCluster() {
        ArgumentCaptor<List> argument = ArgumentCaptor.forClass(List.class);
        final Cluster cluster = Mockito.mock(Cluster.class);
        Mockito.doNothing().when(cluster).joinSeedNodes(argument.capture());
        final Map<String, String> environment = new HashMap<>();
        environment.put("SEED_NODES_PROGRAMMATICALLY.0", "akka.tcp://AkkaClusterSystem@127.0.0.1:2551");
        environment.put("SEED_NODES_PROGRAMMATICALLY.1", "akka.tcp://AkkaClusterSystem@wrong_url:2551");
        joinToCluster(cluster, environment);
        final List<Address> addresses = argument.getValue();
        Assert.assertEquals(1, addresses.size());
        Assert.assertEquals("akka.tcp://AkkaClusterSystem@127.0.0.1:2551", addresses.get(0).toString());
    }

}
