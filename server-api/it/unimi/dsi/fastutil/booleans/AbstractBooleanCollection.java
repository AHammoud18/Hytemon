/*     */ package it.unimi.dsi.fastutil.booleans;
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
/*     */ public abstract class AbstractBooleanCollection
/*     */   extends AbstractCollection<Boolean>
/*     */   implements BooleanCollection
/*     */ {
/*     */   public boolean add(boolean k) {
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
/*     */   public boolean contains(boolean k) {
/*  59 */     BooleanIterator iterator = iterator();
/*  60 */     while (iterator.hasNext()) { if (k == iterator.nextBoolean()) return true;  }
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
/*     */   public boolean rem(boolean k) {
/*  72 */     BooleanIterator iterator = iterator();
/*  73 */     while (iterator.hasNext()) { if (k == iterator.nextBoolean()) {
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
/*     */   public boolean add(Boolean key) {
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
/*     */   public boolean[] toArray(boolean[] a) {
/* 115 */     int size = size();
/* 116 */     if (a == null) {
/* 117 */       a = new boolean[size];
/* 118 */     } else if (a.length < size) {
/* 119 */       a = Arrays.copyOf(a, size);
/*     */     } 
/* 121 */     BooleanIterators.unwrap(iterator(), a);
/* 122 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] toBooleanArray() {
/* 127 */     int size = size();
/* 128 */     if (size == 0) return BooleanArrays.EMPTY_ARRAY; 
/* 129 */     boolean[] a = new boolean[size];
/* 130 */     BooleanIterators.unwrap(iterator(), a);
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
/*     */   public boolean[] toBooleanArray(boolean[] a) {
/* 143 */     return toArray(a);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(BooleanCollection c) {
/* 148 */     boolean retVal = false;
/* 149 */     for (BooleanIterator i = c.iterator(); i.hasNext();) { if (add(i.nextBoolean())) retVal = true;  }
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
/*     */   public boolean addAll(Collection<? extends Boolean> c) {
/* 161 */     if (c instanceof BooleanCollection) {
/* 162 */       return addAll((BooleanCollection)c);
/*     */     }
/* 164 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(BooleanCollection c) {
/* 169 */     for (BooleanIterator i = c.iterator(); i.hasNext();) { if (!contains(i.nextBoolean())) return false;  }
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
/* 181 */     if (c instanceof BooleanCollection) {
/* 182 */       return containsAll((BooleanCollection)c);
/*     */     }
/* 184 */     return super.containsAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(BooleanCollection c) {
/* 189 */     boolean retVal = false;
/* 190 */     for (BooleanIterator i = c.iterator(); i.hasNext();) { if (rem(i.nextBoolean())) retVal = true;  }
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
/* 202 */     if (c instanceof BooleanCollection) {
/* 203 */       return removeAll((BooleanCollection)c);
/*     */     }
/* 205 */     return super.removeAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(BooleanCollection c) {
/* 210 */     boolean retVal = false;
/* 211 */     for (BooleanIterator i = iterator(); i.hasNext();) { if (!c.contains(i.nextBoolean())) {
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
/* 226 */     if (c instanceof BooleanCollection) {
/* 227 */       return retainAll((BooleanCollection)c);
/*     */     }
/* 229 */     return super.retainAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 234 */     StringBuilder s = new StringBuilder();
/* 235 */     BooleanIterator i = iterator();
/* 236 */     int n = size();
/*     */     
/* 238 */     boolean first = true;
/* 239 */     s.append("{");
/* 240 */     while (n-- != 0) {
/* 241 */       if (first) { first = false; }
/* 242 */       else { s.append(", "); }
/* 243 */        boolean k = i.nextBoolean();
/* 244 */       s.append(String.valueOf(k));
/*     */     } 
/* 246 */     s.append("}");
/* 247 */     return s.toString();
/*     */   }
/*     */   
/*     */   public abstract BooleanIterator iterator();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\AbstractBooleanCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */