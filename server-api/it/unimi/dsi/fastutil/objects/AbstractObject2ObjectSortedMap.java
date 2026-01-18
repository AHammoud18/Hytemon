/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
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
/*     */ public abstract class AbstractObject2ObjectSortedMap<K, V>
/*     */   extends AbstractObject2ObjectMap<K, V>
/*     */   implements Object2ObjectSortedMap<K, V>
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public ObjectSortedSet<K> keySet() {
/*  44 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractObjectSortedSet<K>
/*     */   {
/*     */     public boolean contains(Object k) {
/*  51 */       return AbstractObject2ObjectSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  56 */       return AbstractObject2ObjectSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  61 */       AbstractObject2ObjectSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public Comparator<? super K> comparator() {
/*  66 */       return AbstractObject2ObjectSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public K first() {
/*  71 */       return (K)AbstractObject2ObjectSortedMap.this.firstKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public K last() {
/*  76 */       return (K)AbstractObject2ObjectSortedMap.this.lastKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> headSet(K to) {
/*  81 */       return AbstractObject2ObjectSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> tailSet(K from) {
/*  86 */       return AbstractObject2ObjectSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> subSet(K from, K to) {
/*  91 */       return AbstractObject2ObjectSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/*  96 */       return new AbstractObject2ObjectSortedMap.KeySetIterator<>(AbstractObject2ObjectSortedMap.this.object2ObjectEntrySet().iterator(new AbstractObject2ObjectMap.BasicEntry<>(from, null)));
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator() {
/* 101 */       return new AbstractObject2ObjectSortedMap.KeySetIterator<>(Object2ObjectSortedMaps.fastIterator(AbstractObject2ObjectSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<K, V>
/*     */     implements ObjectBidirectionalIterator<K>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> i) {
/* 116 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public K next() {
/* 121 */       return (K)((Object2ObjectMap.Entry)this.i.next()).getKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public K previous() {
/* 126 */       return (K)((Object2ObjectMap.Entry)this.i.previous()).getKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 131 */       return this.i.hasNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasPrevious() {
/* 136 */       return this.i.hasPrevious();
/*     */     }
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
/*     */   public ObjectCollection<V> values() {
/* 154 */     return new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractObjectCollection<V>
/*     */   {
/*     */     public ObjectIterator<V> iterator() {
/* 161 */       return new AbstractObject2ObjectSortedMap.ValuesIterator<>(Object2ObjectSortedMaps.fastIterator(AbstractObject2ObjectSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object k) {
/* 166 */       return AbstractObject2ObjectSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 171 */       return AbstractObject2ObjectSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 176 */       AbstractObject2ObjectSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<K, V>
/*     */     implements ObjectIterator<V>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> i) {
/* 191 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public V next() {
/* 196 */       return (V)((Object2ObjectMap.Entry)this.i.next()).getValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 201 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\AbstractObject2ObjectSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */