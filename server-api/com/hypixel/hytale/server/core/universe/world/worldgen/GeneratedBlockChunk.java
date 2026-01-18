/*     */ package com.hypixel.hytale.server.core.universe.world.worldgen;
/*     */ 
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.protocol.Opacity;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.environment.EnvironmentChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.palette.IntBytePalette;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.palette.ShortBytePalette;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GeneratedBlockChunk
/*     */ {
/*  24 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   protected long index;
/*     */   
/*     */   protected int x;
/*     */   protected int z;
/*     */   protected final IntBytePalette tint;
/*     */   protected final EnvironmentChunk environments;
/*     */   protected final GeneratedChunkSection[] chunkSections;
/*     */   
/*     */   public GeneratedBlockChunk() {
/*  35 */     this(0L, 0, 0);
/*     */   }
/*     */   
/*     */   public GeneratedBlockChunk(long index, int x, int z) {
/*  39 */     this(index, x, z, new IntBytePalette(), new EnvironmentChunk(), new GeneratedChunkSection[10]);
/*     */   }
/*     */   
/*     */   public GeneratedBlockChunk(long index, int x, int z, IntBytePalette tint, EnvironmentChunk environments, GeneratedChunkSection[] chunkSections) {
/*  43 */     this.index = index;
/*  44 */     this.x = x;
/*  45 */     this.z = z;
/*  46 */     this.tint = tint;
/*  47 */     this.environments = environments;
/*  48 */     this.chunkSections = chunkSections;
/*     */   }
/*     */   
/*     */   public long getIndex() {
/*  52 */     return this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX() {
/*  59 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZ() {
/*  66 */     return this.z;
/*     */   }
/*     */   
/*     */   public void setCoordinates(long index, int x, int z) {
/*  70 */     this.index = index;
/*  71 */     this.x = x;
/*  72 */     this.z = z;
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
/*     */   public int getHeight(int x, int z) {
/*  85 */     int y = 320;
/*  86 */     while (--y > 0) {
/*  87 */       GeneratedChunkSection section = getSection(y);
/*  88 */       if (section == null) {
/*  89 */         y = ChunkUtil.indexSection(y) * 32;
/*  90 */         if (y == 0)
/*     */           break;  continue;
/*  92 */       }  int blockId = section.getBlock(x, y, z);
/*  93 */       BlockType type = (BlockType)BlockType.getAssetMap().getAsset(blockId);
/*  94 */       if (blockId != 0 && type != null && type.getOpacity() != Opacity.Transparent) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/*  99 */     return y;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ShortBytePalette generateHeight() {
/* 104 */     ShortBytePalette height = new ShortBytePalette();
/* 105 */     for (int x = 0; x < 32; x++) {
/* 106 */       for (int z = 0; z < 32; z++) {
/* 107 */         height.set(x, z, (short)getHeight(x, z));
/*     */       }
/*     */     } 
/* 110 */     return height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public GeneratedChunkSection getSection(int y) {
/* 121 */     int index = ChunkUtil.indexSection(y);
/* 122 */     if (index >= 0 && index < this.chunkSections.length) {
/* 123 */       return this.chunkSections[index];
/*     */     }
/* 125 */     return null;
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
/*     */   public int getTint(int x, int z) {
/* 137 */     return this.tint.get(x, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTint(int x, int z, int tint) {
/* 148 */     this.tint.set(x, z, tint);
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
/*     */   public void setEnvironment(int x, int y, int z, int environment) {
/* 160 */     this.environments.set(x, y, z, environment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnvironmentColumn(int x, int z, int environment) {
/* 171 */     this.environments.setColumn(x, z, environment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnvironment(int x, int y, int z) {
/* 182 */     return this.environments.get(x, y, z);
/*     */   }
/*     */   
/*     */   public int getRotationIndex(int x, int y, int z) {
/* 186 */     if (y < 0 || y >= 320) return 0;
/*     */     
/* 188 */     GeneratedChunkSection section = getSection(y);
/* 189 */     if (section == null) {
/* 190 */       return 0;
/*     */     }
/* 192 */     return section.getRotationIndex(x, y, z);
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
/*     */   public int getBlock(int x, int y, int z) {
/* 205 */     if (y < 0 || y >= 320) return 0;
/*     */     
/* 207 */     GeneratedChunkSection section = getSection(y);
/* 208 */     if (section == null) {
/* 209 */       return 0;
/*     */     }
/* 211 */     return section.getBlock(x, y, z);
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
/*     */   public void setBlock(int x, int y, int z, int blockId, int rotation, int filler) {
/* 224 */     if (y < 0 || y >= 320) {
/* 225 */       ((HytaleLogger.Api)LOGGER.at(Level.INFO).withCause(new Exception())).log("Failed to set block %d, %d, %d to %d because it is outside the world bounds", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z), Integer.valueOf(blockId));
/*     */       return;
/*     */     } 
/* 228 */     GeneratedChunkSection section = getSection(y);
/*     */     
/* 230 */     int sectionIndex = ChunkUtil.indexSection(y);
/* 231 */     if (section == null) {
/*     */       
/* 233 */       if (blockId == 0) {
/*     */         return;
/*     */       }
/*     */       
/* 237 */       section = initialize(sectionIndex);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 242 */     section.setBlock(x, y, z, blockId, rotation, filler);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private GeneratedChunkSection initialize(int section) {
/* 247 */     this.chunkSections[section] = new GeneratedChunkSection(); return new GeneratedChunkSection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeSection(int y) {
/* 256 */     int index = ChunkUtil.indexSection(y);
/* 257 */     if (index >= 0 && index < this.chunkSections.length) {
/* 258 */       this.chunkSections[index] = null;
/*     */     }
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BlockChunk toBlockChunk(Holder<ChunkStore>[] sectionHolders) {
/* 264 */     for (int y = 0; y < this.chunkSections.length; y++) {
/* 265 */       GeneratedChunkSection chunkSection = this.chunkSections[y];
/* 266 */       if (chunkSection != null) {
/* 267 */         sectionHolders[y].putComponent(BlockSection.getComponentType(), (Component)chunkSection.toChunkSection());
/*     */       }
/*     */     } 
/* 270 */     ShortBytePalette height = generateHeight();
/* 271 */     this.environments.trim();
/* 272 */     return new BlockChunk(this.x, this.z, height, this.tint, this.environments);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldgen\GeneratedBlockChunk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */