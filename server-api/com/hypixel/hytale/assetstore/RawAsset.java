/*     */ package com.hypixel.hytale.assetstore;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Path;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.annotation.ParametersAreNullableByDefault;
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
/*     */ @ParametersAreNullableByDefault
/*     */ public class RawAsset<K>
/*     */   implements AssetHolder<K>
/*     */ {
/*     */   private final Path parentPath;
/*     */   @Nullable
/*     */   private final K key;
/*     */   private final int lineOffset;
/*     */   private final boolean parentKeyResolved;
/*     */   @Nullable
/*     */   private final K parentKey;
/*     */   @Nullable
/*     */   private final Path path;
/*     */   @Nullable
/*     */   private final char[] buffer;
/*     */   @Nullable
/*     */   private final AssetExtraInfo.Data containerData;
/*     */   @Nonnull
/*     */   private final ContainedAssetCodec.Mode containedAssetMode;
/*     */   
/*     */   public RawAsset(K key, Path path) {
/*  47 */     this.key = key;
/*  48 */     this.lineOffset = 0;
/*  49 */     this.parentKeyResolved = false;
/*  50 */     this.parentKey = null;
/*  51 */     this.path = path;
/*  52 */     this.parentPath = null;
/*  53 */     this.buffer = null;
/*  54 */     this.containerData = null;
/*  55 */     this.containedAssetMode = ContainedAssetCodec.Mode.NONE;
/*     */   }
/*     */   
/*     */   public RawAsset(Path parentPath, K key, K parentKey, int lineOffset, char[] buffer, AssetExtraInfo.Data containerData, @Nonnull ContainedAssetCodec.Mode containedAssetMode) {
/*  59 */     this.key = key;
/*  60 */     this.lineOffset = lineOffset;
/*  61 */     this.parentKeyResolved = true;
/*  62 */     this.parentKey = parentKey;
/*  63 */     this.path = null;
/*  64 */     this.parentPath = parentPath;
/*  65 */     this.buffer = buffer;
/*  66 */     this.containerData = containerData;
/*  67 */     this.containedAssetMode = containedAssetMode;
/*     */   }
/*     */   
/*     */   private RawAsset(K key, boolean parentKeyResolved, K parentKey, Path path, char[] buffer, AssetExtraInfo.Data containerData, @Nonnull ContainedAssetCodec.Mode containedAssetMode) {
/*  71 */     this.key = key;
/*  72 */     this.lineOffset = 0;
/*  73 */     this.parentKeyResolved = parentKeyResolved;
/*  74 */     this.parentKey = parentKey;
/*  75 */     this.path = path;
/*  76 */     this.parentPath = null;
/*  77 */     this.buffer = buffer;
/*  78 */     this.containerData = containerData;
/*  79 */     this.containedAssetMode = containedAssetMode;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public K getKey() {
/*  84 */     return this.key;
/*     */   }
/*     */   
/*     */   public boolean isParentKeyResolved() {
/*  88 */     return this.parentKeyResolved;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public K getParentKey() {
/*  93 */     return this.parentKey;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Path getPath() {
/*  98 */     return this.path;
/*     */   }
/*     */   
/*     */   public Path getParentPath() {
/* 102 */     return this.parentPath;
/*     */   }
/*     */   
/*     */   public int getLineOffset() {
/* 106 */     return this.lineOffset;
/*     */   }
/*     */   
/*     */   public char[] getBuffer() {
/* 110 */     return this.buffer;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ContainedAssetCodec.Mode getContainedAssetMode() {
/* 115 */     return this.containedAssetMode;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public RawJsonReader toRawJsonReader(@Nonnull Supplier<char[]> bufferSupplier) throws IOException {
/* 120 */     if (this.path != null) return RawJsonReader.fromPath(this.path, bufferSupplier.get()); 
/* 121 */     return RawJsonReader.fromBuffer(this.buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public AssetExtraInfo.Data makeData(Class<? extends JsonAssetWithMap<K, ?>> aClass, K key, K parentKey) {
/* 127 */     switch (this.containedAssetMode) { default: throw new MatchException(null, null);case INHERIT_ID: case INHERIT_ID_AND_PARENT: case INJECT_PARENT: case NONE: case GENERATE_ID: break; }  boolean inheritTags = false;
/*     */ 
/*     */ 
/*     */     
/* 131 */     return new AssetExtraInfo.Data(this.containerData, (Class)aClass, key, parentKey, inheritTags);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public RawAsset<K> withResolveKeys(K key, K parentKey) {
/* 140 */     return new RawAsset(key, true, parentKey, this.path, this.buffer, this.containerData, this.containedAssetMode);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 146 */     return "RawAsset{key=" + String.valueOf(this.key) + ", parentKeyResolved=" + this.parentKeyResolved + ", parentKey=" + String.valueOf(this.parentKey) + ", path=" + String.valueOf(this.path) + ", buffer.length=" + (
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 151 */       (this.buffer != null) ? this.buffer.length : -1) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\RawAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */