/*     */ package com.hypixel.hytale.server.npc.asset.builder.validators.asset;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.BreakBlockInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.ChangeBlockInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.MovementConditionInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.PlaceBlockInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.Collector;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.CollectorTag;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.ListCollector;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*     */ import com.hypixel.hytale.server.npc.role.support.CombatSupport;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class CombatInteractionValidator
/*     */   extends AssetValidator
/*     */ {
/*  28 */   private static final Set<Class<? extends Interaction>> DISALLOWED_INTERACTION_TYPES = (Set)Set.of(BreakBlockInteraction.class, PlaceBlockInteraction.class, ChangeBlockInteraction.class, MovementConditionInteraction.class);
/*     */ 
/*     */   
/*  31 */   private final List<String> disallowedInteractions = (List<String>)new ObjectArrayList();
/*     */   
/*     */   private boolean assetExists;
/*     */   
/*     */   private boolean attackTag;
/*     */   
/*     */   private boolean onlyOneAttackType;
/*     */   
/*     */   private boolean onlyOneAimingReference;
/*     */ 
/*     */   
/*     */   private CombatInteractionValidator(EnumSet<AssetValidator.Config> config) {
/*  43 */     super(config);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getDomain() {
/*  49 */     return "Interaction";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean test(String value) {
/*  54 */     RootInteraction interaction = (RootInteraction)RootInteraction.getAssetMap().getAsset(value);
/*  55 */     this.assetExists = (interaction != null);
/*  56 */     if (!this.assetExists) return false;
/*     */     
/*  58 */     this.attackTag = testAttackTag(interaction);
/*  59 */     this.onlyOneAttackType = testOnlyOneAttackType(interaction);
/*     */     
/*  61 */     Set<String> aimingReferenceInteractions = new HashSet<>();
/*  62 */     this.disallowedInteractions.clear();
/*  63 */     Set<String> aimingReferenceTags = Interaction.getAssetMap().getKeysForTag(CombatSupport.AIMING_REFERENCE_TAG_INDEX);
/*  64 */     ListCollector<Object> collector = new ListCollector((collectorTag, interactionContext, iteratedInteraction) -> {
/*     */           if (aimingReferenceTags.contains(iteratedInteraction.getId()))
/*     */             aimingReferenceInteractions.add(iteratedInteraction.getId());  if (DISALLOWED_INTERACTION_TYPES.contains(iteratedInteraction.getClass()))
/*     */             this.disallowedInteractions.add(iteratedInteraction.getClass().getSimpleName()); 
/*     */           return null;
/*     */         });
/*  70 */     InteractionManager.walkChain((Collector)collector, InteractionType.Primary, InteractionContext.withoutEntity(), interaction);
/*  71 */     this.onlyOneAimingReference = (aimingReferenceInteractions.size() <= 1);
/*     */     
/*  73 */     return (this.attackTag && this.onlyOneAttackType && this.onlyOneAimingReference && this.disallowedInteractions.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String errorMessage(String value, String attribute) {
/*  79 */     if (!this.assetExists) return "Interaction \"" + value + "\" does not exist for attribute \"" + attribute + "\"";
/*     */     
/*  81 */     StringBuilder sb = (new StringBuilder("Attribute \"")).append(attribute).append("\" uses interaction with name \"").append(value).append("\" which:");
/*  82 */     if (!this.attackTag) sb.append("\n  - Is not marked with the \"").append("Attack").append("\" tag"); 
/*  83 */     if (!this.onlyOneAttackType) sb.append("\n  - Has too many attack types (only one may be defined)"); 
/*  84 */     if (!this.onlyOneAimingReference) sb.append("\n  - Has too many ").append("AimingReference").append(" tags"); 
/*  85 */     if (!this.disallowedInteractions.isEmpty())
/*  86 */       sb.append("\n  - Contains the following disallowed interaction types: ").append(String.join(", ", (Iterable)this.disallowedInteractions)); 
/*  87 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getAssetName() {
/*  93 */     return RootInteraction.class.getSimpleName();
/*     */   }
/*     */   
/*     */   public static boolean testAttackTag(@Nonnull RootInteraction interaction) {
/*  97 */     return RootInteraction.getAssetMap().getKeysForTag(CombatSupport.ATTACK_TAG_INDEX).contains(interaction.getId());
/*     */   }
/*     */   
/*     */   public static boolean testOnlyOneAttackType(@Nonnull RootInteraction interaction) {
/* 101 */     IndexedLookupTableAssetMap<String, RootInteraction> assetMap = RootInteraction.getAssetMap();
/* 102 */     boolean meleeTag = assetMap.getKeysForTag(CombatSupport.MELEE_TAG_INDEX).contains(interaction.getId());
/* 103 */     boolean rangedTag = assetMap.getKeysForTag(CombatSupport.RANGED_TAG_INDEX).contains(interaction.getId());
/* 104 */     boolean blockTag = assetMap.getKeysForTag(CombatSupport.BLOCK_TAG_INDEX).contains(interaction.getId());
/* 105 */     if (meleeTag) return (!rangedTag && !blockTag); 
/* 106 */     if (rangedTag) return !blockTag; 
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static CombatInteractionValidator required() {
/* 117 */     return new CombatInteractionValidator();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CombatInteractionValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 122 */     return new CombatInteractionValidator(config);
/*     */   }
/*     */   
/*     */   private CombatInteractionValidator() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\asset\CombatInteractionValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */