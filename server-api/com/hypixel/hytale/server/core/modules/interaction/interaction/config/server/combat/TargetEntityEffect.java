/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.map.Object2DoubleMapCodec;
/*    */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.OverlapBehavior;
/*    */ import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
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
/*    */ public class TargetEntityEffect
/*    */ {
/*    */   public static final BuilderCodec<TargetEntityEffect> CODEC;
/*    */   protected float duration;
/*    */   
/*    */   static {
/* 40 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(TargetEntityEffect.class, TargetEntityEffect::new).addField(new KeyedCodec("Duration", (Codec)Codec.DOUBLE), (target, value) -> target.duration = value.floatValue(), target -> Double.valueOf(target.duration))).addField(new KeyedCodec("Chance", (Codec)Codec.DOUBLE), (target, value) -> target.chance = value.doubleValue(), target -> Double.valueOf(target.chance))).addField(new KeyedCodec("EntityTypeDurationModifiers", (Codec)new Object2DoubleMapCodec((Codec)Codec.STRING, it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap::new)), (target, map) -> target.entityTypeDurationModifiers = map, target -> target.entityTypeDurationModifiers)).addField(new KeyedCodec("OverlapBehavior", (Codec)OverlapBehavior.CODEC), (target, value) -> target.overlapBehavior = value, target -> target.overlapBehavior)).build();
/*    */   }
/*    */   
/* 43 */   protected double chance = 1.0D;
/*    */   protected Object2DoubleMap<String> entityTypeDurationModifiers;
/* 45 */   protected OverlapBehavior overlapBehavior = OverlapBehavior.IGNORE;
/*    */   
/*    */   public TargetEntityEffect(float duration, double chance, Object2DoubleMap<String> entityTypeDurationModifiers, OverlapBehavior overlapBehavior) {
/* 48 */     this.duration = duration;
/* 49 */     this.chance = chance;
/* 50 */     this.entityTypeDurationModifiers = entityTypeDurationModifiers;
/* 51 */     this.overlapBehavior = overlapBehavior;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getDuration() {
/* 58 */     return this.duration;
/*    */   }
/*    */   
/*    */   public double getChance() {
/* 62 */     return this.chance;
/*    */   }
/*    */   
/*    */   public Object2DoubleMap<String> getEntityTypeDurationModifiers() {
/* 66 */     return this.entityTypeDurationModifiers;
/*    */   }
/*    */   
/*    */   public OverlapBehavior getOverlapBehavior() {
/* 70 */     return this.overlapBehavior;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 76 */     return "TargetEntityEffect{duration=" + this.duration + ", chance=" + this.chance + ", entityTypeDurationModifiers=" + String.valueOf(this.entityTypeDurationModifiers) + ", overlapBehavior=" + String.valueOf(this.overlapBehavior) + "}";
/*    */   }
/*    */   
/*    */   protected TargetEntityEffect() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\combat\TargetEntityEffect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */