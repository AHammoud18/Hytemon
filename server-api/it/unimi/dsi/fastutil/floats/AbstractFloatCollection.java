/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public abstract class AbstractFloatCollection
/*     */   extends AbstractCollection<Float>
/*     */   implements FloatCollection
/*     */ {
/*     */   public boolean add(float k) {
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
/*     */   public boolean contains(float k) {
/*  59 */     FloatIterator iterator = iterator();
/*  60 */     while (iterator.hasNext()) { if (k == iterator.nextFloat()) return true;  }
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
/*     */   public boolean rem(float k) {
/*  72 */     FloatIterator iterator = iterator();
/*  73 */     while (iterator.hasNext()) { if (k == iterator.nextFloat()) {
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
/*     */   public boolean add(Float key) {
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
/*     */   public float[] toArray(float[] a) {
/* 115 */     int size = size();
/* 116 */     if (a == null) {
/* 117 */       a = new float[size];
/* 118 */     } else if (a.length < size) {
/* 119 */       a = Arrays.copyOf(a, size);
/*     */     } 
/* 121 */     FloatIterators.unwrap(iterator(), a);
/* 122 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] toFloatArray() {
/* 127 */     int size = size();
/* 128 */     if (size == 0) return FloatArrays.EMPTY_ARRAY; 
/* 129 */     float[] a = new float[size];
/* 130 */     FloatIterators.unwrap(iterator(), a);
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
/*     */   public float[] toFloatArray(float[] a) {
/* 143 */     return toArray(a);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(FloatCollection c) {
/* 148 */     boolean retVal = false;
/* 149 */     for (FloatIterator i = c.iterator(); i.hasNext();) { if (add(i.nextFloat())) retVal = true;  }
/* 150 */      return retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Float> c) {
/* 161 */     if (c instanceof FloatCollection) {
/* 162 */       return addAll((FloatCollection)c);
/*     */     }
/* 164 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(FloatCollection c) {
/* 169 */     for (FloatIterator i = c.iterator(); i.hasNext();) { if (!contains(i.nextFloat())) return false;  }
/* 170 */      return true;
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
/* 181 */     if (c instanceof FloatCollection) {
/* 182 */       return containsAll((FloatCollection)c);
/*     */     }
/* 184 */     return super.containsAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(FloatCollection c) {
/* 189 */     boolean retVal = false;
/* 190 */     for (FloatIterator i = c.iterator(); i.hasNext();) { if (rem(i.nextFloat())) retVal = true;  }
/* 191 */      return retVal;
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
/* 202 */     if (c instanceof FloatCollection) {
/* 203 */       return removeAll((FloatCollection)c);
/*     */     }
/* 205 */     return super.removeAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(FloatCollection c) {
/* 210 */     boolean retVal = false;
/* 211 */     for (FloatIterator i = iterator(); i.hasNext();) { if (!c.contains(i.nextFloat())) {
/* 212 */         i.remove();
/* 213 */         retVal = true;
/*     */       }  }
/* 215 */      return retVal;
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
/* 226 */     if (c instanceof FloatCollection) {
/* 227 */       return retainAll((FloatCollection)c);
/*     */     }
/* 229 */     return super.retainAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 234 */     StringBuilder s = new StringBuilder();
/* 235 */     FloatIterator i = iterator();
/* 236 */     int n = size();
/*     */     
/* 238 */     boolean first = true;
/* 239 */     s.append("{");
/* 240 */     while (n-- != 0) {
/* 241 */       if (first) { first = false; }
/* 242 */       else { s.append(", "); }
/* 243 */        float k = i.nextFloat();
/* 244 */       s.append(String.valueOf(k));
/*     */     } 
/* 246 */     s.append("}");
/* 247 */     return s.toString();
/*     */   }
/*     */   
/*     */   public abstract FloatIterator iterator();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\AbstractFloatCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */