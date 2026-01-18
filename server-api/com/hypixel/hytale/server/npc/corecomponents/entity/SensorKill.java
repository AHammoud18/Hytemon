/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderSensorKill;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.PositionProvider;
/*    */ import com.hypixel.hytale.server.npc.util.DamageData;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorKill extends SensorBase {
/* 19 */   protected final PositionProvider positionProvider = new PositionProvider(); protected final int targetSlot;
/*    */   
/*    */   public SensorKill(@Nonnull BuilderSensorKill builder, @Nonnull BuilderSupport support) {
/* 22 */     super((BuilderSensorBase)builder);
/* 23 */     this.targetSlot = builder.getTargetSlot(support);
/*    */   }
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/*    */     Ref<EntityStore> targetRef;
/* 28 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/* 29 */     assert npcComponent != null;
/*    */     
/* 31 */     DamageData damageData = npcComponent.getDamageData();
/* 32 */     if (!super.matches(ref, role, dt, store) || !damageData.haveKill()) {
/* 33 */       this.positionProvider.clear();
/* 34 */       return false;
/*    */     } 
/*    */ 
/*    */     
/* 38 */     if (this.targetSlot >= 0) {
/* 39 */       targetRef = role.getMarkedEntitySupport().getMarkedEntityRef(this.targetSlot);
/* 40 */       if (targetRef == null || !damageData.haveKilled(targetRef)) {
/* 41 */         this.positionProvider.clear();
/* 42 */         return false;
/*    */       } 
/*    */     } else {
/* 45 */       targetRef = damageData.getAnyKilled();
/*    */     } 
/*    */     
/* 48 */     if (targetRef == null) {
/* 49 */       this.positionProvider.clear();
/* 50 */       return false;
/*    */     } 
/*    */     
/* 53 */     Vector3d killPosition = damageData.getKillPosition(targetRef);
/* 54 */     if (killPosition == null) {
/* 55 */       this.positionProvider.clear();
/* 56 */       return false;
/*    */     } 
/*    */     
/* 59 */     this.positionProvider.setTarget(killPosition);
/* 60 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 65 */     return (InfoProvider)this.positionProvider;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\SensorKill.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */