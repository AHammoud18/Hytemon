/*    */ package io.sentry;
/*    */ 
/*    */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ @Experimental
/*    */ public final class UpdateInfo
/*    */ {
/*    */   @NotNull
/*    */   private final String id;
/*    */   @NotNull
/*    */   private final String buildVersion;
/*    */   private final int buildNumber;
/*    */   @NotNull
/*    */   private final String downloadUrl;
/*    */   @NotNull
/*    */   private final String appName;
/*    */   @Nullable
/*    */   private final String createdDate;
/*    */   
/*    */   public UpdateInfo(@NotNull String id, @NotNull String buildVersion, int buildNumber, @NotNull String downloadUrl, @NotNull String appName, @Nullable String createdDate) {
/* 24 */     this.id = id;
/* 25 */     this.buildVersion = buildVersion;
/* 26 */     this.buildNumber = buildNumber;
/* 27 */     this.downloadUrl = downloadUrl;
/* 28 */     this.appName = appName;
/* 29 */     this.createdDate = createdDate;
/*    */   }
/*    */   @NotNull
/*    */   public String getId() {
/* 33 */     return this.id;
/*    */   }
/*    */   @NotNull
/*    */   public String getBuildVersion() {
/* 37 */     return this.buildVersion;
/*    */   }
/*    */   
/*    */   public int getBuildNumber() {
/* 41 */     return this.buildNumber;
/*    */   }
/*    */   @NotNull
/*    */   public String getDownloadUrl() {
/* 45 */     return this.downloadUrl;
/*    */   }
/*    */   @NotNull
/*    */   public String getAppName() {
/* 49 */     return this.appName;
/*    */   }
/*    */   @Nullable
/*    */   public String getCreatedDate() {
/* 53 */     return this.createdDate;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 58 */     return "UpdateInfo{id='" + this.id + '\'' + ", buildVersion='" + this.buildVersion + '\'' + ", buildNumber=" + this.buildNumber + ", downloadUrl='" + this.downloadUrl + '\'' + ", appName='" + this.appName + '\'' + ", createdDate='" + this.createdDate + '\'' + '}';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\UpdateInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */