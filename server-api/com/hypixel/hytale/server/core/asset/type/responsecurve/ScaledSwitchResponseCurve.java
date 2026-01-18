/*    */ package com.hypixel.hytale.server.core.asset.type.responsecurve;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
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
/*    */ public class ScaledSwitchResponseCurve
/*    */   extends ScaledResponseCurve
/*    */ {
/*    */   public static final BuilderCodec<ScaledSwitchResponseCurve> CODEC;
/*    */   
/*    */   static {
/* 43 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ScaledSwitchResponseCurve.class, ScaledSwitchResponseCurve::new).documentation("A special type of scaled response curve which returns the initial state value before the defined switch point and the final state value after reaching it.")).append(new KeyedCodec("InitialState", (Codec)Codec.DOUBLE), (curve, d) -> curve.initialState = d.doubleValue(), curve -> Double.valueOf(curve.initialState)).addValidator(Validators.range(Double.valueOf(0.0D), Double.valueOf(1.0D))).documentation("The y value to return before the switch point.").add()).append(new KeyedCodec("FinalState", (Codec)Codec.DOUBLE), (curve, d) -> curve.finalState = d.doubleValue(), curve -> Double.valueOf(curve.finalState)).addValidator(Validators.range(Double.valueOf(0.0D), Double.valueOf(1.0D))).documentation("The y value to return at and beyond the switch point.").add()).append(new KeyedCodec("SwitchPoint", (Codec)Codec.DOUBLE), (curve, d) -> curve.switchPoint = d.doubleValue(), curve -> Double.valueOf(curve.switchPoint)).addValidator(Validators.nonNull()).documentation("The value at which to switch from the initial state to the final state.").add()).build();
/*    */   }
/* 45 */   protected double initialState = 0.0D;
/* 46 */   protected double finalState = 1.0D;
/*    */ 
/*    */   
/*    */   protected double switchPoint;
/*    */ 
/*    */ 
/*    */   
/*    */   public double computeY(double x) {
/* 54 */     return (x < this.switchPoint) ? this.initialState : this.finalState;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 60 */     return "ScaledSwitchResponseCurve{initialState=" + this.initialState + ", finalState=" + this.finalState + ", switchPoint=" + this.switchPoint + "}" + super
/*    */ 
/*    */ 
/*    */       
/* 64 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\responsecurve\ScaledSwitchResponseCurve.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */