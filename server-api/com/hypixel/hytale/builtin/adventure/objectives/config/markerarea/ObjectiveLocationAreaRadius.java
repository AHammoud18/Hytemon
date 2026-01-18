/*     */ package com.hypixel.hytale.builtin.adventure.objectives.config.markerarea;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.VectorSphereUtil;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*     */ public class ObjectiveLocationAreaRadius
/*     */   extends ObjectiveLocationMarkerArea
/*     */ {
/*     */   public static final BuilderCodec<ObjectiveLocationAreaRadius> CODEC;
/*     */   public static final int DEFAULT_ENTRY_RADIUS = 5;
/*     */   public static final int DEFAULT_EXIT_RADIUS = 10;
/*     */   protected int entryArea;
/*     */   protected int exitArea;
/*     */   
/*     */   static {
/*  41 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ObjectiveLocationAreaRadius.class, ObjectiveLocationAreaRadius::new).append(new KeyedCodec("EntryRadius", (Codec)Codec.INTEGER), (objectiveLocationAreaRadius, integer) -> objectiveLocationAreaRadius.entryArea = integer.intValue(), objectiveLocationAreaRadius -> Integer.valueOf(objectiveLocationAreaRadius.entryArea)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).append(new KeyedCodec("ExitRadius", (Codec)Codec.INTEGER), (objectiveLocationAreaRadius, integer) -> objectiveLocationAreaRadius.exitArea = integer.intValue(), objectiveLocationAreaRadius -> Integer.valueOf(objectiveLocationAreaRadius.exitArea)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).validator((objectiveLocationAreaRadius, validationResults) -> { if (objectiveLocationAreaRadius.exitArea < objectiveLocationAreaRadius.entryArea) validationResults.fail("ExitRadius needs to be greater than EntryRadius");  })).afterDecode(ObjectiveLocationAreaRadius::computeAreaBoxes)).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectiveLocationAreaRadius(int entryRadius, int exitRadius) {
/*  50 */     this.entryArea = entryRadius;
/*  51 */     this.exitArea = exitRadius;
/*  52 */     computeAreaBoxes();
/*     */   }
/*     */   
/*     */   protected ObjectiveLocationAreaRadius() {
/*  56 */     this(5, 10);
/*     */   }
/*     */   
/*     */   public int getEntryArea() {
/*  60 */     return this.entryArea;
/*     */   }
/*     */   
/*     */   public int getExitArea() {
/*  64 */     return this.exitArea;
/*     */   }
/*     */ 
/*     */   
/*     */   public void getPlayersInEntryArea(@Nonnull SpatialResource<Ref<EntityStore>, EntityStore> spatialComponent, @Nonnull List<Ref<EntityStore>> results, @Nonnull Vector3d markerPosition) {
/*  69 */     getPlayersInArea(spatialComponent, results, markerPosition, this.entryArea);
/*     */   }
/*     */ 
/*     */   
/*     */   public void getPlayersInExitArea(@Nonnull SpatialResource<Ref<EntityStore>, EntityStore> spatialComponent, @Nonnull List<Ref<EntityStore>> results, @Nonnull Vector3d markerPosition) {
/*  74 */     getPlayersInArea(spatialComponent, results, markerPosition, this.exitArea);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPlayerInExitArea(@Nonnull SpatialResource<Ref<EntityStore>, EntityStore> spatialComponent, @Nonnull ComponentType<EntityStore, PlayerRef> playerRefComponentType, @Nonnull Vector3d markerPosition, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  79 */     Ref<EntityStore> reference = (Ref<EntityStore>)spatialComponent.getSpatialStructure().closest(markerPosition);
/*  80 */     if (reference == null) return false;
/*     */     
/*  82 */     TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(reference, TransformComponent.getComponentType());
/*  83 */     assert transformComponent != null;
/*  84 */     return VectorSphereUtil.isInside(markerPosition.x, markerPosition.y, markerPosition.z, this.exitArea, this.exitArea, this.exitArea, transformComponent.getPosition());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPlayerInEntryArea(@Nonnull Vector3d playerPosition, @Nonnull Vector3d markerPosition) {
/*  89 */     return VectorSphereUtil.isInside(markerPosition.x, markerPosition.y, markerPosition.z, this.entryArea, playerPosition);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void computeAreaBoxes() {
/*  94 */     this.entryAreaBox = new Box(-this.entryArea, -this.entryArea, -this.entryArea, this.entryArea, this.entryArea, this.entryArea);
/*     */ 
/*     */     
/*  97 */     this.exitAreaBox = new Box(-this.exitArea, -this.exitArea, -this.exitArea, this.exitArea, this.exitArea, this.exitArea);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void getPlayersInArea(@Nonnull SpatialResource<Ref<EntityStore>, EntityStore> spatialComponent, @Nonnull List<Ref<EntityStore>> results, @Nonnull Vector3d markerPosition, int radius) {
/* 106 */     spatialComponent.getSpatialStructure().collect(markerPosition, radius, results);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 112 */     return "ObjectiveLocationAreaRadius{} " + super.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\markerarea\ObjectiveLocationAreaRadius.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */