package com.redhat.runtimes.infinispan.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class InfinispanExampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(InfinispanExampleApplication.class, args);
  }
}
