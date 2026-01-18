/*    */ package com.hypixel.hytale.server.npc.movement.controllers.builders;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.Builder;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderBaseWithType;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderInfo;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.holder.DoubleHolder;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.holder.FloatHolder;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleRangeValidator;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleSingleValidator;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleValidator;
/*    */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*    */ import com.hypixel.hytale.server.npc.validators.NPCLoadTimeValidationHelper;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BuilderMotionControllerBase
/*    */   extends BuilderBaseWithType<MotionController>
/*    */ {
/*    */   protected float epsilonAngle;
/*    */   protected double epsilonSpeed;
/*    */   protected double forceVelocityDamping;
/* 31 */   protected final DoubleHolder maxHorizontalSpeed = new DoubleHolder();
/* 32 */   protected final DoubleHolder fastHorizontalThreshold = new DoubleHolder();
/*    */   protected double fastHorizontalThresholdRange;
/* 34 */   protected final FloatHolder maxHeadRotationSpeed = new FloatHolder();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Builder<MotionController> readCommonConfig(@Nonnull JsonElement data) {
/* 39 */     super.readCommonConfig(data);
/* 40 */     readTypeKey(data);
/* 41 */     getDouble(data, "EpsilonSpeed", v -> this.epsilonSpeed = v, 1.0E-5D, (DoubleValidator)DoubleSingleValidator.greater0(), BuilderDescriptorState.Experimental, "Minimum speed considered non 0", null);
/* 42 */     getFloat(data, "EpsilonAngle", v -> this.epsilonAngle = v, 3.0F, (DoubleValidator)DoubleSingleValidator.greater0(), BuilderDescriptorState.Experimental, "Minimum angle difference considered non 0 in degrees", null);
/* 43 */     getFloat(data, "MaxHeadRotationSpeed", this.maxHeadRotationSpeed, 360.0D, (DoubleValidator)DoubleRangeValidator.between(0.0D, 360.0D), BuilderDescriptorState.Stable, "Maximum rotation speed of the head in degrees", null);
/* 44 */     getDouble(data, "ForceVelocityDamping", v -> this.forceVelocityDamping = v, 0.5D, (DoubleValidator)DoubleSingleValidator.greater0(), BuilderDescriptorState.Experimental, "Damping of external force/velocity over time", null);
/* 45 */     getDouble(data, "RunThreshold", this.fastHorizontalThreshold, 0.7D, (DoubleValidator)DoubleRangeValidator.between01(), BuilderDescriptorState.WorkInProgress, "Relative threshold when running animation should be used", null);
/* 46 */     getDouble(data, "RunThresholdRange", v -> this.fastHorizontalThresholdRange = v, 0.15D, (DoubleValidator)DoubleRangeValidator.between01(), BuilderDescriptorState.WorkInProgress, "Relative threshold range for switching between running/walking", null);
/* 47 */     return (Builder<MotionController>)this;
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean isEnabled(ExecutionContext context) {
/* 52 */     return true;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public String getIdentifier() {
/* 57 */     BuilderInfo builderInfo = NPCPlugin.get().getBuilderInfo((Builder)this);
/* 58 */     Objects.requireNonNull(builderInfo, "Have builder but can't get builderInfo for it");
/* 59 */     return builderInfo.getKeyName();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validate(String configName, @Nonnull NPCLoadTimeValidationHelper validationHelper, ExecutionContext context, Scope globalScope, @Nonnull List<String> errors) {
/* 64 */     boolean result = super.validate(configName, validationHelper, context, globalScope, errors);
/* 65 */     validationHelper.registerMotionControllerType(getClassType());
/* 66 */     return result;
/*    */   }
/*    */   
/*    */   public float getEpsilonAngle() {
/* 70 */     return 0.017453292F * this.epsilonAngle;
/*    */   }
/*    */   
/*    */   public double getEpsilonSpeed() {
/* 74 */     return this.epsilonSpeed;
/*    */   }
/*    */   
/*    */   public double getForceVelocityDamping() {
/* 78 */     return this.forceVelocityDamping;
/*    */   }
/*    */   
/*    */   public double getMaxHorizontalSpeed(@Nonnull BuilderSupport builderSupport) {
/* 82 */     return this.maxHorizontalSpeed.get(builderSupport.getExecutionContext());
/*    */   }
/*    */   
/*    */   public float getMaxHeadRotationSpeed(@Nonnull BuilderSupport support) {
/* 86 */     return this.maxHeadRotationSpeed.get(support.getExecutionContext()) * 0.017453292F;
/*    */   }
/*    */   
/*    */   public double getFastHorizontalThreshold(@Nonnull BuilderSupport builderSupport) {
/* 90 */     return this.fastHorizontalThreshold.get(builderSupport.getExecutionContext());
/*    */   }
/*    */   
/*    */   public double getFastHorizontalThresholdRange() {
/* 94 */     return this.fastHorizontalThresholdRange;
/*    */   }
/*    */   
/*    */   public abstract Class<? extends MotionController> getClassType();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\movement\controllers\builders\BuilderMotionControllerBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */