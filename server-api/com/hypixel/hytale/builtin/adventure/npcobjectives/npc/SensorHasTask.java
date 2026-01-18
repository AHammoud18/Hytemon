/*    */ package com.hypixel.hytale.builtin.adventure.npcobjectives.npc;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.npcobjectives.NPCObjectivesPlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.npcobjectives.npc.builders.BuilderSensorHasTask;
/*    */ import com.hypixel.hytale.component.Archetype;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.role.support.EntitySupport;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class SensorHasTask extends SensorBase {
/*    */   public SensorHasTask(@Nonnull BuilderSensorHasTask builder, @Nonnull BuilderSupport support) {
/* 23 */     super((BuilderSensorBase)builder);
/* 24 */     this.tasksById = builder.getTasksById(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 29 */     if (!super.matches(ref, role, dt, store)) return false;
/*    */     
/* 31 */     Ref<EntityStore> target = role.getStateSupport().getInteractionIterationTarget();
/* 32 */     if (target == null) return false;
/*    */     
/* 34 */     Archetype<EntityStore> targetArchetype = store.getArchetype(target);
/* 35 */     if (targetArchetype.contains(DeathComponent.getComponentType())) return false;
/*    */     
/* 37 */     UUIDComponent targetUuidComponent = (UUIDComponent)store.getComponent(target, UUIDComponent.getComponentType());
/* 38 */     assert targetUuidComponent != null;
/*    */     
/* 40 */     UUID targetUuid = targetUuidComponent.getUuid();
/*    */     
/* 42 */     UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType());
/* 43 */     assert uuidComponent != null;
/*    */     
/* 45 */     UUID uuid = uuidComponent.getUuid();
/* 46 */     NPCObjectivesPlugin objectiveSystem = NPCObjectivesPlugin.get();
/* 47 */     EntitySupport entitySupport = role.getEntitySupport();
/*    */     
/* 49 */     boolean match = false;
/* 50 */     for (String taskById : this.tasksById) {
/* 51 */       if (NPCObjectivesPlugin.hasTask(targetUuid, uuid, taskById)) {
/* 52 */         match = true;
/* 53 */         entitySupport.addTargetPlayerActiveTask(taskById);
/*    */       } 
/*    */     } 
/* 56 */     return match;
/*    */   }
/*    */   @Nullable
/*    */   protected final String[] tasksById;
/*    */   public InfoProvider getSensorInfo() {
/* 61 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcobjectives\npc\SensorHasTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */