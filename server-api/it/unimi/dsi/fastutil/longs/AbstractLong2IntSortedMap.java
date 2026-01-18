/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
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
/*     */ public abstract class AbstractLong2IntSortedMap
/*     */   extends AbstractLong2IntMap
/*     */   implements Long2IntSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public LongSortedSet keySet() {
/*  47 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractLongSortedSet
/*     */   {
/*     */     public boolean contains(long k) {
/*  54 */       return AbstractLong2IntSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractLong2IntSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractLong2IntSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongComparator comparator() {
/*  69 */       return AbstractLong2IntSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public long firstLong() {
/*  74 */       return AbstractLong2IntSortedMap.this.firstLongKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastLong() {
/*  79 */       return AbstractLong2IntSortedMap.this.lastLongKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet headSet(long to) {
/*  84 */       return AbstractLong2IntSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet tailSet(long from) {
/*  89 */       return AbstractLong2IntSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet subSet(long from, long to) {
/*  94 */       return AbstractLong2IntSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongBidirectionalIterator iterator(long from) {
/*  99 */       return new AbstractLong2IntSortedMap.KeySetIterator(AbstractLong2IntSortedMap.this.long2IntEntrySet().iterator(new AbstractLong2IntMap.BasicEntry(from, 0)));
/*     */     }
/*     */ 
/*     */     
/*     */     public LongBidirectionalIterator iterator() {
/* 104 */       return new AbstractLong2IntSortedMap.KeySetIterator(Long2IntSortedMaps.fastIterator(AbstractLong2IntSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements LongBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Long2IntMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Long2IntMap.Entry> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public long nextLong() {
/* 124 */       return ((Long2IntMap.Entry)this.i.next()).getLongKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public long previousLong() {
/* 129 */       return ((Long2IntMap.Entry)this.i.previous()).getLongKey();
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
/*     */   public IntCollection values() {
/* 157 */     return (IntCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractIntCollection
/*     */   {
/*     */     public IntIterator iterator() {
/* 164 */       return new AbstractLong2IntSortedMap.ValuesIterator(Long2IntSortedMaps.fastIterator(AbstractLong2IntSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(int k) {
/* 169 */       return AbstractLong2IntSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractLong2IntSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractLong2IntSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements IntIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Long2IntMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Long2IntMap.Entry> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public int nextInt() {
/* 199 */       return ((Long2IntMap.Entry)this.i.next()).getIntValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\AbstractLong2IntSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */