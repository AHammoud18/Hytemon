/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.util.AutoClosableReentrantLock;
/*     */ import java.util.Collection;
/*     */ import java.util.Queue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SynchronizedQueue<E>
/*     */   extends SynchronizedCollection<E>
/*     */   implements Queue<E>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   static <E> SynchronizedQueue<E> synchronizedQueue(Queue<E> queue) {
/*  48 */     return new SynchronizedQueue<>(queue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SynchronizedQueue(Queue<E> queue) {
/*  59 */     super(queue);
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
/*     */   protected SynchronizedQueue(Queue<E> queue, AutoClosableReentrantLock lock) {
/*  71 */     super(queue, lock);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Queue<E> decorated() {
/*  81 */     return (Queue<E>)super.decorated();
/*     */   }
/*     */ 
/*     */   
/*     */   public E element() {
/*  86 */     ISentryLifecycleToken ignored = this.lock.acquire(); 
/*  87 */     try { E e = decorated().element();
/*  88 */       if (ignored != null) ignored.close();  return e; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1)
/*     */         { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/*  94 */      } public boolean equals(Object object) { if (object == this) {
/*  95 */       return true;
/*     */     }
/*  97 */     ISentryLifecycleToken ignored = this.lock.acquire(); 
/*  98 */     try { boolean bool = decorated().equals(object);
/*  99 */       if (ignored != null) ignored.close();  return bool; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try {
/*     */           ignored.close();
/*     */         } catch (Throwable throwable1) {
/*     */           throwable.addSuppressed(throwable1);
/*     */         }   throw throwable; }
/* 106 */      } public int hashCode() { ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 107 */     try { int i = decorated().hashCode();
/* 108 */       if (ignored != null) ignored.close();  return i; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 113 */      } public boolean offer(E e) { ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 114 */     try { boolean bool = decorated().offer(e);
/* 115 */       if (ignored != null) ignored.close();  return bool; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 120 */      } public E peek() { ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 121 */     try { E e = decorated().peek();
/* 122 */       if (ignored != null) ignored.close();  return e; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 127 */      } public E poll() { ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 128 */     try { E e = decorated().poll();
/* 129 */       if (ignored != null) ignored.close();  return e; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 134 */      } public E remove() { ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 135 */     try { E e = decorated().remove();
/* 136 */       if (ignored != null) ignored.close();  return e; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 141 */      } public Object[] toArray() { ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 142 */     try { Object[] arrayOfObject = decorated().toArray();
/* 143 */       if (ignored != null) ignored.close();  return arrayOfObject; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 148 */      } public <T> T[] toArray(T[] object) { ISentryLifecycleToken ignored = this.lock.acquire(); try {
/* 149 */       Object[] arrayOfObject = decorated().toArray((Object[])object);
/* 150 */       if (ignored != null) ignored.close(); 
/*     */       return (T[])arrayOfObject;
/*     */     } catch (Throwable throwable) {
/*     */       if (ignored != null)
/*     */         try {
/*     */           ignored.close();
/*     */         } catch (Throwable throwable1) {
/*     */           throwable.addSuppressed(throwable1);
/*     */         }  
/*     */       throw throwable;
/*     */     }  }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SynchronizedQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */