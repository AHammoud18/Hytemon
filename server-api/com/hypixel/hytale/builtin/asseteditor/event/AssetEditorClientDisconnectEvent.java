/*    */ package com.hypixel.hytale.builtin.asseteditor.event;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.asseteditor.EditorClient;
/*    */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class AssetEditorClientDisconnectEvent
/*    */   extends EditorClientEvent<Void> {
/*    */   private final PacketHandler.DisconnectReason disconnectReason;
/*    */   
/*    */   public AssetEditorClientDisconnectEvent(EditorClient editorClient, PacketHandler.DisconnectReason disconnectReason) {
/* 12 */     super(editorClient);
/* 13 */     this.disconnectReason = disconnectReason;
/*    */   }
/*    */   
/*    */   public PacketHandler.DisconnectReason getDisconnectReason() {
/* 17 */     return this.disconnectReason;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 23 */     return "AssetEditorClientDisconnectedEvent{disconnectReason=" + String.valueOf(this.disconnectReason) + "}" + super
/*    */       
/* 25 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\asseteditor\event\AssetEditorClientDisconnectEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */