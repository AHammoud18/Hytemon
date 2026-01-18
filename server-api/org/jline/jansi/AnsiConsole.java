/*     */ package org.jline.jansi;
/*     */ 
/*     */ import java.io.IOError;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Objects;
/*     */ import org.jline.jansi.io.AnsiOutputStream;
/*     */ import org.jline.jansi.io.AnsiProcessor;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.terminal.TerminalBuilder;
/*     */ import org.jline.terminal.spi.TerminalExt;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public static final String JANSI_NORESET = "jansi.noreset";
/*     */   public static final String JANSI_GRACEFUL = "jansi.graceful";
/*     */   public static final String JANSI_PROVIDERS = "jansi.providers";
/*     */   public static final String JANSI_PROVIDER_JNI = "jni";
/*     */   public static final String JANSI_PROVIDER_FFM = "ffm";
/*     */   public static final String JANSI_PROVIDER_NATIVE_IMAGE = "native-image";
/* 140 */   private static final PrintStream system_out = System.out;
/*     */   private static PrintStream out;
/* 142 */   private static final PrintStream system_err = System.err;
/*     */ 
/*     */ 
/*     */   
/*     */   private static PrintStream err;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getTerminalWidth() {
/* 152 */     int w = out().getTerminalWidth();
/* 153 */     if (w <= 0) {
/* 154 */       w = err().getTerminalWidth();
/*     */     }
/* 156 */     return w;
/*     */   }
/*     */   
/* 159 */   static final boolean IS_WINDOWS = OSUtils.IS_WINDOWS;
/*     */   
/* 161 */   static final boolean IS_CYGWIN = (IS_WINDOWS && 
/* 162 */     System.getenv("PWD") != null && System.getenv("PWD").startsWith("/"));
/*     */   
/* 164 */   static final boolean IS_MSYSTEM = (IS_WINDOWS && 
/* 165 */     System.getenv("MSYSTEM") != null && (
/* 166 */     System.getenv("MSYSTEM").startsWith("MINGW") || 
/* 167 */     System.getenv("MSYSTEM").equals("MSYS")));
/*     */   
/* 169 */   static final boolean IS_CONEMU = (IS_WINDOWS && System.getenv("ConEmuPID") != null);
/*     */   
/*     */   static final int ENABLE_VIRTUAL_TERMINAL_PROCESSING = 4;
/*     */   
/* 173 */   static int STDOUT_FILENO = 1;
/*     */   
/* 175 */   static int STDERR_FILENO = 2;
/*     */   
/*     */   private static int installed;
/*     */   
/*     */   static Terminal terminal;
/*     */ 
/*     */   
/*     */   public static Terminal getTerminal() {
/* 183 */     return terminal;
/*     */   }
/*     */   
/*     */   public static void setTerminal(Terminal terminal) {
/* 187 */     AnsiConsole.terminal = terminal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized void doInstall() {
/*     */     try {
/* 195 */       if (terminal == null) {
/*     */ 
/*     */ 
/*     */         
/* 199 */         TerminalBuilder builder = TerminalBuilder.builder().system(true).name("jansi").providers(System.getProperty("jansi.providers"));
/* 200 */         String graceful = System.getProperty("jansi.graceful");
/* 201 */         if (graceful != null) {
/* 202 */           builder.dumb(Boolean.parseBoolean(graceful));
/*     */         }
/* 204 */         terminal = builder.build();
/*     */       } 
/* 206 */       if (out == null) {
/* 207 */         out = ansiStream(true);
/* 208 */         err = ansiStream(false);
/*     */       } 
/* 210 */     } catch (IOException e) {
/* 211 */       throw new IOError(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   static synchronized void doUninstall() {
/*     */     try {
/* 217 */       if (terminal != null) {
/* 218 */         terminal.close();
/*     */       }
/* 220 */     } catch (IOException e) {
/* 221 */       throw new IOError(e);
/*     */     } finally {
/* 223 */       terminal = null;
/* 224 */       out = null;
/* 225 */       err = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static AnsiPrintStream ansiStream(boolean stdout) throws IOException {
/*     */     AnsiMode mode;
/*     */     AnsiColors colors;
/* 232 */     AnsiProcessor processor = null;
/*     */     
/* 234 */     AnsiOutputStream.IoRunnable installer = null;
/* 235 */     AnsiOutputStream.IoRunnable uninstaller = null;
/*     */     
/* 237 */     OutputStream out = terminal.output();
/* 238 */     Objects.requireNonNull(terminal); AnsiOutputStream.WidthSupplier width = terminal::getWidth;
/*     */ 
/*     */     
/* 241 */     AnsiType type = (terminal instanceof org.jline.terminal.impl.DumbTerminal) ? AnsiType.Unsupported : ((((TerminalExt)terminal).getSystemStream() != null) ? AnsiType.Native : AnsiType.Redirected);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 246 */     String jansiMode = System.getProperty(stdout ? "jansi.out.mode" : "jansi.err.mode", System.getProperty("jansi.mode"));
/* 247 */     if ("force".equals(jansiMode)) {
/* 248 */       mode = AnsiMode.Force;
/* 249 */     } else if ("strip".equals(jansiMode)) {
/* 250 */       mode = AnsiMode.Strip;
/*     */     } else {
/* 252 */       mode = (type == AnsiType.Native) ? AnsiMode.Default : AnsiMode.Strip;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 260 */     String jansiColors = System.getProperty(stdout ? "jansi.out.colors" : "jansi.err.colors", System.getProperty("jansi.colors"));
/* 261 */     if ("truecolor".equals(jansiColors)) {
/* 262 */       colors = AnsiColors.TrueColor;
/* 263 */     } else if ("256".equals(jansiColors)) {
/* 264 */       colors = AnsiColors.Colors256;
/* 265 */     } else if (jansiColors != null) {
/* 266 */       colors = AnsiColors.Colors16;
/*     */     } else {
/*     */       String colorterm;
/*     */ 
/*     */       
/* 271 */       if ((colorterm = System.getenv("COLORTERM")) != null && (colorterm
/* 272 */         .contains("truecolor") || colorterm.contains("24bit"))) {
/* 273 */         colors = AnsiColors.TrueColor;
/*     */       } else {
/*     */         String term;
/*     */         
/* 277 */         if ((term = System.getenv("TERM")) != null && term.contains("-direct")) {
/* 278 */           colors = AnsiColors.TrueColor;
/*     */ 
/*     */         
/*     */         }
/* 282 */         else if (term != null && term.contains("-256color")) {
/* 283 */           colors = AnsiColors.Colors256;
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 288 */           colors = AnsiColors.Colors16;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 293 */     boolean resetAtUninstall = (type != AnsiType.Unsupported && !getBoolean("jansi.noreset"));
/*     */     
/* 295 */     return newPrintStream(new AnsiOutputStream(out, width, mode, processor, type, colors, terminal
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 303 */           .encoding(), installer, uninstaller, resetAtUninstall), terminal
/*     */ 
/*     */ 
/*     */         
/* 307 */         .encoding().name());
/*     */   }
/*     */   
/*     */   private static AnsiPrintStream newPrintStream(AnsiOutputStream out, String enc) {
/* 311 */     if (enc != null) {
/*     */       try {
/* 313 */         return new AnsiPrintStream(out, true, enc);
/* 314 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*     */     }
/*     */     
/* 317 */     return new AnsiPrintStream(out, true);
/*     */   }
/*     */   
/*     */   static boolean getBoolean(String name) {
/* 321 */     boolean result = false;
/*     */     try {
/* 323 */       String val = System.getProperty(name);
/* 324 */       result = (val.isEmpty() || Boolean.parseBoolean(val));
/* 325 */     } catch (IllegalArgumentException|NullPointerException illegalArgumentException) {}
/*     */     
/* 327 */     return result;
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
/* 339 */     doInstall();
/* 340 */     return (AnsiPrintStream)out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrintStream sysOut() {
/* 349 */     return system_out;
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
/* 361 */     doInstall();
/* 362 */     return (AnsiPrintStream)err;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrintStream sysErr() {
/* 371 */     return system_err;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void systemInstall() {
/* 380 */     if (installed == 0) {
/* 381 */       doInstall();
/* 382 */       System.setOut(out);
/* 383 */       System.setErr(err);
/*     */     } 
/* 385 */     installed++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized boolean isInstalled() {
/* 392 */     return (installed > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void systemUninstall() {
/* 401 */     installed--;
/* 402 */     if (installed == 0) {
/* 403 */       doUninstall();
/* 404 */       System.setOut(system_out);
/* 405 */       System.setErr(system_err);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\jansi\AnsiConsole.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */