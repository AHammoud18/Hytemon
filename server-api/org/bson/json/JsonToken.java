/*    */ package org.bson.json;
/*    */ 
/*    */ import org.bson.BsonDouble;
/*    */ import org.bson.types.Decimal128;
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
/*    */ 
/*    */ 
/*    */ class JsonToken
/*    */ {
/*    */   private final Object value;
/*    */   private final JsonTokenType type;
/*    */   
/*    */   JsonToken(JsonTokenType type, Object value) {
/* 33 */     this.value = value;
/* 34 */     this.type = type;
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 38 */     return this.value;
/*    */   }
/*    */   
/*    */   public <T> T getValue(Class<T> clazz) {
/*    */     try {
/* 43 */       if (Long.class == clazz) {
/* 44 */         if (this.value instanceof Integer)
/* 45 */           return clazz.cast(Long.valueOf(((Integer)this.value).longValue())); 
/* 46 */         if (this.value instanceof String) {
/* 47 */           return clazz.cast(Long.valueOf((String)this.value));
/*    */         }
/* 49 */       } else if (Integer.class == clazz) {
/* 50 */         if (this.value instanceof String) {
/* 51 */           return clazz.cast(Integer.valueOf((String)this.value));
/*    */         }
/* 53 */       } else if (Double.class == clazz) {
/* 54 */         if (this.value instanceof String) {
/* 55 */           return clazz.cast(Double.valueOf((String)this.value));
/*    */         }
/* 57 */       } else if (Decimal128.class == clazz) {
/* 58 */         if (this.value instanceof Integer)
/* 59 */           return clazz.cast(new Decimal128(((Integer)this.value).intValue())); 
/* 60 */         if (this.value instanceof Long)
/* 61 */           return clazz.cast(new Decimal128(((Long)this.value).longValue())); 
/* 62 */         if (this.value instanceof Double)
/* 63 */           return clazz.cast((new BsonDouble(((Double)this.value).doubleValue())).decimal128Value()); 
/* 64 */         if (this.value instanceof String) {
/* 65 */           return clazz.cast(Decimal128.parse((String)this.value));
/*    */         }
/*    */       } 
/*    */       
/* 69 */       return clazz.cast(this.value);
/* 70 */     } catch (Exception e) {
/* 71 */       throw new JsonParseException(String.format("Exception converting value '%s' to type %s", new Object[] { this.value, clazz.getName() }), e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public JsonTokenType getType() {
/* 76 */     return this.type;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\JsonToken.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */