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
/*     */ public class PhysicsConfig
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 122;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 122;
/*     */   public static final int MAX_SIZE = 122;
/*     */   @Nonnull
/*  20 */   public PhysicsType type = PhysicsType.Standard; public double density;
/*     */   public double gravity;
/*     */   public double bounciness;
/*     */   public int bounceCount;
/*     */   public double bounceLimit;
/*     */   public boolean sticksVertically;
/*     */   public boolean computeYaw;
/*     */   public boolean computePitch;
/*     */   @Nonnull
/*  29 */   public RotationMode rotationMode = RotationMode.None;
/*     */   
/*     */   public double moveOutOfSolidSpeed;
/*     */   
/*     */   public double terminalVelocityAir;
/*     */   
/*     */   public double densityAir;
/*     */   public double terminalVelocityWater;
/*     */   public double densityWater;
/*     */   public double hitWaterImpulseLoss;
/*     */   public double rotationForce;
/*     */   public float speedRotationFactor;
/*     */   public double swimmingDampingFactor;
/*     */   public boolean allowRolling;
/*     */   public double rollingFrictionFactor;
/*     */   public float rollingSpeed;
/*     */   
/*     */   public PhysicsConfig(@Nonnull PhysicsType type, double density, double gravity, double bounciness, int bounceCount, double bounceLimit, boolean sticksVertically, boolean computeYaw, boolean computePitch, @Nonnull RotationMode rotationMode, double moveOutOfSolidSpeed, double terminalVelocityAir, double densityAir, double terminalVelocityWater, double densityWater, double hitWaterImpulseLoss, double rotationForce, float speedRotationFactor, double swimmingDampingFactor, boolean allowRolling, double rollingFrictionFactor, float rollingSpeed) {
/*  47 */     this.type = type;
/*  48 */     this.density = density;
/*  49 */     this.gravity = gravity;
/*  50 */     this.bounciness = bounciness;
/*  51 */     this.bounceCount = bounceCount;
/*  52 */     this.bounceLimit = bounceLimit;
/*  53 */     this.sticksVertically = sticksVertically;
/*  54 */     this.computeYaw = computeYaw;
/*  55 */     this.computePitch = computePitch;
/*  56 */     this.rotationMode = rotationMode;
/*  57 */     this.moveOutOfSolidSpeed = moveOutOfSolidSpeed;
/*  58 */     this.terminalVelocityAir = terminalVelocityAir;
/*  59 */     this.densityAir = densityAir;
/*  60 */     this.terminalVelocityWater = terminalVelocityWater;
/*  61 */     this.densityWater = densityWater;
/*  62 */     this.hitWaterImpulseLoss = hitWaterImpulseLoss;
/*  63 */     this.rotationForce = rotationForce;
/*  64 */     this.speedRotationFactor = speedRotationFactor;
/*  65 */     this.swimmingDampingFactor = swimmingDampingFactor;
/*  66 */     this.allowRolling = allowRolling;
/*  67 */     this.rollingFrictionFactor = rollingFrictionFactor;
/*  68 */     this.rollingSpeed = rollingSpeed;
/*     */   }
/*     */   
/*     */   public PhysicsConfig(@Nonnull PhysicsConfig other) {
/*  72 */     this.type = other.type;
/*  73 */     this.density = other.density;
/*  74 */     this.gravity = other.gravity;
/*  75 */     this.bounciness = other.bounciness;
/*  76 */     this.bounceCount = other.bounceCount;
/*  77 */     this.bounceLimit = other.bounceLimit;
/*  78 */     this.sticksVertically = other.sticksVertically;
/*  79 */     this.computeYaw = other.computeYaw;
/*  80 */     this.computePitch = other.computePitch;
/*  81 */     this.rotationMode = other.rotationMode;
/*  82 */     this.moveOutOfSolidSpeed = other.moveOutOfSolidSpeed;
/*  83 */     this.terminalVelocityAir = other.terminalVelocityAir;
/*  84 */     this.densityAir = other.densityAir;
/*  85 */     this.terminalVelocityWater = other.terminalVelocityWater;
/*  86 */     this.densityWater = other.densityWater;
/*  87 */     this.hitWaterImpulseLoss = other.hitWaterImpulseLoss;
/*  88 */     this.rotationForce = other.rotationForce;
/*  89 */     this.speedRotationFactor = other.speedRotationFactor;
/*  90 */     this.swimmingDampingFactor = other.swimmingDampingFactor;
/*  91 */     this.allowRolling = other.allowRolling;
/*  92 */     this.rollingFrictionFactor = other.rollingFrictionFactor;
/*  93 */     this.rollingSpeed = other.rollingSpeed;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static PhysicsConfig deserialize(@Nonnull ByteBuf buf, int offset) {
/*  98 */     PhysicsConfig obj = new PhysicsConfig();
/*     */     
/* 100 */     obj.type = PhysicsType.fromValue(buf.getByte(offset + 0));
/* 101 */     obj.density = buf.getDoubleLE(offset + 1);
/* 102 */     obj.gravity = buf.getDoubleLE(offset + 9);
/* 103 */     obj.bounciness = buf.getDoubleLE(offset + 17);
/* 104 */     obj.bounceCount = buf.getIntLE(offset + 25);
/* 105 */     obj.bounceLimit = buf.getDoubleLE(offset + 29);
/* 106 */     obj.sticksVertically = (buf.getByte(offset + 37) != 0);
/* 107 */     obj.computeYaw = (buf.getByte(offset + 38) != 0);
/* 108 */     obj.computePitch = (buf.getByte(offset + 39) != 0);
/* 109 */     obj.rotationMode = RotationMode.fromValue(buf.getByte(offset + 40));
/* 110 */     obj.moveOutOfSolidSpeed = buf.getDoubleLE(offset + 41);
/* 111 */     obj.terminalVelocityAir = buf.getDoubleLE(offset + 49);
/* 112 */     obj.densityAir = buf.getDoubleLE(offset + 57);
/* 113 */     obj.terminalVelocityWater = buf.getDoubleLE(offset + 65);
/* 114 */     obj.densityWater = buf.getDoubleLE(offset + 73);
/* 115 */     obj.hitWaterImpulseLoss = buf.getDoubleLE(offset + 81);
/* 116 */     obj.rotationForce = buf.getDoubleLE(offset + 89);
/* 117 */     obj.speedRotationFactor = buf.getFloatLE(offset + 97);
/* 118 */     obj.swimmingDampingFactor = buf.getDoubleLE(offset + 101);
/* 119 */     obj.allowRolling = (buf.getByte(offset + 109) != 0);
/* 120 */     obj.rollingFrictionFactor = buf.getDoubleLE(offset + 110);
/* 121 */     obj.rollingSpeed = buf.getFloatLE(offset + 118);
/*     */ 
/*     */     
/* 124 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 128 */     return 122;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 133 */     buf.writeByte(this.type.getValue());
/* 134 */     buf.writeDoubleLE(this.density);
/* 135 */     buf.writeDoubleLE(this.gravity);
/* 136 */     buf.writeDoubleLE(this.bounciness);
/* 137 */     buf.writeIntLE(this.bounceCount);
/* 138 */     buf.writeDoubleLE(this.bounceLimit);
/* 139 */     buf.writeByte(this.sticksVertically ? 1 : 0);
/* 140 */     buf.writeByte(this.computeYaw ? 1 : 0);
/* 141 */     buf.writeByte(this.computePitch ? 1 : 0);
/* 142 */     buf.writeByte(this.rotationMode.getValue());
/* 143 */     buf.writeDoubleLE(this.moveOutOfSolidSpeed);
/* 144 */     buf.writeDoubleLE(this.terminalVelocityAir);
/* 145 */     buf.writeDoubleLE(this.densityAir);
/* 146 */     buf.writeDoubleLE(this.terminalVelocityWater);
/* 147 */     buf.writeDoubleLE(this.densityWater);
/* 148 */     buf.writeDoubleLE(this.hitWaterImpulseLoss);
/* 149 */     buf.writeDoubleLE(this.rotationForce);
/* 150 */     buf.writeFloatLE(this.speedRotationFactor);
/* 151 */     buf.writeDoubleLE(this.swimmingDampingFactor);
/* 152 */     buf.writeByte(this.allowRolling ? 1 : 0);
/* 153 */     buf.writeDoubleLE(this.rollingFrictionFactor);
/* 154 */     buf.writeFloatLE(this.rollingSpeed);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 160 */     return 122;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 164 */     if (buffer.readableBytes() - offset < 122) {
/* 165 */       return ValidationResult.error("Buffer too small: expected at least 122 bytes");
/*     */     }
/*     */ 
/*     */     
/* 169 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public PhysicsConfig clone() {
/* 173 */     PhysicsConfig copy = new PhysicsConfig();
/* 174 */     copy.type = this.type;
/* 175 */     copy.density = this.density;
/* 176 */     copy.gravity = this.gravity;
/* 177 */     copy.bounciness = this.bounciness;
/* 178 */     copy.bounceCount = this.bounceCount;
/* 179 */     copy.bounceLimit = this.bounceLimit;
/* 180 */     copy.sticksVertically = this.sticksVertically;
/* 181 */     copy.computeYaw = this.computeYaw;
/* 182 */     copy.computePitch = this.computePitch;
/* 183 */     copy.rotationMode = this.rotationMode;
/* 184 */     copy.moveOutOfSolidSpeed = this.moveOutOfSolidSpeed;
/* 185 */     copy.terminalVelocityAir = this.terminalVelocityAir;
/* 186 */     copy.densityAir = this.densityAir;
/* 187 */     copy.terminalVelocityWater = this.terminalVelocityWater;
/* 188 */     copy.densityWater = this.densityWater;
/* 189 */     copy.hitWaterImpulseLoss = this.hitWaterImpulseLoss;
/* 190 */     copy.rotationForce = this.rotationForce;
/* 191 */     copy.speedRotationFactor = this.speedRotationFactor;
/* 192 */     copy.swimmingDampingFactor = this.swimmingDampingFactor;
/* 193 */     copy.allowRolling = this.allowRolling;
/* 194 */     copy.rollingFrictionFactor = this.rollingFrictionFactor;
/* 195 */     copy.rollingSpeed = this.rollingSpeed;
/* 196 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     PhysicsConfig other;
/* 202 */     if (this == obj) return true; 
/* 203 */     if (obj instanceof PhysicsConfig) { other = (PhysicsConfig)obj; } else { return false; }
/* 204 */      return (Objects.equals(this.type, other.type) && this.density == other.density && this.gravity == other.gravity && this.bounciness == other.bounciness && this.bounceCount == other.bounceCount && this.bounceLimit == other.bounceLimit && this.sticksVertically == other.sticksVertically && this.computeYaw == other.computeYaw && this.computePitch == other.computePitch && Objects.equals(this.rotationMode, other.rotationMode) && this.moveOutOfSolidSpeed == other.moveOutOfSolidSpeed && this.terminalVelocityAir == other.terminalVelocityAir && this.densityAir == other.densityAir && this.terminalVelocityWater == other.terminalVelocityWater && this.densityWater == other.densityWater && this.hitWaterImpulseLoss == other.hitWaterImpulseLoss && this.rotationForce == other.rotationForce && this.speedRotationFactor == other.speedRotationFactor && this.swimmingDampingFactor == other.swimmingDampingFactor && this.allowRolling == other.allowRolling && this.rollingFrictionFactor == other.rollingFrictionFactor && this.rollingSpeed == other.rollingSpeed);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 209 */     return Objects.hash(new Object[] { this.type, Double.valueOf(this.density), Double.valueOf(this.gravity), Double.valueOf(this.bounciness), Integer.valueOf(this.bounceCount), Double.valueOf(this.bounceLimit), Boolean.valueOf(this.sticksVertically), Boolean.valueOf(this.computeYaw), Boolean.valueOf(this.computePitch), this.rotationMode, Double.valueOf(this.moveOutOfSolidSpeed), Double.valueOf(this.terminalVelocityAir), Double.valueOf(this.densityAir), Double.valueOf(this.terminalVelocityWater), Double.valueOf(this.densityWater), Double.valueOf(this.hitWaterImpulseLoss), Double.valueOf(this.rotationForce), Float.valueOf(this.speedRotationFactor), Double.valueOf(this.swimmingDampingFactor), Boolean.valueOf(this.allowRolling), Double.valueOf(this.rollingFrictionFactor), Float.valueOf(this.rollingSpeed) });
/*     */   }
/*     */   
/*     */   public PhysicsConfig() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\PhysicsConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */