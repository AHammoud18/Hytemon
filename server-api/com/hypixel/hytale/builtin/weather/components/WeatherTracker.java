/*     */ package com.hypixel.hytale.builtin.weather.components;
/*     */ import com.hypixel.hytale.builtin.weather.resources.WeatherResource;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.world.UpdateWeather;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class WeatherTracker implements Component<EntityStore> {
/*     */   public static ComponentType<EntityStore, WeatherTracker> getComponentType() {
/*  23 */     return WeatherPlugin.get().getWeatherTrackerComponentType();
/*     */   }
/*     */   
/*  26 */   private final UpdateWeather updateWeather = new UpdateWeather(0, 10.0F);
/*  27 */   private final Vector3i previousBlockPosition = new Vector3i();
/*     */ 
/*     */   
/*     */   private int environmentId;
/*     */   
/*     */   private boolean firstSendForWorld = true;
/*     */ 
/*     */   
/*     */   private WeatherTracker(@Nonnull WeatherTracker other) {
/*  36 */     this.environmentId = other.environmentId;
/*  37 */     this.updateWeather.weatherIndex = other.updateWeather.weatherIndex;
/*  38 */     this.previousBlockPosition.assign(other.previousBlockPosition);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateWeather(@Nonnull PlayerRef playerRef, @Nonnull WeatherResource weatherComponent, @Nonnull TransformComponent transformComponent, float transitionSeconds, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  47 */     int forcedWeatherIndex = weatherComponent.getForcedWeatherIndex();
/*  48 */     if (forcedWeatherIndex != 0) {
/*  49 */       sendWeatherIndex(playerRef, forcedWeatherIndex, transitionSeconds);
/*     */       
/*     */       return;
/*     */     } 
/*  53 */     updateEnvironment(transformComponent, componentAccessor);
/*  54 */     int weatherIndexForEnvironment = weatherComponent.getWeatherIndexForEnvironment(this.environmentId);
/*     */     
/*  56 */     sendWeatherIndex(playerRef, weatherIndexForEnvironment, transitionSeconds);
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendWeatherIndex(@Nonnull PlayerRef playerRef, int weatherIndex, float transitionSeconds) {
/*  61 */     if (weatherIndex == Integer.MIN_VALUE) weatherIndex = 0;
/*     */     
/*  63 */     if (this.updateWeather.weatherIndex != weatherIndex) {
/*  64 */       this.updateWeather.weatherIndex = weatherIndex;
/*  65 */       this.updateWeather.transitionSeconds = transitionSeconds;
/*     */       
/*  67 */       playerRef.getPacketHandler().write((Packet)this.updateWeather);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean consumeFirstSendForWorld() {
/*  72 */     if (this.firstSendForWorld) {
/*  73 */       this.firstSendForWorld = false;
/*  74 */       return true;
/*     */     } 
/*  76 */     return false;
/*     */   }
/*     */   
/*     */   public void clear() {
/*  80 */     this.updateWeather.weatherIndex = 0;
/*  81 */     this.firstSendForWorld = true;
/*     */   }
/*     */   
/*     */   public void updateEnvironment(@Nonnull TransformComponent transformComponent, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  85 */     Vector3d vector = transformComponent.getPosition();
/*  86 */     int blockX = MathUtil.floor(vector.getX());
/*  87 */     int blockY = MathUtil.floor(vector.getY());
/*  88 */     int blockZ = MathUtil.floor(vector.getZ());
/*     */     
/*  90 */     if (this.previousBlockPosition.getX() != blockX || this.previousBlockPosition.getY() != blockY || this.previousBlockPosition.getZ() != blockZ) {
/*  91 */       Ref<ChunkStore> chunkRef = transformComponent.getChunkRef();
/*  92 */       if (chunkRef == null || !chunkRef.isValid())
/*     */         return; 
/*  94 */       World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*  95 */       Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*     */       
/*  97 */       BlockChunk blockChunkComponent = (BlockChunk)chunkStore.getComponent(chunkRef, BlockChunk.getComponentType());
/*  98 */       assert blockChunkComponent != null;
/*     */ 
/*     */       
/* 101 */       int y = MathUtil.clamp(blockY, 0, 319);
/* 102 */       this.environmentId = blockChunkComponent.getEnvironment(blockX, y, blockZ);
/*     */     } 
/*     */     
/* 105 */     this.previousBlockPosition.x = blockX;
/* 106 */     this.previousBlockPosition.y = blockY;
/* 107 */     this.previousBlockPosition.z = blockZ;
/*     */   }
/*     */   
/*     */   public int getEnvironmentId() {
/* 111 */     return this.environmentId;
/*     */   }
/*     */   
/*     */   public int getWeatherIndex() {
/* 115 */     return this.updateWeather.weatherIndex;
/*     */   }
/*     */   
/*     */   public void setWeatherIndex(@Nonnull PlayerRef playerRef, int weatherIndex) {
/* 119 */     this.updateWeather.weatherIndex = weatherIndex;
/*     */     
/* 121 */     playerRef.getPacketHandler().write((Packet)this.updateWeather);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<EntityStore> clone() {
/* 127 */     return new WeatherTracker(this);
/*     */   }
/*     */   
/*     */   public WeatherTracker() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\weather\components\WeatherTracker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */