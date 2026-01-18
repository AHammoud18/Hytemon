/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ public abstract class AbstractDoubleCollection
/*     */   extends AbstractCollection<Double>
/*     */   implements DoubleCollection
/*     */ {
/*     */   public boolean add(double k) {
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
/*     */   public boolean contains(double k) {
/*  59 */     DoubleIterator iterator = iterator();
/*  60 */     while (iterator.hasNext()) { if (k == iterator.nextDouble()) return true;  }
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
/*     */   public boolean rem(double k) {
/*  72 */     DoubleIterator iterator = iterator();
/*  73 */     while (iterator.hasNext()) { if (k == iterator.nextDouble()) {
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
/*     */   public boolean add(Double key) {
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
/*     */   public double[] toArray(double[] a) {
/* 115 */     int size = size();
/* 116 */     if (a == null) {
/* 117 */       a = new double[size];
/* 118 */     } else if (a.length < size) {
/* 119 */       a = Arrays.copyOf(a, size);
/*     */     } 
/* 121 */     DoubleIterators.unwrap(iterator(), a);
/* 122 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] toDoubleArray() {
/* 127 */     int size = size();
/* 128 */     if (size == 0) return DoubleArrays.EMPTY_ARRAY; 
/* 129 */     double[] a = new double[size];
/* 130 */     DoubleIterators.unwrap(iterator(), a);
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
/*     */   public double[] toDoubleArray(double[] a) {
/* 143 */     return toArray(a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void forEach(DoubleConsumer action) {
/* 153 */     super.forEach(action);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean removeIf(DoublePredicate filter) {
/* 163 */     return super.removeIf(filter);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(DoubleCollection c) {
/* 168 */     boolean retVal = false;
/* 169 */     for (DoubleIterator i = c.iterator(); i.hasNext();) { if (add(i.nextDouble())) retVal = true;  }
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
/*     */   public boolean addAll(Collection<? extends Double> c) {
/* 181 */     if (c instanceof DoubleCollection) {
/* 182 */       return addAll((DoubleCollection)c);
/*     */     }
/* 184 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(DoubleCollection c) {
/* 189 */     for (DoubleIterator i = c.iterator(); i.hasNext();) { if (!contains(i.nextDouble())) return false;  }
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
/* 201 */     if (c instanceof DoubleCollection) {
/* 202 */       return containsAll((DoubleCollection)c);
/*     */     }
/* 204 */     return super.containsAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(DoubleCollection c) {
/* 209 */     boolean retVal = false;
/* 210 */     for (DoubleIterator i = c.iterator(); i.hasNext();) { if (rem(i.nextDouble())) retVal = true;  }
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
/* 222 */     if (c instanceof DoubleCollection) {
/* 223 */       return removeAll((DoubleCollection)c);
/*     */     }
/* 225 */     return super.removeAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(DoubleCollection c) {
/* 230 */     boolean retVal = false;
/* 231 */     for (DoubleIterator i = iterator(); i.hasNext();) { if (!c.contains(i.nextDouble())) {
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
/* 246 */     if (c instanceof DoubleCollection) {
/* 247 */       return retainAll((DoubleCollection)c);
/*     */     }
/* 249 */     return super.retainAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 254 */     StringBuilder s = new StringBuilder();
/* 255 */     DoubleIterator i = iterator();
/* 256 */     int n = size();
/*     */     
/* 258 */     boolean first = true;
/* 259 */     s.append("{");
/* 260 */     while (n-- != 0) {
/* 261 */       if (first) { first = false; }
/* 262 */       else { s.append(", "); }
/* 263 */        double k = i.nextDouble();
/* 264 */       s.append(String.valueOf(k));
/*     */     } 
/* 266 */     s.append("}");
/* 267 */     return s.toString();
/*     */   }
/*     */   
/*     */   public abstract DoubleIterator iterator();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\AbstractDoubleCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */