/*     */ package org.fusesource.jansi;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOError;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.UnsupportedCharsetException;
/*     */ import java.util.Locale;
/*     */ import org.fusesource.jansi.internal.CLibrary;
/*     */ import org.fusesource.jansi.internal.Kernel32;
/*     */ import org.fusesource.jansi.internal.MingwSupport;
/*     */ import org.fusesource.jansi.io.AnsiOutputStream;
/*     */ import org.fusesource.jansi.io.AnsiProcessor;
/*     */ import org.fusesource.jansi.io.FastBufferedOutputStream;
/*     */ import org.fusesource.jansi.io.WindowsAnsiProcessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnsiConsole
/*     */ {
/*     */   public static final String JANSI_MODE = "jansi.mode";
/*     */   public static final String JANSI_OUT_MODE = "jansi.out.mode";
/*     */   public static final String JANSI_ERR_MODE = "jansi.err.mode";
/*     */   public static final String JANSI_MODE_STRIP = "strip";
/*     */   public static final String JANSI_MODE_FORCE = "force";
/*     */   public static final String JANSI_MODE_DEFAULT = "default";
/*     */   public static final String JANSI_COLORS = "jansi.colors";
/*     */   public static final String JANSI_OUT_COLORS = "jansi.out.colors";
/*     */   public static final String JANSI_ERR_COLORS = "jansi.err.colors";
/*     */   public static final String JANSI_COLORS_16 = "16";
/*     */   public static final String JANSI_COLORS_256 = "256";
/*     */   public static final String JANSI_COLORS_TRUECOLOR = "truecolor";
/*     */   @Deprecated
/*     */   public static final String JANSI_PASSTHROUGH = "jansi.passthrough";
/*     */   @Deprecated
/*     */   public static final String JANSI_STRIP = "jansi.strip";
/*     */   @Deprecated
/*     */   public static final String JANSI_FORCE = "jansi.force";
/*     */   @Deprecated
/*     */   public static final String JANSI_EAGER = "jansi.eager";
/*     */   public static final String JANSI_NORESET = "jansi.noreset";
/*     */   public static final String JANSI_GRACEFUL = "jansi.graceful";
/*     */   @Deprecated
/* 175 */   public static PrintStream system_out = System.out;
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static PrintStream out;
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/* 185 */   public static PrintStream system_err = System.err;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static PrintStream err;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getTerminalWidth() {
/* 199 */     int w = out().getTerminalWidth();
/* 200 */     if (w <= 0) {
/* 201 */       w = err().getTerminalWidth();
/*     */     }
/* 203 */     return w;
/*     */   }
/*     */ 
/*     */   
/* 207 */   static final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase(Locale.ENGLISH).contains("win");
/*     */   
/* 209 */   static final boolean IS_CYGWIN = (IS_WINDOWS && 
/* 210 */     System.getenv("PWD") != null && System.getenv("PWD").startsWith("/"));
/*     */   
/* 212 */   static final boolean IS_MSYSTEM = (IS_WINDOWS && 
/* 213 */     System.getenv("MSYSTEM") != null && (
/* 214 */     System.getenv("MSYSTEM").startsWith("MINGW") || 
/* 215 */     System.getenv("MSYSTEM").equals("MSYS")));
/*     */   
/* 217 */   static final boolean IS_CONEMU = (IS_WINDOWS && System.getenv("ConEmuPID") != null);
/*     */   
/*     */   static final int ENABLE_VIRTUAL_TERMINAL_PROCESSING = 4;
/*     */   
/* 221 */   static int STDOUT_FILENO = 1;
/*     */   
/* 223 */   static int STDERR_FILENO = 2; private static boolean initialized;
/*     */   
/*     */   static {
/* 226 */     if (getBoolean("jansi.eager"))
/* 227 */       initStreams(); 
/*     */   }
/*     */   private static int installed; private static int virtualProcessing;
/*     */   private static AnsiPrintStream ansiStream(boolean stdout) {
/*     */     boolean isAtty, withException;
/*     */     AnsiOutputStream.WidthSupplier width;
/*     */     AnsiProcessor processor;
/*     */     AnsiType type;
/*     */     AnsiOutputStream.IoRunnable installer, uninstaller;
/*     */     AnsiMode mode;
/*     */     AnsiColors colors;
/* 238 */     FileDescriptor descriptor = stdout ? FileDescriptor.out : FileDescriptor.err;
/* 239 */     FastBufferedOutputStream fastBufferedOutputStream = new FastBufferedOutputStream(new FileOutputStream(descriptor));
/*     */     
/* 241 */     String enc = System.getProperty(stdout ? "stdout.encoding" : "stderr.encoding");
/* 242 */     if (enc == null) {
/* 243 */       enc = System.getProperty(stdout ? "sun.stdout.encoding" : "sun.stderr.encoding");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 251 */     final int fd = stdout ? STDOUT_FILENO : STDERR_FILENO;
/*     */ 
/*     */     
/*     */     try {
/* 255 */       isAtty = (CLibrary.isatty(fd) != 0);
/* 256 */       String term = System.getenv("TERM");
/* 257 */       String emacs = System.getenv("INSIDE_EMACS");
/* 258 */       if (isAtty && "dumb".equals(term) && emacs != null && !emacs.contains("comint")) {
/* 259 */         isAtty = false;
/*     */       }
/* 261 */       withException = false;
/* 262 */     } catch (Throwable ignore) {
/*     */ 
/*     */       
/* 265 */       isAtty = false;
/* 266 */       withException = true;
/*     */     } 
/* 268 */     boolean isatty = isAtty;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 275 */     if (!isatty) {
/* 276 */       processor = null;
/* 277 */       type = withException ? AnsiType.Unsupported : AnsiType.Redirected;
/* 278 */       installer = uninstaller = null;
/* 279 */       AnsiOutputStream.ZeroWidthSupplier zeroWidthSupplier = new AnsiOutputStream.ZeroWidthSupplier();
/* 280 */     } else if (IS_WINDOWS) {
/* 281 */       final long console = Kernel32.GetStdHandle(stdout ? Kernel32.STD_OUTPUT_HANDLE : Kernel32.STD_ERROR_HANDLE);
/* 282 */       int[] arrayOfInt = new int[1];
/* 283 */       boolean isConsole = (Kernel32.GetConsoleMode(console, arrayOfInt) != 0);
/* 284 */       AnsiOutputStream.WidthSupplier kernel32Width = new AnsiOutputStream.WidthSupplier()
/*     */         {
/*     */           public int getTerminalWidth() {
/* 287 */             Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
/* 288 */             Kernel32.GetConsoleScreenBufferInfo(console, info);
/* 289 */             return info.windowWidth();
/*     */           }
/*     */         };
/*     */       
/* 293 */       if (isConsole && Kernel32.SetConsoleMode(console, arrayOfInt[0] | 0x4) != 0) {
/* 294 */         Kernel32.SetConsoleMode(console, arrayOfInt[0]);
/* 295 */         processor = null;
/* 296 */         type = AnsiType.VirtualTerminal;
/* 297 */         installer = (() -> {
/*     */             synchronized (AnsiConsole.class) {
/*     */               virtualProcessing++;
/*     */               Kernel32.SetConsoleMode(console, mode[0] | 0x4);
/*     */             } 
/*     */           });
/* 303 */         uninstaller = (() -> {
/*     */             synchronized (AnsiConsole.class) {
/*     */               if (--virtualProcessing == 0) {
/*     */                 Kernel32.SetConsoleMode(console, mode[0]);
/*     */               }
/*     */             } 
/*     */           });
/* 310 */         width = kernel32Width;
/* 311 */       } else if ((IS_CONEMU || IS_CYGWIN || IS_MSYSTEM) && !isConsole) {
/*     */         
/* 313 */         processor = null;
/* 314 */         type = AnsiType.Native;
/* 315 */         installer = uninstaller = null;
/* 316 */         MingwSupport mingw = new MingwSupport();
/* 317 */         String name = mingw.getConsoleName(stdout);
/* 318 */         if (name != null && !name.isEmpty()) {
/* 319 */           width = (() -> mingw.getTerminalWidth(name));
/*     */         } else {
/* 321 */           width = (() -> -1);
/*     */         } 
/*     */       } else {
/*     */         AnsiProcessor proc;
/*     */ 
/*     */         
/*     */         AnsiType ttype;
/*     */         
/*     */         try {
/* 330 */           WindowsAnsiProcessor windowsAnsiProcessor = new WindowsAnsiProcessor((OutputStream)fastBufferedOutputStream, console);
/* 331 */           ttype = AnsiType.Emulation;
/* 332 */         } catch (Throwable ignore) {
/*     */ 
/*     */           
/* 335 */           proc = new AnsiProcessor((OutputStream)fastBufferedOutputStream);
/* 336 */           ttype = AnsiType.Unsupported;
/*     */         } 
/* 338 */         processor = proc;
/* 339 */         type = ttype;
/* 340 */         installer = uninstaller = null;
/* 341 */         width = kernel32Width;
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 348 */       processor = null;
/* 349 */       type = AnsiType.Native;
/* 350 */       installer = uninstaller = null;
/* 351 */       width = new AnsiOutputStream.WidthSupplier()
/*     */         {
/*     */           public int getTerminalWidth() {
/* 354 */             CLibrary.WinSize sz = new CLibrary.WinSize();
/* 355 */             CLibrary.ioctl(fd, CLibrary.TIOCGWINSZ, sz);
/* 356 */             return sz.ws_col;
/*     */           }
/*     */         };
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 364 */     String jansiMode = System.getProperty(stdout ? "jansi.out.mode" : "jansi.err.mode", System.getProperty("jansi.mode"));
/* 365 */     if ("force".equals(jansiMode)) {
/* 366 */       mode = AnsiMode.Force;
/* 367 */     } else if ("strip".equals(jansiMode)) {
/* 368 */       mode = AnsiMode.Strip;
/* 369 */     } else if (jansiMode != null) {
/* 370 */       mode = isatty ? AnsiMode.Default : AnsiMode.Strip;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 375 */     else if (getBoolean("jansi.passthrough")) {
/* 376 */       mode = AnsiMode.Force;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 381 */     else if (getBoolean("jansi.strip")) {
/* 382 */       mode = AnsiMode.Strip;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 387 */     else if (getBoolean("jansi.force")) {
/* 388 */       mode = AnsiMode.Force;
/*     */     } else {
/* 390 */       mode = isatty ? AnsiMode.Default : AnsiMode.Strip;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 398 */     String jansiColors = System.getProperty(stdout ? "jansi.out.colors" : "jansi.err.colors", System.getProperty("jansi.colors"));
/* 399 */     if ("truecolor".equals(jansiColors)) {
/* 400 */       colors = AnsiColors.TrueColor;
/* 401 */     } else if ("256".equals(jansiColors)) {
/* 402 */       colors = AnsiColors.Colors256;
/* 403 */     } else if (jansiColors != null) {
/* 404 */       colors = AnsiColors.Colors16;
/*     */     } else {
/*     */       String colorterm;
/*     */ 
/*     */       
/* 409 */       if ((colorterm = System.getenv("COLORTERM")) != null && (colorterm
/* 410 */         .contains("truecolor") || colorterm.contains("24bit"))) {
/* 411 */         colors = AnsiColors.TrueColor;
/*     */       } else {
/*     */         String term;
/*     */         
/* 415 */         if ((term = System.getenv("TERM")) != null && term.contains("-direct")) {
/* 416 */           colors = AnsiColors.TrueColor;
/*     */ 
/*     */         
/*     */         }
/* 420 */         else if (term != null && term.contains("-256color")) {
/* 421 */           colors = AnsiColors.Colors256;
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 426 */           colors = AnsiColors.Colors16;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 431 */     boolean resetAtUninstall = (type != AnsiType.Unsupported && !getBoolean("jansi.noreset"));
/*     */     
/* 433 */     Charset cs = Charset.defaultCharset();
/* 434 */     if (enc != null) {
/*     */       try {
/* 436 */         cs = Charset.forName(enc);
/* 437 */       } catch (UnsupportedCharsetException unsupportedCharsetException) {}
/*     */     }
/*     */     
/* 440 */     return newPrintStream(new AnsiOutputStream((OutputStream)fastBufferedOutputStream, width, mode, processor, type, colors, cs, installer, uninstaller, resetAtUninstall), cs
/*     */ 
/*     */         
/* 443 */         .name());
/*     */   }
/*     */   
/*     */   private static AnsiPrintStream newPrintStream(AnsiOutputStream out, String enc) {
/* 447 */     if (enc != null) {
/*     */       try {
/* 449 */         return new AnsiPrintStream(out, true, enc);
/* 450 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*     */     }
/*     */     
/* 453 */     return new AnsiPrintStream(out, true);
/*     */   }
/*     */   
/*     */   static boolean getBoolean(String name) {
/* 457 */     boolean result = false;
/*     */     try {
/* 459 */       String val = System.getProperty(name);
/* 460 */       result = (val.isEmpty() || Boolean.parseBoolean(val));
/* 461 */     } catch (IllegalArgumentException|NullPointerException illegalArgumentException) {}
/*     */     
/* 463 */     return result;
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
/*     */   public static AnsiPrintStream out() {
/* 475 */     initStreams();
/* 476 */     return (AnsiPrintStream)out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrintStream sysOut() {
/* 485 */     return system_out;
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
/*     */   public static AnsiPrintStream err() {
/* 497 */     initStreams();
/* 498 */     return (AnsiPrintStream)err;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrintStream sysErr() {
/* 507 */     return system_err;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void systemInstall() {
/* 516 */     if (installed == 0) {
/* 517 */       initStreams();
/*     */       try {
/* 519 */         ((AnsiPrintStream)out).install();
/* 520 */         ((AnsiPrintStream)err).install();
/* 521 */       } catch (IOException e) {
/* 522 */         throw new IOError(e);
/*     */       } 
/* 524 */       System.setOut(out);
/* 525 */       System.setErr(err);
/*     */     } 
/* 527 */     installed++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized boolean isInstalled() {
/* 534 */     return (installed > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void systemUninstall() {
/* 543 */     installed--;
/* 544 */     if (installed == 0) {
/*     */       try {
/* 546 */         ((AnsiPrintStream)out).uninstall();
/* 547 */         ((AnsiPrintStream)err).uninstall();
/* 548 */       } catch (IOException e) {
/* 549 */         throw new IOError(e);
/*     */       } 
/* 551 */       initialized = false;
/* 552 */       System.setOut(system_out);
/* 553 */       System.setErr(system_err);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized void initStreams() {
/* 561 */     if (!initialized) {
/* 562 */       out = ansiStream(true);
/* 563 */       err = ansiStream(false);
/* 564 */       initialized = true;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\fusesource\jansi\AnsiConsole.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */