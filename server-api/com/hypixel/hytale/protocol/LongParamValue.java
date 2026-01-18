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
/*    */ public class LongParamValue
/*    */   extends ParamValue
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 8;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 8;
/*    */   public static final int MAX_SIZE = 8;
/*    */   public long value;
/*    */   
/*    */   public LongParamValue() {}
/*    */   
/*    */   public LongParamValue(long value) {
/* 26 */     this.value = value;
/*    */   }
/*    */   
/*    */   public LongParamValue(@Nonnull LongParamValue other) {
/* 30 */     this.value = other.value;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static LongParamValue deserialize(@Nonnull ByteBuf buf, int offset) {
/* 35 */     LongParamValue obj = new LongParamValue();
/*    */     
/* 37 */     obj.value = buf.getLongLE(offset + 0);
/*    */ 
/*    */     
/* 40 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 44 */     return 8;
/*    */   }
/*    */ 
/*    */   
/*    */   public int serialize(@Nonnull ByteBuf buf) {
/* 49 */     int startPos = buf.writerIndex();
/*    */     
/* 51 */     buf.writeLongLE(this.value);
/*    */     
/* 53 */     return buf.writerIndex() - startPos;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 59 */     return 8;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 63 */     if (buffer.readableBytes() - offset < 8) {
/* 64 */       return ValidationResult.error("Buffer too small: expected at least 8 bytes");
/*    */     }
/*    */ 
/*    */     
/* 68 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public LongParamValue clone() {
/* 72 */     LongParamValue copy = new LongParamValue();
/* 73 */     copy.value = this.value;
/* 74 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     LongParamValue other;
/* 80 */     if (this == obj) return true; 
/* 81 */     if (obj instanceof LongParamValue) { other = (LongParamValue)obj; } else { return false; }
/* 82 */      return (this.value == other.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 87 */     return Objects.hash(new Object[] { Long.valueOf(this.value) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\LongParamValue.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */