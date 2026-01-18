/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public abstract class AbstractShort2ShortSortedMap
/*     */   extends AbstractShort2ShortMap
/*     */   implements Short2ShortSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public ShortSortedSet keySet() {
/*  44 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractShortSortedSet
/*     */   {
/*     */     public boolean contains(short k) {
/*  51 */       return AbstractShort2ShortSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  56 */       return AbstractShort2ShortSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  61 */       AbstractShort2ShortSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortComparator comparator() {
/*  66 */       return AbstractShort2ShortSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public short firstShort() {
/*  71 */       return AbstractShort2ShortSortedMap.this.firstShortKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public short lastShort() {
/*  76 */       return AbstractShort2ShortSortedMap.this.lastShortKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet headSet(short to) {
/*  81 */       return AbstractShort2ShortSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet tailSet(short from) {
/*  86 */       return AbstractShort2ShortSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet subSet(short from, short to) {
/*  91 */       return AbstractShort2ShortSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortBidirectionalIterator iterator(short from) {
/*  96 */       return new AbstractShort2ShortSortedMap.KeySetIterator(AbstractShort2ShortSortedMap.this.short2ShortEntrySet().iterator(new AbstractShort2ShortMap.BasicEntry(from, (short)0)));
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortBidirectionalIterator iterator() {
/* 101 */       return new AbstractShort2ShortSortedMap.KeySetIterator(Short2ShortSortedMaps.fastIterator(AbstractShort2ShortSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements ShortBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Short2ShortMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Short2ShortMap.Entry> i) {
/* 116 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public short nextShort() {
/* 121 */       return ((Short2ShortMap.Entry)this.i.next()).getShortKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public short previousShort() {
/* 126 */       return ((Short2ShortMap.Entry)this.i.previous()).getShortKey();
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
/*     */   public ShortCollection values() {
/* 154 */     return new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractShortCollection
/*     */   {
/*     */     public ShortIterator iterator() {
/* 161 */       return new AbstractShort2ShortSortedMap.ValuesIterator(Short2ShortSortedMaps.fastIterator(AbstractShort2ShortSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(short k) {
/* 166 */       return AbstractShort2ShortSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 171 */       return AbstractShort2ShortSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 176 */       AbstractShort2ShortSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements ShortIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Short2ShortMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Short2ShortMap.Entry> i) {
/* 191 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public short nextShort() {
/* 196 */       return ((Short2ShortMap.Entry)this.i.next()).getShortValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 201 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\AbstractShort2ShortSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */