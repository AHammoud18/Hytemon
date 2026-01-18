/*     */ package com.hypixel.hytale.server.core.universe.world.worldgen.provider;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.codec.validation.validator.RangeRefValidator;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
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
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FlatWorldGenProvider
/*     */   implements IWorldGenProvider
/*     */ {
/*     */   public static final String ID = "Flat";
/*     */   public static final BuilderCodec<FlatWorldGenProvider> CODEC;
/*     */   
/*     */   static {
/*  47 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FlatWorldGenProvider.class, FlatWorldGenProvider::new).documentation("A world generation provider that generates a flat world with defined layers.")).append(new KeyedCodec("Tint", (Codec)ProtocolCodecs.COLOR), (config, o) -> config.tint = o, config -> config.tint).documentation("The tint to set for all chunks that are generated.").add()).append(new KeyedCodec("Layers", (Codec)new ArrayCodec((Codec)Layer.CODEC, x$0 -> new Layer[x$0])), (config, o) -> config.layers = o, config -> config.layers).documentation("The list of layers to add to the world.").addValidator(Validators.nonNull()).add()).build();
/*     */   }
/*  49 */   public static final Color DEFAULT_TINT = new Color((byte)91, (byte)-98, (byte)40);
/*     */   
/*  51 */   private Color tint = DEFAULT_TINT;
/*     */   private Layer[] layers;
/*     */   
/*     */   public FlatWorldGenProvider() {
/*  55 */     this.layers = new Layer[] { new Layer(0, 1, Environment.UNKNOWN.getId(), "Soil_Grass") };
/*     */   }
/*     */   
/*     */   public FlatWorldGenProvider(Color tint, Layer[] layers) {
/*  59 */     this.tint = tint;
/*  60 */     this.layers = layers;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IWorldGen getGenerator() throws WorldGenLoadException {
/*  66 */     int tintId = ColorParseUtil.colorToARGBInt(this.tint);
/*     */     
/*  68 */     for (Layer layer : this.layers) {
/*  69 */       if (layer.from >= layer.to) throw new WorldGenLoadException("Failed to load 'Flat' WorldGen config, 'To' must be greater than 'From': " + String.valueOf(layer));
/*     */       
/*  71 */       layer.from = Math.max(layer.from, 0);
/*  72 */       layer.to = Math.min(layer.to, 320);
/*     */       
/*  74 */       if (layer.environment != null) {
/*  75 */         int index = Environment.getAssetMap().getIndex(layer.environment);
/*  76 */         if (index == Integer.MIN_VALUE) throw new WorldGenLoadException("Unknown key! " + layer.environment); 
/*  77 */         layer.environmentId = index;
/*     */       } else {
/*  79 */         layer.environmentId = 0;
/*     */       } 
/*  81 */       if (layer.blockType != null) {
/*  82 */         int index = BlockType.getAssetMap().getIndex(layer.blockType);
/*  83 */         if (index == Integer.MIN_VALUE) throw new WorldGenLoadException("Unknown key! " + layer.blockType); 
/*  84 */         layer.blockId = index;
/*     */       } else {
/*  86 */         layer.blockId = 0;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  91 */     return new FlatWorldGen(this.layers, tintId);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  97 */     return "FlatWorldGenProvider{tint=" + String.valueOf(this.tint) + ", layers=" + 
/*     */       
/*  99 */       Arrays.toString((Object[])this.layers) + "}";
/*     */   }
/*     */   
/*     */   private static class FlatWorldGen
/*     */     implements IWorldGen {
/*     */     private final FlatWorldGenProvider.Layer[] layers;
/*     */     private final int tintId;
/*     */     
/*     */     public FlatWorldGen(FlatWorldGenProvider.Layer[] layers, int tintId) {
/* 108 */       this.layers = layers;
/* 109 */       this.tintId = tintId;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public WorldGenTimingsCollector getTimings() {
/* 115 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Transform[] getSpawnPoints(int seed) {
/* 121 */       return new Transform[] { new Transform(0.0D, 81.0D, 0.0D) };
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public CompletableFuture<GeneratedChunk> generate(int seed, long index, int cx, int cz, LongPredicate stillNeeded) {
/* 127 */       GeneratedBlockChunk generatedBlockChunk = new GeneratedBlockChunk(index, cx, cz);
/*     */       
/* 129 */       for (int x = 0; x < 32; x++) {
/* 130 */         for (int z = 0; z < 32; z++) {
/* 131 */           generatedBlockChunk.setTint(x, z, this.tintId);
/*     */         }
/*     */       } 
/*     */       
/* 135 */       for (FlatWorldGenProvider.Layer layer : this.layers) {
/* 136 */         for (int i = 0; i < 32; i++) {
/* 137 */           for (int z = 0; z < 32; z++) {
/* 138 */             for (int y = layer.from; y < layer.to; y++) {
/* 139 */               generatedBlockChunk.setBlock(i, y, z, layer.blockId, 0, 0);
/* 140 */               generatedBlockChunk.setEnvironment(i, y, z, layer.environmentId);
/*     */             } 
/* 142 */             generatedBlockChunk.setTint(i, z, this.tintId);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 147 */       return CompletableFuture.completedFuture(new GeneratedChunk(generatedBlockChunk, new GeneratedBlockStateChunk(), new GeneratedEntityChunk(), GeneratedChunk.makeSections()));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Layer
/*     */   {
/*     */     public static final BuilderCodec<Layer> CODEC;
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
/*     */     static {
/* 185 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Layer.class, Layer::new).documentation("A layer of blocks for a given range.")).append(new KeyedCodec("From", (Codec)Codec.INTEGER), (layer, i) -> layer.from = i.intValue(), layer -> Integer.valueOf(layer.from)).documentation("The Y coordinate (inclusive) to start placing blocks at.").add()).append(new KeyedCodec("To", (Codec)Codec.INTEGER), (layer, i) -> layer.to = i.intValue(), layer -> Integer.valueOf(layer.to)).documentation("The Y coordinate (exclusive) to stop placing blocks at.").addValidator((Validator)new RangeRefValidator("1/From", null, false)).add()).append(new KeyedCodec("BlockType", (Codec)Codec.STRING), (layer, s) -> layer.blockType = s, layer -> layer.blockType).documentation("The type of block that will be used for all blocks placed at this layer.").addValidator(BlockType.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("Environment", (Codec)Codec.STRING), (layer, s) -> layer.environment = s, layer -> layer.environment).documentation("The environment to set for every block placed.").addValidator(Environment.VALIDATOR_CACHE.getValidator()).add()).build();
/*     */     }
/* 187 */     public int from = Integer.MIN_VALUE;
/* 188 */     public int to = Integer.MAX_VALUE;
/*     */     
/*     */     public String environment;
/*     */     
/*     */     public String blockType;
/*     */     
/*     */     public int environmentId;
/*     */     
/*     */     public int blockId;
/*     */     
/*     */     public Layer(int from, int to, String environment, String blockType) {
/* 199 */       this.from = from;
/* 200 */       this.to = to;
/* 201 */       this.environment = environment;
/* 202 */       this.blockType = blockType;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 208 */       return "Layer{from=" + this.from + ", to=" + this.to + ", environment='" + this.environment + "', blockType=" + this.blockType + "}";
/*     */     }
/*     */     
/*     */     public Layer() {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldgen\provider\FlatWorldGenProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */