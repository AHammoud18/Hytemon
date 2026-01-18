/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public abstract class AbstractByte2FloatSortedMap
/*     */   extends AbstractByte2FloatMap
/*     */   implements Byte2FloatSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public ByteSortedSet keySet() {
/*  47 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractByteSortedSet
/*     */   {
/*     */     public boolean contains(byte k) {
/*  54 */       return AbstractByte2FloatSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractByte2FloatSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractByte2FloatSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteComparator comparator() {
/*  69 */       return AbstractByte2FloatSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte firstByte() {
/*  74 */       return AbstractByte2FloatSortedMap.this.firstByteKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte lastByte() {
/*  79 */       return AbstractByte2FloatSortedMap.this.lastByteKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet headSet(byte to) {
/*  84 */       return AbstractByte2FloatSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet tailSet(byte from) {
/*  89 */       return AbstractByte2FloatSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet subSet(byte from, byte to) {
/*  94 */       return AbstractByte2FloatSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBidirectionalIterator iterator(byte from) {
/*  99 */       return new AbstractByte2FloatSortedMap.KeySetIterator(AbstractByte2FloatSortedMap.this.byte2FloatEntrySet().iterator(new AbstractByte2FloatMap.BasicEntry(from, 0.0F)));
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBidirectionalIterator iterator() {
/* 104 */       return new AbstractByte2FloatSortedMap.KeySetIterator(Byte2FloatSortedMaps.fastIterator(AbstractByte2FloatSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements ByteBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Byte2FloatMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Byte2FloatMap.Entry> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte nextByte() {
/* 124 */       return ((Byte2FloatMap.Entry)this.i.next()).getByteKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte previousByte() {
/* 129 */       return ((Byte2FloatMap.Entry)this.i.previous()).getByteKey();
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
/* 164 */       return new AbstractByte2FloatSortedMap.ValuesIterator(Byte2FloatSortedMaps.fastIterator(AbstractByte2FloatSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(float k) {
/* 169 */       return AbstractByte2FloatSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractByte2FloatSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractByte2FloatSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements FloatIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Byte2FloatMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Byte2FloatMap.Entry> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public float nextFloat() {
/* 199 */       return ((Byte2FloatMap.Entry)this.i.next()).getFloatValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\AbstractByte2FloatSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */