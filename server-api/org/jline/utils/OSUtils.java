/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.io.File;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OSUtils
/*     */ {
/*  49 */   public static final boolean IS_LINUX = System.getProperty("os.name").toLowerCase().contains("linux");
/*     */ 
/*     */   
/*  52 */   public static final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().contains("win");
/*     */ 
/*     */   
/*  55 */   public static final boolean IS_OSX = System.getProperty("os.name").toLowerCase().contains("mac");
/*     */ 
/*     */   
/*  58 */   public static final boolean IS_AIX = System.getProperty("os.name").toLowerCase().contains("aix");
/*     */   
/*  60 */   public static final boolean IS_CYGWIN = (IS_WINDOWS && 
/*  61 */     System.getenv("PWD") != null && System.getenv("PWD").startsWith("/"));
/*     */   
/*     */   @Deprecated
/*  64 */   public static final boolean IS_MINGW = (IS_WINDOWS && 
/*  65 */     System.getenv("MSYSTEM") != null && 
/*  66 */     System.getenv("MSYSTEM").startsWith("MINGW"));
/*     */   
/*  68 */   public static final boolean IS_MSYSTEM = (IS_WINDOWS && 
/*  69 */     System.getenv("MSYSTEM") != null && (
/*  70 */     System.getenv("MSYSTEM").startsWith("MINGW") || 
/*  71 */     System.getenv("MSYSTEM").equals("MSYS")));
/*     */   
/*  73 */   public static final boolean IS_WSL = (System.getenv("WSL_DISTRO_NAME") != null);
/*     */   
/*  75 */   public static final boolean IS_WSL1 = (IS_WSL && System.getenv("WSL_INTEROP") == null);
/*     */   
/*  77 */   public static final boolean IS_WSL2 = (IS_WSL && !IS_WSL1);
/*     */   
/*  79 */   public static final boolean IS_CONEMU = (IS_WINDOWS && System.getenv("ConEmuPID") != null);
/*     */   
/*     */   public static String TTY_COMMAND;
/*     */   public static String STTY_COMMAND;
/*     */   public static String STTY_F_OPTION;
/*     */   public static String INFOCMP_COMMAND;
/*     */   public static String TEST_COMMAND;
/*     */   
/*     */   private static boolean isExecutable(File f) {
/*  88 */     return (f.canExecute() && !f.isDirectory());
/*     */   }
/*     */   
/*     */   static {
/*  92 */     boolean cygwinOrMsys = (IS_CYGWIN || IS_MSYSTEM);
/*  93 */     String suffix = cygwinOrMsys ? ".exe" : "";
/*  94 */     String tty = null;
/*  95 */     String stty = null;
/*  96 */     String sttyfopt = null;
/*  97 */     String infocmp = null;
/*  98 */     String test = null;
/*  99 */     String path = System.getenv("PATH");
/* 100 */     if (path != null) {
/* 101 */       String[] paths = path.split(File.pathSeparator);
/* 102 */       for (String p : paths) {
/* 103 */         File ttyFile = new File(p, "tty" + suffix);
/* 104 */         if (tty == null && isExecutable(ttyFile)) {
/* 105 */           tty = ttyFile.getAbsolutePath();
/*     */         }
/* 107 */         File sttyFile = new File(p, "stty" + suffix);
/* 108 */         if (stty == null && isExecutable(sttyFile)) {
/* 109 */           stty = sttyFile.getAbsolutePath();
/*     */         }
/* 111 */         File infocmpFile = new File(p, "infocmp" + suffix);
/* 112 */         if (infocmp == null && isExecutable(infocmpFile)) {
/* 113 */           infocmp = infocmpFile.getAbsolutePath();
/*     */         }
/* 115 */         File testFile = new File(p, "test" + suffix);
/* 116 */         if (test == null && isExecutable(testFile)) {
/* 117 */           test = testFile.getAbsolutePath();
/*     */         }
/*     */       } 
/*     */     } 
/* 121 */     if (tty == null) {
/* 122 */       tty = "tty" + suffix;
/*     */     }
/* 124 */     if (stty == null) {
/* 125 */       stty = "stty" + suffix;
/*     */     }
/* 127 */     if (infocmp == null) {
/* 128 */       infocmp = "infocmp" + suffix;
/*     */     }
/* 130 */     if (test == null) {
/* 131 */       test = "test" + suffix;
/*     */     }
/* 133 */     sttyfopt = IS_OSX ? "-f" : "-F";
/* 134 */     TTY_COMMAND = tty;
/* 135 */     STTY_COMMAND = stty;
/* 136 */     STTY_F_OPTION = sttyfopt;
/* 137 */     INFOCMP_COMMAND = infocmp;
/* 138 */     TEST_COMMAND = test;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\OSUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */