/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CombatTextEntityUIComponentAnimationEvent
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 34;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 34;
/*     */   public static final int MAX_SIZE = 34;
/*     */   @Nonnull
/*  20 */   public CombatTextEntityUIAnimationEventType type = CombatTextEntityUIAnimationEventType.Scale;
/*     */   
/*     */   public float startAt;
/*     */   
/*     */   public float endAt;
/*     */   public float startScale;
/*     */   public float endScale;
/*     */   @Nullable
/*     */   public Vector2f positionOffset;
/*     */   public float startOpacity;
/*     */   public float endOpacity;
/*     */   
/*     */   public CombatTextEntityUIComponentAnimationEvent(@Nonnull CombatTextEntityUIAnimationEventType type, float startAt, float endAt, float startScale, float endScale, @Nullable Vector2f positionOffset, float startOpacity, float endOpacity) {
/*  33 */     this.type = type;
/*  34 */     this.startAt = startAt;
/*  35 */     this.endAt = endAt;
/*  36 */     this.startScale = startScale;
/*  37 */     this.endScale = endScale;
/*  38 */     this.positionOffset = positionOffset;
/*  39 */     this.startOpacity = startOpacity;
/*  40 */     this.endOpacity = endOpacity;
/*     */   }
/*     */   
/*     */   public CombatTextEntityUIComponentAnimationEvent(@Nonnull CombatTextEntityUIComponentAnimationEvent other) {
/*  44 */     this.type = other.type;
/*  45 */     this.startAt = other.startAt;
/*  46 */     this.endAt = other.endAt;
/*  47 */     this.startScale = other.startScale;
/*  48 */     this.endScale = other.endScale;
/*  49 */     this.positionOffset = other.positionOffset;
/*  50 */     this.startOpacity = other.startOpacity;
/*  51 */     this.endOpacity = other.endOpacity;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CombatTextEntityUIComponentAnimationEvent deserialize(@Nonnull ByteBuf buf, int offset) {
/*  56 */     CombatTextEntityUIComponentAnimationEvent obj = new CombatTextEntityUIComponentAnimationEvent();
/*  57 */     byte nullBits = buf.getByte(offset);
/*  58 */     obj.type = CombatTextEntityUIAnimationEventType.fromValue(buf.getByte(offset + 1));
/*  59 */     obj.startAt = buf.getFloatLE(offset + 2);
/*  60 */     obj.endAt = buf.getFloatLE(offset + 6);
/*  61 */     obj.startScale = buf.getFloatLE(offset + 10);
/*  62 */     obj.endScale = buf.getFloatLE(offset + 14);
/*  63 */     if ((nullBits & 0x1) != 0) obj.positionOffset = Vector2f.deserialize(buf, offset + 18); 
/*  64 */     obj.startOpacity = buf.getFloatLE(offset + 26);
/*  65 */     obj.endOpacity = buf.getFloatLE(offset + 30);
/*     */ 
/*     */     
/*  68 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  72 */     return 34;
/*     */   }
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  76 */     byte nullBits = 0;
/*  77 */     if (this.positionOffset != null) nullBits = (byte)(nullBits | 0x1); 
/*  78 */     buf.writeByte(nullBits);
/*     */     
/*  80 */     buf.writeByte(this.type.getValue());
/*  81 */     buf.writeFloatLE(this.startAt);
/*  82 */     buf.writeFloatLE(this.endAt);
/*  83 */     buf.writeFloatLE(this.startScale);
/*  84 */     buf.writeFloatLE(this.endScale);
/*  85 */     if (this.positionOffset != null) { this.positionOffset.serialize(buf); } else { buf.writeZero(8); }
/*  86 */      buf.writeFloatLE(this.startOpacity);
/*  87 */     buf.writeFloatLE(this.endOpacity);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  93 */     return 34;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  97 */     if (buffer.readableBytes() - offset < 34) {
/*  98 */       return ValidationResult.error("Buffer too small: expected at least 34 bytes");
/*     */     }
/*     */ 
/*     */     
/* 102 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public CombatTextEntityUIComponentAnimationEvent clone() {
/* 106 */     CombatTextEntityUIComponentAnimationEvent copy = new CombatTextEntityUIComponentAnimationEvent();
/* 107 */     copy.type = this.type;
/* 108 */     copy.startAt = this.startAt;
/* 109 */     copy.endAt = this.endAt;
/* 110 */     copy.startScale = this.startScale;
/* 111 */     copy.endScale = this.endScale;
/* 112 */     copy.positionOffset = (this.positionOffset != null) ? this.positionOffset.clone() : null;
/* 113 */     copy.startOpacity = this.startOpacity;
/* 114 */     copy.endOpacity = this.endOpacity;
/* 115 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     CombatTextEntityUIComponentAnimationEvent other;
/* 121 */     if (this == obj) return true; 
/* 122 */     if (obj instanceof CombatTextEntityUIComponentAnimationEvent) { other = (CombatTextEntityUIComponentAnimationEvent)obj; } else { return false; }
/* 123 */      return (Objects.equals(this.type, other.type) && this.startAt == other.startAt && this.endAt == other.endAt && this.startScale == other.startScale && this.endScale == other.endScale && Objects.equals(this.positionOffset, other.positionOffset) && this.startOpacity == other.startOpacity && this.endOpacity == other.endOpacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 128 */     return Objects.hash(new Object[] { this.type, Float.valueOf(this.startAt), Float.valueOf(this.endAt), Float.valueOf(this.startScale), Float.valueOf(this.endScale), this.positionOffset, Float.valueOf(this.startOpacity), Float.valueOf(this.endOpacity) });
/*     */   }
/*     */   
/*     */   public CombatTextEntityUIComponentAnimationEvent() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\CombatTextEntityUIComponentAnimationEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */