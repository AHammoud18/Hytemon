/*    */ package com.hypixel.hytale.builtin.adventure.objectives.config.markerarea;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*    */ import com.hypixel.hytale.math.shape.Box;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class ObjectiveLocationMarkerArea
/*    */ {
/*    */   @Nonnull
/* 19 */   public static final CodecMapCodec<ObjectiveLocationMarkerArea> CODEC = new CodecMapCodec("Type");
/*    */   
/*    */   protected Box entryAreaBox;
/*    */   
/*    */   protected Box exitAreaBox;
/*    */   
/*    */   public abstract void getPlayersInEntryArea(@Nonnull SpatialResource<Ref<EntityStore>, EntityStore> paramSpatialResource, @Nonnull List<Ref<EntityStore>> paramList, @Nonnull Vector3d paramVector3d);
/*    */   
/*    */   public abstract void getPlayersInExitArea(@Nonnull SpatialResource<Ref<EntityStore>, EntityStore> paramSpatialResource, @Nonnull List<Ref<EntityStore>> paramList, @Nonnull Vector3d paramVector3d);
/*    */   
/*    */   public abstract boolean hasPlayerInExitArea(@Nonnull SpatialResource<Ref<EntityStore>, EntityStore> paramSpatialResource, @Nonnull ComponentType<EntityStore, PlayerRef> paramComponentType, @Nonnull Vector3d paramVector3d, @Nonnull CommandBuffer<EntityStore> paramCommandBuffer);
/*    */   
/*    */   public abstract boolean isPlayerInEntryArea(@Nonnull Vector3d paramVector3d1, @Nonnull Vector3d paramVector3d2);
/*    */   
/*    */   public Box getBoxForEntryArea() {
/* 34 */     return this.entryAreaBox;
/*    */   }
/*    */   
/*    */   public Box getBoxForExitArea() {
/* 38 */     return this.exitAreaBox;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ObjectiveLocationMarkerArea getRotatedArea(float yaw, float pitch) {
/* 43 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract void computeAreaBoxes();
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 51 */     return "ObjectiveLocationMarkerArea{, entryAreaBox=" + String.valueOf(this.entryAreaBox) + ", exitAreaBox=" + String.valueOf(this.exitAreaBox) + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 58 */     CODEC.register("Box", ObjectiveLocationAreaBox.class, (Codec)ObjectiveLocationAreaBox.CODEC);
/* 59 */     CODEC.register("Radius", ObjectiveLocationAreaRadius.class, (Codec)ObjectiveLocationAreaRadius.CODEC);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\markerarea\ObjectiveLocationMarkerArea.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */