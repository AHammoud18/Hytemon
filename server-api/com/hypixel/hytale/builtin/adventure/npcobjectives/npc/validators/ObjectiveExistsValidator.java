/*    */ package com.hypixel.hytale.builtin.adventure.npcobjectives.npc.validators;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.ObjectiveAsset;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObjectiveExistsValidator
/*    */   extends AssetValidator
/*    */ {
/* 14 */   private static final ObjectiveExistsValidator DEFAULT_INSTANCE = new ObjectiveExistsValidator();
/*    */ 
/*    */   
/*    */   private ObjectiveExistsValidator() {}
/*    */ 
/*    */   
/*    */   private ObjectiveExistsValidator(EnumSet<AssetValidator.Config> config) {
/* 21 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 27 */     return "Objective";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String objective) {
/* 32 */     return (ObjectiveAsset.getAssetMap().getAsset(objective) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String objective, String attributeName) {
/* 38 */     return "The objective with the name \"" + objective + "\" does not exist for attribute \"" + attributeName + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 44 */     return ObjectiveAsset.class.getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ObjectiveExistsValidator required() {
/* 53 */     return DEFAULT_INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ObjectiveExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 58 */     return new ObjectiveExistsValidator(config);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcobjectives\npc\validators\ObjectiveExistsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */