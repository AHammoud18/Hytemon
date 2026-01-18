/*     */ package com.hypixel.hytale.server.core.modules.collision;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.protocol.BlockMaterial;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
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
/*     */ public class BlockDataProvider
/*     */   extends BlockData
/*     */ {
/*  27 */   protected static int FULL_LEVEL = 8;
/*  28 */   protected final int INVALID_CHUNK_SECTION_INDEX = Integer.MIN_VALUE;
/*     */   
/*     */   @Nullable
/*     */   protected World world;
/*     */   
/*     */   @Nullable
/*     */   protected WorldChunk chunk;
/*     */   protected int chunkSectionIndex;
/*     */   @Nullable
/*     */   protected BlockSection chunkSection;
/*     */   protected int chunkX;
/*     */   protected int chunkY;
/*     */   protected int chunkZ;
/*     */   @Nullable
/*     */   protected Ref<ChunkStore> chunkSectionRef;
/*     */   
/*     */   public void initialize(World world) {
/*  45 */     this.world = world;
/*  46 */     this.blockId = Integer.MIN_VALUE;
/*  47 */     cleanup0();
/*     */   }
/*     */   
/*     */   public void cleanup() {
/*  51 */     this.world = null;
/*  52 */     cleanup0();
/*     */   }
/*     */   
/*     */   public void read(int x, int y, int z) {
/*  56 */     int newBlockId = readBlockId(x, y, z);
/*     */     
/*  58 */     int fluidId = readFluidId(x, y, z);
/*     */     
/*  60 */     if (this.blockId == newBlockId && this.fluidId == fluidId)
/*     */       return; 
/*  62 */     if (newBlockId == 0 && fluidId == 0) {
/*  63 */       setBlock(0, BlockType.EMPTY, 0, 1);
/*  64 */       this.fluidId = 0;
/*  65 */       this.fluid = Fluid.EMPTY;
/*  66 */       this.fluidKey = "Empty";
/*  67 */       this.fillHeight = 0.0D;
/*     */       
/*     */       return;
/*     */     } 
/*  71 */     if (newBlockId == 1) {
/*  72 */       setBlock(1, BlockType.UNKNOWN, 0, 4);
/*  73 */       this.fluidId = 0;
/*  74 */       this.fluid = Fluid.EMPTY;
/*  75 */       this.fluidKey = "Empty";
/*  76 */       this.fillHeight = 0.0D;
/*     */       
/*     */       return;
/*     */     } 
/*  80 */     this.blockId = newBlockId;
/*  81 */     this.blockType = (BlockType)BlockType.getAssetMap().getAsset(newBlockId);
/*  82 */     if (this.blockType.isUnknown()) {
/*  83 */       setBlock(newBlockId, this.blockType, 0, 4);
/*  84 */       this.fluidId = 0;
/*  85 */       this.fluid = Fluid.EMPTY;
/*  86 */       this.fluidKey = "Empty";
/*  87 */       this.fillHeight = 0.0D;
/*     */       
/*     */       return;
/*     */     } 
/*  91 */     Fluid fluid = (Fluid)Fluid.getAssetMap().getAsset(fluidId);
/*  92 */     byte fluidLevel = readFluidLevel(x, y, z);
/*     */     
/*  94 */     this.blockTypeKey = this.blockType.getId();
/*  95 */     this.filler = readFiller(x, y, z);
/*  96 */     this.rotation = readRotation(x, y, z);
/*     */     
/*  98 */     if (this.blockType.getMaterial() == BlockMaterial.Solid) {
/*  99 */       this.collisionMaterials = 4;
/*     */ 
/*     */       
/* 102 */       if (this.blockType.getHitboxTypeIndex() != 0)
/*     */       {
/* 104 */         this.collisionMaterials += materialFromFillLevel(fluid, fluidLevel);
/*     */       }
/*     */     } else {
/* 107 */       this.collisionMaterials = materialFromFillLevel(fluid, fluidLevel);
/*     */     } 
/*     */     
/* 110 */     this.fluidId = fluidId;
/* 111 */     this.fluid = fluid;
/* 112 */     this.fluidKey = fluid.getId();
/* 113 */     this.fillHeight = (fluidId != 0) ? (fluidLevel / fluid.getMaxFluidLevel()) : 0.0D;
/* 114 */     this.blockBoundingBoxes = null;
/*     */   }
/*     */   
/*     */   protected int readBlockId(int x, int y, int z) {
/* 118 */     int chunkX = ChunkUtil.chunkCoordinate(x);
/* 119 */     int chunkZ = ChunkUtil.chunkCoordinate(z);
/*     */     
/* 121 */     if (this.chunk == null || this.chunk.getX() != chunkX || chunkZ != this.chunk.getZ()) {
/* 122 */       this.chunk = this.world.getChunkIfInMemory(ChunkUtil.indexChunk(chunkX, chunkZ));
/* 123 */       this.chunkSectionIndex = Integer.MIN_VALUE;
/* 124 */       this.chunkSection = null;
/*     */     } 
/*     */ 
/*     */     
/* 128 */     if (this.chunk == null) return 1;
/*     */     
/* 130 */     int sectionIndex = ChunkUtil.indexSection(y);
/* 131 */     if (this.chunkSection == null || this.chunkSectionIndex != sectionIndex) {
/* 132 */       this.chunkSectionIndex = sectionIndex;
/* 133 */       this.chunkSection = (sectionIndex >= 0 && this.chunkSectionIndex < 10) ? this.chunk.getBlockChunk().getSectionAtIndex(sectionIndex) : null;
/*     */     } 
/*     */ 
/*     */     
/* 137 */     if (this.chunkSection == null) return 0;
/*     */     
/* 139 */     return this.chunkSection.get(x, y, z);
/*     */   }
/*     */   
/*     */   protected int readRotation(int x, int y, int z) {
/* 143 */     int chunkX = ChunkUtil.chunkCoordinate(x);
/* 144 */     int chunkZ = ChunkUtil.chunkCoordinate(z);
/*     */     
/* 146 */     if (this.chunk == null || this.chunk.getX() != chunkX || chunkZ != this.chunk.getZ()) {
/* 147 */       this.chunk = this.world.getChunkIfInMemory(ChunkUtil.indexChunk(chunkX, chunkZ));
/* 148 */       this.chunkSectionIndex = Integer.MIN_VALUE;
/* 149 */       this.chunkSection = null;
/*     */     } 
/*     */ 
/*     */     
/* 153 */     if (this.chunk == null) return 0;
/*     */     
/* 155 */     int sectionIndex = ChunkUtil.indexSection(y);
/* 156 */     if (this.chunkSection == null || this.chunkSectionIndex != sectionIndex) {
/* 157 */       this.chunkSectionIndex = sectionIndex;
/* 158 */       this.chunkSection = (sectionIndex >= 0 && this.chunkSectionIndex < 10) ? this.chunk.getBlockChunk().getSectionAtIndex(sectionIndex) : null;
/*     */     } 
/*     */ 
/*     */     
/* 162 */     if (this.chunkSection == null) return 0;
/*     */     
/* 164 */     return this.chunkSection.getRotationIndex(x, y, z);
/*     */   }
/*     */   
/*     */   protected int readFiller(int x, int y, int z) {
/* 168 */     int chunkX = ChunkUtil.chunkCoordinate(x);
/* 169 */     int chunkZ = ChunkUtil.chunkCoordinate(z);
/*     */     
/* 171 */     if (this.chunk == null || this.chunk.getX() != chunkX || chunkZ != this.chunk.getZ()) {
/* 172 */       this.chunk = this.world.getChunkIfInMemory(ChunkUtil.indexChunk(chunkX, chunkZ));
/* 173 */       this.chunkSectionIndex = Integer.MIN_VALUE;
/* 174 */       this.chunkSection = null;
/*     */     } 
/*     */     
/* 177 */     if (this.chunk == null) return 0;
/*     */     
/* 179 */     int sectionIndex = ChunkUtil.indexSection(y);
/* 180 */     if (this.chunkSection == null || this.chunkSectionIndex != sectionIndex) {
/* 181 */       this.chunkSectionIndex = sectionIndex;
/* 182 */       this.chunkSection = (sectionIndex >= 0 && this.chunkSectionIndex < 10) ? this.chunk.getBlockChunk().getSectionAtIndex(sectionIndex) : null;
/*     */     } 
/*     */ 
/*     */     
/* 186 */     if (this.chunkSection == null) return 0;
/*     */     
/* 188 */     return this.chunkSection.getFiller(x, y, z);
/*     */   }
/*     */   
/*     */   protected int readFluidId(int x, int y, int z) {
/* 192 */     int chunkX = ChunkUtil.chunkCoordinate(x);
/* 193 */     int chunkY = ChunkUtil.chunkCoordinate(y);
/* 194 */     int chunkZ = ChunkUtil.chunkCoordinate(z);
/*     */     
/* 196 */     if (this.chunkSectionRef == null || !this.chunkSectionRef.isValid() || this.chunkX != chunkX || this.chunkY != chunkY || this.chunkZ != chunkZ) {
/* 197 */       this.chunkSectionRef = this.world.getChunkStore().getChunkSectionReference(chunkX, chunkY, chunkZ);
/* 198 */       this.chunkX = chunkX;
/* 199 */       this.chunkY = chunkY;
/* 200 */       this.chunkZ = chunkZ;
/*     */     } 
/*     */ 
/*     */     
/* 204 */     if (this.chunkSectionRef == null) return 1;
/*     */     
/* 206 */     FluidSection fluidSection = (FluidSection)this.world.getChunkStore().getStore().getComponent(this.chunkSectionRef, FluidSection.getComponentType());
/* 207 */     if (fluidSection == null) return 1;
/*     */     
/* 209 */     return fluidSection.getFluidId(x, y, z);
/*     */   }
/*     */   
/*     */   protected byte readFluidLevel(int x, int y, int z) {
/* 213 */     int chunkX = ChunkUtil.chunkCoordinate(x);
/* 214 */     int chunkY = ChunkUtil.chunkCoordinate(y);
/* 215 */     int chunkZ = ChunkUtil.chunkCoordinate(z);
/*     */     
/* 217 */     if (this.chunkSectionRef == null || !this.chunkSectionRef.isValid() || this.chunkX != chunkX || this.chunkY != chunkY || this.chunkZ != chunkZ) {
/* 218 */       this.chunkSectionRef = this.world.getChunkStore().getChunkSectionReference(chunkX, chunkY, chunkZ);
/* 219 */       this.chunkX = chunkX;
/* 220 */       this.chunkY = chunkY;
/* 221 */       this.chunkZ = chunkZ;
/*     */     } 
/*     */     
/* 224 */     if (this.chunkSectionRef == null) return 0;
/*     */     
/* 226 */     FluidSection fluidSection = (FluidSection)this.world.getChunkStore().getStore().getComponent(this.chunkSectionRef, FluidSection.getComponentType());
/* 227 */     if (fluidSection == null) return 0;
/*     */     
/* 229 */     return fluidSection.getFluidLevel(x, y, z);
/*     */   }
/*     */   
/*     */   protected void setBlock(int id, @Nonnull BlockType type, int rotation, int material, BlockBoundingBoxes box) {
/* 233 */     this.blockId = id;
/* 234 */     this.rotation = rotation;
/* 235 */     this.blockType = type;
/* 236 */     this.blockTypeKey = type.getId();
/* 237 */     this.collisionMaterials = material;
/* 238 */     this.blockBoundingBoxes = box;
/* 239 */     this.fillHeight = 0.0D;
/*     */   }
/*     */   
/*     */   protected void setBlock(int id, @Nonnull BlockType type, int rotation, int material) {
/* 243 */     setBlock(id, type, rotation, material, BlockBoundingBoxes.UNIT_BOX);
/*     */   }
/*     */   
/*     */   protected void cleanup0() {
/* 247 */     this.chunk = null;
/* 248 */     this.chunkSectionIndex = Integer.MIN_VALUE;
/* 249 */     this.chunkSection = null;
/* 250 */     this.blockType = null;
/* 251 */     this.blockTypeKey = null;
/* 252 */     this.blockBoundingBoxes = null;
/* 253 */     this.fluid = null;
/* 254 */     this.fluidKey = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static int materialFromFillLevel(@Nonnull Fluid fluid, byte level) {
/* 259 */     if (level == 0) return 1; 
/* 260 */     if (level == fluid.getMaxFluidLevel()) return 2; 
/* 261 */     return 3;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\BlockDataProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */