/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArraysOneSetValidator
/*    */   extends Validator
/*    */ {
/*    */   private final String[] attributes;
/*    */   
/*    */   private ArraysOneSetValidator(String[] attributes) {
/* 14 */     this.attributes = attributes;
/*    */   }
/*    */   
/*    */   public static boolean validate(String[] value1, String[] value2) {
/* 18 */     return (arrayContainsNonEmptyString(value1) || arrayContainsNonEmptyString(value2));
/*    */   }
/*    */   
/*    */   private static boolean arrayContainsNonEmptyString(@Nullable String[] array) {
/* 22 */     if (array != null) {
/* 23 */       for (String value : array) {
/* 24 */         if (value != null && !value.isEmpty()) {
/* 25 */           return true;
/*    */         }
/*    */       } 
/*    */     }
/* 29 */     return false;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String formatErrorMessage(String attr1, String attr2, String context) {
/* 34 */     return String.format("%s or %s must be provided in %s!", new Object[] { attr1, attr2, context });
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ArraysOneSetValidator withAttributes(String attribute1, String attribute2) {
/* 39 */     return new ArraysOneSetValidator(new String[] { attribute1, attribute2 });
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ArraysOneSetValidator withAttributes(String[] attributes) {
/* 44 */     return new ArraysOneSetValidator(attributes);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\ArraysOneSetValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */