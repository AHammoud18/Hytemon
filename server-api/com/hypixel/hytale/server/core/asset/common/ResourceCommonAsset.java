/*    */ package com.hypixel.hytale.server.core.asset.common;
/*    */ 
/*    */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResourceCommonAsset
/*    */   extends CommonAsset
/*    */ {
/*    */   private final Class<?> clazz;
/*    */   private final String path;
/*    */   
/*    */   public ResourceCommonAsset(Class<?> clazz, String path, @Nonnull String name, byte[] bytes) {
/* 21 */     super(name, bytes);
/* 22 */     this.clazz = clazz;
/* 23 */     this.path = path;
/*    */   }
/*    */   
/*    */   public ResourceCommonAsset(Class<?> clazz, String path, @Nonnull String name, @Nonnull String hash, byte[] bytes) {
/* 27 */     super(name, hash, bytes);
/* 28 */     this.clazz = clazz;
/* 29 */     this.path = path;
/*    */   }
/*    */   
/*    */   public String getPath() {
/* 33 */     return this.path;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public CompletableFuture<byte[]> getBlob0() {
/*    */     
/* 39 */     try { InputStream stream = this.clazz.getResourceAsStream(this.path); 
/* 40 */       try { CompletableFuture<byte> completableFuture = CompletableFuture.completedFuture(stream.readAllBytes());
/* 41 */         if (stream != null) stream.close();  return (CompletableFuture)completableFuture; } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 42 */     { return (CompletableFuture)CompletableFuture.failedFuture(e); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 49 */     return "ResourceCommonAsset{" + super.toString() + "}";
/*    */   }
/*    */   @Nullable
/*    */   public static ResourceCommonAsset of(@Nonnull Class<?> clazz, @Nonnull String path, @Nonnull String name) {
/*    */     
/* 54 */     try { InputStream stream = clazz.getResourceAsStream(path); 
/* 55 */       try { if (stream == null) { ResourceCommonAsset resourceCommonAsset1 = null;
/*    */ 
/*    */           
/* 58 */           if (stream != null) stream.close();  return resourceCommonAsset1; }  byte[] bytes = stream.readAllBytes(); ResourceCommonAsset resourceCommonAsset = new ResourceCommonAsset(clazz, path, name, bytes); if (stream != null) stream.close();  return resourceCommonAsset; } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 59 */     { throw SneakyThrow.sneakyThrow(e); }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\common\ResourceCommonAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */