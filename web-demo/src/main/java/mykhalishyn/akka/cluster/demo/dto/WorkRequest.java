package mykhalishyn.akka.cluster.demo.dto;

/**
 * @author dmihalishin@gmail.com
 */
public class WorkRequest {

    private Integer tasks = 1;

    public Integer getTasks() {
        return tasks;
    }

    public void setTasks(Integer tasks) {
        this.tasks = tasks;
    }
}
