/*    */ package com.hypixel.hytale.server.worldgen.chunk;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockPriorityChunk
/*    */ {
/*    */   public static final byte NO_CHANGE = -1;
/*    */   public static final byte NONE = 0;
/*    */   public static final byte FILLING = 1;
/*    */   public static final byte LAYER = 2;
/*    */   public static final byte COVER = 3;
/*    */   public static final byte WATER = 4;
/*    */   public static final byte CAVE_COVER = 5;
/*    */   public static final byte CAVE = 6;
/*    */   public static final byte CAVE_PREFAB = 7;
/*    */   public static final byte PREFAB_CAVE = 8;
/*    */   public static final byte PREFAB = 9;
/*    */   public static final byte EXCLUSIVE_MAX_PRIORITY = 32;
/*    */   public static final byte MASK = 31;
/*    */   public static final byte FLAG_MASK = -32;
/*    */   public static final byte FLAG_SUBMERGE = 32;
/*    */   @Nonnull
/* 39 */   private final byte[] blocks = new byte[327680];
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public BlockPriorityChunk reset() {
/* 44 */     Arrays.fill(this.blocks, (byte)0);
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte get(int x, int y, int z) {
/* 52 */     return (byte)(this.blocks[index(x, y, z)] & 0x1F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getRaw(int x, int y, int z) {
/* 59 */     return this.blocks[index(x, y, z)];
/*    */   }
/*    */   
/*    */   public void set(int x, int y, int z, byte type) {
/* 63 */     this.blocks[index(x, y, z)] = type;
/*    */   }
/*    */   
/*    */   private static int index(int x, int y, int z) {
/* 67 */     return ChunkUtil.indexBlockInColumn(x, y, z);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\chunk\BlockPriorityChunk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */