/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ public abstract class AbstractDouble2ByteSortedMap
/*     */   extends AbstractDouble2ByteMap
/*     */   implements Double2ByteSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public DoubleSortedSet keySet() {
/*  47 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractDoubleSortedSet
/*     */   {
/*     */     public boolean contains(double k) {
/*  54 */       return AbstractDouble2ByteSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractDouble2ByteSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractDouble2ByteSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleComparator comparator() {
/*  69 */       return AbstractDouble2ByteSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public double firstDouble() {
/*  74 */       return AbstractDouble2ByteSortedMap.this.firstDoubleKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public double lastDouble() {
/*  79 */       return AbstractDouble2ByteSortedMap.this.lastDoubleKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet headSet(double to) {
/*  84 */       return AbstractDouble2ByteSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet tailSet(double from) {
/*  89 */       return AbstractDouble2ByteSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet subSet(double from, double to) {
/*  94 */       return AbstractDouble2ByteSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleBidirectionalIterator iterator(double from) {
/*  99 */       return new AbstractDouble2ByteSortedMap.KeySetIterator(AbstractDouble2ByteSortedMap.this.double2ByteEntrySet().iterator(new AbstractDouble2ByteMap.BasicEntry(from, (byte)0)));
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleBidirectionalIterator iterator() {
/* 104 */       return new AbstractDouble2ByteSortedMap.KeySetIterator(Double2ByteSortedMaps.fastIterator(AbstractDouble2ByteSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements DoubleBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Double2ByteMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Double2ByteMap.Entry> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public double nextDouble() {
/* 124 */       return ((Double2ByteMap.Entry)this.i.next()).getDoubleKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public double previousDouble() {
/* 129 */       return ((Double2ByteMap.Entry)this.i.previous()).getDoubleKey();
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
/* 164 */       return new AbstractDouble2ByteSortedMap.ValuesIterator(Double2ByteSortedMaps.fastIterator(AbstractDouble2ByteSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(byte k) {
/* 169 */       return AbstractDouble2ByteSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractDouble2ByteSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractDouble2ByteSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements ByteIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Double2ByteMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Double2ByteMap.Entry> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte nextByte() {
/* 199 */       return ((Double2ByteMap.Entry)this.i.next()).getByteValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\AbstractDouble2ByteSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */