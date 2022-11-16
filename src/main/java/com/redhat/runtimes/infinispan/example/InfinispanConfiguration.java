package com.redhat.runtimes.infinispan.example;

import org.springframework.context.annotation.Configuration;
import org.wildfly.clustering.web.spring.SessionPersistenceGranularity;
import org.wildfly.clustering.web.spring.annotation.Indexing;
import org.wildfly.clustering.web.spring.annotation.SessionManager;
import org.wildfly.clustering.web.spring.hotrod.annotation.EnableHotRodIndexedHttpSession;
import org.wildfly.clustering.web.spring.hotrod.annotation.HotRod;

@Configuration
@EnableHotRodIndexedHttpSession(
  manager = @SessionManager(granularity = SessionPersistenceGranularity.SESSION),
  config = @HotRod(uri = "hotrod://192.168.1.210:11222"),
  indexing = @Indexing()
)
public class InfinispanConfiguration {

}
