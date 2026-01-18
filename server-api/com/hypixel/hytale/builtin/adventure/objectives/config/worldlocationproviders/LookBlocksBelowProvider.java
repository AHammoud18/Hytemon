/*     */ package com.hypixel.hytale.builtin.adventure.objectives.config.worldlocationproviders;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LookBlocksBelowProvider
/*     */   extends WorldLocationProvider
/*     */ {
/*     */   public static final BuilderCodec<LookBlocksBelowProvider> CODEC;
/*     */   protected String[] blockTags;
/*     */   
/*     */   static {
/*  53 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(LookBlocksBelowProvider.class, LookBlocksBelowProvider::new, BASE_CODEC).append(new KeyedCodec("BlockTags", (Codec)Codec.STRING_ARRAY), (lookBlocksBelowCondition, strings) -> lookBlocksBelowCondition.blockTags = strings, lookBlocksBelowCondition -> lookBlocksBelowCondition.blockTags).addValidator(Validators.nonEmptyArray()).addValidator(Validators.uniqueInArray()).add()).append(new KeyedCodec("Count", (Codec)Codec.INTEGER), (lookBlocksBelowCondition, integer) -> lookBlocksBelowCondition.count = integer.intValue(), lookBlocksBelowCondition -> Integer.valueOf(lookBlocksBelowCondition.count)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).append(new KeyedCodec("MinRange", (Codec)Codec.INTEGER), (lookBlocksBelowCondition, integer) -> lookBlocksBelowCondition.minRange = integer.intValue(), lookBlocksBelowCondition -> Integer.valueOf(lookBlocksBelowCondition.minRange)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(0))).add()).append(new KeyedCodec("MaxRange", (Codec)Codec.INTEGER), (lookBlocksBelowCondition, integer) -> lookBlocksBelowCondition.maxRange = integer.intValue(), lookBlocksBelowCondition -> Integer.valueOf(lookBlocksBelowCondition.maxRange)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(1))).add()).afterDecode(lookBlocksBelowCondition -> { if (lookBlocksBelowCondition.blockTags == null) return;  lookBlocksBelowCondition.blockTagsIndexes = new int[lookBlocksBelowCondition.blockTags.length]; for (int i = 0; i < lookBlocksBelowCondition.blockTags.length; i++) { String blockTag = lookBlocksBelowCondition.blockTags[i]; lookBlocksBelowCondition.blockTagsIndexes[i] = AssetRegistry.getOrCreateTagIndex(blockTag); }  if (lookBlocksBelowCondition.minRange > lookBlocksBelowCondition.maxRange) throw new IllegalArgumentException("LookBlocksBelowCondition.MinRange (" + lookBlocksBelowCondition.minRange + ") needs to be greater than LookBlocksBelowCondition.MaxRange (" + lookBlocksBelowCondition.maxRange + ")");  })).build();
/*     */   }
/*     */   
/*  56 */   protected int count = 1;
/*  57 */   protected int minRange = 0;
/*  58 */   protected int maxRange = 10;
/*     */   
/*     */   private int[] blockTagsIndexes;
/*     */   
/*     */   public LookBlocksBelowProvider(@Nonnull String[] blockTags, int count, int minRange, int maxRange) {
/*  63 */     this.blockTags = blockTags;
/*  64 */     this.count = count;
/*  65 */     this.minRange = minRange;
/*  66 */     this.maxRange = maxRange;
/*     */     
/*  68 */     this.blockTagsIndexes = new int[blockTags.length];
/*  69 */     for (int i = 0; i < blockTags.length; i++) {
/*  70 */       String blockTag = blockTags[i];
/*  71 */       this.blockTagsIndexes[i] = AssetRegistry.getOrCreateTagIndex(blockTag);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Vector3i runCondition(@Nonnull World world, @Nonnull Vector3i position) {
/*  81 */     Vector3i newPosition = position.clone();
/*  82 */     WorldChunk worldChunk = world.getChunk(ChunkUtil.indexChunkFromBlock(newPosition.x, newPosition.z));
/*     */     
/*  84 */     int baseY = newPosition.y;
/*  85 */     int x = newPosition.x;
/*  86 */     int y = newPosition.y;
/*  87 */     int z = newPosition.z;
/*     */     
/*  89 */     int currentCount = 0;
/*  90 */     while (y >= this.minRange && baseY - y <= this.maxRange) {
/*  91 */       String blockStateKey = worldChunk.getBlockType(x, y, z).getId();
/*     */       
/*  93 */       boolean found = false;
/*  94 */       for (int i = 0; i < this.blockTagsIndexes.length; ) {
/*  95 */         int blockTagId = this.blockTagsIndexes[i];
/*  96 */         if (!BlockType.getAssetMap().getKeysForTag(blockTagId).contains(blockStateKey)) {
/*     */           i++; continue;
/*  98 */         }  found = true;
/*  99 */         currentCount++;
/*     */       } 
/*     */ 
/*     */       
/* 103 */       if (currentCount == this.count)
/*     */         break; 
/* 105 */       y--;
/* 106 */       if (!found) currentCount = 0;
/*     */     
/*     */     } 
/* 109 */     return (currentCount == this.count) ? new Vector3i(x, y, z) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 114 */     if (this == o) return true; 
/* 115 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 117 */     LookBlocksBelowProvider that = (LookBlocksBelowProvider)o;
/*     */     
/* 119 */     if (this.count != that.count) return false; 
/* 120 */     if (this.minRange != that.minRange) return false; 
/* 121 */     if (this.maxRange != that.maxRange) return false;
/*     */     
/* 123 */     return Arrays.equals((Object[])this.blockTags, (Object[])that.blockTags);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 128 */     int result = Arrays.hashCode((Object[])this.blockTags);
/* 129 */     result = 31 * result + this.count;
/* 130 */     result = 31 * result + this.minRange;
/* 131 */     result = 31 * result + this.maxRange;
/* 132 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 139 */     return "LookBlocksBelowProvider{blockTags=" + Arrays.toString((Object[])this.blockTags) + ", count=" + this.count + ", minRange=" + this.minRange + ", maxRange=" + this.maxRange + "} " + super
/*     */ 
/*     */ 
/*     */       
/* 143 */       .toString();
/*     */   }
/*     */   
/*     */   protected LookBlocksBelowProvider() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\worldlocationproviders\LookBlocksBelowProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */