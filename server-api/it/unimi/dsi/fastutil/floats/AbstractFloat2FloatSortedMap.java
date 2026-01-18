/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public abstract class AbstractFloat2FloatSortedMap
/*     */   extends AbstractFloat2FloatMap
/*     */   implements Float2FloatSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public FloatSortedSet keySet() {
/*  44 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractFloatSortedSet
/*     */   {
/*     */     public boolean contains(float k) {
/*  51 */       return AbstractFloat2FloatSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  56 */       return AbstractFloat2FloatSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  61 */       AbstractFloat2FloatSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatComparator comparator() {
/*  66 */       return AbstractFloat2FloatSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public float firstFloat() {
/*  71 */       return AbstractFloat2FloatSortedMap.this.firstFloatKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public float lastFloat() {
/*  76 */       return AbstractFloat2FloatSortedMap.this.lastFloatKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet headSet(float to) {
/*  81 */       return AbstractFloat2FloatSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet tailSet(float from) {
/*  86 */       return AbstractFloat2FloatSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet subSet(float from, float to) {
/*  91 */       return AbstractFloat2FloatSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatBidirectionalIterator iterator(float from) {
/*  96 */       return new AbstractFloat2FloatSortedMap.KeySetIterator(AbstractFloat2FloatSortedMap.this.float2FloatEntrySet().iterator(new AbstractFloat2FloatMap.BasicEntry(from, 0.0F)));
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatBidirectionalIterator iterator() {
/* 101 */       return new AbstractFloat2FloatSortedMap.KeySetIterator(Float2FloatSortedMaps.fastIterator(AbstractFloat2FloatSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements FloatBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Float2FloatMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Float2FloatMap.Entry> i) {
/* 116 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public float nextFloat() {
/* 121 */       return ((Float2FloatMap.Entry)this.i.next()).getFloatKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public float previousFloat() {
/* 126 */       return ((Float2FloatMap.Entry)this.i.previous()).getFloatKey();
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
/*     */   public FloatCollection values() {
/* 154 */     return new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractFloatCollection
/*     */   {
/*     */     public FloatIterator iterator() {
/* 161 */       return new AbstractFloat2FloatSortedMap.ValuesIterator(Float2FloatSortedMaps.fastIterator(AbstractFloat2FloatSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(float k) {
/* 166 */       return AbstractFloat2FloatSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 171 */       return AbstractFloat2FloatSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 176 */       AbstractFloat2FloatSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements FloatIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Float2FloatMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Float2FloatMap.Entry> i) {
/* 191 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public float nextFloat() {
/* 196 */       return ((Float2FloatMap.Entry)this.i.next()).getFloatValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 201 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\AbstractFloat2FloatSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */