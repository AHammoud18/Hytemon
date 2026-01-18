/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators.asset;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnvironmentExistsValidator
/*    */   extends AssetValidator
/*    */ {
/* 13 */   public static final EnvironmentExistsValidator DEFAULT_INSTANCE = new EnvironmentExistsValidator();
/*    */ 
/*    */   
/*    */   private EnvironmentExistsValidator() {}
/*    */ 
/*    */   
/*    */   private EnvironmentExistsValidator(EnumSet<AssetValidator.Config> config) {
/* 20 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 26 */     return "Environment";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String envName) {
/* 31 */     return (Environment.getAssetMap().getAsset(envName) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String envName, String attribute) {
/* 37 */     return "The environment with the file name \"" + envName + "\" does not exist in attribute \"" + attribute + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 43 */     return Environment.class.getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static EnvironmentExistsValidator required() {
/* 51 */     return DEFAULT_INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static EnvironmentExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 56 */     return new EnvironmentExistsValidator(config);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\asset\EnvironmentExistsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */