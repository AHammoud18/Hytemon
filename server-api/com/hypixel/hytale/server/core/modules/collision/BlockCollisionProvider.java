/*     */ package com.hypixel.hytale.server.core.modules.collision;
/*     */ 
/*     */ import com.hypixel.hytale.math.iterator.BoxBlockIterator;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockMaterial;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
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
/*     */ public class BlockCollisionProvider
/*     */   implements BoxBlockIterator.BoxIterationConsumer
/*     */ {
/*  40 */   protected final BoxBlockIntersectionEvaluator boxBlockIntersectionEvaluator = new BoxBlockIntersectionEvaluator();
/*  41 */   protected final MovingBoxBoxCollisionEvaluator movingBoxBoxCollisionEvaluator = new MovingBoxBoxCollisionEvaluator();
/*     */   
/*  43 */   protected final BlockDataProvider blockData = new BlockDataProvider();
/*  44 */   protected final Box fluidBox = new Box(Box.UNIT);
/*  45 */   protected final CollisionTracker damageTracker = new CollisionTracker();
/*  46 */   protected final CollisionTracker triggerTracker = new CollisionTracker();
/*  47 */   protected final BlockTracker collisionTracker = new BlockTracker();
/*     */   
/*  49 */   protected int requestedCollisionMaterials = 4;
/*     */   
/*     */   protected boolean reportOverlaps;
/*     */   
/*     */   @Nullable
/*     */   protected IBlockCollisionConsumer collisionConsumer;
/*     */   @Nullable
/*     */   protected IBlockTracker activeTriggers;
/*     */   @Nullable
/*     */   protected Vector3d motion;
/*     */   protected double relativeStopDistance;
/*     */   protected IBlockCollisionConsumer.Result collisionState;
/*     */   
/*     */   public void setRequestedCollisionMaterials(int requestedCollisionMaterials) {
/*  63 */     this.requestedCollisionMaterials = requestedCollisionMaterials;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReportOverlaps(boolean reportOverlaps) {
/*  74 */     this.reportOverlaps = reportOverlaps;
/*  75 */     this.movingBoxBoxCollisionEvaluator.setComputeOverlaps(reportOverlaps);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean next() {
/*  80 */     return onSliceFinished();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean accept(long x, long y, long z) {
/*  85 */     return processBlockDynamic((int)x, (int)y, (int)z);
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
/*     */   public void cast(@Nonnull World world, @Nonnull Box collider, @Nonnull Vector3d pos, @Nonnull Vector3d v, @Nonnull IBlockCollisionConsumer collisionConsumer, @Nonnull IBlockTracker activeTriggers, double collisionStop) {
/* 106 */     if (CollisionModule.get().isDisabled())
/*     */       return; 
/* 108 */     this.collisionConsumer = collisionConsumer;
/* 109 */     this.activeTriggers = activeTriggers;
/* 110 */     this.motion = v;
/*     */     
/* 112 */     this.blockData.initialize(world);
/*     */ 
/*     */     
/* 115 */     boolean isFarDistance = !CollisionModule.isBelowMovementThreshold(v);
/* 116 */     if (isFarDistance) {
/* 117 */       castIterative(collider, pos, v, collisionStop);
/*     */     } else {
/*     */       
/* 120 */       castShortDistance(collider, pos, v);
/*     */     } 
/* 122 */     collisionConsumer.onCollisionFinished();
/*     */     
/* 124 */     this.blockData.cleanup();
/* 125 */     this.triggerTracker.reset();
/* 126 */     this.damageTracker.reset();
/*     */     
/* 128 */     this.collisionConsumer = null;
/* 129 */     this.activeTriggers = null;
/* 130 */     this.motion = null;
/*     */   }
/*     */   
/*     */   protected void castShortDistance(@Nonnull Box collider, @Nonnull Vector3d pos, @Nonnull Vector3d v) {
/* 134 */     this.boxBlockIntersectionEvaluator.setBox(collider, pos).offsetPosition(v);
/*     */     
/* 136 */     collider.forEachBlock(pos.x + v.x, pos.y + v.y, pos.z + v.z, 1.0E-5D, this, (x, y, z, _this) -> _this.processBlockStatic(x, y, z));
/*     */     
/* 138 */     generateTriggerExit();
/*     */   }
/*     */   
/*     */   protected boolean processBlockStatic(int x, int y, int z) {
/* 142 */     this.blockData.read(x, y, z);
/*     */     
/* 144 */     BlockBoundingBoxes boundingBoxes = this.blockData.getBlockBoundingBoxes();
/* 145 */     int blockX = this.blockData.originX(x);
/* 146 */     int blockY = this.blockData.originY(y);
/* 147 */     int blockZ = this.blockData.originZ(z);
/*     */     
/* 149 */     boolean trigger = (this.blockData.isTrigger() && !this.triggerTracker.isTracked(blockX, blockY, blockZ));
/* 150 */     int damage = this.blockData.getBlockDamage();
/* 151 */     boolean canCollide = canCollide();
/* 152 */     Box[] boxes = boundingBoxes.get(this.blockData.rotation).getDetailBoxes();
/*     */     
/* 154 */     this.boxBlockIntersectionEvaluator.setDamageAndSubmerged(damage, false);
/*     */     
/* 156 */     if (this.blockData.getBlockType().getMaterial() != BlockMaterial.Empty || (this.blockData.getBlockType().getMaterial() == BlockMaterial.Empty && this.blockData.getFluidId() == 0)) {
/*     */       
/* 158 */       if (damage != 0 && boundingBoxes.protrudesUnitBox()) {
/* 159 */         if (this.damageTracker.isTracked(blockX, blockY, blockZ)) {
/* 160 */           damage = 0;
/*     */         } else {
/* 162 */           this.damageTracker.trackNew(blockX, blockY, blockZ);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 167 */       if (canCollide && boundingBoxes.protrudesUnitBox()) {
/* 168 */         if (this.collisionTracker.isTracked(blockX, blockY, blockZ)) {
/* 169 */           canCollide = false;
/*     */         } else {
/* 171 */           this.collisionTracker.trackNew(blockX, blockY, blockZ);
/*     */         } 
/*     */       }
/*     */       
/* 175 */       for (int i = 0; (canCollide || trigger || damage > 0) && i < boxes.length; i++) {
/* 176 */         Box box = boxes[i];
/* 177 */         if (!CollisionMath.isDisjoint(this.boxBlockIntersectionEvaluator.intersectBoxComputeTouch(box, blockX, blockY, blockZ))) {
/* 178 */           if (canCollide || (this.boxBlockIntersectionEvaluator.isOverlapping() && this.reportOverlaps)) {
/* 179 */             this.collisionConsumer.onCollision(blockX, blockY, blockZ, this.motion, this.boxBlockIntersectionEvaluator, this.blockData, box);
/*     */             
/* 181 */             canCollide = false;
/*     */           } 
/* 183 */           if (trigger) {
/* 184 */             if (!this.activeTriggers.isTracked(blockX, blockY, blockZ)) {
/* 185 */               this.activeTriggers.trackNew(blockX, blockY, blockZ);
/*     */             }
/* 187 */             this.triggerTracker.trackNew(blockX, blockY, blockZ);
/* 188 */             trigger = false;
/*     */           } 
/* 190 */           if (damage != 0) {
/* 191 */             this.collisionConsumer.onCollisionDamage(blockX, blockY, blockZ, this.motion, this.boxBlockIntersectionEvaluator, this.blockData);
/* 192 */             damage = 0;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 198 */       Fluid fluid = this.blockData.getFluid();
/* 199 */       if (fluid != null && this.blockData.getFluidId() != 0) {
/* 200 */         processBlockStaticFluid(x, y, z, fluid, true);
/*     */       }
/* 202 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 206 */     if (trigger) {
/* 207 */       this.boxBlockIntersectionEvaluator.setDamageAndSubmerged(damage, false);
/* 208 */       for (Box box : boxes) {
/* 209 */         if (!CollisionMath.isDisjoint(this.boxBlockIntersectionEvaluator.intersectBoxComputeTouch(box, blockX, blockY, blockZ))) {
/* 210 */           this.triggerTracker.trackNew(blockX, blockY, blockZ);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 215 */     processBlockStaticFluid(x, y, z, this.blockData.getFluid(), false);
/* 216 */     return true;
/*     */   }
/*     */   
/*     */   protected void processBlockStaticFluid(int x, int y, int z, @Nonnull Fluid fluid, boolean submergeFluid) {
/* 220 */     boolean processDamage = (fluid.getDamageToEntities() != 0);
/* 221 */     boolean processCollision = canCollide(2);
/*     */     
/* 223 */     if (processDamage || processCollision) {
/* 224 */       this.fluidBox.max.y = this.blockData.getFillHeight();
/* 225 */       if (!CollisionMath.isDisjoint(this.boxBlockIntersectionEvaluator.intersectBoxComputeTouch(this.fluidBox, x, y, z))) {
/* 226 */         this.boxBlockIntersectionEvaluator.setDamageAndSubmerged(fluid.getDamageToEntities(), submergeFluid);
/* 227 */         if (processCollision) this.collisionConsumer.onCollision(x, y, z, this.motion, this.boxBlockIntersectionEvaluator, this.blockData, this.fluidBox); 
/* 228 */         if (processDamage) {
/* 229 */           this.collisionConsumer.onCollisionDamage(x, y, z, this.motion, this.boxBlockIntersectionEvaluator, this.blockData);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean canCollide() {
/* 236 */     return canCollide(this.blockData.getCollisionMaterials());
/*     */   }
/*     */   
/*     */   protected boolean canCollide(int collisionMaterials) {
/* 240 */     return ((collisionMaterials & this.requestedCollisionMaterials) != 0);
/*     */   }
/*     */   
/*     */   protected void castIterative(@Nonnull Box collider, @Nonnull Vector3d pos, @Nonnull Vector3d v, double collisionStop) {
/* 244 */     this.relativeStopDistance = MathUtil.clamp(collisionStop, 0.0D, 1.0D);
/* 245 */     this.collisionState = IBlockCollisionConsumer.Result.CONTINUE;
/*     */     
/* 247 */     this.movingBoxBoxCollisionEvaluator.setCollider(collider).setMove(pos, v);
/*     */ 
/*     */ 
/*     */     
/* 251 */     collider.forEachBlock(pos, 1.0E-5D, this, (x, y, z, _this) -> _this.processBlockDynamic(x, y, z));
/*     */ 
/*     */     
/* 254 */     BoxBlockIterator.iterate(collider, pos, v, v.length(), this);
/*     */     
/* 256 */     int count = this.damageTracker.getCount(); int i;
/* 257 */     for (i = 0; i < count; i++) {
/* 258 */       BlockContactData collision = this.damageTracker.getContactData(i);
/* 259 */       if (collision.getCollisionStart() <= this.relativeStopDistance) {
/* 260 */         Vector3i position = this.damageTracker.getPosition(i);
/* 261 */         this.collisionConsumer.onCollisionDamage(position.x, position.y, position.z, this.motion, collision, this.damageTracker.getBlockData(i));
/*     */       } 
/*     */     } 
/*     */     
/* 265 */     generateTriggerExit();
/*     */     
/* 267 */     count = this.triggerTracker.getCount();
/* 268 */     for (i = 0; i < count; i++) {
/* 269 */       BlockContactData collision = this.triggerTracker.getContactData(i);
/* 270 */       if (collision.getCollisionStart() <= this.relativeStopDistance) {
/* 271 */         Vector3i position = this.triggerTracker.getPosition(i);
/* 272 */         int x = position.x;
/* 273 */         int y = position.y;
/* 274 */         int z = position.z;
/*     */         
/* 276 */         if (!this.activeTriggers.isTracked(x, y, z)) {
/* 277 */           this.activeTriggers.trackNew(x, y, z);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean onSliceFinished() {
/* 285 */     IBlockCollisionConsumer.Result result = this.collisionConsumer.onCollisionSliceFinished();
/* 286 */     if (result != null && this.collisionState.ordinal() < result.ordinal()) this.collisionState = result;
/*     */     
/* 288 */     return (this.collisionState == IBlockCollisionConsumer.Result.CONTINUE);
/*     */   }
/*     */   
/*     */   protected boolean processBlockDynamic(int x, int y, int z) {
/* 292 */     this.blockData.read(x, y, z);
/*     */     
/* 294 */     int blockX = this.blockData.originX(x);
/* 295 */     int blockY = this.blockData.originY(y);
/* 296 */     int blockZ = this.blockData.originZ(z);
/* 297 */     BlockBoundingBoxes boundingBoxes = this.blockData.getBlockBoundingBoxes();
/* 298 */     Box[] boxes = boundingBoxes.get(this.blockData.rotation).getDetailBoxes();
/*     */     
/* 300 */     boolean canCollide = canCollide();
/* 301 */     int damage = this.blockData.getBlockDamage();
/* 302 */     boolean trigger = this.blockData.isTrigger();
/*     */     
/* 304 */     this.movingBoxBoxCollisionEvaluator.setDamageAndSubmerged(damage, false);
/*     */     
/* 306 */     BlockContactData triggerCollisionData = null;
/* 307 */     BlockContactData damageCollisionData = null;
/*     */ 
/*     */     
/* 310 */     if (trigger) {
/* 311 */       triggerCollisionData = this.triggerTracker.getContactData(blockX, blockY, blockZ);
/* 312 */       if (triggerCollisionData != null) trigger = false;
/*     */     
/*     */     } 
/* 315 */     if (damage != 0)
/*     */     {
/* 317 */       if (boundingBoxes.protrudesUnitBox()) {
/* 318 */         damageCollisionData = this.damageTracker.getContactData(blockX, blockY, blockZ);
/* 319 */         if (damageCollisionData != null) {
/* 320 */           damage = 0;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 325 */     if (this.blockData.getBlockType().getMaterial() != BlockMaterial.Empty || (this.blockData.getBlockType().getMaterial() == BlockMaterial.Empty && this.blockData.getFluidId() == 0)) {
/* 326 */       for (Box box : boxes) {
/* 327 */         if (this.movingBoxBoxCollisionEvaluator.isBoundingBoxColliding(box, blockX, blockY, blockZ)) {
/* 328 */           if (this.movingBoxBoxCollisionEvaluator.getCollisionStart() > this.relativeStopDistance) {
/*     */             
/* 330 */             if (this.movingBoxBoxCollisionEvaluator.isOverlapping() && this.reportOverlaps) {
/* 331 */               IBlockCollisionConsumer.Result result = this.collisionConsumer.onCollision(blockX, blockY, blockZ, this.motion, this.movingBoxBoxCollisionEvaluator, this.blockData, box);
/* 332 */               updateStopDistance(result);
/*     */             } 
/*     */           } else {
/*     */             
/* 336 */             if (canCollide || (this.movingBoxBoxCollisionEvaluator.isOverlapping() && this.reportOverlaps)) {
/* 337 */               IBlockCollisionConsumer.Result result = this.collisionConsumer.onCollision(blockX, blockY, blockZ, this.motion, this.movingBoxBoxCollisionEvaluator, this.blockData, box);
/* 338 */               updateStopDistance(result);
/*     */             } 
/* 340 */             if (trigger) triggerCollisionData = processTriggerDynamic(blockX, blockY, blockZ, triggerCollisionData); 
/* 341 */             if (damage != 0) damageCollisionData = processDamageDynamic(blockX, blockY, blockZ, damageCollisionData);
/*     */           
/*     */           } 
/*     */         }
/*     */       } 
/* 346 */       Fluid fluid = this.blockData.getFluid();
/* 347 */       if (fluid != null && this.blockData.getFluidId() != 0) {
/* 348 */         processBlockDynamicFluid(x, y, z, fluid, damageCollisionData, true);
/*     */       }
/* 350 */       return (this.collisionState != IBlockCollisionConsumer.Result.STOP_NOW);
/*     */     } 
/*     */ 
/*     */     
/* 354 */     if (trigger) {
/* 355 */       for (Box box : boxes) {
/* 356 */         if (this.movingBoxBoxCollisionEvaluator.isBoundingBoxColliding(box, blockX, blockY, blockZ) && this.movingBoxBoxCollisionEvaluator
/* 357 */           .getCollisionStart() <= this.relativeStopDistance) {
/* 358 */           triggerCollisionData = processTriggerDynamic(blockX, blockY, blockZ, triggerCollisionData);
/*     */         }
/*     */       } 
/*     */     }
/* 362 */     processBlockDynamicFluid(x, y, z, this.blockData.getFluid(), damageCollisionData, false);
/*     */     
/* 364 */     return (this.collisionState != IBlockCollisionConsumer.Result.STOP_NOW);
/*     */   }
/*     */   
/*     */   protected void processBlockDynamicFluid(int x, int y, int z, @Nonnull Fluid fluid, BlockContactData damageCollisionData, boolean isSubmergeFluid) {
/* 368 */     boolean processDamage = (fluid.getDamageToEntities() != 0);
/* 369 */     boolean processCollision = canCollide(2);
/*     */     
/* 371 */     if (!processDamage && !processCollision)
/*     */       return; 
/* 373 */     this.fluidBox.max.y = this.blockData.getFillHeight();
/* 374 */     if (this.movingBoxBoxCollisionEvaluator.isBoundingBoxColliding(this.fluidBox, x, y, z) && this.movingBoxBoxCollisionEvaluator.getCollisionStart() <= this.relativeStopDistance) {
/* 375 */       this.movingBoxBoxCollisionEvaluator.setDamageAndSubmerged(fluid.getDamageToEntities(), isSubmergeFluid);
/* 376 */       if (processCollision) {
/* 377 */         IBlockCollisionConsumer.Result result = this.collisionConsumer.onCollision(x, y, z, this.motion, this.movingBoxBoxCollisionEvaluator, this.blockData, this.fluidBox);
/* 378 */         updateStopDistance(result);
/*     */       } 
/* 380 */       if (processDamage) {
/* 381 */         processDamageDynamic(x, y, z, damageCollisionData);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected BlockContactData processTriggerDynamic(int blockX, int blockY, int blockZ, @Nullable BlockContactData collisionData) {
/* 389 */     if (collisionData == null) {
/* 390 */       return this.triggerTracker.trackNew(blockX, blockY, blockZ, this.movingBoxBoxCollisionEvaluator, this.blockData);
/*     */     }
/*     */     
/* 393 */     double collisionEnd = Math.max(collisionData.collisionEnd, this.movingBoxBoxCollisionEvaluator.getCollisionEnd());
/*     */     
/* 395 */     if (this.movingBoxBoxCollisionEvaluator.getCollisionStart() < collisionData.collisionStart) {
/* 396 */       collisionData.assign(this.movingBoxBoxCollisionEvaluator);
/*     */     }
/*     */ 
/*     */     
/* 400 */     collisionData.collisionEnd = collisionEnd;
/* 401 */     return collisionData;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected BlockContactData processDamageDynamic(int blockX, int blockY, int blockZ, @Nullable BlockContactData collisionData) {
/* 407 */     IBlockCollisionConsumer.Result result = this.collisionConsumer.probeCollisionDamage(blockX, blockY, blockZ, this.motion, this.movingBoxBoxCollisionEvaluator, this.blockData);
/* 408 */     updateStopDistance(result);
/*     */ 
/*     */     
/* 411 */     if (collisionData == null) {
/* 412 */       return this.damageTracker.trackNew(blockX, blockY, blockZ, this.movingBoxBoxCollisionEvaluator, this.blockData);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 417 */     if (this.movingBoxBoxCollisionEvaluator.getCollisionStart() < collisionData.collisionStart) {
/* 418 */       collisionData.assign(this.movingBoxBoxCollisionEvaluator);
/*     */     }
/* 420 */     return collisionData;
/*     */   }
/*     */   
/*     */   protected void updateStopDistance(@Nullable IBlockCollisionConsumer.Result result) {
/* 424 */     if (result == null || result == IBlockCollisionConsumer.Result.CONTINUE)
/*     */       return; 
/* 426 */     if (this.movingBoxBoxCollisionEvaluator.collisionStart < this.relativeStopDistance) {
/* 427 */       this.relativeStopDistance = this.movingBoxBoxCollisionEvaluator.collisionStart;
/*     */     }
/* 429 */     if (result.ordinal() > this.collisionState.ordinal()) this.collisionState = result; 
/*     */   }
/*     */   
/*     */   protected void generateTriggerExit() {
/* 433 */     for (int i = this.activeTriggers.getCount() - 1; i >= 0; i--) {
/* 434 */       Vector3i p = this.activeTriggers.getPosition(i);
/* 435 */       if (!this.triggerTracker.isTracked(p.x, p.y, p.z))
/* 436 */         this.activeTriggers.untrack(p.x, p.y, p.z); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\BlockCollisionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */