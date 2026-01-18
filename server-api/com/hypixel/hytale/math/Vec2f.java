/*    */ package com.hypixel.hytale.math;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Vec2f
/*    */ {
/*    */   public static final int SIZE = 8;
/*    */   public float x;
/*    */   public float y;
/*    */   
/*    */   public Vec2f(float x, float y) {
/* 15 */     this.x = x;
/* 16 */     this.y = y;
/*    */   }
/*    */   
/*    */   public Vec2f() {}
/*    */   
/*    */   @Nonnull
/*    */   public static Vec2f deserialize(@Nonnull ByteBuf buf, int offset) {
/* 23 */     return new Vec2f(
/* 24 */         Float.intBitsToFloat(buf.getIntLE(offset)), 
/* 25 */         Float.intBitsToFloat(buf.getIntLE(offset + 4)));
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 30 */     buf.writeIntLE(Float.floatToRawIntBits(this.x));
/* 31 */     buf.writeIntLE(Float.floatToRawIntBits(this.y));
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 36 */     return "Vec2f(" + this.x + ", " + this.y + ")";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\Vec2f.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */