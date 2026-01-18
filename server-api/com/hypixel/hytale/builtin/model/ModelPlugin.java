/*    */ package com.hypixel.hytale.builtin.model;
/*    */ import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.builtin.model.commands.ModelCommand;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*    */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ModelPlugin extends JavaPlugin {
/*    */   public ModelPlugin(@Nonnull JavaPluginInit init) {
/* 22 */     super(init);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 27 */     getCommandRegistry().registerCommand((AbstractCommand)new ModelCommand());
/*    */     
/* 29 */     getEventRegistry().register(LoadedAssetsEvent.class, ModelAsset.class, this::updateModelAssets);
/*    */   }
/*    */   
/*    */   private void checkForModelUpdate(@Nonnull Map<String, ModelAsset> reloadedModelAssets, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 33 */     ModelComponent modelComponent = (ModelComponent)archetypeChunk.getComponent(index, ModelComponent.getComponentType());
/* 34 */     Model oldModel = modelComponent.getModel();
/*    */     
/* 36 */     ModelAsset newModel = reloadedModelAssets.get(oldModel.getModelAssetId());
/* 37 */     if (newModel != null) {
/* 38 */       Model model = Model.createScaledModel(newModel, oldModel.getScale(), oldModel.getRandomAttachmentIds());
/* 39 */       commandBuffer.putComponent(archetypeChunk.getReferenceTo(index), ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/*    */     } 
/*    */   }
/*    */   
/*    */   private void updateModelAssets(@Nonnull LoadedAssetsEvent<String, ModelAsset, DefaultAssetMap<String, ModelAsset>> event) {
/* 44 */     Map<String, ModelAsset> map = event.getLoadedAssets();
/*    */     
/* 46 */     Universe.get().getWorlds().forEach((name, world) -> world.execute(()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\model\ModelPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */