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
/*     */ 
/*     */ 
/*     */ public class SoundEventLayerRandomSettings
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 20;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 20;
/*     */   public static final int MAX_SIZE = 20;
/*     */   public float minVolume;
/*     */   public float maxVolume;
/*     */   public float minPitch;
/*     */   public float maxPitch;
/*     */   public float maxStartOffset;
/*     */   
/*     */   public SoundEventLayerRandomSettings() {}
/*     */   
/*     */   public SoundEventLayerRandomSettings(float minVolume, float maxVolume, float minPitch, float maxPitch, float maxStartOffset) {
/*  30 */     this.minVolume = minVolume;
/*  31 */     this.maxVolume = maxVolume;
/*  32 */     this.minPitch = minPitch;
/*  33 */     this.maxPitch = maxPitch;
/*  34 */     this.maxStartOffset = maxStartOffset;
/*     */   }
/*     */   
/*     */   public SoundEventLayerRandomSettings(@Nonnull SoundEventLayerRandomSettings other) {
/*  38 */     this.minVolume = other.minVolume;
/*  39 */     this.maxVolume = other.maxVolume;
/*  40 */     this.minPitch = other.minPitch;
/*  41 */     this.maxPitch = other.maxPitch;
/*  42 */     this.maxStartOffset = other.maxStartOffset;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SoundEventLayerRandomSettings deserialize(@Nonnull ByteBuf buf, int offset) {
/*  47 */     SoundEventLayerRandomSettings obj = new SoundEventLayerRandomSettings();
/*     */     
/*  49 */     obj.minVolume = buf.getFloatLE(offset + 0);
/*  50 */     obj.maxVolume = buf.getFloatLE(offset + 4);
/*  51 */     obj.minPitch = buf.getFloatLE(offset + 8);
/*  52 */     obj.maxPitch = buf.getFloatLE(offset + 12);
/*  53 */     obj.maxStartOffset = buf.getFloatLE(offset + 16);
/*     */ 
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     return 20;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  65 */     buf.writeFloatLE(this.minVolume);
/*  66 */     buf.writeFloatLE(this.maxVolume);
/*  67 */     buf.writeFloatLE(this.minPitch);
/*  68 */     buf.writeFloatLE(this.maxPitch);
/*  69 */     buf.writeFloatLE(this.maxStartOffset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  75 */     return 20;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  79 */     if (buffer.readableBytes() - offset < 20) {
/*  80 */       return ValidationResult.error("Buffer too small: expected at least 20 bytes");
/*     */     }
/*     */ 
/*     */     
/*  84 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SoundEventLayerRandomSettings clone() {
/*  88 */     SoundEventLayerRandomSettings copy = new SoundEventLayerRandomSettings();
/*  89 */     copy.minVolume = this.minVolume;
/*  90 */     copy.maxVolume = this.maxVolume;
/*  91 */     copy.minPitch = this.minPitch;
/*  92 */     copy.maxPitch = this.maxPitch;
/*  93 */     copy.maxStartOffset = this.maxStartOffset;
/*  94 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SoundEventLayerRandomSettings other;
/* 100 */     if (this == obj) return true; 
/* 101 */     if (obj instanceof SoundEventLayerRandomSettings) { other = (SoundEventLayerRandomSettings)obj; } else { return false; }
/* 102 */      return (this.minVolume == other.minVolume && this.maxVolume == other.maxVolume && this.minPitch == other.minPitch && this.maxPitch == other.maxPitch && this.maxStartOffset == other.maxStartOffset);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 107 */     return Objects.hash(new Object[] { Float.valueOf(this.minVolume), Float.valueOf(this.maxVolume), Float.valueOf(this.minPitch), Float.valueOf(this.maxPitch), Float.valueOf(this.maxStartOffset) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\SoundEventLayerRandomSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */