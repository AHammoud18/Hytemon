/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class StringArrayNoEmptyStringsValidator
/*    */   extends StringArrayValidator
/*    */ {
/* 10 */   private static final StringArrayNoEmptyStringsValidator INSTANCE = new StringArrayNoEmptyStringsValidator();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean test(@Nullable String[] list) {
/* 17 */     if (list == null) return true; 
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
/* 32 */     return name + " must not contain empty strings";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String[] list) {
/* 38 */     return errorMessage(null, list);
/*    */   }
/*    */   
/*    */   public static StringArrayNoEmptyStringsValidator get() {
/* 42 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\StringArrayNoEmptyStringsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */