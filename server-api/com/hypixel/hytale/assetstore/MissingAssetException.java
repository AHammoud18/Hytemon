/*    */ package com.hypixel.hytale.assetstore;
/*    */ 
/*    */ import com.hypixel.hytale.codec.ExtraInfo;
/*    */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MissingAssetException
/*    */   extends RuntimeException
/*    */ {
/*    */   private String field;
/*    */   private Class<? extends JsonAsset> assetType;
/*    */   private Object assetId;
/*    */   
/*    */   public MissingAssetException(String field, @Nonnull Class<? extends JsonAsset> assetType, Object assetId) {
/* 18 */     super("Missing asset '" + String.valueOf(assetId) + "' of type " + assetType.getSimpleName() + " for field '" + field + "'!");
/* 19 */     this.field = field;
/* 20 */     this.assetType = assetType;
/* 21 */     this.assetId = assetId;
/*    */   }
/*    */   
/*    */   public MissingAssetException(String field, @Nonnull Class<? extends JsonAsset> assetType, Object assetId, String extra) {
/* 25 */     super("Missing asset '" + String.valueOf(assetId) + "' of type " + assetType.getSimpleName() + " for field '" + field + "'! " + extra);
/* 26 */     this.field = field;
/* 27 */     this.assetType = assetType;
/* 28 */     this.assetId = assetId;
/*    */   }
/*    */   
/*    */   public String getField() {
/* 32 */     return this.field;
/*    */   }
/*    */   
/*    */   public Class<? extends JsonAsset> getAssetType() {
/* 36 */     return this.assetType;
/*    */   }
/*    */   
/*    */   public Object getAssetId() {
/* 40 */     return this.assetId;
/*    */   }
/*    */   
/*    */   public static void handle(@Nonnull ExtraInfo extraInfo, String field, @Nonnull Class<? extends JsonAsset> assetType, Object assetId) {
/* 44 */     ValidationResults validationResults = extraInfo.getValidationResults();
/* 45 */     if (validationResults instanceof AssetValidationResults) {
/* 46 */       ((AssetValidationResults)validationResults).handleMissingAsset(field, assetType, assetId);
/*    */     } else {
/* 48 */       throw new MissingAssetException(field, assetType, assetId);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void handle(@Nonnull ExtraInfo extraInfo, String field, @Nonnull Class<? extends JsonAsset> assetType, Object assetId, String extra) {
/* 53 */     ValidationResults validationResults = extraInfo.getValidationResults();
/* 54 */     if (validationResults instanceof AssetValidationResults) {
/* 55 */       ((AssetValidationResults)validationResults).handleMissingAsset(field, assetType, assetId, extra);
/*    */     } else {
/* 57 */       throw new MissingAssetException(field, assetType, assetId, extra);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\MissingAssetException.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */