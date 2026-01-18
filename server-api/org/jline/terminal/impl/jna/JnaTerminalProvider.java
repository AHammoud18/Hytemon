/*     */ package org.jline.terminal.impl.jna;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.terminal.Size;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.terminal.impl.PosixPtyTerminal;
/*     */ import org.jline.terminal.impl.PosixSysTerminal;
/*     */ import org.jline.terminal.impl.jna.win.JnaWinSysTerminal;
/*     */ import org.jline.terminal.spi.Pty;
/*     */ import org.jline.terminal.spi.SystemStream;
/*     */ import org.jline.terminal.spi.TerminalProvider;
/*     */ import org.jline.utils.OSUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JnaTerminalProvider
/*     */   implements TerminalProvider
/*     */ {
/*     */   public JnaTerminalProvider() {
/*  31 */     checkSystemStream(SystemStream.Output);
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/*  36 */     return "jna";
/*     */   }
/*     */   
/*     */   public Pty current(SystemStream systemStream) throws IOException {
/*  40 */     return JnaNativePty.current(this, systemStream);
/*     */   }
/*     */   
/*     */   public Pty open(Attributes attributes, Size size) throws IOException {
/*  44 */     return JnaNativePty.open(this, attributes, size);
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
/*     */   public Terminal sysTerminal(String name, String type, boolean ansiPassThrough, Charset encoding, Charset inputEncoding, Charset outputEncoding, boolean nativeSignals, Terminal.SignalHandler signalHandler, boolean paused, SystemStream systemStream) throws IOException {
/*  60 */     if (OSUtils.IS_WINDOWS) {
/*  61 */       return winSysTerminal(name, type, ansiPassThrough, encoding, inputEncoding, outputEncoding, outputEncoding, nativeSignals, signalHandler, paused, systemStream);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     return posixSysTerminal(name, type, ansiPassThrough, encoding, inputEncoding, outputEncoding, outputEncoding, nativeSignals, signalHandler, paused, systemStream);
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
/*     */   @Deprecated
/*     */   public Terminal sysTerminal(String name, String type, boolean ansiPassThrough, Charset encoding, Charset stdinEncoding, Charset stdoutEncoding, Charset stderrEncoding, boolean nativeSignals, Terminal.SignalHandler signalHandler, boolean paused, SystemStream systemStream) throws IOException {
/* 105 */     if (OSUtils.IS_WINDOWS) {
/* 106 */       return winSysTerminal(name, type, ansiPassThrough, encoding, stdinEncoding, stdoutEncoding, stderrEncoding, nativeSignals, signalHandler, paused, systemStream);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     return posixSysTerminal(name, type, ansiPassThrough, encoding, stdinEncoding, stdoutEncoding, stderrEncoding, nativeSignals, signalHandler, paused, systemStream);
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
/*     */   public Terminal winSysTerminal(String name, String type, boolean ansiPassThrough, Charset encoding, boolean nativeSignals, Terminal.SignalHandler signalHandler, boolean paused, SystemStream systemStream) throws IOException {
/* 144 */     return winSysTerminal(name, type, ansiPassThrough, encoding, encoding, encoding, encoding, nativeSignals, signalHandler, paused, systemStream);
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
/*     */   public Terminal winSysTerminal(String name, String type, boolean ansiPassThrough, Charset encoding, Charset stdinEncoding, Charset stdoutEncoding, Charset stderrEncoding, boolean nativeSignals, Terminal.SignalHandler signalHandler, boolean paused, SystemStream systemStream) throws IOException {
/* 171 */     return (Terminal)JnaWinSysTerminal.createTerminal(this, systemStream, name, type, ansiPassThrough, encoding, stdinEncoding, stdoutEncoding, stderrEncoding, nativeSignals, signalHandler, paused);
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
/*     */   public Terminal posixSysTerminal(String name, String type, boolean ansiPassThrough, Charset encoding, boolean nativeSignals, Terminal.SignalHandler signalHandler, boolean paused, SystemStream systemStream) throws IOException {
/* 196 */     return posixSysTerminal(name, type, ansiPassThrough, encoding, encoding, encoding, encoding, nativeSignals, signalHandler, paused, systemStream);
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
/*     */   public Terminal posixSysTerminal(String name, String type, boolean ansiPassThrough, Charset encoding, Charset stdinEncoding, Charset stdoutEncoding, Charset stderrEncoding, boolean nativeSignals, Terminal.SignalHandler signalHandler, boolean paused, SystemStream systemStream) throws IOException {
/* 223 */     Pty pty = current(systemStream);
/*     */     
/* 225 */     Charset outputEncoding = (systemStream == SystemStream.Error) ? stderrEncoding : stdoutEncoding;
/* 226 */     return (Terminal)new PosixSysTerminal(name, type, pty, encoding, stdinEncoding, outputEncoding, nativeSignals, signalHandler);
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
/*     */   public Terminal newTerminal(String name, String type, InputStream in, OutputStream out, Charset encoding, Charset inputEncoding, Charset outputEncoding, Terminal.SignalHandler signalHandler, boolean paused, Attributes attributes, Size size) throws IOException {
/* 244 */     Pty pty = open(attributes, size);
/* 245 */     return (Terminal)new PosixPtyTerminal(name, type, pty, in, out, encoding, inputEncoding, outputEncoding, signalHandler, paused);
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
/*     */   @Deprecated
/*     */   public Terminal newTerminal(String name, String type, InputStream in, OutputStream out, Charset encoding, Charset stdinEncoding, Charset stdoutEncoding, Charset stderrEncoding, Terminal.SignalHandler signalHandler, boolean paused, Attributes attributes, Size size) throws IOException {
/* 266 */     Pty pty = open(attributes, size);
/* 267 */     return (Terminal)new PosixPtyTerminal(name, type, pty, in, out, encoding, stdinEncoding, stdoutEncoding, signalHandler, paused);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSystemStream(SystemStream stream) {
/*     */     try {
/* 274 */       return checkSystemStream(stream);
/* 275 */     } catch (Throwable t) {
/* 276 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean checkSystemStream(SystemStream stream) {
/* 281 */     if (OSUtils.IS_WINDOWS) {
/* 282 */       return JnaWinSysTerminal.isWindowsSystemStream(stream);
/*     */     }
/* 284 */     return JnaNativePty.isPosixSystemStream(stream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String systemStreamName(SystemStream stream) {
/* 290 */     if (OSUtils.IS_WINDOWS) {
/* 291 */       return null;
/*     */     }
/* 293 */     return JnaNativePty.posixSystemStreamName(stream);
/*     */   }
/*     */ 
/*     */   
/*     */   public int systemStreamWidth(SystemStream stream) {
/*     */     
/* 299 */     try { Pty pty = current(stream); 
/* 300 */       try { int i = pty.getSize().getColumns();
/* 301 */         if (pty != null) pty.close();  return i; } catch (Throwable throwable) { if (pty != null) try { pty.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable t)
/* 302 */     { return -1; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 308 */     return "TerminalProvider[" + name() + "]";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jna\JnaTerminalProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */