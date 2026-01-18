/*     */ package io.sentry;public final class Session implements JsonUnknown, JsonSerializable { @NotNull
/*     */   private final Date started; @Nullable
/*     */   private Date timestamp; @NotNull
/*     */   private final AtomicInteger errorCount; @Nullable
/*     */   private final String distinctId; @Nullable
/*     */   private final String sessionId; @Nullable
/*     */   private Boolean init; @NotNull
/*     */   private State status; @Nullable
/*     */   private Long sequence; @Nullable
/*     */   private Double duration; @Nullable
/*     */   private final String ipAddress;
/*     */   @Nullable
/*     */   private String userAgent;
/*     */   @Nullable
/*     */   private final String environment;
/*     */   @NotNull
/*     */   private final String release;
/*     */   @Nullable
/*     */   private String abnormalMechanism;
/*     */   
/*  21 */   public enum State { Ok,
/*  22 */     Exited,
/*  23 */     Crashed,
/*  24 */     Abnormal; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*  70 */   private final AutoClosableReentrantLock sessionLock = new AutoClosableReentrantLock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Session(@NotNull State status, @NotNull Date started, @Nullable Date timestamp, int errorCount, @Nullable String distinctId, @Nullable String sessionId, @Nullable Boolean init, @Nullable Long sequence, @Nullable Double duration, @Nullable String ipAddress, @Nullable String userAgent, @Nullable String environment, @NotNull String release, @Nullable String abnormalMechanism) {
/*  90 */     this.status = status;
/*  91 */     this.started = started;
/*  92 */     this.timestamp = timestamp;
/*  93 */     this.errorCount = new AtomicInteger(errorCount);
/*  94 */     this.distinctId = distinctId;
/*  95 */     this.sessionId = sessionId;
/*  96 */     this.init = init;
/*  97 */     this.sequence = sequence;
/*  98 */     this.duration = duration;
/*  99 */     this.ipAddress = ipAddress;
/* 100 */     this.userAgent = userAgent;
/* 101 */     this.environment = environment;
/* 102 */     this.release = release;
/* 103 */     this.abnormalMechanism = abnormalMechanism;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Session(@Nullable String distinctId, @Nullable User user, @Nullable String environment, @NotNull String release) {
/* 111 */     this(State.Ok, 
/*     */         
/* 113 */         DateUtils.getCurrentDateTime(), 
/* 114 */         DateUtils.getCurrentDateTime(), 0, distinctId, 
/*     */ 
/*     */         
/* 117 */         SentryUUID.generateSentryId(), 
/* 118 */         Boolean.valueOf(true), null, null, 
/*     */ 
/*     */         
/* 121 */         (user != null) ? user.getIpAddress() : null, null, environment, release, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTerminated() {
/* 129 */     return (this.status != State.Ok);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Date getStarted() {
/* 134 */     if (this.started == null) {
/* 135 */       return null;
/*     */     }
/* 137 */     return (Date)this.started.clone();
/*     */   }
/*     */   @Nullable
/*     */   public String getDistinctId() {
/* 141 */     return this.distinctId;
/*     */   }
/*     */   @Nullable
/*     */   public String getSessionId() {
/* 145 */     return this.sessionId;
/*     */   }
/*     */   @Nullable
/*     */   public String getIpAddress() {
/* 149 */     return this.ipAddress;
/*     */   }
/*     */   @Nullable
/*     */   public String getUserAgent() {
/* 153 */     return this.userAgent;
/*     */   }
/*     */   @Nullable
/*     */   public String getEnvironment() {
/* 157 */     return this.environment;
/*     */   }
/*     */   @NotNull
/*     */   public String getRelease() {
/* 161 */     return this.release;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean getInit() {
/* 165 */     return this.init;
/*     */   }
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public void setInitAsTrue() {
/* 171 */     this.init = Boolean.valueOf(true);
/*     */   }
/*     */   
/*     */   public int errorCount() {
/* 175 */     return this.errorCount.get();
/*     */   }
/*     */   @NotNull
/*     */   public State getStatus() {
/* 179 */     return this.status;
/*     */   }
/*     */   @Nullable
/*     */   public Long getSequence() {
/* 183 */     return this.sequence;
/*     */   }
/*     */   @Nullable
/*     */   public Double getDuration() {
/* 187 */     return this.duration;
/*     */   }
/*     */   @Nullable
/*     */   public String getAbnormalMechanism() {
/* 191 */     return this.abnormalMechanism;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Date getTimestamp() {
/* 196 */     Date timestampRef = this.timestamp;
/* 197 */     return (timestampRef != null) ? (Date)timestampRef.clone() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void end() {
/* 202 */     end(DateUtils.getCurrentDateTime());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void end(@Nullable Date timestamp) {
/* 211 */     ISentryLifecycleToken ignored = this.sessionLock.acquire(); try {
/* 212 */       this.init = null;
/*     */ 
/*     */       
/* 215 */       if (this.status == State.Ok) {
/* 216 */         this.status = State.Exited;
/*     */       }
/*     */       
/* 219 */       if (timestamp != null) {
/* 220 */         this.timestamp = timestamp;
/*     */       } else {
/* 222 */         this.timestamp = DateUtils.getCurrentDateTime();
/*     */       } 
/*     */       
/* 225 */       if (this.timestamp != null) {
/* 226 */         this.duration = Double.valueOf(calculateDurationTime(this.timestamp));
/* 227 */         this.sequence = Long.valueOf(getSequenceTimestamp(this.timestamp));
/*     */       } 
/* 229 */       if (ignored != null) ignored.close(); 
/*     */     } catch (Throwable throwable) {
/*     */       if (ignored != null)
/*     */         try {
/*     */           ignored.close();
/*     */         } catch (Throwable throwable1) {
/*     */           throwable.addSuppressed(throwable1);
/*     */         }  
/*     */       throw throwable;
/*     */     } 
/*     */   } private double calculateDurationTime(@NotNull Date timestamp) {
/* 240 */     long diff = Math.abs(timestamp.getTime() - this.started.getTime());
/* 241 */     return diff / 1000.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean update(@Nullable State status, @Nullable String userAgent, boolean addErrorsCount) {
/* 248 */     return update(status, userAgent, addErrorsCount, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean update(@Nullable State status, @Nullable String userAgent, boolean addErrorsCount, @Nullable String abnormalMechanism) {
/* 265 */     ISentryLifecycleToken ignored = this.sessionLock.acquire(); try {
/* 266 */       boolean sessionHasBeenUpdated = false;
/* 267 */       if (status != null) {
/* 268 */         this.status = status;
/* 269 */         sessionHasBeenUpdated = true;
/*     */       } 
/*     */       
/* 272 */       if (userAgent != null) {
/* 273 */         this.userAgent = userAgent;
/* 274 */         sessionHasBeenUpdated = true;
/*     */       } 
/* 276 */       if (addErrorsCount) {
/* 277 */         this.errorCount.addAndGet(1);
/* 278 */         sessionHasBeenUpdated = true;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 285 */       if (abnormalMechanism != null) {
/* 286 */         this.abnormalMechanism = abnormalMechanism;
/* 287 */         sessionHasBeenUpdated = true;
/*     */       } 
/*     */       
/* 290 */       if (sessionHasBeenUpdated) {
/* 291 */         this.init = null;
/* 292 */         this.timestamp = DateUtils.getCurrentDateTime();
/* 293 */         if (this.timestamp != null) {
/* 294 */           this.sequence = Long.valueOf(getSequenceTimestamp(this.timestamp));
/*     */         }
/*     */       } 
/* 297 */       boolean bool1 = sessionHasBeenUpdated;
/* 298 */       if (ignored != null) ignored.close(); 
/*     */       return bool1;
/*     */     } catch (Throwable throwable) {
/*     */       if (ignored != null)
/*     */         try {
/*     */           ignored.close();
/*     */         } catch (Throwable throwable1) {
/*     */           throwable.addSuppressed(throwable1);
/*     */         }  
/*     */       throw throwable;
/*     */     }  } private long getSequenceTimestamp(@NotNull Date timestamp) {
/* 309 */     long sequence = timestamp.getTime();
/*     */ 
/*     */     
/* 312 */     if (sequence < 0L) {
/* 313 */       sequence = Math.abs(sequence);
/*     */     }
/* 315 */     return sequence;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Session clone() {
/* 325 */     return new Session(this.status, this.started, this.timestamp, this.errorCount
/*     */ 
/*     */ 
/*     */         
/* 329 */         .get(), this.distinctId, this.sessionId, this.init, this.sequence, this.duration, this.ipAddress, this.userAgent, this.environment, this.release, this.abnormalMechanism);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String SID = "sid";
/*     */     
/*     */     public static final String DID = "did";
/*     */     
/*     */     public static final String INIT = "init";
/*     */     
/*     */     public static final String STARTED = "started";
/*     */     
/*     */     public static final String STATUS = "status";
/*     */     
/*     */     public static final String SEQ = "seq";
/*     */     
/*     */     public static final String ERRORS = "errors";
/*     */     
/*     */     public static final String DURATION = "duration";
/*     */     
/*     */     public static final String TIMESTAMP = "timestamp";
/*     */     
/*     */     public static final String ATTRS = "attrs";
/*     */     
/*     */     public static final String RELEASE = "release";
/*     */     
/*     */     public static final String ENVIRONMENT = "environment";
/*     */     
/*     */     public static final String IP_ADDRESS = "ip_address";
/*     */     
/*     */     public static final String USER_AGENT = "user_agent";
/*     */     public static final String ABNORMAL_MECHANISM = "abnormal_mechanism";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 366 */     writer.beginObject();
/* 367 */     if (this.sessionId != null) {
/* 368 */       writer.name("sid").value(this.sessionId);
/*     */     }
/* 370 */     if (this.distinctId != null) {
/* 371 */       writer.name("did").value(this.distinctId);
/*     */     }
/* 373 */     if (this.init != null) {
/* 374 */       writer.name("init").value(this.init);
/*     */     }
/* 376 */     writer.name("started").value(logger, this.started);
/* 377 */     writer.name("status").value(logger, this.status.name().toLowerCase(Locale.ROOT));
/* 378 */     if (this.sequence != null) {
/* 379 */       writer.name("seq").value(this.sequence);
/*     */     }
/* 381 */     writer.name("errors").value(this.errorCount.intValue());
/* 382 */     if (this.duration != null) {
/* 383 */       writer.name("duration").value(this.duration);
/*     */     }
/* 385 */     if (this.timestamp != null) {
/* 386 */       writer.name("timestamp").value(logger, this.timestamp);
/*     */     }
/* 388 */     if (this.abnormalMechanism != null) {
/* 389 */       writer.name("abnormal_mechanism").value(logger, this.abnormalMechanism);
/*     */     }
/* 391 */     writer.name("attrs");
/* 392 */     writer.beginObject();
/* 393 */     writer.name("release").value(logger, this.release);
/* 394 */     if (this.environment != null) {
/* 395 */       writer.name("environment").value(logger, this.environment);
/*     */     }
/* 397 */     if (this.ipAddress != null) {
/* 398 */       writer.name("ip_address").value(logger, this.ipAddress);
/*     */     }
/* 400 */     if (this.userAgent != null) {
/* 401 */       writer.name("user_agent").value(logger, this.userAgent);
/*     */     }
/* 403 */     writer.endObject();
/* 404 */     if (this.unknown != null) {
/* 405 */       for (String key : this.unknown.keySet()) {
/* 406 */         Object value = this.unknown.get(key);
/* 407 */         writer.name(key);
/* 408 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 411 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 417 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 422 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<Session>
/*     */   {
/*     */     @NotNull
/*     */     public Session deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 431 */       reader.beginObject();
/*     */       
/* 433 */       Date started = null;
/* 434 */       Date timestamp = null;
/* 435 */       Integer errorCount = null;
/* 436 */       String distinctId = null;
/* 437 */       String sessionId = null;
/* 438 */       Boolean init = null;
/* 439 */       Session.State status = null;
/* 440 */       Long sequence = null;
/* 441 */       Double duration = null;
/* 442 */       String ipAddress = null;
/* 443 */       String userAgent = null;
/* 444 */       String environment = null;
/* 445 */       String release = null;
/* 446 */       String abnormalMechanism = null;
/*     */       
/* 448 */       Map<String, Object> unknown = null;
/* 449 */       while (reader.peek() == JsonToken.NAME) {
/* 450 */         String sid, statusValue, nextName = reader.nextName();
/* 451 */         switch (nextName) {
/*     */           case "sid":
/* 453 */             sid = reader.nextStringOrNull();
/* 454 */             if (sid != null && (sid.length() == 36 || sid.length() == 32)) {
/* 455 */               sessionId = sid; continue;
/*     */             } 
/* 457 */             logger.log(SentryLevel.ERROR, "%s sid is not valid.", new Object[] { sid });
/*     */             continue;
/*     */           
/*     */           case "did":
/* 461 */             distinctId = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "init":
/* 464 */             init = reader.nextBooleanOrNull();
/*     */             continue;
/*     */           case "started":
/* 467 */             started = reader.nextDateOrNull(logger);
/*     */             continue;
/*     */           case "status":
/* 470 */             statusValue = StringUtils.capitalize(reader.nextStringOrNull());
/* 471 */             if (statusValue != null) {
/* 472 */               status = Session.State.valueOf(statusValue);
/*     */             }
/*     */             continue;
/*     */           case "seq":
/* 476 */             sequence = reader.nextLongOrNull();
/*     */             continue;
/*     */           case "errors":
/* 479 */             errorCount = reader.nextIntegerOrNull();
/*     */             continue;
/*     */           case "duration":
/* 482 */             duration = reader.nextDoubleOrNull();
/*     */             continue;
/*     */           case "timestamp":
/* 485 */             timestamp = reader.nextDateOrNull(logger);
/*     */             continue;
/*     */           case "abnormal_mechanism":
/* 488 */             abnormalMechanism = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "attrs":
/* 491 */             reader.beginObject();
/* 492 */             while (reader.peek() == JsonToken.NAME) {
/* 493 */               String nextAttrName = reader.nextName();
/* 494 */               switch (nextAttrName) {
/*     */                 case "release":
/* 496 */                   release = reader.nextStringOrNull();
/*     */                   continue;
/*     */                 case "environment":
/* 499 */                   environment = reader.nextStringOrNull();
/*     */                   continue;
/*     */                 case "ip_address":
/* 502 */                   ipAddress = reader.nextStringOrNull();
/*     */                   continue;
/*     */                 case "user_agent":
/* 505 */                   userAgent = reader.nextStringOrNull();
/*     */                   continue;
/*     */               } 
/* 508 */               reader.skipValue();
/*     */             } 
/*     */             
/* 511 */             reader.endObject();
/*     */             continue;
/*     */         } 
/* 514 */         if (unknown == null) {
/* 515 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 517 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 521 */       if (status == null) {
/* 522 */         throw missingRequiredFieldException("status", logger);
/*     */       }
/* 524 */       if (started == null) {
/* 525 */         throw missingRequiredFieldException("started", logger);
/*     */       }
/* 527 */       if (errorCount == null) {
/* 528 */         throw missingRequiredFieldException("errors", logger);
/*     */       }
/* 530 */       if (release == null) {
/* 531 */         throw missingRequiredFieldException("release", logger);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 538 */       Session session = new Session(status, started, timestamp, errorCount.intValue(), distinctId, sessionId, init, sequence, duration, ipAddress, userAgent, environment, release, abnormalMechanism);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 549 */       session.setUnknown(unknown);
/* 550 */       reader.endObject();
/* 551 */       return session;
/*     */     }
/*     */     
/*     */     private Exception missingRequiredFieldException(String field, ILogger logger) {
/* 555 */       String message = "Missing required field \"" + field + "\"";
/* 556 */       Exception exception = new IllegalStateException(message);
/* 557 */       logger.log(SentryLevel.ERROR, message, exception);
/* 558 */       return exception;
/*     */     }
/*     */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\Session.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */