/*     */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.protocol.ItemUtility;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatsModule;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ItemUtility implements NetworkSerializable<ItemUtility> {
/*  23 */   public static final ItemUtility DEFAULT = new ItemUtility();
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<ItemUtility> CODEC;
/*     */ 
/*     */   
/*     */   protected boolean usable;
/*     */ 
/*     */   
/*     */   protected boolean compatible;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Map<String, StaticModifier[]> rawStatModifiers;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Int2ObjectMap<StaticModifier[]> statModifiers;
/*     */ 
/*     */   
/*     */   protected String[] rawEntityStatsToClear;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected int[] entityStatsToClear;
/*     */ 
/*     */   
/*     */   static {
/*  51 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ItemUtility.class, ItemUtility::new).append(new KeyedCodec("Usable", (Codec)Codec.BOOLEAN), (o, v) -> o.usable = v.booleanValue(), o -> Boolean.valueOf(o.usable)).add()).append(new KeyedCodec("Compatible", (Codec)Codec.BOOLEAN), (o, v) -> o.compatible = v.booleanValue(), o -> Boolean.valueOf(o.compatible)).add()).append(new KeyedCodec("StatModifiers", (Codec)new MapCodec((Codec)new ArrayCodec((Codec)StaticModifier.CODEC, x$0 -> new StaticModifier[x$0]), java.util.HashMap::new)), (itemArmor, map) -> itemArmor.rawStatModifiers = map, itemArmor -> itemArmor.rawStatModifiers).addValidator((Validator)EntityStatType.VALIDATOR_CACHE.getMapKeyValidator().late()).add()).append(new KeyedCodec("EntityStatsToClear", (Codec)Codec.STRING_ARRAY), (itemUtility, strings) -> itemUtility.rawEntityStatsToClear = strings, itemUtility -> itemUtility.rawEntityStatsToClear).add()).afterDecode(item -> { item.statModifiers = EntityStatsModule.resolveEntityStats(item.rawStatModifiers); item.entityStatsToClear = EntityStatsModule.resolveEntityStats(item.rawEntityStatsToClear); })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsable() {
/*  75 */     return this.usable;
/*     */   }
/*     */   
/*     */   public boolean isCompatible() {
/*  79 */     return this.compatible;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Int2ObjectMap<StaticModifier[]> getStatModifiers() {
/*  84 */     return this.statModifiers;
/*     */   }
/*     */   
/*     */   public int[] getEntityStatsToClear() {
/*  88 */     return this.entityStatsToClear;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemUtility toPacket() {
/*  94 */     return new ItemUtility(this.usable, this.compatible, this.entityStatsToClear, (Map)EntityStatMap.toPacket(this.statModifiers));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 100 */     return "ItemUtility{usable=" + this.usable + ", compatible=" + this.compatible + ", rawStatModifiers=" + String.valueOf(this.rawStatModifiers) + ", statModifiers=" + String.valueOf(this.statModifiers) + ", rawEntityStatsToClear=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 105 */       Arrays.toString((Object[])this.rawEntityStatsToClear) + ", entityStatsToClear=" + 
/* 106 */       Arrays.toString(this.entityStatsToClear) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\ItemUtility.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */