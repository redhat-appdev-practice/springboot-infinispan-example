# An example application which uses Infinispan for Spring Remote Session Caching

## Required Tools

* [OpenJDK 11 from Adoptium](https://adoptium.net/installation/)

## Building

```bash
./gradlew compileJava bootJar
```

## Running

1. Start an instance of Infinispan using a container as follows:
   `podman run --name datagrid -d -p 11222:11222 -p 11223:11223 -p 7800:7800 -e USER=admin -e PASS=admin registry.redhat.io/datagrid/datagrid-8-rhel8:latest`
2. Run the SpringBoot application
   `java -jar ./build/libs/infinispan-example-0.0.1-SNAPSHOT.jar

## Adding Artificial Latency For Data Grid

Using the `tc` command, you can add artificial latency to emulate network contention.

```
sudo tc qdisc add dev enp7s0f0 root netem delay 10ms # Add 10ms of latency to the interface

sudo tc qdisc del dev enp7s0f0 root  # Clear the latency from the interface
```
