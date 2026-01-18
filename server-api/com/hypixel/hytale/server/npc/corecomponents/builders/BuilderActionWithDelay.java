/*    */ package com.hypixel.hytale.server.npc.corecomponents.builders;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.Builder;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.holder.NumberArrayHolder;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleArrayValidator;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleSequenceValidator;
/*    */ import com.hypixel.hytale.server.npc.instructions.Action;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BuilderActionWithDelay
/*    */   extends BuilderActionBase
/*    */ {
/* 18 */   public static final double[] DEFAULT_TIMEOUT_RANGE = new double[] { 1.0D, 1.0D };
/*    */   
/* 20 */   protected final NumberArrayHolder delayRange = new NumberArrayHolder();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Builder<Action> readCommonConfig(@Nonnull JsonElement data) {
/* 25 */     super.readCommonConfig(data);
/* 26 */     getDoubleRange(data, "Delay", this.delayRange, getDefaultTimeoutRange(), (DoubleArrayValidator)DoubleSequenceValidator.betweenWeaklyMonotonic(0.0D, Double.MAX_VALUE), BuilderDescriptorState.Stable, "Range of time to delay in seconds", null);
/* 27 */     return (Builder<Action>)this;
/*    */   }
/*    */   
/*    */   public double[] getDelayRange(@Nonnull BuilderSupport support) {
/* 31 */     return this.delayRange.get(support.getExecutionContext());
/*    */   }
/*    */ 
/*    */   
/*    */   protected double[] getDefaultTimeoutRange() {
/* 36 */     return DEFAULT_TIMEOUT_RANGE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\builders\BuilderActionWithDelay.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */