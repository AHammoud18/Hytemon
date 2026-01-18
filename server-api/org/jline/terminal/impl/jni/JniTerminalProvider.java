/*     */ package org.jline.terminal.impl.jni;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import org.jline.nativ.JLineNativeLoader;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.terminal.Size;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.terminal.impl.PosixPtyTerminal;
/*     */ import org.jline.terminal.impl.PosixSysTerminal;
/*     */ import org.jline.terminal.impl.jni.freebsd.FreeBsdNativePty;
/*     */ import org.jline.terminal.impl.jni.linux.LinuxNativePty;
/*     */ import org.jline.terminal.impl.jni.osx.OsXNativePty;
/*     */ import org.jline.terminal.impl.jni.solaris.SolarisNativePty;
/*     */ import org.jline.terminal.impl.jni.win.NativeWinSysTerminal;
/*     */ import org.jline.terminal.spi.Pty;
/*     */ import org.jline.terminal.spi.SystemStream;
/*     */ import org.jline.terminal.spi.TerminalProvider;
/*     */ import org.jline.utils.Log;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JniTerminalProvider
/*     */   implements TerminalProvider
/*     */ {
/*     */   public JniTerminalProvider() {
/*     */     try {
/*  62 */       JLineNativeLoader.initialize();
/*  63 */     } catch (Exception e) {
/*     */ 
/*     */ 
/*     */       
/*  67 */       Log.debug(new Object[] { "Failed to load JLine native library: " + e.getMessage(), e });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/*  73 */     return "jni";
/*     */   }
/*     */   
/*     */   public Pty current(SystemStream systemStream) throws IOException {
/*  77 */     String osName = System.getProperty("os.name");
/*  78 */     if (osName.startsWith("Linux"))
/*  79 */       return (Pty)LinuxNativePty.current(this, systemStream); 
/*  80 */     if (osName.startsWith("Mac") || osName.startsWith("Darwin"))
/*  81 */       return (Pty)OsXNativePty.current(this, systemStream); 
/*  82 */     if (osName.startsWith("Solaris") || osName.startsWith("SunOS"))
/*  83 */       return (Pty)SolarisNativePty.current(this, systemStream); 
/*  84 */     if (osName.startsWith("FreeBSD")) {
/*  85 */       return (Pty)FreeBsdNativePty.current(this, systemStream);
/*     */     }
/*  87 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Pty open(Attributes attributes, Size size) throws IOException {
/*  91 */     String osName = System.getProperty("os.name");
/*  92 */     if (osName.startsWith("Linux"))
/*  93 */       return (Pty)LinuxNativePty.open(this, attributes, size); 
/*  94 */     if (osName.startsWith("Mac") || osName.startsWith("Darwin"))
/*  95 */       return (Pty)OsXNativePty.open(this, attributes, size); 
/*  96 */     if (osName.startsWith("Solaris") || osName.startsWith("SunOS"))
/*  97 */       return (Pty)SolarisNativePty.open(this, attributes, size); 
/*  98 */     if (osName.startsWith("FreeBSD")) {
/*  99 */       return (Pty)FreeBsdNativePty.open(this, attributes, size);
/*     */     }
/* 101 */     throw new UnsupportedOperationException();
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
/* 117 */     if (OSUtils.IS_WINDOWS) {
/* 118 */       return winSysTerminal(name, type, ansiPassThrough, encoding, inputEncoding, outputEncoding, outputEncoding, nativeSignals, signalHandler, paused, systemStream);
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
/* 131 */     return posixSysTerminal(name, type, ansiPassThrough, encoding, inputEncoding, outputEncoding, outputEncoding, nativeSignals, signalHandler, paused, systemStream);
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
/* 162 */     if (OSUtils.IS_WINDOWS) {
/* 163 */       return winSysTerminal(name, type, ansiPassThrough, encoding, stdinEncoding, stdoutEncoding, stderrEncoding, nativeSignals, signalHandler, paused, systemStream);
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
/* 176 */     return posixSysTerminal(name, type, ansiPassThrough, encoding, stdinEncoding, stdoutEncoding, stderrEncoding, nativeSignals, signalHandler, paused, systemStream);
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
/* 201 */     return winSysTerminal(name, type, ansiPassThrough, encoding, encoding, encoding, encoding, nativeSignals, signalHandler, paused, systemStream);
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
/* 228 */     return (Terminal)NativeWinSysTerminal.createTerminal(this, systemStream, name, type, ansiPassThrough, encoding, stdinEncoding, stdoutEncoding, stderrEncoding, nativeSignals, signalHandler, paused);
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
/* 253 */     return posixSysTerminal(name, type, ansiPassThrough, encoding, encoding, encoding, encoding, nativeSignals, signalHandler, paused, systemStream);
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
/* 280 */     Pty pty = current(systemStream);
/*     */     
/* 282 */     Charset outputEncoding = (systemStream == SystemStream.Error) ? stderrEncoding : stdoutEncoding;
/* 283 */     return (Terminal)new PosixSysTerminal(name, type, pty, encoding, stdinEncoding, outputEncoding, nativeSignals, signalHandler);
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
/* 301 */     Pty pty = open(attributes, size);
/* 302 */     return (Terminal)new PosixPtyTerminal(name, type, pty, in, out, encoding, inputEncoding, outputEncoding, signalHandler, paused);
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
/* 323 */     Pty pty = open(attributes, size);
/* 324 */     return (Terminal)new PosixPtyTerminal(name, type, pty, in, out, encoding, stdinEncoding, stdoutEncoding, signalHandler, paused);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSystemStream(SystemStream stream) {
/*     */     try {
/* 331 */       if (OSUtils.IS_WINDOWS) {
/* 332 */         return isWindowsSystemStream(stream);
/*     */       }
/* 334 */       return isPosixSystemStream(stream);
/*     */     }
/* 336 */     catch (Throwable t) {
/* 337 */       Log.debug(new Object[] { "Exception while checking system stream (this may disable the JNI provider)", t });
/* 338 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isWindowsSystemStream(SystemStream stream) {
/* 343 */     return NativeWinSysTerminal.isWindowsSystemStream(stream);
/*     */   }
/*     */   
/*     */   public boolean isPosixSystemStream(SystemStream stream) {
/* 347 */     return JniNativePty.isPosixSystemStream(stream);
/*     */   }
/*     */ 
/*     */   
/*     */   public String systemStreamName(SystemStream stream) {
/* 352 */     return JniNativePty.posixSystemStreamName(stream);
/*     */   }
/*     */ 
/*     */   
/*     */   public int systemStreamWidth(SystemStream stream) {
/* 357 */     return JniNativePty.systemStreamWidth(stream);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 362 */     return "TerminalProvider[" + name() + "]";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jni\JniTerminalProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */