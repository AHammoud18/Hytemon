/*    */ package com.hypixel.hytale.server.core.asset.type.gameplay;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.server.core.asset.type.camera.CameraEffect;
/*    */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*    */ import it.unimi.dsi.fastutil.ints.Int2IntMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2IntMaps;
/*    */ import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class CameraEffectsConfig
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<CameraEffectsConfig> CODEC;
/*    */   protected Map<String, String> damageEffectIds;
/*    */   
/*    */   static {
/* 47 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CameraEffectsConfig.class, CameraEffectsConfig::new).appendInherited(new KeyedCodec("DamageEffects", (Codec)new MapCodec(CameraEffect.CHILD_ASSET_CODEC, java.util.HashMap::new)), (config, damageEffectIds) -> config.damageEffectIds = damageEffectIds, config -> config.damageEffectIds, (config, parent) -> config.damageEffectIds = parent.damageEffectIds).addValidator((Validator)DamageCause.VALIDATOR_CACHE.getMapKeyValidator()).addValidator((Validator)CameraEffect.VALIDATOR_CACHE.getMapValueValidator()).documentation("The default damage camera effects").add()).afterDecode(config -> { if (config.damageEffectIds != null) { config.damageEffectIndices = (Int2IntMap)new Int2IntOpenHashMap(); for (Map.Entry<String, String> entry : config.damageEffectIds.entrySet()) { int key = DamageCause.getAssetMap().getIndex(entry.getKey()); int effectIndex = CameraEffect.getAssetMap().getIndex(entry.getValue()); config.damageEffectIndices.put(key, effectIndex); }  }  })).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 57 */   protected transient Int2IntMap damageEffectIndices = (Int2IntMap)Int2IntMaps.EMPTY_MAP;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCameraEffectIndex(int damageCauseIndex) {
/* 67 */     return this.damageEffectIndices.getOrDefault(damageCauseIndex, -2147483648);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\gameplay\CameraEffectsConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */