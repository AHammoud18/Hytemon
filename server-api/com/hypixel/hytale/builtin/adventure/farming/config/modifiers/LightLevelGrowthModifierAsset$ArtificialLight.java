/*     */ package com.hypixel.hytale.builtin.adventure.farming.config.modifiers;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.protocol.Range;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
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
/*     */ public class ArtificialLight
/*     */ {
/*     */   public static final BuilderCodec<ArtificialLight> CODEC;
/*     */   protected Range red;
/*     */   protected Range green;
/*     */   protected Range blue;
/*     */   
/*     */   static {
/* 145 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ArtificialLight.class, ArtificialLight::new).addField(new KeyedCodec("Red", (Codec)ProtocolCodecs.RANGE), (light, red) -> light.red = red, light -> light.red)).addField(new KeyedCodec("Green", (Codec)ProtocolCodecs.RANGE), (light, green) -> light.green = green, light -> light.green)).addField(new KeyedCodec("Blue", (Codec)ProtocolCodecs.RANGE), (light, blue) -> light.blue = blue, light -> light.blue)).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Range getRed() {
/* 151 */     return this.red;
/*     */   }
/*     */   
/*     */   public Range getGreen() {
/* 155 */     return this.green;
/*     */   }
/*     */   
/*     */   public Range getBlue() {
/* 159 */     return this.blue;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 165 */     return "ArtificialLightLevel{red=" + String.valueOf(this.red) + ", green=" + String.valueOf(this.green) + ", blue=" + String.valueOf(this.blue) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\config\modifiers\LightLevelGrowthModifierAsset$ArtificialLight.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */