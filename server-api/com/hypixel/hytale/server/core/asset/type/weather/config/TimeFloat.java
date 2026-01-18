/*    */ package com.hypixel.hytale.server.core.asset.type.weather.config;
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
/*    */ public class TimeFloat
/*    */ {
/*    */   public static final BuilderCodec<TimeFloat> CODEC;
/*    */   protected float hour;
/*    */   protected float value;
/*    */   
/*    */   static {
/* 22 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(TimeFloat.class, TimeFloat::new).append(new KeyedCodec("Hour", (Codec)Codec.DOUBLE), (timeFloat, i) -> timeFloat.hour = i.floatValue(), timeFloat -> Double.valueOf(timeFloat.getHour())).addValidator(Validators.range(Double.valueOf(0.0D), Double.valueOf(24.0D))).add()).addField(new KeyedCodec("Value", (Codec)Codec.DOUBLE), (timeFloat, d) -> timeFloat.value = d.floatValue(), timeFloat -> Double.valueOf(timeFloat.value))).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TimeFloat(float hour, float value) {
/* 28 */     this.hour = hour;
/* 29 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   protected TimeFloat() {}
/*    */   
/*    */   public float getHour() {
/* 36 */     return this.hour;
/*    */   }
/*    */   
/*    */   public float getValue() {
/* 40 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 46 */     return "TimeFloat{hour=" + this.hour + ", value='" + this.value + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\weather\config\TimeFloat.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */