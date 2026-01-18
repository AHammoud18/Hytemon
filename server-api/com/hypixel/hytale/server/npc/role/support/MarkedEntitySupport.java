/*     */ package com.hypixel.hytale.server.npc.role.support;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.entity.group.EntityGroup;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.flock.FlockPlugin;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class MarkedEntitySupport
/*     */ {
/*     */   @Nullable
/*  23 */   protected static final ComponentType<EntityStore, NPCEntity> NPC_COMPONENT_TYPE = NPCEntity.getComponentType();
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
/*  40 */   protected int targetSlotToIgnoreForAvoidance = Integer.MIN_VALUE;
/*     */   
/*     */   @Nonnull
/*  43 */   private static final Object2IntMap<String> EMPTY_TARGET_SLOT_MAP = (Object2IntMap<String>)new Object2IntOpenHashMap(0); public static final String DEFAULT_TARGET_SLOT = "LockedTarget"; protected final NPCEntity parent; protected Object2IntMap<String> targetSlotMappings; static {
/*  44 */     EMPTY_TARGET_SLOT_MAP.defaultReturnValue(-2147483648);
/*     */   } @Nullable
/*     */   protected Int2ObjectMap<String> slotToNameMap; protected Ref<EntityStore>[] entityTargets; @Nullable
/*     */   protected Vector3d[] storedPositions; protected int defaultTargetSlot; public MarkedEntitySupport(NPCEntity parent) {
/*  48 */     this.parent = parent;
/*     */   }
/*     */   
/*     */   public Ref<EntityStore>[] getEntityTargets() {
/*  52 */     return this.entityTargets;
/*     */   }
/*     */ 
/*     */   
/*     */   public void postRoleBuilder(@Nonnull BuilderSupport support) {
/*  57 */     Object2IntMap<String> slotMappings = support.getTargetSlotMappings();
/*  58 */     if (slotMappings != null) {
/*  59 */       this.targetSlotMappings = slotMappings;
/*  60 */       this.slotToNameMap = support.getTargetSlotToNameMap();
/*  61 */       this.entityTargets = (Ref<EntityStore>[])new Ref[this.targetSlotMappings.size()];
/*  62 */       this.defaultTargetSlot = this.targetSlotMappings.getInt("LockedTarget");
/*     */     } else {
/*  64 */       this.targetSlotMappings = EMPTY_TARGET_SLOT_MAP;
/*  65 */       this.slotToNameMap = null;
/*  66 */       this.entityTargets = (Ref<EntityStore>[])Ref.EMPTY_ARRAY;
/*  67 */       this.defaultTargetSlot = Integer.MIN_VALUE;
/*     */     } 
/*  69 */     this.storedPositions = support.allocatePositionSlots();
/*     */   }
/*     */   
/*     */   public void clearMarkedEntity(int targetSlot) {
/*  73 */     this.entityTargets[targetSlot] = null;
/*     */   }
/*     */   
/*     */   public void setMarkedEntity(String targetSlot, Ref<EntityStore> target) {
/*  77 */     int slot = this.targetSlotMappings.getInt(targetSlot);
/*  78 */     if (slot >= 0) {
/*  79 */       setMarkedEntity(slot, target);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setMarkedEntity(int targetSlot, @Nullable Ref<EntityStore> target) {
/*  84 */     if (target == null || !target.isValid()) {
/*  85 */       clearMarkedEntity(targetSlot);
/*     */       return;
/*     */     } 
/*  88 */     this.entityTargets[targetSlot] = target;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getMarkedEntityRef(String targetSlot) {
/*  93 */     int slot = this.targetSlotMappings.getInt(targetSlot);
/*  94 */     if (slot >= 0) return getMarkedEntityRef(slot);
/*     */     
/*  96 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getMarkedEntityRef(int targetSlot) {
/* 101 */     Ref<EntityStore> ref = this.entityTargets[targetSlot];
/* 102 */     return (ref != null && ref.isValid()) ? ref : null;
/*     */   }
/*     */   
/*     */   public int getMarkedEntitySlotCount() {
/* 106 */     return this.entityTargets.length;
/*     */   }
/*     */   
/*     */   public Vector3d getStoredPosition(int slot) {
/* 110 */     return this.storedPositions[slot];
/*     */   }
/*     */   
/*     */   public boolean hasMarkedEntity(@Nonnull Ref<EntityStore> entityReference, int targetSlot) {
/* 114 */     return entityReference.equals(getMarkedEntityRef(targetSlot));
/*     */   }
/*     */   
/*     */   public boolean hasMarkedEntityInSlot(String targetSlot) {
/* 118 */     int slot = this.targetSlotMappings.getInt(targetSlot);
/* 119 */     if (slot < 0) return false;
/*     */     
/* 121 */     return hasMarkedEntityInSlot(slot);
/*     */   }
/*     */   public boolean hasMarkedEntityInSlot(int targetSlot) {
/* 124 */     return (getMarkedEntityRef(targetSlot) != null);
/*     */   }
/*     */   
/*     */   public void flockSetTarget(@Nonnull String targetSlot, @Nullable Ref<EntityStore> targetRef, @Nonnull Store<EntityStore> store) {
/* 128 */     Ref<EntityStore> parentRef = this.parent.getReference();
/* 129 */     Ref<EntityStore> flockReference = FlockPlugin.getFlockReference(parentRef, (ComponentAccessor)store);
/* 130 */     if (flockReference != null)
/* 131 */       ((EntityGroup)store.getComponent(flockReference, EntityGroup.getComponentType()))
/* 132 */         .forEachMember((member, sender, _target, _targetSlot) -> {
/*     */             NPCEntity npcComponent = (NPCEntity)member.getStore().getComponent(member, NPC_COMPONENT_TYPE);
/*     */             if (npcComponent == null)
/*     */               return; 
/*     */             npcComponent.onFlockSetTarget(_targetSlot, _target);
/*     */           }parentRef, targetRef, targetSlot); 
/*     */   }
/*     */   
/*     */   public void setTargetSlotToIgnoreForAvoidance(int targetSlotToIgnoreForAvoidance) {
/* 141 */     this.targetSlotToIgnoreForAvoidance = targetSlotToIgnoreForAvoidance;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getTargetReferenceToIgnoreForAvoidance() {
/* 146 */     int slot = (this.targetSlotToIgnoreForAvoidance >= 0) ? this.targetSlotToIgnoreForAvoidance : this.defaultTargetSlot;
/* 147 */     if (slot < 0) return null;
/*     */     
/* 149 */     return getMarkedEntityRef(slot);
/*     */   }
/*     */   
/*     */   public String getSlotName(int slot) {
/* 153 */     return (String)this.slotToNameMap.get(slot);
/*     */   }
/*     */   
/*     */   public void unloaded() {
/* 157 */     for (int i = 0; i < this.entityTargets.length; i++)
/* 158 */       clearMarkedEntity(i); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\role\support\MarkedEntitySupport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */