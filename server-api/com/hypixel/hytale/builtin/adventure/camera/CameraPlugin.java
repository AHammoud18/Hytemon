/*    */ package com.hypixel.hytale.builtin.adventure.camera;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetMap;
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.IndexedAssetMap;
/*    */ import com.hypixel.hytale.builtin.adventure.camera.asset.cameraeffect.CameraShakeEffect;
/*    */ import com.hypixel.hytale.builtin.adventure.camera.asset.camerashake.CameraShake;
/*    */ import com.hypixel.hytale.builtin.adventure.camera.asset.camerashake.CameraShakePacketGenerator;
/*    */ import com.hypixel.hytale.builtin.adventure.camera.asset.viewbobbing.ViewBobbing;
/*    */ import com.hypixel.hytale.builtin.adventure.camera.asset.viewbobbing.ViewBobbingPacketGenerator;
/*    */ import com.hypixel.hytale.builtin.adventure.camera.command.CameraEffectCommand;
/*    */ import com.hypixel.hytale.builtin.adventure.camera.interaction.CameraShakeInteraction;
/*    */ import com.hypixel.hytale.builtin.adventure.camera.system.CameraEffectSystem;
/*    */ import com.hypixel.hytale.component.system.ISystem;
/*    */ import com.hypixel.hytale.protocol.MovementType;
/*    */ import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
/*    */ import com.hypixel.hytale.server.core.asset.packet.AssetPacketGenerator;
/*    */ import com.hypixel.hytale.server.core.asset.type.camera.CameraEffect;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import com.hypixel.hytale.server.core.plugin.registry.AssetRegistry;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CameraPlugin
/*    */   extends JavaPlugin
/*    */ {
/*    */   private static final String CODEC_CAMERA_SHAKE = "CameraShake";
/*    */   
/*    */   public CameraPlugin(@Nonnull JavaPluginInit init) {
/* 38 */     super(init);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 43 */     AssetRegistry assetRegistry = getAssetRegistry();
/*    */     
/* 45 */     getCodecRegistry(CameraEffect.CODEC).register("CameraShake", CameraShakeEffect.class, CameraShakeEffect.CODEC);
/* 46 */     getCodecRegistry(Interaction.CODEC).register("CameraShake", CameraShakeInteraction.class, CameraShakeInteraction.CODEC);
/*    */     
/* 48 */     assetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(String.class, CameraShake.class, (AssetMap)new IndexedAssetMap())
/* 49 */         .loadsBefore(new Class[] { CameraEffect.class
/* 50 */           })).setCodec((AssetCodec)CameraShake.CODEC))
/* 51 */         .setPath("Camera/CameraShake"))
/* 52 */         .setKeyFunction(CameraShake::getId))
/* 53 */         .setReplaceOnRemove(CameraShake::new))
/* 54 */         .setPacketGenerator((AssetPacketGenerator)new CameraShakePacketGenerator())
/* 55 */         .build());
/*    */     
/* 57 */     assetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(MovementType.class, ViewBobbing.class, (AssetMap)new DefaultAssetMap())
/* 58 */         .setCodec((AssetCodec)ViewBobbing.CODEC))
/* 59 */         .setPath("Camera/ViewBobbing"))
/* 60 */         .setKeyFunction(ViewBobbing::getId))
/* 61 */         .setPacketGenerator((AssetPacketGenerator)new ViewBobbingPacketGenerator())
/* 62 */         .build());
/*    */     
/* 64 */     getCommandRegistry().registerCommand((AbstractCommand)new CameraEffectCommand());
/*    */     
/* 66 */     getEntityStoreRegistry().registerSystem((ISystem)new CameraEffectSystem());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\camera\CameraPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */