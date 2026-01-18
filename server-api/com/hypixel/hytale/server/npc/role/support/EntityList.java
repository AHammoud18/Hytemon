/*     */ package com.hypixel.hytale.server.npc.role.support;
/*     */ 
/*     */ import com.hypixel.hytale.common.collection.BucketItem;
/*     */ import com.hypixel.hytale.common.collection.BucketItemPool;
/*     */ import com.hypixel.hytale.common.collection.BucketList;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.function.consumer.DoubleQuadObjectConsumer;
/*     */ import com.hypixel.hytale.function.consumer.QuadConsumer;
/*     */ import com.hypixel.hytale.function.consumer.TriConsumer;
/*     */ import com.hypixel.hytale.function.predicate.QuadObjectDoublePredicate;
/*     */ import com.hypixel.hytale.function.predicate.QuadPredicate;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.components.SortBufferProviderResource;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiPredicate;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class EntityList
/*     */   extends BucketList<Ref<EntityStore>>
/*     */ {
/*     */   protected static final int BUCKET_COUNT = 6;
/*     */   protected static final int BUCKET_DISTANCE_NEAR = 5;
/*     */   protected static final int BUCKET_DISTANCE_NEARER = 3;
/*  35 */   protected static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*     */   
/*     */   @Nonnull
/*     */   protected final BiPredicate<Ref<EntityStore>, ComponentAccessor<EntityStore>> validator;
/*     */   
/*     */   protected IntArrayList bucketRanges;
/*     */   
/*     */   protected int maxDistanceUnsorted;
/*     */   
/*     */   protected int maxDistanceSorted;
/*     */   protected int maxDistanceAvoidance;
/*     */   protected int squaredMaxDistanceSorted;
/*     */   protected int squaredMaxDistanceAvoidance;
/*     */   protected int squaredMaxDistanceUnsorted;
/*     */   protected int searchRadius;
/*     */   
/*     */   public EntityList(@Nullable BucketItemPool<Ref<EntityStore>> holderPool, @Nonnull BiPredicate<Ref<EntityStore>, ComponentAccessor<EntityStore>> validator) {
/*  52 */     super(holderPool);
/*  53 */     this.validator = validator;
/*  54 */     this.bucketRanges = new IntArrayList();
/*     */   }
/*     */   
/*     */   public int getMaxDistanceUnsorted() {
/*  58 */     return this.maxDistanceUnsorted;
/*     */   }
/*     */   
/*     */   public int getMaxDistanceSorted() {
/*  62 */     return this.maxDistanceSorted;
/*     */   }
/*     */   
/*     */   public int getMaxDistanceAvoidance() {
/*  66 */     return this.maxDistanceAvoidance;
/*     */   }
/*     */   
/*     */   public int getSearchRadius() {
/*  70 */     return this.searchRadius;
/*     */   }
/*     */   
/*     */   public IntArrayList getBucketRanges() {
/*  74 */     return this.bucketRanges;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/*  79 */     this.maxDistanceUnsorted = 0;
/*  80 */     this.maxDistanceSorted = 0;
/*  81 */     this.maxDistanceAvoidance = 0;
/*  82 */     this.squaredMaxDistanceSorted = 0;
/*  83 */     this.squaredMaxDistanceUnsorted = 0;
/*  84 */     this.squaredMaxDistanceAvoidance = 0;
/*  85 */     this.searchRadius = 0;
/*  86 */     this.squaredMaxDistance = 0;
/*  87 */     clear();
/*  88 */     this.bucketRanges.clear();
/*     */   }
/*     */   
/*     */   public int requireDistanceSorted(int value) {
/*  92 */     value = MathUtil.fastCeil(value);
/*  93 */     if (this.maxDistanceSorted < value) this.maxDistanceSorted = value; 
/*  94 */     addBucketDistance(this.bucketRanges, 6, value);
/*  95 */     return value;
/*     */   }
/*     */   
/*     */   public int requireDistanceUnsorted(int value) {
/*  99 */     value = MathUtil.fastCeil(value);
/* 100 */     if (this.maxDistanceUnsorted < value) this.maxDistanceUnsorted = value; 
/* 101 */     addBucketDistance(this.bucketRanges, 6, value);
/* 102 */     return value;
/*     */   }
/*     */   
/*     */   public int requireDistanceAvoidance(int value) {
/* 106 */     value = MathUtil.fastCeil(value);
/* 107 */     if (this.maxDistanceAvoidance < value) this.maxDistanceAvoidance = value; 
/* 108 */     addBucketDistance(this.bucketRanges, 6, value);
/* 109 */     return value;
/*     */   }
/*     */   
/*     */   public void finalizeConfiguration() {
/* 113 */     this.squaredMaxDistanceSorted = this.maxDistanceSorted * this.maxDistanceSorted;
/* 114 */     this.squaredMaxDistanceUnsorted = this.maxDistanceUnsorted * this.maxDistanceUnsorted;
/* 115 */     this.squaredMaxDistanceAvoidance = this.maxDistanceAvoidance * this.maxDistanceAvoidance;
/* 116 */     this.searchRadius = MathUtil.maxValue(this.maxDistanceAvoidance, this.maxDistanceSorted, this.maxDistanceUnsorted);
/* 117 */     this.squaredMaxDistance = this.searchRadius * this.searchRadius;
/*     */     
/* 119 */     if (this.searchRadius == 0)
/*     */       return; 
/* 121 */     int keepRange = (this.maxDistanceAvoidance > 0) ? this.maxDistanceAvoidance : -1;
/* 122 */     if (keepRange > 0) addBucketDistance(this.bucketRanges, 6, keepRange);
/*     */ 
/*     */     
/* 125 */     if (this.maxDistanceSorted > 3) {
/* 126 */       addBucketDistance(this.bucketRanges, 6, 3, keepRange);
/* 127 */       if (this.maxDistanceSorted > 5) {
/* 128 */         addBucketDistance(this.bucketRanges, 6, 5, keepRange);
/*     */       }
/*     */     } 
/*     */     
/* 132 */     configureWithPreSortedArray(this.bucketRanges.toIntArray());
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3d parentPosition, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 137 */     if (!this.validator.test(ref, commandBuffer))
/*     */       return; 
/* 139 */     TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/* 140 */     assert transformComponent != null;
/*     */     
/* 142 */     double distance = parentPosition.distanceSquaredTo(transformComponent.getPosition());
/* 143 */     add(ref, distance);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T, U, V> void forEachEntity(@Nonnull DoubleQuadObjectConsumer<Ref<EntityStore>, T, U, V> consumer, T t, U u, V v, double d, ComponentAccessor<EntityStore> componentAccessor) {
/* 148 */     for (BucketList.Bucket<Ref<EntityStore>> bucket : this.buckets) {
/* 149 */       if (!bucket.isEmpty()) {
/* 150 */         BucketItem[] arrayOfBucketItem = bucket.getItems();
/* 151 */         for (int i = 0, entityHoldersSize = bucket.size(); i < entityHoldersSize; i++) {
/* 152 */           Ref<EntityStore> ref = validateEntityRef(arrayOfBucketItem[i], componentAccessor);
/* 153 */           if (ref != null) consumer.accept(d, ref, t, u, v);
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T, U, V, R> void forEachEntityUnordered(double maxDistance, @Nonnull QuadPredicate<Ref<EntityStore>, T, U, ComponentAccessor<EntityStore>> predicate, @Nonnull QuadConsumer<Ref<EntityStore>, T, V, R> consumer, T t, U u, V v, R r, ComponentAccessor<EntityStore> componentAccessor) {
/* 161 */     int maxDistanceSquared = (int)(maxDistance * maxDistance);
/* 162 */     int endBucket = getLastBucketIndex(maxDistanceSquared);
/*     */     
/* 164 */     for (int i = 0; i <= endBucket; i++) {
/* 165 */       BucketList.Bucket<Ref<EntityStore>> bucket = this.buckets[i];
/* 166 */       if (!bucket.isEmpty()) {
/* 167 */         BucketItem[] arrayOfBucketItem = bucket.getItems();
/* 168 */         if (bucket.isUnsorted()) {
/* 169 */           for (int i1 = 0, entityHoldersSize = bucket.size(); i1 < entityHoldersSize; i1++) {
/* 170 */             BucketItem<Ref<EntityStore>> holder = arrayOfBucketItem[i1];
/* 171 */             if (holder.squaredDistance < maxDistanceSquared) {
/* 172 */               Ref<EntityStore> ref = validateEntityRef(holder, componentAccessor);
/* 173 */               if (ref != null && predicate.test(ref, t, u, componentAccessor)) consumer.accept(ref, t, v, r); 
/*     */             } 
/*     */           } 
/*     */         } else {
/* 177 */           for (int i1 = 0, entityHoldersSize = bucket.size(); i1 < entityHoldersSize; i1++) {
/* 178 */             BucketItem<Ref<EntityStore>> holder = arrayOfBucketItem[i1];
/* 179 */             if (holder.squaredDistance >= maxDistanceSquared)
/*     */               break; 
/* 181 */             Ref<EntityStore> ref = validateEntityRef(holder, componentAccessor);
/* 182 */             if (ref != null && predicate.test(ref, t, u, componentAccessor)) consumer.accept(ref, t, v, r);
/*     */           
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T> void forEachEntityAvoidance(@Nonnull Set<Ref<EntityStore>> ignoredEntitiesForAvoidance, @Nonnull TriConsumer<Ref<EntityStore>, T, CommandBuffer<EntityStore>> consumer, T t, CommandBuffer<EntityStore> commandBuffer) {
/* 191 */     int endBucket = getLastBucketIndex(this.squaredMaxDistanceAvoidance);
/*     */     
/* 193 */     for (int i = 0; i <= endBucket; i++) {
/* 194 */       BucketList.Bucket<Ref<EntityStore>> bucket = this.buckets[i];
/* 195 */       if (!bucket.isEmpty()) {
/* 196 */         BucketItem[] arrayOfBucketItem = bucket.getItems();
/* 197 */         if (bucket.isUnsorted()) {
/* 198 */           for (int i1 = 0, entityHoldersSize = bucket.size(); i1 < entityHoldersSize; i1++) {
/* 199 */             BucketItem<Ref<EntityStore>> entityHolder = arrayOfBucketItem[i1];
/* 200 */             if (entityHolder.squaredDistance <= this.squaredMaxDistanceAvoidance) {
/* 201 */               Ref<EntityStore> ref = validateEntityRef(entityHolder, (ComponentAccessor<EntityStore>)commandBuffer);
/* 202 */               if (ref != null && !ignoredEntitiesForAvoidance.contains(ref)) {
/* 203 */                 consumer.accept(ref, t, commandBuffer);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } else {
/* 208 */           for (int i1 = 0, entityHoldersSize = bucket.size(); i1 < entityHoldersSize; i1++) {
/* 209 */             BucketItem<Ref<EntityStore>> entityHolder = arrayOfBucketItem[i1];
/* 210 */             if (entityHolder.squaredDistance > this.squaredMaxDistanceAvoidance)
/* 211 */               break;  Ref<EntityStore> ref = validateEntityRef(entityHolder, (ComponentAccessor<EntityStore>)commandBuffer);
/* 212 */             if (ref != null && !ignoredEntitiesForAvoidance.contains(ref)) {
/* 213 */               consumer.accept(ref, t, commandBuffer);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public <T, U> void forEachEntityAvoidance(@Nonnull Set<Ref<EntityStore>> ignoredEntitiesForAvoidance, @Nonnull QuadConsumer<Ref<EntityStore>, T, U, CommandBuffer<EntityStore>> consumer, T t, U u, CommandBuffer<EntityStore> commandBuffer) {
/* 223 */     int endBucket = getLastBucketIndex(this.squaredMaxDistanceAvoidance);
/*     */     
/* 225 */     for (int i = 0; i <= endBucket; i++) {
/* 226 */       BucketList.Bucket<Ref<EntityStore>> bucket = this.buckets[i];
/* 227 */       if (!bucket.isEmpty()) {
/* 228 */         BucketItem[] arrayOfBucketItem = bucket.getItems();
/* 229 */         if (bucket.isUnsorted()) {
/* 230 */           for (int i1 = 0, entityHoldersSize = bucket.size(); i1 < entityHoldersSize; i1++) {
/* 231 */             BucketItem<Ref<EntityStore>> entityHolder = arrayOfBucketItem[i1];
/* 232 */             if (entityHolder.squaredDistance <= this.squaredMaxDistanceAvoidance) {
/* 233 */               Ref<EntityStore> ref = validateEntityRef(entityHolder, (ComponentAccessor<EntityStore>)commandBuffer);
/* 234 */               if (ref != null && !ignoredEntitiesForAvoidance.contains(ref)) {
/* 235 */                 consumer.accept(ref, t, u, commandBuffer);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } else {
/* 240 */           for (int i1 = 0, entityHoldersSize = bucket.size(); i1 < entityHoldersSize; i1++) {
/* 241 */             BucketItem<Ref<EntityStore>> entityHolder = arrayOfBucketItem[i1];
/* 242 */             if (entityHolder.squaredDistance > this.squaredMaxDistanceAvoidance)
/* 243 */               break;  Ref<EntityStore> ref = validateEntityRef(entityHolder, (ComponentAccessor<EntityStore>)commandBuffer);
/* 244 */             if (ref != null && !ignoredEntitiesForAvoidance.contains(ref)) {
/* 245 */               consumer.accept(ref, t, u, commandBuffer);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <S, T> int countEntitiesInRange(double minRange, double maxRange, int maxCount, @Nonnull QuadPredicate<S, Ref<EntityStore>, T, ComponentAccessor<EntityStore>> filter, S s, T t, ComponentAccessor<EntityStore> componentAccessor) {
/* 256 */     int minRangeSquared = (int)(minRange * minRange);
/* 257 */     int startBucket = getFirstBucketIndex(minRangeSquared);
/* 258 */     if (startBucket < 0) return 0;
/*     */     
/* 260 */     int maxRangeSquared = (int)(maxRange * maxRange);
/* 261 */     int endBucket = getLastBucketIndex(maxRangeSquared);
/*     */     
/* 263 */     int count = 0;
/* 264 */     for (int i = startBucket; i <= endBucket && count < maxCount; i++) {
/* 265 */       BucketList.Bucket<Ref<EntityStore>> bucket = this.buckets[i];
/* 266 */       if (!bucket.isEmpty()) {
/* 267 */         BucketItem[] arrayOfBucketItem = bucket.getItems();
/* 268 */         if (bucket.isUnsorted()) {
/* 269 */           for (int i1 = 0, entityHoldersSize = bucket.size(); i1 < entityHoldersSize; i1++) {
/* 270 */             BucketItem<Ref<EntityStore>> entityHolder = arrayOfBucketItem[i1];
/* 271 */             double squaredDistance = entityHolder.squaredDistance;
/* 272 */             if (squaredDistance >= minRangeSquared && squaredDistance < maxRangeSquared) {
/*     */               
/* 274 */               Ref<EntityStore> ref = validateEntityRef(entityHolder, componentAccessor);
/* 275 */               if (ref != null && filter.test(s, ref, t, componentAccessor) && 
/* 276 */                 ++count >= maxCount) return count; 
/*     */             } 
/*     */           } 
/*     */         } else {
/* 280 */           for (int i1 = 0, entityHoldersSize = bucket.size(); i1 < entityHoldersSize; i1++) {
/* 281 */             BucketItem<Ref<EntityStore>> entityHolder = arrayOfBucketItem[i1];
/* 282 */             double squaredDistance = entityHolder.squaredDistance;
/* 283 */             if (squaredDistance >= minRangeSquared) {
/* 284 */               if (squaredDistance >= maxRangeSquared) return count;
/*     */               
/* 286 */               Ref<EntityStore> ref = validateEntityRef(entityHolder, componentAccessor);
/* 287 */               if (ref != null && filter.test(s, ref, t, componentAccessor) && 
/* 288 */                 ++count >= maxCount) return count; 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 293 */     }  return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getClosestEntityInRange(double minRange, double maxRange, @Nonnull Predicate<Ref<EntityStore>> filter, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 301 */     int minRangeSquared = (int)(minRange * minRange);
/* 302 */     int startBucket = getFirstBucketIndex(minRangeSquared);
/* 303 */     if (startBucket < 0) return null;
/*     */     
/* 305 */     int maxRangeSquared = (int)(maxRange * maxRange);
/* 306 */     int endBucket = getLastBucketIndex(maxRangeSquared);
/*     */ 
/*     */     
/* 309 */     BucketList.SortBufferProvider sortBufferProvider = ((SortBufferProviderResource)componentAccessor.getResource(SortBufferProviderResource.getResourceType())).getSortBufferProvider();
/* 310 */     for (int i = startBucket; i <= endBucket; i++) {
/* 311 */       BucketList.Bucket<Ref<EntityStore>> bucket = this.buckets[i];
/* 312 */       if (!bucket.isEmpty()) {
/* 313 */         if (bucket.isUnsorted()) bucket.sort(sortBufferProvider); 
/* 314 */         BucketItem[] arrayOfBucketItem = bucket.getItems();
/* 315 */         for (int i1 = 0, entityHoldersSize = bucket.size(); i1 < entityHoldersSize; i1++) {
/* 316 */           BucketItem<Ref<EntityStore>> holder = arrayOfBucketItem[i1];
/* 317 */           double squaredDistance = holder.squaredDistance;
/* 318 */           if (squaredDistance >= minRangeSquared) {
/* 319 */             if (squaredDistance >= maxRangeSquared) return null;
/*     */             
/* 321 */             Ref<EntityStore> ref = validateEntityRef(holder, componentAccessor);
/* 322 */             if (ref != null && filter.test(ref))
/* 323 */               return ref; 
/*     */           } 
/*     */         } 
/*     */       } 
/* 327 */     }  return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <S, T> Ref<EntityStore> getClosestEntityInRange(@Nullable Ref<EntityStore> ignoredEntityReference, double minRange, double maxRange, @Nonnull QuadPredicate<S, Ref<EntityStore>, Role, T> filter, Role role, S s, T t, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 336 */     int minRangeSquared = (int)(minRange * minRange);
/* 337 */     int startBucket = getFirstBucketIndex(minRangeSquared);
/* 338 */     if (startBucket < 0) return null;
/*     */     
/* 340 */     int maxRangeSquared = (int)(maxRange * maxRange);
/* 341 */     int endBucket = getLastBucketIndex(maxRangeSquared);
/*     */ 
/*     */     
/* 344 */     BucketList.SortBufferProvider sortBufferProvider = ((SortBufferProviderResource)componentAccessor.getResource(SortBufferProviderResource.getResourceType())).getSortBufferProvider();
/* 345 */     if (ignoredEntityReference == null)
/* 346 */     { for (int i = startBucket; i <= endBucket; i++) {
/* 347 */         BucketList.Bucket<Ref<EntityStore>> bucket = this.buckets[i];
/* 348 */         if (!bucket.isEmpty()) {
/* 349 */           if (bucket.isUnsorted()) bucket.sort(sortBufferProvider); 
/* 350 */           BucketItem[] arrayOfBucketItem = bucket.getItems();
/* 351 */           for (int i1 = 0, entityHoldersSize = bucket.size(); i1 < entityHoldersSize; i1++) {
/* 352 */             BucketItem<Ref<EntityStore>> holder = arrayOfBucketItem[i1];
/* 353 */             double squaredDistance = holder.squaredDistance;
/* 354 */             if (squaredDistance >= minRangeSquared) {
/* 355 */               if (squaredDistance >= maxRangeSquared) return null;
/*     */               
/* 357 */               Ref<EntityStore> ref = validateEntityRef(holder, componentAccessor);
/* 358 */               if (ref != null && filter.test(s, ref, role, t))
/* 359 */                 return ref; 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }  }
/* 364 */     else { for (int i = startBucket; i <= endBucket; i++) {
/* 365 */         BucketList.Bucket<Ref<EntityStore>> bucket = this.buckets[i];
/* 366 */         if (!bucket.isEmpty()) {
/* 367 */           if (bucket.isUnsorted()) bucket.sort(sortBufferProvider); 
/* 368 */           BucketItem[] arrayOfBucketItem = bucket.getItems();
/* 369 */           for (int i1 = 0, entityHoldersSize = bucket.size(); i1 < entityHoldersSize; i1++) {
/* 370 */             BucketItem<Ref<EntityStore>> holder = arrayOfBucketItem[i1];
/* 371 */             double squaredDistance = holder.squaredDistance;
/* 372 */             if (squaredDistance >= minRangeSquared) {
/* 373 */               if (squaredDistance >= maxRangeSquared) return null;
/*     */               
/* 375 */               Ref<EntityStore> ref = validateEntityRef(holder, componentAccessor);
/* 376 */               if (ref != null && !ref.equals(ignoredEntityReference) && filter.test(s, ref, role, t))
/* 377 */                 return ref; 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }  }
/* 382 */      return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <S, T> Ref<EntityStore> getClosestEntityInRangeProjected(@Nonnull Ref<EntityStore> parentRef, @Nullable Ref<EntityStore> ignoredEntityReference, @Nonnull MotionController motionController, double minRange, double maxRange, @Nonnull QuadPredicate<S, Ref<EntityStore>, Role, T> filter, Role role, S s, T t, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 391 */     int minRangeSquared = (int)(minRange * minRange);
/* 392 */     int startBucket = getFirstBucketIndex(minRangeSquared);
/* 393 */     if (startBucket < 0) return null;
/*     */     
/* 395 */     int maxRangeSquared = (int)(maxRange * maxRange);
/* 396 */     int endBucket = getLastBucketIndex(maxRangeSquared);
/*     */     
/* 398 */     Vector3d position = ((TransformComponent)componentAccessor.getComponent(parentRef, TRANSFORM_COMPONENT_TYPE)).getPosition();
/*     */     
/* 400 */     BucketList.SortBufferProvider sortBufferProvider = ((SortBufferProviderResource)componentAccessor.getResource(SortBufferProviderResource.getResourceType())).getSortBufferProvider();
/* 401 */     if (ignoredEntityReference == null)
/* 402 */     { for (int i = startBucket; i <= endBucket; i++) {
/* 403 */         BucketList.Bucket<Ref<EntityStore>> bucket = this.buckets[i];
/* 404 */         if (!bucket.isEmpty()) {
/* 405 */           if (bucket.isUnsorted()) bucket.sort(sortBufferProvider); 
/* 406 */           BucketItem[] arrayOfBucketItem = bucket.getItems();
/* 407 */           for (int i1 = 0, entityHoldersSize = bucket.size(); i1 < entityHoldersSize; i1++) {
/* 408 */             BucketItem<Ref<EntityStore>> holder = arrayOfBucketItem[i1];
/* 409 */             Ref<EntityStore> ref = validateEntityRef(holder, componentAccessor);
/* 410 */             if (ref != null) {
/*     */               
/* 412 */               double squaredDistance = motionController.waypointDistanceSquared(((TransformComponent)componentAccessor.getComponent(ref, TRANSFORM_COMPONENT_TYPE)).getPosition(), position);
/* 413 */               if (squaredDistance >= minRangeSquared)
/* 414 */               { if (squaredDistance >= maxRangeSquared) return null;
/*     */                 
/* 416 */                 if (filter.test(s, ref, role, t))
/* 417 */                   return ref;  } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }  }
/* 422 */     else { for (int i = startBucket; i <= endBucket; i++) {
/* 423 */         BucketList.Bucket<Ref<EntityStore>> bucket = this.buckets[i];
/* 424 */         if (!bucket.isEmpty()) {
/* 425 */           if (bucket.isUnsorted()) bucket.sort(sortBufferProvider); 
/* 426 */           BucketItem[] arrayOfBucketItem = bucket.getItems();
/* 427 */           for (int i1 = 0, entityHoldersSize = bucket.size(); i1 < entityHoldersSize; i1++) {
/* 428 */             BucketItem<Ref<EntityStore>> holder = arrayOfBucketItem[i1];
/* 429 */             Ref<EntityStore> ref = validateEntityRef(holder, componentAccessor);
/* 430 */             if (ref != null && !ref.equals(ignoredEntityReference)) {
/*     */               
/* 432 */               double squaredDistance = motionController.waypointDistanceSquared(((TransformComponent)componentAccessor.getComponent(ref, TRANSFORM_COMPONENT_TYPE)).getPosition(), position);
/* 433 */               if (squaredDistance >= minRangeSquared)
/* 434 */               { if (squaredDistance >= maxRangeSquared) return null;
/*     */                 
/* 436 */                 if (filter.test(s, ref, role, t))
/* 437 */                   return ref;  } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }  }
/* 442 */      return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <S, T> boolean testAnyEntity(double maxDistance, @Nonnull QuadObjectDoublePredicate<S, Ref<EntityStore>, T, ComponentAccessor<EntityStore>> predicate, S s, T t, ComponentAccessor<EntityStore> componentAccessor) {
/* 448 */     return testAnyEntityDistanceSquared(maxDistance * maxDistance, predicate, s, t, maxDistance, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <S, T> boolean testAnyEntityDistanceSquared(double maxDistanceSquared, @Nonnull QuadObjectDoublePredicate<S, Ref<EntityStore>, T, ComponentAccessor<EntityStore>> predicate, S s, T t, ComponentAccessor<EntityStore> componentAccessor) {
/* 454 */     return testAnyEntityDistanceSquared(maxDistanceSquared, predicate, s, t, maxDistanceSquared, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <S, T> boolean testAnyEntityDistanceSquared(double maxDistanceSquared, @Nonnull QuadObjectDoublePredicate<S, Ref<EntityStore>, T, ComponentAccessor<EntityStore>> predicate, S s, T t, double d, ComponentAccessor<EntityStore> componentAccessor) {
/* 460 */     int endBucket = getLastBucketIndex((int)maxDistanceSquared);
/*     */     
/* 462 */     for (int i = 0; i <= endBucket; i++) {
/* 463 */       BucketList.Bucket<Ref<EntityStore>> bucket = this.buckets[i];
/* 464 */       if (!bucket.isEmpty()) {
/* 465 */         BucketItem[] arrayOfBucketItem = bucket.getItems();
/* 466 */         if (!bucket.isUnsorted()) {
/* 467 */           for (int i1 = 0, entityHoldersSize = bucket.size(); i1 < entityHoldersSize; i1++) {
/* 468 */             BucketItem<Ref<EntityStore>> entityHolder = arrayOfBucketItem[i1];
/* 469 */             if (entityHolder.squaredDistance >= maxDistanceSquared)
/*     */               break; 
/* 471 */             Ref<EntityStore> ref = validateEntityRef(entityHolder, componentAccessor);
/* 472 */             if (ref != null && predicate.test(s, ref, t, componentAccessor, d)) return true; 
/*     */           } 
/*     */         } else {
/* 475 */           for (int i1 = 0, entityHoldersSize = bucket.size(); i1 < entityHoldersSize; i1++) {
/* 476 */             BucketItem<Ref<EntityStore>> entityHolder = arrayOfBucketItem[i1];
/* 477 */             if (entityHolder.squaredDistance < maxDistanceSquared) {
/* 478 */               Ref<EntityStore> ref = validateEntityRef(entityHolder, componentAccessor);
/* 479 */               if (ref != null && predicate.test(s, ref, t, componentAccessor, d)) return true; 
/*     */             } 
/*     */           } 
/* 482 */           return false;
/*     */         } 
/*     */       } 
/* 485 */     }  return false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected Ref<EntityStore> validateEntityRef(@Nonnull BucketItem<Ref<EntityStore>> holder, ComponentAccessor<EntityStore> componentAccessor) {
/* 490 */     Ref<EntityStore> ref = (Ref<EntityStore>)holder.item;
/* 491 */     return (ref == null || !ref.isValid() || !this.validator.test(ref, componentAccessor)) ? null : ref;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\role\support\EntityList.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */