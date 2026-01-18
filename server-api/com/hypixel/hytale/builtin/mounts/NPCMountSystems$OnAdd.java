/*    */ package com.hypixel.hytale.builtin.mounts;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.packets.interaction.MountNPC;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.systems.RoleChangeSystem;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OnAdd
/*    */   extends RefSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/*    */   private final ComponentType<EntityStore, NPCMountComponent> mountComponentType;
/*    */   
/*    */   public OnAdd(@Nonnull ComponentType<EntityStore, NPCMountComponent> mountRoleChangeComponentType) {
/* 39 */     this.mountComponentType = mountRoleChangeComponentType;
/*    */   }
/*    */ 
/*    */   
/*    */   public Query<EntityStore> getQuery() {
/* 44 */     return (Query)this.mountComponentType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 49 */     NPCMountComponent mountComponent = (NPCMountComponent)store.getComponent(ref, this.mountComponentType);
/* 50 */     assert mountComponent != null;
/*    */     
/* 52 */     PlayerRef playerRef = mountComponent.getOwnerPlayerRef();
/* 53 */     if (playerRef == null) {
/* 54 */       resetOriginalRoleMount(ref, store, commandBuffer, mountComponent);
/*    */       
/*    */       return;
/*    */     } 
/* 58 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/* 59 */     assert npcComponent != null;
/*    */     
/* 61 */     NetworkId networkIdComponent = (NetworkId)store.getComponent(ref, NetworkId.getComponentType());
/* 62 */     assert networkIdComponent != null;
/*    */     
/* 64 */     int networkId = networkIdComponent.getId();
/* 65 */     MountNPC packet = new MountNPC(mountComponent.getAnchorX(), mountComponent.getAnchorY(), mountComponent.getAnchorZ(), networkId);
/*    */     
/* 67 */     Player playerComponent = (Player)playerRef.getComponent(Player.getComponentType());
/* 68 */     assert playerComponent != null;
/*    */     
/* 70 */     playerComponent.setMountEntityId(networkId);
/* 71 */     playerRef.getPacketHandler().write((Packet)packet);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void resetOriginalRoleMount(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull NPCMountComponent mountComponent) {
/* 83 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/* 84 */     assert npcComponent != null;
/*    */     
/* 86 */     RoleChangeSystem.requestRoleChange(ref, npcComponent.getRole(), mountComponent.getOriginalRoleIndex(), false, "Idle", null, (ComponentAccessor)store);
/* 87 */     commandBuffer.removeComponent(ref, NPCMountComponent.getComponentType());
/*    */   }
/*    */   
/*    */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\mounts\NPCMountSystems$OnAdd.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */