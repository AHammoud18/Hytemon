/*     */ package com.hypixel.hytale.server.core.command.commands.debug;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAsset;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetRegistry;
/*     */ import com.hypixel.hytale.server.core.asset.common.asset.FileCommonAsset;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class DuplicatedAssetInfo
/*     */ {
/*     */   @Nonnull
/*     */   public static final Comparator<DuplicatedAssetInfo> COMPARATOR;
/*     */   
/*     */   static {
/*  82 */     COMPARATOR = Comparator.comparingLong(o -> o.wastedSpace);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  88 */   public static final Comparator<DuplicatedAssetInfo> COMPARATOR_REVERSE = Collections.reverseOrder(COMPARATOR);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   final String hash;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   final List<CommonAssetRegistry.PackAsset> assets;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long wastedSpace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DuplicatedAssetInfo(@Nonnull String hash, @Nonnull List<CommonAssetRegistry.PackAsset> assets) {
/* 114 */     this.hash = hash;
/* 115 */     this.assets = assets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CompletableFuture<Void> calculateTotalSize() {
/* 125 */     CommonAsset commonAsset = ((CommonAssetRegistry.PackAsset)this.assets.getFirst()).asset();
/* 126 */     if (commonAsset instanceof FileCommonAsset) { FileCommonAsset fileCommonAsset = (FileCommonAsset)commonAsset;
/* 127 */       Path path = fileCommonAsset.getFile();
/* 128 */       return CompletableFuture.runAsync(SneakyThrow.sneakyRunnable(() -> this.wastedSpace = Files.size(path) * (this.assets.size() - 1))); }
/*     */     
/* 130 */     return commonAsset.getBlob().thenAccept(bytes -> this.wastedSpace = bytes.length * (this.assets.size() - 1));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\AssetsDuplicatesCommand$DuplicatedAssetInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */