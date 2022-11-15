package com.redhat.runtimes.infinispan.example.api;

import com.github.javafaker.Faker;
import com.redhat.runtimes.infinispan.example.models.Account;
import com.redhat.runtimes.infinispan.example.models.SessionInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

@RestController
@SessionAttributes("greetings")
public class HelloController {

  private final Faker faker = new Faker();

  @GetMapping("/hello")
  public String hello(HttpSession session) {
    Long count = readAndIncrementCount(session);
    return "Hello SpringBoot!";
  }

  @GetMapping("/hello/{name}")
  public String greetWithName(@PathVariable String name, HttpSession session) {
    Long count = readAndIncrementCount(session);
    Object sessObj = session.getAttribute("state");
    SessionInfo sessionInfo;
    if (sessObj instanceof SessionInfo) {
      sessionInfo = (SessionInfo) sessObj;
      sessionInfo.setLastGreeting(name);
    } else {
      sessionInfo = new SessionInfo();
      sessionInfo.setLastGreeting(name);
    }
    Account acct = new Account();
    acct.setAccountId(faker.idNumber().valid());
    acct.setFirstName(faker.name().firstName());
    sessionInfo.setAccount(acct);
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
    Account acct = new Account();
    acct.setAccountId(faker.idNumber().valid());
    acct.setFirstName(faker.name().firstName());
    sessionInfo.setAccount(acct);
    session.setAttribute("state", sessionInfo);
    return String.format("Hello %s! - count %d", sessionInfo.getLastGreeting(), sessionInfo.getCount());

  }

  private static Long readAndIncrementCount(HttpSession session) {
    Object sessionInfo = session.getAttribute("state");
    if (sessionInfo instanceof SessionInfo) {
      Long count = ((SessionInfo)sessionInfo).incrementCount();
      session.setAttribute("state", sessionInfo);
      return count;
    }
    SessionInfo newSessionInfo = new SessionInfo();
    session.setAttribute("state", newSessionInfo);
    return newSessionInfo.getCount();
  }
}
