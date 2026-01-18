/*     */ package com.hypixel.hytale.server.core.asset.common;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.CompletableFutureUtil;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OggVorbisInfoCache
/*     */ {
/*  20 */   public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*  22 */   private static final Map<String, OggVorbisInfo> vorbisFiles = new ConcurrentHashMap<>();
/*     */   
/*     */   @Nonnull
/*     */   public static CompletableFuture<OggVorbisInfo> get(String name) {
/*  26 */     OggVorbisInfo info = vorbisFiles.get(name);
/*  27 */     if (info != null) return CompletableFuture.completedFuture(info); 
/*  28 */     CommonAsset asset = CommonAssetRegistry.getByName(name);
/*  29 */     if (asset == null) return CompletableFuture.completedFuture(null); 
/*  30 */     return get0(asset);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CompletableFuture<OggVorbisInfo> get(@Nonnull CommonAsset asset) {
/*  35 */     OggVorbisInfo info = vorbisFiles.get(asset.getName());
/*  36 */     if (info != null) return CompletableFuture.completedFuture(info); 
/*  37 */     return get0(asset);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static OggVorbisInfo getNow(String name) {
/*  42 */     OggVorbisInfo info = vorbisFiles.get(name);
/*  43 */     if (info != null) return info; 
/*  44 */     CommonAsset asset = CommonAssetRegistry.getByName(name);
/*  45 */     if (asset == null) return null; 
/*  46 */     return get0(asset).join();
/*     */   }
/*     */   
/*     */   public static OggVorbisInfo getNow(@Nonnull CommonAsset asset) {
/*  50 */     OggVorbisInfo info = vorbisFiles.get(asset.getName());
/*  51 */     if (info != null) return info; 
/*  52 */     return get0(asset).join();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static CompletableFuture<OggVorbisInfo> get0(@Nonnull CommonAsset asset) {
/*  57 */     String name = asset.getName();
/*  58 */     return CompletableFutureUtil._catch(asset.getBlob().thenApply(bytes -> {
/*     */             ByteBuf b = Unpooled.wrappedBuffer(bytes);
/*     */             try {
/*     */               int len = b.readableBytes();
/*     */               int id = -1;
/*     */               int i = 0;
/*     */               int end = len - 7;
/*     */               while (i <= end) {
/*     */                 i = b.indexOf(i, len - 7, (byte)1);
/*     */                 if (i == -1) {
/*     */                   break;
/*     */                 }
/*     */                 if (b.getByte(i + 1) == 118 && b.getByte(i + 2) == 111 && b.getByte(i + 3) == 114 && b.getByte(i + 4) == 98 && b.getByte(i + 5) == 105 && b.getByte(i + 6) == 115) {
/*     */                   id = i;
/*     */                   break;
/*     */                 } 
/*     */                 i++;
/*     */               } 
/*     */               if (id < 0 || id + 16 > len) {
/*     */                 throw new IllegalArgumentException("Vorbis id header not found");
/*     */               }
/*     */               int channels = b.getUnsignedByte(id + 11);
/*     */               int sampleRate = b.getIntLE(id + 12);
/*     */               double duration = -1.0D;
/*     */               if (sampleRate > 0) {
/*     */                 for (int j = Math.max(0, len - 14); j >= 0; j--) {
/*     */                   j = b.indexOf(j, 0, (byte)79);
/*     */                   if (j == -1) {
/*     */                     break;
/*     */                   }
/*     */                   if (b.getByte(j + 1) == 103 && b.getByte(j + 2) == 103 && b.getByte(j + 3) == 83) {
/*     */                     int headerType = b.getUnsignedByte(j + 5);
/*     */                     if ((headerType & 0x4) != 0) {
/*     */                       long granule = b.getLongLE(j + 6);
/*     */                       if (granule >= 0L) {
/*     */                         duration = granule / sampleRate;
/*     */                       }
/*     */                       break;
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */               OggVorbisInfo info = new OggVorbisInfo(channels, sampleRate, duration);
/*     */               vorbisFiles.put(name, info);
/*     */               return info;
/*     */             } finally {
/*     */               b.release();
/*     */             } 
/*     */           }));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void invalidate(String name) {
/* 111 */     vorbisFiles.remove(name);
/*     */   }
/*     */   
/*     */   public static class OggVorbisInfo { public final int channels;
/*     */     public final int sampleRate;
/*     */     public final double duration;
/*     */     
/*     */     OggVorbisInfo(int channels, int sampleRate, double duration) {
/* 119 */       this.channels = channels;
/* 120 */       this.sampleRate = sampleRate;
/* 121 */       this.duration = duration;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 127 */       return "OggVorbisInfo{channels=" + this.channels + ", sampleRate=" + this.sampleRate + ", duration=" + this.duration + "}";
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\common\OggVorbisInfoCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */