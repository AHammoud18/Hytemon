/*    */ package io.sentry.util.network;
/*    */ 
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class NetworkRequestData {
/*    */   @Nullable
/*    */   private final String method;
/*    */   @Nullable
/*    */   private Integer statusCode;
/*    */   @Nullable
/*    */   private Long requestBodySize;
/*    */   @Nullable
/*    */   private Long responseBodySize;
/*    */   @Nullable
/*    */   private ReplayNetworkRequestOrResponse request;
/*    */   @Nullable
/*    */   private ReplayNetworkRequestOrResponse response;
/*    */   
/*    */   public NetworkRequestData(@Nullable String method) {
/* 21 */     this.method = method;
/*    */   }
/*    */   @Nullable
/*    */   public String getMethod() {
/* 25 */     return this.method;
/*    */   }
/*    */   @Nullable
/*    */   public Integer getStatusCode() {
/* 29 */     return this.statusCode;
/*    */   }
/*    */   @Nullable
/*    */   public Long getRequestBodySize() {
/* 33 */     return this.requestBodySize;
/*    */   }
/*    */   @Nullable
/*    */   public Long getResponseBodySize() {
/* 37 */     return this.responseBodySize;
/*    */   }
/*    */   @Nullable
/*    */   public ReplayNetworkRequestOrResponse getRequest() {
/* 41 */     return this.request;
/*    */   }
/*    */   @Nullable
/*    */   public ReplayNetworkRequestOrResponse getResponse() {
/* 45 */     return this.response;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRequestDetails(@NotNull ReplayNetworkRequestOrResponse requestData) {
/* 53 */     this.request = requestData;
/* 54 */     this.requestBodySize = requestData.getSize();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setResponseDetails(int statusCode, @NotNull ReplayNetworkRequestOrResponse responseData) {
/* 63 */     this.statusCode = Integer.valueOf(statusCode);
/* 64 */     this.response = responseData;
/* 65 */     this.responseBodySize = responseData.getSize();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 70 */     return "NetworkRequestData{method='" + this.method + '\'' + ", statusCode=" + this.statusCode + ", requestBodySize=" + this.requestBodySize + ", responseBodySize=" + this.responseBodySize + ", request=" + this.request + ", response=" + this.response + '}';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\network\NetworkRequestData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */