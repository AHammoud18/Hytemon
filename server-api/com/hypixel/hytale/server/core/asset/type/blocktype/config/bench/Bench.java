/*     */ package com.hypixel.hytale.server.core.asset.type.blocktype.config.bench;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.lookup.ObjectCodecMapCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.protocol.Bench;
/*     */ import com.hypixel.hytale.protocol.BenchType;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.validator.SoundEventValidators;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Bench
/*     */   implements NetworkSerializable<Bench>
/*     */ {
/*  30 */   public static final ObjectCodecMapCodec<BenchType, Bench> CODEC = new ObjectCodecMapCodec("Type", (Codec)new EnumCodec(BenchType.class));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<Bench> BASE_CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 124 */     BASE_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(Bench.class).addField(new KeyedCodec("Id", (Codec)Codec.STRING), (bench, s) -> bench.id = s, bench -> bench.id)).addField(new KeyedCodec("DescriptiveLabel", (Codec)Codec.STRING), (bench, s) -> bench.descriptiveLabel = s, bench -> bench.descriptiveLabel)).appendInherited(new KeyedCodec("TierLevels", (Codec)new ArrayCodec((Codec)BenchTierLevel.CODEC, x$0 -> new BenchTierLevel[x$0])), (bench, u) -> bench.tierLevels = u, bench -> bench.tierLevels, (bench, parent) -> bench.tierLevels = parent.tierLevels).add()).append(new KeyedCodec("LocalOpenSoundEventId", (Codec)Codec.STRING), (bench, s) -> bench.localOpenSoundEventId = s, bench -> bench.localOpenSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.ONESHOT).add()).append(new KeyedCodec("LocalCloseSoundEventId", (Codec)Codec.STRING), (bench, s) -> bench.localCloseSoundEventId = s, bench -> bench.localCloseSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.ONESHOT).add()).append(new KeyedCodec("CompletedSoundEventId", (Codec)Codec.STRING), (bench, s) -> bench.completedSoundEventId = s, bench -> bench.completedSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).addValidator((Validator)SoundEventValidators.ONESHOT).add()).append(new KeyedCodec("FailedSoundEventId", (Codec)Codec.STRING), (bench, s) -> bench.failedSoundEventId = s, bench -> bench.failedSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).addValidator((Validator)SoundEventValidators.ONESHOT).add()).append(new KeyedCodec("BenchUpgradeSoundEventId", (Codec)Codec.STRING), (bench, s) -> bench.benchUpgradeSoundEventId = s, bench -> bench.benchUpgradeSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).addValidator((Validator)SoundEventValidators.ONESHOT).add()).append(new KeyedCodec("BenchUpgradeCompletedSoundEventId", (Codec)Codec.STRING), (bench, s) -> bench.benchUpgradeCompletedSoundEventId = s, bench -> bench.benchUpgradeCompletedSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).addValidator((Validator)SoundEventValidators.ONESHOT).add()).afterDecode(bench -> { bench.type = (BenchType)CODEC.getIdFor(bench.getClass()); if (bench.localOpenSoundEventId != null) bench.localOpenSoundEventIndex = SoundEvent.getAssetMap().getIndex(bench.localOpenSoundEventId);  if (bench.localCloseSoundEventId != null) bench.localCloseSoundEventIndex = SoundEvent.getAssetMap().getIndex(bench.localCloseSoundEventId);  if (bench.completedSoundEventId != null) bench.completedSoundEventIndex = SoundEvent.getAssetMap().getIndex(bench.completedSoundEventId);  if (bench.benchUpgradeSoundEventId != null) bench.benchUpgradeSoundEventIndex = SoundEvent.getAssetMap().getIndex(bench.benchUpgradeSoundEventId);  if (bench.benchUpgradeCompletedSoundEventId != null) bench.benchUpgradeCompletedSoundEventIndex = SoundEvent.getAssetMap().getIndex(bench.benchUpgradeCompletedSoundEventId);  if (bench.failedSoundEventId != null) bench.failedSoundEventIndex = SoundEvent.getAssetMap().getIndex(bench.failedSoundEventId);  })).build();
/*     */   }
/*     */   
/*     */   @Deprecated(forRemoval = true)
/* 128 */   protected static final Map<BenchType, RootInteraction> BENCH_INTERACTIONS = new EnumMap<>(BenchType.class);
/*     */   @Nonnull
/* 130 */   protected BenchType type = BenchType.Crafting;
/*     */   
/*     */   protected String id;
/*     */   protected String descriptiveLabel;
/*     */   protected BenchTierLevel[] tierLevels;
/*     */   @Nullable
/* 136 */   protected String localOpenSoundEventId = null;
/*     */   
/* 138 */   protected transient int localOpenSoundEventIndex = 0; @Nullable
/* 139 */   protected String localCloseSoundEventId = null;
/*     */   
/* 141 */   protected transient int localCloseSoundEventIndex = 0; @Nullable
/* 142 */   protected String completedSoundEventId = null;
/*     */   
/* 144 */   protected transient int completedSoundEventIndex = 0; @Nullable
/* 145 */   protected String failedSoundEventId = null;
/*     */   
/* 147 */   protected transient int failedSoundEventIndex = 0; @Nullable
/* 148 */   protected String benchUpgradeSoundEventId = null;
/*     */   
/* 150 */   protected transient int benchUpgradeSoundEventIndex = 0; @Nullable
/* 151 */   protected String benchUpgradeCompletedSoundEventId = null;
/*     */   
/* 153 */   protected transient int benchUpgradeCompletedSoundEventIndex = 0;
/*     */   
/*     */   public BenchType getType() {
/* 156 */     return this.type;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 160 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getDescriptiveLabel() {
/* 164 */     return this.descriptiveLabel;
/*     */   }
/*     */   
/*     */   public BenchTierLevel getTierLevel(int tierLevel) {
/* 168 */     if (this.tierLevels == null || tierLevel < 1 || tierLevel > this.tierLevels.length) return null; 
/* 169 */     return this.tierLevels[tierLevel - 1];
/*     */   }
/*     */   
/*     */   public BenchUpgradeRequirement getUpgradeRequirement(int tierLevel) {
/* 173 */     BenchTierLevel currentTierLevel = getTierLevel(tierLevel);
/* 174 */     if (currentTierLevel == null) return null; 
/* 175 */     return currentTierLevel.upgradeRequirement;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getLocalOpenSoundEventId() {
/* 180 */     return this.localOpenSoundEventId;
/*     */   }
/*     */   
/*     */   public int getLocalOpenSoundEventIndex() {
/* 184 */     return this.localOpenSoundEventIndex;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getLocalCloseSoundEventId() {
/* 189 */     return this.localCloseSoundEventId;
/*     */   }
/*     */   
/*     */   public int getLocalCloseSoundEventIndex() {
/* 193 */     return this.localCloseSoundEventIndex;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getCompletedSoundEventId() {
/* 198 */     return this.completedSoundEventId;
/*     */   }
/*     */   
/*     */   public int getCompletedSoundEventIndex() {
/* 202 */     return this.completedSoundEventIndex;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getFailedSoundEventId() {
/* 207 */     return this.failedSoundEventId;
/*     */   }
/*     */   
/*     */   public int getFailedSoundEventIndex() {
/* 211 */     return this.failedSoundEventIndex;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getBenchUpgradeSoundEventId() {
/* 216 */     return this.benchUpgradeSoundEventId;
/*     */   }
/*     */   
/*     */   public int getBenchUpgradeSoundEventIndex() {
/* 220 */     return this.benchUpgradeSoundEventIndex;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getBenchUpgradeCompletedSoundEventId() {
/* 225 */     return this.benchUpgradeCompletedSoundEventId;
/*     */   }
/*     */   
/*     */   public int getBenchUpgradeCompletedSoundEventIndex() {
/* 229 */     return this.benchUpgradeCompletedSoundEventIndex;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public RootInteraction getRootInteraction() {
/* 234 */     return BENCH_INTERACTIONS.get(this.type);
/*     */   }
/*     */ 
/*     */   
/*     */   public Bench toPacket() {
/* 239 */     Bench packet = new Bench();
/* 240 */     if (this.tierLevels != null && this.tierLevels.length > 0) {
/* 241 */       packet.benchTierLevels = new com.hypixel.hytale.protocol.BenchTierLevel[this.tierLevels.length];
/* 242 */       for (int i = 0; i < this.tierLevels.length; i++) {
/* 243 */         packet.benchTierLevels[i] = this.tierLevels[i].toPacket();
/*     */       }
/*     */     } 
/* 246 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 251 */     if (o == null || getClass() != o.getClass()) return false; 
/* 252 */     Bench bench = (Bench)o;
/* 253 */     return (this.localOpenSoundEventIndex == bench.localOpenSoundEventIndex && this.localCloseSoundEventIndex == bench.localCloseSoundEventIndex && this.completedSoundEventIndex == bench.completedSoundEventIndex && this.benchUpgradeSoundEventIndex == bench.benchUpgradeSoundEventIndex && this.benchUpgradeCompletedSoundEventIndex == bench.benchUpgradeCompletedSoundEventIndex && this.type == bench.type && Objects.equals(this.id, bench.id) && Objects.equals(this.descriptiveLabel, bench.descriptiveLabel) && Objects.deepEquals(this.tierLevels, bench.tierLevels) && Objects.equals(this.localOpenSoundEventId, bench.localOpenSoundEventId) && Objects.equals(this.localCloseSoundEventId, bench.localCloseSoundEventId) && Objects.equals(this.completedSoundEventId, bench.completedSoundEventId) && Objects.equals(this.failedSoundEventId, bench.failedSoundEventId) && Objects.equals(this.benchUpgradeSoundEventId, bench.benchUpgradeSoundEventId) && Objects.equals(this.benchUpgradeCompletedSoundEventId, bench.benchUpgradeCompletedSoundEventId));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 258 */     return Objects.hash(new Object[] { this.type, this.id, this.descriptiveLabel, Integer.valueOf(Arrays.hashCode((Object[])this.tierLevels)), this.localOpenSoundEventId, Integer.valueOf(this.localOpenSoundEventIndex), this.localCloseSoundEventId, Integer.valueOf(this.localCloseSoundEventIndex), this.completedSoundEventId, Integer.valueOf(this.completedSoundEventIndex), this.failedSoundEventId, Integer.valueOf(this.failedSoundEventIndex), this.benchUpgradeSoundEventId, Integer.valueOf(this.benchUpgradeSoundEventIndex), this.benchUpgradeCompletedSoundEventId, Integer.valueOf(this.benchUpgradeCompletedSoundEventIndex) });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 263 */     return "Bench{type=" + String.valueOf(this.type) + ", id='" + this.id + "', descriptiveLabel='" + this.descriptiveLabel + "', tierLevels=" + 
/*     */ 
/*     */ 
/*     */       
/* 267 */       Arrays.toString((Object[])this.tierLevels) + ", localOpenSoundEventId='" + this.localOpenSoundEventId + "', localOpenSoundEventIndex=" + this.localOpenSoundEventIndex + ", localCloseSoundEventId='" + this.localCloseSoundEventId + "', localCloseSoundEventIndex=" + this.localCloseSoundEventIndex + ", completedSoundEventId='" + this.completedSoundEventId + "', completedSoundEventIndex=" + this.completedSoundEventIndex + ", failedSoundEventId='" + this.failedSoundEventId + "', failedSoundEventIndex=" + this.failedSoundEventIndex + ", benchUpgradeSoundEventId='" + this.benchUpgradeSoundEventId + "', benchUpgradeSoundEventIndex=" + this.benchUpgradeSoundEventIndex + ", benchUpgradeCompletedSoundEventId='" + this.benchUpgradeCompletedSoundEventId + "', benchUpgradeCompletedSoundEventIndex=" + this.benchUpgradeCompletedSoundEventIndex + "}";
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
/*     */   @Deprecated(forRemoval = true)
/*     */   public static void registerRootInteraction(BenchType benchType, RootInteraction interaction) {
/* 292 */     BENCH_INTERACTIONS.put(benchType, interaction);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class BenchSlot
/*     */   {
/*     */     public static final BuilderCodec<BenchSlot> CODEC;
/*     */     protected String icon;
/*     */     
/*     */     static {
/* 302 */       CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(BenchSlot.class, BenchSlot::new).addField(new KeyedCodec("Icon", (Codec)Codec.STRING), (benchSlot, s) -> benchSlot.icon = s, benchSlot -> benchSlot.icon)).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getIcon() {
/* 310 */       return this.icon;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object o) {
/* 315 */       if (this == o) return true; 
/* 316 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 318 */       BenchSlot benchSlot = (BenchSlot)o;
/*     */       
/* 320 */       return (this.icon != null) ? this.icon.equals(benchSlot.icon) : ((benchSlot.icon == null));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 325 */       return (this.icon != null) ? this.icon.hashCode() : 0;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 331 */       return "BenchSlot{icon='" + this.icon + "'}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\bench\Bench.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */