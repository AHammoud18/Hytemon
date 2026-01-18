/*    */ package com.hypixel.hytale.server.core.asset.type.weather.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.ColorAlpha;
/*    */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TimeColorAlpha
/*    */ {
/*    */   public static final BuilderCodec<TimeColorAlpha> CODEC;
/*    */   public static final ArrayCodec<TimeColorAlpha> ARRAY_CODEC;
/*    */   protected float hour;
/*    */   protected ColorAlpha color;
/*    */   
/*    */   static {
/* 25 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(TimeColorAlpha.class, TimeColorAlpha::new).append(new KeyedCodec("Hour", (Codec)Codec.DOUBLE), (timeColorAlpha, i) -> timeColorAlpha.hour = i.floatValue(), timeColorAlpha -> Double.valueOf(timeColorAlpha.getHour())).addValidator(Validators.range(Double.valueOf(0.0D), Double.valueOf(24.0D))).add()).addField(new KeyedCodec("Color", (Codec)ProtocolCodecs.COLOR_AlPHA), (timeColorAlpha, o) -> timeColorAlpha.color = o, TimeColorAlpha::getColor)).build();
/* 26 */     ARRAY_CODEC = new ArrayCodec((Codec)CODEC, x$0 -> new TimeColorAlpha[x$0]);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TimeColorAlpha(float hour, ColorAlpha color) {
/* 32 */     this.hour = hour;
/* 33 */     this.color = color;
/*    */   }
/*    */ 
/*    */   
/*    */   protected TimeColorAlpha() {}
/*    */   
/*    */   public float getHour() {
/* 40 */     return this.hour;
/*    */   }
/*    */   
/*    */   public ColorAlpha getColor() {
/* 44 */     return this.color;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 50 */     return "TimeColorAlpha{hour=" + this.hour + ", color='" + String.valueOf(this.color) + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\weather\config\TimeColorAlpha.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */