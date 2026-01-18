/*     */ package com.hypixel.hytale.server.npc.statetransition.builders;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StateTransitionEdges
/*     */ {
/*     */   private final int priority;
/*     */   private final int[] fromStateIndices;
/*     */   private final int[] toStateIndices;
/*     */   
/*     */   private StateTransitionEdges(@Nonnull BuilderStateTransitionEdges builder) {
/* 108 */     this.priority = builder.priority;
/* 109 */     this.fromStateIndices = builder.fromStateIndices;
/* 110 */     this.toStateIndices = builder.toStateIndices;
/*     */   }
/*     */   
/*     */   public int getPriority() {
/* 114 */     return this.priority;
/*     */   }
/*     */   
/*     */   public int[] getFromStateIndices() {
/* 118 */     return this.fromStateIndices;
/*     */   }
/*     */   
/*     */   public int[] getToStateIndices() {
/* 122 */     return this.toStateIndices;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\statetransition\builders\BuilderStateTransitionEdges$StateTransitionEdges.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */