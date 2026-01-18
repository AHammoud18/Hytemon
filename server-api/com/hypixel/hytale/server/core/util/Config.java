/*     */ package com.hypixel.hytale.server.core.util;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import com.hypixel.hytale.sneakythrow.supplier.ThrowableSupplier;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class Config<T>
/*     */ {
/*     */   @Nonnull
/*     */   private final Path path;
/*     */   private final String name;
/*     */   private final BuilderCodec<T> codec;
/*     */   @Nullable
/*     */   private T config;
/*     */   @Nullable
/*     */   private CompletableFuture<T> loadingConfig;
/*     */   
/*     */   public Config(@Nonnull Path path, String name, BuilderCodec<T> codec) {
/*  38 */     this.path = path.resolve(name + ".json");
/*  39 */     this.name = name;
/*  40 */     this.codec = codec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   @Deprecated(forRemoval = true)
/*     */   public static <T> Config<T> preloadedConfig(@Nonnull Path path, String name, BuilderCodec<T> codec, T config) {
/*  49 */     Config<T> c = new Config<>(path, name, codec);
/*  50 */     c.config = config;
/*  51 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CompletableFuture<T> load() {
/*  62 */     if (this.loadingConfig != null) {
/*  63 */       return this.loadingConfig;
/*     */     }
/*  65 */     if (!Files.exists(this.path, new java.nio.file.LinkOption[0])) {
/*  66 */       this.config = (T)this.codec.getDefaultValue();
/*  67 */       return CompletableFuture.completedFuture(this.config);
/*     */     } 
/*  69 */     return this.loadingConfig = CompletableFuture.supplyAsync(SneakyThrow.sneakySupplier(() -> {
/*     */             this.config = (T)RawJsonReader.readSync(this.path, (Codec)this.codec, HytaleLogger.getLogger());
/*     */             this.loadingConfig = null;
/*     */             return (ThrowableSupplier)this.config;
/*     */           }));
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
/*     */ 
/*     */   
/*     */   public T get() {
/*  89 */     if (this.config == null && this.loadingConfig == null) throw new IllegalStateException("Config is not loaded"); 
/*  90 */     if (this.loadingConfig != null) {
/*  91 */       return this.loadingConfig.join();
/*     */     }
/*  93 */     return this.config;
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
/*     */   @Nonnull
/*     */   public CompletableFuture<Void> save() {
/* 106 */     if (this.config == null && this.loadingConfig == null) throw new IllegalStateException("Config is not loaded"); 
/* 107 */     if (this.loadingConfig != null)
/*     */     {
/*     */       
/* 110 */       return CompletableFuture.completedFuture(null);
/*     */     }
/*     */     
/* 113 */     return BsonUtil.writeDocument(this.path, this.codec.encode(this.config, new ExtraInfo()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\Config.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */