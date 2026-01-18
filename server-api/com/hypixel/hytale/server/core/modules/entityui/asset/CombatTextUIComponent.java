/*     */ package com.hypixel.hytale.server.core.modules.entityui.asset;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.protocol.EntityUIComponent;
/*     */ import com.hypixel.hytale.protocol.EntityUIType;
/*     */ import com.hypixel.hytale.protocol.RangeVector2f;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class CombatTextUIComponent extends EntityUIComponent {
/*     */   private static final float DEFAULT_FONT_SIZE = 68.0F;
/*  19 */   private static final Color DEFAULT_TEXT_COLOR = new Color((byte)-1, (byte)-1, (byte)-1);
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
/*     */   public static final BuilderCodec<CombatTextUIComponent> CODEC;
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
/*     */   private RangeVector2f randomPositionOffsetRange;
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
/*     */   private float viewportMargin;
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
/*     */   private float duration;
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
/*     */   static {
/*  84 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CombatTextUIComponent.class, CombatTextUIComponent::new, EntityUIComponent.ABSTRACT_CODEC).appendInherited(new KeyedCodec("RandomPositionOffsetRange", (Codec)ProtocolCodecs.RANGE_VECTOR2F), (component, v) -> component.randomPositionOffsetRange = v, component -> component.randomPositionOffsetRange, (component, parent) -> component.randomPositionOffsetRange = parent.randomPositionOffsetRange).addValidator(Validators.nonNull()).documentation("The maximum range for randomly offsetting text instances from their starting position. Values are treated as absolute and apply in both directions of the axis.").add()).appendInherited(new KeyedCodec("ViewportMargin", (Codec)Codec.FLOAT), (component, f) -> component.viewportMargin = f.floatValue(), component -> Float.valueOf(component.viewportMargin), (component, parent) -> component.viewportMargin = parent.viewportMargin).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(200.0F))).documentation("The minimum distance (in px) from the edges of the viewport that text instances should clamp to.").add()).appendInherited(new KeyedCodec("Duration", (Codec)Codec.FLOAT), (component, f) -> component.duration = f.floatValue(), component -> Float.valueOf(component.duration), (component, parent) -> component.duration = parent.duration).addValidator(Validators.nonNull()).addValidator(Validators.range(Float.valueOf(0.1F), Float.valueOf(10.0F))).documentation("The duration for which text instances in this component should be visible.").add()).appendInherited(new KeyedCodec("HitAngleModifierStrength", (Codec)Codec.FLOAT), (component, f) -> component.hitAngleModifierStrength = f.floatValue(), component -> Float.valueOf(component.hitAngleModifierStrength), (component, parent) -> component.hitAngleModifierStrength = parent.hitAngleModifierStrength).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(10.0F))).documentation("Strength of the modifier to apply to the X axis of a position animation (if set) based on the angle of a melee attack.").add()).appendInherited(new KeyedCodec("FontSize", (Codec)Codec.FLOAT), (component, f) -> component.fontSize = f.floatValue(), component -> Float.valueOf(component.fontSize), (component, parent) -> component.fontSize = parent.fontSize).documentation("The font size to apply to text instances.").add()).appendInherited(new KeyedCodec("TextColor", (Codec)ProtocolCodecs.COLOR), (component, c) -> component.textColor = c, component -> component.textColor, (component, parent) -> component.textColor = parent.textColor).documentation("The text color to apply to text instances.").add()).appendInherited(new KeyedCodec("AnimationEvents", (Codec)new ArrayCodec((Codec)CombatTextUIComponentAnimationEvent.CODEC, x$0 -> new CombatTextUIComponentAnimationEvent[x$0])), (component, o) -> component.animationEvents = o, component -> component.animationEvents, (component, parent) -> component.animationEvents = parent.animationEvents).addValidator(Validators.nonNull()).documentation("Animation events for controlling animation of scale, position, and opacity.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  89 */   private float hitAngleModifierStrength = 1.0F;
/*  90 */   private float fontSize = 68.0F;
/*  91 */   private Color textColor = DEFAULT_TEXT_COLOR;
/*     */   
/*     */   private CombatTextUIComponentAnimationEvent[] animationEvents;
/*     */   
/*     */   @Nonnull
/*     */   protected EntityUIComponent generatePacket() {
/*  97 */     EntityUIComponent packet = super.generatePacket();
/*  98 */     packet.type = EntityUIType.CombatText;
/*  99 */     packet.combatTextRandomPositionOffsetRange = this.randomPositionOffsetRange;
/* 100 */     packet.combatTextViewportMargin = this.viewportMargin;
/* 101 */     packet.combatTextDuration = this.duration;
/* 102 */     packet.combatTextHitAngleModifierStrength = this.hitAngleModifierStrength;
/* 103 */     packet.combatTextFontSize = this.fontSize;
/* 104 */     packet.combatTextColor = this.textColor;
/* 105 */     packet.combatTextAnimationEvents = new com.hypixel.hytale.protocol.CombatTextEntityUIComponentAnimationEvent[this.animationEvents.length];
/* 106 */     for (int i = 0; i < this.animationEvents.length; i++) {
/* 107 */       packet.combatTextAnimationEvents[i] = this.animationEvents[i].generatePacket();
/*     */     }
/*     */     
/* 110 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 116 */     return "CombatTextUIComponent{randomPositionOffsetRange=" + String.valueOf(this.randomPositionOffsetRange) + ", viewportMargin" + this.viewportMargin + ", duration=" + this.duration + ", hitAngleModifierStrength=" + this.hitAngleModifierStrength + ", fontSize=" + this.fontSize + ", textColor=" + String.valueOf(this.textColor) + ", animationEvents=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 123 */       Arrays.toString((Object[])this.animationEvents) + "} " + super
/* 124 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entityui\asset\CombatTextUIComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */