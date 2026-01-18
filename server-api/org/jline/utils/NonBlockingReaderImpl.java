/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.io.Reader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NonBlockingReaderImpl
/*     */   extends NonBlockingReader
/*     */ {
/*     */   public static final int READ_EXPIRED = -2;
/*     */   private Reader in;
/*  34 */   private int ch = -2;
/*     */   
/*     */   private String name;
/*     */   private boolean threadIsReading = false;
/*  38 */   private IOException exception = null;
/*  39 */   private long threadDelay = 60000L;
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
/*     */   public NonBlockingReaderImpl(String name, Reader in) {
/*  51 */     this.in = in;
/*  52 */     this.name = name;
/*     */   }
/*     */   
/*     */   private synchronized void startReadingThreadIfNeeded() {
/*  56 */     if (this.thread == null) {
/*  57 */       this.thread = new Thread(this::run);
/*  58 */       this.thread.setName(this.name + " non blocking reader thread");
/*  59 */       this.thread.setDaemon(true);
/*  60 */       this.thread.start();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void shutdown() {
/*  70 */     if (this.thread != null) {
/*  71 */       notify();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  81 */     this.in.close();
/*  82 */     shutdown();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean ready() throws IOException {
/*  87 */     return (this.ch >= 0 || this.in.ready());
/*     */   }
/*     */ 
/*     */   
/*     */   public int readBuffered(char[] b, int off, int len, long timeout) throws IOException {
/*  92 */     if (b == null)
/*  93 */       throw new NullPointerException(); 
/*  94 */     if (off < 0 || len < 0 || off + len < b.length)
/*  95 */       throw new IllegalArgumentException(); 
/*  96 */     if (len == 0)
/*  97 */       return 0; 
/*  98 */     if (this.exception != null) {
/*  99 */       assert this.ch == -2;
/* 100 */       IOException toBeThrown = this.exception;
/* 101 */       this.exception = null;
/* 102 */       throw toBeThrown;
/* 103 */     }  if (this.ch >= -1) {
/* 104 */       b[0] = (char)this.ch;
/* 105 */       this.ch = -2;
/* 106 */       return 1;
/* 107 */     }  if (!this.threadIsReading && timeout <= 0L) {
/* 108 */       return this.in.read(b, off, len);
/*     */     }
/*     */     
/* 111 */     int c = read(timeout, false);
/* 112 */     if (c >= 0) {
/* 113 */       b[off] = (char)c;
/* 114 */       return 1;
/*     */     } 
/* 116 */     return c;
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
/*     */   protected synchronized int read(long timeout, boolean isPeek) throws IOException {
/* 132 */     if (this.exception != null) {
/* 133 */       assert this.ch == -2;
/* 134 */       IOException toBeThrown = this.exception;
/* 135 */       if (!isPeek) this.exception = null; 
/* 136 */       throw toBeThrown;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     if (this.ch >= -1) {
/* 145 */       assert this.exception == null;
/* 146 */     } else if (!isPeek && timeout <= 0L && !this.threadIsReading) {
/* 147 */       this.ch = this.in.read();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 152 */       if (!this.threadIsReading) {
/* 153 */         this.threadIsReading = true;
/* 154 */         startReadingThreadIfNeeded();
/* 155 */         notifyAll();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 162 */       Timeout t = new Timeout(timeout);
/* 163 */       while (!t.elapsed()) {
/*     */         try {
/* 165 */           if (Thread.interrupted()) {
/* 166 */             throw new InterruptedException();
/*     */           }
/* 168 */           wait(t.timeout());
/* 169 */         } catch (InterruptedException e) {
/* 170 */           this.exception = (IOException)(new InterruptedIOException()).initCause(e);
/*     */         } 
/*     */         
/* 173 */         if (this.exception != null) {
/* 174 */           assert this.ch == -2;
/*     */           
/* 176 */           IOException toBeThrown = this.exception;
/* 177 */           if (!isPeek) this.exception = null; 
/* 178 */           throw toBeThrown;
/*     */         } 
/*     */         
/* 181 */         if (this.ch >= -1) {
/* 182 */           assert this.exception == null;
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
/* 194 */     int ret = this.ch;
/* 195 */     if (!isPeek) {
/* 196 */       this.ch = -2;
/*     */     }
/* 198 */     return ret;
/*     */   }
/*     */   
/*     */   private void run() {
/* 202 */     Log.debug(new Object[] { "NonBlockingReader start" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*     */       while (true) {
/* 212 */         synchronized (this) {
/* 213 */           boolean needToRead = this.threadIsReading;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 219 */             if (!needToRead) {
/* 220 */               wait(this.threadDelay);
/*     */             }
/* 222 */           } catch (InterruptedException interruptedException) {}
/*     */ 
/*     */ 
/*     */           
/* 226 */           needToRead = this.threadIsReading;
/* 227 */           if (!needToRead) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 236 */         int charRead = -2;
/* 237 */         IOException failure = null;
/*     */         try {
/* 239 */           charRead = this.in.read();
/*     */ 
/*     */         
/*     */         }
/* 243 */         catch (IOException e) {
/* 244 */           failure = e;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 251 */         synchronized (this) {
/* 252 */           this.exception = failure;
/* 253 */           this.ch = charRead;
/* 254 */           this.threadIsReading = false;
/* 255 */           notify();
/*     */         } 
/*     */       } 
/* 258 */     } catch (Throwable t) {
/* 259 */       Log.warn(new Object[] { "Error in NonBlockingReader thread", t });
/*     */     } finally {
/* 261 */       Log.debug(new Object[] { "NonBlockingReader shutdown" });
/* 262 */       synchronized (this) {
/* 263 */         this.thread = null;
/* 264 */         this.threadIsReading = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized void clear() throws IOException {
/* 270 */     while (ready())
/* 271 */       read(); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\NonBlockingReaderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */