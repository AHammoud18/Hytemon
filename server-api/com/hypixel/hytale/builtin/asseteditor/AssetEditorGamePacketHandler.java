/*     */ package com.hypixel.hytale.builtin.asseteditor;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorAuthorization;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorInitialize;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorUpdateJsonAsset;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.io.handlers.IPacketHandler;
/*     */ import com.hypixel.hytale.server.core.io.handlers.SubPacketHandler;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class AssetEditorGamePacketHandler implements SubPacketHandler {
/*  22 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   private final IPacketHandler packetHandler;
/*     */   
/*     */   public AssetEditorGamePacketHandler(IPacketHandler packetHandler) {
/*  27 */     this.packetHandler = packetHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerHandlers() {
/*  32 */     if (AssetEditorPlugin.get().isDisabled()) {
/*  33 */       this.packetHandler.registerNoOpHandlers(new int[] { 302 });
/*  34 */       this.packetHandler.registerNoOpHandlers(new int[] { 325 });
/*     */       
/*     */       return;
/*     */     } 
/*  38 */     this.packetHandler.registerHandler(302, p -> handle((AssetEditorInitialize)p));
/*  39 */     this.packetHandler.registerHandler(323, p -> handle((AssetEditorUpdateJsonAsset)p));
/*     */   }
/*     */   
/*     */   public void handle(AssetEditorInitialize packet) {
/*  43 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/*  44 */     Ref<EntityStore> ref = playerRef.getReference();
/*  45 */     if (ref == null || !ref.isValid()) {
/*  46 */       throw new RuntimeException("Unable to process AssetEditorInitialize packet. Player ref is invalid!");
/*     */     }
/*     */     
/*  49 */     Store<EntityStore> store = ref.getStore();
/*  50 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/*  52 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           assert playerComponent != null;
/*     */           if (lacksPermission(playerComponent, false)) {
/*     */             this.packetHandler.getPlayerRef().getPacketHandler().write((Packet)new AssetEditorAuthorization(false));
/*     */             return;
/*     */           } 
/*     */           this.packetHandler.getPlayerRef().getPacketHandler().write((Packet)new AssetEditorAuthorization(true));
/*     */           AssetEditorPlugin.get().handleInitializeEditor(ref, (ComponentAccessor<EntityStore>)store);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void handle(@Nonnull AssetEditorUpdateJsonAsset packet) {
/*  75 */     PlayerRef playerRef = this.packetHandler.getPlayerRef();
/*  76 */     Ref<EntityStore> ref = playerRef.getReference();
/*  77 */     if (ref == null || !ref.isValid()) {
/*  78 */       throw new RuntimeException("Unable to process AssetEditorUpdateJsonAsset packet. Player ref is invalid!");
/*     */     }
/*     */     
/*  81 */     Store<EntityStore> store = ref.getStore();
/*  82 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/*  84 */     CompletableFuture.runAsync(() -> { Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType()); if (lacksPermission(playerComponent, true)) return;  }(Executor)world)
/*     */ 
/*     */ 
/*     */       
/*  88 */       .thenRunAsync(() -> {
/*     */           LOGGER.at(Level.INFO).log("%s updating json asset at %s", this.packetHandler.getPlayerRef().getUsername(), packet.path);
/*     */           EditorClient mockClient = new EditorClient(playerRef);
/*     */           AssetEditorPlugin.get().handleJsonAssetUpdate(mockClient, (packet.path != null) ? new AssetPath(packet.path) : null, packet.assetType, packet.assetIndex, packet.commands, packet.token);
/*     */         });
/*     */   }
/*     */   
/*     */   private boolean lacksPermission(@Nonnull Player player, boolean shouldShowDenialMessage) {
/*  96 */     if (!player.hasPermission("hytale.editor.asset")) {
/*  97 */       if (shouldShowDenialMessage) {
/*  98 */         player.sendMessage(Messages.USAGE_DENIED_MESSAGE);
/*     */       }
/* 100 */       return true;
/*     */     } 
/*     */     
/* 103 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\asseteditor\AssetEditorGamePacketHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */