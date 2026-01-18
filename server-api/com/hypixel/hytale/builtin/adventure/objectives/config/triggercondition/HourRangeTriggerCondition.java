/*    */ package com.hypixel.hytale.builtin.adventure.objectives.config.triggercondition;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.markers.objectivelocation.ObjectiveLocationMarker;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HourRangeTriggerCondition
/*    */   extends ObjectiveLocationTriggerCondition
/*    */ {
/*    */   public static final BuilderCodec<HourRangeTriggerCondition> CODEC;
/*    */   
/*    */   static {
/* 27 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(HourRangeTriggerCondition.class, HourRangeTriggerCondition::new).append(new KeyedCodec("MinHour", (Codec)Codec.INTEGER), (hourRangeTriggerCondition, integer) -> hourRangeTriggerCondition.minHour = integer.intValue(), hourRangeTriggerCondition -> Integer.valueOf(hourRangeTriggerCondition.minHour)).add()).append(new KeyedCodec("MaxHour", (Codec)Codec.INTEGER), (hourRangeTriggerCondition, integer) -> hourRangeTriggerCondition.maxHour = integer.intValue(), hourRangeTriggerCondition -> Integer.valueOf(hourRangeTriggerCondition.maxHour)).add()).build();
/*    */   }
/* 29 */   protected static final ResourceType<EntityStore, WorldTimeResource> WORLD_TIME_RESOURCE_RESOURCE_TYPE = WorldTimeResource.getResourceType();
/*    */   
/*    */   protected int minHour;
/*    */   
/*    */   protected int maxHour;
/*    */   
/*    */   public boolean isConditionMet(@Nonnull ComponentAccessor<EntityStore> componentAccessor, Ref<EntityStore> ref, ObjectiveLocationMarker objectiveLocationMarker) {
/* 36 */     int currentHour = ((WorldTimeResource)componentAccessor.getResource(WORLD_TIME_RESOURCE_RESOURCE_TYPE)).getCurrentHour();
/* 37 */     if (this.minHour > this.maxHour) {
/* 38 */       return (currentHour >= this.minHour || currentHour < this.maxHour);
/*    */     }
/* 40 */     return (currentHour >= this.minHour && currentHour < this.maxHour);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 47 */     return "HourRangeTriggerCondition{minHour=" + this.minHour + ", maxHour=" + this.maxHour + "} " + super
/*    */ 
/*    */       
/* 50 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\triggercondition\HourRangeTriggerCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */