/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NoiseConfig
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 23;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 23;
/*     */   public static final int MAX_SIZE = 23;
/*     */   public int seed;
/*     */   @Nonnull
/*  21 */   public NoiseType type = NoiseType.Sin;
/*     */   
/*     */   public float frequency;
/*     */   
/*     */   public float amplitude;
/*     */   @Nullable
/*     */   public ClampConfig clamp;
/*     */   
/*     */   public NoiseConfig(int seed, @Nonnull NoiseType type, float frequency, float amplitude, @Nullable ClampConfig clamp) {
/*  30 */     this.seed = seed;
/*  31 */     this.type = type;
/*  32 */     this.frequency = frequency;
/*  33 */     this.amplitude = amplitude;
/*  34 */     this.clamp = clamp;
/*     */   }
/*     */   
/*     */   public NoiseConfig(@Nonnull NoiseConfig other) {
/*  38 */     this.seed = other.seed;
/*  39 */     this.type = other.type;
/*  40 */     this.frequency = other.frequency;
/*  41 */     this.amplitude = other.amplitude;
/*  42 */     this.clamp = other.clamp;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static NoiseConfig deserialize(@Nonnull ByteBuf buf, int offset) {
/*  47 */     NoiseConfig obj = new NoiseConfig();
/*  48 */     byte nullBits = buf.getByte(offset);
/*  49 */     obj.seed = buf.getIntLE(offset + 1);
/*  50 */     obj.type = NoiseType.fromValue(buf.getByte(offset + 5));
/*  51 */     obj.frequency = buf.getFloatLE(offset + 6);
/*  52 */     obj.amplitude = buf.getFloatLE(offset + 10);
/*  53 */     if ((nullBits & 0x1) != 0) obj.clamp = ClampConfig.deserialize(buf, offset + 14);
/*     */ 
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     return 23;
/*     */   }
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  64 */     byte nullBits = 0;
/*  65 */     if (this.clamp != null) nullBits = (byte)(nullBits | 0x1); 
/*  66 */     buf.writeByte(nullBits);
/*     */     
/*  68 */     buf.writeIntLE(this.seed);
/*  69 */     buf.writeByte(this.type.getValue());
/*  70 */     buf.writeFloatLE(this.frequency);
/*  71 */     buf.writeFloatLE(this.amplitude);
/*  72 */     if (this.clamp != null) { this.clamp.serialize(buf); } else { buf.writeZero(9); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  78 */     return 23;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  82 */     if (buffer.readableBytes() - offset < 23) {
/*  83 */       return ValidationResult.error("Buffer too small: expected at least 23 bytes");
/*     */     }
/*     */ 
/*     */     
/*  87 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public NoiseConfig clone() {
/*  91 */     NoiseConfig copy = new NoiseConfig();
/*  92 */     copy.seed = this.seed;
/*  93 */     copy.type = this.type;
/*  94 */     copy.frequency = this.frequency;
/*  95 */     copy.amplitude = this.amplitude;
/*  96 */     copy.clamp = (this.clamp != null) ? this.clamp.clone() : null;
/*  97 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     NoiseConfig other;
/* 103 */     if (this == obj) return true; 
/* 104 */     if (obj instanceof NoiseConfig) { other = (NoiseConfig)obj; } else { return false; }
/* 105 */      return (this.seed == other.seed && Objects.equals(this.type, other.type) && this.frequency == other.frequency && this.amplitude == other.amplitude && Objects.equals(this.clamp, other.clamp));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 110 */     return Objects.hash(new Object[] { Integer.valueOf(this.seed), this.type, Float.valueOf(this.frequency), Float.valueOf(this.amplitude), this.clamp });
/*     */   }
/*     */   
/*     */   public NoiseConfig() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\NoiseConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */