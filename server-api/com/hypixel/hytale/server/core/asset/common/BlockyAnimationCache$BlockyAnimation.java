/*     */ package com.hypixel.hytale.server.core.asset.common;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
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
/*     */ public class BlockyAnimation
/*     */ {
/*     */   public static final BuilderCodec<BlockyAnimation> CODEC;
/*     */   public static final double FRAMES_PER_SECOND = 60.0D;
/*     */   private int duration;
/*     */   
/*     */   static {
/*  91 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(BlockyAnimation.class, BlockyAnimation::new).addField(new KeyedCodec("duration", (Codec)Codec.INTEGER, true, true), (blockyAnimation, i) -> blockyAnimation.duration = i.intValue(), blockyAnimation -> Integer.valueOf(blockyAnimation.duration))).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDurationFrames() {
/* 101 */     return this.duration;
/*     */   }
/*     */   
/*     */   public double getDurationMillis() {
/* 105 */     return this.duration * 1000.0D / 60.0D;
/*     */   }
/*     */   
/*     */   public double getDurationSeconds() {
/* 109 */     return this.duration / 60.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 115 */     return "BlockyAnimation{duration=" + this.duration + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\common\BlockyAnimationCache$BlockyAnimation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */