/*    */ package com.google.crypto.tink.jwt.internal;
/*    */ 
/*    */ import com.google.crypto.tink.internal.JsonParser;
/*    */ import com.google.crypto.tink.jwt.JwtInvalidException;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonObject;
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
/*    */ public final class JsonUtil
/*    */ {
/*    */   public static boolean isValidString(String s) {
/* 30 */     return JsonParser.isValidString(s);
/*    */   }
/*    */   
/*    */   public static JsonObject parseJson(String jsonString) throws JwtInvalidException {
/*    */     try {
/* 35 */       return JsonParser.parse(jsonString).getAsJsonObject();
/* 36 */     } catch (IllegalStateException|com.google.gson.JsonParseException|java.io.IOException ex) {
/* 37 */       throw new JwtInvalidException("invalid JSON: " + ex);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static JsonArray parseJsonArray(String jsonString) throws JwtInvalidException {
/*    */     try {
/* 43 */       return JsonParser.parse(jsonString).getAsJsonArray();
/* 44 */     } catch (IllegalStateException|com.google.gson.JsonParseException|java.io.IOException ex) {
/* 45 */       throw new JwtInvalidException("invalid JSON: " + ex);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\internal\JsonUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */