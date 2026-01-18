/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
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
/*     */ public abstract class AbstractObject2ShortSortedMap<K>
/*     */   extends AbstractObject2ShortMap<K>
/*     */   implements Object2ShortSortedMap<K>
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
/*  54 */       return AbstractObject2ShortSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractObject2ShortSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractObject2ShortSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public Comparator<? super K> comparator() {
/*  69 */       return AbstractObject2ShortSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public K first() {
/*  74 */       return AbstractObject2ShortSortedMap.this.firstKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public K last() {
/*  79 */       return AbstractObject2ShortSortedMap.this.lastKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> headSet(K to) {
/*  84 */       return AbstractObject2ShortSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> tailSet(K from) {
/*  89 */       return AbstractObject2ShortSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> subSet(K from, K to) {
/*  94 */       return AbstractObject2ShortSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/*  99 */       return new AbstractObject2ShortSortedMap.KeySetIterator<>(AbstractObject2ShortSortedMap.this.object2ShortEntrySet().iterator(new AbstractObject2ShortMap.BasicEntry<>(from, (short)0)));
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator() {
/* 104 */       return new AbstractObject2ShortSortedMap.KeySetIterator<>(Object2ShortSortedMaps.fastIterator(AbstractObject2ShortSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<K>
/*     */     implements ObjectBidirectionalIterator<K>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Object2ShortMap.Entry<K>> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Object2ShortMap.Entry<K>> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public K next() {
/* 124 */       return ((Object2ShortMap.Entry<K>)this.i.next()).getKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public K previous() {
/* 129 */       return ((Object2ShortMap.Entry<K>)this.i.previous()).getKey();
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
/*     */   public ShortCollection values() {
/* 157 */     return (ShortCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractShortCollection
/*     */   {
/*     */     public ShortIterator iterator() {
/* 164 */       return new AbstractObject2ShortSortedMap.ValuesIterator(Object2ShortSortedMaps.fastIterator(AbstractObject2ShortSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(short k) {
/* 169 */       return AbstractObject2ShortSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractObject2ShortSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractObject2ShortSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<K>
/*     */     implements ShortIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Object2ShortMap.Entry<K>> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Object2ShortMap.Entry<K>> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public short nextShort() {
/* 199 */       return ((Object2ShortMap.Entry)this.i.next()).getShortValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\AbstractObject2ShortSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */