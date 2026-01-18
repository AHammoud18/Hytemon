/*     */ package com.hypixel.hytale.server.npc.corecomponents.items;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.asset.type.attitude.Attitude;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.items.builders.BuilderSensorDroppedItem;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.EntityPositionProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.util.InventoryHelper;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class SensorDroppedItem extends SensorBase {
/*  27 */   protected static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*     */   
/*     */   @Nullable
/*     */   protected final List<String> items;
/*     */   
/*     */   @Nonnull
/*     */   protected final EnumSet<Attitude> attitudes;
/*     */   
/*     */   protected final double range;
/*     */   protected final float viewCone;
/*     */   protected final boolean hasLineOfSight;
/*     */   protected float heading;
/*  39 */   protected final EntityPositionProvider positionProvider = new EntityPositionProvider();
/*     */   
/*     */   public SensorDroppedItem(@Nonnull BuilderSensorDroppedItem builder, @Nonnull BuilderSupport support) {
/*  42 */     super((BuilderSensorBase)builder);
/*  43 */     String[] itemArray = builder.getItems(support);
/*  44 */     this.items = (itemArray != null) ? List.<String>of(itemArray) : null;
/*  45 */     this.attitudes = builder.getAttitudes(support);
/*  46 */     this.range = builder.getRange(support);
/*  47 */     this.viewCone = builder.getViewSectorRadians(support);
/*  48 */     this.hasLineOfSight = builder.getHasLineOfSight(support);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerWithSupport(@Nonnull Role role) {
/*  53 */     role.getPositionCache().requireDroppedItemDistance(this.range);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/*  58 */     if (!super.matches(ref, role, dt, store)) {
/*  59 */       this.positionProvider.clear();
/*  60 */       return false;
/*     */     } 
/*     */     
/*  63 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/*  64 */     assert headRotationComponent != null;
/*     */     
/*  66 */     this.heading = headRotationComponent.getRotation().getYaw();
/*     */     
/*  68 */     Ref<EntityStore> droppedItem = role.getPositionCache().getClosestDroppedItemInRange(ref, 0.0D, this.range, (sensorDroppedItem, itemRef, role1, componentAccessor) -> sensorDroppedItem.filterItem(ref, itemRef, role1, componentAccessor), role, this, (ComponentAccessor)store);
/*     */     
/*  70 */     if (droppedItem == null) {
/*  71 */       this.positionProvider.clear();
/*  72 */       return false;
/*     */     } 
/*     */     
/*  75 */     this.positionProvider.setTarget(droppedItem, (ComponentAccessor)store);
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public InfoProvider getSensorInfo() {
/*  81 */     return (InfoProvider)this.positionProvider;
/*     */   }
/*     */   
/*     */   protected boolean filterItem(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> itemRef, @Nonnull Role role, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  85 */     if (!itemRef.isValid()) {
/*  86 */       return false;
/*     */     }
/*     */     
/*  89 */     if (this.viewCone > 0.0F) {
/*  90 */       TransformComponent selfTransformComponent = (TransformComponent)componentAccessor.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/*  91 */       assert selfTransformComponent != null;
/*  92 */       Vector3d selfPosition = selfTransformComponent.getPosition();
/*     */       
/*  94 */       TransformComponent itemTransformComponent = (TransformComponent)componentAccessor.getComponent(itemRef, TRANSFORM_COMPONENT_TYPE);
/*  95 */       assert itemTransformComponent != null;
/*  96 */       Vector3d itemPosition = itemTransformComponent.getPosition();
/*     */       
/*  98 */       if (!NPCPhysicsMath.inViewSector(selfPosition.getX(), selfPosition.getZ(), this.heading, this.viewCone, itemPosition.getX(), itemPosition.getZ())) {
/*  99 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 103 */     if (this.hasLineOfSight && 
/* 104 */       !role.getPositionCache().hasLineOfSight(ref, itemRef, componentAccessor)) {
/* 105 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 109 */     if (this.items == null && this.attitudes.isEmpty()) return true; 
/* 110 */     ItemComponent itemComponent = (ItemComponent)componentAccessor.getComponent(itemRef, ItemComponent.getComponentType());
/* 111 */     assert itemComponent != null;
/*     */     
/* 113 */     ItemStack itemStack = itemComponent.getItemStack();
/* 114 */     if (InventoryHelper.matchesItem(this.items, itemStack)) {
/* 115 */       return true;
/*     */     }
/*     */     
/* 118 */     Attitude attitude = role.getWorldSupport().getItemAttitude(itemStack);
/* 119 */     return (attitude != null && this.attitudes.contains(attitude));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\items\SensorDroppedItem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */