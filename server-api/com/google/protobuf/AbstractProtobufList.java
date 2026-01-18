/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.util.AbstractList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
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
/*     */ abstract class AbstractProtobufList<E>
/*     */   extends AbstractList<E>
/*     */   implements Internal.ProtobufList<E>
/*     */ {
/*     */   protected static final int DEFAULT_CAPACITY = 10;
/*     */   private boolean isMutable;
/*     */   
/*     */   AbstractProtobufList() {
/*  32 */     this(true);
/*     */   }
/*     */ 
/*     */   
/*     */   AbstractProtobufList(boolean isMutable) {
/*  37 */     this.isMutable = isMutable;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  43 */     if (o == this) {
/*  44 */       return true;
/*     */     }
/*  46 */     if (!(o instanceof List)) {
/*  47 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  52 */     if (!(o instanceof java.util.RandomAccess)) {
/*  53 */       return super.equals(o);
/*     */     }
/*     */     
/*  56 */     List<?> other = (List)o;
/*  57 */     int size = size();
/*  58 */     if (size != other.size()) {
/*  59 */       return false;
/*     */     }
/*  61 */     for (int i = 0; i < size; i++) {
/*  62 */       if (!get(i).equals(other.get(i))) {
/*  63 */         return false;
/*     */       }
/*     */     } 
/*  66 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  71 */     int size = size();
/*  72 */     int hashCode = 1;
/*  73 */     for (int i = 0; i < size; i++) {
/*  74 */       hashCode = 31 * hashCode + get(i).hashCode();
/*     */     }
/*  76 */     return hashCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(E e) {
/*  81 */     ensureIsMutable();
/*  82 */     return super.add(e);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int index, E element) {
/*  87 */     ensureIsMutable();
/*  88 */     super.add(index, element);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends E> c) {
/*  93 */     ensureIsMutable();
/*  94 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends E> c) {
/*  99 */     ensureIsMutable();
/* 100 */     return super.addAll(index, c);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 105 */     ensureIsMutable();
/* 106 */     super.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isModifiable() {
/* 111 */     return this.isMutable;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void makeImmutable() {
/* 116 */     if (this.isMutable) {
/* 117 */       this.isMutable = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public E remove(int index) {
/* 123 */     ensureIsMutable();
/* 124 */     return super.remove(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object o) {
/* 129 */     ensureIsMutable();
/* 130 */     int index = indexOf(o);
/* 131 */     if (index == -1) {
/* 132 */       return false;
/*     */     }
/* 134 */     remove(index);
/* 135 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 140 */     ensureIsMutable();
/* 141 */     return super.removeAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(Collection<?> c) {
/* 146 */     ensureIsMutable();
/* 147 */     return super.retainAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public E set(int index, E element) {
/* 152 */     ensureIsMutable();
/* 153 */     return super.set(index, element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void ensureIsMutable() {
/* 161 */     if (!this.isMutable)
/* 162 */       throw new UnsupportedOperationException(); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\AbstractProtobufList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */