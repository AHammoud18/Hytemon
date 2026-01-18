/*     */ package it.unimi.dsi.fastutil.longs;
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
/*     */ public abstract class AbstractLongCollection
/*     */   extends AbstractCollection<Long>
/*     */   implements LongCollection
/*     */ {
/*     */   public boolean add(long k) {
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
/*     */   public boolean contains(long k) {
/*  59 */     LongIterator iterator = iterator();
/*  60 */     while (iterator.hasNext()) { if (k == iterator.nextLong()) return true;  }
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
/*     */   public boolean rem(long k) {
/*  72 */     LongIterator iterator = iterator();
/*  73 */     while (iterator.hasNext()) { if (k == iterator.nextLong()) {
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
/*     */   public boolean add(Long key) {
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
/*     */   public long[] toArray(long[] a) {
/* 115 */     int size = size();
/* 116 */     if (a == null) {
/* 117 */       a = new long[size];
/* 118 */     } else if (a.length < size) {
/* 119 */       a = Arrays.copyOf(a, size);
/*     */     } 
/* 121 */     LongIterators.unwrap(iterator(), a);
/* 122 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public long[] toLongArray() {
/* 127 */     int size = size();
/* 128 */     if (size == 0) return LongArrays.EMPTY_ARRAY; 
/* 129 */     long[] a = new long[size];
/* 130 */     LongIterators.unwrap(iterator(), a);
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
/*     */   public long[] toLongArray(long[] a) {
/* 143 */     return toArray(a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void forEach(LongConsumer action) {
/* 153 */     super.forEach(action);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean removeIf(LongPredicate filter) {
/* 163 */     return super.removeIf(filter);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(LongCollection c) {
/* 168 */     boolean retVal = false;
/* 169 */     for (LongIterator i = c.iterator(); i.hasNext();) { if (add(i.nextLong())) retVal = true;  }
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
/*     */   public boolean addAll(Collection<? extends Long> c) {
/* 181 */     if (c instanceof LongCollection) {
/* 182 */       return addAll((LongCollection)c);
/*     */     }
/* 184 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(LongCollection c) {
/* 189 */     for (LongIterator i = c.iterator(); i.hasNext();) { if (!contains(i.nextLong())) return false;  }
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
/* 201 */     if (c instanceof LongCollection) {
/* 202 */       return containsAll((LongCollection)c);
/*     */     }
/* 204 */     return super.containsAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(LongCollection c) {
/* 209 */     boolean retVal = false;
/* 210 */     for (LongIterator i = c.iterator(); i.hasNext();) { if (rem(i.nextLong())) retVal = true;  }
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
/* 222 */     if (c instanceof LongCollection) {
/* 223 */       return removeAll((LongCollection)c);
/*     */     }
/* 225 */     return super.removeAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(LongCollection c) {
/* 230 */     boolean retVal = false;
/* 231 */     for (LongIterator i = iterator(); i.hasNext();) { if (!c.contains(i.nextLong())) {
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
/* 246 */     if (c instanceof LongCollection) {
/* 247 */       return retainAll((LongCollection)c);
/*     */     }
/* 249 */     return super.retainAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 254 */     StringBuilder s = new StringBuilder();
/* 255 */     LongIterator i = iterator();
/* 256 */     int n = size();
/*     */     
/* 258 */     boolean first = true;
/* 259 */     s.append("{");
/* 260 */     while (n-- != 0) {
/* 261 */       if (first) { first = false; }
/* 262 */       else { s.append(", "); }
/* 263 */        long k = i.nextLong();
/* 264 */       s.append(String.valueOf(k));
/*     */     } 
/* 266 */     s.append("}");
/* 267 */     return s.toString();
/*     */   }
/*     */   
/*     */   public abstract LongIterator iterator();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\AbstractLongCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */