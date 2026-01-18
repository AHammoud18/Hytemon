/*     */ package com.hypixel.hytale.server.core.universe.world.worldgen.provider;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.validator.RangeRefValidator;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import java.util.function.Supplier;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Layer
/*     */ {
/*     */   public static final BuilderCodec<Layer> CODEC;
/*     */   
/*     */   static {
/* 185 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Layer.class, Layer::new).documentation("A layer of blocks for a given range.")).append(new KeyedCodec("From", (Codec)Codec.INTEGER), (layer, i) -> layer.from = i.intValue(), layer -> Integer.valueOf(layer.from)).documentation("The Y coordinate (inclusive) to start placing blocks at.").add()).append(new KeyedCodec("To", (Codec)Codec.INTEGER), (layer, i) -> layer.to = i.intValue(), layer -> Integer.valueOf(layer.to)).documentation("The Y coordinate (exclusive) to stop placing blocks at.").addValidator((Validator)new RangeRefValidator("1/From", null, false)).add()).append(new KeyedCodec("BlockType", (Codec)Codec.STRING), (layer, s) -> layer.blockType = s, layer -> layer.blockType).documentation("The type of block that will be used for all blocks placed at this layer.").addValidator(BlockType.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("Environment", (Codec)Codec.STRING), (layer, s) -> layer.environment = s, layer -> layer.environment).documentation("The environment to set for every block placed.").addValidator(Environment.VALIDATOR_CACHE.getValidator()).add()).build();
/*     */   }
/* 187 */   public int from = Integer.MIN_VALUE;
/* 188 */   public int to = Integer.MAX_VALUE;
/*     */   
/*     */   public String environment;
/*     */   
/*     */   public String blockType;
/*     */   
/*     */   public int environmentId;
/*     */   
/*     */   public int blockId;
/*     */   
/*     */   public Layer(int from, int to, String environment, String blockType) {
/* 199 */     this.from = from;
/* 200 */     this.to = to;
/* 201 */     this.environment = environment;
/* 202 */     this.blockType = blockType;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 208 */     return "Layer{from=" + this.from + ", to=" + this.to + ", environment='" + this.environment + "', blockType=" + this.blockType + "}";
/*     */   }
/*     */   
/*     */   public Layer() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldgen\provider\FlatWorldGenProvider$Layer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */