/*     */ package io.netty.util.internal.logging;
/*     */ 
/*     */ import org.slf4j.helpers.FormattingTuple;
/*     */ import org.slf4j.helpers.MessageFormatter;
/*     */ import org.slf4j.spi.LocationAwareLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class LocationAwareSlf4JLogger
/*     */   extends AbstractInternalLogger
/*     */ {
/*  31 */   static final String FQCN = LocationAwareSlf4JLogger.class.getName();
/*     */   
/*     */   private static final long serialVersionUID = -8292030083201538180L;
/*     */   private final transient LocationAwareLogger logger;
/*     */   
/*     */   LocationAwareSlf4JLogger(LocationAwareLogger logger) {
/*  37 */     super(logger.getName());
/*  38 */     this.logger = logger;
/*     */   }
/*     */   
/*     */   private void log(int level, String message) {
/*  42 */     this.logger.log(null, FQCN, level, message, null, null);
/*     */   }
/*     */   
/*     */   private void log(int level, String message, Throwable cause) {
/*  46 */     this.logger.log(null, FQCN, level, message, null, cause);
/*     */   }
/*     */   
/*     */   private void log(int level, FormattingTuple tuple) {
/*  50 */     this.logger.log(null, FQCN, level, tuple.getMessage(), null, tuple.getThrowable());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTraceEnabled() {
/*  55 */     return this.logger.isTraceEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public void trace(String msg) {
/*  60 */     if (isTraceEnabled()) {
/*  61 */       log(0, msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void trace(String format, Object arg) {
/*  67 */     if (isTraceEnabled()) {
/*  68 */       log(0, MessageFormatter.format(format, arg));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void trace(String format, Object argA, Object argB) {
/*  74 */     if (isTraceEnabled()) {
/*  75 */       log(0, MessageFormatter.format(format, argA, argB));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void trace(String format, Object... argArray) {
/*  81 */     if (isTraceEnabled()) {
/*  82 */       log(0, MessageFormatter.arrayFormat(format, argArray));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void trace(String msg, Throwable t) {
/*  88 */     if (isTraceEnabled()) {
/*  89 */       log(0, msg, t);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDebugEnabled() {
/*  95 */     return this.logger.isDebugEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public void debug(String msg) {
/* 100 */     if (isDebugEnabled()) {
/* 101 */       log(10, msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void debug(String format, Object arg) {
/* 107 */     if (isDebugEnabled()) {
/* 108 */       log(10, MessageFormatter.format(format, arg));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void debug(String format, Object argA, Object argB) {
/* 114 */     if (isDebugEnabled()) {
/* 115 */       log(10, MessageFormatter.format(format, argA, argB));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void debug(String format, Object... argArray) {
/* 121 */     if (isDebugEnabled()) {
/* 122 */       log(10, MessageFormatter.arrayFormat(format, argArray));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void debug(String msg, Throwable t) {
/* 128 */     if (isDebugEnabled()) {
/* 129 */       log(10, msg, t);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInfoEnabled() {
/* 135 */     return this.logger.isInfoEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public void info(String msg) {
/* 140 */     if (isInfoEnabled()) {
/* 141 */       log(20, msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void info(String format, Object arg) {
/* 147 */     if (isInfoEnabled()) {
/* 148 */       log(20, MessageFormatter.format(format, arg));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void info(String format, Object argA, Object argB) {
/* 154 */     if (isInfoEnabled()) {
/* 155 */       log(20, MessageFormatter.format(format, argA, argB));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void info(String format, Object... argArray) {
/* 161 */     if (isInfoEnabled()) {
/* 162 */       log(20, MessageFormatter.arrayFormat(format, argArray));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void info(String msg, Throwable t) {
/* 168 */     if (isInfoEnabled()) {
/* 169 */       log(20, msg, t);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWarnEnabled() {
/* 175 */     return this.logger.isWarnEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public void warn(String msg) {
/* 180 */     if (isWarnEnabled()) {
/* 181 */       log(30, msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void warn(String format, Object arg) {
/* 187 */     if (isWarnEnabled()) {
/* 188 */       log(30, MessageFormatter.format(format, arg));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void warn(String format, Object... argArray) {
/* 194 */     if (isWarnEnabled()) {
/* 195 */       log(30, MessageFormatter.arrayFormat(format, argArray));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void warn(String format, Object argA, Object argB) {
/* 201 */     if (isWarnEnabled()) {
/* 202 */       log(30, MessageFormatter.format(format, argA, argB));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void warn(String msg, Throwable t) {
/* 208 */     if (isWarnEnabled()) {
/* 209 */       log(30, msg, t);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isErrorEnabled() {
/* 215 */     return this.logger.isErrorEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public void error(String msg) {
/* 220 */     if (isErrorEnabled()) {
/* 221 */       log(40, msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void error(String format, Object arg) {
/* 227 */     if (isErrorEnabled()) {
/* 228 */       log(40, MessageFormatter.format(format, arg));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void error(String format, Object argA, Object argB) {
/* 234 */     if (isErrorEnabled()) {
/* 235 */       log(40, MessageFormatter.format(format, argA, argB));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void error(String format, Object... argArray) {
/* 241 */     if (isErrorEnabled()) {
/* 242 */       log(40, MessageFormatter.arrayFormat(format, argArray));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void error(String msg, Throwable t) {
/* 248 */     if (isErrorEnabled())
/* 249 */       log(40, msg, t); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\logging\LocationAwareSlf4JLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */