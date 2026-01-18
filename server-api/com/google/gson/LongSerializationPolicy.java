/*    */ package com.google.gson;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum LongSerializationPolicy
/*    */ {
/* 34 */   DEFAULT
/*    */   {
/*    */     public JsonElement serialize(Long value) {
/* 37 */       if (value == null) {
/* 38 */         return JsonNull.INSTANCE;
/*    */       }
/* 40 */       return new JsonPrimitive(value);
/*    */     }
/*    */   },
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   STRING
/*    */   {
/*    */     public JsonElement serialize(Long value) {
/* 53 */       if (value == null) {
/* 54 */         return JsonNull.INSTANCE;
/*    */       }
/* 56 */       return new JsonPrimitive(value.toString());
/*    */     }
/*    */   };
/*    */   
/*    */   public abstract JsonElement serialize(Long paramLong);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\LongSerializationPolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */