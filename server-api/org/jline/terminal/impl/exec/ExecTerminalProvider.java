/*     */ package org.jline.terminal.impl.exec;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.nio.charset.Charset;
/*     */ import org.jline.nativ.JLineLibrary;
/*     */ import org.jline.nativ.JLineNativeLoader;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.terminal.Size;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.terminal.TerminalBuilder;
/*     */ import org.jline.terminal.impl.ExternalTerminal;
/*     */ import org.jline.terminal.impl.PosixSysTerminal;
/*     */ import org.jline.terminal.spi.Pty;
/*     */ import org.jline.terminal.spi.SystemStream;
/*     */ import org.jline.terminal.spi.TerminalProvider;
/*     */ import org.jline.utils.ExecHelper;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExecTerminalProvider
/*     */   implements TerminalProvider
/*     */ {
/*     */   private static boolean warned;
/*     */   private static RedirectPipeCreator redirectPipeCreator;
/*     */   
/*     */   public String name() {
/*  80 */     return "exec";
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
/*     */   public Pty current(SystemStream systemStream) throws IOException {
/*  98 */     if (!isSystemStream(systemStream)) {
/*  99 */       throw new IOException("Not a system stream: " + systemStream);
/*     */     }
/* 101 */     return ExecPty.current(this, systemStream);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Terminal sysTerminal(String name, String type, boolean ansiPassThrough, Charset encoding, Charset inputEncoding, Charset outputEncoding, boolean nativeSignals, Terminal.SignalHandler signalHandler, boolean paused, SystemStream systemStream) throws IOException {
/* 139 */     if (OSUtils.IS_WINDOWS) {
/* 140 */       return winSysTerminal(name, type, ansiPassThrough, encoding, inputEncoding, outputEncoding, outputEncoding, nativeSignals, signalHandler, paused, systemStream);
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
/* 153 */     return posixSysTerminal(name, type, ansiPassThrough, encoding, inputEncoding, outputEncoding, outputEncoding, nativeSignals, signalHandler, paused, systemStream);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 205 */     if (OSUtils.IS_WINDOWS) {
/* 206 */       return winSysTerminal(name, type, ansiPassThrough, encoding, stdinEncoding, stdoutEncoding, stderrEncoding, nativeSignals, signalHandler, paused, systemStream);
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
/* 219 */     return posixSysTerminal(name, type, ansiPassThrough, encoding, stdinEncoding, stdoutEncoding, stderrEncoding, nativeSignals, signalHandler, paused, systemStream);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 269 */     return winSysTerminal(name, type, ansiPassThrough, encoding, encoding, encoding, encoding, nativeSignals, signalHandler, paused, systemStream);
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
/* 296 */     if (OSUtils.IS_CYGWIN || OSUtils.IS_MSYSTEM) {
/* 297 */       Pty pty = current(systemStream);
/*     */       
/* 299 */       Charset outputEncoding = (systemStream == SystemStream.Error) ? stderrEncoding : stdoutEncoding;
/* 300 */       return (Terminal)new PosixSysTerminal(name, type, pty, encoding, stdinEncoding, outputEncoding, nativeSignals, signalHandler);
/*     */     } 
/*     */     
/* 303 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Terminal posixSysTerminal(String name, String type, boolean ansiPassThrough, Charset encoding, boolean nativeSignals, Terminal.SignalHandler signalHandler, boolean paused, SystemStream systemStream) throws IOException {
/* 337 */     return posixSysTerminal(name, type, ansiPassThrough, encoding, encoding, encoding, encoding, nativeSignals, signalHandler, paused, systemStream);
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
/* 364 */     Pty pty = current(systemStream);
/*     */     
/* 366 */     Charset outputEncoding = (systemStream == SystemStream.Error) ? stderrEncoding : stdoutEncoding;
/* 367 */     return (Terminal)new PosixSysTerminal(name, type, pty, encoding, stdinEncoding, outputEncoding, nativeSignals, signalHandler);
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
/*     */ 
/*     */ 
/*     */ 
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
/* 409 */     return (Terminal)new ExternalTerminal(this, name, type, in, out, encoding, inputEncoding, outputEncoding, signalHandler, paused, attributes, size);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 464 */     return (Terminal)new ExternalTerminal(this, name, type, in, out, encoding, stdinEncoding, stdoutEncoding, signalHandler, paused, attributes, size);
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
/*     */   public boolean isSystemStream(SystemStream stream) {
/*     */     try {
/* 494 */       return (isPosixSystemStream(stream) || isWindowsSystemStream(stream));
/* 495 */     } catch (Throwable t) {
/* 496 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isWindowsSystemStream(SystemStream stream) {
/* 501 */     return (systemStreamName(stream) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPosixSystemStream(SystemStream stream) {
/*     */     try {
/* 508 */       Process p = (new ProcessBuilder(new String[] { OSUtils.TEST_COMMAND, "-t", Integer.toString(stream.ordinal()) })).inheritIO().start();
/* 509 */       return (p.waitFor() == 0);
/* 510 */     } catch (Throwable t) {
/* 511 */       Log.debug(new Object[] { "ExecTerminalProvider failed 'test -t' for " + stream, t });
/*     */ 
/*     */       
/* 514 */       return false;
/*     */     } 
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
/*     */   public String systemStreamName(SystemStream stream) {
/*     */     try {
/* 534 */       ProcessBuilder.Redirect input = (stream == SystemStream.Input) ? ProcessBuilder.Redirect.INHERIT : newDescriptor((stream == SystemStream.Output) ? FileDescriptor.out : FileDescriptor.err);
/*     */       
/* 536 */       Process p = (new ProcessBuilder(new String[] { OSUtils.TTY_COMMAND })).redirectInput(input).start();
/* 537 */       String result = ExecHelper.waitAndCapture(p);
/* 538 */       if (p.exitValue() == 0) {
/* 539 */         return result.trim();
/*     */       }
/* 541 */     } catch (Throwable t) {
/* 542 */       if ("java.lang.reflect.InaccessibleObjectException"
/* 543 */         .equals(t.getClass().getName()) && !warned) {
/*     */         
/* 545 */         Log.warn(new Object[] { "The ExecTerminalProvider requires the JVM options: '--add-opens java.base/java.lang=ALL-UNNAMED'" });
/*     */         
/* 547 */         warned = true;
/*     */       } 
/*     */     } 
/*     */     
/* 551 */     return null;
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
/*     */   public int systemStreamWidth(SystemStream stream) {
/*     */     
/* 574 */     try { ExecPty pty = new ExecPty(this, stream, null); 
/* 575 */       try { int i = pty.getSize().getColumns();
/* 576 */         pty.close(); return i; } catch (Throwable throwable) { try { pty.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable t)
/* 577 */     { return -1; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static ProcessBuilder.Redirect newDescriptor(FileDescriptor fd) {
/* 584 */     if (redirectPipeCreator == null) {
/* 585 */       String str = System.getProperty("org.jline.terminal.exec.redirectPipeCreationMode", TerminalBuilder.PROP_REDIRECT_PIPE_CREATION_MODE_DEFAULT);
/* 586 */       String[] modes = str.split(",");
/* 587 */       IllegalStateException ise = new IllegalStateException("Unable to create RedirectPipe");
/* 588 */       for (String mode : modes) {
/*     */         try {
/* 590 */           switch (mode) {
/*     */             case "native":
/* 592 */               redirectPipeCreator = new NativeRedirectPipeCreator();
/*     */               break;
/*     */             case "reflection":
/* 595 */               redirectPipeCreator = new ReflectionRedirectPipeCreator();
/*     */               break;
/*     */           } 
/* 598 */         } catch (Throwable t) {
/*     */           
/* 600 */           ise.addSuppressed(t);
/*     */         } 
/* 602 */         if (redirectPipeCreator != null) {
/*     */           break;
/*     */         }
/*     */       } 
/* 606 */       if (redirectPipeCreator == null) {
/* 607 */         throw ise;
/*     */       }
/*     */     } 
/* 610 */     return redirectPipeCreator.newRedirectPipe(fd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class ReflectionRedirectPipeCreator
/*     */     implements RedirectPipeCreator
/*     */   {
/*     */     private final Constructor<ProcessBuilder.Redirect> constructor;
/*     */ 
/*     */ 
/*     */     
/*     */     private final Field fdField;
/*     */ 
/*     */ 
/*     */     
/*     */     ReflectionRedirectPipeCreator() throws Exception {
/* 628 */       Class<?> rpi = Class.forName("java.lang.ProcessBuilder$RedirectPipeImpl");
/* 629 */       this.constructor = (Constructor)rpi.getDeclaredConstructor(new Class[0]);
/* 630 */       this.constructor.setAccessible(true);
/* 631 */       this.fdField = rpi.getDeclaredField("fd");
/* 632 */       this.fdField.setAccessible(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public ProcessBuilder.Redirect newRedirectPipe(FileDescriptor fd) {
/*     */       try {
/* 638 */         ProcessBuilder.Redirect input = this.constructor.newInstance(new Object[0]);
/* 639 */         this.fdField.set(input, fd);
/* 640 */         return input;
/* 641 */       } catch (ReflectiveOperationException e) {
/*     */         
/* 643 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class NativeRedirectPipeCreator
/*     */     implements RedirectPipeCreator {
/*     */     public NativeRedirectPipeCreator() {
/* 651 */       JLineNativeLoader.initialize();
/*     */     }
/*     */ 
/*     */     
/*     */     public ProcessBuilder.Redirect newRedirectPipe(FileDescriptor fd) {
/* 656 */       return JLineLibrary.newRedirectPipe(fd);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 662 */     return "TerminalProvider[" + name() + "]";
/*     */   }
/*     */   
/*     */   static interface RedirectPipeCreator {
/*     */     ProcessBuilder.Redirect newRedirectPipe(FileDescriptor param1FileDescriptor);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\exec\ExecTerminalProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */