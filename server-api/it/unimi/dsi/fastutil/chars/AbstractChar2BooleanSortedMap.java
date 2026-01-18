/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public abstract class AbstractChar2BooleanSortedMap
/*     */   extends AbstractChar2BooleanMap
/*     */   implements Char2BooleanSortedMap
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
/*  54 */       return AbstractChar2BooleanSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractChar2BooleanSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractChar2BooleanSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharComparator comparator() {
/*  69 */       return AbstractChar2BooleanSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public char firstChar() {
/*  74 */       return AbstractChar2BooleanSortedMap.this.firstCharKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public char lastChar() {
/*  79 */       return AbstractChar2BooleanSortedMap.this.lastCharKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet headSet(char to) {
/*  84 */       return AbstractChar2BooleanSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet tailSet(char from) {
/*  89 */       return AbstractChar2BooleanSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet subSet(char from, char to) {
/*  94 */       return AbstractChar2BooleanSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharBidirectionalIterator iterator(char from) {
/*  99 */       return new AbstractChar2BooleanSortedMap.KeySetIterator(AbstractChar2BooleanSortedMap.this.char2BooleanEntrySet().iterator(new AbstractChar2BooleanMap.BasicEntry(from, false)));
/*     */     }
/*     */ 
/*     */     
/*     */     public CharBidirectionalIterator iterator() {
/* 104 */       return new AbstractChar2BooleanSortedMap.KeySetIterator(Char2BooleanSortedMaps.fastIterator(AbstractChar2BooleanSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements CharBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Char2BooleanMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Char2BooleanMap.Entry> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public char nextChar() {
/* 124 */       return ((Char2BooleanMap.Entry)this.i.next()).getCharKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public char previousChar() {
/* 129 */       return ((Char2BooleanMap.Entry)this.i.previous()).getCharKey();
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
/* 164 */       return new AbstractChar2BooleanSortedMap.ValuesIterator(Char2BooleanSortedMaps.fastIterator(AbstractChar2BooleanSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(boolean k) {
/* 169 */       return AbstractChar2BooleanSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractChar2BooleanSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractChar2BooleanSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements BooleanIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Char2BooleanMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Char2BooleanMap.Entry> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean nextBoolean() {
/* 199 */       return ((Char2BooleanMap.Entry)this.i.next()).getBooleanValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\AbstractChar2BooleanSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */