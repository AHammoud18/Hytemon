/*     */ package com.hypixel.hytale.server.core.asset.type.blocktype.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.protocol.BlockMovementSettings;
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
/*     */ public class BlockMovementSettings
/*     */   implements NetworkSerializable<BlockMovementSettings>
/*     */ {
/*     */   public static final BuilderCodec<BlockMovementSettings> CODEC;
/*     */   private boolean isClimbable;
/*     */   private boolean isBouncy;
/*     */   private float bounceVelocity;
/*     */   
/*     */   static {
/*  80 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BlockMovementSettings.class, BlockMovementSettings::new).append(new KeyedCodec("IsClimbable", (Codec)Codec.BOOLEAN), (blockMovementSettings, o) -> blockMovementSettings.isClimbable = o.booleanValue(), blockMovementSettings -> Boolean.valueOf(blockMovementSettings.isClimbable)).add()).append(new KeyedCodec("IsBouncy", (Codec)Codec.BOOLEAN), (blockMovementSettings, o) -> blockMovementSettings.isBouncy = o.booleanValue(), blockMovementSettings -> Boolean.valueOf(blockMovementSettings.isBouncy)).add()).append(new KeyedCodec("BounceVelocity", (Codec)Codec.FLOAT), (blockMovementSettings, o) -> blockMovementSettings.bounceVelocity = o.floatValue(), blockMovementSettings -> Float.valueOf(blockMovementSettings.bounceVelocity)).add()).append(new KeyedCodec("ClimbUpSpeedMultiplier", (Codec)Codec.FLOAT), (blockMovementSettings, o) -> blockMovementSettings.climbUpSpeedMultiplier = o.floatValue(), blockMovementSettings -> Float.valueOf(blockMovementSettings.climbUpSpeedMultiplier)).add()).append(new KeyedCodec("ClimbDownSpeedMultiplier", (Codec)Codec.FLOAT), (blockMovementSettings, o) -> blockMovementSettings.climbDownSpeedMultiplier = o.floatValue(), blockMovementSettings -> Float.valueOf(blockMovementSettings.climbDownSpeedMultiplier)).add()).append(new KeyedCodec("ClimbLateralSpeedMultiplier", (Codec)Codec.FLOAT), (blockMovementSettings, o) -> blockMovementSettings.climbLateralSpeedMultiplier = o.floatValue(), blockMovementSettings -> Float.valueOf(blockMovementSettings.climbLateralSpeedMultiplier)).add()).append(new KeyedCodec("Drag", (Codec)Codec.FLOAT), (blockMovementSettings, o) -> blockMovementSettings.drag = o.floatValue(), blockMovementSettings -> Float.valueOf(blockMovementSettings.drag)).add()).append(new KeyedCodec("Friction", (Codec)Codec.FLOAT), (blockMovementSettings, o) -> blockMovementSettings.friction = o.floatValue(), blockMovementSettings -> Float.valueOf(blockMovementSettings.friction)).add()).append(new KeyedCodec("TerminalVelocityModifier", (Codec)Codec.FLOAT), (blockMovementSettings, o) -> blockMovementSettings.terminalVelocityModifier = o.floatValue(), blockMovementSettings -> Float.valueOf(blockMovementSettings.terminalVelocityModifier)).add()).append(new KeyedCodec("HorizontalSpeedMultiplier", (Codec)Codec.FLOAT), (blockMovementSettings, o) -> blockMovementSettings.horizontalSpeedMultiplier = o.floatValue(), blockMovementSettings -> Float.valueOf(blockMovementSettings.horizontalSpeedMultiplier)).add()).append(new KeyedCodec("JumpForceMultiplier", (Codec)Codec.FLOAT), (blockMovementSettings, o) -> blockMovementSettings.jumpForceMultiplier = o.floatValue(), blockMovementSettings -> Float.valueOf(blockMovementSettings.jumpForceMultiplier)).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  85 */   private float drag = 0.82F;
/*  86 */   private float friction = 0.18F;
/*  87 */   private float climbUpSpeedMultiplier = 1.0F;
/*  88 */   private float climbDownSpeedMultiplier = 1.0F;
/*  89 */   private float climbLateralSpeedMultiplier = 1.0F;
/*  90 */   private float terminalVelocityModifier = 1.0F;
/*  91 */   private float horizontalSpeedMultiplier = 1.0F;
/*  92 */   private float jumpForceMultiplier = 1.0F;
/*     */   
/*     */   public BlockMovementSettings(boolean isClimbable, boolean isBouncy, float bounceVelocity, float drag, float friction, float climbUpSpeed, float climbDownSpeed, float climbLateralSpeedMultiplier, float terminalVelocityModifier, float horizontalSpeedMultiplier, float jumpForceMultiplier) {
/*  95 */     this.isClimbable = isClimbable;
/*  96 */     this.isBouncy = isBouncy;
/*  97 */     this.bounceVelocity = bounceVelocity;
/*  98 */     this.climbUpSpeedMultiplier = climbUpSpeed;
/*  99 */     this.climbDownSpeedMultiplier = climbDownSpeed;
/* 100 */     this.climbLateralSpeedMultiplier = climbLateralSpeedMultiplier;
/* 101 */     this.drag = drag;
/* 102 */     this.friction = friction;
/* 103 */     this.terminalVelocityModifier = terminalVelocityModifier;
/* 104 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/* 105 */     this.jumpForceMultiplier = jumpForceMultiplier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BlockMovementSettings toPacket() {
/* 114 */     BlockMovementSettings packet = new BlockMovementSettings();
/* 115 */     packet.isClimbable = this.isClimbable;
/* 116 */     packet.isBouncy = this.isBouncy;
/* 117 */     packet.bounceVelocity = this.bounceVelocity;
/* 118 */     packet.climbUpSpeedMultiplier = this.climbUpSpeedMultiplier;
/* 119 */     packet.climbDownSpeedMultiplier = this.climbDownSpeedMultiplier;
/* 120 */     packet.climbLateralSpeedMultiplier = this.climbLateralSpeedMultiplier;
/* 121 */     packet.drag = this.drag;
/* 122 */     packet.friction = this.friction;
/* 123 */     packet.terminalVelocityModifier = this.terminalVelocityModifier;
/* 124 */     packet.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 125 */     packet.jumpForceMultiplier = this.jumpForceMultiplier;
/* 126 */     return packet;
/*     */   }
/*     */   
/*     */   public boolean isClimbable() {
/* 130 */     return this.isClimbable;
/*     */   }
/*     */   
/*     */   public boolean isBouncy() {
/* 134 */     return this.isBouncy;
/*     */   }
/*     */   
/*     */   public float getBounceVelocity() {
/* 138 */     return this.bounceVelocity;
/*     */   }
/*     */   
/*     */   public float getDrag() {
/* 142 */     return this.drag;
/*     */   }
/*     */   
/*     */   public float getFriction() {
/* 146 */     return this.friction;
/*     */   }
/*     */   
/*     */   public float getClimbUpSpeedMultiplier() {
/* 150 */     return this.climbUpSpeedMultiplier;
/*     */   }
/*     */   
/*     */   public float getClimbDownSpeedMultiplier() {
/* 154 */     return this.climbDownSpeedMultiplier;
/*     */   }
/*     */   
/*     */   public float getClimbLateralSpeedMultiplier() {
/* 158 */     return this.climbLateralSpeedMultiplier;
/*     */   }
/*     */   
/*     */   public float getTerminalVelocityModifier() {
/* 162 */     return this.terminalVelocityModifier;
/*     */   }
/*     */   
/*     */   public float getHorizontalSpeedMultiplier() {
/* 166 */     return this.horizontalSpeedMultiplier;
/*     */   }
/*     */   public float jumpForceMultiplier() {
/* 169 */     return this.jumpForceMultiplier;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 174 */     return "BlockMovementSettings{isClimbable=" + this.isClimbable + "isBouncy=" + this.isBouncy + "bounceSpeed=" + this.bounceVelocity + ", climbUpSpeedMultiplier=" + this.climbUpSpeedMultiplier + ", climbDownSpeedMultiplier=" + this.climbDownSpeedMultiplier + ", climbLateralSpeedMultiplier=" + this.climbLateralSpeedMultiplier + ", drag=" + this.drag + ", friction=" + this.friction + ", terminalVelocityModifier=" + this.terminalVelocityModifier + ", horizontalSpeedMultiplier=" + this.horizontalSpeedMultiplier + ", jumpForceMultiplier=" + this.jumpForceMultiplier + "}";
/*     */   }
/*     */   
/*     */   protected BlockMovementSettings() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\BlockMovementSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */