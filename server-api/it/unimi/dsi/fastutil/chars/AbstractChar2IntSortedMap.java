/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
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
/*     */ public abstract class AbstractChar2IntSortedMap
/*     */   extends AbstractChar2IntMap
/*     */   implements Char2IntSortedMap
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
/*  54 */       return AbstractChar2IntSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractChar2IntSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractChar2IntSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharComparator comparator() {
/*  69 */       return AbstractChar2IntSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public char firstChar() {
/*  74 */       return AbstractChar2IntSortedMap.this.firstCharKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public char lastChar() {
/*  79 */       return AbstractChar2IntSortedMap.this.lastCharKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet headSet(char to) {
/*  84 */       return AbstractChar2IntSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet tailSet(char from) {
/*  89 */       return AbstractChar2IntSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet subSet(char from, char to) {
/*  94 */       return AbstractChar2IntSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharBidirectionalIterator iterator(char from) {
/*  99 */       return new AbstractChar2IntSortedMap.KeySetIterator(AbstractChar2IntSortedMap.this.char2IntEntrySet().iterator(new AbstractChar2IntMap.BasicEntry(from, 0)));
/*     */     }
/*     */ 
/*     */     
/*     */     public CharBidirectionalIterator iterator() {
/* 104 */       return new AbstractChar2IntSortedMap.KeySetIterator(Char2IntSortedMaps.fastIterator(AbstractChar2IntSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements CharBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Char2IntMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Char2IntMap.Entry> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public char nextChar() {
/* 124 */       return ((Char2IntMap.Entry)this.i.next()).getCharKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public char previousChar() {
/* 129 */       return ((Char2IntMap.Entry)this.i.previous()).getCharKey();
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
/*     */   public IntCollection values() {
/* 157 */     return (IntCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractIntCollection
/*     */   {
/*     */     public IntIterator iterator() {
/* 164 */       return new AbstractChar2IntSortedMap.ValuesIterator(Char2IntSortedMaps.fastIterator(AbstractChar2IntSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(int k) {
/* 169 */       return AbstractChar2IntSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractChar2IntSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractChar2IntSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements IntIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Char2IntMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Char2IntMap.Entry> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public int nextInt() {
/* 199 */       return ((Char2IntMap.Entry)this.i.next()).getIntValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\AbstractChar2IntSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */