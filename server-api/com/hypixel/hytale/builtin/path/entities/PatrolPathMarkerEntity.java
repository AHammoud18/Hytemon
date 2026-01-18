/*     */ package com.hypixel.hytale.builtin.path.entities;
/*     */ 
/*     */ import com.hypixel.fastutil.ints.Int2ObjectConcurrentHashMap;
/*     */ import com.hypixel.hytale.builtin.path.WorldPathData;
/*     */ import com.hypixel.hytale.builtin.path.path.IPrefabPath;
/*     */ import com.hypixel.hytale.builtin.path.waypoint.IPrefabPathWaypoint;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.path.IPath;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class PatrolPathMarkerEntity
/*     */   extends Entity
/*     */   implements IPrefabPathWaypoint
/*     */ {
/*     */   public static final BuilderCodec<PatrolPathMarkerEntity> CODEC;
/*     */   @Nullable
/*     */   private UUID pathId;
/*     */   private String pathName;
/*     */   private int order;
/*     */   private double pauseTime;
/*     */   private float observationAngle;
/*     */   private short tempPathLength;
/*     */   private IPrefabPath parentPath;
/*     */   
/*     */   static {
/*  70 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PatrolPathMarkerEntity.class, PatrolPathMarkerEntity::new, Entity.CODEC).append(new KeyedCodec("PathId", (Codec)Codec.UUID_BINARY), (patrolPathMarkerEntity, uuid) -> patrolPathMarkerEntity.pathId = uuid, patrolPathMarkerEntity -> patrolPathMarkerEntity.pathId).setVersionRange(5, 5).add()).append(new KeyedCodec("PathName", (Codec)Codec.STRING), (patrolPathMarkerEntity, s) -> patrolPathMarkerEntity.pathName = s, patrolPathMarkerEntity -> patrolPathMarkerEntity.pathName).setVersionRange(5, 5).add()).append(new KeyedCodec("Path", (Codec)Codec.STRING), (patrolPathMarkerEntity, s) -> patrolPathMarkerEntity.pathName = s, patrolPathMarkerEntity -> patrolPathMarkerEntity.pathName).setVersionRange(0, 4).add()).addField(new KeyedCodec("PathLength", (Codec)Codec.INTEGER), (patrolPathMarkerEntity, i) -> patrolPathMarkerEntity.tempPathLength = i.shortValue(), patrolPathMarkerEntity -> Integer.valueOf((patrolPathMarkerEntity.parentPath != null) ? patrolPathMarkerEntity.parentPath.length() : patrolPathMarkerEntity.tempPathLength))).addField(new KeyedCodec("Order", (Codec)Codec.INTEGER), (patrolPathMarkerEntity, i) -> patrolPathMarkerEntity.order = i.intValue(), patrolPathMarkerEntity -> Integer.valueOf(patrolPathMarkerEntity.order))).addField(new KeyedCodec("PauseTime", (Codec)Codec.DOUBLE), (patrolPathMarkerEntity, d) -> patrolPathMarkerEntity.pauseTime = d.doubleValue(), patrolPathMarkerEntity -> Double.valueOf(patrolPathMarkerEntity.pauseTime))).addField(new KeyedCodec("ObsvAngle", (Codec)Codec.DOUBLE), (patrolPathMarkerEntity, d) -> patrolPathMarkerEntity.observationAngle = d.floatValue(), patrolPathMarkerEntity -> Double.valueOf(patrolPathMarkerEntity.observationAngle))).build();
/*     */   }
/*     */   @Nullable
/*     */   public static ComponentType<EntityStore, PatrolPathMarkerEntity> getComponentType() {
/*  74 */     return EntityModule.get().getComponentType(PatrolPathMarkerEntity.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PatrolPathMarkerEntity() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PatrolPathMarkerEntity(World world) {
/*  95 */     super(world);
/*     */   }
/*     */   
/*     */   public void setParentPath(IPrefabPath parentPath) {
/*  99 */     this.parentPath = parentPath;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public UUID getPathId() {
/* 104 */     return this.pathId;
/*     */   }
/*     */   
/*     */   public void setPathId(UUID pathId) {
/* 108 */     this.pathId = pathId;
/*     */   }
/*     */   
/*     */   public String getPathName() {
/* 112 */     return this.pathName;
/*     */   }
/*     */   
/*     */   public void setPathName(String pathName) {
/* 116 */     this.pathName = pathName;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String generateDisplayName(int worldgenId, PatrolPathMarkerEntity patrolPathMarkerEntity) {
/* 121 */     return String.format("%s.%s (%s) #%s [Wait %ss] <Rotate %.2fdeg>", new Object[] {
/* 122 */           Integer.valueOf(worldgenId), patrolPathMarkerEntity.pathId, patrolPathMarkerEntity.pathName, 
/*     */ 
/*     */           
/* 125 */           Integer.valueOf(patrolPathMarkerEntity.order), 
/* 126 */           Double.valueOf(patrolPathMarkerEntity.pauseTime), 
/* 127 */           Float.valueOf(patrolPathMarkerEntity.observationAngle * 57.295776F) });
/*     */   }
/*     */   
/*     */   public short getTempPathLength() {
/* 131 */     return this.tempPathLength;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialise(@Nonnull UUID id, @Nonnull String pathName, int index, double pauseTime, float observationAngle, int worldGenId, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 136 */     this.pathId = id;
/* 137 */     this.pathName = pathName;
/*     */     
/* 139 */     WorldPathData worldPathData = (WorldPathData)componentAccessor.getResource(WorldPathData.getResourceType());
/* 140 */     this.parentPath = worldPathData.getOrConstructPrefabPath(worldGenId, this.pathId, pathName, com.hypixel.hytale.builtin.path.path.PatrolPath::new);
/* 141 */     this.pauseTime = pauseTime;
/* 142 */     this.observationAngle = observationAngle;
/* 143 */     if (index < 0) {
/* 144 */       this.order = this.parentPath.registerNewWaypoint(this, worldGenId);
/*     */     } else {
/* 146 */       this.order = index;
/* 147 */       this.parentPath.registerNewWaypointAt(index, this, worldGenId);
/*     */     } 
/* 149 */     this.tempPathLength = (short)this.parentPath.length();
/*     */   }
/*     */ 
/*     */   
/*     */   public IPath<IPrefabPathWaypoint> getParentPath() {
/* 154 */     return (IPath<IPrefabPathWaypoint>)this.parentPath;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCollidable() {
/* 159 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHiddenFromLivingEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 164 */     Player playerComponent = (Player)componentAccessor.getComponent(targetRef, Player.getComponentType());
/* 165 */     return (playerComponent == null || playerComponent.getGameMode() != GameMode.Creative);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOrder() {
/* 170 */     return this.order;
/*     */   }
/*     */   
/*     */   public void setOrder(int order) {
/* 174 */     this.order = order;
/* 175 */     markNeedsSave();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getPauseTime() {
/* 180 */     return this.pauseTime;
/*     */   }
/*     */   
/*     */   public void setPauseTime(double pauseTime) {
/* 184 */     this.pauseTime = pauseTime;
/* 185 */     markNeedsSave();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getObservationAngle() {
/* 190 */     return this.observationAngle;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onReplaced() {
/* 195 */     this.pathId = null;
/* 196 */     remove();
/*     */   }
/*     */   
/*     */   public void setObservationAngle(float observationAngle) {
/* 200 */     this.observationAngle = observationAngle;
/* 201 */     markNeedsSave();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getWaypointPosition(@Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 207 */     Ref<EntityStore> ref = getReference();
/* 208 */     assert ref != null && ref.isValid() : "Entity reference is null or invalid";
/*     */     
/* 210 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 211 */     assert transformComponent != null;
/*     */     
/* 213 */     return transformComponent.getPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3f getWaypointRotation(@Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 219 */     Ref<EntityStore> ref = getReference();
/* 220 */     assert ref != null && ref.isValid() : "Entity reference is null or invalid";
/*     */     
/* 222 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 223 */     assert transformComponent != null;
/*     */     
/* 225 */     return transformComponent.getRotation();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 231 */     return "PatrolPathMarkerEntity{pathId=" + String.valueOf(this.pathId) + ", path='" + this.pathName + "', order=" + this.order + ", pauseTime=" + this.pauseTime + ", observationAngle=" + this.observationAngle + ", tempPathLength=" + this.tempPathLength + ", parentPath=" + String.valueOf(this.parentPath) + "} " + super
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 239 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\entities\PatrolPathMarkerEntity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */