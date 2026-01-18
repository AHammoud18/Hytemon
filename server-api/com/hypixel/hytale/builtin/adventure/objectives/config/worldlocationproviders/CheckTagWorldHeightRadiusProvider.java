/*     */ package com.hypixel.hytale.builtin.adventure.objectives.config.worldlocationproviders;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.iterator.SpiralIterator;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
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
/*     */ public class CheckTagWorldHeightRadiusProvider
/*     */   extends WorldLocationProvider
/*     */ {
/*     */   public static final BuilderCodec<CheckTagWorldHeightRadiusProvider> CODEC;
/*     */   protected String[] blockTags;
/*     */   
/*     */   static {
/*  40 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CheckTagWorldHeightRadiusProvider.class, CheckTagWorldHeightRadiusProvider::new, BASE_CODEC).append(new KeyedCodec("BlockTags", (Codec)Codec.STRING_ARRAY), (lookBlocksBelowCondition, strings) -> lookBlocksBelowCondition.blockTags = strings, lookBlocksBelowCondition -> lookBlocksBelowCondition.blockTags).addValidator(Validators.nonEmptyArray()).addValidator(Validators.uniqueInArray()).add()).append(new KeyedCodec("Radius", (Codec)Codec.INTEGER), (checkTagWorldHeightRadiusCondition, integer) -> checkTagWorldHeightRadiusCondition.radius = integer.intValue(), checkTagWorldHeightRadiusCondition -> Integer.valueOf(checkTagWorldHeightRadiusCondition.radius)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).afterDecode(checkTagWorldHeightRadiusCondition -> { if (checkTagWorldHeightRadiusCondition.blockTags == null) return;  checkTagWorldHeightRadiusCondition.blockTagsIndexes = new int[checkTagWorldHeightRadiusCondition.blockTags.length]; for (int i = 0; i < checkTagWorldHeightRadiusCondition.blockTags.length; i++) { String blockTag = checkTagWorldHeightRadiusCondition.blockTags[i]; checkTagWorldHeightRadiusCondition.blockTagsIndexes[i] = AssetRegistry.getOrCreateTagIndex(blockTag); }  })).build();
/*     */   }
/*     */   
/*  43 */   protected int radius = 5;
/*     */   
/*     */   private int[] blockTagsIndexes;
/*     */   
/*     */   public CheckTagWorldHeightRadiusProvider(@Nonnull String[] blockTags, int radius) {
/*  48 */     this.blockTags = blockTags;
/*  49 */     this.radius = radius;
/*     */     
/*  51 */     this.blockTagsIndexes = new int[blockTags.length];
/*  52 */     for (int i = 0; i < blockTags.length; i++) {
/*  53 */       String blockTag = blockTags[i];
/*  54 */       this.blockTagsIndexes[i] = AssetRegistry.getOrCreateTagIndex(blockTag);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Vector3i runCondition(@Nonnull World world, @Nonnull Vector3i position) {
/*  64 */     SpiralIterator iterator = new SpiralIterator(position.x, position.z, this.radius);
/*  65 */     while (iterator.hasNext()) {
/*  66 */       long pos = iterator.next();
/*     */       
/*  68 */       int blockX = MathUtil.unpackLeft(pos);
/*  69 */       int blockZ = MathUtil.unpackRight(pos);
/*     */       
/*  71 */       WorldChunk chunk = world.getNonTickingChunk(ChunkUtil.indexChunkFromBlock(blockX, blockZ));
/*     */       
/*  73 */       int blockY = chunk.getHeight(blockX, blockZ);
/*     */       
/*  75 */       int blockId = chunk.getBlock(blockX, blockY, blockZ);
/*  76 */       for (int i = 0; i < this.blockTagsIndexes.length; i++) {
/*  77 */         if (BlockType.getAssetMap().getIndexesForTag(this.blockTagsIndexes[i]).contains(blockId)) {
/*  78 */           return new Vector3i(blockX, blockY + 1, blockZ);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/*  88 */     if (this == o) return true; 
/*  89 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/*  91 */     CheckTagWorldHeightRadiusProvider that = (CheckTagWorldHeightRadiusProvider)o;
/*     */     
/*  93 */     if (this.radius != that.radius) return false;
/*     */     
/*  95 */     return Arrays.equals((Object[])this.blockTags, (Object[])that.blockTags);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 100 */     int result = Arrays.hashCode((Object[])this.blockTags);
/* 101 */     result = 31 * result + this.radius;
/* 102 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 109 */     return "CheckTagWorldHeightRadiusProvider{blockTags=" + Arrays.toString((Object[])this.blockTags) + ", radius=" + this.radius + "} " + super
/*     */       
/* 111 */       .toString();
/*     */   }
/*     */   
/*     */   protected CheckTagWorldHeightRadiusProvider() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\worldlocationproviders\CheckTagWorldHeightRadiusProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */