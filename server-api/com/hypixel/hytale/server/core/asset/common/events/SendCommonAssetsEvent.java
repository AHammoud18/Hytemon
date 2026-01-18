/*    */ package com.hypixel.hytale.server.core.asset.common.events;
/*    */ 
/*    */ import com.hypixel.hytale.event.IAsyncEvent;
/*    */ import com.hypixel.hytale.protocol.Asset;
/*    */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SendCommonAssetsEvent
/*    */   implements IAsyncEvent<Void>
/*    */ {
/*    */   private final PacketHandler packetHandler;
/*    */   private final Asset[] assets;
/*    */   
/*    */   public SendCommonAssetsEvent(PacketHandler packetHandler, Asset[] assets) {
/* 19 */     this.packetHandler = packetHandler;
/* 20 */     this.assets = assets;
/*    */   }
/*    */   
/*    */   public PacketHandler getPacketHandler() {
/* 24 */     return this.packetHandler;
/*    */   }
/*    */   
/*    */   public Asset[] getRequestedAssets() {
/* 28 */     return this.assets;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 34 */     return "SendCommonAssetsEvent{packetHandler=" + String.valueOf(this.packetHandler) + ", assets=" + 
/*    */       
/* 36 */       Arrays.toString((Object[])this.assets) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\common\events\SendCommonAssetsEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */