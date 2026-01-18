/*    */ package com.hypixel.hytale.builtin.adventure.objectives.config.triggercondition;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.markers.objectivelocation.ObjectiveLocationMarker;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ObjectiveLocationTriggerCondition
/*    */ {
/* 16 */   public static final CodecMapCodec<ObjectiveLocationTriggerCondition> CODEC = new CodecMapCodec("Type");
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 21 */     return "ObjectiveLocationTriggerCondition{}";
/*    */   }
/*    */   
/*    */   public abstract boolean isConditionMet(ComponentAccessor<EntityStore> paramComponentAccessor, Ref<EntityStore> paramRef, ObjectiveLocationMarker paramObjectiveLocationMarker);
/*    */   
/*    */   static {
/* 27 */     CODEC.register("HourRange", HourRangeTriggerCondition.class, (Codec)HourRangeTriggerCondition.CODEC);
/* 28 */     CODEC.register("Weather", WeatherTriggerCondition.class, (Codec)WeatherTriggerCondition.CODEC);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\triggercondition\ObjectiveLocationTriggerCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */