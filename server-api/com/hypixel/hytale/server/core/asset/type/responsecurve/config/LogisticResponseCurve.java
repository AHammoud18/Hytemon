/*    */ package com.hypixel.hytale.server.core.asset.type.responsecurve.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
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
/*    */ 
/*    */ public class LogisticResponseCurve
/*    */   extends ResponseCurve
/*    */ {
/*    */   public static final BuilderCodec<LogisticResponseCurve> CODEC;
/*    */   
/*    */   static {
/* 51 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(LogisticResponseCurve.class, LogisticResponseCurve::new, BASE_CODEC).documentation("A response curve with a logistic rate of change.")).appendInherited(new KeyedCodec("RateOfChange", (Codec)Codec.DOUBLE), (curve, d) -> curve.rateOfChange = d.doubleValue(), curve -> Double.valueOf(curve.rateOfChange), (curve, parent) -> curve.rateOfChange = parent.rateOfChange).documentation("The rate of change of the curve - similar to **Slope** in the exponential curve.").add()).appendInherited(new KeyedCodec("Ceiling", (Codec)Codec.DOUBLE), (curve, d) -> curve.ceiling = d.doubleValue(), curve -> Double.valueOf(curve.ceiling), (curve, parent) -> curve.ceiling = parent.ceiling).documentation("The total height of the curve between its two plateaus. Using a negative value with vertical offsets allows the curve to act as a diminishing factor").add()).appendInherited(new KeyedCodec("HorizontalShift", (Codec)Codec.DOUBLE), (curve, d) -> curve.horizontalShift = d.doubleValue(), curve -> Double.valueOf(curve.horizontalShift), (curve, parent) -> curve.horizontalShift = parent.horizontalShift).documentation("The horizontal shift to apply to the curve. This decides how far the curve is shifted left or right along the x axis.").add()).appendInherited(new KeyedCodec("VerticalShift", (Codec)Codec.DOUBLE), (curve, d) -> curve.verticalShift = d.doubleValue(), curve -> Double.valueOf(curve.verticalShift), (curve, parent) -> curve.verticalShift = parent.verticalShift).documentation("The vertical shift to apply to the curve. This decides how far the curve is shifted up or down along the y axis.").add()).build();
/*    */   }
/* 53 */   protected double rateOfChange = 1.0D;
/* 54 */   protected double ceiling = 1.0D;
/* 55 */   protected double horizontalShift = 0.5D;
/* 56 */   protected double verticalShift = 0.0D;
/*    */   
/*    */   public LogisticResponseCurve(double rateOfChange, double ceiling, double horizontalShift, double verticalShift) {
/* 59 */     this.rateOfChange = rateOfChange;
/* 60 */     this.ceiling = ceiling;
/* 61 */     this.horizontalShift = horizontalShift;
/* 62 */     this.verticalShift = verticalShift;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double computeY(double x) {
/* 70 */     if (x < 0.0D || x > 1.0D) throw new IllegalArgumentException("X must be between 0.0 and 1.0");
/*    */     
/* 72 */     return this.ceiling / (1.0D + Math.pow(100.0D, 2.0D * this.rateOfChange * (this.horizontalShift - x))) + this.verticalShift;
/*    */   }
/*    */   
/*    */   public double getRateOfChange() {
/* 76 */     return this.rateOfChange;
/*    */   }
/*    */   
/*    */   public double getCeiling() {
/* 80 */     return this.ceiling;
/*    */   }
/*    */   
/*    */   public double getHorizontalShift() {
/* 84 */     return this.horizontalShift;
/*    */   }
/*    */   
/*    */   public double getVerticalShift() {
/* 88 */     return this.verticalShift;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 94 */     return "LogisticResponseCurve{rateOfChange=" + this.rateOfChange + ", ceiling=" + this.ceiling + ", horizontalShift=" + this.horizontalShift + ", verticalShift=" + this.verticalShift + "} " + super
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 99 */       .toString();
/*    */   }
/*    */   
/*    */   protected LogisticResponseCurve() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\responsecurve\config\LogisticResponseCurve.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */