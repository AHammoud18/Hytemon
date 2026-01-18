/*     */ package com.hypixel.hytale.builtin.adventure.objectives.markers.objectivelocation;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.ObjectiveLocationMarkerAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.markerarea.ObjectiveLocationMarkerArea;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.triggercondition.ObjectiveLocationTriggerCondition;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.protocol.packets.assets.UntrackObjective;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ObjectiveLocationMarker implements Component<EntityStore> {
/*     */   public static final BuilderCodec<ObjectiveLocationMarker> CODEC;
/*     */   protected String objectiveLocationMarkerId;
/*     */   protected UUID activeObjectiveUUID;
/*     */   protected ObjectiveLocationMarkerArea area;
/*     */   protected int[] environmentIndexes;
/*     */   protected ObjectiveLocationTriggerCondition[] triggerConditions;
/*     */   @Nullable
/*     */   private Objective activeObjective;
/*     */   private UntrackObjective untrackPacket;
/*     */   
/*     */   static {
/*  34 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ObjectiveLocationMarker.class, ObjectiveLocationMarker::new).append(new KeyedCodec("ObjectiveLocationMarkerId", (Codec)Codec.STRING), (objectiveLocationMarkerEntity, s) -> objectiveLocationMarkerEntity.objectiveLocationMarkerId = s, objectiveLocationMarkerEntity -> objectiveLocationMarkerEntity.objectiveLocationMarkerId).addValidator(Validators.nonNull()).addValidator(ObjectiveLocationMarkerAsset.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("ActiveObjectiveUUID", (Codec)Codec.UUID_BINARY), (objectiveLocationMarkerEntity, uuid) -> objectiveLocationMarkerEntity.activeObjectiveUUID = uuid, objectiveLocationMarkerEntity -> objectiveLocationMarkerEntity.activeObjectiveUUID).add()).build();
/*     */   }
/*     */   public static ComponentType<EntityStore, ObjectiveLocationMarker> getComponentType() {
/*  37 */     return ObjectivePlugin.get().getObjectiveLocationMarkerComponentType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectiveLocationMarker() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectiveLocationMarker(String objectiveLocationMarkerId) {
/*  54 */     this.objectiveLocationMarkerId = objectiveLocationMarkerId;
/*     */   }
/*     */   
/*     */   public void setObjectiveLocationMarkerId(String objectiveLocationMarkerId) {
/*  58 */     this.objectiveLocationMarkerId = objectiveLocationMarkerId;
/*     */   }
/*     */   
/*     */   public void setActiveObjectiveUUID(UUID activeObjectiveUUID) {
/*  62 */     this.activeObjectiveUUID = activeObjectiveUUID;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Objective getActiveObjective() {
/*  67 */     return this.activeObjective;
/*     */   }
/*     */   
/*     */   public void setActiveObjective(Objective activeObjective) {
/*  71 */     this.activeObjective = activeObjective;
/*     */   }
/*     */   
/*     */   public String getObjectiveLocationMarkerId() {
/*  75 */     return this.objectiveLocationMarkerId;
/*     */   }
/*     */   
/*     */   public UntrackObjective getUntrackPacket() {
/*  79 */     return this.untrackPacket;
/*     */   }
/*     */   
/*     */   public void setUntrackPacket(@Nonnull UntrackObjective untrackPacket) {
/*  83 */     this.untrackPacket = untrackPacket;
/*     */   }
/*     */   
/*     */   public ObjectiveLocationMarkerArea getArea() {
/*  87 */     return this.area;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateLocationMarkerValues(@Nonnull ObjectiveLocationMarkerAsset objectiveLocationMarkerAsset, float yaw, @Nonnull Store<EntityStore> store) {
/*  92 */     if (this.activeObjective != null && !this.activeObjective.getObjectiveId().equals(objectiveLocationMarkerAsset.getObjectiveTypeSetup().getObjectiveIdToStart())) {
/*  93 */       ObjectivePlugin.get().cancelObjective(this.activeObjectiveUUID, store);
/*  94 */       this.activeObjective = null;
/*     */     } 
/*     */     
/*  97 */     this.environmentIndexes = objectiveLocationMarkerAsset.getEnvironmentIndexes();
/*  98 */     this.area = objectiveLocationMarkerAsset.getArea().getRotatedArea(yaw, 0.0F);
/*  99 */     this.triggerConditions = objectiveLocationMarkerAsset.getTriggerConditions();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<EntityStore> clone() {
/* 106 */     ObjectiveLocationMarker marker = new ObjectiveLocationMarker(this.objectiveLocationMarkerId);
/* 107 */     marker.activeObjectiveUUID = this.activeObjectiveUUID;
/* 108 */     marker.area = this.area;
/* 109 */     marker.environmentIndexes = this.environmentIndexes;
/* 110 */     marker.triggerConditions = this.triggerConditions;
/* 111 */     marker.activeObjective = this.activeObjective;
/* 112 */     marker.untrackPacket = this.untrackPacket;
/* 113 */     return marker;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\markers\objectivelocation\ObjectiveLocationMarker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */