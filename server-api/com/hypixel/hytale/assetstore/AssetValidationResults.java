/*    */ package com.hypixel.hytale.assetstore;
/*    */ 
/*    */ import com.hypixel.hytale.codec.ExtraInfo;
/*    */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.logger.backend.HytaleLoggerBackend;
/*    */ import com.hypixel.hytale.logger.util.GithubMessageUtil;
/*    */ import java.nio.file.Path;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*    */ 
/*    */ public class AssetValidationResults
/*    */   extends ValidationResults {
/*    */   private Set<Class<? extends JsonAsset>> disabledMissingAssetClasses;
/*    */   
/*    */   public AssetValidationResults(ExtraInfo extraInfo) {
/* 20 */     super(extraInfo);
/*    */   }
/*    */   
/*    */   public void handleMissingAsset(String field, @Nonnull Class<? extends JsonAsset> assetType, Object assetId) {
/* 24 */     if (this.disabledMissingAssetClasses != null && this.disabledMissingAssetClasses.contains(assetType))
/* 25 */       return;  throw new MissingAssetException(field, assetType, assetId);
/*    */   }
/*    */   
/*    */   public void handleMissingAsset(String field, @Nonnull Class<? extends JsonAsset> assetType, Object assetId, String extra) {
/* 29 */     if (this.disabledMissingAssetClasses != null && this.disabledMissingAssetClasses.contains(assetType))
/* 30 */       return;  throw new MissingAssetException(field, assetType, assetId, extra);
/*    */   }
/*    */   
/*    */   public void disableMissingAssetFor(Class<? extends JsonAsset> assetType) {
/* 34 */     if (this.disabledMissingAssetClasses == null) this.disabledMissingAssetClasses = new HashSet<>(); 
/* 35 */     this.disabledMissingAssetClasses.add(assetType);
/*    */   }
/*    */ 
/*    */   
/*    */   public void logOrThrowValidatorExceptions(@NonNullDecl HytaleLogger logger, @NonNullDecl String msg) {
/* 40 */     logOrThrowValidatorExceptions(logger, msg, (Path)null, 0);
/*    */   }
/*    */   
/*    */   public void logOrThrowValidatorExceptions(@NonNullDecl HytaleLogger logger, @NonNullDecl String msg, @Nullable Path path, int lineOffset) {
/* 44 */     if (GithubMessageUtil.isGithub() && this.validatorExceptions != null && !this.validatorExceptions.isEmpty()) {
/* 45 */       for (ValidationResults.ValidatorResultsHolder holder : this.validatorExceptions) {
/* 46 */         String file = "unknown";
/* 47 */         if (path == null) { ExtraInfo extraInfo = this.extraInfo; if (extraInfo instanceof AssetExtraInfo) { AssetExtraInfo<?> assetExtraInfo = (AssetExtraInfo)extraInfo;
/* 48 */             path = assetExtraInfo.getAssetPath(); }
/*    */            }
/* 50 */          if (path != null) file = path.toString();
/*    */         
/* 52 */         for (ValidationResults.ValidationResult result : holder.results()) {
/* 53 */           switch (result.result()) { default: throw new MatchException(null, null);
/*    */             case SUCCESS: 
/*    */             case WARNING:
/* 56 */               if (holder.line() == -1);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */             
/*    */             case FAIL:
/* 63 */               if (holder.line() == -1);
/*    */               break; }
/*    */           
/* 66 */           HytaleLoggerBackend.rawLog(GithubMessageUtil.messageError(file, holder.line() + lineOffset, holder.column(), result.reason()));
/*    */         } 
/*    */       } 
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 73 */     super.logOrThrowValidatorExceptions(logger, msg);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\AssetValidationResults.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */