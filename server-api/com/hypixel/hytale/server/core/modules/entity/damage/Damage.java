/*     */ package com.hypixel.hytale.server.core.modules.entity.damage;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.system.CancellableEcsEvent;
/*     */ import com.hypixel.hytale.math.vector.Vector4d;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelParticle;
/*     */ import com.hypixel.hytale.server.core.asset.type.particle.config.WorldParticle;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*     */ import com.hypixel.hytale.server.core.entity.knockback.KnockbackComponent;
/*     */ import com.hypixel.hytale.server.core.meta.DynamicMetaStore;
/*     */ import com.hypixel.hytale.server.core.meta.IMetaRegistry;
/*     */ import com.hypixel.hytale.server.core.meta.IMetaStore;
/*     */ import com.hypixel.hytale.server.core.meta.IMetaStoreImpl;
/*     */ import com.hypixel.hytale.server.core.meta.MetaKey;
/*     */ import com.hypixel.hytale.server.core.meta.MetaRegistry;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.DisplayNameComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Locale;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class Damage
/*     */   extends CancellableEcsEvent
/*     */   implements IMetaStore<Damage>
/*     */ {
/*     */   @Nonnull
/*  31 */   private static final Message MESSAGE_GENERAL_DAMAGE_CAUSE_UNKNOWN = Message.translation("server.general.damageCauses.unknown");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  37 */   public static final MetaRegistry<Damage> META_REGISTRY = new MetaRegistry();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  43 */   public static final MetaKey<Vector4d> HIT_LOCATION = META_REGISTRY.registerMetaObject();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  49 */   public static final MetaKey<Float> HIT_ANGLE = META_REGISTRY.registerMetaObject();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  55 */   public static final MetaKey<Particles> IMPACT_PARTICLES = META_REGISTRY.registerMetaObject();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  61 */   public static final MetaKey<SoundEffect> IMPACT_SOUND_EFFECT = META_REGISTRY.registerMetaObject();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  67 */   public static final MetaKey<SoundEffect> PLAYER_IMPACT_SOUND_EFFECT = META_REGISTRY.registerMetaObject();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  73 */   public static final MetaKey<CameraEffect> CAMERA_EFFECT = META_REGISTRY.registerMetaObject();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  79 */   public static final MetaKey<String> DEATH_ICON = META_REGISTRY.registerMetaObject();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  85 */   public static final MetaKey<Boolean> BLOCKED = META_REGISTRY.registerMetaObject(data -> Boolean.FALSE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  91 */   public static final MetaKey<Float> STAMINA_DRAIN_MULTIPLIER = META_REGISTRY.registerMetaObject();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  97 */   public static final MetaKey<Boolean> CAN_BE_PREDICTED = META_REGISTRY.registerMetaObject(data -> Boolean.FALSE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 103 */   public static final MetaKey<KnockbackComponent> KNOCKBACK_COMPONENT = META_REGISTRY.registerMetaObject();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 109 */   public static final Source NULL_SOURCE = new Source()
/*     */     {
/*     */     
/*     */     };
/*     */   
/*     */   @Nonnull
/* 115 */   private final IMetaStoreImpl<Damage> metaStore = (IMetaStoreImpl<Damage>)new DynamicMetaStore(this, (IMetaRegistry)META_REGISTRY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final float initialAmount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int damageCauseIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private Source source;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float amount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Damage(@Nonnull Source source, @Nonnull DamageCause damageCause, float amount) {
/* 147 */     this.source = source;
/* 148 */     this.damageCauseIndex = DamageCause.getAssetMap().getIndex(damageCause.getId());
/* 149 */     this.initialAmount = this.amount = amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Damage(@Nonnull Source source, int damageCauseIndex, float amount) {
/* 160 */     this.source = source;
/* 161 */     this.damageCauseIndex = damageCauseIndex;
/* 162 */     this.initialAmount = this.amount = amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDamageCauseIndex() {
/* 169 */     return this.damageCauseIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDamageCauseIndex(int damageCauseIndex) {
/* 178 */     this.damageCauseIndex = damageCauseIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @Nullable
/*     */   public DamageCause getCause() {
/* 189 */     return (DamageCause)DamageCause.getAssetMap().getAsset(this.damageCauseIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Source getSource() {
/* 199 */     return this.source;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSource(@Nonnull Source source) {
/* 208 */     this.source = source;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAmount() {
/* 217 */     return this.amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAmount(float amount) {
/* 226 */     this.amount = amount;
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
/*     */   public float getInitialAmount() {
/* 238 */     return this.initialAmount;
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
/*     */   @Nonnull
/*     */   public Message getDeathMessage(@Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 254 */     return this.source.getDeathMessage(this, targetRef, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IMetaStoreImpl<Damage> getMetaStore() {
/* 260 */     return this.metaStore;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Particles
/*     */   {
/*     */     @Nullable
/*     */     protected ModelParticle[] modelParticles;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     protected WorldParticle[] worldParticles;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected double viewDistance;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Particles(@Nullable ModelParticle[] modelParticles, @Nullable WorldParticle[] worldParticles, double viewDistance) {
/* 295 */       this.modelParticles = modelParticles;
/* 296 */       this.worldParticles = worldParticles;
/* 297 */       this.viewDistance = viewDistance;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public ModelParticle[] getModelParticles() {
/* 305 */       return this.modelParticles;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setModelParticles(@Nullable ModelParticle[] modelParticles) {
/* 314 */       this.modelParticles = modelParticles;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public WorldParticle[] getWorldParticles() {
/* 322 */       return this.worldParticles;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setWorldParticles(@Nullable WorldParticle[] worldParticles) {
/* 331 */       this.worldParticles = worldParticles;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getViewDistance() {
/* 338 */       return this.viewDistance;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setViewDistance(double viewDistance) {
/* 347 */       this.viewDistance = viewDistance;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SoundEffect
/*     */   {
/*     */     private int soundEventIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SoundEffect(int soundEventIndex) {
/* 369 */       this.soundEventIndex = soundEventIndex;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setSoundEventIndex(int soundEventIndex) {
/* 378 */       this.soundEventIndex = soundEventIndex;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getSoundEventIndex() {
/* 385 */       return this.soundEventIndex;
/*     */     } }
/*     */   public static final class CameraEffect extends Record { private final int cameraEffectIndex;
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/modules/entity/damage/Damage$CameraEffect;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #396	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/modules/entity/damage/Damage$CameraEffect;
/*     */     }
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/modules/entity/damage/Damage$CameraEffect;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #396	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/modules/entity/damage/Damage$CameraEffect;
/*     */     }
/*     */     
/*     */     public int cameraEffectIndex() {
/* 396 */       return this.cameraEffectIndex;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CameraEffect(int cameraEffectIndex) {
/* 403 */       this.cameraEffectIndex = cameraEffectIndex;
/*     */     }
/*     */     public final boolean equals(Object o) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/modules/entity/damage/Damage$CameraEffect;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #396	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/core/modules/entity/damage/Damage$CameraEffect;
/*     */       //   0	8	1	o	Ljava/lang/Object;
/*     */     }
/*     */     
/*     */     public int getEffectIndex() {
/* 410 */       return this.cameraEffectIndex;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Source
/*     */   {
/*     */     @Nonnull
/*     */     default Message getDeathMessage(@Nonnull Damage info, @Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 435 */       DamageCause damageCauseAsset = (DamageCause)DamageCause.getAssetMap().getAsset(info.damageCauseIndex);
/* 436 */       String causeId = (damageCauseAsset != null) ? damageCauseAsset.getId().toLowerCase(Locale.ROOT) : "unknown";
/* 437 */       Message damageCauseMessage = Message.translation("server.general.damageCauses." + causeId);
/*     */       
/* 439 */       return Message.translation("server.general.killedBy")
/* 440 */         .param("damageSource", damageCauseMessage);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EnvironmentSource
/*     */     implements Source
/*     */   {
/*     */     @Nonnull
/*     */     private final String type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EnvironmentSource(@Nonnull String type) {
/* 461 */       this.type = type;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String getType() {
/* 469 */       return this.type;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Message getDeathMessage(@Nonnull Damage info, @Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 475 */       return Message.translation("server.general.killedBy")
/* 476 */         .param("damageSource", this.type);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EntitySource
/*     */     implements Source
/*     */   {
/*     */     @Nonnull
/*     */     protected final Ref<EntityStore> sourceRef;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EntitySource(@Nonnull Ref<EntityStore> sourceRef) {
/* 498 */       this.sourceRef = sourceRef;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Ref<EntityStore> getRef() {
/* 508 */       return this.sourceRef;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Message getDeathMessage(@Nonnull Damage info, @Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 514 */       Message damageCauseMessage = Damage.MESSAGE_GENERAL_DAMAGE_CAUSE_UNKNOWN;
/* 515 */       DisplayNameComponent displayNameComponent = (DisplayNameComponent)componentAccessor.getComponent(this.sourceRef, DisplayNameComponent.getComponentType());
/* 516 */       if (displayNameComponent != null) {
/* 517 */         Message displayName = displayNameComponent.getDisplayName();
/* 518 */         if (displayName != null) {
/* 519 */           damageCauseMessage = displayName;
/*     */         }
/*     */       } 
/*     */       
/* 523 */       return Message.translation("server.general.killedBy")
/* 524 */         .param("damageSource", damageCauseMessage);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ProjectileSource
/*     */     extends EntitySource
/*     */   {
/*     */     @Nonnull
/*     */     protected final Ref<EntityStore> projectile;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ProjectileSource(@Nonnull Ref<EntityStore> shooter, @Nonnull Ref<EntityStore> projectile) {
/* 548 */       super(shooter);
/* 549 */       this.projectile = projectile;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Ref<EntityStore> getProjectile() {
/* 557 */       return this.projectile;
/*     */     }
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
/*     */   public static class CommandSource
/*     */     implements Source
/*     */   {
/*     */     @Nonnull
/*     */     private static final String COMMAND_NAME_UNKNOWN = "Unknown";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final CommandSender commandSender;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private final String commandName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CommandSource(@Nonnull CommandSender commandSender, @Nonnull AbstractCommand cmd) {
/* 618 */       this(commandSender, cmd.getName());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CommandSource(@Nonnull CommandSender commandSender, @Nullable String commandName) {
/* 628 */       this.commandSender = commandSender;
/* 629 */       this.commandName = commandName;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Message getDeathMessage(@Nonnull Damage info, @Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 635 */       return Message.translation("server.general.killedByCommand")
/* 636 */         .param("displayName", this.commandSender.getDisplayName())
/* 637 */         .param("commandName", (this.commandName != null) ? this.commandName : "Unknown");
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\damage\Damage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */