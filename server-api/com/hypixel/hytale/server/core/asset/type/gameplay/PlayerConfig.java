/*     */ package com.hypixel.hytale.server.core.asset.type.gameplay;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementConfig;
/*     */ import com.hypixel.hytale.server.core.modules.entity.hitboxcollision.HitboxCollisionConfig;
/*     */ import com.hypixel.hytale.server.core.modules.entity.repulsion.RepulsionConfig;
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
/*     */ public class PlayerConfig
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<PlayerConfig> CODEC;
/*     */   protected String hitboxCollisionConfigId;
/*     */   protected String repulsionConfigId;
/*     */   
/*     */   static {
/*  67 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PlayerConfig.class, PlayerConfig::new).appendInherited(new KeyedCodec("HitboxCollisionConfig", (Codec)Codec.STRING), (playerConfig, s) -> playerConfig.hitboxCollisionConfigId = s, playerConfig -> playerConfig.hitboxCollisionConfigId, (playerConfig, parent) -> playerConfig.hitboxCollisionConfigId = parent.hitboxCollisionConfigId).documentation("The HitboxCollision config to apply to all players.").addValidator(HitboxCollisionConfig.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("RepulsionConfig", (Codec)Codec.STRING), (playerConfig, s) -> playerConfig.repulsionConfigId = s, playerConfig -> playerConfig.repulsionConfigId, (playerConfig, parent) -> playerConfig.repulsionConfigId = parent.repulsionConfigId).documentation("The Repulsion to apply to all players.").addValidator(RepulsionConfig.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("MovementConfig", (Codec)Codec.STRING), (playerConfig, s) -> playerConfig.movementConfigId = s, playerConfig -> playerConfig.movementConfigId, (playerConfig, parent) -> playerConfig.movementConfigId = parent.movementConfigId).addValidator(MovementConfig.VALIDATOR_CACHE.getValidator()).documentation("The maximum number of simultaneous deployable entities players are allowed to own.").add()).appendInherited(new KeyedCodec("MaxDeployableEntities", (Codec)Codec.INTEGER), (playerConfig, s) -> playerConfig.maxDeployableEntities = s.intValue(), playerConfig -> Integer.valueOf(playerConfig.maxDeployableEntities), (playerConfig, parent) -> playerConfig.maxDeployableEntities = parent.maxDeployableEntities).add()).afterDecode(playerConfig -> { if (playerConfig.hitboxCollisionConfigId != null) playerConfig.hitboxCollisionConfigIndex = HitboxCollisionConfig.getAssetMap().getIndexOrDefault(playerConfig.hitboxCollisionConfigId, -1);  if (playerConfig.repulsionConfigId != null) playerConfig.repulsionConfigIndex = RepulsionConfig.getAssetMap().getIndexOrDefault(playerConfig.repulsionConfigId, -1);  if (playerConfig.movementConfigId != null) playerConfig.movementConfigIndex = MovementConfig.getAssetMap().getIndexOrDefault(playerConfig.movementConfigId, 0);  })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   protected String movementConfigId = "BuiltinDefault";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   protected int hitboxCollisionConfigIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   protected int repulsionConfigIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   protected int movementConfigIndex = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   protected int maxDeployableEntities = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHitboxCollisionConfigIndex() {
/* 108 */     return this.hitboxCollisionConfigIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRepulsionConfigIndex() {
/* 115 */     return this.repulsionConfigIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMovementConfigIndex() {
/* 122 */     return this.movementConfigIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMovementConfigId() {
/* 129 */     return this.movementConfigId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxDeployableEntities() {
/* 136 */     return this.maxDeployableEntities;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\gameplay\PlayerConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */