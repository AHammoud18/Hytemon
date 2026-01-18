/*    */ package com.hypixel.hytale.builtin.hytalegenerator.iterators;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class IntIterators
/*    */ {
/*    */   @Nonnull
/*    */   public static IntIterator range(int start, int end) {
/* 11 */     if (start <= end) {
/* 12 */       return new ForwardIntIterator(start, end);
/*    */     }
/* 14 */     return new BackwardIntIterator(end, start);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\iterators\IntIterators.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */