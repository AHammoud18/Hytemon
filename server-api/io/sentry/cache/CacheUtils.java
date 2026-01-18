/*     */ package io.sentry.cache;
/*     */ 
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.SentryOptions;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.nio.charset.Charset;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CacheUtils
/*     */ {
/*  27 */   private static final Charset UTF_8 = Charset.forName("UTF-8");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> void store(@NotNull SentryOptions options, @NotNull T entity, @NotNull String dirName, @NotNull String fileName) {
/*  34 */     File cacheDir = ensureCacheDir(options, dirName);
/*  35 */     if (cacheDir == null) {
/*  36 */       options.getLogger().log(SentryLevel.INFO, "Cache dir is not set, cannot store in scope cache", new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/*  40 */     File file = new File(cacheDir, fileName); 
/*  41 */     try { OutputStream outputStream = new FileOutputStream(file); 
/*  42 */       try { Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream, UTF_8)); 
/*  43 */         try { options.getSerializer().serialize(entity, writer);
/*  44 */           writer.close(); } catch (Throwable throwable) { try { writer.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  outputStream.close(); } catch (Throwable throwable) { try { outputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable e)
/*  45 */     { options.getLogger().log(SentryLevel.ERROR, e, "Error persisting entity: %s", new Object[] { fileName }); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void delete(@NotNull SentryOptions options, @NotNull String dirName, @NotNull String fileName) {
/*  53 */     File cacheDir = ensureCacheDir(options, dirName);
/*  54 */     if (cacheDir == null) {
/*  55 */       options.getLogger().log(SentryLevel.INFO, "Cache dir is not set, cannot delete from scope cache", new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/*  59 */     File file = new File(cacheDir, fileName);
/*  60 */     options.getLogger().log(SentryLevel.DEBUG, "Deleting %s from scope cache", new Object[] { fileName });
/*  61 */     if (!file.delete()) {
/*  62 */       options.getLogger().log(SentryLevel.INFO, "Failed to delete: %s", new Object[] { file.getAbsolutePath() });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   static <T, R> T read(@NotNull SentryOptions options, @NotNull String dirName, @NotNull String fileName, @NotNull Class<T> clazz, @Nullable JsonDeserializer<R> elementDeserializer) {
/*  72 */     File cacheDir = ensureCacheDir(options, dirName);
/*  73 */     if (cacheDir == null) {
/*  74 */       options.getLogger().log(SentryLevel.INFO, "Cache dir is not set, cannot read from scope cache", new Object[0]);
/*  75 */       return null;
/*     */     } 
/*     */     
/*  78 */     File file = new File(cacheDir, fileName);
/*  79 */     if (file.exists()) { 
/*  80 */       try { Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), UTF_8));
/*     */         
/*  82 */         try { if (elementDeserializer == null)
/*  83 */           { Object object1 = options.getSerializer().deserialize(reader, clazz);
/*     */ 
/*     */ 
/*     */             
/*  87 */             reader.close(); return (T)object1; }  Object object = options.getSerializer().deserializeCollection(reader, clazz, elementDeserializer); reader.close(); return (T)object; } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable e)
/*  88 */       { options.getLogger().log(SentryLevel.ERROR, e, "Error reading entity from scope cache: %s", new Object[] { fileName }); }
/*     */        }
/*     */     else
/*  91 */     { options.getLogger().log(SentryLevel.DEBUG, "No entry stored for %s", new Object[] { fileName }); }
/*     */     
/*  93 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   static File ensureCacheDir(@NotNull SentryOptions options, @NotNull String cacheDirName) {
/*  98 */     String cacheDir = options.getCacheDirPath();
/*  99 */     if (cacheDir == null) {
/* 100 */       return null;
/*     */     }
/* 102 */     File dir = new File(cacheDir, cacheDirName);
/* 103 */     dir.mkdirs();
/* 104 */     return dir;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\cache\CacheUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */