# An example application which uses Infinispan for Spring Remote Session Caching

## Required Tools

* [OpenJDK 11 from Adoptium](https://adoptium.net/installation/)

## Building

```bash
./gradlew compileJava bootJar
```

## Running

1. Start an instance of Infinispan using a container as follows:
   `podman run -d -p 11222:11222 -p 11223:11223 -p 7800:7800 -e USER=admin -e PASS=admin infinispan/server`
2. Run the SpringBoot application
   `java -jar ./build/libs/infinispan-example-0.0.1-SNAPSHOT.jar
