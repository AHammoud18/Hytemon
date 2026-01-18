/*    */ package com.hypixel.hytale.server.npc.blackboard.view;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BlockRegionView<ViewType extends IBlackboardView<ViewType>>
/*    */   implements IBlackboardView<ViewType>
/*    */ {
/*    */   public static final int BITS = 7;
/*    */   public static final int SIZE = 128;
/*    */   public static final int SIZE_MASK = 127;
/*    */   public static final int BITS2 = 14;
/*    */   
/*    */   public static int toRegionalBlackboardCoordinate(int pos) {
/* 20 */     return pos >> 7;
/*    */   }
/*    */   
/*    */   public static int toWorldCoordinate(int pos) {
/* 24 */     return pos << 7;
/*    */   }
/*    */   
/*    */   public static int chunkToRegionalBlackboardCoordinate(int pos) {
/* 28 */     return pos >> 2;
/*    */   }
/*    */   
/*    */   public static long indexView(int x, int z) {
/* 32 */     return ChunkUtil.indexChunk(x, z);
/*    */   }
/*    */   
/*    */   public static int indexSection(int y) {
/* 36 */     return y >> 7;
/*    */   }
/*    */   
/*    */   public static int xOfViewIndex(long index) {
/* 40 */     return ChunkUtil.xOfChunkIndex(index);
/*    */   }
/*    */   
/*    */   public static int zOfViewIndex(long index) {
/* 44 */     return ChunkUtil.zOfChunkIndex(index);
/*    */   }
/*    */   
/*    */   public static long indexViewFromChunkCoordinates(int x, int z) {
/* 48 */     return indexView(toRegionalBlackboardCoordinate(x), toRegionalBlackboardCoordinate(z));
/*    */   }
/*    */   
/*    */   public static long indexViewFromWorldPosition(@Nonnull Vector3d pos) {
/* 52 */     int blackboardX = toRegionalBlackboardCoordinate(MathUtil.floor(pos.getX()));
/* 53 */     int blackboardZ = toRegionalBlackboardCoordinate(MathUtil.floor(pos.getZ()));
/* 54 */     return indexView(blackboardX, blackboardZ);
/*    */   }
/*    */   
/*    */   public static int indexBlock(int x, int y, int z) {
/* 58 */     return (y & 0x7F) << 14 | (z & 0x7F) << 7 | x & 0x7F;
/*    */   }
/*    */   
/*    */   public static int xFromIndex(int index) {
/* 62 */     return index & 0x7F;
/*    */   }
/*    */   
/*    */   public static int yFromIndex(int index) {
/* 66 */     return index >> 14 & 0x7F;
/*    */   }
/*    */   
/*    */   public static int zFromIndex(int index) {
/* 70 */     return index >> 7 & 0x7F;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\blackboard\view\BlockRegionView.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */