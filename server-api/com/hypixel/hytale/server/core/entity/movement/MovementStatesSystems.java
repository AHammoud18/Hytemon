/*     */ package com.hypixel.hytale.server.core.entity.movement;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.SystemGroup;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.HolderSystem;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdate;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdateType;
/*     */ import com.hypixel.hytale.protocol.MovementStates;
/*     */ import com.hypixel.hytale.protocol.SavedMovementStates;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerWorldData;
/*     */ import com.hypixel.hytale.server.core.modules.entity.AllLegacyLivingEntityTypesQuery;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MovementStatesSystems
/*     */ {
/*     */   public static class AddSystem
/*     */     extends HolderSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, MovementStatesComponent> movementStatesComponentComponentType;
/*     */     
/*     */     public AddSystem(@Nonnull ComponentType<EntityStore, MovementStatesComponent> movementStatesComponentComponentType) {
/*  43 */       this.movementStatesComponentComponentType = movementStatesComponentComponentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/*  48 */       holder.ensureComponent(this.movementStatesComponentComponentType);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/*  58 */       return (Query<EntityStore>)AllLegacyLivingEntityTypesQuery.INSTANCE;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PlayerInitSystem
/*     */     extends RefSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, Player> playerComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, MovementStatesComponent> movementStatesComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PlayerInitSystem(@Nonnull ComponentType<EntityStore, Player> playerComponentType, @Nonnull ComponentType<EntityStore, MovementStatesComponent> movementStatesComponentType) {
/*  93 */       this.playerComponentType = playerComponentType;
/*  94 */       this.movementStatesComponentType = movementStatesComponentType;
/*  95 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)playerComponentType, (Query)movementStatesComponentType });
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 100 */       World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/*     */       
/* 102 */       Player playerComponent = (Player)store.getComponent(ref, this.playerComponentType);
/* 103 */       assert playerComponent != null;
/*     */       
/* 105 */       MovementStatesComponent movementStatesComponent = (MovementStatesComponent)store.getComponent(ref, this.movementStatesComponentType);
/* 106 */       assert movementStatesComponent != null;
/*     */       
/* 108 */       PlayerWorldData perWorldData = playerComponent.getPlayerConfigData().getPerWorldData(world.getName());
/* 109 */       SavedMovementStates movementStates = perWorldData.getLastMovementStates();
/* 110 */       playerComponent.applyMovementStates(ref, (movementStates != null) ? movementStates : new SavedMovementStates(), movementStatesComponent.getMovementStates(), (ComponentAccessor)store);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 121 */       return this.query;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class TickingSystem
/*     */     extends EntityTickingSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, MovementStatesComponent> movementStatesComponentComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public TickingSystem(@Nonnull ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType, @Nonnull ComponentType<EntityStore, MovementStatesComponent> movementStatesComponentComponentType) {
/* 157 */       this.visibleComponentType = visibleComponentType;
/* 158 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)movementStatesComponentComponentType, (Query)visibleComponentType });
/* 159 */       this.movementStatesComponentComponentType = movementStatesComponentComponentType;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public SystemGroup<EntityStore> getGroup() {
/* 165 */       return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 171 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 176 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 181 */       EntityTrackerSystems.Visible visibleComponent = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.visibleComponentType);
/* 182 */       assert visibleComponent != null;
/*     */       
/* 184 */       MovementStatesComponent movementStatesComponent = (MovementStatesComponent)archetypeChunk.getComponent(index, this.movementStatesComponentComponentType);
/* 185 */       assert movementStatesComponent != null;
/*     */ 
/*     */       
/* 188 */       MovementStates newMovementStates = movementStatesComponent.getMovementStates();
/* 189 */       MovementStates sentMovementStates = movementStatesComponent.getSentMovementStates();
/* 190 */       if (!newMovementStates.equals(sentMovementStates)) {
/* 191 */         copyMovementStatesFrom(newMovementStates, sentMovementStates);
/* 192 */         queueUpdatesFor(archetypeChunk.getReferenceTo(index), visibleComponent.visibleTo, movementStatesComponent);
/* 193 */       } else if (!visibleComponent.newlyVisibleTo.isEmpty()) {
/* 194 */         queueUpdatesFor(archetypeChunk.getReferenceTo(index), visibleComponent.newlyVisibleTo, movementStatesComponent);
/*     */       } 
/*     */     }
/*     */     
/*     */     private static void queueUpdatesFor(@Nonnull Ref<EntityStore> ref, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo, @Nonnull MovementStatesComponent movementStatesComponent) {
/* 199 */       ComponentUpdate update = new ComponentUpdate();
/* 200 */       update.type = ComponentUpdateType.MovementStates;
/* 201 */       update.movementStates = movementStatesComponent.getMovementStates();
/*     */       
/* 203 */       for (Map.Entry<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> entry : visibleTo.entrySet()) {
/*     */         
/* 205 */         if (ref.equals(entry.getKey()))
/*     */           continue; 
/* 207 */         ((EntityTrackerSystems.EntityViewer)entry.getValue()).queueUpdate(ref, update);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static void copyMovementStatesFrom(@Nonnull MovementStates from, @Nonnull MovementStates to) {
/* 218 */       to.idle = from.idle;
/* 219 */       to.horizontalIdle = from.horizontalIdle;
/* 220 */       to.jumping = from.jumping;
/* 221 */       to.flying = from.flying;
/* 222 */       to.walking = from.walking;
/* 223 */       to.running = from.running;
/* 224 */       to.sprinting = from.sprinting;
/* 225 */       to.crouching = from.crouching;
/* 226 */       to.forcedCrouching = from.forcedCrouching;
/* 227 */       to.falling = from.falling;
/* 228 */       to.climbing = from.climbing;
/* 229 */       to.inFluid = from.inFluid;
/* 230 */       to.swimming = from.swimming;
/* 231 */       to.swimJumping = from.swimJumping;
/* 232 */       to.onGround = from.onGround;
/* 233 */       to.mantling = from.mantling;
/* 234 */       to.sliding = from.sliding;
/* 235 */       to.mounting = from.mounting;
/* 236 */       to.rolling = from.rolling;
/* 237 */       to.sitting = from.sitting;
/* 238 */       to.gliding = from.gliding;
/* 239 */       to.sleeping = from.sleeping;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\movement\MovementStatesSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */