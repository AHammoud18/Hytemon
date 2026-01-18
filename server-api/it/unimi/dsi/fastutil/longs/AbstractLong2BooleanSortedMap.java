/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
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
/*     */ public abstract class AbstractLong2BooleanSortedMap
/*     */   extends AbstractLong2BooleanMap
/*     */   implements Long2BooleanSortedMap
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
/*  54 */       return AbstractLong2BooleanSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractLong2BooleanSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractLong2BooleanSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongComparator comparator() {
/*  69 */       return AbstractLong2BooleanSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public long firstLong() {
/*  74 */       return AbstractLong2BooleanSortedMap.this.firstLongKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastLong() {
/*  79 */       return AbstractLong2BooleanSortedMap.this.lastLongKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet headSet(long to) {
/*  84 */       return AbstractLong2BooleanSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet tailSet(long from) {
/*  89 */       return AbstractLong2BooleanSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet subSet(long from, long to) {
/*  94 */       return AbstractLong2BooleanSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongBidirectionalIterator iterator(long from) {
/*  99 */       return new AbstractLong2BooleanSortedMap.KeySetIterator(AbstractLong2BooleanSortedMap.this.long2BooleanEntrySet().iterator(new AbstractLong2BooleanMap.BasicEntry(from, false)));
/*     */     }
/*     */ 
/*     */     
/*     */     public LongBidirectionalIterator iterator() {
/* 104 */       return new AbstractLong2BooleanSortedMap.KeySetIterator(Long2BooleanSortedMaps.fastIterator(AbstractLong2BooleanSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements LongBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Long2BooleanMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Long2BooleanMap.Entry> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public long nextLong() {
/* 124 */       return ((Long2BooleanMap.Entry)this.i.next()).getLongKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public long previousLong() {
/* 129 */       return ((Long2BooleanMap.Entry)this.i.previous()).getLongKey();
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
/*     */   public BooleanCollection values() {
/* 157 */     return (BooleanCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractBooleanCollection
/*     */   {
/*     */     public BooleanIterator iterator() {
/* 164 */       return new AbstractLong2BooleanSortedMap.ValuesIterator(Long2BooleanSortedMaps.fastIterator(AbstractLong2BooleanSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(boolean k) {
/* 169 */       return AbstractLong2BooleanSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractLong2BooleanSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractLong2BooleanSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements BooleanIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Long2BooleanMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Long2BooleanMap.Entry> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean nextBoolean() {
/* 199 */       return ((Long2BooleanMap.Entry)this.i.next()).getBooleanValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\AbstractLong2BooleanSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */