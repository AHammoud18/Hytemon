/*    */ package com.hypixel.hytale.math.block;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
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
/*    */ public class BlockUtil
/*    */ {
/*    */   public static final float RADIUS_ADJUST = 0.41F;
/*    */   public static final long BITS_Y = 9L;
/*    */   public static final long MAX_Y = 512L;
/*    */   public static final long MIN_Y = -513L;
/*    */   public static final long Y_INVERT = -512L;
/*    */   public static final long Y_MASK = 511L;
/*    */   public static final long BITS_PER_DIRECTION = 26L;
/*    */   public static final long MAX = 67108864L;
/*    */   public static final long MIN = -67108865L;
/*    */   public static final long DIRECTION_INVERT = -67108864L;
/*    */   public static final long DIRECTION_MASK = 67108863L;
/*    */   
/*    */   public static long pack(@Nonnull Vector3i val) {
/* 47 */     return pack(val.x, val.y, val.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public static long pack(int x, int y, int z) {
/* 52 */     if (y <= -513L || y >= 512L) throw new IllegalArgumentException(String.valueOf(y)); 
/* 53 */     if (x <= -67108865L || x >= 67108864L) throw new IllegalArgumentException(String.valueOf(x)); 
/* 54 */     if (z <= -67108865L || z >= 67108864L) throw new IllegalArgumentException(String.valueOf(z));
/*    */     
/* 56 */     long l = (y & 0x1FFL) << 54L | (z & 0x3FFFFFFL) << 27L | x & 0x3FFFFFFL;
/* 57 */     if (y < 0) l |= Long.MIN_VALUE; 
/* 58 */     if (z < 0) l |= 0x20000000000000L; 
/* 59 */     if (x < 0) l |= 0x4000000L; 
/* 60 */     return l;
/*    */   }
/*    */   
/*    */   public static int unpackX(long packed) {
/* 64 */     int i = (int)(packed & 0x3FFFFFFL);
/* 65 */     if ((packed & 0x4000000L) != 0L) i = (int)(i | 0xFFFFFFFFFC000000L); 
/* 66 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int unpackY(long packed) {
/* 71 */     int i = (int)(packed >> 54L & 0x1FFL);
/* 72 */     if ((packed & Long.MIN_VALUE) != 0L) i = (int)(i | 0xFFFFFFFFFFFFFE00L); 
/* 73 */     return i;
/*    */   }
/*    */   
/*    */   public static int unpackZ(long packed) {
/* 77 */     int i = (int)(packed >> 27L & 0x3FFFFFFL);
/* 78 */     if ((packed & 0x20000000000000L) != 0L) i = (int)(i | 0xFFFFFFFFFC000000L); 
/* 79 */     return i;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Vector3i unpack(long packed) {
/* 84 */     return new Vector3i(unpackX(packed), unpackY(packed), unpackZ(packed));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\block\BlockUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */