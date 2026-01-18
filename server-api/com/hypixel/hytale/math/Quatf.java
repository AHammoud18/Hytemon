/*    */ package com.hypixel.hytale.math;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class Quatf
/*    */ {
/*    */   public static final int SIZE = 16;
/*    */   public final float x;
/*    */   public final float y;
/*    */   public final float z;
/*    */   public final float w;
/*    */   
/*    */   public Quatf(float x, float y, float z, float w) {
/* 16 */     this.x = x;
/* 17 */     this.y = y;
/* 18 */     this.z = z;
/* 19 */     this.w = w;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Quatf deserialize(@Nonnull ByteBuf buf, int offset) {
/* 24 */     return new Quatf(
/* 25 */         Float.intBitsToFloat(buf.getIntLE(offset)), 
/* 26 */         Float.intBitsToFloat(buf.getIntLE(offset + 4)), 
/* 27 */         Float.intBitsToFloat(buf.getIntLE(offset + 8)), 
/* 28 */         Float.intBitsToFloat(buf.getIntLE(offset + 12)));
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 33 */     buf.writeIntLE(Float.floatToRawIntBits(this.x));
/* 34 */     buf.writeIntLE(Float.floatToRawIntBits(this.y));
/* 35 */     buf.writeIntLE(Float.floatToRawIntBits(this.z));
/* 36 */     buf.writeIntLE(Float.floatToRawIntBits(this.w));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\Quatf.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */