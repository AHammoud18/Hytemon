/*    */ package com.hypixel.hytale.protocol;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Direction
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 12;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 12;
/*    */   public static final int MAX_SIZE = 12;
/*    */   public float yaw;
/*    */   public float pitch;
/*    */   public float roll;
/*    */   
/*    */   public Direction() {}
/*    */   
/*    */   public Direction(float yaw, float pitch, float roll) {
/* 28 */     this.yaw = yaw;
/* 29 */     this.pitch = pitch;
/* 30 */     this.roll = roll;
/*    */   }
/*    */   
/*    */   public Direction(@Nonnull Direction other) {
/* 34 */     this.yaw = other.yaw;
/* 35 */     this.pitch = other.pitch;
/* 36 */     this.roll = other.roll;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Direction deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     Direction obj = new Direction();
/*    */     
/* 43 */     obj.yaw = buf.getFloatLE(offset + 0);
/* 44 */     obj.pitch = buf.getFloatLE(offset + 4);
/* 45 */     obj.roll = buf.getFloatLE(offset + 8);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 12;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 57 */     buf.writeFloatLE(this.yaw);
/* 58 */     buf.writeFloatLE(this.pitch);
/* 59 */     buf.writeFloatLE(this.roll);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 65 */     return 12;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 69 */     if (buffer.readableBytes() - offset < 12) {
/* 70 */       return ValidationResult.error("Buffer too small: expected at least 12 bytes");
/*    */     }
/*    */ 
/*    */     
/* 74 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public Direction clone() {
/* 78 */     Direction copy = new Direction();
/* 79 */     copy.yaw = this.yaw;
/* 80 */     copy.pitch = this.pitch;
/* 81 */     copy.roll = this.roll;
/* 82 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     Direction other;
/* 88 */     if (this == obj) return true; 
/* 89 */     if (obj instanceof Direction) { other = (Direction)obj; } else { return false; }
/* 90 */      return (this.yaw == other.yaw && this.pitch == other.pitch && this.roll == other.roll);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return Objects.hash(new Object[] { Float.valueOf(this.yaw), Float.valueOf(this.pitch), Float.valueOf(this.roll) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Direction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */