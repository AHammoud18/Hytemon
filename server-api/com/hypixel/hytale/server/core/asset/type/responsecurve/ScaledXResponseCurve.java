/*    */ package com.hypixel.hytale.server.core.asset.type.responsecurve;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.server.core.asset.type.responsecurve.config.ResponseCurve;
/*    */ import java.util.Arrays;
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
/*    */ public class ScaledXResponseCurve
/*    */   extends ScaledResponseCurve
/*    */ {
/*    */   public static final BuilderCodec<ScaledXResponseCurve> CODEC;
/*    */   
/*    */   static {
/* 47 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ScaledXResponseCurve.class, ScaledXResponseCurve::new).documentation("A response curve scaled only on the x axis.")).append(new KeyedCodec("ResponseCurve", (Codec)Codec.STRING), (curve, s) -> curve.responseCurve = s, curve -> curve.responseCurve).documentation("The response curve to scale").addValidator(Validators.nonNull()).addValidator(ResponseCurve.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("XRange", (Codec)Codec.DOUBLE_ARRAY), (curve, o) -> curve.xRange = o, curve -> new double[] { curve.xRange[0], curve.xRange[1] }).documentation("The range to map the x axis to. e.g. [ 0, 10 ]").addValidator(Validators.doubleArraySize(2)).addValidator(Validators.monotonicSequentialDoubleArrayValidator()).add()).afterDecode(curve -> { if (curve.responseCurve != null) { int index = ResponseCurve.getAssetMap().getIndex(curve.responseCurve); curve.responseCurveReference = new ResponseCurve.Reference(index, (ResponseCurve)ResponseCurve.getAssetMap().getAsset(index)); }  })).build();
/*    */   }
/* 49 */   public static final double[] DEFAULT_RANGE = new double[] { 0.0D, 1.0D };
/*    */   
/*    */   protected String responseCurve;
/*    */   protected ResponseCurve.Reference responseCurveReference;
/* 53 */   protected double[] xRange = DEFAULT_RANGE;
/*    */   
/*    */   public ScaledXResponseCurve(String responseCurve, double[] xRange) {
/* 56 */     this.responseCurve = responseCurve;
/* 57 */     this.xRange = xRange;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getResponseCurve() {
/* 64 */     return this.responseCurve;
/*    */   }
/*    */   
/*    */   public double[] getXRange() {
/* 68 */     return this.xRange;
/*    */   }
/*    */ 
/*    */   
/*    */   public double computeY(double x) {
/* 73 */     return MathUtil.clamp(computeNormalisedY(x), 0.0D, 1.0D);
/*    */   }
/*    */   
/*    */   protected double computeNormalisedY(double x) {
/* 77 */     ResponseCurve curve = this.responseCurveReference.get();
/*    */     
/* 79 */     double minX = this.xRange[0];
/* 80 */     double maxX = this.xRange[1];
/* 81 */     x = MathUtil.clamp(x, minX, maxX);
/*    */     
/* 83 */     double normalisedX = (x - minX) / (maxX - minX);
/* 84 */     return curve.computeY(normalisedX);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 90 */     return "ScaledXResponseCurve{responseCurve=" + this.responseCurve + ", xRange=" + 
/*    */       
/* 92 */       Arrays.toString(this.xRange) + "}" + super
/* 93 */       .toString();
/*    */   }
/*    */   
/*    */   protected ScaledXResponseCurve() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\responsecurve\ScaledXResponseCurve.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */