/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public abstract class AbstractFloat2BooleanSortedMap
/*     */   extends AbstractFloat2BooleanMap
/*     */   implements Float2BooleanSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public FloatSortedSet keySet() {
/*  47 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractFloatSortedSet
/*     */   {
/*     */     public boolean contains(float k) {
/*  54 */       return AbstractFloat2BooleanSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractFloat2BooleanSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractFloat2BooleanSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatComparator comparator() {
/*  69 */       return AbstractFloat2BooleanSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public float firstFloat() {
/*  74 */       return AbstractFloat2BooleanSortedMap.this.firstFloatKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public float lastFloat() {
/*  79 */       return AbstractFloat2BooleanSortedMap.this.lastFloatKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet headSet(float to) {
/*  84 */       return AbstractFloat2BooleanSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet tailSet(float from) {
/*  89 */       return AbstractFloat2BooleanSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet subSet(float from, float to) {
/*  94 */       return AbstractFloat2BooleanSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatBidirectionalIterator iterator(float from) {
/*  99 */       return new AbstractFloat2BooleanSortedMap.KeySetIterator(AbstractFloat2BooleanSortedMap.this.float2BooleanEntrySet().iterator(new AbstractFloat2BooleanMap.BasicEntry(from, false)));
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatBidirectionalIterator iterator() {
/* 104 */       return new AbstractFloat2BooleanSortedMap.KeySetIterator(Float2BooleanSortedMaps.fastIterator(AbstractFloat2BooleanSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements FloatBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Float2BooleanMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Float2BooleanMap.Entry> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public float nextFloat() {
/* 124 */       return ((Float2BooleanMap.Entry)this.i.next()).getFloatKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public float previousFloat() {
/* 129 */       return ((Float2BooleanMap.Entry)this.i.previous()).getFloatKey();
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
/* 164 */       return new AbstractFloat2BooleanSortedMap.ValuesIterator(Float2BooleanSortedMaps.fastIterator(AbstractFloat2BooleanSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(boolean k) {
/* 169 */       return AbstractFloat2BooleanSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractFloat2BooleanSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractFloat2BooleanSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements BooleanIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Float2BooleanMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Float2BooleanMap.Entry> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean nextBoolean() {
/* 199 */       return ((Float2BooleanMap.Entry)this.i.next()).getBooleanValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\AbstractFloat2BooleanSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */