package com.redhat.runtimes.infinispan.example.models;

import java.io.Serializable;
import java.util.Objects;

public class SessionInfo implements Serializable {
  public long getCount() {
    return count;
  }

  public long incrementCount() {
    count++;
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }

  public String getLastGreeting() {
    return lastGreeting;
  }

  public void setLastGreeting(String lastGreeting) {
    this.lastGreeting = lastGreeting;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public Account getAccount() {
    return this.account;
  }

  private long count = 1;

  private String lastGreeting;

  private Account account;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SessionInfo that = (SessionInfo) o;
    return count == that.count && Objects.equals(lastGreeting, that.lastGreeting) && account.equals(that.account);
  }

  @Override
  public int hashCode() {
    return Objects.hash(count, lastGreeting, account);
  }
}
