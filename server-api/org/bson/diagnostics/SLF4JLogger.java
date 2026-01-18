/*     */ package org.bson.diagnostics;
/*     */ 
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SLF4JLogger
/*     */   implements Logger
/*     */ {
/*     */   private final Logger delegate;
/*     */   
/*     */   SLF4JLogger(String name) {
/*  26 */     this.delegate = LoggerFactory.getLogger(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  31 */     return this.delegate.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTraceEnabled() {
/*  36 */     return this.delegate.isTraceEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public void trace(String msg) {
/*  41 */     this.delegate.trace(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void trace(String msg, Throwable t) {
/*  46 */     this.delegate.trace(msg, t);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDebugEnabled() {
/*  51 */     return this.delegate.isDebugEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public void debug(String msg) {
/*  56 */     this.delegate.debug(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void debug(String msg, Throwable t) {
/*  61 */     this.delegate.debug(msg, t);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInfoEnabled() {
/*  66 */     return this.delegate.isInfoEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public void info(String msg) {
/*  71 */     this.delegate.info(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void info(String msg, Throwable t) {
/*  76 */     this.delegate.info(msg, t);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWarnEnabled() {
/*  81 */     return this.delegate.isWarnEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public void warn(String msg) {
/*  86 */     this.delegate.warn(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void warn(String msg, Throwable t) {
/*  91 */     this.delegate.warn(msg, t);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isErrorEnabled() {
/*  96 */     return this.delegate.isErrorEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public void error(String msg) {
/* 101 */     this.delegate.error(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void error(String msg, Throwable t) {
/* 106 */     this.delegate.error(msg, t);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\diagnostics\SLF4JLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */