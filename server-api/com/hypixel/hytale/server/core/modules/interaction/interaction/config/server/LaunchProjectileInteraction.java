/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.BrokenPenalties;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.asset.type.projectile.config.Projectile;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.ProjectileComponent;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.Intangible;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.config.BallisticData;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.config.BallisticDataProvider;
/*     */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ @Deprecated(forRemoval = true)
/*     */ public class LaunchProjectileInteraction extends SimpleInstantInteraction implements BallisticDataProvider {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<LaunchProjectileInteraction> CODEC;
/*     */   protected String projectileId;
/*     */   
/*     */   static {
/*  50 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(LaunchProjectileInteraction.class, LaunchProjectileInteraction::new, SimpleInstantInteraction.CODEC).documentation("Launches a projectile.")).appendInherited(new KeyedCodec("ProjectileId", (Codec)Codec.STRING), (i, o) -> i.projectileId = o, i -> i.projectileId, (i, p) -> i.projectileId = p.projectileId).addValidator(Validators.nonNull()).addValidator((Validator)Projectile.VALIDATOR_CACHE.getValidator().late()).add()).build();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getProjectileId() {
/*  55 */     return this.projectileId;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BallisticData getBallisticData() {
/*  61 */     return (BallisticData)Projectile.getAssetMap().getAsset(this.projectileId);
/*     */   }
/*     */   
/*     */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*     */     LivingEntity attackerLivingEntity;
/*  66 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*  67 */     assert commandBuffer != null;
/*     */     
/*  69 */     World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/*  70 */     Ref<EntityStore> attackerRef = context.getEntity();
/*     */     
/*  72 */     Entity entity = EntityUtils.getEntity(attackerRef, (ComponentAccessor)commandBuffer); if (entity instanceof LivingEntity) { attackerLivingEntity = (LivingEntity)entity; }
/*     */     else
/*     */     { return; }
/*  75 */      Transform lookVec = TargetUtil.getLook(attackerRef, (ComponentAccessor)commandBuffer);
/*  76 */     Vector3d lookPosition = lookVec.getPosition();
/*  77 */     Vector3f lookRotation = lookVec.getRotation();
/*     */     
/*  79 */     UUIDComponent attackerUuidComponent = (UUIDComponent)commandBuffer.getComponent(attackerRef, UUIDComponent.getComponentType());
/*  80 */     assert attackerUuidComponent != null;
/*     */     
/*  82 */     UUID attackerUuid = attackerUuidComponent.getUuid();
/*     */     
/*  84 */     TimeResource timeResource = (TimeResource)commandBuffer.getResource(TimeResource.getResourceType());
/*  85 */     Holder<EntityStore> holder = ProjectileComponent.assembleDefaultProjectile(timeResource, this.projectileId, lookPosition, lookRotation);
/*     */     
/*  87 */     ProjectileComponent projectileComponent = (ProjectileComponent)holder.getComponent(ProjectileComponent.getComponentType());
/*  88 */     assert projectileComponent != null;
/*     */     
/*  90 */     holder.ensureComponent(Intangible.getComponentType());
/*     */     
/*  92 */     if (projectileComponent.getProjectile() == null) {
/*  93 */       projectileComponent.initialize();
/*     */       
/*  95 */       if (projectileComponent.getProjectile() == null) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 100 */     projectileComponent.shoot(holder, attackerUuid, lookPosition.getX(), lookPosition.getY(), lookPosition.getZ(), lookRotation.getYaw(), lookRotation.getPitch());
/* 101 */     commandBuffer.addEntity(holder, AddReason.SPAWN);
/*     */     
/* 103 */     ItemStack itemInHand = context.getHeldItem();
/* 104 */     if (itemInHand != null && !itemInHand.isEmpty()) {
/*     */ 
/*     */       
/* 107 */       Item item = itemInHand.getItem();
/* 108 */       if (attackerLivingEntity.canDecreaseItemStackDurability(attackerRef, (ComponentAccessor)commandBuffer) && !itemInHand.isUnbreakable() && 
/* 109 */         item.getWeapon() != null) {
/* 110 */         Inventory inventory = attackerLivingEntity.getInventory();
/* 111 */         ItemContainer section = inventory.getSectionById(context.getHeldItemSectionId());
/*     */         
/* 113 */         if (section != null) {
/* 114 */           attackerLivingEntity.updateItemStackDurability(attackerRef, itemInHand, section, context.getHeldItemSlot(), -item.getDurabilityLossOnHit(), (ComponentAccessor)commandBuffer);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 120 */       if (itemInHand.isBroken()) {
/* 121 */         BrokenPenalties brokenPenalties = world.getGameplayConfig().getItemDurabilityConfig().getBrokenPenalties();
/* 122 */         projectileComponent.applyBrokenPenalty((float)brokenPenalties.getWeapon(1.0D));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void simulateFirstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\LaunchProjectileInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */