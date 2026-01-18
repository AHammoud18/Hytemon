/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
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
/*     */ public final class AppendableCharSequence
/*     */   implements CharSequence, Appendable
/*     */ {
/*     */   private char[] chars;
/*     */   private int pos;
/*     */   
/*     */   public AppendableCharSequence(int length) {
/*  28 */     this.chars = new char[ObjectUtil.checkPositive(length, "length")];
/*     */   }
/*     */   
/*     */   private AppendableCharSequence(char[] chars) {
/*  32 */     this.chars = ObjectUtil.checkNonEmpty(chars, "chars");
/*  33 */     this.pos = chars.length;
/*     */   }
/*     */   
/*     */   public void setLength(int length) {
/*  37 */     if (length < 0 || length > this.pos) {
/*  38 */       throw new IllegalArgumentException("length: " + length + " (length: >= 0, <= " + this.pos + ')');
/*     */     }
/*  40 */     this.pos = length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int length() {
/*  45 */     return this.pos;
/*     */   }
/*     */ 
/*     */   
/*     */   public char charAt(int index) {
/*  50 */     if (index > this.pos) {
/*  51 */       throw new IndexOutOfBoundsException();
/*     */     }
/*  53 */     return this.chars[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char charAtUnsafe(int index) {
/*  64 */     return this.chars[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public AppendableCharSequence subSequence(int start, int end) {
/*  69 */     if (start == end)
/*     */     {
/*     */ 
/*     */       
/*  73 */       return new AppendableCharSequence(Math.min(16, this.chars.length));
/*     */     }
/*  75 */     return new AppendableCharSequence(Arrays.copyOfRange(this.chars, start, end));
/*     */   }
/*     */ 
/*     */   
/*     */   public AppendableCharSequence append(char c) {
/*  80 */     if (this.pos == this.chars.length) {
/*  81 */       char[] old = this.chars;
/*  82 */       this.chars = new char[old.length << 1];
/*  83 */       System.arraycopy(old, 0, this.chars, 0, old.length);
/*     */     } 
/*  85 */     this.chars[this.pos++] = c;
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public AppendableCharSequence append(CharSequence csq) {
/*  91 */     return append(csq, 0, csq.length());
/*     */   }
/*     */ 
/*     */   
/*     */   public AppendableCharSequence append(CharSequence csq, int start, int end) {
/*  96 */     if (csq.length() < end) {
/*  97 */       throw new IndexOutOfBoundsException("expected: csq.length() >= (" + end + "),but actual is (" + csq
/*  98 */           .length() + ")");
/*     */     }
/* 100 */     int length = end - start;
/* 101 */     if (length > this.chars.length - this.pos) {
/* 102 */       this.chars = expand(this.chars, this.pos + length, this.pos);
/*     */     }
/* 104 */     if (csq instanceof AppendableCharSequence) {
/*     */       
/* 106 */       AppendableCharSequence seq = (AppendableCharSequence)csq;
/* 107 */       char[] src = seq.chars;
/* 108 */       System.arraycopy(src, start, this.chars, this.pos, length);
/* 109 */       this.pos += length;
/* 110 */       return this;
/*     */     } 
/* 112 */     for (int i = start; i < end; i++) {
/* 113 */       this.chars[this.pos++] = csq.charAt(i);
/*     */     }
/*     */     
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 124 */     this.pos = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 129 */     return new String(this.chars, 0, this.pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String substring(int start, int end) {
/* 136 */     int length = end - start;
/* 137 */     if (start > this.pos || length > this.pos) {
/* 138 */       throw new IndexOutOfBoundsException("expected: start and length <= (" + this.pos + ")");
/*     */     }
/*     */     
/* 141 */     return new String(this.chars, start, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String subStringUnsafe(int start, int end) {
/* 150 */     return new String(this.chars, start, end - start);
/*     */   }
/*     */   
/*     */   private static char[] expand(char[] array, int neededSpace, int size) {
/* 154 */     int newCapacity = array.length;
/*     */     
/*     */     do {
/* 157 */       newCapacity <<= 1;
/*     */       
/* 159 */       if (newCapacity < 0) {
/* 160 */         throw new IllegalStateException();
/*     */       }
/*     */     }
/* 163 */     while (neededSpace > newCapacity);
/*     */     
/* 165 */     char[] newArray = new char[newCapacity];
/* 166 */     System.arraycopy(array, 0, newArray, 0, size);
/*     */     
/* 168 */     return newArray;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\AppendableCharSequence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */