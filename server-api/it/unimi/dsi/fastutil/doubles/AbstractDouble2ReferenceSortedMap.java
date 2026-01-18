/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
/*     */ public abstract class AbstractDouble2ReferenceSortedMap<V>
/*     */   extends AbstractDouble2ReferenceMap<V>
/*     */   implements Double2ReferenceSortedMap<V>
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public DoubleSortedSet keySet() {
/*  47 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractDoubleSortedSet
/*     */   {
/*     */     public boolean contains(double k) {
/*  54 */       return AbstractDouble2ReferenceSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractDouble2ReferenceSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractDouble2ReferenceSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleComparator comparator() {
/*  69 */       return AbstractDouble2ReferenceSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public double firstDouble() {
/*  74 */       return AbstractDouble2ReferenceSortedMap.this.firstDoubleKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public double lastDouble() {
/*  79 */       return AbstractDouble2ReferenceSortedMap.this.lastDoubleKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet headSet(double to) {
/*  84 */       return AbstractDouble2ReferenceSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet tailSet(double from) {
/*  89 */       return AbstractDouble2ReferenceSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet subSet(double from, double to) {
/*  94 */       return AbstractDouble2ReferenceSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleBidirectionalIterator iterator(double from) {
/*  99 */       return new AbstractDouble2ReferenceSortedMap.KeySetIterator(AbstractDouble2ReferenceSortedMap.this.double2ReferenceEntrySet().iterator(new AbstractDouble2ReferenceMap.BasicEntry(from, null)));
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleBidirectionalIterator iterator() {
/* 104 */       return new AbstractDouble2ReferenceSortedMap.KeySetIterator(Double2ReferenceSortedMaps.fastIterator(AbstractDouble2ReferenceSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator<V>
/*     */     implements DoubleBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public double nextDouble() {
/* 124 */       return ((Double2ReferenceMap.Entry)this.i.next()).getDoubleKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public double previousDouble() {
/* 129 */       return ((Double2ReferenceMap.Entry)this.i.previous()).getDoubleKey();
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
/*     */   public ReferenceCollection<V> values() {
/* 157 */     return (ReferenceCollection<V>)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractReferenceCollection<V>
/*     */   {
/*     */     public ObjectIterator<V> iterator() {
/* 164 */       return new AbstractDouble2ReferenceSortedMap.ValuesIterator<>(Double2ReferenceSortedMaps.fastIterator(AbstractDouble2ReferenceSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object k) {
/* 169 */       return AbstractDouble2ReferenceSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractDouble2ReferenceSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractDouble2ReferenceSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator<V>
/*     */     implements ObjectIterator<V>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public V next() {
/* 199 */       return ((Double2ReferenceMap.Entry<V>)this.i.next()).getValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\AbstractDouble2ReferenceSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */