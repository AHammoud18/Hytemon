/*    */ package com.hypixel.hytale.server.npc.corecomponents.interaction;
/*    */ 
/*    */ import com.hypixel.hytale.component.Archetype;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.interaction.builders.BuilderSensorInteractionContext;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorInteractionContext
/*    */   extends SensorBase {
/*    */   public SensorInteractionContext(@Nonnull BuilderSensorInteractionContext builder, @Nonnull BuilderSupport support) {
/* 19 */     super((BuilderSensorBase)builder);
/* 20 */     this.interactionContext = builder.getInteractionContext(support);
/*    */   }
/*    */   private final String interactionContext;
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 25 */     if (!super.matches(ref, role, dt, store)) return false;
/*    */     
/* 27 */     Ref<EntityStore> target = role.getStateSupport().getInteractionIterationTarget();
/* 28 */     if (target == null) return false;
/*    */     
/* 30 */     Archetype<EntityStore> targetArchetype = store.getArchetype(target);
/* 31 */     if (targetArchetype.contains(DeathComponent.getComponentType())) return false;
/*    */     
/* 33 */     return role.getStateSupport().hasContextualInteraction(target, this.interactionContext);
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 38 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\interaction\SensorInteractionContext.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */