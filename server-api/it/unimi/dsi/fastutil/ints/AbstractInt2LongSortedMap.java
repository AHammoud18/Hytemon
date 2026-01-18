/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ public abstract class AbstractInt2LongSortedMap
/*     */   extends AbstractInt2LongMap
/*     */   implements Int2LongSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public IntSortedSet keySet() {
/*  47 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractIntSortedSet
/*     */   {
/*     */     public boolean contains(int k) {
/*  54 */       return AbstractInt2LongSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractInt2LongSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractInt2LongSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntComparator comparator() {
/*  69 */       return AbstractInt2LongSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public int firstInt() {
/*  74 */       return AbstractInt2LongSortedMap.this.firstIntKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastInt() {
/*  79 */       return AbstractInt2LongSortedMap.this.lastIntKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet headSet(int to) {
/*  84 */       return AbstractInt2LongSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet tailSet(int from) {
/*  89 */       return AbstractInt2LongSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet subSet(int from, int to) {
/*  94 */       return AbstractInt2LongSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntBidirectionalIterator iterator(int from) {
/*  99 */       return new AbstractInt2LongSortedMap.KeySetIterator(AbstractInt2LongSortedMap.this.int2LongEntrySet().iterator(new AbstractInt2LongMap.BasicEntry(from, 0L)));
/*     */     }
/*     */ 
/*     */     
/*     */     public IntBidirectionalIterator iterator() {
/* 104 */       return new AbstractInt2LongSortedMap.KeySetIterator(Int2LongSortedMaps.fastIterator(AbstractInt2LongSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements IntBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Int2LongMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Int2LongMap.Entry> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public int nextInt() {
/* 124 */       return ((Int2LongMap.Entry)this.i.next()).getIntKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public int previousInt() {
/* 129 */       return ((Int2LongMap.Entry)this.i.previous()).getIntKey();
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
/* 164 */       return new AbstractInt2LongSortedMap.ValuesIterator(Int2LongSortedMaps.fastIterator(AbstractInt2LongSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(long k) {
/* 169 */       return AbstractInt2LongSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractInt2LongSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractInt2LongSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements LongIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Int2LongMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Int2LongMap.Entry> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public long nextLong() {
/* 199 */       return ((Int2LongMap.Entry)this.i.next()).getLongValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\AbstractInt2LongSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */