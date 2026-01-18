/*     */ package com.hypixel.hytale.server.core.modules.collision;
/*     */ 
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.function.consumer.TriConsumer;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.vector.Vector2d;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.DetailBox;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.component.Projectile;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.BiPredicate;
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
/*     */ public class EntityRefCollisionProvider
/*     */ {
/*     */   protected static final int ALLOC_SIZE = 4;
/*     */   protected static final double EXTRA_DISTANCE = 8.0D;
/*     */   protected EntityContactData[] contacts;
/*     */   protected EntityContactData[] sortBuffer;
/*     */   protected int count;
/*  51 */   protected final Vector2d minMax = new Vector2d();
/*  52 */   protected final Vector3d collisionPosition = new Vector3d();
/*  53 */   protected final Box tempBox = new Box();
/*     */   
/*     */   protected double nearestCollisionStart;
/*     */   @Nullable
/*     */   protected Vector3d position;
/*     */   @Nullable
/*     */   protected Vector3d direction;
/*     */   @Nullable
/*     */   protected Box boundingBox;
/*     */   @Nullable
/*     */   protected BiPredicate<Ref<EntityStore>, CommandBuffer<EntityStore>> entityFilter;
/*     */   @Nullable
/*     */   protected Ref<EntityStore> ignoreSelf;
/*     */   @Nullable
/*     */   protected Ref<EntityStore> ignoreOther;
/*     */   @Nonnull
/*  69 */   protected List<Ref<EntityStore>> tmpResults = (List<Ref<EntityStore>>)new ObjectArrayList();
/*     */   @Nonnull
/*  71 */   protected Vector3d tmpVector = new Vector3d();
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected String hitDetail;
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityRefCollisionProvider() {
/*  80 */     this.contacts = new EntityContactData[4];
/*  81 */     this.sortBuffer = new EntityContactData[4];
/*     */     
/*  83 */     for (int i = 0; i < this.contacts.length; i++) {
/*  84 */       this.contacts[i] = new EntityContactData();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount() {
/*  92 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EntityContactData getContact(int i) {
/* 103 */     return this.contacts[i];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 110 */     for (int i = 0; i < this.count; i++) {
/* 111 */       this.contacts[i].clear();
/*     */     }
/* 113 */     this.count = 0;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double computeNearest(@Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull Box entityBoundingBox, @Nonnull Vector3d pos, @Nonnull Vector3d dir, @Nullable Ref<EntityStore> ignoreSelf, @Nullable Ref<EntityStore> ignore) {
/* 133 */     return computeNearest(commandBuffer, pos, dir, entityBoundingBox, dir.length() + 8.0D, EntityRefCollisionProvider::defaultEntityFilter, ignoreSelf, ignore);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double computeNearest(@Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull Vector3d pos, @Nonnull Vector3d dir, @Nonnull Box boundingBox, double radius, @Nonnull BiPredicate<Ref<EntityStore>, CommandBuffer<EntityStore>> entityFilter, @Nullable Ref<EntityStore> ignoreSelf, @Nullable Ref<EntityStore> ignoreOther) {
/* 150 */     this.ignoreSelf = ignoreSelf;
/* 151 */     this.ignoreOther = ignoreOther;
/* 152 */     this.nearestCollisionStart = Double.MAX_VALUE;
/* 153 */     this.entityFilter = entityFilter;
/* 154 */     iterateEntitiesInSphere(commandBuffer, pos, dir, boundingBox, radius, EntityRefCollisionProvider::acceptNearestIgnore);
/* 155 */     if (this.count == 0) this.nearestCollisionStart = -1.7976931348623157E308D; 
/* 156 */     clearRefs();
/* 157 */     this.ignoreSelf = null;
/* 158 */     this.ignoreOther = null;
/* 159 */     return this.nearestCollisionStart;
/*     */   }
/*     */   
/*     */   protected void iterateEntitiesInSphere(@Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull Vector3d pos, @Nonnull Vector3d dir, @Nonnull Box boundingBox, double radius, @Nonnull TriConsumer<EntityRefCollisionProvider, Ref<EntityStore>, CommandBuffer<EntityStore>> consumer) {
/* 163 */     this.position = pos;
/* 164 */     this.direction = dir;
/* 165 */     this.boundingBox = boundingBox;
/*     */     
/* 167 */     SpatialResource<Ref<EntityStore>, EntityStore> spatial = (SpatialResource<Ref<EntityStore>, EntityStore>)commandBuffer.getResource(CollisionModule.get().getTangiableEntitySpatialComponent());
/* 168 */     this.tmpResults.clear();
/* 169 */     spatial.getSpatialStructure().collect(pos, radius, this.tmpResults);
/*     */     
/* 171 */     for (Ref<EntityStore> result : this.tmpResults) {
/* 172 */       consumer.accept(this, result, commandBuffer);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setContact(@Nonnull Ref<EntityStore> ref, @Nonnull String detailName) {
/* 183 */     this.collisionPosition.assign(this.position).addScaled(this.direction, this.minMax.x);
/* 184 */     this.contacts[0].assign(this.collisionPosition, this.minMax.x, this.minMax.y, ref, detailName);
/* 185 */     this.count = 1;
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
/*     */   protected boolean isColliding(@Nonnull Ref<EntityStore> ref, @Nonnull Vector2d minMax, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 197 */     BoundingBox boundingBoxComponent = (BoundingBox)commandBuffer.getComponent(ref, BoundingBox.getComponentType());
/* 198 */     assert boundingBoxComponent != null;
/*     */     
/* 200 */     TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(ref, TransformComponent.getComponentType());
/* 201 */     assert transformComponent != null;
/*     */     
/* 203 */     Box entityBoundingBox = boundingBoxComponent.getBoundingBox();
/*     */     
/* 205 */     if (boundingBoxComponent.getDetailBoxes() != null && !boundingBoxComponent.getDetailBoxes().isEmpty()) {
/* 206 */       for (Map.Entry<String, DetailBox[]> e : (Iterable<Map.Entry<String, DetailBox[]>>)boundingBoxComponent.getDetailBoxes().entrySet()) {
/* 207 */         for (DetailBox v : (DetailBox[])e.getValue()) {
/* 208 */           this.tmpVector.assign(v.getOffset());
/* 209 */           this.tmpVector.rotateY(transformComponent.getRotation().getYaw());
/* 210 */           this.tmpVector.add(transformComponent.getPosition());
/*     */           
/* 212 */           if (CollisionMath.intersectSweptAABBs(this.position, this.direction, this.boundingBox, this.tmpVector, v.getBox(), minMax, this.tempBox) && minMax.x <= 1.0D) {
/* 213 */             this.hitDetail = e.getKey();
/* 214 */             return true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 219 */       this.hitDetail = null;
/* 220 */       return false;
/*     */     } 
/*     */     
/* 223 */     this.hitDetail = null;
/* 224 */     return (CollisionMath.intersectSweptAABBs(this.position, this.direction, this.boundingBox, transformComponent.getPosition(), entityBoundingBox, minMax, this.tempBox) && minMax.x <= 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void clearRefs() {
/* 231 */     this.position = null;
/* 232 */     this.direction = null;
/* 233 */     this.boundingBox = null;
/* 234 */     this.entityFilter = null;
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
/*     */   public static boolean defaultEntityFilter(@Nonnull Ref<EntityStore> entity, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 246 */     if (!entity.isValid()) return false; 
/* 247 */     Archetype<EntityStore> archetype = commandBuffer.getArchetype(entity);
/* 248 */     if (archetype.contains(Projectile.getComponentType())) return false; 
/* 249 */     if (archetype.contains(DeathComponent.getComponentType())) return false;
/*     */     
/* 251 */     Entity legacy = EntityUtils.getEntity(entity, (ComponentAccessor)commandBuffer);
/* 252 */     if (legacy != null && !legacy.isCollidable()) return false; 
/* 253 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void acceptNearestIgnore(@Nonnull Ref<EntityStore> entity, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 263 */     if (this.entityFilter.test(entity, commandBuffer) && !entity.equals(this.ignoreSelf) && !entity.equals(this.ignoreOther) && isColliding(entity, this.minMax, commandBuffer) && 
/* 264 */       this.minMax.x < this.nearestCollisionStart) {
/* 265 */       this.nearestCollisionStart = this.minMax.x;
/* 266 */       setContact(entity, this.hitDetail);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\EntityRefCollisionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */