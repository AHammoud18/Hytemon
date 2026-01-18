/*    */ package com.hypixel.hytale.builtin.hytalegenerator.datastructures;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CollectionFactory
/*    */ {
/*    */   @Nonnull
/*    */   public static <T> Set<T> hashSetOf(@Nonnull T... elements) {
/* 20 */     Set<T> set = new HashSet<>(elements.length);
/* 21 */     for (T element : elements) {
/* 22 */       if (element == null)
/* 23 */         throw new NullPointerException("elements can't be null"); 
/* 24 */       set.add(element);
/*    */     } 
/* 26 */     return set;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\datastructures\CollectionFactory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */