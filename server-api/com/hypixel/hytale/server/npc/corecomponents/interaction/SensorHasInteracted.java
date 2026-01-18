/*    */ package com.hypixel.hytale.server.npc.corecomponents.interaction;
/*    */ 
/*    */ import com.hypixel.hytale.component.Archetype;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorHasInteracted extends SensorBase {
/*    */   public SensorHasInteracted(@Nonnull BuilderSensorBase builderSensorBase) {
/* 16 */     super(builderSensorBase);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 21 */     if (!super.matches(ref, role, dt, store)) return false;
/*    */     
/* 23 */     Ref<EntityStore> target = role.getStateSupport().getInteractionIterationTarget();
/* 24 */     if (target == null) return false;
/*    */     
/* 26 */     Archetype<EntityStore> targetArchetype = store.getArchetype(target);
/* 27 */     if (targetArchetype.contains(DeathComponent.getComponentType())) return false;
/*    */     
/* 29 */     return role.getStateSupport().consumeInteraction(target);
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 34 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\interaction\SensorHasInteracted.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */