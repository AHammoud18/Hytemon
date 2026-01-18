/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InterruptedIOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NonBlockingInputStreamImpl
/*     */   extends NonBlockingInputStream
/*     */ {
/*     */   private InputStream in;
/*  31 */   private int b = -2;
/*     */   
/*     */   private String name;
/*     */   private boolean threadIsReading = false;
/*  35 */   private IOException exception = null;
/*  36 */   private long threadDelay = 60000L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Thread thread;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NonBlockingInputStreamImpl(String name, InputStream in) {
/*  48 */     this.in = in;
/*  49 */     this.name = name;
/*     */   }
/*     */   
/*     */   private synchronized void startReadingThreadIfNeeded() {
/*  53 */     if (this.thread == null) {
/*  54 */       this.thread = new Thread(this::run);
/*  55 */       this.thread.setName(this.name + " non blocking reader thread");
/*  56 */       this.thread.setDaemon(true);
/*  57 */       this.thread.start();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void shutdown() {
/*  67 */     if (this.thread != null) {
/*  68 */       notify();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  78 */     this.in.close();
/*  79 */     shutdown();
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
/*     */   public synchronized int read(long timeout, boolean isPeek) throws IOException {
/*  95 */     if (this.exception != null) {
/*  96 */       assert this.b == -2;
/*  97 */       IOException toBeThrown = this.exception;
/*  98 */       if (!isPeek) this.exception = null; 
/*  99 */       throw toBeThrown;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     if (this.b >= -1) {
/* 108 */       assert this.exception == null;
/* 109 */     } else if (!isPeek && timeout <= 0L && !this.threadIsReading) {
/* 110 */       this.b = this.in.read();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 115 */       if (!this.threadIsReading) {
/* 116 */         this.threadIsReading = true;
/* 117 */         startReadingThreadIfNeeded();
/* 118 */         notifyAll();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 125 */       Timeout t = new Timeout(timeout);
/* 126 */       while (!t.elapsed()) {
/*     */         try {
/* 128 */           if (Thread.interrupted()) {
/* 129 */             throw new InterruptedException();
/*     */           }
/* 131 */           wait(t.timeout());
/* 132 */         } catch (InterruptedException e) {
/* 133 */           this.exception = (IOException)(new InterruptedIOException()).initCause(e);
/*     */         } 
/*     */         
/* 136 */         if (this.exception != null) {
/* 137 */           assert this.b == -2;
/*     */           
/* 139 */           IOException toBeThrown = this.exception;
/* 140 */           if (!isPeek) this.exception = null; 
/* 141 */           throw toBeThrown;
/*     */         } 
/*     */         
/* 144 */         if (this.b >= -1) {
/* 145 */           assert this.exception == null;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 157 */     int ret = this.b;
/* 158 */     if (!isPeek) {
/* 159 */       this.b = -2;
/*     */     }
/* 161 */     return ret;
/*     */   }
/*     */   
/*     */   private void run() {
/* 165 */     Log.debug(new Object[] { "NonBlockingInputStream start" });
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*     */       int byteRead;
/*     */ 
/*     */ 
/*     */       
/*     */       do {
/* 175 */         synchronized (this) {
/* 176 */           boolean needToRead = this.threadIsReading;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 182 */             if (!needToRead) {
/* 183 */               wait(this.threadDelay);
/*     */             }
/* 185 */           } catch (InterruptedException interruptedException) {}
/*     */ 
/*     */ 
/*     */           
/* 189 */           needToRead = this.threadIsReading;
/* 190 */           if (!needToRead) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 199 */         byteRead = -2;
/* 200 */         IOException failure = null;
/*     */         try {
/* 202 */           byteRead = this.in.read();
/* 203 */         } catch (IOException e) {
/* 204 */           failure = e;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 210 */         synchronized (this) {
/* 211 */           this.exception = failure;
/* 212 */           this.b = byteRead;
/* 213 */           this.threadIsReading = false;
/* 214 */           notify();
/*     */         }
/*     */       
/*     */       }
/* 218 */       while (byteRead >= 0);
/*     */ 
/*     */       
/*     */       return;
/* 222 */     } catch (Throwable t) {
/* 223 */       Log.warn(new Object[] { "Error in NonBlockingInputStream thread", t });
/*     */     } finally {
/* 225 */       Log.debug(new Object[] { "NonBlockingInputStream shutdown" });
/* 226 */       synchronized (this) {
/* 227 */         this.thread = null;
/* 228 */         this.threadIsReading = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\NonBlockingInputStreamImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */