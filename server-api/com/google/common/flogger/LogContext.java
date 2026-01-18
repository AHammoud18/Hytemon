/*      */ package com.google.common.flogger;
/*      */ 
/*      */ import com.google.common.flogger.backend.LogData;
/*      */ import com.google.common.flogger.backend.Metadata;
/*      */ import com.google.common.flogger.backend.Platform;
/*      */ import com.google.common.flogger.backend.TemplateContext;
/*      */ import com.google.common.flogger.context.Tags;
/*      */ import com.google.common.flogger.parser.MessageParser;
/*      */ import com.google.common.flogger.util.CallerFinder;
/*      */ import com.google.common.flogger.util.Checks;
/*      */ import com.google.errorprone.annotations.CheckReturnValue;
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.logging.Level;
/*      */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @CheckReturnValue
/*      */ public abstract class LogContext<LOGGER extends AbstractLogger<API>, API extends LoggingApi<API>>
/*      */   implements LoggingApi<API>, LogData
/*      */ {
/*      */   public static final class Key
/*      */   {
/*   72 */     public static final MetadataKey<Throwable> LOG_CAUSE = MetadataKey.single("cause", Throwable.class);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   79 */     public static final MetadataKey<Integer> LOG_EVERY_N = MetadataKey.single("ratelimit_count", Integer.class);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   86 */     public static final MetadataKey<LogSiteStats.RateLimitPeriod> LOG_AT_MOST_EVERY = MetadataKey.single("ratelimit_period", LogSiteStats.RateLimitPeriod.class);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   94 */     public static final MetadataKey<Object> LOG_SITE_GROUPING_KEY = new MetadataKey("group_by", Object.class, true)
/*      */       {
/*      */         public void emitRepeated(Iterator<Object> keys, MetadataKey.KeyValueHandler out)
/*      */         {
/*   98 */           if (keys.hasNext()) {
/*   99 */             Object first = keys.next();
/*  100 */             if (!keys.hasNext()) {
/*  101 */               out.handle(getLabel(), first);
/*      */             } else {
/*      */               
/*  104 */               StringBuilder buf = new StringBuilder();
/*  105 */               buf.append('[').append(first);
/*      */               while (true) {
/*  107 */                 buf.append(',').append(keys.next());
/*  108 */                 if (!keys.hasNext()) {
/*  109 */                   out.handle(getLabel(), buf.append(']').toString());
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  145 */     public static final MetadataKey<Boolean> WAS_FORCED = MetadataKey.single("forced", Boolean.class);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  155 */     public static final MetadataKey<Tags> TAGS = new MetadataKey<Tags>("tags", Tags.class, false)
/*      */       {
/*      */         public void emit(Tags tags, MetadataKey.KeyValueHandler out) {
/*  158 */           for (Map.Entry<String, ? extends Set<Object>> e : (Iterable<Map.Entry<String, ? extends Set<Object>>>)tags.asMap().entrySet()) {
/*  159 */             Set<Object> values = e.getValue();
/*  160 */             if (!values.isEmpty()) {
/*  161 */               for (Object v : e.getValue())
/*  162 */                 out.handle(e.getKey(), v); 
/*      */               continue;
/*      */             } 
/*  165 */             out.handle(e.getKey(), null);
/*      */           } 
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  176 */     public static final MetadataKey<StackSize> CONTEXT_STACK_SIZE = MetadataKey.single("stack_size", StackSize.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class MutableMetadata
/*      */     extends Metadata
/*      */   {
/*      */     private static final int INITIAL_KEY_VALUE_CAPACITY = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  198 */     private Object[] keyValuePairs = new Object[8];
/*      */     
/*  200 */     private int keyValueCount = 0;
/*      */ 
/*      */     
/*      */     public int size() {
/*  204 */       return this.keyValueCount;
/*      */     }
/*      */ 
/*      */     
/*      */     public MetadataKey<?> getKey(int n) {
/*  209 */       if (n >= this.keyValueCount) {
/*  210 */         throw new IndexOutOfBoundsException();
/*      */       }
/*  212 */       return (MetadataKey)this.keyValuePairs[2 * n];
/*      */     }
/*      */ 
/*      */     
/*      */     public Object getValue(int n) {
/*  217 */       if (n >= this.keyValueCount) {
/*  218 */         throw new IndexOutOfBoundsException();
/*      */       }
/*  220 */       return this.keyValuePairs[2 * n + 1];
/*      */     }
/*      */     
/*      */     private int indexOf(MetadataKey<?> key) {
/*  224 */       for (int index = 0; index < this.keyValueCount; index++) {
/*  225 */         if (this.keyValuePairs[2 * index].equals(key)) {
/*  226 */           return index;
/*      */         }
/*      */       } 
/*  229 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*      */     @NullableDecl
/*      */     public <T> T findValue(MetadataKey<T> key) {
/*  235 */       int index = indexOf(key);
/*  236 */       return (index != -1) ? key.cast(this.keyValuePairs[2 * index + 1]) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     <T> void addValue(MetadataKey<T> key, T value) {
/*  245 */       if (!key.canRepeat()) {
/*  246 */         int index = indexOf(key);
/*  247 */         if (index != -1) {
/*  248 */           this.keyValuePairs[2 * index + 1] = Checks.checkNotNull(value, "metadata value");
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*  253 */       if (2 * (this.keyValueCount + 1) > this.keyValuePairs.length)
/*      */       {
/*      */ 
/*      */         
/*  257 */         this.keyValuePairs = Arrays.copyOf(this.keyValuePairs, 2 * this.keyValuePairs.length);
/*      */       }
/*  259 */       this.keyValuePairs[2 * this.keyValueCount] = Checks.checkNotNull(key, "metadata key");
/*  260 */       this.keyValuePairs[2 * this.keyValueCount + 1] = Checks.checkNotNull(value, "metadata value");
/*  261 */       this.keyValueCount++;
/*      */     }
/*      */ 
/*      */     
/*      */     void removeAllValues(MetadataKey<?> key) {
/*  266 */       int index = indexOf(key);
/*  267 */       if (index >= 0) {
/*  268 */         int dest = 2 * index;
/*  269 */         int src = dest + 2;
/*  270 */         while (src < 2 * this.keyValueCount) {
/*  271 */           Object nextKey = this.keyValuePairs[src];
/*  272 */           if (!nextKey.equals(key)) {
/*  273 */             this.keyValuePairs[dest] = nextKey;
/*  274 */             this.keyValuePairs[dest + 1] = this.keyValuePairs[src + 1];
/*  275 */             dest += 2;
/*      */           } 
/*  277 */           src += 2;
/*      */         } 
/*      */         
/*  280 */         this.keyValueCount -= src - dest >> 1;
/*  281 */         while (dest < src) {
/*  282 */           this.keyValuePairs[dest++] = null;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  290 */       StringBuilder out = new StringBuilder("Metadata{");
/*  291 */       for (int n = 0; n < size(); n++) {
/*  292 */         out.append(" '").append(getKey(n)).append("': ").append(getValue(n));
/*      */       }
/*  294 */       return out.append(" }").toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  303 */   private static final String LITERAL_VALUE_MESSAGE = new String();
/*      */ 
/*      */ 
/*      */   
/*      */   private final Level level;
/*      */ 
/*      */   
/*      */   private final long timestampNanos;
/*      */ 
/*      */   
/*  313 */   private MutableMetadata metadata = null;
/*      */   
/*  315 */   private LogSite logSite = null;
/*      */   
/*  317 */   private TemplateContext templateContext = null;
/*      */   
/*  319 */   private Object[] args = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LogContext(Level level, boolean isForced) {
/*  329 */     this(level, isForced, Platform.getCurrentTimeNanos());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LogContext(Level level, boolean isForced, long timestampNanos) {
/*  344 */     this.level = (Level)Checks.checkNotNull(level, "level");
/*  345 */     this.timestampNanos = timestampNanos;
/*  346 */     if (isForced) {
/*  347 */       addMetadata(Key.WAS_FORCED, Boolean.TRUE);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract API api();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract LOGGER getLogger();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract API noOp();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract MessageParser getMessageParser();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Level getLevel() {
/*  381 */     return this.level;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final long getTimestampMicros() {
/*  387 */     return TimeUnit.NANOSECONDS.toMicros(this.timestampNanos);
/*      */   }
/*      */ 
/*      */   
/*      */   public final long getTimestampNanos() {
/*  392 */     return this.timestampNanos;
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getLoggerName() {
/*  397 */     return getLogger().getBackend().getLoggerName();
/*      */   }
/*      */ 
/*      */   
/*      */   public final LogSite getLogSite() {
/*  402 */     if (this.logSite == null) {
/*  403 */       throw new IllegalStateException("cannot request log site information prior to postProcess()");
/*      */     }
/*  405 */     return this.logSite;
/*      */   }
/*      */ 
/*      */   
/*      */   public final TemplateContext getTemplateContext() {
/*  410 */     return this.templateContext;
/*      */   }
/*      */ 
/*      */   
/*      */   public final Object[] getArguments() {
/*  415 */     if (this.templateContext == null) {
/*  416 */       throw new IllegalStateException("cannot get arguments unless a template context exists");
/*      */     }
/*  418 */     return this.args;
/*      */   }
/*      */ 
/*      */   
/*      */   public final Object getLiteralArgument() {
/*  423 */     if (this.templateContext != null) {
/*  424 */       throw new IllegalStateException("cannot get literal argument if a template context exists");
/*      */     }
/*  426 */     return this.args[0];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean wasForced() {
/*  432 */     return (this.metadata != null && Boolean.TRUE.equals(this.metadata.findValue(Key.WAS_FORCED)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Metadata getMetadata() {
/*  445 */     return (this.metadata != null) ? this.metadata : Metadata.empty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final <T> void addMetadata(MetadataKey<T> key, T value) {
/*  459 */     if (this.metadata == null) {
/*  460 */       this.metadata = new MutableMetadata();
/*      */     }
/*  462 */     this.metadata.addValue(key, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void removeMetadata(MetadataKey<?> key) {
/*  472 */     if (this.metadata != null) {
/*  473 */       this.metadata.removeAllValues(key);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean postProcess(@NullableDecl LogSiteKey logSiteKey) {
/*  520 */     if (this.metadata != null) {
/*      */       
/*  522 */       if (logSiteKey != null) {
/*      */         
/*  524 */         Integer rateLimitCount = this.metadata.<Integer>findValue(Key.LOG_EVERY_N);
/*  525 */         LogSiteStats.RateLimitPeriod rateLimitPeriod = this.metadata.<LogSiteStats.RateLimitPeriod>findValue(Key.LOG_AT_MOST_EVERY);
/*  526 */         LogSiteStats stats = LogSiteStats.getStatsForKey(logSiteKey, this.metadata);
/*  527 */         if (rateLimitCount != null && !stats.incrementAndCheckInvocationCount(rateLimitCount.intValue())) {
/*  528 */           return false;
/*      */         }
/*      */         
/*  531 */         if (rateLimitPeriod != null && 
/*  532 */           !stats.checkLastTimestamp(getTimestampNanos(), rateLimitPeriod)) {
/*  533 */           return false;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  538 */       StackSize stackSize = this.metadata.<StackSize>findValue(Key.CONTEXT_STACK_SIZE);
/*  539 */       if (stackSize != null) {
/*      */         
/*  541 */         removeMetadata(Key.CONTEXT_STACK_SIZE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  558 */         LogSiteStackTrace context = new LogSiteStackTrace((Throwable)getMetadata().findValue(Key.LOG_CAUSE), stackSize, CallerFinder.getStackForCallerOf(LogContext.class, new Throwable(), stackSize.getMaxDepth(), 1));
/*      */         
/*  560 */         addMetadata(Key.LOG_CAUSE, context);
/*      */       } 
/*      */     } 
/*      */     
/*  564 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean shouldLog() {
/*  577 */     if (this.logSite == null)
/*      */     {
/*      */       
/*  580 */       this.logSite = (LogSite)Checks.checkNotNull(Platform.getCallerFinder().findLogSite(LogContext.class, 1), "logger backend must not return a null LogSite");
/*      */     }
/*      */     
/*  583 */     LogSiteKey logSiteKey = null;
/*  584 */     if (this.logSite != LogSite.INVALID) {
/*  585 */       logSiteKey = this.logSite;
/*      */     }
/*  587 */     if (!postProcess(logSiteKey)) {
/*  588 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  593 */     Tags tags = Platform.getInjectedTags();
/*  594 */     if (!tags.isEmpty()) {
/*  595 */       addMetadata(Key.TAGS, tags);
/*      */     }
/*  597 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static LogSiteKey specializeLogSiteKeyFromMetadata(LogSiteKey logSiteKey, Metadata metadata) {
/*  609 */     Checks.checkNotNull(logSiteKey, "logSiteKey");
/*  610 */     for (int n = 0, size = metadata.size(); n < size; n++) {
/*  611 */       if (Key.LOG_SITE_GROUPING_KEY.equals(metadata.getKey(n))) {
/*  612 */         Object groupByQualifier = metadata.getValue(n);
/*      */         
/*  614 */         if (groupByQualifier instanceof LoggingScope) {
/*  615 */           logSiteKey = ((LoggingScope)groupByQualifier).specialize(logSiteKey);
/*      */         } else {
/*  617 */           logSiteKey = SpecializedLogSiteKey.of(logSiteKey, groupByQualifier);
/*      */         } 
/*      */       } 
/*      */     } 
/*  621 */     return logSiteKey;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void logImpl(String message, Object... args) {
/*  630 */     this.args = args;
/*      */ 
/*      */ 
/*      */     
/*  634 */     for (int n = 0; n < args.length; n++) {
/*  635 */       if (args[n] instanceof LazyArg) {
/*  636 */         args[n] = ((LazyArg)args[n]).evaluate();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  643 */     if (message != LITERAL_VALUE_MESSAGE) {
/*  644 */       this.templateContext = new TemplateContext(getMessageParser(), message);
/*      */     }
/*  646 */     getLogger().write(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final API withInjectedLogSite(LogSite logSite) {
/*  657 */     if (this.logSite == null && logSite != null) {
/*  658 */       this.logSite = logSite;
/*      */     }
/*  660 */     return api();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final API withInjectedLogSite(String internalClassName, String methodName, int encodedLineNumber, @NullableDecl String sourceFileName) {
/*  670 */     return withInjectedLogSite(
/*  671 */         LogSite.injectedLogSite(internalClassName, methodName, encodedLineNumber, sourceFileName));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isEnabled() {
/*  682 */     return (wasForced() || getLogger().isLoggable(this.level));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final <T> API with(MetadataKey<T> key, @NullableDecl T value) {
/*  692 */     Checks.checkNotNull(key, "metadata key");
/*  693 */     if (value != null) {
/*  694 */       addMetadata(key, value);
/*      */     }
/*  696 */     return api();
/*      */   }
/*      */ 
/*      */   
/*      */   public final <T> API with(MetadataKey<Boolean> key) {
/*  701 */     return with(key, Boolean.TRUE);
/*      */   }
/*      */ 
/*      */   
/*      */   public final API withCause(Throwable cause) {
/*  706 */     if (cause != null) {
/*  707 */       addMetadata(Key.LOG_CAUSE, cause);
/*      */     }
/*  709 */     return api();
/*      */   }
/*      */ 
/*      */   
/*      */   public API withStackTrace(StackSize size) {
/*  714 */     if (Checks.checkNotNull(size, "stack size") != StackSize.NONE) {
/*  715 */       addMetadata(Key.CONTEXT_STACK_SIZE, size);
/*      */     }
/*  717 */     return api();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final API every(int n) {
/*  723 */     if (wasForced()) {
/*  724 */       return api();
/*      */     }
/*  726 */     if (n <= 0) {
/*  727 */       throw new IllegalArgumentException("rate limit count must be positive");
/*      */     }
/*      */     
/*  730 */     if (n > 1) {
/*  731 */       addMetadata(Key.LOG_EVERY_N, Integer.valueOf(n));
/*      */     }
/*  733 */     return api();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final API atMostEvery(int n, TimeUnit unit) {
/*  739 */     if (wasForced()) {
/*  740 */       return api();
/*      */     }
/*  742 */     if (n < 0) {
/*  743 */       throw new IllegalArgumentException("rate limit period cannot be negative");
/*      */     }
/*      */ 
/*      */     
/*  747 */     if (n > 0) {
/*  748 */       addMetadata(Key.LOG_AT_MOST_EVERY, LogSiteStats.newRateLimitPeriod(n, unit));
/*      */     }
/*  750 */     return api();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void log() {
/*  761 */     if (shouldLog()) logImpl(LITERAL_VALUE_MESSAGE, new Object[] { "" });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String msg) {
/*  766 */     if (shouldLog()) logImpl(LITERAL_VALUE_MESSAGE, new Object[] { msg });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, @NullableDecl Object p1) {
/*  771 */     if (shouldLog()) logImpl(message, new Object[] { p1 });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, @NullableDecl Object p1, @NullableDecl Object p2) {
/*  776 */     if (shouldLog()) logImpl(message, new Object[] { p1, p2 });
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public final void log(String message, @NullableDecl Object p1, @NullableDecl Object p2, @NullableDecl Object p3) {
/*  782 */     if (shouldLog()) logImpl(message, new Object[] { p1, p2, p3 });
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void log(String message, @NullableDecl Object p1, @NullableDecl Object p2, @NullableDecl Object p3, @NullableDecl Object p4) {
/*  792 */     if (shouldLog()) logImpl(message, new Object[] { p1, p2, p3, p4 });
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void log(String msg, @NullableDecl Object p1, @NullableDecl Object p2, @NullableDecl Object p3, @NullableDecl Object p4, @NullableDecl Object p5) {
/*  803 */     if (shouldLog()) logImpl(msg, new Object[] { p1, p2, p3, p4, p5 });
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void log(String msg, @NullableDecl Object p1, @NullableDecl Object p2, @NullableDecl Object p3, @NullableDecl Object p4, @NullableDecl Object p5, @NullableDecl Object p6) {
/*  815 */     if (shouldLog()) logImpl(msg, new Object[] { p1, p2, p3, p4, p5, p6 });
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void log(String msg, @NullableDecl Object p1, @NullableDecl Object p2, @NullableDecl Object p3, @NullableDecl Object p4, @NullableDecl Object p5, @NullableDecl Object p6, @NullableDecl Object p7) {
/*  828 */     if (shouldLog()) logImpl(msg, new Object[] { p1, p2, p3, p4, p5, p6, p7 });
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void log(String msg, @NullableDecl Object p1, @NullableDecl Object p2, @NullableDecl Object p3, @NullableDecl Object p4, @NullableDecl Object p5, @NullableDecl Object p6, @NullableDecl Object p7, @NullableDecl Object p8) {
/*  842 */     if (shouldLog()) logImpl(msg, new Object[] { p1, p2, p3, p4, p5, p6, p7, p8 });
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void log(String msg, @NullableDecl Object p1, @NullableDecl Object p2, @NullableDecl Object p3, @NullableDecl Object p4, @NullableDecl Object p5, @NullableDecl Object p6, @NullableDecl Object p7, @NullableDecl Object p8, @NullableDecl Object p9) {
/*  857 */     if (shouldLog()) logImpl(msg, new Object[] { p1, p2, p3, p4, p5, p6, p7, p8, p9 });
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void log(String msg, @NullableDecl Object p1, @NullableDecl Object p2, @NullableDecl Object p3, @NullableDecl Object p4, @NullableDecl Object p5, @NullableDecl Object p6, @NullableDecl Object p7, @NullableDecl Object p8, @NullableDecl Object p9, @NullableDecl Object p10) {
/*  873 */     if (shouldLog()) logImpl(msg, new Object[] { p1, p2, p3, p4, p5, p6, p7, p8, p9, p10 });
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void log(String msg, @NullableDecl Object p1, @NullableDecl Object p2, @NullableDecl Object p3, @NullableDecl Object p4, @NullableDecl Object p5, @NullableDecl Object p6, @NullableDecl Object p7, @NullableDecl Object p8, @NullableDecl Object p9, @NullableDecl Object p10, Object... rest) {
/*  890 */     if (shouldLog()) {
/*      */       
/*  892 */       Object[] params = new Object[rest.length + 10];
/*  893 */       params[0] = p1;
/*  894 */       params[1] = p2;
/*  895 */       params[2] = p3;
/*  896 */       params[3] = p4;
/*  897 */       params[4] = p5;
/*  898 */       params[5] = p6;
/*  899 */       params[6] = p7;
/*  900 */       params[7] = p8;
/*  901 */       params[8] = p9;
/*  902 */       params[9] = p10;
/*  903 */       System.arraycopy(rest, 0, params, 10, rest.length);
/*  904 */       logImpl(msg, params);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void log(String message, char p1) {
/*  910 */     if (shouldLog()) logImpl(message, new Object[] { Character.valueOf(p1) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, byte p1) {
/*  915 */     if (shouldLog()) logImpl(message, new Object[] { Byte.valueOf(p1) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, short p1) {
/*  920 */     if (shouldLog()) logImpl(message, new Object[] { Short.valueOf(p1) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, int p1) {
/*  925 */     if (shouldLog()) logImpl(message, new Object[] { Integer.valueOf(p1) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, long p1) {
/*  930 */     if (shouldLog()) logImpl(message, new Object[] { Long.valueOf(p1) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, @NullableDecl Object p1, boolean p2) {
/*  935 */     if (shouldLog()) logImpl(message, new Object[] { p1, Boolean.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, @NullableDecl Object p1, char p2) {
/*  940 */     if (shouldLog()) logImpl(message, new Object[] { p1, Character.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, @NullableDecl Object p1, byte p2) {
/*  945 */     if (shouldLog()) logImpl(message, new Object[] { p1, Byte.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, @NullableDecl Object p1, short p2) {
/*  950 */     if (shouldLog()) logImpl(message, new Object[] { p1, Short.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, @NullableDecl Object p1, int p2) {
/*  955 */     if (shouldLog()) logImpl(message, new Object[] { p1, Integer.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, @NullableDecl Object p1, long p2) {
/*  960 */     if (shouldLog()) logImpl(message, new Object[] { p1, Long.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, @NullableDecl Object p1, float p2) {
/*  965 */     if (shouldLog()) logImpl(message, new Object[] { p1, Float.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, @NullableDecl Object p1, double p2) {
/*  970 */     if (shouldLog()) logImpl(message, new Object[] { p1, Double.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, boolean p1, @NullableDecl Object p2) {
/*  975 */     if (shouldLog()) logImpl(message, new Object[] { Boolean.valueOf(p1), p2 });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, char p1, @NullableDecl Object p2) {
/*  980 */     if (shouldLog()) logImpl(message, new Object[] { Character.valueOf(p1), p2 });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, byte p1, @NullableDecl Object p2) {
/*  985 */     if (shouldLog()) logImpl(message, new Object[] { Byte.valueOf(p1), p2 });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, short p1, @NullableDecl Object p2) {
/*  990 */     if (shouldLog()) logImpl(message, new Object[] { Short.valueOf(p1), p2 });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, int p1, @NullableDecl Object p2) {
/*  995 */     if (shouldLog()) logImpl(message, new Object[] { Integer.valueOf(p1), p2 });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, long p1, @NullableDecl Object p2) {
/* 1000 */     if (shouldLog()) logImpl(message, new Object[] { Long.valueOf(p1), p2 });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, float p1, @NullableDecl Object p2) {
/* 1005 */     if (shouldLog()) logImpl(message, new Object[] { Float.valueOf(p1), p2 });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, double p1, @NullableDecl Object p2) {
/* 1010 */     if (shouldLog()) logImpl(message, new Object[] { Double.valueOf(p1), p2 });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, boolean p1, boolean p2) {
/* 1015 */     if (shouldLog()) logImpl(message, new Object[] { Boolean.valueOf(p1), Boolean.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, char p1, boolean p2) {
/* 1020 */     if (shouldLog()) logImpl(message, new Object[] { Character.valueOf(p1), Boolean.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, byte p1, boolean p2) {
/* 1025 */     if (shouldLog()) logImpl(message, new Object[] { Byte.valueOf(p1), Boolean.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, short p1, boolean p2) {
/* 1030 */     if (shouldLog()) logImpl(message, new Object[] { Short.valueOf(p1), Boolean.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, int p1, boolean p2) {
/* 1035 */     if (shouldLog()) logImpl(message, new Object[] { Integer.valueOf(p1), Boolean.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, long p1, boolean p2) {
/* 1040 */     if (shouldLog()) logImpl(message, new Object[] { Long.valueOf(p1), Boolean.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, float p1, boolean p2) {
/* 1045 */     if (shouldLog()) logImpl(message, new Object[] { Float.valueOf(p1), Boolean.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, double p1, boolean p2) {
/* 1050 */     if (shouldLog()) logImpl(message, new Object[] { Double.valueOf(p1), Boolean.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, boolean p1, char p2) {
/* 1055 */     if (shouldLog()) logImpl(message, new Object[] { Boolean.valueOf(p1), Character.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, char p1, char p2) {
/* 1060 */     if (shouldLog()) logImpl(message, new Object[] { Character.valueOf(p1), Character.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, byte p1, char p2) {
/* 1065 */     if (shouldLog()) logImpl(message, new Object[] { Byte.valueOf(p1), Character.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, short p1, char p2) {
/* 1070 */     if (shouldLog()) logImpl(message, new Object[] { Short.valueOf(p1), Character.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, int p1, char p2) {
/* 1075 */     if (shouldLog()) logImpl(message, new Object[] { Integer.valueOf(p1), Character.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, long p1, char p2) {
/* 1080 */     if (shouldLog()) logImpl(message, new Object[] { Long.valueOf(p1), Character.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, float p1, char p2) {
/* 1085 */     if (shouldLog()) logImpl(message, new Object[] { Float.valueOf(p1), Character.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, double p1, char p2) {
/* 1090 */     if (shouldLog()) logImpl(message, new Object[] { Double.valueOf(p1), Character.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, boolean p1, byte p2) {
/* 1095 */     if (shouldLog()) logImpl(message, new Object[] { Boolean.valueOf(p1), Byte.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, char p1, byte p2) {
/* 1100 */     if (shouldLog()) logImpl(message, new Object[] { Character.valueOf(p1), Byte.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, byte p1, byte p2) {
/* 1105 */     if (shouldLog()) logImpl(message, new Object[] { Byte.valueOf(p1), Byte.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, short p1, byte p2) {
/* 1110 */     if (shouldLog()) logImpl(message, new Object[] { Short.valueOf(p1), Byte.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, int p1, byte p2) {
/* 1115 */     if (shouldLog()) logImpl(message, new Object[] { Integer.valueOf(p1), Byte.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, long p1, byte p2) {
/* 1120 */     if (shouldLog()) logImpl(message, new Object[] { Long.valueOf(p1), Byte.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, float p1, byte p2) {
/* 1125 */     if (shouldLog()) logImpl(message, new Object[] { Float.valueOf(p1), Byte.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, double p1, byte p2) {
/* 1130 */     if (shouldLog()) logImpl(message, new Object[] { Double.valueOf(p1), Byte.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, boolean p1, short p2) {
/* 1135 */     if (shouldLog()) logImpl(message, new Object[] { Boolean.valueOf(p1), Short.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, char p1, short p2) {
/* 1140 */     if (shouldLog()) logImpl(message, new Object[] { Character.valueOf(p1), Short.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, byte p1, short p2) {
/* 1145 */     if (shouldLog()) logImpl(message, new Object[] { Byte.valueOf(p1), Short.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, short p1, short p2) {
/* 1150 */     if (shouldLog()) logImpl(message, new Object[] { Short.valueOf(p1), Short.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, int p1, short p2) {
/* 1155 */     if (shouldLog()) logImpl(message, new Object[] { Integer.valueOf(p1), Short.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, long p1, short p2) {
/* 1160 */     if (shouldLog()) logImpl(message, new Object[] { Long.valueOf(p1), Short.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, float p1, short p2) {
/* 1165 */     if (shouldLog()) logImpl(message, new Object[] { Float.valueOf(p1), Short.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, double p1, short p2) {
/* 1170 */     if (shouldLog()) logImpl(message, new Object[] { Double.valueOf(p1), Short.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, boolean p1, int p2) {
/* 1175 */     if (shouldLog()) logImpl(message, new Object[] { Boolean.valueOf(p1), Integer.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, char p1, int p2) {
/* 1180 */     if (shouldLog()) logImpl(message, new Object[] { Character.valueOf(p1), Integer.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, byte p1, int p2) {
/* 1185 */     if (shouldLog()) logImpl(message, new Object[] { Byte.valueOf(p1), Integer.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, short p1, int p2) {
/* 1190 */     if (shouldLog()) logImpl(message, new Object[] { Short.valueOf(p1), Integer.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, int p1, int p2) {
/* 1195 */     if (shouldLog()) logImpl(message, new Object[] { Integer.valueOf(p1), Integer.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, long p1, int p2) {
/* 1200 */     if (shouldLog()) logImpl(message, new Object[] { Long.valueOf(p1), Integer.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, float p1, int p2) {
/* 1205 */     if (shouldLog()) logImpl(message, new Object[] { Float.valueOf(p1), Integer.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, double p1, int p2) {
/* 1210 */     if (shouldLog()) logImpl(message, new Object[] { Double.valueOf(p1), Integer.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, boolean p1, long p2) {
/* 1215 */     if (shouldLog()) logImpl(message, new Object[] { Boolean.valueOf(p1), Long.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, char p1, long p2) {
/* 1220 */     if (shouldLog()) logImpl(message, new Object[] { Character.valueOf(p1), Long.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, byte p1, long p2) {
/* 1225 */     if (shouldLog()) logImpl(message, new Object[] { Byte.valueOf(p1), Long.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, short p1, long p2) {
/* 1230 */     if (shouldLog()) logImpl(message, new Object[] { Short.valueOf(p1), Long.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, int p1, long p2) {
/* 1235 */     if (shouldLog()) logImpl(message, new Object[] { Integer.valueOf(p1), Long.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, long p1, long p2) {
/* 1240 */     if (shouldLog()) logImpl(message, new Object[] { Long.valueOf(p1), Long.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, float p1, long p2) {
/* 1245 */     if (shouldLog()) logImpl(message, new Object[] { Float.valueOf(p1), Long.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, double p1, long p2) {
/* 1250 */     if (shouldLog()) logImpl(message, new Object[] { Double.valueOf(p1), Long.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, boolean p1, float p2) {
/* 1255 */     if (shouldLog()) logImpl(message, new Object[] { Boolean.valueOf(p1), Float.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, char p1, float p2) {
/* 1260 */     if (shouldLog()) logImpl(message, new Object[] { Character.valueOf(p1), Float.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, byte p1, float p2) {
/* 1265 */     if (shouldLog()) logImpl(message, new Object[] { Byte.valueOf(p1), Float.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, short p1, float p2) {
/* 1270 */     if (shouldLog()) logImpl(message, new Object[] { Short.valueOf(p1), Float.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, int p1, float p2) {
/* 1275 */     if (shouldLog()) logImpl(message, new Object[] { Integer.valueOf(p1), Float.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, long p1, float p2) {
/* 1280 */     if (shouldLog()) logImpl(message, new Object[] { Long.valueOf(p1), Float.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, float p1, float p2) {
/* 1285 */     if (shouldLog()) logImpl(message, new Object[] { Float.valueOf(p1), Float.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, double p1, float p2) {
/* 1290 */     if (shouldLog()) logImpl(message, new Object[] { Double.valueOf(p1), Float.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, boolean p1, double p2) {
/* 1295 */     if (shouldLog()) logImpl(message, new Object[] { Boolean.valueOf(p1), Double.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, char p1, double p2) {
/* 1300 */     if (shouldLog()) logImpl(message, new Object[] { Character.valueOf(p1), Double.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, byte p1, double p2) {
/* 1305 */     if (shouldLog()) logImpl(message, new Object[] { Byte.valueOf(p1), Double.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, short p1, double p2) {
/* 1310 */     if (shouldLog()) logImpl(message, new Object[] { Short.valueOf(p1), Double.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, int p1, double p2) {
/* 1315 */     if (shouldLog()) logImpl(message, new Object[] { Integer.valueOf(p1), Double.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, long p1, double p2) {
/* 1320 */     if (shouldLog()) logImpl(message, new Object[] { Long.valueOf(p1), Double.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, float p1, double p2) {
/* 1325 */     if (shouldLog()) logImpl(message, new Object[] { Float.valueOf(p1), Double.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void log(String message, double p1, double p2) {
/* 1330 */     if (shouldLog()) logImpl(message, new Object[] { Double.valueOf(p1), Double.valueOf(p2) });
/*      */   
/*      */   }
/*      */   
/*      */   public final void logVarargs(String message, @NullableDecl Object[] params) {
/* 1335 */     if (shouldLog())
/*      */     {
/* 1337 */       logImpl(message, Arrays.copyOf(params, params.length));
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\LogContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */