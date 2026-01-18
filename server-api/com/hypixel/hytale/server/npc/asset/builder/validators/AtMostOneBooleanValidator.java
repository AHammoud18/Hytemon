/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AtMostOneBooleanValidator
/*    */   extends Validator
/*    */ {
/*    */   @Nonnull
/*    */   private final String[] attributes;
/*    */   
/*    */   private AtMostOneBooleanValidator(@Nonnull String[] attributes) {
/* 15 */     Objects.requireNonNull(attributes);
/* 16 */     this.attributes = attributes;
/*    */   }
/*    */   
/*    */   public static boolean test(@Nonnull boolean[] values) {
/* 20 */     int count = 0;
/* 21 */     for (boolean value : values) {
/* 22 */       if (value) count++; 
/*    */     } 
/* 24 */     return (count <= 1);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String errorMessage(String[] attributes) {
/* 29 */     return "At most one of " + String.join(" ", (CharSequence[])attributes) + " can be true";
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage() {
/* 34 */     return errorMessage(this.attributes);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static AtMostOneBooleanValidator withAttributes(String attribute1, String attribute2) {
/* 39 */     return new AtMostOneBooleanValidator(new String[] { attribute1, attribute2 });
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static AtMostOneBooleanValidator withAttributes(@Nonnull String[] attributes) {
/* 44 */     return new AtMostOneBooleanValidator(attributes);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\AtMostOneBooleanValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */