/*    */ package com.hypixel.hytale.builtin.hytalegenerator.iterators;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*    */ import java.util.Iterator;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ForwardIntIterator
/*    */   implements IntIterator, Iterator<Integer> {
/*    */   private int max;
/*    */   private int current;
/*    */   
/*    */   public ForwardIntIterator(int min, int maxExclusive) {
/* 13 */     if (min > maxExclusive)
/* 14 */       throw new IllegalArgumentException("Start greater than end."); 
/* 15 */     this.max = maxExclusive - 1;
/* 16 */     this.current = min - 1;
/*    */   }
/*    */ 
/*    */   
/*    */   private ForwardIntIterator() {}
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 24 */     return (this.current < this.max);
/*    */   }
/*    */ 
/*    */   
/*    */   public int nextInt() {
/* 29 */     return ++this.current;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Integer next() {
/* 35 */     return Integer.valueOf(++this.current);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Integer getCurrent() {
/* 40 */     return Integer.valueOf(this.current);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ForwardIntIterator clone() {
/* 46 */     ForwardIntIterator clone = new ForwardIntIterator();
/* 47 */     clone.current = this.current;
/* 48 */     clone.max = this.max;
/* 49 */     return clone;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\iterators\ForwardIntIterator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */