/*     */ package com.google.gson.internal;
/*     */ 
/*     */ import java.util.AbstractList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Objects;
/*     */ import java.util.RandomAccess;
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
/*     */ public class NonNullElementWrapperList<E>
/*     */   extends AbstractList<E>
/*     */   implements RandomAccess
/*     */ {
/*     */   private final ArrayList<E> delegate;
/*     */   
/*     */   public NonNullElementWrapperList(ArrayList<E> delegate) {
/*  37 */     this.delegate = Objects.<ArrayList<E>>requireNonNull(delegate);
/*     */   }
/*     */ 
/*     */   
/*     */   public E get(int index) {
/*  42 */     return this.delegate.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  47 */     return this.delegate.size();
/*     */   }
/*     */   
/*     */   private E nonNull(E element) {
/*  51 */     if (element == null) {
/*  52 */       throw new NullPointerException("Element must be non-null");
/*     */     }
/*  54 */     return element;
/*     */   }
/*     */ 
/*     */   
/*     */   public E set(int index, E element) {
/*  59 */     return this.delegate.set(index, nonNull(element));
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int index, E element) {
/*  64 */     this.delegate.add(index, nonNull(element));
/*     */   }
/*     */ 
/*     */   
/*     */   public E remove(int index) {
/*  69 */     return this.delegate.remove(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/*  76 */     this.delegate.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(Object o) {
/*  82 */     return this.delegate.remove(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/*  87 */     return this.delegate.removeAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(Collection<?> c) {
/*  92 */     return this.delegate.retainAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object o) {
/*  97 */     return this.delegate.contains(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(Object o) {
/* 102 */     return this.delegate.indexOf(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(Object o) {
/* 107 */     return this.delegate.lastIndexOf(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/* 112 */     return this.delegate.toArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] a) {
/* 117 */     return this.delegate.toArray(a);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 122 */     return this.delegate.equals(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 127 */     return this.delegate.hashCode();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\NonNullElementWrapperList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */