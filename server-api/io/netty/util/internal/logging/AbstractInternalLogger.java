/*     */ package io.netty.util.internal.logging;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractInternalLogger
/*     */   implements InternalLogger, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6382972526573193470L;
/*     */   static final String EXCEPTION_MESSAGE = "Unexpected exception:";
/*     */   private final String name;
/*     */   
/*     */   protected AbstractInternalLogger(String name) {
/*  41 */     this.name = (String)ObjectUtil.checkNotNull(name, "name");
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/*  46 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled(InternalLogLevel level) {
/*  51 */     switch (level) {
/*     */       case TRACE:
/*  53 */         return isTraceEnabled();
/*     */       case DEBUG:
/*  55 */         return isDebugEnabled();
/*     */       case INFO:
/*  57 */         return isInfoEnabled();
/*     */       case WARN:
/*  59 */         return isWarnEnabled();
/*     */       case ERROR:
/*  61 */         return isErrorEnabled();
/*     */     } 
/*  63 */     throw new Error("Unexpected log level: " + level);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void trace(Throwable t) {
/*  69 */     trace("Unexpected exception:", t);
/*     */   }
/*     */ 
/*     */   
/*     */   public void debug(Throwable t) {
/*  74 */     debug("Unexpected exception:", t);
/*     */   }
/*     */ 
/*     */   
/*     */   public void info(Throwable t) {
/*  79 */     info("Unexpected exception:", t);
/*     */   }
/*     */ 
/*     */   
/*     */   public void warn(Throwable t) {
/*  84 */     warn("Unexpected exception:", t);
/*     */   }
/*     */ 
/*     */   
/*     */   public void error(Throwable t) {
/*  89 */     error("Unexpected exception:", t);
/*     */   }
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, String msg, Throwable cause) {
/*  94 */     switch (level) {
/*     */       case TRACE:
/*  96 */         trace(msg, cause);
/*     */         return;
/*     */       case DEBUG:
/*  99 */         debug(msg, cause);
/*     */         return;
/*     */       case INFO:
/* 102 */         info(msg, cause);
/*     */         return;
/*     */       case WARN:
/* 105 */         warn(msg, cause);
/*     */         return;
/*     */       case ERROR:
/* 108 */         error(msg, cause);
/*     */         return;
/*     */     } 
/* 111 */     throw new Error("Unexpected log level: " + level);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, Throwable cause) {
/* 117 */     switch (level) {
/*     */       case TRACE:
/* 119 */         trace(cause);
/*     */         return;
/*     */       case DEBUG:
/* 122 */         debug(cause);
/*     */         return;
/*     */       case INFO:
/* 125 */         info(cause);
/*     */         return;
/*     */       case WARN:
/* 128 */         warn(cause);
/*     */         return;
/*     */       case ERROR:
/* 131 */         error(cause);
/*     */         return;
/*     */     } 
/* 134 */     throw new Error("Unexpected log level: " + level);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, String msg) {
/* 140 */     switch (level) {
/*     */       case TRACE:
/* 142 */         trace(msg);
/*     */         return;
/*     */       case DEBUG:
/* 145 */         debug(msg);
/*     */         return;
/*     */       case INFO:
/* 148 */         info(msg);
/*     */         return;
/*     */       case WARN:
/* 151 */         warn(msg);
/*     */         return;
/*     */       case ERROR:
/* 154 */         error(msg);
/*     */         return;
/*     */     } 
/* 157 */     throw new Error("Unexpected log level: " + level);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, String format, Object arg) {
/* 163 */     switch (level) {
/*     */       case TRACE:
/* 165 */         trace(format, arg);
/*     */         return;
/*     */       case DEBUG:
/* 168 */         debug(format, arg);
/*     */         return;
/*     */       case INFO:
/* 171 */         info(format, arg);
/*     */         return;
/*     */       case WARN:
/* 174 */         warn(format, arg);
/*     */         return;
/*     */       case ERROR:
/* 177 */         error(format, arg);
/*     */         return;
/*     */     } 
/* 180 */     throw new Error("Unexpected log level: " + level);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, String format, Object argA, Object argB) {
/* 186 */     switch (level) {
/*     */       case TRACE:
/* 188 */         trace(format, argA, argB);
/*     */         return;
/*     */       case DEBUG:
/* 191 */         debug(format, argA, argB);
/*     */         return;
/*     */       case INFO:
/* 194 */         info(format, argA, argB);
/*     */         return;
/*     */       case WARN:
/* 197 */         warn(format, argA, argB);
/*     */         return;
/*     */       case ERROR:
/* 200 */         error(format, argA, argB);
/*     */         return;
/*     */     } 
/* 203 */     throw new Error("Unexpected log level: " + level);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, String format, Object... arguments) {
/* 209 */     switch (level) {
/*     */       case TRACE:
/* 211 */         trace(format, arguments);
/*     */         return;
/*     */       case DEBUG:
/* 214 */         debug(format, arguments);
/*     */         return;
/*     */       case INFO:
/* 217 */         info(format, arguments);
/*     */         return;
/*     */       case WARN:
/* 220 */         warn(format, arguments);
/*     */         return;
/*     */       case ERROR:
/* 223 */         error(format, arguments);
/*     */         return;
/*     */     } 
/* 226 */     throw new Error("Unexpected log level: " + level);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object readResolve() throws ObjectStreamException {
/* 231 */     return InternalLoggerFactory.getInstance(name());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 236 */     return StringUtil.simpleClassName(this) + '(' + name() + ')';
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\logging\AbstractInternalLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */