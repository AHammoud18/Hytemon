/*    */ package com.hypixel.hytale.server.npc.corecomponents.builders;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.Builder;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderBase;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.holder.BooleanHolder;
/*    */ import com.hypixel.hytale.server.npc.instructions.Sensor;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*    */ import com.hypixel.hytale.server.npc.validators.NPCLoadTimeValidationHelper;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BuilderSensorBase
/*    */   extends BuilderBase<Sensor>
/*    */ {
/*    */   protected boolean once;
/* 21 */   protected final BooleanHolder enabled = new BooleanHolder();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Builder<Sensor> readCommonConfig(@Nonnull JsonElement data) {
/* 26 */     super.readCommonConfig(data);
/* 27 */     getBoolean(data, "Once", aBoolean -> this.once = aBoolean, false, BuilderDescriptorState.Stable, "Sensor only triggers once", null);
/* 28 */     getBoolean(data, "Enabled", this.enabled, true, BuilderDescriptorState.Stable, "Whether this sensor should be enabled on the NPC", null);
/* 29 */     return (Builder<Sensor>)this;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Class<Sensor> category() {
/* 35 */     return Sensor.class;
/*    */   }
/*    */   
/*    */   public boolean getOnce() {
/* 39 */     return this.once;
/*    */   }
/*    */   
/*    */   public void setOnce(boolean once) {
/* 43 */     this.once = once;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEnabled(ExecutionContext context) {
/* 48 */     return this.enabled.get(context);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validate(String configName, @Nonnull NPCLoadTimeValidationHelper validationHelper, ExecutionContext context, Scope globalScope, @Nonnull List<String> errors) {
/* 53 */     validationHelper.updateParentSensorOnce(this.once);
/* 54 */     return super.validate(configName, validationHelper, context, globalScope, errors);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\builders\BuilderSensorBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */