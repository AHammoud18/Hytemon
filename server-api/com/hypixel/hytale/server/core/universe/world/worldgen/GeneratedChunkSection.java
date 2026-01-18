/*     */ package com.hypixel.hytale.server.core.universe.world.worldgen;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.palette.EmptySectionPalette;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.palette.ISectionPalette;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*     */ import java.util.Arrays;
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
/*     */ public class GeneratedChunkSection
/*     */ {
/*     */   @Nonnull
/*  27 */   private final int[] data = new int[32768]; @Nonnull
/*  28 */   private final int[] temp = new int[32768];
/*  29 */   private ISectionPalette fillers = (ISectionPalette)EmptySectionPalette.INSTANCE;
/*  30 */   private ISectionPalette rotations = (ISectionPalette)EmptySectionPalette.INSTANCE;
/*     */ 
/*     */   
/*     */   public int getRotationIndex(int x, int y, int z) {
/*  34 */     return getRotationIndex(ChunkUtil.indexBlock(x, y, z));
/*     */   }
/*     */   
/*     */   private int getRotationIndex(int index) {
/*  38 */     return this.rotations.get(index);
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
/*     */   public int getBlock(int x, int y, int z) {
/*  51 */     return getBlock(ChunkUtil.indexBlock(x, y, z));
/*     */   }
/*     */   
/*     */   public int getFiller(int x, int y, int z) {
/*  55 */     return this.fillers.get(ChunkUtil.indexBlock(x, y, z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getBlock(int index) {
/*  65 */     return this.data[index];
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
/*     */   public void setBlock(int x, int y, int z, int block, int rotation, int filler) {
/*  78 */     setBlock(ChunkUtil.indexBlock(x, y, z), block, rotation, filler);
/*     */   }
/*     */   
/*     */   public void setBlock(int index, int block, int rotation, int filler) {
/*  82 */     this.data[index] = block;
/*  83 */     ISectionPalette.SetResult result = this.fillers.set(index, filler);
/*  84 */     if (result == ISectionPalette.SetResult.REQUIRES_PROMOTE) {
/*  85 */       this.fillers = this.fillers.promote();
/*  86 */       this.fillers.set(index, filler);
/*  87 */     } else if (result == ISectionPalette.SetResult.ADDED_OR_REMOVED && 
/*  88 */       this.fillers.shouldDemote()) {
/*  89 */       this.fillers = this.fillers.demote();
/*     */     } 
/*     */ 
/*     */     
/*  93 */     result = this.rotations.set(index, rotation);
/*  94 */     if (result == ISectionPalette.SetResult.REQUIRES_PROMOTE) {
/*  95 */       this.rotations = this.rotations.promote();
/*  96 */       this.rotations.set(index, rotation);
/*  97 */     } else if (result == ISectionPalette.SetResult.ADDED_OR_REMOVED && 
/*  98 */       this.rotations.shouldDemote()) {
/*  99 */       this.rotations = this.rotations.demote();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getData() {
/* 105 */     return this.data;
/*     */   }
/*     */   
/*     */   public void reset() {
/* 109 */     Arrays.fill(this.data, 0);
/*     */   }
/*     */   
/*     */   public boolean isSolidAir() {
/* 113 */     for (int i = 0; i < this.data.length; i++) {
/* 114 */       if (this.data[i] != 0) return false; 
/*     */     } 
/* 116 */     return true;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BlockSection toChunkSection() {
/* 121 */     System.arraycopy(this.data, 0, this.temp, 0, 32768);
/* 122 */     IntArrays.unstableSort(this.temp);
/*     */     
/* 124 */     int count = 1;
/* 125 */     for (int i = 1; i < 32768; i++) {
/* 126 */       if (this.temp[i] != this.temp[i - 1]) {
/* 127 */         this.temp[count++] = this.temp[i];
/*     */       }
/*     */     } 
/*     */     
/* 131 */     return new BlockSection(ISectionPalette.from(this.data, this.temp, count), this.fillers, this.rotations);
/*     */   }
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 135 */     for (int i = 0; i < 32768; i++) {
/* 136 */       buf.writeInt(this.data[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   public void deserialize(@Nonnull ByteBuf buf, int version) {
/* 141 */     int[] blocks = new int[32768];
/* 142 */     for (int i = 0; i < blocks.length; i++)
/* 143 */       blocks[i] = buf.readInt(); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldgen\GeneratedChunkSection.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */