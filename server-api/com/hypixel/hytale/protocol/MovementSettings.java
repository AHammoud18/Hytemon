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
/*     */ public class MovementSettings
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 251;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 251;
/*     */   public static final int MAX_SIZE = 251;
/*     */   public float mass;
/*     */   public float dragCoefficient;
/*     */   public boolean invertedGravity;
/*     */   public float velocityResistance;
/*     */   public float jumpForce;
/*     */   public float swimJumpForce;
/*     */   public float jumpBufferDuration;
/*     */   public float jumpBufferMaxYVelocity;
/*     */   public float acceleration;
/*     */   public float airDragMin;
/*     */   public float airDragMax;
/*     */   public float airDragMinSpeed;
/*     */   public float airDragMaxSpeed;
/*     */   public float airFrictionMin;
/*     */   public float airFrictionMax;
/*     */   public float airFrictionMinSpeed;
/*     */   public float airFrictionMaxSpeed;
/*     */   public float airSpeedMultiplier;
/*     */   public float airControlMinSpeed;
/*     */   public float airControlMaxSpeed;
/*     */   public float airControlMinMultiplier;
/*     */   public float airControlMaxMultiplier;
/*     */   public float comboAirSpeedMultiplier;
/*     */   public float baseSpeed;
/*     */   public float climbSpeed;
/*     */   public float climbSpeedLateral;
/*     */   public float climbUpSprintSpeed;
/*     */   public float climbDownSprintSpeed;
/*     */   public float horizontalFlySpeed;
/*     */   public float verticalFlySpeed;
/*     */   public float maxSpeedMultiplier;
/*     */   public float minSpeedMultiplier;
/*     */   public float wishDirectionGravityX;
/*     */   public float wishDirectionGravityY;
/*     */   public float wishDirectionWeightX;
/*     */   public float wishDirectionWeightY;
/*     */   public boolean canFly;
/*     */   public float collisionExpulsionForce;
/*     */   public float forwardWalkSpeedMultiplier;
/*     */   public float backwardWalkSpeedMultiplier;
/*     */   public float strafeWalkSpeedMultiplier;
/*     */   public float forwardRunSpeedMultiplier;
/*     */   public float backwardRunSpeedMultiplier;
/*     */   public float strafeRunSpeedMultiplier;
/*     */   public float forwardCrouchSpeedMultiplier;
/*     */   public float backwardCrouchSpeedMultiplier;
/*     */   public float strafeCrouchSpeedMultiplier;
/*     */   public float forwardSprintSpeedMultiplier;
/*     */   public float variableJumpFallForce;
/*     */   public float fallEffectDuration;
/*     */   public float fallJumpForce;
/*     */   public float fallMomentumLoss;
/*     */   public float autoJumpObstacleSpeedLoss;
/*     */   public float autoJumpObstacleSprintSpeedLoss;
/*     */   public float autoJumpObstacleEffectDuration;
/*     */   public float autoJumpObstacleSprintEffectDuration;
/*     */   public float autoJumpObstacleMaxAngle;
/*     */   public boolean autoJumpDisableJumping;
/*     */   public float minSlideEntrySpeed;
/*     */   public float slideExitSpeed;
/*     */   public float minFallSpeedToEngageRoll;
/*     */   public float maxFallSpeedToEngageRoll;
/*     */   public float rollStartSpeedModifier;
/*     */   public float rollExitSpeedModifier;
/*     */   public float rollTimeToComplete;
/*     */   
/*     */   public MovementSettings() {}
/*     */   
/*     */   public MovementSettings(float mass, float dragCoefficient, boolean invertedGravity, float velocityResistance, float jumpForce, float swimJumpForce, float jumpBufferDuration, float jumpBufferMaxYVelocity, float acceleration, float airDragMin, float airDragMax, float airDragMinSpeed, float airDragMaxSpeed, float airFrictionMin, float airFrictionMax, float airFrictionMinSpeed, float airFrictionMaxSpeed, float airSpeedMultiplier, float airControlMinSpeed, float airControlMaxSpeed, float airControlMinMultiplier, float airControlMaxMultiplier, float comboAirSpeedMultiplier, float baseSpeed, float climbSpeed, float climbSpeedLateral, float climbUpSprintSpeed, float climbDownSprintSpeed, float horizontalFlySpeed, float verticalFlySpeed, float maxSpeedMultiplier, float minSpeedMultiplier, float wishDirectionGravityX, float wishDirectionGravityY, float wishDirectionWeightX, float wishDirectionWeightY, boolean canFly, float collisionExpulsionForce, float forwardWalkSpeedMultiplier, float backwardWalkSpeedMultiplier, float strafeWalkSpeedMultiplier, float forwardRunSpeedMultiplier, float backwardRunSpeedMultiplier, float strafeRunSpeedMultiplier, float forwardCrouchSpeedMultiplier, float backwardCrouchSpeedMultiplier, float strafeCrouchSpeedMultiplier, float forwardSprintSpeedMultiplier, float variableJumpFallForce, float fallEffectDuration, float fallJumpForce, float fallMomentumLoss, float autoJumpObstacleSpeedLoss, float autoJumpObstacleSprintSpeedLoss, float autoJumpObstacleEffectDuration, float autoJumpObstacleSprintEffectDuration, float autoJumpObstacleMaxAngle, boolean autoJumpDisableJumping, float minSlideEntrySpeed, float slideExitSpeed, float minFallSpeedToEngageRoll, float maxFallSpeedToEngageRoll, float rollStartSpeedModifier, float rollExitSpeedModifier, float rollTimeToComplete) {
/*  90 */     this.mass = mass;
/*  91 */     this.dragCoefficient = dragCoefficient;
/*  92 */     this.invertedGravity = invertedGravity;
/*  93 */     this.velocityResistance = velocityResistance;
/*  94 */     this.jumpForce = jumpForce;
/*  95 */     this.swimJumpForce = swimJumpForce;
/*  96 */     this.jumpBufferDuration = jumpBufferDuration;
/*  97 */     this.jumpBufferMaxYVelocity = jumpBufferMaxYVelocity;
/*  98 */     this.acceleration = acceleration;
/*  99 */     this.airDragMin = airDragMin;
/* 100 */     this.airDragMax = airDragMax;
/* 101 */     this.airDragMinSpeed = airDragMinSpeed;
/* 102 */     this.airDragMaxSpeed = airDragMaxSpeed;
/* 103 */     this.airFrictionMin = airFrictionMin;
/* 104 */     this.airFrictionMax = airFrictionMax;
/* 105 */     this.airFrictionMinSpeed = airFrictionMinSpeed;
/* 106 */     this.airFrictionMaxSpeed = airFrictionMaxSpeed;
/* 107 */     this.airSpeedMultiplier = airSpeedMultiplier;
/* 108 */     this.airControlMinSpeed = airControlMinSpeed;
/* 109 */     this.airControlMaxSpeed = airControlMaxSpeed;
/* 110 */     this.airControlMinMultiplier = airControlMinMultiplier;
/* 111 */     this.airControlMaxMultiplier = airControlMaxMultiplier;
/* 112 */     this.comboAirSpeedMultiplier = comboAirSpeedMultiplier;
/* 113 */     this.baseSpeed = baseSpeed;
/* 114 */     this.climbSpeed = climbSpeed;
/* 115 */     this.climbSpeedLateral = climbSpeedLateral;
/* 116 */     this.climbUpSprintSpeed = climbUpSprintSpeed;
/* 117 */     this.climbDownSprintSpeed = climbDownSprintSpeed;
/* 118 */     this.horizontalFlySpeed = horizontalFlySpeed;
/* 119 */     this.verticalFlySpeed = verticalFlySpeed;
/* 120 */     this.maxSpeedMultiplier = maxSpeedMultiplier;
/* 121 */     this.minSpeedMultiplier = minSpeedMultiplier;
/* 122 */     this.wishDirectionGravityX = wishDirectionGravityX;
/* 123 */     this.wishDirectionGravityY = wishDirectionGravityY;
/* 124 */     this.wishDirectionWeightX = wishDirectionWeightX;
/* 125 */     this.wishDirectionWeightY = wishDirectionWeightY;
/* 126 */     this.canFly = canFly;
/* 127 */     this.collisionExpulsionForce = collisionExpulsionForce;
/* 128 */     this.forwardWalkSpeedMultiplier = forwardWalkSpeedMultiplier;
/* 129 */     this.backwardWalkSpeedMultiplier = backwardWalkSpeedMultiplier;
/* 130 */     this.strafeWalkSpeedMultiplier = strafeWalkSpeedMultiplier;
/* 131 */     this.forwardRunSpeedMultiplier = forwardRunSpeedMultiplier;
/* 132 */     this.backwardRunSpeedMultiplier = backwardRunSpeedMultiplier;
/* 133 */     this.strafeRunSpeedMultiplier = strafeRunSpeedMultiplier;
/* 134 */     this.forwardCrouchSpeedMultiplier = forwardCrouchSpeedMultiplier;
/* 135 */     this.backwardCrouchSpeedMultiplier = backwardCrouchSpeedMultiplier;
/* 136 */     this.strafeCrouchSpeedMultiplier = strafeCrouchSpeedMultiplier;
/* 137 */     this.forwardSprintSpeedMultiplier = forwardSprintSpeedMultiplier;
/* 138 */     this.variableJumpFallForce = variableJumpFallForce;
/* 139 */     this.fallEffectDuration = fallEffectDuration;
/* 140 */     this.fallJumpForce = fallJumpForce;
/* 141 */     this.fallMomentumLoss = fallMomentumLoss;
/* 142 */     this.autoJumpObstacleSpeedLoss = autoJumpObstacleSpeedLoss;
/* 143 */     this.autoJumpObstacleSprintSpeedLoss = autoJumpObstacleSprintSpeedLoss;
/* 144 */     this.autoJumpObstacleEffectDuration = autoJumpObstacleEffectDuration;
/* 145 */     this.autoJumpObstacleSprintEffectDuration = autoJumpObstacleSprintEffectDuration;
/* 146 */     this.autoJumpObstacleMaxAngle = autoJumpObstacleMaxAngle;
/* 147 */     this.autoJumpDisableJumping = autoJumpDisableJumping;
/* 148 */     this.minSlideEntrySpeed = minSlideEntrySpeed;
/* 149 */     this.slideExitSpeed = slideExitSpeed;
/* 150 */     this.minFallSpeedToEngageRoll = minFallSpeedToEngageRoll;
/* 151 */     this.maxFallSpeedToEngageRoll = maxFallSpeedToEngageRoll;
/* 152 */     this.rollStartSpeedModifier = rollStartSpeedModifier;
/* 153 */     this.rollExitSpeedModifier = rollExitSpeedModifier;
/* 154 */     this.rollTimeToComplete = rollTimeToComplete;
/*     */   }
/*     */   
/*     */   public MovementSettings(@Nonnull MovementSettings other) {
/* 158 */     this.mass = other.mass;
/* 159 */     this.dragCoefficient = other.dragCoefficient;
/* 160 */     this.invertedGravity = other.invertedGravity;
/* 161 */     this.velocityResistance = other.velocityResistance;
/* 162 */     this.jumpForce = other.jumpForce;
/* 163 */     this.swimJumpForce = other.swimJumpForce;
/* 164 */     this.jumpBufferDuration = other.jumpBufferDuration;
/* 165 */     this.jumpBufferMaxYVelocity = other.jumpBufferMaxYVelocity;
/* 166 */     this.acceleration = other.acceleration;
/* 167 */     this.airDragMin = other.airDragMin;
/* 168 */     this.airDragMax = other.airDragMax;
/* 169 */     this.airDragMinSpeed = other.airDragMinSpeed;
/* 170 */     this.airDragMaxSpeed = other.airDragMaxSpeed;
/* 171 */     this.airFrictionMin = other.airFrictionMin;
/* 172 */     this.airFrictionMax = other.airFrictionMax;
/* 173 */     this.airFrictionMinSpeed = other.airFrictionMinSpeed;
/* 174 */     this.airFrictionMaxSpeed = other.airFrictionMaxSpeed;
/* 175 */     this.airSpeedMultiplier = other.airSpeedMultiplier;
/* 176 */     this.airControlMinSpeed = other.airControlMinSpeed;
/* 177 */     this.airControlMaxSpeed = other.airControlMaxSpeed;
/* 178 */     this.airControlMinMultiplier = other.airControlMinMultiplier;
/* 179 */     this.airControlMaxMultiplier = other.airControlMaxMultiplier;
/* 180 */     this.comboAirSpeedMultiplier = other.comboAirSpeedMultiplier;
/* 181 */     this.baseSpeed = other.baseSpeed;
/* 182 */     this.climbSpeed = other.climbSpeed;
/* 183 */     this.climbSpeedLateral = other.climbSpeedLateral;
/* 184 */     this.climbUpSprintSpeed = other.climbUpSprintSpeed;
/* 185 */     this.climbDownSprintSpeed = other.climbDownSprintSpeed;
/* 186 */     this.horizontalFlySpeed = other.horizontalFlySpeed;
/* 187 */     this.verticalFlySpeed = other.verticalFlySpeed;
/* 188 */     this.maxSpeedMultiplier = other.maxSpeedMultiplier;
/* 189 */     this.minSpeedMultiplier = other.minSpeedMultiplier;
/* 190 */     this.wishDirectionGravityX = other.wishDirectionGravityX;
/* 191 */     this.wishDirectionGravityY = other.wishDirectionGravityY;
/* 192 */     this.wishDirectionWeightX = other.wishDirectionWeightX;
/* 193 */     this.wishDirectionWeightY = other.wishDirectionWeightY;
/* 194 */     this.canFly = other.canFly;
/* 195 */     this.collisionExpulsionForce = other.collisionExpulsionForce;
/* 196 */     this.forwardWalkSpeedMultiplier = other.forwardWalkSpeedMultiplier;
/* 197 */     this.backwardWalkSpeedMultiplier = other.backwardWalkSpeedMultiplier;
/* 198 */     this.strafeWalkSpeedMultiplier = other.strafeWalkSpeedMultiplier;
/* 199 */     this.forwardRunSpeedMultiplier = other.forwardRunSpeedMultiplier;
/* 200 */     this.backwardRunSpeedMultiplier = other.backwardRunSpeedMultiplier;
/* 201 */     this.strafeRunSpeedMultiplier = other.strafeRunSpeedMultiplier;
/* 202 */     this.forwardCrouchSpeedMultiplier = other.forwardCrouchSpeedMultiplier;
/* 203 */     this.backwardCrouchSpeedMultiplier = other.backwardCrouchSpeedMultiplier;
/* 204 */     this.strafeCrouchSpeedMultiplier = other.strafeCrouchSpeedMultiplier;
/* 205 */     this.forwardSprintSpeedMultiplier = other.forwardSprintSpeedMultiplier;
/* 206 */     this.variableJumpFallForce = other.variableJumpFallForce;
/* 207 */     this.fallEffectDuration = other.fallEffectDuration;
/* 208 */     this.fallJumpForce = other.fallJumpForce;
/* 209 */     this.fallMomentumLoss = other.fallMomentumLoss;
/* 210 */     this.autoJumpObstacleSpeedLoss = other.autoJumpObstacleSpeedLoss;
/* 211 */     this.autoJumpObstacleSprintSpeedLoss = other.autoJumpObstacleSprintSpeedLoss;
/* 212 */     this.autoJumpObstacleEffectDuration = other.autoJumpObstacleEffectDuration;
/* 213 */     this.autoJumpObstacleSprintEffectDuration = other.autoJumpObstacleSprintEffectDuration;
/* 214 */     this.autoJumpObstacleMaxAngle = other.autoJumpObstacleMaxAngle;
/* 215 */     this.autoJumpDisableJumping = other.autoJumpDisableJumping;
/* 216 */     this.minSlideEntrySpeed = other.minSlideEntrySpeed;
/* 217 */     this.slideExitSpeed = other.slideExitSpeed;
/* 218 */     this.minFallSpeedToEngageRoll = other.minFallSpeedToEngageRoll;
/* 219 */     this.maxFallSpeedToEngageRoll = other.maxFallSpeedToEngageRoll;
/* 220 */     this.rollStartSpeedModifier = other.rollStartSpeedModifier;
/* 221 */     this.rollExitSpeedModifier = other.rollExitSpeedModifier;
/* 222 */     this.rollTimeToComplete = other.rollTimeToComplete;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static MovementSettings deserialize(@Nonnull ByteBuf buf, int offset) {
/* 227 */     MovementSettings obj = new MovementSettings();
/*     */     
/* 229 */     obj.mass = buf.getFloatLE(offset + 0);
/* 230 */     obj.dragCoefficient = buf.getFloatLE(offset + 4);
/* 231 */     obj.invertedGravity = (buf.getByte(offset + 8) != 0);
/* 232 */     obj.velocityResistance = buf.getFloatLE(offset + 9);
/* 233 */     obj.jumpForce = buf.getFloatLE(offset + 13);
/* 234 */     obj.swimJumpForce = buf.getFloatLE(offset + 17);
/* 235 */     obj.jumpBufferDuration = buf.getFloatLE(offset + 21);
/* 236 */     obj.jumpBufferMaxYVelocity = buf.getFloatLE(offset + 25);
/* 237 */     obj.acceleration = buf.getFloatLE(offset + 29);
/* 238 */     obj.airDragMin = buf.getFloatLE(offset + 33);
/* 239 */     obj.airDragMax = buf.getFloatLE(offset + 37);
/* 240 */     obj.airDragMinSpeed = buf.getFloatLE(offset + 41);
/* 241 */     obj.airDragMaxSpeed = buf.getFloatLE(offset + 45);
/* 242 */     obj.airFrictionMin = buf.getFloatLE(offset + 49);
/* 243 */     obj.airFrictionMax = buf.getFloatLE(offset + 53);
/* 244 */     obj.airFrictionMinSpeed = buf.getFloatLE(offset + 57);
/* 245 */     obj.airFrictionMaxSpeed = buf.getFloatLE(offset + 61);
/* 246 */     obj.airSpeedMultiplier = buf.getFloatLE(offset + 65);
/* 247 */     obj.airControlMinSpeed = buf.getFloatLE(offset + 69);
/* 248 */     obj.airControlMaxSpeed = buf.getFloatLE(offset + 73);
/* 249 */     obj.airControlMinMultiplier = buf.getFloatLE(offset + 77);
/* 250 */     obj.airControlMaxMultiplier = buf.getFloatLE(offset + 81);
/* 251 */     obj.comboAirSpeedMultiplier = buf.getFloatLE(offset + 85);
/* 252 */     obj.baseSpeed = buf.getFloatLE(offset + 89);
/* 253 */     obj.climbSpeed = buf.getFloatLE(offset + 93);
/* 254 */     obj.climbSpeedLateral = buf.getFloatLE(offset + 97);
/* 255 */     obj.climbUpSprintSpeed = buf.getFloatLE(offset + 101);
/* 256 */     obj.climbDownSprintSpeed = buf.getFloatLE(offset + 105);
/* 257 */     obj.horizontalFlySpeed = buf.getFloatLE(offset + 109);
/* 258 */     obj.verticalFlySpeed = buf.getFloatLE(offset + 113);
/* 259 */     obj.maxSpeedMultiplier = buf.getFloatLE(offset + 117);
/* 260 */     obj.minSpeedMultiplier = buf.getFloatLE(offset + 121);
/* 261 */     obj.wishDirectionGravityX = buf.getFloatLE(offset + 125);
/* 262 */     obj.wishDirectionGravityY = buf.getFloatLE(offset + 129);
/* 263 */     obj.wishDirectionWeightX = buf.getFloatLE(offset + 133);
/* 264 */     obj.wishDirectionWeightY = buf.getFloatLE(offset + 137);
/* 265 */     obj.canFly = (buf.getByte(offset + 141) != 0);
/* 266 */     obj.collisionExpulsionForce = buf.getFloatLE(offset + 142);
/* 267 */     obj.forwardWalkSpeedMultiplier = buf.getFloatLE(offset + 146);
/* 268 */     obj.backwardWalkSpeedMultiplier = buf.getFloatLE(offset + 150);
/* 269 */     obj.strafeWalkSpeedMultiplier = buf.getFloatLE(offset + 154);
/* 270 */     obj.forwardRunSpeedMultiplier = buf.getFloatLE(offset + 158);
/* 271 */     obj.backwardRunSpeedMultiplier = buf.getFloatLE(offset + 162);
/* 272 */     obj.strafeRunSpeedMultiplier = buf.getFloatLE(offset + 166);
/* 273 */     obj.forwardCrouchSpeedMultiplier = buf.getFloatLE(offset + 170);
/* 274 */     obj.backwardCrouchSpeedMultiplier = buf.getFloatLE(offset + 174);
/* 275 */     obj.strafeCrouchSpeedMultiplier = buf.getFloatLE(offset + 178);
/* 276 */     obj.forwardSprintSpeedMultiplier = buf.getFloatLE(offset + 182);
/* 277 */     obj.variableJumpFallForce = buf.getFloatLE(offset + 186);
/* 278 */     obj.fallEffectDuration = buf.getFloatLE(offset + 190);
/* 279 */     obj.fallJumpForce = buf.getFloatLE(offset + 194);
/* 280 */     obj.fallMomentumLoss = buf.getFloatLE(offset + 198);
/* 281 */     obj.autoJumpObstacleSpeedLoss = buf.getFloatLE(offset + 202);
/* 282 */     obj.autoJumpObstacleSprintSpeedLoss = buf.getFloatLE(offset + 206);
/* 283 */     obj.autoJumpObstacleEffectDuration = buf.getFloatLE(offset + 210);
/* 284 */     obj.autoJumpObstacleSprintEffectDuration = buf.getFloatLE(offset + 214);
/* 285 */     obj.autoJumpObstacleMaxAngle = buf.getFloatLE(offset + 218);
/* 286 */     obj.autoJumpDisableJumping = (buf.getByte(offset + 222) != 0);
/* 287 */     obj.minSlideEntrySpeed = buf.getFloatLE(offset + 223);
/* 288 */     obj.slideExitSpeed = buf.getFloatLE(offset + 227);
/* 289 */     obj.minFallSpeedToEngageRoll = buf.getFloatLE(offset + 231);
/* 290 */     obj.maxFallSpeedToEngageRoll = buf.getFloatLE(offset + 235);
/* 291 */     obj.rollStartSpeedModifier = buf.getFloatLE(offset + 239);
/* 292 */     obj.rollExitSpeedModifier = buf.getFloatLE(offset + 243);
/* 293 */     obj.rollTimeToComplete = buf.getFloatLE(offset + 247);
/*     */ 
/*     */     
/* 296 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 300 */     return 251;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 305 */     buf.writeFloatLE(this.mass);
/* 306 */     buf.writeFloatLE(this.dragCoefficient);
/* 307 */     buf.writeByte(this.invertedGravity ? 1 : 0);
/* 308 */     buf.writeFloatLE(this.velocityResistance);
/* 309 */     buf.writeFloatLE(this.jumpForce);
/* 310 */     buf.writeFloatLE(this.swimJumpForce);
/* 311 */     buf.writeFloatLE(this.jumpBufferDuration);
/* 312 */     buf.writeFloatLE(this.jumpBufferMaxYVelocity);
/* 313 */     buf.writeFloatLE(this.acceleration);
/* 314 */     buf.writeFloatLE(this.airDragMin);
/* 315 */     buf.writeFloatLE(this.airDragMax);
/* 316 */     buf.writeFloatLE(this.airDragMinSpeed);
/* 317 */     buf.writeFloatLE(this.airDragMaxSpeed);
/* 318 */     buf.writeFloatLE(this.airFrictionMin);
/* 319 */     buf.writeFloatLE(this.airFrictionMax);
/* 320 */     buf.writeFloatLE(this.airFrictionMinSpeed);
/* 321 */     buf.writeFloatLE(this.airFrictionMaxSpeed);
/* 322 */     buf.writeFloatLE(this.airSpeedMultiplier);
/* 323 */     buf.writeFloatLE(this.airControlMinSpeed);
/* 324 */     buf.writeFloatLE(this.airControlMaxSpeed);
/* 325 */     buf.writeFloatLE(this.airControlMinMultiplier);
/* 326 */     buf.writeFloatLE(this.airControlMaxMultiplier);
/* 327 */     buf.writeFloatLE(this.comboAirSpeedMultiplier);
/* 328 */     buf.writeFloatLE(this.baseSpeed);
/* 329 */     buf.writeFloatLE(this.climbSpeed);
/* 330 */     buf.writeFloatLE(this.climbSpeedLateral);
/* 331 */     buf.writeFloatLE(this.climbUpSprintSpeed);
/* 332 */     buf.writeFloatLE(this.climbDownSprintSpeed);
/* 333 */     buf.writeFloatLE(this.horizontalFlySpeed);
/* 334 */     buf.writeFloatLE(this.verticalFlySpeed);
/* 335 */     buf.writeFloatLE(this.maxSpeedMultiplier);
/* 336 */     buf.writeFloatLE(this.minSpeedMultiplier);
/* 337 */     buf.writeFloatLE(this.wishDirectionGravityX);
/* 338 */     buf.writeFloatLE(this.wishDirectionGravityY);
/* 339 */     buf.writeFloatLE(this.wishDirectionWeightX);
/* 340 */     buf.writeFloatLE(this.wishDirectionWeightY);
/* 341 */     buf.writeByte(this.canFly ? 1 : 0);
/* 342 */     buf.writeFloatLE(this.collisionExpulsionForce);
/* 343 */     buf.writeFloatLE(this.forwardWalkSpeedMultiplier);
/* 344 */     buf.writeFloatLE(this.backwardWalkSpeedMultiplier);
/* 345 */     buf.writeFloatLE(this.strafeWalkSpeedMultiplier);
/* 346 */     buf.writeFloatLE(this.forwardRunSpeedMultiplier);
/* 347 */     buf.writeFloatLE(this.backwardRunSpeedMultiplier);
/* 348 */     buf.writeFloatLE(this.strafeRunSpeedMultiplier);
/* 349 */     buf.writeFloatLE(this.forwardCrouchSpeedMultiplier);
/* 350 */     buf.writeFloatLE(this.backwardCrouchSpeedMultiplier);
/* 351 */     buf.writeFloatLE(this.strafeCrouchSpeedMultiplier);
/* 352 */     buf.writeFloatLE(this.forwardSprintSpeedMultiplier);
/* 353 */     buf.writeFloatLE(this.variableJumpFallForce);
/* 354 */     buf.writeFloatLE(this.fallEffectDuration);
/* 355 */     buf.writeFloatLE(this.fallJumpForce);
/* 356 */     buf.writeFloatLE(this.fallMomentumLoss);
/* 357 */     buf.writeFloatLE(this.autoJumpObstacleSpeedLoss);
/* 358 */     buf.writeFloatLE(this.autoJumpObstacleSprintSpeedLoss);
/* 359 */     buf.writeFloatLE(this.autoJumpObstacleEffectDuration);
/* 360 */     buf.writeFloatLE(this.autoJumpObstacleSprintEffectDuration);
/* 361 */     buf.writeFloatLE(this.autoJumpObstacleMaxAngle);
/* 362 */     buf.writeByte(this.autoJumpDisableJumping ? 1 : 0);
/* 363 */     buf.writeFloatLE(this.minSlideEntrySpeed);
/* 364 */     buf.writeFloatLE(this.slideExitSpeed);
/* 365 */     buf.writeFloatLE(this.minFallSpeedToEngageRoll);
/* 366 */     buf.writeFloatLE(this.maxFallSpeedToEngageRoll);
/* 367 */     buf.writeFloatLE(this.rollStartSpeedModifier);
/* 368 */     buf.writeFloatLE(this.rollExitSpeedModifier);
/* 369 */     buf.writeFloatLE(this.rollTimeToComplete);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 375 */     return 251;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 379 */     if (buffer.readableBytes() - offset < 251) {
/* 380 */       return ValidationResult.error("Buffer too small: expected at least 251 bytes");
/*     */     }
/*     */ 
/*     */     
/* 384 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public MovementSettings clone() {
/* 388 */     MovementSettings copy = new MovementSettings();
/* 389 */     copy.mass = this.mass;
/* 390 */     copy.dragCoefficient = this.dragCoefficient;
/* 391 */     copy.invertedGravity = this.invertedGravity;
/* 392 */     copy.velocityResistance = this.velocityResistance;
/* 393 */     copy.jumpForce = this.jumpForce;
/* 394 */     copy.swimJumpForce = this.swimJumpForce;
/* 395 */     copy.jumpBufferDuration = this.jumpBufferDuration;
/* 396 */     copy.jumpBufferMaxYVelocity = this.jumpBufferMaxYVelocity;
/* 397 */     copy.acceleration = this.acceleration;
/* 398 */     copy.airDragMin = this.airDragMin;
/* 399 */     copy.airDragMax = this.airDragMax;
/* 400 */     copy.airDragMinSpeed = this.airDragMinSpeed;
/* 401 */     copy.airDragMaxSpeed = this.airDragMaxSpeed;
/* 402 */     copy.airFrictionMin = this.airFrictionMin;
/* 403 */     copy.airFrictionMax = this.airFrictionMax;
/* 404 */     copy.airFrictionMinSpeed = this.airFrictionMinSpeed;
/* 405 */     copy.airFrictionMaxSpeed = this.airFrictionMaxSpeed;
/* 406 */     copy.airSpeedMultiplier = this.airSpeedMultiplier;
/* 407 */     copy.airControlMinSpeed = this.airControlMinSpeed;
/* 408 */     copy.airControlMaxSpeed = this.airControlMaxSpeed;
/* 409 */     copy.airControlMinMultiplier = this.airControlMinMultiplier;
/* 410 */     copy.airControlMaxMultiplier = this.airControlMaxMultiplier;
/* 411 */     copy.comboAirSpeedMultiplier = this.comboAirSpeedMultiplier;
/* 412 */     copy.baseSpeed = this.baseSpeed;
/* 413 */     copy.climbSpeed = this.climbSpeed;
/* 414 */     copy.climbSpeedLateral = this.climbSpeedLateral;
/* 415 */     copy.climbUpSprintSpeed = this.climbUpSprintSpeed;
/* 416 */     copy.climbDownSprintSpeed = this.climbDownSprintSpeed;
/* 417 */     copy.horizontalFlySpeed = this.horizontalFlySpeed;
/* 418 */     copy.verticalFlySpeed = this.verticalFlySpeed;
/* 419 */     copy.maxSpeedMultiplier = this.maxSpeedMultiplier;
/* 420 */     copy.minSpeedMultiplier = this.minSpeedMultiplier;
/* 421 */     copy.wishDirectionGravityX = this.wishDirectionGravityX;
/* 422 */     copy.wishDirectionGravityY = this.wishDirectionGravityY;
/* 423 */     copy.wishDirectionWeightX = this.wishDirectionWeightX;
/* 424 */     copy.wishDirectionWeightY = this.wishDirectionWeightY;
/* 425 */     copy.canFly = this.canFly;
/* 426 */     copy.collisionExpulsionForce = this.collisionExpulsionForce;
/* 427 */     copy.forwardWalkSpeedMultiplier = this.forwardWalkSpeedMultiplier;
/* 428 */     copy.backwardWalkSpeedMultiplier = this.backwardWalkSpeedMultiplier;
/* 429 */     copy.strafeWalkSpeedMultiplier = this.strafeWalkSpeedMultiplier;
/* 430 */     copy.forwardRunSpeedMultiplier = this.forwardRunSpeedMultiplier;
/* 431 */     copy.backwardRunSpeedMultiplier = this.backwardRunSpeedMultiplier;
/* 432 */     copy.strafeRunSpeedMultiplier = this.strafeRunSpeedMultiplier;
/* 433 */     copy.forwardCrouchSpeedMultiplier = this.forwardCrouchSpeedMultiplier;
/* 434 */     copy.backwardCrouchSpeedMultiplier = this.backwardCrouchSpeedMultiplier;
/* 435 */     copy.strafeCrouchSpeedMultiplier = this.strafeCrouchSpeedMultiplier;
/* 436 */     copy.forwardSprintSpeedMultiplier = this.forwardSprintSpeedMultiplier;
/* 437 */     copy.variableJumpFallForce = this.variableJumpFallForce;
/* 438 */     copy.fallEffectDuration = this.fallEffectDuration;
/* 439 */     copy.fallJumpForce = this.fallJumpForce;
/* 440 */     copy.fallMomentumLoss = this.fallMomentumLoss;
/* 441 */     copy.autoJumpObstacleSpeedLoss = this.autoJumpObstacleSpeedLoss;
/* 442 */     copy.autoJumpObstacleSprintSpeedLoss = this.autoJumpObstacleSprintSpeedLoss;
/* 443 */     copy.autoJumpObstacleEffectDuration = this.autoJumpObstacleEffectDuration;
/* 444 */     copy.autoJumpObstacleSprintEffectDuration = this.autoJumpObstacleSprintEffectDuration;
/* 445 */     copy.autoJumpObstacleMaxAngle = this.autoJumpObstacleMaxAngle;
/* 446 */     copy.autoJumpDisableJumping = this.autoJumpDisableJumping;
/* 447 */     copy.minSlideEntrySpeed = this.minSlideEntrySpeed;
/* 448 */     copy.slideExitSpeed = this.slideExitSpeed;
/* 449 */     copy.minFallSpeedToEngageRoll = this.minFallSpeedToEngageRoll;
/* 450 */     copy.maxFallSpeedToEngageRoll = this.maxFallSpeedToEngageRoll;
/* 451 */     copy.rollStartSpeedModifier = this.rollStartSpeedModifier;
/* 452 */     copy.rollExitSpeedModifier = this.rollExitSpeedModifier;
/* 453 */     copy.rollTimeToComplete = this.rollTimeToComplete;
/* 454 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     MovementSettings other;
/* 460 */     if (this == obj) return true; 
/* 461 */     if (obj instanceof MovementSettings) { other = (MovementSettings)obj; } else { return false; }
/* 462 */      return (this.mass == other.mass && this.dragCoefficient == other.dragCoefficient && this.invertedGravity == other.invertedGravity && this.velocityResistance == other.velocityResistance && this.jumpForce == other.jumpForce && this.swimJumpForce == other.swimJumpForce && this.jumpBufferDuration == other.jumpBufferDuration && this.jumpBufferMaxYVelocity == other.jumpBufferMaxYVelocity && this.acceleration == other.acceleration && this.airDragMin == other.airDragMin && this.airDragMax == other.airDragMax && this.airDragMinSpeed == other.airDragMinSpeed && this.airDragMaxSpeed == other.airDragMaxSpeed && this.airFrictionMin == other.airFrictionMin && this.airFrictionMax == other.airFrictionMax && this.airFrictionMinSpeed == other.airFrictionMinSpeed && this.airFrictionMaxSpeed == other.airFrictionMaxSpeed && this.airSpeedMultiplier == other.airSpeedMultiplier && this.airControlMinSpeed == other.airControlMinSpeed && this.airControlMaxSpeed == other.airControlMaxSpeed && this.airControlMinMultiplier == other.airControlMinMultiplier && this.airControlMaxMultiplier == other.airControlMaxMultiplier && this.comboAirSpeedMultiplier == other.comboAirSpeedMultiplier && this.baseSpeed == other.baseSpeed && this.climbSpeed == other.climbSpeed && this.climbSpeedLateral == other.climbSpeedLateral && this.climbUpSprintSpeed == other.climbUpSprintSpeed && this.climbDownSprintSpeed == other.climbDownSprintSpeed && this.horizontalFlySpeed == other.horizontalFlySpeed && this.verticalFlySpeed == other.verticalFlySpeed && this.maxSpeedMultiplier == other.maxSpeedMultiplier && this.minSpeedMultiplier == other.minSpeedMultiplier && this.wishDirectionGravityX == other.wishDirectionGravityX && this.wishDirectionGravityY == other.wishDirectionGravityY && this.wishDirectionWeightX == other.wishDirectionWeightX && this.wishDirectionWeightY == other.wishDirectionWeightY && this.canFly == other.canFly && this.collisionExpulsionForce == other.collisionExpulsionForce && this.forwardWalkSpeedMultiplier == other.forwardWalkSpeedMultiplier && this.backwardWalkSpeedMultiplier == other.backwardWalkSpeedMultiplier && this.strafeWalkSpeedMultiplier == other.strafeWalkSpeedMultiplier && this.forwardRunSpeedMultiplier == other.forwardRunSpeedMultiplier && this.backwardRunSpeedMultiplier == other.backwardRunSpeedMultiplier && this.strafeRunSpeedMultiplier == other.strafeRunSpeedMultiplier && this.forwardCrouchSpeedMultiplier == other.forwardCrouchSpeedMultiplier && this.backwardCrouchSpeedMultiplier == other.backwardCrouchSpeedMultiplier && this.strafeCrouchSpeedMultiplier == other.strafeCrouchSpeedMultiplier && this.forwardSprintSpeedMultiplier == other.forwardSprintSpeedMultiplier && this.variableJumpFallForce == other.variableJumpFallForce && this.fallEffectDuration == other.fallEffectDuration && this.fallJumpForce == other.fallJumpForce && this.fallMomentumLoss == other.fallMomentumLoss && this.autoJumpObstacleSpeedLoss == other.autoJumpObstacleSpeedLoss && this.autoJumpObstacleSprintSpeedLoss == other.autoJumpObstacleSprintSpeedLoss && this.autoJumpObstacleEffectDuration == other.autoJumpObstacleEffectDuration && this.autoJumpObstacleSprintEffectDuration == other.autoJumpObstacleSprintEffectDuration && this.autoJumpObstacleMaxAngle == other.autoJumpObstacleMaxAngle && this.autoJumpDisableJumping == other.autoJumpDisableJumping && this.minSlideEntrySpeed == other.minSlideEntrySpeed && this.slideExitSpeed == other.slideExitSpeed && this.minFallSpeedToEngageRoll == other.minFallSpeedToEngageRoll && this.maxFallSpeedToEngageRoll == other.maxFallSpeedToEngageRoll && this.rollStartSpeedModifier == other.rollStartSpeedModifier && this.rollExitSpeedModifier == other.rollExitSpeedModifier && this.rollTimeToComplete == other.rollTimeToComplete);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 467 */     return Objects.hash(new Object[] { Float.valueOf(this.mass), Float.valueOf(this.dragCoefficient), Boolean.valueOf(this.invertedGravity), Float.valueOf(this.velocityResistance), Float.valueOf(this.jumpForce), Float.valueOf(this.swimJumpForce), Float.valueOf(this.jumpBufferDuration), Float.valueOf(this.jumpBufferMaxYVelocity), Float.valueOf(this.acceleration), Float.valueOf(this.airDragMin), Float.valueOf(this.airDragMax), Float.valueOf(this.airDragMinSpeed), Float.valueOf(this.airDragMaxSpeed), Float.valueOf(this.airFrictionMin), Float.valueOf(this.airFrictionMax), Float.valueOf(this.airFrictionMinSpeed), Float.valueOf(this.airFrictionMaxSpeed), Float.valueOf(this.airSpeedMultiplier), Float.valueOf(this.airControlMinSpeed), Float.valueOf(this.airControlMaxSpeed), Float.valueOf(this.airControlMinMultiplier), Float.valueOf(this.airControlMaxMultiplier), Float.valueOf(this.comboAirSpeedMultiplier), Float.valueOf(this.baseSpeed), Float.valueOf(this.climbSpeed), Float.valueOf(this.climbSpeedLateral), Float.valueOf(this.climbUpSprintSpeed), Float.valueOf(this.climbDownSprintSpeed), Float.valueOf(this.horizontalFlySpeed), Float.valueOf(this.verticalFlySpeed), Float.valueOf(this.maxSpeedMultiplier), Float.valueOf(this.minSpeedMultiplier), Float.valueOf(this.wishDirectionGravityX), Float.valueOf(this.wishDirectionGravityY), Float.valueOf(this.wishDirectionWeightX), Float.valueOf(this.wishDirectionWeightY), Boolean.valueOf(this.canFly), Float.valueOf(this.collisionExpulsionForce), Float.valueOf(this.forwardWalkSpeedMultiplier), Float.valueOf(this.backwardWalkSpeedMultiplier), Float.valueOf(this.strafeWalkSpeedMultiplier), Float.valueOf(this.forwardRunSpeedMultiplier), Float.valueOf(this.backwardRunSpeedMultiplier), Float.valueOf(this.strafeRunSpeedMultiplier), Float.valueOf(this.forwardCrouchSpeedMultiplier), Float.valueOf(this.backwardCrouchSpeedMultiplier), Float.valueOf(this.strafeCrouchSpeedMultiplier), Float.valueOf(this.forwardSprintSpeedMultiplier), Float.valueOf(this.variableJumpFallForce), Float.valueOf(this.fallEffectDuration), Float.valueOf(this.fallJumpForce), Float.valueOf(this.fallMomentumLoss), Float.valueOf(this.autoJumpObstacleSpeedLoss), Float.valueOf(this.autoJumpObstacleSprintSpeedLoss), Float.valueOf(this.autoJumpObstacleEffectDuration), Float.valueOf(this.autoJumpObstacleSprintEffectDuration), Float.valueOf(this.autoJumpObstacleMaxAngle), Boolean.valueOf(this.autoJumpDisableJumping), Float.valueOf(this.minSlideEntrySpeed), Float.valueOf(this.slideExitSpeed), Float.valueOf(this.minFallSpeedToEngageRoll), Float.valueOf(this.maxFallSpeedToEngageRoll), Float.valueOf(this.rollStartSpeedModifier), Float.valueOf(this.rollExitSpeedModifier), Float.valueOf(this.rollTimeToComplete) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\MovementSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */