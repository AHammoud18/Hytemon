/*    */ package com.hypixel.hytale.server.core.asset.type.weather.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
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
/*    */ 
/*    */ 
/*    */ public class FogOptions
/*    */ {
/*    */   public static final BuilderCodec<FogOptions> CODEC;
/*    */   
/*    */   static {
/* 52 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FogOptions.class, FogOptions::new).appendInherited(new KeyedCodec("IgnoreFogLimits", (Codec)Codec.BOOLEAN), (opt, s) -> opt.ignoreFogLimits = s.booleanValue(), opt -> Boolean.valueOf(opt.ignoreFogLimits), (opt, parent) -> opt.ignoreFogLimits = parent.ignoreFogLimits).documentation("The client has a default minimum AND maximum for \"FogFar\". Toggling this on will allow your FogDistance[1] to bypass those limits.").add()).appendInherited(new KeyedCodec("EffectiveViewDistanceMultiplier", (Codec)Codec.FLOAT), (opt, s) -> opt.effectiveViewDistanceMultiplier = s.floatValue(), opt -> Float.valueOf(opt.effectiveViewDistanceMultiplier), (opt, parent) -> opt.effectiveViewDistanceMultiplier = parent.effectiveViewDistanceMultiplier).documentation("The client's default cap for FogDistance[1] (aka FogFar) is the effective view distance, meaning the farthest viewable chunk. This value (defaults 1.0) multiplies that cap. For example with high fog density, you can afford a fog multiplier of 1.3 as the cutoff of unloaded chunks may still be hidden.").add()).appendInherited(new KeyedCodec("FogHeightCameraFixed", (Codec)Codec.FLOAT), (opt, s) -> opt.fogHeightCameraFixed = s, opt -> opt.fogHeightCameraFixed, (opt, parent) -> opt.fogHeightCameraFixed = parent.fogHeightCameraFixed).documentation("By default, the client has e^(-FogHeightFalloff * ~Camera.Y) height-based fog. This adds significant fog near Camera.Y = 0. By setting this value (between 0.0 and 1.0), the Exp function is bypassed and there will be a fixed fog for height in the fog shader.").add()).appendInherited(new KeyedCodec("FogHeightCameraOffset", (Codec)Codec.FLOAT), (opt, s) -> opt.fogHeightCameraOffset = s.floatValue(), opt -> Float.valueOf(opt.fogHeightCameraOffset), (opt, parent) -> opt.fogHeightCameraOffset = parent.fogHeightCameraOffset).documentation("By default, the client has e^(-FogHeightFalloff * ~Camera.Y) height-based fog. This adds significant fog near Camera.Y = 0. The FogHeightCameraOffset is added to the Camera.Y.").add()).build();
/*    */   }
/*    */   
/* 55 */   private float effectiveViewDistanceMultiplier = 1.0F; private boolean ignoreFogLimits = false;
/* 56 */   private Float fogHeightCameraFixed = null;
/* 57 */   private float fogHeightCameraOffset = 0.0F;
/*    */   
/*    */   public boolean isIgnoreFogLimits() {
/* 60 */     return this.ignoreFogLimits;
/*    */   }
/*    */   
/*    */   public float getEffectiveViewDistanceMultiplier() {
/* 64 */     return this.effectiveViewDistanceMultiplier;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Float getFogHeightCameraFixed() {
/* 69 */     return this.fogHeightCameraFixed;
/*    */   }
/*    */   
/*    */   public float getFogHeightCameraOffset() {
/* 73 */     return this.fogHeightCameraOffset;
/*    */   }
/*    */   
/*    */   public com.hypixel.hytale.protocol.FogOptions toPacket() {
/* 77 */     com.hypixel.hytale.protocol.FogOptions proto = new com.hypixel.hytale.protocol.FogOptions();
/* 78 */     proto.ignoreFogLimits = this.ignoreFogLimits;
/* 79 */     proto.effectiveViewDistanceMultiplier = this.effectiveViewDistanceMultiplier;
/* 80 */     if (this.fogHeightCameraFixed == null) {
/* 81 */       proto.fogHeightCameraOverriden = false;
/*    */     } else {
/* 83 */       proto.fogHeightCameraOverriden = true;
/* 84 */       proto.fogHeightCameraFixed = this.fogHeightCameraFixed.floatValue();
/*    */     } 
/* 86 */     proto.fogHeightCameraOffset = this.fogHeightCameraOffset;
/* 87 */     return proto;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 92 */     return "FogOptions{ignoreFogLimits=" + this.ignoreFogLimits + ", effectiveViewDistanceMultiplier=" + this.effectiveViewDistanceMultiplier + ", fogHeightCameraFixed=" + this.fogHeightCameraFixed + ", fogHeightCameraOffset=" + this.fogHeightCameraOffset + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\weather\config\FogOptions.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */