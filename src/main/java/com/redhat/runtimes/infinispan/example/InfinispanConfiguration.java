package com.redhat.runtimes.infinispan.example;

import org.infinispan.commons.marshall.ProtoStreamMarshaller;
import org.infinispan.spring.starter.remote.InfinispanRemoteCacheCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class InfinispanConfiguration {

  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public InfinispanRemoteCacheCustomizer caches() {
    return b -> {
      // Ask the server to create this cache on startup
      URI uri;
      try {
        uri = this.getClass().getClassLoader().getResource("remote-cache-all.xml").toURI();
      } catch (URISyntaxException | NullPointerException e) {
        throw new RuntimeException(e);
      }

      b.remoteCache("sessions").configurationURI(uri);
      // Use Java marshaller to serialize the sessions with Protobuf
      b.remoteCache("sessions").marshaller(ProtoStreamMarshaller.class);
    };
  }
}
