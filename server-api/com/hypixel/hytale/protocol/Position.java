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
/*    */ public class Position
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 24;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 24;
/*    */   public static final int MAX_SIZE = 24;
/*    */   public double x;
/*    */   public double y;
/*    */   public double z;
/*    */   
/*    */   public Position() {}
/*    */   
/*    */   public Position(double x, double y, double z) {
/* 28 */     this.x = x;
/* 29 */     this.y = y;
/* 30 */     this.z = z;
/*    */   }
/*    */   
/*    */   public Position(@Nonnull Position other) {
/* 34 */     this.x = other.x;
/* 35 */     this.y = other.y;
/* 36 */     this.z = other.z;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Position deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     Position obj = new Position();
/*    */     
/* 43 */     obj.x = buf.getDoubleLE(offset + 0);
/* 44 */     obj.y = buf.getDoubleLE(offset + 8);
/* 45 */     obj.z = buf.getDoubleLE(offset + 16);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 24;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 57 */     buf.writeDoubleLE(this.x);
/* 58 */     buf.writeDoubleLE(this.y);
/* 59 */     buf.writeDoubleLE(this.z);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 65 */     return 24;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 69 */     if (buffer.readableBytes() - offset < 24) {
/* 70 */       return ValidationResult.error("Buffer too small: expected at least 24 bytes");
/*    */     }
/*    */ 
/*    */     
/* 74 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public Position clone() {
/* 78 */     Position copy = new Position();
/* 79 */     copy.x = this.x;
/* 80 */     copy.y = this.y;
/* 81 */     copy.z = this.z;
/* 82 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     Position other;
/* 88 */     if (this == obj) return true; 
/* 89 */     if (obj instanceof Position) { other = (Position)obj; } else { return false; }
/* 90 */      return (this.x == other.x && this.y == other.y && this.z == other.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return Objects.hash(new Object[] { Double.valueOf(this.x), Double.valueOf(this.y), Double.valueOf(this.z) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Position.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */