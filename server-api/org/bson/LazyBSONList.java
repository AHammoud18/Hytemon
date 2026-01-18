/*     */ package org.bson;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
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
/*     */ public class LazyBSONList
/*     */   extends LazyBSONObject
/*     */   implements List
/*     */ {
/*     */   public LazyBSONList(byte[] bytes, LazyBSONCallback callback) {
/*  40 */     super(bytes, callback);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LazyBSONList(byte[] bytes, int offset, LazyBSONCallback callback) {
/*  51 */     super(bytes, offset, callback);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  56 */     return keySet().size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object o) {
/*  61 */     return (indexOf(o) > -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator iterator() {
/*  66 */     return new LazyBSONListIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(Collection<?> collection) {
/*  71 */     Set<Object> values = new HashSet();
/*  72 */     for (Object o : this) {
/*  73 */       values.add(o);
/*     */     }
/*  75 */     return values.containsAll(collection);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object get(int index) {
/*  80 */     return get(String.valueOf(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(Object o) {
/*  85 */     Iterator it = iterator();
/*  86 */     for (int pos = 0; it.hasNext(); pos++) {
/*  87 */       if (o.equals(it.next())) {
/*  88 */         return pos;
/*     */       }
/*     */     } 
/*  91 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(Object o) {
/*  96 */     int lastFound = -1;
/*  97 */     Iterator it = iterator();
/*     */     
/*  99 */     for (int pos = 0; it.hasNext(); pos++) {
/* 100 */       if (o.equals(it.next())) {
/* 101 */         lastFound = pos;
/*     */       }
/*     */     } 
/*     */     
/* 105 */     return lastFound;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class LazyBSONListIterator
/*     */     implements Iterator
/*     */   {
/*     */     private final BsonBinaryReader reader;
/*     */     
/*     */     private BsonType cachedBsonType;
/*     */ 
/*     */     
/*     */     public LazyBSONListIterator() {
/* 119 */       this.reader = LazyBSONList.this.getBsonReader();
/* 120 */       this.reader.readStartDocument();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 125 */       if (this.cachedBsonType == null) {
/* 126 */         this.cachedBsonType = this.reader.readBsonType();
/*     */       }
/* 128 */       return (this.cachedBsonType != BsonType.END_OF_DOCUMENT);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object next() {
/* 133 */       if (!hasNext()) {
/* 134 */         throw new NoSuchElementException();
/*     */       }
/* 136 */       this.cachedBsonType = null;
/* 137 */       this.reader.readName();
/* 138 */       return LazyBSONList.this.readValue(this.reader);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {
/* 144 */       throw new UnsupportedOperationException("Operation is not supported");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListIterator listIterator() {
/* 153 */     throw new UnsupportedOperationException("Operation is not supported instance of this type");
/*     */   }
/*     */ 
/*     */   
/*     */   public ListIterator listIterator(int index) {
/* 158 */     throw new UnsupportedOperationException("Operation is not supported instance of this type");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(Object o) {
/* 163 */     throw new UnsupportedOperationException("Object is read only");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object o) {
/* 168 */     throw new UnsupportedOperationException("Object is read only");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection c) {
/* 173 */     throw new UnsupportedOperationException("Object is read only");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, Collection c) {
/* 178 */     throw new UnsupportedOperationException("Object is read only");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(Collection c) {
/* 183 */     throw new UnsupportedOperationException("Object is read only");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(Collection c) {
/* 188 */     throw new UnsupportedOperationException("Object is read only");
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 193 */     throw new UnsupportedOperationException("Object is read only");
/*     */   }
/*     */ 
/*     */   
/*     */   public Object set(int index, Object element) {
/* 198 */     throw new UnsupportedOperationException("Object is read only");
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int index, Object element) {
/* 203 */     throw new UnsupportedOperationException("Object is read only");
/*     */   }
/*     */ 
/*     */   
/*     */   public Object remove(int index) {
/* 208 */     throw new UnsupportedOperationException("Object is read only");
/*     */   }
/*     */ 
/*     */   
/*     */   public List subList(int fromIndex, int toIndex) {
/* 213 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/* 218 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] toArray(Object[] a) {
/* 223 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\LazyBSONList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */