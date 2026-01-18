/*     */ package com.hypixel.hytale.server.core.asset.type.responsecurve.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
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
/*     */ public class ExponentialResponseCurve
/*     */   extends ResponseCurve
/*     */ {
/*     */   public static final BuilderCodec<ExponentialResponseCurve> CODEC;
/*     */   
/*     */   static {
/*  51 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ExponentialResponseCurve.class, ExponentialResponseCurve::new, BASE_CODEC).documentation("An response curve which changes at an exponential rate.")).appendInherited(new KeyedCodec("Slope", (Codec)Codec.DOUBLE), (curve, d) -> curve.slope = d.doubleValue(), curve -> Double.valueOf(curve.slope), (curve, parent) -> curve.slope = parent.slope).documentation("The slope of the curve.").add()).appendInherited(new KeyedCodec("Exponent", (Codec)Codec.DOUBLE), (curve, d) -> curve.exponent = d.doubleValue(), curve -> Double.valueOf(curve.exponent), (curve, parent) -> curve.exponent = parent.exponent).documentation("The exponent used to generate this curve. 1 is linear, 2 results in a quadratic parabola, and 3 in a cubic curve, etc.").add()).appendInherited(new KeyedCodec("HorizontalShift", (Codec)Codec.DOUBLE), (curve, d) -> curve.horizontalShift = d.doubleValue(), curve -> Double.valueOf(curve.horizontalShift), (curve, parent) -> curve.horizontalShift = parent.horizontalShift).documentation("The horizontal shift to apply to the curve. This decides how far the curve is shifted left or right along the x axis.").addValidator(Validators.range(Double.valueOf(-1.0D), Double.valueOf(1.0D))).add()).appendInherited(new KeyedCodec("VerticalShift", (Codec)Codec.DOUBLE), (curve, d) -> curve.verticalShift = d.doubleValue(), curve -> Double.valueOf(curve.verticalShift), (curve, parent) -> curve.verticalShift = parent.verticalShift).documentation("The vertical shift to apply to the curve. This decides how far the curve is shifted up or down along the y axis.").addValidator(Validators.range(Double.valueOf(-1.0D), Double.valueOf(1.0D))).add()).build();
/*     */   }
/*  53 */   protected double slope = 1.0D;
/*  54 */   protected double exponent = 1.0D;
/*     */   protected double horizontalShift;
/*     */   protected double verticalShift;
/*     */   
/*     */   public ExponentialResponseCurve(double slope, double exponent, double horizontalShift, double verticalShift) {
/*  59 */     this.slope = slope;
/*  60 */     this.exponent = exponent;
/*  61 */     this.horizontalShift = horizontalShift;
/*  62 */     this.verticalShift = verticalShift;
/*     */   }
/*     */   
/*     */   public ExponentialResponseCurve(String id) {
/*  66 */     super(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double computeY(double x) {
/*  74 */     if (x < 0.0D || x > 1.0D) throw new IllegalArgumentException("X must be between 0.0 and 1.0");
/*     */     
/*  76 */     return this.slope * Math.pow(x - this.horizontalShift, this.exponent) + this.verticalShift;
/*     */   }
/*     */   
/*     */   public double getSlope() {
/*  80 */     return this.slope;
/*     */   }
/*     */   
/*     */   public double getExponent() {
/*  84 */     return this.exponent;
/*     */   }
/*     */   
/*     */   public double getHorizontalShift() {
/*  88 */     return this.horizontalShift;
/*     */   }
/*     */   
/*     */   public double getVerticalShift() {
/*  92 */     return this.verticalShift;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  98 */     return "ExponentialResponseCurve{slope=" + this.slope + ", exponent=" + this.exponent + ", horizontalShift=" + this.horizontalShift + ", verticalShift=" + this.verticalShift + "} " + super
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 103 */       .toString();
/*     */   }
/*     */   
/*     */   protected ExponentialResponseCurve() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\responsecurve\config\ExponentialResponseCurve.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */