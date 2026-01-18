/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class StringsOneSetValidator extends Validator {
/*    */   private final String[] attributes;
/*    */   
/*    */   private StringsOneSetValidator(String[] attributes) {
/* 10 */     this.attributes = attributes;
/*    */   }
/*    */   
/*    */   public static boolean test(@Nullable String string1, @Nullable String string2) {
/* 14 */     boolean str1IsEmpty = (string1 == null || string1.isEmpty());
/* 15 */     boolean str2IsEmpty = (string2 == null || string2.isEmpty());
/* 16 */     return (str1IsEmpty != str2IsEmpty);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String errorMessage(String string1, String string2, String context) {
/* 21 */     return errorMessage(string1, "Value1", string2, "Value2", context);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String errorMessage(String string1, String attribute1, String string2, String attribute2, String context) {
/* 26 */     return formatErrorMessage(string1, attribute1, string2, attribute2, context);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String formatErrorMessage(String string1, String attribute1, String string2, String attribute2, String context) {
/* 31 */     return String.format("Only %s or %s must be set to some value in %s.", new Object[] { attribute1, attribute2, context });
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static StringsOneSetValidator withAttributes(String attribute1, String attribute2) {
/* 36 */     return new StringsOneSetValidator(new String[] { attribute1, attribute2 });
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static StringsOneSetValidator withAttributes(String[] attributes) {
/* 41 */     return new StringsOneSetValidator(attributes);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\StringsOneSetValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */