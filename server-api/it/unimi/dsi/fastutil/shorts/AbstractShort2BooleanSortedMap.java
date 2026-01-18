/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public abstract class AbstractShort2BooleanSortedMap
/*     */   extends AbstractShort2BooleanMap
/*     */   implements Short2BooleanSortedMap
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
/*  54 */       return AbstractShort2BooleanSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractShort2BooleanSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractShort2BooleanSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortComparator comparator() {
/*  69 */       return AbstractShort2BooleanSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public short firstShort() {
/*  74 */       return AbstractShort2BooleanSortedMap.this.firstShortKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public short lastShort() {
/*  79 */       return AbstractShort2BooleanSortedMap.this.lastShortKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet headSet(short to) {
/*  84 */       return AbstractShort2BooleanSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet tailSet(short from) {
/*  89 */       return AbstractShort2BooleanSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet subSet(short from, short to) {
/*  94 */       return AbstractShort2BooleanSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortBidirectionalIterator iterator(short from) {
/*  99 */       return new AbstractShort2BooleanSortedMap.KeySetIterator(AbstractShort2BooleanSortedMap.this.short2BooleanEntrySet().iterator(new AbstractShort2BooleanMap.BasicEntry(from, false)));
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortBidirectionalIterator iterator() {
/* 104 */       return new AbstractShort2BooleanSortedMap.KeySetIterator(Short2BooleanSortedMaps.fastIterator(AbstractShort2BooleanSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements ShortBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Short2BooleanMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Short2BooleanMap.Entry> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public short nextShort() {
/* 124 */       return ((Short2BooleanMap.Entry)this.i.next()).getShortKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public short previousShort() {
/* 129 */       return ((Short2BooleanMap.Entry)this.i.previous()).getShortKey();
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
/* 164 */       return new AbstractShort2BooleanSortedMap.ValuesIterator(Short2BooleanSortedMaps.fastIterator(AbstractShort2BooleanSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(boolean k) {
/* 169 */       return AbstractShort2BooleanSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractShort2BooleanSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractShort2BooleanSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements BooleanIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Short2BooleanMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Short2BooleanMap.Entry> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean nextBoolean() {
/* 199 */       return ((Short2BooleanMap.Entry)this.i.next()).getBooleanValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\AbstractShort2BooleanSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */