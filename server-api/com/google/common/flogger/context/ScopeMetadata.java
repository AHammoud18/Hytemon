/*     */ package com.google.common.flogger.context;
/*     */ 
/*     */ import com.google.common.flogger.MetadataKey;
/*     */ import com.google.common.flogger.backend.Metadata;
/*     */ import com.google.common.flogger.util.Checks;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
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
/*     */ public abstract class ScopeMetadata
/*     */   extends Metadata
/*     */ {
/*     */   private static final class Entry<T>
/*     */   {
/*     */     final MetadataKey<T> key;
/*     */     final T value;
/*     */     
/*     */     Entry(MetadataKey<T> key, T value) {
/*  41 */       this.key = (MetadataKey<T>)Checks.checkNotNull(key, "key");
/*  42 */       this.value = (T)Checks.checkNotNull(value, "value");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*  52 */     private static final ScopeMetadata.Entry<?>[] EMPTY_ARRAY = (ScopeMetadata.Entry<?>[])new ScopeMetadata.Entry[0];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     private final List<ScopeMetadata.Entry<?>> entries = new ArrayList<ScopeMetadata.Entry<?>>(2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> Builder add(MetadataKey<T> key, T value) {
/*  65 */       this.entries.add(new ScopeMetadata.Entry(key, value));
/*  66 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ScopeMetadata build() {
/*  72 */       return new ScopeMetadata.ImmutableScopeMetadata((ScopeMetadata.Entry<?>[])this.entries.<ScopeMetadata.Entry>toArray((ScopeMetadata.Entry[])EMPTY_ARRAY));
/*     */     }
/*     */     
/*     */     private Builder() {} }
/*     */   
/*     */   public static Builder builder() {
/*  78 */     return new Builder();
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T> ScopeMetadata singleton(MetadataKey<T> key, T value) {
/*  83 */     return new SingletonMetadata(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ScopeMetadata none() {
/*  89 */     return EmptyMetadata.INSTANCE;
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
/*     */   private ScopeMetadata() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetadataKey<?> getKey(int n) {
/* 112 */     return (get(n)).key;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValue(int n) {
/* 117 */     return (get(n)).value;
/*     */   }
/*     */   public abstract ScopeMetadata concatenate(ScopeMetadata paramScopeMetadata);
/*     */   
/*     */   abstract Entry<?> get(int paramInt);
/*     */   
/*     */   private static final class ImmutableScopeMetadata extends ScopeMetadata { ImmutableScopeMetadata(ScopeMetadata.Entry<?>[] entries) {
/* 124 */       this.entries = entries;
/*     */     }
/*     */     private final ScopeMetadata.Entry<?>[] entries;
/*     */     
/*     */     public int size() {
/* 129 */       return this.entries.length;
/*     */     }
/*     */ 
/*     */     
/*     */     ScopeMetadata.Entry<?> get(int n) {
/* 134 */       return this.entries[n];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @NullableDecl
/*     */     public <T> T findValue(MetadataKey<T> key) {
/* 141 */       Checks.checkArgument(!key.canRepeat(), "metadata key must be single valued");
/* 142 */       for (int n = this.entries.length - 1; n >= 0; n--) {
/* 143 */         ScopeMetadata.Entry<?> e = this.entries[n];
/* 144 */         if (e.key.equals(key)) {
/* 145 */           return e.value;
/*     */         }
/*     */       } 
/* 148 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ScopeMetadata concatenate(ScopeMetadata metadata) {
/* 153 */       int extraSize = metadata.size();
/* 154 */       if (extraSize == 0) {
/* 155 */         return this;
/*     */       }
/* 157 */       if (this.entries.length == 0) {
/* 158 */         return metadata;
/*     */       }
/* 160 */       ScopeMetadata.Entry[] arrayOfEntry = Arrays.<ScopeMetadata.Entry>copyOf((ScopeMetadata.Entry[])this.entries, this.entries.length + extraSize);
/* 161 */       for (int i = 0; i < extraSize; i++) {
/* 162 */         arrayOfEntry[i + this.entries.length] = metadata.get(i);
/*     */       }
/* 164 */       return new ImmutableScopeMetadata((ScopeMetadata.Entry<?>[])arrayOfEntry);
/*     */     } }
/*     */ 
/*     */   
/*     */   private static final class SingletonMetadata extends ScopeMetadata {
/*     */     private final ScopeMetadata.Entry<?> entry;
/*     */     
/*     */     <T> SingletonMetadata(MetadataKey<T> key, T value) {
/* 172 */       this.entry = new ScopeMetadata.Entry(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 177 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     ScopeMetadata.Entry<?> get(int n) {
/* 182 */       if (n == 0) {
/* 183 */         return this.entry;
/*     */       }
/* 185 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @NullableDecl
/*     */     public <R> R findValue(MetadataKey<R> key) {
/* 192 */       Checks.checkArgument(!key.canRepeat(), "metadata key must be single valued");
/* 193 */       return this.entry.key.equals(key) ? (R)this.entry.value : null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ScopeMetadata concatenate(ScopeMetadata metadata) {
/* 199 */       int extraSize = metadata.size();
/* 200 */       if (extraSize == 0) {
/* 201 */         return this;
/*     */       }
/* 203 */       ScopeMetadata.Entry[] arrayOfEntry = new ScopeMetadata.Entry[extraSize + 1];
/* 204 */       arrayOfEntry[0] = this.entry;
/* 205 */       for (int i = 0; i < extraSize; i++) {
/* 206 */         arrayOfEntry[i + 1] = metadata.get(i);
/*     */       }
/* 208 */       return new ScopeMetadata.ImmutableScopeMetadata((ScopeMetadata.Entry<?>[])arrayOfEntry);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class EmptyMetadata
/*     */     extends ScopeMetadata
/*     */   {
/* 217 */     static final ScopeMetadata INSTANCE = new EmptyMetadata();
/*     */ 
/*     */     
/*     */     public int size() {
/* 221 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     ScopeMetadata.Entry<?> get(int n) {
/* 226 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @NullableDecl
/*     */     public <T> T findValue(MetadataKey<T> key) {
/* 233 */       Checks.checkArgument(!key.canRepeat(), "metadata key must be single valued");
/* 234 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ScopeMetadata concatenate(ScopeMetadata metadata) {
/* 239 */       return metadata;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\context\ScopeMetadata.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */