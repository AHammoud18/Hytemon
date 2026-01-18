/*    */ package com.hypixel.hytale.builtin.mounts.npc;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.mounts.NPCMountComponent;
/*    */ import com.hypixel.hytale.builtin.mounts.npc.builders.BuilderActionMount;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementConfig;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
/*    */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*    */ import com.hypixel.hytale.server.core.modules.physics.component.PhysicsValues;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import com.hypixel.hytale.server.npc.systems.RoleChangeSystem;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionMount extends ActionBase {
/*    */   public static final String EMPTY_ROLE_ID = "Empty_Role";
/*    */   protected final float anchorX;
/*    */   protected final float anchorY;
/*    */   protected final float anchorZ;
/*    */   protected final String movementConfigId;
/*    */   protected final int emptyRoleIndex;
/*    */   
/*    */   public ActionMount(@Nonnull BuilderActionMount builderActionMount, @Nonnull BuilderSupport builderSupport) {
/* 34 */     super((BuilderActionBase)builderActionMount);
/*    */     
/* 36 */     this.anchorX = builderActionMount.getAnchorX(builderSupport);
/* 37 */     this.anchorY = builderActionMount.getAnchorY(builderSupport);
/* 38 */     this.anchorZ = builderActionMount.getAnchorZ(builderSupport);
/* 39 */     this.movementConfigId = builderActionMount.getMovementConfig(builderSupport);
/*    */     
/* 41 */     this.emptyRoleIndex = NPCPlugin.get().getIndex("Empty_Role");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 46 */     Ref<EntityStore> target = role.getStateSupport().getInteractionIterationTarget();
/* 47 */     boolean targetExists = (target != null && !store.getArchetype(target).contains(DeathComponent.getComponentType()));
/* 48 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && targetExists);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 53 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 55 */     ComponentType<EntityStore, NPCMountComponent> mountComponentType = NPCMountComponent.getComponentType();
/* 56 */     NPCMountComponent mountComponent = (NPCMountComponent)store.getComponent(ref, mountComponentType);
/*    */ 
/*    */     
/* 59 */     if (mountComponent != null) {
/* 60 */       return false;
/*    */     }
/*    */     
/* 63 */     mountComponent = (NPCMountComponent)store.ensureAndGetComponent(ref, mountComponentType);
/* 64 */     mountComponent.setOriginalRoleIndex(NPCPlugin.get().getIndex(role.getRoleName()));
/*    */     
/* 66 */     Ref<EntityStore> playerReference = role.getStateSupport().getInteractionIterationTarget();
/* 67 */     if (playerReference == null) return false;
/*    */     
/* 69 */     PlayerRef playerRefComponent = (PlayerRef)store.getComponent(playerReference, PlayerRef.getComponentType());
/* 70 */     assert playerRefComponent != null;
/*    */     
/* 72 */     mountComponent.setOwnerPlayerRef(playerRefComponent);
/* 73 */     mountComponent.setAnchor(this.anchorX, this.anchorY, this.anchorZ);
/*    */     
/* 75 */     Player playerComponent = (Player)store.getComponent(playerReference, Player.getComponentType());
/* 76 */     assert playerComponent != null;
/*    */     
/* 78 */     PhysicsValues playerPhysicsValues = (PhysicsValues)store.getComponent(playerReference, PhysicsValues.getComponentType());
/*    */ 
/*    */ 
/*    */     
/* 82 */     RoleChangeSystem.requestRoleChange(ref, role, this.emptyRoleIndex, false, null, null, (ComponentAccessor)store);
/*    */     
/* 84 */     MovementConfig movementConfig = (MovementConfig)MovementConfig.getAssetMap().getAsset(this.movementConfigId);
/* 85 */     if (movementConfig != null) {
/* 86 */       MovementManager movementManagerComponent = (MovementManager)store.getComponent(playerReference, MovementManager.getComponentType());
/* 87 */       assert movementManagerComponent != null;
/*    */       
/* 89 */       movementManagerComponent.setDefaultSettings(movementConfig.toPacket(), playerPhysicsValues, playerComponent.getGameMode());
/* 90 */       movementManagerComponent.applyDefaultSettings();
/* 91 */       movementManagerComponent.update(playerRefComponent.getPacketHandler());
/*    */     } 
/*    */     
/* 94 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\mounts\npc\ActionMount.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */