/*     */ package com.hypixel.hytale.server.npc.corecomponents.items;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.random.RandomExtra;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.entity.ItemUtils;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.item.ItemModule;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.ActionWithDelay;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionWithDelay;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.items.builders.BuilderActionDropItem;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.util.AimingHelper;
/*     */ import com.hypixel.hytale.server.npc.util.InventoryHelper;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class ActionDropItem
/*     */   extends ActionWithDelay {
/*     */   protected final String item;
/*     */   protected final String dropList;
/*     */   protected final float dropSectorStart;
/*     */   protected final float dropSectorEnd;
/*     */   protected final double minDistance;
/*     */   protected final double maxDistance;
/*     */   protected final boolean highPitch;
/*  36 */   protected final float[] pitch = new float[2];
/*  37 */   protected final Vector3d dropDirection = new Vector3d();
/*     */   
/*     */   protected float throwSpeed;
/*     */   
/*     */   public ActionDropItem(@Nonnull BuilderActionDropItem builder, @Nonnull BuilderSupport support) {
/*  42 */     super((BuilderActionWithDelay)builder, support);
/*  43 */     this.item = builder.getItem(support);
/*  44 */     this.dropList = builder.getDropList(support);
/*  45 */     double[] distance = builder.getDistance();
/*  46 */     this.minDistance = distance[0];
/*  47 */     this.maxDistance = distance[1];
/*  48 */     this.throwSpeed = builder.getThrowSpeed();
/*  49 */     double[] dropSector = builder.getDropSectorRadians();
/*  50 */     this.dropSectorStart = 0.017453292F * (float)dropSector[0];
/*  51 */     float end = 0.017453292F * (float)dropSector[1];
/*  52 */     if (this.dropSectorStart > end) end += 6.2831855F; 
/*  53 */     this.dropSectorEnd = end;
/*  54 */     this.highPitch = builder.isHighPitch();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/*  59 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && !isDelaying());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/*  64 */     super.execute(ref, role, sensorInfo, dt, store);
/*     */     
/*  66 */     prepareDelay();
/*  67 */     startDelay(role.getEntitySupport());
/*     */     
/*  69 */     ModelComponent modelComponent = (ModelComponent)store.getComponent(ref, ModelComponent.getComponentType());
/*  70 */     float eyeHeight = (modelComponent != null) ? modelComponent.getModel().getEyeHeight(ref, (ComponentAccessor)store) : 0.0F;
/*     */     
/*  72 */     float height = -eyeHeight;
/*     */     
/*  74 */     if (this.item == null) {
/*  75 */       ItemModule itemModule = ItemModule.get();
/*  76 */       if (itemModule.isEnabled()) {
/*  77 */         List<ItemStack> randomItemsToDrop = itemModule.getRandomItemDrops(this.dropList);
/*  78 */         for (ItemStack randomItem : randomItemsToDrop) {
/*  79 */           newDirection(ref, pickDistance(), height, (ComponentAccessor<EntityStore>)store);
/*  80 */           ItemUtils.throwItem(ref, (ComponentAccessor)store, randomItem, this.dropDirection, this.throwSpeed);
/*     */         } 
/*     */       } 
/*  83 */       return true;
/*     */     } 
/*     */     
/*  86 */     newDirection(ref, pickDistance(), height, (ComponentAccessor<EntityStore>)store);
/*     */     
/*  88 */     ItemStack drop = InventoryHelper.createItem(this.item);
/*  89 */     if (drop != null) {
/*  90 */       ItemUtils.throwItem(ref, (ComponentAccessor)store, drop, this.dropDirection, this.throwSpeed);
/*     */     }
/*  92 */     return true;
/*     */   }
/*     */   
/*     */   protected double pickDistance() {
/*  96 */     return RandomExtra.randomRange(this.minDistance, this.maxDistance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void newDirection(@Nonnull Ref<EntityStore> ref, double distance, double height, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*     */     Vector3d direction;
/* 105 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 106 */     assert transformComponent != null;
/*     */     
/* 108 */     HeadRotation headRotationComponent = (HeadRotation)componentAccessor.getComponent(ref, HeadRotation.getComponentType());
/* 109 */     if (headRotationComponent != null) {
/* 110 */       direction = headRotationComponent.getDirection();
/*     */     } else {
/* 112 */       direction = transformComponent.getRotation().toVector3d();
/*     */     } 
/*     */     
/* 115 */     this.dropDirection.assign(direction);
/* 116 */     this.dropDirection.rotateY(RandomExtra.randomRange(this.dropSectorStart, this.dropSectorEnd));
/* 117 */     if (!AimingHelper.computePitch(distance, height, this.throwSpeed, 32.0D, this.pitch))
/*     */     {
/* 119 */       throw new IllegalStateException(String.format("Error in computing pitch with distance %s, height %s, and speed %s that was not caught in validation", new Object[] {
/* 120 */               Double.valueOf(distance), Double.valueOf(height), Float.valueOf(this.throwSpeed)
/*     */             }));
/*     */     }
/* 123 */     float heading = PhysicsMath.headingFromDirection(this.dropDirection.x, this.dropDirection.z);
/* 124 */     PhysicsMath.vectorFromAngles(heading, this.highPitch ? this.pitch[1] : this.pitch[0], this.dropDirection).normalize();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\items\ActionDropItem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */