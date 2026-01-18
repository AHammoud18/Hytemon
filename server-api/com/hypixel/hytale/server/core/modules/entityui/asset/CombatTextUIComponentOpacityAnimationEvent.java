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
/*    */ public class CombatTextUIComponentOpacityAnimationEvent
/*    */   extends CombatTextUIComponentAnimationEvent
/*    */ {
/*    */   public static final BuilderCodec<CombatTextUIComponentOpacityAnimationEvent> CODEC;
/*    */   private float startOpacity;
/*    */   private float endOpacity;
/*    */   
/*    */   static {
/* 34 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CombatTextUIComponentOpacityAnimationEvent.class, CombatTextUIComponentOpacityAnimationEvent::new, CombatTextUIComponentAnimationEvent.ABSTRACT_CODEC).appendInherited(new KeyedCodec("StartOpacity", (Codec)Codec.FLOAT), (event, f) -> event.startOpacity = f.floatValue(), event -> Float.valueOf(event.startOpacity), (parent, event) -> event.startOpacity = parent.startOpacity).documentation("The opacity that should be applied to text instances before the animation event begins.").addValidator(Validators.nonNull()).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F))).add()).appendInherited(new KeyedCodec("EndOpacity", (Codec)Codec.FLOAT), (event, f) -> event.endOpacity = f.floatValue(), event -> Float.valueOf(event.endOpacity), (parent, event) -> event.endOpacity = parent.endOpacity).documentation("The opacity that should be applied to text instances by the end of the animation.").addValidator(Validators.nonNull()).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F))).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CombatTextEntityUIComponentAnimationEvent generatePacket() {
/* 42 */     CombatTextEntityUIComponentAnimationEvent packet = super.generatePacket();
/* 43 */     packet.type = CombatTextEntityUIAnimationEventType.Opacity;
/* 44 */     packet.startOpacity = this.startOpacity;
/* 45 */     packet.endOpacity = this.endOpacity;
/* 46 */     return packet;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 52 */     return "CombatTextUIComponentOpacityAnimationEvent{startOpacity=" + this.startOpacity + ", endOpacity=" + this.endOpacity + "} " + super
/*    */ 
/*    */       
/* 55 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entityui\asset\CombatTextUIComponentOpacityAnimationEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */