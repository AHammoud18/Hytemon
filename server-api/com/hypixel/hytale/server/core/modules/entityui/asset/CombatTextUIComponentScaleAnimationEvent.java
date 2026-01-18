/*    */ package com.hypixel.hytale.server.core.modules.entityui.asset;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.CombatTextEntityUIAnimationEventType;
/*    */ import com.hypixel.hytale.protocol.CombatTextEntityUIComponentAnimationEvent;
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
/*    */ public class CombatTextUIComponentScaleAnimationEvent
/*    */   extends CombatTextUIComponentAnimationEvent
/*    */ {
/*    */   public static final BuilderCodec<CombatTextUIComponentScaleAnimationEvent> CODEC;
/*    */   private float startScale;
/*    */   private float endScale;
/*    */   
/*    */   static {
/* 34 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CombatTextUIComponentScaleAnimationEvent.class, CombatTextUIComponentScaleAnimationEvent::new, CombatTextUIComponentAnimationEvent.ABSTRACT_CODEC).appendInherited(new KeyedCodec("StartScale", (Codec)Codec.FLOAT), (event, f) -> event.startScale = f.floatValue(), event -> Float.valueOf(event.startScale), (parent, event) -> event.startScale = parent.startScale).documentation("The scale that should be applied to text instances before the animation event begins.").addValidator(Validators.nonNull()).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F))).add()).appendInherited(new KeyedCodec("EndScale", (Codec)Codec.FLOAT), (event, f) -> event.endScale = f.floatValue(), event -> Float.valueOf(event.endScale), (parent, event) -> event.endScale = parent.endScale).documentation("The scale that should be applied to text instances by the end of the animation.").addValidator(Validators.nonNull()).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F))).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CombatTextEntityUIComponentAnimationEvent generatePacket() {
/* 42 */     CombatTextEntityUIComponentAnimationEvent packet = super.generatePacket();
/* 43 */     packet.type = CombatTextEntityUIAnimationEventType.Scale;
/* 44 */     packet.startScale = this.startScale;
/* 45 */     packet.endScale = this.endScale;
/* 46 */     return packet;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 52 */     return "CombatTextUIComponentConfig{startScale=" + this.startScale + ", endScale=" + this.endScale + "} " + super
/*    */ 
/*    */       
/* 55 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entityui\asset\CombatTextUIComponentScaleAnimationEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */