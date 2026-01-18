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
/*     */ public class FogOptions
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 18;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 18;
/*     */   public static final int MAX_SIZE = 18;
/*     */   public boolean ignoreFogLimits;
/*     */   public float effectiveViewDistanceMultiplier;
/*     */   public float fogFarViewDistance;
/*     */   public float fogHeightCameraOffset;
/*     */   public boolean fogHeightCameraOverriden;
/*     */   public float fogHeightCameraFixed;
/*     */   
/*     */   public FogOptions() {}
/*     */   
/*     */   public FogOptions(boolean ignoreFogLimits, float effectiveViewDistanceMultiplier, float fogFarViewDistance, float fogHeightCameraOffset, boolean fogHeightCameraOverriden, float fogHeightCameraFixed) {
/*  31 */     this.ignoreFogLimits = ignoreFogLimits;
/*  32 */     this.effectiveViewDistanceMultiplier = effectiveViewDistanceMultiplier;
/*  33 */     this.fogFarViewDistance = fogFarViewDistance;
/*  34 */     this.fogHeightCameraOffset = fogHeightCameraOffset;
/*  35 */     this.fogHeightCameraOverriden = fogHeightCameraOverriden;
/*  36 */     this.fogHeightCameraFixed = fogHeightCameraFixed;
/*     */   }
/*     */   
/*     */   public FogOptions(@Nonnull FogOptions other) {
/*  40 */     this.ignoreFogLimits = other.ignoreFogLimits;
/*  41 */     this.effectiveViewDistanceMultiplier = other.effectiveViewDistanceMultiplier;
/*  42 */     this.fogFarViewDistance = other.fogFarViewDistance;
/*  43 */     this.fogHeightCameraOffset = other.fogHeightCameraOffset;
/*  44 */     this.fogHeightCameraOverriden = other.fogHeightCameraOverriden;
/*  45 */     this.fogHeightCameraFixed = other.fogHeightCameraFixed;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static FogOptions deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     FogOptions obj = new FogOptions();
/*     */     
/*  52 */     obj.ignoreFogLimits = (buf.getByte(offset + 0) != 0);
/*  53 */     obj.effectiveViewDistanceMultiplier = buf.getFloatLE(offset + 1);
/*  54 */     obj.fogFarViewDistance = buf.getFloatLE(offset + 5);
/*  55 */     obj.fogHeightCameraOffset = buf.getFloatLE(offset + 9);
/*  56 */     obj.fogHeightCameraOverriden = (buf.getByte(offset + 13) != 0);
/*  57 */     obj.fogHeightCameraFixed = buf.getFloatLE(offset + 14);
/*     */ 
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     return 18;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  69 */     buf.writeByte(this.ignoreFogLimits ? 1 : 0);
/*  70 */     buf.writeFloatLE(this.effectiveViewDistanceMultiplier);
/*  71 */     buf.writeFloatLE(this.fogFarViewDistance);
/*  72 */     buf.writeFloatLE(this.fogHeightCameraOffset);
/*  73 */     buf.writeByte(this.fogHeightCameraOverriden ? 1 : 0);
/*  74 */     buf.writeFloatLE(this.fogHeightCameraFixed);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  80 */     return 18;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  84 */     if (buffer.readableBytes() - offset < 18) {
/*  85 */       return ValidationResult.error("Buffer too small: expected at least 18 bytes");
/*     */     }
/*     */ 
/*     */     
/*  89 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public FogOptions clone() {
/*  93 */     FogOptions copy = new FogOptions();
/*  94 */     copy.ignoreFogLimits = this.ignoreFogLimits;
/*  95 */     copy.effectiveViewDistanceMultiplier = this.effectiveViewDistanceMultiplier;
/*  96 */     copy.fogFarViewDistance = this.fogFarViewDistance;
/*  97 */     copy.fogHeightCameraOffset = this.fogHeightCameraOffset;
/*  98 */     copy.fogHeightCameraOverriden = this.fogHeightCameraOverriden;
/*  99 */     copy.fogHeightCameraFixed = this.fogHeightCameraFixed;
/* 100 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     FogOptions other;
/* 106 */     if (this == obj) return true; 
/* 107 */     if (obj instanceof FogOptions) { other = (FogOptions)obj; } else { return false; }
/* 108 */      return (this.ignoreFogLimits == other.ignoreFogLimits && this.effectiveViewDistanceMultiplier == other.effectiveViewDistanceMultiplier && this.fogFarViewDistance == other.fogFarViewDistance && this.fogHeightCameraOffset == other.fogHeightCameraOffset && this.fogHeightCameraOverriden == other.fogHeightCameraOverriden && this.fogHeightCameraFixed == other.fogHeightCameraFixed);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     return Objects.hash(new Object[] { Boolean.valueOf(this.ignoreFogLimits), Float.valueOf(this.effectiveViewDistanceMultiplier), Float.valueOf(this.fogFarViewDistance), Float.valueOf(this.fogHeightCameraOffset), Boolean.valueOf(this.fogHeightCameraOverriden), Float.valueOf(this.fogHeightCameraFixed) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\FogOptions.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */