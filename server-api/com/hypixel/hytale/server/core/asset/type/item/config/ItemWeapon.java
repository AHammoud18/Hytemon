/*    */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.protocol.ItemWeapon;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatsModule;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import java.util.Arrays;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class ItemWeapon
/*    */   implements NetworkSerializable<ItemWeapon>
/*    */ {
/*    */   public static final BuilderCodec<ItemWeapon> CODEC;
/*    */   @Nullable
/*    */   protected Map<String, StaticModifier[]> rawStatModifiers;
/*    */   @Nullable
/*    */   protected Int2ObjectMap<StaticModifier[]> statModifiers;
/*    */   protected String[] rawEntityStatsToClear;
/*    */   @Nullable
/*    */   protected int[] entityStatsToClear;
/*    */   protected boolean renderDualWielded;
/*    */   
/*    */   static {
/* 45 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ItemWeapon.class, ItemWeapon::new).append(new KeyedCodec("StatModifiers", (Codec)new MapCodec((Codec)new ArrayCodec((Codec)StaticModifier.CODEC, x$0 -> new StaticModifier[x$0]), java.util.HashMap::new)), (itemArmor, map) -> itemArmor.rawStatModifiers = map, itemArmor -> itemArmor.rawStatModifiers).addValidator((Validator)EntityStatType.VALIDATOR_CACHE.getMapKeyValidator().late()).add()).append(new KeyedCodec("EntityStatsToClear", (Codec)Codec.STRING_ARRAY), (itemWeapon, strings) -> itemWeapon.rawEntityStatsToClear = strings, itemWeapon -> itemWeapon.rawEntityStatsToClear).add()).append(new KeyedCodec("RenderDualWielded", (Codec)Codec.BOOLEAN), (itemWeapon, value) -> itemWeapon.renderDualWielded = value.booleanValue(), itemWeapon -> Boolean.valueOf(itemWeapon.renderDualWielded)).add()).afterDecode(item -> { item.statModifiers = EntityStatsModule.resolveEntityStats(item.rawStatModifiers); item.entityStatsToClear = EntityStatsModule.resolveEntityStats(item.rawEntityStatsToClear); })).build();
/*    */   }
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
/*    */   @Nullable
/*    */   public Int2ObjectMap<StaticModifier[]> getStatModifiers() {
/* 60 */     return this.statModifiers;
/*    */   }
/*    */   
/*    */   public int[] getEntityStatsToClear() {
/* 64 */     return this.entityStatsToClear;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ItemWeapon toPacket() {
/* 70 */     return new ItemWeapon(this.entityStatsToClear, 
/*    */         
/* 72 */         (Map)EntityStatMap.toPacket(this.statModifiers), this.renderDualWielded);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 79 */     return "ItemWeapon{rawStatModifiers=" + String.valueOf(this.rawStatModifiers) + ", statModifiers=" + String.valueOf(this.statModifiers) + ", rawEntityStatsToClear=" + 
/*    */ 
/*    */       
/* 82 */       Arrays.toString((Object[])this.rawEntityStatsToClear) + ", entityStatsToClear=" + 
/* 83 */       Arrays.toString(this.entityStatsToClear) + ", renderDualWielded=" + this.renderDualWielded + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\ItemWeapon.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */