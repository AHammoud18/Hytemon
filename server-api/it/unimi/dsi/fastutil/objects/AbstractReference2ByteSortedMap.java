/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
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
/*     */ public abstract class AbstractReference2ByteSortedMap<K>
/*     */   extends AbstractReference2ByteMap<K>
/*     */   implements Reference2ByteSortedMap<K>
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public ReferenceSortedSet<K> keySet() {
/*  47 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractReferenceSortedSet<K>
/*     */   {
/*     */     public boolean contains(Object k) {
/*  54 */       return AbstractReference2ByteSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractReference2ByteSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractReference2ByteSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public Comparator<? super K> comparator() {
/*  69 */       return AbstractReference2ByteSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public K first() {
/*  74 */       return AbstractReference2ByteSortedMap.this.firstKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public K last() {
/*  79 */       return AbstractReference2ByteSortedMap.this.lastKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSortedSet<K> headSet(K to) {
/*  84 */       return AbstractReference2ByteSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSortedSet<K> tailSet(K from) {
/*  89 */       return AbstractReference2ByteSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSortedSet<K> subSet(K from, K to) {
/*  94 */       return AbstractReference2ByteSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/*  99 */       return new AbstractReference2ByteSortedMap.KeySetIterator<>(AbstractReference2ByteSortedMap.this.reference2ByteEntrySet().iterator(new AbstractReference2ByteMap.BasicEntry<>(from, (byte)0)));
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator() {
/* 104 */       return new AbstractReference2ByteSortedMap.KeySetIterator<>(Reference2ByteSortedMaps.fastIterator(AbstractReference2ByteSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<K>
/*     */     implements ObjectBidirectionalIterator<K>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Reference2ByteMap.Entry<K>> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Reference2ByteMap.Entry<K>> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public K next() {
/* 124 */       return ((Reference2ByteMap.Entry<K>)this.i.next()).getKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public K previous() {
/* 129 */       return ((Reference2ByteMap.Entry<K>)this.i.previous()).getKey();
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
/*     */   public ByteCollection values() {
/* 157 */     return (ByteCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractByteCollection
/*     */   {
/*     */     public ByteIterator iterator() {
/* 164 */       return new AbstractReference2ByteSortedMap.ValuesIterator(Reference2ByteSortedMaps.fastIterator(AbstractReference2ByteSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(byte k) {
/* 169 */       return AbstractReference2ByteSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractReference2ByteSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractReference2ByteSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<K>
/*     */     implements ByteIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Reference2ByteMap.Entry<K>> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Reference2ByteMap.Entry<K>> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte nextByte() {
/* 199 */       return ((Reference2ByteMap.Entry)this.i.next()).getByteValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\AbstractReference2ByteSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */