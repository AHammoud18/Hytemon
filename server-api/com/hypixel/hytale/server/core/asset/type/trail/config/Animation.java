/*    */ package com.hypixel.hytale.server.core.asset.type.trail.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.math.vector.Vector2i;
/*    */ import com.hypixel.hytale.protocol.Range;
/*    */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
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
/*    */ public class Animation
/*    */ {
/*    */   public static final BuilderCodec<Animation> CODEC;
/*    */   private Vector2i frameSize;
/*    */   private Range frameRange;
/*    */   private int frameLifeSpan;
/*    */   
/*    */   static {
/* 35 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Animation.class, Animation::new).appendInherited(new KeyedCodec("FrameSize", (Codec)Vector2i.CODEC), (animation, b) -> animation.frameSize = b, animation -> animation.frameSize, (animation, parent) -> animation.frameSize = parent.frameSize).add()).appendInherited(new KeyedCodec("FrameRange", (Codec)ProtocolCodecs.RANGE), (animation, b) -> animation.frameRange = b, animation -> animation.frameRange, (animation, parent) -> animation.frameRange = parent.frameRange).add()).appendInherited(new KeyedCodec("FrameLifeSpan", (Codec)Codec.INTEGER), (animation, i) -> animation.frameLifeSpan = i.intValue(), animation -> Integer.valueOf(animation.frameLifeSpan), (animation, parent) -> animation.frameLifeSpan = parent.frameLifeSpan).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Vector2i getFrameSize() {
/* 42 */     return this.frameSize;
/*    */   }
/*    */   
/*    */   public Range getFrameRange() {
/* 46 */     return this.frameRange;
/*    */   }
/*    */   
/*    */   public int getFrameLifeSpan() {
/* 50 */     return this.frameLifeSpan;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 56 */     return "Animation{frameSize=" + String.valueOf(this.frameSize) + ", frameRange=" + String.valueOf(this.frameRange) + ", frameLifeSpan=" + this.frameLifeSpan + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\trail\config\Animation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */