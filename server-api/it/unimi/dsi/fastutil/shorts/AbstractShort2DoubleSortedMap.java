/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
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
/*     */ public abstract class AbstractShort2DoubleSortedMap
/*     */   extends AbstractShort2DoubleMap
/*     */   implements Short2DoubleSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public ShortSortedSet keySet() {
/*  47 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractShortSortedSet
/*     */   {
/*     */     public boolean contains(short k) {
/*  54 */       return AbstractShort2DoubleSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractShort2DoubleSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractShort2DoubleSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortComparator comparator() {
/*  69 */       return AbstractShort2DoubleSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public short firstShort() {
/*  74 */       return AbstractShort2DoubleSortedMap.this.firstShortKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public short lastShort() {
/*  79 */       return AbstractShort2DoubleSortedMap.this.lastShortKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet headSet(short to) {
/*  84 */       return AbstractShort2DoubleSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet tailSet(short from) {
/*  89 */       return AbstractShort2DoubleSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet subSet(short from, short to) {
/*  94 */       return AbstractShort2DoubleSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortBidirectionalIterator iterator(short from) {
/*  99 */       return new AbstractShort2DoubleSortedMap.KeySetIterator(AbstractShort2DoubleSortedMap.this.short2DoubleEntrySet().iterator(new AbstractShort2DoubleMap.BasicEntry(from, 0.0D)));
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortBidirectionalIterator iterator() {
/* 104 */       return new AbstractShort2DoubleSortedMap.KeySetIterator(Short2DoubleSortedMaps.fastIterator(AbstractShort2DoubleSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements ShortBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Short2DoubleMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Short2DoubleMap.Entry> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public short nextShort() {
/* 124 */       return ((Short2DoubleMap.Entry)this.i.next()).getShortKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public short previousShort() {
/* 129 */       return ((Short2DoubleMap.Entry)this.i.previous()).getShortKey();
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
/*     */   public DoubleCollection values() {
/* 157 */     return (DoubleCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractDoubleCollection
/*     */   {
/*     */     public DoubleIterator iterator() {
/* 164 */       return new AbstractShort2DoubleSortedMap.ValuesIterator(Short2DoubleSortedMaps.fastIterator(AbstractShort2DoubleSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(double k) {
/* 169 */       return AbstractShort2DoubleSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractShort2DoubleSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractShort2DoubleSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements DoubleIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Short2DoubleMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Short2DoubleMap.Entry> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public double nextDouble() {
/* 199 */       return ((Short2DoubleMap.Entry)this.i.next()).getDoubleValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\AbstractShort2DoubleSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */