/*     */ package com.hypixel.hytale.server.core.entity;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemTool;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat.Knockback;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExplosionConfig
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<ExplosionConfig> CODEC;
/*     */   
/*     */   static {
/* 102 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ExplosionConfig.class, ExplosionConfig::new).appendInherited(new KeyedCodec("DamageEntities", (Codec)Codec.BOOLEAN), (explosionConfig, b) -> explosionConfig.damageEntities = b.booleanValue(), explosionConfig -> Boolean.valueOf(explosionConfig.damageEntities), (explosionConfig, parent) -> explosionConfig.damageEntities = parent.damageEntities).documentation("Determines whether the explosion should damage entities.").add()).appendInherited(new KeyedCodec("DamageBlocks", (Codec)Codec.BOOLEAN), (explosionConfig, b) -> explosionConfig.damageBlocks = b.booleanValue(), explosionConfig -> Boolean.valueOf(explosionConfig.damageBlocks), (explosionConfig, parent) -> explosionConfig.damageBlocks = parent.damageBlocks).documentation("Determines whether the explosion should damage blocks.").add()).appendInherited(new KeyedCodec("BlockDamageRadius", (Codec)Codec.INTEGER), (explosionConfig, i) -> explosionConfig.blockDamageRadius = i.intValue(), explosionConfig -> Integer.valueOf(explosionConfig.blockDamageRadius), (explosionConfig, parent) -> explosionConfig.blockDamageRadius = parent.blockDamageRadius).documentation("The radius in which blocks should be damaged by the explosion.").add()).appendInherited(new KeyedCodec("BlockDamageFalloff", (Codec)Codec.FLOAT), (explosionConfig, f) -> explosionConfig.blockDamageFalloff = f.floatValue(), explosionConfig -> Float.valueOf(explosionConfig.entityDamageFalloff), (explosionConfig, parent) -> explosionConfig.entityDamageFalloff = parent.entityDamageFalloff).documentation("The falloff applied to the block damage.").add()).appendInherited(new KeyedCodec("BlockDropChance", (Codec)Codec.FLOAT), (explosionConfig, f) -> explosionConfig.blockDropChance = f.floatValue(), explosionConfig -> Float.valueOf(explosionConfig.blockDropChance), (explosionConfig, parent) -> explosionConfig.blockDropChance = parent.blockDropChance).documentation("The chance in which a block drops its loot after breaking.").add()).appendInherited(new KeyedCodec("EntityDamageRadius", (Codec)Codec.FLOAT), (explosionConfig, f) -> explosionConfig.entityDamageRadius = f.floatValue(), explosionConfig -> Float.valueOf(explosionConfig.entityDamageRadius), (explosionConfig, parent) -> explosionConfig.entityDamageRadius = parent.entityDamageRadius).documentation("The radius in which entities should be damaged by the explosion.").add()).appendInherited(new KeyedCodec("EntityDamage", (Codec)Codec.FLOAT), (explosionConfig, f) -> explosionConfig.entityDamage = f.floatValue(), explosionConfig -> Float.valueOf(explosionConfig.entityDamage), (explosionConfig, parent) -> explosionConfig.entityDamage = parent.entityDamage).documentation("The amount of damage to be applied to entities within range.").add()).appendInherited(new KeyedCodec("EntityDamageFalloff", (Codec)Codec.FLOAT), (explosionConfig, f) -> explosionConfig.entityDamageFalloff = f.floatValue(), explosionConfig -> Float.valueOf(explosionConfig.entityDamageFalloff), (explosionConfig, parent) -> explosionConfig.entityDamageFalloff = parent.entityDamageFalloff).documentation("The falloff applied to the entity damage.").add()).appendInherited(new KeyedCodec("Knockback", (Codec)Knockback.CODEC), (explosionConfig, s) -> explosionConfig.knockback = s, explosionConfig -> explosionConfig.knockback, (explosionConfig, parent) -> explosionConfig.knockback = parent.knockback).documentation("Determines the knockback effect applied to damaged entities.").add()).appendInherited(new KeyedCodec("ItemTool", (Codec)ItemTool.CODEC), (damageEffects, s) -> damageEffects.itemTool = s, damageEffects -> damageEffects.itemTool, (damageEffects, parent) -> damageEffects.itemTool = parent.itemTool).documentation("The item tool to reference when applying damage to blocks.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean damageEntities = true;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean damageBlocks = true;
/*     */ 
/*     */ 
/*     */   
/* 117 */   protected int blockDamageRadius = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   protected float blockDamageFalloff = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   protected float entityDamageRadius = 5.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   protected float entityDamage = 50.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   protected float entityDamageFalloff = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   protected float blockDropChance = 1.0F;
/*     */   @Nullable
/*     */   protected Knockback knockback;
/*     */   @Nullable
/*     */   protected ItemTool itemTool;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\ExplosionConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */