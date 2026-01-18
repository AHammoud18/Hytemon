/*     */ package org.jline.terminal.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Objects;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.terminal.spi.Pty;
/*     */ import org.jline.utils.ClosedException;
/*     */ import org.jline.utils.NonBlocking;
/*     */ import org.jline.utils.NonBlockingInputStream;
/*     */ import org.jline.utils.NonBlockingReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PosixPtyTerminal
/*     */   extends AbstractPosixTerminal
/*     */ {
/*     */   private final InputStream in;
/*     */   private final OutputStream out;
/*     */   private final InputStream masterInput;
/*     */   private final OutputStream masterOutput;
/*     */   private final NonBlockingInputStream input;
/*     */   private final OutputStream output;
/*     */   private final NonBlockingReader reader;
/*     */   private final PrintWriter writer;
/*  66 */   private final Object lock = new Object();
/*     */   
/*     */   private Thread inputPumpThread;
/*     */   private Thread outputPumpThread;
/*     */   private boolean paused = true;
/*     */   
/*     */   public PosixPtyTerminal(String name, String type, Pty pty, InputStream in, OutputStream out, Charset encoding) throws IOException {
/*  73 */     this(name, type, pty, in, out, encoding, Terminal.SignalHandler.SIG_DFL);
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
/*     */   public PosixPtyTerminal(String name, String type, Pty pty, InputStream in, OutputStream out, Charset encoding, Terminal.SignalHandler signalHandler) throws IOException {
/*  85 */     this(name, type, pty, in, out, encoding, signalHandler, false);
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
/*     */   public PosixPtyTerminal(String name, String type, Pty pty, InputStream in, OutputStream out, Charset encoding, Terminal.SignalHandler signalHandler, boolean paused) throws IOException {
/*  99 */     this(name, type, pty, in, out, encoding, encoding, encoding, signalHandler, paused);
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
/*     */   public PosixPtyTerminal(String name, String type, Pty pty, InputStream in, OutputStream out, Charset encoding, Charset inputEncoding, Charset outputEncoding, Terminal.SignalHandler signalHandler, boolean paused) throws IOException {
/* 115 */     super(name, type, pty, encoding, inputEncoding, outputEncoding, signalHandler);
/* 116 */     this.in = Objects.<InputStream>requireNonNull(in);
/* 117 */     this.out = Objects.<OutputStream>requireNonNull(out);
/* 118 */     this.masterInput = pty.getMasterInput();
/* 119 */     this.masterOutput = pty.getMasterOutput();
/* 120 */     this.input = new InputStreamWrapper(NonBlocking.nonBlocking(name, pty.getSlaveInput()));
/* 121 */     this.output = pty.getSlaveOutput();
/* 122 */     this.reader = NonBlocking.nonBlocking(name, (InputStream)this.input, inputEncoding());
/* 123 */     this.writer = new PrintWriter(new OutputStreamWriter(this.output, outputEncoding()));
/* 124 */     parseInfoCmp();
/* 125 */     if (!paused) {
/* 126 */       resume();
/*     */     }
/*     */   }
/*     */   
/*     */   public InputStream input() {
/* 131 */     return (InputStream)this.input;
/*     */   }
/*     */   
/*     */   public NonBlockingReader reader() {
/* 135 */     return this.reader;
/*     */   }
/*     */   
/*     */   public OutputStream output() {
/* 139 */     return this.output;
/*     */   }
/*     */   
/*     */   public PrintWriter writer() {
/* 143 */     return this.writer;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws IOException {
/* 148 */     super.doClose();
/* 149 */     this.reader.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPauseResume() {
/* 154 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void pause() {
/*     */     try {
/* 160 */       pause(false);
/* 161 */     } catch (InterruptedException interruptedException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void pause(boolean wait) throws InterruptedException {
/*     */     Thread p1;
/*     */     Thread p2;
/* 169 */     synchronized (this.lock) {
/* 170 */       this.paused = true;
/* 171 */       p1 = this.inputPumpThread;
/* 172 */       p2 = this.outputPumpThread;
/*     */     } 
/* 174 */     if (p1 != null) {
/* 175 */       p1.interrupt();
/*     */     }
/* 177 */     if (p2 != null) {
/* 178 */       p2.interrupt();
/*     */     }
/* 180 */     if (wait) {
/* 181 */       if (p1 != null) {
/* 182 */         p1.join();
/*     */       }
/* 184 */       if (p2 != null) {
/* 185 */         p2.join();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void resume() {
/* 192 */     synchronized (this.lock) {
/* 193 */       this.paused = false;
/* 194 */       if (this.inputPumpThread == null) {
/* 195 */         this.inputPumpThread = new Thread(this::pumpIn, toString() + " input pump thread");
/* 196 */         this.inputPumpThread.setDaemon(true);
/* 197 */         this.inputPumpThread.start();
/*     */       } 
/* 199 */       if (this.outputPumpThread == null) {
/* 200 */         this.outputPumpThread = new Thread(this::pumpOut, toString() + " output pump thread");
/* 201 */         this.outputPumpThread.setDaemon(true);
/* 202 */         this.outputPumpThread.start();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean paused() {
/* 209 */     synchronized (this.lock) {
/* 210 */       return this.paused;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class InputStreamWrapper
/*     */     extends NonBlockingInputStream {
/*     */     private final NonBlockingInputStream in;
/*     */     private volatile boolean closed;
/*     */     
/*     */     protected InputStreamWrapper(NonBlockingInputStream in) {
/* 220 */       this.in = in;
/*     */     }
/*     */ 
/*     */     
/*     */     public int read(long timeout, boolean isPeek) throws IOException {
/* 225 */       if (this.closed) {
/* 226 */         throw new ClosedException();
/*     */       }
/* 228 */       return this.in.read(timeout, isPeek);
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 233 */       this.closed = true;
/*     */     }
/*     */   }
/*     */   
/*     */   private void pumpIn() {
/*     */     try {
/*     */       while (true) {
/* 240 */         synchronized (this.lock) {
/* 241 */           if (this.paused) {
/* 242 */             this.inputPumpThread = null;
/*     */             return;
/*     */           } 
/*     */         } 
/* 246 */         int b = this.in.read();
/* 247 */         if (b < 0) {
/* 248 */           this.input.close();
/*     */           break;
/*     */         } 
/* 251 */         this.masterOutput.write(b);
/* 252 */         this.masterOutput.flush();
/*     */       } 
/* 254 */     } catch (IOException e) {
/* 255 */       e.printStackTrace();
/*     */     } finally {
/* 257 */       synchronized (this.lock) {
/* 258 */         this.inputPumpThread = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void pumpOut() {
/*     */     try {
/*     */       while (true) {
/* 266 */         synchronized (this.lock) {
/* 267 */           if (this.paused) {
/* 268 */             this.outputPumpThread = null;
/*     */             return;
/*     */           } 
/*     */         } 
/* 272 */         int b = this.masterInput.read();
/* 273 */         if (b < 0) {
/* 274 */           this.input.close();
/*     */           break;
/*     */         } 
/* 277 */         this.out.write(b);
/* 278 */         this.out.flush();
/*     */       } 
/* 280 */     } catch (IOException e) {
/* 281 */       e.printStackTrace();
/*     */     } finally {
/* 283 */       synchronized (this.lock) {
/* 284 */         this.outputPumpThread = null;
/*     */       } 
/*     */     } 
/*     */     try {
/* 288 */       close();
/* 289 */     } catch (Throwable throwable) {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\PosixPtyTerminal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */