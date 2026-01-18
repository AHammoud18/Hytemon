/*     */ package com.hypixel.hytale.server.core.asset.common;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*     */ import com.hypixel.hytale.common.util.CompletableFutureUtil;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockyAnimationCache
/*     */ {
/*  27 */   public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*  29 */   private static final Map<String, BlockyAnimation> animations = new ConcurrentHashMap<>();
/*     */   
/*     */   @Nonnull
/*     */   public static CompletableFuture<BlockyAnimation> get(String name) {
/*  33 */     BlockyAnimation animationData = animations.get(name);
/*  34 */     if (animationData != null) return CompletableFuture.completedFuture(animationData); 
/*  35 */     CommonAsset asset = CommonAssetRegistry.getByName(name);
/*  36 */     if (asset == null) return CompletableFuture.completedFuture(null); 
/*  37 */     return get0(asset);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CompletableFuture<BlockyAnimation> get(@Nonnull CommonAsset asset) {
/*  42 */     BlockyAnimation animationData = animations.get(asset.getName());
/*  43 */     if (animationData != null) return CompletableFuture.completedFuture(animationData); 
/*  44 */     return get0(asset);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static BlockyAnimation getNow(String name) {
/*  49 */     BlockyAnimation animationData = animations.get(name);
/*  50 */     if (animationData != null) return animationData; 
/*  51 */     CommonAsset asset = CommonAssetRegistry.getByName(name);
/*  52 */     if (asset == null) return null; 
/*  53 */     return get0(asset).join();
/*     */   }
/*     */   
/*     */   public static BlockyAnimation getNow(@Nonnull CommonAsset asset) {
/*  57 */     BlockyAnimation animationData = animations.get(asset.getName());
/*  58 */     if (animationData != null) return animationData; 
/*  59 */     return get0(asset).join();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static CompletableFuture<BlockyAnimation> get0(@Nonnull CommonAsset asset) {
/*  64 */     String name = asset.getName();
/*  65 */     return CompletableFutureUtil._catch(asset.getBlob().thenApply(bytes -> {
/*     */             String str = new String(bytes, StandardCharsets.UTF_8);
/*     */             RawJsonReader reader = RawJsonReader.fromJsonString(str);
/*     */             try {
/*     */               ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/*     */               BlockyAnimation newAnimationData = (BlockyAnimation)BlockyAnimation.CODEC.decodeJson(reader, extraInfo);
/*     */               extraInfo.getValidationResults().logOrThrowValidatorExceptions(LOGGER);
/*     */               animations.put(name, newAnimationData);
/*     */               return newAnimationData;
/*  74 */             } catch (IOException e) {
/*     */               throw SneakyThrow.sneakyThrow(e);
/*     */             } 
/*     */           }));
/*     */   }
/*     */   
/*     */   public static void invalidate(String name) {
/*  81 */     animations.remove(name);
/*     */   }
/*     */   
/*     */   public static class BlockyAnimation
/*     */   {
/*     */     public static final BuilderCodec<BlockyAnimation> CODEC;
/*     */     public static final double FRAMES_PER_SECOND = 60.0D;
/*     */     private int duration;
/*     */     
/*     */     static {
/*  91 */       CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(BlockyAnimation.class, BlockyAnimation::new).addField(new KeyedCodec("duration", (Codec)Codec.INTEGER, true, true), (blockyAnimation, i) -> blockyAnimation.duration = i.intValue(), blockyAnimation -> Integer.valueOf(blockyAnimation.duration))).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getDurationFrames() {
/* 101 */       return this.duration;
/*     */     }
/*     */     
/*     */     public double getDurationMillis() {
/* 105 */       return this.duration * 1000.0D / 60.0D;
/*     */     }
/*     */     
/*     */     public double getDurationSeconds() {
/* 109 */       return this.duration / 60.0D;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 115 */       return "BlockyAnimation{duration=" + this.duration + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\common\BlockyAnimationCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */