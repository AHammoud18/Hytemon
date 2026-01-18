/*     */ package org.fusesource.jansi;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Properties;
/*     */ import org.fusesource.jansi.internal.CLibrary;
/*     */ import org.fusesource.jansi.internal.JansiLoader;
/*     */ import org.fusesource.jansi.internal.Kernel32;
/*     */ import org.fusesource.jansi.internal.MingwSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnsiMain
/*     */ {
/*     */   public static void main(String... args) throws IOException {
/*  57 */     System.out.println("Jansi " + getJansiVersion());
/*     */     
/*  59 */     System.out.println();
/*     */ 
/*     */     
/*  62 */     System.out.println("library.jansi.path= " + System.getProperty("library.jansi.path", ""));
/*  63 */     System.out.println("library.jansi.version= " + System.getProperty("library.jansi.version", ""));
/*  64 */     boolean loaded = JansiLoader.initialize();
/*  65 */     if (loaded) {
/*  66 */       System.out.println("Jansi native library loaded from " + JansiLoader.getNativeLibraryPath());
/*  67 */       if (JansiLoader.getNativeLibrarySourceUrl() != null) {
/*  68 */         System.out.println("   which was auto-extracted from " + JansiLoader.getNativeLibrarySourceUrl());
/*     */       }
/*     */     } else {
/*  71 */       String prev = System.getProperty("jansi.graceful");
/*     */       try {
/*  73 */         System.setProperty("jansi.graceful", "false");
/*  74 */         JansiLoader.initialize();
/*  75 */       } catch (Throwable e) {
/*  76 */         e.printStackTrace(System.out);
/*     */       } finally {
/*  78 */         if (prev != null) {
/*  79 */           System.setProperty("jansi.graceful", prev);
/*     */         } else {
/*  81 */           System.clearProperty("jansi.graceful");
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  86 */     System.out.println();
/*     */     
/*  88 */     System.out.println("os.name= " + System.getProperty("os.name") + ", os.version= " + 
/*  89 */         System.getProperty("os.version") + ", os.arch= " + 
/*  90 */         System.getProperty("os.arch"));
/*  91 */     System.out.println("file.encoding= " + System.getProperty("file.encoding"));
/*  92 */     System.out.println("sun.stdout.encoding= " + System.getProperty("sun.stdout.encoding") + ", sun.stderr.encoding= " + 
/*  93 */         System.getProperty("sun.stderr.encoding"));
/*  94 */     System.out.println("stdout.encoding= " + System.getProperty("stdout.encoding") + ", stderr.encoding= " + 
/*  95 */         System.getProperty("stderr.encoding"));
/*  96 */     System.out.println("java.version= " + System.getProperty("java.version") + ", java.vendor= " + 
/*  97 */         System.getProperty("java.vendor") + ", java.home= " + 
/*  98 */         System.getProperty("java.home"));
/*  99 */     System.out.println("Console: " + System.console());
/*     */     
/* 101 */     System.out.println();
/*     */     
/* 103 */     System.out.println("jansi.graceful= " + System.getProperty("jansi.graceful", ""));
/* 104 */     System.out.println("jansi.mode= " + System.getProperty("jansi.mode", ""));
/* 105 */     System.out.println("jansi.out.mode= " + System.getProperty("jansi.out.mode", ""));
/* 106 */     System.out.println("jansi.err.mode= " + System.getProperty("jansi.err.mode", ""));
/* 107 */     System.out.println("jansi.colors= " + System.getProperty("jansi.colors", ""));
/* 108 */     System.out.println("jansi.out.colors= " + System.getProperty("jansi.out.colors", ""));
/* 109 */     System.out.println("jansi.err.colors= " + System.getProperty("jansi.err.colors", ""));
/* 110 */     System.out.println("jansi.passthrough= " + 
/* 111 */         AnsiConsole.getBoolean("jansi.passthrough"));
/* 112 */     System.out.println("jansi.strip= " + AnsiConsole.getBoolean("jansi.strip"));
/* 113 */     System.out.println("jansi.force= " + AnsiConsole.getBoolean("jansi.force"));
/* 114 */     System.out.println("jansi.noreset= " + AnsiConsole.getBoolean("jansi.noreset"));
/* 115 */     System.out.println(Ansi.DISABLE + "= " + AnsiConsole.getBoolean(Ansi.DISABLE));
/*     */     
/* 117 */     System.out.println();
/*     */     
/* 119 */     System.out.println("IS_WINDOWS: " + AnsiConsole.IS_WINDOWS);
/* 120 */     if (AnsiConsole.IS_WINDOWS) {
/* 121 */       System.out.println("IS_CONEMU: " + AnsiConsole.IS_CONEMU);
/* 122 */       System.out.println("IS_CYGWIN: " + AnsiConsole.IS_CYGWIN);
/* 123 */       System.out.println("IS_MSYSTEM: " + AnsiConsole.IS_MSYSTEM);
/*     */     } 
/*     */     
/* 126 */     System.out.println();
/*     */     
/* 128 */     diagnoseTty(false);
/* 129 */     diagnoseTty(true);
/*     */     
/* 131 */     AnsiConsole.systemInstall();
/*     */     
/* 133 */     System.out.println();
/*     */     
/* 135 */     System.out.println("Resulting Jansi modes for stout/stderr streams:");
/* 136 */     System.out.println("  - System.out: " + AnsiConsole.out().toString());
/* 137 */     System.out.println("  - System.err: " + AnsiConsole.err().toString());
/* 138 */     System.out.println("Processor types description:");
/* 139 */     for (AnsiType type : AnsiType.values()) {
/* 140 */       System.out.println("  - " + type + ": " + type.getDescription());
/*     */     }
/* 142 */     System.out.println("Colors support description:");
/* 143 */     for (AnsiColors colors : AnsiColors.values()) {
/* 144 */       System.out.println("  - " + colors + ": " + colors.getDescription());
/*     */     }
/* 146 */     System.out.println("Modes description:");
/* 147 */     for (AnsiMode mode : AnsiMode.values()) {
/* 148 */       System.out.println("  - " + mode + ": " + mode.getDescription());
/*     */     }
/*     */     
/*     */     try {
/* 152 */       System.out.println();
/*     */       
/* 154 */       testAnsi(false);
/* 155 */       testAnsi(true);
/*     */       
/* 157 */       if (args.length == 0) {
/* 158 */         printJansiLogoDemo();
/*     */         
/*     */         return;
/*     */       } 
/* 162 */       System.out.println();
/*     */       
/* 164 */       if (args.length == 1) {
/* 165 */         File f = new File(args[0]);
/* 166 */         if (f.exists()) {
/*     */           
/* 168 */           System.out.println(
/* 169 */               Ansi.ansi().bold().a("\"" + args[0] + "\" content:").reset());
/* 170 */           writeFileContent(f);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */       
/* 176 */       System.out.println(Ansi.ansi().bold().a("original args:").reset());
/* 177 */       int i = 1;
/* 178 */       for (String arg : args) {
/* 179 */         AnsiConsole.system_out.print(i++ + ": ");
/* 180 */         AnsiConsole.system_out.println(arg);
/*     */       } 
/*     */       
/* 183 */       System.out.println(Ansi.ansi().bold().a("Jansi filtered args:").reset());
/* 184 */       i = 1;
/* 185 */       for (String arg : args) {
/* 186 */         System.out.print(i++ + ": ");
/* 187 */         System.out.println(arg);
/*     */       } 
/*     */     } finally {
/* 190 */       AnsiConsole.systemUninstall();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getJansiVersion() {
/* 195 */     Package p = AnsiMain.class.getPackage();
/* 196 */     return (p == null) ? null : p.getImplementationVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void diagnoseTty(boolean stderr) {
/*     */     int isatty, width;
/* 202 */     if (AnsiConsole.IS_WINDOWS) {
/* 203 */       long console = Kernel32.GetStdHandle(stderr ? Kernel32.STD_ERROR_HANDLE : Kernel32.STD_OUTPUT_HANDLE);
/* 204 */       int[] mode = new int[1];
/* 205 */       isatty = Kernel32.GetConsoleMode(console, mode);
/* 206 */       if ((AnsiConsole.IS_CONEMU || AnsiConsole.IS_CYGWIN || AnsiConsole.IS_MSYSTEM) && isatty == 0) {
/* 207 */         MingwSupport mingw = new MingwSupport();
/* 208 */         String name = mingw.getConsoleName(!stderr);
/* 209 */         if (name != null && !name.isEmpty()) {
/* 210 */           isatty = 1;
/* 211 */           width = mingw.getTerminalWidth(name);
/*     */         } else {
/* 213 */           isatty = 0;
/* 214 */           width = 0;
/*     */         } 
/*     */       } else {
/* 217 */         Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
/* 218 */         Kernel32.GetConsoleScreenBufferInfo(console, info);
/* 219 */         width = info.windowWidth();
/*     */       } 
/*     */     } else {
/* 222 */       int fd = stderr ? CLibrary.STDERR_FILENO : CLibrary.STDOUT_FILENO;
/* 223 */       isatty = CLibrary.LOADED ? CLibrary.isatty(fd) : 0;
/* 224 */       CLibrary.WinSize ws = new CLibrary.WinSize();
/* 225 */       CLibrary.ioctl(fd, CLibrary.TIOCGWINSZ, ws);
/* 226 */       width = ws.ws_col;
/*     */     } 
/*     */     
/* 229 */     System.out.println("isatty(STD" + (stderr ? "ERR" : "OUT") + "_FILENO): " + isatty + ", System." + (
/* 230 */         stderr ? "err" : "out") + " " + ((isatty == 0) ? "is *NOT*" : "is") + " a terminal");
/* 231 */     System.out.println("width(STD" + (stderr ? "ERR" : "OUT") + "_FILENO): " + width);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void testAnsi(boolean stderr) {
/* 236 */     PrintStream s = stderr ? System.err : System.out;
/* 237 */     s.print("test on System." + (stderr ? "err" : "out") + ":");
/* 238 */     for (Ansi.Color c : Ansi.Color.values()) {
/* 239 */       s.print(" " + Ansi.ansi().fg(c) + c + Ansi.ansi().reset());
/*     */     }
/* 241 */     s.println();
/* 242 */     s.print("            bright:");
/* 243 */     for (Ansi.Color c : Ansi.Color.values()) {
/* 244 */       s.print(" " + Ansi.ansi().fgBright(c) + c + Ansi.ansi().reset());
/*     */     }
/* 246 */     s.println();
/* 247 */     s.print("              bold:");
/* 248 */     for (Ansi.Color c : Ansi.Color.values()) {
/* 249 */       s.print(" " + Ansi.ansi().bold().fg(c) + c + Ansi.ansi().reset());
/*     */     }
/* 251 */     s.println();
/* 252 */     s.print("             faint:");
/* 253 */     for (Ansi.Color c : Ansi.Color.values()) {
/* 254 */       s.print(" " + Ansi.ansi().a(Ansi.Attribute.INTENSITY_FAINT).fg(c) + c + Ansi.ansi().reset());
/*     */     }
/* 256 */     s.println();
/* 257 */     s.print("        bold+faint:");
/* 258 */     for (Ansi.Color c : Ansi.Color.values()) {
/* 259 */       s.print(" " + Ansi.ansi().bold().a(Ansi.Attribute.INTENSITY_FAINT).fg(c) + c + Ansi.ansi().reset());
/*     */     }
/* 261 */     s.println();
/* 262 */     Ansi ansi = Ansi.ansi();
/* 263 */     ansi.a("        256 colors: "); int i;
/* 264 */     for (i = 0; i < 216; i++) {
/* 265 */       if (i > 0 && i % 36 == 0) {
/* 266 */         ansi.reset();
/* 267 */         ansi.newline();
/* 268 */         ansi.a("                    ");
/* 269 */       } else if (i > 0 && i % 6 == 0) {
/* 270 */         ansi.reset();
/* 271 */         ansi.a("  ");
/*     */       } 
/* 273 */       int a0 = i % 6;
/* 274 */       int a1 = i / 6 % 6;
/* 275 */       int a2 = i / 36;
/* 276 */       ansi.bg(16 + a0 + a2 * 6 + a1 * 36).a(' ');
/*     */     } 
/* 278 */     ansi.reset();
/* 279 */     s.println(ansi);
/* 280 */     ansi = Ansi.ansi();
/* 281 */     ansi.a("         truecolor: ");
/* 282 */     for (i = 0; i < 256; i++) {
/* 283 */       if (i > 0 && i % 48 == 0) {
/* 284 */         ansi.reset();
/* 285 */         ansi.newline();
/* 286 */         ansi.a("                    ");
/*     */       } 
/* 288 */       int r = 255 - i;
/* 289 */       int g = (i * 2 > 255) ? (255 - 2 * i) : (2 * i);
/* 290 */       int b = i;
/* 291 */       ansi.bgRgb(r, g, b).fgRgb(255 - r, 255 - g, 255 - b).a((i % 2 == 0) ? 47 : 92);
/*     */     } 
/* 293 */     ansi.reset();
/* 294 */     s.println(ansi);
/*     */   }
/*     */   
/*     */   private static String getPomPropertiesVersion(String path) throws IOException {
/* 298 */     InputStream in = AnsiMain.class.getResourceAsStream("/META-INF/maven/" + path + "/pom.properties");
/* 299 */     if (in == null) {
/* 300 */       return null;
/*     */     }
/*     */     try {
/* 303 */       Properties p = new Properties();
/* 304 */       p.load(in);
/* 305 */       return p.getProperty("version");
/*     */     } finally {
/* 307 */       closeQuietly(in);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void printJansiLogoDemo() throws IOException {
/* 313 */     BufferedReader in = new BufferedReader(new InputStreamReader(AnsiMain.class.getResourceAsStream("jansi.txt"), StandardCharsets.UTF_8));
/*     */     try {
/*     */       String l;
/* 316 */       while ((l = in.readLine()) != null) {
/* 317 */         System.out.println(l);
/*     */       }
/*     */     } finally {
/* 320 */       closeQuietly(in);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void writeFileContent(File f) throws IOException {
/* 325 */     InputStream in = new FileInputStream(f);
/*     */     try {
/* 327 */       byte[] buf = new byte[1024];
/* 328 */       int l = 0;
/* 329 */       while ((l = in.read(buf)) >= 0) {
/* 330 */         System.out.write(buf, 0, l);
/*     */       }
/*     */     } finally {
/* 333 */       closeQuietly(in);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void closeQuietly(Closeable c) {
/*     */     try {
/* 339 */       c.close();
/* 340 */     } catch (IOException ioe) {
/* 341 */       ioe.printStackTrace(System.err);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\fusesource\jansi\AnsiMain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */