/*     */ package org.jline.terminal.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.function.IntConsumer;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.terminal.Cursor;
/*     */ import org.jline.terminal.Size;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.terminal.spi.TerminalProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExternalTerminal
/*     */   extends LineDisciplineTerminal
/*     */ {
/*     */   private final TerminalProvider provider;
/*  63 */   protected final AtomicBoolean closed = new AtomicBoolean();
/*     */   protected final InputStream masterInput;
/*  65 */   protected final Object lock = new Object();
/*     */   
/*     */   protected boolean paused = true;
/*     */   
/*     */   protected Thread pumpThread;
/*     */   
/*     */   public ExternalTerminal(String name, String type, InputStream masterInput, OutputStream masterOutput, Charset encoding) throws IOException {
/*  72 */     this((TerminalProvider)null, name, type, masterInput, masterOutput, encoding, encoding, encoding, encoding, Terminal.SignalHandler.SIG_DFL);
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
/*     */   public ExternalTerminal(TerminalProvider provider, String name, String type, InputStream masterInput, OutputStream masterOutput, Charset encoding, Terminal.SignalHandler signalHandler) throws IOException {
/*  94 */     this(provider, name, type, masterInput, masterOutput, encoding, encoding, encoding, signalHandler, false);
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
/*     */   public ExternalTerminal(TerminalProvider provider, String name, String type, InputStream masterInput, OutputStream masterOutput, Charset encoding, Charset stdinEncoding, Charset stdoutEncoding, Charset stderrEncoding, Terminal.SignalHandler signalHandler) throws IOException {
/* 109 */     this(provider, name, type, masterInput, masterOutput, encoding, stdinEncoding, stdoutEncoding, signalHandler, false);
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
/*     */   public ExternalTerminal(TerminalProvider provider, String name, String type, InputStream masterInput, OutputStream masterOutput, Charset encoding, Terminal.SignalHandler signalHandler, boolean paused) throws IOException {
/* 132 */     this(provider, name, type, masterInput, masterOutput, encoding, encoding, encoding, signalHandler, paused, (Attributes)null, (Size)null);
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
/*     */   public ExternalTerminal(TerminalProvider provider, String name, String type, InputStream masterInput, OutputStream masterOutput, Charset encoding, Charset inputEncoding, Charset outputEncoding, Terminal.SignalHandler signalHandler, boolean paused) throws IOException {
/* 159 */     this(provider, name, type, masterInput, masterOutput, encoding, inputEncoding, outputEncoding, signalHandler, paused, (Attributes)null, (Size)null);
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
/*     */   public ExternalTerminal(TerminalProvider provider, String name, String type, InputStream masterInput, OutputStream masterOutput, Charset encoding, Terminal.SignalHandler signalHandler, boolean paused, Attributes attributes, Size size) throws IOException {
/* 187 */     this(provider, name, type, masterInput, masterOutput, encoding, encoding, encoding, signalHandler, paused, attributes, size);
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
/*     */   
/*     */   public ExternalTerminal(TerminalProvider provider, String name, String type, InputStream masterInput, OutputStream masterOutput, Charset encoding, Charset inputEncoding, Charset outputEncoding, Terminal.SignalHandler signalHandler, boolean paused, Attributes attributes, Size size) throws IOException {
/* 217 */     super(name, type, masterOutput, encoding, inputEncoding, outputEncoding, signalHandler);
/* 218 */     this.provider = provider;
/* 219 */     this.masterInput = masterInput;
/* 220 */     if (attributes != null) {
/* 221 */       setAttributes(attributes);
/*     */     }
/* 223 */     if (size != null) {
/* 224 */       setSize(size);
/*     */     }
/* 226 */     if (!paused) {
/* 227 */       resume();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doClose() throws IOException {
/* 232 */     if (this.closed.compareAndSet(false, true)) {
/* 233 */       pause();
/* 234 */       super.doClose();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPauseResume() {
/* 240 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void pause() {
/*     */     try {
/* 246 */       pause(false);
/* 247 */     } catch (InterruptedException interruptedException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pause(boolean wait) throws InterruptedException {
/*     */     Thread p;
/* 255 */     synchronized (this.lock) {
/* 256 */       this.paused = true;
/* 257 */       p = this.pumpThread;
/*     */     } 
/* 259 */     if (p != null) {
/* 260 */       p.interrupt();
/* 261 */       if (wait) {
/* 262 */         p.join();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void resume() {
/* 269 */     synchronized (this.lock) {
/* 270 */       this.paused = false;
/* 271 */       if (this.pumpThread == null) {
/* 272 */         this.pumpThread = new Thread(this::pump, toString() + " input pump thread");
/* 273 */         this.pumpThread.setDaemon(true);
/* 274 */         this.pumpThread.start();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean paused() {
/* 281 */     synchronized (this.lock) {
/* 282 */       return this.paused;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void pump() {
/*     */     try {
/* 288 */       byte[] buf = new byte[1024];
/*     */       while (true) {
/* 290 */         int c = this.masterInput.read(buf);
/* 291 */         if (c >= 0) {
/* 292 */           processInputBytes(buf, 0, c);
/*     */         }
/* 294 */         if (c < 0 || this.closed.get()) {
/*     */           break;
/*     */         }
/* 297 */         synchronized (this.lock) {
/* 298 */           if (this.paused) {
/* 299 */             this.pumpThread = null;
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/* 304 */     } catch (IOException e) {
/* 305 */       processIOException(e);
/*     */     } finally {
/* 307 */       synchronized (this.lock) {
/* 308 */         this.pumpThread = null;
/*     */       } 
/*     */     } 
/*     */     try {
/* 312 */       this.slaveInput.close();
/* 313 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cursor getCursorPosition(IntConsumer discarded) {
/* 320 */     return CursorSupport.getCursorPosition((Terminal)this, discarded);
/*     */   }
/*     */ 
/*     */   
/*     */   public TerminalProvider getProvider() {
/* 325 */     return this.provider;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\ExternalTerminal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */