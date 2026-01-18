/*    */ package com.hypixel.hytale.server.core.universe.world.chunk.section;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkSectionReference
/*    */ {
/*    */   private BlockChunk chunk;
/*    */   private BlockSection section;
/*    */   private int sectionIndex;
/*    */   
/*    */   public ChunkSectionReference(BlockChunk chunk, BlockSection section, int sectionIndex) {
/* 16 */     this.section = section;
/* 17 */     this.chunk = chunk;
/* 18 */     this.sectionIndex = sectionIndex;
/*    */   }
/*    */   
/*    */   public BlockChunk getChunk() {
/* 22 */     return this.chunk;
/*    */   }
/*    */   
/*    */   public BlockSection getSection() {
/* 26 */     return this.section;
/*    */   }
/*    */   
/*    */   public int getSectionIndex() {
/* 30 */     return this.sectionIndex;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\section\ChunkSectionReference.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */