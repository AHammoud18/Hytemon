/*     */ package com.hypixel.hytale.server.npc.corecomponents.entity;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.matrix.Matrix4d;
/*     */ import com.hypixel.hytale.math.random.RandomExtra;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.modules.debug.DebugUtils;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.components.messaging.BeaconSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderActionBeacon;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.role.RoleDebugFlags;
/*     */ import com.hypixel.hytale.server.npc.role.support.PositionCache;
/*     */ import com.hypixel.hytale.server.npc.role.support.WorldSupport;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ActionBeacon
/*     */   extends ActionBase
/*     */ {
/*     */   protected final String message;
/*     */   protected final double range;
/*     */   protected final int[] targetGroups;
/*     */   protected final int targetToSendSlot;
/*     */   protected final double expirationTime;
/*     */   protected final int sendCount;
/*     */   @Nullable
/*     */   protected final List<Ref<EntityStore>> sendList;
/*     */   
/*     */   public ActionBeacon(@Nonnull BuilderActionBeacon builderActionBeacon, @Nonnull BuilderSupport support) {
/*  47 */     super((BuilderActionBase)builderActionBeacon);
/*  48 */     this.message = builderActionBeacon.getMessage(support);
/*  49 */     this.range = builderActionBeacon.getRange();
/*  50 */     this.targetGroups = builderActionBeacon.getTargetGroups(support);
/*  51 */     this.targetToSendSlot = builderActionBeacon.getTargetToSendSlot(support);
/*  52 */     this.expirationTime = builderActionBeacon.getExpirationTime();
/*  53 */     this.sendCount = builderActionBeacon.getSendCount();
/*  54 */     this.sendList = (this.sendCount > 0) ? (List<Ref<EntityStore>>)new ObjectArrayList(this.sendCount) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerWithSupport(@Nonnull Role role) {
/*  59 */     role.getPositionCache().requireEntityDistanceUnsorted(this.range);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/*  64 */     if (!super.canExecute(ref, role, sensorInfo, dt, store)) return false; 
/*  65 */     return (this.targetToSendSlot == Integer.MIN_VALUE || role.getMarkedEntitySupport().hasMarkedEntityInSlot(this.targetToSendSlot));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/*  70 */     super.execute(ref, role, sensorInfo, dt, store);
/*     */     
/*  72 */     Ref<EntityStore> target = (this.targetToSendSlot >= 0) ? role.getMarkedEntitySupport().getMarkedEntityRef(this.targetToSendSlot) : ref;
/*  73 */     PositionCache positionCache = role.getPositionCache();
/*  74 */     if (this.sendCount <= 0) {
/*     */       
/*  76 */       positionCache.forEachNPCUnordered(this.range, ActionBeacon::filterNPCs, (_ref, _this, _target, _self) -> _this.sendNPCMessage(_self, _ref, _target, (ComponentAccessor<EntityStore>)_self.getStore()), this, role, target, ref, (ComponentAccessor)store);
/*     */ 
/*     */ 
/*     */       
/*  80 */       return true;
/*     */     } 
/*     */ 
/*     */     
/*  84 */     positionCache.forEachNPCUnordered(this.range, ActionBeacon::filterNPCs, (npcEntity, _this, _sendList, _self) -> RandomExtra.reservoirSample(npcEntity, _this.sendCount, _sendList), this, role, this.sendList, ref, (ComponentAccessor)store);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     for (int i = 0; i < this.sendList.size(); i++) {
/*  91 */       sendNPCMessage(ref, this.sendList.get(i), target, (ComponentAccessor<EntityStore>)store);
/*     */     }
/*     */     
/*  94 */     this.sendList.clear();
/*  95 */     return true;
/*     */   }
/*     */   
/*     */   protected static boolean filterNPCs(@Nonnull Ref<EntityStore> ref, @Nonnull ActionBeacon _this, @Nonnull Role role, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  99 */     return (ref.getStore().getComponent(ref, BeaconSupport.getComponentType()) != null && 
/* 100 */       WorldSupport.isGroupMember(role.getRoleIndex(), ref, _this.targetGroups, componentAccessor));
/*     */   }
/*     */   
/*     */   protected void sendNPCMessage(@Nonnull Ref<EntityStore> self, @Nonnull Ref<EntityStore> targetRef, @Nonnull Ref<EntityStore> target, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 104 */     NPCEntity npcComponent = (NPCEntity)componentAccessor.getComponent(self, NPCEntity.getComponentType());
/* 105 */     assert npcComponent != null;
/*     */     
/* 107 */     Role role = npcComponent.getRole();
/* 108 */     if (role.getDebugSupport().isDebugFlagSet(RoleDebugFlags.BeaconMessages)) {
/* 109 */       ((HytaleLogger.Api)NPCPlugin.get().getLogger().atInfo()).log("ID %d sent message '%s' with target ID %d to ID %d", Integer.valueOf(self.getIndex()), this.message, 
/* 110 */           Integer.valueOf(target.getIndex()), Integer.valueOf(targetRef.getIndex()));
/*     */       
/* 112 */       ThreadLocalRandom random = ThreadLocalRandom.current();
/* 113 */       Vector3f color = new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
/*     */       
/* 115 */       Matrix4d matrix = new Matrix4d();
/* 116 */       matrix.identity();
/* 117 */       Matrix4d tmp = new Matrix4d();
/*     */       
/* 119 */       TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(self, TransformComponent.getComponentType());
/* 120 */       assert transformComponent != null;
/* 121 */       Vector3d pos = transformComponent.getPosition();
/*     */       
/* 123 */       ModelComponent modelComponent = (ModelComponent)componentAccessor.getComponent(self, ModelComponent.getComponentType());
/* 124 */       assert modelComponent != null;
/*     */       
/* 126 */       Model model = modelComponent.getModel();
/*     */       
/* 128 */       double x = pos.x;
/* 129 */       double y = pos.y + ((model != null) ? model.getEyeHeight(self, componentAccessor) : 0.0F);
/* 130 */       double z = pos.z;
/* 131 */       matrix.translate(x, y + random.nextFloat() * 0.5D - 0.25D, z);
/*     */       
/* 133 */       TransformComponent targetTransformComponent = (TransformComponent)componentAccessor.getComponent(targetRef, TransformComponent.getComponentType());
/* 134 */       assert targetTransformComponent != null;
/*     */       
/* 136 */       Vector3d targetPos = targetTransformComponent.getPosition();
/*     */       
/* 138 */       ModelComponent targetModelComponent = (ModelComponent)componentAccessor.getComponent(targetRef, ModelComponent.getComponentType());
/*     */       
/* 140 */       float targetEyeHeight = (targetModelComponent != null) ? targetModelComponent.getModel().getEyeHeight(targetRef, componentAccessor) : 0.0F;
/* 141 */       x -= targetPos.getX();
/* 142 */       y -= targetPos.getY() + targetEyeHeight;
/* 143 */       z -= targetPos.getZ();
/*     */       
/* 145 */       double angleY = Math.atan2(-z, -x);
/* 146 */       matrix.rotateAxis(angleY + 1.5707963705062866D, 0.0D, 1.0D, 0.0D, tmp);
/*     */       
/* 148 */       double angleX = Math.atan2(Math.sqrt(x * x + z * z), -y);
/* 149 */       matrix.rotateAxis(angleX, 1.0D, 0.0D, 0.0D, tmp);
/*     */       
/* 151 */       DebugUtils.addArrow(((EntityStore)componentAccessor.getExternalData()).getWorld(), matrix, color, pos.distanceTo(targetPos), 5.0F, true);
/*     */     } 
/*     */     
/* 154 */     BeaconSupport beaconSupportComponent = (BeaconSupport)componentAccessor.getComponent(targetRef, BeaconSupport.getComponentType());
/* 155 */     if (beaconSupportComponent != null) beaconSupportComponent.postMessage(this.message, target, this.expirationTime); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\ActionBeacon.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */