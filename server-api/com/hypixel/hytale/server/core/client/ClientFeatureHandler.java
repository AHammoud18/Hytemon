/*    */ package com.hypixel.hytale.server.core.client;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.packets.setup.ClientFeature;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
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
/*    */ public class ClientFeatureHandler
/*    */ {
/*    */   public static void register(@Nonnull ClientFeature feature) {
/* 21 */     for (World world : Universe.get().getWorlds().values()) {
/* 22 */       world.registerFeature(feature, true);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void unregister(@Nonnull ClientFeature feature) {
/* 32 */     for (World world : Universe.get().getWorlds().values())
/* 33 */       world.registerFeature(feature, false); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\client\ClientFeatureHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */