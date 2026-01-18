/*    */ package io.sentry.transport;
/*    */ 
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TransportResult
/*    */ {
/*    */   @NotNull
/*    */   public static TransportResult success() {
/* 18 */     return SuccessTransportResult.INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public static TransportResult error(int responseCode) {
/* 28 */     return new ErrorTransportResult(responseCode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public static TransportResult error() {
/* 38 */     return error(-1);
/*    */   }
/*    */   
/*    */   private TransportResult() {}
/*    */   
/*    */   public abstract boolean isSuccess();
/*    */   
/*    */   public abstract int getResponseCode();
/*    */   
/*    */   private static final class SuccessTransportResult extends TransportResult {
/* 48 */     static final SuccessTransportResult INSTANCE = new SuccessTransportResult();
/*    */ 
/*    */     
/*    */     public boolean isSuccess() {
/* 52 */       return true;
/*    */     }
/*    */ 
/*    */     
/*    */     public int getResponseCode() {
/* 57 */       return -1;
/*    */     }
/*    */   }
/*    */   
/*    */   private static final class ErrorTransportResult extends TransportResult {
/*    */     private final int responseCode;
/*    */     
/*    */     ErrorTransportResult(int responseCode) {
/* 65 */       this.responseCode = responseCode;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isSuccess() {
/* 70 */       return false;
/*    */     }
/*    */ 
/*    */     
/*    */     public int getResponseCode() {
/* 75 */       return this.responseCode;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\transport\TransportResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */