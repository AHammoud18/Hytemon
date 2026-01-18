/*    */ package com.hypixel.hytale.server.npc.corecomponents.utility.builders;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.Builder;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderContext;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderObjectArrayHelper;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderObjectListHelper;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.holder.StringHolder;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.ArrayValidator;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.instructions.Sensor;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*    */ import com.hypixel.hytale.server.npc.validators.NPCLoadTimeValidationHelper;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class BuilderSensorMany extends BuilderSensorBase {
/*    */   @Nonnull
/* 22 */   protected BuilderObjectListHelper<Sensor> objectListHelper = new BuilderObjectListHelper(Sensor.class, (BuilderContext)this);
/*    */   
/* 24 */   protected final StringHolder unlockTargetSlot = new StringHolder();
/*    */ 
/*    */   
/*    */   public void registerTags(@Nonnull Set<String> tags) {
/* 28 */     super.registerTags(tags);
/* 29 */     tags.add("logic");
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Builder<Sensor> readConfig(@Nonnull JsonElement data) {
/* 35 */     preventParameterOverride();
/* 36 */     requireArray(data, "Sensors", (BuilderObjectArrayHelper)this.objectListHelper, (ArrayValidator)ArrayNotEmptyValidator.get(), BuilderDescriptorState.Stable, "List of sensors", null, this.validationHelper);
/* 37 */     getString(data, "AutoUnlockTargetSlot", this.unlockTargetSlot, null, (StringValidator)StringNullOrNotEmptyValidator.get(), BuilderDescriptorState.Stable, "A target slot to unlock when sensor doesn't match anymore", null);
/* 38 */     return (Builder<Sensor>)this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean validate(String configName, @Nonnull NPCLoadTimeValidationHelper validationHelper, @Nonnull ExecutionContext context, Scope globalScope, @Nonnull List<String> errors) {
/* 44 */     return super.validate(configName, validationHelper, context, globalScope, errors) & this.objectListHelper
/* 45 */       .validate(configName, validationHelper, this.builderManager, context, globalScope, errors);
/*    */   }
/*    */   
/*    */   public int getAutoUnlockedTargetSlot(@Nonnull BuilderSupport support) {
/* 49 */     String slot = this.unlockTargetSlot.get(support.getExecutionContext());
/* 50 */     if (slot == null) return Integer.MIN_VALUE;
/*    */     
/* 52 */     return support.getTargetSlot(slot);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\builders\BuilderSensorMany.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */