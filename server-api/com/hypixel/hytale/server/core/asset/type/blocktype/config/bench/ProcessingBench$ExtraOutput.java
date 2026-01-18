/*     */ package com.hypixel.hytale.server.core.asset.type.blocktype.config.bench;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.inventory.MaterialQuantity;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExtraOutput
/*     */ {
/*     */   public static final BuilderCodec<ExtraOutput> CODEC;
/*     */   private MaterialQuantity[] outputs;
/*     */   
/*     */   static {
/* 271 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ExtraOutput.class, ExtraOutput::new).append(new KeyedCodec("Outputs", (Codec)new ArrayCodec((Codec)MaterialQuantity.CODEC, x$0 -> new MaterialQuantity[x$0])), (o, i) -> o.outputs = i, o -> o.outputs).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("PerFuelItemsConsumed", (Codec)Codec.INTEGER), (o, i) -> o.perFuelItemsConsumed = i.intValue(), o -> Integer.valueOf(o.perFuelItemsConsumed)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(1))).add()).append(new KeyedCodec("IgnoredFuelSources", (Codec)new ArrayCodec((Codec)MaterialQuantity.CODEC, x$0 -> new MaterialQuantity[x$0])), (o, i) -> o.ignoredFuelSources = i, o -> o.ignoredFuelSources).add()).build();
/*     */   }
/*     */ 
/*     */   
/* 275 */   private int perFuelItemsConsumed = 1;
/*     */   private MaterialQuantity[] ignoredFuelSources;
/*     */   
/*     */   public MaterialQuantity[] getOutputs() {
/* 279 */     return this.outputs;
/*     */   }
/*     */   
/*     */   public int getPerFuelItemsConsumed() {
/* 283 */     return this.perFuelItemsConsumed;
/*     */   }
/*     */   
/*     */   public boolean isIgnoredFuelSource(Item id) {
/* 287 */     if (this.ignoredFuelSources == null) return false; 
/* 288 */     for (MaterialQuantity mat : this.ignoredFuelSources) {
/* 289 */       if (mat.getItemId() != null && mat.getItemId().equals(id.getBlockId())) return true;
/*     */     
/*     */     } 
/* 292 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 297 */     if (this == o) return true; 
/* 298 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 300 */     ExtraOutput that = (ExtraOutput)o;
/*     */     
/* 302 */     if (this.perFuelItemsConsumed != that.perFuelItemsConsumed) return false;
/*     */     
/* 304 */     return Arrays.equals((Object[])this.outputs, (Object[])that.outputs);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 309 */     int result = Arrays.hashCode((Object[])this.outputs);
/* 310 */     result = 31 * result + this.perFuelItemsConsumed;
/* 311 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\bench\ProcessingBench$ExtraOutput.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */