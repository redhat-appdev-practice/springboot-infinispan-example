package com.redhat.runtimes.infinispan.example;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class MyHttpSessionApplicationInitializer extends AbstractHttpSessionApplicationInitializer {
  public MyHttpSessionApplicationInitializer() {
    // Do not construct with any component classes
    // This skips dynamic registration of Spring Session's ServletContextListener
  }
}
