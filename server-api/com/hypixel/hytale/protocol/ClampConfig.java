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
/*    */ public class ClampConfig
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 9;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 9;
/*    */   public static final int MAX_SIZE = 9;
/*    */   public float min;
/*    */   public float max;
/*    */   public boolean normalize;
/*    */   
/*    */   public ClampConfig() {}
/*    */   
/*    */   public ClampConfig(float min, float max, boolean normalize) {
/* 28 */     this.min = min;
/* 29 */     this.max = max;
/* 30 */     this.normalize = normalize;
/*    */   }
/*    */   
/*    */   public ClampConfig(@Nonnull ClampConfig other) {
/* 34 */     this.min = other.min;
/* 35 */     this.max = other.max;
/* 36 */     this.normalize = other.normalize;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ClampConfig deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     ClampConfig obj = new ClampConfig();
/*    */     
/* 43 */     obj.min = buf.getFloatLE(offset + 0);
/* 44 */     obj.max = buf.getFloatLE(offset + 4);
/* 45 */     obj.normalize = (buf.getByte(offset + 8) != 0);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 9;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 57 */     buf.writeFloatLE(this.min);
/* 58 */     buf.writeFloatLE(this.max);
/* 59 */     buf.writeByte(this.normalize ? 1 : 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 65 */     return 9;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 69 */     if (buffer.readableBytes() - offset < 9) {
/* 70 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*    */     }
/*    */ 
/*    */     
/* 74 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public ClampConfig clone() {
/* 78 */     ClampConfig copy = new ClampConfig();
/* 79 */     copy.min = this.min;
/* 80 */     copy.max = this.max;
/* 81 */     copy.normalize = this.normalize;
/* 82 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     ClampConfig other;
/* 88 */     if (this == obj) return true; 
/* 89 */     if (obj instanceof ClampConfig) { other = (ClampConfig)obj; } else { return false; }
/* 90 */      return (this.min == other.min && this.max == other.max && this.normalize == other.normalize);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return Objects.hash(new Object[] { Float.valueOf(this.min), Float.valueOf(this.max), Boolean.valueOf(this.normalize) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ClampConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */