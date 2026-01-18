/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector4d;
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.entity.ExplosionConfig;
/*     */ import com.hypixel.hytale.server.core.entity.ExplosionUtils;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.entities.ProjectileComponent;
/*     */ import com.hypixel.hytale.server.core.meta.DynamicMetaStore;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.InteractionTypeUtils;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.component.Projectile;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class ExplodeInteraction
/*     */   extends SimpleInstantInteraction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<ExplodeInteraction> CODEC;
/*     */   
/*     */   static {
/*  48 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ExplodeInteraction.class, ExplodeInteraction::new, SimpleInstantInteraction.CODEC).documentation("Performs an explosion using the provided config.")).appendInherited(new KeyedCodec("Config", (Codec)ExplosionConfig.CODEC), (interaction, s) -> interaction.config = s, interaction -> interaction.config, (interaction, parent) -> interaction.config = parent.config).addValidator(Validators.nonNull()).documentation("The explosion config associated with this projectile.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  54 */   public static final Damage.EnvironmentSource DAMAGE_SOURCE_EXPLOSION = new Damage.EnvironmentSource("explosion");
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private ExplosionConfig config;
/*     */ 
/*     */ 
/*     */   
/*     */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*     */     Vector3d position;
/*  64 */     assert this.config != null;
/*     */     
/*  66 */     DynamicMetaStore<InteractionContext> metaStore = context.getMetaStore();
/*  67 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*  68 */     assert commandBuffer != null;
/*     */     
/*  70 */     Ref<EntityStore> ref = context.getEntity();
/*  71 */     Ref<EntityStore> ownerRef = context.getOwningEntity();
/*     */ 
/*     */ 
/*     */     
/*  75 */     World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/*  76 */     Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*     */     
/*  78 */     BlockPosition blockPosition = (BlockPosition)metaStore.getIfPresentMetaObject(Interaction.TARGET_BLOCK);
/*     */ 
/*     */     
/*  81 */     Vector4d hitLocation = (Vector4d)metaStore.getIfPresentMetaObject(Interaction.HIT_LOCATION);
/*  82 */     if (hitLocation != null) {
/*  83 */       position = new Vector3d(hitLocation.x, hitLocation.y, hitLocation.z);
/*     */     
/*     */     }
/*  86 */     else if (InteractionTypeUtils.isCollisionType(type) && blockPosition != null) {
/*  87 */       long chunkIndex = ChunkUtil.indexChunkFromBlock(blockPosition.x, blockPosition.z);
/*  88 */       Ref<ChunkStore> chunkReference = ((ChunkStore)chunkStore.getExternalData()).getChunkReference(chunkIndex);
/*     */ 
/*     */       
/*  91 */       if (chunkReference == null || !chunkReference.isValid())
/*     */         return; 
/*  93 */       WorldChunk worldChunkComponent = (WorldChunk)chunkStore.getComponent(chunkReference, WorldChunk.getComponentType());
/*  94 */       assert worldChunkComponent != null;
/*     */       
/*  96 */       BlockChunk blockChunkComponent = (BlockChunk)chunkStore.getComponent(chunkReference, BlockChunk.getComponentType());
/*  97 */       assert blockChunkComponent != null;
/*     */       
/*  99 */       BlockType blockType = worldChunkComponent.getBlockType(blockPosition.x, blockPosition.y, blockPosition.z);
/* 100 */       if (blockType == null)
/*     */         return; 
/* 102 */       BlockSection blockSection = blockChunkComponent.getSectionAtBlockY(blockPosition.y);
/* 103 */       int rotationIndex = blockSection.getRotationIndex(blockPosition.x, blockPosition.y, blockPosition.z);
/* 104 */       position = new Vector3d();
/* 105 */       blockType.getBlockCenter(rotationIndex, position);
/* 106 */       position.add(blockPosition.x, blockPosition.y, blockPosition.z);
/*     */     }
/*     */     else {
/*     */       
/* 110 */       TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(ref, TransformComponent.getComponentType());
/* 111 */       assert transformComponent != null;
/* 112 */       position = transformComponent.getPosition();
/*     */     } 
/*     */     
/* 115 */     Archetype<EntityStore> archetype = commandBuffer.getArchetype(ref);
/* 116 */     boolean isProjectile = (archetype.contains(Projectile.getComponentType()) || archetype.contains(ProjectileComponent.getComponentType()));
/*     */     
/* 118 */     Damage.Source damageSource = isProjectile ? (Damage.Source)new Damage.ProjectileSource(ownerRef, ref) : (Damage.Source)DAMAGE_SOURCE_EXPLOSION;
/*     */     
/* 120 */     ExplosionUtils.performExplosion(damageSource, position, this.config, isProjectile ? ref : null, commandBuffer, (ComponentAccessor)chunkStore);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\ExplodeInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */