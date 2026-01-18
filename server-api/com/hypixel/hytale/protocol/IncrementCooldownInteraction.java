/*     */ package com.hypixel.hytale.protocol;
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
/*     */ public class IncrementCooldownInteraction extends SimpleInteraction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 32;
/*     */   public static final int VARIABLE_FIELD_COUNT = 6;
/*     */   public static final int VARIABLE_BLOCK_START = 56;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public String cooldownId;
/*     */   public float cooldownIncrementTime;
/*     */   public int cooldownIncrementCharge;
/*     */   public float cooldownIncrementChargeTime;
/*     */   public boolean cooldownIncrementInterrupt;
/*     */   
/*     */   public IncrementCooldownInteraction() {}
/*     */   
/*     */   public IncrementCooldownInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, @Nullable String cooldownId, float cooldownIncrementTime, int cooldownIncrementCharge, float cooldownIncrementChargeTime, boolean cooldownIncrementInterrupt) {
/*  30 */     this.waitForDataFrom = waitForDataFrom;
/*  31 */     this.effects = effects;
/*  32 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  33 */     this.runTime = runTime;
/*  34 */     this.cancelOnItemChange = cancelOnItemChange;
/*  35 */     this.settings = settings;
/*  36 */     this.rules = rules;
/*  37 */     this.tags = tags;
/*  38 */     this.camera = camera;
/*  39 */     this.next = next;
/*  40 */     this.failed = failed;
/*  41 */     this.cooldownId = cooldownId;
/*  42 */     this.cooldownIncrementTime = cooldownIncrementTime;
/*  43 */     this.cooldownIncrementCharge = cooldownIncrementCharge;
/*  44 */     this.cooldownIncrementChargeTime = cooldownIncrementChargeTime;
/*  45 */     this.cooldownIncrementInterrupt = cooldownIncrementInterrupt;
/*     */   }
/*     */   
/*     */   public IncrementCooldownInteraction(@Nonnull IncrementCooldownInteraction other) {
/*  49 */     this.waitForDataFrom = other.waitForDataFrom;
/*  50 */     this.effects = other.effects;
/*  51 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  52 */     this.runTime = other.runTime;
/*  53 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  54 */     this.settings = other.settings;
/*  55 */     this.rules = other.rules;
/*  56 */     this.tags = other.tags;
/*  57 */     this.camera = other.camera;
/*  58 */     this.next = other.next;
/*  59 */     this.failed = other.failed;
/*  60 */     this.cooldownId = other.cooldownId;
/*  61 */     this.cooldownIncrementTime = other.cooldownIncrementTime;
/*  62 */     this.cooldownIncrementCharge = other.cooldownIncrementCharge;
/*  63 */     this.cooldownIncrementChargeTime = other.cooldownIncrementChargeTime;
/*  64 */     this.cooldownIncrementInterrupt = other.cooldownIncrementInterrupt;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static IncrementCooldownInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  69 */     IncrementCooldownInteraction obj = new IncrementCooldownInteraction();
/*  70 */     byte nullBits = buf.getByte(offset);
/*  71 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  72 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  73 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  74 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  75 */     obj.next = buf.getIntLE(offset + 11);
/*  76 */     obj.failed = buf.getIntLE(offset + 15);
/*  77 */     obj.cooldownIncrementTime = buf.getFloatLE(offset + 19);
/*  78 */     obj.cooldownIncrementCharge = buf.getIntLE(offset + 23);
/*  79 */     obj.cooldownIncrementChargeTime = buf.getFloatLE(offset + 27);
/*  80 */     obj.cooldownIncrementInterrupt = (buf.getByte(offset + 31) != 0);
/*     */     
/*  82 */     if ((nullBits & 0x1) != 0) {
/*  83 */       int varPos0 = offset + 56 + buf.getIntLE(offset + 32);
/*  84 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  86 */     if ((nullBits & 0x2) != 0) {
/*  87 */       int varPos1 = offset + 56 + buf.getIntLE(offset + 36);
/*  88 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  89 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  90 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  91 */       int varIntLen = VarInt.length(buf, varPos1);
/*  92 */       obj.settings = new HashMap<>(settingsCount);
/*  93 */       int dictPos = varPos1 + varIntLen;
/*  94 */       for (int i = 0; i < settingsCount; i++) {
/*  95 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  96 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/*  97 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/*  98 */         if (obj.settings.put(key, val) != null)
/*  99 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/* 102 */     if ((nullBits & 0x4) != 0) {
/* 103 */       int varPos2 = offset + 56 + buf.getIntLE(offset + 40);
/* 104 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/* 106 */     if ((nullBits & 0x8) != 0) {
/* 107 */       int varPos3 = offset + 56 + buf.getIntLE(offset + 44);
/* 108 */       int tagsCount = VarInt.peek(buf, varPos3);
/* 109 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/* 110 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/* 111 */       int varIntLen = VarInt.length(buf, varPos3);
/* 112 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/* 113 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/* 114 */       obj.tags = new int[tagsCount];
/* 115 */       for (int i = 0; i < tagsCount; i++) {
/* 116 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 119 */     if ((nullBits & 0x10) != 0) {
/* 120 */       int varPos4 = offset + 56 + buf.getIntLE(offset + 48);
/* 121 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/* 123 */     if ((nullBits & 0x20) != 0) {
/* 124 */       int varPos5 = offset + 56 + buf.getIntLE(offset + 52);
/* 125 */       int cooldownIdLen = VarInt.peek(buf, varPos5);
/* 126 */       if (cooldownIdLen < 0) throw ProtocolException.negativeLength("CooldownId", cooldownIdLen); 
/* 127 */       if (cooldownIdLen > 4096000) throw ProtocolException.stringTooLong("CooldownId", cooldownIdLen, 4096000); 
/* 128 */       obj.cooldownId = PacketIO.readVarString(buf, varPos5, PacketIO.UTF8);
/*     */     } 
/*     */     
/* 131 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 135 */     byte nullBits = buf.getByte(offset);
/* 136 */     int maxEnd = 56;
/* 137 */     if ((nullBits & 0x1) != 0) {
/* 138 */       int fieldOffset0 = buf.getIntLE(offset + 32);
/* 139 */       int pos0 = offset + 56 + fieldOffset0;
/* 140 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 141 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 143 */     if ((nullBits & 0x2) != 0) {
/* 144 */       int fieldOffset1 = buf.getIntLE(offset + 36);
/* 145 */       int pos1 = offset + 56 + fieldOffset1;
/* 146 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 147 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 148 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 150 */     if ((nullBits & 0x4) != 0) {
/* 151 */       int fieldOffset2 = buf.getIntLE(offset + 40);
/* 152 */       int pos2 = offset + 56 + fieldOffset2;
/* 153 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 154 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 156 */     if ((nullBits & 0x8) != 0) {
/* 157 */       int fieldOffset3 = buf.getIntLE(offset + 44);
/* 158 */       int pos3 = offset + 56 + fieldOffset3;
/* 159 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 160 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 162 */     if ((nullBits & 0x10) != 0) {
/* 163 */       int fieldOffset4 = buf.getIntLE(offset + 48);
/* 164 */       int pos4 = offset + 56 + fieldOffset4;
/* 165 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 166 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 168 */     if ((nullBits & 0x20) != 0) {
/* 169 */       int fieldOffset5 = buf.getIntLE(offset + 52);
/* 170 */       int pos5 = offset + 56 + fieldOffset5;
/* 171 */       int sl = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5) + sl;
/* 172 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 174 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 180 */     int startPos = buf.writerIndex();
/* 181 */     byte nullBits = 0;
/* 182 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 183 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 184 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 185 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 186 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 187 */     if (this.cooldownId != null) nullBits = (byte)(nullBits | 0x20); 
/* 188 */     buf.writeByte(nullBits);
/*     */     
/* 190 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 191 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 192 */     buf.writeFloatLE(this.runTime);
/* 193 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 194 */     buf.writeIntLE(this.next);
/* 195 */     buf.writeIntLE(this.failed);
/* 196 */     buf.writeFloatLE(this.cooldownIncrementTime);
/* 197 */     buf.writeIntLE(this.cooldownIncrementCharge);
/* 198 */     buf.writeFloatLE(this.cooldownIncrementChargeTime);
/* 199 */     buf.writeByte(this.cooldownIncrementInterrupt ? 1 : 0);
/*     */     
/* 201 */     int effectsOffsetSlot = buf.writerIndex();
/* 202 */     buf.writeIntLE(0);
/* 203 */     int settingsOffsetSlot = buf.writerIndex();
/* 204 */     buf.writeIntLE(0);
/* 205 */     int rulesOffsetSlot = buf.writerIndex();
/* 206 */     buf.writeIntLE(0);
/* 207 */     int tagsOffsetSlot = buf.writerIndex();
/* 208 */     buf.writeIntLE(0);
/* 209 */     int cameraOffsetSlot = buf.writerIndex();
/* 210 */     buf.writeIntLE(0);
/* 211 */     int cooldownIdOffsetSlot = buf.writerIndex();
/* 212 */     buf.writeIntLE(0);
/*     */     
/* 214 */     int varBlockStart = buf.writerIndex();
/* 215 */     if (this.effects != null) {
/* 216 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 217 */       this.effects.serialize(buf);
/*     */     } else {
/* 219 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 221 */     if (this.settings != null)
/* 222 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 223 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 225 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 227 */     if (this.rules != null) {
/* 228 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 229 */       this.rules.serialize(buf);
/*     */     } else {
/* 231 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 233 */     if (this.tags != null) {
/* 234 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 235 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 237 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 239 */     if (this.camera != null) {
/* 240 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 241 */       this.camera.serialize(buf);
/*     */     } else {
/* 243 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 245 */     if (this.cooldownId != null) {
/* 246 */       buf.setIntLE(cooldownIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 247 */       PacketIO.writeVarString(buf, this.cooldownId, 4096000);
/*     */     } else {
/* 249 */       buf.setIntLE(cooldownIdOffsetSlot, -1);
/*     */     } 
/* 251 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 257 */     int size = 56;
/* 258 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 259 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 260 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 261 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 262 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 263 */     if (this.cooldownId != null) size += PacketIO.stringSize(this.cooldownId);
/*     */     
/* 265 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 269 */     if (buffer.readableBytes() - offset < 56) {
/* 270 */       return ValidationResult.error("Buffer too small: expected at least 56 bytes");
/*     */     }
/*     */     
/* 273 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 276 */     if ((nullBits & 0x1) != 0) {
/* 277 */       int effectsOffset = buffer.getIntLE(offset + 32);
/* 278 */       if (effectsOffset < 0) {
/* 279 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 281 */       int pos = offset + 56 + effectsOffset;
/* 282 */       if (pos >= buffer.writerIndex()) {
/* 283 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 285 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 286 */       if (!effectsResult.isValid()) {
/* 287 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 289 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 292 */     if ((nullBits & 0x2) != 0) {
/* 293 */       int settingsOffset = buffer.getIntLE(offset + 36);
/* 294 */       if (settingsOffset < 0) {
/* 295 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 297 */       int pos = offset + 56 + settingsOffset;
/* 298 */       if (pos >= buffer.writerIndex()) {
/* 299 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 301 */       int settingsCount = VarInt.peek(buffer, pos);
/* 302 */       if (settingsCount < 0) {
/* 303 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 305 */       if (settingsCount > 4096000) {
/* 306 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 308 */       pos += VarInt.length(buffer, pos);
/* 309 */       for (int i = 0; i < settingsCount; i++) {
/* 310 */         pos++;
/*     */         
/* 312 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 317 */     if ((nullBits & 0x4) != 0) {
/* 318 */       int rulesOffset = buffer.getIntLE(offset + 40);
/* 319 */       if (rulesOffset < 0) {
/* 320 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 322 */       int pos = offset + 56 + rulesOffset;
/* 323 */       if (pos >= buffer.writerIndex()) {
/* 324 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 326 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 327 */       if (!rulesResult.isValid()) {
/* 328 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 330 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 333 */     if ((nullBits & 0x8) != 0) {
/* 334 */       int tagsOffset = buffer.getIntLE(offset + 44);
/* 335 */       if (tagsOffset < 0) {
/* 336 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 338 */       int pos = offset + 56 + tagsOffset;
/* 339 */       if (pos >= buffer.writerIndex()) {
/* 340 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 342 */       int tagsCount = VarInt.peek(buffer, pos);
/* 343 */       if (tagsCount < 0) {
/* 344 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 346 */       if (tagsCount > 4096000) {
/* 347 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 349 */       pos += VarInt.length(buffer, pos);
/* 350 */       pos += tagsCount * 4;
/* 351 */       if (pos > buffer.writerIndex()) {
/* 352 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 356 */     if ((nullBits & 0x10) != 0) {
/* 357 */       int cameraOffset = buffer.getIntLE(offset + 48);
/* 358 */       if (cameraOffset < 0) {
/* 359 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 361 */       int pos = offset + 56 + cameraOffset;
/* 362 */       if (pos >= buffer.writerIndex()) {
/* 363 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 365 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 366 */       if (!cameraResult.isValid()) {
/* 367 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 369 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 372 */     if ((nullBits & 0x20) != 0) {
/* 373 */       int cooldownIdOffset = buffer.getIntLE(offset + 52);
/* 374 */       if (cooldownIdOffset < 0) {
/* 375 */         return ValidationResult.error("Invalid offset for CooldownId");
/*     */       }
/* 377 */       int pos = offset + 56 + cooldownIdOffset;
/* 378 */       if (pos >= buffer.writerIndex()) {
/* 379 */         return ValidationResult.error("Offset out of bounds for CooldownId");
/*     */       }
/* 381 */       int cooldownIdLen = VarInt.peek(buffer, pos);
/* 382 */       if (cooldownIdLen < 0) {
/* 383 */         return ValidationResult.error("Invalid string length for CooldownId");
/*     */       }
/* 385 */       if (cooldownIdLen > 4096000) {
/* 386 */         return ValidationResult.error("CooldownId exceeds max length 4096000");
/*     */       }
/* 388 */       pos += VarInt.length(buffer, pos);
/* 389 */       pos += cooldownIdLen;
/* 390 */       if (pos > buffer.writerIndex()) {
/* 391 */         return ValidationResult.error("Buffer overflow reading CooldownId");
/*     */       }
/*     */     } 
/* 394 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public IncrementCooldownInteraction clone() {
/* 398 */     IncrementCooldownInteraction copy = new IncrementCooldownInteraction();
/* 399 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 400 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 401 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 402 */     copy.runTime = this.runTime;
/* 403 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 404 */     if (this.settings != null) {
/* 405 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 406 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 407 */       copy.settings = m;
/*     */     } 
/* 409 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 410 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 411 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 412 */     copy.next = this.next;
/* 413 */     copy.failed = this.failed;
/* 414 */     copy.cooldownId = this.cooldownId;
/* 415 */     copy.cooldownIncrementTime = this.cooldownIncrementTime;
/* 416 */     copy.cooldownIncrementCharge = this.cooldownIncrementCharge;
/* 417 */     copy.cooldownIncrementChargeTime = this.cooldownIncrementChargeTime;
/* 418 */     copy.cooldownIncrementInterrupt = this.cooldownIncrementInterrupt;
/* 419 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     IncrementCooldownInteraction other;
/* 425 */     if (this == obj) return true; 
/* 426 */     if (obj instanceof IncrementCooldownInteraction) { other = (IncrementCooldownInteraction)obj; } else { return false; }
/* 427 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && Objects.equals(this.cooldownId, other.cooldownId) && this.cooldownIncrementTime == other.cooldownIncrementTime && this.cooldownIncrementCharge == other.cooldownIncrementCharge && this.cooldownIncrementChargeTime == other.cooldownIncrementChargeTime && this.cooldownIncrementInterrupt == other.cooldownIncrementInterrupt);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 432 */     int result = 1;
/* 433 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 434 */     result = 31 * result + Objects.hashCode(this.effects);
/* 435 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 436 */     result = 31 * result + Float.hashCode(this.runTime);
/* 437 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 438 */     result = 31 * result + Objects.hashCode(this.settings);
/* 439 */     result = 31 * result + Objects.hashCode(this.rules);
/* 440 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 441 */     result = 31 * result + Objects.hashCode(this.camera);
/* 442 */     result = 31 * result + Integer.hashCode(this.next);
/* 443 */     result = 31 * result + Integer.hashCode(this.failed);
/* 444 */     result = 31 * result + Objects.hashCode(this.cooldownId);
/* 445 */     result = 31 * result + Float.hashCode(this.cooldownIncrementTime);
/* 446 */     result = 31 * result + Integer.hashCode(this.cooldownIncrementCharge);
/* 447 */     result = 31 * result + Float.hashCode(this.cooldownIncrementChargeTime);
/* 448 */     result = 31 * result + Boolean.hashCode(this.cooldownIncrementInterrupt);
/* 449 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\IncrementCooldownInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */