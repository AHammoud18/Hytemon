/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators.asset;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import com.hypixel.hytale.server.npc.config.AttitudeGroup;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttitudeGroupExistsValidator
/*    */   extends AssetValidator
/*    */ {
/* 15 */   private static final AttitudeGroupExistsValidator DEFAULT_INSTANCE = new AttitudeGroupExistsValidator();
/*    */ 
/*    */   
/*    */   private AttitudeGroupExistsValidator() {}
/*    */ 
/*    */   
/*    */   private AttitudeGroupExistsValidator(EnumSet<AssetValidator.Config> config) {
/* 22 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 28 */     return "AttitudeGroup";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String attitudeGroup) {
/* 33 */     return (AttitudeGroup.getAssetMap().getAsset(attitudeGroup) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String attitudeGroup, String attributeName) {
/* 39 */     return "The attitude group with the name \"" + attitudeGroup + "\" does not exist for attribute \"" + attributeName + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 45 */     return AttitudeGroup.class.getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static AttitudeGroupExistsValidator required() {
/* 54 */     return DEFAULT_INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static AttitudeGroupExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 59 */     return new AttitudeGroupExistsValidator(config);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\asset\AttitudeGroupExistsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */