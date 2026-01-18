/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class StringsNotEmptyValidator
/*    */   extends Validator
/*    */ {
/*    */   private final String[] attributes;
/*    */   
/*    */   private StringsNotEmptyValidator(String[] attributes) {
/* 13 */     this.attributes = attributes;
/*    */   }
/*    */   
/*    */   public static boolean test(@Nullable String string1, @Nullable String string2) {
/* 17 */     return ((string1 != null && !string1.isEmpty()) || (string2 != null && !string2.isEmpty()));
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String errorMessage(String string1, String string2, String context) {
/* 22 */     return errorMessage(string1, "Value1", string2, "Value2", context);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String errorMessage(String string1, String attribute1, String string2, String attribute2, String context) {
/* 27 */     return String.format("Either '%s' or '%s' must be non empty in %s", new Object[] { attribute1, attribute2, context });
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static StringsNotEmptyValidator withAttributes(String attribute1, String attribute2) {
/* 32 */     return new StringsNotEmptyValidator(new String[] { attribute1, attribute2 });
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static StringsNotEmptyValidator withAttributes(String[] attributes) {
/* 37 */     return new StringsNotEmptyValidator(attributes);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\StringsNotEmptyValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */