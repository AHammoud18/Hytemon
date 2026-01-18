/*    */ package com.hypixel.hytale.builtin.adventure.npcobjectives.task;
/*    */ import com.hypixel.hytale.builtin.adventure.npcobjectives.assets.KillObjectiveTaskAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.npcobjectives.assets.KillSpawnMarkerObjectiveTaskAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.npcobjectives.transaction.KillTaskTransaction;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.ObjectiveTaskAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.transaction.TransactionRecord;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.spawnmarkers.SpawnMarkerEntity;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class KillSpawnMarkerObjectiveTask extends KillObjectiveTask {
/* 24 */   public static final BuilderCodec<KillSpawnMarkerObjectiveTask> CODEC = BuilderCodec.builder(KillSpawnMarkerObjectiveTask.class, KillSpawnMarkerObjectiveTask::new, KillObjectiveTask.CODEC)
/* 25 */     .build();
/*    */   
/* 27 */   private static final ComponentType<EntityStore, SpawnMarkerEntity> SPAWN_MARKER_COMPONENT_TYPE = SpawnMarkerEntity.getComponentType();
/* 28 */   private static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*    */   
/*    */   public KillSpawnMarkerObjectiveTask(@Nonnull KillSpawnMarkerObjectiveTaskAsset asset, int taskSetIndex, int taskIndex) {
/* 31 */     super((KillObjectiveTaskAsset)asset, taskSetIndex, taskIndex);
/*    */   }
/*    */ 
/*    */   
/*    */   protected KillSpawnMarkerObjectiveTask() {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public KillSpawnMarkerObjectiveTaskAsset getAsset() {
/* 40 */     return (KillSpawnMarkerObjectiveTaskAsset)super.getAsset();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected TransactionRecord[] setup0(@Nonnull Objective objective, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 46 */     Vector3d objectivePosition = objective.getPosition((ComponentAccessor)store);
/* 47 */     if (objectivePosition != null) {
/* 48 */       KillSpawnMarkerObjectiveTaskAsset asset = getAsset();
/*    */       
/* 50 */       ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 51 */       SpatialResource<Ref<EntityStore>, EntityStore> spatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(SpawningPlugin.get().getSpawnMarkerSpatialResource());
/* 52 */       spatialResource.getSpatialStructure().collect(objectivePosition, asset.getRadius(), (List)results);
/*    */       
/* 54 */       String[] spawnMarkerIds = asset.getSpawnMarkerIds();
/* 55 */       HytaleLogger logger = ObjectivePlugin.get().getLogger();
/*    */       
/* 57 */       for (ObjectListIterator<Ref<EntityStore>> objectListIterator = results.iterator(); objectListIterator.hasNext(); ) { Ref<EntityStore> entityReference = objectListIterator.next();
/* 58 */         SpawnMarkerEntity entitySpawnMarkerComponent = (SpawnMarkerEntity)store.getComponent(entityReference, SPAWN_MARKER_COMPONENT_TYPE);
/* 59 */         assert entitySpawnMarkerComponent != null;
/*    */         
/* 61 */         String spawnMarkerId = entitySpawnMarkerComponent.getSpawnMarkerId();
/* 62 */         if (!ArrayUtil.contains((Object[])spawnMarkerIds, spawnMarkerId))
/*    */           continue; 
/* 64 */         world.execute(() -> entitySpawnMarkerComponent.trigger(entityReference, store));
/* 65 */         logger.at(Level.INFO).log("Triggered SpawnMarker '" + spawnMarkerId + "' at position: " + String.valueOf(((TransformComponent)store.getComponent(entityReference, TRANSFORM_COMPONENT_TYPE)).getPosition())); }
/*    */     
/*    */     } 
/*    */     
/* 69 */     KillTaskTransaction transaction = new KillTaskTransaction(this, objective, (ComponentAccessor)store);
/* 70 */     ((KillTrackerResource)store.getResource(KillTrackerResource.getResourceType())).watch(transaction);
/* 71 */     return new TransactionRecord[] { (TransactionRecord)transaction };
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 77 */     return "KillSpawnMarkerObjectiveTask{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcobjectives\task\KillSpawnMarkerObjectiveTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */