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
/*    */ public class Range
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 8;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 8;
/*    */   public static final int MAX_SIZE = 8;
/*    */   public int min;
/*    */   public int max;
/*    */   
/*    */   public Range() {}
/*    */   
/*    */   public Range(int min, int max) {
/* 27 */     this.min = min;
/* 28 */     this.max = max;
/*    */   }
/*    */   
/*    */   public Range(@Nonnull Range other) {
/* 32 */     this.min = other.min;
/* 33 */     this.max = other.max;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Range deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     Range obj = new Range();
/*    */     
/* 40 */     obj.min = buf.getIntLE(offset + 0);
/* 41 */     obj.max = buf.getIntLE(offset + 4);
/*    */ 
/*    */     
/* 44 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 48 */     return 8;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 53 */     buf.writeIntLE(this.min);
/* 54 */     buf.writeIntLE(this.max);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 60 */     return 8;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 64 */     if (buffer.readableBytes() - offset < 8) {
/* 65 */       return ValidationResult.error("Buffer too small: expected at least 8 bytes");
/*    */     }
/*    */ 
/*    */     
/* 69 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public Range clone() {
/* 73 */     Range copy = new Range();
/* 74 */     copy.min = this.min;
/* 75 */     copy.max = this.max;
/* 76 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     Range other;
/* 82 */     if (this == obj) return true; 
/* 83 */     if (obj instanceof Range) { other = (Range)obj; } else { return false; }
/* 84 */      return (this.min == other.min && this.max == other.max);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return Objects.hash(new Object[] { Integer.valueOf(this.min), Integer.valueOf(this.max) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Range.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */