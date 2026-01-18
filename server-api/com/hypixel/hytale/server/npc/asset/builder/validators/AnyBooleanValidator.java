/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class AnyBooleanValidator
/*    */   extends Validator
/*    */ {
/*    */   @Nonnull
/*    */   private final String[] attributes;
/*    */   
/*    */   private AnyBooleanValidator(@Nonnull String[] attributes) {
/* 14 */     Objects.requireNonNull(attributes);
/* 15 */     this.attributes = attributes;
/*    */   }
/*    */   
/*    */   public static boolean test(@Nonnull boolean[] values) {
/* 19 */     for (boolean value : values) {
/* 20 */       if (value)
/* 21 */         return true; 
/*    */     } 
/* 23 */     return false;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String errorMessage(String[] attributes) {
/* 28 */     return "At least one of " + String.join(" ", (CharSequence[])attributes) + " must be true";
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage() {
/* 33 */     return errorMessage(this.attributes);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static AnyBooleanValidator withAttributes(String attribute1, String attribute2) {
/* 38 */     return new AnyBooleanValidator(new String[] { attribute1, attribute2 });
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static AnyBooleanValidator withAttributes(@Nonnull String[] attributes) {
/* 43 */     return new AnyBooleanValidator(attributes);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\AnyBooleanValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */