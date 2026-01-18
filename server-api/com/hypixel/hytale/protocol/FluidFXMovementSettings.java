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
/*     */ public class FluidFXMovementSettings
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 24;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 24;
/*     */   public static final int MAX_SIZE = 24;
/*     */   public float swimUpSpeed;
/*     */   public float swimDownSpeed;
/*     */   public float sinkSpeed;
/*     */   public float horizontalSpeedMultiplier;
/*     */   public float fieldOfViewMultiplier;
/*     */   public float entryVelocityMultiplier;
/*     */   
/*     */   public FluidFXMovementSettings() {}
/*     */   
/*     */   public FluidFXMovementSettings(float swimUpSpeed, float swimDownSpeed, float sinkSpeed, float horizontalSpeedMultiplier, float fieldOfViewMultiplier, float entryVelocityMultiplier) {
/*  31 */     this.swimUpSpeed = swimUpSpeed;
/*  32 */     this.swimDownSpeed = swimDownSpeed;
/*  33 */     this.sinkSpeed = sinkSpeed;
/*  34 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  35 */     this.fieldOfViewMultiplier = fieldOfViewMultiplier;
/*  36 */     this.entryVelocityMultiplier = entryVelocityMultiplier;
/*     */   }
/*     */   
/*     */   public FluidFXMovementSettings(@Nonnull FluidFXMovementSettings other) {
/*  40 */     this.swimUpSpeed = other.swimUpSpeed;
/*  41 */     this.swimDownSpeed = other.swimDownSpeed;
/*  42 */     this.sinkSpeed = other.sinkSpeed;
/*  43 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  44 */     this.fieldOfViewMultiplier = other.fieldOfViewMultiplier;
/*  45 */     this.entryVelocityMultiplier = other.entryVelocityMultiplier;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static FluidFXMovementSettings deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     FluidFXMovementSettings obj = new FluidFXMovementSettings();
/*     */     
/*  52 */     obj.swimUpSpeed = buf.getFloatLE(offset + 0);
/*  53 */     obj.swimDownSpeed = buf.getFloatLE(offset + 4);
/*  54 */     obj.sinkSpeed = buf.getFloatLE(offset + 8);
/*  55 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 12);
/*  56 */     obj.fieldOfViewMultiplier = buf.getFloatLE(offset + 16);
/*  57 */     obj.entryVelocityMultiplier = buf.getFloatLE(offset + 20);
/*     */ 
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     return 24;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  69 */     buf.writeFloatLE(this.swimUpSpeed);
/*  70 */     buf.writeFloatLE(this.swimDownSpeed);
/*  71 */     buf.writeFloatLE(this.sinkSpeed);
/*  72 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/*  73 */     buf.writeFloatLE(this.fieldOfViewMultiplier);
/*  74 */     buf.writeFloatLE(this.entryVelocityMultiplier);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  80 */     return 24;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  84 */     if (buffer.readableBytes() - offset < 24) {
/*  85 */       return ValidationResult.error("Buffer too small: expected at least 24 bytes");
/*     */     }
/*     */ 
/*     */     
/*  89 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public FluidFXMovementSettings clone() {
/*  93 */     FluidFXMovementSettings copy = new FluidFXMovementSettings();
/*  94 */     copy.swimUpSpeed = this.swimUpSpeed;
/*  95 */     copy.swimDownSpeed = this.swimDownSpeed;
/*  96 */     copy.sinkSpeed = this.sinkSpeed;
/*  97 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/*  98 */     copy.fieldOfViewMultiplier = this.fieldOfViewMultiplier;
/*  99 */     copy.entryVelocityMultiplier = this.entryVelocityMultiplier;
/* 100 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     FluidFXMovementSettings other;
/* 106 */     if (this == obj) return true; 
/* 107 */     if (obj instanceof FluidFXMovementSettings) { other = (FluidFXMovementSettings)obj; } else { return false; }
/* 108 */      return (this.swimUpSpeed == other.swimUpSpeed && this.swimDownSpeed == other.swimDownSpeed && this.sinkSpeed == other.sinkSpeed && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.fieldOfViewMultiplier == other.fieldOfViewMultiplier && this.entryVelocityMultiplier == other.entryVelocityMultiplier);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     return Objects.hash(new Object[] { Float.valueOf(this.swimUpSpeed), Float.valueOf(this.swimDownSpeed), Float.valueOf(this.sinkSpeed), Float.valueOf(this.horizontalSpeedMultiplier), Float.valueOf(this.fieldOfViewMultiplier), Float.valueOf(this.entryVelocityMultiplier) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\FluidFXMovementSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */