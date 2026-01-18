/*     */ package com.hypixel.hytale.server.core.entity.effect;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.protocol.EffectOp;
/*     */ import com.hypixel.hytale.protocol.EntityEffectUpdate;
/*     */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
/*     */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.OverlapBehavior;
/*     */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.RemovalBehavior;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.livingentity.LivingEntityEffectSystem;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerSkinComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntArraySet;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class EffectControllerComponent implements Component<EntityStore> {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<EffectControllerComponent> CODEC;
/*     */   
/*     */   @Nonnull
/*     */   public static ComponentType<EntityStore, EffectControllerComponent> getComponentType() {
/*  44 */     return EntityModule.get().getEffectControllerComponentType();
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
/*     */   static {
/*  58 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(EffectControllerComponent.class, EffectControllerComponent::new).append(new KeyedCodec("ActiveEntityEffects", (Codec)new ArrayCodec((Codec)ActiveEntityEffect.CODEC, x$0 -> new ActiveEntityEffect[x$0])), EffectControllerComponent::addActiveEntityEffects, EffectControllerComponent::getAllActiveEntityEffects).add()).build();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*  63 */   protected final Int2ObjectMap<ActiveEntityEffect> activeEffects = (Int2ObjectMap<ActiveEntityEffect>)new Int2ObjectOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected int[] cachedActiveEffectIndexes;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  75 */   protected ObjectList<EntityEffectUpdate> changes = (ObjectList<EntityEffectUpdate>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isNetworkOutdated;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*  86 */   protected Model originalModel = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int activeModelChangeEntityEffectIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isInvulnerable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EffectControllerComponent(@Nonnull EffectControllerComponent effectControllerComponent) {
/* 114 */     this.originalModel = effectControllerComponent.originalModel;
/* 115 */     this.activeModelChangeEntityEffectIndex = effectControllerComponent.activeModelChangeEntityEffectIndex;
/* 116 */     this.changes.addAll(effectControllerComponent.changes);
/*     */     
/* 118 */     ActiveEntityEffect[] activeEntityEffects = effectControllerComponent.getAllActiveEntityEffects();
/* 119 */     if (activeEntityEffects != null) {
/* 120 */       effectControllerComponent.addActiveEntityEffects(activeEntityEffects);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInvulnerable() {
/* 128 */     return this.isInvulnerable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInvulnerable(boolean invulnerable) {
/* 137 */     this.isInvulnerable = invulnerable;
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
/*     */   public boolean addEffect(@Nonnull Ref<EntityStore> ownerRef, @Nonnull EntityEffect entityEffect, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 149 */     int entityEffectIndex = EntityEffect.getAssetMap().getIndex(entityEffect.getId());
/* 150 */     if (entityEffectIndex == Integer.MIN_VALUE) {
/* 151 */       return false;
/*     */     }
/*     */     
/* 154 */     return addEffect(ownerRef, entityEffectIndex, entityEffect, componentAccessor);
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
/*     */   public boolean addEffect(@Nonnull Ref<EntityStore> ownerRef, int entityEffectIndex, @Nonnull EntityEffect entityEffect, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 167 */     boolean infinite = entityEffect.isInfinite();
/* 168 */     float duration = entityEffect.getDuration();
/* 169 */     OverlapBehavior overlapBehavior = entityEffect.getOverlapBehavior();
/*     */     
/* 171 */     if (infinite) {
/* 172 */       return addInfiniteEffect(ownerRef, entityEffectIndex, entityEffect, componentAccessor);
/*     */     }
/* 174 */     return addEffect(ownerRef, entityEffectIndex, entityEffect, duration, overlapBehavior, componentAccessor);
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
/*     */   public boolean addEffect(@Nonnull Ref<EntityStore> ownerRef, @Nonnull EntityEffect entityEffect, float duration, @Nonnull OverlapBehavior overlapBehavior, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 188 */     int entityEffectIndex = EntityEffect.getAssetMap().getIndex(entityEffect.getId());
/* 189 */     if (entityEffectIndex == Integer.MIN_VALUE) {
/* 190 */       return false;
/*     */     }
/*     */     
/* 193 */     return addEffect(ownerRef, entityEffectIndex, entityEffect, duration, overlapBehavior, componentAccessor);
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
/*     */   public boolean addEffect(@Nonnull Ref<EntityStore> ownerRef, int entityEffectIndex, @Nonnull EntityEffect entityEffect, float duration, @Nonnull OverlapBehavior overlapBehavior, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 208 */     if (!LivingEntityEffectSystem.canApplyEffect(ownerRef, entityEffect, componentAccessor)) return false;
/*     */     
/* 210 */     ActiveEntityEffect currentActiveEntityEffectEntry = (ActiveEntityEffect)this.activeEffects.get(entityEffectIndex);
/* 211 */     if (currentActiveEntityEffectEntry != null) {
/* 212 */       if (currentActiveEntityEffectEntry.isInfinite()) return true;
/*     */       
/* 214 */       switch (overlapBehavior) {
/*     */         case COMPLETE:
/* 216 */           currentActiveEntityEffectEntry.remainingDuration += duration;
/* 217 */           addChange(new EntityEffectUpdate(EffectOp.Add, entityEffectIndex, currentActiveEntityEffectEntry.remainingDuration, false, currentActiveEntityEffectEntry.debuff, currentActiveEntityEffectEntry.statusEffectIcon));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 227 */           return true;
/*     */         case INFINITE:
/* 229 */           return true;
/*     */       } 
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
/*     */     } 
/* 243 */     ActiveEntityEffect activeEntityEffectEntry = new ActiveEntityEffect(entityEffect.getId(), entityEffectIndex, duration, entityEffect.isDebuff(), entityEffect.getStatusEffectIcon(), entityEffect.isInvulnerable());
/*     */ 
/*     */     
/* 246 */     this.activeEffects.put(entityEffectIndex, activeEntityEffectEntry);
/*     */     
/* 248 */     Entity ownerEntity = EntityUtils.getEntity(ownerRef, componentAccessor);
/* 249 */     if (ownerEntity instanceof LivingEntity) { LivingEntity ownerLivingEntity = (LivingEntity)ownerEntity;
/* 250 */       ownerLivingEntity.getStatModifiersManager().setRecalculate(true); }
/*     */ 
/*     */     
/* 253 */     setModelChange(ownerRef, entityEffect, entityEffectIndex, componentAccessor);
/*     */     
/* 255 */     addChange(new EntityEffectUpdate(EffectOp.Add, entityEffectIndex, activeEntityEffectEntry.remainingDuration, false, activeEntityEffectEntry.debuff, activeEntityEffectEntry.statusEffectIcon));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 265 */     invalidateCache();
/*     */     
/* 267 */     return true;
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
/*     */   public boolean addInfiniteEffect(@Nonnull Ref<EntityStore> ownerRef, int entityEffectIndex, @Nonnull EntityEffect entityEffect, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 280 */     if (!LivingEntityEffectSystem.canApplyEffect(ownerRef, entityEffect, componentAccessor)) return false;
/*     */     
/* 282 */     ActiveEntityEffect currentActiveEntityEffectEntry = (ActiveEntityEffect)this.activeEffects.get(entityEffectIndex);
/* 283 */     if (currentActiveEntityEffectEntry == null) {
/* 284 */       currentActiveEntityEffectEntry = new ActiveEntityEffect(entityEffect.getId(), entityEffectIndex, true, entityEffect.isInvulnerable());
/*     */       
/* 286 */       this.activeEffects.put(entityEffectIndex, currentActiveEntityEffectEntry);
/*     */       
/* 288 */       Entity ownerEntity = EntityUtils.getEntity(ownerRef, componentAccessor);
/* 289 */       if (ownerEntity instanceof LivingEntity) { LivingEntity ownerLivingEntity = (LivingEntity)ownerEntity;
/* 290 */         ownerLivingEntity.getStatModifiersManager().setRecalculate(true); }
/*     */       
/* 292 */       invalidateCache();
/* 293 */     } else if (!currentActiveEntityEffectEntry.isInfinite()) {
/* 294 */       currentActiveEntityEffectEntry.infinite = true;
/*     */     } 
/*     */     
/* 297 */     setModelChange(ownerRef, entityEffect, entityEffectIndex, componentAccessor);
/*     */     
/* 299 */     addChange(new EntityEffectUpdate(EffectOp.Add, entityEffectIndex, currentActiveEntityEffectEntry.remainingDuration, true, currentActiveEntityEffectEntry.debuff, currentActiveEntityEffectEntry.statusEffectIcon));
/*     */     
/* 301 */     return true;
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
/*     */   public void setModelChange(@Nonnull Ref<EntityStore> ownerRef, @Nonnull EntityEffect entityEffect, int entityEffectIndex, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 314 */     if (this.originalModel != null)
/*     */       return; 
/* 316 */     if (entityEffect.getModelChange() == null)
/*     */       return; 
/* 318 */     ModelComponent modelComponent = (ModelComponent)componentAccessor.getComponent(ownerRef, ModelComponent.getComponentType());
/* 319 */     assert modelComponent != null;
/*     */     
/* 321 */     this.originalModel = modelComponent.getModel();
/* 322 */     this.activeModelChangeEntityEffectIndex = entityEffectIndex;
/*     */     
/* 324 */     ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(entityEffect.getModelChange());
/* 325 */     Model scaledModel = Model.createRandomScaleModel(modelAsset);
/* 326 */     componentAccessor.putComponent(ownerRef, ModelComponent.getComponentType(), (Component)new ModelComponent(scaledModel));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tryResetModelChange(@Nonnull Ref<EntityStore> ownerRef, int activeEffectIndex, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 337 */     if (this.originalModel != null && this.activeModelChangeEntityEffectIndex == activeEffectIndex) {
/*     */ 
/*     */       
/* 340 */       componentAccessor.putComponent(ownerRef, ModelComponent.getComponentType(), (Component)new ModelComponent(this.originalModel));
/*     */ 
/*     */       
/* 343 */       PlayerSkinComponent playerSkinComponent = (PlayerSkinComponent)componentAccessor.getComponent(ownerRef, PlayerSkinComponent.getComponentType());
/* 344 */       if (playerSkinComponent != null) {
/* 345 */         playerSkinComponent.setNetworkOutdated();
/*     */       }
/*     */       
/* 348 */       this.originalModel = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addActiveEntityEffects(@Nonnull ActiveEntityEffect[] activeEntityEffects) {
/* 358 */     if (activeEntityEffects.length == 0)
/*     */       return; 
/* 360 */     for (ActiveEntityEffect activeEntityEffect : activeEntityEffects) {
/* 361 */       int entityEffectIndex = EntityEffect.getAssetMap().getIndex(activeEntityEffect.entityEffectId);
/*     */ 
/*     */       
/* 364 */       if (entityEffectIndex != Integer.MIN_VALUE) {
/*     */         
/* 366 */         activeEntityEffect.entityEffectIndex = entityEffectIndex;
/* 367 */         this.activeEffects.put(entityEffectIndex, activeEntityEffect);
/*     */         
/* 369 */         addChange(new EntityEffectUpdate(EffectOp.Add, entityEffectIndex, activeEntityEffect.remainingDuration, activeEntityEffect.infinite, activeEntityEffect.debuff, activeEntityEffect.statusEffectIcon));
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 380 */     invalidateCache();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeEffect(@Nonnull Ref<EntityStore> ownerRef, int entityEffectIndex, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 391 */     EntityEffect entityEffect = (EntityEffect)EntityEffect.getAssetMap().getAsset(entityEffectIndex);
/* 392 */     if (entityEffect == null) {
/* 393 */       throw new IllegalArgumentException(String.format("Unknown EntityEffect with index \"%s\"", new Object[] { Integer.valueOf(entityEffectIndex) }));
/*     */     }
/*     */     
/* 396 */     removeEffect(ownerRef, entityEffectIndex, entityEffect.getRemovalBehavior(), componentAccessor);
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
/*     */   public void removeEffect(@Nonnull Ref<EntityStore> ownerRef, int entityEffectIndex, @Nonnull RemovalBehavior removalBehavior, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 408 */     ActiveEntityEffect activeEffectEntry = (ActiveEntityEffect)this.activeEffects.get(entityEffectIndex);
/* 409 */     if (activeEffectEntry == null) {
/*     */       return;
/*     */     }
/*     */     
/* 413 */     tryResetModelChange(ownerRef, activeEffectEntry.getEntityEffectIndex(), componentAccessor);
/*     */     
/* 415 */     switch (removalBehavior) {
/*     */       case COMPLETE:
/* 417 */         this.activeEffects.remove(entityEffectIndex);
/*     */         
/* 419 */         ownerEntity = EntityUtils.getEntity(ownerRef, componentAccessor);
/* 420 */         if (ownerEntity instanceof LivingEntity) { LivingEntity ownerLivingEntity = (LivingEntity)ownerEntity;
/* 421 */           ownerLivingEntity.getStatModifiersManager().setRecalculate(true); }
/*     */         
/* 423 */         addChange(new EntityEffectUpdate(EffectOp.Remove, entityEffectIndex, 0.0F, false, false, ""));
/* 424 */         invalidateCache();
/*     */         return;
/*     */       case INFINITE:
/* 427 */         activeEffectEntry.infinite = false; break;
/* 428 */       case DURATION: activeEffectEntry.remainingDuration = 0.0F;
/*     */         break;
/*     */     } 
/* 431 */     Entity ownerEntity = EntityUtils.getEntity(ownerRef, componentAccessor);
/* 432 */     if (ownerEntity instanceof LivingEntity) { LivingEntity ownerLivingEntity = (LivingEntity)ownerEntity;
/* 433 */       ownerLivingEntity.getStatModifiersManager().setRecalculate(true); }
/*     */ 
/*     */     
/* 436 */     addChange(new EntityEffectUpdate(EffectOp.Remove, entityEffectIndex, activeEffectEntry.remainingDuration, activeEffectEntry.infinite, activeEffectEntry.debuff, activeEffectEntry.statusEffectIcon));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addChange(@Nonnull EntityEffectUpdate update) {
/* 445 */     this.isNetworkOutdated = true;
/* 446 */     this.changes.add(update);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearEffects(@Nonnull Ref<EntityStore> ownerRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 457 */     IntArraySet intArraySet = new IntArraySet(this.activeEffects.keySet());
/* 458 */     for (IntIterator<Integer> intIterator = intArraySet.iterator(); intIterator.hasNext(); ) { int effect = ((Integer)intIterator.next()).intValue();
/* 459 */       removeEffect(ownerRef, effect, componentAccessor); }
/*     */ 
/*     */     
/* 462 */     invalidateCache();
/*     */ 
/*     */     
/* 465 */     if (this.originalModel != null) {
/* 466 */       componentAccessor.putComponent(ownerRef, ModelComponent.getComponentType(), (Component)new ModelComponent(this.originalModel));
/* 467 */       this.originalModel = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidateCache() {
/* 475 */     this.cachedActiveEffectIndexes = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Int2ObjectMap<ActiveEntityEffect> getActiveEffects() {
/* 485 */     return this.activeEffects;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getActiveEffectIndexes() {
/* 494 */     if (this.cachedActiveEffectIndexes == null) {
/* 495 */       if (this.activeEffects.isEmpty()) {
/*     */         
/* 497 */         this.cachedActiveEffectIndexes = ArrayUtil.EMPTY_INT_ARRAY;
/*     */       } else {
/* 499 */         this.cachedActiveEffectIndexes = this.activeEffects.keySet().toIntArray();
/*     */       } 
/*     */     }
/*     */     
/* 503 */     return this.cachedActiveEffectIndexes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean consumeNetworkOutdated() {
/* 512 */     boolean temp = this.isNetworkOutdated;
/* 513 */     this.isNetworkOutdated = false;
/* 514 */     return temp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EntityEffectUpdate[] consumeChanges() {
/* 524 */     return (EntityEffectUpdate[])this.changes.toArray(x$0 -> new EntityEffectUpdate[x$0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearChanges() {
/* 531 */     this.changes.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EntityEffectUpdate[] createInitUpdates() {
/* 539 */     EntityEffectUpdate[] changeArray = new EntityEffectUpdate[this.activeEffects.size()];
/* 540 */     int index = 0;
/* 541 */     ObjectIterator<Int2ObjectMap.Entry<ActiveEntityEffect>> iterator = Int2ObjectMaps.fastIterator(this.activeEffects);
/* 542 */     while (iterator.hasNext()) {
/* 543 */       Int2ObjectMap.Entry<ActiveEntityEffect> entry = (Int2ObjectMap.Entry<ActiveEntityEffect>)iterator.next();
/* 544 */       ActiveEntityEffect activeEntityEffectEntry = (ActiveEntityEffect)entry.getValue();
/* 545 */       changeArray[index++] = new EntityEffectUpdate(EffectOp.Add, entry
/*     */           
/* 547 */           .getIntKey(), activeEntityEffectEntry.remainingDuration, activeEntityEffectEntry.infinite, activeEntityEffectEntry.debuff, activeEntityEffectEntry.statusEffectIcon);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 554 */     return changeArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ActiveEntityEffect[] getAllActiveEntityEffects() {
/* 564 */     if (this.activeEffects.isEmpty()) return null;
/*     */     
/* 566 */     ActiveEntityEffect[] activeEntityEffects = new ActiveEntityEffect[this.activeEffects.size()];
/* 567 */     int index = 0;
/*     */     
/* 569 */     for (ObjectIterator<ActiveEntityEffect> objectIterator = this.activeEffects.values().iterator(); objectIterator.hasNext(); ) { ActiveEntityEffect entityEffect = objectIterator.next();
/* 570 */       activeEntityEffects[index] = entityEffect;
/* 571 */       index++; }
/*     */ 
/*     */     
/* 574 */     return activeEntityEffects;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 580 */     return "EntityEffectController{, activeEffects=" + String.valueOf(this.activeEffects) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EffectControllerComponent clone() {
/* 588 */     return new EffectControllerComponent(this);
/*     */   }
/*     */   
/*     */   public EffectControllerComponent() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\effect\EffectControllerComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */