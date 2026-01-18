/*     */ package com.hypixel.hytale.builtin.npccombatactionevaluator.memory;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.systems.RoleSystems;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TargetMemorySystems
/*     */ {
/*     */   @Nonnull
/*  35 */   public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Ticking
/*     */     extends EntityTickingSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     private static final String HOSTILE = "hostile";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private static final String FRIENDLY = "friendly";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, TargetMemory> targetMemoryComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final Set<Dependency<EntityStore>> dependencies;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Ticking(@Nonnull ComponentType<EntityStore, TargetMemory> targetMemoryComponentType) {
/*  72 */       this.targetMemoryComponentType = targetMemoryComponentType;
/*  73 */       this.dependencies = Set.of(new SystemDependency(Order.BEFORE, RoleSystems.BehaviourTickSystem.class));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  78 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/*  84 */       return (Query)this.targetMemoryComponentType;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/*  90 */       return this.dependencies;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  95 */       TargetMemory targetMemoryComponent = (TargetMemory)archetypeChunk.getComponent(index, this.targetMemoryComponentType);
/*  96 */       assert targetMemoryComponent != null;
/*     */       
/*  98 */       Int2FloatOpenHashMap hostileMap = targetMemoryComponent.getKnownHostiles();
/*  99 */       List<Ref<EntityStore>> hostileList = targetMemoryComponent.getKnownHostilesList();
/* 100 */       iterateMemory(dt, index, archetypeChunk, commandBuffer, hostileList, hostileMap, "hostile");
/*     */       
/* 102 */       Int2FloatOpenHashMap friendlyMap = targetMemoryComponent.getKnownFriendlies();
/* 103 */       List<Ref<EntityStore>> friendlyList = targetMemoryComponent.getKnownFriendliesList();
/* 104 */       iterateMemory(dt, index, archetypeChunk, commandBuffer, friendlyList, friendlyMap, "friendly");
/*     */       
/* 106 */       Ref<EntityStore> closestHostileRef = targetMemoryComponent.getClosestHostile();
/* 107 */       if (closestHostileRef != null && !isValidTarget(closestHostileRef, commandBuffer)) {
/* 108 */         targetMemoryComponent.setClosestHostile(null);
/*     */       }
/*     */     }
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
/*     */     private static void iterateMemory(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull List<Ref<EntityStore>> targetsList, @Nonnull Int2FloatOpenHashMap targetsMap, @Nonnull String type) {
/* 131 */       for (int i = targetsList.size() - 1; i >= 0; i--) {
/* 132 */         Ref<EntityStore> ref = targetsList.get(i);
/* 133 */         if (!isValidTarget(ref, commandBuffer)) {
/* 134 */           removeEntry(index, archetypeChunk, i, ref, targetsList, targetsMap, type);
/*     */         }
/*     */         else {
/*     */           
/* 138 */           float timeRemaining = targetsMap.mergeFloat(ref.getIndex(), -dt, Float::sum);
/* 139 */           if (timeRemaining <= 0.0F) {
/* 140 */             removeEntry(index, archetypeChunk, i, ref, targetsList, targetsMap, type);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
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
/*     */     private static boolean isValidTarget(@Nonnull Ref<EntityStore> targetRef, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 157 */       if (!targetRef.isValid()) return false;
/*     */ 
/*     */       
/* 160 */       if (commandBuffer.getArchetype(targetRef).contains(DeathComponent.getComponentType())) return false;
/*     */ 
/*     */ 
/*     */       
/* 164 */       Player targetPlayerComponent = (Player)commandBuffer.getComponent(targetRef, Player.getComponentType());
/* 165 */       return (targetPlayerComponent == null || targetPlayerComponent.getGameMode() == GameMode.Adventure);
/*     */     }
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
/*     */     private static void removeEntry(int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, int targetIndex, @Nonnull Ref<EntityStore> targetRef, @Nonnull List<Ref<EntityStore>> targetsList, @Nonnull Int2FloatOpenHashMap targetsMap, @Nonnull String type) {
/* 187 */       targetsMap.remove(targetRef.getIndex());
/* 188 */       targetsList.remove(targetIndex);
/*     */       
/* 190 */       HytaleLogger.Api context = TargetMemorySystems.LOGGER.at(Level.FINEST);
/* 191 */       if (context.isEnabled())
/* 192 */         context.log("%s: Removed lost %s target %s", archetypeChunk.getReferenceTo(index), type, targetRef); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\npccombatactionevaluator\memory\TargetMemorySystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */