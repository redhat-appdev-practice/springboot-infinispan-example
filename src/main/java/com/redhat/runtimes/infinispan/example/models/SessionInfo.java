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

  private Address address;

  public void setAddress(Address address) {
    this.address = address;
  }

  public Address getAddress() {
    return this.address;
  }

  private CreditCard creditCard;

  public void setCreditCard(CreditCard card) {
    this.creditCard = card;
  }

  public CreditCard getCreditCard() {
    return this.creditCard;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SessionInfo that = (SessionInfo) o;
    return count == that.count && Objects.equals(lastGreeting, that.lastGreeting) && Objects.equals(account, that.account) && Objects.equals(address, that.address) && Objects.equals(creditCard, that.creditCard);
  }

  @Override
  public int hashCode() {
    return Objects.hash(count, lastGreeting, account, address, creditCard);
  }
}
