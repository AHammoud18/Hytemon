/*     */ package com.hypixel.hytale.builtin.adventure.camera.asset.cameraeffect;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
/*     */ import java.util.Arrays;
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
/*     */ public class Modifier
/*     */   implements FloatUnaryOperator
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<Modifier> CODEC;
/*     */   private float[] input;
/*     */   private float[] output;
/*     */   
/*     */   static {
/* 140 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Modifier.class, Modifier::new).appendInherited(new KeyedCodec("Input", (Codec)Codec.FLOAT_ARRAY), (modifier, v) -> modifier.input = v, modifier -> modifier.input, (modifier, parent) -> modifier.input = parent.input).addValidator(Validators.nonEmptyFloatArray()).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Output", (Codec)Codec.FLOAT_ARRAY), (modifier, v) -> modifier.output = v, modifier -> modifier.output, (modifier, parent) -> modifier.output = parent.output).addValidator(Validators.nonEmptyFloatArray()).addValidator(Validators.nonNull()).add()).build();
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
/*     */   public float apply(float intensityContext) {
/* 154 */     float inputMin = this.input[0];
/* 155 */     float outputMin = this.output[0];
/*     */     
/* 157 */     if (intensityContext < inputMin) return outputMin;
/*     */     
/* 159 */     int length = Math.min(this.input.length, this.output.length);
/* 160 */     for (int i = 1; i < length; i++) {
/* 161 */       float inputMax = this.input[i];
/* 162 */       float outputMax = this.output[i];
/*     */       
/* 164 */       if (intensityContext > inputMax) {
/* 165 */         inputMin = inputMax;
/* 166 */         outputMin = outputMax;
/*     */       }
/*     */       else {
/*     */         
/* 170 */         return MathUtil.mapToRange(intensityContext, inputMin, inputMax, outputMin, outputMax);
/*     */       } 
/*     */     } 
/* 173 */     return outputMin;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 180 */     return "Modifier{input=" + Arrays.toString(this.input) + ", output=" + 
/* 181 */       Arrays.toString(this.output) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\camera\asset\cameraeffect\ShakeIntensity$Modifier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */