/*    */ package com.hypixel.hytale.server.core.universe.world.chunk.section.blockpositions;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkSectionReference;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockPositionData
/*    */   implements IBlockPositionData
/*    */ {
/*    */   private static final double HALF_BLOCK = 0.5D;
/*    */   private int blockIndex;
/*    */   private ChunkSectionReference section;
/*    */   private int blockType;
/*    */   
/*    */   public BlockPositionData(int blockIndex, ChunkSectionReference section, int blockType) {
/* 21 */     this.blockIndex = blockIndex;
/* 22 */     this.section = section;
/* 23 */     this.blockType = blockType;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockSection getChunkSection() {
/* 28 */     return this.section.getSection();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlockType() {
/* 33 */     return this.blockType;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getX() {
/* 38 */     return ChunkUtil.xFromIndex(this.blockIndex) + (this.section.getChunk().getX() << 5);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getY() {
/* 43 */     return ChunkUtil.yFromIndex(this.blockIndex) + (this.section.getSectionIndex() << 5);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getZ() {
/* 48 */     return ChunkUtil.zFromIndex(this.blockIndex) + (this.section.getChunk().getZ() << 5);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getXCentre() {
/* 53 */     return getX() + 0.5D;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getYCentre() {
/* 58 */     return getY() + 0.5D;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getZCentre() {
/* 63 */     return getZ() + 0.5D;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\section\blockpositions\BlockPositionData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */