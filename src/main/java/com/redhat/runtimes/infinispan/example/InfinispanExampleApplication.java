package com.redhat.runtimes.infinispan.example;

import org.infinispan.spring.remote.session.configuration.EnableInfinispanRemoteHttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableCaching
@EnableInfinispanRemoteHttpSession(cacheName = "sessions")
@EnableAspectJAutoProxy
public class InfinispanExampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(InfinispanExampleApplication.class, args);
  }
}
