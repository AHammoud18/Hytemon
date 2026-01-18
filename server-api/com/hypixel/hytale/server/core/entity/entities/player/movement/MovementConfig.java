/*     */ package com.hypixel.hytale.server.core.entity.entities.player.movement;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.protocol.MovementSettings;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MovementConfig
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, MovementConfig>>, NetworkSerializable<MovementSettings>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, MovementConfig> CODEC;
/*     */   
/*     */   static {
/* 409 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(MovementConfig.class, MovementConfig::new, (Codec)Codec.STRING, (movementConfig, s) -> movementConfig.id = s, movementConfig -> movementConfig.id, (movementConfig, data) -> movementConfig.extraData = data, movementConfig -> movementConfig.extraData).appendInherited(new KeyedCodec("VelocityResistance", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.velocityResistance = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.velocityResistance), (movementConfig, parent) -> movementConfig.velocityResistance = parent.velocityResistance).add()).appendInherited(new KeyedCodec("JumpForce", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.jumpForce = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.jumpForce), (movementConfig, parent) -> movementConfig.jumpForce = parent.jumpForce).add()).appendInherited(new KeyedCodec("SwimJumpForce", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.swimJumpForce = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.swimJumpForce), (movementConfig, parent) -> movementConfig.swimJumpForce = parent.swimJumpForce).add()).appendInherited(new KeyedCodec("JumpBufferDuration", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.jumpBufferDuration = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.jumpBufferDuration), (movementConfig, parent) -> movementConfig.jumpBufferDuration = parent.jumpBufferDuration).add()).appendInherited(new KeyedCodec("JumpBufferMaxYVelocity", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.jumpBufferMaxYVelocity = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.jumpBufferMaxYVelocity), (movementConfig, parent) -> movementConfig.jumpBufferMaxYVelocity = parent.jumpBufferMaxYVelocity).add()).appendInherited(new KeyedCodec("Acceleration", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.acceleration = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.acceleration), (movementConfig, parent) -> movementConfig.acceleration = parent.acceleration).add()).appendInherited(new KeyedCodec("AirDragMin", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.airDragMin = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.airDragMin), (movementConfig, parent) -> movementConfig.airDragMin = parent.airDragMin).add()).appendInherited(new KeyedCodec("AirDragMax", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.airDragMax = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.airDragMax), (movementConfig, parent) -> movementConfig.airDragMax = parent.airDragMax).add()).appendInherited(new KeyedCodec("AirDragMinSpeed", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.airDragMinSpeed = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.airDragMinSpeed), (movementConfig, parent) -> movementConfig.airDragMinSpeed = parent.airDragMinSpeed).add()).appendInherited(new KeyedCodec("AirDragMaxSpeed", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.airDragMaxSpeed = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.airDragMaxSpeed), (movementConfig, parent) -> movementConfig.airDragMaxSpeed = parent.airDragMaxSpeed).add()).appendInherited(new KeyedCodec("AirFrictionMin", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.airFrictionMin = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.airFrictionMin), (movementConfig, parent) -> movementConfig.airFrictionMin = parent.airFrictionMin).add()).appendInherited(new KeyedCodec("AirFrictionMax", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.airFrictionMax = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.airFrictionMax), (movementConfig, parent) -> movementConfig.airFrictionMax = parent.airFrictionMax).add()).appendInherited(new KeyedCodec("AirFrictionMinSpeed", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.airFrictionMinSpeed = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.airFrictionMinSpeed), (movementConfig, parent) -> movementConfig.airFrictionMinSpeed = parent.airFrictionMinSpeed).add()).appendInherited(new KeyedCodec("AirFrictionMaxSpeed", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.airFrictionMaxSpeed = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.airFrictionMaxSpeed), (movementConfig, parent) -> movementConfig.airFrictionMaxSpeed = parent.airFrictionMaxSpeed).add()).appendInherited(new KeyedCodec("AirSpeedMultiplier", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.airSpeedMultiplier = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.airSpeedMultiplier), (movementConfig, parent) -> movementConfig.airSpeedMultiplier = parent.airSpeedMultiplier).add()).appendInherited(new KeyedCodec("AirControlMinSpeed", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.airControlMinSpeed = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.airControlMinSpeed), (movementConfig, parent) -> movementConfig.airControlMinSpeed = parent.airControlMinSpeed).add()).appendInherited(new KeyedCodec("AirControlMaxSpeed", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.airControlMaxSpeed = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.airControlMaxSpeed), (movementConfig, parent) -> movementConfig.airControlMaxSpeed = parent.airControlMaxSpeed).add()).appendInherited(new KeyedCodec("AirControlMinMultiplier", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.airControlMinMultiplier = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.airControlMinMultiplier), (movementConfig, parent) -> movementConfig.airControlMinMultiplier = parent.airControlMinMultiplier).add()).appendInherited(new KeyedCodec("AirControlMaxMultiplier", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.airControlMaxMultiplier = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.airControlMaxMultiplier), (movementConfig, parent) -> movementConfig.airControlMaxMultiplier = parent.airControlMaxMultiplier).add()).appendInherited(new KeyedCodec("ComboAirSpeedMultiplier", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.comboAirSpeedMultiplier = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.comboAirSpeedMultiplier), (movementConfig, parent) -> movementConfig.comboAirSpeedMultiplier = parent.comboAirSpeedMultiplier).add()).appendInherited(new KeyedCodec("BaseSpeed", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.baseSpeed = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.baseSpeed), (movementConfig, parent) -> movementConfig.baseSpeed = parent.baseSpeed).add()).appendInherited(new KeyedCodec("ClimbSpeed", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.climbSpeed = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.climbSpeed), (movementConfig, parent) -> movementConfig.climbSpeed = parent.climbSpeed).add()).appendInherited(new KeyedCodec("ClimbSpeedLateral", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.climbSpeedLateral = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.climbSpeedLateral), (movementConfig, parent) -> movementConfig.climbSpeedLateral = parent.climbSpeedLateral).add()).appendInherited(new KeyedCodec("ClimbUpSprintSpeed", (Codec)Codec.FLOAT), (movementConfig, aFloat) -> movementConfig.climbUpSprintSpeed = aFloat.floatValue(), movementConfig -> Float.valueOf(movementConfig.climbUpSprintSpeed), (movementConfig, parent) -> movementConfig.climbUpSprintSpeed = parent.climbUpSprintSpeed).add()).appendInherited(new KeyedCodec("ClimbDownSprintSpeed", (Codec)Codec.FLOAT), (movementConfig, aFloat) -> movementConfig.climbDownSprintSpeed = aFloat.floatValue(), movementConfig -> Float.valueOf(movementConfig.climbDownSprintSpeed), (movementConfig, parent) -> movementConfig.climbDownSprintSpeed = parent.climbDownSprintSpeed).add()).appendInherited(new KeyedCodec("HorizontalFlySpeed", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.horizontalFlySpeed = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.horizontalFlySpeed), (movementConfig, parent) -> movementConfig.horizontalFlySpeed = parent.horizontalFlySpeed).add()).appendInherited(new KeyedCodec("VerticalFlySpeed", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.verticalFlySpeed = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.verticalFlySpeed), (movementConfig, parent) -> movementConfig.verticalFlySpeed = parent.verticalFlySpeed).add()).appendInherited(new KeyedCodec("MaxSpeedMultiplier", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.maxSpeedMultiplier = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.maxSpeedMultiplier), (movementConfig, parent) -> movementConfig.maxSpeedMultiplier = parent.maxSpeedMultiplier).add()).appendInherited(new KeyedCodec("MinSpeedMultiplier", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.minSpeedMultiplier = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.minSpeedMultiplier), (movementConfig, parent) -> movementConfig.minSpeedMultiplier = parent.minSpeedMultiplier).add()).appendInherited(new KeyedCodec("WishDirectionGravityX", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.wishDirectionGravityX = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.wishDirectionGravityX), (movementConfig, parent) -> movementConfig.wishDirectionGravityX = parent.wishDirectionGravityX).add()).appendInherited(new KeyedCodec("WishDirectionGravityY", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.wishDirectionGravityY = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.wishDirectionGravityY), (movementConfig, parent) -> movementConfig.wishDirectionGravityY = parent.wishDirectionGravityY).add()).appendInherited(new KeyedCodec("WishDirectionWeightX", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.wishDirectionWeightX = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.wishDirectionWeightX), (movementConfig, parent) -> movementConfig.wishDirectionWeightX = parent.wishDirectionWeightX).add()).appendInherited(new KeyedCodec("WishDirectionWeightY", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.wishDirectionWeightY = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.wishDirectionWeightY), (movementConfig, parent) -> movementConfig.wishDirectionWeightY = parent.wishDirectionWeightY).add()).appendInherited(new KeyedCodec("CollisionExpulsionForce", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.collisionExpulsionForce = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.collisionExpulsionForce), (movementConfig, parent) -> movementConfig.collisionExpulsionForce = parent.collisionExpulsionForce).add()).appendInherited(new KeyedCodec("ForwardWalkSpeedMultiplier", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.forwardWalkSpeedMultiplier = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.forwardWalkSpeedMultiplier), (movementConfig, parent) -> movementConfig.forwardWalkSpeedMultiplier = parent.forwardWalkSpeedMultiplier).add()).appendInherited(new KeyedCodec("BackwardWalkSpeedMultiplier", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.backwardWalkSpeedMultiplier = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.backwardWalkSpeedMultiplier), (movementConfig, parent) -> movementConfig.backwardWalkSpeedMultiplier = parent.backwardWalkSpeedMultiplier).add()).appendInherited(new KeyedCodec("StrafeWalkSpeedMultiplier", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.strafeWalkSpeedMultiplier = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.strafeWalkSpeedMultiplier), (movementConfig, parent) -> movementConfig.strafeWalkSpeedMultiplier = parent.strafeWalkSpeedMultiplier).add()).appendInherited(new KeyedCodec("ForwardRunSpeedMultiplier", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.forwardRunSpeedMultiplier = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.forwardRunSpeedMultiplier), (movementConfig, parent) -> movementConfig.forwardRunSpeedMultiplier = parent.forwardRunSpeedMultiplier).add()).appendInherited(new KeyedCodec("BackwardRunSpeedMultiplier", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.backwardRunSpeedMultiplier = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.backwardRunSpeedMultiplier), (movementConfig, parent) -> movementConfig.backwardRunSpeedMultiplier = parent.backwardRunSpeedMultiplier).add()).appendInherited(new KeyedCodec("StrafeRunSpeedMultiplier", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.strafeRunSpeedMultiplier = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.strafeRunSpeedMultiplier), (movementConfig, parent) -> movementConfig.strafeRunSpeedMultiplier = parent.strafeRunSpeedMultiplier).add()).appendInherited(new KeyedCodec("ForwardCrouchSpeedMultiplier", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.forwardCrouchSpeedMultiplier = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.forwardCrouchSpeedMultiplier), (movementConfig, parent) -> movementConfig.forwardCrouchSpeedMultiplier = parent.forwardCrouchSpeedMultiplier).add()).appendInherited(new KeyedCodec("BackwardCrouchSpeedMultiplier", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.backwardCrouchSpeedMultiplier = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.backwardCrouchSpeedMultiplier), (movementConfig, parent) -> movementConfig.backwardCrouchSpeedMultiplier = parent.backwardCrouchSpeedMultiplier).add()).appendInherited(new KeyedCodec("StrafeCrouchSpeedMultiplier", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.strafeCrouchSpeedMultiplier = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.strafeCrouchSpeedMultiplier), (movementConfig, parent) -> movementConfig.strafeCrouchSpeedMultiplier = parent.strafeCrouchSpeedMultiplier).add()).appendInherited(new KeyedCodec("ForwardSprintSpeedMultiplier", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.forwardSprintSpeedMultiplier = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.forwardSprintSpeedMultiplier), (movementConfig, parent) -> movementConfig.forwardSprintSpeedMultiplier = parent.forwardSprintSpeedMultiplier).add()).appendInherited(new KeyedCodec("VariableJumpFallForce", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.variableJumpFallForce = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.variableJumpFallForce), (movementConfig, parent) -> movementConfig.variableJumpFallForce = parent.variableJumpFallForce).add()).appendInherited(new KeyedCodec("FallEffectDuration", (Codec)Codec.FLOAT), (movementConfig, aFloat) -> movementConfig.fallEffectDuration = aFloat.floatValue(), movementConfig -> Float.valueOf(movementConfig.fallEffectDuration), (movementConfig, parent) -> movementConfig.fallEffectDuration = parent.fallEffectDuration).add()).appendInherited(new KeyedCodec("FallJumpForce", (Codec)Codec.FLOAT), (movementConfig, aFloat) -> movementConfig.fallJumpForce = aFloat.floatValue(), movementConfig -> Float.valueOf(movementConfig.fallJumpForce), (movementConfig, parent) -> movementConfig.fallJumpForce = parent.fallJumpForce).add()).appendInherited(new KeyedCodec("FallMomentumLoss", (Codec)Codec.FLOAT), (movementConfig, aFloat) -> movementConfig.fallMomentumLoss = aFloat.floatValue(), movementConfig -> Float.valueOf(movementConfig.fallMomentumLoss), (movementConfig, parent) -> movementConfig.fallMomentumLoss = parent.fallMomentumLoss).add()).appendInherited(new KeyedCodec("AutoJumpObstacleSpeedLoss", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.autoJumpObstacleSpeedLoss = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.autoJumpObstacleSpeedLoss), (movementConfig, parent) -> movementConfig.autoJumpObstacleSpeedLoss = parent.autoJumpObstacleSpeedLoss).add()).appendInherited(new KeyedCodec("AutoJumpObstacleSprintSpeedLoss", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.autoJumpObstacleSprintSpeedLoss = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.autoJumpObstacleSprintSpeedLoss), (movementConfig, parent) -> movementConfig.autoJumpObstacleSprintSpeedLoss = parent.autoJumpObstacleSprintSpeedLoss).add()).appendInherited(new KeyedCodec("AutoJumpObstacleEffectDuration", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.autoJumpObstacleEffectDuration = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.autoJumpObstacleEffectDuration), (movementConfig, parent) -> movementConfig.autoJumpObstacleEffectDuration = parent.autoJumpObstacleEffectDuration).add()).appendInherited(new KeyedCodec("AutoJumpObstacleSprintEffectDuration", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.autoJumpObstacleSprintEffectDuration = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.autoJumpObstacleSprintEffectDuration), (movementConfig, parent) -> movementConfig.autoJumpObstacleSprintEffectDuration = parent.autoJumpObstacleSprintEffectDuration).add()).appendInherited(new KeyedCodec("AutoJumpObstacleMaxAngle", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.autoJumpObstacleMaxAngle = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.autoJumpObstacleMaxAngle), (movementConfig, parent) -> movementConfig.autoJumpObstacleMaxAngle = parent.autoJumpObstacleMaxAngle).add()).appendInherited(new KeyedCodec("AutoJumpDisableJumping", (Codec)Codec.BOOLEAN), (movementConfig, tasks) -> movementConfig.autoJumpDisableJumping = tasks.booleanValue(), movementConfig -> Boolean.valueOf(movementConfig.autoJumpDisableJumping), (movementConfig, parent) -> movementConfig.autoJumpDisableJumping = parent.autoJumpDisableJumping).add()).appendInherited(new KeyedCodec("MinSlideEntrySpeed", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.minSlideEntrySpeed = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.minSlideEntrySpeed), (movementConfig, parent) -> movementConfig.minSlideEntrySpeed = parent.minSlideEntrySpeed).add()).appendInherited(new KeyedCodec("SlideExitSpeed", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.slideExitSpeed = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.slideExitSpeed), (movementConfig, parent) -> movementConfig.slideExitSpeed = parent.slideExitSpeed).add()).appendInherited(new KeyedCodec("MinFallSpeedToEngageRoll", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.minFallSpeedToEngageRoll = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.minFallSpeedToEngageRoll), (movementConfig, parent) -> movementConfig.minFallSpeedToEngageRoll = parent.minFallSpeedToEngageRoll).add()).appendInherited(new KeyedCodec("MaxFallSpeedToEngageRoll", (Codec)Codec.FLOAT), (movementConfig, value) -> movementConfig.maxFallSpeedToEngageRoll = value.floatValue(), movementConfig -> Float.valueOf(movementConfig.maxFallSpeedToEngageRoll), (movementConfig, parent) -> movementConfig.maxFallSpeedToEngageRoll = parent.maxFallSpeedToEngageRoll).add()).appendInherited(new KeyedCodec("FallDamagePartialMitigationPercent", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.fallDamagePartialMitigationPercent = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.fallDamagePartialMitigationPercent), (movementConfig, parent) -> movementConfig.fallDamagePartialMitigationPercent = parent.fallDamagePartialMitigationPercent).add()).appendInherited(new KeyedCodec("MaxFallSpeedRollFullMitigation", (Codec)Codec.FLOAT), (movementConfig, value) -> movementConfig.maxFallSpeedRollFullMitigation = value.floatValue(), movementConfig -> Float.valueOf(movementConfig.maxFallSpeedRollFullMitigation), (movementConfig, parent) -> movementConfig.maxFallSpeedRollFullMitigation = parent.maxFallSpeedRollFullMitigation).add()).appendInherited(new KeyedCodec("RollStartSpeedModifier", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.rollStartSpeedModifier = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.rollStartSpeedModifier), (movementConfig, parent) -> movementConfig.rollStartSpeedModifier = parent.rollStartSpeedModifier).add()).appendInherited(new KeyedCodec("RollExitSpeedModifier", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.rollExitSpeedModifier = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.rollExitSpeedModifier), (movementConfig, parent) -> movementConfig.rollExitSpeedModifier = parent.rollExitSpeedModifier).add()).appendInherited(new KeyedCodec("RollTimeToComplete", (Codec)Codec.FLOAT), (movementConfig, tasks) -> movementConfig.rollTimeToComplete = tasks.floatValue(), movementConfig -> Float.valueOf(movementConfig.rollTimeToComplete), (movementConfig, parent) -> movementConfig.rollTimeToComplete = parent.rollTimeToComplete).add()).build();
/*     */   }
/* 411 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(MovementConfig::getAssetStore)); private static AssetStore<String, MovementConfig, IndexedLookupTableAssetMap<String, MovementConfig>> ASSET_STORE;
/*     */   public static final int DEFAULT_INDEX = 0;
/*     */   public static final String DEFAULT_ID = "BuiltinDefault";
/*     */   
/*     */   public static AssetStore<String, MovementConfig, IndexedLookupTableAssetMap<String, MovementConfig>> getAssetStore() {
/* 416 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(MovementConfig.class); 
/* 417 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static IndexedLookupTableAssetMap<String, MovementConfig> getAssetMap() {
/* 421 */     return (IndexedLookupTableAssetMap<String, MovementConfig>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 429 */   public static final MovementConfig DEFAULT_MOVEMENT = new MovementConfig("BuiltinDefault")
/*     */     {
/*     */     
/*     */     };
/*     */ 
/*     */   
/*     */   protected AssetExtraInfo.Data extraData;
/*     */   
/*     */   protected String id;
/*     */   
/*     */   protected float velocityResistance;
/*     */   
/*     */   protected float jumpForce;
/*     */   
/*     */   protected float swimJumpForce;
/*     */   
/*     */   protected float jumpBufferDuration;
/*     */   
/*     */   protected float jumpBufferMaxYVelocity;
/*     */   
/*     */   protected float acceleration;
/*     */   
/*     */   protected float airDragMin;
/*     */   
/*     */   protected float airDragMax;
/*     */   
/*     */   protected float airDragMinSpeed;
/*     */   
/*     */   protected float airDragMaxSpeed;
/*     */   
/*     */   protected float airFrictionMin;
/*     */   
/*     */   protected float airFrictionMax;
/*     */   
/*     */   protected float airFrictionMinSpeed;
/*     */   
/*     */   protected float airFrictionMaxSpeed;
/*     */   
/*     */   protected float airSpeedMultiplier;
/*     */   
/*     */   protected float airControlMinSpeed;
/*     */   
/*     */   protected float airControlMaxSpeed;
/*     */   
/*     */   protected float airControlMinMultiplier;
/*     */   
/*     */   protected float airControlMaxMultiplier;
/*     */   
/*     */   protected float comboAirSpeedMultiplier;
/*     */   
/*     */   protected float baseSpeed;
/*     */   
/*     */   protected float climbSpeed;
/*     */   
/*     */   protected float climbSpeedLateral;
/*     */   
/*     */   protected float climbUpSprintSpeed;
/*     */   
/*     */   protected float climbDownSprintSpeed;
/*     */   
/*     */   protected float horizontalFlySpeed;
/*     */   
/*     */   protected float verticalFlySpeed;
/*     */   
/*     */   protected float maxSpeedMultiplier;
/*     */   
/*     */   protected float minSpeedMultiplier;
/*     */   
/*     */   protected float wishDirectionGravityX;
/*     */   
/*     */   protected float wishDirectionGravityY;
/*     */   
/*     */   protected float wishDirectionWeightX;
/*     */   
/*     */   protected float wishDirectionWeightY;
/*     */   
/*     */   protected float collisionExpulsionForce;
/*     */   
/*     */   protected float forwardWalkSpeedMultiplier;
/*     */   
/*     */   protected float backwardWalkSpeedMultiplier;
/*     */   
/*     */   protected float strafeWalkSpeedMultiplier;
/*     */   
/*     */   protected float forwardRunSpeedMultiplier;
/*     */   
/*     */   protected float backwardRunSpeedMultiplier;
/*     */   
/*     */   protected float strafeRunSpeedMultiplier;
/*     */   
/*     */   protected float forwardCrouchSpeedMultiplier;
/*     */   
/*     */   protected float backwardCrouchSpeedMultiplier;
/*     */   
/*     */   protected float strafeCrouchSpeedMultiplier;
/*     */   
/*     */   protected float forwardSprintSpeedMultiplier;
/*     */   
/*     */   protected float variableJumpFallForce;
/*     */   
/*     */   protected float fallEffectDuration;
/*     */   
/*     */   protected float fallJumpForce;
/*     */   
/*     */   protected float fallMomentumLoss;
/*     */   
/*     */   protected float autoJumpObstacleSpeedLoss;
/*     */   
/*     */   protected float autoJumpObstacleSprintSpeedLoss;
/*     */   
/*     */   protected float autoJumpObstacleEffectDuration;
/*     */   
/*     */   protected float autoJumpObstacleSprintEffectDuration;
/*     */   
/*     */   protected float autoJumpObstacleMaxAngle;
/*     */   
/*     */   protected boolean autoJumpDisableJumping;
/*     */   
/*     */   protected float minSlideEntrySpeed;
/*     */   
/*     */   protected float slideExitSpeed;
/*     */   
/*     */   protected float minFallSpeedToEngageRoll;
/*     */   
/*     */   protected float maxFallSpeedToEngageRoll;
/*     */   
/*     */   protected float fallDamagePartialMitigationPercent;
/*     */   
/*     */   protected float maxFallSpeedRollFullMitigation;
/*     */   
/*     */   protected float rollStartSpeedModifier;
/*     */   
/*     */   protected float rollExitSpeedModifier;
/*     */   
/*     */   protected float rollTimeToComplete;
/*     */ 
/*     */   
/*     */   public MovementConfig(@Nonnull MovementConfig movementConfig) {
/* 567 */     this.id = movementConfig.id;
/* 568 */     this.velocityResistance = movementConfig.velocityResistance;
/* 569 */     this.jumpForce = movementConfig.jumpForce;
/* 570 */     this.swimJumpForce = movementConfig.swimJumpForce;
/* 571 */     this.jumpBufferDuration = movementConfig.jumpBufferDuration;
/* 572 */     this.jumpBufferMaxYVelocity = movementConfig.jumpBufferMaxYVelocity;
/* 573 */     this.acceleration = movementConfig.acceleration;
/* 574 */     this.airDragMin = movementConfig.airDragMin;
/* 575 */     this.airDragMax = movementConfig.airDragMax;
/* 576 */     this.airDragMinSpeed = movementConfig.airDragMinSpeed;
/* 577 */     this.airDragMaxSpeed = movementConfig.airDragMaxSpeed;
/* 578 */     this.airFrictionMin = movementConfig.airFrictionMin;
/* 579 */     this.airFrictionMax = movementConfig.airFrictionMax;
/* 580 */     this.airFrictionMinSpeed = movementConfig.airFrictionMinSpeed;
/* 581 */     this.airFrictionMaxSpeed = movementConfig.airFrictionMaxSpeed;
/* 582 */     this.airSpeedMultiplier = movementConfig.airSpeedMultiplier;
/* 583 */     this.airControlMinSpeed = movementConfig.airControlMinSpeed;
/* 584 */     this.airControlMaxSpeed = movementConfig.airControlMaxSpeed;
/* 585 */     this.airControlMinMultiplier = movementConfig.airControlMinMultiplier;
/* 586 */     this.airControlMaxMultiplier = movementConfig.airControlMaxMultiplier;
/* 587 */     this.comboAirSpeedMultiplier = movementConfig.airSpeedMultiplier;
/* 588 */     this.baseSpeed = movementConfig.baseSpeed;
/* 589 */     this.climbSpeed = movementConfig.climbSpeed;
/* 590 */     this.climbSpeedLateral = movementConfig.climbSpeedLateral;
/* 591 */     this.climbUpSprintSpeed = movementConfig.climbUpSprintSpeed;
/* 592 */     this.climbDownSprintSpeed = movementConfig.climbDownSprintSpeed;
/* 593 */     this.horizontalFlySpeed = movementConfig.horizontalFlySpeed;
/* 594 */     this.verticalFlySpeed = movementConfig.verticalFlySpeed;
/* 595 */     this.maxSpeedMultiplier = movementConfig.maxSpeedMultiplier;
/* 596 */     this.minSpeedMultiplier = movementConfig.minSpeedMultiplier;
/* 597 */     this.wishDirectionGravityX = movementConfig.wishDirectionGravityX;
/* 598 */     this.wishDirectionGravityY = movementConfig.wishDirectionGravityY;
/* 599 */     this.wishDirectionWeightX = movementConfig.wishDirectionWeightX;
/* 600 */     this.wishDirectionWeightY = movementConfig.wishDirectionWeightY;
/* 601 */     this.collisionExpulsionForce = movementConfig.collisionExpulsionForce;
/* 602 */     this.forwardWalkSpeedMultiplier = movementConfig.forwardWalkSpeedMultiplier;
/* 603 */     this.backwardWalkSpeedMultiplier = movementConfig.backwardWalkSpeedMultiplier;
/* 604 */     this.strafeWalkSpeedMultiplier = movementConfig.strafeWalkSpeedMultiplier;
/* 605 */     this.forwardRunSpeedMultiplier = movementConfig.forwardRunSpeedMultiplier;
/* 606 */     this.backwardRunSpeedMultiplier = movementConfig.backwardRunSpeedMultiplier;
/* 607 */     this.strafeRunSpeedMultiplier = movementConfig.strafeRunSpeedMultiplier;
/* 608 */     this.forwardCrouchSpeedMultiplier = movementConfig.forwardCrouchSpeedMultiplier;
/* 609 */     this.backwardCrouchSpeedMultiplier = movementConfig.backwardCrouchSpeedMultiplier;
/* 610 */     this.strafeCrouchSpeedMultiplier = movementConfig.strafeCrouchSpeedMultiplier;
/* 611 */     this.forwardSprintSpeedMultiplier = movementConfig.forwardSprintSpeedMultiplier;
/* 612 */     this.variableJumpFallForce = movementConfig.variableJumpFallForce;
/* 613 */     this.autoJumpObstacleSpeedLoss = movementConfig.autoJumpObstacleSpeedLoss;
/* 614 */     this.autoJumpObstacleSprintSpeedLoss = movementConfig.autoJumpObstacleSprintSpeedLoss;
/* 615 */     this.autoJumpObstacleEffectDuration = movementConfig.autoJumpObstacleEffectDuration;
/* 616 */     this.autoJumpObstacleSprintEffectDuration = movementConfig.autoJumpObstacleSprintEffectDuration;
/* 617 */     this.autoJumpObstacleMaxAngle = movementConfig.autoJumpObstacleMaxAngle;
/* 618 */     this.autoJumpDisableJumping = movementConfig.autoJumpDisableJumping;
/* 619 */     this.minSlideEntrySpeed = movementConfig.minSlideEntrySpeed;
/* 620 */     this.slideExitSpeed = movementConfig.slideExitSpeed;
/* 621 */     this.minFallSpeedToEngageRoll = movementConfig.minFallSpeedToEngageRoll;
/* 622 */     this.maxFallSpeedToEngageRoll = movementConfig.maxFallSpeedToEngageRoll;
/* 623 */     this.fallDamagePartialMitigationPercent = movementConfig.fallDamagePartialMitigationPercent;
/* 624 */     this.maxFallSpeedRollFullMitigation = movementConfig.maxFallSpeedRollFullMitigation;
/* 625 */     this.rollStartSpeedModifier = movementConfig.rollStartSpeedModifier;
/* 626 */     this.rollExitSpeedModifier = movementConfig.rollExitSpeedModifier;
/* 627 */     this.rollTimeToComplete = movementConfig.rollTimeToComplete;
/*     */   }
/*     */   
/*     */   public MovementConfig(String id) {
/* 631 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   protected MovementConfig() {}
/*     */ 
/*     */   
/*     */   public String getId() {
/* 639 */     return this.id;
/*     */   }
/*     */   
/*     */   public AssetExtraInfo.Data getExtraData() {
/* 643 */     return this.extraData;
/*     */   }
/*     */   
/*     */   public float getVelocityResistance() {
/* 647 */     return this.velocityResistance;
/*     */   }
/*     */   
/*     */   public float getJumpForce() {
/* 651 */     return this.jumpForce;
/*     */   }
/*     */   
/*     */   public float getSwimJumpForce() {
/* 655 */     return this.swimJumpForce;
/*     */   }
/*     */   
/*     */   public float getJumpBufferDuration() {
/* 659 */     return this.jumpBufferDuration;
/*     */   }
/*     */   
/*     */   public float getJumpBufferMaxYVelocity() {
/* 663 */     return this.jumpBufferMaxYVelocity;
/*     */   }
/*     */   
/*     */   public float getAcceleration() {
/* 667 */     return this.acceleration;
/*     */   }
/*     */   
/*     */   public float getAirDragMin() {
/* 671 */     return this.airDragMin;
/*     */   }
/*     */   
/*     */   public float getAirDragMax() {
/* 675 */     return this.airDragMax;
/*     */   }
/*     */   
/*     */   public float getAirDragMinSpeed() {
/* 679 */     return this.airDragMinSpeed;
/*     */   }
/*     */   
/*     */   public float getAirDragMaxSpeed() {
/* 683 */     return this.airDragMaxSpeed;
/*     */   }
/*     */   
/*     */   public float getAirFrictionMin() {
/* 687 */     return this.airFrictionMin;
/*     */   }
/*     */   
/*     */   public float getAirFrictionMax() {
/* 691 */     return this.airFrictionMax;
/*     */   }
/*     */   
/*     */   public float getAirFrictionMinSpeed() {
/* 695 */     return this.airFrictionMinSpeed;
/*     */   }
/*     */   
/*     */   public float getAirFrictionMaxSpeed() {
/* 699 */     return this.airFrictionMaxSpeed;
/*     */   }
/*     */   
/*     */   public float getAirSpeedMultiplier() {
/* 703 */     return this.airSpeedMultiplier;
/*     */   }
/*     */   
/*     */   public float getAirControlMinSpeed() {
/* 707 */     return this.airControlMinSpeed;
/*     */   }
/*     */   
/*     */   public float getAirControlMaxSpeed() {
/* 711 */     return this.airControlMaxSpeed;
/*     */   }
/*     */   
/*     */   public float getAirControlMinMultiplier() {
/* 715 */     return this.airControlMinMultiplier;
/*     */   }
/*     */   
/*     */   public float getAirControlMaxMultiplier() {
/* 719 */     return this.airControlMaxMultiplier;
/*     */   }
/*     */   
/*     */   public float getComboAirSpeedMultiplier() {
/* 723 */     return this.comboAirSpeedMultiplier;
/*     */   }
/*     */   
/*     */   public float getBaseSpeed() {
/* 727 */     return this.baseSpeed;
/*     */   }
/*     */   
/*     */   public float getClimbSpeed() {
/* 731 */     return this.climbSpeed;
/*     */   }
/*     */   
/*     */   public float getClimbSpeedLateral() {
/* 735 */     return this.climbSpeedLateral;
/*     */   }
/*     */   
/*     */   public float getClimbUpSprintSpeed() {
/* 739 */     return this.climbUpSprintSpeed;
/*     */   }
/*     */   
/*     */   public float getClimbDownSprintSpeed() {
/* 743 */     return this.climbDownSprintSpeed;
/*     */   }
/*     */   
/*     */   public float getHorizontalFlySpeed() {
/* 747 */     return this.horizontalFlySpeed;
/*     */   }
/*     */   
/*     */   public float getVerticalFlySpeed() {
/* 751 */     return this.verticalFlySpeed;
/*     */   }
/*     */   
/*     */   public float getMaxSpeedMultiplier() {
/* 755 */     return this.maxSpeedMultiplier;
/*     */   }
/*     */   
/*     */   public float getMinSpeedMultiplier() {
/* 759 */     return this.minSpeedMultiplier;
/*     */   }
/*     */   
/*     */   public float getWishDirectionGravityX() {
/* 763 */     return this.wishDirectionGravityX;
/*     */   }
/*     */   
/*     */   public float getWishDirectionGravityY() {
/* 767 */     return this.wishDirectionGravityY;
/*     */   }
/*     */   
/*     */   public float getWishDirectionWeightX() {
/* 771 */     return this.wishDirectionWeightX;
/*     */   }
/*     */   
/*     */   public float getWishDirectionWeightY() {
/* 775 */     return this.wishDirectionWeightY;
/*     */   }
/*     */   
/*     */   public float getCollisionExpulsionForce() {
/* 779 */     return this.collisionExpulsionForce;
/*     */   }
/*     */   
/*     */   public float getForwardWalkSpeedMultiplier() {
/* 783 */     return this.forwardWalkSpeedMultiplier;
/*     */   }
/*     */   
/*     */   public float getBackwardWalkSpeedMultiplier() {
/* 787 */     return this.backwardWalkSpeedMultiplier;
/*     */   }
/*     */   
/*     */   public float getStrafeWalkSpeedMultiplier() {
/* 791 */     return this.strafeWalkSpeedMultiplier;
/*     */   }
/*     */   
/*     */   public float getForwardRunSpeedMultiplier() {
/* 795 */     return this.forwardRunSpeedMultiplier;
/*     */   }
/*     */   
/*     */   public float getBackwardRunSpeedMultiplier() {
/* 799 */     return this.backwardRunSpeedMultiplier;
/*     */   }
/*     */   
/*     */   public float getStrafeRunSpeedMultiplier() {
/* 803 */     return this.strafeRunSpeedMultiplier;
/*     */   }
/*     */   
/*     */   public float getForwardCrouchSpeedMultiplier() {
/* 807 */     return this.forwardCrouchSpeedMultiplier;
/*     */   }
/*     */   
/*     */   public float getBackwardCrouchSpeedMultiplier() {
/* 811 */     return this.backwardCrouchSpeedMultiplier;
/*     */   }
/*     */   
/*     */   public float getStrafeCrouchSpeedMultiplier() {
/* 815 */     return this.strafeCrouchSpeedMultiplier;
/*     */   }
/*     */   
/*     */   public float getForwardSprintSpeedMultiplier() {
/* 819 */     return this.forwardSprintSpeedMultiplier;
/*     */   }
/*     */   
/*     */   public float getVariableJumpFallForce() {
/* 823 */     return this.variableJumpFallForce;
/*     */   }
/*     */   
/*     */   public float getFallEffectDuration() {
/* 827 */     return this.fallEffectDuration;
/*     */   }
/*     */   
/*     */   public float getFallJumpForce() {
/* 831 */     return this.fallJumpForce;
/*     */   }
/*     */   
/*     */   public float getFallMomentumLoss() {
/* 835 */     return this.fallMomentumLoss;
/*     */   }
/*     */   
/*     */   public float getAutoJumpObstacleSpeedLoss() {
/* 839 */     return this.autoJumpObstacleSpeedLoss;
/*     */   }
/*     */   
/*     */   public float getAutoJumpObstacleSprintSpeedLoss() {
/* 843 */     return this.autoJumpObstacleSprintSpeedLoss;
/*     */   }
/*     */   
/*     */   public float getAutoJumpObstacleEffectDuration() {
/* 847 */     return this.autoJumpObstacleEffectDuration;
/*     */   }
/*     */   
/*     */   public float getAutoJumpObstacleSprintEffectDuration() {
/* 851 */     return this.autoJumpObstacleSprintEffectDuration;
/*     */   }
/*     */   
/*     */   public float getAutoJumpObstacleMaxAngle() {
/* 855 */     return this.autoJumpObstacleMaxAngle;
/*     */   }
/*     */   
/*     */   public boolean isAutoJumpDisableJumping() {
/* 859 */     return this.autoJumpDisableJumping;
/*     */   }
/*     */   
/*     */   public float getMinFallSpeedToEngageRoll() {
/* 863 */     return this.minFallSpeedToEngageRoll;
/*     */   }
/*     */   
/*     */   public float getMaxFallSpeedToEngageRoll() {
/* 867 */     return this.maxFallSpeedToEngageRoll;
/*     */   }
/*     */   
/*     */   public float getFallDamagePartialMitigationPercent() {
/* 871 */     return this.fallDamagePartialMitigationPercent;
/*     */   }
/*     */   
/*     */   public float getMaxFallSpeedRollFullMitigation() {
/* 875 */     return this.maxFallSpeedRollFullMitigation;
/*     */   }
/*     */   
/*     */   public float getRollStartSpeedModifier() {
/* 879 */     return this.rollStartSpeedModifier;
/*     */   }
/*     */   
/*     */   public float getRollExitSpeedModifier() {
/* 883 */     return this.rollExitSpeedModifier;
/*     */   }
/*     */   
/*     */   public float getRollTimeToComplete() {
/* 887 */     return this.rollTimeToComplete;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public MovementSettings toPacket() {
/* 893 */     MovementSettings packet = new MovementSettings();
/*     */     
/* 895 */     packet.velocityResistance = this.velocityResistance;
/* 896 */     packet.jumpForce = this.jumpForce;
/* 897 */     packet.swimJumpForce = this.swimJumpForce;
/* 898 */     packet.jumpBufferDuration = this.jumpBufferDuration;
/* 899 */     packet.jumpBufferMaxYVelocity = this.jumpBufferMaxYVelocity;
/* 900 */     packet.acceleration = this.acceleration;
/* 901 */     packet.airDragMin = this.airDragMin;
/* 902 */     packet.airDragMax = this.airDragMax;
/* 903 */     packet.airDragMinSpeed = this.airDragMinSpeed;
/* 904 */     packet.airDragMaxSpeed = this.airDragMaxSpeed;
/* 905 */     packet.airFrictionMin = this.airFrictionMin;
/* 906 */     packet.airFrictionMax = this.airFrictionMax;
/* 907 */     packet.airFrictionMinSpeed = this.airFrictionMinSpeed;
/* 908 */     packet.airFrictionMaxSpeed = this.airFrictionMaxSpeed;
/* 909 */     packet.airSpeedMultiplier = this.airSpeedMultiplier;
/* 910 */     packet.airControlMinSpeed = this.airControlMinSpeed;
/* 911 */     packet.airControlMaxSpeed = this.airControlMaxSpeed;
/* 912 */     packet.airControlMinMultiplier = this.airControlMinMultiplier;
/* 913 */     packet.airControlMaxMultiplier = this.airControlMaxMultiplier;
/* 914 */     packet.comboAirSpeedMultiplier = this.airSpeedMultiplier;
/* 915 */     packet.baseSpeed = this.baseSpeed;
/* 916 */     packet.climbSpeed = this.climbSpeed;
/* 917 */     packet.climbSpeedLateral = this.climbSpeedLateral;
/* 918 */     packet.climbUpSprintSpeed = this.climbUpSprintSpeed;
/* 919 */     packet.climbDownSprintSpeed = this.climbDownSprintSpeed;
/* 920 */     packet.horizontalFlySpeed = this.horizontalFlySpeed;
/* 921 */     packet.verticalFlySpeed = this.verticalFlySpeed;
/* 922 */     packet.maxSpeedMultiplier = this.maxSpeedMultiplier;
/* 923 */     packet.minSpeedMultiplier = this.minSpeedMultiplier;
/* 924 */     packet.wishDirectionGravityX = this.wishDirectionGravityX;
/* 925 */     packet.wishDirectionGravityY = this.wishDirectionGravityY;
/* 926 */     packet.wishDirectionWeightX = this.wishDirectionWeightX;
/* 927 */     packet.wishDirectionWeightY = this.wishDirectionWeightY;
/* 928 */     packet.collisionExpulsionForce = this.collisionExpulsionForce;
/* 929 */     packet.forwardWalkSpeedMultiplier = this.forwardWalkSpeedMultiplier;
/* 930 */     packet.backwardWalkSpeedMultiplier = this.backwardWalkSpeedMultiplier;
/* 931 */     packet.strafeWalkSpeedMultiplier = this.strafeWalkSpeedMultiplier;
/* 932 */     packet.forwardRunSpeedMultiplier = this.forwardRunSpeedMultiplier;
/* 933 */     packet.backwardRunSpeedMultiplier = this.backwardRunSpeedMultiplier;
/* 934 */     packet.strafeRunSpeedMultiplier = this.strafeRunSpeedMultiplier;
/* 935 */     packet.forwardCrouchSpeedMultiplier = this.forwardCrouchSpeedMultiplier;
/* 936 */     packet.backwardCrouchSpeedMultiplier = this.backwardCrouchSpeedMultiplier;
/* 937 */     packet.strafeCrouchSpeedMultiplier = this.strafeCrouchSpeedMultiplier;
/* 938 */     packet.forwardSprintSpeedMultiplier = this.forwardSprintSpeedMultiplier;
/* 939 */     packet.variableJumpFallForce = this.variableJumpFallForce;
/* 940 */     packet.fallEffectDuration = this.fallEffectDuration;
/* 941 */     packet.fallJumpForce = this.fallJumpForce;
/* 942 */     packet.fallMomentumLoss = this.fallMomentumLoss;
/* 943 */     packet.autoJumpObstacleSpeedLoss = this.autoJumpObstacleSpeedLoss;
/* 944 */     packet.autoJumpObstacleSprintSpeedLoss = this.autoJumpObstacleSprintSpeedLoss;
/* 945 */     packet.autoJumpObstacleEffectDuration = this.autoJumpObstacleEffectDuration;
/* 946 */     packet.autoJumpObstacleSprintEffectDuration = this.autoJumpObstacleSprintEffectDuration;
/* 947 */     packet.autoJumpObstacleMaxAngle = this.autoJumpObstacleMaxAngle;
/* 948 */     packet.autoJumpDisableJumping = this.autoJumpDisableJumping;
/* 949 */     packet.minSlideEntrySpeed = this.minSlideEntrySpeed;
/* 950 */     packet.slideExitSpeed = this.slideExitSpeed;
/* 951 */     packet.minFallSpeedToEngageRoll = this.minFallSpeedToEngageRoll;
/* 952 */     packet.maxFallSpeedToEngageRoll = this.maxFallSpeedToEngageRoll;
/* 953 */     packet.rollStartSpeedModifier = this.rollStartSpeedModifier;
/* 954 */     packet.rollExitSpeedModifier = this.rollExitSpeedModifier;
/* 955 */     packet.rollTimeToComplete = this.rollTimeToComplete;
/*     */     
/* 957 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 963 */     return "MovementConfig{id='" + this.id + "', velocityResistance=" + this.velocityResistance + ", jumpForce=" + this.jumpForce + ", swimJumpForce=" + this.swimJumpForce + ", jumpBufferDuration=" + this.jumpBufferDuration + ", jumpBufferMaxYVelocity=" + this.jumpBufferMaxYVelocity + ", acceleration=" + this.acceleration + ", airDragMin=" + this.airDragMin + ", airDragMax=" + this.airDragMax + ", airDragMinSpeed=" + this.airDragMinSpeed + ", airDragMaxSpeed=" + this.airDragMaxSpeed + ", airFrictionMin=" + this.airFrictionMin + ", airFrictionMax=" + this.airFrictionMax + ", airFrictionMinSpeed=" + this.airFrictionMinSpeed + ", airFrictionMaxSpeed=" + this.airFrictionMaxSpeed + ", airSpeedMultiplier=" + this.airSpeedMultiplier + ", airControlMinSpeed=" + this.airControlMinSpeed + ", airControlMaxSpeed=" + this.airControlMaxSpeed + ", airControlMinMultiplier=" + this.airControlMinMultiplier + ", airControlMaxMultiplier=" + this.airControlMaxMultiplier + ", comboAirSpeedMultiplier=" + this.comboAirSpeedMultiplier + ", baseSpeed=" + this.baseSpeed + ", climbSpeed=" + this.climbSpeed + ", climbSpeedLateral=" + this.climbSpeedLateral + ", climbUpSprintSpeed=" + this.climbUpSprintSpeed + ", climbDownSprintSpeed=" + this.climbDownSprintSpeed + ", horizontalFlySpeed=" + this.horizontalFlySpeed + ", verticalFlySpeed=" + this.verticalFlySpeed + ", maxSpeedMultiplier=" + this.maxSpeedMultiplier + ", minSpeedMultiplier=" + this.minSpeedMultiplier + ", wishDirectionGravityX=" + this.wishDirectionGravityX + ", wishDirectionGravityY=" + this.wishDirectionGravityY + ", wishDirectionWeightX=" + this.wishDirectionWeightX + ", wishDirectionWeightY=" + this.wishDirectionWeightY + ", collisionExpulsionForce=" + this.collisionExpulsionForce + ", forwardWalkSpeedMultiplier=" + this.forwardWalkSpeedMultiplier + ", backwardWalkSpeedMultiplier=" + this.backwardWalkSpeedMultiplier + ", strafeWalkSpeedMultiplier=" + this.strafeWalkSpeedMultiplier + ", forwardRunSpeedMultiplier=" + this.forwardRunSpeedMultiplier + ", backwardRunSpeedMultiplier=" + this.backwardRunSpeedMultiplier + ", strafeRunSpeedMultiplier=" + this.strafeRunSpeedMultiplier + ", forwardCrouchSpeedMultiplier=" + this.forwardCrouchSpeedMultiplier + ", backwardCrouchSpeedMultiplier=" + this.backwardCrouchSpeedMultiplier + ", strafeCrouchSpeedMultiplier=" + this.strafeCrouchSpeedMultiplier + ", forwardSprintSpeedMultiplier=" + this.forwardSprintSpeedMultiplier + ", variableJumpFallForce=" + this.variableJumpFallForce + ", fallEffectDuration=" + this.fallEffectDuration + ", fallJumpForce=" + this.fallJumpForce + ", fallMomentumLoss=" + this.fallMomentumLoss + ", autoJumpObstacleSpeedLoss=" + this.autoJumpObstacleSpeedLoss + ", autoJumpObstacleSprintSpeedLoss=" + this.autoJumpObstacleSprintSpeedLoss + ", autoJumpObstacleEffectDuration=" + this.autoJumpObstacleEffectDuration + ", autoJumpObstacleSprintEffectDuration=" + this.autoJumpObstacleSprintEffectDuration + ", autoJumpObstacleMaxAngle=" + this.autoJumpObstacleMaxAngle + ", autoJumpDisableJumping=" + this.autoJumpDisableJumping + ", minSlideEntrySpeed=" + this.minSlideEntrySpeed + ", slideExitSpeed=" + this.slideExitSpeed + ", minFallSpeedToEngageRoll=" + this.minFallSpeedToEngageRoll + ", maxFallSpeedToEngageRoll=" + this.maxFallSpeedToEngageRoll + ", fallDamagePartialMitigationPercent=" + this.fallDamagePartialMitigationPercent + ", maxFallSpeedRollFullMitigation=" + this.maxFallSpeedRollFullMitigation + ", rollStartSpeedModifier=" + this.rollStartSpeedModifier + ", rollExitSpeedModifier=" + this.rollExitSpeedModifier + ", rollTimeToComplete=" + this.rollTimeToComplete + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\movement\MovementConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */