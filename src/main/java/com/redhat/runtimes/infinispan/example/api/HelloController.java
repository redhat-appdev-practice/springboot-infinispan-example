package com.redhat.runtimes.infinispan.example.api;

import com.github.javafaker.Faker;
import com.redhat.runtimes.infinispan.example.models.Account;
import com.redhat.runtimes.infinispan.example.models.Address;
import com.redhat.runtimes.infinispan.example.models.CreditCard;
import com.redhat.runtimes.infinispan.example.models.SessionInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@SessionAttributes("greetings")
public class HelloController {

  private final Faker faker = new Faker();

  private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

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
    addFakedObjects(sessionInfo);
    session.setAttribute("state", sessionInfo);
    return String.format("Hello %s!", sessionInfo.getLastGreeting());
  }

  private void addFakedObjects(SessionInfo sessionInfo) {
    Account acct = new Account();
    acct.setAccountId(faker.idNumber().valid());
    acct.setFirstName(faker.name().firstName());
    sessionInfo.setAccount(acct);
    Address addr = new Address();
    addr.setCity(faker.address().city());
    addr.setCountry(faker.address().country());
    addr.setState(faker.address().state());
    addr.setStreet1(faker.address().streetAddress(false));
    addr.setZipCode(faker.address().zipCode());
    sessionInfo.setAddress(addr);
    CreditCard card = new CreditCard();
    card.setCreditCardId(UUID.randomUUID().toString());
    card.setNumber(faker.finance().creditCard());
    Date expiration = faker.date().future(1825, TimeUnit.DAYS);
    card.setExpiration(sdf.format(expiration));
    sessionInfo.setCreditCard(card);
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
    addFakedObjects(sessionInfo);
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
