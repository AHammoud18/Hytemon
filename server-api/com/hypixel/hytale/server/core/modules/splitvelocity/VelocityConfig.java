/*     */ package com.hypixel.hytale.server.core.modules.splitvelocity;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.VelocityConfig;
/*     */ import com.hypixel.hytale.protocol.VelocityThresholdStyle;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VelocityConfig
/*     */   implements NetworkSerializable<VelocityConfig>
/*     */ {
/*     */   @Nonnull
/*     */   public static BuilderCodec<VelocityConfig> CODEC;
/*     */   
/*     */   static {
/*  65 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(VelocityConfig.class, VelocityConfig::new).appendInherited(new KeyedCodec("GroundResistance", (Codec)Codec.FLOAT), (o, i) -> o.groundResistance = i.floatValue(), o -> Float.valueOf(o.groundResistance), (o, p) -> o.groundResistance = p.groundResistance).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F))).add()).appendInherited(new KeyedCodec("GroundResistanceMax", (Codec)Codec.FLOAT), (o, i) -> o.groundResistanceMax = i.floatValue(), o -> Float.valueOf(o.groundResistanceMax), (o, p) -> o.groundResistanceMax = p.groundResistanceMax).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F))).add()).appendInherited(new KeyedCodec("AirResistance", (Codec)Codec.FLOAT), (o, i) -> o.airResistance = i.floatValue(), o -> Float.valueOf(o.airResistance), (o, p) -> o.airResistance = p.airResistance).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F))).add()).appendInherited(new KeyedCodec("AirResistanceMax", (Codec)Codec.FLOAT), (o, i) -> o.airResistanceMax = i.floatValue(), o -> Float.valueOf(o.airResistanceMax), (o, p) -> o.airResistance = p.airResistanceMax).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F))).add()).appendInherited(new KeyedCodec("Threshold", (Codec)Codec.FLOAT), (o, i) -> o.threshold = i.floatValue(), o -> Float.valueOf(o.threshold), (o, p) -> o.threshold = p.threshold).documentation("The threshold of the velocity's length before resistance starts to transition to the Max values (if set)").add()).appendInherited(new KeyedCodec("Style", (Codec)new EnumCodec(VelocityThresholdStyle.class)), (o, i) -> o.style = i, o -> o.style, (o, p) -> o.style = p.style).documentation("Whether the transition from min to max resistance values should be linear or not").add()).build();
/*     */   }
/*  67 */   private float groundResistance = 0.82F;
/*  68 */   private float groundResistanceMax = 0.0F;
/*  69 */   private float airResistance = 0.96F;
/*  70 */   private float airResistanceMax = 0.0F;
/*  71 */   private float threshold = 1.0F;
/*  72 */   private VelocityThresholdStyle style = VelocityThresholdStyle.Linear;
/*     */   
/*     */   public float getGroundResistance() {
/*  75 */     return this.groundResistance;
/*     */   }
/*     */   
/*     */   public float getAirResistance() {
/*  79 */     return this.airResistance;
/*     */   }
/*     */   
/*     */   public float getGroundResistanceMax() {
/*  83 */     return this.groundResistanceMax;
/*     */   }
/*     */   
/*     */   public float getAirResistanceMax() {
/*  87 */     return this.airResistanceMax;
/*     */   }
/*     */   
/*     */   public float getThreshold() {
/*  91 */     return this.threshold;
/*     */   }
/*     */   
/*     */   public VelocityThresholdStyle getStyle() {
/*  95 */     return this.style;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public VelocityConfig toPacket() {
/* 101 */     return new VelocityConfig(this.groundResistance, this.groundResistanceMax, this.airResistance, this.airResistanceMax, this.threshold, this.style);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\splitvelocity\VelocityConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */