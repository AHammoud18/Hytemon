/*     */ package com.hypixel.hytale.server.core.asset.type.particle.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.protocol.ParticleAnimationFrame;
/*     */ import com.hypixel.hytale.protocol.Range;
/*     */ import com.hypixel.hytale.protocol.RangeVector2f;
/*     */ import com.hypixel.hytale.protocol.RangeVector3f;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
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
/*     */ public class ParticleAnimationFrame
/*     */   implements NetworkSerializable<ParticleAnimationFrame>
/*     */ {
/*     */   public static final int UNASSIGNED_OPACITY = -1;
/*     */   public static final BuilderCodec<ParticleAnimationFrame> CODEC;
/*     */   protected Range frameIndex;
/*     */   protected RangeVector2f scale;
/*     */   protected RangeVector3f rotation;
/*     */   protected Color color;
/*     */   
/*     */   static {
/*  50 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ParticleAnimationFrame.class, ParticleAnimationFrame::new).addField(new KeyedCodec("FrameIndex", (Codec)ProtocolCodecs.RANGE), (animationFrame, s) -> animationFrame.frameIndex = s, animationFrame -> animationFrame.frameIndex)).addField(new KeyedCodec("Scale", (Codec)ProtocolCodecs.RANGE_VECTOR2F), (animationFrame, o) -> animationFrame.scale = o, animationFrame -> animationFrame.scale)).addField(new KeyedCodec("Rotation", (Codec)ProtocolCodecs.RANGE_VECTOR3F), (animationFrame, o) -> animationFrame.rotation = o, animationFrame -> animationFrame.rotation)).addField(new KeyedCodec("Color", (Codec)ProtocolCodecs.COLOR), (animationFrame, o) -> animationFrame.color = o, animationFrame -> animationFrame.color)).append(new KeyedCodec("Opacity", (Codec)Codec.FLOAT), (animationFrame, f) -> animationFrame.opacity = f.floatValue(), animationFrame -> Float.valueOf(animationFrame.opacity)).addValidator(Validators.or(new Validator[] { Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F)), Validators.equal(Float.valueOf(-1.0F)) })).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   protected float opacity = -1.0F;
/*     */   
/*     */   public ParticleAnimationFrame(Range frameIndex, RangeVector2f scale, RangeVector3f rotation, Color color, float opacity) {
/*  59 */     this.frameIndex = frameIndex;
/*  60 */     this.scale = scale;
/*  61 */     this.rotation = rotation;
/*  62 */     this.color = color;
/*  63 */     this.opacity = opacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Range getFrameIndex() {
/*  70 */     return this.frameIndex;
/*     */   }
/*     */   
/*     */   public RangeVector2f getScale() {
/*  74 */     return this.scale;
/*     */   }
/*     */   
/*     */   public RangeVector3f getRotation() {
/*  78 */     return this.rotation;
/*     */   }
/*     */   
/*     */   public Color getColor() {
/*  82 */     return this.color;
/*     */   }
/*     */   
/*     */   public float getOpacity() {
/*  86 */     return this.opacity;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ParticleAnimationFrame toPacket() {
/*  92 */     ParticleAnimationFrame packet = new ParticleAnimationFrame();
/*     */     
/*  94 */     packet.frameIndex = this.frameIndex;
/*  95 */     packet.scale = this.scale;
/*  96 */     packet.rotation = this.rotation;
/*  97 */     packet.color = this.color;
/*  98 */     packet.opacity = this.opacity;
/*     */     
/* 100 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 106 */     return "ParticleAnimationFrame{frameIndex=" + String.valueOf(this.frameIndex) + ", scale=" + String.valueOf(this.scale) + ", rotation=" + String.valueOf(this.rotation) + ", color=" + String.valueOf(this.color) + ", opacity=" + this.opacity + "}";
/*     */   }
/*     */   
/*     */   protected ParticleAnimationFrame() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\particle\config\ParticleAnimationFrame.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */