/*     */ package org.bson.json;
/*     */ 
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonDocumentWrapper;
/*     */ import org.bson.assertions.Assertions;
/*     */ import org.bson.codecs.Encoder;
/*     */ import org.bson.codecs.configuration.CodecRegistry;
/*     */ import org.bson.conversions.Bson;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JsonObject
/*     */   implements Bson
/*     */ {
/*     */   private final String json;
/*     */   
/*     */   public JsonObject(String json) {
/*  45 */     Assertions.notNull("Json", json);
/*     */     
/*  47 */     boolean foundBrace = false;
/*  48 */     for (int i = 0; i < json.length(); i++) {
/*  49 */       char c = json.charAt(i);
/*  50 */       if (c == '{') {
/*  51 */         foundBrace = true;
/*     */         break;
/*     */       } 
/*  54 */       Assertions.isTrueArgument("json is a valid JSON object", Character.isWhitespace(c));
/*     */     } 
/*  56 */     Assertions.isTrueArgument("json is a valid JSON object", foundBrace);
/*     */     
/*  58 */     this.json = json;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJson() {
/*  67 */     return this.json;
/*     */   }
/*     */ 
/*     */   
/*     */   public <TDocument> BsonDocument toBsonDocument(Class<TDocument> documentClass, CodecRegistry registry) {
/*  72 */     return (BsonDocument)new BsonDocumentWrapper(this, (Encoder)registry.get(JsonObject.class));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  77 */     if (this == o) {
/*  78 */       return true;
/*     */     }
/*     */     
/*  81 */     if (o == null || getClass() != o.getClass()) {
/*  82 */       return false;
/*     */     }
/*     */     
/*  85 */     JsonObject that = (JsonObject)o;
/*     */     
/*  87 */     if (!this.json.equals(that.getJson())) {
/*  88 */       return false;
/*     */     }
/*     */     
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  96 */     return this.json.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 101 */     return this.json;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\JsonObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */