/*     */ package com.hypixel.hytale.builtin.adventure.farming.config.stages.spread;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.map.IWeightedElement;
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
/*     */ public class BlockTypeWeight
/*     */   implements IWeightedElement
/*     */ {
/*     */   @Nonnull
/*     */   public static BuilderCodec<BlockTypeWeight> CODEC;
/*     */   
/*     */   static {
/* 212 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BlockTypeWeight.class, BlockTypeWeight::new).append(new KeyedCodec("Weight", (Codec)Codec.DOUBLE), (blockTypeWeight, integer) -> blockTypeWeight.weight = integer.doubleValue(), blockTypeWeight -> Double.valueOf(blockTypeWeight.weight)).documentation("Defines the probability to have this entry.").addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("BlockType", (Codec)Codec.STRING), (blockTypeWeight, blockTypeKey) -> blockTypeWeight.blockTypeKey = blockTypeKey, blockTypeWeight -> blockTypeWeight.blockTypeKey).documentation("Defines the BlockType that'll be spread").addValidator(Validators.nonNull()).add()).build();
/*     */   }
/* 214 */   protected double weight = 1.0D;
/*     */   
/*     */   protected String blockTypeKey;
/*     */   
/*     */   public double getWeight() {
/* 219 */     return this.weight;
/*     */   }
/*     */   
/*     */   public String getBlockTypeKey() {
/* 223 */     return this.blockTypeKey;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 229 */     return "BlockTypeWeight{weight=" + this.weight + ", blockTypeKey=" + this.blockTypeKey + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\config\stages\spread\DirectionalGrowthBehaviour$BlockTypeWeight.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */