/*    */ package com.hypixel.hytale.builtin.portals.components;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.portals.PortalsPlugin;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PortalDevice
/*    */   implements Component<ChunkStore>
/*    */ {
/*    */   public static final BuilderCodec<PortalDevice> CODEC;
/*    */   private PortalDeviceConfig config;
/*    */   private String baseBlockTypeKey;
/*    */   private UUID destinationWorldUuid;
/*    */   
/*    */   static {
/* 40 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PortalDevice.class, PortalDevice::new).append(new KeyedCodec("Config", (Codec)PortalDeviceConfig.CODEC), (portal, o) -> portal.config = o, portal -> portal.config).add()).append(new KeyedCodec("BaseBlockType", (Codec)Codec.STRING), (portal, o) -> portal.baseBlockTypeKey = o, portal -> portal.baseBlockTypeKey).add()).append(new KeyedCodec("DestinationWorld", (Codec)Codec.UUID_BINARY), (portal, o) -> portal.destinationWorldUuid = o, portal -> portal.destinationWorldUuid).add()).build();
/*    */   }
/*    */   public static ComponentType<ChunkStore, PortalDevice> getComponentType() {
/* 43 */     return PortalsPlugin.getInstance().getPortalDeviceComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private PortalDevice() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PortalDevice(PortalDeviceConfig config, String baseBlockTypeKey) {
/* 55 */     this.config = config;
/* 56 */     this.baseBlockTypeKey = baseBlockTypeKey;
/*    */   }
/*    */   
/*    */   public PortalDeviceConfig getConfig() {
/* 60 */     return this.config;
/*    */   }
/*    */   
/*    */   public String getBaseBlockTypeKey() {
/* 64 */     return this.baseBlockTypeKey;
/*    */   }
/*    */   
/*    */   public BlockType getBaseBlockType() {
/* 68 */     return (BlockType)BlockType.getAssetMap().getAsset(this.baseBlockTypeKey);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public UUID getDestinationWorldUuid() {
/* 73 */     return this.destinationWorldUuid;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public World getDestinationWorld() {
/* 78 */     if (this.destinationWorldUuid == null) return null; 
/* 79 */     World world = Universe.get().getWorld(this.destinationWorldUuid);
/* 80 */     if (world == null || !world.isAlive()) return null; 
/* 81 */     return world;
/*    */   }
/*    */   
/*    */   public void setDestinationWorld(World world) {
/* 85 */     this.destinationWorldUuid = world.getWorldConfig().getUuid();
/*    */   }
/*    */ 
/*    */   
/*    */   public Component<ChunkStore> clone() {
/* 90 */     PortalDevice portal = new PortalDevice();
/* 91 */     portal.config = this.config;
/* 92 */     portal.baseBlockTypeKey = this.baseBlockTypeKey;
/* 93 */     portal.destinationWorldUuid = this.destinationWorldUuid;
/* 94 */     return portal;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\components\PortalDevice.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */