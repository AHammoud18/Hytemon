/*     */ package com.hypixel.hytale.server.spawning.interactions;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.math.random.RandomExtra;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawnmarker.config.SpawnMarker;
/*     */ import com.hypixel.hytale.server.spawning.spawnmarkers.SpawnMarkerEntity;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class TriggerSpawnMarkersInteraction
/*     */   extends SimpleInstantInteraction
/*     */ {
/*     */   public static final BuilderCodec<TriggerSpawnMarkersInteraction> CODEC;
/*     */   private String markerType;
/*     */   
/*     */   static {
/*  56 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(TriggerSpawnMarkersInteraction.class, TriggerSpawnMarkersInteraction::new, SimpleInstantInteraction.CODEC).appendInherited(new KeyedCodec("MarkerType", (Codec)Codec.STRING), (o, v) -> o.markerType = v, o -> o.markerType, (o, p) -> o.markerType = p.markerType).addValidator(SpawnMarker.VALIDATOR_CACHE.getValidator()).documentation("The manual spawn marker type to trigger. If omitted, will trigger all manual spawners.").add()).appendInherited(new KeyedCodec("Range", (Codec)Codec.DOUBLE), (o, v) -> o.range = v.doubleValue(), o -> Double.valueOf(o.range), (o, p) -> o.range = p.range).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).documentation("Range within which to trigger spawn markers.").add()).appendInherited(new KeyedCodec("Count", (Codec)Codec.INTEGER), (o, v) -> o.count = v.intValue(), o -> Integer.valueOf(o.count), (o, p) -> o.count = p.count).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(0))).documentation("Max number of spawn markers to activate. Set to 0 to activate all spawn markers.").add()).afterDecode(i -> i.rangeSquared = i.range * i.range)).build();
/*     */   }
/*     */ 
/*     */   
/*  60 */   private double range = 10.0D;
/*     */   
/*     */   private double rangeSquared;
/*     */   
/*     */   private int count;
/*     */   
/*     */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*  67 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*  68 */     Ref<EntityStore> self = context.getEntity();
/*  69 */     Vector3d position = ((TransformComponent)commandBuffer.getComponent(self, TransformComponent.getComponentType())).getPosition();
/*     */     
/*  71 */     SpatialResource<Ref<EntityStore>, EntityStore> spatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)commandBuffer.getResource(SpawningPlugin.get().getSpawnMarkerSpatialResource());
/*  72 */     ObjectArrayList<Ref<EntityStore>> spawners = new ObjectArrayList();
/*     */     
/*  74 */     spatialResource.getSpatialStructure().collect(position, ((int)this.range + 1), (List)spawners);
/*     */     
/*  76 */     if (this.count == 0) {
/*     */       
/*  78 */       for (int j = 0; j < spawners.size(); j++) {
/*  79 */         Ref<EntityStore> spawnMarkerRef = filterMarker((Ref<EntityStore>)spawners.get(j), position, commandBuffer);
/*  80 */         if (spawnMarkerRef != null) {
/*  81 */           SpawnMarkerEntity spawnMarkerEntityComponent = (SpawnMarkerEntity)commandBuffer.getComponent(spawnMarkerRef, SpawnMarkerEntity.getComponentType());
/*  82 */           assert spawnMarkerEntityComponent != null;
/*     */ 
/*     */           
/*  85 */           commandBuffer.run(store -> spawnMarkerEntityComponent.trigger(spawnMarkerRef, store));
/*     */         } 
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*  91 */     ObjectArrayList<Ref<EntityStore>> triggerList = new ObjectArrayList();
/*     */     
/*  93 */     RandomExtra.reservoirSample((List)spawners, (reference, _this, _commandBuffer) -> _this.filterMarker(reference, position, _commandBuffer), this.count, (List)triggerList, this, commandBuffer);
/*     */     
/*  95 */     for (int i = 0; i < triggerList.size(); i++) {
/*  96 */       Ref<EntityStore> spawnMarkerRef = (Ref<EntityStore>)triggerList.get(i);
/*     */       
/*  98 */       SpawnMarkerEntity spawnMarkerEntityComponent = (SpawnMarkerEntity)commandBuffer.getComponent(spawnMarkerRef, SpawnMarkerEntity.getComponentType());
/*  99 */       assert spawnMarkerEntityComponent != null;
/*     */ 
/*     */       
/* 102 */       commandBuffer.run(store -> spawnMarkerEntityComponent.trigger(spawnMarkerRef, store));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Ref<EntityStore> filterMarker(@Nonnull Ref<EntityStore> targetRef, @Nonnull Vector3d position, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 108 */     if (!targetRef.isValid()) return null;
/*     */     
/* 110 */     TransformComponent targetTransformComponent = (TransformComponent)commandBuffer.getComponent(targetRef, TransformComponent.getComponentType());
/* 111 */     assert targetTransformComponent != null;
/* 112 */     Vector3d targetPosition = targetTransformComponent.getPosition();
/*     */     
/* 114 */     SpawnMarkerEntity targetMarkerEntityComponent = (SpawnMarkerEntity)commandBuffer.getComponent(targetRef, SpawnMarkerEntity.getComponentType());
/*     */     
/* 116 */     return 
/*     */ 
/*     */       
/* 119 */       (targetMarkerEntityComponent != null && targetMarkerEntityComponent.isManualTrigger() && position.distanceSquaredTo(targetPosition) <= this.rangeSquared && (this.markerType == null || this.markerType.equals(targetMarkerEntityComponent.getSpawnMarkerId()))) ? targetRef : null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\interactions\TriggerSpawnMarkersInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */