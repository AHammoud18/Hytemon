/*     */ package io.sentry.logger;
/*     */ 
/*     */ import io.sentry.HostnameCache;
/*     */ import io.sentry.IScope;
/*     */ import io.sentry.ISpan;
/*     */ import io.sentry.PropagationContext;
/*     */ import io.sentry.Scopes;
/*     */ import io.sentry.SentryAttribute;
/*     */ import io.sentry.SentryAttributeType;
/*     */ import io.sentry.SentryAttributes;
/*     */ import io.sentry.SentryDate;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.SentryLogEvent;
/*     */ import io.sentry.SentryLogEventAttributeValue;
/*     */ import io.sentry.SentryLogLevel;
/*     */ import io.sentry.SentryOptions;
/*     */ import io.sentry.SpanId;
/*     */ import io.sentry.protocol.SdkVersion;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.protocol.User;
/*     */ import io.sentry.util.Platform;
/*     */ import io.sentry.util.TracingUtils;
/*     */ import java.util.HashMap;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class LoggerApi implements ILoggerApi {
/*     */   @NotNull
/*     */   private final Scopes scopes;
/*     */   
/*     */   public LoggerApi(@NotNull Scopes scopes) {
/*  32 */     this.scopes = scopes;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trace(@Nullable String message, @Nullable Object... args) {
/*  37 */     log(SentryLogLevel.TRACE, message, args);
/*     */   }
/*     */ 
/*     */   
/*     */   public void debug(@Nullable String message, @Nullable Object... args) {
/*  42 */     log(SentryLogLevel.DEBUG, message, args);
/*     */   }
/*     */ 
/*     */   
/*     */   public void info(@Nullable String message, @Nullable Object... args) {
/*  47 */     log(SentryLogLevel.INFO, message, args);
/*     */   }
/*     */ 
/*     */   
/*     */   public void warn(@Nullable String message, @Nullable Object... args) {
/*  52 */     log(SentryLogLevel.WARN, message, args);
/*     */   }
/*     */ 
/*     */   
/*     */   public void error(@Nullable String message, @Nullable Object... args) {
/*  57 */     log(SentryLogLevel.ERROR, message, args);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fatal(@Nullable String message, @Nullable Object... args) {
/*  62 */     log(SentryLogLevel.FATAL, message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(@NotNull SentryLogLevel level, @Nullable String message, @Nullable Object... args) {
/*  70 */     captureLog(level, SentryLogParameters.create(null, null), message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(@NotNull SentryLogLevel level, @Nullable SentryDate timestamp, @Nullable String message, @Nullable Object... args) {
/*  79 */     captureLog(level, SentryLogParameters.create(timestamp, null), message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(@NotNull SentryLogLevel level, @NotNull SentryLogParameters params, @Nullable String message, @Nullable Object... args) {
/*  88 */     captureLog(level, params, message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void captureLog(@NotNull SentryLogLevel level, @NotNull SentryLogParameters params, @Nullable String message, @Nullable Object... args) {
/*  97 */     SentryOptions options = this.scopes.getOptions();
/*     */     try {
/*  99 */       if (!this.scopes.isEnabled()) {
/* 100 */         options
/* 101 */           .getLogger()
/* 102 */           .log(SentryLevel.WARNING, "Instance is disabled and this 'logger' call is a no-op.", new Object[0]);
/*     */         
/*     */         return;
/*     */       } 
/* 106 */       if (!options.getLogs().isEnabled()) {
/* 107 */         options
/* 108 */           .getLogger()
/* 109 */           .log(SentryLevel.WARNING, "Sentry Log is disabled and this 'logger' call is a no-op.", new Object[0]);
/*     */         
/*     */         return;
/*     */       } 
/* 113 */       if (message == null) {
/*     */         return;
/*     */       }
/*     */       
/* 117 */       SentryDate timestamp = params.getTimestamp();
/*     */       
/* 119 */       SentryDate timestampToUse = (timestamp == null) ? options.getDateProvider().now() : timestamp;
/* 120 */       String messageToUse = maybeFormatMessage(message, args);
/*     */       
/* 122 */       IScope combinedScope = this.scopes.getCombinedScopeView();
/* 123 */       PropagationContext propagationContext = combinedScope.getPropagationContext();
/* 124 */       ISpan span = combinedScope.getSpan();
/* 125 */       if (span == null) {
/* 126 */         TracingUtils.maybeUpdateBaggage(combinedScope, options);
/*     */       }
/*     */       
/* 129 */       SentryId traceId = (span == null) ? propagationContext.getTraceId() : span.getSpanContext().getTraceId();
/*     */       
/* 131 */       SpanId spanId = (span == null) ? propagationContext.getSpanId() : span.getSpanContext().getSpanId();
/* 132 */       SentryLogEvent logEvent = new SentryLogEvent(traceId, timestampToUse, messageToUse, level);
/*     */       
/* 134 */       logEvent.setAttributes(createAttributes(params, message, spanId, args));
/* 135 */       logEvent.setSeverityNumber(Integer.valueOf(level.getSeverityNumber()));
/*     */       
/* 137 */       this.scopes.getClient().captureLog(logEvent, combinedScope);
/* 138 */     } catch (Throwable e) {
/* 139 */       options.getLogger().log(SentryLevel.ERROR, "Error while capturing log event", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   private String maybeFormatMessage(@NotNull String message, @Nullable Object[] args) {
/* 145 */     if (args == null || args.length == 0) {
/* 146 */       return message;
/*     */     }
/*     */     
/*     */     try {
/* 150 */       return String.format(message, args);
/* 151 */     } catch (Throwable t) {
/* 152 */       this.scopes
/* 153 */         .getOptions()
/* 154 */         .getLogger()
/* 155 */         .log(SentryLevel.ERROR, "Error while running log through String.format", t);
/* 156 */       return message;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private HashMap<String, SentryLogEventAttributeValue> createAttributes(@NotNull SentryLogParameters params, @NotNull String message, @NotNull SpanId spanId, @Nullable Object... args) {
/* 165 */     HashMap<String, SentryLogEventAttributeValue> attributes = new HashMap<>();
/* 166 */     String origin = params.getOrigin();
/* 167 */     if (!"manual".equalsIgnoreCase(origin)) {
/* 168 */       attributes.put("sentry.origin", new SentryLogEventAttributeValue(SentryAttributeType.STRING, origin));
/*     */     }
/*     */ 
/*     */     
/* 172 */     SentryAttributes incomingAttributes = params.getAttributes();
/*     */     
/* 174 */     if (incomingAttributes != null) {
/* 175 */       for (SentryAttribute attribute : incomingAttributes.getAttributes().values()) {
/* 176 */         Object value = attribute.getValue();
/*     */         
/* 178 */         SentryAttributeType type = (attribute.getType() == null) ? getType(value) : attribute.getType();
/* 179 */         attributes.put(attribute.getName(), new SentryLogEventAttributeValue(type, value));
/*     */       } 
/*     */     }
/*     */     
/* 183 */     if (args != null) {
/* 184 */       int i = 0;
/* 185 */       for (Object arg : args) {
/* 186 */         SentryAttributeType type = getType(arg);
/* 187 */         attributes.put("sentry.message.parameter." + i, new SentryLogEventAttributeValue(type, arg));
/*     */         
/* 189 */         i++;
/*     */       } 
/* 191 */       if (i > 0 && 
/* 192 */         attributes.get("sentry.message.template") == null) {
/* 193 */         attributes.put("sentry.message.template", new SentryLogEventAttributeValue(SentryAttributeType.STRING, message));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 200 */     SdkVersion sdkVersion = this.scopes.getOptions().getSdkVersion();
/* 201 */     if (sdkVersion != null) {
/* 202 */       attributes.put("sentry.sdk.name", new SentryLogEventAttributeValue(SentryAttributeType.STRING, sdkVersion
/*     */             
/* 204 */             .getName()));
/* 205 */       attributes.put("sentry.sdk.version", new SentryLogEventAttributeValue(SentryAttributeType.STRING, sdkVersion
/*     */             
/* 207 */             .getVersion()));
/*     */     } 
/*     */     
/* 210 */     String environment = this.scopes.getOptions().getEnvironment();
/* 211 */     if (environment != null) {
/* 212 */       attributes.put("sentry.environment", new SentryLogEventAttributeValue(SentryAttributeType.STRING, environment));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 217 */     SentryId scopeReplayId = this.scopes.getCombinedScopeView().getReplayId();
/* 218 */     if (!SentryId.EMPTY_ID.equals(scopeReplayId)) {
/* 219 */       attributes.put("sentry.replay_id", new SentryLogEventAttributeValue(SentryAttributeType.STRING, scopeReplayId
/*     */             
/* 221 */             .toString()));
/*     */     } else {
/*     */       
/* 224 */       SentryId controllerReplayId = this.scopes.getOptions().getReplayController().getReplayId();
/* 225 */       if (!SentryId.EMPTY_ID.equals(controllerReplayId)) {
/* 226 */         attributes.put("sentry.replay_id", new SentryLogEventAttributeValue(SentryAttributeType.STRING, controllerReplayId
/*     */ 
/*     */               
/* 229 */               .toString()));
/* 230 */         attributes.put("sentry._internal.replay_is_buffering", new SentryLogEventAttributeValue(SentryAttributeType.BOOLEAN, 
/*     */               
/* 232 */               Boolean.valueOf(true)));
/*     */       } 
/*     */     } 
/*     */     
/* 236 */     String release = this.scopes.getOptions().getRelease();
/* 237 */     if (release != null) {
/* 238 */       attributes.put("sentry.release", new SentryLogEventAttributeValue(SentryAttributeType.STRING, release));
/*     */     }
/*     */ 
/*     */     
/* 242 */     attributes.put("sentry.trace.parent_span_id", new SentryLogEventAttributeValue(SentryAttributeType.STRING, spanId));
/*     */ 
/*     */ 
/*     */     
/* 246 */     if (Platform.isJvm()) {
/* 247 */       setServerName(attributes);
/*     */     }
/*     */     
/* 250 */     setUser(attributes);
/*     */     
/* 252 */     return attributes;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setServerName(@NotNull HashMap<String, SentryLogEventAttributeValue> attributes) {
/* 257 */     SentryOptions options = this.scopes.getOptions();
/* 258 */     String optionsServerName = options.getServerName();
/* 259 */     if (optionsServerName != null) {
/* 260 */       attributes.put("server.address", new SentryLogEventAttributeValue(SentryAttributeType.STRING, optionsServerName));
/*     */     
/*     */     }
/* 263 */     else if (options.isAttachServerName()) {
/* 264 */       String hostname = HostnameCache.getInstance().getHostname();
/* 265 */       if (hostname != null) {
/* 266 */         attributes.put("server.address", new SentryLogEventAttributeValue(SentryAttributeType.STRING, hostname));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setUser(@NotNull HashMap<String, SentryLogEventAttributeValue> attributes) {
/* 274 */     User user = this.scopes.getCombinedScopeView().getUser();
/* 275 */     if (user == null) {
/*     */ 
/*     */       
/* 278 */       String id = this.scopes.getOptions().getDistinctId();
/* 279 */       if (id != null) {
/* 280 */         attributes.put("user.id", new SentryLogEventAttributeValue(SentryAttributeType.STRING, id));
/*     */       }
/*     */     } else {
/* 283 */       String id = user.getId();
/* 284 */       if (id != null) {
/* 285 */         attributes.put("user.id", new SentryLogEventAttributeValue(SentryAttributeType.STRING, id));
/*     */       }
/* 287 */       String username = user.getUsername();
/* 288 */       if (username != null) {
/* 289 */         attributes.put("user.name", new SentryLogEventAttributeValue(SentryAttributeType.STRING, username));
/*     */       }
/*     */       
/* 292 */       String email = user.getEmail();
/* 293 */       if (email != null) {
/* 294 */         attributes.put("user.email", new SentryLogEventAttributeValue(SentryAttributeType.STRING, email));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   private SentryAttributeType getType(@Nullable Object arg) {
/* 301 */     if (arg instanceof Boolean) {
/* 302 */       return SentryAttributeType.BOOLEAN;
/*     */     }
/* 304 */     if (arg instanceof Integer) {
/* 305 */       return SentryAttributeType.INTEGER;
/*     */     }
/* 307 */     if (arg instanceof Number) {
/* 308 */       return SentryAttributeType.DOUBLE;
/*     */     }
/* 310 */     return SentryAttributeType.STRING;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\logger\LoggerApi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */