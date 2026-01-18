/*     */ package com.hypixel.hytale.server.core.modules.collision;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.protocol.BlockMaterial;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.util.FillerBlockUtil;
/*     */ import java.util.function.Predicate;
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
/*     */ public class CollisionConfig
/*     */ {
/*     */   public static final int MATERIAL_EMPTY = 1;
/*     */   public static final int MATERIAL_FLUID = 2;
/*     */   public static final int MATERIAL_SOLID = 4;
/*     */   public static final int MATERIAL_SUBMERGED = 8;
/*     */   public static final int MATERIAL_DAMAGE = 16;
/*     */   public static final int MATERIAL_SET_NONE = 0;
/*     */   public static final int MATERIAL_SET_ANY = 15;
/*     */   private static final int INVALID_CHUNK_SECTION_INDEX = -2147483648;
/*     */   public int blockId;
/*     */   @Nullable
/*     */   public BlockType blockType;
/*     */   @Nullable
/*     */   public BlockMaterial blockMaterial;
/*     */   public int rotation;
/*     */   public int blockX;
/*     */   public int blockY;
/*     */   public int blockZ;
/*     */   private int boundingBoxOffsetX;
/*     */   private int boundingBoxOffsetY;
/*     */   private int boundingBoxOffsetZ;
/*     */   private BlockBoundingBoxes.RotatedVariantBoxes boundingBoxes;
/*     */   @Nullable
/*     */   private WorldChunk chunk;
/*     */   private int chunkSectionIndex;
/*     */   @Nullable
/*     */   private BlockSection chunkSection;
/*     */   private int chunkX;
/*     */   private int chunkY;
/*     */   private int chunkZ;
/*     */   @Nullable
/*     */   private Ref<ChunkStore> chunkSectionRef;
/*     */   @Nullable
/*     */   public Fluid fluid;
/*     */   public int fluidId;
/*     */   public byte fluidLevel;
/*     */   @Nonnull
/*  70 */   private Box blockBox = new Box();
/*     */   
/*     */   private World world;
/*     */   
/*     */   private int blockMaterialCollisionMask;
/*     */   
/*     */   public int blockMaterialMask;
/*     */   
/*     */   public boolean blockCanCollide;
/*     */   
/*     */   public boolean blockCanTrigger;
/*     */   public boolean blockCanTriggerPartial;
/*     */   public boolean checkTriggerBlocks;
/*     */   public boolean checkDamageBlocks;
/*     */   public Predicate<CollisionConfig> canCollide;
/*     */   public boolean dumpInvalidBlocks;
/*     */   @Nullable
/*     */   public Object extraData1;
/*     */   @Nullable
/*     */   public Object extraData2;
/*     */   
/*     */   public CollisionConfig() {
/*  92 */     this.checkTriggerBlocks = true;
/*  93 */     this.checkDamageBlocks = true;
/*     */   }
/*     */   
/*     */   public int getDetailCount() {
/*  97 */     return (this.boundingBoxes.getDetailBoxes()).length;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box getBoundingBox() {
/* 102 */     this.blockBox.assign(this.boundingBoxes.getBoundingBox());
/*     */ 
/*     */     
/* 105 */     if (this.blockId == 0 && this.fluidId != 0 && this.fluid != null) {
/*     */       
/* 107 */       this.blockBox.max.y -= 0.03125D;
/* 108 */       this.blockBox.max.y *= this.fluidLevel / this.fluid.getMaxFluidLevel();
/*     */     } 
/* 110 */     return this.blockBox;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box getBoundingBox(int i) {
/* 115 */     this.blockBox.assign(this.boundingBoxes.getDetailBoxes()[i]);
/* 116 */     return this.blockBox;
/*     */   }
/*     */   
/*     */   public int getBoundingBoxOffsetX() {
/* 120 */     return this.boundingBoxOffsetX;
/*     */   }
/*     */   
/*     */   public int getBoundingBoxOffsetY() {
/* 124 */     return this.boundingBoxOffsetY;
/*     */   }
/*     */   
/*     */   public int getBoundingBoxOffsetZ() {
/* 128 */     return this.boundingBoxOffsetZ;
/*     */   }
/*     */   
/*     */   public void setCollisionByMaterial(int collidingMaterials) {
/* 132 */     this.blockMaterialCollisionMask = this.blockMaterialCollisionMask & 0xFFFFFFF0 | collidingMaterials & 0xF;
/*     */   }
/*     */   
/*     */   public int getCollisionByMaterial() {
/* 136 */     return this.blockMaterialCollisionMask & 0xF;
/*     */   }
/*     */   
/*     */   public boolean isCollidingWithDamageBlocks() {
/* 140 */     return ((this.blockMaterialCollisionMask & 0x10) != 0);
/*     */   }
/*     */   
/*     */   public boolean setCollideWithDamageBlocks(boolean damageColliding) {
/* 144 */     boolean oldState = isCollidingWithDamageBlocks();
/* 145 */     if (damageColliding) { this.blockMaterialCollisionMask |= 0x10; }
/* 146 */     else { this.blockMaterialCollisionMask &= 0xFFFFFFEF; }
/* 147 */      return oldState;
/*     */   }
/*     */   
/*     */   public Predicate<CollisionConfig> getBlockCollisionPredicate() {
/* 151 */     return this.canCollide;
/*     */   }
/*     */   
/*     */   public void setDefaultCollisionBehaviour() {
/* 155 */     setCollisionByMaterial(4);
/* 156 */     setCollideWithDamageBlocks(true);
/* 157 */     setDefaultBlockCollisionPredicate();
/*     */   }
/*     */   
/*     */   public void setDefaultBlockCollisionPredicate() {
/* 161 */     this.canCollide = (collisionConfig -> ((collisionConfig.blockMaterialMask & collisionConfig.blockMaterialCollisionMask) != 0));
/*     */   }
/*     */   
/*     */   public boolean isCheckTriggerBlocks() {
/* 165 */     return this.checkTriggerBlocks;
/*     */   }
/*     */   
/*     */   public void setCheckTriggerBlocks(boolean checkTriggerBlocks) {
/* 169 */     this.checkTriggerBlocks = checkTriggerBlocks;
/*     */   }
/*     */   
/*     */   public boolean isCheckDamageBlocks() {
/* 173 */     return this.checkDamageBlocks;
/*     */   }
/*     */   
/*     */   public void setCheckDamageBlocks(boolean checkDamageBlocks) {
/* 177 */     this.checkDamageBlocks = checkDamageBlocks;
/*     */   }
/*     */   
/*     */   public void setWorld(World world) {
/* 181 */     if (this.world != world) {
/* 182 */       this.chunk = null;
/* 183 */       this.chunkSectionRef = null;
/* 184 */       this.chunkSection = null;
/* 185 */       this.chunkSectionIndex = Integer.MIN_VALUE;
/*     */     } 
/* 187 */     this.world = world;
/* 188 */     this.blockId = Integer.MIN_VALUE;
/* 189 */     this.blockX = Integer.MIN_VALUE;
/* 190 */     this.blockY = Integer.MIN_VALUE;
/* 191 */     this.blockZ = Integer.MIN_VALUE;
/*     */   } public boolean canCollide(int x, int y, int z) {
/*     */     int newFluidId;
/*     */     Fluid newFluid;
/* 195 */     this.blockX = x;
/* 196 */     this.blockY = y;
/* 197 */     this.blockZ = z;
/*     */     
/* 199 */     int chunkX = ChunkUtil.chunkCoordinate(x);
/* 200 */     int chunkY = ChunkUtil.chunkCoordinate(y);
/* 201 */     int chunkZ = ChunkUtil.chunkCoordinate(z);
/*     */     
/* 203 */     if (this.chunk == null || this.chunk.getX() != chunkX || chunkZ != this.chunk.getZ()) {
/* 204 */       this.chunk = this.world.getChunkIfInMemory(ChunkUtil.indexChunk(chunkX, chunkZ));
/* 205 */       this.chunkSectionIndex = Integer.MIN_VALUE;
/* 206 */       this.chunkSection = null;
/*     */     } 
/*     */     
/* 209 */     if (this.chunkSectionRef == null || !this.chunkSectionRef.isValid() || this.chunkX != chunkX || this.chunkY != chunkY || this.chunkZ != chunkZ) {
/* 210 */       this.chunkSectionRef = this.world.getChunkStore().getChunkSectionReference(chunkX, chunkY, chunkZ);
/* 211 */       this.chunkX = chunkX;
/* 212 */       this.chunkY = chunkY;
/* 213 */       this.chunkZ = chunkZ;
/*     */     } 
/*     */     
/* 216 */     this.blockCanTrigger = false;
/*     */ 
/*     */     
/* 219 */     if (this.chunk == null || this.chunkSectionRef == null) {
/* 220 */       this.blockType = null;
/* 221 */       this.blockMaterial = null;
/* 222 */       this.fluid = null;
/* 223 */       this.fluidId = Integer.MIN_VALUE;
/* 224 */       this.boundingBoxes = BlockBoundingBoxes.UNIT_BOX.get(Rotation.None, Rotation.None, Rotation.None);
/* 225 */       this.blockMaterialMask = 0;
/* 226 */       this.blockCanCollide = true;
/* 227 */       this.blockId = Integer.MIN_VALUE;
/* 228 */       return true;
/*     */     } 
/*     */     
/* 231 */     int sectionIndex = ChunkUtil.indexSection(y);
/* 232 */     if (this.chunkSection == null || this.chunkSectionIndex != sectionIndex) {
/* 233 */       this.chunkSectionIndex = sectionIndex;
/* 234 */       if (sectionIndex >= 0 && this.chunkSectionIndex < 10) {
/* 235 */         this.chunkSection = this.chunk.getBlockChunk().getSectionAtIndex(sectionIndex);
/*     */       } else {
/* 237 */         this.chunkSection = null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 242 */     if (this.chunkSection == null) {
/* 243 */       this.blockType = BlockType.EMPTY;
/* 244 */       this.blockMaterial = BlockMaterial.Empty;
/* 245 */       this.fluid = null;
/* 246 */       this.fluidId = Integer.MIN_VALUE;
/* 247 */       this.boundingBoxes = BlockBoundingBoxes.UNIT_BOX.get(Rotation.None, Rotation.None, Rotation.None);
/* 248 */       this.blockMaterialMask = 1;
/* 249 */       this.blockCanCollide = ((this.blockMaterialCollisionMask & this.blockMaterialMask) != 0);
/* 250 */       this.blockId = 0;
/* 251 */       return this.blockCanCollide;
/*     */     } 
/*     */     
/* 254 */     int newBlockId = this.chunkSection.get(x, y, z);
/* 255 */     BlockType newBlockType = (BlockType)BlockType.getAssetMap().getAsset(newBlockId);
/*     */     
/* 257 */     FluidSection fluidSection = (FluidSection)this.chunkSectionRef.getStore().getComponent(this.chunkSectionRef, FluidSection.getComponentType());
/*     */ 
/*     */     
/* 260 */     byte newFluidLevel = 0;
/* 261 */     if (fluidSection != null) {
/* 262 */       newFluidId = fluidSection.getFluidId(this.blockX, this.blockY, this.blockZ);
/* 263 */       newFluid = (Fluid)Fluid.getAssetMap().getAsset(newFluidId);
/* 264 */       newFluidLevel = fluidSection.getFluidLevel(this.blockX, this.blockY, this.blockZ);
/*     */     } else {
/* 266 */       newFluidId = Integer.MIN_VALUE;
/* 267 */       newFluid = null;
/*     */     } 
/*     */     
/* 270 */     int filler = this.chunkSection.getFiller(x, y, z);
/* 271 */     if (!newBlockType.isUnknown() && filler != 0) {
/* 272 */       this.boundingBoxOffsetX = -FillerBlockUtil.unpackX(filler);
/* 273 */       this.boundingBoxOffsetY = -FillerBlockUtil.unpackY(filler);
/* 274 */       this.boundingBoxOffsetZ = -FillerBlockUtil.unpackZ(filler);
/*     */     } else {
/* 276 */       this.boundingBoxOffsetX = 0;
/* 277 */       this.boundingBoxOffsetY = 0;
/* 278 */       this.boundingBoxOffsetZ = 0;
/*     */     } 
/*     */     
/* 281 */     int newRotation = this.chunkSection.getRotationIndex(x, y, z);
/*     */ 
/*     */     
/* 284 */     if (newBlockId == this.blockId && this.rotation == newRotation && this.fluidId == newFluidId && this.fluidLevel == newFluidLevel) {
/* 285 */       this.blockCanTrigger = (this.blockCanTriggerPartial || (this.checkTriggerBlocks && (newBlockType.isTrigger() || newFluid.isTrigger())));
/* 286 */       return (this.blockCanCollide || this.blockCanTrigger);
/*     */     } 
/*     */     
/* 289 */     this.blockId = newBlockId;
/* 290 */     this.blockType = newBlockType;
/* 291 */     this.rotation = newRotation;
/* 292 */     this.fluidId = newFluidId;
/* 293 */     this.fluid = newFluid;
/* 294 */     this.fluidLevel = newFluidLevel;
/*     */     
/* 296 */     boolean blockWillDamage = (this.checkDamageBlocks && (this.blockType.getDamageToEntities() > 0 || this.fluid.getDamageToEntities() > 0));
/* 297 */     this.blockCanTrigger = (blockWillDamage || (this.checkTriggerBlocks && (this.blockType.isTrigger() || newFluid.isTrigger())));
/* 298 */     this.blockMaterialMask = blockWillDamage ? 16 : 0;
/*     */     
/* 300 */     if ((this.blockId == 0 || this.blockType == BlockType.EMPTY) && this.fluidId == 0) {
/* 301 */       this.blockMaterial = BlockMaterial.Empty;
/* 302 */       this.boundingBoxes = BlockBoundingBoxes.UNIT_BOX.get(Rotation.None, Rotation.None, Rotation.None);
/* 303 */       this.blockMaterialMask |= 0x1;
/* 304 */       this.blockCanCollide = ((this.blockMaterialMask & this.blockMaterialCollisionMask) != 0);
/* 305 */       return (this.blockCanCollide || this.blockCanTrigger);
/*     */     } 
/*     */     
/* 308 */     this.blockMaterial = this.blockType.getMaterial();
/* 309 */     this.boundingBoxes = ((BlockBoundingBoxes)BlockBoundingBoxes.getAssetMap().getAsset(newBlockType.getHitboxTypeIndex())).get(this.rotation);
/*     */     
/* 311 */     if (this.blockMaterial == BlockMaterial.Empty) {
/* 312 */       this.blockMaterialMask |= (this.fluidId != 0) ? 2 : 1;
/* 313 */       this.blockCanCollide = ((this.blockMaterialMask & this.blockMaterialCollisionMask) != 0);
/* 314 */       return (this.blockCanCollide || this.blockCanTrigger);
/*     */     } 
/*     */     
/* 317 */     if (this.boundingBoxes == null) {
/* 318 */       this.boundingBoxes = BlockBoundingBoxes.UNIT_BOX.get(Rotation.None, Rotation.None, Rotation.None);
/*     */     }
/*     */     
/* 321 */     this.blockMaterialMask |= (this.fluidId == 0) ? 4 : 14;
/* 322 */     this.blockCanCollide = this.canCollide.test(this);
/* 323 */     return (this.blockCanCollide || this.blockCanTrigger);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 327 */     this.chunk = null;
/* 328 */     this.chunkSectionIndex = Integer.MIN_VALUE;
/* 329 */     this.chunkSection = null;
/* 330 */     setWorld(null);
/*     */     
/* 332 */     this.dumpInvalidBlocks = false;
/* 333 */     this.extraData1 = null;
/* 334 */     this.extraData2 = null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\CollisionConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */