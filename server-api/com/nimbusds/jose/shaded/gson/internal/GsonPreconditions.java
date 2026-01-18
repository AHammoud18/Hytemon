/*    */ package com.nimbusds.jose.shaded.gson.internal;
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
/*    */ 
/*    */ 
/*    */ public final class GsonPreconditions
/*    */ {
/*    */   private GsonPreconditions() {
/* 37 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static <T> T checkNotNull(T obj) {
/* 47 */     if (obj == null) {
/* 48 */       throw new NullPointerException();
/*    */     }
/* 50 */     return obj;
/*    */   }
/*    */   
/*    */   public static void checkArgument(boolean condition) {
/* 54 */     if (!condition)
/* 55 */       throw new IllegalArgumentException(); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\internal\GsonPreconditions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */