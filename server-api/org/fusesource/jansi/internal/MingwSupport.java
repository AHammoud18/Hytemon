/*     */ package org.fusesource.jansi.internal;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MingwSupport
/*     */ {
/*     */   private final String sttyCommand;
/*     */   private final String ttyCommand;
/*     */   private final Pattern columnsPatterns;
/*     */   
/*     */   public MingwSupport() {
/*  41 */     String tty = null;
/*  42 */     String stty = null;
/*  43 */     String path = System.getenv("PATH");
/*  44 */     if (path != null) {
/*  45 */       String[] paths = path.split(File.pathSeparator);
/*  46 */       for (String p : paths) {
/*  47 */         File ttyFile = new File(p, "tty.exe");
/*  48 */         if (tty == null && ttyFile.canExecute()) {
/*  49 */           tty = ttyFile.getAbsolutePath();
/*     */         }
/*  51 */         File sttyFile = new File(p, "stty.exe");
/*  52 */         if (stty == null && sttyFile.canExecute()) {
/*  53 */           stty = sttyFile.getAbsolutePath();
/*     */         }
/*     */       } 
/*     */     } 
/*  57 */     if (tty == null) {
/*  58 */       tty = "tty.exe";
/*     */     }
/*  60 */     if (stty == null) {
/*  61 */       stty = "stty.exe";
/*     */     }
/*  63 */     this.ttyCommand = tty;
/*  64 */     this.sttyCommand = stty;
/*     */     
/*  66 */     this.columnsPatterns = Pattern.compile("\\bcolumns\\s+(\\d+)\\b");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getConsoleName(boolean stdout) {
/*     */     try {
/*  73 */       Process p = (new ProcessBuilder(new String[] { this.ttyCommand })).redirectInput(getRedirect(stdout ? FileDescriptor.out : FileDescriptor.err)).start();
/*  74 */       String result = waitAndCapture(p);
/*  75 */       if (p.exitValue() == 0) {
/*  76 */         return result.trim();
/*     */       }
/*  78 */     } catch (Throwable t) {
/*  79 */       if ("java.lang.reflect.InaccessibleObjectException"
/*  80 */         .equals(t.getClass().getName())) {
/*  81 */         System.err.println("MINGW support requires --add-opens java.base/java.lang=ALL-UNNAMED");
/*     */       }
/*     */     } 
/*     */     
/*  85 */     return null;
/*     */   }
/*     */   
/*     */   public int getTerminalWidth(String name) {
/*     */     try {
/*  90 */       Process p = (new ProcessBuilder(new String[] { this.sttyCommand, "-F", name, "-a" })).start();
/*  91 */       String result = waitAndCapture(p);
/*  92 */       if (p.exitValue() != 0) {
/*  93 */         throw new IOException("Error executing '" + this.sttyCommand + "': " + result);
/*     */       }
/*  95 */       Matcher matcher = this.columnsPatterns.matcher(result);
/*  96 */       if (matcher.find()) {
/*  97 */         return Integer.parseInt(matcher.group(1));
/*     */       }
/*  99 */       throw new IOException("Unable to parse columns");
/* 100 */     } catch (Exception e) {
/* 101 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String waitAndCapture(Process p) throws IOException, InterruptedException {
/* 106 */     ByteArrayOutputStream bout = new ByteArrayOutputStream();
/* 107 */     InputStream in = p.getInputStream(); 
/* 108 */     try { InputStream err = p.getErrorStream(); 
/*     */       try { int c;
/* 110 */         while ((c = in.read()) != -1) {
/* 111 */           bout.write(c);
/*     */         }
/* 113 */         while ((c = err.read()) != -1) {
/* 114 */           bout.write(c);
/*     */         }
/* 116 */         p.waitFor();
/* 117 */         if (err != null) err.close();  } catch (Throwable throwable) { if (err != null) try { err.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (in != null) in.close();  } catch (Throwable throwable) { if (in != null)
/* 118 */         try { in.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return bout.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ProcessBuilder.Redirect getRedirect(FileDescriptor fd) throws ReflectiveOperationException {
/* 128 */     Class<?> rpi = Class.forName("java.lang.ProcessBuilder$RedirectPipeImpl");
/* 129 */     Constructor<?> cns = rpi.getDeclaredConstructor(new Class[0]);
/* 130 */     cns.setAccessible(true);
/* 131 */     ProcessBuilder.Redirect input = (ProcessBuilder.Redirect)cns.newInstance(new Object[0]);
/* 132 */     Field f = rpi.getDeclaredField("fd");
/* 133 */     f.setAccessible(true);
/* 134 */     f.set(input, fd);
/* 135 */     return input;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\fusesource\jansi\internal\MingwSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */