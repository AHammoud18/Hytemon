/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*     */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*     */ import it.unimi.dsi.fastutil.chars.CharIterator;
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
/*     */ public abstract class AbstractReference2CharSortedMap<K>
/*     */   extends AbstractReference2CharMap<K>
/*     */   implements Reference2CharSortedMap<K>
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
/*  54 */       return AbstractReference2CharSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractReference2CharSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractReference2CharSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public Comparator<? super K> comparator() {
/*  69 */       return AbstractReference2CharSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public K first() {
/*  74 */       return AbstractReference2CharSortedMap.this.firstKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public K last() {
/*  79 */       return AbstractReference2CharSortedMap.this.lastKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSortedSet<K> headSet(K to) {
/*  84 */       return AbstractReference2CharSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSortedSet<K> tailSet(K from) {
/*  89 */       return AbstractReference2CharSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSortedSet<K> subSet(K from, K to) {
/*  94 */       return AbstractReference2CharSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/*  99 */       return new AbstractReference2CharSortedMap.KeySetIterator<>(AbstractReference2CharSortedMap.this.reference2CharEntrySet().iterator(new AbstractReference2CharMap.BasicEntry<>(from, false)));
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator() {
/* 104 */       return new AbstractReference2CharSortedMap.KeySetIterator<>(Reference2CharSortedMaps.fastIterator(AbstractReference2CharSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<K>
/*     */     implements ObjectBidirectionalIterator<K>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Reference2CharMap.Entry<K>> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Reference2CharMap.Entry<K>> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public K next() {
/* 124 */       return ((Reference2CharMap.Entry<K>)this.i.next()).getKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public K previous() {
/* 129 */       return ((Reference2CharMap.Entry<K>)this.i.previous()).getKey();
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
/*     */   public CharCollection values() {
/* 157 */     return (CharCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractCharCollection
/*     */   {
/*     */     public CharIterator iterator() {
/* 164 */       return new AbstractReference2CharSortedMap.ValuesIterator(Reference2CharSortedMaps.fastIterator(AbstractReference2CharSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(char k) {
/* 169 */       return AbstractReference2CharSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractReference2CharSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractReference2CharSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<K>
/*     */     implements CharIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Reference2CharMap.Entry<K>> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Reference2CharMap.Entry<K>> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public char nextChar() {
/* 199 */       return ((Reference2CharMap.Entry)this.i.next()).getCharValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\AbstractReference2CharSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */