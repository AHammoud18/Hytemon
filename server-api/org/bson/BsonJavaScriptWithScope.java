/*     */ package org.bson;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BsonJavaScriptWithScope
/*     */   extends BsonValue
/*     */ {
/*     */   private final String code;
/*     */   private final BsonDocument scope;
/*     */   
/*     */   public BsonJavaScriptWithScope(String code, BsonDocument scope) {
/*  36 */     if (code == null) {
/*  37 */       throw new IllegalArgumentException("code can not be null");
/*     */     }
/*  39 */     if (scope == null) {
/*  40 */       throw new IllegalArgumentException("scope can not be null");
/*     */     }
/*  42 */     this.code = code;
/*  43 */     this.scope = scope;
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonType getBsonType() {
/*  48 */     return BsonType.JAVASCRIPT_WITH_SCOPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCode() {
/*  57 */     return this.code;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonDocument getScope() {
/*  66 */     return this.scope;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  71 */     if (this == o) {
/*  72 */       return true;
/*     */     }
/*  74 */     if (o == null || getClass() != o.getClass()) {
/*  75 */       return false;
/*     */     }
/*     */     
/*  78 */     BsonJavaScriptWithScope that = (BsonJavaScriptWithScope)o;
/*     */     
/*  80 */     if (!this.code.equals(that.code)) {
/*  81 */       return false;
/*     */     }
/*  83 */     if (!this.scope.equals(that.scope)) {
/*  84 */       return false;
/*     */     }
/*     */     
/*  87 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  92 */     int result = this.code.hashCode();
/*  93 */     result = 31 * result + this.scope.hashCode();
/*  94 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  99 */     return "BsonJavaScriptWithScope{code=" + 
/* 100 */       getCode() + "scope=" + this.scope + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static BsonJavaScriptWithScope clone(BsonJavaScriptWithScope from) {
/* 106 */     return new BsonJavaScriptWithScope(from.code, from.scope.clone());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonJavaScriptWithScope.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */