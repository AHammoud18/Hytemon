/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class EntityUIComponent
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 51;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 51;
/*     */   public static final int MAX_SIZE = 139264056;
/*     */   @Nonnull
/*  20 */   public EntityUIType type = EntityUIType.EntityStat; @Nullable
/*     */   public Vector2f hitboxOffset;
/*     */   public boolean unknown;
/*     */   public int entityStatIndex;
/*     */   @Nullable
/*     */   public RangeVector2f combatTextRandomPositionOffsetRange;
/*     */   public float combatTextViewportMargin;
/*     */   public float combatTextDuration;
/*     */   public float combatTextHitAngleModifierStrength;
/*     */   public float combatTextFontSize;
/*     */   @Nullable
/*     */   public Color combatTextColor;
/*     */   @Nullable
/*     */   public CombatTextEntityUIComponentAnimationEvent[] combatTextAnimationEvents;
/*     */   
/*     */   public EntityUIComponent(@Nonnull EntityUIType type, @Nullable Vector2f hitboxOffset, boolean unknown, int entityStatIndex, @Nullable RangeVector2f combatTextRandomPositionOffsetRange, float combatTextViewportMargin, float combatTextDuration, float combatTextHitAngleModifierStrength, float combatTextFontSize, @Nullable Color combatTextColor, @Nullable CombatTextEntityUIComponentAnimationEvent[] combatTextAnimationEvents) {
/*  36 */     this.type = type;
/*  37 */     this.hitboxOffset = hitboxOffset;
/*  38 */     this.unknown = unknown;
/*  39 */     this.entityStatIndex = entityStatIndex;
/*  40 */     this.combatTextRandomPositionOffsetRange = combatTextRandomPositionOffsetRange;
/*  41 */     this.combatTextViewportMargin = combatTextViewportMargin;
/*  42 */     this.combatTextDuration = combatTextDuration;
/*  43 */     this.combatTextHitAngleModifierStrength = combatTextHitAngleModifierStrength;
/*  44 */     this.combatTextFontSize = combatTextFontSize;
/*  45 */     this.combatTextColor = combatTextColor;
/*  46 */     this.combatTextAnimationEvents = combatTextAnimationEvents;
/*     */   }
/*     */   
/*     */   public EntityUIComponent(@Nonnull EntityUIComponent other) {
/*  50 */     this.type = other.type;
/*  51 */     this.hitboxOffset = other.hitboxOffset;
/*  52 */     this.unknown = other.unknown;
/*  53 */     this.entityStatIndex = other.entityStatIndex;
/*  54 */     this.combatTextRandomPositionOffsetRange = other.combatTextRandomPositionOffsetRange;
/*  55 */     this.combatTextViewportMargin = other.combatTextViewportMargin;
/*  56 */     this.combatTextDuration = other.combatTextDuration;
/*  57 */     this.combatTextHitAngleModifierStrength = other.combatTextHitAngleModifierStrength;
/*  58 */     this.combatTextFontSize = other.combatTextFontSize;
/*  59 */     this.combatTextColor = other.combatTextColor;
/*  60 */     this.combatTextAnimationEvents = other.combatTextAnimationEvents;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static EntityUIComponent deserialize(@Nonnull ByteBuf buf, int offset) {
/*  65 */     EntityUIComponent obj = new EntityUIComponent();
/*  66 */     byte nullBits = buf.getByte(offset);
/*  67 */     obj.type = EntityUIType.fromValue(buf.getByte(offset + 1));
/*  68 */     if ((nullBits & 0x1) != 0) obj.hitboxOffset = Vector2f.deserialize(buf, offset + 2); 
/*  69 */     obj.unknown = (buf.getByte(offset + 10) != 0);
/*  70 */     obj.entityStatIndex = buf.getIntLE(offset + 11);
/*  71 */     if ((nullBits & 0x2) != 0) obj.combatTextRandomPositionOffsetRange = RangeVector2f.deserialize(buf, offset + 15); 
/*  72 */     obj.combatTextViewportMargin = buf.getFloatLE(offset + 32);
/*  73 */     obj.combatTextDuration = buf.getFloatLE(offset + 36);
/*  74 */     obj.combatTextHitAngleModifierStrength = buf.getFloatLE(offset + 40);
/*  75 */     obj.combatTextFontSize = buf.getFloatLE(offset + 44);
/*  76 */     if ((nullBits & 0x4) != 0) obj.combatTextColor = Color.deserialize(buf, offset + 48);
/*     */     
/*  78 */     int pos = offset + 51;
/*  79 */     if ((nullBits & 0x8) != 0) { int combatTextAnimationEventsCount = VarInt.peek(buf, pos);
/*  80 */       if (combatTextAnimationEventsCount < 0) throw ProtocolException.negativeLength("CombatTextAnimationEvents", combatTextAnimationEventsCount); 
/*  81 */       if (combatTextAnimationEventsCount > 4096000) throw ProtocolException.arrayTooLong("CombatTextAnimationEvents", combatTextAnimationEventsCount, 4096000); 
/*  82 */       int combatTextAnimationEventsVarLen = VarInt.size(combatTextAnimationEventsCount);
/*  83 */       if ((pos + combatTextAnimationEventsVarLen) + combatTextAnimationEventsCount * 34L > buf.readableBytes())
/*  84 */         throw ProtocolException.bufferTooSmall("CombatTextAnimationEvents", pos + combatTextAnimationEventsVarLen + combatTextAnimationEventsCount * 34, buf.readableBytes()); 
/*  85 */       pos += combatTextAnimationEventsVarLen;
/*  86 */       obj.combatTextAnimationEvents = new CombatTextEntityUIComponentAnimationEvent[combatTextAnimationEventsCount];
/*  87 */       for (int i = 0; i < combatTextAnimationEventsCount; i++) {
/*  88 */         obj.combatTextAnimationEvents[i] = CombatTextEntityUIComponentAnimationEvent.deserialize(buf, pos);
/*  89 */         pos += CombatTextEntityUIComponentAnimationEvent.computeBytesConsumed(buf, pos);
/*     */       }  }
/*     */     
/*  92 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  96 */     byte nullBits = buf.getByte(offset);
/*  97 */     int pos = offset + 51;
/*  98 */     if ((nullBits & 0x8) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  99 */       for (int i = 0; i < arrLen; ) { pos += CombatTextEntityUIComponentAnimationEvent.computeBytesConsumed(buf, pos); i++; }  }
/* 100 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 105 */     byte nullBits = 0;
/* 106 */     if (this.hitboxOffset != null) nullBits = (byte)(nullBits | 0x1); 
/* 107 */     if (this.combatTextRandomPositionOffsetRange != null) nullBits = (byte)(nullBits | 0x2); 
/* 108 */     if (this.combatTextColor != null) nullBits = (byte)(nullBits | 0x4); 
/* 109 */     if (this.combatTextAnimationEvents != null) nullBits = (byte)(nullBits | 0x8); 
/* 110 */     buf.writeByte(nullBits);
/*     */     
/* 112 */     buf.writeByte(this.type.getValue());
/* 113 */     if (this.hitboxOffset != null) { this.hitboxOffset.serialize(buf); } else { buf.writeZero(8); }
/* 114 */      buf.writeByte(this.unknown ? 1 : 0);
/* 115 */     buf.writeIntLE(this.entityStatIndex);
/* 116 */     if (this.combatTextRandomPositionOffsetRange != null) { this.combatTextRandomPositionOffsetRange.serialize(buf); } else { buf.writeZero(17); }
/* 117 */      buf.writeFloatLE(this.combatTextViewportMargin);
/* 118 */     buf.writeFloatLE(this.combatTextDuration);
/* 119 */     buf.writeFloatLE(this.combatTextHitAngleModifierStrength);
/* 120 */     buf.writeFloatLE(this.combatTextFontSize);
/* 121 */     if (this.combatTextColor != null) { this.combatTextColor.serialize(buf); } else { buf.writeZero(3); }
/*     */     
/* 123 */     if (this.combatTextAnimationEvents != null) { if (this.combatTextAnimationEvents.length > 4096000) throw ProtocolException.arrayTooLong("CombatTextAnimationEvents", this.combatTextAnimationEvents.length, 4096000);  VarInt.write(buf, this.combatTextAnimationEvents.length); for (CombatTextEntityUIComponentAnimationEvent item : this.combatTextAnimationEvents) item.serialize(buf);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/* 128 */     int size = 51;
/* 129 */     if (this.combatTextAnimationEvents != null) size += VarInt.size(this.combatTextAnimationEvents.length) + this.combatTextAnimationEvents.length * 34;
/*     */     
/* 131 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 135 */     if (buffer.readableBytes() - offset < 51) {
/* 136 */       return ValidationResult.error("Buffer too small: expected at least 51 bytes");
/*     */     }
/*     */     
/* 139 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 141 */     int pos = offset + 51;
/*     */     
/* 143 */     if ((nullBits & 0x8) != 0) {
/* 144 */       int combatTextAnimationEventsCount = VarInt.peek(buffer, pos);
/* 145 */       if (combatTextAnimationEventsCount < 0) {
/* 146 */         return ValidationResult.error("Invalid array count for CombatTextAnimationEvents");
/*     */       }
/* 148 */       if (combatTextAnimationEventsCount > 4096000) {
/* 149 */         return ValidationResult.error("CombatTextAnimationEvents exceeds max length 4096000");
/*     */       }
/* 151 */       pos += VarInt.length(buffer, pos);
/* 152 */       pos += combatTextAnimationEventsCount * 34;
/* 153 */       if (pos > buffer.writerIndex()) {
/* 154 */         return ValidationResult.error("Buffer overflow reading CombatTextAnimationEvents");
/*     */       }
/*     */     } 
/* 157 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public EntityUIComponent clone() {
/* 161 */     EntityUIComponent copy = new EntityUIComponent();
/* 162 */     copy.type = this.type;
/* 163 */     copy.hitboxOffset = (this.hitboxOffset != null) ? this.hitboxOffset.clone() : null;
/* 164 */     copy.unknown = this.unknown;
/* 165 */     copy.entityStatIndex = this.entityStatIndex;
/* 166 */     copy.combatTextRandomPositionOffsetRange = (this.combatTextRandomPositionOffsetRange != null) ? this.combatTextRandomPositionOffsetRange.clone() : null;
/* 167 */     copy.combatTextViewportMargin = this.combatTextViewportMargin;
/* 168 */     copy.combatTextDuration = this.combatTextDuration;
/* 169 */     copy.combatTextHitAngleModifierStrength = this.combatTextHitAngleModifierStrength;
/* 170 */     copy.combatTextFontSize = this.combatTextFontSize;
/* 171 */     copy.combatTextColor = (this.combatTextColor != null) ? this.combatTextColor.clone() : null;
/* 172 */     copy.combatTextAnimationEvents = (this.combatTextAnimationEvents != null) ? (CombatTextEntityUIComponentAnimationEvent[])Arrays.<CombatTextEntityUIComponentAnimationEvent>stream(this.combatTextAnimationEvents).map(e -> e.clone()).toArray(x$0 -> new CombatTextEntityUIComponentAnimationEvent[x$0]) : null;
/* 173 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     EntityUIComponent other;
/* 179 */     if (this == obj) return true; 
/* 180 */     if (obj instanceof EntityUIComponent) { other = (EntityUIComponent)obj; } else { return false; }
/* 181 */      return (Objects.equals(this.type, other.type) && Objects.equals(this.hitboxOffset, other.hitboxOffset) && this.unknown == other.unknown && this.entityStatIndex == other.entityStatIndex && Objects.equals(this.combatTextRandomPositionOffsetRange, other.combatTextRandomPositionOffsetRange) && this.combatTextViewportMargin == other.combatTextViewportMargin && this.combatTextDuration == other.combatTextDuration && this.combatTextHitAngleModifierStrength == other.combatTextHitAngleModifierStrength && this.combatTextFontSize == other.combatTextFontSize && Objects.equals(this.combatTextColor, other.combatTextColor) && Arrays.equals((Object[])this.combatTextAnimationEvents, (Object[])other.combatTextAnimationEvents));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 186 */     int result = 1;
/* 187 */     result = 31 * result + Objects.hashCode(this.type);
/* 188 */     result = 31 * result + Objects.hashCode(this.hitboxOffset);
/* 189 */     result = 31 * result + Boolean.hashCode(this.unknown);
/* 190 */     result = 31 * result + Integer.hashCode(this.entityStatIndex);
/* 191 */     result = 31 * result + Objects.hashCode(this.combatTextRandomPositionOffsetRange);
/* 192 */     result = 31 * result + Float.hashCode(this.combatTextViewportMargin);
/* 193 */     result = 31 * result + Float.hashCode(this.combatTextDuration);
/* 194 */     result = 31 * result + Float.hashCode(this.combatTextHitAngleModifierStrength);
/* 195 */     result = 31 * result + Float.hashCode(this.combatTextFontSize);
/* 196 */     result = 31 * result + Objects.hashCode(this.combatTextColor);
/* 197 */     result = 31 * result + Arrays.hashCode((Object[])this.combatTextAnimationEvents);
/* 198 */     return result;
/*     */   }
/*     */   
/*     */   public EntityUIComponent() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\EntityUIComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */