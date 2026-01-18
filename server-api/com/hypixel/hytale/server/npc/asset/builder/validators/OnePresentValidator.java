/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderObjectHelper;
/*    */ import java.util.Objects;
/*    */ import java.util.function.IntPredicate;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OnePresentValidator
/*    */   extends Validator
/*    */ {
/*    */   @Nonnull
/*    */   private final String[] attributes;
/*    */   
/*    */   private OnePresentValidator(String... attributes) {
/* 17 */     this.attributes = Objects.<String[]>requireNonNull(attributes, "Attributes in OnePresentValidator must not be null");
/*    */   }
/*    */   
/*    */   public static int countPresent(int size, @Nonnull IntPredicate presentPredicate) {
/* 21 */     int count = 0;
/* 22 */     for (int i = 0; i < size; i++) {
/* 23 */       if (presentPredicate.test(i)) count++; 
/*    */     } 
/* 25 */     return count;
/*    */   }
/*    */   
/*    */   public static boolean test(@Nonnull BuilderObjectHelper<?>[] objects) {
/* 29 */     return (countPresent(objects.length, i -> objects[i].isPresent()) == 1);
/*    */   }
/*    */   
/*    */   public static boolean test(@Nonnull boolean[] readStatus) {
/* 33 */     return (countPresent(readStatus.length, i -> readStatus[i]) == 1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int countPresent(@Nonnull BuilderObjectHelper<?> objectHelper) {
/* 40 */     return objectHelper.isPresent() ? 1 : 0;
/*    */   }
/*    */   
/*    */   public static int countPresent(@Nonnull BuilderObjectHelper<?> objectHelper1, @Nonnull BuilderObjectHelper<?> objectHelper2) {
/* 44 */     return countPresent(objectHelper1) + countPresent(objectHelper2);
/*    */   }
/*    */   
/*    */   public static int countPresent(@Nonnull BuilderObjectHelper<?> objectHelper1, @Nonnull BuilderObjectHelper<?> objectHelper2, @Nonnull BuilderObjectHelper<?> objectHelper3) {
/* 48 */     return countPresent(objectHelper1) + countPresent(objectHelper2, objectHelper3);
/*    */   }
/*    */   
/*    */   public static boolean test(@Nonnull BuilderObjectHelper<?> objectHelper1, @Nonnull BuilderObjectHelper<?> objectHelper2) {
/* 52 */     return (countPresent(objectHelper1, objectHelper2) == 1);
/*    */   }
/*    */   
/*    */   public static boolean test(@Nonnull BuilderObjectHelper<?> objectHelper1, @Nonnull BuilderObjectHelper<?> objectHelper2, @Nonnull BuilderObjectHelper<?> objectHelper3) {
/* 56 */     return (countPresent(objectHelper1, objectHelper2, objectHelper3) == 1);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String errorMessage(@Nonnull String[] attributes, BuilderObjectHelper<?>[] objects) {
/* 61 */     return errorMessage(attributes, i -> objects[i].isPresent());
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String errorMessage(@Nonnull String[] attributes, boolean[] readStatus) {
/* 66 */     return errorMessage(attributes, i -> readStatus[i]);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String errorMessage(@Nonnull String[] attributes, @Nonnull IntPredicate presentPredicate) {
/* 71 */     StringBuilder result = new StringBuilder("Exactly one of ");
/* 72 */     String sep = ", ";
/* 73 */     for (int i = 0; i < attributes.length; i++) {
/* 74 */       if (i == attributes.length - 1) {
/* 75 */         sep = "";
/*    */       }
/* 77 */       result.append(String.format("'%s'%s%s", new Object[] { attributes[i], presentPredicate.test(i) ? "(Present)" : "", sep }));
/*    */     } 
/* 79 */     return String.valueOf(result) + " must be present";
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static OnePresentValidator withAttributes(String... attributes) {
/* 84 */     return new OnePresentValidator(attributes);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\OnePresentValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */