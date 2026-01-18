/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VelocityConfig
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 21;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 21;
/*     */   public static final int MAX_SIZE = 21;
/*     */   public float groundResistance;
/*     */   public float groundResistanceMax;
/*     */   public float airResistance;
/*     */   public float airResistanceMax;
/*     */   public float threshold;
/*     */   @Nonnull
/*  25 */   public VelocityThresholdStyle style = VelocityThresholdStyle.Linear;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VelocityConfig(float groundResistance, float groundResistanceMax, float airResistance, float airResistanceMax, float threshold, @Nonnull VelocityThresholdStyle style) {
/*  31 */     this.groundResistance = groundResistance;
/*  32 */     this.groundResistanceMax = groundResistanceMax;
/*  33 */     this.airResistance = airResistance;
/*  34 */     this.airResistanceMax = airResistanceMax;
/*  35 */     this.threshold = threshold;
/*  36 */     this.style = style;
/*     */   }
/*     */   
/*     */   public VelocityConfig(@Nonnull VelocityConfig other) {
/*  40 */     this.groundResistance = other.groundResistance;
/*  41 */     this.groundResistanceMax = other.groundResistanceMax;
/*  42 */     this.airResistance = other.airResistance;
/*  43 */     this.airResistanceMax = other.airResistanceMax;
/*  44 */     this.threshold = other.threshold;
/*  45 */     this.style = other.style;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static VelocityConfig deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     VelocityConfig obj = new VelocityConfig();
/*     */     
/*  52 */     obj.groundResistance = buf.getFloatLE(offset + 0);
/*  53 */     obj.groundResistanceMax = buf.getFloatLE(offset + 4);
/*  54 */     obj.airResistance = buf.getFloatLE(offset + 8);
/*  55 */     obj.airResistanceMax = buf.getFloatLE(offset + 12);
/*  56 */     obj.threshold = buf.getFloatLE(offset + 16);
/*  57 */     obj.style = VelocityThresholdStyle.fromValue(buf.getByte(offset + 20));
/*     */ 
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     return 21;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  69 */     buf.writeFloatLE(this.groundResistance);
/*  70 */     buf.writeFloatLE(this.groundResistanceMax);
/*  71 */     buf.writeFloatLE(this.airResistance);
/*  72 */     buf.writeFloatLE(this.airResistanceMax);
/*  73 */     buf.writeFloatLE(this.threshold);
/*  74 */     buf.writeByte(this.style.getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  80 */     return 21;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  84 */     if (buffer.readableBytes() - offset < 21) {
/*  85 */       return ValidationResult.error("Buffer too small: expected at least 21 bytes");
/*     */     }
/*     */ 
/*     */     
/*  89 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public VelocityConfig clone() {
/*  93 */     VelocityConfig copy = new VelocityConfig();
/*  94 */     copy.groundResistance = this.groundResistance;
/*  95 */     copy.groundResistanceMax = this.groundResistanceMax;
/*  96 */     copy.airResistance = this.airResistance;
/*  97 */     copy.airResistanceMax = this.airResistanceMax;
/*  98 */     copy.threshold = this.threshold;
/*  99 */     copy.style = this.style;
/* 100 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     VelocityConfig other;
/* 106 */     if (this == obj) return true; 
/* 107 */     if (obj instanceof VelocityConfig) { other = (VelocityConfig)obj; } else { return false; }
/* 108 */      return (this.groundResistance == other.groundResistance && this.groundResistanceMax == other.groundResistanceMax && this.airResistance == other.airResistance && this.airResistanceMax == other.airResistanceMax && this.threshold == other.threshold && Objects.equals(this.style, other.style));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     return Objects.hash(new Object[] { Float.valueOf(this.groundResistance), Float.valueOf(this.groundResistanceMax), Float.valueOf(this.airResistance), Float.valueOf(this.airResistanceMax), Float.valueOf(this.threshold), this.style });
/*     */   }
/*     */   
/*     */   public VelocityConfig() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\VelocityConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */