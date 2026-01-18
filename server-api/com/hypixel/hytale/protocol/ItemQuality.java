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
/*     */ public class ItemQuality
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 7;
/*     */   public static final int VARIABLE_FIELD_COUNT = 7;
/*     */   public static final int VARIABLE_BLOCK_START = 35;
/*     */   public static final int MAX_SIZE = 114688070;
/*     */   @Nullable
/*     */   public String id;
/*     */   @Nullable
/*     */   public String itemTooltipTexture;
/*     */   @Nullable
/*     */   public String itemTooltipArrowTexture;
/*     */   
/*     */   public ItemQuality(@Nullable String id, @Nullable String itemTooltipTexture, @Nullable String itemTooltipArrowTexture, @Nullable String slotTexture, @Nullable String blockSlotTexture, @Nullable String specialSlotTexture, @Nullable Color textColor, @Nullable String localizationKey, boolean visibleQualityLabel, boolean renderSpecialSlot, boolean hideFromSearch) {
/*  36 */     this.id = id;
/*  37 */     this.itemTooltipTexture = itemTooltipTexture;
/*  38 */     this.itemTooltipArrowTexture = itemTooltipArrowTexture;
/*  39 */     this.slotTexture = slotTexture;
/*  40 */     this.blockSlotTexture = blockSlotTexture;
/*  41 */     this.specialSlotTexture = specialSlotTexture;
/*  42 */     this.textColor = textColor;
/*  43 */     this.localizationKey = localizationKey;
/*  44 */     this.visibleQualityLabel = visibleQualityLabel;
/*  45 */     this.renderSpecialSlot = renderSpecialSlot;
/*  46 */     this.hideFromSearch = hideFromSearch; } @Nullable public String slotTexture; @Nullable
/*     */   public String blockSlotTexture; @Nullable
/*     */   public String specialSlotTexture; @Nullable
/*     */   public Color textColor; @Nullable
/*  50 */   public String localizationKey; public boolean visibleQualityLabel; public boolean renderSpecialSlot; public boolean hideFromSearch; public ItemQuality() {} public ItemQuality(@Nonnull ItemQuality other) { this.id = other.id;
/*  51 */     this.itemTooltipTexture = other.itemTooltipTexture;
/*  52 */     this.itemTooltipArrowTexture = other.itemTooltipArrowTexture;
/*  53 */     this.slotTexture = other.slotTexture;
/*  54 */     this.blockSlotTexture = other.blockSlotTexture;
/*  55 */     this.specialSlotTexture = other.specialSlotTexture;
/*  56 */     this.textColor = other.textColor;
/*  57 */     this.localizationKey = other.localizationKey;
/*  58 */     this.visibleQualityLabel = other.visibleQualityLabel;
/*  59 */     this.renderSpecialSlot = other.renderSpecialSlot;
/*  60 */     this.hideFromSearch = other.hideFromSearch; }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ItemQuality deserialize(@Nonnull ByteBuf buf, int offset) {
/*  65 */     ItemQuality obj = new ItemQuality();
/*  66 */     byte nullBits = buf.getByte(offset);
/*  67 */     if ((nullBits & 0x40) != 0) obj.textColor = Color.deserialize(buf, offset + 1); 
/*  68 */     obj.visibleQualityLabel = (buf.getByte(offset + 4) != 0);
/*  69 */     obj.renderSpecialSlot = (buf.getByte(offset + 5) != 0);
/*  70 */     obj.hideFromSearch = (buf.getByte(offset + 6) != 0);
/*     */     
/*  72 */     if ((nullBits & 0x1) != 0) {
/*  73 */       int varPos0 = offset + 35 + buf.getIntLE(offset + 7);
/*  74 */       int idLen = VarInt.peek(buf, varPos0);
/*  75 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  76 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  77 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  79 */     if ((nullBits & 0x2) != 0) {
/*  80 */       int varPos1 = offset + 35 + buf.getIntLE(offset + 11);
/*  81 */       int itemTooltipTextureLen = VarInt.peek(buf, varPos1);
/*  82 */       if (itemTooltipTextureLen < 0) throw ProtocolException.negativeLength("ItemTooltipTexture", itemTooltipTextureLen); 
/*  83 */       if (itemTooltipTextureLen > 4096000) throw ProtocolException.stringTooLong("ItemTooltipTexture", itemTooltipTextureLen, 4096000); 
/*  84 */       obj.itemTooltipTexture = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  86 */     if ((nullBits & 0x4) != 0) {
/*  87 */       int varPos2 = offset + 35 + buf.getIntLE(offset + 15);
/*  88 */       int itemTooltipArrowTextureLen = VarInt.peek(buf, varPos2);
/*  89 */       if (itemTooltipArrowTextureLen < 0) throw ProtocolException.negativeLength("ItemTooltipArrowTexture", itemTooltipArrowTextureLen); 
/*  90 */       if (itemTooltipArrowTextureLen > 4096000) throw ProtocolException.stringTooLong("ItemTooltipArrowTexture", itemTooltipArrowTextureLen, 4096000); 
/*  91 */       obj.itemTooltipArrowTexture = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*  93 */     if ((nullBits & 0x8) != 0) {
/*  94 */       int varPos3 = offset + 35 + buf.getIntLE(offset + 19);
/*  95 */       int slotTextureLen = VarInt.peek(buf, varPos3);
/*  96 */       if (slotTextureLen < 0) throw ProtocolException.negativeLength("SlotTexture", slotTextureLen); 
/*  97 */       if (slotTextureLen > 4096000) throw ProtocolException.stringTooLong("SlotTexture", slotTextureLen, 4096000); 
/*  98 */       obj.slotTexture = PacketIO.readVarString(buf, varPos3, PacketIO.UTF8);
/*     */     } 
/* 100 */     if ((nullBits & 0x10) != 0) {
/* 101 */       int varPos4 = offset + 35 + buf.getIntLE(offset + 23);
/* 102 */       int blockSlotTextureLen = VarInt.peek(buf, varPos4);
/* 103 */       if (blockSlotTextureLen < 0) throw ProtocolException.negativeLength("BlockSlotTexture", blockSlotTextureLen); 
/* 104 */       if (blockSlotTextureLen > 4096000) throw ProtocolException.stringTooLong("BlockSlotTexture", blockSlotTextureLen, 4096000); 
/* 105 */       obj.blockSlotTexture = PacketIO.readVarString(buf, varPos4, PacketIO.UTF8);
/*     */     } 
/* 107 */     if ((nullBits & 0x20) != 0) {
/* 108 */       int varPos5 = offset + 35 + buf.getIntLE(offset + 27);
/* 109 */       int specialSlotTextureLen = VarInt.peek(buf, varPos5);
/* 110 */       if (specialSlotTextureLen < 0) throw ProtocolException.negativeLength("SpecialSlotTexture", specialSlotTextureLen); 
/* 111 */       if (specialSlotTextureLen > 4096000) throw ProtocolException.stringTooLong("SpecialSlotTexture", specialSlotTextureLen, 4096000); 
/* 112 */       obj.specialSlotTexture = PacketIO.readVarString(buf, varPos5, PacketIO.UTF8);
/*     */     } 
/* 114 */     if ((nullBits & 0x80) != 0) {
/* 115 */       int varPos6 = offset + 35 + buf.getIntLE(offset + 31);
/* 116 */       int localizationKeyLen = VarInt.peek(buf, varPos6);
/* 117 */       if (localizationKeyLen < 0) throw ProtocolException.negativeLength("LocalizationKey", localizationKeyLen); 
/* 118 */       if (localizationKeyLen > 4096000) throw ProtocolException.stringTooLong("LocalizationKey", localizationKeyLen, 4096000); 
/* 119 */       obj.localizationKey = PacketIO.readVarString(buf, varPos6, PacketIO.UTF8);
/*     */     } 
/*     */     
/* 122 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 126 */     byte nullBits = buf.getByte(offset);
/* 127 */     int maxEnd = 35;
/* 128 */     if ((nullBits & 0x1) != 0) {
/* 129 */       int fieldOffset0 = buf.getIntLE(offset + 7);
/* 130 */       int pos0 = offset + 35 + fieldOffset0;
/* 131 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 132 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 134 */     if ((nullBits & 0x2) != 0) {
/* 135 */       int fieldOffset1 = buf.getIntLE(offset + 11);
/* 136 */       int pos1 = offset + 35 + fieldOffset1;
/* 137 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/* 138 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 140 */     if ((nullBits & 0x4) != 0) {
/* 141 */       int fieldOffset2 = buf.getIntLE(offset + 15);
/* 142 */       int pos2 = offset + 35 + fieldOffset2;
/* 143 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/* 144 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 146 */     if ((nullBits & 0x8) != 0) {
/* 147 */       int fieldOffset3 = buf.getIntLE(offset + 19);
/* 148 */       int pos3 = offset + 35 + fieldOffset3;
/* 149 */       int sl = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + sl;
/* 150 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 152 */     if ((nullBits & 0x10) != 0) {
/* 153 */       int fieldOffset4 = buf.getIntLE(offset + 23);
/* 154 */       int pos4 = offset + 35 + fieldOffset4;
/* 155 */       int sl = VarInt.peek(buf, pos4); pos4 += VarInt.length(buf, pos4) + sl;
/* 156 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 158 */     if ((nullBits & 0x20) != 0) {
/* 159 */       int fieldOffset5 = buf.getIntLE(offset + 27);
/* 160 */       int pos5 = offset + 35 + fieldOffset5;
/* 161 */       int sl = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5) + sl;
/* 162 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 164 */     if ((nullBits & 0x80) != 0) {
/* 165 */       int fieldOffset6 = buf.getIntLE(offset + 31);
/* 166 */       int pos6 = offset + 35 + fieldOffset6;
/* 167 */       int sl = VarInt.peek(buf, pos6); pos6 += VarInt.length(buf, pos6) + sl;
/* 168 */       if (pos6 - offset > maxEnd) maxEnd = pos6 - offset; 
/*     */     } 
/* 170 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 175 */     int startPos = buf.writerIndex();
/* 176 */     byte nullBits = 0;
/* 177 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 178 */     if (this.itemTooltipTexture != null) nullBits = (byte)(nullBits | 0x2); 
/* 179 */     if (this.itemTooltipArrowTexture != null) nullBits = (byte)(nullBits | 0x4); 
/* 180 */     if (this.slotTexture != null) nullBits = (byte)(nullBits | 0x8); 
/* 181 */     if (this.blockSlotTexture != null) nullBits = (byte)(nullBits | 0x10); 
/* 182 */     if (this.specialSlotTexture != null) nullBits = (byte)(nullBits | 0x20); 
/* 183 */     if (this.textColor != null) nullBits = (byte)(nullBits | 0x40); 
/* 184 */     if (this.localizationKey != null) nullBits = (byte)(nullBits | 0x80); 
/* 185 */     buf.writeByte(nullBits);
/*     */     
/* 187 */     if (this.textColor != null) { this.textColor.serialize(buf); } else { buf.writeZero(3); }
/* 188 */      buf.writeByte(this.visibleQualityLabel ? 1 : 0);
/* 189 */     buf.writeByte(this.renderSpecialSlot ? 1 : 0);
/* 190 */     buf.writeByte(this.hideFromSearch ? 1 : 0);
/*     */     
/* 192 */     int idOffsetSlot = buf.writerIndex();
/* 193 */     buf.writeIntLE(0);
/* 194 */     int itemTooltipTextureOffsetSlot = buf.writerIndex();
/* 195 */     buf.writeIntLE(0);
/* 196 */     int itemTooltipArrowTextureOffsetSlot = buf.writerIndex();
/* 197 */     buf.writeIntLE(0);
/* 198 */     int slotTextureOffsetSlot = buf.writerIndex();
/* 199 */     buf.writeIntLE(0);
/* 200 */     int blockSlotTextureOffsetSlot = buf.writerIndex();
/* 201 */     buf.writeIntLE(0);
/* 202 */     int specialSlotTextureOffsetSlot = buf.writerIndex();
/* 203 */     buf.writeIntLE(0);
/* 204 */     int localizationKeyOffsetSlot = buf.writerIndex();
/* 205 */     buf.writeIntLE(0);
/*     */     
/* 207 */     int varBlockStart = buf.writerIndex();
/* 208 */     if (this.id != null) {
/* 209 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 210 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 212 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 214 */     if (this.itemTooltipTexture != null) {
/* 215 */       buf.setIntLE(itemTooltipTextureOffsetSlot, buf.writerIndex() - varBlockStart);
/* 216 */       PacketIO.writeVarString(buf, this.itemTooltipTexture, 4096000);
/*     */     } else {
/* 218 */       buf.setIntLE(itemTooltipTextureOffsetSlot, -1);
/*     */     } 
/* 220 */     if (this.itemTooltipArrowTexture != null) {
/* 221 */       buf.setIntLE(itemTooltipArrowTextureOffsetSlot, buf.writerIndex() - varBlockStart);
/* 222 */       PacketIO.writeVarString(buf, this.itemTooltipArrowTexture, 4096000);
/*     */     } else {
/* 224 */       buf.setIntLE(itemTooltipArrowTextureOffsetSlot, -1);
/*     */     } 
/* 226 */     if (this.slotTexture != null) {
/* 227 */       buf.setIntLE(slotTextureOffsetSlot, buf.writerIndex() - varBlockStart);
/* 228 */       PacketIO.writeVarString(buf, this.slotTexture, 4096000);
/*     */     } else {
/* 230 */       buf.setIntLE(slotTextureOffsetSlot, -1);
/*     */     } 
/* 232 */     if (this.blockSlotTexture != null) {
/* 233 */       buf.setIntLE(blockSlotTextureOffsetSlot, buf.writerIndex() - varBlockStart);
/* 234 */       PacketIO.writeVarString(buf, this.blockSlotTexture, 4096000);
/*     */     } else {
/* 236 */       buf.setIntLE(blockSlotTextureOffsetSlot, -1);
/*     */     } 
/* 238 */     if (this.specialSlotTexture != null) {
/* 239 */       buf.setIntLE(specialSlotTextureOffsetSlot, buf.writerIndex() - varBlockStart);
/* 240 */       PacketIO.writeVarString(buf, this.specialSlotTexture, 4096000);
/*     */     } else {
/* 242 */       buf.setIntLE(specialSlotTextureOffsetSlot, -1);
/*     */     } 
/* 244 */     if (this.localizationKey != null) {
/* 245 */       buf.setIntLE(localizationKeyOffsetSlot, buf.writerIndex() - varBlockStart);
/* 246 */       PacketIO.writeVarString(buf, this.localizationKey, 4096000);
/*     */     } else {
/* 248 */       buf.setIntLE(localizationKeyOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 254 */     int size = 35;
/* 255 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 256 */     if (this.itemTooltipTexture != null) size += PacketIO.stringSize(this.itemTooltipTexture); 
/* 257 */     if (this.itemTooltipArrowTexture != null) size += PacketIO.stringSize(this.itemTooltipArrowTexture); 
/* 258 */     if (this.slotTexture != null) size += PacketIO.stringSize(this.slotTexture); 
/* 259 */     if (this.blockSlotTexture != null) size += PacketIO.stringSize(this.blockSlotTexture); 
/* 260 */     if (this.specialSlotTexture != null) size += PacketIO.stringSize(this.specialSlotTexture); 
/* 261 */     if (this.localizationKey != null) size += PacketIO.stringSize(this.localizationKey);
/*     */     
/* 263 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 267 */     if (buffer.readableBytes() - offset < 35) {
/* 268 */       return ValidationResult.error("Buffer too small: expected at least 35 bytes");
/*     */     }
/*     */     
/* 271 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 274 */     if ((nullBits & 0x1) != 0) {
/* 275 */       int idOffset = buffer.getIntLE(offset + 7);
/* 276 */       if (idOffset < 0) {
/* 277 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 279 */       int pos = offset + 35 + idOffset;
/* 280 */       if (pos >= buffer.writerIndex()) {
/* 281 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 283 */       int idLen = VarInt.peek(buffer, pos);
/* 284 */       if (idLen < 0) {
/* 285 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 287 */       if (idLen > 4096000) {
/* 288 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 290 */       pos += VarInt.length(buffer, pos);
/* 291 */       pos += idLen;
/* 292 */       if (pos > buffer.writerIndex()) {
/* 293 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 297 */     if ((nullBits & 0x2) != 0) {
/* 298 */       int itemTooltipTextureOffset = buffer.getIntLE(offset + 11);
/* 299 */       if (itemTooltipTextureOffset < 0) {
/* 300 */         return ValidationResult.error("Invalid offset for ItemTooltipTexture");
/*     */       }
/* 302 */       int pos = offset + 35 + itemTooltipTextureOffset;
/* 303 */       if (pos >= buffer.writerIndex()) {
/* 304 */         return ValidationResult.error("Offset out of bounds for ItemTooltipTexture");
/*     */       }
/* 306 */       int itemTooltipTextureLen = VarInt.peek(buffer, pos);
/* 307 */       if (itemTooltipTextureLen < 0) {
/* 308 */         return ValidationResult.error("Invalid string length for ItemTooltipTexture");
/*     */       }
/* 310 */       if (itemTooltipTextureLen > 4096000) {
/* 311 */         return ValidationResult.error("ItemTooltipTexture exceeds max length 4096000");
/*     */       }
/* 313 */       pos += VarInt.length(buffer, pos);
/* 314 */       pos += itemTooltipTextureLen;
/* 315 */       if (pos > buffer.writerIndex()) {
/* 316 */         return ValidationResult.error("Buffer overflow reading ItemTooltipTexture");
/*     */       }
/*     */     } 
/*     */     
/* 320 */     if ((nullBits & 0x4) != 0) {
/* 321 */       int itemTooltipArrowTextureOffset = buffer.getIntLE(offset + 15);
/* 322 */       if (itemTooltipArrowTextureOffset < 0) {
/* 323 */         return ValidationResult.error("Invalid offset for ItemTooltipArrowTexture");
/*     */       }
/* 325 */       int pos = offset + 35 + itemTooltipArrowTextureOffset;
/* 326 */       if (pos >= buffer.writerIndex()) {
/* 327 */         return ValidationResult.error("Offset out of bounds for ItemTooltipArrowTexture");
/*     */       }
/* 329 */       int itemTooltipArrowTextureLen = VarInt.peek(buffer, pos);
/* 330 */       if (itemTooltipArrowTextureLen < 0) {
/* 331 */         return ValidationResult.error("Invalid string length for ItemTooltipArrowTexture");
/*     */       }
/* 333 */       if (itemTooltipArrowTextureLen > 4096000) {
/* 334 */         return ValidationResult.error("ItemTooltipArrowTexture exceeds max length 4096000");
/*     */       }
/* 336 */       pos += VarInt.length(buffer, pos);
/* 337 */       pos += itemTooltipArrowTextureLen;
/* 338 */       if (pos > buffer.writerIndex()) {
/* 339 */         return ValidationResult.error("Buffer overflow reading ItemTooltipArrowTexture");
/*     */       }
/*     */     } 
/*     */     
/* 343 */     if ((nullBits & 0x8) != 0) {
/* 344 */       int slotTextureOffset = buffer.getIntLE(offset + 19);
/* 345 */       if (slotTextureOffset < 0) {
/* 346 */         return ValidationResult.error("Invalid offset for SlotTexture");
/*     */       }
/* 348 */       int pos = offset + 35 + slotTextureOffset;
/* 349 */       if (pos >= buffer.writerIndex()) {
/* 350 */         return ValidationResult.error("Offset out of bounds for SlotTexture");
/*     */       }
/* 352 */       int slotTextureLen = VarInt.peek(buffer, pos);
/* 353 */       if (slotTextureLen < 0) {
/* 354 */         return ValidationResult.error("Invalid string length for SlotTexture");
/*     */       }
/* 356 */       if (slotTextureLen > 4096000) {
/* 357 */         return ValidationResult.error("SlotTexture exceeds max length 4096000");
/*     */       }
/* 359 */       pos += VarInt.length(buffer, pos);
/* 360 */       pos += slotTextureLen;
/* 361 */       if (pos > buffer.writerIndex()) {
/* 362 */         return ValidationResult.error("Buffer overflow reading SlotTexture");
/*     */       }
/*     */     } 
/*     */     
/* 366 */     if ((nullBits & 0x10) != 0) {
/* 367 */       int blockSlotTextureOffset = buffer.getIntLE(offset + 23);
/* 368 */       if (blockSlotTextureOffset < 0) {
/* 369 */         return ValidationResult.error("Invalid offset for BlockSlotTexture");
/*     */       }
/* 371 */       int pos = offset + 35 + blockSlotTextureOffset;
/* 372 */       if (pos >= buffer.writerIndex()) {
/* 373 */         return ValidationResult.error("Offset out of bounds for BlockSlotTexture");
/*     */       }
/* 375 */       int blockSlotTextureLen = VarInt.peek(buffer, pos);
/* 376 */       if (blockSlotTextureLen < 0) {
/* 377 */         return ValidationResult.error("Invalid string length for BlockSlotTexture");
/*     */       }
/* 379 */       if (blockSlotTextureLen > 4096000) {
/* 380 */         return ValidationResult.error("BlockSlotTexture exceeds max length 4096000");
/*     */       }
/* 382 */       pos += VarInt.length(buffer, pos);
/* 383 */       pos += blockSlotTextureLen;
/* 384 */       if (pos > buffer.writerIndex()) {
/* 385 */         return ValidationResult.error("Buffer overflow reading BlockSlotTexture");
/*     */       }
/*     */     } 
/*     */     
/* 389 */     if ((nullBits & 0x20) != 0) {
/* 390 */       int specialSlotTextureOffset = buffer.getIntLE(offset + 27);
/* 391 */       if (specialSlotTextureOffset < 0) {
/* 392 */         return ValidationResult.error("Invalid offset for SpecialSlotTexture");
/*     */       }
/* 394 */       int pos = offset + 35 + specialSlotTextureOffset;
/* 395 */       if (pos >= buffer.writerIndex()) {
/* 396 */         return ValidationResult.error("Offset out of bounds for SpecialSlotTexture");
/*     */       }
/* 398 */       int specialSlotTextureLen = VarInt.peek(buffer, pos);
/* 399 */       if (specialSlotTextureLen < 0) {
/* 400 */         return ValidationResult.error("Invalid string length for SpecialSlotTexture");
/*     */       }
/* 402 */       if (specialSlotTextureLen > 4096000) {
/* 403 */         return ValidationResult.error("SpecialSlotTexture exceeds max length 4096000");
/*     */       }
/* 405 */       pos += VarInt.length(buffer, pos);
/* 406 */       pos += specialSlotTextureLen;
/* 407 */       if (pos > buffer.writerIndex()) {
/* 408 */         return ValidationResult.error("Buffer overflow reading SpecialSlotTexture");
/*     */       }
/*     */     } 
/*     */     
/* 412 */     if ((nullBits & 0x80) != 0) {
/* 413 */       int localizationKeyOffset = buffer.getIntLE(offset + 31);
/* 414 */       if (localizationKeyOffset < 0) {
/* 415 */         return ValidationResult.error("Invalid offset for LocalizationKey");
/*     */       }
/* 417 */       int pos = offset + 35 + localizationKeyOffset;
/* 418 */       if (pos >= buffer.writerIndex()) {
/* 419 */         return ValidationResult.error("Offset out of bounds for LocalizationKey");
/*     */       }
/* 421 */       int localizationKeyLen = VarInt.peek(buffer, pos);
/* 422 */       if (localizationKeyLen < 0) {
/* 423 */         return ValidationResult.error("Invalid string length for LocalizationKey");
/*     */       }
/* 425 */       if (localizationKeyLen > 4096000) {
/* 426 */         return ValidationResult.error("LocalizationKey exceeds max length 4096000");
/*     */       }
/* 428 */       pos += VarInt.length(buffer, pos);
/* 429 */       pos += localizationKeyLen;
/* 430 */       if (pos > buffer.writerIndex()) {
/* 431 */         return ValidationResult.error("Buffer overflow reading LocalizationKey");
/*     */       }
/*     */     } 
/* 434 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemQuality clone() {
/* 438 */     ItemQuality copy = new ItemQuality();
/* 439 */     copy.id = this.id;
/* 440 */     copy.itemTooltipTexture = this.itemTooltipTexture;
/* 441 */     copy.itemTooltipArrowTexture = this.itemTooltipArrowTexture;
/* 442 */     copy.slotTexture = this.slotTexture;
/* 443 */     copy.blockSlotTexture = this.blockSlotTexture;
/* 444 */     copy.specialSlotTexture = this.specialSlotTexture;
/* 445 */     copy.textColor = (this.textColor != null) ? this.textColor.clone() : null;
/* 446 */     copy.localizationKey = this.localizationKey;
/* 447 */     copy.visibleQualityLabel = this.visibleQualityLabel;
/* 448 */     copy.renderSpecialSlot = this.renderSpecialSlot;
/* 449 */     copy.hideFromSearch = this.hideFromSearch;
/* 450 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemQuality other;
/* 456 */     if (this == obj) return true; 
/* 457 */     if (obj instanceof ItemQuality) { other = (ItemQuality)obj; } else { return false; }
/* 458 */      return (Objects.equals(this.id, other.id) && Objects.equals(this.itemTooltipTexture, other.itemTooltipTexture) && Objects.equals(this.itemTooltipArrowTexture, other.itemTooltipArrowTexture) && Objects.equals(this.slotTexture, other.slotTexture) && Objects.equals(this.blockSlotTexture, other.blockSlotTexture) && Objects.equals(this.specialSlotTexture, other.specialSlotTexture) && Objects.equals(this.textColor, other.textColor) && Objects.equals(this.localizationKey, other.localizationKey) && this.visibleQualityLabel == other.visibleQualityLabel && this.renderSpecialSlot == other.renderSpecialSlot && this.hideFromSearch == other.hideFromSearch);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 463 */     return Objects.hash(new Object[] { this.id, this.itemTooltipTexture, this.itemTooltipArrowTexture, this.slotTexture, this.blockSlotTexture, this.specialSlotTexture, this.textColor, this.localizationKey, Boolean.valueOf(this.visibleQualityLabel), Boolean.valueOf(this.renderSpecialSlot), Boolean.valueOf(this.hideFromSearch) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemQuality.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */