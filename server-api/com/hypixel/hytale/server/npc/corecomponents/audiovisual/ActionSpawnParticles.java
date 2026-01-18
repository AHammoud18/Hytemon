/*    */ package com.hypixel.hytale.server.npc.corecomponents.audiovisual;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.audiovisual.builders.BuilderActionSpawnParticles;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionSpawnParticles extends ActionBase {
/*    */   protected final String particleSystem;
/*    */   
/*    */   public ActionSpawnParticles(@Nonnull BuilderActionSpawnParticles builder, @Nonnull BuilderSupport support) {
/* 25 */     super((BuilderActionBase)builder);
/* 26 */     this.particleSystem = builder.getParticleSystem(support);
/* 27 */     this.offset = builder.getOffset();
/* 28 */     this.range = builder.getRange();
/*    */   }
/*    */   protected final double range; protected final Vector3d offset;
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 33 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 35 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 36 */     assert transformComponent != null;
/*    */ 
/*    */ 
/*    */     
/* 40 */     Vector3d position = (new Vector3d(this.offset)).rotateY(transformComponent.getRotation().getYaw()).add(transformComponent.getPosition());
/*    */     
/* 42 */     SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(EntityModule.get().getPlayerSpatialResourceType());
/* 43 */     ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 44 */     playerSpatialResource.getSpatialStructure().collect(position, this.range, (List)results);
/*    */     
/* 46 */     ParticleUtil.spawnParticleEffect(this.particleSystem, position, (List)results, (ComponentAccessor)store);
/* 47 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\audiovisual\ActionSpawnParticles.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */