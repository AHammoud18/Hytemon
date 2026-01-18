/*    */ package io.sentry.util.network;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class NetworkBody
/*    */ {
/*    */   @Nullable
/*    */   private final Object body;
/*    */   @Nullable
/*    */   private final List<NetworkBodyWarning> warnings;
/*    */   
/*    */   public NetworkBody(@Nullable Object body) {
/* 21 */     this(body, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public NetworkBody(@Nullable Object body, @Nullable List<NetworkBodyWarning> warnings) {
/* 26 */     this.body = body;
/* 27 */     this.warnings = warnings;
/*    */   }
/*    */   @Nullable
/*    */   public Object getBody() {
/* 31 */     return this.body;
/*    */   }
/*    */   @Nullable
/*    */   public List<NetworkBodyWarning> getWarnings() {
/* 35 */     return this.warnings;
/*    */   }
/*    */ 
/*    */   
/*    */   public enum NetworkBodyWarning
/*    */   {
/* 41 */     JSON_TRUNCATED("JSON_TRUNCATED"),
/* 42 */     TEXT_TRUNCATED("TEXT_TRUNCATED"),
/* 43 */     INVALID_JSON("INVALID_JSON"),
/* 44 */     BODY_PARSE_ERROR("BODY_PARSE_ERROR");
/*    */     
/*    */     private final String value;
/*    */     
/*    */     NetworkBodyWarning(String value) {
/* 49 */       this.value = value;
/*    */     }
/*    */     
/*    */     public String getValue() {
/* 53 */       return this.value;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 59 */     return "NetworkBody{body=" + this.body + ", warnings=" + this.warnings + '}';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\network\NetworkBody.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */