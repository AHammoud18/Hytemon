/*     */ package com.hypixel.hytale.server.core.asset.type.gameplay;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldMapConfig
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<WorldMapConfig> CODEC;
/*     */   
/*     */   static {
/*  44 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(WorldMapConfig.class, WorldMapConfig::new).addField(new KeyedCodec("DisplaySpawn", (Codec)Codec.BOOLEAN), (worldMapConfig, o) -> worldMapConfig.displaySpawn = o.booleanValue(), worldMapConfig -> Boolean.valueOf(worldMapConfig.displaySpawn))).addField(new KeyedCodec("DisplayHome", (Codec)Codec.BOOLEAN), (worldMapConfig, o) -> worldMapConfig.displayHome = o.booleanValue(), worldMapConfig -> Boolean.valueOf(worldMapConfig.displayHome))).addField(new KeyedCodec("DisplayWarps", (Codec)Codec.BOOLEAN), (worldMapConfig, o) -> worldMapConfig.displayWarps = o.booleanValue(), worldMapConfig -> Boolean.valueOf(worldMapConfig.displayWarps))).addField(new KeyedCodec("DisplayDeathMarker", (Codec)Codec.BOOLEAN), (worldMapConfig, o) -> worldMapConfig.displayDeathMarker = o.booleanValue(), worldMapConfig -> Boolean.valueOf(worldMapConfig.displayDeathMarker))).addField(new KeyedCodec("DisplayPlayers", (Codec)Codec.BOOLEAN), (worldMapConfig, o) -> worldMapConfig.displayPlayers = o.booleanValue(), worldMapConfig -> Boolean.valueOf(worldMapConfig.displayPlayers))).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean displaySpawn = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean displayHome = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean displayWarps = true;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean displayDeathMarker = true;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean displayPlayers = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDisplaySpawn() {
/*  75 */     return this.displaySpawn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDisplayHome() {
/*  82 */     return this.displayHome;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDisplayWarps() {
/*  89 */     return this.displayWarps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDisplayDeathMarker() {
/*  96 */     return this.displayDeathMarker;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDisplayPlayers() {
/* 103 */     return this.displayPlayers;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\gameplay\WorldMapConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */