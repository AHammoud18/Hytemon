/*    */ package com.hypixel.hytale.server.spawning.blockstates;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.LateValidator;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.StateData;
/*    */ import com.hypixel.hytale.server.core.entity.reference.PersistentRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*    */ import com.hypixel.hytale.server.spawning.assets.spawnmarker.config.SpawnMarker;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpawnMarkerBlockState
/*    */   extends BlockState
/*    */ {
/*    */   public static final Codec<SpawnMarkerBlockState> CODEC;
/*    */   private PersistentRef spawnMarkerReference;
/*    */   
/*    */   static {
/* 29 */     CODEC = (Codec<SpawnMarkerBlockState>)((BuilderCodec.Builder)BuilderCodec.builder(SpawnMarkerBlockState.class, SpawnMarkerBlockState::new, BlockState.BASE_CODEC).append(new KeyedCodec("MarkerReference", (Codec)PersistentRef.CODEC), (spawn, o) -> spawn.spawnMarkerReference = o, spawn -> spawn.spawnMarkerReference).add()).build();
/*    */   }
/*    */ 
/*    */   
/* 33 */   private float markerLostTimeout = 30.0F;
/*    */   
/*    */   public PersistentRef getSpawnMarkerReference() {
/* 36 */     return this.spawnMarkerReference;
/*    */   }
/*    */   
/*    */   public void setSpawnMarkerReference(PersistentRef spawnMarkerReference) {
/* 40 */     this.spawnMarkerReference = spawnMarkerReference;
/*    */   }
/*    */   
/*    */   public void refreshMarkerLostTimeout() {
/* 44 */     this.markerLostTimeout = 30.0F;
/*    */   }
/*    */   
/*    */   public boolean tickMarkerLostTimeout(float dt) {
/* 48 */     return ((this.markerLostTimeout -= dt) <= 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class Data
/*    */     extends StateData
/*    */   {
/*    */     public static final BuilderCodec<Data> CODEC;
/*    */ 
/*    */ 
/*    */     
/*    */     private String spawnMarker;
/*    */ 
/*    */ 
/*    */     
/*    */     private Vector3i markerOffset;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/* 71 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Data.class, Data::new, StateData.DEFAULT_CODEC).appendInherited(new KeyedCodec("SpawnMarker", (Codec)Codec.STRING), (spawn, s) -> spawn.spawnMarker = s, spawn -> spawn.spawnMarker, (spawn, parent) -> spawn.spawnMarker = parent.spawnMarker).documentation("The spawn marker to use.").addValidator(Validators.nonNull()).addValidatorLate(() -> SpawnMarker.VALIDATOR_CACHE.getValidator().late()).add()).appendInherited(new KeyedCodec("MarkerOffset", (Codec)Vector3i.CODEC), (spawn, o) -> spawn.markerOffset = o, spawn -> spawn.markerOffset, (spawn, parent) -> spawn.markerOffset = parent.markerOffset).documentation("An offset from the block at which the marker entity should be spawned.").add()).build();
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public String getSpawnMarker() {
/* 80 */       return this.spawnMarker;
/*    */     }
/*    */     
/*    */     public Vector3i getMarkerOffset() {
/* 84 */       return this.markerOffset;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\blockstates\SpawnMarkerBlockState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */