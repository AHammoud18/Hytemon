/*     */ package com.hypixel.hytale.logger.sentry;
/*     */ 
/*     */ import io.sentry.Breadcrumb;
/*     */ import io.sentry.Hint;
/*     */ import io.sentry.IScopes;
/*     */ import io.sentry.ScopesAdapter;
/*     */ import io.sentry.Sentry;
/*     */ import io.sentry.SentryAttribute;
/*     */ import io.sentry.SentryAttributes;
/*     */ import io.sentry.SentryEvent;
/*     */ import io.sentry.SentryIntegrationPackageStorage;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.SentryLogLevel;
/*     */ import io.sentry.exception.ExceptionMechanismException;
/*     */ import io.sentry.logger.SentryLogParameters;
/*     */ import io.sentry.protocol.Mechanism;
/*     */ import io.sentry.protocol.Message;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.logging.Filter;
/*     */ import java.util.logging.Handler;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.LogManager;
/*     */ import java.util.logging.LogRecord;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class HytaleSentryHandler
/*     */   extends Handler
/*     */ {
/*     */   public static final String MECHANISM_TYPE = "JulSentryHandler";
/*     */   public static final String THREAD_ID = "thread_id";
/*     */   private final IScopes scope;
/*     */   private boolean printfStyle;
/*     */   @Nonnull
/*  61 */   private Level minimumBreadcrumbLevel = Level.INFO; @Nonnull
/*  62 */   private Level minimumEventLevel = Level.SEVERE; @Nonnull
/*  63 */   private Level minimumLevel = Level.INFO;
/*     */   
/*     */   static {
/*  66 */     SentryIntegrationPackageStorage.getInstance()
/*  67 */       .addPackage("maven:io.sentry:sentry-jul", "8.29.0");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HytaleSentryHandler(@Nonnull IScopes scope) {
/*  76 */     this(scope, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   HytaleSentryHandler(@Nonnull IScopes scope, boolean configureFromLogManager) {
/*  83 */     setFilter(new DropSentryFilter());
/*  84 */     if (configureFromLogManager) {
/*  85 */       retrieveProperties();
/*     */     }
/*  87 */     this.scope = scope;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void publish(@Nonnull LogRecord record) {
/*  93 */     if (!isLoggable(record)) {
/*     */       return;
/*     */     }
/*     */     try {
/*  97 */       if (ScopesAdapter.getInstance().getOptions().getLogs().isEnabled() && record
/*  98 */         .getLevel().intValue() >= this.minimumLevel.intValue()) {
/*  99 */         captureLog(record);
/*     */       }
/* 101 */       if (record.getLevel().intValue() >= this.minimumEventLevel.intValue()) {
/* 102 */         Hint hint = new Hint();
/* 103 */         hint.set("syntheticException", record);
/*     */         
/* 105 */         this.scope.captureEvent(createEvent(record), hint);
/*     */       } 
/* 107 */       if (record.getLevel().intValue() >= this.minimumBreadcrumbLevel.intValue()) {
/* 108 */         Hint hint = new Hint();
/* 109 */         hint.set("jul:logRecord", record);
/*     */         
/* 111 */         Sentry.addBreadcrumb(createBreadcrumb(record), hint);
/*     */       } 
/* 113 */     } catch (RuntimeException e) {
/* 114 */       reportError("An exception occurred while creating a new event in Sentry", e, 1);
/*     */     } 
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
/*     */   protected void captureLog(@Nonnull LogRecord loggingEvent) {
/* 129 */     SentryLogLevel sentryLevel = toSentryLogLevel(loggingEvent.getLevel());
/*     */     
/* 131 */     Object[] arguments = loggingEvent.getParameters();
/* 132 */     SentryAttributes attributes = SentryAttributes.of(new SentryAttribute[0]);
/*     */     
/* 134 */     String message = loggingEvent.getMessage();
/* 135 */     if (loggingEvent.getResourceBundle() != null && loggingEvent
/* 136 */       .getResourceBundle().containsKey(loggingEvent.getMessage())) {
/* 137 */       message = loggingEvent.getResourceBundle().getString(loggingEvent.getMessage());
/*     */     }
/*     */     
/* 140 */     String formattedMessage = maybeFormatted(arguments, message);
/*     */     
/* 142 */     if (!formattedMessage.equals(message)) {
/* 143 */       attributes.add(SentryAttribute.stringAttribute("sentry.message.template", message));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     SentryLogParameters params = SentryLogParameters.create(attributes);
/* 153 */     params.setOrigin("auto.log.jul.hytale");
/*     */     
/* 155 */     Sentry.logger().log(sentryLevel, params, formattedMessage, arguments);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private String maybeFormatted(@Nonnull Object[] arguments, @Nonnull String message) {
/* 160 */     if (arguments != null) {
/*     */       try {
/* 162 */         return formatMessage(message, arguments);
/* 163 */       } catch (RuntimeException runtimeException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 168 */     return message;
/*     */   }
/*     */ 
/*     */   
/*     */   private void retrieveProperties() {
/* 173 */     LogManager manager = LogManager.getLogManager();
/* 174 */     String className = HytaleSentryHandler.class.getName();
/* 175 */     setPrintfStyle(Boolean.parseBoolean(manager.getProperty(className + ".printfStyle")));
/* 176 */     setLevel(parseLevelOrDefault(manager.getProperty(className + ".level")));
/*     */     
/* 178 */     String minimumBreadCrumbLevel = manager.getProperty(className + ".minimumBreadcrumbLevel");
/* 179 */     if (minimumBreadCrumbLevel != null) {
/* 180 */       setMinimumBreadcrumbLevel(parseLevelOrDefault(minimumBreadCrumbLevel));
/*     */     }
/* 182 */     String minimumEventLevel = manager.getProperty(className + ".minimumEventLevel");
/* 183 */     if (minimumEventLevel != null) {
/* 184 */       setMinimumEventLevel(parseLevelOrDefault(minimumEventLevel));
/*     */     }
/* 186 */     String minimumLevel = manager.getProperty(className + ".minimumLevel");
/* 187 */     if (minimumLevel != null) {
/* 188 */       setMinimumLevel(parseLevelOrDefault(minimumLevel));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static SentryLevel formatLevel(@Nonnull Level level) {
/* 199 */     if (level.intValue() >= Level.SEVERE.intValue())
/* 200 */       return SentryLevel.ERROR; 
/* 201 */     if (level.intValue() >= Level.WARNING.intValue())
/* 202 */       return SentryLevel.WARNING; 
/* 203 */     if (level.intValue() >= Level.INFO.intValue())
/* 204 */       return SentryLevel.INFO; 
/* 205 */     if (level.intValue() >= Level.ALL.intValue()) {
/* 206 */       return SentryLevel.DEBUG;
/*     */     }
/* 208 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static SentryLogLevel toSentryLogLevel(@Nonnull Level level) {
/* 219 */     if (level.intValue() >= Level.SEVERE.intValue())
/* 220 */       return SentryLogLevel.ERROR; 
/* 221 */     if (level.intValue() >= Level.WARNING.intValue())
/* 222 */       return SentryLogLevel.WARN; 
/* 223 */     if (level.intValue() >= Level.INFO.intValue())
/* 224 */       return SentryLogLevel.INFO; 
/* 225 */     if (level.intValue() >= Level.FINE.intValue()) {
/* 226 */       return SentryLogLevel.DEBUG;
/*     */     }
/* 228 */     return SentryLogLevel.TRACE;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private Level parseLevelOrDefault(@Nonnull String levelName) {
/*     */     try {
/* 234 */       return Level.parse(levelName.trim());
/* 235 */     } catch (RuntimeException e) {
/* 236 */       return Level.WARNING;
/*     */     } 
/*     */   }
/*     */   @Nonnull
/*     */   private Breadcrumb createBreadcrumb(@Nonnull LogRecord record) {
/* 241 */     Breadcrumb breadcrumb = new Breadcrumb();
/* 242 */     breadcrumb.setLevel(formatLevel(record.getLevel()));
/* 243 */     breadcrumb.setCategory(record.getLoggerName());
/* 244 */     if (record.getParameters() != null) {
/*     */       try {
/* 246 */         breadcrumb.setMessage(formatMessage(record.getMessage(), record.getParameters()));
/* 247 */       } catch (RuntimeException e) {
/* 248 */         breadcrumb.setMessage(record.getMessage());
/*     */       } 
/*     */     } else {
/* 251 */       breadcrumb.setMessage(record.getMessage());
/*     */     } 
/* 253 */     return breadcrumb;
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
/*     */   @Nonnull
/*     */   SentryEvent createEvent(@Nonnull LogRecord record) {
/* 266 */     SentryEvent event = new SentryEvent(new Date(record.getMillis()));
/* 267 */     event.setLevel(formatLevel(record.getLevel()));
/* 268 */     event.setLogger(record.getLoggerName());
/*     */     
/* 270 */     Message sentryMessage = new Message();
/* 271 */     sentryMessage.setParams(toParams(record.getParameters()));
/*     */     
/* 273 */     String message = record.getMessage();
/* 274 */     if (record.getResourceBundle() != null && record
/* 275 */       .getResourceBundle().containsKey(record.getMessage())) {
/* 276 */       message = record.getResourceBundle().getString(record.getMessage());
/*     */     }
/* 278 */     sentryMessage.setMessage(message);
/* 279 */     if (record.getParameters() != null) {
/*     */       try {
/* 281 */         sentryMessage.setFormatted(formatMessage(message, record.getParameters()));
/* 282 */       } catch (RuntimeException runtimeException) {}
/*     */     }
/*     */ 
/*     */     
/* 286 */     event.setMessage(sentryMessage);
/*     */     
/* 288 */     Throwable throwable = record.getThrown();
/* 289 */     if (throwable != null) {
/* 290 */       Mechanism mechanism = new Mechanism();
/* 291 */       mechanism.setType("JulSentryHandler");
/*     */       
/* 293 */       ExceptionMechanismException exceptionMechanismException = new ExceptionMechanismException(mechanism, throwable, Thread.currentThread());
/* 294 */       event.setThrowable((Throwable)exceptionMechanismException);
/*     */     } 
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
/* 307 */     event.setExtra("thread_id", String.valueOf(record.getLongThreadID()));
/* 308 */     return event;
/*     */   }
/*     */   @Nonnull
/*     */   private List<String> toParams(@Nullable Object[] arguments) {
/* 312 */     List<String> result = new ArrayList<>();
/* 313 */     if (arguments != null) {
/* 314 */       for (Object argument : arguments) {
/* 315 */         if (argument != null) {
/* 316 */           result.add(argument.toString());
/*     */         }
/*     */       } 
/*     */     }
/* 320 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private String formatMessage(@Nonnull String message, @Nullable Object[] parameters) {
/*     */     String formatted;
/* 333 */     if (this.printfStyle) {
/* 334 */       formatted = String.format(message, parameters);
/*     */     } else {
/* 336 */       formatted = MessageFormat.format(message, parameters);
/*     */     } 
/* 338 */     return formatted;
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() {}
/*     */ 
/*     */   
/*     */   public void close() throws SecurityException {
/*     */     try {
/* 347 */       Sentry.close();
/* 348 */     } catch (RuntimeException e) {
/* 349 */       reportError("An exception occurred while closing the Sentry connection", e, 3);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPrintfStyle(boolean printfStyle) {
/* 357 */     this.printfStyle = printfStyle;
/*     */   }
/*     */   
/*     */   public void setMinimumBreadcrumbLevel(@Nullable Level minimumBreadcrumbLevel) {
/* 361 */     if (minimumBreadcrumbLevel != null)
/* 362 */       this.minimumBreadcrumbLevel = minimumBreadcrumbLevel; 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Level getMinimumBreadcrumbLevel() {
/* 367 */     return this.minimumBreadcrumbLevel;
/*     */   }
/*     */   
/*     */   public void setMinimumEventLevel(@Nullable Level minimumEventLevel) {
/* 371 */     if (minimumEventLevel != null)
/* 372 */       this.minimumEventLevel = minimumEventLevel; 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Level getMinimumEventLevel() {
/* 377 */     return this.minimumEventLevel;
/*     */   }
/*     */   
/*     */   public void setMinimumLevel(@Nullable Level minimumLevel) {
/* 381 */     if (minimumLevel != null)
/* 382 */       this.minimumLevel = minimumLevel; 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Level getMinimumLevel() {
/* 387 */     return this.minimumLevel;
/*     */   }
/*     */   
/*     */   public boolean isPrintfStyle() {
/* 391 */     return this.printfStyle;
/*     */   }
/*     */   
/*     */   private static final class DropSentryFilter
/*     */     implements Filter {
/*     */     public boolean isLoggable(@Nonnull LogRecord record) {
/* 397 */       String loggerName = record.getLoggerName();
/* 398 */       return (loggerName == null || 
/* 399 */         !loggerName.startsWith("server.io.sentry") || loggerName
/* 400 */         .startsWith("server.io.sentry.samples"));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\logger\sentry\HytaleSentryHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */