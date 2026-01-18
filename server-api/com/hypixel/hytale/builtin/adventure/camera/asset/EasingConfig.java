/*    */ package com.hypixel.hytale.builtin.adventure.camera.asset;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.EasingConfig;
/*    */ import com.hypixel.hytale.protocol.EasingType;
/*    */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
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
/*    */ public class EasingConfig
/*    */   implements NetworkSerializable<EasingConfig>
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<EasingConfig> CODEC;
/*    */   
/*    */   static {
/* 31 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(EasingConfig.class, EasingConfig::new).appendInherited(new KeyedCodec("Time", (Codec)Codec.FLOAT), (o, v) -> o.time = v.floatValue(), o -> Float.valueOf(o.time), (o, p) -> o.time = p.time).documentation("The duration time of the easing").addValidator(Validators.min(Float.valueOf(0.0F))).add()).appendInherited(new KeyedCodec("Type", (Codec)ProtocolCodecs.EASING_TYPE_CODEC), (o, v) -> o.type = v, o -> o.type, (o, p) -> o.type = p.type).documentation("The curve type of the easing").addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 37 */   public static final EasingConfig NONE = new EasingConfig();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected float time;
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 47 */   protected EasingType type = EasingType.Linear;
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public EasingConfig toPacket() {
/* 53 */     return new EasingConfig(this.time, this.type);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 59 */     return "EasingConfig{time=" + this.time + ", type=" + String.valueOf(this.type) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\camera\asset\EasingConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */