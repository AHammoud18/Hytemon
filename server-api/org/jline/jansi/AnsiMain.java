/*     */ package org.jline.jansi;
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
/*     */ import org.jline.terminal.impl.Diag;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  46 */     Diag.diag(System.out);
/*     */     
/*  48 */     System.out.println("Jansi " + getJansiVersion());
/*     */     
/*  50 */     System.out.println();
/*     */     
/*  52 */     System.out.println("jansi.providers= " + System.getProperty("jansi.providers", ""));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     System.out.println();
/*     */     
/*  86 */     System.out.println("os.name= " + System.getProperty("os.name") + ", os.version= " + 
/*  87 */         System.getProperty("os.version") + ", os.arch= " + 
/*  88 */         System.getProperty("os.arch"));
/*  89 */     System.out.println("file.encoding= " + System.getProperty("file.encoding"));
/*  90 */     System.out.println("sun.stdout.encoding= " + System.getProperty("sun.stdout.encoding") + ", sun.stderr.encoding= " + 
/*  91 */         System.getProperty("sun.stderr.encoding"));
/*  92 */     System.out.println("stdout.encoding= " + System.getProperty("stdout.encoding") + ", stderr.encoding= " + 
/*  93 */         System.getProperty("stderr.encoding"));
/*  94 */     System.out.println("java.version= " + System.getProperty("java.version") + ", java.vendor= " + 
/*  95 */         System.getProperty("java.vendor") + ", java.home= " + 
/*  96 */         System.getProperty("java.home"));
/*  97 */     System.out.println("Console: " + System.console());
/*     */     
/*  99 */     System.out.println();
/*     */     
/* 101 */     System.out.println("jansi.graceful= " + System.getProperty("jansi.graceful", ""));
/* 102 */     System.out.println("jansi.mode= " + System.getProperty("jansi.mode", ""));
/* 103 */     System.out.println("jansi.out.mode= " + System.getProperty("jansi.out.mode", ""));
/* 104 */     System.out.println("jansi.err.mode= " + System.getProperty("jansi.err.mode", ""));
/* 105 */     System.out.println("jansi.colors= " + System.getProperty("jansi.colors", ""));
/* 106 */     System.out.println("jansi.out.colors= " + System.getProperty("jansi.out.colors", ""));
/* 107 */     System.out.println("jansi.err.colors= " + System.getProperty("jansi.err.colors", ""));
/* 108 */     System.out.println("jansi.noreset= " + AnsiConsole.getBoolean("jansi.noreset"));
/* 109 */     System.out.println(Ansi.DISABLE + "= " + AnsiConsole.getBoolean(Ansi.DISABLE));
/*     */     
/* 111 */     System.out.println();
/*     */     
/* 113 */     System.out.println("IS_WINDOWS: " + AnsiConsole.IS_WINDOWS);
/* 114 */     if (AnsiConsole.IS_WINDOWS) {
/* 115 */       System.out.println("IS_CONEMU: " + AnsiConsole.IS_CONEMU);
/* 116 */       System.out.println("IS_CYGWIN: " + AnsiConsole.IS_CYGWIN);
/* 117 */       System.out.println("IS_MSYSTEM: " + AnsiConsole.IS_MSYSTEM);
/*     */     } 
/*     */     
/* 120 */     System.out.println();
/*     */     
/* 122 */     diagnoseTty(false);
/* 123 */     diagnoseTty(true);
/*     */     
/* 125 */     AnsiConsole.systemInstall();
/*     */     
/* 127 */     System.out.println();
/*     */     
/* 129 */     System.out.println("Resulting Jansi modes for stout/stderr streams:");
/* 130 */     System.out.println("  - System.out: " + AnsiConsole.out().toString());
/* 131 */     System.out.println("  - System.err: " + AnsiConsole.err().toString());
/* 132 */     System.out.println("Processor types description:");
/* 133 */     for (AnsiType type : AnsiType.values()) {
/* 134 */       System.out.println("  - " + type + ": " + type.getDescription());
/*     */     }
/* 136 */     System.out.println("Colors support description:");
/* 137 */     for (AnsiColors colors : AnsiColors.values()) {
/* 138 */       System.out.println("  - " + colors + ": " + colors.getDescription());
/*     */     }
/* 140 */     System.out.println("Modes description:");
/* 141 */     for (AnsiMode mode : AnsiMode.values()) {
/* 142 */       System.out.println("  - " + mode + ": " + mode.getDescription());
/*     */     }
/*     */     
/*     */     try {
/* 146 */       System.out.println();
/*     */       
/* 148 */       testAnsi(false);
/* 149 */       testAnsi(true);
/*     */       
/* 151 */       if (args.length == 0) {
/* 152 */         printJansiLogoDemo();
/*     */         
/*     */         return;
/*     */       } 
/* 156 */       System.out.println();
/*     */       
/* 158 */       if (args.length == 1) {
/* 159 */         File f = new File(args[0]);
/* 160 */         if (f.exists()) {
/*     */           
/* 162 */           System.out.println(
/* 163 */               Ansi.ansi().bold().a("\"" + args[0] + "\" content:").reset());
/* 164 */           writeFileContent(f);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */       
/* 170 */       System.out.println(Ansi.ansi().bold().a("original args:").reset());
/* 171 */       int i = 1;
/* 172 */       for (String arg : args) {
/* 173 */         AnsiConsole.sysOut().print(i++ + ": ");
/* 174 */         AnsiConsole.sysOut().println(arg);
/*     */       } 
/*     */       
/* 177 */       System.out.println(Ansi.ansi().bold().a("Jansi filtered args:").reset());
/* 178 */       i = 1;
/* 179 */       for (String arg : args) {
/* 180 */         System.out.print(i++ + ": ");
/* 181 */         System.out.println(arg);
/*     */       } 
/*     */     } finally {
/* 184 */       AnsiConsole.systemUninstall();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getJansiVersion() {
/* 189 */     Package p = AnsiMain.class.getPackage();
/* 190 */     return (p == null) ? null : p.getImplementationVersion();
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
/*     */   private static void diagnoseTty(boolean stderr) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void testAnsi(boolean stderr) {
/* 226 */     PrintStream s = stderr ? System.err : System.out;
/* 227 */     s.print("test on System." + (stderr ? "err" : "out") + ":");
/* 228 */     for (Ansi.Color c : Ansi.Color.values()) {
/* 229 */       s.print(" " + Ansi.ansi().fg(c) + c + Ansi.ansi().reset());
/*     */     }
/* 231 */     s.println();
/* 232 */     s.print("            bright:");
/* 233 */     for (Ansi.Color c : Ansi.Color.values()) {
/* 234 */       s.print(" " + Ansi.ansi().fgBright(c) + c + Ansi.ansi().reset());
/*     */     }
/* 236 */     s.println();
/* 237 */     s.print("              bold:");
/* 238 */     for (Ansi.Color c : Ansi.Color.values()) {
/* 239 */       s.print(" " + Ansi.ansi().bold().fg(c) + c + Ansi.ansi().reset());
/*     */     }
/* 241 */     s.println();
/* 242 */     s.print("             faint:");
/* 243 */     for (Ansi.Color c : Ansi.Color.values()) {
/* 244 */       s.print(" " + Ansi.ansi().a(Ansi.Attribute.INTENSITY_FAINT).fg(c) + c + 
/* 245 */           Ansi.ansi().reset());
/*     */     }
/* 247 */     s.println();
/* 248 */     s.print("        bold+faint:");
/* 249 */     for (Ansi.Color c : Ansi.Color.values()) {
/* 250 */       s.print(" " + Ansi.ansi().bold().a(Ansi.Attribute.INTENSITY_FAINT).fg(c) + c + 
/* 251 */           Ansi.ansi().reset());
/*     */     }
/* 253 */     s.println();
/* 254 */     Ansi ansi = Ansi.ansi();
/* 255 */     ansi.a("        256 colors: "); int i;
/* 256 */     for (i = 0; i < 216; i++) {
/* 257 */       if (i > 0 && i % 36 == 0) {
/* 258 */         ansi.reset();
/* 259 */         ansi.newline();
/* 260 */         ansi.a("                    ");
/* 261 */       } else if (i > 0 && i % 6 == 0) {
/* 262 */         ansi.reset();
/* 263 */         ansi.a("  ");
/*     */       } 
/* 265 */       int a0 = i % 6;
/* 266 */       int a1 = i / 6 % 6;
/* 267 */       int a2 = i / 36;
/* 268 */       ansi.bg(16 + a0 + a2 * 6 + a1 * 36).a(' ');
/*     */     } 
/* 270 */     ansi.reset();
/* 271 */     s.println(ansi);
/* 272 */     ansi = Ansi.ansi();
/* 273 */     ansi.a("         truecolor: ");
/* 274 */     for (i = 0; i < 256; i++) {
/* 275 */       if (i > 0 && i % 48 == 0) {
/* 276 */         ansi.reset();
/* 277 */         ansi.newline();
/* 278 */         ansi.a("                    ");
/*     */       } 
/* 280 */       int r = 255 - i;
/* 281 */       int g = (i * 2 > 255) ? (255 - 2 * i) : (2 * i);
/* 282 */       int b = i;
/* 283 */       ansi.bgRgb(r, g, b).fgRgb(255 - r, 255 - g, 255 - b).a((i % 2 == 0) ? 47 : 92);
/*     */     } 
/* 285 */     ansi.reset();
/* 286 */     s.println(ansi);
/*     */   }
/*     */   
/*     */   private static String getPomPropertiesVersion(String path) throws IOException {
/* 290 */     InputStream in = AnsiMain.class.getResourceAsStream("/META-INF/maven/" + path + "/pom.properties");
/* 291 */     if (in == null) {
/* 292 */       return null;
/*     */     }
/*     */     try {
/* 295 */       Properties p = new Properties();
/* 296 */       p.load(in);
/* 297 */       return p.getProperty("version");
/*     */     } finally {
/* 299 */       closeQuietly(in);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void printJansiLogoDemo() throws IOException {
/* 305 */     BufferedReader in = new BufferedReader(new InputStreamReader(AnsiMain.class.getResourceAsStream("jansi.txt"), StandardCharsets.UTF_8));
/*     */     try {
/*     */       String l;
/* 308 */       while ((l = in.readLine()) != null) {
/* 309 */         System.out.println(l);
/*     */       }
/*     */     } finally {
/* 312 */       closeQuietly(in);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void writeFileContent(File f) throws IOException {
/* 317 */     InputStream in = new FileInputStream(f);
/*     */     try {
/* 319 */       byte[] buf = new byte[1024];
/* 320 */       int l = 0;
/* 321 */       while ((l = in.read(buf)) >= 0) {
/* 322 */         System.out.write(buf, 0, l);
/*     */       }
/*     */     } finally {
/* 325 */       closeQuietly(in);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void closeQuietly(Closeable c) {
/*     */     try {
/* 331 */       c.close();
/* 332 */     } catch (IOException ioe) {
/* 333 */       ioe.printStackTrace(System.err);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\jansi\AnsiMain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */