/*     */ package com.hypixel.hytale.builtin.adventure.objectives.config.markerarea;
/*     */ 
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
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
/*     */ 
/*     */ public class ObjectiveLocationAreaBox
/*     */   extends ObjectiveLocationMarkerArea
/*     */ {
/*     */   public static final BuilderCodec<ObjectiveLocationAreaBox> CODEC;
/*     */   
/*     */   static {
/*  35 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ObjectiveLocationAreaBox.class, ObjectiveLocationAreaBox::new).append(new KeyedCodec("EntryBox", Box.CODEC), (objectiveLocationAreaBox, box) -> objectiveLocationAreaBox.entryArea = box, objectiveLocationAreaBox -> objectiveLocationAreaBox.entryArea).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("ExitBox", Box.CODEC), (objectiveLocationAreaBox, box) -> objectiveLocationAreaBox.exitArea = box, objectiveLocationAreaBox -> objectiveLocationAreaBox.exitArea).addValidator(Validators.nonNull()).add()).afterDecode(ObjectiveLocationAreaBox::computeAreaBoxes)).build();
/*     */   }
/*  37 */   private static final Box DEFAULT_ENTRY_BOX = new Box(-5.0D, -5.0D, -5.0D, 5.0D, 5.0D, 5.0D);
/*  38 */   private static final Box DEFAULT_EXIT_BOX = new Box(-10.0D, -10.0D, -10.0D, 10.0D, 10.0D, 10.0D);
/*     */   
/*     */   private Box entryArea;
/*     */   private Box exitArea;
/*     */   
/*     */   public ObjectiveLocationAreaBox(Box entryBox, Box exitBox) {
/*  44 */     this.entryArea = entryBox;
/*  45 */     this.exitArea = exitBox;
/*  46 */     computeAreaBoxes();
/*     */   }
/*     */   
/*     */   protected ObjectiveLocationAreaBox() {
/*  50 */     this(DEFAULT_ENTRY_BOX, DEFAULT_EXIT_BOX);
/*     */   }
/*     */   
/*     */   public Box getEntryArea() {
/*  54 */     return this.entryArea;
/*     */   }
/*     */   
/*     */   public Box getExitArea() {
/*  58 */     return this.exitArea;
/*     */   }
/*     */ 
/*     */   
/*     */   public void getPlayersInEntryArea(@Nonnull SpatialResource<Ref<EntityStore>, EntityStore> spatialComponent, @Nonnull List<Ref<EntityStore>> results, @Nonnull Vector3d markerPosition) {
/*  63 */     getPlayersInArea(spatialComponent, results, markerPosition, this.entryArea);
/*     */   }
/*     */ 
/*     */   
/*     */   public void getPlayersInExitArea(@Nonnull SpatialResource<Ref<EntityStore>, EntityStore> spatialComponent, @Nonnull List<Ref<EntityStore>> results, @Nonnull Vector3d markerPosition) {
/*  68 */     getPlayersInArea(spatialComponent, results, markerPosition, this.exitArea);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPlayerInExitArea(@Nonnull SpatialResource<Ref<EntityStore>, EntityStore> spatialComponent, @Nonnull ComponentType<EntityStore, PlayerRef> playerRefComponentType, @Nonnull Vector3d markerPosition, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  73 */     Ref<EntityStore> reference = (Ref<EntityStore>)spatialComponent.getSpatialStructure().closest(markerPosition);
/*  74 */     if (reference == null) return false;
/*     */     
/*  76 */     TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(reference, TransformComponent.getComponentType());
/*  77 */     assert transformComponent != null;
/*  78 */     return this.exitArea.containsPosition(markerPosition, transformComponent.getPosition());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPlayerInEntryArea(@Nonnull Vector3d playerPosition, @Nonnull Vector3d markerPosition) {
/*  83 */     return this.entryArea.containsPosition(markerPosition, playerPosition);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ObjectiveLocationMarkerArea getRotatedArea(float yaw, float pitch) {
/*  90 */     float snappedYaw = Math.round(yaw / 1.5707964F) * 1.5707964F;
/*     */ 
/*     */     
/*  93 */     if (Math.abs(snappedYaw % 6.2831855F) > 0.7853982F) {
/*     */ 
/*     */       
/*  96 */       Box entry = this.entryArea.clone().rotateY(snappedYaw).normalize();
/*     */ 
/*     */ 
/*     */       
/* 100 */       Box exit = this.exitArea.clone().rotateY(snappedYaw).normalize();
/*     */       
/* 102 */       return new ObjectiveLocationAreaBox(entry, exit);
/*     */     } 
/*     */     
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void computeAreaBoxes() {
/* 110 */     this.entryAreaBox = this.entryArea;
/* 111 */     this.exitAreaBox = this.exitArea;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void getPlayersInArea(@Nonnull SpatialResource<Ref<EntityStore>, EntityStore> spatialComponent, List<Ref<EntityStore>> results, @Nonnull Vector3d markerPosition, @Nonnull Box box) {
/* 116 */     spatialComponent.getSpatialStructure().collect(markerPosition, box.getMaximumExtent(), results);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 122 */     return "ObjectiveLocationAreaBox{} " + super.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\markerarea\ObjectiveLocationAreaBox.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */