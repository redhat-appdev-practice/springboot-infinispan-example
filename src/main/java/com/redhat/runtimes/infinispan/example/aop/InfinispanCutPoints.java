package com.redhat.runtimes.infinispan.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Aspect
@Component
public class InfinispanCutPoints {

  private static final Logger LOG = LoggerFactory.getLogger(InfinispanCutPoints.class);
  public static final int NANOS_TO_MICROS = 1000;
  public static final int MICROS_TO_MILLIS = 1000;

  @Pointcut(value = "execution(* org.infinispan.spring.remote.session.InfinispanRemoteSessionRepository.*(..)) ")
  public void logInfinispanOperations() {

  }
  @Pointcut(value = "execution(* org.infinispan.spring.common.session.AbstractInfinispanSessionRepository.*(..)) ")
  public void logInterfaceOerations() {

  }

  @Around(value = "logInfinispanOperations() || logInterfaceOerations()")
  public Object timeCacheCalls(ProceedingJoinPoint joinPoint) throws Throwable {
    String targetMethod = joinPoint.getSignature().getName();

    Instant startTime = Instant.now();

    Object retVal = joinPoint.proceed();

    Instant endTime = Instant.now();

    Duration duration = Duration.between(startTime, endTime);
    LOG.warn("Method {} took {}ms", targetMethod, duration.getNano()/ NANOS_TO_MICROS / MICROS_TO_MILLIS);
    return retVal;
  }
}
