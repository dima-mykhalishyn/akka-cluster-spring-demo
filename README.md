# Akka Cluster + Spring Demo
Simple demonstration of akka integration with Spring

Project contains 3 sub modules:
* **common-config** - common classes for integration with Spring
* **simple-demo** - simple demo that demonstrate Actor creation 
and communication between them
* **web-demo** - simple web demo, that demonstrate Spring Boot integration 
with akka cluster


To be able to run **Simple Demo**, simply run:
> ./simple-demo/src/main/java/mykhalishyn/akka/cluster/demo/DemoApplication.java

 
To be able to run **Web Demo**, simply run:
> ./web-demo/src/main/java/mykhalishyn/akka/cluster/demo/WebDemoApplication.java

To be able to start cluster, you would need start 
2 instances of the **WebDemo** application using proper **SEED_NODES**

or run the Cluster on local k8s using 
> kubectl --context=docker-for-desktop apply -f ./k8sLocalDeployment.yaml 

then we need to forward the port to some of the pods:
> kubectl --context=docker-for-desktop port-forward akka-cluster-spring-demo-0 8080:8080

After this we could connect to the pod:
> http://localhost:8080/actuator/health

