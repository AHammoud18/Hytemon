/*     */ package com.hypixel.hytale.math.util;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkUtil
/*     */ {
/*     */   public static final int BITS = 5;
/*     */   public static final int SIZE = 32;
/*     */   public static final int SIZE_2 = 1024;
/*     */   public static final int SIZE_MINUS_1 = 31;
/*     */   public static final int SIZE_MASK = 31;
/*     */   public static final int SIZE_COLUMNS = 1024;
/*     */   public static final int SIZE_COLUMNS_MASK = 1023;
/*     */   public static final int SIZE_BLOCKS = 32768;
/*     */   public static final int BITS2 = 10;
/*     */   public static final int NON_CHUNK_MASK = -32;
/*     */   public static final int HEIGHT_SECTIONS = 10;
/*     */   public static final int HEIGHT = 320;
/*     */   public static final int HEIGHT_MINUS_1 = 319;
/*  27 */   public static final int HEIGHT_MASK = (Integer.highestOneBit(320) << 1) - 1;
/*     */   
/*     */   public static final int SIZE_BLOCKS_COLUMN = 327680;
/*  30 */   public static final long NOT_FOUND = indexChunk(-2147483648, -2147483648);
/*     */   
/*     */   public static final int MIN_Y = 0;
/*     */   
/*     */   public static final int MIN_ENTITY_Y = -32;
/*     */   
/*     */   public static final int MIN_SECTION = 0;
/*     */ 
/*     */   
/*     */   public static byte[] shortToByteArray(@Nonnull short[] data) {
/*  40 */     ByteBuffer byteBuffer = ByteBuffer.allocate(data.length * 2).order(ByteOrder.LITTLE_ENDIAN);
/*  41 */     byteBuffer.asShortBuffer().put(data);
/*  42 */     return byteBuffer.array();
/*     */   }
/*     */   
/*     */   public static byte[] intToByteArray(@Nonnull int[] data) {
/*  46 */     ByteBuffer byteBuffer = ByteBuffer.allocate(data.length * 4).order(ByteOrder.LITTLE_ENDIAN);
/*  47 */     byteBuffer.asIntBuffer().put(data);
/*  48 */     return byteBuffer.array();
/*     */   }
/*     */   
/*     */   public static int indexColumn(int x, int z) {
/*  52 */     return (z & 0x1F) << 5 | x & 0x1F;
/*     */   }
/*     */   
/*     */   public static int xFromColumn(int index) {
/*  56 */     return index & 0x1F;
/*     */   }
/*     */   
/*     */   public static int zFromColumn(int index) {
/*  60 */     return index >> 5 & 0x1F;
/*     */   }
/*     */   
/*     */   public static int indexSection(int y) {
/*  64 */     return y >> 5;
/*     */   }
/*     */   
/*     */   public static int indexBlockFromColumn(int column, int y) {
/*  68 */     return (y & 0x1F) << 10 | column & 0x3FF;
/*     */   }
/*     */   
/*     */   public static int indexBlock(int x, int y, int z) {
/*  72 */     return (y & 0x1F) << 10 | (z & 0x1F) << 5 | x & 0x1F;
/*     */   }
/*     */   
/*     */   public static int xFromIndex(int index) {
/*  76 */     return index & 0x1F;
/*     */   }
/*     */   
/*     */   public static int yFromIndex(int index) {
/*  80 */     return index >> 10 & 0x1F;
/*     */   }
/*     */   
/*     */   public static int zFromIndex(int index) {
/*  84 */     return index >> 5 & 0x1F;
/*     */   }
/*     */   
/*     */   public static int indexBlockInColumn(int x, int y, int z) {
/*  88 */     return (y & HEIGHT_MASK) << 10 | (z & 0x1F) << 5 | x & 0x1F;
/*     */   }
/*     */   
/*     */   public static int indexBlockInColumnFromColumn(int column, int y) {
/*  92 */     return (y & HEIGHT_MASK) << 10 | column & 0x3FF;
/*     */   }
/*     */   
/*     */   public static int xFromBlockInColumn(int index) {
/*  96 */     return index & 0x1F;
/*     */   }
/*     */   
/*     */   public static int yFromBlockInColumn(int index) {
/* 100 */     return index >> 10 & HEIGHT_MASK;
/*     */   }
/*     */   
/*     */   public static int zFromBlockInColumn(int index) {
/* 104 */     return index >> 5 & 0x1F;
/*     */   }
/*     */   
/*     */   public static int localCoordinate(long v) {
/* 108 */     return (int)(v & 0x1FL);
/*     */   }
/*     */   
/*     */   public static int chunkCoordinate(double block) {
/* 112 */     return MathUtil.floor(block) >> 5;
/*     */   }
/*     */   
/*     */   public static int chunkCoordinate(int block) {
/* 116 */     return block >> 5;
/*     */   }
/*     */   
/*     */   public static int chunkCoordinate(long block) {
/* 120 */     return (int)(block >> 5L);
/*     */   }
/*     */   
/*     */   public static int minBlock(int index) {
/* 124 */     return index << 5;
/*     */   }
/*     */   
/*     */   public static int maxBlock(int index) {
/* 128 */     return (index << 5) + 31;
/*     */   }
/*     */   
/*     */   public static boolean isWithinLocalChunk(int x, int z) {
/* 132 */     return (x >= 0 && z >= 0 && x < 32 && z < 32);
/*     */   }
/*     */   
/*     */   public static boolean isBorderBlock(int x, int z) {
/* 136 */     return (x == 0 || z == 0 || x == 31 || z == 31);
/*     */   }
/*     */   
/*     */   public static boolean isBorderBlockGlobal(int x, int z) {
/* 140 */     x &= 0x1F;
/* 141 */     z &= 0x1F;
/* 142 */     return isBorderBlock(x, z);
/*     */   }
/*     */   
/*     */   public static boolean isInsideChunk(int chunkX, int chunkZ, int x, int z) {
/* 146 */     return (chunkCoordinate(x) == chunkX && chunkCoordinate(z) == chunkZ);
/*     */   }
/*     */   
/*     */   public static boolean isSameChunk(int x0, int z0, int x1, int z1) {
/* 150 */     return (chunkCoordinate(x0) == chunkCoordinate(x1) && chunkCoordinate(z0) == chunkCoordinate(z1));
/*     */   }
/*     */   
/*     */   public static boolean isSameChunkSection(int x0, int y0, int z0, int x1, int y1, int z1) {
/* 154 */     return (chunkCoordinate(x0) == chunkCoordinate(x1) && 
/* 155 */       chunkCoordinate(y0) == chunkCoordinate(y1) && 
/* 156 */       chunkCoordinate(z0) == chunkCoordinate(z1));
/*     */   }
/*     */   
/*     */   public static boolean isInsideChunkRelative(int x, int z) {
/* 160 */     return ((x & 0x1F) == x && (z & 0x1F) == z);
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
/*     */   public static int xOfChunkIndex(long index) {
/* 173 */     return (int)(index >> 32L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int zOfChunkIndex(long index) {
/* 184 */     return (int)index;
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
/*     */   public static long indexChunk(int x, int z) {
/* 198 */     return x << 32L | z & 0xFFFFFFFFL;
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
/*     */   public static long indexChunkFromBlock(int blockX, int blockZ) {
/* 210 */     return indexChunk(chunkCoordinate(blockX), chunkCoordinate(blockZ));
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
/*     */   public static long indexChunkFromBlock(double blockX, double blockZ) {
/* 223 */     return indexChunkFromBlock(MathUtil.floor(blockX), MathUtil.floor(blockZ));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int worldCoordFromLocalCoord(int chunkCoord, int localCoord) {
/* 233 */     return chunkCoord << 5 | localCoord;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\mat\\util\ChunkUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */