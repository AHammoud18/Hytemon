/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public abstract class AbstractByte2LongSortedMap
/*     */   extends AbstractByte2LongMap
/*     */   implements Byte2LongSortedMap
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
/*  54 */       return AbstractByte2LongSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractByte2LongSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractByte2LongSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteComparator comparator() {
/*  69 */       return AbstractByte2LongSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte firstByte() {
/*  74 */       return AbstractByte2LongSortedMap.this.firstByteKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte lastByte() {
/*  79 */       return AbstractByte2LongSortedMap.this.lastByteKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet headSet(byte to) {
/*  84 */       return AbstractByte2LongSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet tailSet(byte from) {
/*  89 */       return AbstractByte2LongSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet subSet(byte from, byte to) {
/*  94 */       return AbstractByte2LongSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBidirectionalIterator iterator(byte from) {
/*  99 */       return new AbstractByte2LongSortedMap.KeySetIterator(AbstractByte2LongSortedMap.this.byte2LongEntrySet().iterator(new AbstractByte2LongMap.BasicEntry(from, 0L)));
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBidirectionalIterator iterator() {
/* 104 */       return new AbstractByte2LongSortedMap.KeySetIterator(Byte2LongSortedMaps.fastIterator(AbstractByte2LongSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements ByteBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Byte2LongMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Byte2LongMap.Entry> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte nextByte() {
/* 124 */       return ((Byte2LongMap.Entry)this.i.next()).getByteKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte previousByte() {
/* 129 */       return ((Byte2LongMap.Entry)this.i.previous()).getByteKey();
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
/* 164 */       return new AbstractByte2LongSortedMap.ValuesIterator(Byte2LongSortedMaps.fastIterator(AbstractByte2LongSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(long k) {
/* 169 */       return AbstractByte2LongSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractByte2LongSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractByte2LongSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements LongIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Byte2LongMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Byte2LongMap.Entry> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public long nextLong() {
/* 199 */       return ((Byte2LongMap.Entry)this.i.next()).getLongValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\AbstractByte2LongSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */