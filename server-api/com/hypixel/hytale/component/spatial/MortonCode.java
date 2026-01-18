/*    */ package com.hypixel.hytale.component.spatial;
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
/*    */ public class MortonCode
/*    */ {
/*    */   private static final int BITS_PER_AXIS = 21;
/*    */   private static final long MAX_COORD = 2097151L;
/*    */   
/*    */   public static long encode(double x, double y, double z, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
/* 37 */     double nx = (x - minX) / (maxX - minX);
/* 38 */     double ny = (y - minY) / (maxY - minY);
/* 39 */     double nz = (z - minZ) / (maxZ - minZ);
/*    */ 
/*    */     
/* 42 */     long ix = Math.min(Math.max((long)(nx * 2097151.0D), 0L), 2097151L);
/* 43 */     long iy = Math.min(Math.max((long)(ny * 2097151.0D), 0L), 2097151L);
/* 44 */     long iz = Math.min(Math.max((long)(nz * 2097151.0D), 0L), 2097151L);
/*    */     
/* 46 */     return interleaveBits(ix, iy, iz);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static long interleaveBits(long x, long y, long z) {
/* 58 */     x = expandBits(x);
/* 59 */     y = expandBits(y);
/* 60 */     z = expandBits(z);
/* 61 */     return x | y << 1L | z << 2L;
/*    */   }
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
/*    */   private static long expandBits(long value) {
/* 75 */     value &= 0x1FFFFFL;
/* 76 */     value = (value | value << 32L) & 0x1F00000000FFFFL;
/* 77 */     value = (value | value << 16L) & 0x1F0000FF0000FFL;
/* 78 */     value = (value | value << 8L) & 0x100F00F00F00F00FL;
/* 79 */     value = (value | value << 4L) & 0x10C30C30C30C30CL;
/* 80 */     value = (value | value << 2L) & 0x1249249249249249L;
/* 81 */     return value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\spatial\MortonCode.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */