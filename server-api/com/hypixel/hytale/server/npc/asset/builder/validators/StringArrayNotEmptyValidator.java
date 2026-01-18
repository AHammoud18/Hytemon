/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class StringArrayNotEmptyValidator
/*    */   extends StringArrayValidator
/*    */ {
/* 10 */   private static final StringArrayNotEmptyValidator INSTANCE = new StringArrayNotEmptyValidator();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean test(@Nullable String[] list) {
/* 17 */     if (list == null || list.length == 0) return false; 
/* 18 */     for (String s : list) {
/* 19 */       if (s == null || s.isEmpty()) return false; 
/*    */     } 
/* 21 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(@Nullable String name, String[] list) {
/* 27 */     if (name == null) {
/* 28 */       name = "StringList";
/*    */     } else {
/* 30 */       name = "'" + name + "'";
/*    */     } 
/* 32 */     return name + " must not be empty or contain empty strings";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String[] list) {
/* 38 */     return errorMessage(null, list);
/*    */   }
/*    */   
/*    */   public static StringArrayNotEmptyValidator get() {
/* 42 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\StringArrayNotEmptyValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */