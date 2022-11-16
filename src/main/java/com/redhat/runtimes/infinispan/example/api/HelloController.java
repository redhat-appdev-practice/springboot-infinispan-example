package com.redhat.runtimes.infinispan.example.api;

import com.redhat.runtimes.infinispan.example.models.*;
import org.instancio.Instancio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.Instant;

@RestController
@SessionAttributes("greetings")
public class HelloController {
  private static final Logger LOG = LoggerFactory.getLogger(HelloController.class);

  private static final int NANOS_TO_MICROS = 1000;

  private void addFakedObjects(HttpSession session) {
    Instant startTime = Instant.now();
    Account acct = Instancio.create(Account.class);
    session.setAttribute("account", acct);
    Address addr = Instancio.create(Address.class);
    session.setAttribute("address", addr);
    CreditCard card = Instancio.create(CreditCard.class);
    session.setAttribute("credit_card", card);
    Product product = Instancio.create(Product.class);
    session.setAttribute("product", product);
    Instant endTime = Instant.now();
    int duration = Duration.between(startTime, endTime).getNano() / NANOS_TO_MICROS;
    LOG.info("Fake data generated in {}μs", duration);
  }

  @GetMapping("/hello")
  public String hello(HttpSession session) {
    incrementCount(session);
    return "Hello SpringBoot!";
  }

  @GetMapping("/hello/{name}")
  public String greetWithName(@PathVariable String name, HttpSession session) {
    LOG.info("Hello {}", name);
    incrementCount(session);
    Object sessObj = session.getAttribute("state");
    SessionInfo sessionInfo;
    if (sessObj instanceof SessionInfo) {
      sessionInfo = (SessionInfo) sessObj;
      sessionInfo.setLastGreeting(name);
    } else {
      sessionInfo = new SessionInfo();
      sessionInfo.setLastGreeting(name);
    }
    addFakedObjects(session);
    session.setAttribute("state", sessionInfo);
    return String.format("Hello %s!", sessionInfo.getLastGreeting());
  }

  @GetMapping("/last")
  public String greetWithMostRecentName(HttpSession session) {

    Object sessObj = session.getAttribute("state");
    SessionInfo sessionInfo;
    if (sessObj instanceof SessionInfo) {
      sessionInfo = (SessionInfo) sessObj;
      sessionInfo.incrementCount();
    } else {
      sessionInfo = new SessionInfo();
      sessionInfo.incrementCount();
    }
    addFakedObjects(session);
    session.setAttribute("state", sessionInfo);
    return String.format("Hello %s! - count %d", sessionInfo.getLastGreeting(), sessionInfo.getCount());

  }

  private void incrementCount(HttpSession session) {
    Object sessionInfo = session.getAttribute("state");
    if (sessionInfo instanceof SessionInfo) {
      ((SessionInfo)sessionInfo).incrementCount();
      session.setAttribute("state", sessionInfo);
    }
    SessionInfo newSessionInfo = new SessionInfo();
    session.setAttribute("state", newSessionInfo);
  }
}
