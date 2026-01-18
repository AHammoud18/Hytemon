/*    */ package com.hypixel.hytale.common.collection;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BucketItem<E>
/*    */ {
/*    */   public E item;
/*    */   public double squaredDistance;
/*    */   
/*    */   @Nonnull
/*    */   public BucketItem<E> set(E reference, double squaredDistance) {
/* 15 */     this.item = reference;
/* 16 */     this.squaredDistance = squaredDistance;
/* 17 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\collection\BucketItem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */