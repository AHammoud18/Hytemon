/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatIterator;
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
/*     */ public abstract class AbstractObject2FloatSortedMap<K>
/*     */   extends AbstractObject2FloatMap<K>
/*     */   implements Object2FloatSortedMap<K>
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public ObjectSortedSet<K> keySet() {
/*  47 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractObjectSortedSet<K>
/*     */   {
/*     */     public boolean contains(Object k) {
/*  54 */       return AbstractObject2FloatSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractObject2FloatSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractObject2FloatSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public Comparator<? super K> comparator() {
/*  69 */       return AbstractObject2FloatSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public K first() {
/*  74 */       return AbstractObject2FloatSortedMap.this.firstKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public K last() {
/*  79 */       return AbstractObject2FloatSortedMap.this.lastKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> headSet(K to) {
/*  84 */       return AbstractObject2FloatSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> tailSet(K from) {
/*  89 */       return AbstractObject2FloatSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> subSet(K from, K to) {
/*  94 */       return AbstractObject2FloatSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/*  99 */       return new AbstractObject2FloatSortedMap.KeySetIterator<>(AbstractObject2FloatSortedMap.this.object2FloatEntrySet().iterator(new AbstractObject2FloatMap.BasicEntry<>(from, 0.0F)));
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator() {
/* 104 */       return new AbstractObject2FloatSortedMap.KeySetIterator<>(Object2FloatSortedMaps.fastIterator(AbstractObject2FloatSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<K>
/*     */     implements ObjectBidirectionalIterator<K>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public K next() {
/* 124 */       return ((Object2FloatMap.Entry<K>)this.i.next()).getKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public K previous() {
/* 129 */       return ((Object2FloatMap.Entry<K>)this.i.previous()).getKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 134 */       return this.i.hasNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasPrevious() {
/* 139 */       return this.i.hasPrevious();
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
/*     */   public FloatCollection values() {
/* 157 */     return (FloatCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractFloatCollection
/*     */   {
/*     */     public FloatIterator iterator() {
/* 164 */       return new AbstractObject2FloatSortedMap.ValuesIterator(Object2FloatSortedMaps.fastIterator(AbstractObject2FloatSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(float k) {
/* 169 */       return AbstractObject2FloatSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractObject2FloatSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractObject2FloatSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<K>
/*     */     implements FloatIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public float nextFloat() {
/* 199 */       return ((Object2FloatMap.Entry)this.i.next()).getFloatValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\AbstractObject2FloatSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */