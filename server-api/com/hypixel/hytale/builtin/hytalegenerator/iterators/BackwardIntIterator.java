/*    */ package com.hypixel.hytale.builtin.hytalegenerator.iterators;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*    */ import java.util.Iterator;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BackwardIntIterator
/*    */   implements IntIterator, Iterator<Integer> {
/*    */   private int min;
/*    */   private int current;
/*    */   
/*    */   public BackwardIntIterator(int min, int maxExclusive) {
/* 13 */     if (min > maxExclusive)
/* 14 */       throw new IllegalArgumentException("Start greater than end."); 
/* 15 */     this.min = min;
/* 16 */     this.current = maxExclusive;
/*    */   }
/*    */ 
/*    */   
/*    */   private BackwardIntIterator() {}
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 24 */     return (this.current > this.min);
/*    */   }
/*    */ 
/*    */   
/*    */   public int nextInt() {
/* 29 */     return --this.current;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Integer next() {
/* 35 */     return Integer.valueOf(--this.current);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Integer getCurrent() {
/* 40 */     return Integer.valueOf(this.current);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public BackwardIntIterator clone() {
/* 46 */     BackwardIntIterator clone = new BackwardIntIterator();
/* 47 */     clone.current = this.current;
/* 48 */     clone.min = this.min;
/* 49 */     return clone;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\iterators\BackwardIntIterator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */