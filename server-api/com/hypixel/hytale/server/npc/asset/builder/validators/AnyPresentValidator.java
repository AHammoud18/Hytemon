/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderObjectHelper;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnyPresentValidator
/*    */   extends Validator
/*    */ {
/*    */   @Nonnull
/*    */   private final String[] attributes;
/*    */   
/*    */   private AnyPresentValidator(@Nonnull String[] attributes) {
/* 16 */     Objects.requireNonNull(attributes);
/* 17 */     this.attributes = attributes;
/*    */   }
/*    */   
/*    */   public static boolean test(@Nonnull BuilderObjectHelper<?>[] objects) {
/* 21 */     for (BuilderObjectHelper<?> object : objects) {
/* 22 */       if (object.isPresent())
/* 23 */         return true; 
/*    */     } 
/* 25 */     return false;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String errorMessage(String[] attributes) {
/* 30 */     return "At least one of " + String.join(" ", (CharSequence[])attributes) + " must be present";
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage() {
/* 35 */     return errorMessage(this.attributes);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static AnyPresentValidator withAttributes(String attribute1, String attribute2) {
/* 40 */     return new AnyPresentValidator(new String[] { attribute1, attribute2 });
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static AnyPresentValidator withAttributes(@Nonnull String[] attributes) {
/* 45 */     return new AnyPresentValidator(attributes);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\AnyPresentValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */