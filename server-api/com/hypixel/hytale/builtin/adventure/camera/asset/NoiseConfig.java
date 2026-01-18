/*     */ package com.hypixel.hytale.builtin.adventure.camera.asset;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDefaultCollapsedState;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.NoiseConfig;
/*     */ import com.hypixel.hytale.protocol.NoiseType;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NoiseConfig
/*     */   implements NetworkSerializable<NoiseConfig>
/*     */ {
/*     */   @Nonnull
/*  25 */   public static final Codec<NoiseType> NOISE_TYPE_CODEC = (Codec<NoiseType>)new EnumCodec(NoiseType.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static final BuilderCodec<NoiseConfig> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static final ArrayCodec<NoiseConfig> ARRAY_CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  49 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(NoiseConfig.class, NoiseConfig::new).appendInherited(new KeyedCodec("Seed", (Codec)Codec.INTEGER), (o, v) -> o.seed = v.intValue(), o -> Integer.valueOf(o.seed), (o, p) -> o.seed = p.seed).documentation("The value used to seed the noise source").add()).appendInherited(new KeyedCodec("Type", NOISE_TYPE_CODEC), (o, v) -> o.type = v, o -> o.type, (o, p) -> o.type = p.type).documentation("The type of noise used to move the camera").addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Frequency", (Codec)Codec.FLOAT), (o, v) -> o.frequency = v.floatValue(), o -> Float.valueOf(o.frequency), (o, p) -> o.frequency = p.frequency).documentation("The frequency at which the noise source is sampled").add()).appendInherited(new KeyedCodec("Amplitude", (Codec)Codec.FLOAT), (o, v) -> o.amplitude = v.floatValue(), o -> Float.valueOf(o.amplitude), (o, p) -> o.amplitude = p.amplitude).documentation("The maximum extent of the noise source output").add()).appendInherited(new KeyedCodec("Clamp", (Codec)ClampConfig.CODEC), (o, v) -> o.clamp = v, o -> o.clamp, (o, p) -> o.clamp = p.clamp).documentation("Restricts the range of values that the noise source can output").metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  55 */     ARRAY_CODEC = ArrayCodec.ofBuilderCodec(CODEC, x$0 -> new NoiseConfig[x$0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  61 */   public static final NoiseConfig[] NOISE_CONFIGS = new NoiseConfig[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int seed;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  71 */   protected NoiseType type = NoiseType.Sin;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  77 */   protected ClampConfig clamp = ClampConfig.NONE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float frequency;
/*     */ 
/*     */ 
/*     */   
/*     */   protected float amplitude;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public NoiseConfig toPacket() {
/*  93 */     return new NoiseConfig(this.seed, this.type, this.frequency, this.amplitude, this.clamp.toPacket());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  99 */     return "NoiseConfig{seed=" + this.seed + ", type=" + String.valueOf(this.type) + ", clamp=" + String.valueOf(this.clamp) + ", frequency=" + this.frequency + ", amplitude=" + this.amplitude + "}";
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
/*     */   @Nonnull
/*     */   public static NoiseConfig[] toPacket(@Nullable NoiseConfig[] configs) {
/* 116 */     if (configs == null || configs.length == 0) return NOISE_CONFIGS;
/*     */     
/* 118 */     NoiseConfig[] result = new NoiseConfig[configs.length];
/* 119 */     for (int i = 0; i < configs.length; i++) {
/* 120 */       NoiseConfig config = configs[i];
/* 121 */       if (config != null) result[i] = config.toPacket();
/*     */     
/*     */     } 
/* 124 */     return result;
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
/*     */   public static class ClampConfig
/*     */     implements NetworkSerializable<com.hypixel.hytale.protocol.ClampConfig>
/*     */   {
/*     */     @Nonnull
/*     */     public static final BuilderCodec<ClampConfig> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 152 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ClampConfig.class, ClampConfig::new).appendInherited(new KeyedCodec("Min", (Codec)Codec.FLOAT), (o, v) -> o.min = v.floatValue(), o -> Float.valueOf(o.min), (o, p) -> o.min = p.min).documentation("The inclusive minimum value of the clamp range").addValidator(Validators.range(Float.valueOf(-1.0F), Float.valueOf(1.0F))).add()).appendInherited(new KeyedCodec("Max", (Codec)Codec.FLOAT), (o, v) -> o.max = v.floatValue(), o -> Float.valueOf(o.max), (o, p) -> o.max = p.max).documentation("The inclusive maximum value of the clamp range").addValidator(Validators.range(Float.valueOf(-1.0F), Float.valueOf(1.0F))).add()).appendInherited(new KeyedCodec("Normalize", (Codec)Codec.BOOLEAN), (o, v) -> o.normalize = v.booleanValue(), o -> Boolean.valueOf(o.normalize), (o, p) -> o.normalize = p.normalize).documentation("Rescales the clamped output value back to the range -1 to 1").add()).afterDecode(range -> { range.min = Math.min(range.min, range.max); range.max = Math.max(range.min, range.max); })).build();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 158 */     public static final ClampConfig NONE = new ClampConfig();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     protected float min = -1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 168 */     protected float max = 1.0F;
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean normalize = true;
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public com.hypixel.hytale.protocol.ClampConfig toPacket() {
/* 178 */       return new com.hypixel.hytale.protocol.ClampConfig(this.min, this.max, this.normalize);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 184 */       return "ClampConfig{min=" + this.min + ", max=" + this.max + ", normalize=" + this.normalize + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\camera\asset\NoiseConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */