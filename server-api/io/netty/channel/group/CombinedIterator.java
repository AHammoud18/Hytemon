/*    */ package io.netty.channel.group;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.util.Iterator;
/*    */ import java.util.NoSuchElementException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class CombinedIterator<E>
/*    */   implements Iterator<E>
/*    */ {
/*    */   private final Iterator<E> i1;
/*    */   private final Iterator<E> i2;
/*    */   private Iterator<E> currentIterator;
/*    */   
/*    */   CombinedIterator(Iterator<E> i1, Iterator<E> i2) {
/* 32 */     this.i1 = (Iterator<E>)ObjectUtil.checkNotNull(i1, "i1");
/* 33 */     this.i2 = (Iterator<E>)ObjectUtil.checkNotNull(i2, "i2");
/* 34 */     this.currentIterator = i1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/*    */     while (true) {
/* 40 */       if (this.currentIterator.hasNext()) {
/* 41 */         return true;
/*    */       }
/*    */       
/* 44 */       if (this.currentIterator == this.i1) {
/* 45 */         this.currentIterator = this.i2; continue;
/*    */       }  break;
/* 47 */     }  return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public E next() {
/*    */     while (true) {
/*    */       try {
/* 56 */         return this.currentIterator.next();
/* 57 */       } catch (NoSuchElementException e) {
/* 58 */         if (this.currentIterator == this.i1)
/* 59 */         { this.currentIterator = this.i2; continue; }  break;
/*    */       } 
/* 61 */     }  throw e;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void remove() {
/* 69 */     this.currentIterator.remove();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\group\CombinedIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */