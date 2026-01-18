/*    */ package com.hypixel.hytale.server.core.universe.world;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public interface WorldConfigProvider
/*    */ {
/*    */   @Nonnull
/*    */   default CompletableFuture<WorldConfig> load(@Nonnull Path savePath, String name) {
/* 13 */     Path oldPath = savePath.resolve("config.bson");
/* 14 */     Path path = savePath.resolve("config.json");
/*    */     
/* 16 */     if (Files.exists(oldPath, new java.nio.file.LinkOption[0]) && !Files.exists(path, new java.nio.file.LinkOption[0])) {
/*    */       try {
/* 18 */         Files.move(oldPath, path, new java.nio.file.CopyOption[0]);
/* 19 */       } catch (IOException iOException) {}
/*    */     }
/*    */ 
/*    */     
/* 23 */     return WorldConfig.load(path);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   default CompletableFuture<Void> save(@Nonnull Path savePath, WorldConfig config, World world) {
/* 28 */     return WorldConfig.save(savePath.resolve("config.json"), config);
/*    */   }
/*    */   
/*    */   public static class Default implements WorldConfigProvider {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\WorldConfigProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */