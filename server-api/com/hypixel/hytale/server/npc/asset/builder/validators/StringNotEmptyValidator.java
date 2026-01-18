/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class StringNotEmptyValidator
/*    */   extends StringValidator
/*    */ {
/* 10 */   private static final StringNotEmptyValidator INSTANCE = new StringNotEmptyValidator();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean test(@Nullable String value) {
/* 17 */     return (value != null && !value.isEmpty());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String value) {
/* 23 */     return errorMessage0(value, "Value");
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String value, String name) {
/* 29 */     return errorMessage0(value, "\"" + name + "\"");
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   private String errorMessage0(String value, String name) {
/* 34 */     return name + " must not be an empty string";
/*    */   }
/*    */   
/*    */   public static StringNotEmptyValidator get() {
/* 38 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\StringNotEmptyValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */