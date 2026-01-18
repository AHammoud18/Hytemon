/*    */ package com.hypixel.hytale.server.flock.corecomponents;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.entity.group.EntityGroup;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.flock.FlockMembership;
/*    */ import com.hypixel.hytale.server.flock.corecomponents.builders.BuilderActionFlockBeacon;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.components.messaging.BeaconSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionFlockBeacon
/*    */   extends ActionBase
/*    */ {
/* 27 */   protected static final ComponentType<EntityStore, BeaconSupport> BEACON_SUPPORT_COMPONENT_TYPE = BeaconSupport.getComponentType();
/* 28 */   protected static final ComponentType<EntityStore, FlockMembership> FLOCK_MEMBERSHIP_COMPONENT_TYPE = FlockMembership.getComponentType();
/* 29 */   protected static final ComponentType<EntityStore, EntityGroup> ENTITY_GROUP_COMPONENT_TYPE = EntityGroup.getComponentType();
/*    */   
/*    */   protected final String message;
/*    */   protected final double expirationTime;
/*    */   protected final boolean sendToSelf;
/*    */   protected final boolean sendToLeaderOnly;
/*    */   protected final int sendTargetSlot;
/*    */   
/*    */   public ActionFlockBeacon(@Nonnull BuilderActionFlockBeacon builderActionFlockBeacon, @Nonnull BuilderSupport builderSupport) {
/* 38 */     super((BuilderActionBase)builderActionFlockBeacon);
/* 39 */     this.message = builderActionFlockBeacon.getMessage(builderSupport);
/* 40 */     this.sendTargetSlot = builderActionFlockBeacon.getSendTargetSlot(builderSupport);
/* 41 */     this.expirationTime = builderActionFlockBeacon.getExpirationTime();
/* 42 */     this.sendToSelf = builderActionFlockBeacon.isSendToSelf();
/* 43 */     this.sendToLeaderOnly = builderActionFlockBeacon.isSendToLeaderOnly();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 48 */     if (!super.canExecute(ref, role, sensorInfo, dt, store)) return false;
/*    */     
/* 50 */     return (store.getArchetype(ref).contains(FLOCK_MEMBERSHIP_COMPONENT_TYPE) && (this.sendTargetSlot == Integer.MIN_VALUE || role
/* 51 */       .getMarkedEntitySupport().hasMarkedEntityInSlot(this.sendTargetSlot)));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 56 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 58 */     FlockMembership flockMembership = (FlockMembership)store.getComponent(ref, FLOCK_MEMBERSHIP_COMPONENT_TYPE);
/* 59 */     Ref<EntityStore> flockReference = (flockMembership != null) ? flockMembership.getFlockRef() : null;
/* 60 */     if (flockReference == null || !flockReference.isValid()) return true;
/*    */     
/* 62 */     EntityGroup entityGroup = (EntityGroup)store.getComponent(flockReference, ENTITY_GROUP_COMPONENT_TYPE);
/* 63 */     if (entityGroup == null) return true;
/*    */     
/* 65 */     Ref<EntityStore> targetRef = (this.sendTargetSlot >= 0) ? role.getMarkedEntitySupport().getMarkedEntityRef(this.sendTargetSlot) : ref;
/* 66 */     if (this.sendToLeaderOnly) {
/* 67 */       Ref<EntityStore> leaderReference = entityGroup.getLeaderRef();
/* 68 */       if ((this.sendToSelf || targetRef == null || !targetRef.equals(leaderReference)) && 
/* 69 */         leaderReference.isValid()) {
/* 70 */         sendNPCMessage(leaderReference, targetRef, (ComponentAccessor<EntityStore>)store);
/*    */       }
/*    */     } else {
/*    */       
/* 74 */       entityGroup.forEachMember((flockMember, entity, _target) -> sendNPCMessage(flockMember, _target, (ComponentAccessor<EntityStore>)store), ref, targetRef, this.sendToSelf ? null : ref);
/*    */     } 
/* 76 */     return true;
/*    */   }
/*    */   
/*    */   protected void sendNPCMessage(@Nonnull Ref<EntityStore> ref, @Nullable Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 80 */     BeaconSupport beaconSupport = (BeaconSupport)componentAccessor.getComponent(ref, BEACON_SUPPORT_COMPONENT_TYPE);
/* 81 */     if (beaconSupport != null) beaconSupport.postMessage(this.message, targetRef, this.expirationTime); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\flock\corecomponents\ActionFlockBeacon.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */