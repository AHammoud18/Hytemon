/*    */ package com.hypixel.hytale.builtin.hytalegenerator.delimiters;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class DelimiterInt<V> {
/*    */   @Nonnull
/*    */   private final RangeInt range;
/*    */   @Nullable
/*    */   private final V value;
/*    */   
/*    */   public DelimiterInt(@Nonnull RangeInt range, @Nullable V value) {
/* 13 */     this.range = range;
/* 14 */     this.value = value;
/*    */   }
/*    */   @Nonnull
/*    */   public RangeInt getRange() {
/* 18 */     return this.range;
/*    */   }
/*    */   @Nullable
/*    */   public V getValue() {
/* 22 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\delimiters\DelimiterInt.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */