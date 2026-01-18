/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public abstract class AbstractByte2DoubleSortedMap
/*     */   extends AbstractByte2DoubleMap
/*     */   implements Byte2DoubleSortedMap
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
/*  54 */       return AbstractByte2DoubleSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractByte2DoubleSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractByte2DoubleSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteComparator comparator() {
/*  69 */       return AbstractByte2DoubleSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte firstByte() {
/*  74 */       return AbstractByte2DoubleSortedMap.this.firstByteKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte lastByte() {
/*  79 */       return AbstractByte2DoubleSortedMap.this.lastByteKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet headSet(byte to) {
/*  84 */       return AbstractByte2DoubleSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet tailSet(byte from) {
/*  89 */       return AbstractByte2DoubleSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet subSet(byte from, byte to) {
/*  94 */       return AbstractByte2DoubleSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBidirectionalIterator iterator(byte from) {
/*  99 */       return new AbstractByte2DoubleSortedMap.KeySetIterator(AbstractByte2DoubleSortedMap.this.byte2DoubleEntrySet().iterator(new AbstractByte2DoubleMap.BasicEntry(from, 0.0D)));
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBidirectionalIterator iterator() {
/* 104 */       return new AbstractByte2DoubleSortedMap.KeySetIterator(Byte2DoubleSortedMaps.fastIterator(AbstractByte2DoubleSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements ByteBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Byte2DoubleMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Byte2DoubleMap.Entry> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte nextByte() {
/* 124 */       return ((Byte2DoubleMap.Entry)this.i.next()).getByteKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte previousByte() {
/* 129 */       return ((Byte2DoubleMap.Entry)this.i.previous()).getByteKey();
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
/* 164 */       return new AbstractByte2DoubleSortedMap.ValuesIterator(Byte2DoubleSortedMaps.fastIterator(AbstractByte2DoubleSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(double k) {
/* 169 */       return AbstractByte2DoubleSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractByte2DoubleSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractByte2DoubleSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements DoubleIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Byte2DoubleMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Byte2DoubleMap.Entry> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public double nextDouble() {
/* 199 */       return ((Byte2DoubleMap.Entry)this.i.next()).getDoubleValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\AbstractByte2DoubleSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */