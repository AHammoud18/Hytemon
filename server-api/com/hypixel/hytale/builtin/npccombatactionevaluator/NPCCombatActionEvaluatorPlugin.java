/*     */ package com.hypixel.hytale.builtin.npccombatactionevaluator;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetMap;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.conditions.RecentSustainedDamageCondition;
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.conditions.TargetMemoryCountCondition;
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.conditions.TotalSustainedDamageCondition;
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.config.CombatBalanceAsset;
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.evaluator.CombatActionEvaluator;
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.evaluator.combatactions.CombatActionOption;
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.memory.DamageMemory;
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.memory.DamageMemorySystems;
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.memory.TargetMemory;
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.memory.TargetMemorySystems;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.config.balancing.BalanceAsset;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.base.Condition;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class NPCCombatActionEvaluatorPlugin
/*     */   extends JavaPlugin {
/*     */   public static final String CAE_MARKED_TARGET_SLOT = "CAETargetSlot";
/*     */   public static final String CAE_MIN_RANGE_PARAMETER = "CAEMinRange";
/*     */   
/*     */   public static NPCCombatActionEvaluatorPlugin get() {
/*  38 */     return instance;
/*     */   }
/*     */   public static final String CAE_MAX_RANGE_PARAMETER = "CAEMaxRange"; public static final String CAE_POSITIONING_ANGLE_PARAMETER = "CAEPositioningAngle"; private static NPCCombatActionEvaluatorPlugin instance;
/*     */   private ComponentType<EntityStore, TargetMemory> targetMemoryComponentType;
/*     */   private ComponentType<EntityStore, CombatActionEvaluator> combatActionEvaluatorComponentType;
/*     */   private ComponentType<EntityStore, CombatActionEvaluatorSystems.CombatConstructionData> combatConstructionDataComponentType;
/*     */   private ComponentType<EntityStore, DamageMemory> damageMemoryComponentType;
/*     */   
/*     */   public NPCCombatActionEvaluatorPlugin(@Nonnull JavaPluginInit init) {
/*  47 */     super(init);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  52 */     instance = this;
/*     */     
/*  54 */     BalanceAsset.CODEC.register("CombatActionEvaluator", CombatBalanceAsset.class, CombatBalanceAsset.CODEC);
/*     */     
/*  56 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(CombatActionOption.class, (AssetMap)new IndexedLookupTableAssetMap(x$0 -> new CombatActionOption[x$0]))
/*  57 */         .setPath("NPC/DecisionMaking/CombatActions"))
/*  58 */         .setCodec((AssetCodec)CombatActionOption.CODEC))
/*  59 */         .setKeyFunction(CombatActionOption::getId))
/*  60 */         .setReplaceOnRemove(CombatActionOption::getNothingFor))
/*  61 */         .loadsAfter(new Class[] { Item.class, Condition.class, RootInteraction.class
/*  62 */           })).loadsBefore(new Class[] { BalanceAsset.class
/*  63 */           })).build());
/*     */     
/*  65 */     NPCPlugin.get().registerCoreComponentType("CombatActionEvaluator", com.hypixel.hytale.builtin.npccombatactionevaluator.corecomponents.builders.BuilderSensorCombatActionEvaluator::new)
/*  66 */       .registerCoreComponentType("CombatTargets", com.hypixel.hytale.builtin.npccombatactionevaluator.corecomponents.builders.BuilderCombatTargetCollector::new)
/*  67 */       .registerCoreComponentType("HasHostileTargetMemory", com.hypixel.hytale.builtin.npccombatactionevaluator.corecomponents.builders.BuilderSensorHasHostileTargetMemory::new)
/*  68 */       .registerCoreComponentType("CombatAbility", com.hypixel.hytale.builtin.npccombatactionevaluator.corecomponents.builders.BuilderActionCombatAbility::new)
/*  69 */       .registerCoreComponentType("AddToHostileTargetMemory", com.hypixel.hytale.builtin.npccombatactionevaluator.corecomponents.builders.BuilderActionAddToTargetMemory::new);
/*     */     
/*  71 */     this.targetMemoryComponentType = getEntityStoreRegistry().registerComponent(TargetMemory.class, () -> {
/*     */           throw new UnsupportedOperationException("Not implemented");
/*     */         });
/*  74 */     this.combatActionEvaluatorComponentType = getEntityStoreRegistry().registerComponent(CombatActionEvaluator.class, () -> {
/*     */           throw new UnsupportedOperationException("Not implemented");
/*     */         });
/*  77 */     this.combatConstructionDataComponentType = getEntityStoreRegistry().registerComponent(CombatActionEvaluatorSystems.CombatConstructionData.class, CombatConstructionData::new);
/*     */     
/*  79 */     this.damageMemoryComponentType = getEntityStoreRegistry().registerComponent(DamageMemory.class, DamageMemory::new);
/*     */     
/*  81 */     getEntityStoreRegistry().registerSystem((ISystem)new TargetMemorySystems.Ticking(this.targetMemoryComponentType));
/*  82 */     getEntityStoreRegistry().registerSystem((ISystem)new CombatActionEvaluatorSystems.EvaluatorTick(this.combatActionEvaluatorComponentType, this.targetMemoryComponentType, this.damageMemoryComponentType));
/*     */ 
/*     */     
/*  85 */     getEntityStoreRegistry().registerSystem((ISystem)new DamageMemorySystems.CollectDamage(this.damageMemoryComponentType));
/*     */     
/*  87 */     getEntityStoreRegistry().registerSystem((ISystem)new CombatActionEvaluatorSystems.OnAdded(this.combatConstructionDataComponentType));
/*     */     
/*  89 */     Condition.CODEC.register("RecentSustainedDamage", RecentSustainedDamageCondition.class, RecentSustainedDamageCondition.CODEC);
/*  90 */     Condition.CODEC.register("TotalSustainedDamage", TotalSustainedDamageCondition.class, TotalSustainedDamageCondition.CODEC);
/*  91 */     Condition.CODEC.register("KnownTargetCount", TargetMemoryCountCondition.class, TargetMemoryCountCondition.CODEC);
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, TargetMemory> getTargetMemoryComponentType() {
/*  95 */     return this.targetMemoryComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, CombatActionEvaluator> getCombatActionEvaluatorComponentType() {
/*  99 */     return this.combatActionEvaluatorComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, CombatActionEvaluatorSystems.CombatConstructionData> getCombatConstructionDataComponentType() {
/* 103 */     return this.combatConstructionDataComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, DamageMemory> getDamageMemoryComponentType() {
/* 107 */     return this.damageMemoryComponentType;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\npccombatactionevaluator\NPCCombatActionEvaluatorPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */