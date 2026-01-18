/*    */ package com.hypixel.hytale.server.core.asset.common.asset;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.asset.common.CommonAsset;
/*    */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class FileCommonAsset extends CommonAsset {
/*    */   private final Path file;
/*    */   
/*    */   public FileCommonAsset(Path file, @Nonnull String name, byte[] bytes) {
/* 15 */     super(name, bytes);
/* 16 */     this.file = file;
/*    */   }
/*    */   
/*    */   public FileCommonAsset(Path file, @Nonnull String name, @Nonnull String hash, byte[] bytes) {
/* 20 */     super(name, hash, bytes);
/* 21 */     this.file = file;
/*    */   }
/*    */   
/*    */   public Path getFile() {
/* 25 */     return this.file;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CompletableFuture<byte[]> getBlob0() {
/* 31 */     return (CompletableFuture)CompletableFuture.supplyAsync(SneakyThrow.sneakySupplier(() -> Files.readAllBytes(this.file)));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 37 */     return "FileCommonAsset{file=" + String.valueOf(this.file) + ", " + super
/*    */       
/* 39 */       .toString() + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\common\asset\FileCommonAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */