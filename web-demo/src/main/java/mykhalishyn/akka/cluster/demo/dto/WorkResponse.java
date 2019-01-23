package mykhalishyn.akka.cluster.demo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dmihalishin@gmail.com
 */
public class WorkResponse implements Serializable {
    private static final long serialVersionUID = -6175113572530201320L;

    private final List<String> statuses = new ArrayList<>();

    public List<String> getStatuses() {
        return statuses;
    }
}
