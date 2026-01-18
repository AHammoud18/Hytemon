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
/*    */ public class RepulsionConfig
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 12;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 12;
/*    */   public static final int MAX_SIZE = 12;
/*    */   public float radius;
/*    */   public float minForce;
/*    */   public float maxForce;
/*    */   
/*    */   public RepulsionConfig() {}
/*    */   
/*    */   public RepulsionConfig(float radius, float minForce, float maxForce) {
/* 28 */     this.radius = radius;
/* 29 */     this.minForce = minForce;
/* 30 */     this.maxForce = maxForce;
/*    */   }
/*    */   
/*    */   public RepulsionConfig(@Nonnull RepulsionConfig other) {
/* 34 */     this.radius = other.radius;
/* 35 */     this.minForce = other.minForce;
/* 36 */     this.maxForce = other.maxForce;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static RepulsionConfig deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     RepulsionConfig obj = new RepulsionConfig();
/*    */     
/* 43 */     obj.radius = buf.getFloatLE(offset + 0);
/* 44 */     obj.minForce = buf.getFloatLE(offset + 4);
/* 45 */     obj.maxForce = buf.getFloatLE(offset + 8);
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
/* 57 */     buf.writeFloatLE(this.radius);
/* 58 */     buf.writeFloatLE(this.minForce);
/* 59 */     buf.writeFloatLE(this.maxForce);
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
/*    */   public RepulsionConfig clone() {
/* 78 */     RepulsionConfig copy = new RepulsionConfig();
/* 79 */     copy.radius = this.radius;
/* 80 */     copy.minForce = this.minForce;
/* 81 */     copy.maxForce = this.maxForce;
/* 82 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     RepulsionConfig other;
/* 88 */     if (this == obj) return true; 
/* 89 */     if (obj instanceof RepulsionConfig) { other = (RepulsionConfig)obj; } else { return false; }
/* 90 */      return (this.radius == other.radius && this.minForce == other.minForce && this.maxForce == other.maxForce);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return Objects.hash(new Object[] { Float.valueOf(this.radius), Float.valueOf(this.minForce), Float.valueOf(this.maxForce) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\RepulsionConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */