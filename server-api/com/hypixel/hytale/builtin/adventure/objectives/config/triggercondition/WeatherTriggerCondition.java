/*     */ package com.hypixel.hytale.builtin.adventure.objectives.config.triggercondition;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.markers.objectivelocation.ObjectiveLocationMarker;
/*     */ import com.hypixel.hytale.builtin.weather.resources.WeatherResource;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.asset.type.weather.config.Weather;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Arrays;
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
/*     */ public class WeatherTriggerCondition
/*     */   extends ObjectiveLocationTriggerCondition
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<WeatherTriggerCondition> CODEC;
/*     */   
/*     */   static {
/*  50 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(WeatherTriggerCondition.class, WeatherTriggerCondition::new).append(new KeyedCodec("WeatherIds", (Codec)Codec.STRING_ARRAY), (weatherTriggerCondition, strings) -> weatherTriggerCondition.weatherIds = strings, weatherTriggerCondition -> weatherTriggerCondition.weatherIds).addValidator(Validators.nonEmptyArray()).addValidator((Validator)Weather.VALIDATOR_CACHE.getArrayValidator()).add()).afterDecode(weatherTriggerCondition -> { if (weatherTriggerCondition.weatherIds == null) return;  weatherTriggerCondition.weatherIndexes = new int[weatherTriggerCondition.weatherIds.length]; for (int i = 0; i < weatherTriggerCondition.weatherIds.length; i++) { String key = weatherTriggerCondition.weatherIds[i]; int index = Weather.getAssetMap().getIndex(key); if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key);  weatherTriggerCondition.weatherIndexes[i] = index; }  Arrays.sort(weatherTriggerCondition.weatherIndexes); })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  56 */   protected static final ResourceType<EntityStore, WeatherResource> WEATHER_RESOURCE_RESOURCE_TYPE = WeatherResource.getResourceType();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  62 */   protected static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*     */ 
/*     */ 
/*     */   
/*     */   protected String[] weatherIds;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int[] weatherIndexes;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConditionMet(@Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull Ref<EntityStore> ref, ObjectiveLocationMarker objectiveLocationMarker) {
/*  76 */     WeatherResource weatherResource = (WeatherResource)componentAccessor.getResource(WEATHER_RESOURCE_RESOURCE_TYPE);
/*     */     
/*  78 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/*  79 */     assert transformComponent != null;
/*     */     
/*  81 */     Ref<ChunkStore> chunkRef = transformComponent.getChunkRef();
/*  82 */     if (chunkRef == null || !chunkRef.isValid()) return false;
/*     */     
/*  84 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*  85 */     Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*     */     
/*  87 */     BlockChunk blockChunkComponent = (BlockChunk)chunkStore.getComponent(chunkRef, BlockChunk.getComponentType());
/*  88 */     assert blockChunkComponent != null;
/*     */     
/*  90 */     int environmentIndex = blockChunkComponent.getEnvironment(transformComponent.getPosition());
/*  91 */     int currentWeatherIndex = weatherResource.getWeatherIndexForEnvironment(environmentIndex);
/*     */     
/*  93 */     return (Arrays.binarySearch(this.weatherIndexes, currentWeatherIndex) >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 100 */     return "WeatherTriggerCondition{weatherIds=" + Arrays.toString((Object[])this.weatherIds) + "} " + super
/* 101 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\triggercondition\WeatherTriggerCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */