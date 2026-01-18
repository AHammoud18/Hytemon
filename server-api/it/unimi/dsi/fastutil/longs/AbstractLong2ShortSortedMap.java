/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*     */ import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
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
/*     */ public abstract class AbstractLong2ShortSortedMap
/*     */   extends AbstractLong2ShortMap
/*     */   implements Long2ShortSortedMap
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
/*  54 */       return AbstractLong2ShortSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractLong2ShortSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractLong2ShortSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongComparator comparator() {
/*  69 */       return AbstractLong2ShortSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public long firstLong() {
/*  74 */       return AbstractLong2ShortSortedMap.this.firstLongKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastLong() {
/*  79 */       return AbstractLong2ShortSortedMap.this.lastLongKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet headSet(long to) {
/*  84 */       return AbstractLong2ShortSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet tailSet(long from) {
/*  89 */       return AbstractLong2ShortSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet subSet(long from, long to) {
/*  94 */       return AbstractLong2ShortSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongBidirectionalIterator iterator(long from) {
/*  99 */       return new AbstractLong2ShortSortedMap.KeySetIterator(AbstractLong2ShortSortedMap.this.long2ShortEntrySet().iterator(new AbstractLong2ShortMap.BasicEntry(from, (short)0)));
/*     */     }
/*     */ 
/*     */     
/*     */     public LongBidirectionalIterator iterator() {
/* 104 */       return new AbstractLong2ShortSortedMap.KeySetIterator(Long2ShortSortedMaps.fastIterator(AbstractLong2ShortSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements LongBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Long2ShortMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Long2ShortMap.Entry> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public long nextLong() {
/* 124 */       return ((Long2ShortMap.Entry)this.i.next()).getLongKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public long previousLong() {
/* 129 */       return ((Long2ShortMap.Entry)this.i.previous()).getLongKey();
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
/* 164 */       return new AbstractLong2ShortSortedMap.ValuesIterator(Long2ShortSortedMaps.fastIterator(AbstractLong2ShortSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(short k) {
/* 169 */       return AbstractLong2ShortSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractLong2ShortSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractLong2ShortSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements ShortIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Long2ShortMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Long2ShortMap.Entry> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public short nextShort() {
/* 199 */       return ((Long2ShortMap.Entry)this.i.next()).getShortValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\AbstractLong2ShortSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */