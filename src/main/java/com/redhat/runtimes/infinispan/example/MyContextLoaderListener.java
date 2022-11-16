package com.redhat.runtimes.infinispan.example;

import org.wildfly.clustering.web.spring.context.ContextLoaderListener;

import javax.servlet.annotation.WebListener;

@WebListener
public class MyContextLoaderListener extends ContextLoaderListener {
  public MyContextLoaderListener() {
    // Specify spring session repository component class to super implementation
    super(InfinispanConfiguration.class);
  }
}
