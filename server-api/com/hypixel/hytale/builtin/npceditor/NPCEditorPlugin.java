/*    */ package com.hypixel.hytale.builtin.npceditor;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.asseteditor.AssetEditorPlugin;
/*    */ import com.hypixel.hytale.builtin.asseteditor.assettypehandler.AssetTypeHandler;
/*    */ import com.hypixel.hytale.builtin.asseteditor.event.AssetEditorSelectAssetEvent;
/*    */ import com.hypixel.hytale.protocol.Model;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.Vector3f;
/*    */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorPreviewCameraSettings;
/*    */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorUpdateModelPreview;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*    */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*    */ import com.hypixel.hytale.server.core.command.system.exceptions.GeneralCommandException;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.Builder;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderInfo;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.spawning.ISpawnableWithModel;
/*    */ import com.hypixel.hytale.server.spawning.SpawningContext;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class NPCEditorPlugin extends JavaPlugin {
/* 26 */   private static final AssetEditorPreviewCameraSettings DEFAULT_PREVIEW_CAMERA_SETTINGS = new AssetEditorPreviewCameraSettings(0.25F, new Vector3f(0.0F, 75.0F, 0.0F), new Vector3f(0.0F, 0.7853F, 0.0F));
/*    */   
/*    */   public NPCEditorPlugin(@Nonnull JavaPluginInit init) {
/* 29 */     super(init);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 34 */     AssetEditorPlugin.get().getAssetTypeRegistry().registerAssetType((AssetTypeHandler)new NPCRoleAssetTypeHandler());
/*    */     
/* 36 */     getEventRegistry().register(AssetEditorSelectAssetEvent.class, NPCEditorPlugin::onSelectAsset);
/*    */   }
/*    */   
/*    */   private static void onSelectAsset(@Nonnull AssetEditorSelectAssetEvent event) {
/* 40 */     String assetType = event.getAssetType();
/*    */     
/* 42 */     if ("NPCRole".equals(assetType)) {
/* 43 */       ISpawnableWithModel spawnable; NPCPlugin npcPlugin = NPCPlugin.get();
/* 44 */       String key = (String)ModelAsset.getAssetStore().decodeFilePathKey(event.getAssetFilePath().path());
/*    */       
/* 46 */       int roleIndex = npcPlugin.getIndex(key);
/* 47 */       npcPlugin.forceValidation(roleIndex);
/*    */       
/* 49 */       BuilderInfo roleBuilderInfo = npcPlugin.getRoleBuilderInfo(roleIndex);
/* 50 */       if (roleBuilderInfo == null) throw new IllegalStateException("Can't find a matching role builder"); 
/* 51 */       if (!npcPlugin.testAndValidateRole(roleBuilderInfo)) throw new GeneralCommandException(Message.translation("server.commands.npc.spawn.validation_failed"));
/*    */       
/* 53 */       Builder<Role> roleBuilder = npcPlugin.tryGetCachedValidRole(roleIndex);
/* 54 */       if (roleBuilder == null) throw new IllegalArgumentException("Can't find a matching role builder"); 
/* 55 */       if (roleBuilder instanceof ISpawnableWithModel) { spawnable = (ISpawnableWithModel)roleBuilder; }
/*    */       else
/*    */       { return; }
/* 58 */        if (!roleBuilder.isSpawnable()) {
/*    */         return;
/*    */       }
/*    */       
/* 62 */       SpawningContext spawningContext = new SpawningContext();
/* 63 */       if (!spawningContext.setSpawnable(spawnable)) {
/*    */         return;
/*    */       }
/*    */       
/* 67 */       Model model = spawningContext.getModel();
/* 68 */       if (model == null) {
/*    */         return;
/*    */       }
/*    */       
/* 72 */       Model modelPacket = model.toPacket();
/* 73 */       event.getEditorClient().getPacketHandler().write((Packet)new AssetEditorUpdateModelPreview(event.getAssetFilePath().toPacket(), modelPacket, null, DEFAULT_PREVIEW_CAMERA_SETTINGS));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\npceditor\NPCEditorPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */