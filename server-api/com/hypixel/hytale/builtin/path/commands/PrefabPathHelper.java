/*    */ package com.hypixel.hytale.builtin.path.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.path.PathPlugin;
/*    */ import com.hypixel.hytale.builtin.path.entities.PatrolPathMarkerEntity;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*    */ import com.hypixel.hytale.server.core.entity.Entity;
/*    */ import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.DisplayNameComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.UUID;
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
/*    */ public final class PrefabPathHelper
/*    */ {
/*    */   public static void addMarker(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> playerRef, @Nonnull UUID pathId, @Nonnull String pathName, double pauseTime, float obsvAngleDegrees, short targetIndex, int worldgenId) {
/* 46 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */     
/* 48 */     PatrolPathMarkerEntity waypoint = new PatrolPathMarkerEntity(world);
/* 49 */     waypoint.initialise(pathId, pathName, targetIndex, pauseTime, obsvAngleDegrees * 0.017453292F, worldgenId, (ComponentAccessor)store);
/*    */     
/* 51 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(playerRef, TransformComponent.getComponentType());
/* 52 */     assert transformComponent != null;
/*    */     
/* 54 */     Vector3d playerPosition = transformComponent.getPosition().clone();
/* 55 */     Vector3f playerBodyRotation = transformComponent.getRotation().clone();
/*    */     
/* 57 */     PatrolPathMarkerEntity waypointEntity = (PatrolPathMarkerEntity)world.spawnEntity((Entity)waypoint, playerPosition, playerBodyRotation);
/* 58 */     if (waypointEntity == null)
/*    */       return; 
/* 60 */     Ref<EntityStore> waypointRef = waypointEntity.getReference();
/* 61 */     if (waypointRef == null || !waypointRef.isValid())
/*    */       return; 
/* 63 */     TransformComponent waypointTransformComponent = (TransformComponent)store.getComponent(waypointRef, TransformComponent.getComponentType());
/* 64 */     Vector3f waypointRotation = waypointTransformComponent.getRotation();
/*    */     
/* 66 */     waypointRotation.assign(playerBodyRotation);
/*    */     
/* 68 */     Model model = PathPlugin.get().getPathMarkerModel();
/* 69 */     store.putComponent(waypointRef, ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/*    */     
/* 71 */     String displayName = PatrolPathMarkerEntity.generateDisplayName(worldgenId, waypointEntity);
/* 72 */     Message displayNameMessage = Message.raw(displayName);
/*    */     
/* 74 */     store.putComponent(waypointRef, DisplayNameComponent.getComponentType(), (Component)new DisplayNameComponent(displayNameMessage));
/* 75 */     store.putComponent(waypointRef, Nameplate.getComponentType(), (Component)new Nameplate(displayName));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\commands\PrefabPathHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */