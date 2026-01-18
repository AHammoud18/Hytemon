/*     */ package com.hypixel.hytale.server.core.universe.world.worldgen.provider;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.core.asset.util.ColorParseUtil;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedBlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedBlockStateChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedEntityChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.WorldGenLoadException;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.WorldGenTimingsCollector;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.LongPredicate;
/*     */ import java.util.function.Supplier;
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
/*     */ public class VoidWorldGenProvider
/*     */   implements IWorldGenProvider
/*     */ {
/*     */   public static final String ID = "Void";
/*     */   public static final BuilderCodec<VoidWorldGenProvider> CODEC;
/*     */   private Color tint;
/*     */   private String environment;
/*     */   
/*     */   static {
/*  40 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(VoidWorldGenProvider.class, VoidWorldGenProvider::new).documentation("A world generation provider that does not generate any blocks.")).append(new KeyedCodec("Tint", (Codec)ProtocolCodecs.COLOR), (config, o) -> config.tint = o, config -> config.tint).documentation("The tint to set for all chunks that are generated.").add()).append(new KeyedCodec("Environment", (Codec)Codec.STRING), (config, s) -> config.environment = s, config -> config.environment).documentation("The environment to set for every column in generated chunks.").addValidator(Environment.VALIDATOR_CACHE.getValidator()).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VoidWorldGenProvider() {}
/*     */ 
/*     */   
/*     */   public VoidWorldGenProvider(Color tint, String environment) {
/*  49 */     this.tint = tint;
/*  50 */     this.environment = environment;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IWorldGen getGenerator() throws WorldGenLoadException {
/*  56 */     int tintId = (this.tint == null) ? 0 : ColorParseUtil.colorToARGBInt(this.tint);
/*     */     
/*  58 */     String key = (this.environment != null) ? this.environment : "Default";
/*  59 */     int index = Environment.getAssetMap().getIndex(key);
/*  60 */     if (index == Integer.MIN_VALUE) throw new WorldGenLoadException("Unknown key! " + key);
/*     */     
/*  62 */     return new VoidWorldGen(tintId, index);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  68 */     return "VoidWorldGenProvider{environment='" + this.environment + "'}";
/*     */   }
/*     */   
/*     */   public static class VoidWorldGen
/*     */     implements IWorldGen
/*     */   {
/*     */     private final int tintId;
/*     */     private final int environmentId;
/*     */     
/*     */     public VoidWorldGen() {
/*  78 */       this.tintId = 0;
/*  79 */       this.environmentId = 0;
/*     */     }
/*     */     
/*     */     public VoidWorldGen(@Nullable Color tint, @Nullable String environment) throws WorldGenLoadException {
/*  83 */       int tintId = (tint == null) ? 0 : ColorParseUtil.colorToARGBInt(tint);
/*  84 */       this.tintId = tintId;
/*     */       
/*  86 */       String key = (environment != null) ? environment : "Default";
/*  87 */       int index = Environment.getAssetMap().getIndex(key);
/*  88 */       if (index == Integer.MIN_VALUE) throw new WorldGenLoadException("Unknown key! " + key); 
/*  89 */       this.environmentId = index;
/*     */     }
/*     */     
/*     */     public VoidWorldGen(int tintId, int environmentId) {
/*  93 */       this.tintId = tintId;
/*  94 */       this.environmentId = environmentId;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public WorldGenTimingsCollector getTimings() {
/* 100 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Transform[] getSpawnPoints(int seed) {
/* 106 */       return new Transform[] { new Transform(0.0D, 1.0D, 0.0D) };
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public CompletableFuture<GeneratedChunk> generate(int seed, long index, int cx, int cz, LongPredicate stillNeeded) {
/* 112 */       GeneratedBlockChunk generatedBlockChunk = new GeneratedBlockChunk(index, cx, cz);
/*     */       
/* 114 */       for (int x = 0; x < 32; x++) {
/* 115 */         for (int z = 0; z < 32; z++) {
/* 116 */           if (this.environmentId != 0) generatedBlockChunk.setEnvironmentColumn(x, z, this.environmentId); 
/* 117 */           generatedBlockChunk.setTint(x, z, this.tintId);
/*     */         } 
/*     */       } 
/*     */       
/* 121 */       return CompletableFuture.completedFuture(new GeneratedChunk(generatedBlockChunk, new GeneratedBlockStateChunk(), new GeneratedEntityChunk(), GeneratedChunk.makeSections()));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldgen\provider\VoidWorldGenProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */