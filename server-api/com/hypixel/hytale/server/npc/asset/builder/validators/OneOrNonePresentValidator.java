/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderObjectHelper;
/*    */ import java.util.Objects;
/*    */ import java.util.function.IntPredicate;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OneOrNonePresentValidator
/*    */   extends Validator
/*    */ {
/*    */   @Nonnull
/*    */   private final String[] attributes;
/*    */   
/*    */   private OneOrNonePresentValidator(String... attributes) {
/* 17 */     this.attributes = Objects.<String[]>requireNonNull(attributes, "Attributes in OneOrNonePresentValidator must not be null");
/*    */   }
/*    */   
/*    */   public static boolean test(@Nonnull BuilderObjectHelper<?>[] objects) {
/* 21 */     return (OnePresentValidator.countPresent(objects.length, i -> objects[i].isPresent()) <= 1);
/*    */   }
/*    */   
/*    */   public static boolean test(@Nonnull boolean[] readStatus) {
/* 25 */     return (OnePresentValidator.countPresent(readStatus.length, i -> readStatus[i]) <= 1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean test(@Nonnull BuilderObjectHelper<?> objectHelper1, @Nonnull BuilderObjectHelper<?> objectHelper2) {
/* 32 */     return (OnePresentValidator.countPresent(objectHelper1, objectHelper2) <= 1);
/*    */   }
/*    */   
/*    */   public static boolean test(@Nonnull BuilderObjectHelper<?> objectHelper1, @Nonnull BuilderObjectHelper<?> objectHelper2, @Nonnull BuilderObjectHelper<?> objectHelper3) {
/* 36 */     return (OnePresentValidator.countPresent(objectHelper1, objectHelper2, objectHelper3) <= 1);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String errorMessage(@Nonnull String[] attributes, BuilderObjectHelper<?>[] objectHelpers) {
/* 41 */     return errorMessage(attributes, i -> objectHelpers[i].isPresent());
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String errorMessage(@Nonnull String[] attributes, boolean[] readStatus) {
/* 46 */     return errorMessage(attributes, i -> readStatus[i]);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String errorMessage(@Nonnull String[] attributes, @Nonnull IntPredicate presentPredicate) {
/* 51 */     StringBuilder result = new StringBuilder("Exactly one or none of ");
/* 52 */     String sep = ", ";
/* 53 */     for (int i = 0; i < attributes.length; i++) {
/* 54 */       if (i == attributes.length - 1) {
/* 55 */         sep = "";
/*    */       }
/* 57 */       result.append(String.format("'%s'%s%s", new Object[] { attributes[i], presentPredicate.test(i) ? "(Present)" : "", sep }));
/*    */     } 
/* 59 */     return String.valueOf(result) + " must be present";
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static OneOrNonePresentValidator withAttributes(String... attributes) {
/* 64 */     return new OneOrNonePresentValidator(attributes);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\OneOrNonePresentValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */