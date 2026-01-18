/*    */ package com.hypixel.hytale.server.npc.corecomponents.world.builders;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.Builder;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.Feature;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.holder.DoubleHolder;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.holder.EnumHolder;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.holder.StringHolder;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleSingleValidator;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleValidator;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.StringNullOrNotEmptyValidator;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.StringValidator;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.world.SensorEvent;
/*    */ import com.hypixel.hytale.server.npc.instructions.Sensor;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class BuilderSensorEvent extends BuilderSensorBase {
/* 21 */   protected final DoubleHolder range = new DoubleHolder();
/* 22 */   protected final EnumHolder<SensorEvent.EventSearchType> searchType = new EnumHolder();
/* 23 */   protected final StringHolder lockOnTargetSlot = new StringHolder();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Builder<Sensor> readConfig(@Nonnull JsonElement data) {
/* 28 */     requireDouble(data, "Range", this.range, (DoubleValidator)DoubleSingleValidator.greater0(), BuilderDescriptorState.Stable, "Max range to listen in", null);
/* 29 */     getEnum(data, "SearchType", this.searchType, SensorEvent.EventSearchType.class, (Enum)SensorEvent.EventSearchType.PlayerOnly, BuilderDescriptorState.Stable, "Whether to listen for events triggered by players, npcs, or both in a certain order", null);
/* 30 */     getString(data, "TargetSlot", this.lockOnTargetSlot, null, (StringValidator)StringNullOrNotEmptyValidator.get(), BuilderDescriptorState.Stable, "A target slot to place the target in. If omitted, no slot will be used", null);
/* 31 */     provideFeature(Feature.LiveEntity);
/* 32 */     return (Builder<Sensor>)this;
/*    */   }
/*    */   
/*    */   public double getRange(@Nonnull BuilderSupport support) {
/* 36 */     return this.range.get(support.getExecutionContext());
/*    */   }
/*    */   
/*    */   public SensorEvent.EventSearchType getEventSearchType(@Nonnull BuilderSupport support) {
/* 40 */     return (SensorEvent.EventSearchType)this.searchType.get(support.getExecutionContext());
/*    */   }
/*    */   
/*    */   public int getLockOnTargetSlot(@Nonnull BuilderSupport support) {
/* 44 */     String slot = this.lockOnTargetSlot.get(support.getExecutionContext());
/* 45 */     if (slot == null) return Integer.MIN_VALUE;
/*    */     
/* 47 */     return support.getTargetSlot(slot);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\builders\BuilderSensorEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */