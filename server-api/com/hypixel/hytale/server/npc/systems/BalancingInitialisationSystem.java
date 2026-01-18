/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.HolderSystem;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatsSystems;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.modifier.Modifier;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import java.util.Set;
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
/*     */ public class BalancingInitialisationSystem
/*     */   extends HolderSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   private static final String NPC_MAX_MODIFIER = "NPC_Max";
/*     */   @Nonnull
/*     */   public static final String HEALTH_STAT_INDEX = "Health";
/*     */   @Nonnull
/*  65 */   private final ComponentType<EntityStore, NPCEntity> npcComponentType = NPCEntity.getComponentType(); @Nonnull
/*  66 */   private final ComponentType<EntityStore, EntityStatMap> entityStatMapComponentType = EntityStatMap.getComponentType(); @Nonnull
/*  67 */   private final Set<Dependency<EntityStore>> dependencies = (Set)Set.of(new SystemDependency(Order.AFTER, RoleBuilderSystem.class), new SystemDependency(Order.AFTER, EntityStatsSystems.Setup.class)); @Nonnull
/*  68 */   private final Query<EntityStore> query = (Query<EntityStore>)Archetype.of(new ComponentType[] { this.npcComponentType, this.entityStatMapComponentType });
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/*  74 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  80 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/*  85 */     NPCEntity npcComponent = (NPCEntity)holder.getComponent(this.npcComponentType);
/*  86 */     assert npcComponent != null;
/*     */     
/*  88 */     Role role = npcComponent.getRole();
/*  89 */     int initialMaxHealth = role.getInitialMaxHealth();
/*     */     
/*  91 */     EntityStatMap entityStatMapComponent = (EntityStatMap)holder.getComponent(this.entityStatMapComponentType);
/*  92 */     assert entityStatMapComponent != null;
/*     */     
/*  94 */     int statIndex = EntityStatType.getAssetMap().getIndex("Health");
/*     */ 
/*     */ 
/*     */     
/*  98 */     EntityStatType asset = (EntityStatType)EntityStatType.getAssetMap().getAsset(statIndex);
/*  99 */     StaticModifier modifier = new StaticModifier(Modifier.ModifierTarget.MAX, StaticModifier.CalculationType.ADDITIVE, initialMaxHealth - asset.getMax());
/* 100 */     entityStatMapComponent.putModifier(statIndex, "NPC_Max", (Modifier)modifier);
/* 101 */     entityStatMapComponent.maximizeStatValue(statIndex);
/*     */   }
/*     */   
/*     */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\BalancingInitialisationSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */