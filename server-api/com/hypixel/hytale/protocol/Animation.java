/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class Animation {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 22;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 30;
/*     */   public static final int MAX_SIZE = 32768040;
/*     */   @Nullable
/*     */   public String name;
/*     */   public float speed;
/*  22 */   public float blendingDuration = 0.2F;
/*     */   
/*     */   public boolean looping;
/*     */   
/*     */   public float weight;
/*     */   @Nullable
/*     */   public int[] footstepIntervals;
/*     */   public int soundEventIndex;
/*     */   public int passiveLoopCount;
/*     */   
/*     */   public Animation(@Nullable String name, float speed, float blendingDuration, boolean looping, float weight, @Nullable int[] footstepIntervals, int soundEventIndex, int passiveLoopCount) {
/*  33 */     this.name = name;
/*  34 */     this.speed = speed;
/*  35 */     this.blendingDuration = blendingDuration;
/*  36 */     this.looping = looping;
/*  37 */     this.weight = weight;
/*  38 */     this.footstepIntervals = footstepIntervals;
/*  39 */     this.soundEventIndex = soundEventIndex;
/*  40 */     this.passiveLoopCount = passiveLoopCount;
/*     */   }
/*     */   
/*     */   public Animation(@Nonnull Animation other) {
/*  44 */     this.name = other.name;
/*  45 */     this.speed = other.speed;
/*  46 */     this.blendingDuration = other.blendingDuration;
/*  47 */     this.looping = other.looping;
/*  48 */     this.weight = other.weight;
/*  49 */     this.footstepIntervals = other.footstepIntervals;
/*  50 */     this.soundEventIndex = other.soundEventIndex;
/*  51 */     this.passiveLoopCount = other.passiveLoopCount;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Animation deserialize(@Nonnull ByteBuf buf, int offset) {
/*  56 */     Animation obj = new Animation();
/*  57 */     byte nullBits = buf.getByte(offset);
/*  58 */     obj.speed = buf.getFloatLE(offset + 1);
/*  59 */     obj.blendingDuration = buf.getFloatLE(offset + 5);
/*  60 */     obj.looping = (buf.getByte(offset + 9) != 0);
/*  61 */     obj.weight = buf.getFloatLE(offset + 10);
/*  62 */     obj.soundEventIndex = buf.getIntLE(offset + 14);
/*  63 */     obj.passiveLoopCount = buf.getIntLE(offset + 18);
/*     */     
/*  65 */     if ((nullBits & 0x1) != 0) {
/*  66 */       int varPos0 = offset + 30 + buf.getIntLE(offset + 22);
/*  67 */       int nameLen = VarInt.peek(buf, varPos0);
/*  68 */       if (nameLen < 0) throw ProtocolException.negativeLength("Name", nameLen); 
/*  69 */       if (nameLen > 4096000) throw ProtocolException.stringTooLong("Name", nameLen, 4096000); 
/*  70 */       obj.name = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  72 */     if ((nullBits & 0x2) != 0) {
/*  73 */       int varPos1 = offset + 30 + buf.getIntLE(offset + 26);
/*  74 */       int footstepIntervalsCount = VarInt.peek(buf, varPos1);
/*  75 */       if (footstepIntervalsCount < 0) throw ProtocolException.negativeLength("FootstepIntervals", footstepIntervalsCount); 
/*  76 */       if (footstepIntervalsCount > 4096000) throw ProtocolException.arrayTooLong("FootstepIntervals", footstepIntervalsCount, 4096000); 
/*  77 */       int varIntLen = VarInt.length(buf, varPos1);
/*  78 */       if ((varPos1 + varIntLen) + footstepIntervalsCount * 4L > buf.readableBytes())
/*  79 */         throw ProtocolException.bufferTooSmall("FootstepIntervals", varPos1 + varIntLen + footstepIntervalsCount * 4, buf.readableBytes()); 
/*  80 */       obj.footstepIntervals = new int[footstepIntervalsCount];
/*  81 */       for (int i = 0; i < footstepIntervalsCount; i++) {
/*  82 */         obj.footstepIntervals[i] = buf.getIntLE(varPos1 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/*     */     
/*  86 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  90 */     byte nullBits = buf.getByte(offset);
/*  91 */     int maxEnd = 30;
/*  92 */     if ((nullBits & 0x1) != 0) {
/*  93 */       int fieldOffset0 = buf.getIntLE(offset + 22);
/*  94 */       int pos0 = offset + 30 + fieldOffset0;
/*  95 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  96 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  98 */     if ((nullBits & 0x2) != 0) {
/*  99 */       int fieldOffset1 = buf.getIntLE(offset + 26);
/* 100 */       int pos1 = offset + 30 + fieldOffset1;
/* 101 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + arrLen * 4;
/* 102 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 104 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 109 */     int startPos = buf.writerIndex();
/* 110 */     byte nullBits = 0;
/* 111 */     if (this.name != null) nullBits = (byte)(nullBits | 0x1); 
/* 112 */     if (this.footstepIntervals != null) nullBits = (byte)(nullBits | 0x2); 
/* 113 */     buf.writeByte(nullBits);
/*     */     
/* 115 */     buf.writeFloatLE(this.speed);
/* 116 */     buf.writeFloatLE(this.blendingDuration);
/* 117 */     buf.writeByte(this.looping ? 1 : 0);
/* 118 */     buf.writeFloatLE(this.weight);
/* 119 */     buf.writeIntLE(this.soundEventIndex);
/* 120 */     buf.writeIntLE(this.passiveLoopCount);
/*     */     
/* 122 */     int nameOffsetSlot = buf.writerIndex();
/* 123 */     buf.writeIntLE(0);
/* 124 */     int footstepIntervalsOffsetSlot = buf.writerIndex();
/* 125 */     buf.writeIntLE(0);
/*     */     
/* 127 */     int varBlockStart = buf.writerIndex();
/* 128 */     if (this.name != null) {
/* 129 */       buf.setIntLE(nameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 130 */       PacketIO.writeVarString(buf, this.name, 4096000);
/*     */     } else {
/* 132 */       buf.setIntLE(nameOffsetSlot, -1);
/*     */     } 
/* 134 */     if (this.footstepIntervals != null) {
/* 135 */       buf.setIntLE(footstepIntervalsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 136 */       if (this.footstepIntervals.length > 4096000) throw ProtocolException.arrayTooLong("FootstepIntervals", this.footstepIntervals.length, 4096000);  VarInt.write(buf, this.footstepIntervals.length); for (int item : this.footstepIntervals) buf.writeIntLE(item); 
/*     */     } else {
/* 138 */       buf.setIntLE(footstepIntervalsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 144 */     int size = 30;
/* 145 */     if (this.name != null) size += PacketIO.stringSize(this.name); 
/* 146 */     if (this.footstepIntervals != null) size += VarInt.size(this.footstepIntervals.length) + this.footstepIntervals.length * 4;
/*     */     
/* 148 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 152 */     if (buffer.readableBytes() - offset < 30) {
/* 153 */       return ValidationResult.error("Buffer too small: expected at least 30 bytes");
/*     */     }
/*     */     
/* 156 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 159 */     if ((nullBits & 0x1) != 0) {
/* 160 */       int nameOffset = buffer.getIntLE(offset + 22);
/* 161 */       if (nameOffset < 0) {
/* 162 */         return ValidationResult.error("Invalid offset for Name");
/*     */       }
/* 164 */       int pos = offset + 30 + nameOffset;
/* 165 */       if (pos >= buffer.writerIndex()) {
/* 166 */         return ValidationResult.error("Offset out of bounds for Name");
/*     */       }
/* 168 */       int nameLen = VarInt.peek(buffer, pos);
/* 169 */       if (nameLen < 0) {
/* 170 */         return ValidationResult.error("Invalid string length for Name");
/*     */       }
/* 172 */       if (nameLen > 4096000) {
/* 173 */         return ValidationResult.error("Name exceeds max length 4096000");
/*     */       }
/* 175 */       pos += VarInt.length(buffer, pos);
/* 176 */       pos += nameLen;
/* 177 */       if (pos > buffer.writerIndex()) {
/* 178 */         return ValidationResult.error("Buffer overflow reading Name");
/*     */       }
/*     */     } 
/*     */     
/* 182 */     if ((nullBits & 0x2) != 0) {
/* 183 */       int footstepIntervalsOffset = buffer.getIntLE(offset + 26);
/* 184 */       if (footstepIntervalsOffset < 0) {
/* 185 */         return ValidationResult.error("Invalid offset for FootstepIntervals");
/*     */       }
/* 187 */       int pos = offset + 30 + footstepIntervalsOffset;
/* 188 */       if (pos >= buffer.writerIndex()) {
/* 189 */         return ValidationResult.error("Offset out of bounds for FootstepIntervals");
/*     */       }
/* 191 */       int footstepIntervalsCount = VarInt.peek(buffer, pos);
/* 192 */       if (footstepIntervalsCount < 0) {
/* 193 */         return ValidationResult.error("Invalid array count for FootstepIntervals");
/*     */       }
/* 195 */       if (footstepIntervalsCount > 4096000) {
/* 196 */         return ValidationResult.error("FootstepIntervals exceeds max length 4096000");
/*     */       }
/* 198 */       pos += VarInt.length(buffer, pos);
/* 199 */       pos += footstepIntervalsCount * 4;
/* 200 */       if (pos > buffer.writerIndex()) {
/* 201 */         return ValidationResult.error("Buffer overflow reading FootstepIntervals");
/*     */       }
/*     */     } 
/* 204 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public Animation clone() {
/* 208 */     Animation copy = new Animation();
/* 209 */     copy.name = this.name;
/* 210 */     copy.speed = this.speed;
/* 211 */     copy.blendingDuration = this.blendingDuration;
/* 212 */     copy.looping = this.looping;
/* 213 */     copy.weight = this.weight;
/* 214 */     copy.footstepIntervals = (this.footstepIntervals != null) ? Arrays.copyOf(this.footstepIntervals, this.footstepIntervals.length) : null;
/* 215 */     copy.soundEventIndex = this.soundEventIndex;
/* 216 */     copy.passiveLoopCount = this.passiveLoopCount;
/* 217 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Animation other;
/* 223 */     if (this == obj) return true; 
/* 224 */     if (obj instanceof Animation) { other = (Animation)obj; } else { return false; }
/* 225 */      return (Objects.equals(this.name, other.name) && this.speed == other.speed && this.blendingDuration == other.blendingDuration && this.looping == other.looping && this.weight == other.weight && Arrays.equals(this.footstepIntervals, other.footstepIntervals) && this.soundEventIndex == other.soundEventIndex && this.passiveLoopCount == other.passiveLoopCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 230 */     int result = 1;
/* 231 */     result = 31 * result + Objects.hashCode(this.name);
/* 232 */     result = 31 * result + Float.hashCode(this.speed);
/* 233 */     result = 31 * result + Float.hashCode(this.blendingDuration);
/* 234 */     result = 31 * result + Boolean.hashCode(this.looping);
/* 235 */     result = 31 * result + Float.hashCode(this.weight);
/* 236 */     result = 31 * result + Arrays.hashCode(this.footstepIntervals);
/* 237 */     result = 31 * result + Integer.hashCode(this.soundEventIndex);
/* 238 */     result = 31 * result + Integer.hashCode(this.passiveLoopCount);
/* 239 */     return result;
/*     */   }
/*     */   
/*     */   public Animation() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Animation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */