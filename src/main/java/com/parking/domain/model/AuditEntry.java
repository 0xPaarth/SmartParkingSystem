package com.parking.domain.model;

import java.time.Instant;

public final class AuditEntry {
  private final Instant timestamp;
  private final String actor;
  private final String action;
  private final String target;
  private final String details;

  public AuditEntry(Instant timestamp, String actor, String action, String target, String details) {
    this.timestamp = timestamp;
    this.actor = actor;
    this.action = action;
    this.target = target;
    this.details = details;
  }

  public Instant getTimestamp() { return timestamp; }
  public String getActor() { return actor; }
  public String getAction() { return action; }
  public String getTarget() { return target; }
  public String getDetails() { return details; }
}
