/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.protocol.TransactionNameSource;
/*     */ import io.sentry.util.AutoClosableReentrantLock;
/*     */ import io.sentry.util.SampleRateUtils;
/*     */ import io.sentry.util.StringUtils;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.net.URLEncoder;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Experimental
/*     */ public final class Baggage {
/*     */   @NotNull
/*     */   static final String CHARSET = "UTF-8";
/*     */   @NotNull
/*  32 */   static final Integer MAX_BAGGAGE_STRING_LENGTH = Integer.valueOf(8192); @NotNull
/*  33 */   static final Integer MAX_BAGGAGE_LIST_MEMBER_COUNT = Integer.valueOf(64);
/*     */   @NotNull
/*     */   static final String SENTRY_BAGGAGE_PREFIX = "sentry-";
/*     */   
/*     */   private static class DecimalFormatterThreadLocal extends ThreadLocal<DecimalFormat> {
/*     */     private DecimalFormatterThreadLocal() {}
/*     */     
/*     */     protected DecimalFormat initialValue() {
/*  41 */       return new DecimalFormat("#.################", DecimalFormatSymbols.getInstance(Locale.ROOT));
/*     */     }
/*     */   }
/*     */   
/*  45 */   private static final DecimalFormatterThreadLocal decimalFormatter = new DecimalFormatterThreadLocal();
/*     */   @NotNull
/*     */   private final ConcurrentHashMap<String, String> keyValues;
/*     */   @NotNull
/*  49 */   private final AutoClosableReentrantLock keyValuesLock = new AutoClosableReentrantLock(); @Nullable
/*     */   private Double sampleRate; @Nullable
/*     */   private Double sampleRand;
/*     */   @Nullable
/*     */   private final String thirdPartyHeader;
/*     */   private boolean mutable;
/*     */   private final boolean shouldFreeze;
/*     */   @NotNull
/*     */   final ILogger logger;
/*     */   
/*     */   @NotNull
/*     */   public static Baggage fromHeader(@Nullable String headerValue) {
/*  61 */     return fromHeader(headerValue, false, 
/*  62 */         ScopesAdapter.getInstance().getOptions().getLogger());
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public static Baggage fromHeader(@Nullable List<String> headerValues) {
/*  67 */     return fromHeader(headerValues, false, 
/*  68 */         ScopesAdapter.getInstance().getOptions().getLogger());
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public static Baggage fromHeader(String headerValue, @NotNull ILogger logger) {
/*  74 */     return fromHeader(headerValue, false, logger);
/*     */   }
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public static Baggage fromHeader(@Nullable List<String> headerValues, @NotNull ILogger logger) {
/*  81 */     return fromHeader(headerValues, false, logger);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public static Baggage fromHeader(@Nullable List<String> headerValues, boolean includeThirdPartyValues, @NotNull ILogger logger) {
/*  91 */     if (headerValues != null) {
/*  92 */       return fromHeader(
/*  93 */           StringUtils.join(",", headerValues), includeThirdPartyValues, logger);
/*     */     }
/*  95 */     return fromHeader((String)null, includeThirdPartyValues, logger);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public static Baggage fromHeader(@Nullable String headerValue, boolean includeThirdPartyValues, @NotNull ILogger logger) {
/* 106 */     ConcurrentHashMap<String, String> keyValues = new ConcurrentHashMap<>();
/*     */     
/* 108 */     List<String> thirdPartyKeyValueStrings = new ArrayList<>();
/* 109 */     boolean shouldFreeze = false;
/*     */     
/* 111 */     Double sampleRate = null;
/* 112 */     Double sampleRand = null;
/*     */     
/* 114 */     if (headerValue != null) {
/*     */       
/*     */       try {
/* 117 */         String[] keyValueStrings = headerValue.split(",", -1);
/* 118 */         for (String keyValueString : keyValueStrings) {
/* 119 */           if (keyValueString.trim().startsWith("sentry-")) {
/*     */ 
/*     */             
/*     */             try {
/* 123 */               int separatorIndex = keyValueString.indexOf("=");
/* 124 */               String key = keyValueString.substring(0, separatorIndex).trim();
/* 125 */               String keyDecoded = decode(key);
/* 126 */               String value = keyValueString.substring(separatorIndex + 1).trim();
/* 127 */               String valueDecoded = decode(value);
/*     */               
/* 129 */               if ("sentry-sample_rate".equals(keyDecoded)) {
/* 130 */                 sampleRate = toDouble(valueDecoded);
/* 131 */               } else if ("sentry-sample_rand".equals(keyDecoded)) {
/* 132 */                 sampleRand = toDouble(valueDecoded);
/*     */               } else {
/* 134 */                 keyValues.put(keyDecoded, valueDecoded);
/*     */               } 
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
/* 146 */               if (!"sentry-sample_rand".equalsIgnoreCase(key)) {
/* 147 */                 shouldFreeze = true;
/*     */               }
/* 149 */             } catch (Throwable e) {
/* 150 */               logger.log(SentryLevel.ERROR, e, "Unable to decode baggage key value pair %s", new Object[] { keyValueString });
/*     */             
/*     */             }
/*     */ 
/*     */           
/*     */           }
/* 156 */           else if (includeThirdPartyValues) {
/* 157 */             thirdPartyKeyValueStrings.add(keyValueString.trim());
/*     */           } 
/*     */         } 
/* 160 */       } catch (Throwable e) {
/* 161 */         logger.log(SentryLevel.ERROR, e, "Unable to decode baggage header %s", new Object[] { headerValue });
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 167 */     String thirdPartyHeader = thirdPartyKeyValueStrings.isEmpty() ? null : StringUtils.join(",", thirdPartyKeyValueStrings);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     return new Baggage(keyValues, sampleRate, sampleRand, thirdPartyHeader, true, shouldFreeze, logger);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public static Baggage fromEvent(@NotNull SentryBaseEvent event, @Nullable String transaction, @NotNull SentryOptions options) {
/* 183 */     Baggage baggage = new Baggage(options.getLogger());
/* 184 */     SpanContext trace = event.getContexts().getTrace();
/* 185 */     baggage.setTraceId((trace != null) ? trace.getTraceId().toString() : null);
/* 186 */     baggage.setPublicKey(options.retrieveParsedDsn().getPublicKey());
/* 187 */     baggage.setRelease(event.getRelease());
/* 188 */     baggage.setEnvironment(event.getEnvironment());
/* 189 */     baggage.setTransaction(transaction);
/*     */     
/* 191 */     baggage.setSampleRate(null);
/* 192 */     baggage.setSampled(null);
/* 193 */     baggage.setSampleRand(null);
/* 194 */     Object replayId = event.getContexts().get("replay_id");
/* 195 */     if (replayId != null && !replayId.toString().equals(SentryId.EMPTY_ID.toString())) {
/* 196 */       baggage.setReplayId(replayId.toString());
/*     */       
/* 198 */       event.getContexts().remove("replay_id");
/*     */     } 
/* 200 */     baggage.freeze();
/* 201 */     return baggage;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public Baggage(@NotNull ILogger logger) {
/* 206 */     this(new ConcurrentHashMap<>(), null, null, null, true, false, logger);
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public Baggage(@NotNull Baggage baggage) {
/* 211 */     this(baggage.keyValues, baggage.sampleRate, baggage.sampleRand, baggage.thirdPartyHeader, baggage.mutable, baggage.shouldFreeze, baggage.logger);
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
/*     */   
/*     */   @Internal
/*     */   public Baggage(@NotNull ConcurrentHashMap<String, String> keyValues, @Nullable Double sampleRate, @Nullable Double sampleRand, @Nullable String thirdPartyHeader, boolean isMutable, boolean shouldFreeze, @NotNull ILogger logger) {
/* 230 */     this.keyValues = keyValues;
/* 231 */     this.sampleRate = sampleRate;
/* 232 */     this.sampleRand = sampleRand;
/* 233 */     this.logger = logger;
/* 234 */     this.thirdPartyHeader = thirdPartyHeader;
/* 235 */     this.mutable = isMutable;
/* 236 */     this.shouldFreeze = shouldFreeze;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void freeze() {
/* 241 */     this.mutable = false;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public boolean isMutable() {
/* 246 */     return this.mutable;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public boolean isShouldFreeze() {
/* 251 */     return this.shouldFreeze;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getThirdPartyHeader() {
/* 256 */     return this.thirdPartyHeader;
/*     */   } @NotNull
/*     */   public String toHeaderString(@Nullable String thirdPartyBaggageHeaderString) {
/*     */     Set<String> keys;
/* 260 */     StringBuilder sb = new StringBuilder();
/* 261 */     String separator = "";
/* 262 */     int listMemberCount = 0;
/*     */     
/* 264 */     if (thirdPartyBaggageHeaderString != null && !thirdPartyBaggageHeaderString.isEmpty()) {
/* 265 */       sb.append(thirdPartyBaggageHeaderString);
/* 266 */       listMemberCount = StringUtils.countOf(thirdPartyBaggageHeaderString, ',') + 1;
/* 267 */       separator = ",";
/*     */     } 
/*     */ 
/*     */     
/* 271 */     ISentryLifecycleToken ignored = this.keyValuesLock.acquire(); 
/* 272 */     try { keys = new TreeSet<>(Collections.list(this.keyValues.keys()));
/* 273 */       if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/* 274 */         try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  keys.add("sentry-sample_rate");
/* 275 */     keys.add("sentry-sample_rand");
/*     */     
/* 277 */     for (String key : keys) {
/*     */       String value;
/* 279 */       if ("sentry-sample_rate".equals(key)) {
/* 280 */         value = sampleRateToString(this.sampleRate);
/* 281 */       } else if ("sentry-sample_rand".equals(key)) {
/* 282 */         value = sampleRateToString(this.sampleRand);
/*     */       } else {
/* 284 */         value = this.keyValues.get(key);
/*     */       } 
/*     */       
/* 287 */       if (value != null) {
/* 288 */         if (listMemberCount >= MAX_BAGGAGE_LIST_MEMBER_COUNT.intValue()) {
/* 289 */           this.logger.log(SentryLevel.ERROR, "Not adding baggage value %s as the total number of list members would exceed the maximum of %s.", new Object[] { key, MAX_BAGGAGE_LIST_MEMBER_COUNT });
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/*     */         try {
/* 296 */           String encodedKey = encode(key);
/* 297 */           String encodedValue = encode(value);
/* 298 */           String encodedKeyValue = separator + encodedKey + "=" + encodedValue;
/*     */           
/* 300 */           int valueLength = encodedKeyValue.length();
/* 301 */           int totalLengthIfValueAdded = sb.length() + valueLength;
/* 302 */           if (totalLengthIfValueAdded > MAX_BAGGAGE_STRING_LENGTH.intValue()) {
/* 303 */             this.logger.log(SentryLevel.ERROR, "Not adding baggage value %s as the total header value length would exceed the maximum of %s.", new Object[] { key, MAX_BAGGAGE_STRING_LENGTH });
/*     */ 
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 309 */           listMemberCount++;
/* 310 */           sb.append(encodedKeyValue);
/* 311 */           separator = ",";
/*     */         }
/* 313 */         catch (Throwable e) {
/* 314 */           this.logger.log(SentryLevel.ERROR, e, "Unable to encode baggage key value pair (key=%s,value=%s).", new Object[] { key, value });
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 324 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private String encode(@NotNull String value) throws UnsupportedEncodingException {
/* 328 */     return URLEncoder.encode(value, "UTF-8").replaceAll("\\+", "%20");
/*     */   }
/*     */   
/*     */   private static String decode(@NotNull String value) throws UnsupportedEncodingException {
/* 332 */     return URLDecoder.decode(value, "UTF-8");
/*     */   }
/*     */   @Internal
/*     */   @Nullable
/*     */   public String get(@Nullable String key) {
/* 337 */     if (key == null) {
/* 338 */       return null;
/*     */     }
/*     */     
/* 341 */     return this.keyValues.get(key);
/*     */   }
/*     */   @Internal
/*     */   @Nullable
/*     */   public String getTraceId() {
/* 346 */     return get("sentry-trace_id");
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setTraceId(@Nullable String traceId) {
/* 351 */     set("sentry-trace_id", traceId);
/*     */   }
/*     */   @Internal
/*     */   @Nullable
/*     */   public String getPublicKey() {
/* 356 */     return get("sentry-public_key");
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setPublicKey(@Nullable String publicKey) {
/* 361 */     set("sentry-public_key", publicKey);
/*     */   }
/*     */   @Internal
/*     */   @Nullable
/*     */   public String getEnvironment() {
/* 366 */     return get("sentry-environment");
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setEnvironment(@Nullable String environment) {
/* 371 */     set("sentry-environment", environment);
/*     */   }
/*     */   @Internal
/*     */   @Nullable
/*     */   public String getRelease() {
/* 376 */     return get("sentry-release");
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setRelease(@Nullable String release) {
/* 381 */     set("sentry-release", release);
/*     */   }
/*     */   @Internal
/*     */   @Nullable
/*     */   public String getUserId() {
/* 386 */     return get("sentry-user_id");
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setUserId(@Nullable String userId) {
/* 391 */     set("sentry-user_id", userId);
/*     */   }
/*     */   @Internal
/*     */   @Nullable
/*     */   public String getTransaction() {
/* 396 */     return get("sentry-transaction");
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setTransaction(@Nullable String transaction) {
/* 401 */     set("sentry-transaction", transaction);
/*     */   }
/*     */   @Internal
/*     */   @Nullable
/*     */   public Double getSampleRate() {
/* 406 */     return this.sampleRate;
/*     */   }
/*     */   @Internal
/*     */   @Nullable
/*     */   public String getSampled() {
/* 411 */     return get("sentry-sampled");
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setSampleRate(@Nullable Double sampleRate) {
/* 416 */     if (isMutable()) {
/* 417 */       this.sampleRate = sampleRate;
/*     */     }
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void forceSetSampleRate(@Nullable Double sampleRate) {
/* 423 */     this.sampleRate = sampleRate;
/*     */   }
/*     */   @Internal
/*     */   @Nullable
/*     */   public Double getSampleRand() {
/* 428 */     return this.sampleRand;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setSampleRand(@Nullable Double sampleRand) {
/* 433 */     if (isMutable()) {
/* 434 */       this.sampleRand = sampleRand;
/*     */     }
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setSampled(@Nullable String sampled) {
/* 440 */     set("sentry-sampled", sampled);
/*     */   }
/*     */   @Internal
/*     */   @Nullable
/*     */   public String getReplayId() {
/* 445 */     return get("sentry-replay_id");
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setReplayId(@Nullable String replayId) {
/* 450 */     set("sentry-replay_id", replayId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public void set(@NotNull String key, @Nullable String value) {
/* 461 */     if (this.mutable)
/* 462 */       if (value == null) {
/* 463 */         this.keyValues.remove(key);
/*     */       } else {
/* 465 */         this.keyValues.put(key, value);
/*     */       }  
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public Map<String, Object> getUnknown() {
/* 472 */     Map<String, Object> unknown = new ConcurrentHashMap<>();
/* 473 */     ISentryLifecycleToken ignored = this.keyValuesLock.acquire(); 
/* 474 */     try { for (Map.Entry<String, String> keyValue : this.keyValues.entrySet()) {
/* 475 */         String key = keyValue.getKey();
/* 476 */         String value = keyValue.getValue();
/* 477 */         if (!DSCKeys.ALL.contains(key) && 
/* 478 */           value != null) {
/* 479 */           String unknownKey = key.replaceFirst("sentry-", "");
/* 480 */           unknown.put(unknownKey, value);
/*     */         } 
/*     */       } 
/*     */       
/* 484 */       if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/* 485 */         try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return unknown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public void setValuesFromTransaction(@NotNull SentryId traceId, @Nullable SentryId replayId, @NotNull SentryOptions sentryOptions, @Nullable TracesSamplingDecision samplingDecision, @Nullable String transactionName, @Nullable TransactionNameSource transactionNameSource) {
/* 496 */     setTraceId(traceId.toString());
/* 497 */     setPublicKey(sentryOptions.retrieveParsedDsn().getPublicKey());
/* 498 */     setRelease(sentryOptions.getRelease());
/* 499 */     setEnvironment(sentryOptions.getEnvironment());
/* 500 */     setTransaction(isHighQualityTransactionName(transactionNameSource) ? transactionName : null);
/* 501 */     if (replayId != null && !SentryId.EMPTY_ID.equals(replayId)) {
/* 502 */       setReplayId(replayId.toString());
/*     */     }
/* 504 */     setSampleRate(sampleRate(samplingDecision));
/* 505 */     setSampled(StringUtils.toString(sampled(samplingDecision)));
/* 506 */     setSampleRand(sampleRand(samplingDecision));
/*     */   }
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public void setValuesFromSamplingDecision(@Nullable TracesSamplingDecision samplingDecision) {
/* 512 */     if (samplingDecision == null) {
/*     */       return;
/*     */     }
/*     */     
/* 516 */     setSampled(StringUtils.toString(sampled(samplingDecision)));
/*     */     
/* 518 */     if (samplingDecision.getSampleRand() != null) {
/* 519 */       setSampleRand(sampleRand(samplingDecision));
/*     */     }
/*     */     
/* 522 */     if (samplingDecision.getSampleRate() != null) {
/* 523 */       forceSetSampleRate(sampleRate(samplingDecision));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public void setValuesFromScope(@NotNull IScope scope, @NotNull SentryOptions options) {
/* 530 */     PropagationContext propagationContext = scope.getPropagationContext();
/* 531 */     SentryId replayId = scope.getReplayId();
/* 532 */     setTraceId(propagationContext.getTraceId().toString());
/* 533 */     setPublicKey(options.retrieveParsedDsn().getPublicKey());
/* 534 */     setRelease(options.getRelease());
/* 535 */     setEnvironment(options.getEnvironment());
/* 536 */     if (!SentryId.EMPTY_ID.equals(replayId)) {
/* 537 */       setReplayId(replayId.toString());
/*     */     }
/* 539 */     setTransaction(null);
/* 540 */     setSampleRate(null);
/* 541 */     setSampled(null);
/*     */   }
/*     */   @Nullable
/*     */   private static Double sampleRate(@Nullable TracesSamplingDecision samplingDecision) {
/* 545 */     if (samplingDecision == null) {
/* 546 */       return null;
/*     */     }
/*     */     
/* 549 */     return samplingDecision.getSampleRate();
/*     */   }
/*     */   @Nullable
/*     */   private static Double sampleRand(@Nullable TracesSamplingDecision samplingDecision) {
/* 553 */     if (samplingDecision == null) {
/* 554 */       return null;
/*     */     }
/*     */     
/* 557 */     return samplingDecision.getSampleRand();
/*     */   }
/*     */   @Nullable
/*     */   private static String sampleRateToString(@Nullable Double sampleRateAsDouble) {
/* 561 */     if (!SampleRateUtils.isValidTracesSampleRate(sampleRateAsDouble, false)) {
/* 562 */       return null;
/*     */     }
/* 564 */     return decimalFormatter.get().format(sampleRateAsDouble);
/*     */   }
/*     */   @Nullable
/*     */   private static Boolean sampled(@Nullable TracesSamplingDecision samplingDecision) {
/* 568 */     if (samplingDecision == null) {
/* 569 */       return null;
/*     */     }
/*     */     
/* 572 */     return samplingDecision.getSampled();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isHighQualityTransactionName(@Nullable TransactionNameSource transactionNameSource) {
/* 577 */     return (transactionNameSource != null && 
/* 578 */       !TransactionNameSource.URL.equals(transactionNameSource));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static Double toDouble(@Nullable String stringValue) {
/* 583 */     if (stringValue != null) {
/*     */       try {
/* 585 */         double doubleValue = Double.parseDouble(stringValue);
/* 586 */         if (SampleRateUtils.isValidTracesSampleRate(Double.valueOf(doubleValue), false)) {
/* 587 */           return Double.valueOf(doubleValue);
/*     */         }
/* 589 */       } catch (NumberFormatException e) {
/* 590 */         return null;
/*     */       } 
/*     */     }
/* 593 */     return null;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @Nullable
/*     */   public TraceContext toTraceContext() {
/* 599 */     String traceIdString = getTraceId();
/* 600 */     String replayIdString = getReplayId();
/* 601 */     String publicKey = getPublicKey();
/*     */     
/* 603 */     if (traceIdString != null && publicKey != null) {
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
/* 615 */       TraceContext traceContext = new TraceContext(new SentryId(traceIdString), publicKey, getRelease(), getEnvironment(), getUserId(), getTransaction(), sampleRateToString(getSampleRate()), getSampled(), (replayIdString == null) ? null : new SentryId(replayIdString), sampleRateToString(getSampleRand()));
/* 616 */       traceContext.setUnknown(getUnknown());
/* 617 */       return traceContext;
/*     */     } 
/* 619 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public static final class DSCKeys
/*     */   {
/*     */     public static final String TRACE_ID = "sentry-trace_id";
/*     */     
/*     */     public static final String PUBLIC_KEY = "sentry-public_key";
/*     */     public static final String RELEASE = "sentry-release";
/*     */     public static final String USER_ID = "sentry-user_id";
/*     */     public static final String ENVIRONMENT = "sentry-environment";
/*     */     public static final String TRANSACTION = "sentry-transaction";
/*     */     public static final String SAMPLE_RATE = "sentry-sample_rate";
/*     */     public static final String SAMPLE_RAND = "sentry-sample_rand";
/*     */     public static final String SAMPLED = "sentry-sampled";
/*     */     public static final String REPLAY_ID = "sentry-replay_id";
/* 637 */     public static final List<String> ALL = Arrays.asList(new String[] { "sentry-trace_id", "sentry-public_key", "sentry-release", "sentry-user_id", "sentry-environment", "sentry-transaction", "sentry-sample_rate", "sentry-sample_rand", "sentry-sampled", "sentry-replay_id" });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\Baggage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */