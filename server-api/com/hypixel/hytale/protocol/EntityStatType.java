/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityStatType
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 14;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   public static final int VARIABLE_BLOCK_START = 26;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nonnull
/*  26 */   public EntityStatResetBehavior resetBehavior = EntityStatResetBehavior.InitialValue; @Nullable
/*     */   public String id; public float value; public float min; public float max; @Nullable
/*     */   public EntityStatEffects minValueEffects; @Nullable
/*     */   public EntityStatEffects maxValueEffects;
/*     */   
/*     */   public EntityStatType(@Nullable String id, float value, float min, float max, @Nullable EntityStatEffects minValueEffects, @Nullable EntityStatEffects maxValueEffects, @Nonnull EntityStatResetBehavior resetBehavior) {
/*  32 */     this.id = id;
/*  33 */     this.value = value;
/*  34 */     this.min = min;
/*  35 */     this.max = max;
/*  36 */     this.minValueEffects = minValueEffects;
/*  37 */     this.maxValueEffects = maxValueEffects;
/*  38 */     this.resetBehavior = resetBehavior;
/*     */   }
/*     */   
/*     */   public EntityStatType(@Nonnull EntityStatType other) {
/*  42 */     this.id = other.id;
/*  43 */     this.value = other.value;
/*  44 */     this.min = other.min;
/*  45 */     this.max = other.max;
/*  46 */     this.minValueEffects = other.minValueEffects;
/*  47 */     this.maxValueEffects = other.maxValueEffects;
/*  48 */     this.resetBehavior = other.resetBehavior;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static EntityStatType deserialize(@Nonnull ByteBuf buf, int offset) {
/*  53 */     EntityStatType obj = new EntityStatType();
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     obj.value = buf.getFloatLE(offset + 1);
/*  56 */     obj.min = buf.getFloatLE(offset + 5);
/*  57 */     obj.max = buf.getFloatLE(offset + 9);
/*  58 */     obj.resetBehavior = EntityStatResetBehavior.fromValue(buf.getByte(offset + 13));
/*     */     
/*  60 */     if ((nullBits & 0x1) != 0) {
/*  61 */       int varPos0 = offset + 26 + buf.getIntLE(offset + 14);
/*  62 */       int idLen = VarInt.peek(buf, varPos0);
/*  63 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  64 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  65 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  67 */     if ((nullBits & 0x2) != 0) {
/*  68 */       int varPos1 = offset + 26 + buf.getIntLE(offset + 18);
/*  69 */       obj.minValueEffects = EntityStatEffects.deserialize(buf, varPos1);
/*     */     } 
/*  71 */     if ((nullBits & 0x4) != 0) {
/*  72 */       int varPos2 = offset + 26 + buf.getIntLE(offset + 22);
/*  73 */       obj.maxValueEffects = EntityStatEffects.deserialize(buf, varPos2);
/*     */     } 
/*     */     
/*  76 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  80 */     byte nullBits = buf.getByte(offset);
/*  81 */     int maxEnd = 26;
/*  82 */     if ((nullBits & 0x1) != 0) {
/*  83 */       int fieldOffset0 = buf.getIntLE(offset + 14);
/*  84 */       int pos0 = offset + 26 + fieldOffset0;
/*  85 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  86 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  88 */     if ((nullBits & 0x2) != 0) {
/*  89 */       int fieldOffset1 = buf.getIntLE(offset + 18);
/*  90 */       int pos1 = offset + 26 + fieldOffset1;
/*  91 */       pos1 += EntityStatEffects.computeBytesConsumed(buf, pos1);
/*  92 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  94 */     if ((nullBits & 0x4) != 0) {
/*  95 */       int fieldOffset2 = buf.getIntLE(offset + 22);
/*  96 */       int pos2 = offset + 26 + fieldOffset2;
/*  97 */       pos2 += EntityStatEffects.computeBytesConsumed(buf, pos2);
/*  98 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 100 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 105 */     int startPos = buf.writerIndex();
/* 106 */     byte nullBits = 0;
/* 107 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 108 */     if (this.minValueEffects != null) nullBits = (byte)(nullBits | 0x2); 
/* 109 */     if (this.maxValueEffects != null) nullBits = (byte)(nullBits | 0x4); 
/* 110 */     buf.writeByte(nullBits);
/*     */     
/* 112 */     buf.writeFloatLE(this.value);
/* 113 */     buf.writeFloatLE(this.min);
/* 114 */     buf.writeFloatLE(this.max);
/* 115 */     buf.writeByte(this.resetBehavior.getValue());
/*     */     
/* 117 */     int idOffsetSlot = buf.writerIndex();
/* 118 */     buf.writeIntLE(0);
/* 119 */     int minValueEffectsOffsetSlot = buf.writerIndex();
/* 120 */     buf.writeIntLE(0);
/* 121 */     int maxValueEffectsOffsetSlot = buf.writerIndex();
/* 122 */     buf.writeIntLE(0);
/*     */     
/* 124 */     int varBlockStart = buf.writerIndex();
/* 125 */     if (this.id != null) {
/* 126 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 127 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 129 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 131 */     if (this.minValueEffects != null) {
/* 132 */       buf.setIntLE(minValueEffectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 133 */       this.minValueEffects.serialize(buf);
/*     */     } else {
/* 135 */       buf.setIntLE(minValueEffectsOffsetSlot, -1);
/*     */     } 
/* 137 */     if (this.maxValueEffects != null) {
/* 138 */       buf.setIntLE(maxValueEffectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 139 */       this.maxValueEffects.serialize(buf);
/*     */     } else {
/* 141 */       buf.setIntLE(maxValueEffectsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 147 */     int size = 26;
/* 148 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 149 */     if (this.minValueEffects != null) size += this.minValueEffects.computeSize(); 
/* 150 */     if (this.maxValueEffects != null) size += this.maxValueEffects.computeSize();
/*     */     
/* 152 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 156 */     if (buffer.readableBytes() - offset < 26) {
/* 157 */       return ValidationResult.error("Buffer too small: expected at least 26 bytes");
/*     */     }
/*     */     
/* 160 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 163 */     if ((nullBits & 0x1) != 0) {
/* 164 */       int idOffset = buffer.getIntLE(offset + 14);
/* 165 */       if (idOffset < 0) {
/* 166 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 168 */       int pos = offset + 26 + idOffset;
/* 169 */       if (pos >= buffer.writerIndex()) {
/* 170 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 172 */       int idLen = VarInt.peek(buffer, pos);
/* 173 */       if (idLen < 0) {
/* 174 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 176 */       if (idLen > 4096000) {
/* 177 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 179 */       pos += VarInt.length(buffer, pos);
/* 180 */       pos += idLen;
/* 181 */       if (pos > buffer.writerIndex()) {
/* 182 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 186 */     if ((nullBits & 0x2) != 0) {
/* 187 */       int minValueEffectsOffset = buffer.getIntLE(offset + 18);
/* 188 */       if (minValueEffectsOffset < 0) {
/* 189 */         return ValidationResult.error("Invalid offset for MinValueEffects");
/*     */       }
/* 191 */       int pos = offset + 26 + minValueEffectsOffset;
/* 192 */       if (pos >= buffer.writerIndex()) {
/* 193 */         return ValidationResult.error("Offset out of bounds for MinValueEffects");
/*     */       }
/* 195 */       ValidationResult minValueEffectsResult = EntityStatEffects.validateStructure(buffer, pos);
/* 196 */       if (!minValueEffectsResult.isValid()) {
/* 197 */         return ValidationResult.error("Invalid MinValueEffects: " + minValueEffectsResult.error());
/*     */       }
/* 199 */       pos += EntityStatEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 202 */     if ((nullBits & 0x4) != 0) {
/* 203 */       int maxValueEffectsOffset = buffer.getIntLE(offset + 22);
/* 204 */       if (maxValueEffectsOffset < 0) {
/* 205 */         return ValidationResult.error("Invalid offset for MaxValueEffects");
/*     */       }
/* 207 */       int pos = offset + 26 + maxValueEffectsOffset;
/* 208 */       if (pos >= buffer.writerIndex()) {
/* 209 */         return ValidationResult.error("Offset out of bounds for MaxValueEffects");
/*     */       }
/* 211 */       ValidationResult maxValueEffectsResult = EntityStatEffects.validateStructure(buffer, pos);
/* 212 */       if (!maxValueEffectsResult.isValid()) {
/* 213 */         return ValidationResult.error("Invalid MaxValueEffects: " + maxValueEffectsResult.error());
/*     */       }
/* 215 */       pos += EntityStatEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 217 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public EntityStatType clone() {
/* 221 */     EntityStatType copy = new EntityStatType();
/* 222 */     copy.id = this.id;
/* 223 */     copy.value = this.value;
/* 224 */     copy.min = this.min;
/* 225 */     copy.max = this.max;
/* 226 */     copy.minValueEffects = (this.minValueEffects != null) ? this.minValueEffects.clone() : null;
/* 227 */     copy.maxValueEffects = (this.maxValueEffects != null) ? this.maxValueEffects.clone() : null;
/* 228 */     copy.resetBehavior = this.resetBehavior;
/* 229 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     EntityStatType other;
/* 235 */     if (this == obj) return true; 
/* 236 */     if (obj instanceof EntityStatType) { other = (EntityStatType)obj; } else { return false; }
/* 237 */      return (Objects.equals(this.id, other.id) && this.value == other.value && this.min == other.min && this.max == other.max && Objects.equals(this.minValueEffects, other.minValueEffects) && Objects.equals(this.maxValueEffects, other.maxValueEffects) && Objects.equals(this.resetBehavior, other.resetBehavior));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 242 */     return Objects.hash(new Object[] { this.id, Float.valueOf(this.value), Float.valueOf(this.min), Float.valueOf(this.max), this.minValueEffects, this.maxValueEffects, this.resetBehavior });
/*     */   }
/*     */   
/*     */   public EntityStatType() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\EntityStatType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */