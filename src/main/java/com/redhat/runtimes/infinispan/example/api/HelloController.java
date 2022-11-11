package com.redhat.runtimes.infinispan.example.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

@RestController
@SessionAttributes("greetings")
public class HelloController {

  @GetMapping("/hello")
  public String hello(HttpSession session) {
    Integer count = readAndIncrementCount(session);
    return "Hello SpringBoot!";
  }

  @GetMapping("/hello/{name}")
  public String greetWithName(@PathVariable String name, HttpSession session) {
    Integer count = readAndIncrementCount(session);
    session.setAttribute("lastProvidedName", name);
    return String.format("Hello %s!", name);
  }

  @GetMapping("/last")
  public String greetWithMostRecentName(HttpSession session) {
    Integer count = readAndIncrementCount(session);
    return String.format("Hello %s! - count %d", session.getAttribute("lastProvidedName"), count);
  }

  private static Integer readAndIncrementCount(HttpSession session) {
    Object sessionCount = session.getAttribute("count");
    if (sessionCount != null && sessionCount instanceof Integer) {
      Integer count = ((Integer)sessionCount) + 1;
      session.setAttribute("count", count);
    } else {
      session.setAttribute("count", Integer.valueOf(1));
    }
    return (Integer) session.getAttribute("count");
  }
}
