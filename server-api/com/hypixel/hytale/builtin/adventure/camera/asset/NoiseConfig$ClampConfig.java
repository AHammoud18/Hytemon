/*     */ package com.hypixel.hytale.builtin.adventure.camera.asset;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.ClampConfig;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClampConfig
/*     */   implements NetworkSerializable<ClampConfig>
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<ClampConfig> CODEC;
/*     */   
/*     */   static {
/* 152 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ClampConfig.class, ClampConfig::new).appendInherited(new KeyedCodec("Min", (Codec)Codec.FLOAT), (o, v) -> o.min = v.floatValue(), o -> Float.valueOf(o.min), (o, p) -> o.min = p.min).documentation("The inclusive minimum value of the clamp range").addValidator(Validators.range(Float.valueOf(-1.0F), Float.valueOf(1.0F))).add()).appendInherited(new KeyedCodec("Max", (Codec)Codec.FLOAT), (o, v) -> o.max = v.floatValue(), o -> Float.valueOf(o.max), (o, p) -> o.max = p.max).documentation("The inclusive maximum value of the clamp range").addValidator(Validators.range(Float.valueOf(-1.0F), Float.valueOf(1.0F))).add()).appendInherited(new KeyedCodec("Normalize", (Codec)Codec.BOOLEAN), (o, v) -> o.normalize = v.booleanValue(), o -> Boolean.valueOf(o.normalize), (o, p) -> o.normalize = p.normalize).documentation("Rescales the clamped output value back to the range -1 to 1").add()).afterDecode(range -> { range.min = Math.min(range.min, range.max); range.max = Math.max(range.min, range.max); })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 158 */   public static final ClampConfig NONE = new ClampConfig();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 163 */   protected float min = -1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   protected float max = 1.0F;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean normalize = true;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ClampConfig toPacket() {
/* 178 */     return new ClampConfig(this.min, this.max, this.normalize);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 184 */     return "ClampConfig{min=" + this.min + ", max=" + this.max + ", normalize=" + this.normalize + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\camera\asset\NoiseConfig$ClampConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */