/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.util.AutoClosableReentrantLock;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
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
/*     */ class SynchronizedCollection<E>
/*     */   implements Collection<E>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2412805092710877986L;
/*     */   private final Collection<E> collection;
/*     */   final AutoClosableReentrantLock lock;
/*     */   
/*     */   public static <T> SynchronizedCollection<T> synchronizedCollection(Collection<T> coll) {
/*  68 */     return new SynchronizedCollection<>(coll);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SynchronizedCollection(Collection<E> collection) {
/*  79 */     if (collection == null) {
/*  80 */       throw new NullPointerException("Collection must not be null.");
/*     */     }
/*  82 */     this.collection = collection;
/*  83 */     this.lock = new AutoClosableReentrantLock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SynchronizedCollection(Collection<E> collection, AutoClosableReentrantLock lock) {
/*  94 */     if (collection == null) {
/*  95 */       throw new NullPointerException("Collection must not be null.");
/*     */     }
/*  97 */     if (lock == null) {
/*  98 */       throw new NullPointerException("Lock must not be null.");
/*     */     }
/* 100 */     this.collection = collection;
/* 101 */     this.lock = lock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Collection<E> decorated() {
/* 110 */     return this.collection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(E object) {
/* 117 */     ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 118 */     try { boolean bool = decorated().add(object);
/* 119 */       if (ignored != null) ignored.close();  return bool; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 124 */      } public boolean addAll(Collection<? extends E> coll) { ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 125 */     try { boolean bool = decorated().addAll(coll);
/* 126 */       if (ignored != null) ignored.close();  return bool; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 131 */      } public void clear() { ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 132 */     try { decorated().clear();
/* 133 */       if (ignored != null) ignored.close();  }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 138 */      } public boolean contains(Object object) { ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 139 */     try { boolean bool = decorated().contains(object);
/* 140 */       if (ignored != null) ignored.close();  return bool; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 145 */      } public boolean containsAll(Collection<?> coll) { ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 146 */     try { boolean bool = decorated().containsAll(coll);
/* 147 */       if (ignored != null) ignored.close();  return bool; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 152 */      } public boolean isEmpty() { ISentryLifecycleToken ignored = this.lock.acquire(); try {
/* 153 */       boolean bool = decorated().isEmpty();
/* 154 */       if (ignored != null) ignored.close();
/*     */       
/*     */       return bool;
/*     */     } catch (Throwable throwable) {
/*     */       if (ignored != null) {
/*     */         try {
/*     */           ignored.close();
/*     */         } catch (Throwable throwable1) {
/*     */           throwable.addSuppressed(throwable1);
/*     */         } 
/*     */       }
/*     */       throw throwable;
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<E> iterator() {
/* 171 */     return decorated().iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/* 176 */     ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 177 */     try { Object[] arrayOfObject = decorated().toArray();
/* 178 */       if (ignored != null) ignored.close();  return arrayOfObject; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 183 */      } public <T> T[] toArray(T[] object) { ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 184 */     try { Object[] arrayOfObject = decorated().toArray((Object[])object);
/* 185 */       if (ignored != null) ignored.close();  return (T[])arrayOfObject; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 190 */      } public boolean remove(Object object) { ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 191 */     try { boolean bool = decorated().remove(object);
/* 192 */       if (ignored != null) ignored.close();  return bool; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 197 */      } public boolean removeAll(Collection<?> coll) { ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 198 */     try { boolean bool = decorated().removeAll(coll);
/* 199 */       if (ignored != null) ignored.close();  return bool; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 204 */      } public boolean retainAll(Collection<?> coll) { ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 205 */     try { boolean bool = decorated().retainAll(coll);
/* 206 */       if (ignored != null) ignored.close();  return bool; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 211 */      } public int size() { ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 212 */     try { int i = decorated().size();
/* 213 */       if (ignored != null) ignored.close();  return i; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1)
/*     */         { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 219 */      } public boolean equals(Object object) { ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 220 */     try { if (object == this)
/* 221 */       { boolean bool1 = true;
/*     */ 
/*     */         
/* 224 */         if (ignored != null) ignored.close();  return bool1; }  boolean bool = (object == this || decorated().equals(object)) ? true : false; if (ignored != null) ignored.close();  return bool; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 229 */      } public int hashCode() { ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 230 */     try { int i = decorated().hashCode();
/* 231 */       if (ignored != null) ignored.close();  return i; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 236 */      } public String toString() { ISentryLifecycleToken ignored = this.lock.acquire(); try {
/* 237 */       String str = decorated().toString();
/* 238 */       if (ignored != null) ignored.close(); 
/*     */       return str;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SynchronizedCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */