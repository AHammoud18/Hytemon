/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.components.messaging.BeaconSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderSensorBeacon;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.role.RoleDebugFlags;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.EntityPositionProvider;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorBeacon extends SensorBase {
/* 24 */   protected static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*    */   
/*    */   protected final int messageIndex;
/*    */   
/*    */   protected final double range;
/*    */   protected final int targetSlot;
/*    */   protected final boolean consume;
/* 31 */   private final EntityPositionProvider positionProvider = new EntityPositionProvider();
/*    */   
/*    */   public SensorBeacon(@Nonnull BuilderSensorBeacon builderSensorBeacon, @Nonnull BuilderSupport builderSupport) {
/* 34 */     super((BuilderSensorBase)builderSensorBeacon);
/* 35 */     this.messageIndex = builderSensorBeacon.getMessageSlot(builderSupport);
/* 36 */     this.range = builderSensorBeacon.getRange(builderSupport);
/* 37 */     this.targetSlot = builderSensorBeacon.getTargetSlot(builderSupport);
/* 38 */     this.consume = builderSensorBeacon.isConsume();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 43 */     if (!super.matches(ref, role, dt, store)) return false;
/*    */     
/* 45 */     BeaconSupport beaconSupportComponent = (BeaconSupport)store.getComponent(ref, BeaconSupport.getComponentType());
/* 46 */     if (beaconSupportComponent == null) {
/* 47 */       this.positionProvider.clear();
/* 48 */       return false;
/*    */     } 
/*    */     
/* 51 */     if (!beaconSupportComponent.isMessageQueued(this.messageIndex)) {
/* 52 */       this.positionProvider.clear();
/* 53 */       return false;
/*    */     } 
/*    */     
/* 56 */     Ref<EntityStore> target = this.consume ? beaconSupportComponent.pollMessage(this.messageIndex) : beaconSupportComponent.peekMessage(this.messageIndex);
/* 57 */     if (target == null) {
/* 58 */       this.positionProvider.clear();
/* 59 */       return false;
/*    */     } 
/*    */     
/* 62 */     Ref<EntityStore> targetRef = this.positionProvider.setTarget(target, (ComponentAccessor)store);
/* 63 */     if (targetRef == null || !targetRef.isValid()) {
/* 64 */       this.positionProvider.clear();
/* 65 */       return false;
/*    */     } 
/*    */     
/* 68 */     TransformComponent targetTransformComponent = (TransformComponent)store.getComponent(targetRef, TRANSFORM_COMPONENT_TYPE);
/* 69 */     assert targetTransformComponent != null;
/* 70 */     Vector3d targetPosition = targetTransformComponent.getPosition();
/*    */     
/* 72 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/* 73 */     assert transformComponent != null;
/* 74 */     Vector3d position = transformComponent.getPosition();
/*    */     
/* 76 */     if (targetPosition.distanceSquaredTo(position) > this.range * this.range) {
/* 77 */       this.positionProvider.clear();
/* 78 */       return false;
/*    */     } 
/*    */ 
/*    */     
/* 82 */     if (this.targetSlot >= 0) {
/* 83 */       role.getMarkedEntitySupport().setMarkedEntity(this.targetSlot, targetRef);
/*    */     }
/*    */     
/* 86 */     if (role.getDebugSupport().isDebugFlagSet(RoleDebugFlags.BeaconMessages)) {
/* 87 */       ((HytaleLogger.Api)NPCPlugin.get().getLogger().atInfo()).log("ID %d received message '%s' with target ID %d", Integer.valueOf(ref.getIndex()), beaconSupportComponent
/* 88 */           .getMessageTextForIndex(this.messageIndex), Integer.valueOf(target.getIndex()));
/*    */     }
/*    */     
/* 91 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 96 */     return (InfoProvider)this.positionProvider;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\SensorBeacon.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */