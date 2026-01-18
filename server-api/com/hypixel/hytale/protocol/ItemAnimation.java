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
/*     */ public class ItemAnimation
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 12;
/*     */   public static final int VARIABLE_FIELD_COUNT = 5;
/*     */   public static final int VARIABLE_BLOCK_START = 32;
/*     */   public static final int MAX_SIZE = 81920057;
/*     */   @Nullable
/*     */   public String thirdPerson;
/*  27 */   public float blendingDuration = 0.2F; @Nullable
/*     */   public String thirdPersonMoving; @Nullable
/*     */   public String thirdPersonFace; @Nullable
/*     */   public String firstPerson; @Nullable
/*     */   public String firstPersonOverride; public boolean keepPreviousFirstPersonAnimation; public float speed; public boolean looping;
/*     */   public boolean clipsGeometry;
/*     */   
/*     */   public ItemAnimation(@Nullable String thirdPerson, @Nullable String thirdPersonMoving, @Nullable String thirdPersonFace, @Nullable String firstPerson, @Nullable String firstPersonOverride, boolean keepPreviousFirstPersonAnimation, float speed, float blendingDuration, boolean looping, boolean clipsGeometry) {
/*  35 */     this.thirdPerson = thirdPerson;
/*  36 */     this.thirdPersonMoving = thirdPersonMoving;
/*  37 */     this.thirdPersonFace = thirdPersonFace;
/*  38 */     this.firstPerson = firstPerson;
/*  39 */     this.firstPersonOverride = firstPersonOverride;
/*  40 */     this.keepPreviousFirstPersonAnimation = keepPreviousFirstPersonAnimation;
/*  41 */     this.speed = speed;
/*  42 */     this.blendingDuration = blendingDuration;
/*  43 */     this.looping = looping;
/*  44 */     this.clipsGeometry = clipsGeometry;
/*     */   }
/*     */   
/*     */   public ItemAnimation(@Nonnull ItemAnimation other) {
/*  48 */     this.thirdPerson = other.thirdPerson;
/*  49 */     this.thirdPersonMoving = other.thirdPersonMoving;
/*  50 */     this.thirdPersonFace = other.thirdPersonFace;
/*  51 */     this.firstPerson = other.firstPerson;
/*  52 */     this.firstPersonOverride = other.firstPersonOverride;
/*  53 */     this.keepPreviousFirstPersonAnimation = other.keepPreviousFirstPersonAnimation;
/*  54 */     this.speed = other.speed;
/*  55 */     this.blendingDuration = other.blendingDuration;
/*  56 */     this.looping = other.looping;
/*  57 */     this.clipsGeometry = other.clipsGeometry;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ItemAnimation deserialize(@Nonnull ByteBuf buf, int offset) {
/*  62 */     ItemAnimation obj = new ItemAnimation();
/*  63 */     byte nullBits = buf.getByte(offset);
/*  64 */     obj.keepPreviousFirstPersonAnimation = (buf.getByte(offset + 1) != 0);
/*  65 */     obj.speed = buf.getFloatLE(offset + 2);
/*  66 */     obj.blendingDuration = buf.getFloatLE(offset + 6);
/*  67 */     obj.looping = (buf.getByte(offset + 10) != 0);
/*  68 */     obj.clipsGeometry = (buf.getByte(offset + 11) != 0);
/*     */     
/*  70 */     if ((nullBits & 0x1) != 0) {
/*  71 */       int varPos0 = offset + 32 + buf.getIntLE(offset + 12);
/*  72 */       int thirdPersonLen = VarInt.peek(buf, varPos0);
/*  73 */       if (thirdPersonLen < 0) throw ProtocolException.negativeLength("ThirdPerson", thirdPersonLen); 
/*  74 */       if (thirdPersonLen > 4096000) throw ProtocolException.stringTooLong("ThirdPerson", thirdPersonLen, 4096000); 
/*  75 */       obj.thirdPerson = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  77 */     if ((nullBits & 0x2) != 0) {
/*  78 */       int varPos1 = offset + 32 + buf.getIntLE(offset + 16);
/*  79 */       int thirdPersonMovingLen = VarInt.peek(buf, varPos1);
/*  80 */       if (thirdPersonMovingLen < 0) throw ProtocolException.negativeLength("ThirdPersonMoving", thirdPersonMovingLen); 
/*  81 */       if (thirdPersonMovingLen > 4096000) throw ProtocolException.stringTooLong("ThirdPersonMoving", thirdPersonMovingLen, 4096000); 
/*  82 */       obj.thirdPersonMoving = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  84 */     if ((nullBits & 0x4) != 0) {
/*  85 */       int varPos2 = offset + 32 + buf.getIntLE(offset + 20);
/*  86 */       int thirdPersonFaceLen = VarInt.peek(buf, varPos2);
/*  87 */       if (thirdPersonFaceLen < 0) throw ProtocolException.negativeLength("ThirdPersonFace", thirdPersonFaceLen); 
/*  88 */       if (thirdPersonFaceLen > 4096000) throw ProtocolException.stringTooLong("ThirdPersonFace", thirdPersonFaceLen, 4096000); 
/*  89 */       obj.thirdPersonFace = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*  91 */     if ((nullBits & 0x8) != 0) {
/*  92 */       int varPos3 = offset + 32 + buf.getIntLE(offset + 24);
/*  93 */       int firstPersonLen = VarInt.peek(buf, varPos3);
/*  94 */       if (firstPersonLen < 0) throw ProtocolException.negativeLength("FirstPerson", firstPersonLen); 
/*  95 */       if (firstPersonLen > 4096000) throw ProtocolException.stringTooLong("FirstPerson", firstPersonLen, 4096000); 
/*  96 */       obj.firstPerson = PacketIO.readVarString(buf, varPos3, PacketIO.UTF8);
/*     */     } 
/*  98 */     if ((nullBits & 0x10) != 0) {
/*  99 */       int varPos4 = offset + 32 + buf.getIntLE(offset + 28);
/* 100 */       int firstPersonOverrideLen = VarInt.peek(buf, varPos4);
/* 101 */       if (firstPersonOverrideLen < 0) throw ProtocolException.negativeLength("FirstPersonOverride", firstPersonOverrideLen); 
/* 102 */       if (firstPersonOverrideLen > 4096000) throw ProtocolException.stringTooLong("FirstPersonOverride", firstPersonOverrideLen, 4096000); 
/* 103 */       obj.firstPersonOverride = PacketIO.readVarString(buf, varPos4, PacketIO.UTF8);
/*     */     } 
/*     */     
/* 106 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 110 */     byte nullBits = buf.getByte(offset);
/* 111 */     int maxEnd = 32;
/* 112 */     if ((nullBits & 0x1) != 0) {
/* 113 */       int fieldOffset0 = buf.getIntLE(offset + 12);
/* 114 */       int pos0 = offset + 32 + fieldOffset0;
/* 115 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 116 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 118 */     if ((nullBits & 0x2) != 0) {
/* 119 */       int fieldOffset1 = buf.getIntLE(offset + 16);
/* 120 */       int pos1 = offset + 32 + fieldOffset1;
/* 121 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/* 122 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 124 */     if ((nullBits & 0x4) != 0) {
/* 125 */       int fieldOffset2 = buf.getIntLE(offset + 20);
/* 126 */       int pos2 = offset + 32 + fieldOffset2;
/* 127 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/* 128 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 130 */     if ((nullBits & 0x8) != 0) {
/* 131 */       int fieldOffset3 = buf.getIntLE(offset + 24);
/* 132 */       int pos3 = offset + 32 + fieldOffset3;
/* 133 */       int sl = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + sl;
/* 134 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 136 */     if ((nullBits & 0x10) != 0) {
/* 137 */       int fieldOffset4 = buf.getIntLE(offset + 28);
/* 138 */       int pos4 = offset + 32 + fieldOffset4;
/* 139 */       int sl = VarInt.peek(buf, pos4); pos4 += VarInt.length(buf, pos4) + sl;
/* 140 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 142 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 147 */     int startPos = buf.writerIndex();
/* 148 */     byte nullBits = 0;
/* 149 */     if (this.thirdPerson != null) nullBits = (byte)(nullBits | 0x1); 
/* 150 */     if (this.thirdPersonMoving != null) nullBits = (byte)(nullBits | 0x2); 
/* 151 */     if (this.thirdPersonFace != null) nullBits = (byte)(nullBits | 0x4); 
/* 152 */     if (this.firstPerson != null) nullBits = (byte)(nullBits | 0x8); 
/* 153 */     if (this.firstPersonOverride != null) nullBits = (byte)(nullBits | 0x10); 
/* 154 */     buf.writeByte(nullBits);
/*     */     
/* 156 */     buf.writeByte(this.keepPreviousFirstPersonAnimation ? 1 : 0);
/* 157 */     buf.writeFloatLE(this.speed);
/* 158 */     buf.writeFloatLE(this.blendingDuration);
/* 159 */     buf.writeByte(this.looping ? 1 : 0);
/* 160 */     buf.writeByte(this.clipsGeometry ? 1 : 0);
/*     */     
/* 162 */     int thirdPersonOffsetSlot = buf.writerIndex();
/* 163 */     buf.writeIntLE(0);
/* 164 */     int thirdPersonMovingOffsetSlot = buf.writerIndex();
/* 165 */     buf.writeIntLE(0);
/* 166 */     int thirdPersonFaceOffsetSlot = buf.writerIndex();
/* 167 */     buf.writeIntLE(0);
/* 168 */     int firstPersonOffsetSlot = buf.writerIndex();
/* 169 */     buf.writeIntLE(0);
/* 170 */     int firstPersonOverrideOffsetSlot = buf.writerIndex();
/* 171 */     buf.writeIntLE(0);
/*     */     
/* 173 */     int varBlockStart = buf.writerIndex();
/* 174 */     if (this.thirdPerson != null) {
/* 175 */       buf.setIntLE(thirdPersonOffsetSlot, buf.writerIndex() - varBlockStart);
/* 176 */       PacketIO.writeVarString(buf, this.thirdPerson, 4096000);
/*     */     } else {
/* 178 */       buf.setIntLE(thirdPersonOffsetSlot, -1);
/*     */     } 
/* 180 */     if (this.thirdPersonMoving != null) {
/* 181 */       buf.setIntLE(thirdPersonMovingOffsetSlot, buf.writerIndex() - varBlockStart);
/* 182 */       PacketIO.writeVarString(buf, this.thirdPersonMoving, 4096000);
/*     */     } else {
/* 184 */       buf.setIntLE(thirdPersonMovingOffsetSlot, -1);
/*     */     } 
/* 186 */     if (this.thirdPersonFace != null) {
/* 187 */       buf.setIntLE(thirdPersonFaceOffsetSlot, buf.writerIndex() - varBlockStart);
/* 188 */       PacketIO.writeVarString(buf, this.thirdPersonFace, 4096000);
/*     */     } else {
/* 190 */       buf.setIntLE(thirdPersonFaceOffsetSlot, -1);
/*     */     } 
/* 192 */     if (this.firstPerson != null) {
/* 193 */       buf.setIntLE(firstPersonOffsetSlot, buf.writerIndex() - varBlockStart);
/* 194 */       PacketIO.writeVarString(buf, this.firstPerson, 4096000);
/*     */     } else {
/* 196 */       buf.setIntLE(firstPersonOffsetSlot, -1);
/*     */     } 
/* 198 */     if (this.firstPersonOverride != null) {
/* 199 */       buf.setIntLE(firstPersonOverrideOffsetSlot, buf.writerIndex() - varBlockStart);
/* 200 */       PacketIO.writeVarString(buf, this.firstPersonOverride, 4096000);
/*     */     } else {
/* 202 */       buf.setIntLE(firstPersonOverrideOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 208 */     int size = 32;
/* 209 */     if (this.thirdPerson != null) size += PacketIO.stringSize(this.thirdPerson); 
/* 210 */     if (this.thirdPersonMoving != null) size += PacketIO.stringSize(this.thirdPersonMoving); 
/* 211 */     if (this.thirdPersonFace != null) size += PacketIO.stringSize(this.thirdPersonFace); 
/* 212 */     if (this.firstPerson != null) size += PacketIO.stringSize(this.firstPerson); 
/* 213 */     if (this.firstPersonOverride != null) size += PacketIO.stringSize(this.firstPersonOverride);
/*     */     
/* 215 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 219 */     if (buffer.readableBytes() - offset < 32) {
/* 220 */       return ValidationResult.error("Buffer too small: expected at least 32 bytes");
/*     */     }
/*     */     
/* 223 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 226 */     if ((nullBits & 0x1) != 0) {
/* 227 */       int thirdPersonOffset = buffer.getIntLE(offset + 12);
/* 228 */       if (thirdPersonOffset < 0) {
/* 229 */         return ValidationResult.error("Invalid offset for ThirdPerson");
/*     */       }
/* 231 */       int pos = offset + 32 + thirdPersonOffset;
/* 232 */       if (pos >= buffer.writerIndex()) {
/* 233 */         return ValidationResult.error("Offset out of bounds for ThirdPerson");
/*     */       }
/* 235 */       int thirdPersonLen = VarInt.peek(buffer, pos);
/* 236 */       if (thirdPersonLen < 0) {
/* 237 */         return ValidationResult.error("Invalid string length for ThirdPerson");
/*     */       }
/* 239 */       if (thirdPersonLen > 4096000) {
/* 240 */         return ValidationResult.error("ThirdPerson exceeds max length 4096000");
/*     */       }
/* 242 */       pos += VarInt.length(buffer, pos);
/* 243 */       pos += thirdPersonLen;
/* 244 */       if (pos > buffer.writerIndex()) {
/* 245 */         return ValidationResult.error("Buffer overflow reading ThirdPerson");
/*     */       }
/*     */     } 
/*     */     
/* 249 */     if ((nullBits & 0x2) != 0) {
/* 250 */       int thirdPersonMovingOffset = buffer.getIntLE(offset + 16);
/* 251 */       if (thirdPersonMovingOffset < 0) {
/* 252 */         return ValidationResult.error("Invalid offset for ThirdPersonMoving");
/*     */       }
/* 254 */       int pos = offset + 32 + thirdPersonMovingOffset;
/* 255 */       if (pos >= buffer.writerIndex()) {
/* 256 */         return ValidationResult.error("Offset out of bounds for ThirdPersonMoving");
/*     */       }
/* 258 */       int thirdPersonMovingLen = VarInt.peek(buffer, pos);
/* 259 */       if (thirdPersonMovingLen < 0) {
/* 260 */         return ValidationResult.error("Invalid string length for ThirdPersonMoving");
/*     */       }
/* 262 */       if (thirdPersonMovingLen > 4096000) {
/* 263 */         return ValidationResult.error("ThirdPersonMoving exceeds max length 4096000");
/*     */       }
/* 265 */       pos += VarInt.length(buffer, pos);
/* 266 */       pos += thirdPersonMovingLen;
/* 267 */       if (pos > buffer.writerIndex()) {
/* 268 */         return ValidationResult.error("Buffer overflow reading ThirdPersonMoving");
/*     */       }
/*     */     } 
/*     */     
/* 272 */     if ((nullBits & 0x4) != 0) {
/* 273 */       int thirdPersonFaceOffset = buffer.getIntLE(offset + 20);
/* 274 */       if (thirdPersonFaceOffset < 0) {
/* 275 */         return ValidationResult.error("Invalid offset for ThirdPersonFace");
/*     */       }
/* 277 */       int pos = offset + 32 + thirdPersonFaceOffset;
/* 278 */       if (pos >= buffer.writerIndex()) {
/* 279 */         return ValidationResult.error("Offset out of bounds for ThirdPersonFace");
/*     */       }
/* 281 */       int thirdPersonFaceLen = VarInt.peek(buffer, pos);
/* 282 */       if (thirdPersonFaceLen < 0) {
/* 283 */         return ValidationResult.error("Invalid string length for ThirdPersonFace");
/*     */       }
/* 285 */       if (thirdPersonFaceLen > 4096000) {
/* 286 */         return ValidationResult.error("ThirdPersonFace exceeds max length 4096000");
/*     */       }
/* 288 */       pos += VarInt.length(buffer, pos);
/* 289 */       pos += thirdPersonFaceLen;
/* 290 */       if (pos > buffer.writerIndex()) {
/* 291 */         return ValidationResult.error("Buffer overflow reading ThirdPersonFace");
/*     */       }
/*     */     } 
/*     */     
/* 295 */     if ((nullBits & 0x8) != 0) {
/* 296 */       int firstPersonOffset = buffer.getIntLE(offset + 24);
/* 297 */       if (firstPersonOffset < 0) {
/* 298 */         return ValidationResult.error("Invalid offset for FirstPerson");
/*     */       }
/* 300 */       int pos = offset + 32 + firstPersonOffset;
/* 301 */       if (pos >= buffer.writerIndex()) {
/* 302 */         return ValidationResult.error("Offset out of bounds for FirstPerson");
/*     */       }
/* 304 */       int firstPersonLen = VarInt.peek(buffer, pos);
/* 305 */       if (firstPersonLen < 0) {
/* 306 */         return ValidationResult.error("Invalid string length for FirstPerson");
/*     */       }
/* 308 */       if (firstPersonLen > 4096000) {
/* 309 */         return ValidationResult.error("FirstPerson exceeds max length 4096000");
/*     */       }
/* 311 */       pos += VarInt.length(buffer, pos);
/* 312 */       pos += firstPersonLen;
/* 313 */       if (pos > buffer.writerIndex()) {
/* 314 */         return ValidationResult.error("Buffer overflow reading FirstPerson");
/*     */       }
/*     */     } 
/*     */     
/* 318 */     if ((nullBits & 0x10) != 0) {
/* 319 */       int firstPersonOverrideOffset = buffer.getIntLE(offset + 28);
/* 320 */       if (firstPersonOverrideOffset < 0) {
/* 321 */         return ValidationResult.error("Invalid offset for FirstPersonOverride");
/*     */       }
/* 323 */       int pos = offset + 32 + firstPersonOverrideOffset;
/* 324 */       if (pos >= buffer.writerIndex()) {
/* 325 */         return ValidationResult.error("Offset out of bounds for FirstPersonOverride");
/*     */       }
/* 327 */       int firstPersonOverrideLen = VarInt.peek(buffer, pos);
/* 328 */       if (firstPersonOverrideLen < 0) {
/* 329 */         return ValidationResult.error("Invalid string length for FirstPersonOverride");
/*     */       }
/* 331 */       if (firstPersonOverrideLen > 4096000) {
/* 332 */         return ValidationResult.error("FirstPersonOverride exceeds max length 4096000");
/*     */       }
/* 334 */       pos += VarInt.length(buffer, pos);
/* 335 */       pos += firstPersonOverrideLen;
/* 336 */       if (pos > buffer.writerIndex()) {
/* 337 */         return ValidationResult.error("Buffer overflow reading FirstPersonOverride");
/*     */       }
/*     */     } 
/* 340 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemAnimation clone() {
/* 344 */     ItemAnimation copy = new ItemAnimation();
/* 345 */     copy.thirdPerson = this.thirdPerson;
/* 346 */     copy.thirdPersonMoving = this.thirdPersonMoving;
/* 347 */     copy.thirdPersonFace = this.thirdPersonFace;
/* 348 */     copy.firstPerson = this.firstPerson;
/* 349 */     copy.firstPersonOverride = this.firstPersonOverride;
/* 350 */     copy.keepPreviousFirstPersonAnimation = this.keepPreviousFirstPersonAnimation;
/* 351 */     copy.speed = this.speed;
/* 352 */     copy.blendingDuration = this.blendingDuration;
/* 353 */     copy.looping = this.looping;
/* 354 */     copy.clipsGeometry = this.clipsGeometry;
/* 355 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemAnimation other;
/* 361 */     if (this == obj) return true; 
/* 362 */     if (obj instanceof ItemAnimation) { other = (ItemAnimation)obj; } else { return false; }
/* 363 */      return (Objects.equals(this.thirdPerson, other.thirdPerson) && Objects.equals(this.thirdPersonMoving, other.thirdPersonMoving) && Objects.equals(this.thirdPersonFace, other.thirdPersonFace) && Objects.equals(this.firstPerson, other.firstPerson) && Objects.equals(this.firstPersonOverride, other.firstPersonOverride) && this.keepPreviousFirstPersonAnimation == other.keepPreviousFirstPersonAnimation && this.speed == other.speed && this.blendingDuration == other.blendingDuration && this.looping == other.looping && this.clipsGeometry == other.clipsGeometry);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 368 */     return Objects.hash(new Object[] { this.thirdPerson, this.thirdPersonMoving, this.thirdPersonFace, this.firstPerson, this.firstPersonOverride, Boolean.valueOf(this.keepPreviousFirstPersonAnimation), Float.valueOf(this.speed), Float.valueOf(this.blendingDuration), Boolean.valueOf(this.looping), Boolean.valueOf(this.clipsGeometry) });
/*     */   }
/*     */   
/*     */   public ItemAnimation() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemAnimation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */