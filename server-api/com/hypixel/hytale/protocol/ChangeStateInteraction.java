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
/*     */ public class ChangeStateInteraction extends SimpleBlockInteraction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 20;
/*     */   public static final int VARIABLE_FIELD_COUNT = 6;
/*     */   public static final int VARIABLE_BLOCK_START = 44;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public Map<String, String> stateChanges;
/*     */   
/*     */   public ChangeStateInteraction() {}
/*     */   
/*     */   public ChangeStateInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, boolean useLatestTarget, @Nullable Map<String, String> stateChanges) {
/*  26 */     this.waitForDataFrom = waitForDataFrom;
/*  27 */     this.effects = effects;
/*  28 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  29 */     this.runTime = runTime;
/*  30 */     this.cancelOnItemChange = cancelOnItemChange;
/*  31 */     this.settings = settings;
/*  32 */     this.rules = rules;
/*  33 */     this.tags = tags;
/*  34 */     this.camera = camera;
/*  35 */     this.next = next;
/*  36 */     this.failed = failed;
/*  37 */     this.useLatestTarget = useLatestTarget;
/*  38 */     this.stateChanges = stateChanges;
/*     */   }
/*     */   
/*     */   public ChangeStateInteraction(@Nonnull ChangeStateInteraction other) {
/*  42 */     this.waitForDataFrom = other.waitForDataFrom;
/*  43 */     this.effects = other.effects;
/*  44 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  45 */     this.runTime = other.runTime;
/*  46 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  47 */     this.settings = other.settings;
/*  48 */     this.rules = other.rules;
/*  49 */     this.tags = other.tags;
/*  50 */     this.camera = other.camera;
/*  51 */     this.next = other.next;
/*  52 */     this.failed = other.failed;
/*  53 */     this.useLatestTarget = other.useLatestTarget;
/*  54 */     this.stateChanges = other.stateChanges;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ChangeStateInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  59 */     ChangeStateInteraction obj = new ChangeStateInteraction();
/*  60 */     byte nullBits = buf.getByte(offset);
/*  61 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  62 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  63 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  64 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  65 */     obj.next = buf.getIntLE(offset + 11);
/*  66 */     obj.failed = buf.getIntLE(offset + 15);
/*  67 */     obj.useLatestTarget = (buf.getByte(offset + 19) != 0);
/*     */     
/*  69 */     if ((nullBits & 0x1) != 0) {
/*  70 */       int varPos0 = offset + 44 + buf.getIntLE(offset + 20);
/*  71 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  73 */     if ((nullBits & 0x2) != 0) {
/*  74 */       int varPos1 = offset + 44 + buf.getIntLE(offset + 24);
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
/*  90 */       int varPos2 = offset + 44 + buf.getIntLE(offset + 28);
/*  91 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/*  93 */     if ((nullBits & 0x8) != 0) {
/*  94 */       int varPos3 = offset + 44 + buf.getIntLE(offset + 32);
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
/* 107 */       int varPos4 = offset + 44 + buf.getIntLE(offset + 36);
/* 108 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/* 110 */     if ((nullBits & 0x20) != 0) {
/* 111 */       int varPos5 = offset + 44 + buf.getIntLE(offset + 40);
/* 112 */       int stateChangesCount = VarInt.peek(buf, varPos5);
/* 113 */       if (stateChangesCount < 0) throw ProtocolException.negativeLength("StateChanges", stateChangesCount); 
/* 114 */       if (stateChangesCount > 4096000) throw ProtocolException.dictionaryTooLarge("StateChanges", stateChangesCount, 4096000); 
/* 115 */       int varIntLen = VarInt.length(buf, varPos5);
/* 116 */       obj.stateChanges = new HashMap<>(stateChangesCount);
/* 117 */       int dictPos = varPos5 + varIntLen;
/* 118 */       for (int i = 0; i < stateChangesCount; i++) {
/* 119 */         int keyLen = VarInt.peek(buf, dictPos);
/* 120 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/* 121 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/* 122 */         int keyVarLen = VarInt.length(buf, dictPos);
/* 123 */         String key = PacketIO.readVarString(buf, dictPos);
/* 124 */         dictPos += keyVarLen + keyLen;
/* 125 */         int valLen = VarInt.peek(buf, dictPos);
/* 126 */         if (valLen < 0) throw ProtocolException.negativeLength("val", valLen); 
/* 127 */         if (valLen > 4096000) throw ProtocolException.stringTooLong("val", valLen, 4096000); 
/* 128 */         int valVarLen = VarInt.length(buf, dictPos);
/* 129 */         String val = PacketIO.readVarString(buf, dictPos);
/* 130 */         dictPos += valVarLen + valLen;
/* 131 */         if (obj.stateChanges.put(key, val) != null) {
/* 132 */           throw ProtocolException.duplicateKey("stateChanges", key);
/*     */         }
/*     */       } 
/*     */     } 
/* 136 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 140 */     byte nullBits = buf.getByte(offset);
/* 141 */     int maxEnd = 44;
/* 142 */     if ((nullBits & 0x1) != 0) {
/* 143 */       int fieldOffset0 = buf.getIntLE(offset + 20);
/* 144 */       int pos0 = offset + 44 + fieldOffset0;
/* 145 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 146 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 148 */     if ((nullBits & 0x2) != 0) {
/* 149 */       int fieldOffset1 = buf.getIntLE(offset + 24);
/* 150 */       int pos1 = offset + 44 + fieldOffset1;
/* 151 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 152 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 153 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 155 */     if ((nullBits & 0x4) != 0) {
/* 156 */       int fieldOffset2 = buf.getIntLE(offset + 28);
/* 157 */       int pos2 = offset + 44 + fieldOffset2;
/* 158 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 159 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 161 */     if ((nullBits & 0x8) != 0) {
/* 162 */       int fieldOffset3 = buf.getIntLE(offset + 32);
/* 163 */       int pos3 = offset + 44 + fieldOffset3;
/* 164 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 165 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 167 */     if ((nullBits & 0x10) != 0) {
/* 168 */       int fieldOffset4 = buf.getIntLE(offset + 36);
/* 169 */       int pos4 = offset + 44 + fieldOffset4;
/* 170 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 171 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 173 */     if ((nullBits & 0x20) != 0) {
/* 174 */       int fieldOffset5 = buf.getIntLE(offset + 40);
/* 175 */       int pos5 = offset + 44 + fieldOffset5;
/* 176 */       int dictLen = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5);
/* 177 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5) + sl; sl = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5) + sl; i++; }
/* 178 */        if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 180 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 186 */     int startPos = buf.writerIndex();
/* 187 */     byte nullBits = 0;
/* 188 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 189 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 190 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 191 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 192 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 193 */     if (this.stateChanges != null) nullBits = (byte)(nullBits | 0x20); 
/* 194 */     buf.writeByte(nullBits);
/*     */     
/* 196 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 197 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 198 */     buf.writeFloatLE(this.runTime);
/* 199 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 200 */     buf.writeIntLE(this.next);
/* 201 */     buf.writeIntLE(this.failed);
/* 202 */     buf.writeByte(this.useLatestTarget ? 1 : 0);
/*     */     
/* 204 */     int effectsOffsetSlot = buf.writerIndex();
/* 205 */     buf.writeIntLE(0);
/* 206 */     int settingsOffsetSlot = buf.writerIndex();
/* 207 */     buf.writeIntLE(0);
/* 208 */     int rulesOffsetSlot = buf.writerIndex();
/* 209 */     buf.writeIntLE(0);
/* 210 */     int tagsOffsetSlot = buf.writerIndex();
/* 211 */     buf.writeIntLE(0);
/* 212 */     int cameraOffsetSlot = buf.writerIndex();
/* 213 */     buf.writeIntLE(0);
/* 214 */     int stateChangesOffsetSlot = buf.writerIndex();
/* 215 */     buf.writeIntLE(0);
/*     */     
/* 217 */     int varBlockStart = buf.writerIndex();
/* 218 */     if (this.effects != null) {
/* 219 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 220 */       this.effects.serialize(buf);
/*     */     } else {
/* 222 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 224 */     if (this.settings != null)
/* 225 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 226 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 228 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 230 */     if (this.rules != null) {
/* 231 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 232 */       this.rules.serialize(buf);
/*     */     } else {
/* 234 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 236 */     if (this.tags != null) {
/* 237 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 238 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 240 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 242 */     if (this.camera != null) {
/* 243 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 244 */       this.camera.serialize(buf);
/*     */     } else {
/* 246 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 248 */     if (this.stateChanges != null)
/* 249 */     { buf.setIntLE(stateChangesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 250 */       if (this.stateChanges.size() > 4096000) throw ProtocolException.dictionaryTooLarge("StateChanges", this.stateChanges.size(), 4096000);  VarInt.write(buf, this.stateChanges.size()); for (Map.Entry<String, String> e : this.stateChanges.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); PacketIO.writeVarString(buf, e.getValue(), 4096000); }
/*     */        }
/* 252 */     else { buf.setIntLE(stateChangesOffsetSlot, -1); }
/*     */     
/* 254 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 260 */     int size = 44;
/* 261 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 262 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 263 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 264 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 265 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 266 */     if (this.stateChanges != null) {
/* 267 */       int stateChangesSize = 0;
/* 268 */       for (Map.Entry<String, String> kvp : this.stateChanges.entrySet()) stateChangesSize += PacketIO.stringSize(kvp.getKey()) + PacketIO.stringSize(kvp.getValue()); 
/* 269 */       size += VarInt.size(this.stateChanges.size()) + stateChangesSize;
/*     */     } 
/*     */     
/* 272 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 276 */     if (buffer.readableBytes() - offset < 44) {
/* 277 */       return ValidationResult.error("Buffer too small: expected at least 44 bytes");
/*     */     }
/*     */     
/* 280 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 283 */     if ((nullBits & 0x1) != 0) {
/* 284 */       int effectsOffset = buffer.getIntLE(offset + 20);
/* 285 */       if (effectsOffset < 0) {
/* 286 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 288 */       int pos = offset + 44 + effectsOffset;
/* 289 */       if (pos >= buffer.writerIndex()) {
/* 290 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 292 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 293 */       if (!effectsResult.isValid()) {
/* 294 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 296 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 299 */     if ((nullBits & 0x2) != 0) {
/* 300 */       int settingsOffset = buffer.getIntLE(offset + 24);
/* 301 */       if (settingsOffset < 0) {
/* 302 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 304 */       int pos = offset + 44 + settingsOffset;
/* 305 */       if (pos >= buffer.writerIndex()) {
/* 306 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 308 */       int settingsCount = VarInt.peek(buffer, pos);
/* 309 */       if (settingsCount < 0) {
/* 310 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 312 */       if (settingsCount > 4096000) {
/* 313 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 315 */       pos += VarInt.length(buffer, pos);
/* 316 */       for (int i = 0; i < settingsCount; i++) {
/* 317 */         pos++;
/*     */         
/* 319 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 324 */     if ((nullBits & 0x4) != 0) {
/* 325 */       int rulesOffset = buffer.getIntLE(offset + 28);
/* 326 */       if (rulesOffset < 0) {
/* 327 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 329 */       int pos = offset + 44 + rulesOffset;
/* 330 */       if (pos >= buffer.writerIndex()) {
/* 331 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 333 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 334 */       if (!rulesResult.isValid()) {
/* 335 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 337 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 340 */     if ((nullBits & 0x8) != 0) {
/* 341 */       int tagsOffset = buffer.getIntLE(offset + 32);
/* 342 */       if (tagsOffset < 0) {
/* 343 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 345 */       int pos = offset + 44 + tagsOffset;
/* 346 */       if (pos >= buffer.writerIndex()) {
/* 347 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 349 */       int tagsCount = VarInt.peek(buffer, pos);
/* 350 */       if (tagsCount < 0) {
/* 351 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 353 */       if (tagsCount > 4096000) {
/* 354 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 356 */       pos += VarInt.length(buffer, pos);
/* 357 */       pos += tagsCount * 4;
/* 358 */       if (pos > buffer.writerIndex()) {
/* 359 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 363 */     if ((nullBits & 0x10) != 0) {
/* 364 */       int cameraOffset = buffer.getIntLE(offset + 36);
/* 365 */       if (cameraOffset < 0) {
/* 366 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 368 */       int pos = offset + 44 + cameraOffset;
/* 369 */       if (pos >= buffer.writerIndex()) {
/* 370 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 372 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 373 */       if (!cameraResult.isValid()) {
/* 374 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 376 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 379 */     if ((nullBits & 0x20) != 0) {
/* 380 */       int stateChangesOffset = buffer.getIntLE(offset + 40);
/* 381 */       if (stateChangesOffset < 0) {
/* 382 */         return ValidationResult.error("Invalid offset for StateChanges");
/*     */       }
/* 384 */       int pos = offset + 44 + stateChangesOffset;
/* 385 */       if (pos >= buffer.writerIndex()) {
/* 386 */         return ValidationResult.error("Offset out of bounds for StateChanges");
/*     */       }
/* 388 */       int stateChangesCount = VarInt.peek(buffer, pos);
/* 389 */       if (stateChangesCount < 0) {
/* 390 */         return ValidationResult.error("Invalid dictionary count for StateChanges");
/*     */       }
/* 392 */       if (stateChangesCount > 4096000) {
/* 393 */         return ValidationResult.error("StateChanges exceeds max length 4096000");
/*     */       }
/* 395 */       pos += VarInt.length(buffer, pos);
/* 396 */       for (int i = 0; i < stateChangesCount; i++) {
/* 397 */         int keyLen = VarInt.peek(buffer, pos);
/* 398 */         if (keyLen < 0) {
/* 399 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 401 */         if (keyLen > 4096000) {
/* 402 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 404 */         pos += VarInt.length(buffer, pos);
/* 405 */         pos += keyLen;
/* 406 */         if (pos > buffer.writerIndex()) {
/* 407 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 409 */         int valueLen = VarInt.peek(buffer, pos);
/* 410 */         if (valueLen < 0) {
/* 411 */           return ValidationResult.error("Invalid string length for value");
/*     */         }
/* 413 */         if (valueLen > 4096000) {
/* 414 */           return ValidationResult.error("value exceeds max length 4096000");
/*     */         }
/* 416 */         pos += VarInt.length(buffer, pos);
/* 417 */         pos += valueLen;
/* 418 */         if (pos > buffer.writerIndex()) {
/* 419 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/* 423 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ChangeStateInteraction clone() {
/* 427 */     ChangeStateInteraction copy = new ChangeStateInteraction();
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
/* 443 */     copy.useLatestTarget = this.useLatestTarget;
/* 444 */     copy.stateChanges = (this.stateChanges != null) ? new HashMap<>(this.stateChanges) : null;
/* 445 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ChangeStateInteraction other;
/* 451 */     if (this == obj) return true; 
/* 452 */     if (obj instanceof ChangeStateInteraction) { other = (ChangeStateInteraction)obj; } else { return false; }
/* 453 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && this.useLatestTarget == other.useLatestTarget && Objects.equals(this.stateChanges, other.stateChanges));
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
/* 470 */     result = 31 * result + Boolean.hashCode(this.useLatestTarget);
/* 471 */     result = 31 * result + Objects.hashCode(this.stateChanges);
/* 472 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ChangeStateInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */