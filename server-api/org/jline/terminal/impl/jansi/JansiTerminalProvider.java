/*     */ package org.jline.terminal.impl.jansi;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Properties;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.fusesource.jansi.AnsiConsole;
/*     */ import org.fusesource.jansi.internal.Kernel32;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.terminal.Size;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.terminal.impl.PosixPtyTerminal;
/*     */ import org.jline.terminal.impl.PosixSysTerminal;
/*     */ import org.jline.terminal.impl.jansi.freebsd.FreeBsdNativePty;
/*     */ import org.jline.terminal.impl.jansi.linux.LinuxNativePty;
/*     */ import org.jline.terminal.impl.jansi.osx.OsXNativePty;
/*     */ import org.jline.terminal.impl.jansi.win.JansiWinSysTerminal;
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
/*     */ public class JansiTerminalProvider
/*     */   implements TerminalProvider
/*     */ {
/*     */   static final int JANSI_MAJOR_VERSION;
/*     */   static final int JANSI_MINOR_VERSION;
/*     */   
/*     */   static {
/*  42 */     int major = 0, minor = 0;
/*     */     try {
/*  44 */       String v = null; 
/*  45 */       try { InputStream is = AnsiConsole.class.getResourceAsStream("jansi.properties"); 
/*  46 */         try { if (is != null) {
/*  47 */             Properties props = new Properties();
/*  48 */             props.load(is);
/*  49 */             v = props.getProperty("version");
/*     */           } 
/*  51 */           if (is != null) is.close();  } catch (Throwable throwable) { if (is != null) try { is.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException iOException) {}
/*     */ 
/*     */       
/*  54 */       if (v == null) {
/*  55 */         v = AnsiConsole.class.getPackage().getImplementationVersion();
/*     */       }
/*  57 */       if (v != null) {
/*  58 */         Matcher m = Pattern.compile("([0-9]+)\\.([0-9]+)([\\.-]\\S+)?").matcher(v);
/*  59 */         if (m.matches()) {
/*  60 */           major = Integer.parseInt(m.group(1));
/*  61 */           minor = Integer.parseInt(m.group(2));
/*     */         } 
/*     */       } 
/*  64 */     } catch (Throwable throwable) {}
/*     */ 
/*     */     
/*  67 */     JANSI_MAJOR_VERSION = major;
/*  68 */     JANSI_MINOR_VERSION = minor;
/*     */   }
/*     */   
/*     */   public static int getJansiMajorVersion() {
/*  72 */     return JANSI_MAJOR_VERSION;
/*     */   }
/*     */   
/*     */   public static int getJansiMinorVersion() {
/*  76 */     return JANSI_MINOR_VERSION;
/*     */   }
/*     */   
/*     */   public static boolean isAtLeast(int major, int minor) {
/*  80 */     return (JANSI_MAJOR_VERSION > major || (JANSI_MAJOR_VERSION == major && JANSI_MINOR_VERSION >= minor));
/*     */   }
/*     */   
/*     */   public static void verifyAtLeast(int major, int minor) {
/*  84 */     if (!isAtLeast(major, minor)) {
/*  85 */       throw new UnsupportedOperationException("An old version of Jansi is loaded from " + Kernel32.class
/*     */           
/*  87 */           .getClassLoader()
/*  88 */           .getResource(Kernel32.class.getName().replace('.', '/') + ".class"));
/*     */     }
/*     */   }
/*     */   
/*     */   public JansiTerminalProvider() {
/*  93 */     verifyAtLeast(1, 17);
/*  94 */     checkIsSystemStream(SystemStream.Output);
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/*  99 */     return "jansi";
/*     */   }
/*     */   
/*     */   public Pty current(SystemStream systemStream) throws IOException {
/* 103 */     String osName = System.getProperty("os.name");
/* 104 */     if (osName.startsWith("Linux"))
/* 105 */       return (Pty)LinuxNativePty.current(this, systemStream); 
/* 106 */     if (osName.startsWith("Mac") || osName.startsWith("Darwin"))
/* 107 */       return (Pty)OsXNativePty.current(this, systemStream); 
/* 108 */     if (osName.startsWith("Solaris") || osName.startsWith("SunOS"))
/*     */     {
/*     */       
/* 111 */       throw new UnsupportedOperationException("Unsupported platform " + osName); } 
/* 112 */     if (osName.startsWith("FreeBSD")) {
/* 113 */       return (Pty)FreeBsdNativePty.current(this, systemStream);
/*     */     }
/* 115 */     throw new UnsupportedOperationException("Unsupported platform " + osName);
/*     */   }
/*     */ 
/*     */   
/*     */   public Pty open(Attributes attributes, Size size) throws IOException {
/* 120 */     String osName = System.getProperty("os.name");
/* 121 */     if (osName.startsWith("Linux"))
/* 122 */       return (Pty)LinuxNativePty.open(this, attributes, size); 
/* 123 */     if (osName.startsWith("Mac") || osName.startsWith("Darwin"))
/* 124 */       return (Pty)OsXNativePty.open(this, attributes, size); 
/* 125 */     if (osName.startsWith("Solaris") || osName.startsWith("SunOS"))
/*     */     {
/*     */       
/* 128 */       throw new UnsupportedOperationException("Unsupported platform " + osName); } 
/* 129 */     if (osName.startsWith("FreeBSD")) {
/* 130 */       return (Pty)FreeBsdNativePty.open(this, attributes, size);
/*     */     }
/* 132 */     throw new UnsupportedOperationException("Unsupported platform " + osName);
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
/*     */   public Terminal sysTerminal(String name, String type, boolean ansiPassThrough, Charset encoding, Charset inputEncoding, Charset outputEncoding, boolean nativeSignals, Terminal.SignalHandler signalHandler, boolean paused, SystemStream systemStream) throws IOException {
/* 149 */     if (OSUtils.IS_WINDOWS) {
/* 150 */       return winSysTerminal(name, type, ansiPassThrough, encoding, inputEncoding, outputEncoding, outputEncoding, nativeSignals, signalHandler, paused, systemStream);
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
/* 163 */     return posixSysTerminal(name, type, ansiPassThrough, encoding, inputEncoding, outputEncoding, outputEncoding, nativeSignals, signalHandler, paused, systemStream);
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
/* 194 */     if (OSUtils.IS_WINDOWS) {
/* 195 */       return winSysTerminal(name, type, ansiPassThrough, encoding, stdinEncoding, stdoutEncoding, stderrEncoding, nativeSignals, signalHandler, paused, systemStream);
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
/* 208 */     return posixSysTerminal(name, type, ansiPassThrough, encoding, stdinEncoding, stdoutEncoding, stderrEncoding, nativeSignals, signalHandler, paused, systemStream);
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
/* 233 */     return winSysTerminal(name, type, ansiPassThrough, encoding, encoding, encoding, encoding, nativeSignals, signalHandler, paused, systemStream);
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
/* 260 */     JansiWinSysTerminal terminal = JansiWinSysTerminal.createTerminal(this, systemStream, name, type, ansiPassThrough, encoding, stdinEncoding, stdoutEncoding, stderrEncoding, nativeSignals, signalHandler, paused);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 273 */     terminal.disableScrolling();
/* 274 */     return (Terminal)terminal;
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
/*     */   public Terminal posixSysTerminal(String name, String type, boolean ansiPassThrough, Charset encoding, boolean nativeSignals, Terminal.SignalHandler signalHandler, boolean paused, SystemStream systemStream) throws IOException {
/* 287 */     return posixSysTerminal(name, type, ansiPassThrough, encoding, encoding, encoding, encoding, nativeSignals, signalHandler, paused, systemStream);
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
/* 314 */     Pty pty = current(systemStream);
/*     */     
/* 316 */     Charset outputEncoding = (systemStream == SystemStream.Error) ? stderrEncoding : stdoutEncoding;
/* 317 */     return (Terminal)new PosixSysTerminal(name, type, pty, encoding, stdinEncoding, outputEncoding, nativeSignals, signalHandler);
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
/* 335 */     Pty pty = open(attributes, size);
/* 336 */     return (Terminal)new PosixPtyTerminal(name, type, pty, in, out, encoding, inputEncoding, outputEncoding, signalHandler, paused);
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
/* 357 */     Pty pty = open(attributes, size);
/* 358 */     return (Terminal)new PosixPtyTerminal(name, type, pty, in, out, encoding, stdinEncoding, stdoutEncoding, signalHandler, paused);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSystemStream(SystemStream stream) {
/*     */     try {
/* 365 */       return checkIsSystemStream(stream);
/* 366 */     } catch (Throwable t) {
/* 367 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean checkIsSystemStream(SystemStream stream) {
/* 372 */     if (OSUtils.IS_WINDOWS) {
/* 373 */       return JansiWinSysTerminal.isWindowsSystemStream(stream);
/*     */     }
/* 375 */     return JansiNativePty.isPosixSystemStream(stream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String systemStreamName(SystemStream stream) {
/* 381 */     return JansiNativePty.posixSystemStreamName(stream);
/*     */   }
/*     */ 
/*     */   
/*     */   public int systemStreamWidth(SystemStream stream) {
/* 386 */     return JansiNativePty.systemStreamWidth(stream);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 391 */     return "TerminalProvider[" + name() + "]";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jansi\JansiTerminalProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */