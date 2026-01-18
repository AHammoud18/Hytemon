/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
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
/*     */ public abstract class AbstractShort2ObjectSortedMap<V>
/*     */   extends AbstractShort2ObjectMap<V>
/*     */   implements Short2ObjectSortedMap<V>
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public ShortSortedSet keySet() {
/*  47 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractShortSortedSet
/*     */   {
/*     */     public boolean contains(short k) {
/*  54 */       return AbstractShort2ObjectSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractShort2ObjectSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractShort2ObjectSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortComparator comparator() {
/*  69 */       return AbstractShort2ObjectSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public short firstShort() {
/*  74 */       return AbstractShort2ObjectSortedMap.this.firstShortKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public short lastShort() {
/*  79 */       return AbstractShort2ObjectSortedMap.this.lastShortKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet headSet(short to) {
/*  84 */       return AbstractShort2ObjectSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet tailSet(short from) {
/*  89 */       return AbstractShort2ObjectSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet subSet(short from, short to) {
/*  94 */       return AbstractShort2ObjectSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortBidirectionalIterator iterator(short from) {
/*  99 */       return new AbstractShort2ObjectSortedMap.KeySetIterator(AbstractShort2ObjectSortedMap.this.short2ObjectEntrySet().iterator(new AbstractShort2ObjectMap.BasicEntry(from, null)));
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortBidirectionalIterator iterator() {
/* 104 */       return new AbstractShort2ObjectSortedMap.KeySetIterator(Short2ObjectSortedMaps.fastIterator(AbstractShort2ObjectSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<V>
/*     */     implements ShortBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Short2ObjectMap.Entry<V>> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Short2ObjectMap.Entry<V>> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public short nextShort() {
/* 124 */       return ((Short2ObjectMap.Entry)this.i.next()).getShortKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public short previousShort() {
/* 129 */       return ((Short2ObjectMap.Entry)this.i.previous()).getShortKey();
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
/*     */   public ObjectCollection<V> values() {
/* 157 */     return (ObjectCollection<V>)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractObjectCollection<V>
/*     */   {
/*     */     public ObjectIterator<V> iterator() {
/* 164 */       return new AbstractShort2ObjectSortedMap.ValuesIterator<>(Short2ObjectSortedMaps.fastIterator(AbstractShort2ObjectSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object k) {
/* 169 */       return AbstractShort2ObjectSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractShort2ObjectSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractShort2ObjectSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<V>
/*     */     implements ObjectIterator<V>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Short2ObjectMap.Entry<V>> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Short2ObjectMap.Entry<V>> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public V next() {
/* 199 */       return ((Short2ObjectMap.Entry<V>)this.i.next()).getValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\AbstractShort2ObjectSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */