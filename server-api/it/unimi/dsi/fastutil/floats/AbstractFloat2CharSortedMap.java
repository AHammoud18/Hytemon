/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*     */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*     */ import it.unimi.dsi.fastutil.chars.CharIterator;
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
/*     */ public abstract class AbstractFloat2CharSortedMap
/*     */   extends AbstractFloat2CharMap
/*     */   implements Float2CharSortedMap
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
/*  54 */       return AbstractFloat2CharSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractFloat2CharSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractFloat2CharSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatComparator comparator() {
/*  69 */       return AbstractFloat2CharSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public float firstFloat() {
/*  74 */       return AbstractFloat2CharSortedMap.this.firstFloatKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public float lastFloat() {
/*  79 */       return AbstractFloat2CharSortedMap.this.lastFloatKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet headSet(float to) {
/*  84 */       return AbstractFloat2CharSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet tailSet(float from) {
/*  89 */       return AbstractFloat2CharSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet subSet(float from, float to) {
/*  94 */       return AbstractFloat2CharSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatBidirectionalIterator iterator(float from) {
/*  99 */       return new AbstractFloat2CharSortedMap.KeySetIterator(AbstractFloat2CharSortedMap.this.float2CharEntrySet().iterator(new AbstractFloat2CharMap.BasicEntry(from, false)));
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatBidirectionalIterator iterator() {
/* 104 */       return new AbstractFloat2CharSortedMap.KeySetIterator(Float2CharSortedMaps.fastIterator(AbstractFloat2CharSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements FloatBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Float2CharMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Float2CharMap.Entry> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public float nextFloat() {
/* 124 */       return ((Float2CharMap.Entry)this.i.next()).getFloatKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public float previousFloat() {
/* 129 */       return ((Float2CharMap.Entry)this.i.previous()).getFloatKey();
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
/*     */   public CharCollection values() {
/* 157 */     return (CharCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractCharCollection
/*     */   {
/*     */     public CharIterator iterator() {
/* 164 */       return new AbstractFloat2CharSortedMap.ValuesIterator(Float2CharSortedMaps.fastIterator(AbstractFloat2CharSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(char k) {
/* 169 */       return AbstractFloat2CharSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractFloat2CharSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractFloat2CharSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements CharIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Float2CharMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Float2CharMap.Entry> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public char nextChar() {
/* 199 */       return ((Float2CharMap.Entry)this.i.next()).getCharValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\AbstractFloat2CharSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */