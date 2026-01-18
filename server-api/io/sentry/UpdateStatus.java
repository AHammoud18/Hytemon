/*    */ package io.sentry;
/*    */ 
/*    */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ @Experimental
/*    */ public abstract class UpdateStatus
/*    */ {
/*    */   public static final class UpToDate
/*    */     extends UpdateStatus
/*    */   {
/* 12 */     private static final UpToDate INSTANCE = new UpToDate();
/*    */ 
/*    */ 
/*    */     
/*    */     public static UpToDate getInstance() {
/* 17 */       return INSTANCE;
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 22 */       return "UpdateStatus.UpToDate{}";
/*    */     }
/*    */   }
/*    */   
/*    */   public static final class NewRelease extends UpdateStatus {
/*    */     @NotNull
/*    */     private final UpdateInfo info;
/*    */     
/*    */     public NewRelease(@NotNull UpdateInfo info) {
/* 31 */       this.info = info;
/*    */     }
/*    */     @NotNull
/*    */     public UpdateInfo getInfo() {
/* 35 */       return this.info;
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 40 */       return "UpdateStatus.NewRelease{info=" + this.info + '}';
/*    */     }
/*    */   }
/*    */   
/*    */   public static final class UpdateError extends UpdateStatus {
/*    */     @NotNull
/*    */     private final String message;
/*    */     
/*    */     public UpdateError(@NotNull String message) {
/* 49 */       this.message = message;
/*    */     }
/*    */     @NotNull
/*    */     public String getMessage() {
/* 53 */       return this.message;
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 58 */       return "UpdateStatus.UpdateError{message='" + this.message + '\'' + '}';
/*    */     }
/*    */   }
/*    */   
/*    */   public static final class NoNetwork extends UpdateStatus {
/*    */     @NotNull
/*    */     private final String message;
/*    */     
/*    */     public NoNetwork(@NotNull String message) {
/* 67 */       this.message = message;
/*    */     }
/*    */     @NotNull
/*    */     public String getMessage() {
/* 71 */       return this.message;
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 76 */       return "UpdateStatus.NoNetwork{message='" + this.message + '\'' + '}';
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\UpdateStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */