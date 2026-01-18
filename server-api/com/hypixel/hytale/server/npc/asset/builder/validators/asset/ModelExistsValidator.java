/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators.asset;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ModelExistsValidator
/*    */   extends AssetValidator {
/* 10 */   private static final ModelExistsValidator DEFAULT_INSTANCE = new ModelExistsValidator();
/*    */ 
/*    */   
/*    */   private ModelExistsValidator() {}
/*    */ 
/*    */   
/*    */   private ModelExistsValidator(EnumSet<AssetValidator.Config> config) {
/* 17 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 23 */     return "Model";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String model) {
/* 28 */     return (ModelAsset.getAssetMap().getAsset(model) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String model, String attributeName) {
/* 34 */     return "The model with the name \"" + model + "\" does not exist for attribute \"" + attributeName + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 40 */     return ModelAsset.class.getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ModelExistsValidator required() {
/* 49 */     return DEFAULT_INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ModelExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 54 */     return new ModelExistsValidator(config);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\asset\ModelExistsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */