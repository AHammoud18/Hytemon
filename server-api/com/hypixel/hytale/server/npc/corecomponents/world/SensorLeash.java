/*    */ package com.hypixel.hytale.server.npc.corecomponents.world;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorLeash;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.PositionProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SensorLeash
/*    */   extends SensorBase
/*    */ {
/*    */   protected final double range;
/*    */   protected final double rangeSq;
/* 25 */   protected final PositionProvider positionProvider = new PositionProvider();
/*    */   
/*    */   public SensorLeash(@Nonnull BuilderSensorLeash builderSensorLeash, @Nonnull BuilderSupport builderSupport) {
/* 28 */     super((BuilderSensorBase)builderSensorLeash);
/* 29 */     this.range = builderSensorLeash.getRange(builderSupport);
/* 30 */     this.rangeSq = this.range * this.range;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 35 */     if (!super.matches(ref, role, dt, store)) return false;
/*    */     
/* 37 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 38 */     assert transformComponent != null;
/*    */     
/* 40 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/* 41 */     Vector3d leashPoint = npcComponent.getLeashPoint();
/*    */     
/* 43 */     if (transformComponent.getPosition().distanceSquaredTo(leashPoint) > this.rangeSq) {
/* 44 */       this.positionProvider.setTarget(leashPoint);
/* 45 */       return true;
/*    */     } 
/*    */     
/* 48 */     this.positionProvider.clear();
/* 49 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 54 */     return (InfoProvider)this.positionProvider;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\SensorLeash.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */