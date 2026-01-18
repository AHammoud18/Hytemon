/*     */ package com.hypixel.hytale.server.core.modules.collision;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.block.BlockUtil;
/*     */ import com.hypixel.hytale.math.iterator.BoxBlockIterator;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionChain;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.FillerBlockUtil;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class CollisionResult
/*     */   implements BoxBlockIterator.BoxIterationConsumer {
/*     */   public static final Comparator<BlockCollisionData> BLOCK_COLLISION_DATA_COMPARATOR;
/*     */   
/*     */   static {
/*  42 */     BLOCK_COLLISION_DATA_COMPARATOR = Comparator.<BlockCollisionData>comparingDouble(a -> a.collisionStart).thenComparingDouble(a -> a.collisionEnd);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final CollisionConfig collisionConfig;
/*     */   
/*     */   @Nonnull
/*     */   private final CollisionDataArray<BlockCollisionData> blockCollisions;
/*     */   
/*     */   @Nonnull
/*     */   private final CollisionDataArray<BlockCollisionData> blockSlides;
/*     */   
/*     */   @Nonnull
/*     */   private final CollisionDataArray<BlockCollisionData> blockTriggers;
/*     */   
/*     */   @Nonnull
/*     */   private final CollisionDataArray<CharacterCollisionData> characterCollisions;
/*     */   
/*     */   @Nonnull
/*     */   private final MovingBoxBoxCollisionEvaluator movingBoxBoxCollision;
/*     */   
/*     */   @Nonnull
/*     */   private final BoxBlockIntersectionEvaluator boxBlockIntersection;
/*     */   
/*     */   public List<Entity> collisionEntities;
/*     */   
/*     */   private boolean continueAfterCollision = true;
/*     */   
/*     */   private boolean haveNoCollision = true;
/*     */   
/*     */   private HytaleLogger logger;
/*     */   public double slideStart;
/*     */   public double slideEnd;
/*     */   public boolean isSliding;
/*     */   public int validate;
/*     */   private boolean checkForCharacterCollisions;
/*     */   private int walkableMaterialMask;
/*     */   public Predicate<CollisionConfig> isNonWalkable;
/*  81 */   private LongSet lastTriggers = (LongSet)new LongOpenHashSet();
/*  82 */   private LongSet newTriggers = (LongSet)new LongOpenHashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CollisionResult() {
/*  88 */     this(true, false);
/*     */   }
/*     */   
/*     */   public CollisionResult(boolean enableSlides, boolean enableCharacters) {
/*  92 */     ObjectArrayList<BlockCollisionData> blockCollisionDataFreePool = new ObjectArrayList();
/*  93 */     ObjectArrayList<CharacterCollisionData> characterCollisionDataFreePool = new ObjectArrayList();
/*     */     
/*  95 */     this.blockCollisions = new CollisionDataArray<>(BlockCollisionData::new, BlockCollisionData::clear, (List<BlockCollisionData>)blockCollisionDataFreePool);
/*  96 */     this.blockSlides = new CollisionDataArray<>(BlockCollisionData::new, BlockCollisionData::clear, (List<BlockCollisionData>)blockCollisionDataFreePool);
/*  97 */     this.blockTriggers = new CollisionDataArray<>(BlockCollisionData::new, BlockCollisionData::clear, (List<BlockCollisionData>)blockCollisionDataFreePool);
/*  98 */     this.characterCollisions = new CollisionDataArray<>(CharacterCollisionData::new, null, (List<CharacterCollisionData>)characterCollisionDataFreePool);
/*     */     
/* 100 */     this.collisionConfig = new CollisionConfig();
/* 101 */     this.collisionConfig.setDefaultCollisionBehaviour();
/* 102 */     this.movingBoxBoxCollision = new MovingBoxBoxCollisionEvaluator();
/* 103 */     this.movingBoxBoxCollision.setCheckForOnGround(enableSlides);
/* 104 */     this.boxBlockIntersection = new BoxBlockIntersectionEvaluator();
/* 105 */     this.checkForCharacterCollisions = enableCharacters;
/*     */     
/* 107 */     setDefaultWalkableBehaviour();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public CollisionConfig getConfig() {
/* 112 */     return this.collisionConfig;
/*     */   }
/*     */   
/*     */   public List<Entity> getCollisionEntities() {
/* 116 */     return this.collisionEntities;
/*     */   }
/*     */   
/*     */   public void setCollisionEntities(List<Entity> collisionEntities) {
/* 120 */     this.collisionEntities = collisionEntities;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BoxBlockIntersectionEvaluator getBoxBlockIntersection() {
/* 125 */     return this.boxBlockIntersection;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public MovingBoxBoxCollisionEvaluator getMovingBoxBoxCollision() {
/* 130 */     return this.movingBoxBoxCollision;
/*     */   }
/*     */   
/*     */   public CharacterCollisionData allocCharacterCollision() {
/* 134 */     return this.characterCollisions.alloc();
/*     */   }
/*     */   
/*     */   public void addCollision(@Nonnull IBlockCollisionEvaluator blockCollisionEvaluator, int index) {
/* 138 */     if (blockCollisionEvaluator.getCollisionStart() > 1.0D)
/* 139 */       return;  blockCollisionEvaluator.setCollisionData(newCollision(), this.collisionConfig, index);
/*     */   }
/*     */   
/*     */   public BlockCollisionData newCollision() {
/* 143 */     return this.blockCollisions.alloc();
/*     */   }
/*     */   
/*     */   public void addSlide(@Nonnull IBlockCollisionEvaluator blockCollisionEvaluator, int index) {
/* 147 */     if (blockCollisionEvaluator.getCollisionStart() > 1.0D)
/* 148 */       return;  blockCollisionEvaluator.setCollisionData(newSlide(), this.collisionConfig, index);
/*     */   }
/*     */   
/*     */   public BlockCollisionData newSlide() {
/* 152 */     return this.blockSlides.alloc();
/*     */   }
/*     */   
/*     */   public void addTrigger(@Nonnull IBlockCollisionEvaluator blockCollisionEvaluator, int index) {
/* 156 */     if (blockCollisionEvaluator.getCollisionStart() > 1.0D)
/* 157 */       return;  blockCollisionEvaluator.setCollisionData(newTrigger(), this.collisionConfig, index);
/*     */   }
/*     */   
/*     */   public BlockCollisionData newTrigger() {
/* 161 */     return this.blockTriggers.alloc();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 169 */     this.blockCollisions.reset();
/* 170 */     this.blockSlides.reset();
/* 171 */     this.blockTriggers.reset();
/* 172 */     this.characterCollisions.reset();
/*     */   }
/*     */   
/*     */   public void process() {
/* 176 */     this.blockCollisions.sort(BasicCollisionData.COLLISION_START_COMPARATOR);
/* 177 */     this.blockTriggers.sort(BasicCollisionData.COLLISION_START_COMPARATOR);
/* 178 */     this.characterCollisions.sort(BasicCollisionData.COLLISION_START_COMPARATOR);
/*     */     
/* 180 */     if (this.blockSlides.getCount() > 0) {
/*     */       
/* 182 */       this.blockSlides.sort(BLOCK_COLLISION_DATA_COMPARATOR);
/*     */ 
/*     */       
/* 185 */       BlockCollisionData slide = this.blockSlides.get(0);
/*     */       
/* 187 */       this.slideStart = slide.collisionStart;
/* 188 */       this.slideEnd = slide.collisionEnd;
/*     */       
/* 190 */       for (int i = 1; i < this.blockSlides.getCount(); i++) {
/* 191 */         slide = this.blockSlides.get(i);
/* 192 */         if (slide.collisionStart <= this.slideEnd && 
/* 193 */           slide.collisionEnd > this.slideEnd) {
/* 194 */           this.slideEnd = slide.collisionEnd;
/*     */         }
/*     */       } 
/*     */       
/* 198 */       this.isSliding = (this.slideStart <= 0.0D);
/* 199 */       if (this.slideEnd > 1.0D) {
/* 200 */         this.slideEnd = 1.0D;
/*     */       }
/*     */     } else {
/* 203 */       this.isSliding = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getBlockCollisionCount() {
/* 208 */     return this.blockCollisions.getCount();
/*     */   }
/*     */   
/*     */   public BlockCollisionData getBlockCollision(int i) {
/* 212 */     return this.blockCollisions.get(i);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockCollisionData getFirstBlockCollision() {
/* 217 */     return this.blockCollisions.getFirst();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockCollisionData forgetFirstBlockCollision() {
/* 222 */     return this.blockCollisions.forgetFirst();
/*     */   }
/*     */   
/*     */   public int getCharacterCollisionCount() {
/* 226 */     return this.characterCollisions.getCount();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public CharacterCollisionData getFirstCharacterCollision() {
/* 231 */     return this.characterCollisions.getFirst();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public CharacterCollisionData forgetFirstCharacterCollision() {
/* 236 */     return this.characterCollisions.forgetFirst();
/*     */   }
/*     */   
/*     */   public void pruneTriggerBlocks(double distance) {
/* 240 */     int l = this.blockTriggers.size() - 1;
/*     */     
/* 242 */     while (l >= 0) {
/* 243 */       BlockCollisionData blockCollisionData = this.blockTriggers.get(l);
/* 244 */       if (blockCollisionData.collisionStart <= distance) {
/*     */         break;
/*     */       }
/* 247 */       this.blockTriggers.remove(l);
/* 248 */       l--;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public CollisionDataArray<BlockCollisionData> getTriggerBlocks() {
/* 254 */     return this.blockTriggers;
/*     */   }
/*     */   
/*     */   public int defaultTriggerBlocksProcessing(@Nonnull InteractionManager manager, @Nonnull Entity entity, @Nonnull Ref<EntityStore> ref, boolean executeTriggers, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 258 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/* 259 */     LongSet temp = this.lastTriggers;
/* 260 */     this.lastTriggers = this.newTriggers;
/* 261 */     this.newTriggers = temp;
/* 262 */     this.newTriggers.clear();
/*     */     
/* 264 */     int damageToEntity = 0;
/* 265 */     CollisionDataArray<BlockCollisionData> triggerBlocks = getTriggerBlocks();
/* 266 */     for (int i = 0, size = triggerBlocks.size(); i < size; i++) {
/* 267 */       BlockCollisionData triggerCollision = triggerBlocks.get(i);
/* 268 */       if (triggerCollision.blockType != null) {
/* 269 */         int damageToEntities = Math.max(triggerCollision.blockType.getDamageToEntities(), triggerCollision.fluid.getDamageToEntities());
/* 270 */         if (damageToEntities > damageToEntity) {
/* 271 */           damageToEntity = damageToEntities;
/*     */         }
/*     */       } 
/* 274 */       if (executeTriggers && entity instanceof com.hypixel.hytale.server.core.entity.LivingEntity) {
/* 275 */         WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(triggerCollision.x, triggerCollision.z));
/* 276 */         if (chunk != null) {
/* 277 */           BlockType blockType = chunk.getBlockType(triggerCollision.x, triggerCollision.y, triggerCollision.z);
/* 278 */           Fluid fluidType = (Fluid)Fluid.getAssetMap().getAsset(chunk.getFluidId(triggerCollision.x, triggerCollision.y, triggerCollision.z));
/*     */           
/* 280 */           String interactionsEnter = (String)blockType.getInteractions().get(InteractionType.CollisionEnter);
/* 281 */           if (interactionsEnter == null) interactionsEnter = (String)fluidType.getInteractions().get(InteractionType.CollisionEnter);
/*     */           
/* 283 */           String interactions = (String)blockType.getInteractions().get(InteractionType.Collision);
/* 284 */           if (interactions == null) interactions = (String)fluidType.getInteractions().get(InteractionType.Collision);
/*     */           
/* 286 */           if (interactionsEnter != null || interactions != null) {
/*     */             
/* 288 */             int filler = chunk.getFiller(triggerCollision.x, triggerCollision.y, triggerCollision.z);
/*     */             
/* 290 */             int x = triggerCollision.x;
/* 291 */             int y = triggerCollision.y;
/* 292 */             int z = triggerCollision.z;
/* 293 */             String blockTypeId = blockType.getId();
/* 294 */             if (filler != 0) {
/* 295 */               x -= FillerBlockUtil.unpackX(filler);
/* 296 */               y -= FillerBlockUtil.unpackY(filler);
/* 297 */               z -= FillerBlockUtil.unpackZ(filler);
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 302 */             long index = BlockUtil.pack(x, y, z);
/* 303 */             if (this.newTriggers.add(index)) {
/*     */ 
/*     */ 
/*     */               
/* 307 */               BlockPosition pos = new BlockPosition(x, y, z);
/*     */               
/* 309 */               if (!this.lastTriggers.remove(index) && 
/* 310 */                 interactionsEnter != null) {
/* 311 */                 doCollisionInteraction(manager, InteractionType.CollisionEnter, ref, interactionsEnter, pos, componentAccessor);
/*     */               }
/*     */ 
/*     */               
/* 315 */               if (interactions != null) {
/* 316 */                 doCollisionInteraction(manager, InteractionType.Collision, ref, interactions, pos, componentAccessor);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 323 */     if (executeTriggers && entity instanceof com.hypixel.hytale.server.core.entity.LivingEntity && !this.lastTriggers.isEmpty())
/*     */     {
/* 325 */       for (LongIterator<Long> longIterator = this.lastTriggers.iterator(); longIterator.hasNext(); ) { Long old = longIterator.next();
/* 326 */         int x = BlockUtil.unpackX(old.longValue());
/* 327 */         int y = BlockUtil.unpackY(old.longValue());
/* 328 */         int z = BlockUtil.unpackZ(old.longValue());
/*     */         
/* 330 */         WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(x, z));
/* 331 */         if (chunk != null) {
/* 332 */           BlockType blockType = chunk.getBlockType(x, y, z);
/* 333 */           Fluid fluidType = (Fluid)Fluid.getAssetMap().getAsset(chunk.getFluidId(x, y, z));
/* 334 */           String interactions = (String)blockType.getInteractions().get(InteractionType.CollisionLeave);
/* 335 */           if (interactions == null) interactions = (String)fluidType.getInteractions().get(InteractionType.CollisionLeave); 
/* 336 */           if (interactions != null) {
/* 337 */             doCollisionInteraction(manager, InteractionType.CollisionLeave, ref, interactions, new BlockPosition(x, y, z), componentAccessor);
/*     */           }
/*     */         }  }
/*     */     
/*     */     }
/*     */     
/* 343 */     return damageToEntity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doCollisionInteraction(@Nonnull InteractionManager manager, @Nonnull InteractionType type, @Nonnull Ref<EntityStore> ref, @Nonnull String interactions, @Nonnull BlockPosition pos, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 353 */     RootInteraction root = RootInteraction.getRootInteractionOrUnknown(interactions);
/*     */     
/* 355 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/* 356 */     InteractionContext context = InteractionContext.forInteraction(manager, ref, type, componentAccessor);
/* 357 */     context.getMetaStore().putMetaObject(Interaction.TARGET_BLOCK_RAW, pos);
/* 358 */     context.getMetaStore().putMetaObject(Interaction.TARGET_BLOCK, world.getBaseBlock(pos));
/*     */     
/* 360 */     InteractionChain chain = manager.initChain(type, context, root, -1, pos, false);
/* 361 */     manager.queueExecuteChain(chain);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean next() {
/* 367 */     return (this.continueAfterCollision || this.haveNoCollision);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean accept(long x, long y, long z) {
/* 373 */     if (this.collisionConfig.canCollide((int)x, (int)y, (int)z)) {
/*     */       
/* 375 */       x += this.collisionConfig.getBoundingBoxOffsetX();
/* 376 */       y += this.collisionConfig.getBoundingBoxOffsetY();
/* 377 */       z += this.collisionConfig.getBoundingBoxOffsetZ();
/*     */       
/* 379 */       int numDetails = this.collisionConfig.getDetailCount();
/* 380 */       boolean haveCollision = this.movingBoxBoxCollision.isBoundingBoxColliding(this.collisionConfig.getBoundingBox(), x, y, z);
/*     */       
/* 382 */       if (this.logger != null) {
/* 383 */         Object arg7 = (this.collisionConfig.blockType != null) ? this.collisionConfig.blockType.getId() : "null";
/* 384 */         this.logger.at(Level.INFO).log("?? Block Test at %s/%s/%s numDet=%d haveColl=%s overlap=%s blockType=%s", Long.valueOf(x), Long.valueOf(y), Long.valueOf(z), Integer.valueOf(numDetails), Boolean.valueOf(haveCollision), Boolean.valueOf(this.movingBoxBoxCollision.isOverlapping()), arg7);
/*     */       } 
/* 386 */       if (numDetails <= 1) {
/* 387 */         processCollisionResult(haveCollision, 0);
/* 388 */       } else if (haveCollision || this.movingBoxBoxCollision.isOverlapping() || this.movingBoxBoxCollision.isTouching()) {
/* 389 */         for (int i = 0; i < numDetails; i++) {
/* 390 */           haveCollision = this.movingBoxBoxCollision.isBoundingBoxColliding(this.collisionConfig.getBoundingBox(i), x, y, z);
/* 391 */           processCollisionResult(haveCollision, i);
/*     */         } 
/*     */       } 
/* 394 */     } else if (this.logger != null) {
/* 395 */       Object arg4 = (this.collisionConfig.blockType != null) ? this.collisionConfig.blockType.getId() : "null";
/* 396 */       this.logger.at(Level.INFO).log("-- Ignoring block at %s/%s/%s blockType=%s", Long.valueOf(x), Long.valueOf(y), Long.valueOf(z), arg4);
/*     */     } 
/* 398 */     return true;
/*     */   }
/*     */   
/*     */   private void processCollisionResult(boolean haveCollision, int hitboxIndex) {
/* 402 */     if (this.logger != null) {
/* 403 */       this.logger.at(Level.INFO).log("?? Further testing block haveCol=%s hitBoxIndex=%s onGround=%s touching=%s canCollide=%s canTrigger=%s", Boolean.valueOf(haveCollision), Integer.valueOf(hitboxIndex), Boolean.valueOf(this.movingBoxBoxCollision.isOnGround()), Boolean.valueOf(this.movingBoxBoxCollision.isTouching()), Boolean.valueOf(this.collisionConfig.blockCanCollide), Boolean.valueOf(this.collisionConfig.blockCanTrigger));
/*     */     }
/* 405 */     if (this.collisionConfig.blockCanCollide) {
/* 406 */       boolean isNoSlideCollision = true;
/* 407 */       if (this.movingBoxBoxCollision.onGround) {
/*     */ 
/*     */         
/* 410 */         haveCollision = (this.collisionConfig.blockType == null || this.isNonWalkable.test(this.collisionConfig));
/* 411 */         if (!haveCollision) {
/* 412 */           addSlide(this.movingBoxBoxCollision, hitboxIndex);
/* 413 */           if (this.collisionConfig.blockCanTrigger) {
/* 414 */             addTrigger(this.movingBoxBoxCollision, hitboxIndex);
/*     */           }
/* 416 */           if (this.logger != null) {
/* 417 */             this.logger.at(Level.INFO).log("++ Sliding block start=%s end=%s normal=%s", Double.valueOf(this.movingBoxBoxCollision.getCollisionStart()), Double.valueOf(this.movingBoxBoxCollision.getCollisionEnd()), Vector3d.formatShortString(this.movingBoxBoxCollision.getCollisionNormal()));
/*     */           }
/*     */           
/*     */           return;
/*     */         } 
/* 422 */         isNoSlideCollision = false;
/* 423 */         if (this.logger != null) {
/* 424 */           this.logger.at(Level.INFO).log("?? Sliding block is unwalkable start=%s end=%s normal=%s", Double.valueOf(this.movingBoxBoxCollision.getCollisionStart()), Double.valueOf(this.movingBoxBoxCollision.getCollisionEnd()), Vector3d.formatShortString(this.movingBoxBoxCollision.getCollisionNormal()));
/*     */         }
/*     */       } 
/* 427 */       if (haveCollision) {
/* 428 */         addCollision(this.movingBoxBoxCollision, hitboxIndex);
/* 429 */         if (isNoSlideCollision) this.haveNoCollision = false; 
/* 430 */         if (this.logger != null) {
/* 431 */           this.logger.at(Level.INFO).log("++ Collision with block start=%s end=%s normal=%s", Double.valueOf(this.movingBoxBoxCollision.collisionStart), Double.valueOf(this.movingBoxBoxCollision.collisionEnd), Vector3d.formatShortString(this.movingBoxBoxCollision.collisionNormal));
/*     */         }
/*     */       } 
/*     */     } 
/* 435 */     if (this.collisionConfig.blockCanTrigger && (haveCollision || this.movingBoxBoxCollision.isTouching())) {
/* 436 */       if (this.logger != null) {
/* 437 */         this.logger.at(Level.INFO).log("++ Trigger block start=%s end=%s normal=%s", Double.valueOf(this.movingBoxBoxCollision.getCollisionStart()), Double.valueOf(this.movingBoxBoxCollision.getCollisionEnd()), Vector3d.formatShortString(this.movingBoxBoxCollision.getCollisionNormal()));
/*     */       }
/* 439 */       addTrigger(this.movingBoxBoxCollision, hitboxIndex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void iterateBlocks(@Nonnull Box collider, @Nonnull Vector3d pos, @Nonnull Vector3d direction, double length, boolean stopOnCollisionFound) {
/* 444 */     this.continueAfterCollision = !stopOnCollisionFound;
/* 445 */     BoxBlockIterator.iterate(collider, pos, direction, length, this);
/*     */   }
/*     */   
/*     */   public void acquireCollisionModule() {
/* 449 */     this.haveNoCollision = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disableSlides() {
/* 457 */     this.movingBoxBoxCollision.setCheckForOnGround(false);
/*     */   }
/*     */   
/*     */   public void enableSlides() {
/* 461 */     this.movingBoxBoxCollision.setCheckForOnGround(true);
/*     */   }
/*     */   
/*     */   public void disableCharacterCollisions() {
/* 465 */     this.checkForCharacterCollisions = false;
/*     */   }
/*     */   
/*     */   public void enableCharacterCollsions() {
/* 469 */     this.checkForCharacterCollisions = true;
/*     */   }
/*     */   
/*     */   public boolean isCheckingForCharacterCollisions() {
/* 473 */     return this.checkForCharacterCollisions;
/*     */   }
/*     */   
/*     */   public void enableTriggerBlocks() {
/* 477 */     this.collisionConfig.setCheckTriggerBlocks(true);
/*     */   }
/*     */   
/*     */   public void disableTriggerBlocks() {
/* 481 */     this.collisionConfig.setCheckTriggerBlocks(false);
/*     */   }
/*     */   
/*     */   public boolean isCheckingTriggerBlocks() {
/* 485 */     return this.collisionConfig.isCheckTriggerBlocks();
/*     */   }
/*     */   
/*     */   public void enableDamageBlocks() {
/* 489 */     this.collisionConfig.setCheckDamageBlocks(true);
/*     */   }
/*     */   
/*     */   public void disableDamageBlocks() {
/* 493 */     this.collisionConfig.setCheckDamageBlocks(false);
/*     */   }
/*     */   
/*     */   public boolean isCheckingDamageBlocks() {
/* 497 */     return this.collisionConfig.isCheckDamageBlocks();
/*     */   }
/*     */   
/*     */   public boolean setDamageBlocking(boolean blocking) {
/* 501 */     boolean oldState = this.collisionConfig.setCollideWithDamageBlocks(blocking);
/* 502 */     updateDamageWalkableFlag();
/* 503 */     return oldState;
/*     */   }
/*     */   
/*     */   public boolean isDamageBlocking() {
/* 507 */     return this.collisionConfig.isCollidingWithDamageBlocks();
/*     */   }
/*     */   
/*     */   public void setCollisionByMaterial(int collidingMaterials) {
/* 511 */     this.collisionConfig.setCollisionByMaterial(collidingMaterials);
/*     */   }
/*     */   
/*     */   public void setCollisionByMaterial(int collidingMaterials, int walkableMaterials) {
/* 515 */     this.collisionConfig.setCollisionByMaterial(collidingMaterials);
/* 516 */     setWalkableByMaterial(walkableMaterials);
/*     */   }
/*     */   
/*     */   public int getCollisionByMaterial() {
/* 520 */     return this.collisionConfig.getCollisionByMaterial();
/*     */   }
/*     */   
/*     */   public void setDefaultCollisionBehaviour() {
/* 524 */     this.collisionConfig.setDefaultCollisionBehaviour();
/*     */   }
/*     */   
/*     */   public void setDefaultBlockCollisionPredicate() {
/* 528 */     this.collisionConfig.setDefaultBlockCollisionPredicate();
/*     */   }
/*     */   
/*     */   public void setDefaultNonWalkablePredicate() {
/* 532 */     this.isNonWalkable = (collisionConfig -> {
/*     */         int matches = collisionConfig.blockMaterialMask & this.walkableMaterialMask;
/* 534 */         return (matches == 0 || (matches & 0x10) != 0);
/*     */       });
/*     */   }
/*     */   
/*     */   public void setNonWalkablePredicate(Predicate<CollisionConfig> classifier) {
/* 539 */     this.isNonWalkable = classifier;
/*     */   }
/*     */   
/*     */   public void setWalkableByMaterial(int walkableMaterial) {
/* 543 */     this.walkableMaterialMask = 0xF & walkableMaterial;
/* 544 */     updateDamageWalkableFlag();
/*     */   }
/*     */   
/*     */   protected void updateDamageWalkableFlag() {
/* 548 */     if (this.collisionConfig.isCollidingWithDamageBlocks()) {
/* 549 */       this.walkableMaterialMask |= 0x10;
/*     */     } else {
/* 551 */       this.walkableMaterialMask &= 0xFFFFFFEF;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setDefaultWalkableBehaviour() {
/* 556 */     setDefaultNonWalkablePredicate();
/* 557 */     setWalkableByMaterial(5);
/*     */   }
/*     */   
/*     */   public void setDefaultPlayerSettings() {
/* 561 */     enableSlides();
/* 562 */     disableCharacterCollisions();
/* 563 */     setDefaultNonWalkablePredicate();
/* 564 */     setDefaultBlockCollisionPredicate();
/* 565 */     setCollisionByMaterial(4);
/* 566 */     setWalkableByMaterial(15);
/*     */   }
/*     */   
/*     */   public boolean isComputeOverlaps() {
/* 570 */     return this.movingBoxBoxCollision.isComputeOverlaps();
/*     */   }
/*     */   
/*     */   public void setComputeOverlaps(boolean computeOverlaps) {
/* 574 */     this.movingBoxBoxCollision.setComputeOverlaps(computeOverlaps);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public HytaleLogger getLogger() {
/* 580 */     return this.logger;
/*     */   }
/*     */   
/*     */   public boolean shouldLog() {
/* 584 */     return (this.logger != null);
/*     */   }
/*     */   
/*     */   public void setLogger(HytaleLogger logger) {
/* 588 */     this.logger = logger;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\CollisionResult.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */