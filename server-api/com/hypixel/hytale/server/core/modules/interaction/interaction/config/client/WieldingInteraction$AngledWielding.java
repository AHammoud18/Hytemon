/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.Object2DoubleMapCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.Object2FloatMapCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.protocol.AngledWielding;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*     */ import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2DoubleMaps;
/*     */ import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatMaps;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2FloatMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class AngledWielding
/*     */   implements NetworkSerializable<AngledWielding>
/*     */ {
/*     */   public static final BuilderCodec<AngledWielding> CODEC;
/*     */   protected float angleRad;
/*     */   protected float angleDistanceRad;
/*     */   @Nullable
/*     */   protected Object2DoubleMap<String> knockbackModifiersRaw;
/*     */   @Nullable
/*     */   protected Object2FloatMap<String> damageModifiersRaw;
/*     */   
/*     */   static {
/* 327 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(AngledWielding.class, AngledWielding::new).appendInherited(new KeyedCodec("Angle", (Codec)Codec.FLOAT), (o, i) -> o.angleRad = i.floatValue() * 0.017453292F, o -> Float.valueOf(o.angleRad * 57.295776F), (o, p) -> o.angleRad = p.angleRad).add()).appendInherited(new KeyedCodec("AngleDistance", (Codec)Codec.FLOAT), (o, i) -> o.angleDistanceRad = i.floatValue() * 0.017453292F, o -> Float.valueOf(o.angleDistanceRad * 57.295776F), (o, p) -> o.angleDistanceRad = p.angleDistanceRad).add()).appendInherited(new KeyedCodec("KnockbackModifiers", (Codec)new Object2DoubleMapCodec((Codec)Codec.STRING, it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap::new)), (o, m) -> o.knockbackModifiersRaw = m, o -> o.knockbackModifiersRaw, (o, p) -> o.knockbackModifiersRaw = p.knockbackModifiersRaw).addValidator((Validator)DamageCause.VALIDATOR_CACHE.getMapKeyValidator()).add()).appendInherited(new KeyedCodec("DamageModifiers", (Codec)new Object2FloatMapCodec((Codec)Codec.STRING, it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap::new)), (o, m) -> o.damageModifiersRaw = m, o -> o.damageModifiersRaw, (o, p) -> o.damageModifiersRaw = p.damageModifiersRaw).addValidator((Validator)DamageCause.VALIDATOR_CACHE.getMapKeyValidator()).add()).afterDecode(o -> { if (o.knockbackModifiersRaw != null) { o.knockbackModifiers = (Int2DoubleMap)new Int2DoubleOpenHashMap(); ObjectIterator<Object2DoubleMap.Entry<String>> objectIterator = o.knockbackModifiersRaw.object2DoubleEntrySet().iterator(); while (objectIterator.hasNext()) { Object2DoubleMap.Entry<String> entry = objectIterator.next(); int index = DamageCause.getAssetMap().getIndex(entry.getKey()); o.knockbackModifiers.put(index, entry.getDoubleValue()); }  }  if (o.damageModifiersRaw != null) { o.damageModifiers = (Int2FloatMap)new Int2FloatOpenHashMap(); ObjectIterator<Object2FloatMap.Entry<String>> objectIterator = o.damageModifiersRaw.object2FloatEntrySet().iterator(); while (objectIterator.hasNext()) { Object2FloatMap.Entry<String> entry = objectIterator.next(); int index = DamageCause.getAssetMap().getIndex(entry.getKey()); o.damageModifiers.put(index, entry.getFloatValue()); }  }  })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 336 */   protected transient Int2DoubleMap knockbackModifiers = (Int2DoubleMap)Int2DoubleMaps.EMPTY_MAP;
/*     */   @Nonnull
/* 338 */   protected transient Int2FloatMap damageModifiers = (Int2FloatMap)Int2FloatMaps.EMPTY_MAP;
/*     */ 
/*     */   
/*     */   public double getAngleRad() {
/* 342 */     return this.angleRad;
/*     */   }
/*     */   
/*     */   public double getAngleDistanceRad() {
/* 346 */     return this.angleDistanceRad;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Int2DoubleMap getKnockbackModifiers() {
/* 351 */     return this.knockbackModifiers;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Int2FloatMap getDamageModifiers() {
/* 356 */     return this.damageModifiers;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public AngledWielding toPacket() {
/* 362 */     AngledWielding packet = new AngledWielding();
/* 363 */     packet.angleRad = this.angleRad;
/* 364 */     packet.angleDistanceRad = this.angleDistanceRad;
/* 365 */     packet.hasModifiers = (this.damageModifiersRaw != null || this.knockbackModifiersRaw != null);
/* 366 */     return packet;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\WieldingInteraction$AngledWielding.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */