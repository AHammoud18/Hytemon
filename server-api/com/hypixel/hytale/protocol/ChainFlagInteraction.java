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
/*     */ public class ChainFlagInteraction
/*     */   extends SimpleInteraction
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 19;
/*     */   public static final int VARIABLE_FIELD_COUNT = 7;
/*     */   public static final int VARIABLE_BLOCK_START = 47;
/*     */   
/*     */   public ChainFlagInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, @Nullable String chainId, @Nullable String flag) {
/*  27 */     this.waitForDataFrom = waitForDataFrom;
/*  28 */     this.effects = effects;
/*  29 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  30 */     this.runTime = runTime;
/*  31 */     this.cancelOnItemChange = cancelOnItemChange;
/*  32 */     this.settings = settings;
/*  33 */     this.rules = rules;
/*  34 */     this.tags = tags;
/*  35 */     this.camera = camera;
/*  36 */     this.next = next;
/*  37 */     this.failed = failed;
/*  38 */     this.chainId = chainId;
/*  39 */     this.flag = flag;
/*     */   } public static final int MAX_SIZE = 1677721600; @Nullable
/*     */   public String chainId; @Nullable
/*     */   public String flag; public ChainFlagInteraction() {} public ChainFlagInteraction(@Nonnull ChainFlagInteraction other) {
/*  43 */     this.waitForDataFrom = other.waitForDataFrom;
/*  44 */     this.effects = other.effects;
/*  45 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  46 */     this.runTime = other.runTime;
/*  47 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  48 */     this.settings = other.settings;
/*  49 */     this.rules = other.rules;
/*  50 */     this.tags = other.tags;
/*  51 */     this.camera = other.camera;
/*  52 */     this.next = other.next;
/*  53 */     this.failed = other.failed;
/*  54 */     this.chainId = other.chainId;
/*  55 */     this.flag = other.flag;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ChainFlagInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  60 */     ChainFlagInteraction obj = new ChainFlagInteraction();
/*  61 */     byte nullBits = buf.getByte(offset);
/*  62 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  63 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  64 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  65 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  66 */     obj.next = buf.getIntLE(offset + 11);
/*  67 */     obj.failed = buf.getIntLE(offset + 15);
/*     */     
/*  69 */     if ((nullBits & 0x1) != 0) {
/*  70 */       int varPos0 = offset + 47 + buf.getIntLE(offset + 19);
/*  71 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  73 */     if ((nullBits & 0x2) != 0) {
/*  74 */       int varPos1 = offset + 47 + buf.getIntLE(offset + 23);
/*  75 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  76 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  77 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  78 */       int varIntLen = VarInt.length(buf, varPos1);
/*  79 */       obj.settings = new HashMap<>(settingsCount);
/*  80 */       int dictPos = varPos1 + varIntLen;
/*  81 */       for (int i = 0; i < settingsCount; i++) {
/*  82 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  83 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/*  84 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/*  85 */         if (obj.settings.put(key, val) != null)
/*  86 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/*  89 */     if ((nullBits & 0x4) != 0) {
/*  90 */       int varPos2 = offset + 47 + buf.getIntLE(offset + 27);
/*  91 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/*  93 */     if ((nullBits & 0x8) != 0) {
/*  94 */       int varPos3 = offset + 47 + buf.getIntLE(offset + 31);
/*  95 */       int tagsCount = VarInt.peek(buf, varPos3);
/*  96 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/*  97 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/*  98 */       int varIntLen = VarInt.length(buf, varPos3);
/*  99 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/* 100 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/* 101 */       obj.tags = new int[tagsCount];
/* 102 */       for (int i = 0; i < tagsCount; i++) {
/* 103 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 106 */     if ((nullBits & 0x10) != 0) {
/* 107 */       int varPos4 = offset + 47 + buf.getIntLE(offset + 35);
/* 108 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/* 110 */     if ((nullBits & 0x20) != 0) {
/* 111 */       int varPos5 = offset + 47 + buf.getIntLE(offset + 39);
/* 112 */       int chainIdLen = VarInt.peek(buf, varPos5);
/* 113 */       if (chainIdLen < 0) throw ProtocolException.negativeLength("ChainId", chainIdLen); 
/* 114 */       if (chainIdLen > 4096000) throw ProtocolException.stringTooLong("ChainId", chainIdLen, 4096000); 
/* 115 */       obj.chainId = PacketIO.readVarString(buf, varPos5, PacketIO.UTF8);
/*     */     } 
/* 117 */     if ((nullBits & 0x40) != 0) {
/* 118 */       int varPos6 = offset + 47 + buf.getIntLE(offset + 43);
/* 119 */       int flagLen = VarInt.peek(buf, varPos6);
/* 120 */       if (flagLen < 0) throw ProtocolException.negativeLength("Flag", flagLen); 
/* 121 */       if (flagLen > 4096000) throw ProtocolException.stringTooLong("Flag", flagLen, 4096000); 
/* 122 */       obj.flag = PacketIO.readVarString(buf, varPos6, PacketIO.UTF8);
/*     */     } 
/*     */     
/* 125 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 129 */     byte nullBits = buf.getByte(offset);
/* 130 */     int maxEnd = 47;
/* 131 */     if ((nullBits & 0x1) != 0) {
/* 132 */       int fieldOffset0 = buf.getIntLE(offset + 19);
/* 133 */       int pos0 = offset + 47 + fieldOffset0;
/* 134 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 135 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 137 */     if ((nullBits & 0x2) != 0) {
/* 138 */       int fieldOffset1 = buf.getIntLE(offset + 23);
/* 139 */       int pos1 = offset + 47 + fieldOffset1;
/* 140 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 141 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 142 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 144 */     if ((nullBits & 0x4) != 0) {
/* 145 */       int fieldOffset2 = buf.getIntLE(offset + 27);
/* 146 */       int pos2 = offset + 47 + fieldOffset2;
/* 147 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 148 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 150 */     if ((nullBits & 0x8) != 0) {
/* 151 */       int fieldOffset3 = buf.getIntLE(offset + 31);
/* 152 */       int pos3 = offset + 47 + fieldOffset3;
/* 153 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 154 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 156 */     if ((nullBits & 0x10) != 0) {
/* 157 */       int fieldOffset4 = buf.getIntLE(offset + 35);
/* 158 */       int pos4 = offset + 47 + fieldOffset4;
/* 159 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 160 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 162 */     if ((nullBits & 0x20) != 0) {
/* 163 */       int fieldOffset5 = buf.getIntLE(offset + 39);
/* 164 */       int pos5 = offset + 47 + fieldOffset5;
/* 165 */       int sl = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5) + sl;
/* 166 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 168 */     if ((nullBits & 0x40) != 0) {
/* 169 */       int fieldOffset6 = buf.getIntLE(offset + 43);
/* 170 */       int pos6 = offset + 47 + fieldOffset6;
/* 171 */       int sl = VarInt.peek(buf, pos6); pos6 += VarInt.length(buf, pos6) + sl;
/* 172 */       if (pos6 - offset > maxEnd) maxEnd = pos6 - offset; 
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
/* 187 */     if (this.chainId != null) nullBits = (byte)(nullBits | 0x20); 
/* 188 */     if (this.flag != null) nullBits = (byte)(nullBits | 0x40); 
/* 189 */     buf.writeByte(nullBits);
/*     */     
/* 191 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 192 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 193 */     buf.writeFloatLE(this.runTime);
/* 194 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 195 */     buf.writeIntLE(this.next);
/* 196 */     buf.writeIntLE(this.failed);
/*     */     
/* 198 */     int effectsOffsetSlot = buf.writerIndex();
/* 199 */     buf.writeIntLE(0);
/* 200 */     int settingsOffsetSlot = buf.writerIndex();
/* 201 */     buf.writeIntLE(0);
/* 202 */     int rulesOffsetSlot = buf.writerIndex();
/* 203 */     buf.writeIntLE(0);
/* 204 */     int tagsOffsetSlot = buf.writerIndex();
/* 205 */     buf.writeIntLE(0);
/* 206 */     int cameraOffsetSlot = buf.writerIndex();
/* 207 */     buf.writeIntLE(0);
/* 208 */     int chainIdOffsetSlot = buf.writerIndex();
/* 209 */     buf.writeIntLE(0);
/* 210 */     int flagOffsetSlot = buf.writerIndex();
/* 211 */     buf.writeIntLE(0);
/*     */     
/* 213 */     int varBlockStart = buf.writerIndex();
/* 214 */     if (this.effects != null) {
/* 215 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 216 */       this.effects.serialize(buf);
/*     */     } else {
/* 218 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 220 */     if (this.settings != null)
/* 221 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 222 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 224 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 226 */     if (this.rules != null) {
/* 227 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 228 */       this.rules.serialize(buf);
/*     */     } else {
/* 230 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 232 */     if (this.tags != null) {
/* 233 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 234 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 236 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 238 */     if (this.camera != null) {
/* 239 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 240 */       this.camera.serialize(buf);
/*     */     } else {
/* 242 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 244 */     if (this.chainId != null) {
/* 245 */       buf.setIntLE(chainIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 246 */       PacketIO.writeVarString(buf, this.chainId, 4096000);
/*     */     } else {
/* 248 */       buf.setIntLE(chainIdOffsetSlot, -1);
/*     */     } 
/* 250 */     if (this.flag != null) {
/* 251 */       buf.setIntLE(flagOffsetSlot, buf.writerIndex() - varBlockStart);
/* 252 */       PacketIO.writeVarString(buf, this.flag, 4096000);
/*     */     } else {
/* 254 */       buf.setIntLE(flagOffsetSlot, -1);
/*     */     } 
/* 256 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 262 */     int size = 47;
/* 263 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 264 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 265 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 266 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 267 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 268 */     if (this.chainId != null) size += PacketIO.stringSize(this.chainId); 
/* 269 */     if (this.flag != null) size += PacketIO.stringSize(this.flag);
/*     */     
/* 271 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 275 */     if (buffer.readableBytes() - offset < 47) {
/* 276 */       return ValidationResult.error("Buffer too small: expected at least 47 bytes");
/*     */     }
/*     */     
/* 279 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 282 */     if ((nullBits & 0x1) != 0) {
/* 283 */       int effectsOffset = buffer.getIntLE(offset + 19);
/* 284 */       if (effectsOffset < 0) {
/* 285 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 287 */       int pos = offset + 47 + effectsOffset;
/* 288 */       if (pos >= buffer.writerIndex()) {
/* 289 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 291 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 292 */       if (!effectsResult.isValid()) {
/* 293 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 295 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 298 */     if ((nullBits & 0x2) != 0) {
/* 299 */       int settingsOffset = buffer.getIntLE(offset + 23);
/* 300 */       if (settingsOffset < 0) {
/* 301 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 303 */       int pos = offset + 47 + settingsOffset;
/* 304 */       if (pos >= buffer.writerIndex()) {
/* 305 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 307 */       int settingsCount = VarInt.peek(buffer, pos);
/* 308 */       if (settingsCount < 0) {
/* 309 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 311 */       if (settingsCount > 4096000) {
/* 312 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 314 */       pos += VarInt.length(buffer, pos);
/* 315 */       for (int i = 0; i < settingsCount; i++) {
/* 316 */         pos++;
/*     */         
/* 318 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 323 */     if ((nullBits & 0x4) != 0) {
/* 324 */       int rulesOffset = buffer.getIntLE(offset + 27);
/* 325 */       if (rulesOffset < 0) {
/* 326 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 328 */       int pos = offset + 47 + rulesOffset;
/* 329 */       if (pos >= buffer.writerIndex()) {
/* 330 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 332 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 333 */       if (!rulesResult.isValid()) {
/* 334 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 336 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 339 */     if ((nullBits & 0x8) != 0) {
/* 340 */       int tagsOffset = buffer.getIntLE(offset + 31);
/* 341 */       if (tagsOffset < 0) {
/* 342 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 344 */       int pos = offset + 47 + tagsOffset;
/* 345 */       if (pos >= buffer.writerIndex()) {
/* 346 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 348 */       int tagsCount = VarInt.peek(buffer, pos);
/* 349 */       if (tagsCount < 0) {
/* 350 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 352 */       if (tagsCount > 4096000) {
/* 353 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 355 */       pos += VarInt.length(buffer, pos);
/* 356 */       pos += tagsCount * 4;
/* 357 */       if (pos > buffer.writerIndex()) {
/* 358 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 362 */     if ((nullBits & 0x10) != 0) {
/* 363 */       int cameraOffset = buffer.getIntLE(offset + 35);
/* 364 */       if (cameraOffset < 0) {
/* 365 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 367 */       int pos = offset + 47 + cameraOffset;
/* 368 */       if (pos >= buffer.writerIndex()) {
/* 369 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 371 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 372 */       if (!cameraResult.isValid()) {
/* 373 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 375 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 378 */     if ((nullBits & 0x20) != 0) {
/* 379 */       int chainIdOffset = buffer.getIntLE(offset + 39);
/* 380 */       if (chainIdOffset < 0) {
/* 381 */         return ValidationResult.error("Invalid offset for ChainId");
/*     */       }
/* 383 */       int pos = offset + 47 + chainIdOffset;
/* 384 */       if (pos >= buffer.writerIndex()) {
/* 385 */         return ValidationResult.error("Offset out of bounds for ChainId");
/*     */       }
/* 387 */       int chainIdLen = VarInt.peek(buffer, pos);
/* 388 */       if (chainIdLen < 0) {
/* 389 */         return ValidationResult.error("Invalid string length for ChainId");
/*     */       }
/* 391 */       if (chainIdLen > 4096000) {
/* 392 */         return ValidationResult.error("ChainId exceeds max length 4096000");
/*     */       }
/* 394 */       pos += VarInt.length(buffer, pos);
/* 395 */       pos += chainIdLen;
/* 396 */       if (pos > buffer.writerIndex()) {
/* 397 */         return ValidationResult.error("Buffer overflow reading ChainId");
/*     */       }
/*     */     } 
/*     */     
/* 401 */     if ((nullBits & 0x40) != 0) {
/* 402 */       int flagOffset = buffer.getIntLE(offset + 43);
/* 403 */       if (flagOffset < 0) {
/* 404 */         return ValidationResult.error("Invalid offset for Flag");
/*     */       }
/* 406 */       int pos = offset + 47 + flagOffset;
/* 407 */       if (pos >= buffer.writerIndex()) {
/* 408 */         return ValidationResult.error("Offset out of bounds for Flag");
/*     */       }
/* 410 */       int flagLen = VarInt.peek(buffer, pos);
/* 411 */       if (flagLen < 0) {
/* 412 */         return ValidationResult.error("Invalid string length for Flag");
/*     */       }
/* 414 */       if (flagLen > 4096000) {
/* 415 */         return ValidationResult.error("Flag exceeds max length 4096000");
/*     */       }
/* 417 */       pos += VarInt.length(buffer, pos);
/* 418 */       pos += flagLen;
/* 419 */       if (pos > buffer.writerIndex()) {
/* 420 */         return ValidationResult.error("Buffer overflow reading Flag");
/*     */       }
/*     */     } 
/* 423 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ChainFlagInteraction clone() {
/* 427 */     ChainFlagInteraction copy = new ChainFlagInteraction();
/* 428 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 429 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 430 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 431 */     copy.runTime = this.runTime;
/* 432 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 433 */     if (this.settings != null) {
/* 434 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 435 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 436 */       copy.settings = m;
/*     */     } 
/* 438 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 439 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 440 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 441 */     copy.next = this.next;
/* 442 */     copy.failed = this.failed;
/* 443 */     copy.chainId = this.chainId;
/* 444 */     copy.flag = this.flag;
/* 445 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ChainFlagInteraction other;
/* 451 */     if (this == obj) return true; 
/* 452 */     if (obj instanceof ChainFlagInteraction) { other = (ChainFlagInteraction)obj; } else { return false; }
/* 453 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && Objects.equals(this.chainId, other.chainId) && Objects.equals(this.flag, other.flag));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 458 */     int result = 1;
/* 459 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 460 */     result = 31 * result + Objects.hashCode(this.effects);
/* 461 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 462 */     result = 31 * result + Float.hashCode(this.runTime);
/* 463 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 464 */     result = 31 * result + Objects.hashCode(this.settings);
/* 465 */     result = 31 * result + Objects.hashCode(this.rules);
/* 466 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 467 */     result = 31 * result + Objects.hashCode(this.camera);
/* 468 */     result = 31 * result + Integer.hashCode(this.next);
/* 469 */     result = 31 * result + Integer.hashCode(this.failed);
/* 470 */     result = 31 * result + Objects.hashCode(this.chainId);
/* 471 */     result = 31 * result + Objects.hashCode(this.flag);
/* 472 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ChainFlagInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */