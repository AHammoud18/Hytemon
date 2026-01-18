/*     */ package com.hypixel.hytale.builtin.adventure.stash;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockStateModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.state.ItemContainerState;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
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
/*     */ class StashSystem
/*     */   extends RefSystem<ChunkStore>
/*     */ {
/*     */   private final ComponentType<ChunkStore, ItemContainerState> componentType;
/*     */   @Nonnull
/*     */   private final Set<Dependency<ChunkStore>> dependencies;
/*     */   
/*     */   public StashSystem(ComponentType<ChunkStore, ItemContainerState> componentType) {
/*  97 */     this.componentType = componentType;
/*  98 */     this.dependencies = Set.of(new SystemDependency(Order.AFTER, BlockStateModule.LegacyBlockStateRefSystem.class));
/*     */   }
/*     */ 
/*     */   
/*     */   public Query<ChunkStore> getQuery() {
/* 103 */     return (Query)this.componentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 108 */     World world = ((ChunkStore)store.getExternalData()).getWorld();
/* 109 */     if (world.getWorldConfig().getGameMode() == GameMode.Creative)
/*     */       return; 
/* 111 */     StashGameplayConfig stashGameplayConfig = StashGameplayConfig.getOrDefault(world.getGameplayConfig());
/*     */     
/* 113 */     boolean clearContainerDropList = stashGameplayConfig.isClearContainerDropList();
/* 114 */     StashPlugin.stash((ItemContainerState)store.getComponent(ref, this.componentType), clearContainerDropList);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {}
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<ChunkStore>> getDependencies() {
/* 124 */     return this.dependencies;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\stash\StashPlugin$StashSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */