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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockTextures
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 6;
/*     */   public static final int VARIABLE_BLOCK_START = 29;
/*     */   public static final int MAX_SIZE = 98304059;
/*     */   @Nullable
/*     */   public String top;
/*     */   
/*     */   public BlockTextures(@Nullable String top, @Nullable String bottom, @Nullable String front, @Nullable String back, @Nullable String left, @Nullable String right, float weight) {
/*  32 */     this.top = top;
/*  33 */     this.bottom = bottom;
/*  34 */     this.front = front;
/*  35 */     this.back = back;
/*  36 */     this.left = left;
/*  37 */     this.right = right;
/*  38 */     this.weight = weight; } @Nullable public String bottom; @Nullable
/*     */   public String front; @Nullable
/*     */   public String back; @Nullable
/*     */   public String left; @Nullable
/*  42 */   public String right; public float weight; public BlockTextures() {} public BlockTextures(@Nonnull BlockTextures other) { this.top = other.top;
/*  43 */     this.bottom = other.bottom;
/*  44 */     this.front = other.front;
/*  45 */     this.back = other.back;
/*  46 */     this.left = other.left;
/*  47 */     this.right = other.right;
/*  48 */     this.weight = other.weight; }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static BlockTextures deserialize(@Nonnull ByteBuf buf, int offset) {
/*  53 */     BlockTextures obj = new BlockTextures();
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     obj.weight = buf.getFloatLE(offset + 1);
/*     */     
/*  57 */     if ((nullBits & 0x1) != 0) {
/*  58 */       int varPos0 = offset + 29 + buf.getIntLE(offset + 5);
/*  59 */       int topLen = VarInt.peek(buf, varPos0);
/*  60 */       if (topLen < 0) throw ProtocolException.negativeLength("Top", topLen); 
/*  61 */       if (topLen > 4096000) throw ProtocolException.stringTooLong("Top", topLen, 4096000); 
/*  62 */       obj.top = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  64 */     if ((nullBits & 0x2) != 0) {
/*  65 */       int varPos1 = offset + 29 + buf.getIntLE(offset + 9);
/*  66 */       int bottomLen = VarInt.peek(buf, varPos1);
/*  67 */       if (bottomLen < 0) throw ProtocolException.negativeLength("Bottom", bottomLen); 
/*  68 */       if (bottomLen > 4096000) throw ProtocolException.stringTooLong("Bottom", bottomLen, 4096000); 
/*  69 */       obj.bottom = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  71 */     if ((nullBits & 0x4) != 0) {
/*  72 */       int varPos2 = offset + 29 + buf.getIntLE(offset + 13);
/*  73 */       int frontLen = VarInt.peek(buf, varPos2);
/*  74 */       if (frontLen < 0) throw ProtocolException.negativeLength("Front", frontLen); 
/*  75 */       if (frontLen > 4096000) throw ProtocolException.stringTooLong("Front", frontLen, 4096000); 
/*  76 */       obj.front = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*  78 */     if ((nullBits & 0x8) != 0) {
/*  79 */       int varPos3 = offset + 29 + buf.getIntLE(offset + 17);
/*  80 */       int backLen = VarInt.peek(buf, varPos3);
/*  81 */       if (backLen < 0) throw ProtocolException.negativeLength("Back", backLen); 
/*  82 */       if (backLen > 4096000) throw ProtocolException.stringTooLong("Back", backLen, 4096000); 
/*  83 */       obj.back = PacketIO.readVarString(buf, varPos3, PacketIO.UTF8);
/*     */     } 
/*  85 */     if ((nullBits & 0x10) != 0) {
/*  86 */       int varPos4 = offset + 29 + buf.getIntLE(offset + 21);
/*  87 */       int leftLen = VarInt.peek(buf, varPos4);
/*  88 */       if (leftLen < 0) throw ProtocolException.negativeLength("Left", leftLen); 
/*  89 */       if (leftLen > 4096000) throw ProtocolException.stringTooLong("Left", leftLen, 4096000); 
/*  90 */       obj.left = PacketIO.readVarString(buf, varPos4, PacketIO.UTF8);
/*     */     } 
/*  92 */     if ((nullBits & 0x20) != 0) {
/*  93 */       int varPos5 = offset + 29 + buf.getIntLE(offset + 25);
/*  94 */       int rightLen = VarInt.peek(buf, varPos5);
/*  95 */       if (rightLen < 0) throw ProtocolException.negativeLength("Right", rightLen); 
/*  96 */       if (rightLen > 4096000) throw ProtocolException.stringTooLong("Right", rightLen, 4096000); 
/*  97 */       obj.right = PacketIO.readVarString(buf, varPos5, PacketIO.UTF8);
/*     */     } 
/*     */     
/* 100 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 104 */     byte nullBits = buf.getByte(offset);
/* 105 */     int maxEnd = 29;
/* 106 */     if ((nullBits & 0x1) != 0) {
/* 107 */       int fieldOffset0 = buf.getIntLE(offset + 5);
/* 108 */       int pos0 = offset + 29 + fieldOffset0;
/* 109 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 110 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 112 */     if ((nullBits & 0x2) != 0) {
/* 113 */       int fieldOffset1 = buf.getIntLE(offset + 9);
/* 114 */       int pos1 = offset + 29 + fieldOffset1;
/* 115 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/* 116 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 118 */     if ((nullBits & 0x4) != 0) {
/* 119 */       int fieldOffset2 = buf.getIntLE(offset + 13);
/* 120 */       int pos2 = offset + 29 + fieldOffset2;
/* 121 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/* 122 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 124 */     if ((nullBits & 0x8) != 0) {
/* 125 */       int fieldOffset3 = buf.getIntLE(offset + 17);
/* 126 */       int pos3 = offset + 29 + fieldOffset3;
/* 127 */       int sl = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + sl;
/* 128 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 130 */     if ((nullBits & 0x10) != 0) {
/* 131 */       int fieldOffset4 = buf.getIntLE(offset + 21);
/* 132 */       int pos4 = offset + 29 + fieldOffset4;
/* 133 */       int sl = VarInt.peek(buf, pos4); pos4 += VarInt.length(buf, pos4) + sl;
/* 134 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 136 */     if ((nullBits & 0x20) != 0) {
/* 137 */       int fieldOffset5 = buf.getIntLE(offset + 25);
/* 138 */       int pos5 = offset + 29 + fieldOffset5;
/* 139 */       int sl = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5) + sl;
/* 140 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 142 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 147 */     int startPos = buf.writerIndex();
/* 148 */     byte nullBits = 0;
/* 149 */     if (this.top != null) nullBits = (byte)(nullBits | 0x1); 
/* 150 */     if (this.bottom != null) nullBits = (byte)(nullBits | 0x2); 
/* 151 */     if (this.front != null) nullBits = (byte)(nullBits | 0x4); 
/* 152 */     if (this.back != null) nullBits = (byte)(nullBits | 0x8); 
/* 153 */     if (this.left != null) nullBits = (byte)(nullBits | 0x10); 
/* 154 */     if (this.right != null) nullBits = (byte)(nullBits | 0x20); 
/* 155 */     buf.writeByte(nullBits);
/*     */     
/* 157 */     buf.writeFloatLE(this.weight);
/*     */     
/* 159 */     int topOffsetSlot = buf.writerIndex();
/* 160 */     buf.writeIntLE(0);
/* 161 */     int bottomOffsetSlot = buf.writerIndex();
/* 162 */     buf.writeIntLE(0);
/* 163 */     int frontOffsetSlot = buf.writerIndex();
/* 164 */     buf.writeIntLE(0);
/* 165 */     int backOffsetSlot = buf.writerIndex();
/* 166 */     buf.writeIntLE(0);
/* 167 */     int leftOffsetSlot = buf.writerIndex();
/* 168 */     buf.writeIntLE(0);
/* 169 */     int rightOffsetSlot = buf.writerIndex();
/* 170 */     buf.writeIntLE(0);
/*     */     
/* 172 */     int varBlockStart = buf.writerIndex();
/* 173 */     if (this.top != null) {
/* 174 */       buf.setIntLE(topOffsetSlot, buf.writerIndex() - varBlockStart);
/* 175 */       PacketIO.writeVarString(buf, this.top, 4096000);
/*     */     } else {
/* 177 */       buf.setIntLE(topOffsetSlot, -1);
/*     */     } 
/* 179 */     if (this.bottom != null) {
/* 180 */       buf.setIntLE(bottomOffsetSlot, buf.writerIndex() - varBlockStart);
/* 181 */       PacketIO.writeVarString(buf, this.bottom, 4096000);
/*     */     } else {
/* 183 */       buf.setIntLE(bottomOffsetSlot, -1);
/*     */     } 
/* 185 */     if (this.front != null) {
/* 186 */       buf.setIntLE(frontOffsetSlot, buf.writerIndex() - varBlockStart);
/* 187 */       PacketIO.writeVarString(buf, this.front, 4096000);
/*     */     } else {
/* 189 */       buf.setIntLE(frontOffsetSlot, -1);
/*     */     } 
/* 191 */     if (this.back != null) {
/* 192 */       buf.setIntLE(backOffsetSlot, buf.writerIndex() - varBlockStart);
/* 193 */       PacketIO.writeVarString(buf, this.back, 4096000);
/*     */     } else {
/* 195 */       buf.setIntLE(backOffsetSlot, -1);
/*     */     } 
/* 197 */     if (this.left != null) {
/* 198 */       buf.setIntLE(leftOffsetSlot, buf.writerIndex() - varBlockStart);
/* 199 */       PacketIO.writeVarString(buf, this.left, 4096000);
/*     */     } else {
/* 201 */       buf.setIntLE(leftOffsetSlot, -1);
/*     */     } 
/* 203 */     if (this.right != null) {
/* 204 */       buf.setIntLE(rightOffsetSlot, buf.writerIndex() - varBlockStart);
/* 205 */       PacketIO.writeVarString(buf, this.right, 4096000);
/*     */     } else {
/* 207 */       buf.setIntLE(rightOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 213 */     int size = 29;
/* 214 */     if (this.top != null) size += PacketIO.stringSize(this.top); 
/* 215 */     if (this.bottom != null) size += PacketIO.stringSize(this.bottom); 
/* 216 */     if (this.front != null) size += PacketIO.stringSize(this.front); 
/* 217 */     if (this.back != null) size += PacketIO.stringSize(this.back); 
/* 218 */     if (this.left != null) size += PacketIO.stringSize(this.left); 
/* 219 */     if (this.right != null) size += PacketIO.stringSize(this.right);
/*     */     
/* 221 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 225 */     if (buffer.readableBytes() - offset < 29) {
/* 226 */       return ValidationResult.error("Buffer too small: expected at least 29 bytes");
/*     */     }
/*     */     
/* 229 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 232 */     if ((nullBits & 0x1) != 0) {
/* 233 */       int topOffset = buffer.getIntLE(offset + 5);
/* 234 */       if (topOffset < 0) {
/* 235 */         return ValidationResult.error("Invalid offset for Top");
/*     */       }
/* 237 */       int pos = offset + 29 + topOffset;
/* 238 */       if (pos >= buffer.writerIndex()) {
/* 239 */         return ValidationResult.error("Offset out of bounds for Top");
/*     */       }
/* 241 */       int topLen = VarInt.peek(buffer, pos);
/* 242 */       if (topLen < 0) {
/* 243 */         return ValidationResult.error("Invalid string length for Top");
/*     */       }
/* 245 */       if (topLen > 4096000) {
/* 246 */         return ValidationResult.error("Top exceeds max length 4096000");
/*     */       }
/* 248 */       pos += VarInt.length(buffer, pos);
/* 249 */       pos += topLen;
/* 250 */       if (pos > buffer.writerIndex()) {
/* 251 */         return ValidationResult.error("Buffer overflow reading Top");
/*     */       }
/*     */     } 
/*     */     
/* 255 */     if ((nullBits & 0x2) != 0) {
/* 256 */       int bottomOffset = buffer.getIntLE(offset + 9);
/* 257 */       if (bottomOffset < 0) {
/* 258 */         return ValidationResult.error("Invalid offset for Bottom");
/*     */       }
/* 260 */       int pos = offset + 29 + bottomOffset;
/* 261 */       if (pos >= buffer.writerIndex()) {
/* 262 */         return ValidationResult.error("Offset out of bounds for Bottom");
/*     */       }
/* 264 */       int bottomLen = VarInt.peek(buffer, pos);
/* 265 */       if (bottomLen < 0) {
/* 266 */         return ValidationResult.error("Invalid string length for Bottom");
/*     */       }
/* 268 */       if (bottomLen > 4096000) {
/* 269 */         return ValidationResult.error("Bottom exceeds max length 4096000");
/*     */       }
/* 271 */       pos += VarInt.length(buffer, pos);
/* 272 */       pos += bottomLen;
/* 273 */       if (pos > buffer.writerIndex()) {
/* 274 */         return ValidationResult.error("Buffer overflow reading Bottom");
/*     */       }
/*     */     } 
/*     */     
/* 278 */     if ((nullBits & 0x4) != 0) {
/* 279 */       int frontOffset = buffer.getIntLE(offset + 13);
/* 280 */       if (frontOffset < 0) {
/* 281 */         return ValidationResult.error("Invalid offset for Front");
/*     */       }
/* 283 */       int pos = offset + 29 + frontOffset;
/* 284 */       if (pos >= buffer.writerIndex()) {
/* 285 */         return ValidationResult.error("Offset out of bounds for Front");
/*     */       }
/* 287 */       int frontLen = VarInt.peek(buffer, pos);
/* 288 */       if (frontLen < 0) {
/* 289 */         return ValidationResult.error("Invalid string length for Front");
/*     */       }
/* 291 */       if (frontLen > 4096000) {
/* 292 */         return ValidationResult.error("Front exceeds max length 4096000");
/*     */       }
/* 294 */       pos += VarInt.length(buffer, pos);
/* 295 */       pos += frontLen;
/* 296 */       if (pos > buffer.writerIndex()) {
/* 297 */         return ValidationResult.error("Buffer overflow reading Front");
/*     */       }
/*     */     } 
/*     */     
/* 301 */     if ((nullBits & 0x8) != 0) {
/* 302 */       int backOffset = buffer.getIntLE(offset + 17);
/* 303 */       if (backOffset < 0) {
/* 304 */         return ValidationResult.error("Invalid offset for Back");
/*     */       }
/* 306 */       int pos = offset + 29 + backOffset;
/* 307 */       if (pos >= buffer.writerIndex()) {
/* 308 */         return ValidationResult.error("Offset out of bounds for Back");
/*     */       }
/* 310 */       int backLen = VarInt.peek(buffer, pos);
/* 311 */       if (backLen < 0) {
/* 312 */         return ValidationResult.error("Invalid string length for Back");
/*     */       }
/* 314 */       if (backLen > 4096000) {
/* 315 */         return ValidationResult.error("Back exceeds max length 4096000");
/*     */       }
/* 317 */       pos += VarInt.length(buffer, pos);
/* 318 */       pos += backLen;
/* 319 */       if (pos > buffer.writerIndex()) {
/* 320 */         return ValidationResult.error("Buffer overflow reading Back");
/*     */       }
/*     */     } 
/*     */     
/* 324 */     if ((nullBits & 0x10) != 0) {
/* 325 */       int leftOffset = buffer.getIntLE(offset + 21);
/* 326 */       if (leftOffset < 0) {
/* 327 */         return ValidationResult.error("Invalid offset for Left");
/*     */       }
/* 329 */       int pos = offset + 29 + leftOffset;
/* 330 */       if (pos >= buffer.writerIndex()) {
/* 331 */         return ValidationResult.error("Offset out of bounds for Left");
/*     */       }
/* 333 */       int leftLen = VarInt.peek(buffer, pos);
/* 334 */       if (leftLen < 0) {
/* 335 */         return ValidationResult.error("Invalid string length for Left");
/*     */       }
/* 337 */       if (leftLen > 4096000) {
/* 338 */         return ValidationResult.error("Left exceeds max length 4096000");
/*     */       }
/* 340 */       pos += VarInt.length(buffer, pos);
/* 341 */       pos += leftLen;
/* 342 */       if (pos > buffer.writerIndex()) {
/* 343 */         return ValidationResult.error("Buffer overflow reading Left");
/*     */       }
/*     */     } 
/*     */     
/* 347 */     if ((nullBits & 0x20) != 0) {
/* 348 */       int rightOffset = buffer.getIntLE(offset + 25);
/* 349 */       if (rightOffset < 0) {
/* 350 */         return ValidationResult.error("Invalid offset for Right");
/*     */       }
/* 352 */       int pos = offset + 29 + rightOffset;
/* 353 */       if (pos >= buffer.writerIndex()) {
/* 354 */         return ValidationResult.error("Offset out of bounds for Right");
/*     */       }
/* 356 */       int rightLen = VarInt.peek(buffer, pos);
/* 357 */       if (rightLen < 0) {
/* 358 */         return ValidationResult.error("Invalid string length for Right");
/*     */       }
/* 360 */       if (rightLen > 4096000) {
/* 361 */         return ValidationResult.error("Right exceeds max length 4096000");
/*     */       }
/* 363 */       pos += VarInt.length(buffer, pos);
/* 364 */       pos += rightLen;
/* 365 */       if (pos > buffer.writerIndex()) {
/* 366 */         return ValidationResult.error("Buffer overflow reading Right");
/*     */       }
/*     */     } 
/* 369 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BlockTextures clone() {
/* 373 */     BlockTextures copy = new BlockTextures();
/* 374 */     copy.top = this.top;
/* 375 */     copy.bottom = this.bottom;
/* 376 */     copy.front = this.front;
/* 377 */     copy.back = this.back;
/* 378 */     copy.left = this.left;
/* 379 */     copy.right = this.right;
/* 380 */     copy.weight = this.weight;
/* 381 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BlockTextures other;
/* 387 */     if (this == obj) return true; 
/* 388 */     if (obj instanceof BlockTextures) { other = (BlockTextures)obj; } else { return false; }
/* 389 */      return (Objects.equals(this.top, other.top) && Objects.equals(this.bottom, other.bottom) && Objects.equals(this.front, other.front) && Objects.equals(this.back, other.back) && Objects.equals(this.left, other.left) && Objects.equals(this.right, other.right) && this.weight == other.weight);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 394 */     return Objects.hash(new Object[] { this.top, this.bottom, this.front, this.back, this.left, this.right, Float.valueOf(this.weight) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BlockTextures.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */