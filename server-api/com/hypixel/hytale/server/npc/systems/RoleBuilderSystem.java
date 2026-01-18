/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ import com.hypixel.hytale.common.thread.ticking.Tickable;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.logger.sentry.SkipSentryException;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ActiveAnimationComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.DisplayNameComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.Invulnerable;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatsSystems;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.Interactions;
/*     */ import com.hypixel.hytale.server.core.modules.physics.systems.PhysicsValuesAddSystem;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.Builder;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderInfo;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.EventSlotMapper;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.event.block.BlockEventType;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.event.entity.EntityEventType;
/*     */ import com.hypixel.hytale.server.npc.components.FailedSpawnComponent;
/*     */ import com.hypixel.hytale.server.npc.components.Timers;
/*     */ import com.hypixel.hytale.server.npc.components.messaging.BeaconSupport;
/*     */ import com.hypixel.hytale.server.npc.components.messaging.NPCBlockEventSupport;
/*     */ import com.hypixel.hytale.server.npc.components.messaging.NPCEntityEventSupport;
/*     */ import com.hypixel.hytale.server.npc.components.messaging.PlayerBlockEventSupport;
/*     */ import com.hypixel.hytale.server.npc.components.messaging.PlayerEntityEventSupport;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.stateevaluator.StateEvaluator;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.role.SpawnEffect;
/*     */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*     */ import com.hypixel.hytale.server.npc.valuestore.ValueStore;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class RoleBuilderSystem extends HolderSystem<EntityStore> {
/*     */   @Nonnull
/*  57 */   private final ComponentType<EntityStore, TransformComponent> transformComponentType = TransformComponent.getComponentType();
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */   
/*     */   @Nonnull
/*  63 */   private final ComponentType<EntityStore, ModelComponent> modelComponentType = ModelComponent.getComponentType();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  69 */   private final ComponentType<EntityStore, PersistentModel> persistentModelComponentType = PersistentModel.getComponentType();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final Set<Dependency<EntityStore>> dependencies;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RoleBuilderSystem() {
/*  87 */     this.npcComponentType = NPCEntity.getComponentType();
/*  88 */     this.dependencies = Set.of(new SystemDependency(Order.AFTER, EntityStatsSystems.Setup.class), new SystemDependency(Order.AFTER, PhysicsValuesAddSystem.class));
/*     */     
/*  90 */     this.query = (Query<EntityStore>)Archetype.of(new ComponentType[] { this.npcComponentType, this.transformComponentType });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/*  96 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 103 */     return this.query;
/*     */   }
/*     */   
/*     */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/*     */     Role role;
/* 108 */     NPCEntity npcComponent = (NPCEntity)holder.getComponent(this.npcComponentType);
/* 109 */     assert npcComponent != null;
/*     */     
/* 111 */     if (npcComponent.getRole() != null)
/*     */       return; 
/* 113 */     NPCPlugin npcPlugin = NPCPlugin.get();
/* 114 */     int roleIndex = npcComponent.getRoleIndex();
/* 115 */     if (roleIndex == Integer.MIN_VALUE) {
/*     */ 
/*     */       
/* 118 */       String roleName = npcComponent.getRoleName();
/* 119 */       roleIndex = npcPlugin.getIndex(roleName);
/*     */       
/* 121 */       if (roleIndex < 0) {
/* 122 */         fail(holder);
/* 123 */         npcPlugin.getLogger().at(Level.SEVERE).log("Reloading nonexistent role %s!", roleName);
/*     */         
/*     */         return;
/*     */       } 
/* 127 */       if (npcPlugin.tryGetCachedValidRole(roleIndex) == null) {
/* 128 */         fail(holder);
/* 129 */         npcPlugin.getLogger().at(Level.SEVERE).log("Reloading invalid role %s!", roleName);
/*     */         
/*     */         return;
/*     */       } 
/* 133 */       npcComponent.setRoleIndex(roleIndex);
/*     */     } 
/*     */     
/* 136 */     BuilderInfo builderInfo = npcPlugin.prepareRoleBuilderInfo(roleIndex);
/* 137 */     Builder<Role> roleBuilder = builderInfo.getBuilder();
/* 138 */     if (!roleBuilder.isSpawnable()) {
/* 139 */       fail(holder);
/* 140 */       npcPlugin.getLogger().at(Level.SEVERE).log("Attempting to spawn un-spawnable (abstract) role: %s", npcComponent.getRoleName());
/*     */       
/*     */       return;
/*     */     } 
/* 144 */     BuilderSupport builderSupport = new BuilderSupport(npcPlugin.getBuilderManager(), npcComponent, holder, new ExecutionContext(), roleBuilder, null);
/*     */ 
/*     */     
/*     */     try {
/* 148 */       role = NPCPlugin.buildRole(roleBuilder, builderInfo, builderSupport, roleIndex);
/* 149 */     } catch (SkipSentryException e) {
/* 150 */       fail(holder);
/* 151 */       npcPlugin.getLogger().at(Level.SEVERE).log("Error: %s for NPC %s", e.getMessage(), npcComponent.getRole());
/*     */       
/*     */       return;
/*     */     } 
/* 155 */     npcComponent.setRole(role);
/*     */ 
/*     */     
/* 158 */     if (role.isInvulnerable()) {
/* 159 */       holder.ensureComponent(Invulnerable.getComponentType());
/*     */     }
/*     */ 
/*     */     
/* 163 */     Message roleNameMessage = Message.translation(role.getNameTranslationKey());
/* 164 */     holder.putComponent(DisplayNameComponent.getComponentType(), (Component)new DisplayNameComponent(roleNameMessage));
/*     */     
/* 166 */     Interactions interactionsComponent = (Interactions)holder.ensureAndGetComponent(Interactions.getComponentType());
/* 167 */     interactionsComponent.setInteractionId(InteractionType.Use, "*UseNPC");
/*     */     
/* 169 */     if (role.getDeathInteraction() != null) {
/* 170 */       interactionsComponent.setInteractionId(InteractionType.Death, role.getDeathInteraction());
/*     */     }
/*     */ 
/*     */     
/* 174 */     Object2IntMap<String> beaconSlotMappings = builderSupport.getBeaconSlotMappings();
/* 175 */     if (beaconSlotMappings != null) {
/* 176 */       BeaconSupport beaconSupport = new BeaconSupport();
/* 177 */       beaconSupport.initialise(beaconSlotMappings);
/* 178 */       holder.putComponent(BeaconSupport.getComponentType(), (Component)beaconSupport);
/*     */     } 
/*     */     
/* 181 */     if (builderSupport.hasBlockEventSupport()) {
/* 182 */       EventSlotMapper<BlockEventType> playerEventSlotMapper = builderSupport.getPlayerBlockEventSlotMapper();
/* 183 */       if (playerEventSlotMapper != null) {
/* 184 */         PlayerBlockEventSupport playerBlockEventSupport = new PlayerBlockEventSupport();
/* 185 */         playerBlockEventSupport.initialise(playerEventSlotMapper.getEventSlotMappings(), playerEventSlotMapper.getEventSlotRanges(), playerEventSlotMapper
/* 186 */             .getEventSlotCount());
/* 187 */         holder.putComponent(PlayerBlockEventSupport.getComponentType(), (Component)playerBlockEventSupport);
/*     */       } 
/*     */       
/* 190 */       EventSlotMapper<BlockEventType> npcEventSlotMapper = builderSupport.getNPCBlockEventSlotMapper();
/* 191 */       if (npcEventSlotMapper != null) {
/* 192 */         NPCBlockEventSupport npcBlockEventSupport = new NPCBlockEventSupport();
/* 193 */         npcBlockEventSupport.initialise(npcEventSlotMapper.getEventSlotMappings(), npcEventSlotMapper.getEventSlotRanges(), npcEventSlotMapper
/* 194 */             .getEventSlotCount());
/* 195 */         holder.putComponent(NPCBlockEventSupport.getComponentType(), (Component)npcBlockEventSupport);
/*     */       } 
/*     */       
/* 198 */       for (int i = 0; i < BlockEventType.VALUES.length; i++) {
/* 199 */         BlockEventType type = BlockEventType.VALUES[i];
/* 200 */         IntSet sets = builderSupport.getBlockChangeSets(type);
/* 201 */         if (sets != null) {
/* 202 */           npcComponent.addBlackboardBlockChangeSets(type, sets);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 207 */     if (builderSupport.hasEntityEventSupport()) {
/* 208 */       EventSlotMapper<EntityEventType> playerEventSlotMapper = builderSupport.getPlayerEntityEventSlotMapper();
/* 209 */       if (playerEventSlotMapper != null) {
/* 210 */         PlayerEntityEventSupport playerEntityEventSupport = new PlayerEntityEventSupport();
/* 211 */         playerEntityEventSupport.initialise(playerEventSlotMapper.getEventSlotMappings(), playerEventSlotMapper.getEventSlotRanges(), playerEventSlotMapper
/* 212 */             .getEventSlotCount());
/* 213 */         holder.putComponent(PlayerEntityEventSupport.getComponentType(), (Component)playerEntityEventSupport);
/*     */       } 
/*     */       
/* 216 */       EventSlotMapper<EntityEventType> npcEventSlotMapper = builderSupport.getNPCEntityEventSlotMapper();
/* 217 */       if (npcEventSlotMapper != null) {
/* 218 */         NPCEntityEventSupport npcEntityEventSupport = new NPCEntityEventSupport();
/* 219 */         npcEntityEventSupport.initialise(npcEventSlotMapper.getEventSlotMappings(), npcEventSlotMapper.getEventSlotRanges(), npcEventSlotMapper
/* 220 */             .getEventSlotCount());
/* 221 */         holder.putComponent(NPCEntityEventSupport.getComponentType(), (Component)npcEntityEventSupport);
/*     */       } 
/*     */       
/* 224 */       for (EntityEventType type : EntityEventType.VALUES) {
/* 225 */         IntSet sets = builderSupport.getEventNPCGroups(type);
/* 226 */         if (sets != null) {
/* 227 */           npcComponent.addBlackboardEntityEventSets(type, sets);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 232 */     Tickable[] timers = builderSupport.allocateTimers();
/* 233 */     if (timers != null) {
/* 234 */       holder.putComponent(Timers.getComponentType(), (Component)new Timers(timers));
/*     */     }
/*     */     
/* 237 */     StateEvaluator stateEvaluator = builderSupport.getStateEvaluator();
/* 238 */     if (stateEvaluator != null) {
/* 239 */       holder.putComponent(StateEvaluator.getComponentType(), (Component)stateEvaluator);
/*     */     }
/*     */     
/* 242 */     ValueStore.Builder valueStoreBuilder = builderSupport.getValueStoreBuilder();
/* 243 */     if (valueStoreBuilder != null) {
/* 244 */       holder.putComponent(ValueStore.getComponentType(), (Component)valueStoreBuilder.build());
/*     */     }
/*     */ 
/*     */     
/* 248 */     holder.ensureComponent(EffectControllerComponent.getComponentType());
/*     */     
/* 250 */     holder.ensureComponent(ActiveAnimationComponent.getComponentType());
/*     */     
/* 252 */     boolean fromPrefab = holder.getArchetype().contains(FromPrefab.getComponentType());
/* 253 */     boolean spawnedOrPrefab = (reason.equals(AddReason.SPAWN) || fromPrefab);
/*     */     
/* 255 */     if (spawnedOrPrefab) {
/*     */ 
/*     */       
/* 258 */       ModelComponent modelComponent = (ModelComponent)holder.getComponent(this.modelComponentType);
/* 259 */       if (modelComponent == null) {
/* 260 */         String appearance = role.getAppearanceName();
/* 261 */         if (appearance == null || appearance.isEmpty()) {
/* 262 */           fail(holder);
/* 263 */           npcPlugin.getLogger().at(Level.SEVERE).log("Appearance can't be initially empty for role %s", npcComponent.getRoleName());
/*     */           
/*     */           return;
/*     */         } 
/* 267 */         ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(appearance);
/* 268 */         if (modelAsset == null) {
/* 269 */           fail(holder);
/* 270 */           npcPlugin.getLogger().at(Level.SEVERE).log("Model asset not found: %s for role %s", appearance, npcComponent.getRoleName());
/*     */           
/*     */           return;
/*     */         } 
/* 274 */         float scale = modelAsset.generateRandomScale();
/* 275 */         npcComponent.setInitialModelScale(scale);
/* 276 */         Model scaledModel = Model.createScaledModel(modelAsset, scale);
/* 277 */         holder.putComponent(this.persistentModelComponentType, (Component)new PersistentModel(scaledModel.toReference()));
/* 278 */         holder.putComponent(this.modelComponentType, (Component)new ModelComponent(scaledModel));
/*     */       } 
/*     */       
/* 281 */       role.spawned(holder, npcComponent);
/*     */       
/* 283 */       if (roleBuilder instanceof SpawnEffect) { SpawnEffect spawnEffect = (SpawnEffect)roleBuilder;
/* 284 */         TransformComponent transformComponent = (TransformComponent)holder.getComponent(this.transformComponentType);
/* 285 */         assert transformComponent != null;
/* 286 */         spawnEffect.spawnEffect(transformComponent.getPosition(), transformComponent.getRotation(), (ComponentAccessor)store); }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*     */ 
/*     */   
/*     */   private void fail(@Nonnull Holder<EntityStore> holder) {
/* 296 */     Archetype<EntityStore> archetype = holder.getArchetype();
/* 297 */     if (archetype == null)
/*     */       return; 
/* 299 */     for (int i = archetype.getMinIndex(); i < archetype.length(); i++) {
/* 300 */       ComponentType<EntityStore, ? extends Component<EntityStore>> componentType = archetype.get(i);
/* 301 */       if (componentType != null)
/*     */       {
/* 303 */         holder.removeComponent(componentType);
/*     */       }
/*     */     } 
/* 306 */     holder.ensureComponent(FailedSpawnComponent.getComponentType());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\RoleBuilderSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */