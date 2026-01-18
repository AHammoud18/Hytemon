/*     */ package io.sentry.util;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public final class FileUtils
/*     */ {
/*     */   public static boolean deleteRecursively(@Nullable File file) {
/*  25 */     if (file == null || !file.exists()) {
/*  26 */       return true;
/*     */     }
/*  28 */     if (file.isFile()) {
/*  29 */       return file.delete();
/*     */     }
/*  31 */     File[] children = file.listFiles();
/*  32 */     if (children == null) return true; 
/*  33 */     for (File f : children) {
/*  34 */       if (!deleteRecursively(f)) return false; 
/*     */     } 
/*  36 */     return file.delete();
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
/*     */   @Nullable
/*     */   public static String readText(@Nullable File file) throws IOException {
/*  49 */     if (file == null || !file.exists() || !file.isFile() || !file.canRead()) {
/*  50 */       return null;
/*     */     }
/*  52 */     StringBuilder contentBuilder = new StringBuilder();
/*  53 */     BufferedReader br = new BufferedReader(new FileReader(file));
/*     */ 
/*     */     
/*     */     try { String line;
/*  57 */       if ((line = br.readLine()) != null) {
/*  58 */         contentBuilder.append(line);
/*     */       }
/*  60 */       while ((line = br.readLine()) != null) {
/*  61 */         contentBuilder.append("\n").append(line);
/*     */       }
/*  63 */       br.close(); } catch (Throwable throwable) { try { br.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/*  64 */      return contentBuilder.toString();
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
/*     */   public static byte[] readBytesFromFile(String pathname, long maxFileLength) throws IOException, SecurityException {
/*  78 */     File file = new File(pathname);
/*     */     
/*  80 */     if (!file.exists()) {
/*  81 */       throw new IOException(String.format("File '%s' doesn't exists", new Object[] { file.getName() }));
/*     */     }
/*     */     
/*  84 */     if (!file.isFile()) {
/*  85 */       throw new IOException(
/*  86 */           String.format("Reading path %s failed, because it's not a file.", new Object[] { pathname }));
/*     */     }
/*     */     
/*  89 */     if (!file.canRead()) {
/*  90 */       throw new IOException(
/*  91 */           String.format("Reading the item %s failed, because can't read the file.", new Object[] { pathname }));
/*     */     }
/*     */     
/*  94 */     if (file.length() > maxFileLength) {
/*  95 */       throw new IOException(
/*  96 */           String.format("Reading file failed, because size located at '%s' with %d bytes is bigger than the maximum allowed size of %d bytes.", new Object[] {
/*     */ 
/*     */               
/*  99 */               pathname, Long.valueOf(file.length()), Long.valueOf(maxFileLength)
/*     */             }));
/*     */     }
/* 102 */     FileInputStream fileInputStream = new FileInputStream(pathname); try {
/* 103 */       BufferedInputStream inputStream = new BufferedInputStream(fileInputStream); try {
/* 104 */         ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); try {
/* 105 */           byte[] bytes = new byte[1024];
/*     */           
/* 107 */           int offset = 0; int length;
/* 108 */           while ((length = inputStream.read(bytes)) != -1) {
/* 109 */             outputStream.write(bytes, offset, length);
/*     */           }
/* 111 */           byte[] arrayOfByte1 = outputStream.toByteArray();
/* 112 */           outputStream.close(); inputStream.close(); fileInputStream.close();
/*     */           return arrayOfByte1;
/*     */         } catch (Throwable throwable) {
/*     */           try {
/*     */             outputStream.close();
/*     */           } catch (Throwable throwable1) {
/*     */             throwable.addSuppressed(throwable1);
/*     */           } 
/*     */           throw throwable;
/*     */         } 
/*     */       } catch (Throwable throwable) {
/*     */         try {
/*     */           inputStream.close();
/*     */         } catch (Throwable throwable1) {
/*     */           throwable.addSuppressed(throwable1);
/*     */         } 
/*     */         throw throwable;
/*     */       } 
/*     */     } catch (Throwable throwable) {
/*     */       try {
/*     */         fileInputStream.close();
/*     */       } catch (Throwable throwable1) {
/*     */         throwable.addSuppressed(throwable1);
/*     */       } 
/*     */       throw throwable;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\FileUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */