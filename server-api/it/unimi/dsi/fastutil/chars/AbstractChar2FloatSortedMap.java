/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public abstract class AbstractChar2FloatSortedMap
/*     */   extends AbstractChar2FloatMap
/*     */   implements Char2FloatSortedMap
/*     */ {
/*     */   private static final long serialVersionUID = -1773560792952436569L;
/*     */   
/*     */   public CharSortedSet keySet() {
/*  47 */     return new KeySet();
/*     */   }
/*     */   
/*     */   protected class KeySet
/*     */     extends AbstractCharSortedSet
/*     */   {
/*     */     public boolean contains(char k) {
/*  54 */       return AbstractChar2FloatSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractChar2FloatSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractChar2FloatSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharComparator comparator() {
/*  69 */       return AbstractChar2FloatSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public char firstChar() {
/*  74 */       return AbstractChar2FloatSortedMap.this.firstCharKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public char lastChar() {
/*  79 */       return AbstractChar2FloatSortedMap.this.lastCharKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet headSet(char to) {
/*  84 */       return AbstractChar2FloatSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet tailSet(char from) {
/*  89 */       return AbstractChar2FloatSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet subSet(char from, char to) {
/*  94 */       return AbstractChar2FloatSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharBidirectionalIterator iterator(char from) {
/*  99 */       return new AbstractChar2FloatSortedMap.KeySetIterator(AbstractChar2FloatSortedMap.this.char2FloatEntrySet().iterator(new AbstractChar2FloatMap.BasicEntry(from, 0.0F)));
/*     */     }
/*     */ 
/*     */     
/*     */     public CharBidirectionalIterator iterator() {
/* 104 */       return new AbstractChar2FloatSortedMap.KeySetIterator(Char2FloatSortedMaps.fastIterator(AbstractChar2FloatSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements CharBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Char2FloatMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Char2FloatMap.Entry> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public char nextChar() {
/* 124 */       return ((Char2FloatMap.Entry)this.i.next()).getCharKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public char previousChar() {
/* 129 */       return ((Char2FloatMap.Entry)this.i.previous()).getCharKey();
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
/* 164 */       return new AbstractChar2FloatSortedMap.ValuesIterator(Char2FloatSortedMaps.fastIterator(AbstractChar2FloatSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(float k) {
/* 169 */       return AbstractChar2FloatSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractChar2FloatSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractChar2FloatSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements FloatIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Char2FloatMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Char2FloatMap.Entry> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public float nextFloat() {
/* 199 */       return ((Char2FloatMap.Entry)this.i.next()).getFloatValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\AbstractChar2FloatSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */