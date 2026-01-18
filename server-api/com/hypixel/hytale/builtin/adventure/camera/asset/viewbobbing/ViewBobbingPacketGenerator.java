/*    */ package com.hypixel.hytale.builtin.adventure.camera.asset.viewbobbing;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetMap;
/*    */ import com.hypixel.hytale.protocol.CachedPacket;
/*    */ import com.hypixel.hytale.protocol.MovementType;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.UpdateType;
/*    */ import com.hypixel.hytale.protocol.packets.assets.UpdateViewBobbing;
/*    */ import com.hypixel.hytale.server.core.asset.packet.SimpleAssetPacketGenerator;
/*    */ import java.util.EnumMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ViewBobbingPacketGenerator
/*    */   extends SimpleAssetPacketGenerator<MovementType, ViewBobbing, AssetMap<MovementType, ViewBobbing>>
/*    */ {
/*    */   @Nonnull
/*    */   public Packet generateInitPacket(AssetMap<MovementType, ViewBobbing> assetMap, @Nonnull Map<MovementType, ViewBobbing> assets) {
/* 24 */     return toCachedPacket(UpdateType.Init, assets);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected Packet generateUpdatePacket(AssetMap<MovementType, ViewBobbing> assetMap, @Nonnull Map<MovementType, ViewBobbing> loadedAssets) {
/* 30 */     return toCachedPacket(UpdateType.AddOrUpdate, loadedAssets);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected Packet generateRemovePacket(AssetMap<MovementType, ViewBobbing> assetMap, @Nonnull Set<MovementType> removed) {
/* 36 */     UpdateViewBobbing packet = new UpdateViewBobbing();
/* 37 */     packet.type = UpdateType.Remove;
/* 38 */     packet.profiles = new EnumMap<>(MovementType.class);
/* 39 */     for (MovementType type : removed) {
/* 40 */       packet.profiles.put(type, null);
/*    */     }
/* 42 */     return (Packet)CachedPacket.cache((Packet)packet);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected static Packet toCachedPacket(UpdateType type, @Nonnull Map<MovementType, ViewBobbing> assets) {
/* 47 */     UpdateViewBobbing packet = new UpdateViewBobbing();
/* 48 */     packet.type = type;
/* 49 */     packet.profiles = new EnumMap<>(MovementType.class);
/* 50 */     for (Map.Entry<MovementType, ViewBobbing> entry : assets.entrySet()) {
/* 51 */       packet.profiles.put(entry.getKey(), ((ViewBobbing)entry.getValue()).toPacket());
/*    */     }
/* 53 */     return (Packet)CachedPacket.cache((Packet)packet);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\camera\asset\viewbobbing\ViewBobbingPacketGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */