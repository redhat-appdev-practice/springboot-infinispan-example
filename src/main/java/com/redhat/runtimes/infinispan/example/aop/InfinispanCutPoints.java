package com.redhat.runtimes.infinispan.example.aop;

import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Aspect
@Component
public class InfinispanCutPoints {

  private Logger logger = org.apache.logging.log4j.LogManager.getLogger(InfinispanCutPoints.class);

  @Around(value = "execution(* org.infinispan.spring.common.provider.SpringCache.*(..))")
  public void timeCacheCalls(ProceedingJoinPoint joinPoint) throws Throwable {
    String targetMethod = joinPoint.getSignature().getName();

    Instant startTime = Instant.now();

    joinPoint.proceed();

    Instant endTime = Instant.now();

    Duration duration = Duration.between(startTime, endTime);
    logger.warn("Method {} took {}ns", targetMethod, duration.getNano());
  }
}
