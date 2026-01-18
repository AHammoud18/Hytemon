/*    */ package com.hypixel.hytale.server.core.registry;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.function.consumer.BooleanConsumer;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.packets.setup.ClientFeature;
/*    */ import com.hypixel.hytale.protocol.packets.setup.ServerTags;
/*    */ import com.hypixel.hytale.registry.Registry;
/*    */ import com.hypixel.hytale.server.core.client.ClientFeatureHandler;
/*    */ import com.hypixel.hytale.server.core.plugin.PluginBase;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.function.BooleanSupplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ClientFeatureRegistry extends Registry<ClientFeatureRegistration> {
/*    */   public ClientFeatureRegistry(@Nonnull List<BooleanConsumer> registrations, BooleanSupplier precondition, String preconditionMessage, PluginBase plugin) {
/* 19 */     super(registrations, precondition, preconditionMessage, ClientFeatureRegistration::new);
/*    */   }
/*    */   
/*    */   public ClientFeatureRegistration register(ClientFeature feature) {
/* 23 */     ClientFeatureHandler.register(feature);
/* 24 */     return (ClientFeatureRegistration)register(new ClientFeatureRegistration(feature));
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerClientTag(@Nonnull String tag) {
/* 29 */     if (AssetRegistry.registerClientTag(tag)) {
/* 30 */       ServerTags packet = new ServerTags((Map)AssetRegistry.getClientTags()); Universe.get().broadcastPacketNoCache((Packet)packet);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\registry\ClientFeatureRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */