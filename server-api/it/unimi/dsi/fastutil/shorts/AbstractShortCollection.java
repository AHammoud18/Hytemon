/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public abstract class AbstractShortCollection
/*     */   extends AbstractCollection<Short>
/*     */   implements ShortCollection
/*     */ {
/*     */   public boolean add(short k) {
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
/*     */   public boolean contains(short k) {
/*  59 */     ShortIterator iterator = iterator();
/*  60 */     while (iterator.hasNext()) { if (k == iterator.nextShort()) return true;  }
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
/*     */   public boolean rem(short k) {
/*  72 */     ShortIterator iterator = iterator();
/*  73 */     while (iterator.hasNext()) { if (k == iterator.nextShort()) {
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
/*     */   public boolean add(Short key) {
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
/*     */   public short[] toArray(short[] a) {
/* 115 */     int size = size();
/* 116 */     if (a == null) {
/* 117 */       a = new short[size];
/* 118 */     } else if (a.length < size) {
/* 119 */       a = Arrays.copyOf(a, size);
/*     */     } 
/* 121 */     ShortIterators.unwrap(iterator(), a);
/* 122 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public short[] toShortArray() {
/* 127 */     int size = size();
/* 128 */     if (size == 0) return ShortArrays.EMPTY_ARRAY; 
/* 129 */     short[] a = new short[size];
/* 130 */     ShortIterators.unwrap(iterator(), a);
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
/*     */   public short[] toShortArray(short[] a) {
/* 143 */     return toArray(a);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(ShortCollection c) {
/* 148 */     boolean retVal = false;
/* 149 */     for (ShortIterator i = c.iterator(); i.hasNext();) { if (add(i.nextShort())) retVal = true;  }
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
/*     */   public boolean addAll(Collection<? extends Short> c) {
/* 161 */     if (c instanceof ShortCollection) {
/* 162 */       return addAll((ShortCollection)c);
/*     */     }
/* 164 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(ShortCollection c) {
/* 169 */     for (ShortIterator i = c.iterator(); i.hasNext();) { if (!contains(i.nextShort())) return false;  }
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
/* 181 */     if (c instanceof ShortCollection) {
/* 182 */       return containsAll((ShortCollection)c);
/*     */     }
/* 184 */     return super.containsAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(ShortCollection c) {
/* 189 */     boolean retVal = false;
/* 190 */     for (ShortIterator i = c.iterator(); i.hasNext();) { if (rem(i.nextShort())) retVal = true;  }
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
/* 202 */     if (c instanceof ShortCollection) {
/* 203 */       return removeAll((ShortCollection)c);
/*     */     }
/* 205 */     return super.removeAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(ShortCollection c) {
/* 210 */     boolean retVal = false;
/* 211 */     for (ShortIterator i = iterator(); i.hasNext();) { if (!c.contains(i.nextShort())) {
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
/* 226 */     if (c instanceof ShortCollection) {
/* 227 */       return retainAll((ShortCollection)c);
/*     */     }
/* 229 */     return super.retainAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 234 */     StringBuilder s = new StringBuilder();
/* 235 */     ShortIterator i = iterator();
/* 236 */     int n = size();
/*     */     
/* 238 */     boolean first = true;
/* 239 */     s.append("{");
/* 240 */     while (n-- != 0) {
/* 241 */       if (first) { first = false; }
/* 242 */       else { s.append(", "); }
/* 243 */        short k = i.nextShort();
/* 244 */       s.append(String.valueOf(k));
/*     */     } 
/* 246 */     s.append("}");
/* 247 */     return s.toString();
/*     */   }
/*     */   
/*     */   public abstract ShortIterator iterator();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\AbstractShortCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */