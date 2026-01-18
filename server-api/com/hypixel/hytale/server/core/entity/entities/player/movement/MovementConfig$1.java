/*     */ package com.hypixel.hytale.server.core.entity.entities.player.movement;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   extends MovementConfig
/*     */ {
/*     */   null(String id) {
/* 429 */     super(id);
/* 430 */     this.velocityResistance = 0.242F;
/* 431 */     this.jumpForce = 11.8F;
/* 432 */     this.swimJumpForce = 10.0F;
/* 433 */     this.jumpBufferDuration = 0.3F;
/* 434 */     this.jumpBufferMaxYVelocity = 3.0F;
/* 435 */     this.acceleration = 0.1F;
/* 436 */     this.airDragMin = 0.96F;
/* 437 */     this.airDragMax = 0.995F;
/* 438 */     this.airDragMinSpeed = 6.0F;
/* 439 */     this.airDragMaxSpeed = 10.0F;
/* 440 */     this.airFrictionMin = 0.02F;
/* 441 */     this.airFrictionMax = 0.045F;
/* 442 */     this.airFrictionMinSpeed = 6.0F;
/* 443 */     this.airFrictionMaxSpeed = 10.0F;
/* 444 */     this.airSpeedMultiplier = 1.0F;
/* 445 */     this.airControlMinSpeed = 0.0F;
/* 446 */     this.airControlMaxSpeed = 3.0F;
/* 447 */     this.airControlMinMultiplier = 0.0F;
/* 448 */     this.airControlMaxMultiplier = 3.13F;
/* 449 */     this.comboAirSpeedMultiplier = 1.05F;
/* 450 */     this.baseSpeed = 5.5F;
/* 451 */     this.climbSpeed = 0.035F;
/* 452 */     this.climbSpeedLateral = 0.035F;
/* 453 */     this.climbUpSprintSpeed = 0.5F;
/* 454 */     this.climbDownSprintSpeed = 0.6F;
/* 455 */     this.horizontalFlySpeed = 10.32F;
/* 456 */     this.verticalFlySpeed = 10.32F;
/* 457 */     this.maxSpeedMultiplier = 1000.0F;
/* 458 */     this.minSpeedMultiplier = 0.01F;
/* 459 */     this.wishDirectionGravityX = 0.5F;
/* 460 */     this.wishDirectionGravityY = 0.5F;
/* 461 */     this.wishDirectionWeightX = 0.5F;
/* 462 */     this.wishDirectionWeightY = 0.5F;
/* 463 */     this.collisionExpulsionForce = 0.04F;
/* 464 */     this.forwardWalkSpeedMultiplier = 0.3F;
/* 465 */     this.backwardWalkSpeedMultiplier = 0.3F;
/* 466 */     this.strafeWalkSpeedMultiplier = 0.3F;
/* 467 */     this.forwardRunSpeedMultiplier = 1.0F;
/* 468 */     this.backwardRunSpeedMultiplier = 0.65F;
/* 469 */     this.strafeRunSpeedMultiplier = 0.8F;
/* 470 */     this.forwardCrouchSpeedMultiplier = 0.55F;
/* 471 */     this.backwardCrouchSpeedMultiplier = 0.4F;
/* 472 */     this.strafeCrouchSpeedMultiplier = 0.45F;
/* 473 */     this.forwardSprintSpeedMultiplier = 1.65F;
/* 474 */     this.variableJumpFallForce = 35.0F;
/* 475 */     this.fallEffectDuration = 0.6F;
/* 476 */     this.fallJumpForce = 7.0F;
/* 477 */     this.fallMomentumLoss = 0.1F;
/* 478 */     this.autoJumpObstacleSpeedLoss = 0.95F;
/* 479 */     this.autoJumpObstacleSprintSpeedLoss = 0.75F;
/* 480 */     this.autoJumpObstacleEffectDuration = 0.2F;
/* 481 */     this.autoJumpObstacleSprintEffectDuration = 0.1F;
/* 482 */     this.autoJumpObstacleMaxAngle = 45.0F;
/* 483 */     this.autoJumpDisableJumping = true;
/* 484 */     this.minSlideEntrySpeed = 8.5F;
/* 485 */     this.slideExitSpeed = 2.5F;
/* 486 */     this.minFallSpeedToEngageRoll = 21.0F;
/* 487 */     this.maxFallSpeedToEngageRoll = 31.0F;
/* 488 */     this.fallDamagePartialMitigationPercent = 33.0F;
/* 489 */     this.maxFallSpeedRollFullMitigation = 25.0F;
/* 490 */     this.rollStartSpeedModifier = 2.5F;
/* 491 */     this.rollExitSpeedModifier = 1.5F;
/* 492 */     this.rollTimeToComplete = 0.9F;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\movement\MovementConfig$1.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */