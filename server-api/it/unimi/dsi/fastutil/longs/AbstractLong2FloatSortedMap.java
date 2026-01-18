/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatIterator;
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
/*     */ public abstract class AbstractLong2FloatSortedMap
/*     */   extends AbstractLong2FloatMap
/*     */   implements Long2FloatSortedMap
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
/*  54 */       return AbstractLong2FloatSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractLong2FloatSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractLong2FloatSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongComparator comparator() {
/*  69 */       return AbstractLong2FloatSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public long firstLong() {
/*  74 */       return AbstractLong2FloatSortedMap.this.firstLongKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastLong() {
/*  79 */       return AbstractLong2FloatSortedMap.this.lastLongKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet headSet(long to) {
/*  84 */       return AbstractLong2FloatSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet tailSet(long from) {
/*  89 */       return AbstractLong2FloatSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet subSet(long from, long to) {
/*  94 */       return AbstractLong2FloatSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongBidirectionalIterator iterator(long from) {
/*  99 */       return new AbstractLong2FloatSortedMap.KeySetIterator(AbstractLong2FloatSortedMap.this.long2FloatEntrySet().iterator(new AbstractLong2FloatMap.BasicEntry(from, 0.0F)));
/*     */     }
/*     */ 
/*     */     
/*     */     public LongBidirectionalIterator iterator() {
/* 104 */       return new AbstractLong2FloatSortedMap.KeySetIterator(Long2FloatSortedMaps.fastIterator(AbstractLong2FloatSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements LongBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Long2FloatMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Long2FloatMap.Entry> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public long nextLong() {
/* 124 */       return ((Long2FloatMap.Entry)this.i.next()).getLongKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public long previousLong() {
/* 129 */       return ((Long2FloatMap.Entry)this.i.previous()).getLongKey();
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
/* 164 */       return new AbstractLong2FloatSortedMap.ValuesIterator(Long2FloatSortedMaps.fastIterator(AbstractLong2FloatSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(float k) {
/* 169 */       return AbstractLong2FloatSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractLong2FloatSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractLong2FloatSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements FloatIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Long2FloatMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Long2FloatMap.Entry> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public float nextFloat() {
/* 199 */       return ((Long2FloatMap.Entry)this.i.next()).getFloatValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\AbstractLong2FloatSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */