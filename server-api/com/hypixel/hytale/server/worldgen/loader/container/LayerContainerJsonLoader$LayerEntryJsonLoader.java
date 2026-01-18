/*     */ package com.hypixel.hytale.server.worldgen.loader.container;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.NoiseMaskConditionJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.container.LayerContainer;
/*     */ import com.hypixel.hytale.server.worldgen.loader.util.NoiseBlockArrayJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.util.NoiseBlockArray;
/*     */ import java.nio.file.Path;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class LayerEntryJsonLoader<T extends LayerContainer.LayerEntry>
/*     */   extends JsonLoader<SeedStringResource, T>
/*     */ {
/*     */   public LayerEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 109 */     super(seed.append(".LayerEntry"), dataFolder, json);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected NoiseBlockArray loadBlocks() {
/* 114 */     if (!has("Blocks")) return NoiseBlockArray.EMPTY; 
/* 115 */     return (new NoiseBlockArrayJsonLoader(this.seed, this.dataFolder, get("Blocks")))
/* 116 */       .load();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected ICoordinateCondition loadMapCondition() {
/* 121 */     return (new NoiseMaskConditionJsonLoader(this.seed, this.dataFolder, get("NoiseMask")))
/* 122 */       .load();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\container\LayerContainerJsonLoader$LayerEntryJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */