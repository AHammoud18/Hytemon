/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class StringsAtMostOneValidator extends Validator {
/*    */   private final String[] attributes;
/*    */   
/*    */   private StringsAtMostOneValidator(String[] attributes) {
/* 10 */     this.attributes = attributes;
/*    */   }
/*    */   
/*    */   public static boolean test(@Nullable String string1, @Nullable String string2) {
/* 14 */     return (string1 == null || string1.isEmpty() || string2 == null || string2
/* 15 */       .isEmpty());
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String errorMessage(String string1, String string2, String context) {
/* 20 */     return errorMessage(string1, "Value1", string2, "Value2", context);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String errorMessage(String string1, String attribute1, String string2, String attribute2, String context) {
/* 25 */     return String.format("Both %s and %s are set to values. At most only 1 of the variables should be set in %s.", new Object[] { attribute1, attribute2, context });
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static StringsAtMostOneValidator withAttributes(String attribute1, String attribute2) {
/* 30 */     return new StringsAtMostOneValidator(new String[] { attribute1, attribute2 });
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static StringsAtMostOneValidator withAttributes(String[] attributes) {
/* 35 */     return new StringsAtMostOneValidator(attributes);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\StringsAtMostOneValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */