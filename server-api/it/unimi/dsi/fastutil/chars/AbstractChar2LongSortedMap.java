/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
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
/*     */ public abstract class AbstractChar2LongSortedMap
/*     */   extends AbstractChar2LongMap
/*     */   implements Char2LongSortedMap
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
/*  54 */       return AbstractChar2LongSortedMap.this.containsKey(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  59 */       return AbstractChar2LongSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  64 */       AbstractChar2LongSortedMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharComparator comparator() {
/*  69 */       return AbstractChar2LongSortedMap.this.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public char firstChar() {
/*  74 */       return AbstractChar2LongSortedMap.this.firstCharKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public char lastChar() {
/*  79 */       return AbstractChar2LongSortedMap.this.lastCharKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet headSet(char to) {
/*  84 */       return AbstractChar2LongSortedMap.this.headMap(to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet tailSet(char from) {
/*  89 */       return AbstractChar2LongSortedMap.this.tailMap(from).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet subSet(char from, char to) {
/*  94 */       return AbstractChar2LongSortedMap.this.subMap(from, to).keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharBidirectionalIterator iterator(char from) {
/*  99 */       return new AbstractChar2LongSortedMap.KeySetIterator(AbstractChar2LongSortedMap.this.char2LongEntrySet().iterator(new AbstractChar2LongMap.BasicEntry(from, 0L)));
/*     */     }
/*     */ 
/*     */     
/*     */     public CharBidirectionalIterator iterator() {
/* 104 */       return new AbstractChar2LongSortedMap.KeySetIterator(Char2LongSortedMaps.fastIterator(AbstractChar2LongSortedMap.this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class KeySetIterator
/*     */     implements CharBidirectionalIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Char2LongMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Char2LongMap.Entry> i) {
/* 119 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public char nextChar() {
/* 124 */       return ((Char2LongMap.Entry)this.i.next()).getCharKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public char previousChar() {
/* 129 */       return ((Char2LongMap.Entry)this.i.previous()).getCharKey();
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
/*     */   public LongCollection values() {
/* 157 */     return (LongCollection)new ValuesCollection();
/*     */   }
/*     */   
/*     */   protected class ValuesCollection
/*     */     extends AbstractLongCollection
/*     */   {
/*     */     public LongIterator iterator() {
/* 164 */       return new AbstractChar2LongSortedMap.ValuesIterator(Char2LongSortedMaps.fastIterator(AbstractChar2LongSortedMap.this));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(long k) {
/* 169 */       return AbstractChar2LongSortedMap.this.containsValue(k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 174 */       return AbstractChar2LongSortedMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 179 */       AbstractChar2LongSortedMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class ValuesIterator
/*     */     implements LongIterator
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Char2LongMap.Entry> i;
/*     */ 
/*     */ 
/*     */     
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Char2LongMap.Entry> i) {
/* 194 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public long nextLong() {
/* 199 */       return ((Char2LongMap.Entry)this.i.next()).getLongValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 204 */       return this.i.hasNext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\AbstractChar2LongSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */