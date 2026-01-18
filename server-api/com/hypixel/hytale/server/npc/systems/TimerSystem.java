/*    */ package com.hypixel.hytale.server.npc.systems;
/*    */ 
/*    */ import com.hypixel.hytale.common.thread.ticking.Tickable;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.dependency.Dependency;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.components.Timers;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TimerSystem
/*    */   extends SteppableTickingSystem
/*    */ {
/*    */   @Nonnull
/*    */   private final ComponentType<EntityStore, Timers> timersComponentType;
/*    */   @Nonnull
/*    */   private final Set<Dependency<EntityStore>> dependencies;
/*    */   
/*    */   public TimerSystem(@Nonnull ComponentType<EntityStore, Timers> timersComponentType, @Nonnull Set<Dependency<EntityStore>> dependencies) {
/* 45 */     this.timersComponentType = timersComponentType;
/* 46 */     this.dependencies = dependencies;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Set<Dependency<EntityStore>> getDependencies() {
/* 52 */     return this.dependencies;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 57 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 63 */     return (Query)this.timersComponentType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void steppedTick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 68 */     Timers timersComponent = (Timers)archetypeChunk.getComponent(index, this.timersComponentType);
/* 69 */     assert timersComponent != null;
/*    */     
/* 71 */     for (Tickable timer : timersComponent.getTimers())
/* 72 */       timer.tick(dt); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\TimerSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */