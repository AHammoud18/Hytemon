/*     */ package com.hypixel.hytale.server.core.entity.movement;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.protocol.SavedMovementStates;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerWorldData;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*     */ public class PlayerInitSystem
/*     */   extends RefSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, Player> playerComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, MovementStatesComponent> movementStatesComponentType;
/*     */   
/*     */   public PlayerInitSystem(@Nonnull ComponentType<EntityStore, Player> playerComponentType, @Nonnull ComponentType<EntityStore, MovementStatesComponent> movementStatesComponentType) {
/*  93 */     this.playerComponentType = playerComponentType;
/*  94 */     this.movementStatesComponentType = movementStatesComponentType;
/*  95 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)playerComponentType, (Query)movementStatesComponentType });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 100 */     World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/*     */     
/* 102 */     Player playerComponent = (Player)store.getComponent(ref, this.playerComponentType);
/* 103 */     assert playerComponent != null;
/*     */     
/* 105 */     MovementStatesComponent movementStatesComponent = (MovementStatesComponent)store.getComponent(ref, this.movementStatesComponentType);
/* 106 */     assert movementStatesComponent != null;
/*     */     
/* 108 */     PlayerWorldData perWorldData = playerComponent.getPlayerConfigData().getPerWorldData(world.getName());
/* 109 */     SavedMovementStates movementStates = perWorldData.getLastMovementStates();
/* 110 */     playerComponent.applyMovementStates(ref, (movementStates != null) ? movementStates : new SavedMovementStates(), movementStatesComponent.getMovementStates(), (ComponentAccessor)store);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 121 */     return this.query;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\movement\MovementStatesSystems$PlayerInitSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */