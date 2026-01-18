/*    */ package com.hypixel.hytale.builtin.asseteditor;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.asseteditor.assettypehandler.AssetTypeHandler;
/*    */ import com.hypixel.hytale.common.util.PathUtil;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorAssetType;
/*    */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorPopupNotificationType;
/*    */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorSetupAssetTypes;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class AssetTypeRegistry
/*    */ {
/* 22 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*    */   
/* 24 */   private final ConcurrentHashMap<String, AssetTypeHandler> assetTypeHandlers = new ConcurrentHashMap<>();
/*    */   private AssetEditorSetupAssetTypes setupPacket;
/*    */   
/*    */   @Nonnull
/*    */   public Map<String, AssetTypeHandler> getRegisteredAssetTypeHandlers() {
/* 29 */     return this.assetTypeHandlers;
/*    */   }
/*    */   
/*    */   public void registerAssetType(@Nonnull AssetTypeHandler assetType) {
/* 33 */     if (this.assetTypeHandlers.putIfAbsent((assetType.getConfig()).id, assetType) != null) {
/* 34 */       throw new IllegalArgumentException("An asset type with id '" + (assetType.getConfig()).id + "' is already registered");
/*    */     }
/*    */   }
/*    */   
/*    */   public void unregisterAssetType(@Nonnull AssetTypeHandler assetType) {
/* 39 */     this.assetTypeHandlers.remove((assetType.getConfig()).id);
/*    */   }
/*    */   
/*    */   public AssetTypeHandler getAssetTypeHandler(String id) {
/* 43 */     return this.assetTypeHandlers.get(id);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public AssetTypeHandler getAssetTypeHandlerForPath(@Nonnull Path path) {
/* 48 */     String extension = PathUtil.getFileExtension(path);
/* 49 */     if (extension.isEmpty()) return null;
/*    */     
/* 51 */     for (AssetTypeHandler handler : this.assetTypeHandlers.values()) {
/* 52 */       if ((handler.getConfig()).fileExtension.equalsIgnoreCase(extension) && path.startsWith((handler.getConfig()).path)) {
/* 53 */         return handler;
/*    */       }
/*    */     } 
/*    */     
/* 57 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isPathInAssetTypeFolder(@Nonnull Path path) {
/* 61 */     for (AssetTypeHandler assetTypeHandler : this.assetTypeHandlers.values()) {
/* 62 */       if (path.startsWith(assetTypeHandler.getRootPath()) && !path.equals(assetTypeHandler.getRootPath())) {
/* 63 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 67 */     return false;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public AssetTypeHandler tryGetAssetTypeHandler(@Nonnull Path assetPath, @Nonnull EditorClient editorClient, int requestToken) {
/* 72 */     AssetTypeHandler assetTypeHandler = getAssetTypeHandlerForPath(assetPath);
/* 73 */     if (assetTypeHandler == null) {
/* 74 */       LOGGER.at(Level.WARNING).log("Invalid asset type for %s", assetPath);
/*    */       
/* 76 */       if (requestToken != -1) {
/* 77 */         editorClient.sendFailureReply(requestToken, Message.translation("server.assetEditor.messages.invalidAssetType"));
/*    */       } else {
/* 79 */         editorClient.sendPopupNotification(AssetEditorPopupNotificationType.Error, Message.translation("server.assetEditor.messages.invalidAssetType"));
/*    */       } 
/*    */       
/* 82 */       return null;
/*    */     } 
/*    */     
/* 85 */     return assetTypeHandler;
/*    */   }
/*    */   
/*    */   public void sendPacket(@Nonnull EditorClient editorClient) {
/* 89 */     editorClient.getPacketHandler().write((Packet)this.setupPacket);
/*    */   }
/*    */   
/*    */   public void setupPacket() {
/* 93 */     ObjectArrayList<AssetEditorAssetType> objectArrayList = new ObjectArrayList();
/* 94 */     for (AssetTypeHandler assetTypeHandler : this.assetTypeHandlers.values()) {
/* 95 */       objectArrayList.add(assetTypeHandler.getConfig());
/*    */     }
/*    */     
/* 98 */     this.setupPacket = new AssetEditorSetupAssetTypes((AssetEditorAssetType[])objectArrayList.toArray(x$0 -> new AssetEditorAssetType[x$0]));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\asseteditor\AssetTypeRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */