/*     */ package com.google.crypto.tink.util;
/*     */ 
/*     */ import com.google.crypto.tink.subtle.Hex;
/*     */ import com.google.errorprone.annotations.Immutable;
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
/*     */ @Immutable
/*     */ public final class Bytes
/*     */ {
/*     */   private final byte[] data;
/*     */   
/*     */   public static Bytes copyFrom(byte[] data) {
/*  38 */     if (data == null) {
/*  39 */       throw new NullPointerException("data must be non-null");
/*     */     }
/*  41 */     return copyFrom(data, 0, data.length);
/*     */   }
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
/*     */   public static Bytes copyFrom(byte[] data, int start, int len) {
/*  55 */     if (data == null) {
/*  56 */       throw new NullPointerException("data must be non-null");
/*     */     }
/*  58 */     if (start + len > data.length) {
/*  59 */       len = data.length - start;
/*     */     }
/*  61 */     return new Bytes(data, start, len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] toByteArray() {
/*  68 */     byte[] result = new byte[this.data.length];
/*  69 */     System.arraycopy(this.data, 0, result, 0, this.data.length);
/*  70 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  77 */     return this.data.length;
/*     */   }
/*     */   
/*     */   private Bytes(byte[] buf, int start, int len) {
/*  81 */     this.data = new byte[len];
/*  82 */     System.arraycopy(buf, start, this.data, 0, len);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  87 */     if (!(o instanceof Bytes)) {
/*  88 */       return false;
/*     */     }
/*  90 */     Bytes other = (Bytes)o;
/*  91 */     return Arrays.equals(other.data, this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  96 */     return Arrays.hashCode(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 101 */     return "Bytes(" + Hex.encode(this.data) + ")";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tin\\util\Bytes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */