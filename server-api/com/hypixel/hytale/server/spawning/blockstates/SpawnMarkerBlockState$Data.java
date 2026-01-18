/*    */ package com.hypixel.hytale.server.spawning.blockstates;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.LateValidator;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.StateData;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Data
/*    */   extends StateData
/*    */ {
/*    */   public static final BuilderCodec<Data> CODEC;
/*    */   private String spawnMarker;
/*    */   private Vector3i markerOffset;
/*    */   
/*    */   static {
/* 71 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Data.class, Data::new, StateData.DEFAULT_CODEC).appendInherited(new KeyedCodec("SpawnMarker", (Codec)Codec.STRING), (spawn, s) -> spawn.spawnMarker = s, spawn -> spawn.spawnMarker, (spawn, parent) -> spawn.spawnMarker = parent.spawnMarker).documentation("The spawn marker to use.").addValidator(Validators.nonNull()).addValidatorLate(() -> SpawnMarker.VALIDATOR_CACHE.getValidator().late()).add()).appendInherited(new KeyedCodec("MarkerOffset", (Codec)Vector3i.CODEC), (spawn, o) -> spawn.markerOffset = o, spawn -> spawn.markerOffset, (spawn, parent) -> spawn.markerOffset = parent.markerOffset).documentation("An offset from the block at which the marker entity should be spawned.").add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSpawnMarker() {
/* 80 */     return this.spawnMarker;
/*    */   }
/*    */   
/*    */   public Vector3i getMarkerOffset() {
/* 84 */     return this.markerOffset;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\blockstates\SpawnMarkerBlockState$Data.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */