/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import com.google.crypto.tink.annotations.Alpha;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
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
/*     */ @Immutable
/*     */ @Alpha
/*     */ public final class MonitoringAnnotations
/*     */ {
/*  35 */   public static final MonitoringAnnotations EMPTY = newBuilder().build();
/*     */   private final Map<String, String> entries;
/*     */   
/*     */   public static final class Builder {
/*  39 */     private HashMap<String, String> builderEntries = new HashMap<>();
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder addAll(Map<String, String> newEntries) {
/*  43 */       if (this.builderEntries == null) {
/*  44 */         throw new IllegalStateException("addAll cannot be called after build()");
/*     */       }
/*  46 */       this.builderEntries.putAll(newEntries);
/*  47 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder add(String name, String value) {
/*  52 */       if (this.builderEntries == null) {
/*  53 */         throw new IllegalStateException("add cannot be called after build()");
/*     */       }
/*  55 */       this.builderEntries.put(name, value);
/*  56 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public MonitoringAnnotations build() {
/*  61 */       if (this.builderEntries == null) {
/*  62 */         throw new IllegalStateException("cannot call build() twice");
/*     */       }
/*     */       
/*  65 */       MonitoringAnnotations output = new MonitoringAnnotations(Collections.unmodifiableMap(this.builderEntries));
/*     */ 
/*     */ 
/*     */       
/*  69 */       this.builderEntries = null;
/*  70 */       return output;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MonitoringAnnotations(Map<String, String> entries) {
/*  78 */     this.entries = entries;
/*     */   }
/*     */   
/*     */   public static Builder newBuilder() {
/*  82 */     return new Builder();
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, String> toMap() {
/*  87 */     return this.entries;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  92 */     return this.entries.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  97 */     if (!(obj instanceof MonitoringAnnotations)) {
/*  98 */       return false;
/*     */     }
/* 100 */     MonitoringAnnotations that = (MonitoringAnnotations)obj;
/* 101 */     return this.entries.equals(that.entries);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 106 */     return this.entries.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 111 */     return this.entries.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\MonitoringAnnotations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */