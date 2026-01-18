/*    */ package com.hypixel.hytale.protocol.packets.buildertools;
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
/*    */ public class BuilderToolFloatArg
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 12;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 12;
/*    */   public static final int MAX_SIZE = 12;
/*    */   public float defaultValue;
/*    */   public float min;
/*    */   public float max;
/*    */   
/*    */   public BuilderToolFloatArg() {}
/*    */   
/*    */   public BuilderToolFloatArg(float defaultValue, float min, float max) {
/* 28 */     this.defaultValue = defaultValue;
/* 29 */     this.min = min;
/* 30 */     this.max = max;
/*    */   }
/*    */   
/*    */   public BuilderToolFloatArg(@Nonnull BuilderToolFloatArg other) {
/* 34 */     this.defaultValue = other.defaultValue;
/* 35 */     this.min = other.min;
/* 36 */     this.max = other.max;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static BuilderToolFloatArg deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     BuilderToolFloatArg obj = new BuilderToolFloatArg();
/*    */     
/* 43 */     obj.defaultValue = buf.getFloatLE(offset + 0);
/* 44 */     obj.min = buf.getFloatLE(offset + 4);
/* 45 */     obj.max = buf.getFloatLE(offset + 8);
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
/* 57 */     buf.writeFloatLE(this.defaultValue);
/* 58 */     buf.writeFloatLE(this.min);
/* 59 */     buf.writeFloatLE(this.max);
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
/*    */   public BuilderToolFloatArg clone() {
/* 78 */     BuilderToolFloatArg copy = new BuilderToolFloatArg();
/* 79 */     copy.defaultValue = this.defaultValue;
/* 80 */     copy.min = this.min;
/* 81 */     copy.max = this.max;
/* 82 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     BuilderToolFloatArg other;
/* 88 */     if (this == obj) return true; 
/* 89 */     if (obj instanceof BuilderToolFloatArg) { other = (BuilderToolFloatArg)obj; } else { return false; }
/* 90 */      return (this.defaultValue == other.defaultValue && this.min == other.min && this.max == other.max);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return Objects.hash(new Object[] { Float.valueOf(this.defaultValue), Float.valueOf(this.min), Float.valueOf(this.max) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolFloatArg.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */