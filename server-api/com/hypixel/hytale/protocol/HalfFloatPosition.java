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
/*    */ public class HalfFloatPosition
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 6;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 6;
/*    */   public static final int MAX_SIZE = 6;
/*    */   public short x;
/*    */   public short y;
/*    */   public short z;
/*    */   
/*    */   public HalfFloatPosition() {}
/*    */   
/*    */   public HalfFloatPosition(short x, short y, short z) {
/* 28 */     this.x = x;
/* 29 */     this.y = y;
/* 30 */     this.z = z;
/*    */   }
/*    */   
/*    */   public HalfFloatPosition(@Nonnull HalfFloatPosition other) {
/* 34 */     this.x = other.x;
/* 35 */     this.y = other.y;
/* 36 */     this.z = other.z;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static HalfFloatPosition deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     HalfFloatPosition obj = new HalfFloatPosition();
/*    */     
/* 43 */     obj.x = buf.getShortLE(offset + 0);
/* 44 */     obj.y = buf.getShortLE(offset + 2);
/* 45 */     obj.z = buf.getShortLE(offset + 4);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 6;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 57 */     buf.writeShortLE(this.x);
/* 58 */     buf.writeShortLE(this.y);
/* 59 */     buf.writeShortLE(this.z);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 65 */     return 6;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 69 */     if (buffer.readableBytes() - offset < 6) {
/* 70 */       return ValidationResult.error("Buffer too small: expected at least 6 bytes");
/*    */     }
/*    */ 
/*    */     
/* 74 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public HalfFloatPosition clone() {
/* 78 */     HalfFloatPosition copy = new HalfFloatPosition();
/* 79 */     copy.x = this.x;
/* 80 */     copy.y = this.y;
/* 81 */     copy.z = this.z;
/* 82 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     HalfFloatPosition other;
/* 88 */     if (this == obj) return true; 
/* 89 */     if (obj instanceof HalfFloatPosition) { other = (HalfFloatPosition)obj; } else { return false; }
/* 90 */      return (this.x == other.x && this.y == other.y && this.z == other.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return Objects.hash(new Object[] { Short.valueOf(this.x), Short.valueOf(this.y), Short.valueOf(this.z) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\HalfFloatPosition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */