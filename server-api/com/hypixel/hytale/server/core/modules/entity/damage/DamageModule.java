/*     */ package com.hypixel.hytale.server.core.modules.entity.damage;
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.component.ComponentRegistryProxy;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.SystemGroup;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemGroupDependency;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.entity.knockback.KnockbackSystems;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.commands.DesyncDamageCommand;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatsModule;
/*     */ import com.hypixel.hytale.server.core.modules.entityui.EntityUIModule;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class DamageModule extends JavaPlugin {
/*  23 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(DamageModule.class)
/*  24 */     .depends(new Class[] { EntityModule.class
/*  25 */       }).depends(new Class[] { EntityStatsModule.class
/*  26 */       }).depends(new Class[] { EntityUIModule.class
/*  27 */       }).build(); private static DamageModule instance;
/*     */   private ComponentType<EntityStore, DeathComponent> deathComponentType;
/*     */   private ComponentType<EntityStore, DeferredCorpseRemoval> deferredCorpseRemovalComponentType;
/*     */   
/*     */   public static DamageModule get() {
/*  32 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   private SystemGroup<EntityStore> gatherDamageGroup;
/*     */   
/*     */   private SystemGroup<EntityStore> filterDamageGroup;
/*     */   private SystemGroup<EntityStore> inspectDamageGroup;
/*     */   
/*     */   public DamageModule(@Nonnull JavaPluginInit init) {
/*  42 */     super(init);
/*  43 */     instance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  48 */     ComponentRegistryProxy<EntityStore> entityStoreRegistry = getEntityStoreRegistry();
/*     */     
/*  50 */     this.deathComponentType = entityStoreRegistry.registerComponent(DeathComponent.class, "Death", DeathComponent.CODEC);
/*  51 */     this.deferredCorpseRemovalComponentType = entityStoreRegistry.registerComponent(DeferredCorpseRemoval.class, () -> {
/*     */           throw new UnsupportedOperationException("not supported");
/*     */         });
/*     */     
/*  55 */     this.gatherDamageGroup = entityStoreRegistry.registerSystemGroup();
/*  56 */     this.filterDamageGroup = entityStoreRegistry.registerSystemGroup();
/*  57 */     this.inspectDamageGroup = entityStoreRegistry.registerSystemGroup();
/*     */     
/*  59 */     entityStoreRegistry.registerSystem(new OrderGatherFilter());
/*     */ 
/*     */     
/*  62 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.ApplyDamage());
/*     */ 
/*     */ 
/*     */     
/*  66 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.CanBreathe());
/*  67 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.OutOfWorldDamage());
/*  68 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.FallDamagePlayers());
/*  69 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.FallDamageNPCs());
/*     */ 
/*     */     
/*  72 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.FilterPlayerWorldConfig());
/*  73 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.FilterNPCWorldConfig());
/*  74 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.FilterUnkillable());
/*  75 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.PlayerDamageFilterSystem());
/*  76 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.WieldingDamageReduction());
/*  77 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.WieldingKnockbackReduction());
/*  78 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.ArmorKnockbackReduction());
/*  79 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.ArmorDamageReduction());
/*  80 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.HackKnockbackValues());
/*     */ 
/*     */     
/*  83 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.RecordLastCombat());
/*  84 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.ApplyParticles());
/*  85 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.ApplySoundEffects());
/*  86 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.HitAnimation());
/*  87 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.TrackLastDamage());
/*  88 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.DamageArmor());
/*  89 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.DamageStamina());
/*  90 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.DamageAttackerTool());
/*  91 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.PlayerHitIndicators());
/*  92 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.ReticleEvents());
/*  93 */     entityStoreRegistry.registerSystem((ISystem)new DamageSystems.EntityUIEvents());
/*     */ 
/*     */     
/*  96 */     entityStoreRegistry.registerSystem((ISystem)new KnockbackSystems.ApplyKnockback());
/*  97 */     entityStoreRegistry.registerSystem((ISystem)new KnockbackSystems.ApplyPlayerKnockback());
/*     */ 
/*     */     
/* 100 */     entityStoreRegistry.registerSystem((ISystem)new DeathSystems.ClearHealth());
/* 101 */     entityStoreRegistry.registerSystem((ISystem)new DeathSystems.ClearInteractions());
/* 102 */     entityStoreRegistry.registerSystem((ISystem)new DeathSystems.ClearEntityEffects());
/* 103 */     entityStoreRegistry.registerSystem((ISystem)new DeathSystems.PlayerKilledPlayer());
/* 104 */     entityStoreRegistry.registerSystem((ISystem)new DeathSystems.DropPlayerDeathItems());
/* 105 */     entityStoreRegistry.registerSystem((ISystem)new DeathSystems.PlayerDropItemsConfig());
/* 106 */     entityStoreRegistry.registerSystem((ISystem)new DeathSystems.RunDeathInteractions());
/* 107 */     entityStoreRegistry.registerSystem((ISystem)new DeathSystems.KillFeed());
/* 108 */     entityStoreRegistry.registerSystem((ISystem)new DeathSystems.PlayerDeathScreen());
/* 109 */     entityStoreRegistry.registerSystem((ISystem)new DeathSystems.PlayerDeathMarker());
/* 110 */     entityStoreRegistry.registerSystem((ISystem)new DeathSystems.CorpseRemoval());
/* 111 */     entityStoreRegistry.registerSystem((ISystem)new DeathSystems.DeathAnimation());
/* 112 */     entityStoreRegistry.registerSystem((ISystem)new DeathSystems.SpawnedDeathAnimation());
/*     */ 
/*     */     
/* 115 */     entityStoreRegistry.registerSystem((ISystem)new RespawnSystems.ResetStatsRespawnSystem());
/* 116 */     entityStoreRegistry.registerSystem((ISystem)new RespawnSystems.ResetPlayerRespawnSystem());
/* 117 */     entityStoreRegistry.registerSystem((ISystem)new RespawnSystems.ClearEntityEffectsRespawnSystem());
/* 118 */     entityStoreRegistry.registerSystem((ISystem)new RespawnSystems.ClearInteractionsRespawnSystem());
/* 119 */     entityStoreRegistry.registerSystem((ISystem)new RespawnSystems.RespawnControllerRespawnSystem());
/* 120 */     entityStoreRegistry.registerSystem((ISystem)new RespawnSystems.CheckBrokenItemsRespawnSystem());
/*     */     
/* 122 */     entityStoreRegistry.registerSystem((ISystem)new DamageCalculatorSystems.SequenceModifier());
/*     */     
/* 124 */     getCommandRegistry().registerCommand((AbstractCommand)new DesyncDamageCommand());
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, DeathComponent> getDeathComponentType() {
/* 128 */     return this.deathComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, DeferredCorpseRemoval> getDeferredCorpseRemovalComponentType() {
/* 132 */     return this.deferredCorpseRemovalComponentType;
/*     */   }
/*     */   
/*     */   public SystemGroup<EntityStore> getGatherDamageGroup() {
/* 136 */     return this.gatherDamageGroup;
/*     */   }
/*     */   
/*     */   public SystemGroup<EntityStore> getFilterDamageGroup() {
/* 140 */     return this.filterDamageGroup;
/*     */   }
/*     */   
/*     */   public SystemGroup<EntityStore> getInspectDamageGroup() {
/* 144 */     return this.inspectDamageGroup;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static class OrderGatherFilter
/*     */     implements ISystem<EntityStore>
/*     */   {
/* 151 */     private final Set<Dependency<EntityStore>> dependencies = (Set)Set.of(new SystemGroupDependency(Order.AFTER, 
/* 152 */           DamageModule.get().getGatherDamageGroup()), new SystemGroupDependency(Order.BEFORE, 
/* 153 */           DamageModule.get().getFilterDamageGroup()));
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 159 */       return this.dependencies;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\damage\DamageModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */