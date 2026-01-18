/*    */ package com.hypixel.hytale.server.worldgen;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedResource;
/*    */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*    */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGenerator;
/*    */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabLoader;
/*    */ import com.hypixel.hytale.server.worldgen.loader.prefab.BlockPlacementMaskRegistry;
/*    */ import com.hypixel.hytale.server.worldgen.loader.util.FileMaskCache;
/*    */ import com.hypixel.hytale.server.worldgen.prefab.PrefabStoreRoot;
/*    */ import com.hypixel.hytale.server.worldgen.util.LogUtil;
/*    */ import java.nio.file.Path;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SeedStringResource
/*    */   implements SeedResource
/*    */ {
/*    */   @Nonnull
/*    */   protected final FileMaskCache<IIntCondition> biomeMaskRegistry;
/*    */   @Nonnull
/*    */   protected final BlockPlacementMaskRegistry blockMaskRegistry;
/*    */   @Nonnull
/*    */   protected Path dataFolder;
/*    */   @Nonnull
/*    */   protected WorldGenPrefabLoader loader;
/*    */   
/*    */   public SeedStringResource(@Nonnull PrefabStoreRoot prefabStore, @Nonnull Path dataFolder) {
/* 32 */     this.dataFolder = dataFolder;
/* 33 */     this.loader = new WorldGenPrefabLoader(prefabStore, dataFolder);
/* 34 */     this.biomeMaskRegistry = new FileMaskCache();
/* 35 */     this.blockMaskRegistry = new BlockPlacementMaskRegistry();
/*    */   }
/*    */   
/*    */   public WorldGenPrefabLoader getLoader() {
/* 39 */     return this.loader;
/*    */   }
/*    */   
/*    */   public void setPrefabStore(@Nonnull PrefabStoreRoot prefabStore) {
/* 43 */     if (prefabStore != this.loader.getStore()) {
/* 44 */       LogUtil.getLogger().at(Level.INFO).log("Set prefab-store to: %s", prefabStore.name());
/* 45 */       this.loader = new WorldGenPrefabLoader(prefabStore, this.dataFolder);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setDataFolder(@Nonnull Path dataFolder) {
/* 50 */     if (!dataFolder.equals(this.dataFolder)) {
/* 51 */       LogUtil.getLogger().at(Level.INFO).log("Set data-folder to: %s", dataFolder);
/* 52 */       this.dataFolder = dataFolder;
/* 53 */       this.loader = new WorldGenPrefabLoader(this.loader.getStore(), dataFolder);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ResultBuffer.Bounds2d localBounds2d() {
/* 60 */     return (ChunkGenerator.getResource()).bounds2d;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ResultBuffer.ResultBuffer2d localBuffer2d() {
/* 66 */     return (ChunkGenerator.getResource()).resultBuffer2d;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ResultBuffer.ResultBuffer3d localBuffer3d() {
/* 72 */     return (ChunkGenerator.getResource()).resultBuffer3d;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeSeedReport(String seedReport) {
/* 77 */     LogUtil.getLogger().at(Level.FINE).log(seedReport);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public FileMaskCache<IIntCondition> getBiomeMaskRegistry() {
/* 82 */     return this.biomeMaskRegistry;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public BlockPlacementMaskRegistry getBlockMaskRegistry() {
/* 87 */     return this.blockMaskRegistry;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\SeedStringResource.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */