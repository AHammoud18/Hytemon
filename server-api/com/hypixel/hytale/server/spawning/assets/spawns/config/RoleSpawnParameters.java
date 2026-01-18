/*     */ package com.hypixel.hytale.server.spawning.assets.spawns.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.map.IWeightedElement;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockset.config.BlockSet;
/*     */ import com.hypixel.hytale.server.flock.config.FlockAsset;
/*     */ import com.hypixel.hytale.server.npc.validators.NPCRoleValidator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RoleSpawnParameters
/*     */   implements IWeightedElement
/*     */ {
/*     */   public static final BuilderCodec<RoleSpawnParameters> CODEC;
/*     */   
/*     */   static {
/*  75 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RoleSpawnParameters.class, RoleSpawnParameters::new).documentation("A set of parameters that configure spawning for a single NPC type.")).append(new KeyedCodec("Id", (Codec)Codec.STRING), (parameters, s) -> parameters.id = s, parameters -> parameters.id).documentation("The Role ID of the NPC to spawn.").addValidator(Validators.nonNull()).addValidator((Validator)NPCRoleValidator.INSTANCE).add()).append(new KeyedCodec("Weight", (Codec)Codec.DOUBLE, true), (parameter, d) -> parameter.weight = d.doubleValue(), parameters -> Double.valueOf(parameters.weight)).documentation("The relative weight of this NPC (chance of being spawned is this value relative to the sum of all weights).").addValidator(Validators.nonNull()).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("SpawnBlockSet", (Codec)Codec.STRING), (parameter, s) -> parameter.spawnBlockSet = s, parameters -> parameters.spawnBlockSet).addValidator(BlockSet.VALIDATOR_CACHE.getValidator()).documentation("An optional BlockSet reference that defines which blocks this NPC can spawn on.").add()).append(new KeyedCodec("SpawnFluidTag", (Codec)Codec.STRING), (parameter, s) -> parameter.spawnFluidTag = s, parameters -> parameters.spawnFluidTag).documentation("An optional tag reference that defines which fluids this NPC can spawn on.").add()).append(new KeyedCodec("Flock", FlockAsset.CHILD_ASSET_CODEC), (spawn, o) -> spawn.flockDefinitionId = o, spawn -> spawn.flockDefinitionId).documentation("The optional flock definition to spawn around this NPC.").addValidator(FlockAsset.VALIDATOR_CACHE.getValidator()).add()).afterDecode(parameters -> { if (parameters.spawnBlockSet != null) { int index = BlockSet.getAssetMap().getIndex(parameters.spawnBlockSet); if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + parameters.spawnBlockSet);  parameters.spawnBlockSetIndex = index; }  if (parameters.spawnFluidTag != null) parameters.spawnFluidTagIndex = AssetRegistry.getOrCreateTagIndex(parameters.spawnFluidTag);  })).build();
/*     */   }
/*  77 */   public static final RoleSpawnParameters[] EMPTY_ARRAY = new RoleSpawnParameters[0];
/*     */   
/*     */   protected String id;
/*     */   
/*     */   protected double weight;
/*     */   protected String spawnBlockSet;
/*  83 */   protected int spawnBlockSetIndex = Integer.MIN_VALUE;
/*     */   protected String spawnFluidTag;
/*  85 */   protected int spawnFluidTagIndex = Integer.MIN_VALUE;
/*     */   
/*     */   protected String flockDefinitionId;
/*  88 */   protected int flockDefinitionIndex = Integer.MIN_VALUE;
/*     */   
/*     */   public RoleSpawnParameters(String id, double weight, String spawnBlockSet, String flockDefinitionId) {
/*  91 */     this.id = id;
/*  92 */     this.weight = weight;
/*  93 */     this.spawnBlockSet = spawnBlockSet;
/*  94 */     this.flockDefinitionId = flockDefinitionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 101 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getWeight() {
/* 106 */     return this.weight;
/*     */   }
/*     */   
/*     */   public String getSpawnBlockSet() {
/* 110 */     return this.spawnBlockSet;
/*     */   }
/*     */   
/*     */   public int getSpawnBlockSetIndex() {
/* 114 */     return this.spawnBlockSetIndex;
/*     */   }
/*     */   
/*     */   public int getSpawnFluidTagIndex() {
/* 118 */     return this.spawnFluidTagIndex;
/*     */   }
/*     */   
/*     */   public String getFlockDefinitionId() {
/* 122 */     return this.flockDefinitionId;
/*     */   }
/*     */   
/*     */   public int getFlockDefinitionIndex() {
/* 126 */     if (this.flockDefinitionIndex == Integer.MIN_VALUE && this.flockDefinitionId != null) {
/* 127 */       int index = FlockAsset.getAssetMap().getIndex(this.flockDefinitionId);
/* 128 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + this.flockDefinitionId); 
/* 129 */       this.flockDefinitionIndex = index;
/*     */     } 
/* 131 */     return this.flockDefinitionIndex;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public FlockAsset getFlockDefinition() {
/* 136 */     int index = getFlockDefinitionIndex();
/* 137 */     return (index != Integer.MIN_VALUE) ? (FlockAsset)FlockAsset.getAssetMap().getAsset(index) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 143 */     return "RoleSpawnParameters{id='" + this.id + "', weight=" + this.weight + ", spawnBlockSet=" + this.spawnBlockSet + ", flockDefinitionId=" + this.flockDefinitionId + "}";
/*     */   }
/*     */   
/*     */   protected RoleSpawnParameters() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\assets\spawns\config\RoleSpawnParameters.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */