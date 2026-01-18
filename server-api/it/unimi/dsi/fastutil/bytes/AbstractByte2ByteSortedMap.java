/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public abstract class AbstractByte2ByteSortedMap
/*     */   extends AbstractByte2ByteMap
/*     */   implements Byte2ByteSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public ByteSortedSet keySet() {
/*  44 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractByteSortedSet
/*     */   {
/*     */     public boolean contains(byte k) {
/*  51 */       return AbstractByte2ByteSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  56 */       return AbstractByte2ByteSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  61 */       AbstractByte2ByteSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteComparator comparator() {
/*  66 */       return AbstractByte2ByteSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte firstByte() {
/*  71 */       return AbstractByte2ByteSortedMap.this.firstByteKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte lastByte() {
/*  76 */       return AbstractByte2ByteSortedMap.this.lastByteKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet headSet(byte to) {
/*  81 */       return AbstractByte2ByteSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet tailSet(byte from) {
/*  86 */       return AbstractByte2ByteSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet subSet(byte from, byte to) {
/*  91 */       return AbstractByte2ByteSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBidirectionalIterator iterator(byte from) {
/*  96 */       return new AbstractByte2ByteSortedMap.KeySetIterator(AbstractByte2ByteSortedMap.this.byte2ByteEntrySet().iterator(new AbstractByte2ByteMap.BasicEntry(from, (byte)0)));
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBidirectionalIterator iterator() {
/* 101 */       return new AbstractByte2ByteSortedMap.KeySetIterator(Byte2ByteSortedMaps.fastIterator(AbstractByte2ByteSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements ByteBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Byte2ByteMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Byte2ByteMap.Entry> i) {
/* 116 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte nextByte() {
/* 121 */       return ((Byte2ByteMap.Entry)this.i.next()).getByteKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte previousByte() {
/* 126 */       return ((Byte2ByteMap.Entry)this.i.previous()).getByteKey();
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
/*     */   public ByteCollection values() {
/* 154 */     return new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractByteCollection
/*     */   {
/*     */     public ByteIterator iterator() {
/* 161 */       return new AbstractByte2ByteSortedMap.ValuesIterator(Byte2ByteSortedMaps.fastIterator(AbstractByte2ByteSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(byte k) {
/* 166 */       return AbstractByte2ByteSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 171 */       return AbstractByte2ByteSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 176 */       AbstractByte2ByteSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements ByteIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Byte2ByteMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Byte2ByteMap.Entry> i) {
/* 191 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte nextByte() {
/* 196 */       return ((Byte2ByteMap.Entry)this.i.next()).getByteValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 201 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\AbstractByte2ByteSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */