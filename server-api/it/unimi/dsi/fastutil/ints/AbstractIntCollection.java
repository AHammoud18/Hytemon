/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractIntCollection
/*     */   extends AbstractCollection<Integer>
/*     */   implements IntCollection
/*     */ {
/*     */   public boolean add(int k) {
/*  48 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(int k) {
/*  59 */     IntIterator iterator = iterator();
/*  60 */     while (iterator.hasNext()) { if (k == iterator.nextInt()) return true;  }
/*  61 */      return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean rem(int k) {
/*  72 */     IntIterator iterator = iterator();
/*  73 */     while (iterator.hasNext()) { if (k == iterator.nextInt()) {
/*  74 */         iterator.remove();
/*  75 */         return true;
/*     */       }  }
/*  77 */      return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean add(Integer key) {
/*  88 */     return super.add(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean contains(Object key) {
/*  99 */     return super.contains(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean remove(Object key) {
/* 110 */     return super.remove(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] toArray(int[] a) {
/* 115 */     int size = size();
/* 116 */     if (a == null) {
/* 117 */       a = new int[size];
/* 118 */     } else if (a.length < size) {
/* 119 */       a = Arrays.copyOf(a, size);
/*     */     } 
/* 121 */     IntIterators.unwrap(iterator(), a);
/* 122 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] toIntArray() {
/* 127 */     int size = size();
/* 128 */     if (size == 0) return IntArrays.EMPTY_ARRAY; 
/* 129 */     int[] a = new int[size];
/* 130 */     IntIterators.unwrap(iterator(), a);
/* 131 */     return a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int[] toIntArray(int[] a) {
/* 143 */     return toArray(a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void forEach(IntConsumer action) {
/* 153 */     super.forEach(action);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean removeIf(IntPredicate filter) {
/* 163 */     return super.removeIf(filter);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(IntCollection c) {
/* 168 */     boolean retVal = false;
/* 169 */     for (IntIterator i = c.iterator(); i.hasNext();) { if (add(i.nextInt())) retVal = true;  }
/* 170 */      return retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Integer> c) {
/* 181 */     if (c instanceof IntCollection) {
/* 182 */       return addAll((IntCollection)c);
/*     */     }
/* 184 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(IntCollection c) {
/* 189 */     for (IntIterator i = c.iterator(); i.hasNext();) { if (!contains(i.nextInt())) return false;  }
/* 190 */      return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsAll(Collection<?> c) {
/* 201 */     if (c instanceof IntCollection) {
/* 202 */       return containsAll((IntCollection)c);
/*     */     }
/* 204 */     return super.containsAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(IntCollection c) {
/* 209 */     boolean retVal = false;
/* 210 */     for (IntIterator i = c.iterator(); i.hasNext();) { if (rem(i.nextInt())) retVal = true;  }
/* 211 */      return retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 222 */     if (c instanceof IntCollection) {
/* 223 */       return removeAll((IntCollection)c);
/*     */     }
/* 225 */     return super.removeAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(IntCollection c) {
/* 230 */     boolean retVal = false;
/* 231 */     for (IntIterator i = iterator(); i.hasNext();) { if (!c.contains(i.nextInt())) {
/* 232 */         i.remove();
/* 233 */         retVal = true;
/*     */       }  }
/* 235 */      return retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean retainAll(Collection<?> c) {
/* 246 */     if (c instanceof IntCollection) {
/* 247 */       return retainAll((IntCollection)c);
/*     */     }
/* 249 */     return super.retainAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 254 */     StringBuilder s = new StringBuilder();
/* 255 */     IntIterator i = iterator();
/* 256 */     int n = size();
/*     */     
/* 258 */     boolean first = true;
/* 259 */     s.append("{");
/* 260 */     while (n-- != 0) {
/* 261 */       if (first) { first = false; }
/* 262 */       else { s.append(", "); }
/* 263 */        int k = i.nextInt();
/* 264 */       s.append(String.valueOf(k));
/*     */     } 
/* 266 */     s.append("}");
/* 267 */     return s.toString();
/*     */   }
/*     */   
/*     */   public abstract IntIterator iterator();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\AbstractIntCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */