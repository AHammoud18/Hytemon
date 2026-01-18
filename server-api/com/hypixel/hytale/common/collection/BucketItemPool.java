/*    */ package com.hypixel.hytale.common.collection;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BucketItemPool<E>
/*    */ {
/*    */   @Nonnull
/* 18 */   protected final List<BucketItem<E>> pool = (List<BucketItem<E>>)new ObjectArrayList();
/*    */ 
/*    */   
/*    */   public void deallocate(BucketItem<E>[] entityHolders, int count) {
/* 22 */     this.pool.addAll(Arrays.<BucketItem<E>>asList(entityHolders).subList(0, count));
/*    */   }
/*    */   
/*    */   public BucketItem<E> allocate(E reference, double squaredDistance) {
/* 26 */     int l = this.pool.size();
/* 27 */     BucketItem<E> holder = (l == 0) ? new BucketItem<>() : this.pool.remove(l - 1);
/* 28 */     return holder.set(reference, squaredDistance);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\collection\BucketItemPool.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */