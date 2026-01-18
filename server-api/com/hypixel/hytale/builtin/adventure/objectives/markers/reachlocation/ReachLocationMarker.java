/*    */ package com.hypixel.hytale.builtin.adventure.objectives.markers.reachlocation;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ReachLocationMarker
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static final BuilderCodec<ReachLocationMarker> CODEC;
/*    */   private String markerId;
/*    */   
/*    */   static {
/* 25 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ReachLocationMarker.class, ReachLocationMarker::new).append(new KeyedCodec("MarkerId", (Codec)Codec.STRING), (reachLocationMarkerEntity, uuid) -> reachLocationMarkerEntity.markerId = uuid, reachLocationMarkerEntity -> reachLocationMarkerEntity.markerId).addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */   public static ComponentType<EntityStore, ReachLocationMarker> getComponentType() {
/* 28 */     return ObjectivePlugin.get().getReachLocationMarkerComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 33 */   private final Set<UUID> players = new HashSet<>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ReachLocationMarker(String markerId) {
/* 39 */     this.markerId = markerId;
/*    */   }
/*    */   
/*    */   public String getMarkerId() {
/* 43 */     return this.markerId;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getLocationName() {
/* 48 */     ReachLocationMarkerAsset asset = (ReachLocationMarkerAsset)ReachLocationMarkerAsset.getAssetMap().getAsset(this.markerId);
/* 49 */     return (asset != null) ? asset.getName() : null;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Set<UUID> getPlayers() {
/* 54 */     return this.players;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 61 */     ReachLocationMarker marker = new ReachLocationMarker(this.markerId);
/* 62 */     marker.players.addAll(this.players);
/* 63 */     return marker;
/*    */   }
/*    */   
/*    */   public ReachLocationMarker() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\markers\reachlocation\ReachLocationMarker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */