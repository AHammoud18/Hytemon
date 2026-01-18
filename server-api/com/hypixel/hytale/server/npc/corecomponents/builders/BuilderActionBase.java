/*    */ package com.hypixel.hytale.server.npc.corecomponents.builders;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.Builder;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderBase;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
/*    */ import com.hypixel.hytale.server.npc.instructions.Action;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BuilderActionBase
/*    */   extends BuilderBase<Action>
/*    */ {
/*    */   protected boolean once;
/*    */   
/*    */   public boolean canRequireFeature() {
/* 20 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Builder<Action> readCommonConfig(@Nonnull JsonElement data) {
/* 26 */     super.readCommonConfig(data);
/* 27 */     getBoolean(data, "Once", v -> this.once = v, false, BuilderDescriptorState.Stable, "Execute only once", null);
/* 28 */     return (Builder<Action>)this;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public final Class<Action> category() {
/* 34 */     return Action.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean isEnabled(ExecutionContext context) {
/* 39 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isOnce() {
/* 43 */     return this.once;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\builders\BuilderActionBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */