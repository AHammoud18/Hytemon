/*     */ package io.netty.util.internal.logging;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import org.apache.commons.logging.Log;
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
/*     */ @Deprecated
/*     */ class CommonsLogger
/*     */   extends AbstractInternalLogger
/*     */ {
/*     */   private static final long serialVersionUID = 8647838678388394885L;
/*     */   private final transient Log logger;
/*     */   
/*     */   CommonsLogger(Log logger, String name) {
/*  60 */     super(name);
/*  61 */     this.logger = (Log)ObjectUtil.checkNotNull(logger, "logger");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTraceEnabled() {
/*  70 */     return this.logger.isTraceEnabled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trace(String msg) {
/*  81 */     this.logger.trace(msg);
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
/*     */   public void trace(String format, Object arg) {
/* 100 */     if (this.logger.isTraceEnabled()) {
/* 101 */       FormattingTuple ft = MessageFormatter.format(format, arg);
/* 102 */       this.logger.trace(ft.getMessage(), ft.getThrowable());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trace(String format, Object argA, Object argB) {
/* 124 */     if (this.logger.isTraceEnabled()) {
/* 125 */       FormattingTuple ft = MessageFormatter.format(format, argA, argB);
/* 126 */       this.logger.trace(ft.getMessage(), ft.getThrowable());
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void trace(String format, Object... arguments) {
/* 144 */     if (this.logger.isTraceEnabled()) {
/* 145 */       FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
/* 146 */       this.logger.trace(ft.getMessage(), ft.getThrowable());
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
/*     */   public void trace(String msg, Throwable t) {
/* 161 */     this.logger.trace(msg, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDebugEnabled() {
/* 170 */     return this.logger.isDebugEnabled();
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
/*     */   public void debug(String msg) {
/* 183 */     this.logger.debug(msg);
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
/*     */   public void debug(String format, Object arg) {
/* 202 */     if (this.logger.isDebugEnabled()) {
/* 203 */       FormattingTuple ft = MessageFormatter.format(format, arg);
/* 204 */       this.logger.debug(ft.getMessage(), ft.getThrowable());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void debug(String format, Object argA, Object argB) {
/* 226 */     if (this.logger.isDebugEnabled()) {
/* 227 */       FormattingTuple ft = MessageFormatter.format(format, argA, argB);
/* 228 */       this.logger.debug(ft.getMessage(), ft.getThrowable());
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void debug(String format, Object... arguments) {
/* 246 */     if (this.logger.isDebugEnabled()) {
/* 247 */       FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
/* 248 */       this.logger.debug(ft.getMessage(), ft.getThrowable());
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
/*     */   public void debug(String msg, Throwable t) {
/* 263 */     this.logger.debug(msg, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInfoEnabled() {
/* 272 */     return this.logger.isInfoEnabled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void info(String msg) {
/* 283 */     this.logger.info(msg);
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
/*     */   public void info(String format, Object arg) {
/* 303 */     if (this.logger.isInfoEnabled()) {
/* 304 */       FormattingTuple ft = MessageFormatter.format(format, arg);
/* 305 */       this.logger.info(ft.getMessage(), ft.getThrowable());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void info(String format, Object argA, Object argB) {
/* 326 */     if (this.logger.isInfoEnabled()) {
/* 327 */       FormattingTuple ft = MessageFormatter.format(format, argA, argB);
/* 328 */       this.logger.info(ft.getMessage(), ft.getThrowable());
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void info(String format, Object... arguments) {
/* 346 */     if (this.logger.isInfoEnabled()) {
/* 347 */       FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
/* 348 */       this.logger.info(ft.getMessage(), ft.getThrowable());
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
/*     */   public void info(String msg, Throwable t) {
/* 363 */     this.logger.info(msg, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWarnEnabled() {
/* 372 */     return this.logger.isWarnEnabled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void warn(String msg) {
/* 383 */     this.logger.warn(msg);
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
/*     */   public void warn(String format, Object arg) {
/* 402 */     if (this.logger.isWarnEnabled()) {
/* 403 */       FormattingTuple ft = MessageFormatter.format(format, arg);
/* 404 */       this.logger.warn(ft.getMessage(), ft.getThrowable());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void warn(String format, Object argA, Object argB) {
/* 426 */     if (this.logger.isWarnEnabled()) {
/* 427 */       FormattingTuple ft = MessageFormatter.format(format, argA, argB);
/* 428 */       this.logger.warn(ft.getMessage(), ft.getThrowable());
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void warn(String format, Object... arguments) {
/* 446 */     if (this.logger.isWarnEnabled()) {
/* 447 */       FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
/* 448 */       this.logger.warn(ft.getMessage(), ft.getThrowable());
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
/*     */   
/*     */   public void warn(String msg, Throwable t) {
/* 464 */     this.logger.warn(msg, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isErrorEnabled() {
/* 473 */     return this.logger.isErrorEnabled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void error(String msg) {
/* 484 */     this.logger.error(msg);
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
/*     */   public void error(String format, Object arg) {
/* 503 */     if (this.logger.isErrorEnabled()) {
/* 504 */       FormattingTuple ft = MessageFormatter.format(format, arg);
/* 505 */       this.logger.error(ft.getMessage(), ft.getThrowable());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void error(String format, Object argA, Object argB) {
/* 527 */     if (this.logger.isErrorEnabled()) {
/* 528 */       FormattingTuple ft = MessageFormatter.format(format, argA, argB);
/* 529 */       this.logger.error(ft.getMessage(), ft.getThrowable());
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void error(String format, Object... arguments) {
/* 547 */     if (this.logger.isErrorEnabled()) {
/* 548 */       FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
/* 549 */       this.logger.error(ft.getMessage(), ft.getThrowable());
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
/*     */   public void error(String msg, Throwable t) {
/* 564 */     this.logger.error(msg, t);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\logging\CommonsLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */