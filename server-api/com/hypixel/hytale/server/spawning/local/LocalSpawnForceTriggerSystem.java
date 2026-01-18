/*    */ package com.hypixel.hytale.server.spawning.local;
/*    */ 
/*    */ import com.hypixel.hytale.component.Archetype;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.ArchetypeTickingSystem;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.math.random.RandomExtra;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class LocalSpawnForceTriggerSystem
/*    */   extends EntityTickingSystem<EntityStore> {
/* 22 */   private static final double[] RERUN_TIME_RANGE = new double[] { 0.0D, 5.0D };
/*    */   
/*    */   private final Archetype<EntityStore> archetype;
/*    */   
/*    */   private final ComponentType<EntityStore, PlayerRef> playerRefComponentType;
/*    */   private final ComponentType<EntityStore, LocalSpawnController> spawnControllerComponentType;
/*    */   private final ResourceType<EntityStore, LocalSpawnState> localSpawnStateResourceType;
/*    */   
/*    */   public LocalSpawnForceTriggerSystem(ComponentType<EntityStore, LocalSpawnController> spawnControllerComponentType, ResourceType<EntityStore, LocalSpawnState> localSpawnStateResourceType) {
/* 31 */     this.playerRefComponentType = PlayerRef.getComponentType();
/* 32 */     this.spawnControllerComponentType = spawnControllerComponentType;
/* 33 */     this.localSpawnStateResourceType = localSpawnStateResourceType;
/* 34 */     this.archetype = Archetype.of(new ComponentType[] { this.playerRefComponentType, spawnControllerComponentType });
/*    */   }
/*    */ 
/*    */   
/*    */   public Query<EntityStore> getQuery() {
/* 39 */     return (Query<EntityStore>)this.archetype;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 45 */     LocalSpawnState state = (LocalSpawnState)store.getResource(this.localSpawnStateResourceType);
/* 46 */     if (state.pollForceTriggerControllers()) {
/* 47 */       store.tick((ArchetypeTickingSystem)this, dt, systemIndex);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 54 */     PlayerRef playerRefComponent = (PlayerRef)archetypeChunk.getComponent(index, this.playerRefComponentType);
/* 55 */     assert playerRefComponent != null;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 60 */     LocalSpawnController controller = (LocalSpawnController)archetypeChunk.getComponent(index, this.spawnControllerComponentType);
/* 61 */     double seconds = RandomExtra.randomRange(RERUN_TIME_RANGE);
/* 62 */     controller.setTimeToNextRunSeconds(seconds);
/* 63 */     HytaleLogger.Api context = SpawningPlugin.get().getLogger().at(Level.FINE);
/* 64 */     if (context.isEnabled())
/* 65 */       context.log("Force running local spawn controller for %s in %f seconds", playerRefComponent.getUsername(), seconds); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\local\LocalSpawnForceTriggerSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */