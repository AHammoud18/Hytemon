/*     */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockset.config.BlockSet;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import java.util.Arrays;
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
/*     */ public class DurabilityLossBlockTypes
/*     */ {
/*     */   public static final BuilderCodec<DurabilityLossBlockTypes> CODEC;
/*     */   protected String[] blockTypes;
/*     */   protected String[] blockSets;
/*     */   protected double durabilityLossOnHit;
/*     */   protected int[] blockTypeIndexes;
/*     */   protected int[] blockSetIndexes;
/*     */   
/*     */   static {
/* 163 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DurabilityLossBlockTypes.class, DurabilityLossBlockTypes::new).addField(new KeyedCodec("BlockTypes", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0])), (item, s) -> item.blockTypes = s, item -> item.blockTypes)).addField(new KeyedCodec("BlockSets", (Codec)Codec.STRING_ARRAY), (item, s) -> item.blockSets = s, item -> item.blockSets)).addField(new KeyedCodec("DurabilityLossOnHit", (Codec)Codec.DOUBLE), (item, s) -> item.durabilityLossOnHit = s.doubleValue(), item -> Double.valueOf(item.durabilityLossOnHit))).afterDecode(item -> { if (item.blockSets != null) { item.blockSetIndexes = new int[item.blockSets.length]; for (int i = 0; i < item.blockSets.length; i++) { String blockSet = item.blockSets[i]; int index = BlockSet.getAssetMap().getIndex(blockSet); if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + blockSet);  item.blockSetIndexes[i] = index; }  }  })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DurabilityLossBlockTypes() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurabilityLossBlockTypes(String[] blockTypes, String[] blockSets, double durabilityLossOnHit) {
/* 176 */     this.blockTypes = blockTypes;
/* 177 */     this.blockSets = blockSets;
/* 178 */     this.durabilityLossOnHit = durabilityLossOnHit;
/*     */   }
/*     */   
/*     */   public String[] getBlockTypes() {
/* 182 */     return this.blockTypes;
/*     */   }
/*     */   
/*     */   public String[] getBlockSets() {
/* 186 */     return this.blockSets;
/*     */   }
/*     */   
/*     */   public double getDurabilityLossOnHit() {
/* 190 */     return this.durabilityLossOnHit;
/*     */   }
/*     */   
/*     */   public int[] getBlockTypeIndexes() {
/* 194 */     if (this.blockTypes != null && this.blockTypeIndexes == null) {
/* 195 */       int[] blockTypeIndexes = new int[this.blockTypes.length];
/* 196 */       for (int i = 0; i < this.blockTypes.length; i++) {
/* 197 */         String key = this.blockTypes[i];
/* 198 */         int index = BlockType.getAssetMap().getIndex(key);
/* 199 */         if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/* 200 */         blockTypeIndexes[i] = index;
/*     */       } 
/* 202 */       this.blockTypeIndexes = blockTypeIndexes;
/*     */     } 
/*     */     
/* 205 */     return this.blockTypeIndexes;
/*     */   }
/*     */   
/*     */   public int[] getBlockSetIndexes() {
/* 209 */     return this.blockSetIndexes;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 216 */     return "DurabilityLossBlockTypes{blockTypes=" + Arrays.toString((Object[])this.blockTypes) + ", blockSets=" + 
/* 217 */       Arrays.toString((Object[])this.blockSets) + ", durabilityLossOnHit=" + this.durabilityLossOnHit + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\ItemTool$DurabilityLossBlockTypes.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */