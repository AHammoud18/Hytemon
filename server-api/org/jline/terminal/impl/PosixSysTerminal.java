/*     */ package org.jline.terminal.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.terminal.spi.Pty;
/*     */ import org.jline.utils.FastBufferedOutputStream;
/*     */ import org.jline.utils.NonBlocking;
/*     */ import org.jline.utils.NonBlockingInputStream;
/*     */ import org.jline.utils.NonBlockingReader;
/*     */ import org.jline.utils.ShutdownHooks;
/*     */ import org.jline.utils.Signals;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PosixSysTerminal
/*     */   extends AbstractPosixTerminal
/*     */ {
/*     */   protected final NonBlockingInputStream input;
/*     */   protected final OutputStream output;
/*     */   protected final NonBlockingReader reader;
/*     */   protected final PrintWriter writer;
/*  65 */   protected final Map<Terminal.Signal, Object> nativeHandlers = new HashMap<>();
/*     */ 
/*     */   
/*     */   protected final ShutdownHooks.Task closer;
/*     */ 
/*     */   
/*     */   public PosixSysTerminal(String name, String type, Pty pty, Charset encoding, boolean nativeSignals, Terminal.SignalHandler signalHandler) throws IOException {
/*  72 */     this(name, type, pty, encoding, encoding, encoding, nativeSignals, signalHandler);
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
/*     */   public PosixSysTerminal(String name, String type, Pty pty, Charset encoding, Charset inputEncoding, Charset outputEncoding, boolean nativeSignals, Terminal.SignalHandler signalHandler) throws IOException {
/*  86 */     super(name, type, pty, encoding, inputEncoding, outputEncoding, signalHandler);
/*  87 */     this.input = NonBlocking.nonBlocking(getName(), pty.getSlaveInput());
/*  88 */     this.output = (OutputStream)new FastBufferedOutputStream(pty.getSlaveOutput());
/*  89 */     this.reader = NonBlocking.nonBlocking(getName(), (InputStream)this.input, inputEncoding());
/*  90 */     this.writer = new PrintWriter(new OutputStreamWriter(this.output, outputEncoding()));
/*  91 */     parseInfoCmp();
/*  92 */     if (nativeSignals) {
/*  93 */       for (Terminal.Signal signal : Terminal.Signal.values()) {
/*  94 */         if (signalHandler == Terminal.SignalHandler.SIG_DFL) {
/*  95 */           this.nativeHandlers.put(signal, Signals.registerDefault(signal.name()));
/*     */         } else {
/*  97 */           this.nativeHandlers.put(signal, Signals.register(signal.name(), () -> raise(signal)));
/*     */         } 
/*     */       } 
/*     */     }
/* 101 */     this.closer = this::close;
/* 102 */     ShutdownHooks.add(this.closer);
/*     */   }
/*     */ 
/*     */   
/*     */   public Terminal.SignalHandler handle(Terminal.Signal signal, Terminal.SignalHandler handler) {
/* 107 */     Terminal.SignalHandler prev = super.handle(signal, handler);
/* 108 */     if (prev != handler) {
/* 109 */       if (handler == Terminal.SignalHandler.SIG_DFL) {
/* 110 */         Signals.registerDefault(signal.name());
/*     */       } else {
/* 112 */         Signals.register(signal.name(), () -> raise(signal));
/*     */       } 
/*     */     }
/* 115 */     return prev;
/*     */   }
/*     */   
/*     */   public NonBlockingReader reader() {
/* 119 */     return this.reader;
/*     */   }
/*     */   
/*     */   public PrintWriter writer() {
/* 123 */     return this.writer;
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream input() {
/* 128 */     return (InputStream)this.input;
/*     */   }
/*     */ 
/*     */   
/*     */   public OutputStream output() {
/* 133 */     return this.output;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws IOException {
/* 138 */     this.writer.flush();
/* 139 */     ShutdownHooks.remove(this.closer);
/* 140 */     for (Map.Entry<Terminal.Signal, Object> entry : this.nativeHandlers.entrySet()) {
/* 141 */       Signals.unregister(((Terminal.Signal)entry.getKey()).name(), entry.getValue());
/*     */     }
/* 143 */     super.doClose();
/*     */     
/* 145 */     this.reader.shutdown();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\PosixSysTerminal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */