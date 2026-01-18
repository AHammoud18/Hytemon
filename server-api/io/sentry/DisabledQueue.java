/*     */ package io.sentry;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Queue;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DisabledQueue<E>
/*     */   extends AbstractCollection<E>
/*     */   implements Queue<E>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8423413834657610417L;
/*     */   
/*     */   public int size() {
/*  27 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  37 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(@NotNull E element) {
/*  52 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean offer(@NotNull E element) {
/*  65 */     return false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public E poll() {
/*  70 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public E element() {
/*  75 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public E peek() {
/*  80 */     return null;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public E remove() {
/*  85 */     throw new NoSuchElementException("queue is disabled");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Iterator<E> iterator() {
/*  97 */     return new Iterator<E>()
/*     */       {
/*     */         public boolean hasNext()
/*     */         {
/* 101 */           return false;
/*     */         }
/*     */ 
/*     */         
/*     */         public E next() {
/* 106 */           throw new NoSuchElementException();
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 111 */           throw new IllegalStateException();
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\DisabledQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */