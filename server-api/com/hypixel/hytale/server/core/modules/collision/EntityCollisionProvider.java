/*     */ package com.hypixel.hytale.server.core.modules.collision;
/*     */ 
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.vector.Vector2d;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*     */ import com.hypixel.hytale.server.core.entity.entities.ProjectileComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.component.Projectile;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.List;
/*     */ import java.util.function.BiConsumer;
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
/*     */ public class EntityCollisionProvider
/*     */ {
/*     */   protected static final int ALLOC_SIZE = 4;
/*     */   protected static final double EXTRA_DISTANCE = 8.0D;
/*     */   protected EntityContactData[] contacts;
/*     */   protected EntityContactData[] sortBuffer;
/*     */   protected int count;
/*  41 */   protected final Vector2d minMax = new Vector2d();
/*  42 */   protected final Vector3d collisionPosition = new Vector3d();
/*  43 */   protected final Box tempBox = new Box();
/*     */   
/*     */   protected double nearestCollisionStart;
/*     */   
/*     */   @Nullable
/*     */   protected Vector3d position;
/*     */   
/*     */   @Nullable
/*     */   protected Vector3d direction;
/*     */   
/*     */   @Nullable
/*     */   protected Box boundingBox;
/*     */   @Nullable
/*     */   protected BiPredicate<Ref<EntityStore>, ComponentAccessor<EntityStore>> entityFilter;
/*     */   @Nullable
/*     */   protected Ref<EntityStore> ignoreSelf;
/*     */   @Nullable
/*     */   protected Ref<EntityStore> ignoreOther;
/*     */   
/*     */   public EntityCollisionProvider() {
/*  63 */     this.contacts = new EntityContactData[4];
/*  64 */     this.sortBuffer = new EntityContactData[4];
/*     */     
/*  66 */     for (int i = 0; i < this.contacts.length; i++) {
/*  67 */       this.contacts[i] = new EntityContactData();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount() {
/*  75 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EntityContactData getContact(int index) {
/*  86 */     return this.contacts[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/*  93 */     for (int i = 0; i < this.count; i++) {
/*  94 */       this.contacts[i].clear();
/*     */     }
/*  96 */     this.count = 0;
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
/*     */   public double computeNearest(@Nonnull Box entityBoundingBox, @Nonnull Vector3d pos, @Nonnull Vector3d dir, @Nullable Ref<EntityStore> ignoreSelf, @Nullable Ref<EntityStore> ignore, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 116 */     return computeNearest(pos, dir, entityBoundingBox, dir
/*     */ 
/*     */ 
/*     */         
/* 120 */         .length() + 8.0D, EntityCollisionProvider::defaultEntityFilter, ignoreSelf, ignore, componentAccessor);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double computeNearest(@Nonnull Vector3d pos, @Nonnull Vector3d dir, @Nonnull Box boundingBox, double radius, @Nonnull BiPredicate<Ref<EntityStore>, ComponentAccessor<EntityStore>> entityFilter, @Nullable Ref<EntityStore> ignoreSelf, @Nullable Ref<EntityStore> ignoreOther, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 147 */     this.ignoreSelf = ignoreSelf;
/* 148 */     this.ignoreOther = ignoreOther;
/* 149 */     this.nearestCollisionStart = Double.MAX_VALUE;
/* 150 */     this.entityFilter = entityFilter;
/*     */     
/* 152 */     iterateEntitiesInSphere(pos, dir, boundingBox, radius, (ref, _this) -> acceptNearestIgnore(ref, _this, componentAccessor), (ref, _this1) -> acceptNearestIgnore(ref, _this1, componentAccessor), componentAccessor);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     if (this.count == 0) this.nearestCollisionStart = -1.7976931348623157E308D; 
/* 163 */     clearRefs();
/*     */     
/* 165 */     this.ignoreSelf = null;
/* 166 */     this.ignoreOther = null;
/* 167 */     return this.nearestCollisionStart;
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
/*     */   
/*     */   protected void iterateEntitiesInSphere(@Nonnull Vector3d pos, @Nonnull Vector3d dir, @Nonnull Box boundingBox, double radius, @Nonnull BiConsumer<Ref<EntityStore>, EntityCollisionProvider> consumer, @Nonnull BiConsumer<Ref<EntityStore>, EntityCollisionProvider> consumerPlayer, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 188 */     this.position = pos;
/* 189 */     this.direction = dir;
/* 190 */     this.boundingBox = boundingBox;
/*     */     
/* 192 */     ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 193 */     SpatialResource<Ref<EntityStore>, EntityStore> entitySpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)componentAccessor.getResource(EntityModule.get().getEntitySpatialResourceType());
/* 194 */     entitySpatialResource.getSpatialStructure().collect(pos, radius, (List)results);
/*     */     
/* 196 */     for (ObjectListIterator<Ref<EntityStore>> objectListIterator1 = results.iterator(); objectListIterator1.hasNext(); ) { Ref<EntityStore> ref = objectListIterator1.next();
/* 197 */       if (!ref.isValid())
/* 198 */         continue;  consumer.accept(ref, this); }
/*     */ 
/*     */     
/* 201 */     results.clear();
/* 202 */     SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)componentAccessor.getResource(EntityModule.get().getPlayerSpatialResourceType());
/* 203 */     playerSpatialResource.getSpatialStructure().collect(pos, radius, (List)results);
/*     */     
/* 205 */     for (ObjectListIterator<Ref<EntityStore>> objectListIterator2 = results.iterator(); objectListIterator2.hasNext(); ) { Ref<EntityStore> ref = objectListIterator2.next();
/* 206 */       if (!ref.isValid())
/* 207 */         continue;  consumerPlayer.accept(ref, this); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setContact(@Nonnull Entity entity) {
/* 217 */     this.collisionPosition.assign(this.position).addScaled(this.direction, this.minMax.x);
/* 218 */     this.contacts[0].assign(this.collisionPosition, this.minMax.x, this.minMax.y, entity.getReference(), null);
/* 219 */     this.count = 1;
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
/*     */   protected boolean isColliding(@Nonnull Ref<EntityStore> ref, @Nonnull Vector2d minMax, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 233 */     BoundingBox boundingBoxComponent = (BoundingBox)componentAccessor.getComponent(ref, BoundingBox.getComponentType());
/* 234 */     if (boundingBoxComponent == null) return false;
/*     */     
/* 236 */     Box boundingBox = boundingBoxComponent.getBoundingBox();
/*     */     
/* 238 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 239 */     assert transformComponent != null;
/*     */     
/* 241 */     Vector3d position = transformComponent.getPosition();
/*     */     
/* 243 */     return (CollisionMath.intersectSweptAABBs(this.position, this.direction, this.boundingBox, position, boundingBox, minMax, this.tempBox) && minMax.x <= 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void clearRefs() {
/* 250 */     this.position = null;
/* 251 */     this.direction = null;
/* 252 */     this.boundingBox = null;
/* 253 */     this.entityFilter = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean defaultEntityFilter(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 264 */     Archetype<EntityStore> archetype = componentAccessor.getArchetype(ref);
/*     */ 
/*     */     
/* 267 */     boolean isProjectile = (archetype.contains(Projectile.getComponentType()) || archetype.contains(ProjectileComponent.getComponentType()));
/* 268 */     if (isProjectile) return false;
/*     */     
/* 270 */     boolean isDead = archetype.contains(DeathComponent.getComponentType());
/* 271 */     if (isDead) {
/* 272 */       return false;
/*     */     }
/*     */     
/* 275 */     return ref.isValid();
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
/*     */   protected static void acceptNearestIgnore(@Nonnull Ref<EntityStore> ref, @Nonnull EntityCollisionProvider collisionProvider, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 288 */     Entity entity = EntityUtils.getEntity(ref, componentAccessor);
/* 289 */     if (entity == null)
/*     */       return; 
/* 291 */     if (entity.isCollidable() && 
/* 292 */       !ref.equals(collisionProvider.ignoreSelf) && !ref.equals(collisionProvider.ignoreOther) && (collisionProvider.entityFilter == null || collisionProvider.entityFilter
/* 293 */       .test(ref, componentAccessor)) && collisionProvider
/* 294 */       .isColliding(ref, collisionProvider.minMax, componentAccessor))
/*     */     {
/* 296 */       if (collisionProvider.minMax.x < collisionProvider.nearestCollisionStart) {
/* 297 */         collisionProvider.nearestCollisionStart = collisionProvider.minMax.x;
/* 298 */         collisionProvider.setContact(entity);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\EntityCollisionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */