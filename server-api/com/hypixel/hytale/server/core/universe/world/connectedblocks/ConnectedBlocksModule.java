/*    */ package com.hypixel.hytale.server.core.universe.world.connectedblocks;
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
/*    */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*    */ import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import com.hypixel.hytale.server.core.universe.world.connectedblocks.builtin.RoofConnectedBlockRuleSet;
/*    */ import com.hypixel.hytale.server.core.universe.world.connectedblocks.builtin.StairConnectedBlockRuleSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ConnectedBlocksModule extends JavaPlugin {
/* 20 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(ConnectedBlocksModule.class)
/* 21 */     .depends(new Class[] { EntityModule.class
/* 22 */       }).depends(new Class[] { InteractionModule.class
/* 23 */       }).build();
/*    */   
/*    */   private static ConnectedBlocksModule instance;
/*    */   
/*    */   public static ConnectedBlocksModule get() {
/* 28 */     return instance;
/*    */   }
/*    */   
/*    */   public ConnectedBlocksModule(@Nonnull JavaPluginInit init) {
/* 32 */     super(init);
/* 33 */     instance = this;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 38 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(CustomConnectedBlockTemplateAsset.class, (AssetMap)new DefaultAssetMap())
/* 39 */         .setPath("Item/CustomConnectedBlockTemplates"))
/* 40 */         .setKeyFunction(CustomConnectedBlockTemplateAsset::getId))
/* 41 */         .setCodec((AssetCodec)CustomConnectedBlockTemplateAsset.CODEC))
/* 42 */         .build());
/*    */     
/* 44 */     getEventRegistry().register(LoadedAssetsEvent.class, BlockType.class, ConnectedBlocksModule::onBlockTypesChanged);
/*    */     
/* 46 */     CustomTemplateConnectedBlockPattern.CODEC.register("Custom", CustomConnectedBlockPattern.class, (Codec)CustomConnectedBlockPattern.CODEC);
/* 47 */     ConnectedBlockRuleSet.CODEC.register("CustomTemplate", CustomTemplateConnectedBlockRuleSet.class, (Codec)CustomTemplateConnectedBlockRuleSet.CODEC);
/*    */     
/* 49 */     ConnectedBlockRuleSet.CODEC.register("Stair", StairConnectedBlockRuleSet.class, (Codec)StairConnectedBlockRuleSet.CODEC);
/* 50 */     ConnectedBlockRuleSet.CODEC.register("Roof", RoofConnectedBlockRuleSet.class, (Codec)RoofConnectedBlockRuleSet.CODEC);
/*    */   }
/*    */   
/*    */   private static void onBlockTypesChanged(@Nonnull LoadedAssetsEvent<String, BlockType, BlockTypeAssetMap<String, BlockType>> event) {
/* 54 */     for (BlockType blockType : event.getLoadedAssets().values()) {
/* 55 */       ConnectedBlockRuleSet ruleSet = blockType.getConnectedBlockRuleSet();
/*    */       
/* 57 */       if (ruleSet != null)
/* 58 */         ruleSet.updateCachedBlockTypes(blockType, (BlockTypeAssetMap<String, BlockType>)event.getAssetMap()); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\connectedblocks\ConnectedBlocksModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */