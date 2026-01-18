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
/*    */ public class EasingConfig
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 5;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 5;
/*    */   public static final int MAX_SIZE = 5;
/*    */   public float time;
/*    */   @Nonnull
/* 21 */   public EasingType type = EasingType.Linear;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EasingConfig(float time, @Nonnull EasingType type) {
/* 27 */     this.time = time;
/* 28 */     this.type = type;
/*    */   }
/*    */   
/*    */   public EasingConfig(@Nonnull EasingConfig other) {
/* 32 */     this.time = other.time;
/* 33 */     this.type = other.type;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static EasingConfig deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     EasingConfig obj = new EasingConfig();
/*    */     
/* 40 */     obj.time = buf.getFloatLE(offset + 0);
/* 41 */     obj.type = EasingType.fromValue(buf.getByte(offset + 4));
/*    */ 
/*    */     
/* 44 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 48 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 53 */     buf.writeFloatLE(this.time);
/* 54 */     buf.writeByte(this.type.getValue());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 60 */     return 5;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 64 */     if (buffer.readableBytes() - offset < 5) {
/* 65 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*    */     }
/*    */ 
/*    */     
/* 69 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public EasingConfig clone() {
/* 73 */     EasingConfig copy = new EasingConfig();
/* 74 */     copy.time = this.time;
/* 75 */     copy.type = this.type;
/* 76 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     EasingConfig other;
/* 82 */     if (this == obj) return true; 
/* 83 */     if (obj instanceof EasingConfig) { other = (EasingConfig)obj; } else { return false; }
/* 84 */      return (this.time == other.time && Objects.equals(this.type, other.type));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return Objects.hash(new Object[] { Float.valueOf(this.time), this.type });
/*    */   }
/*    */   
/*    */   public EasingConfig() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\EasingConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */