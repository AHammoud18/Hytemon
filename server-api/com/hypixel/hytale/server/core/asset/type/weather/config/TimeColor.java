/*    */ package com.hypixel.hytale.server.core.asset.type.weather.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.Color;
/*    */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TimeColor
/*    */ {
/*    */   public static final BuilderCodec<TimeColor> CODEC;
/*    */   public static final ArrayCodec<TimeColor> ARRAY_CODEC;
/*    */   protected float hour;
/*    */   protected Color color;
/*    */   
/*    */   static {
/* 25 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(TimeColor.class, TimeColor::new).append(new KeyedCodec("Hour", (Codec)Codec.DOUBLE), (timeColor, i) -> timeColor.hour = i.floatValue(), timeColor -> Double.valueOf(timeColor.getHour())).addValidator(Validators.range(Double.valueOf(0.0D), Double.valueOf(24.0D))).add()).addField(new KeyedCodec("Color", (Codec)ProtocolCodecs.COLOR), (timeColor, o) -> timeColor.color = o, TimeColor::getColor)).build();
/* 26 */     ARRAY_CODEC = new ArrayCodec((Codec)CODEC, x$0 -> new TimeColor[x$0]);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TimeColor(float hour, Color color) {
/* 32 */     this.hour = hour;
/* 33 */     this.color = color;
/*    */   }
/*    */ 
/*    */   
/*    */   protected TimeColor() {}
/*    */   
/*    */   public float getHour() {
/* 40 */     return this.hour;
/*    */   }
/*    */   
/*    */   public Color getColor() {
/* 44 */     return this.color;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 50 */     return "TimeColor{hour=" + this.hour + ", color='" + String.valueOf(this.color) + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\weather\config\TimeColor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */