package com.redhat.runtimes.infinispan.example;


import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TestWithLotsOfSessions {

  private static final Logger LOG = LoggerFactory.getLogger(TestWithLotsOfSessions.class);
  public static final int NUM_THREADS = 100;


  @Test
  public void testUsing100Session() throws ExecutionException, InterruptedException {
    List<Future> futures = new ArrayList<>();
    ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
    for (int x = 0; x < NUM_THREADS; x++) {
      Future f = executor.submit(new ClientSession());
      futures.add(f);
    }

    futures.get(0).get();
  }

  private class ClientSession extends Thread {

    private HttpClient client;

    public ClientSession() {
      CookieManager cm = new CookieManager();
      cm.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
      CookieHandler.setDefault(cm);
      client = HttpClient.newBuilder()
        .cookieHandler(CookieHandler.getDefault())
        .build();
    }

    @Override
    public void run() {
      super.run();
      try {
        String name = Instancio.create(String.class);
        URI uri = new URI("http://localhost:8080/hello/" + name);
        HttpRequest req = HttpRequest.newBuilder().GET().uri(uri).build();
        long pause = (long) Math.floor(50 * Math.random());
        while (true) {
          Thread.sleep(500 + pause);

          Instant startTime = Instant.now();
          try {
            client.send(req, res -> {
              LOG.info("Status Code: {}", res.statusCode());
              Instant endTime = Instant.now();
              Duration duration = Duration.between(startTime, endTime);
              LOG.info("Request duration: {}μs", duration.getNano() / 1000);
              return HttpResponse.BodySubscribers.discarding();
            });
          } catch (IOException e) {
            throw new RuntimeException(e);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
      } catch (java.net.URISyntaxException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }

    }
  }
}
