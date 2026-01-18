/*     */ package com.hypixel.hytale.server.core.asset.common;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.PatternUtil;
/*     */ import com.hypixel.hytale.protocol.Asset;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.util.HashUtil;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CommonAsset
/*     */   implements NetworkSerializable<Asset>
/*     */ {
/*     */   public static final int HASH_LENGTH = 64;
/*  22 */   public static final Pattern HASH_PATTERN = Pattern.compile("^[A-Fa-f0-9]{64}$");
/*     */   
/*     */   @Nonnull
/*     */   private final String name;
/*     */   
/*     */   @Nonnull
/*     */   private final String hash;
/*     */   protected transient WeakReference<CompletableFuture<byte[]>> blob;
/*     */   protected transient SoftReference<Asset> cachedPacket;
/*     */   
/*     */   public CommonAsset(@Nonnull String name, @Nullable byte[] bytes) {
/*  33 */     this.name = PatternUtil.replaceBackslashWithForwardSlash(name);
/*  34 */     this.hash = hash(bytes);
/*  35 */     this.blob = new WeakReference((bytes != null) ? CompletableFuture.<byte[]>completedFuture(bytes) : null);
/*     */   }
/*     */   
/*     */   public CommonAsset(@Nonnull String name, @Nonnull String hash, @Nullable byte[] bytes) {
/*  39 */     this.name = PatternUtil.replaceBackslashWithForwardSlash(name);
/*  40 */     this.hash = hash.toLowerCase();
/*  41 */     this.blob = new WeakReference((bytes != null) ? CompletableFuture.<byte[]>completedFuture(bytes) : null);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String getName() {
/*  46 */     return this.name;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String getHash() {
/*  51 */     return this.hash;
/*     */   }
/*     */   
/*     */   public CompletableFuture<byte[]> getBlob() {
/*  55 */     CompletableFuture<byte[]> future = this.blob.get();
/*  56 */     if (future == null) {
/*  57 */       future = getBlob0();
/*  58 */       this.blob = new WeakReference<>(future);
/*     */     } 
/*  60 */     return future;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Asset toPacket() {
/*  68 */     Asset cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/*  69 */     if (cached != null) return cached;
/*     */     
/*  71 */     Asset packet = new Asset(this.hash, this.name);
/*     */     
/*  73 */     this.cachedPacket = new SoftReference<>(packet);
/*  74 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/*  79 */     if (this == o) return true; 
/*  80 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/*  82 */     CommonAsset asset = (CommonAsset)o;
/*     */     
/*  84 */     if (!this.name.equals(asset.name)) return false; 
/*  85 */     return this.hash.equals(asset.hash);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  90 */     int result = this.name.hashCode();
/*  91 */     result = 31 * result + this.hash.hashCode();
/*  92 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  98 */     return "CommonAsset{name='" + this.name + "', hash='" + this.hash + "'}";
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
/*     */   @Nonnull
/*     */   public static String hash(byte[] bytes) {
/* 112 */     return HashUtil.sha256(bytes);
/*     */   }
/*     */   
/*     */   protected abstract CompletableFuture<byte[]> getBlob0();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\common\CommonAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */