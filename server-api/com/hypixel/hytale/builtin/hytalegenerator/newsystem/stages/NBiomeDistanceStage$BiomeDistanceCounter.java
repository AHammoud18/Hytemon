/*     */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.stages;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.biome.BiomeType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ 
/*     */ class BiomeDistanceCounter
/*     */ {
/*     */   @Nonnull
/* 256 */   final List<NBiomeDistanceStage.BiomeDistanceEntry> entries = new ArrayList<>(3); @Nullable
/* 257 */   NBiomeDistanceStage.BiomeDistanceEntry cachedEntry = null;
/*     */ 
/*     */   
/*     */   boolean isCloserThanCounted(@Nonnull BiomeType biomeType, double distance_voxelGrid) {
/* 261 */     for (NBiomeDistanceStage.BiomeDistanceEntry entry : this.entries) {
/* 262 */       if (entry.biomeType == biomeType) {
/* 263 */         return (distance_voxelGrid < entry.distance_voxelGrid);
/*     */       }
/*     */     } 
/* 266 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   void accountFor(@Nonnull BiomeType biomeType, double distance_voxelGrid) {
/* 271 */     if (this.cachedEntry != null && this.cachedEntry.biomeType == biomeType) {
/* 272 */       if (this.cachedEntry.distance_voxelGrid <= distance_voxelGrid) {
/*     */         return;
/*     */       }
/* 275 */       this.cachedEntry.distance_voxelGrid = distance_voxelGrid;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 280 */     for (NBiomeDistanceStage.BiomeDistanceEntry biomeDistanceEntry : this.entries) {
/* 281 */       if (biomeDistanceEntry.biomeType == biomeType) {
/* 282 */         this.cachedEntry = biomeDistanceEntry;
/* 283 */         if (biomeDistanceEntry.distance_voxelGrid <= distance_voxelGrid) {
/*     */           return;
/*     */         }
/* 286 */         biomeDistanceEntry.distance_voxelGrid = distance_voxelGrid;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 292 */     NBiomeDistanceStage.BiomeDistanceEntry entry = new NBiomeDistanceStage.BiomeDistanceEntry();
/* 293 */     entry.biomeType = biomeType;
/* 294 */     entry.distance_voxelGrid = distance_voxelGrid;
/* 295 */     this.entries.add(entry);
/* 296 */     this.cachedEntry = entry;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\stages\NBiomeDistanceStage$BiomeDistanceCounter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */