/*     */ package com.hypixel.hytale.codec;
/*     */ 
/*     */ import com.hypixel.hytale.codec.store.CodecStore;
/*     */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*     */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VersionedExtraInfo
/*     */   extends ExtraInfo
/*     */ {
/*     */   private final int version;
/*     */   private final ExtraInfo delegate;
/*     */   
/*     */   public VersionedExtraInfo(int version, ExtraInfo delegate) {
/*  26 */     this.version = version;
/*  27 */     this.delegate = delegate;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVersion() {
/*  32 */     return this.version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getKeysSize() {
/*  39 */     return this.delegate.getKeysSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public CodecStore getCodecStore() {
/*  44 */     return this.delegate.getCodecStore();
/*     */   }
/*     */ 
/*     */   
/*     */   public void pushKey(String key) {
/*  49 */     this.delegate.pushKey(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void pushIntKey(int key) {
/*  54 */     this.delegate.pushIntKey(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void pushKey(String key, RawJsonReader reader) {
/*  59 */     this.delegate.pushKey(key, reader);
/*     */   }
/*     */ 
/*     */   
/*     */   public void pushIntKey(int key, RawJsonReader reader) {
/*  64 */     this.delegate.pushIntKey(key, reader);
/*     */   }
/*     */ 
/*     */   
/*     */   public void popKey() {
/*  69 */     this.delegate.popKey();
/*     */   }
/*     */ 
/*     */   
/*     */   public void ignoreUnusedKey(String key) {
/*  74 */     this.delegate.ignoreUnusedKey(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void popIgnoredUnusedKey() {
/*  79 */     this.delegate.popIgnoredUnusedKey();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean consumeIgnoredUnknownKey(@Nonnull RawJsonReader reader) throws IOException {
/*  84 */     return this.delegate.consumeIgnoredUnknownKey(reader);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean consumeIgnoredUnknownKey(@Nonnull String key) {
/*  89 */     return this.delegate.consumeIgnoredUnknownKey(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readUnknownKey(@Nonnull RawJsonReader reader) throws IOException {
/*  94 */     this.delegate.readUnknownKey(reader);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addUnknownKey(@Nonnull String key) {
/*  99 */     this.delegate.addUnknownKey(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public String peekKey() {
/* 104 */     return this.delegate.peekKey();
/*     */   }
/*     */ 
/*     */   
/*     */   public String peekKey(char separator) {
/* 109 */     return this.delegate.peekKey(separator);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getUnknownKeys() {
/* 114 */     return this.delegate.getUnknownKeys();
/*     */   }
/*     */ 
/*     */   
/*     */   public ValidationResults getValidationResults() {
/* 119 */     return this.delegate.getValidationResults();
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, Object> getMetadata() {
/* 124 */     return this.delegate.getMetadata();
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendDetailsTo(@Nonnull StringBuilder sb) {
/* 129 */     this.delegate.appendDetailsTo(sb);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLegacyVersion() {
/* 134 */     return this.delegate.getLegacyVersion();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\VersionedExtraInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */