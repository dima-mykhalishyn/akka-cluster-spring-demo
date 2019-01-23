package mykhalishyn.akka.cluster.demo.actor.dto;

import java.io.Serializable;

/**
 * Task message
 *
 * @author dmihalishin@gmail.com
 */
public class Task implements Serializable {
    private static final long serialVersionUID = 1173323460636918959L;
    private final int index;

    public Task(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
