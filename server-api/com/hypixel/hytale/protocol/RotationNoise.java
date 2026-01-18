/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class RotationNoise
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   public static final int VARIABLE_BLOCK_START = 13;
/*     */   public static final int MAX_SIZE = 282624028;
/*     */   @Nullable
/*     */   public NoiseConfig[] pitch;
/*     */   @Nullable
/*     */   public NoiseConfig[] yaw;
/*     */   @Nullable
/*     */   public NoiseConfig[] roll;
/*     */   
/*     */   public RotationNoise() {}
/*     */   
/*     */   public RotationNoise(@Nullable NoiseConfig[] pitch, @Nullable NoiseConfig[] yaw, @Nullable NoiseConfig[] roll) {
/*  28 */     this.pitch = pitch;
/*  29 */     this.yaw = yaw;
/*  30 */     this.roll = roll;
/*     */   }
/*     */   
/*     */   public RotationNoise(@Nonnull RotationNoise other) {
/*  34 */     this.pitch = other.pitch;
/*  35 */     this.yaw = other.yaw;
/*  36 */     this.roll = other.roll;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static RotationNoise deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     RotationNoise obj = new RotationNoise();
/*  42 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  44 */     if ((nullBits & 0x1) != 0) {
/*  45 */       int varPos0 = offset + 13 + buf.getIntLE(offset + 1);
/*  46 */       int pitchCount = VarInt.peek(buf, varPos0);
/*  47 */       if (pitchCount < 0) throw ProtocolException.negativeLength("Pitch", pitchCount); 
/*  48 */       if (pitchCount > 4096000) throw ProtocolException.arrayTooLong("Pitch", pitchCount, 4096000); 
/*  49 */       int varIntLen = VarInt.length(buf, varPos0);
/*  50 */       if ((varPos0 + varIntLen) + pitchCount * 23L > buf.readableBytes())
/*  51 */         throw ProtocolException.bufferTooSmall("Pitch", varPos0 + varIntLen + pitchCount * 23, buf.readableBytes()); 
/*  52 */       obj.pitch = new NoiseConfig[pitchCount];
/*  53 */       int elemPos = varPos0 + varIntLen;
/*  54 */       for (int i = 0; i < pitchCount; i++) {
/*  55 */         obj.pitch[i] = NoiseConfig.deserialize(buf, elemPos);
/*  56 */         elemPos += NoiseConfig.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*  59 */     if ((nullBits & 0x2) != 0) {
/*  60 */       int varPos1 = offset + 13 + buf.getIntLE(offset + 5);
/*  61 */       int yawCount = VarInt.peek(buf, varPos1);
/*  62 */       if (yawCount < 0) throw ProtocolException.negativeLength("Yaw", yawCount); 
/*  63 */       if (yawCount > 4096000) throw ProtocolException.arrayTooLong("Yaw", yawCount, 4096000); 
/*  64 */       int varIntLen = VarInt.length(buf, varPos1);
/*  65 */       if ((varPos1 + varIntLen) + yawCount * 23L > buf.readableBytes())
/*  66 */         throw ProtocolException.bufferTooSmall("Yaw", varPos1 + varIntLen + yawCount * 23, buf.readableBytes()); 
/*  67 */       obj.yaw = new NoiseConfig[yawCount];
/*  68 */       int elemPos = varPos1 + varIntLen;
/*  69 */       for (int i = 0; i < yawCount; i++) {
/*  70 */         obj.yaw[i] = NoiseConfig.deserialize(buf, elemPos);
/*  71 */         elemPos += NoiseConfig.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*  74 */     if ((nullBits & 0x4) != 0) {
/*  75 */       int varPos2 = offset + 13 + buf.getIntLE(offset + 9);
/*  76 */       int rollCount = VarInt.peek(buf, varPos2);
/*  77 */       if (rollCount < 0) throw ProtocolException.negativeLength("Roll", rollCount); 
/*  78 */       if (rollCount > 4096000) throw ProtocolException.arrayTooLong("Roll", rollCount, 4096000); 
/*  79 */       int varIntLen = VarInt.length(buf, varPos2);
/*  80 */       if ((varPos2 + varIntLen) + rollCount * 23L > buf.readableBytes())
/*  81 */         throw ProtocolException.bufferTooSmall("Roll", varPos2 + varIntLen + rollCount * 23, buf.readableBytes()); 
/*  82 */       obj.roll = new NoiseConfig[rollCount];
/*  83 */       int elemPos = varPos2 + varIntLen;
/*  84 */       for (int i = 0; i < rollCount; i++) {
/*  85 */         obj.roll[i] = NoiseConfig.deserialize(buf, elemPos);
/*  86 */         elemPos += NoiseConfig.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  94 */     byte nullBits = buf.getByte(offset);
/*  95 */     int maxEnd = 13;
/*  96 */     if ((nullBits & 0x1) != 0) {
/*  97 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  98 */       int pos0 = offset + 13 + fieldOffset0;
/*  99 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0);
/* 100 */       for (int i = 0; i < arrLen; ) { pos0 += NoiseConfig.computeBytesConsumed(buf, pos0); i++; }
/* 101 */        if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 103 */     if ((nullBits & 0x2) != 0) {
/* 104 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/* 105 */       int pos1 = offset + 13 + fieldOffset1;
/* 106 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 107 */       for (int i = 0; i < arrLen; ) { pos1 += NoiseConfig.computeBytesConsumed(buf, pos1); i++; }
/* 108 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 110 */     if ((nullBits & 0x4) != 0) {
/* 111 */       int fieldOffset2 = buf.getIntLE(offset + 9);
/* 112 */       int pos2 = offset + 13 + fieldOffset2;
/* 113 */       int arrLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2);
/* 114 */       for (int i = 0; i < arrLen; ) { pos2 += NoiseConfig.computeBytesConsumed(buf, pos2); i++; }
/* 115 */        if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 117 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 122 */     int startPos = buf.writerIndex();
/* 123 */     byte nullBits = 0;
/* 124 */     if (this.pitch != null) nullBits = (byte)(nullBits | 0x1); 
/* 125 */     if (this.yaw != null) nullBits = (byte)(nullBits | 0x2); 
/* 126 */     if (this.roll != null) nullBits = (byte)(nullBits | 0x4); 
/* 127 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 130 */     int pitchOffsetSlot = buf.writerIndex();
/* 131 */     buf.writeIntLE(0);
/* 132 */     int yawOffsetSlot = buf.writerIndex();
/* 133 */     buf.writeIntLE(0);
/* 134 */     int rollOffsetSlot = buf.writerIndex();
/* 135 */     buf.writeIntLE(0);
/*     */     
/* 137 */     int varBlockStart = buf.writerIndex();
/* 138 */     if (this.pitch != null) {
/* 139 */       buf.setIntLE(pitchOffsetSlot, buf.writerIndex() - varBlockStart);
/* 140 */       if (this.pitch.length > 4096000) throw ProtocolException.arrayTooLong("Pitch", this.pitch.length, 4096000);  VarInt.write(buf, this.pitch.length); for (NoiseConfig item : this.pitch) item.serialize(buf); 
/*     */     } else {
/* 142 */       buf.setIntLE(pitchOffsetSlot, -1);
/*     */     } 
/* 144 */     if (this.yaw != null) {
/* 145 */       buf.setIntLE(yawOffsetSlot, buf.writerIndex() - varBlockStart);
/* 146 */       if (this.yaw.length > 4096000) throw ProtocolException.arrayTooLong("Yaw", this.yaw.length, 4096000);  VarInt.write(buf, this.yaw.length); for (NoiseConfig item : this.yaw) item.serialize(buf); 
/*     */     } else {
/* 148 */       buf.setIntLE(yawOffsetSlot, -1);
/*     */     } 
/* 150 */     if (this.roll != null) {
/* 151 */       buf.setIntLE(rollOffsetSlot, buf.writerIndex() - varBlockStart);
/* 152 */       if (this.roll.length > 4096000) throw ProtocolException.arrayTooLong("Roll", this.roll.length, 4096000);  VarInt.write(buf, this.roll.length); for (NoiseConfig item : this.roll) item.serialize(buf); 
/*     */     } else {
/* 154 */       buf.setIntLE(rollOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 160 */     int size = 13;
/* 161 */     if (this.pitch != null) size += VarInt.size(this.pitch.length) + this.pitch.length * 23; 
/* 162 */     if (this.yaw != null) size += VarInt.size(this.yaw.length) + this.yaw.length * 23; 
/* 163 */     if (this.roll != null) size += VarInt.size(this.roll.length) + this.roll.length * 23;
/*     */     
/* 165 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 169 */     if (buffer.readableBytes() - offset < 13) {
/* 170 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */     
/* 173 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 176 */     if ((nullBits & 0x1) != 0) {
/* 177 */       int pitchOffset = buffer.getIntLE(offset + 1);
/* 178 */       if (pitchOffset < 0) {
/* 179 */         return ValidationResult.error("Invalid offset for Pitch");
/*     */       }
/* 181 */       int pos = offset + 13 + pitchOffset;
/* 182 */       if (pos >= buffer.writerIndex()) {
/* 183 */         return ValidationResult.error("Offset out of bounds for Pitch");
/*     */       }
/* 185 */       int pitchCount = VarInt.peek(buffer, pos);
/* 186 */       if (pitchCount < 0) {
/* 187 */         return ValidationResult.error("Invalid array count for Pitch");
/*     */       }
/* 189 */       if (pitchCount > 4096000) {
/* 190 */         return ValidationResult.error("Pitch exceeds max length 4096000");
/*     */       }
/* 192 */       pos += VarInt.length(buffer, pos);
/* 193 */       pos += pitchCount * 23;
/* 194 */       if (pos > buffer.writerIndex()) {
/* 195 */         return ValidationResult.error("Buffer overflow reading Pitch");
/*     */       }
/*     */     } 
/*     */     
/* 199 */     if ((nullBits & 0x2) != 0) {
/* 200 */       int yawOffset = buffer.getIntLE(offset + 5);
/* 201 */       if (yawOffset < 0) {
/* 202 */         return ValidationResult.error("Invalid offset for Yaw");
/*     */       }
/* 204 */       int pos = offset + 13 + yawOffset;
/* 205 */       if (pos >= buffer.writerIndex()) {
/* 206 */         return ValidationResult.error("Offset out of bounds for Yaw");
/*     */       }
/* 208 */       int yawCount = VarInt.peek(buffer, pos);
/* 209 */       if (yawCount < 0) {
/* 210 */         return ValidationResult.error("Invalid array count for Yaw");
/*     */       }
/* 212 */       if (yawCount > 4096000) {
/* 213 */         return ValidationResult.error("Yaw exceeds max length 4096000");
/*     */       }
/* 215 */       pos += VarInt.length(buffer, pos);
/* 216 */       pos += yawCount * 23;
/* 217 */       if (pos > buffer.writerIndex()) {
/* 218 */         return ValidationResult.error("Buffer overflow reading Yaw");
/*     */       }
/*     */     } 
/*     */     
/* 222 */     if ((nullBits & 0x4) != 0) {
/* 223 */       int rollOffset = buffer.getIntLE(offset + 9);
/* 224 */       if (rollOffset < 0) {
/* 225 */         return ValidationResult.error("Invalid offset for Roll");
/*     */       }
/* 227 */       int pos = offset + 13 + rollOffset;
/* 228 */       if (pos >= buffer.writerIndex()) {
/* 229 */         return ValidationResult.error("Offset out of bounds for Roll");
/*     */       }
/* 231 */       int rollCount = VarInt.peek(buffer, pos);
/* 232 */       if (rollCount < 0) {
/* 233 */         return ValidationResult.error("Invalid array count for Roll");
/*     */       }
/* 235 */       if (rollCount > 4096000) {
/* 236 */         return ValidationResult.error("Roll exceeds max length 4096000");
/*     */       }
/* 238 */       pos += VarInt.length(buffer, pos);
/* 239 */       pos += rollCount * 23;
/* 240 */       if (pos > buffer.writerIndex()) {
/* 241 */         return ValidationResult.error("Buffer overflow reading Roll");
/*     */       }
/*     */     } 
/* 244 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public RotationNoise clone() {
/* 248 */     RotationNoise copy = new RotationNoise();
/* 249 */     copy.pitch = (this.pitch != null) ? (NoiseConfig[])Arrays.<NoiseConfig>stream(this.pitch).map(e -> e.clone()).toArray(x$0 -> new NoiseConfig[x$0]) : null;
/* 250 */     copy.yaw = (this.yaw != null) ? (NoiseConfig[])Arrays.<NoiseConfig>stream(this.yaw).map(e -> e.clone()).toArray(x$0 -> new NoiseConfig[x$0]) : null;
/* 251 */     copy.roll = (this.roll != null) ? (NoiseConfig[])Arrays.<NoiseConfig>stream(this.roll).map(e -> e.clone()).toArray(x$0 -> new NoiseConfig[x$0]) : null;
/* 252 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     RotationNoise other;
/* 258 */     if (this == obj) return true; 
/* 259 */     if (obj instanceof RotationNoise) { other = (RotationNoise)obj; } else { return false; }
/* 260 */      return (Arrays.equals((Object[])this.pitch, (Object[])other.pitch) && Arrays.equals((Object[])this.yaw, (Object[])other.yaw) && Arrays.equals((Object[])this.roll, (Object[])other.roll));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 265 */     int result = 1;
/* 266 */     result = 31 * result + Arrays.hashCode((Object[])this.pitch);
/* 267 */     result = 31 * result + Arrays.hashCode((Object[])this.yaw);
/* 268 */     result = 31 * result + Arrays.hashCode((Object[])this.roll);
/* 269 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\RotationNoise.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */