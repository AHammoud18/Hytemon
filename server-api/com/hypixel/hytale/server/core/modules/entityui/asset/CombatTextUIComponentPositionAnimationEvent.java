/*    */ package com.hypixel.hytale.server.core.modules.entityui.asset;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.CombatTextEntityUIAnimationEventType;
/*    */ import com.hypixel.hytale.protocol.CombatTextEntityUIComponentAnimationEvent;
/*    */ import com.hypixel.hytale.protocol.Vector2f;
/*    */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CombatTextUIComponentPositionAnimationEvent
/*    */   extends CombatTextUIComponentAnimationEvent
/*    */ {
/*    */   public static final BuilderCodec<CombatTextUIComponentPositionAnimationEvent> CODEC;
/*    */   private Vector2f positionOffset;
/*    */   
/*    */   static {
/* 24 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(CombatTextUIComponentPositionAnimationEvent.class, CombatTextUIComponentPositionAnimationEvent::new, CombatTextUIComponentAnimationEvent.ABSTRACT_CODEC).appendInherited(new KeyedCodec("PositionOffset", (Codec)ProtocolCodecs.VECTOR2F), (event, f) -> event.positionOffset = f, event -> event.positionOffset, (parent, event) -> event.positionOffset = parent.positionOffset).documentation("The offset from the starting position that the text instance should animate to.").addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CombatTextEntityUIComponentAnimationEvent generatePacket() {
/* 31 */     CombatTextEntityUIComponentAnimationEvent packet = super.generatePacket();
/* 32 */     packet.type = CombatTextEntityUIAnimationEventType.Position;
/* 33 */     packet.positionOffset = this.positionOffset;
/* 34 */     return packet;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 40 */     return "CombatTextUIComponentPositionAnimationEvent{positionOffset=" + String.valueOf(this.positionOffset) + "} " + super
/*    */       
/* 42 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entityui\asset\CombatTextUIComponentPositionAnimationEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */