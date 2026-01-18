/*    */ package com.hypixel.hytale.builtin.mounts;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.packets.interaction.DismountNPC;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.io.handlers.IPacketHandler;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ 
/*    */ public class MountGamePacketHandler implements SubPacketHandler {
/*    */   public MountGamePacketHandler(IPacketHandler packetHandler) {
/* 14 */     this.packetHandler = packetHandler;
/*    */   }
/*    */   private final IPacketHandler packetHandler;
/*    */   
/*    */   public void registerHandlers() {
/* 19 */     this.packetHandler.registerHandler(294, protoPacket -> handle((DismountNPC)protoPacket));
/*    */   }
/*    */   
/*    */   public void handle(DismountNPC packet) {
/* 23 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/* 24 */     Ref<EntityStore> ref = playerRef.getReference();
/* 25 */     if (ref == null || !ref.isValid()) {
/* 26 */       throw new RuntimeException("Unable to process DismountNPC packet. Player ref is invalid!");
/*    */     }
/*    */     
/* 29 */     Store<EntityStore> store = ref.getStore();
/* 30 */     EntityStore entityStore = (EntityStore)store.getExternalData();
/* 31 */     World world = entityStore.getWorld();
/*    */     
/* 33 */     world.execute(() -> {
/*    */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*    */           assert playerComponent != null;
/*    */           MountedComponent mounted = (MountedComponent)store.getComponent(ref, MountedComponent.getComponentType());
/*    */           if (mounted == null) {
/*    */             int mountEntityId = playerComponent.getMountEntityId();
/*    */             playerComponent.setMountEntityId(0);
/*    */             MountPlugin.dismountNpc((ComponentAccessor<EntityStore>)store, mountEntityId);
/*    */             return;
/*    */           } 
/*    */           if (mounted.getControllerType() == MountController.BlockMount)
/*    */             store.tryRemoveComponent(ref, MountedComponent.getComponentType()); 
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\mounts\MountGamePacketHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */