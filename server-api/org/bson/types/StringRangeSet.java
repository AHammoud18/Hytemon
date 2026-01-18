/*     */ package org.bson.types;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import org.bson.assertions.Assertions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class StringRangeSet
/*     */   implements Set<String>
/*     */ {
/*  29 */   private static final String[] STRINGS = new String[1024];
/*     */   
/*     */   static {
/*  32 */     for (int i = 0; i < STRINGS.length; i++) {
/*  33 */       STRINGS[i] = String.valueOf(i);
/*     */     }
/*     */   }
/*     */   
/*     */   private final int size;
/*     */   
/*     */   StringRangeSet(int size) {
/*  40 */     Assertions.isTrue("size >= 0", (size >= 0));
/*  41 */     this.size = size;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  46 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  51 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object o) {
/*  56 */     if (!(o instanceof String)) {
/*  57 */       return false;
/*     */     }
/*     */     try {
/*  60 */       int i = Integer.parseInt((String)o);
/*  61 */       return (i >= 0 && i < size());
/*  62 */     } catch (NumberFormatException e) {
/*  63 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<String> iterator() {
/*  69 */     return new Iterator<String>() {
/*  70 */         private int cur = 0;
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/*  74 */           return (this.cur < StringRangeSet.this.size);
/*     */         }
/*     */ 
/*     */         
/*     */         public String next() {
/*  79 */           if (!hasNext()) {
/*  80 */             throw new NoSuchElementException();
/*     */           }
/*  82 */           return StringRangeSet.this.intToString(this.cur++);
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/*  87 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/*  94 */     Object[] retVal = new Object[size()];
/*  95 */     for (int i = 0; i < size(); i++) {
/*  96 */       retVal[i] = intToString(i);
/*     */     }
/*  98 */     return retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] a) {
/* 107 */     T[] retVal = (a.length >= size()) ? a : (T[])Array.newInstance(a.getClass().getComponentType(), this.size);
/* 108 */     for (int i = 0; i < size(); i++) {
/* 109 */       retVal[i] = (T)intToString(i);
/*     */     }
/* 111 */     if (a.length > size()) {
/* 112 */       a[this.size] = null;
/*     */     }
/* 114 */     return retVal;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(String integer) {
/* 119 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object o) {
/* 124 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(Collection<?> c) {
/* 129 */     for (Object e : c) {
/* 130 */       if (!contains(e)) {
/* 131 */         return false;
/*     */       }
/*     */     } 
/* 134 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends String> c) {
/* 139 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(Collection<?> c) {
/* 144 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 149 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 154 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private String intToString(int i) {
/* 158 */     return (i < STRINGS.length) ? 
/* 159 */       STRINGS[i] : 
/* 160 */       Integer.toString(i);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\types\StringRangeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */