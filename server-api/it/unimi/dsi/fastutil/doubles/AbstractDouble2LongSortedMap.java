/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
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
/*     */ public abstract class AbstractDouble2LongSortedMap
/*     */   extends AbstractDouble2LongMap
/*     */   implements Double2LongSortedMap
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
/*  54 */       return AbstractDouble2LongSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractDouble2LongSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractDouble2LongSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleComparator comparator() {
/*  69 */       return AbstractDouble2LongSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public double firstDouble() {
/*  74 */       return AbstractDouble2LongSortedMap.this.firstDoubleKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public double lastDouble() {
/*  79 */       return AbstractDouble2LongSortedMap.this.lastDoubleKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet headSet(double to) {
/*  84 */       return AbstractDouble2LongSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet tailSet(double from) {
/*  89 */       return AbstractDouble2LongSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet subSet(double from, double to) {
/*  94 */       return AbstractDouble2LongSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleBidirectionalIterator iterator(double from) {
/*  99 */       return new AbstractDouble2LongSortedMap.KeySetIterator(AbstractDouble2LongSortedMap.this.double2LongEntrySet().iterator(new AbstractDouble2LongMap.BasicEntry(from, 0L)));
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleBidirectionalIterator iterator() {
/* 104 */       return new AbstractDouble2LongSortedMap.KeySetIterator(Double2LongSortedMaps.fastIterator(AbstractDouble2LongSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements DoubleBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Double2LongMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Double2LongMap.Entry> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public double nextDouble() {
/* 124 */       return ((Double2LongMap.Entry)this.i.next()).getDoubleKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public double previousDouble() {
/* 129 */       return ((Double2LongMap.Entry)this.i.previous()).getDoubleKey();
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
/*     */   public LongCollection values() {
/* 157 */     return (LongCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractLongCollection
/*     */   {
/*     */     public LongIterator iterator() {
/* 164 */       return new AbstractDouble2LongSortedMap.ValuesIterator(Double2LongSortedMaps.fastIterator(AbstractDouble2LongSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(long k) {
/* 169 */       return AbstractDouble2LongSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractDouble2LongSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractDouble2LongSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements LongIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Double2LongMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Double2LongMap.Entry> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public long nextLong() {
/* 199 */       return ((Double2LongMap.Entry)this.i.next()).getLongValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\AbstractDouble2LongSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */