/*    */ package com.hypixel.hytale.server.core.asset.type.responsecurve.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SineWaveResponseCurve
/*    */   extends ResponseCurve
/*    */ {
/*    */   public static final BuilderCodec<SineWaveResponseCurve> CODEC;
/*    */   
/*    */   static {
/* 52 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SineWaveResponseCurve.class, SineWaveResponseCurve::new, BASE_CODEC).documentation("A response curve with a sine wave shape.")).appendInherited(new KeyedCodec("Amplitude", (Codec)Codec.DOUBLE), (curve, d) -> curve.amplitude = d.doubleValue(), curve -> Double.valueOf(curve.amplitude), (curve, parent) -> curve.amplitude = parent.amplitude).documentation("The vertical distance between the horizontal axis and the max/min value of the function.").add()).appendInherited(new KeyedCodec("Frequency", (Codec)Codec.DOUBLE), (curve, d) -> curve.frequency = d.doubleValue(), curve -> Double.valueOf(curve.frequency), (curve, parent) -> curve.frequency = parent.frequency).documentation("The frequency of the sine wave's repetition (e.g. set to 1, the full pattern will appear once in the 0-1 range, twice with 2, etc).").add()).appendInherited(new KeyedCodec("HorizontalShift", (Codec)Codec.DOUBLE), (curve, d) -> curve.horizontalShift = d.doubleValue(), curve -> Double.valueOf(curve.horizontalShift), (curve, parent) -> curve.horizontalShift = parent.horizontalShift).documentation("The horizontal shift to apply to the curve,. This decides how far the curve is shifted left or right along the x axis.").addValidator(Validators.range(Double.valueOf(-1.0D), Double.valueOf(1.0D))).add()).appendInherited(new KeyedCodec("VerticalShift", (Codec)Codec.DOUBLE), (curve, d) -> curve.verticalShift = d.doubleValue(), curve -> Double.valueOf(curve.verticalShift), (curve, parent) -> curve.verticalShift = parent.verticalShift).documentation("The vertical shift to apply to the curve. This decides how far the curve is shifted up or down along the y axis.").addValidator(Validators.range(Double.valueOf(-1.0D), Double.valueOf(1.0D))).add()).build();
/*    */   }
/* 54 */   protected double amplitude = 1.0D;
/* 55 */   protected double frequency = 0.5D;
/*    */ 
/*    */   
/*    */   protected double horizontalShift;
/*    */   
/*    */   protected double verticalShift;
/*    */ 
/*    */   
/*    */   public double computeY(double x) {
/* 64 */     if (x < 0.0D || x > 1.0D) throw new IllegalArgumentException("X must be between 0.0 and 1.0");
/*    */     
/* 66 */     return this.amplitude * TrigMathUtil.sin(6.2831854820251465D * this.frequency * x + this.horizontalShift) + this.verticalShift;
/*    */   }
/*    */   
/*    */   public double getAmplitude() {
/* 70 */     return this.amplitude;
/*    */   }
/*    */   
/*    */   public double getFrequency() {
/* 74 */     return this.frequency;
/*    */   }
/*    */   
/*    */   public double getHorizontalShift() {
/* 78 */     return this.horizontalShift;
/*    */   }
/*    */   
/*    */   public double getVerticalShift() {
/* 82 */     return this.verticalShift;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 88 */     return "SineWaveResponseCurve{amplitude=" + this.amplitude + ", frequency=" + this.frequency + ", horizontalShift=" + this.horizontalShift + ", verticalShift=" + this.verticalShift + "} " + super
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 93 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\responsecurve\config\SineWaveResponseCurve.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */