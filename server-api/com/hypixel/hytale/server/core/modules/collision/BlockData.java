/*     */ package com.hypixel.hytale.server.core.modules.collision;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.util.FillerBlockUtil;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockData
/*     */ {
/*     */   protected int blockId;
/*     */   @Nullable
/*     */   protected BlockType blockType;
/*     */   @Nullable
/*     */   protected String blockTypeKey;
/*     */   protected int rotation;
/*     */   protected int filler;
/*     */   protected int fluidId;
/*     */   @Nullable
/*     */   protected Fluid fluid;
/*     */   @Nullable
/*     */   protected String fluidKey;
/*     */   protected double fillHeight;
/*     */   protected int collisionMaterials;
/*     */   @Nullable
/*     */   protected BlockBoundingBoxes blockBoundingBoxes;
/*     */   
/*     */   public void assign(@Nonnull BlockData other) {
/*  36 */     this.blockId = other.blockId;
/*  37 */     this.blockType = other.blockType;
/*  38 */     this.blockTypeKey = other.blockTypeKey;
/*  39 */     this.rotation = other.rotation;
/*  40 */     this.filler = other.filler;
/*  41 */     this.fluidId = other.fluidId;
/*  42 */     this.fluid = other.fluid;
/*  43 */     this.fluidKey = other.fluidKey;
/*  44 */     this.fillHeight = other.fillHeight;
/*  45 */     this.collisionMaterials = other.collisionMaterials;
/*  46 */     this.blockBoundingBoxes = other.blockBoundingBoxes;
/*     */   }
/*     */   
/*     */   public void clear() {
/*  50 */     this.blockId = Integer.MIN_VALUE;
/*  51 */     this.blockType = null;
/*  52 */     this.blockTypeKey = null;
/*  53 */     this.rotation = 0;
/*  54 */     this.filler = 0;
/*  55 */     this.fluidId = Integer.MIN_VALUE;
/*  56 */     this.fluid = null;
/*  57 */     this.fluidKey = null;
/*  58 */     this.blockBoundingBoxes = null;
/*     */   }
/*     */   
/*     */   public boolean isFiller() {
/*  62 */     return (this.filler != 0);
/*     */   }
/*     */   
/*     */   public int originX(int x) {
/*  66 */     return x - FillerBlockUtil.unpackX(this.filler);
/*     */   }
/*     */   
/*     */   public int originY(int y) {
/*  70 */     return y - FillerBlockUtil.unpackY(this.filler);
/*     */   }
/*     */   
/*     */   public int originZ(int z) {
/*  74 */     return z - FillerBlockUtil.unpackZ(this.filler);
/*     */   }
/*     */   
/*     */   public double getFillHeight() {
/*  78 */     return this.fillHeight;
/*     */   }
/*     */   
/*     */   public boolean isTrigger() {
/*  82 */     return this.blockType.isTrigger();
/*     */   }
/*     */   
/*     */   public int getBlockDamage() {
/*  86 */     return Math.max(this.blockType.getDamageToEntities(), this.fluid.getDamageToEntities());
/*     */   }
/*     */   
/*     */   public int getSubmergeDamage() {
/*  90 */     Fluid fluid = getFluid();
/*  91 */     return (fluid == null) ? 0 : fluid.getDamageToEntities();
/*     */   }
/*     */   
/*     */   public int getCollisionMaterials() {
/*  95 */     return this.collisionMaterials;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockBoundingBoxes getBlockBoundingBoxes() {
/* 100 */     if (this.blockBoundingBoxes == null) this.blockBoundingBoxes = (BlockBoundingBoxes)BlockBoundingBoxes.getAssetMap().getAsset(this.blockType.getHitboxTypeIndex()); 
/* 101 */     return this.blockBoundingBoxes;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockType getBlockType() {
/* 106 */     return this.blockType;
/*     */   }
/*     */   
/*     */   public int getFluidId() {
/* 110 */     return this.fluidId;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Fluid getFluid() {
/* 115 */     return this.fluid;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\BlockData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */