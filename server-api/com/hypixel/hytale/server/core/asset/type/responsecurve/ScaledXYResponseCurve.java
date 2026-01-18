/*    */ package com.hypixel.hytale.server.core.asset.type.responsecurve;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
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
/*    */ public class ScaledXYResponseCurve
/*    */   extends ScaledXResponseCurve
/*    */ {
/*    */   public static final BuilderCodec<ScaledXYResponseCurve> CODEC;
/*    */   
/*    */   static {
/* 31 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ScaledXYResponseCurve.class, ScaledXYResponseCurve::new, ScaledXResponseCurve.CODEC).documentation("A response curve that is scaled on both the x and y axes.")).append(new KeyedCodec("YRange", (Codec)Codec.DOUBLE_ARRAY), (curve, o) -> curve.yRange = o, curve -> new double[] { curve.yRange[0], curve.yRange[1] }).documentation("The range to map the y axis to. e.g. [ 0, 10 ]").addValidator(Validators.doubleArraySize(2)).addValidator(Validators.monotonicSequentialDoubleArrayValidator()).add()).build();
/*    */   }
/* 33 */   protected double[] yRange = DEFAULT_RANGE;
/*    */   
/*    */   public ScaledXYResponseCurve(String responseCurve, double[] xRange, double[] yRange) {
/* 36 */     super(responseCurve, xRange);
/* 37 */     this.yRange = yRange;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] getYRange() {
/* 44 */     return this.yRange;
/*    */   }
/*    */ 
/*    */   
/*    */   public double computeY(double x) {
/* 49 */     double normalisedY = computeNormalisedY(x);
/* 50 */     double minY = this.yRange[0];
/* 51 */     double maxY = this.yRange[1];
/* 52 */     return MathUtil.clamp(minY + (maxY - minY) * normalisedY, minY, maxY);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 59 */     return "ScaledXYResponseCurve{yRange=" + Arrays.toString(this.yRange) + "} " + super
/* 60 */       .toString();
/*    */   }
/*    */   
/*    */   protected ScaledXYResponseCurve() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\responsecurve\ScaledXYResponseCurve.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */