/*     */ package org.bson.util;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import org.bson.assertions.Assertions;
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
/*     */ abstract class AbstractCopyOnWriteMap<K, V, M extends Map<K, V>>
/*     */   implements ConcurrentMap<K, V>
/*     */ {
/*     */   private volatile M delegate;
/*  47 */   private final transient Lock lock = new ReentrantLock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final View<K, V> view;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected <N extends Map<? extends K, ? extends V>> AbstractCopyOnWriteMap(N map, View.Type viewType) {
/*  62 */     this.delegate = (M)Assertions.notNull("delegate", copy((Map)Assertions.notNull("map", map)));
/*  63 */     this.view = ((View.Type)Assertions.notNull("viewType", viewType)).get(this);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clear() {
/*  81 */     this.lock.lock();
/*     */     try {
/*  83 */       set(copy(Collections.emptyMap()));
/*     */     } finally {
/*  85 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public final V remove(Object key) {
/*  90 */     this.lock.lock();
/*     */     
/*     */     try {
/*  93 */       if (!this.delegate.containsKey(key)) {
/*  94 */         return null;
/*     */       }
/*  96 */       M map = copy();
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 103 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean remove(Object key, Object value) {
/* 108 */     this.lock.lock();
/*     */     try {
/* 110 */       if (this.delegate.containsKey(key) && equals(value, this.delegate.get(key))) {
/* 111 */         M map = copy();
/* 112 */         map.remove(key);
/* 113 */         set(map);
/* 114 */         return true;
/*     */       } 
/* 116 */       return false;
/*     */     } finally {
/*     */       
/* 119 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean replace(K key, V oldValue, V newValue) {
/* 124 */     this.lock.lock();
/*     */     try {
/* 126 */       if (!this.delegate.containsKey(key) || !equals(oldValue, this.delegate.get(key))) {
/* 127 */         return false;
/*     */       }
/* 129 */       M map = copy();
/* 130 */       map.put(key, newValue);
/* 131 */       set(map);
/* 132 */       return true;
/*     */     } finally {
/* 134 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public V replace(K key, V value) {
/* 139 */     this.lock.lock();
/*     */     try {
/* 141 */       if (!this.delegate.containsKey(key)) {
/* 142 */         return null;
/*     */       }
/* 144 */       M map = copy();
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 151 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public final V put(K key, V value) {
/* 156 */     this.lock.lock();
/*     */     try {
/* 158 */       M map = copy();
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 165 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public V putIfAbsent(K key, V value) {
/* 170 */     this.lock.lock();
/*     */     try {
/* 172 */       if (!this.delegate.containsKey(key)) {
/* 173 */         M map = copy();
/*     */         try {
/* 175 */           return (V)map.put(key, (Object)value);
/*     */         } finally {
/* 177 */           set(map);
/*     */         } 
/*     */       } 
/* 180 */       return (V)this.delegate.get(key);
/*     */     } finally {
/* 182 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void putAll(Map<? extends K, ? extends V> t) {
/* 187 */     this.lock.lock();
/*     */     try {
/* 189 */       M map = copy();
/* 190 */       map.putAll(t);
/* 191 */       set(map);
/*     */     } finally {
/* 193 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected M copy() {
/* 198 */     this.lock.lock();
/*     */     try {
/* 200 */       return copy((Map<? extends K, ? extends V>)this.delegate);
/*     */     } finally {
/* 202 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void set(M map) {
/* 208 */     this.delegate = map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Set<Map.Entry<K, V>> entrySet() {
/* 216 */     return this.view.entrySet();
/*     */   }
/*     */   
/*     */   public final Set<K> keySet() {
/* 220 */     return this.view.keySet();
/*     */   }
/*     */   
/*     */   public final Collection<V> values() {
/* 224 */     return this.view.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean containsKey(Object key) {
/* 232 */     return this.delegate.containsKey(key);
/*     */   }
/*     */   
/*     */   public final boolean containsValue(Object value) {
/* 236 */     return this.delegate.containsValue(value);
/*     */   }
/*     */   
/*     */   public final V get(Object key) {
/* 240 */     return (V)this.delegate.get(key);
/*     */   }
/*     */   
/*     */   public final boolean isEmpty() {
/* 244 */     return this.delegate.isEmpty();
/*     */   }
/*     */   
/*     */   public final int size() {
/* 248 */     return this.delegate.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean equals(Object o) {
/* 253 */     return this.delegate.equals(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/* 258 */     return this.delegate.hashCode();
/*     */   }
/*     */   
/*     */   protected final M getDelegate() {
/* 262 */     return this.delegate;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 267 */     return this.delegate.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private class KeySet
/*     */     extends CollectionView<K>
/*     */     implements Set<K>
/*     */   {
/*     */     private KeySet() {}
/*     */     
/*     */     Collection<K> getDelegate() {
/* 278 */       return AbstractCopyOnWriteMap.this.delegate.keySet();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear() {
/* 286 */       AbstractCopyOnWriteMap.this.lock.lock();
/*     */       try {
/* 288 */         M map = (M)AbstractCopyOnWriteMap.this.copy();
/* 289 */         map.keySet().clear();
/* 290 */         AbstractCopyOnWriteMap.this.set(map);
/*     */       } finally {
/* 292 */         AbstractCopyOnWriteMap.this.lock.unlock();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(Object o) {
/* 297 */       return (AbstractCopyOnWriteMap.this.remove(o) != null);
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 301 */       AbstractCopyOnWriteMap.this.lock.lock();
/*     */       try {
/* 303 */         M map = (M)AbstractCopyOnWriteMap.this.copy();
/*     */ 
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/* 310 */         AbstractCopyOnWriteMap.this.lock.unlock();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 315 */       AbstractCopyOnWriteMap.this.lock.lock();
/*     */       try {
/* 317 */         M map = (M)AbstractCopyOnWriteMap.this.copy();
/*     */ 
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/* 324 */         AbstractCopyOnWriteMap.this.lock.unlock();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private final class Values extends CollectionView<V> {
/*     */     private Values() {}
/*     */     
/*     */     Collection<V> getDelegate() {
/* 333 */       return AbstractCopyOnWriteMap.this.delegate.values();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 337 */       AbstractCopyOnWriteMap.this.lock.lock();
/*     */       try {
/* 339 */         M map = (M)AbstractCopyOnWriteMap.this.copy();
/* 340 */         map.values().clear();
/* 341 */         AbstractCopyOnWriteMap.this.set(map);
/*     */       } finally {
/* 343 */         AbstractCopyOnWriteMap.this.lock.unlock();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(Object o) {
/* 348 */       AbstractCopyOnWriteMap.this.lock.lock();
/*     */       try {
/* 350 */         if (!contains(o)) {
/* 351 */           return false;
/*     */         }
/* 353 */         M map = (M)AbstractCopyOnWriteMap.this.copy();
/*     */ 
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/* 360 */         AbstractCopyOnWriteMap.this.lock.unlock();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 365 */       AbstractCopyOnWriteMap.this.lock.lock();
/*     */       try {
/* 367 */         M map = (M)AbstractCopyOnWriteMap.this.copy();
/*     */ 
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/* 374 */         AbstractCopyOnWriteMap.this.lock.unlock();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 379 */       AbstractCopyOnWriteMap.this.lock.lock();
/*     */       try {
/* 381 */         M map = (M)AbstractCopyOnWriteMap.this.copy();
/*     */ 
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/* 388 */         AbstractCopyOnWriteMap.this.lock.unlock();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class EntrySet extends CollectionView<Map.Entry<K, V>> implements Set<Map.Entry<K, V>> {
/*     */     private EntrySet() {}
/*     */     
/*     */     Collection<Map.Entry<K, V>> getDelegate() {
/* 397 */       return AbstractCopyOnWriteMap.this.delegate.entrySet();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 401 */       AbstractCopyOnWriteMap.this.lock.lock();
/*     */       try {
/* 403 */         M map = (M)AbstractCopyOnWriteMap.this.copy();
/* 404 */         map.entrySet().clear();
/* 405 */         AbstractCopyOnWriteMap.this.set(map);
/*     */       } finally {
/* 407 */         AbstractCopyOnWriteMap.this.lock.unlock();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(Object o) {
/* 412 */       AbstractCopyOnWriteMap.this.lock.lock();
/*     */       try {
/* 414 */         if (!contains(o)) {
/* 415 */           return false;
/*     */         }
/* 417 */         M map = (M)AbstractCopyOnWriteMap.this.copy();
/*     */ 
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/* 424 */         AbstractCopyOnWriteMap.this.lock.unlock();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 429 */       AbstractCopyOnWriteMap.this.lock.lock();
/*     */       try {
/* 431 */         M map = (M)AbstractCopyOnWriteMap.this.copy();
/*     */ 
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/* 438 */         AbstractCopyOnWriteMap.this.lock.unlock();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 443 */       AbstractCopyOnWriteMap.this.lock.lock();
/*     */       try {
/* 445 */         M map = (M)AbstractCopyOnWriteMap.this.copy();
/*     */ 
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/* 452 */         AbstractCopyOnWriteMap.this.lock.unlock();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class UnmodifiableIterator<T> implements Iterator<T> {
/*     */     private final Iterator<T> delegate;
/*     */     
/*     */     UnmodifiableIterator(Iterator<T> delegate) {
/* 461 */       this.delegate = delegate;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 465 */       return this.delegate.hasNext();
/*     */     }
/*     */     
/*     */     public T next() {
/* 469 */       return this.delegate.next();
/*     */     }
/*     */     
/*     */     public void remove() {
/* 473 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static abstract class CollectionView<E>
/*     */     implements Collection<E>
/*     */   {
/*     */     abstract Collection<E> getDelegate();
/*     */ 
/*     */     
/*     */     public final boolean contains(Object o) {
/* 486 */       return getDelegate().contains(o);
/*     */     }
/*     */     
/*     */     public final boolean containsAll(Collection<?> c) {
/* 490 */       return getDelegate().containsAll(c);
/*     */     }
/*     */     
/*     */     public final Iterator<E> iterator() {
/* 494 */       return new AbstractCopyOnWriteMap.UnmodifiableIterator<>(getDelegate().iterator());
/*     */     }
/*     */     
/*     */     public final boolean isEmpty() {
/* 498 */       return getDelegate().isEmpty();
/*     */     }
/*     */     
/*     */     public final int size() {
/* 502 */       return getDelegate().size();
/*     */     }
/*     */     
/*     */     public final Object[] toArray() {
/* 506 */       return getDelegate().toArray();
/*     */     }
/*     */     
/*     */     public final <T> T[] toArray(T[] a) {
/* 510 */       return getDelegate().toArray(a);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 515 */       return getDelegate().hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 520 */       return getDelegate().equals(obj);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 525 */       return getDelegate().toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean add(E o) {
/* 533 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public final boolean addAll(Collection<? extends E> c) {
/* 537 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean equals(Object o1, Object o2) {
/* 542 */     if (o1 == null) {
/* 543 */       return (o2 == null);
/*     */     }
/* 545 */     return o1.equals(o2);
/*     */   }
/*     */ 
/*     */   
/*     */   abstract <N extends Map<? extends K, ? extends V>> M copy(N paramN);
/*     */ 
/*     */   
/*     */   public static abstract class View<K, V>
/*     */   {
/*     */     abstract Set<K> keySet();
/*     */ 
/*     */     
/*     */     abstract Set<Map.Entry<K, V>> entrySet();
/*     */ 
/*     */     
/*     */     abstract Collection<V> values();
/*     */ 
/*     */     
/*     */     public enum Type
/*     */     {
/* 565 */       STABLE
/*     */       {
/*     */         <K, V, M extends Map<K, V>> AbstractCopyOnWriteMap.View<K, V> get(AbstractCopyOnWriteMap<K, V, M> host) {
/* 568 */           Objects.requireNonNull(host); return new AbstractCopyOnWriteMap.Immutable();
/*     */         }
/*     */       },
/* 571 */       LIVE
/*     */       {
/*     */         <K, V, M extends Map<K, V>> AbstractCopyOnWriteMap.View<K, V> get(AbstractCopyOnWriteMap<K, V, M> host) {
/* 574 */           Objects.requireNonNull(host); return new AbstractCopyOnWriteMap.Mutable(); } }; abstract <K, V, M extends Map<K, V>> AbstractCopyOnWriteMap.View<K, V> get(AbstractCopyOnWriteMap<K, V, M> param2AbstractCopyOnWriteMap); } } public enum Type { STABLE { <K, V, M extends Map<K, V>> AbstractCopyOnWriteMap.View<K, V> get(AbstractCopyOnWriteMap<K, V, M> host) { Objects.requireNonNull(host); return new AbstractCopyOnWriteMap.Mutable(); }
/*     */        }
/*     */     ,
/*     */     LIVE { <K, V, M extends Map<K, V>> AbstractCopyOnWriteMap.View<K, V> get(AbstractCopyOnWriteMap<K, V, M> host) {
/*     */         Objects.requireNonNull(host);
/*     */         return new AbstractCopyOnWriteMap.Immutable();
/*     */       } };
/*     */     
/*     */     abstract <K, V, M extends Map<K, V>> AbstractCopyOnWriteMap.View<K, V> get(AbstractCopyOnWriteMap<K, V, M> param1AbstractCopyOnWriteMap); }
/*     */   
/*     */   final class Immutable extends View<K, V> {
/*     */     public Set<K> keySet() {
/* 586 */       return Collections.unmodifiableSet(AbstractCopyOnWriteMap.this.delegate.keySet());
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<Map.Entry<K, V>> entrySet() {
/* 591 */       return Collections.unmodifiableSet(AbstractCopyOnWriteMap.this.delegate.entrySet());
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<V> values() {
/* 596 */       return Collections.unmodifiableCollection(AbstractCopyOnWriteMap.this.delegate.values());
/*     */     }
/*     */   }
/*     */   
/*     */   final class Mutable
/*     */     extends View<K, V> {
/* 602 */     private final transient AbstractCopyOnWriteMap<K, V, M>.KeySet keySet = new AbstractCopyOnWriteMap.KeySet();
/* 603 */     private final transient AbstractCopyOnWriteMap<K, V, M>.EntrySet entrySet = new AbstractCopyOnWriteMap.EntrySet();
/* 604 */     private final transient AbstractCopyOnWriteMap<K, V, M>.Values values = new AbstractCopyOnWriteMap.Values();
/*     */ 
/*     */     
/*     */     public Set<K> keySet() {
/* 608 */       return this.keySet;
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<Map.Entry<K, V>> entrySet() {
/* 613 */       return this.entrySet;
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<V> values() {
/* 618 */       return this.values;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bso\\util\AbstractCopyOnWriteMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */