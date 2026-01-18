/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.SentryLockReason;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ 
/*     */ public final class SentryStackFrame
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @Nullable
/*     */   private List<String> preContext;
/*     */   @Nullable
/*     */   private List<String> postContext;
/*     */   @Nullable
/*     */   private Map<String, Object> vars;
/*     */   @Nullable
/*     */   private List<Integer> framesOmitted;
/*     */   @Nullable
/*     */   private String filename;
/*     */   @Nullable
/*     */   private String function;
/*     */   @Nullable
/*     */   private String module;
/*     */   @Nullable
/*     */   private Integer lineno;
/*     */   @Nullable
/*     */   private Integer colno;
/*     */   @Nullable
/*     */   private String absPath;
/*     */   @Nullable
/*     */   private String contextLine;
/*     */   @Nullable
/*     */   private Boolean inApp;
/*     */   @Nullable
/*     */   private String _package;
/*     */   @Nullable
/*     */   private Boolean _native;
/*     */   @Nullable
/*     */   private String platform;
/*     */   @Nullable
/*     */   private String imageAddr;
/*     */   @Nullable
/*     */   private String symbolAddr;
/*     */   @Nullable
/*     */   private String instructionAddr;
/*     */   @Nullable
/*     */   private String addrMode;
/*     */   @Nullable
/*     */   private String symbol;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   @Nullable
/*     */   private String rawFunction;
/*     */   @Nullable
/*     */   private SentryLockReason lock;
/*     */   
/*     */   @Nullable
/*     */   public List<String> getPreContext() {
/* 159 */     return this.preContext;
/*     */   }
/*     */   
/*     */   public void setPreContext(@Nullable List<String> preContext) {
/* 163 */     this.preContext = preContext;
/*     */   }
/*     */   @Nullable
/*     */   public List<String> getPostContext() {
/* 167 */     return this.postContext;
/*     */   }
/*     */   
/*     */   public void setPostContext(@Nullable List<String> postContext) {
/* 171 */     this.postContext = postContext;
/*     */   }
/*     */   @Nullable
/*     */   public Map<String, Object> getVars() {
/* 175 */     return this.vars;
/*     */   }
/*     */   
/*     */   public void setVars(@Nullable Map<String, Object> vars) {
/* 179 */     this.vars = vars;
/*     */   }
/*     */   @Nullable
/*     */   public List<Integer> getFramesOmitted() {
/* 183 */     return this.framesOmitted;
/*     */   }
/*     */   
/*     */   public void setFramesOmitted(@Nullable List<Integer> framesOmitted) {
/* 187 */     this.framesOmitted = framesOmitted;
/*     */   }
/*     */   @Nullable
/*     */   public String getFilename() {
/* 191 */     return this.filename;
/*     */   }
/*     */   
/*     */   public void setFilename(@Nullable String filename) {
/* 195 */     this.filename = filename;
/*     */   }
/*     */   @Nullable
/*     */   public String getFunction() {
/* 199 */     return this.function;
/*     */   }
/*     */   
/*     */   public void setFunction(@Nullable String function) {
/* 203 */     this.function = function;
/*     */   }
/*     */   @Nullable
/*     */   public String getModule() {
/* 207 */     return this.module;
/*     */   }
/*     */   
/*     */   public void setModule(@Nullable String module) {
/* 211 */     this.module = module;
/*     */   }
/*     */   @Nullable
/*     */   public Integer getLineno() {
/* 215 */     return this.lineno;
/*     */   }
/*     */   
/*     */   public void setLineno(@Nullable Integer lineno) {
/* 219 */     this.lineno = lineno;
/*     */   }
/*     */   @Nullable
/*     */   public Integer getColno() {
/* 223 */     return this.colno;
/*     */   }
/*     */   
/*     */   public void setColno(@Nullable Integer colno) {
/* 227 */     this.colno = colno;
/*     */   }
/*     */   @Nullable
/*     */   public String getAbsPath() {
/* 231 */     return this.absPath;
/*     */   }
/*     */   
/*     */   public void setAbsPath(@Nullable String absPath) {
/* 235 */     this.absPath = absPath;
/*     */   }
/*     */   @Nullable
/*     */   public String getContextLine() {
/* 239 */     return this.contextLine;
/*     */   }
/*     */   
/*     */   public void setContextLine(@Nullable String contextLine) {
/* 243 */     this.contextLine = contextLine;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean isInApp() {
/* 247 */     return this.inApp;
/*     */   }
/*     */   
/*     */   public void setInApp(@Nullable Boolean inApp) {
/* 251 */     this.inApp = inApp;
/*     */   }
/*     */   @Nullable
/*     */   public String getPackage() {
/* 255 */     return this._package;
/*     */   }
/*     */   
/*     */   public void setPackage(@Nullable String _package) {
/* 259 */     this._package = _package;
/*     */   }
/*     */   @Nullable
/*     */   public String getPlatform() {
/* 263 */     return this.platform;
/*     */   }
/*     */   
/*     */   public void setPlatform(@Nullable String platform) {
/* 267 */     this.platform = platform;
/*     */   }
/*     */   @Nullable
/*     */   public String getImageAddr() {
/* 271 */     return this.imageAddr;
/*     */   }
/*     */   
/*     */   public void setImageAddr(@Nullable String imageAddr) {
/* 275 */     this.imageAddr = imageAddr;
/*     */   }
/*     */   @Nullable
/*     */   public String getSymbolAddr() {
/* 279 */     return this.symbolAddr;
/*     */   }
/*     */   
/*     */   public void setSymbolAddr(@Nullable String symbolAddr) {
/* 283 */     this.symbolAddr = symbolAddr;
/*     */   }
/*     */   @Nullable
/*     */   public String getInstructionAddr() {
/* 287 */     return this.instructionAddr;
/*     */   }
/*     */   
/*     */   public void setInstructionAddr(@Nullable String instructionAddr) {
/* 291 */     this.instructionAddr = instructionAddr;
/*     */   }
/*     */   @Nullable
/*     */   public String getAddrMode() {
/* 295 */     return this.addrMode;
/*     */   }
/*     */   
/*     */   public void setAddrMode(@Nullable String addrMode) {
/* 299 */     this.addrMode = addrMode;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean isNative() {
/* 303 */     return this._native;
/*     */   }
/*     */   
/*     */   public void setNative(@Nullable Boolean _native) {
/* 307 */     this._native = _native;
/*     */   }
/*     */   @Nullable
/*     */   public String getRawFunction() {
/* 311 */     return this.rawFunction;
/*     */   }
/*     */   
/*     */   public void setRawFunction(@Nullable String rawFunction) {
/* 315 */     this.rawFunction = rawFunction;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getSymbol() {
/* 320 */     return this.symbol;
/*     */   }
/*     */   
/*     */   public void setSymbol(@Nullable String symbol) {
/* 324 */     this.symbol = symbol;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SentryLockReason getLock() {
/* 329 */     return this.lock;
/*     */   }
/*     */   
/*     */   public void setLock(@Nullable SentryLockReason lock) {
/* 333 */     this.lock = lock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 341 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 346 */     this.unknown = unknown;
/*     */   }
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String FILENAME = "filename";
/*     */     public static final String FUNCTION = "function";
/*     */     public static final String MODULE = "module";
/*     */     public static final String LINENO = "lineno";
/*     */     public static final String COLNO = "colno";
/*     */     public static final String ABS_PATH = "abs_path";
/*     */     public static final String CONTEXT_LINE = "context_line";
/*     */     public static final String IN_APP = "in_app";
/*     */     public static final String PACKAGE = "package";
/*     */     public static final String NATIVE = "native";
/*     */     public static final String PLATFORM = "platform";
/*     */     public static final String IMAGE_ADDR = "image_addr";
/*     */     public static final String SYMBOL_ADDR = "symbol_addr";
/*     */     public static final String INSTRUCTION_ADDR = "instruction_addr";
/*     */     public static final String ADDR_MODE = "addr_mode";
/*     */     public static final String RAW_FUNCTION = "raw_function";
/*     */     public static final String SYMBOL = "symbol";
/*     */     public static final String LOCK = "lock";
/*     */     public static final String PRE_CONTEXT = "pre_context";
/*     */     public static final String POST_CONTEXT = "post_context";
/*     */     public static final String VARS = "vars";
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 375 */     if (o == null || getClass() != o.getClass()) return false; 
/* 376 */     SentryStackFrame that = (SentryStackFrame)o;
/* 377 */     return (Objects.equals(this.preContext, that.preContext) && 
/* 378 */       Objects.equals(this.postContext, that.postContext) && 
/* 379 */       Objects.equals(this.vars, that.vars) && 
/* 380 */       Objects.equals(this.framesOmitted, that.framesOmitted) && 
/* 381 */       Objects.equals(this.filename, that.filename) && 
/* 382 */       Objects.equals(this.function, that.function) && 
/* 383 */       Objects.equals(this.module, that.module) && 
/* 384 */       Objects.equals(this.lineno, that.lineno) && 
/* 385 */       Objects.equals(this.colno, that.colno) && 
/* 386 */       Objects.equals(this.absPath, that.absPath) && 
/* 387 */       Objects.equals(this.contextLine, that.contextLine) && 
/* 388 */       Objects.equals(this.inApp, that.inApp) && 
/* 389 */       Objects.equals(this._package, that._package) && 
/* 390 */       Objects.equals(this._native, that._native) && 
/* 391 */       Objects.equals(this.platform, that.platform) && 
/* 392 */       Objects.equals(this.imageAddr, that.imageAddr) && 
/* 393 */       Objects.equals(this.symbolAddr, that.symbolAddr) && 
/* 394 */       Objects.equals(this.instructionAddr, that.instructionAddr) && 
/* 395 */       Objects.equals(this.addrMode, that.addrMode) && 
/* 396 */       Objects.equals(this.symbol, that.symbol) && 
/* 397 */       Objects.equals(this.unknown, that.unknown) && 
/* 398 */       Objects.equals(this.rawFunction, that.rawFunction) && 
/* 399 */       Objects.equals(this.lock, that.lock));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 404 */     return Objects.hash(new Object[] { this.preContext, this.postContext, this.vars, this.framesOmitted, this.filename, this.function, this.module, this.lineno, this.colno, this.absPath, this.contextLine, this.inApp, this._package, this._native, this.platform, this.imageAddr, this.symbolAddr, this.instructionAddr, this.addrMode, this.symbol, this.unknown, this.rawFunction, this.lock });
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
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 433 */     writer.beginObject();
/* 434 */     if (this.filename != null) {
/* 435 */       writer.name("filename").value(this.filename);
/*     */     }
/* 437 */     if (this.function != null) {
/* 438 */       writer.name("function").value(this.function);
/*     */     }
/* 440 */     if (this.module != null) {
/* 441 */       writer.name("module").value(this.module);
/*     */     }
/* 443 */     if (this.lineno != null) {
/* 444 */       writer.name("lineno").value(this.lineno);
/*     */     }
/* 446 */     if (this.colno != null) {
/* 447 */       writer.name("colno").value(this.colno);
/*     */     }
/* 449 */     if (this.absPath != null) {
/* 450 */       writer.name("abs_path").value(this.absPath);
/*     */     }
/* 452 */     if (this.contextLine != null) {
/* 453 */       writer.name("context_line").value(this.contextLine);
/*     */     }
/* 455 */     if (this.inApp != null) {
/* 456 */       writer.name("in_app").value(this.inApp);
/*     */     }
/* 458 */     if (this._package != null) {
/* 459 */       writer.name("package").value(this._package);
/*     */     }
/* 461 */     if (this._native != null) {
/* 462 */       writer.name("native").value(this._native);
/*     */     }
/* 464 */     if (this.platform != null) {
/* 465 */       writer.name("platform").value(this.platform);
/*     */     }
/* 467 */     if (this.imageAddr != null) {
/* 468 */       writer.name("image_addr").value(this.imageAddr);
/*     */     }
/* 470 */     if (this.symbolAddr != null) {
/* 471 */       writer.name("symbol_addr").value(this.symbolAddr);
/*     */     }
/* 473 */     if (this.instructionAddr != null) {
/* 474 */       writer.name("instruction_addr").value(this.instructionAddr);
/*     */     }
/* 476 */     if (this.addrMode != null) {
/* 477 */       writer.name("addr_mode").value(this.addrMode);
/*     */     }
/* 479 */     if (this.rawFunction != null) {
/* 480 */       writer.name("raw_function").value(this.rawFunction);
/*     */     }
/* 482 */     if (this.symbol != null) {
/* 483 */       writer.name("symbol").value(this.symbol);
/*     */     }
/* 485 */     if (this.lock != null) {
/* 486 */       writer.name("lock").value(logger, this.lock);
/*     */     }
/* 488 */     if (this.preContext != null && !this.preContext.isEmpty()) {
/* 489 */       writer.name("pre_context").value(logger, this.preContext);
/*     */     }
/* 491 */     if (this.postContext != null && !this.postContext.isEmpty()) {
/* 492 */       writer.name("post_context").value(logger, this.postContext);
/*     */     }
/* 494 */     if (this.vars != null && !this.vars.isEmpty()) {
/* 495 */       writer.name("vars").value(logger, this.vars);
/*     */     }
/* 497 */     if (this.unknown != null) {
/* 498 */       for (String key : this.unknown.keySet()) {
/* 499 */         Object value = this.unknown.get(key);
/* 500 */         writer.name(key);
/* 501 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 504 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SentryStackFrame>
/*     */   {
/*     */     @NotNull
/*     */     public SentryStackFrame deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 512 */       SentryStackFrame sentryStackFrame = new SentryStackFrame();
/* 513 */       Map<String, Object> unknown = null;
/* 514 */       reader.beginObject();
/* 515 */       while (reader.peek() == JsonToken.NAME) {
/* 516 */         String nextName = reader.nextName();
/* 517 */         switch (nextName) {
/*     */           case "filename":
/* 519 */             sentryStackFrame.filename = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "function":
/* 522 */             sentryStackFrame.function = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "module":
/* 525 */             sentryStackFrame.module = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "lineno":
/* 528 */             sentryStackFrame.lineno = reader.nextIntegerOrNull();
/*     */             continue;
/*     */           case "colno":
/* 531 */             sentryStackFrame.colno = reader.nextIntegerOrNull();
/*     */             continue;
/*     */           case "abs_path":
/* 534 */             sentryStackFrame.absPath = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "context_line":
/* 537 */             sentryStackFrame.contextLine = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "in_app":
/* 540 */             sentryStackFrame.inApp = reader.nextBooleanOrNull();
/*     */             continue;
/*     */           case "package":
/* 543 */             sentryStackFrame._package = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "native":
/* 546 */             sentryStackFrame._native = reader.nextBooleanOrNull();
/*     */             continue;
/*     */           case "platform":
/* 549 */             sentryStackFrame.platform = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "image_addr":
/* 552 */             sentryStackFrame.imageAddr = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "symbol_addr":
/* 555 */             sentryStackFrame.symbolAddr = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "instruction_addr":
/* 558 */             sentryStackFrame.instructionAddr = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "addr_mode":
/* 561 */             sentryStackFrame.addrMode = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "raw_function":
/* 564 */             sentryStackFrame.rawFunction = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "symbol":
/* 567 */             sentryStackFrame.symbol = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "lock":
/* 570 */             sentryStackFrame.lock = (SentryLockReason)reader.nextOrNull(logger, (JsonDeserializer)new SentryLockReason.Deserializer());
/*     */             continue;
/*     */           case "pre_context":
/* 573 */             sentryStackFrame.preContext = (List)reader.nextObjectOrNull();
/*     */             continue;
/*     */           case "post_context":
/* 576 */             sentryStackFrame.postContext = (List)reader.nextObjectOrNull();
/*     */             continue;
/*     */           case "vars":
/* 579 */             sentryStackFrame.vars = (Map)reader.nextObjectOrNull();
/*     */             continue;
/*     */         } 
/* 582 */         if (unknown == null) {
/* 583 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 585 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 589 */       sentryStackFrame.setUnknown(unknown);
/* 590 */       reader.endObject();
/* 591 */       return sentryStackFrame;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\SentryStackFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */