/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModifyInventoryInteraction
/*     */   extends SimpleInteraction
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 2;
/*     */   public static final int FIXED_BLOCK_SIZE = 33;
/*     */   public static final int VARIABLE_FIELD_COUNT = 8;
/*     */   public static final int VARIABLE_BLOCK_START = 65;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public GameMode requiredGameMode;
/*     */   
/*     */   public ModifyInventoryInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, @Nullable GameMode requiredGameMode, @Nullable ItemWithAllMetadata itemToRemove, int adjustHeldItemQuantity, @Nullable ItemWithAllMetadata itemToAdd, @Nullable String brokenItem, double adjustHeldItemDurability) {
/*  31 */     this.waitForDataFrom = waitForDataFrom;
/*  32 */     this.effects = effects;
/*  33 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  34 */     this.runTime = runTime;
/*  35 */     this.cancelOnItemChange = cancelOnItemChange;
/*  36 */     this.settings = settings;
/*  37 */     this.rules = rules;
/*  38 */     this.tags = tags;
/*  39 */     this.camera = camera;
/*  40 */     this.next = next;
/*  41 */     this.failed = failed;
/*  42 */     this.requiredGameMode = requiredGameMode;
/*  43 */     this.itemToRemove = itemToRemove;
/*  44 */     this.adjustHeldItemQuantity = adjustHeldItemQuantity;
/*  45 */     this.itemToAdd = itemToAdd;
/*  46 */     this.brokenItem = brokenItem;
/*  47 */     this.adjustHeldItemDurability = adjustHeldItemDurability; } @Nullable
/*     */   public ItemWithAllMetadata itemToRemove; public int adjustHeldItemQuantity; @Nullable
/*     */   public ItemWithAllMetadata itemToAdd; @Nullable
/*     */   public String brokenItem; public double adjustHeldItemDurability; public ModifyInventoryInteraction() {} public ModifyInventoryInteraction(@Nonnull ModifyInventoryInteraction other) {
/*  51 */     this.waitForDataFrom = other.waitForDataFrom;
/*  52 */     this.effects = other.effects;
/*  53 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  54 */     this.runTime = other.runTime;
/*  55 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  56 */     this.settings = other.settings;
/*  57 */     this.rules = other.rules;
/*  58 */     this.tags = other.tags;
/*  59 */     this.camera = other.camera;
/*  60 */     this.next = other.next;
/*  61 */     this.failed = other.failed;
/*  62 */     this.requiredGameMode = other.requiredGameMode;
/*  63 */     this.itemToRemove = other.itemToRemove;
/*  64 */     this.adjustHeldItemQuantity = other.adjustHeldItemQuantity;
/*  65 */     this.itemToAdd = other.itemToAdd;
/*  66 */     this.brokenItem = other.brokenItem;
/*  67 */     this.adjustHeldItemDurability = other.adjustHeldItemDurability;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ModifyInventoryInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  72 */     ModifyInventoryInteraction obj = new ModifyInventoryInteraction();
/*  73 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/*  74 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 2));
/*  75 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 3);
/*  76 */     obj.runTime = buf.getFloatLE(offset + 7);
/*  77 */     obj.cancelOnItemChange = (buf.getByte(offset + 11) != 0);
/*  78 */     obj.next = buf.getIntLE(offset + 12);
/*  79 */     obj.failed = buf.getIntLE(offset + 16);
/*  80 */     if ((nullBits[0] & 0x20) != 0) obj.requiredGameMode = GameMode.fromValue(buf.getByte(offset + 20)); 
/*  81 */     obj.adjustHeldItemQuantity = buf.getIntLE(offset + 21);
/*  82 */     obj.adjustHeldItemDurability = buf.getDoubleLE(offset + 25);
/*     */     
/*  84 */     if ((nullBits[0] & 0x1) != 0) {
/*  85 */       int varPos0 = offset + 65 + buf.getIntLE(offset + 33);
/*  86 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  88 */     if ((nullBits[0] & 0x2) != 0) {
/*  89 */       int varPos1 = offset + 65 + buf.getIntLE(offset + 37);
/*  90 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  91 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  92 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  93 */       int varIntLen = VarInt.length(buf, varPos1);
/*  94 */       obj.settings = new HashMap<>(settingsCount);
/*  95 */       int dictPos = varPos1 + varIntLen;
/*  96 */       for (int i = 0; i < settingsCount; i++) {
/*  97 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  98 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/*  99 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/* 100 */         if (obj.settings.put(key, val) != null)
/* 101 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/* 104 */     if ((nullBits[0] & 0x4) != 0) {
/* 105 */       int varPos2 = offset + 65 + buf.getIntLE(offset + 41);
/* 106 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/* 108 */     if ((nullBits[0] & 0x8) != 0) {
/* 109 */       int varPos3 = offset + 65 + buf.getIntLE(offset + 45);
/* 110 */       int tagsCount = VarInt.peek(buf, varPos3);
/* 111 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/* 112 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/* 113 */       int varIntLen = VarInt.length(buf, varPos3);
/* 114 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/* 115 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/* 116 */       obj.tags = new int[tagsCount];
/* 117 */       for (int i = 0; i < tagsCount; i++) {
/* 118 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 121 */     if ((nullBits[0] & 0x10) != 0) {
/* 122 */       int varPos4 = offset + 65 + buf.getIntLE(offset + 49);
/* 123 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/* 125 */     if ((nullBits[0] & 0x40) != 0) {
/* 126 */       int varPos5 = offset + 65 + buf.getIntLE(offset + 53);
/* 127 */       obj.itemToRemove = ItemWithAllMetadata.deserialize(buf, varPos5);
/*     */     } 
/* 129 */     if ((nullBits[0] & 0x80) != 0) {
/* 130 */       int varPos6 = offset + 65 + buf.getIntLE(offset + 57);
/* 131 */       obj.itemToAdd = ItemWithAllMetadata.deserialize(buf, varPos6);
/*     */     } 
/* 133 */     if ((nullBits[1] & 0x1) != 0) {
/* 134 */       int varPos7 = offset + 65 + buf.getIntLE(offset + 61);
/* 135 */       int brokenItemLen = VarInt.peek(buf, varPos7);
/* 136 */       if (brokenItemLen < 0) throw ProtocolException.negativeLength("BrokenItem", brokenItemLen); 
/* 137 */       if (brokenItemLen > 4096000) throw ProtocolException.stringTooLong("BrokenItem", brokenItemLen, 4096000); 
/* 138 */       obj.brokenItem = PacketIO.readVarString(buf, varPos7, PacketIO.UTF8);
/*     */     } 
/*     */     
/* 141 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 145 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/* 146 */     int maxEnd = 65;
/* 147 */     if ((nullBits[0] & 0x1) != 0) {
/* 148 */       int fieldOffset0 = buf.getIntLE(offset + 33);
/* 149 */       int pos0 = offset + 65 + fieldOffset0;
/* 150 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 151 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 153 */     if ((nullBits[0] & 0x2) != 0) {
/* 154 */       int fieldOffset1 = buf.getIntLE(offset + 37);
/* 155 */       int pos1 = offset + 65 + fieldOffset1;
/* 156 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 157 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 158 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 160 */     if ((nullBits[0] & 0x4) != 0) {
/* 161 */       int fieldOffset2 = buf.getIntLE(offset + 41);
/* 162 */       int pos2 = offset + 65 + fieldOffset2;
/* 163 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 164 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 166 */     if ((nullBits[0] & 0x8) != 0) {
/* 167 */       int fieldOffset3 = buf.getIntLE(offset + 45);
/* 168 */       int pos3 = offset + 65 + fieldOffset3;
/* 169 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 170 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 172 */     if ((nullBits[0] & 0x10) != 0) {
/* 173 */       int fieldOffset4 = buf.getIntLE(offset + 49);
/* 174 */       int pos4 = offset + 65 + fieldOffset4;
/* 175 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 176 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 178 */     if ((nullBits[0] & 0x40) != 0) {
/* 179 */       int fieldOffset5 = buf.getIntLE(offset + 53);
/* 180 */       int pos5 = offset + 65 + fieldOffset5;
/* 181 */       pos5 += ItemWithAllMetadata.computeBytesConsumed(buf, pos5);
/* 182 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 184 */     if ((nullBits[0] & 0x80) != 0) {
/* 185 */       int fieldOffset6 = buf.getIntLE(offset + 57);
/* 186 */       int pos6 = offset + 65 + fieldOffset6;
/* 187 */       pos6 += ItemWithAllMetadata.computeBytesConsumed(buf, pos6);
/* 188 */       if (pos6 - offset > maxEnd) maxEnd = pos6 - offset; 
/*     */     } 
/* 190 */     if ((nullBits[1] & 0x1) != 0) {
/* 191 */       int fieldOffset7 = buf.getIntLE(offset + 61);
/* 192 */       int pos7 = offset + 65 + fieldOffset7;
/* 193 */       int sl = VarInt.peek(buf, pos7); pos7 += VarInt.length(buf, pos7) + sl;
/* 194 */       if (pos7 - offset > maxEnd) maxEnd = pos7 - offset; 
/*     */     } 
/* 196 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 202 */     int startPos = buf.writerIndex();
/* 203 */     byte[] nullBits = new byte[2];
/* 204 */     if (this.effects != null) nullBits[0] = (byte)(nullBits[0] | 0x1); 
/* 205 */     if (this.settings != null) nullBits[0] = (byte)(nullBits[0] | 0x2); 
/* 206 */     if (this.rules != null) nullBits[0] = (byte)(nullBits[0] | 0x4); 
/* 207 */     if (this.tags != null) nullBits[0] = (byte)(nullBits[0] | 0x8); 
/* 208 */     if (this.camera != null) nullBits[0] = (byte)(nullBits[0] | 0x10); 
/* 209 */     if (this.requiredGameMode != null) nullBits[0] = (byte)(nullBits[0] | 0x20); 
/* 210 */     if (this.itemToRemove != null) nullBits[0] = (byte)(nullBits[0] | 0x40); 
/* 211 */     if (this.itemToAdd != null) nullBits[0] = (byte)(nullBits[0] | 0x80); 
/* 212 */     if (this.brokenItem != null) nullBits[1] = (byte)(nullBits[1] | 0x1); 
/* 213 */     buf.writeBytes(nullBits);
/*     */     
/* 215 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 216 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 217 */     buf.writeFloatLE(this.runTime);
/* 218 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 219 */     buf.writeIntLE(this.next);
/* 220 */     buf.writeIntLE(this.failed);
/* 221 */     if (this.requiredGameMode != null) { buf.writeByte(this.requiredGameMode.getValue()); } else { buf.writeZero(1); }
/* 222 */      buf.writeIntLE(this.adjustHeldItemQuantity);
/* 223 */     buf.writeDoubleLE(this.adjustHeldItemDurability);
/*     */     
/* 225 */     int effectsOffsetSlot = buf.writerIndex();
/* 226 */     buf.writeIntLE(0);
/* 227 */     int settingsOffsetSlot = buf.writerIndex();
/* 228 */     buf.writeIntLE(0);
/* 229 */     int rulesOffsetSlot = buf.writerIndex();
/* 230 */     buf.writeIntLE(0);
/* 231 */     int tagsOffsetSlot = buf.writerIndex();
/* 232 */     buf.writeIntLE(0);
/* 233 */     int cameraOffsetSlot = buf.writerIndex();
/* 234 */     buf.writeIntLE(0);
/* 235 */     int itemToRemoveOffsetSlot = buf.writerIndex();
/* 236 */     buf.writeIntLE(0);
/* 237 */     int itemToAddOffsetSlot = buf.writerIndex();
/* 238 */     buf.writeIntLE(0);
/* 239 */     int brokenItemOffsetSlot = buf.writerIndex();
/* 240 */     buf.writeIntLE(0);
/*     */     
/* 242 */     int varBlockStart = buf.writerIndex();
/* 243 */     if (this.effects != null) {
/* 244 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 245 */       this.effects.serialize(buf);
/*     */     } else {
/* 247 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 249 */     if (this.settings != null)
/* 250 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 251 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 253 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 255 */     if (this.rules != null) {
/* 256 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 257 */       this.rules.serialize(buf);
/*     */     } else {
/* 259 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 261 */     if (this.tags != null) {
/* 262 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 263 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 265 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 267 */     if (this.camera != null) {
/* 268 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 269 */       this.camera.serialize(buf);
/*     */     } else {
/* 271 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 273 */     if (this.itemToRemove != null) {
/* 274 */       buf.setIntLE(itemToRemoveOffsetSlot, buf.writerIndex() - varBlockStart);
/* 275 */       this.itemToRemove.serialize(buf);
/*     */     } else {
/* 277 */       buf.setIntLE(itemToRemoveOffsetSlot, -1);
/*     */     } 
/* 279 */     if (this.itemToAdd != null) {
/* 280 */       buf.setIntLE(itemToAddOffsetSlot, buf.writerIndex() - varBlockStart);
/* 281 */       this.itemToAdd.serialize(buf);
/*     */     } else {
/* 283 */       buf.setIntLE(itemToAddOffsetSlot, -1);
/*     */     } 
/* 285 */     if (this.brokenItem != null) {
/* 286 */       buf.setIntLE(brokenItemOffsetSlot, buf.writerIndex() - varBlockStart);
/* 287 */       PacketIO.writeVarString(buf, this.brokenItem, 4096000);
/*     */     } else {
/* 289 */       buf.setIntLE(brokenItemOffsetSlot, -1);
/*     */     } 
/* 291 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 297 */     int size = 65;
/* 298 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 299 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 300 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 301 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 302 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 303 */     if (this.itemToRemove != null) size += this.itemToRemove.computeSize(); 
/* 304 */     if (this.itemToAdd != null) size += this.itemToAdd.computeSize(); 
/* 305 */     if (this.brokenItem != null) size += PacketIO.stringSize(this.brokenItem);
/*     */     
/* 307 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 311 */     if (buffer.readableBytes() - offset < 65) {
/* 312 */       return ValidationResult.error("Buffer too small: expected at least 65 bytes");
/*     */     }
/*     */     
/* 315 */     byte[] nullBits = PacketIO.readBytes(buffer, offset, 2);
/*     */     
/* 317 */     if ((nullBits[0] & 0x1) != 0) {
/* 318 */       int effectsOffset = buffer.getIntLE(offset + 33);
/* 319 */       if (effectsOffset < 0) {
/* 320 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 322 */       int pos = offset + 65 + effectsOffset;
/* 323 */       if (pos >= buffer.writerIndex()) {
/* 324 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 326 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 327 */       if (!effectsResult.isValid()) {
/* 328 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 330 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 333 */     if ((nullBits[0] & 0x2) != 0) {
/* 334 */       int settingsOffset = buffer.getIntLE(offset + 37);
/* 335 */       if (settingsOffset < 0) {
/* 336 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 338 */       int pos = offset + 65 + settingsOffset;
/* 339 */       if (pos >= buffer.writerIndex()) {
/* 340 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 342 */       int settingsCount = VarInt.peek(buffer, pos);
/* 343 */       if (settingsCount < 0) {
/* 344 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 346 */       if (settingsCount > 4096000) {
/* 347 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 349 */       pos += VarInt.length(buffer, pos);
/* 350 */       for (int i = 0; i < settingsCount; i++) {
/* 351 */         pos++;
/*     */         
/* 353 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 358 */     if ((nullBits[0] & 0x4) != 0) {
/* 359 */       int rulesOffset = buffer.getIntLE(offset + 41);
/* 360 */       if (rulesOffset < 0) {
/* 361 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 363 */       int pos = offset + 65 + rulesOffset;
/* 364 */       if (pos >= buffer.writerIndex()) {
/* 365 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 367 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 368 */       if (!rulesResult.isValid()) {
/* 369 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 371 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 374 */     if ((nullBits[0] & 0x8) != 0) {
/* 375 */       int tagsOffset = buffer.getIntLE(offset + 45);
/* 376 */       if (tagsOffset < 0) {
/* 377 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 379 */       int pos = offset + 65 + tagsOffset;
/* 380 */       if (pos >= buffer.writerIndex()) {
/* 381 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 383 */       int tagsCount = VarInt.peek(buffer, pos);
/* 384 */       if (tagsCount < 0) {
/* 385 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 387 */       if (tagsCount > 4096000) {
/* 388 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 390 */       pos += VarInt.length(buffer, pos);
/* 391 */       pos += tagsCount * 4;
/* 392 */       if (pos > buffer.writerIndex()) {
/* 393 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 397 */     if ((nullBits[0] & 0x10) != 0) {
/* 398 */       int cameraOffset = buffer.getIntLE(offset + 49);
/* 399 */       if (cameraOffset < 0) {
/* 400 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 402 */       int pos = offset + 65 + cameraOffset;
/* 403 */       if (pos >= buffer.writerIndex()) {
/* 404 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 406 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 407 */       if (!cameraResult.isValid()) {
/* 408 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 410 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 413 */     if ((nullBits[0] & 0x40) != 0) {
/* 414 */       int itemToRemoveOffset = buffer.getIntLE(offset + 53);
/* 415 */       if (itemToRemoveOffset < 0) {
/* 416 */         return ValidationResult.error("Invalid offset for ItemToRemove");
/*     */       }
/* 418 */       int pos = offset + 65 + itemToRemoveOffset;
/* 419 */       if (pos >= buffer.writerIndex()) {
/* 420 */         return ValidationResult.error("Offset out of bounds for ItemToRemove");
/*     */       }
/* 422 */       ValidationResult itemToRemoveResult = ItemWithAllMetadata.validateStructure(buffer, pos);
/* 423 */       if (!itemToRemoveResult.isValid()) {
/* 424 */         return ValidationResult.error("Invalid ItemToRemove: " + itemToRemoveResult.error());
/*     */       }
/* 426 */       pos += ItemWithAllMetadata.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 429 */     if ((nullBits[0] & 0x80) != 0) {
/* 430 */       int itemToAddOffset = buffer.getIntLE(offset + 57);
/* 431 */       if (itemToAddOffset < 0) {
/* 432 */         return ValidationResult.error("Invalid offset for ItemToAdd");
/*     */       }
/* 434 */       int pos = offset + 65 + itemToAddOffset;
/* 435 */       if (pos >= buffer.writerIndex()) {
/* 436 */         return ValidationResult.error("Offset out of bounds for ItemToAdd");
/*     */       }
/* 438 */       ValidationResult itemToAddResult = ItemWithAllMetadata.validateStructure(buffer, pos);
/* 439 */       if (!itemToAddResult.isValid()) {
/* 440 */         return ValidationResult.error("Invalid ItemToAdd: " + itemToAddResult.error());
/*     */       }
/* 442 */       pos += ItemWithAllMetadata.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 445 */     if ((nullBits[1] & 0x1) != 0) {
/* 446 */       int brokenItemOffset = buffer.getIntLE(offset + 61);
/* 447 */       if (brokenItemOffset < 0) {
/* 448 */         return ValidationResult.error("Invalid offset for BrokenItem");
/*     */       }
/* 450 */       int pos = offset + 65 + brokenItemOffset;
/* 451 */       if (pos >= buffer.writerIndex()) {
/* 452 */         return ValidationResult.error("Offset out of bounds for BrokenItem");
/*     */       }
/* 454 */       int brokenItemLen = VarInt.peek(buffer, pos);
/* 455 */       if (brokenItemLen < 0) {
/* 456 */         return ValidationResult.error("Invalid string length for BrokenItem");
/*     */       }
/* 458 */       if (brokenItemLen > 4096000) {
/* 459 */         return ValidationResult.error("BrokenItem exceeds max length 4096000");
/*     */       }
/* 461 */       pos += VarInt.length(buffer, pos);
/* 462 */       pos += brokenItemLen;
/* 463 */       if (pos > buffer.writerIndex()) {
/* 464 */         return ValidationResult.error("Buffer overflow reading BrokenItem");
/*     */       }
/*     */     } 
/* 467 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ModifyInventoryInteraction clone() {
/* 471 */     ModifyInventoryInteraction copy = new ModifyInventoryInteraction();
/* 472 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 473 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 474 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 475 */     copy.runTime = this.runTime;
/* 476 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 477 */     if (this.settings != null) {
/* 478 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 479 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 480 */       copy.settings = m;
/*     */     } 
/* 482 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 483 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 484 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 485 */     copy.next = this.next;
/* 486 */     copy.failed = this.failed;
/* 487 */     copy.requiredGameMode = this.requiredGameMode;
/* 488 */     copy.itemToRemove = (this.itemToRemove != null) ? this.itemToRemove.clone() : null;
/* 489 */     copy.adjustHeldItemQuantity = this.adjustHeldItemQuantity;
/* 490 */     copy.itemToAdd = (this.itemToAdd != null) ? this.itemToAdd.clone() : null;
/* 491 */     copy.brokenItem = this.brokenItem;
/* 492 */     copy.adjustHeldItemDurability = this.adjustHeldItemDurability;
/* 493 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ModifyInventoryInteraction other;
/* 499 */     if (this == obj) return true; 
/* 500 */     if (obj instanceof ModifyInventoryInteraction) { other = (ModifyInventoryInteraction)obj; } else { return false; }
/* 501 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && Objects.equals(this.requiredGameMode, other.requiredGameMode) && Objects.equals(this.itemToRemove, other.itemToRemove) && this.adjustHeldItemQuantity == other.adjustHeldItemQuantity && Objects.equals(this.itemToAdd, other.itemToAdd) && Objects.equals(this.brokenItem, other.brokenItem) && this.adjustHeldItemDurability == other.adjustHeldItemDurability);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 506 */     int result = 1;
/* 507 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 508 */     result = 31 * result + Objects.hashCode(this.effects);
/* 509 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 510 */     result = 31 * result + Float.hashCode(this.runTime);
/* 511 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 512 */     result = 31 * result + Objects.hashCode(this.settings);
/* 513 */     result = 31 * result + Objects.hashCode(this.rules);
/* 514 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 515 */     result = 31 * result + Objects.hashCode(this.camera);
/* 516 */     result = 31 * result + Integer.hashCode(this.next);
/* 517 */     result = 31 * result + Integer.hashCode(this.failed);
/* 518 */     result = 31 * result + Objects.hashCode(this.requiredGameMode);
/* 519 */     result = 31 * result + Objects.hashCode(this.itemToRemove);
/* 520 */     result = 31 * result + Integer.hashCode(this.adjustHeldItemQuantity);
/* 521 */     result = 31 * result + Objects.hashCode(this.itemToAdd);
/* 522 */     result = 31 * result + Objects.hashCode(this.brokenItem);
/* 523 */     result = 31 * result + Double.hashCode(this.adjustHeldItemDurability);
/* 524 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ModifyInventoryInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */