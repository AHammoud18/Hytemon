/*     */ package com.hypixel.hytale.builtin.adventure.farming.states;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.farming.FarmingPlugin;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import java.time.Instant;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class TilledSoilBlock
/*     */   implements Component<ChunkStore>
/*     */ {
/*     */   public static ComponentType<ChunkStore, TilledSoilBlock> getComponentType() {
/*  22 */     return FarmingPlugin.get().getTiledSoilBlockComponentType();
/*     */   }
/*     */   
/*  25 */   public static int VERSION = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<TilledSoilBlock> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean planted;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean fertilized;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean externalWater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Instant wateredUntil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Instant decayTime;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  86 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(TilledSoilBlock.class, TilledSoilBlock::new).versioned()).codecVersion(VERSION)).append(new KeyedCodec("Planted", (Codec)Codec.BOOLEAN), (state, planted) -> state.planted = planted.booleanValue(), state -> state.planted ? Boolean.TRUE : null).add()).append(new KeyedCodec("ModifierTimes", (Codec)new MapCodec((Codec)Codec.INSTANT, it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap::new, false)), (state, times) -> { if (times == null) return;  state.wateredUntil = (Instant)times.get("WateredUntil"); }state -> null).setVersionRange(0, 0).add()).append(new KeyedCodec("Flags", (Codec)Codec.STRING_ARRAY), (state, flags) -> { if (flags == null) return;  state.fertilized = ArrayUtil.contains((Object[])flags, "Fertilized"); state.externalWater = ArrayUtil.contains((Object[])flags, "ExternalWater"); }state -> null).setVersionRange(0, 0).add()).append(new KeyedCodec("Fertilized", (Codec)Codec.BOOLEAN), (state, v) -> state.fertilized = v.booleanValue(), state -> state.fertilized ? Boolean.TRUE : null).setVersionRange(1, VERSION).add()).append(new KeyedCodec("ExternalWater", (Codec)Codec.BOOLEAN), (state, v) -> state.externalWater = v.booleanValue(), state -> state.externalWater ? Boolean.TRUE : null).setVersionRange(1, VERSION).add()).append(new KeyedCodec("WateredUntil", (Codec)Codec.INSTANT), (state, v) -> state.wateredUntil = v, state -> state.wateredUntil).setVersionRange(1, VERSION).add()).append(new KeyedCodec("DecayTime", (Codec)Codec.INSTANT), (state, v) -> state.decayTime = v, state -> state.decayTime).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TilledSoilBlock() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TilledSoilBlock(boolean planted, boolean fertilized, boolean externalWater, Instant wateredUntil, Instant decayTime) {
/* 100 */     this.planted = planted;
/* 101 */     this.fertilized = fertilized;
/* 102 */     this.externalWater = externalWater;
/* 103 */     this.wateredUntil = wateredUntil;
/* 104 */     this.decayTime = decayTime;
/*     */   }
/*     */   
/*     */   public boolean isPlanted() {
/* 108 */     return this.planted;
/*     */   }
/*     */   
/*     */   public void setPlanted(boolean planted) {
/* 112 */     this.planted = planted;
/*     */   }
/*     */   
/*     */   public void setWateredUntil(@Nullable Instant wateredUntil) {
/* 116 */     this.wateredUntil = wateredUntil;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Instant getWateredUntil() {
/* 121 */     return this.wateredUntil;
/*     */   }
/*     */   
/*     */   public boolean isFertilized() {
/* 125 */     return this.fertilized;
/*     */   }
/*     */   
/*     */   public void setFertilized(boolean fertilized) {
/* 129 */     this.fertilized = fertilized;
/*     */   }
/*     */   
/*     */   public boolean hasExternalWater() {
/* 133 */     return this.externalWater;
/*     */   }
/*     */   
/*     */   public void setExternalWater(boolean externalWater) {
/* 137 */     this.externalWater = externalWater;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Instant getDecayTime() {
/* 142 */     return this.decayTime;
/*     */   }
/*     */   
/*     */   public void setDecayTime(@Nullable Instant decayTime) {
/* 146 */     this.decayTime = decayTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public String computeBlockType(Instant gameTime, BlockType type) {
/* 151 */     boolean watered = (hasExternalWater() || (this.wateredUntil != null && this.wateredUntil.isAfter(gameTime)));
/* 152 */     if (this.fertilized && watered) {
/* 153 */       return type.getBlockKeyForState("Fertilized_Watered");
/*     */     }
/* 155 */     if (this.fertilized) {
/* 156 */       return type.getBlockKeyForState("Fertilized");
/*     */     }
/* 158 */     if (watered) {
/* 159 */       return type.getBlockKeyForState("Watered");
/*     */     }
/* 161 */     return type.getBlockKeyForState("default");
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 166 */     return "TilledSoilBlock{planted=" + this.planted + ", fertilized=" + this.fertilized + ", externalWater=" + this.externalWater + ", wateredUntil=" + String.valueOf(this.wateredUntil) + ", decayTime=" + String.valueOf(this.decayTime) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Component<ChunkStore> clone() {
/* 178 */     return new TilledSoilBlock(this.planted, this.fertilized, this.externalWater, this.wateredUntil, this.decayTime);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\states\TilledSoilBlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */