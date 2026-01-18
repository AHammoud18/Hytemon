/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public final class PrefixMap<P>
/*     */ {
/*  41 */   private static final Bytes EMPTY_BYTES = Bytes.copyFrom(new byte[0]);
/*     */ 
/*     */   
/*     */   private final Map<Bytes, List<P>> entries;
/*     */ 
/*     */   
/*     */   public static class Builder<P>
/*     */   {
/*     */     @CanIgnoreReturnValue
/*     */     public Builder<P> put(Bytes prefix, P primitive) throws GeneralSecurityException {
/*     */       List<P> listForThisPrefix;
/*  52 */       if (prefix.size() != 0 && prefix.size() != 5) {
/*  53 */         throw new GeneralSecurityException("PrefixMap only supports 0 and 5 byte prefixes");
/*     */       }
/*     */       
/*  56 */       if (this.entries.containsKey(prefix)) {
/*  57 */         listForThisPrefix = this.entries.get(prefix);
/*     */       } else {
/*  59 */         listForThisPrefix = new ArrayList<>();
/*  60 */         this.entries.put(prefix, listForThisPrefix);
/*     */       } 
/*  62 */       listForThisPrefix.add(primitive);
/*  63 */       return this;
/*     */     }
/*     */     
/*     */     public PrefixMap<P> build() {
/*  67 */       return new PrefixMap<>(this.entries);
/*     */     }
/*     */     
/*  70 */     private final Map<Bytes, List<P>> entries = new HashMap<>(); }
/*     */   
/*     */   private static class ConcatenatedIterator<P> implements Iterator<P> {
/*     */     private final Iterator<P> it0;
/*     */     
/*     */     private ConcatenatedIterator(Iterator<P> it0, Iterator<P> it1) {
/*  76 */       this.it0 = it0;
/*  77 */       this.it1 = it1;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/*  82 */       return (this.it0.hasNext() || this.it1.hasNext());
/*     */     }
/*     */     private final Iterator<P> it1;
/*     */     
/*     */     public P next() {
/*  87 */       if (this.it0.hasNext()) {
/*  88 */         return this.it0.next();
/*     */       }
/*  90 */       return this.it1.next();
/*     */     }
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
/*     */   public Iterable<P> getAllWithMatchingPrefix(byte[] text) {
/* 105 */     final List<P> zeroByteEntriesOrNull = this.entries.get(EMPTY_BYTES);
/*     */     
/* 107 */     final List<P> fiveByteEntriesOrNull = (text.length >= 5) ? this.entries.get(Bytes.copyFrom(text, 0, 5)) : null;
/* 108 */     if (zeroByteEntriesOrNull == null && fiveByteEntriesOrNull == null) {
/* 109 */       return new ArrayList<>();
/*     */     }
/* 111 */     if (zeroByteEntriesOrNull == null) {
/* 112 */       return fiveByteEntriesOrNull;
/*     */     }
/* 114 */     if (fiveByteEntriesOrNull == null) {
/* 115 */       return zeroByteEntriesOrNull;
/*     */     }
/* 117 */     return new Iterable<P>()
/*     */       {
/*     */         public Iterator<P> iterator() {
/* 120 */           return new PrefixMap.ConcatenatedIterator<>(fiveByteEntriesOrNull
/* 121 */               .iterator(), zeroByteEntriesOrNull.iterator());
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private PrefixMap(Map<Bytes, List<P>> entries) {
/* 127 */     this.entries = entries;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\PrefixMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */