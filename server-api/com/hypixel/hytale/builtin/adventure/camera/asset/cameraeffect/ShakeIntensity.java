/*     */ package com.hypixel.hytale.builtin.adventure.camera.asset.cameraeffect;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.protocol.AccumulationMode;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
/*     */ import java.util.Arrays;
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
/*     */ public class ShakeIntensity
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<ShakeIntensity> CODEC;
/*     */   
/*     */   static {
/*  51 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ShakeIntensity.class, ShakeIntensity::new).appendInherited(new KeyedCodec("Value", (Codec)Codec.FLOAT), (cameraShakeEffect, s) -> cameraShakeEffect.value = s.floatValue(), cameraShakeEffect -> Float.valueOf(cameraShakeEffect.value), (cameraShakeEffect, parent) -> cameraShakeEffect.value = parent.value).documentation("The intensity used when no contextual value (such as damage) is present.").addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F))).add()).appendInherited(new KeyedCodec("AccumulationMode", (Codec)ProtocolCodecs.ACCUMULATION_MODE_CODEC), (intensity, mode) -> intensity.accumulationMode = mode, intensity -> intensity.accumulationMode, (intensity, parent) -> intensity.accumulationMode = parent.accumulationMode).documentation("The method by which intensity is combined when multiple instances of the same camera effect overlap.").addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Modifier", (Codec)Modifier.CODEC), (intensity, modifier) -> intensity.modifier = modifier, intensity -> intensity.modifier, (intensity, parent) -> intensity.modifier = parent.modifier).documentation("Converts a contextual-intensity value (such as damage) to a camera shake intensity value.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  57 */   static final AccumulationMode DEFAULT_ACCUMULATION_MODE = AccumulationMode.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float DEFAULT_CONTEXT_VALUE = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   protected float value = 0.0F;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  72 */   protected AccumulationMode accumulationMode = DEFAULT_ACCUMULATION_MODE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Modifier modifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getValue() {
/*  85 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public AccumulationMode getAccumulationMode() {
/*  93 */     return this.accumulationMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Modifier getModifier() {
/* 101 */     return this.modifier;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 107 */     return "ShakeIntensity{value=" + this.value + ", accumulationMode=" + String.valueOf(this.accumulationMode) + ", modifier=" + String.valueOf(this.modifier) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Modifier
/*     */     implements FloatUnaryOperator
/*     */   {
/*     */     @Nonnull
/*     */     public static final BuilderCodec<Modifier> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private float[] input;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private float[] output;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 140 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Modifier.class, Modifier::new).appendInherited(new KeyedCodec("Input", (Codec)Codec.FLOAT_ARRAY), (modifier, v) -> modifier.input = v, modifier -> modifier.input, (modifier, parent) -> modifier.input = parent.input).addValidator(Validators.nonEmptyFloatArray()).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Output", (Codec)Codec.FLOAT_ARRAY), (modifier, v) -> modifier.output = v, modifier -> modifier.output, (modifier, parent) -> modifier.output = parent.output).addValidator(Validators.nonEmptyFloatArray()).addValidator(Validators.nonNull()).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float apply(float intensityContext) {
/* 154 */       float inputMin = this.input[0];
/* 155 */       float outputMin = this.output[0];
/*     */       
/* 157 */       if (intensityContext < inputMin) return outputMin;
/*     */       
/* 159 */       int length = Math.min(this.input.length, this.output.length);
/* 160 */       for (int i = 1; i < length; i++) {
/* 161 */         float inputMax = this.input[i];
/* 162 */         float outputMax = this.output[i];
/*     */         
/* 164 */         if (intensityContext > inputMax) {
/* 165 */           inputMin = inputMax;
/* 166 */           outputMin = outputMax;
/*     */         }
/*     */         else {
/*     */           
/* 170 */           return MathUtil.mapToRange(intensityContext, inputMin, inputMax, outputMin, outputMax);
/*     */         } 
/*     */       } 
/* 173 */       return outputMin;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 180 */       return "Modifier{input=" + Arrays.toString(this.input) + ", output=" + 
/* 181 */         Arrays.toString(this.output) + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\camera\asset\cameraeffect\ShakeIntensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */