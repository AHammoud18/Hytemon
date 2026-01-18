/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
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
/*     */ public abstract class AbstractLong2LongSortedMap
/*     */   extends AbstractLong2LongMap
/*     */   implements Long2LongSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public LongSortedSet keySet() {
/*  44 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractLongSortedSet
/*     */   {
/*     */     public boolean contains(long k) {
/*  51 */       return AbstractLong2LongSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  56 */       return AbstractLong2LongSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  61 */       AbstractLong2LongSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongComparator comparator() {
/*  66 */       return AbstractLong2LongSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public long firstLong() {
/*  71 */       return AbstractLong2LongSortedMap.this.firstLongKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastLong() {
/*  76 */       return AbstractLong2LongSortedMap.this.lastLongKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet headSet(long to) {
/*  81 */       return AbstractLong2LongSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet tailSet(long from) {
/*  86 */       return AbstractLong2LongSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet subSet(long from, long to) {
/*  91 */       return AbstractLong2LongSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongBidirectionalIterator iterator(long from) {
/*  96 */       return new AbstractLong2LongSortedMap.KeySetIterator(AbstractLong2LongSortedMap.this.long2LongEntrySet().iterator(new AbstractLong2LongMap.BasicEntry(from, 0L)));
/*     */     }
/*     */ 
/*     */     
/*     */     public LongBidirectionalIterator iterator() {
/* 101 */       return new AbstractLong2LongSortedMap.KeySetIterator(Long2LongSortedMaps.fastIterator(AbstractLong2LongSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements LongBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Long2LongMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Long2LongMap.Entry> i) {
/* 116 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public long nextLong() {
/* 121 */       return ((Long2LongMap.Entry)this.i.next()).getLongKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public long previousLong() {
/* 126 */       return ((Long2LongMap.Entry)this.i.previous()).getLongKey();
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
/*     */   public LongCollection values() {
/* 154 */     return new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractLongCollection
/*     */   {
/*     */     public LongIterator iterator() {
/* 161 */       return new AbstractLong2LongSortedMap.ValuesIterator(Long2LongSortedMaps.fastIterator(AbstractLong2LongSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(long k) {
/* 166 */       return AbstractLong2LongSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 171 */       return AbstractLong2LongSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 176 */       AbstractLong2LongSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements LongIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Long2LongMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Long2LongMap.Entry> i) {
/* 191 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public long nextLong() {
/* 196 */       return ((Long2LongMap.Entry)this.i.next()).getLongValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 201 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\AbstractLong2LongSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */