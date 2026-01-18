/*     */ package com.hypixel.hytale.server.npc.blackboard.view.resource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockReservation
/*     */ {
/*     */   private final int sectionIndex;
/*     */   private final int blockIndex;
/*     */   
/*     */   public BlockReservation(int sectionIndex, int blockIndex) {
/*  97 */     this.sectionIndex = sectionIndex;
/*  98 */     this.blockIndex = blockIndex;
/*     */   }
/*     */   
/*     */   public int getSectionIndex() {
/* 102 */     return this.sectionIndex;
/*     */   }
/*     */   
/*     */   public int getBlockIndex() {
/* 106 */     return this.blockIndex;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\blackboard\view\resource\ResourceView$BlockReservation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */