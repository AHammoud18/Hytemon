/*    */ package com.hypixel.hytale.math;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public final class Vec3f
/*    */ {
/*    */   public static final int SIZE = 12;
/*    */   public float x;
/*    */   public float y;
/*    */   public float z;
/*    */   
/*    */   public Vec3f(float x, float y, float z) {
/* 15 */     this.x = x;
/* 16 */     this.y = y;
/* 17 */     this.z = z;
/*    */   }
/*    */   
/*    */   public Vec3f() {}
/*    */   
/*    */   @Nonnull
/*    */   public static Vec3f deserialize(@Nonnull ByteBuf buf, int offset) {
/* 24 */     return new Vec3f(
/* 25 */         Float.intBitsToFloat(buf.getIntLE(offset)), 
/* 26 */         Float.intBitsToFloat(buf.getIntLE(offset + 4)), 
/* 27 */         Float.intBitsToFloat(buf.getIntLE(offset + 8)));
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 32 */     buf.writeIntLE(Float.floatToRawIntBits(this.x));
/* 33 */     buf.writeIntLE(Float.floatToRawIntBits(this.y));
/* 34 */     buf.writeIntLE(Float.floatToRawIntBits(this.z));
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 39 */     return "Vec3f(" + this.x + ", " + this.y + ", " + this.z + ")";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\Vec3f.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */