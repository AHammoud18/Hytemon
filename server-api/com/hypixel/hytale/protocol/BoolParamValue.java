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
/*    */ public class BoolParamValue
/*    */   extends ParamValue
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 1;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 1;
/*    */   public static final int MAX_SIZE = 1;
/*    */   public boolean value;
/*    */   
/*    */   public BoolParamValue() {}
/*    */   
/*    */   public BoolParamValue(boolean value) {
/* 26 */     this.value = value;
/*    */   }
/*    */   
/*    */   public BoolParamValue(@Nonnull BoolParamValue other) {
/* 30 */     this.value = other.value;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static BoolParamValue deserialize(@Nonnull ByteBuf buf, int offset) {
/* 35 */     BoolParamValue obj = new BoolParamValue();
/*    */     
/* 37 */     obj.value = (buf.getByte(offset + 0) != 0);
/*    */ 
/*    */     
/* 40 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 44 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public int serialize(@Nonnull ByteBuf buf) {
/* 49 */     int startPos = buf.writerIndex();
/*    */     
/* 51 */     buf.writeByte(this.value ? 1 : 0);
/*    */     
/* 53 */     return buf.writerIndex() - startPos;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 59 */     return 1;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 63 */     if (buffer.readableBytes() - offset < 1) {
/* 64 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*    */     }
/*    */ 
/*    */     
/* 68 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public BoolParamValue clone() {
/* 72 */     BoolParamValue copy = new BoolParamValue();
/* 73 */     copy.value = this.value;
/* 74 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     BoolParamValue other;
/* 80 */     if (this == obj) return true; 
/* 81 */     if (obj instanceof BoolParamValue) { other = (BoolParamValue)obj; } else { return false; }
/* 82 */      return (this.value == other.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 87 */     return Objects.hash(new Object[] { Boolean.valueOf(this.value) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BoolParamValue.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */