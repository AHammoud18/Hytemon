/*     */ package com.hypixel.hytale.builtin.hytalegenerator.material;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MaterialCache
/*     */ {
/*  20 */   private final Map<Integer, SolidMaterial> hashToSolidMap = new HashMap<>();
/*  21 */   private final Map<Integer, FluidMaterial> hashToFluidMap = new HashMap<>();
/*  22 */   private final Map<Integer, Material> hashToMaterialMap = new HashMap<>();
/*     */   
/*     */   public final SolidMaterial EMPTY_AIR;
/*     */   
/*     */   public final SolidMaterial ROCK_STONE;
/*     */   
/*     */   public final SolidMaterial SOIL_GRASS;
/*     */   
/*     */   public final SolidMaterial SOIL_DIRT;
/*     */   
/*     */   public final SolidMaterial SOIL_MUD;
/*     */   
/*     */   public final SolidMaterial SOIL_NEEDLES;
/*     */   
/*     */   public final SolidMaterial SOIL_GRAVEL;
/*     */   public final SolidMaterial ROCK_QUARTZITE;
/*     */   public final SolidMaterial ROCK_MARBLE;
/*     */   public final SolidMaterial ROCK_SHALE;
/*     */   public final SolidMaterial FLUID_WATER;
/*     */   public final SolidMaterial BEDROCK;
/*     */   public final FluidMaterial UNKNOWN_FLUID;
/*     */   public final FluidMaterial EMPTY_FLUID;
/*     */   public final Material EMPTY;
/*     */   
/*     */   public MaterialCache() {
/*  47 */     this.EMPTY_AIR = getSolidMaterial("Empty_Air");
/*  48 */     this.ROCK_STONE = getSolidMaterial("Rock_Stone");
/*  49 */     this.SOIL_GRASS = getSolidMaterial("Soil_Grass");
/*  50 */     this.SOIL_DIRT = getSolidMaterial("Soil_Dirt");
/*  51 */     this.SOIL_MUD = getSolidMaterial("Soil_Mud");
/*  52 */     this.SOIL_NEEDLES = getSolidMaterial("Soil_Needles");
/*  53 */     this.SOIL_GRAVEL = getSolidMaterial("Soil_Gravel");
/*  54 */     this.ROCK_QUARTZITE = getSolidMaterial("Rock_Quartzite");
/*  55 */     this.ROCK_MARBLE = getSolidMaterial("Rock_Marble");
/*  56 */     this.ROCK_SHALE = getSolidMaterial("Rock_Shale");
/*  57 */     this.FLUID_WATER = getSolidMaterial("Fluid_Water");
/*  58 */     this.BEDROCK = getSolidMaterial("Rock_Volcanic");
/*     */ 
/*     */     
/*  61 */     this.UNKNOWN_FLUID = getFluidMaterial(Fluid.UNKNOWN.getId());
/*  62 */     this.EMPTY_FLUID = getFluidMaterial(Fluid.EMPTY.getId());
/*     */ 
/*     */     
/*  65 */     this.EMPTY = getMaterial(this.EMPTY_AIR, this.EMPTY_FLUID);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Material getMaterial(@Nonnull SolidMaterial solidMaterial, @Nonnull FluidMaterial fluidMaterial) {
/*  72 */     int hash = Material.hashCode(solidMaterial, fluidMaterial);
/*     */     
/*  74 */     Material material = this.hashToMaterialMap.get(Integer.valueOf(hash));
/*  75 */     if (material == null) {
/*     */       
/*  77 */       material = new Material(solidMaterial, fluidMaterial);
/*  78 */       this.hashToMaterialMap.put(Integer.valueOf(hash), material);
/*  79 */       return material;
/*     */     } 
/*     */     
/*  82 */     return material;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidMaterial getFluidMaterial(@Nonnull String fluidString) {
/*  88 */     int fluidId = 0;
/*     */     
/*  90 */     Fluid key = (Fluid)Fluid.getAssetMap().getAsset(fluidString);
/*  91 */     if (key != null) {
/*  92 */       fluidId = Fluid.getAssetMap().getIndex(fluidString);
/*     */     } else {
/*  94 */       LoggerUtil.getLogger().warning("Attempted to register an invalid Fluid " + fluidString + ", using Unknown instead.");
/*  95 */       return this.UNKNOWN_FLUID;
/*     */     } 
/*     */     
/*  98 */     byte level = (fluidId == 0) ? 0 : (byte)key.getMaxFluidLevel();
/*  99 */     return getOrRegisterFluid(fluidId, level);
/*     */   }
/*     */   
/*     */   public FluidMaterial getFluidMaterial(int fluidId, byte level) {
/* 103 */     Fluid key = (Fluid)Fluid.getAssetMap().getAsset(fluidId);
/* 104 */     if (key == null) {
/* 105 */       LoggerUtil.getLogger().warning("Attempted to register an invalid Fluid " + fluidId + ", using Unknown instead.");
/* 106 */       return this.UNKNOWN_FLUID;
/*     */     } 
/*     */     
/* 109 */     return getOrRegisterFluid(fluidId, level);
/*     */   }
/*     */   
/*     */   private FluidMaterial getOrRegisterFluid(int fluidId, byte level) {
/* 113 */     int hash = FluidMaterial.contentHash(fluidId, level);
/*     */     
/* 115 */     FluidMaterial fluidMaterial = this.hashToFluidMap.get(Integer.valueOf(hash));
/* 116 */     if (fluidMaterial != null) {
/* 117 */       return fluidMaterial;
/*     */     }
/*     */     
/* 120 */     fluidMaterial = new FluidMaterial(this, fluidId, level);
/* 121 */     this.hashToFluidMap.put(Integer.valueOf(hash), fluidMaterial);
/* 122 */     return fluidMaterial;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SolidMaterial getSolidMaterial(@Nonnull String solidString) {
/* 128 */     int blockId = 0;
/*     */     
/* 130 */     BlockType key = BlockType.fromString(solidString);
/* 131 */     if (key != null) {
/* 132 */       blockId = BlockType.getAssetMap().getIndex(key.getId());
/*     */     }
/* 134 */     if (BlockType.getAssetMap().getAsset(blockId) == null) {
/* 135 */       System.out.println("Attempted to register an invalid block ID " + blockId + ": using Empty_Air instead.");
/* 136 */       return this.EMPTY_AIR;
/*     */     } 
/*     */     
/* 139 */     int hash = SolidMaterial.contentHash(blockId, 0, 0, 0, null);
/*     */     
/* 141 */     SolidMaterial solidMaterial = this.hashToSolidMap.get(Integer.valueOf(hash));
/* 142 */     if (solidMaterial != null) {
/* 143 */       return solidMaterial;
/*     */     }
/*     */     
/* 146 */     solidMaterial = new SolidMaterial(this, blockId, 0, 0, 0, null);
/* 147 */     this.hashToSolidMap.put(Integer.valueOf(blockId), solidMaterial);
/* 148 */     return solidMaterial;
/*     */   }
/*     */   
/*     */   public SolidMaterial getSolidMaterialRotatedY(@Nonnull SolidMaterial solidMaterial, Rotation rotation) {
/* 152 */     PrefabRotation prefabRotation = PrefabRotation.fromRotation(rotation);
/*     */     
/* 154 */     int rotatedRotation = prefabRotation.getRotation(solidMaterial.rotation);
/* 155 */     int rotatedFiller = prefabRotation.getFiller(solidMaterial.filler);
/*     */     
/* 157 */     int hash = SolidMaterial.contentHash(solidMaterial.blockId, solidMaterial.support, rotatedRotation, rotatedFiller, solidMaterial.holder);
/*     */     
/* 159 */     SolidMaterial rotatedSolidMaterial = this.hashToSolidMap.get(Integer.valueOf(hash));
/* 160 */     if (rotatedSolidMaterial != null) {
/* 161 */       return rotatedSolidMaterial;
/*     */     }
/*     */     
/* 164 */     rotatedSolidMaterial = new SolidMaterial(this, solidMaterial.blockId, solidMaterial.support, rotatedRotation, rotatedFiller, solidMaterial.holder);
/* 165 */     this.hashToSolidMap.put(Integer.valueOf(hash), rotatedSolidMaterial);
/* 166 */     return rotatedSolidMaterial;
/*     */   }
/*     */   
/*     */   public SolidMaterial getSolidMaterial(int blockId, int support, int rotation, int filler, @Nullable Holder<ChunkStore> holder) {
/* 170 */     if (BlockType.getAssetMap().getAsset(blockId) == null) {
/* 171 */       System.out.println("Attempted to register an invalid block ID " + blockId + ": using Empty_Air instead.");
/* 172 */       return this.EMPTY_AIR;
/*     */     } 
/*     */     
/* 175 */     int hash = SolidMaterial.contentHash(blockId, support, rotation, filler, holder);
/*     */     
/* 177 */     SolidMaterial solidMaterial = this.hashToSolidMap.get(Integer.valueOf(hash));
/* 178 */     if (solidMaterial != null) {
/* 179 */       return solidMaterial;
/*     */     }
/*     */     
/* 182 */     solidMaterial = new SolidMaterial(this, blockId, support, rotation, filler, holder);
/* 183 */     this.hashToSolidMap.put(Integer.valueOf(hash), solidMaterial);
/* 184 */     return solidMaterial;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\material\MaterialCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */