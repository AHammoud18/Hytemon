/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.util.AbstractList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
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
/*     */ @Deprecated
/*     */ public class UnmodifiableLazyStringList
/*     */   extends AbstractList<String>
/*     */   implements LazyStringList, RandomAccess
/*     */ {
/*     */   private final LazyStringList list;
/*     */   
/*     */   public UnmodifiableLazyStringList(LazyStringList list) {
/*  32 */     this.list = list;
/*     */   }
/*     */ 
/*     */   
/*     */   public String get(int index) {
/*  37 */     return this.list.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getRaw(int index) {
/*  42 */     return this.list.getRaw(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  47 */     return this.list.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteString getByteString(int index) {
/*  52 */     return this.list.getByteString(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(ByteString element) {
/*  57 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int index, ByteString element) {
/*  62 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAllByteString(Collection<? extends ByteString> element) {
/*  67 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getByteArray(int index) {
/*  72 */     return this.list.getByteArray(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(byte[] element) {
/*  77 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int index, byte[] element) {
/*  82 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAllByteArray(Collection<byte[]> element) {
/*  87 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ListIterator<String> listIterator(final int index) {
/*  92 */     return new ListIterator<String>() {
/*  93 */         ListIterator<String> iter = UnmodifiableLazyStringList.this.list.listIterator(index);
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/*  97 */           return this.iter.hasNext();
/*     */         }
/*     */ 
/*     */         
/*     */         public String next() {
/* 102 */           return this.iter.next();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasPrevious() {
/* 107 */           return this.iter.hasPrevious();
/*     */         }
/*     */ 
/*     */         
/*     */         public String previous() {
/* 112 */           return this.iter.previous();
/*     */         }
/*     */ 
/*     */         
/*     */         public int nextIndex() {
/* 117 */           return this.iter.nextIndex();
/*     */         }
/*     */ 
/*     */         
/*     */         public int previousIndex() {
/* 122 */           return this.iter.previousIndex();
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 127 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public void set(String o) {
/* 132 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public void add(String o) {
/* 137 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<String> iterator() {
/* 144 */     return new Iterator<String>() {
/* 145 */         Iterator<String> iter = UnmodifiableLazyStringList.this.list.iterator();
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 149 */           return this.iter.hasNext();
/*     */         }
/*     */ 
/*     */         
/*     */         public String next() {
/* 154 */           return this.iter.next();
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 159 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<?> getUnderlyingElements() {
/* 167 */     return this.list.getUnderlyingElements();
/*     */   }
/*     */ 
/*     */   
/*     */   public void mergeFrom(LazyStringList other) {
/* 172 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<byte[]> asByteArrayList() {
/* 177 */     return (List)Collections.unmodifiableList((List)this.list.asByteArrayList());
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ByteString> asByteStringList() {
/* 182 */     return Collections.unmodifiableList(this.list.asByteStringList());
/*     */   }
/*     */ 
/*     */   
/*     */   public LazyStringList getUnmodifiableView() {
/* 187 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\UnmodifiableLazyStringList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */