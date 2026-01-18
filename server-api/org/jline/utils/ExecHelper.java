/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ExecHelper
/*     */ {
/*     */   public static String exec(boolean redirectInput, String... cmd) throws IOException {
/*  51 */     Objects.requireNonNull(cmd);
/*     */     try {
/*  53 */       Log.trace(new Object[] { "Running: ", cmd });
/*  54 */       ProcessBuilder pb = new ProcessBuilder(cmd);
/*  55 */       if (OSUtils.IS_AIX) {
/*  56 */         Map<String, String> env = pb.environment();
/*  57 */         env.put("PATH", "/opt/freeware/bin:" + (String)env.get("PATH"));
/*  58 */         env.put("LANG", "C");
/*  59 */         env.put("LC_ALL", "C");
/*     */       } 
/*  61 */       if (redirectInput) {
/*  62 */         pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
/*     */       }
/*  64 */       Process p = pb.start();
/*  65 */       String result = waitAndCapture(p);
/*  66 */       Log.trace(new Object[] { "Result: ", result });
/*  67 */       if (p.exitValue() != 0) {
/*  68 */         if (result.endsWith("\n")) {
/*  69 */           result = result.substring(0, result.length() - 1);
/*     */         }
/*  71 */         throw new IOException("Error executing '" + String.join(" ", (CharSequence[])cmd) + "': " + result);
/*     */       } 
/*  73 */       return result;
/*  74 */     } catch (InterruptedException e) {
/*  75 */       throw (IOException)(new InterruptedIOException("Command interrupted")).initCause(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String waitAndCapture(Process p) throws IOException, InterruptedException {
/*  80 */     ByteArrayOutputStream bout = new ByteArrayOutputStream();
/*  81 */     InputStream in = null;
/*  82 */     InputStream err = null;
/*  83 */     OutputStream out = null;
/*     */     
/*     */     try {
/*  86 */       in = p.getInputStream(); int c;
/*  87 */       while ((c = in.read()) != -1) {
/*  88 */         bout.write(c);
/*     */       }
/*  90 */       err = p.getErrorStream();
/*  91 */       while ((c = err.read()) != -1) {
/*  92 */         bout.write(c);
/*     */       }
/*  94 */       out = p.getOutputStream();
/*  95 */       p.waitFor();
/*     */     } finally {
/*  97 */       close(new Closeable[] { in, out, err });
/*     */     } 
/*     */     
/* 100 */     return bout.toString();
/*     */   }
/*     */   
/*     */   private static void close(Closeable... closeables) {
/* 104 */     for (Closeable c : closeables) {
/* 105 */       if (c != null)
/*     */         try {
/* 107 */           c.close();
/* 108 */         } catch (Exception exception) {} 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\ExecHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */