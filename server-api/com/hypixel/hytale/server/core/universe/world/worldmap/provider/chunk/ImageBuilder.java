/*     */ package com.hypixel.hytale.server.core.universe.world.worldmap.provider.chunk;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.protocol.packets.worldmap.MapImage;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.concurrent.Executor;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ class ImageBuilder
/*     */ {
/*     */   private final long index;
/*     */   private final World world;
/*     */   @Nonnull
/*     */   private final MapImage image;
/*     */   private final int sampleWidth;
/*     */   private final int sampleHeight;
/*     */   private final int blockStepX;
/*     */   private final int blockStepZ;
/*     */   @Nonnull
/*     */   private final short[] heightSamples;
/*     */   @Nonnull
/*     */   private final int[] tintSamples;
/*     */   @Nonnull
/*     */   private final int[] blockSamples;
/*     */   @Nonnull
/*     */   private final short[] neighborHeightSamples;
/*     */   @Nonnull
/*     */   private final short[] fluidDepthSamples;
/*     */   @Nonnull
/*     */   private final int[] environmentSamples;
/*     */   @Nonnull
/*     */   private final int[] fluidSamples;
/*  46 */   private final Color outColor = new Color();
/*     */   
/*     */   @Nullable
/*     */   private WorldChunk worldChunk;
/*     */   private FluidSection[] fluidSections;
/*     */   
/*     */   public ImageBuilder(long index, int imageWidth, int imageHeight, World world) {
/*  53 */     this.index = index;
/*  54 */     this.world = world;
/*     */     
/*  56 */     this.image = new MapImage(imageWidth, imageHeight, new int[imageWidth * imageHeight]);
/*     */     
/*  58 */     this.sampleWidth = Math.min(32, this.image.width);
/*  59 */     this.sampleHeight = Math.min(32, this.image.height);
/*  60 */     this.blockStepX = Math.max(1, 32 / this.image.width);
/*  61 */     this.blockStepZ = Math.max(1, 32 / this.image.height);
/*     */     
/*  63 */     this.heightSamples = new short[this.sampleWidth * this.sampleHeight];
/*  64 */     this.tintSamples = new int[this.sampleWidth * this.sampleHeight];
/*  65 */     this.blockSamples = new int[this.sampleWidth * this.sampleHeight];
/*     */     
/*  67 */     this.neighborHeightSamples = new short[(this.sampleWidth + 2) * (this.sampleHeight + 2)];
/*     */     
/*  69 */     this.fluidDepthSamples = new short[this.sampleWidth * this.sampleHeight];
/*  70 */     this.environmentSamples = new int[this.sampleWidth * this.sampleHeight];
/*  71 */     this.fluidSamples = new int[this.sampleWidth * this.sampleHeight];
/*     */   }
/*     */   
/*     */   public long getIndex() {
/*  75 */     return this.index;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public MapImage getImage() {
/*  80 */     return this.image;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private CompletableFuture<ImageBuilder> fetchChunk() {
/*  85 */     return this.world.getChunkStore().getChunkReferenceAsync(this.index).thenApplyAsync(ref -> { if (ref == null || !ref.isValid()) return null;  this.worldChunk = (WorldChunk)ref.getStore().getComponent(ref, WorldChunk.getComponentType()); ChunkColumn chunkColumn = (ChunkColumn)ref.getStore().getComponent(ref, ChunkColumn.getComponentType()); this.fluidSections = new FluidSection[10]; for (int y = 0; y < 10; y++) { Ref<ChunkStore> sectionRef = chunkColumn.getSection(y); this.fluidSections[y] = (FluidSection)this.world.getChunkStore().getStore().getComponent(sectionRef, FluidSection.getComponentType()); }  return this; }(Executor)this.world);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private CompletableFuture<ImageBuilder> sampleNeighborsSync() {
/* 103 */     CompletableFuture<Void> north = this.world.getChunkStore().getChunkReferenceAsync(ChunkUtil.indexChunk(this.worldChunk.getX(), this.worldChunk.getZ() - 1)).thenAcceptAsync(ref -> {
/*     */           if (ref == null || !ref.isValid()) {
/*     */             return;
/*     */           }
/*     */           
/*     */           WorldChunk worldChunk = (WorldChunk)ref.getStore().getComponent(ref, WorldChunk.getComponentType());
/*     */           int z = (this.sampleHeight - 1) * this.blockStepZ;
/*     */           for (int ix = 0; ix < this.sampleWidth; ix++) {
/*     */             int x = ix * this.blockStepX;
/*     */             this.neighborHeightSamples[1 + ix] = worldChunk.getHeight(x, z);
/*     */           } 
/*     */         }(Executor)this.world);
/* 115 */     CompletableFuture<Void> south = this.world.getChunkStore().getChunkReferenceAsync(ChunkUtil.indexChunk(this.worldChunk.getX(), this.worldChunk.getZ() + 1)).thenAcceptAsync(ref -> {
/*     */           if (ref == null || !ref.isValid()) {
/*     */             return;
/*     */           }
/*     */           
/*     */           WorldChunk worldChunk = (WorldChunk)ref.getStore().getComponent(ref, WorldChunk.getComponentType());
/*     */           int z = 0;
/*     */           int neighbourStartIndex = (this.sampleHeight + 1) * (this.sampleWidth + 2) + 1;
/*     */           for (int ix = 0; ix < this.sampleWidth; ix++) {
/*     */             int x = ix * this.blockStepX;
/*     */             this.neighborHeightSamples[neighbourStartIndex + ix] = worldChunk.getHeight(x, z);
/*     */           } 
/*     */         }(Executor)this.world);
/* 128 */     CompletableFuture<Void> west = this.world.getChunkStore().getChunkReferenceAsync(ChunkUtil.indexChunk(this.worldChunk.getX() - 1, this.worldChunk.getZ())).thenAcceptAsync(ref -> {
/*     */           if (ref == null || !ref.isValid()) {
/*     */             return;
/*     */           }
/*     */           
/*     */           WorldChunk worldChunk = (WorldChunk)ref.getStore().getComponent(ref, WorldChunk.getComponentType());
/*     */           int x = (this.sampleWidth - 1) * this.blockStepX;
/*     */           for (int iz = 0; iz < this.sampleHeight; iz++) {
/*     */             int z = iz * this.blockStepZ;
/*     */             this.neighborHeightSamples[(iz + 1) * (this.sampleWidth + 2)] = worldChunk.getHeight(x, z);
/*     */           } 
/*     */         }(Executor)this.world);
/* 140 */     CompletableFuture<Void> east = this.world.getChunkStore().getChunkReferenceAsync(ChunkUtil.indexChunk(this.worldChunk.getX() + 1, this.worldChunk.getZ())).thenAcceptAsync(ref -> {
/*     */           if (ref == null || !ref.isValid()) {
/*     */             return;
/*     */           }
/*     */           
/*     */           WorldChunk worldChunk = (WorldChunk)ref.getStore().getComponent(ref, WorldChunk.getComponentType());
/*     */           int x = 0;
/*     */           for (int iz = 0; iz < this.sampleHeight; iz++) {
/*     */             int z = iz * this.blockStepZ;
/*     */             this.neighborHeightSamples[(iz + 1) * (this.sampleWidth + 2) + this.sampleWidth + 1] = worldChunk.getHeight(x, z);
/*     */           } 
/*     */         }(Executor)this.world);
/* 152 */     CompletableFuture<Void> northeast = this.world.getChunkStore().getChunkReferenceAsync(ChunkUtil.indexChunk(this.worldChunk.getX() + 1, this.worldChunk.getZ() - 1)).thenAcceptAsync(ref -> {
/*     */           if (ref == null || !ref.isValid()) {
/*     */             return;
/*     */           }
/*     */           
/*     */           WorldChunk worldChunk = (WorldChunk)ref.getStore().getComponent(ref, WorldChunk.getComponentType());
/*     */           int x = 0;
/*     */           int z = (this.sampleHeight - 1) * this.blockStepZ;
/*     */           this.neighborHeightSamples[0] = worldChunk.getHeight(x, z);
/*     */         }(Executor)this.world);
/* 162 */     CompletableFuture<Void> northwest = this.world.getChunkStore().getChunkReferenceAsync(ChunkUtil.indexChunk(this.worldChunk.getX() - 1, this.worldChunk.getZ() - 1)).thenAcceptAsync(ref -> {
/*     */           if (ref == null || !ref.isValid()) {
/*     */             return;
/*     */           }
/*     */           
/*     */           WorldChunk worldChunk = (WorldChunk)ref.getStore().getComponent(ref, WorldChunk.getComponentType());
/*     */           int x = (this.sampleWidth - 1) * this.blockStepX;
/*     */           int z = (this.sampleHeight - 1) * this.blockStepZ;
/*     */           this.neighborHeightSamples[this.sampleWidth + 1] = worldChunk.getHeight(x, z);
/*     */         }(Executor)this.world);
/* 172 */     CompletableFuture<Void> southeast = this.world.getChunkStore().getChunkReferenceAsync(ChunkUtil.indexChunk(this.worldChunk.getX() + 1, this.worldChunk.getZ() + 1)).thenAcceptAsync(ref -> {
/*     */           if (ref == null || !ref.isValid()) {
/*     */             return;
/*     */           }
/*     */           
/*     */           WorldChunk worldChunk = (WorldChunk)ref.getStore().getComponent(ref, WorldChunk.getComponentType());
/*     */           int x = 0;
/*     */           int z = 0;
/*     */           this.neighborHeightSamples[(this.sampleHeight + 1) * (this.sampleWidth + 2) + this.sampleWidth + 1] = worldChunk.getHeight(x, z);
/*     */         }(Executor)this.world);
/* 182 */     CompletableFuture<Void> southwest = this.world.getChunkStore().getChunkReferenceAsync(ChunkUtil.indexChunk(this.worldChunk.getX() - 1, this.worldChunk.getZ() + 1)).thenAcceptAsync(ref -> {
/*     */           if (ref == null || !ref.isValid()) {
/*     */             return;
/*     */           }
/*     */           WorldChunk worldChunk = (WorldChunk)ref.getStore().getComponent(ref, WorldChunk.getComponentType());
/*     */           int x = (this.sampleWidth - 1) * this.blockStepX;
/*     */           int z = 0;
/*     */           this.neighborHeightSamples[(this.sampleHeight + 1) * (this.sampleWidth + 2)] = worldChunk.getHeight(x, z);
/*     */         }(Executor)this.world);
/* 191 */     return CompletableFuture.allOf((CompletableFuture<?>[])new CompletableFuture[] { north, south, west, east, northeast, northwest, southeast, southwest
/*     */ 
/*     */         
/* 194 */         }).thenApply(v -> this);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private ImageBuilder generateImageAsync() {
/* 199 */     for (int ix = 0; ix < this.sampleWidth; ix++) {
/* 200 */       for (int j = 0; j < this.sampleHeight; j++) {
/* 201 */         int sampleIndex = j * this.sampleWidth + ix;
/* 202 */         int x = ix * this.blockStepX;
/* 203 */         int z = j * this.blockStepZ;
/*     */         
/* 205 */         short height = this.worldChunk.getHeight(x, z);
/* 206 */         int tint = this.worldChunk.getTint(x, z);
/* 207 */         this.heightSamples[sampleIndex] = height;
/* 208 */         this.tintSamples[sampleIndex] = tint;
/*     */         
/* 210 */         int blockId = this.worldChunk.getBlock(x, height, z);
/* 211 */         this.blockSamples[sampleIndex] = blockId;
/*     */ 
/*     */         
/* 214 */         int fluidId = 0;
/* 215 */         int fluidTop = 320;
/* 216 */         Fluid fluid = null;
/*     */         
/* 218 */         int chunkYGround = ChunkUtil.chunkCoordinate(height);
/*     */ 
/*     */         
/* 221 */         int chunkY = 9;
/*     */         
/* 223 */         label83: while (chunkY >= 0 && chunkY >= chunkYGround) {
/* 224 */           FluidSection fluidSection = this.fluidSections[chunkY];
/*     */ 
/*     */           
/* 227 */           if (fluidSection == null || fluidSection.isEmpty()) {
/* 228 */             chunkY--;
/*     */ 
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 234 */           int minBlockY = Math.max(ChunkUtil.minBlock(chunkY), height);
/* 235 */           int maxBlockY = ChunkUtil.maxBlock(chunkY);
/* 236 */           for (int blockY = maxBlockY; blockY >= minBlockY; ) {
/* 237 */             fluidId = fluidSection.getFluidId(x, blockY, z);
/* 238 */             if (fluidId == 0) { blockY--; continue; }
/* 239 */              fluid = (Fluid)Fluid.getAssetMap().getAsset(fluidId);
/*     */ 
/*     */             
/* 242 */             fluidTop = blockY;
/*     */             
/*     */             break label83;
/*     */           } 
/* 246 */           chunkY--;
/*     */         } 
/*     */ 
/*     */         
/* 250 */         int fluidBottom = height;
/*     */         
/* 252 */         label84: while (chunkY >= 0 && chunkY >= chunkYGround) {
/* 253 */           FluidSection fluidSection = this.fluidSections[chunkY];
/*     */ 
/*     */           
/* 256 */           if (fluidSection == null || fluidSection.isEmpty()) {
/* 257 */             fluidBottom = Math.min(ChunkUtil.maxBlock(chunkY) + 1, fluidTop);
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 263 */           int minBlockY = Math.max(ChunkUtil.minBlock(chunkY), height);
/* 264 */           int maxBlockY = Math.min(ChunkUtil.maxBlock(chunkY), fluidTop - 1);
/* 265 */           for (int blockY = maxBlockY; blockY >= minBlockY; blockY--) {
/* 266 */             int nextFluidId = fluidSection.getFluidId(x, blockY, z);
/* 267 */             if (nextFluidId != fluidId) {
/* 268 */               Fluid nextFluid = (Fluid)Fluid.getAssetMap().getAsset(nextFluidId);
/* 269 */               if (!Objects.equals(fluid.getParticleColor(), nextFluid.getParticleColor())) {
/*     */ 
/*     */                 
/* 272 */                 fluidBottom = blockY + 1;
/*     */                 
/*     */                 break label84;
/*     */               } 
/*     */             } 
/*     */           } 
/* 278 */           chunkY--;
/*     */         } 
/*     */         
/* 281 */         short fluidDepth = (fluidId != 0) ? (short)(fluidTop - fluidBottom + 1) : 0;
/*     */         
/* 283 */         int environmentId = this.worldChunk.getBlockChunk().getEnvironment(x, fluidTop, z);
/*     */         
/* 285 */         this.fluidDepthSamples[sampleIndex] = fluidDepth;
/* 286 */         this.environmentSamples[sampleIndex] = environmentId;
/* 287 */         this.fluidSamples[sampleIndex] = fluidId;
/*     */       } 
/*     */     } 
/*     */     
/* 291 */     float imageToSampleRatioWidth = this.sampleWidth / this.image.width;
/* 292 */     float imageToSampleRatioHeight = this.sampleHeight / this.image.height;
/*     */ 
/*     */     
/* 295 */     int blockPixelWidth = Math.max(1, this.image.width / this.sampleWidth);
/* 296 */     int blockPixelHeight = Math.max(1, this.image.height / this.sampleHeight);
/*     */ 
/*     */     
/* 299 */     for (int iz = 0; iz < this.sampleHeight; iz++) {
/* 300 */       System.arraycopy(this.heightSamples, iz * this.sampleWidth, this.neighborHeightSamples, (iz + 1) * (this.sampleWidth + 2) + 1, this.sampleWidth);
/*     */     }
/*     */     
/* 303 */     for (int i = 0; i < this.image.width; i++) {
/* 304 */       for (int j = 0; j < this.image.height; j++) {
/* 305 */         int sampleX = Math.min((int)(i * imageToSampleRatioWidth), this.sampleWidth - 1);
/* 306 */         int sampleZ = Math.min((int)(j * imageToSampleRatioHeight), this.sampleHeight - 1);
/* 307 */         int sampleIndex = sampleZ * this.sampleWidth + sampleX;
/*     */         
/* 309 */         int blockPixelX = i % blockPixelWidth;
/* 310 */         int blockPixelZ = j % blockPixelHeight;
/*     */         
/* 312 */         short height = this.heightSamples[sampleIndex];
/* 313 */         int tint = this.tintSamples[sampleIndex];
/* 314 */         int blockId = this.blockSamples[sampleIndex];
/*     */ 
/*     */         
/* 317 */         if (height < 0 || blockId == 0) {
/* 318 */           this.outColor.r = this.outColor.g = this.outColor.b = this.outColor.a = 0;
/* 319 */           packImageData(i, j);
/*     */         }
/*     */         else {
/*     */           
/* 323 */           getBlockColor(blockId, tint, this.outColor);
/*     */           
/* 325 */           short north = this.neighborHeightSamples[sampleZ * (this.sampleWidth + 2) + sampleX + 1];
/* 326 */           short south = this.neighborHeightSamples[(sampleZ + 2) * (this.sampleWidth + 2) + sampleX + 1];
/* 327 */           short west = this.neighborHeightSamples[(sampleZ + 1) * (this.sampleWidth + 2) + sampleX];
/* 328 */           short east = this.neighborHeightSamples[(sampleZ + 1) * (this.sampleWidth + 2) + sampleX + 2];
/*     */           
/* 330 */           short northWest = this.neighborHeightSamples[sampleZ * (this.sampleWidth + 2) + sampleX];
/* 331 */           short northEast = this.neighborHeightSamples[sampleZ * (this.sampleWidth + 2) + sampleX + 2];
/* 332 */           short southWest = this.neighborHeightSamples[(sampleZ + 2) * (this.sampleWidth + 2) + sampleX];
/* 333 */           short southEast = this.neighborHeightSamples[(sampleZ + 2) * (this.sampleWidth + 2) + sampleX + 2];
/*     */           
/* 335 */           float shade = shadeFromHeights(blockPixelX, blockPixelZ, blockPixelWidth, blockPixelHeight, height, north, south, west, east, northWest, northEast, southWest, southEast);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 341 */           this.outColor.multiply(shade);
/*     */           
/* 343 */           if (height < 320) {
/* 344 */             int fluidId = this.fluidSamples[sampleIndex];
/* 345 */             if (fluidId != 0) {
/* 346 */               short fluidDepth = this.fluidDepthSamples[sampleIndex];
/* 347 */               int environmentId = this.environmentSamples[sampleIndex];
/*     */               
/* 349 */               getFluidColor(fluidId, environmentId, fluidDepth, this.outColor);
/*     */             } 
/*     */           } 
/*     */           
/* 353 */           packImageData(i, j);
/*     */         } 
/*     */       } 
/*     */     } 
/* 357 */     return this;
/*     */   }
/*     */   
/*     */   private void packImageData(int ix, int iz) {
/* 361 */     this.image.data[iz * this.image.width + ix] = this.outColor.pack();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float shadeFromHeights(int blockPixelX, int blockPixelZ, int blockPixelWidth, int blockPixelHeight, short height, short north, short south, short west, short east, short northWest, short northEast, short southWest, short southEast) {
/* 373 */     float u = (blockPixelX + 0.5F) / blockPixelWidth;
/* 374 */     float v = (blockPixelZ + 0.5F) / blockPixelHeight;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 379 */     float ud = (u + v) / 2.0F;
/* 380 */     float vd = (1.0F - u + v) / 2.0F;
/*     */ 
/*     */ 
/*     */     
/* 384 */     float dhdx1 = (height - west) * (1.0F - u) + (east - height) * u;
/* 385 */     float dhdz1 = (height - north) * (1.0F - v) + (south - height) * v;
/*     */     
/* 387 */     float dhdx2 = (height - northWest) * (1.0F - ud) + (southEast - height) * ud;
/* 388 */     float dhdz2 = (height - northEast) * (1.0F - vd) + (southWest - height) * vd;
/*     */     
/* 390 */     float dhdx = dhdx1 * 2.0F + dhdx2;
/* 391 */     float dhdz = dhdz1 * 2.0F + dhdz2;
/*     */     
/* 393 */     float dy = 3.0F;
/*     */ 
/*     */     
/* 396 */     float nx = dhdx, ny = dy, nz = dhdz;
/* 397 */     float invS = 1.0F / (float)Math.sqrt((nx * nx + ny * ny + nz * nz));
/* 398 */     nx *= invS; ny *= invS; nz *= invS;
/*     */ 
/*     */     
/* 401 */     float lx = -0.2F, ly = 0.8F, lz = 0.5F;
/* 402 */     float invL = 1.0F / (float)Math.sqrt((lx * lx + ly * ly + lz * lz));
/* 403 */     lx *= invL; ly *= invL; lz *= invL;
/*     */     
/* 405 */     float lambert = Math.max(0.0F, nx * lx + ny * ly + nz * lz);
/* 406 */     float ambient = 0.4F, diffuse = 0.6F;
/* 407 */     return ambient + diffuse * lambert;
/*     */   }
/*     */   
/*     */   private static void getBlockColor(int blockId, int biomeTintColor, @Nonnull Color outColor) {
/* 411 */     BlockType block = (BlockType)BlockType.getAssetMap().getAsset(blockId);
/*     */     
/* 413 */     int biomeTintR = biomeTintColor >> 16 & 0xFF;
/* 414 */     int biomeTintG = biomeTintColor >> 8 & 0xFF;
/* 415 */     int biomeTintB = biomeTintColor >> 0 & 0xFF;
/*     */     
/* 417 */     com.hypixel.hytale.protocol.Color[] tintUp = block.getTintUp();
/* 418 */     boolean hasTint = (tintUp != null && tintUp.length > 0);
/* 419 */     int selfTintR = hasTint ? ((tintUp[0]).red & 0xFF) : 255;
/* 420 */     int selfTintG = hasTint ? ((tintUp[0]).green & 0xFF) : 255;
/* 421 */     int selfTintB = hasTint ? ((tintUp[0]).blue & 0xFF) : 255;
/*     */     
/* 423 */     float biomeTintMultiplier = block.getBiomeTintUp() / 100.0F;
/*     */     
/* 425 */     int tintColorR = (int)(selfTintR + (biomeTintR - selfTintR) * biomeTintMultiplier);
/* 426 */     int tintColorG = (int)(selfTintG + (biomeTintG - selfTintG) * biomeTintMultiplier);
/* 427 */     int tintColorB = (int)(selfTintB + (biomeTintB - selfTintB) * biomeTintMultiplier);
/*     */ 
/*     */     
/* 430 */     com.hypixel.hytale.protocol.Color particleColor = block.getParticleColor();
/* 431 */     if (particleColor != null && biomeTintMultiplier < 1.0F) {
/* 432 */       tintColorR = tintColorR * (particleColor.red & 0xFF) / 255;
/* 433 */       tintColorG = tintColorG * (particleColor.green & 0xFF) / 255;
/* 434 */       tintColorB = tintColorB * (particleColor.blue & 0xFF) / 255;
/*     */     } 
/*     */ 
/*     */     
/* 438 */     outColor.r = tintColorR & 0xFF;
/* 439 */     outColor.g = tintColorG & 0xFF;
/* 440 */     outColor.b = tintColorB & 0xFF;
/* 441 */     outColor.a = 255;
/*     */   }
/*     */   
/*     */   private static void getFluidColor(int fluidId, int environmentId, int fluidDepth, @Nonnull Color outColor) {
/* 445 */     int tintColorR = 255;
/* 446 */     int tintColorG = 255;
/* 447 */     int tintColorB = 255;
/*     */     
/* 449 */     Environment environment = (Environment)Environment.getAssetMap().getAsset(environmentId);
/* 450 */     com.hypixel.hytale.protocol.Color waterTint = environment.getWaterTint();
/* 451 */     if (waterTint != null) {
/* 452 */       tintColorR = tintColorR * (waterTint.red & 0xFF) / 255;
/* 453 */       tintColorG = tintColorG * (waterTint.green & 0xFF) / 255;
/* 454 */       tintColorB = tintColorB * (waterTint.blue & 0xFF) / 255;
/*     */     } 
/*     */     
/* 457 */     Fluid fluid = (Fluid)Fluid.getAssetMap().getAsset(fluidId);
/* 458 */     com.hypixel.hytale.protocol.Color partcileColor = fluid.getParticleColor();
/* 459 */     if (partcileColor != null) {
/* 460 */       tintColorR = tintColorR * (partcileColor.red & 0xFF) / 255;
/* 461 */       tintColorG = tintColorG * (partcileColor.green & 0xFF) / 255;
/* 462 */       tintColorB = tintColorB * (partcileColor.blue & 0xFF) / 255;
/*     */     } 
/*     */ 
/*     */     
/* 466 */     float depthMultiplier = Math.min(1.0F, 1.0F / fluidDepth);
/* 467 */     outColor.r = (int)(tintColorR + ((outColor.r & 0xFF) - tintColorR) * depthMultiplier) & 0xFF;
/* 468 */     outColor.g = (int)(tintColorG + ((outColor.g & 0xFF) - tintColorG) * depthMultiplier) & 0xFF;
/* 469 */     outColor.b = (int)(tintColorB + ((outColor.b & 0xFF) - tintColorB) * depthMultiplier) & 0xFF;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CompletableFuture<ImageBuilder> build(long index, int imageWidth, int imageHeight, World world) {
/* 474 */     return CompletableFuture.<ImageBuilder>completedFuture(new ImageBuilder(index, imageWidth, imageHeight, world))
/* 475 */       .thenCompose(ImageBuilder::fetchChunk)
/* 476 */       .thenCompose(builder -> (builder != null) ? builder.sampleNeighborsSync() : CompletableFuture.completedFuture(null))
/* 477 */       .thenApplyAsync(builder -> (builder != null) ? builder.generateImageAsync() : null);
/*     */   }
/*     */   private static class Color { public int r; public int g;
/*     */     public int b;
/*     */     public int a;
/*     */     
/*     */     public int pack() {
/* 484 */       return (this.r & 0xFF) << 24 | (this.g & 0xFF) << 16 | (this.b & 0xFF) << 8 | this.a & 0xFF;
/*     */     }
/*     */     
/*     */     public void multiply(float value) {
/* 488 */       this.r = Math.min(255, Math.max(0, (int)(this.r * value)));
/* 489 */       this.g = Math.min(255, Math.max(0, (int)(this.g * value)));
/* 490 */       this.b = Math.min(255, Math.max(0, (int)(this.b * value)));
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldmap\provider\chunk\ImageBuilder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */