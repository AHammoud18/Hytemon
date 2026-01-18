/*    */ package com.hypixel.hytale.builtin.portals.ui;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.portals.components.PortalDevice;
/*    */ import com.hypixel.hytale.builtin.portals.components.PortalDeviceConfig;
/*    */ import com.hypixel.hytale.builtin.portals.resources.PortalWorld;
/*    */ import com.hypixel.hytale.builtin.portals.utils.BlockTypeUtils;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.BlockPosition;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.OpenCustomUIInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class PortalDevicePageSupplier
/*    */   implements OpenCustomUIInteraction.CustomPageSupplier
/*    */ {
/*    */   public static final BuilderCodec<PortalDevicePageSupplier> CODEC;
/*    */   private PortalDeviceConfig config;
/*    */   
/*    */   static {
/* 38 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(PortalDevicePageSupplier.class, PortalDevicePageSupplier::new).appendInherited(new KeyedCodec("Config", (Codec)PortalDeviceConfig.CODEC), (supplier, o) -> supplier.config = o, supplier -> supplier.config, (supplier, parent) -> supplier.config = parent.config).documentation("The portal device's config.").add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CustomUIPage tryCreate(Ref<EntityStore> ref, ComponentAccessor<EntityStore> store, PlayerRef playerRef, InteractionContext context) {
/* 44 */     BlockPosition targetBlock = context.getTargetBlock();
/* 45 */     if (targetBlock == null) return null;
/*    */     
/* 47 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 48 */     assert playerComponent != null;
/* 49 */     ItemStack inHand = playerComponent.getInventory().getItemInHand();
/*    */     
/* 51 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */     
/* 53 */     BlockType blockType = world.getBlockType(targetBlock.x, targetBlock.y, targetBlock.z);
/*    */     
/* 55 */     for (String blockStateKey : this.config.getBlockStates()) {
/* 56 */       BlockType blockState = BlockTypeUtils.getBlockForState(blockType, blockStateKey);
/* 57 */       if (blockState == null) {
/* 58 */         playerRef.sendMessage(Message.translation("server.portals.device.blockStateMisconfigured").param("state", blockStateKey));
/* 59 */         return null;
/*    */       } 
/*    */     } 
/* 62 */     BlockType onBlock = BlockTypeUtils.getBlockForState(blockType, this.config.getOnState());
/*    */     
/* 64 */     ChunkStore chunkStore = world.getChunkStore();
/*    */     
/* 66 */     Ref<ChunkStore> blockRef = BlockModule.getBlockEntity(world, targetBlock.x, targetBlock.y, targetBlock.z);
/* 67 */     if (blockRef == null) {
/* 68 */       playerRef.sendMessage(Message.translation("server.portals.device.blockEntityMisconfigured"));
/* 69 */       return null;
/*    */     } 
/*    */     
/* 72 */     PortalDevice existingDevice = (PortalDevice)chunkStore.getStore().getComponent(blockRef, PortalDevice.getComponentType());
/* 73 */     World destinationWorld = (existingDevice == null) ? null : existingDevice.getDestinationWorld();
/* 74 */     if (existingDevice != null && blockType == onBlock && !isPortalWorldValid(destinationWorld)) {
/* 75 */       world.setBlockInteractionState(new Vector3i(targetBlock.x, targetBlock.y, targetBlock.z), blockType, this.config.getOffState());
/* 76 */       playerRef.sendMessage(Message.translation("server.portals.device.adjusted").color("#ff0000"));
/* 77 */       return null;
/*    */     } 
/*    */     
/* 80 */     if (existingDevice == null || destinationWorld == null) {
/* 81 */       chunkStore.getStore().putComponent(blockRef, PortalDevice.getComponentType(), (Component)new PortalDevice(this.config, blockType.getId()));
/* 82 */       return (CustomUIPage)new PortalDeviceSummonPage(playerRef, this.config, blockRef, inHand);
/*    */     } 
/*    */     
/* 85 */     return (CustomUIPage)new PortalDeviceActivePage(playerRef, this.config, blockRef);
/*    */   }
/*    */   
/*    */   private static boolean isPortalWorldValid(@Nullable World world) {
/* 89 */     if (world == null) return false; 
/* 90 */     Store<EntityStore> store = world.getEntityStore().getStore();
/* 91 */     PortalWorld portalWorld = (PortalWorld)store.getResource(PortalWorld.getResourceType());
/* 92 */     return portalWorld.exists();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portal\\ui\PortalDevicePageSupplier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */