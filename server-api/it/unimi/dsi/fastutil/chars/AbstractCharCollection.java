/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public abstract class AbstractCharCollection
/*     */   extends AbstractCollection<Character>
/*     */   implements CharCollection
/*     */ {
/*     */   public boolean add(char k) {
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
/*     */   public boolean contains(char k) {
/*  59 */     CharIterator iterator = iterator();
/*  60 */     while (iterator.hasNext()) { if (k == iterator.nextChar()) return true;  }
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
/*     */   public boolean rem(char k) {
/*  72 */     CharIterator iterator = iterator();
/*  73 */     while (iterator.hasNext()) { if (k == iterator.nextChar()) {
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
/*     */   public boolean add(Character key) {
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
/*     */   public char[] toArray(char[] a) {
/* 115 */     int size = size();
/* 116 */     if (a == null) {
/* 117 */       a = new char[size];
/* 118 */     } else if (a.length < size) {
/* 119 */       a = Arrays.copyOf(a, size);
/*     */     } 
/* 121 */     CharIterators.unwrap(iterator(), a);
/* 122 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public char[] toCharArray() {
/* 127 */     int size = size();
/* 128 */     if (size == 0) return CharArrays.EMPTY_ARRAY; 
/* 129 */     char[] a = new char[size];
/* 130 */     CharIterators.unwrap(iterator(), a);
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
/*     */   public char[] toCharArray(char[] a) {
/* 143 */     return toArray(a);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(CharCollection c) {
/* 148 */     boolean retVal = false;
/* 149 */     for (CharIterator i = c.iterator(); i.hasNext();) { if (add(i.nextChar())) retVal = true;  }
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
/*     */   public boolean addAll(Collection<? extends Character> c) {
/* 161 */     if (c instanceof CharCollection) {
/* 162 */       return addAll((CharCollection)c);
/*     */     }
/* 164 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(CharCollection c) {
/* 169 */     for (CharIterator i = c.iterator(); i.hasNext();) { if (!contains(i.nextChar())) return false;  }
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
/* 181 */     if (c instanceof CharCollection) {
/* 182 */       return containsAll((CharCollection)c);
/*     */     }
/* 184 */     return super.containsAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(CharCollection c) {
/* 189 */     boolean retVal = false;
/* 190 */     for (CharIterator i = c.iterator(); i.hasNext();) { if (rem(i.nextChar())) retVal = true;  }
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
/* 202 */     if (c instanceof CharCollection) {
/* 203 */       return removeAll((CharCollection)c);
/*     */     }
/* 205 */     return super.removeAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(CharCollection c) {
/* 210 */     boolean retVal = false;
/* 211 */     for (CharIterator i = iterator(); i.hasNext();) { if (!c.contains(i.nextChar())) {
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
/* 226 */     if (c instanceof CharCollection) {
/* 227 */       return retainAll((CharCollection)c);
/*     */     }
/* 229 */     return super.retainAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 234 */     StringBuilder s = new StringBuilder();
/* 235 */     CharIterator i = iterator();
/* 236 */     int n = size();
/*     */     
/* 238 */     boolean first = true;
/* 239 */     s.append("{");
/* 240 */     while (n-- != 0) {
/* 241 */       if (first) { first = false; }
/* 242 */       else { s.append(", "); }
/* 243 */        char k = i.nextChar();
/* 244 */       s.append(String.valueOf(k));
/*     */     } 
/* 246 */     s.append("}");
/* 247 */     return s.toString();
/*     */   }
/*     */   
/*     */   public abstract CharIterator iterator();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\AbstractCharCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */