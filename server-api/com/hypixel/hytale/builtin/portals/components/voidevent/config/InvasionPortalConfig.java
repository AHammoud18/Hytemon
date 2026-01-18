/*    */ package com.hypixel.hytale.builtin.portals.components.voidevent.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class InvasionPortalConfig
/*    */ {
/*    */   public static final BuilderCodec<InvasionPortalConfig> CODEC;
/*    */   private String blockKey;
/*    */   private String[] spawnBeacons;
/*    */   private String onSpawnParticles;
/*    */   
/*    */   static {
/* 36 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(InvasionPortalConfig.class, InvasionPortalConfig::new).append(new KeyedCodec("BlockKey", (Codec)Codec.STRING), (config, o) -> config.blockKey = o, config -> config.blockKey).documentation("The block used for evil portals that spawn around the world during the event").add()).append(new KeyedCodec("SpawnBeacons", (Codec)Codec.STRING_ARRAY), (config, o) -> config.spawnBeacons = o, config -> config.spawnBeacons).add()).documentation("An array of SpawnBeacon IDs, which will make mobs spawn around the evil portals")).append(new KeyedCodec("OnSpawnParticles", (Codec)Codec.STRING), (config, o) -> config.onSpawnParticles = o, config -> config.onSpawnParticles).documentation("A particle system ID to spawn when the portal spawns, should be a temporary one.").add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getBlockKey() {
/* 43 */     return this.blockKey;
/*    */   }
/*    */   
/*    */   public BlockType getBlockType() {
/* 47 */     return (BlockType)BlockType.getAssetMap().getAsset(this.blockKey);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getOnSpawnParticles() {
/* 52 */     return this.onSpawnParticles;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String[] getSpawnBeacons() {
/* 57 */     return this.spawnBeacons;
/*    */   }
/*    */   
/*    */   public List<String> getSpawnBeaconsList() {
/* 61 */     return (this.spawnBeacons == null) ? 
/* 62 */       Collections.<String>emptyList() : 
/* 63 */       Arrays.<String>asList(this.spawnBeacons);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\components\voidevent\config\InvasionPortalConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */