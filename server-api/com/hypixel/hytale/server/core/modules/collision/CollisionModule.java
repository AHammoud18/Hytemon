/*     */ package com.hypixel.hytale.server.core.modules.collision;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.spatial.KDTree;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.component.spatial.SpatialStructure;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.vector.Vector2d;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.entities.ProjectileComponent;
/*     */ import com.hypixel.hytale.server.core.modules.collision.commands.HitboxCommand;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.component.Projectile;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.Config;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class CollisionModule
/*     */   extends JavaPlugin
/*     */ {
/*  39 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(CollisionModule.class)
/*  40 */     .build();
/*     */   public static final int VALIDATE_INVALID = -1;
/*     */   public static final int VALIDATE_OK = 0;
/*     */   public static final int VALIDATE_ON_GROUND = 1;
/*     */   public static final int VALIDATE_TOUCH_CEIL = 2;
/*     */   private static CollisionModule instance;
/*     */   private ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> tangiableEntitySpatialComponent;
/*     */   private double extentMax;
/*     */   private double minimumThickness;
/*     */   
/*     */   public static CollisionModule get() {
/*  51 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   private final Config<CollisionModuleConfig> config = withConfig("CollisionModule", CollisionModuleConfig.CODEC);
/*     */   
/*     */   public CollisionModule(@Nonnull JavaPluginInit init) {
/*  60 */     super(init);
/*  61 */     instance = this;
/*     */   }
/*     */   
/*     */   public CollisionModuleConfig getConfig() {
/*  65 */     return (CollisionModuleConfig)this.config.get();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  70 */     getCommandRegistry().registerCommand((AbstractCommand)new HitboxCommand());
/*     */     
/*  72 */     getEventRegistry().register(LoadedAssetsEvent.class, BlockBoundingBoxes.class, this::onLoadedAssetsEvent);
/*     */     
/*  74 */     this.tangiableEntitySpatialComponent = getEntityStoreRegistry().registerSpatialResource(() -> new KDTree(Ref::isValid));
/*     */   }
/*     */   
/*     */   public ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> getTangiableEntitySpatialComponent() {
/*  78 */     return this.tangiableEntitySpatialComponent;
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
/*     */   private void onLoadedAssetsEvent(@Nonnull LoadedAssetsEvent<String, BlockBoundingBoxes, IndexedLookupTableAssetMap<String, BlockBoundingBoxes>> event) {
/*  93 */     if (event.isInitial()) {
/*  94 */       this.extentMax = 0.0D;
/*  95 */       this.minimumThickness = Double.MAX_VALUE;
/*     */     } 
/*     */     
/*  98 */     for (BlockBoundingBoxes box : event.getLoadedAssets().values()) {
/*  99 */       handleLoadedHitbox(box);
/*     */     }
/*     */     
/* 102 */     CollisionModuleConfig config = (CollisionModuleConfig)this.config.get();
/* 103 */     if (config.hasMinimumThickness()) {
/* 104 */       this.minimumThickness = config.getMinimumThickness();
/*     */     }
/*     */     
/* 107 */     getLogger().at(Level.INFO).log("Block extents for CollisionSystem is Max=" + this.extentMax + ", Min=" + this.minimumThickness);
/*     */   }
/*     */   private void handleLoadedHitbox(@Nonnull BlockBoundingBoxes box) {
/*     */     double thickness;
/* 111 */     BlockBoundingBoxes.RotatedVariantBoxes defaultBox = box.get(Rotation.None, Rotation.None, Rotation.None);
/* 112 */     double maximumExtent = defaultBox.getBoundingBox().getMaximumExtent();
/* 113 */     double blockExtent = 0.0D;
/* 114 */     if (maximumExtent > blockExtent) blockExtent = maximumExtent;
/*     */     
/* 116 */     if (blockExtent > 1.0D) {
/* 117 */       getLogger().at(Level.FINE).log("Block Hitbox %s protrudes more than 1 unit (%s units) out of standard block and degrades performance", box.getId(), blockExtent);
/*     */     }
/*     */     
/* 120 */     if (blockExtent > this.extentMax) this.extentMax = blockExtent;
/*     */ 
/*     */     
/* 123 */     if (defaultBox.hasDetailBoxes()) {
/* 124 */       thickness = Double.MAX_VALUE;
/* 125 */       for (Box boundingBox : defaultBox.getDetailBoxes()) {
/* 126 */         thickness = Math.min(thickness, boundingBox.getThickness());
/*     */       }
/*     */     } else {
/* 129 */       thickness = defaultBox.getBoundingBox().getThickness();
/*     */     } 
/*     */     
/* 132 */     if (thickness < 0.0D) {
/* 133 */       getLogger().at(Level.SEVERE).log("Hitbox for " + box.getId() + " has a negative size!");
/*     */       
/*     */       return;
/*     */     } 
/* 137 */     if (thickness < this.minimumThickness) this.minimumThickness = thickness;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean findCollisions(@Nonnull Box collider, @Nonnull Vector3d pos, @Nonnull Vector3d v, @Nonnull CollisionResult result, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 149 */     return findCollisions(collider, pos, v, true, result, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean findCollisions(@Nonnull Box collider, @Nonnull Vector3d pos, @Nonnull Vector3d v, boolean stopOnCollisionFound, @Nonnull CollisionResult result, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 158 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*     */     
/* 160 */     result.reset();
/*     */     
/* 162 */     boolean isFarDistance = !isBelowMovementThreshold(v);
/* 163 */     if (isFarDistance) {
/* 164 */       findBlockCollisionsIterative(world, collider, pos, v, stopOnCollisionFound, result);
/*     */     } else {
/*     */       
/* 167 */       findBlockCollisionsShortDistance(world, collider, pos, v, result);
/*     */     } 
/* 169 */     if (result.isCheckingForCharacterCollisions()) findCharacterCollisions(pos, v, result, componentAccessor);
/*     */     
/* 171 */     result.process();
/* 172 */     return isFarDistance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void findBlockCollisionsIterative(@Nonnull World world, @Nonnull Box collider, @Nonnull Vector3d pos, @Nonnull Vector3d v, boolean stopOnCollisionFound, @Nonnull CollisionResult result) {
/* 181 */     if (result.shouldLog()) {
/* 182 */       result.getLogger().at(Level.INFO).log(">>>>>> Start findBlockCollisionIterative collider=[%s] pos=%s dir=%s", collider, 
/* 183 */           Vector3d.formatShortString(pos), Vector3d.formatShortString(v));
/*     */     }
/* 185 */     CollisionConfig coll = result.getConfig();
/* 186 */     coll.setWorld(world);
/*     */     
/* 188 */     result.getMovingBoxBoxCollision().setCollider(collider).setMove(pos, v);
/*     */     
/* 190 */     if (result.shouldLog()) {
/* 191 */       result.getLogger().at(Level.INFO).log(">>>>>> Start collider=[%s] + offset[%s]", collider, v);
/*     */     }
/*     */     
/* 194 */     result.acquireCollisionModule();
/*     */ 
/*     */     
/* 197 */     collider.forEachBlock(pos, 1.0E-5D, result, (x, y, z, aResult) -> aResult.accept(x, y, z));
/*     */     
/* 199 */     if (result.shouldLog()) {
/* 200 */       result.getLogger().at(Level.INFO).log(">>>> line collider=[%s] dir=%s len=%s", collider, Vector3d.formatShortString(v), Double.valueOf(v.length()));
/*     */     }
/*     */     
/* 203 */     result.iterateBlocks(collider, pos, v, v.length(), stopOnCollisionFound);
/*     */     
/* 205 */     coll.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void findCharacterCollisions(@Nonnull Vector3d pos, @Nonnull Vector3d v, @Nonnull CollisionResult result, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 212 */     if (isBelowMovementThreshold(v))
/*     */       return; 
/* 214 */     Vector3d coll = new Vector3d();
/* 215 */     Vector2d minMax = new Vector2d();
/* 216 */     List<Entity> collisionEntities = result.getCollisionEntities();
/*     */ 
/*     */     
/* 219 */     for (int i = 0; i < collisionEntities.size(); i++) {
/* 220 */       Entity entity = collisionEntities.get(i);
/* 221 */       Ref<EntityStore> ref = entity.getReference();
/* 222 */       assert ref != null;
/*     */       
/* 224 */       Archetype<EntityStore> archetype = componentAccessor.getArchetype(ref);
/*     */       
/* 226 */       boolean isProjectile = (archetype.contains(Projectile.getComponentType()) || archetype.contains(ProjectileComponent.getComponentType()));
/* 227 */       if (!isProjectile) {
/*     */ 
/*     */         
/* 230 */         if (archetype.contains(DeathComponent.getComponentType()))
/*     */           return; 
/* 232 */         TransformComponent entityTransformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 233 */         assert entityTransformComponent != null;
/*     */         
/* 235 */         BoundingBox entityBoundingBoxComponent = (BoundingBox)componentAccessor.getComponent(ref, BoundingBox.getComponentType());
/* 236 */         assert entityBoundingBoxComponent != null;
/*     */         
/* 238 */         Vector3d position = entityTransformComponent.getPosition();
/* 239 */         Box boundingBox = entityBoundingBoxComponent.getBoundingBox();
/* 240 */         if (boundingBox != null)
/*     */         {
/* 242 */           if (CollisionMath.intersectVectorAABB(pos, v, position.getX(), position.getY(), position.getZ(), boundingBox, minMax)) {
/* 243 */             coll.assign(pos).addScaled(v, minMax.x);
/* 244 */             result.allocCharacterCollision().assign(coll, minMax.x, entity.getReference(), entity instanceof com.hypixel.hytale.server.core.entity.entities.Player);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void findBlockCollisionsShortDistance(@Nonnull World world, @Nonnull Box collider, @Nonnull Vector3d pos, @Nonnull Vector3d v, @Nonnull CollisionResult result) {
/* 254 */     result.reset();
/*     */     
/* 256 */     result.getConfig().setWorld(world);
/* 257 */     (result.getConfig()).extraData1 = pos;
/*     */     
/* 259 */     BoxBlockIntersectionEvaluator boxBlockIntersectionEvaluator = result.getBoxBlockIntersection();
/* 260 */     boxBlockIntersectionEvaluator.setBox(collider, pos).offsetPosition(v);
/*     */     
/* 262 */     collider.forEachBlock(pos.x + v.x, pos.y + v.y, pos.z + v.z, 1.0E-5D, result, (x, y, z, aResult) -> {
/*     */           CollisionConfig coll = aResult.getConfig();
/*     */           
/*     */           if (!coll.canCollide(x, y, z)) {
/*     */             if (aResult.shouldLog()) {
/*     */               String name = (coll.blockType != null) ? coll.blockType.getId().toString() : "null";
/*     */               
/*     */               aResult.getLogger().at(Level.INFO).log("-- Short: Ignoring block at %s/%s/%s blockType=%s", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z), name);
/*     */             } 
/*     */             
/*     */             return true;
/*     */           } 
/*     */           
/*     */           Vector3d _pos = (Vector3d)coll.extraData1;
/*     */           
/*     */           if (coll.blockId == Integer.MIN_VALUE) {
/*     */             addImmediateCollision(_pos, aResult, coll, 0);
/*     */             
/*     */             if (aResult.shouldLog()) {
/*     */               aResult.getLogger().at(Level.INFO).log("-- Short: Stopping with invalid block at %s/%s/%s blockType=<invalid>", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
/*     */             }
/*     */             return true;
/*     */           } 
/*     */           int boundingBoxX = x + coll.getBoundingBoxOffsetX();
/*     */           int boundingBoxY = y + coll.getBoundingBoxOffsetY();
/*     */           int boundingBoxZ = z + coll.getBoundingBoxOffsetZ();
/*     */           int numDetails = coll.getDetailCount();
/*     */           BoxBlockIntersectionEvaluator blockBox = aResult.getBoxBlockIntersection();
/*     */           int code = blockBox.intersectBoxComputeTouch(coll.getBoundingBox(), boundingBoxX, boundingBoxY, boundingBoxZ);
/*     */           boolean haveCollision = !CollisionMath.isDisjoint(code);
/*     */           if (aResult.shouldLog()) {
/*     */             String name = (coll.blockType != null) ? coll.blockType.getId().toString() : "null";
/*     */             aResult.getLogger().at(Level.INFO).log("?? Block Test at %s/%s/%s numDet=%d haveColl=%s overlap=%s blockType=%s", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z), Integer.valueOf(numDetails), Boolean.valueOf(haveCollision), Boolean.valueOf(aResult.getBoxBlockIntersection().isOverlapping()), name);
/*     */           } 
/*     */           if (numDetails <= 1) {
/*     */             processCollision(aResult, _pos, blockBox, haveCollision, 0);
/*     */           } else {
/*     */             for (int i = 0; i < numDetails; i++) {
/*     */               code = blockBox.intersectBoxComputeTouch(coll.getBoundingBox(i), boundingBoxX, boundingBoxY, boundingBoxZ);
/*     */               haveCollision = !CollisionMath.isDisjoint(code);
/*     */               processCollision(aResult, _pos, blockBox, haveCollision, i);
/*     */             } 
/*     */           } 
/*     */           return true;
/*     */         });
/* 307 */     result.getConfig().clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void processCollision(@Nonnull CollisionResult result, @Nonnull Vector3d pos, @Nonnull BoxBlockIntersectionEvaluator boxBlockIntersectionEvaluator, boolean haveCollision, int hitboxIndex) {
/* 317 */     CollisionConfig coll = result.getConfig();
/* 318 */     Predicate<CollisionConfig> isWalkable = coll.getBlockCollisionPredicate();
/*     */     
/* 320 */     if (result.shouldLog()) {
/* 321 */       result.getLogger().at(Level.INFO).log("?? Short: Further testing block haveCol=%s hitBoxIndex=%s onGround=%s touching=%s canCollide=%s canTrigger=%s", 
/* 322 */           Boolean.valueOf(haveCollision), Integer.valueOf(hitboxIndex), Boolean.valueOf(boxBlockIntersectionEvaluator.isOnGround()), Boolean.valueOf(boxBlockIntersectionEvaluator.isTouching()), Boolean.valueOf(coll.blockCanCollide), Boolean.valueOf(coll.blockCanTrigger));
/*     */     }
/* 324 */     if (boxBlockIntersectionEvaluator.isOnGround() && coll.blockCanCollide) {
/*     */ 
/*     */       
/* 327 */       haveCollision = (coll.blockType == null || !isWalkable.test(coll));
/* 328 */       if (!haveCollision) {
/* 329 */         result.addSlide(boxBlockIntersectionEvaluator, hitboxIndex);
/* 330 */         if (coll.blockCanTrigger) {
/* 331 */           result.addTrigger(boxBlockIntersectionEvaluator, hitboxIndex);
/*     */         }
/* 333 */         if (result.shouldLog()) {
/* 334 */           result.getLogger().at(Level.INFO).log("++ Short: Sliding block start=%s end=%s normal=%s", 
/* 335 */               Double.valueOf(boxBlockIntersectionEvaluator.getCollisionStart()), Double.valueOf(boxBlockIntersectionEvaluator.getCollisionEnd()), Vector3d.formatShortString(boxBlockIntersectionEvaluator.getCollisionNormal()));
/*     */         }
/*     */         return;
/*     */       } 
/* 339 */       if (result.shouldLog()) {
/* 340 */         result.getLogger().at(Level.INFO).log("?? Short: Sliding block is unwalkable start=%s end=%s normal=%s", 
/* 341 */             Double.valueOf(boxBlockIntersectionEvaluator.getCollisionStart()), Double.valueOf(boxBlockIntersectionEvaluator.getCollisionEnd()), Vector3d.formatShortString(boxBlockIntersectionEvaluator.getCollisionNormal()));
/*     */       }
/*     */     } 
/*     */     
/* 345 */     if (haveCollision && coll.blockCanCollide) {
/* 346 */       addImmediateCollision(pos, result, coll, hitboxIndex);
/* 347 */       if (result.shouldLog()) {
/* 348 */         result.getLogger().at(Level.INFO).log("++ Short: Collision with block start=%s end=%s normal=%s", 
/* 349 */             Double.valueOf(boxBlockIntersectionEvaluator.getCollisionStart()), Double.valueOf(boxBlockIntersectionEvaluator.getCollisionEnd()), Vector3d.formatShortString(boxBlockIntersectionEvaluator.getCollisionNormal()));
/*     */       }
/*     */     } 
/* 352 */     if (coll.blockCanTrigger && (haveCollision || boxBlockIntersectionEvaluator.isTouching())) {
/* 353 */       if (result.shouldLog()) {
/* 354 */         result.getLogger().at(Level.INFO).log("++ Short: Trigger block start=%s end=%s normal=%s", 
/* 355 */             Double.valueOf(boxBlockIntersectionEvaluator.getCollisionStart()), Double.valueOf(boxBlockIntersectionEvaluator.getCollisionEnd()), Vector3d.formatShortString(boxBlockIntersectionEvaluator.getCollisionNormal()));
/*     */       }
/* 357 */       result.addTrigger(boxBlockIntersectionEvaluator, hitboxIndex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void findIntersections(@Nonnull World world, @Nonnull Box collider, @Nonnull Vector3d pos, @Nonnull CollisionResult result, boolean triggerBlocks, boolean intersections) {
/* 366 */     if (isDisabled())
/* 367 */       return;  result.reset();
/*     */     
/* 369 */     result.getConfig().setWorld(world);
/* 370 */     (result.getConfig()).extraData1 = Boolean.valueOf(triggerBlocks);
/* 371 */     (result.getConfig()).extraData2 = Boolean.valueOf(intersections);
/*     */     
/* 373 */     result.getBoxBlockIntersection().setBox(collider, pos)
/*     */ 
/*     */       
/* 376 */       .expandBox(0.01D);
/* 377 */     (result.getBoxBlockIntersection()).box.forEachBlock(pos, 1.0E-5D, result, (x, y, z, aResult) -> {
/*     */           CollisionConfig coll = aResult.getConfig();
/*     */           
/*     */           if (!coll.canCollide(x, y, z)) {
/*     */             return true;
/*     */           }
/*     */           
/*     */           int boundingBoxX = x + coll.getBoundingBoxOffsetX();
/*     */           int boundingBoxY = y + coll.getBoundingBoxOffsetY();
/*     */           int boundingBoxZ = z + coll.getBoundingBoxOffsetZ();
/*     */           if (!aResult.getBoxBlockIntersection().isBoxIntersecting(coll.getBoundingBox(), boundingBoxX, boundingBoxY, boundingBoxZ)) {
/*     */             return true;
/*     */           }
/*     */           boolean _triggerBlocks = ((Boolean)coll.extraData1).booleanValue();
/*     */           boolean _intersections = ((Boolean)coll.extraData2).booleanValue();
/*     */           int numDetails = coll.getDetailCount();
/*     */           if (numDetails <= 1) {
/*     */             if (_triggerBlocks && coll.blockCanTrigger) {
/*     */               aResult.addTrigger(aResult.getBoxBlockIntersection(), 0);
/*     */             }
/*     */             if (_intersections) {
/*     */               aResult.addCollision(aResult.getBoxBlockIntersection(), 0);
/*     */             }
/*     */           } else {
/*     */             for (int i = 0; i < numDetails; i++) {
/*     */               if (aResult.getBoxBlockIntersection().isBoxIntersecting(coll.getBoundingBox(i), boundingBoxX, boundingBoxY, boundingBoxZ)) {
/*     */                 if (_triggerBlocks && coll.blockCanTrigger) {
/*     */                   aResult.addTrigger(aResult.getBoxBlockIntersection(), i);
/*     */                 }
/*     */                 if (_intersections) {
/*     */                   aResult.addCollision(aResult.getBoxBlockIntersection(), i);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           return true;
/*     */         });
/* 414 */     result.getConfig().clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int validatePosition(@Nonnull World world, @Nonnull Box collider, @Nonnull Vector3d pos, @Nonnull CollisionResult result) {
/* 421 */     if (isDisabled()) return 0;
/*     */     
/* 423 */     return validatePosition(world, collider, pos, (Object)null, (_this, collisionCode, collision, collisionConfig) -> true, false, result);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> int validatePosition(@Nonnull World world, @Nonnull Box collider, @Nonnull Vector3d pos, int invalidBlockMaterials, T t, @Nonnull CollisionFilter<BoxBlockIntersectionEvaluator, T> predicate, @Nonnull CollisionResult result) {
/* 432 */     if (isDisabled()) return 0;
/*     */     
/* 434 */     int savedCollisionState = result.getCollisionByMaterial();
/*     */     
/* 436 */     result.setCollisionByMaterial(invalidBlockMaterials);
/* 437 */     int code = validatePosition(world, collider, pos, t, predicate, ((invalidBlockMaterials & 0x10) == 0), result);
/*     */     
/* 439 */     result.setCollisionByMaterial(savedCollisionState);
/* 440 */     return code;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <T> int validatePosition(@Nonnull World world, @Nonnull Box collider, @Nonnull Vector3d pos, T t, @Nonnull CollisionFilter<BoxBlockIntersectionEvaluator, T> predicate, boolean disableDamageBlocks, @Nonnull CollisionResult result) {
/* 449 */     CollisionModuleConfig config = (CollisionModuleConfig)this.config.get();
/* 450 */     result.getConfig().setWorld(world);
/* 451 */     (result.getConfig()).dumpInvalidBlocks = config.isDumpInvalidBlocks();
/* 452 */     (result.getConfig()).extraData1 = t;
/* 453 */     (result.getConfig()).extraData2 = predicate;
/*     */     
/* 455 */     result.getBoxBlockIntersection().setBox(collider, pos);
/*     */ 
/*     */     
/* 458 */     boolean saveCheckTriggerState = result.isCheckingTriggerBlocks();
/* 459 */     boolean saveCheckDamageBlock = result.isCheckingDamageBlocks();
/*     */     
/* 461 */     result.disableTriggerBlocks();
/* 462 */     if (disableDamageBlocks) result.disableDamageBlocks();
/*     */     
/* 464 */     result.validate = 0;
/*     */     
/* 466 */     collider.forEachBlock(pos, 1.0E-5D, result, (x, y, z, aResult) -> {
/*     */           CollisionConfig coll = aResult.getConfig();
/*     */           if (!coll.canCollide(x, y, z)) {
/*     */             return true;
/*     */           }
/*     */           BoxBlockIntersectionEvaluator boxBlockIntersection = aResult.getBoxBlockIntersection();
/*     */           int boundingBoxX = x + coll.getBoundingBoxOffsetX();
/*     */           int boundingBoxY = y + coll.getBoundingBoxOffsetY();
/*     */           int boundingBoxZ = z + coll.getBoundingBoxOffsetZ();
/*     */           int code = boxBlockIntersection.intersectBoxComputeOnGround(coll.getBoundingBox(), boundingBoxX, boundingBoxY, boundingBoxZ);
/*     */           if (coll.blockId == Integer.MIN_VALUE) {
/*     */             if (CollisionMath.isOverlapping(code)) {
/*     */               aResult.validate = -1;
/*     */               return false;
/*     */             } 
/*     */             return true;
/*     */           } 
/*     */           if (CollisionMath.isDisjoint(code)) {
/*     */             return true;
/*     */           }
/*     */           Box _collider = boxBlockIntersection.box;
/*     */           Vector3d _pos = boxBlockIntersection.collisionPoint;
/*     */           Object _t = coll.extraData1;
/*     */           CollisionFilter<BoxBlockIntersectionEvaluator, Object> _predicate = (CollisionFilter<BoxBlockIntersectionEvaluator, Object>)coll.extraData2;
/*     */           int numDetails = coll.getDetailCount();
/*     */           if (numDetails <= 1) {
/*     */             if (!_predicate.test(_t, code, boxBlockIntersection, coll)) {
/*     */               return true;
/*     */             }
/*     */             if (CollisionMath.isOverlapping(code)) {
/*     */               if (coll.dumpInvalidBlocks) {
/*     */                 logOverlap(_pos, _collider, coll, coll.getBoundingBox(), x, y, z, 0, code);
/*     */               }
/*     */               aResult.validate = -1;
/*     */               return false;
/*     */             } 
/*     */             if (boxBlockIntersection.isOnGround()) {
/*     */               aResult.validate |= 0x1;
/*     */             }
/*     */             if (boxBlockIntersection.touchesCeil())
/*     */               aResult.validate |= 0x2; 
/*     */           } else {
/*     */             for (int i = 0; i < numDetails; i++) {
/*     */               code = boxBlockIntersection.intersectBoxComputeOnGround(coll.getBoundingBox(i), boundingBoxX, boundingBoxY, boundingBoxZ);
/*     */               if (!CollisionMath.isDisjoint(code) && _predicate.test(_t, code, boxBlockIntersection, coll)) {
/*     */                 if (CollisionMath.isOverlapping(code)) {
/*     */                   if (coll.dumpInvalidBlocks)
/*     */                     logOverlap(_pos, _collider, coll, coll.getBoundingBox(i), x, y, z, i, code); 
/*     */                   aResult.validate = -1;
/*     */                   return false;
/*     */                 } 
/*     */                 if (boxBlockIntersection.isOnGround())
/*     */                   aResult.validate |= 0x1; 
/*     */                 if (boxBlockIntersection.touchesCeil())
/*     */                   aResult.validate |= 0x2; 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           return true;
/*     */         });
/* 526 */     if (saveCheckTriggerState) result.enableTriggerBlocks(); 
/* 527 */     if (saveCheckDamageBlock) result.enableDamageBlocks(); 
/* 528 */     result.getConfig().clear();
/*     */     
/* 530 */     return result.validate;
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
/*     */   private static void addImmediateCollision(@Nonnull Vector3d pos, @Nonnull CollisionResult result, @Nonnull CollisionConfig coll, int i) {
/* 542 */     BlockCollisionData data = result.newCollision();
/* 543 */     data.setStart(pos, 0.0D);
/* 544 */     data.setEnd(1.0D, result.getBoxBlockIntersection().getCollisionNormal());
/* 545 */     data.setBlockData(coll);
/* 546 */     data.setDetailBoxIndex(i);
/* 547 */     data.setTouchingOverlapping(false, true);
/*     */   }
/*     */   
/*     */   public static boolean isBelowMovementThreshold(@Nonnull Vector3d v) {
/* 551 */     return (v.squaredLength() < 1.0000000000000002E-10D);
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
/*     */   private static void logOverlap(@Nonnull Vector3d pos, @Nonnull Box collider, @Nonnull CollisionConfig coll, @Nonnull Box hitBox, int x, int y, int z, int index, int intersectType) {
/* 563 */     get().getLogger().at(Level.WARNING).log("Overlapping blocks - code=%s%s%s index=%s pos=%s loc=%s/%s/%s id=%s mat=%s name=%s box=%s hitbox=%s|%s", 
/* 564 */         ((intersectType & 0x8) != 0) ? "X" : "", ((intersectType & 0x10) != 0) ? "Y" : "", ((intersectType & 0x20) != 0) ? "Z" : "", 
/* 565 */         Integer.valueOf(index), 
/* 566 */         Vector3d.formatShortString(pos), Integer.valueOf(x + coll.getBoundingBoxOffsetX()), Integer.valueOf(y + coll.getBoundingBoxOffsetY()), Integer.valueOf(z + coll.getBoundingBoxOffsetZ()), 
/* 567 */         Integer.valueOf(coll.blockId), (coll.blockMaterial != null) ? coll.blockMaterial.name() : "none", new Object[] {
/* 568 */           (coll.blockType != null) ? coll.blockType.getId() : "none", collider, 
/*     */           
/* 570 */           Vector3d.formatShortString(hitBox.min), Vector3d.formatShortString(hitBox.max)
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\CollisionModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */