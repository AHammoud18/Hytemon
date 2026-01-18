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
/*     */ public class BlockMovementSettings
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 42;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 42;
/*     */   public static final int MAX_SIZE = 42;
/*     */   public boolean isClimbable;
/*     */   public float climbUpSpeedMultiplier;
/*     */   public float climbDownSpeedMultiplier;
/*     */   public float climbLateralSpeedMultiplier;
/*     */   public boolean isBouncy;
/*     */   public float bounceVelocity;
/*     */   public float drag;
/*     */   public float friction;
/*     */   public float terminalVelocityModifier;
/*     */   public float horizontalSpeedMultiplier;
/*     */   public float acceleration;
/*     */   public float jumpForceMultiplier;
/*     */   
/*     */   public BlockMovementSettings() {}
/*     */   
/*     */   public BlockMovementSettings(boolean isClimbable, float climbUpSpeedMultiplier, float climbDownSpeedMultiplier, float climbLateralSpeedMultiplier, boolean isBouncy, float bounceVelocity, float drag, float friction, float terminalVelocityModifier, float horizontalSpeedMultiplier, float acceleration, float jumpForceMultiplier) {
/*  37 */     this.isClimbable = isClimbable;
/*  38 */     this.climbUpSpeedMultiplier = climbUpSpeedMultiplier;
/*  39 */     this.climbDownSpeedMultiplier = climbDownSpeedMultiplier;
/*  40 */     this.climbLateralSpeedMultiplier = climbLateralSpeedMultiplier;
/*  41 */     this.isBouncy = isBouncy;
/*  42 */     this.bounceVelocity = bounceVelocity;
/*  43 */     this.drag = drag;
/*  44 */     this.friction = friction;
/*  45 */     this.terminalVelocityModifier = terminalVelocityModifier;
/*  46 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  47 */     this.acceleration = acceleration;
/*  48 */     this.jumpForceMultiplier = jumpForceMultiplier;
/*     */   }
/*     */   
/*     */   public BlockMovementSettings(@Nonnull BlockMovementSettings other) {
/*  52 */     this.isClimbable = other.isClimbable;
/*  53 */     this.climbUpSpeedMultiplier = other.climbUpSpeedMultiplier;
/*  54 */     this.climbDownSpeedMultiplier = other.climbDownSpeedMultiplier;
/*  55 */     this.climbLateralSpeedMultiplier = other.climbLateralSpeedMultiplier;
/*  56 */     this.isBouncy = other.isBouncy;
/*  57 */     this.bounceVelocity = other.bounceVelocity;
/*  58 */     this.drag = other.drag;
/*  59 */     this.friction = other.friction;
/*  60 */     this.terminalVelocityModifier = other.terminalVelocityModifier;
/*  61 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  62 */     this.acceleration = other.acceleration;
/*  63 */     this.jumpForceMultiplier = other.jumpForceMultiplier;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BlockMovementSettings deserialize(@Nonnull ByteBuf buf, int offset) {
/*  68 */     BlockMovementSettings obj = new BlockMovementSettings();
/*     */     
/*  70 */     obj.isClimbable = (buf.getByte(offset + 0) != 0);
/*  71 */     obj.climbUpSpeedMultiplier = buf.getFloatLE(offset + 1);
/*  72 */     obj.climbDownSpeedMultiplier = buf.getFloatLE(offset + 5);
/*  73 */     obj.climbLateralSpeedMultiplier = buf.getFloatLE(offset + 9);
/*  74 */     obj.isBouncy = (buf.getByte(offset + 13) != 0);
/*  75 */     obj.bounceVelocity = buf.getFloatLE(offset + 14);
/*  76 */     obj.drag = buf.getFloatLE(offset + 18);
/*  77 */     obj.friction = buf.getFloatLE(offset + 22);
/*  78 */     obj.terminalVelocityModifier = buf.getFloatLE(offset + 26);
/*  79 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 30);
/*  80 */     obj.acceleration = buf.getFloatLE(offset + 34);
/*  81 */     obj.jumpForceMultiplier = buf.getFloatLE(offset + 38);
/*     */ 
/*     */     
/*  84 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  88 */     return 42;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  93 */     buf.writeByte(this.isClimbable ? 1 : 0);
/*  94 */     buf.writeFloatLE(this.climbUpSpeedMultiplier);
/*  95 */     buf.writeFloatLE(this.climbDownSpeedMultiplier);
/*  96 */     buf.writeFloatLE(this.climbLateralSpeedMultiplier);
/*  97 */     buf.writeByte(this.isBouncy ? 1 : 0);
/*  98 */     buf.writeFloatLE(this.bounceVelocity);
/*  99 */     buf.writeFloatLE(this.drag);
/* 100 */     buf.writeFloatLE(this.friction);
/* 101 */     buf.writeFloatLE(this.terminalVelocityModifier);
/* 102 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 103 */     buf.writeFloatLE(this.acceleration);
/* 104 */     buf.writeFloatLE(this.jumpForceMultiplier);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 110 */     return 42;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 114 */     if (buffer.readableBytes() - offset < 42) {
/* 115 */       return ValidationResult.error("Buffer too small: expected at least 42 bytes");
/*     */     }
/*     */ 
/*     */     
/* 119 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BlockMovementSettings clone() {
/* 123 */     BlockMovementSettings copy = new BlockMovementSettings();
/* 124 */     copy.isClimbable = this.isClimbable;
/* 125 */     copy.climbUpSpeedMultiplier = this.climbUpSpeedMultiplier;
/* 126 */     copy.climbDownSpeedMultiplier = this.climbDownSpeedMultiplier;
/* 127 */     copy.climbLateralSpeedMultiplier = this.climbLateralSpeedMultiplier;
/* 128 */     copy.isBouncy = this.isBouncy;
/* 129 */     copy.bounceVelocity = this.bounceVelocity;
/* 130 */     copy.drag = this.drag;
/* 131 */     copy.friction = this.friction;
/* 132 */     copy.terminalVelocityModifier = this.terminalVelocityModifier;
/* 133 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 134 */     copy.acceleration = this.acceleration;
/* 135 */     copy.jumpForceMultiplier = this.jumpForceMultiplier;
/* 136 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BlockMovementSettings other;
/* 142 */     if (this == obj) return true; 
/* 143 */     if (obj instanceof BlockMovementSettings) { other = (BlockMovementSettings)obj; } else { return false; }
/* 144 */      return (this.isClimbable == other.isClimbable && this.climbUpSpeedMultiplier == other.climbUpSpeedMultiplier && this.climbDownSpeedMultiplier == other.climbDownSpeedMultiplier && this.climbLateralSpeedMultiplier == other.climbLateralSpeedMultiplier && this.isBouncy == other.isBouncy && this.bounceVelocity == other.bounceVelocity && this.drag == other.drag && this.friction == other.friction && this.terminalVelocityModifier == other.terminalVelocityModifier && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.acceleration == other.acceleration && this.jumpForceMultiplier == other.jumpForceMultiplier);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 149 */     return Objects.hash(new Object[] { Boolean.valueOf(this.isClimbable), Float.valueOf(this.climbUpSpeedMultiplier), Float.valueOf(this.climbDownSpeedMultiplier), Float.valueOf(this.climbLateralSpeedMultiplier), Boolean.valueOf(this.isBouncy), Float.valueOf(this.bounceVelocity), Float.valueOf(this.drag), Float.valueOf(this.friction), Float.valueOf(this.terminalVelocityModifier), Float.valueOf(this.horizontalSpeedMultiplier), Float.valueOf(this.acceleration), Float.valueOf(this.jumpForceMultiplier) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BlockMovementSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */