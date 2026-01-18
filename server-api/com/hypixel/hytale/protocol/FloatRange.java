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
/*    */ public class FloatRange
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 8;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 8;
/*    */   public static final int MAX_SIZE = 8;
/*    */   public float inclusiveMin;
/*    */   public float inclusiveMax;
/*    */   
/*    */   public FloatRange() {}
/*    */   
/*    */   public FloatRange(float inclusiveMin, float inclusiveMax) {
/* 27 */     this.inclusiveMin = inclusiveMin;
/* 28 */     this.inclusiveMax = inclusiveMax;
/*    */   }
/*    */   
/*    */   public FloatRange(@Nonnull FloatRange other) {
/* 32 */     this.inclusiveMin = other.inclusiveMin;
/* 33 */     this.inclusiveMax = other.inclusiveMax;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static FloatRange deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     FloatRange obj = new FloatRange();
/*    */     
/* 40 */     obj.inclusiveMin = buf.getFloatLE(offset + 0);
/* 41 */     obj.inclusiveMax = buf.getFloatLE(offset + 4);
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
/* 53 */     buf.writeFloatLE(this.inclusiveMin);
/* 54 */     buf.writeFloatLE(this.inclusiveMax);
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
/*    */   public FloatRange clone() {
/* 73 */     FloatRange copy = new FloatRange();
/* 74 */     copy.inclusiveMin = this.inclusiveMin;
/* 75 */     copy.inclusiveMax = this.inclusiveMax;
/* 76 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     FloatRange other;
/* 82 */     if (this == obj) return true; 
/* 83 */     if (obj instanceof FloatRange) { other = (FloatRange)obj; } else { return false; }
/* 84 */      return (this.inclusiveMin == other.inclusiveMin && this.inclusiveMax == other.inclusiveMax);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return Objects.hash(new Object[] { Float.valueOf(this.inclusiveMin), Float.valueOf(this.inclusiveMax) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\FloatRange.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */