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

  @Pointcut(value = "execution(* org.infinispan.spring.remote.session.InfinispanRemoteSessionRepository.*(..)) ")
  public void logInfinispanOperations() {

  }

  @Around(value = "logInfinispanOperations()")
  public Object timeCacheCalls(ProceedingJoinPoint joinPoint) throws Throwable {
    String targetMethod = joinPoint.getSignature().getName();

    Instant startTime = Instant.now();

    Object retVal = joinPoint.proceed();

    Instant endTime = Instant.now();

    Duration duration = Duration.between(startTime, endTime);
    LOG.warn("Method {} took {}ns", targetMethod, duration.getNano());
    return retVal;
  }
}
