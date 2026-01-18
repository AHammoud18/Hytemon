/*     */ package com.hypixel.hytale.server.npc.statetransition.builders;
/*     */ 
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.instructions.ActionList;
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
/*     */ public class StateTransition
/*     */ {
/*     */   @Nullable
/*     */   private final List<BuilderStateTransitionEdges.StateTransitionEdges> stateTransitionEdges;
/*     */   @Nonnull
/*     */   private final ActionList actions;
/*     */   
/*     */   private StateTransition(@Nonnull BuilderStateTransition builder, @Nonnull BuilderSupport support) {
/* 119 */     this.stateTransitionEdges = builder.getStateTransitionEdges(support);
/* 120 */     this.actions = builder.getActionList(support);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public List<BuilderStateTransitionEdges.StateTransitionEdges> getStateTransitionEdges() {
/* 125 */     return this.stateTransitionEdges;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ActionList getActions() {
/* 130 */     return this.actions;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\statetransition\builders\BuilderStateTransition$StateTransition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */