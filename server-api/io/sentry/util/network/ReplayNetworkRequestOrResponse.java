/*    */ package io.sentry.util.network;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ReplayNetworkRequestOrResponse
/*    */ {
/*    */   @Nullable
/*    */   private final Long size;
/*    */   @Nullable
/*    */   private final NetworkBody body;
/*    */   @NotNull
/*    */   private final Map<String, String> headers;
/*    */   
/*    */   public ReplayNetworkRequestOrResponse(@Nullable Long size, @Nullable NetworkBody body, @NotNull Map<String, String> headers) {
/* 21 */     this.size = size;
/* 22 */     this.body = body;
/* 23 */     this.headers = headers;
/*    */   }
/*    */   @Nullable
/*    */   public Long getSize() {
/* 27 */     return this.size;
/*    */   }
/*    */   @Nullable
/*    */   public NetworkBody getBody() {
/* 31 */     return this.body;
/*    */   }
/*    */   @NotNull
/*    */   public Map<String, String> getHeaders() {
/* 35 */     return this.headers;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 40 */     return "ReplayNetworkRequestOrResponse{size=" + this.size + ", body=" + this.body + ", headers=" + this.headers + '}';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\network\ReplayNetworkRequestOrResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */