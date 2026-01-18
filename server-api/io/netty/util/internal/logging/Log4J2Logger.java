/*     */ package io.netty.util.internal.logging;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.spi.ExtendedLogger;
/*     */ import org.apache.logging.log4j.spi.ExtendedLoggerWrapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Log4J2Logger
/*     */   extends ExtendedLoggerWrapper
/*     */   implements InternalLogger
/*     */ {
/*     */   private static final long serialVersionUID = 5485418394879791397L;
/*     */   
/*  38 */   private static final boolean VARARGS_ONLY = ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*     */       {
/*     */         public Boolean run() {
/*     */           try {
/*  42 */             Logger.class.getMethod("debug", new Class[] { String.class, Object.class });
/*  43 */             return Boolean.valueOf(false);
/*  44 */           } catch (NoSuchMethodException ignore) {
/*     */             
/*  46 */             return Boolean.valueOf(true);
/*  47 */           } catch (SecurityException ignore) {
/*     */             
/*  49 */             return Boolean.valueOf(false);
/*     */           } 
/*     */         }
/*     */       })).booleanValue();
/*     */ 
/*     */   
/*     */   Log4J2Logger(Logger logger) {
/*  56 */     super((ExtendedLogger)logger, logger.getName(), logger.getMessageFactory());
/*  57 */     if (VARARGS_ONLY) {
/*  58 */       throw new UnsupportedOperationException("Log4J2 version mismatch");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/*  64 */     return getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public void trace(Throwable t) {
/*  69 */     log(Level.TRACE, "Unexpected exception:", t);
/*     */   }
/*     */ 
/*     */   
/*     */   public void debug(Throwable t) {
/*  74 */     log(Level.DEBUG, "Unexpected exception:", t);
/*     */   }
/*     */ 
/*     */   
/*     */   public void info(Throwable t) {
/*  79 */     log(Level.INFO, "Unexpected exception:", t);
/*     */   }
/*     */ 
/*     */   
/*     */   public void warn(Throwable t) {
/*  84 */     log(Level.WARN, "Unexpected exception:", t);
/*     */   }
/*     */ 
/*     */   
/*     */   public void error(Throwable t) {
/*  89 */     log(Level.ERROR, "Unexpected exception:", t);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled(InternalLogLevel level) {
/*  94 */     return isEnabled(toLevel(level));
/*     */   }
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, String msg) {
/*  99 */     log(toLevel(level), msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, String format, Object arg) {
/* 104 */     log(toLevel(level), format, arg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, String format, Object argA, Object argB) {
/* 109 */     log(toLevel(level), format, argA, argB);
/*     */   }
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, String format, Object... arguments) {
/* 114 */     log(toLevel(level), format, arguments);
/*     */   }
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, String msg, Throwable t) {
/* 119 */     log(toLevel(level), msg, t);
/*     */   }
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, Throwable t) {
/* 124 */     log(toLevel(level), "Unexpected exception:", t);
/*     */   }
/*     */   
/*     */   private static Level toLevel(InternalLogLevel level) {
/* 128 */     switch (level) {
/*     */       case INFO:
/* 130 */         return Level.INFO;
/*     */       case DEBUG:
/* 132 */         return Level.DEBUG;
/*     */       case WARN:
/* 134 */         return Level.WARN;
/*     */       case ERROR:
/* 136 */         return Level.ERROR;
/*     */       case TRACE:
/* 138 */         return Level.TRACE;
/*     */     } 
/* 140 */     throw new Error("Unexpected log level: " + level);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\logging\Log4J2Logger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */