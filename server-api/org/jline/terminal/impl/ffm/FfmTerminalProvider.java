/*     */ package org.jline.terminal.impl.ffm;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.foreign.MemoryLayout;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.VarHandle;
/*     */ import java.nio.charset.Charset;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.terminal.Size;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.terminal.impl.PosixPtyTerminal;
/*     */ import org.jline.terminal.impl.PosixSysTerminal;
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
/*     */ 
/*     */ public class FfmTerminalProvider
/*     */   implements TerminalProvider
/*     */ {
/*     */   public FfmTerminalProvider() {
/*  35 */     if (!FfmTerminalProvider.class.getModule().isNativeAccessEnabled()) {
/*  36 */       throw new UnsupportedOperationException("Native access is not enabled for the current module: " + 
/*  37 */           String.valueOf(FfmTerminalProvider.class.getModule()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/*  43 */     return "ffm";
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
/*  59 */     if (OSUtils.IS_WINDOWS) {
/*  60 */       return (Terminal)NativeWinSysTerminal.createTerminal(this, systemStream, name, type, ansiPassThrough, encoding, inputEncoding, outputEncoding, nativeSignals, signalHandler, paused);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     FfmNativePty ffmNativePty = new FfmNativePty(this, systemStream, -1, null, 0, FileDescriptor.in, (systemStream == SystemStream.Output) ? 1 : 2, (systemStream == SystemStream.Output) ? FileDescriptor.out : FileDescriptor.err, CLibrary.ttyName(0));
/*  83 */     return (Terminal)new PosixSysTerminal(name, type, (Pty)ffmNativePty, encoding, inputEncoding, outputEncoding, nativeSignals, signalHandler);
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
/*     */   public Terminal sysTerminal(String name, String type, boolean ansiPassThrough, Charset encoding, Charset stdinEncoding, Charset stdoutEncoding, Charset stderrEncoding, boolean nativeSignals, Terminal.SignalHandler signalHandler, boolean paused, SystemStream systemStream) throws IOException {
/* 104 */     if (OSUtils.IS_WINDOWS) {
/*     */       
/* 106 */       Charset charset = (systemStream == SystemStream.Error) ? stderrEncoding : stdoutEncoding;
/* 107 */       return (Terminal)NativeWinSysTerminal.createTerminal(this, systemStream, name, type, ansiPassThrough, encoding, stdinEncoding, charset, nativeSignals, signalHandler, paused);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     FfmNativePty ffmNativePty = new FfmNativePty(this, systemStream, -1, null, 0, FileDescriptor.in, (systemStream == SystemStream.Output) ? 1 : 2, (systemStream == SystemStream.Output) ? FileDescriptor.out : FileDescriptor.err, CLibrary.ttyName(0));
/*     */     
/* 131 */     Charset outputEncoding = (systemStream == SystemStream.Error) ? stderrEncoding : stdoutEncoding;
/* 132 */     return (Terminal)new PosixSysTerminal(name, type, (Pty)ffmNativePty, encoding, stdinEncoding, outputEncoding, nativeSignals, signalHandler);
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
/*     */   public Terminal newTerminal(String name, String type, InputStream in, OutputStream out, Charset encoding, Charset inputEncoding, Charset outputEncoding, Terminal.SignalHandler signalHandler, boolean paused, Attributes attributes, Size size) throws IOException {
/* 151 */     Pty pty = CLibrary.openpty(this, attributes, size);
/* 152 */     return (Terminal)new PosixPtyTerminal(name, type, pty, in, out, encoding, inputEncoding, outputEncoding, signalHandler, paused);
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
/* 173 */     Pty pty = CLibrary.openpty(this, attributes, size);
/* 174 */     return (Terminal)new PosixPtyTerminal(name, type, pty, in, out, encoding, stdinEncoding, stdoutEncoding, signalHandler, paused);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSystemStream(SystemStream stream) {
/* 180 */     if (OSUtils.IS_WINDOWS) {
/* 181 */       return isWindowsSystemStream(stream);
/*     */     }
/* 183 */     return isPosixSystemStream(stream);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWindowsSystemStream(SystemStream stream) {
/* 188 */     return NativeWinSysTerminal.isWindowsSystemStream(stream);
/*     */   }
/*     */   
/*     */   public boolean isPosixSystemStream(SystemStream stream) {
/* 192 */     return FfmNativePty.isPosixSystemStream(stream);
/*     */   }
/*     */ 
/*     */   
/*     */   public String systemStreamName(SystemStream stream) {
/* 197 */     return FfmNativePty.posixSystemStreamName(stream);
/*     */   }
/*     */ 
/*     */   
/*     */   public int systemStreamWidth(SystemStream stream) {
/* 202 */     return FfmNativePty.systemStreamWidth(stream);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 207 */     return "TerminalProvider[" + name() + "]";
/*     */   }
/*     */   
/*     */   static VarHandle lookupVarHandle(MemoryLayout layout, MemoryLayout.PathElement... element) {
/* 211 */     VarHandle h = layout.varHandle(element);
/*     */ 
/*     */     
/* 214 */     h = MethodHandles.insertCoordinates(h, h.coordinateTypes().size() - 1, new Object[] { Long.valueOf(0L) });
/*     */     
/* 216 */     return h;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\ffm\FfmTerminalProvider.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */