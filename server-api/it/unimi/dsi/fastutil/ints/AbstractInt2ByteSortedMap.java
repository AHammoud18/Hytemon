/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
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
/*     */ public abstract class AbstractInt2ByteSortedMap
/*     */   extends AbstractInt2ByteMap
/*     */   implements Int2ByteSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public IntSortedSet keySet() {
/*  47 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractIntSortedSet
/*     */   {
/*     */     public boolean contains(int k) {
/*  54 */       return AbstractInt2ByteSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractInt2ByteSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractInt2ByteSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntComparator comparator() {
/*  69 */       return AbstractInt2ByteSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public int firstInt() {
/*  74 */       return AbstractInt2ByteSortedMap.this.firstIntKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastInt() {
/*  79 */       return AbstractInt2ByteSortedMap.this.lastIntKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet headSet(int to) {
/*  84 */       return AbstractInt2ByteSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet tailSet(int from) {
/*  89 */       return AbstractInt2ByteSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet subSet(int from, int to) {
/*  94 */       return AbstractInt2ByteSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntBidirectionalIterator iterator(int from) {
/*  99 */       return new AbstractInt2ByteSortedMap.KeySetIterator(AbstractInt2ByteSortedMap.this.int2ByteEntrySet().iterator(new AbstractInt2ByteMap.BasicEntry(from, (byte)0)));
/*     */     }
/*     */ 
/*     */     
/*     */     public IntBidirectionalIterator iterator() {
/* 104 */       return new AbstractInt2ByteSortedMap.KeySetIterator(Int2ByteSortedMaps.fastIterator(AbstractInt2ByteSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements IntBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Int2ByteMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Int2ByteMap.Entry> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public int nextInt() {
/* 124 */       return ((Int2ByteMap.Entry)this.i.next()).getIntKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public int previousInt() {
/* 129 */       return ((Int2ByteMap.Entry)this.i.previous()).getIntKey();
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
/*     */   public ByteCollection values() {
/* 157 */     return (ByteCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractByteCollection
/*     */   {
/*     */     public ByteIterator iterator() {
/* 164 */       return new AbstractInt2ByteSortedMap.ValuesIterator(Int2ByteSortedMaps.fastIterator(AbstractInt2ByteSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(byte k) {
/* 169 */       return AbstractInt2ByteSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractInt2ByteSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractInt2ByteSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements ByteIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Int2ByteMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Int2ByteMap.Entry> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte nextByte() {
/* 199 */       return ((Int2ByteMap.Entry)this.i.next()).getByteValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\AbstractInt2ByteSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */