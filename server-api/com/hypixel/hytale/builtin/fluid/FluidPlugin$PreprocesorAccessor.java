/*     */ package com.hypixel.hytale.builtin.fluid;
/*     */ 
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.FluidTicker;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
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
/*     */ public class PreprocesorAccessor
/*     */   implements FluidTicker.Accessor
/*     */ {
/*     */   private final WorldChunk worldChunk;
/*     */   private final BlockChunk blockChunk;
/*     */   private final Holder<ChunkStore>[] sections;
/*     */   public long tick;
/*     */   public BlockSection blockSection;
/*     */   
/*     */   public PreprocesorAccessor(WorldChunk worldChunk, BlockChunk blockChunk, Holder<ChunkStore>[] sections) {
/* 182 */     this.worldChunk = worldChunk;
/* 183 */     this.blockChunk = blockChunk;
/* 184 */     this.sections = sections;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public FluidSection getFluidSection(int cx, int cy, int cz) {
/* 190 */     if (this.blockChunk.getX() == cx && this.blockChunk.getZ() == cz && cy >= 0 && cy < this.sections.length) {
/* 191 */       return (FluidSection)this.sections[cy].getComponent(FluidSection.getComponentType());
/*     */     }
/* 193 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockSection getBlockSection(int cx, int cy, int cz) {
/* 199 */     if (cy < 0 || cy >= 10) return null; 
/* 200 */     if (this.blockChunk.getX() == cx && this.blockChunk.getZ() == cz) {
/* 201 */       return this.blockChunk.getSectionAtIndex(cy);
/*     */     }
/* 203 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlock(int x, int y, int z, int blockId) {
/* 208 */     if (this.worldChunk.getX() != ChunkUtil.chunkCoordinate(x) && this.worldChunk.getZ() != ChunkUtil.chunkCoordinate(z))
/* 209 */       return;  this.worldChunk.setBlock(x, y, z, blockId, 157);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\fluid\FluidPlugin$PreprocesorAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */