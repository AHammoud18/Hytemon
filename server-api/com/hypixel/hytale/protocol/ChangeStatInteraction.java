/*     */ package com.hypixel.hytale.protocol;
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
/*     */ public class ChangeStatInteraction extends SimpleInteraction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 22;
/*     */   public static final int VARIABLE_FIELD_COUNT = 6;
/*     */   public static final int VARIABLE_BLOCK_START = 46;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nonnull
/*  20 */   public InteractionTarget entityTarget = InteractionTarget.User; @Nonnull
/*  21 */   public ValueType valueType = ValueType.Percent;
/*     */   @Nonnull
/*  23 */   public ChangeStatBehaviour changeStatBehaviour = ChangeStatBehaviour.Add;
/*     */   
/*     */   @Nullable
/*     */   public Map<Integer, Float> statModifiers;
/*     */   
/*     */   public ChangeStatInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, @Nonnull InteractionTarget entityTarget, @Nonnull ValueType valueType, @Nullable Map<Integer, Float> statModifiers, @Nonnull ChangeStatBehaviour changeStatBehaviour) {
/*  29 */     this.waitForDataFrom = waitForDataFrom;
/*  30 */     this.effects = effects;
/*  31 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  32 */     this.runTime = runTime;
/*  33 */     this.cancelOnItemChange = cancelOnItemChange;
/*  34 */     this.settings = settings;
/*  35 */     this.rules = rules;
/*  36 */     this.tags = tags;
/*  37 */     this.camera = camera;
/*  38 */     this.next = next;
/*  39 */     this.failed = failed;
/*  40 */     this.entityTarget = entityTarget;
/*  41 */     this.valueType = valueType;
/*  42 */     this.statModifiers = statModifiers;
/*  43 */     this.changeStatBehaviour = changeStatBehaviour;
/*     */   }
/*     */   
/*     */   public ChangeStatInteraction(@Nonnull ChangeStatInteraction other) {
/*  47 */     this.waitForDataFrom = other.waitForDataFrom;
/*  48 */     this.effects = other.effects;
/*  49 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  50 */     this.runTime = other.runTime;
/*  51 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  52 */     this.settings = other.settings;
/*  53 */     this.rules = other.rules;
/*  54 */     this.tags = other.tags;
/*  55 */     this.camera = other.camera;
/*  56 */     this.next = other.next;
/*  57 */     this.failed = other.failed;
/*  58 */     this.entityTarget = other.entityTarget;
/*  59 */     this.valueType = other.valueType;
/*  60 */     this.statModifiers = other.statModifiers;
/*  61 */     this.changeStatBehaviour = other.changeStatBehaviour;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ChangeStatInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  66 */     ChangeStatInteraction obj = new ChangeStatInteraction();
/*  67 */     byte nullBits = buf.getByte(offset);
/*  68 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  69 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  70 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  71 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  72 */     obj.next = buf.getIntLE(offset + 11);
/*  73 */     obj.failed = buf.getIntLE(offset + 15);
/*  74 */     obj.entityTarget = InteractionTarget.fromValue(buf.getByte(offset + 19));
/*  75 */     obj.valueType = ValueType.fromValue(buf.getByte(offset + 20));
/*  76 */     obj.changeStatBehaviour = ChangeStatBehaviour.fromValue(buf.getByte(offset + 21));
/*     */     
/*  78 */     if ((nullBits & 0x1) != 0) {
/*  79 */       int varPos0 = offset + 46 + buf.getIntLE(offset + 22);
/*  80 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  82 */     if ((nullBits & 0x2) != 0) {
/*  83 */       int varPos1 = offset + 46 + buf.getIntLE(offset + 26);
/*  84 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  85 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  86 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  87 */       int varIntLen = VarInt.length(buf, varPos1);
/*  88 */       obj.settings = new HashMap<>(settingsCount);
/*  89 */       int dictPos = varPos1 + varIntLen;
/*  90 */       for (int i = 0; i < settingsCount; i++) {
/*  91 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  92 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/*  93 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/*  94 */         if (obj.settings.put(key, val) != null)
/*  95 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/*  98 */     if ((nullBits & 0x4) != 0) {
/*  99 */       int varPos2 = offset + 46 + buf.getIntLE(offset + 30);
/* 100 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/* 102 */     if ((nullBits & 0x8) != 0) {
/* 103 */       int varPos3 = offset + 46 + buf.getIntLE(offset + 34);
/* 104 */       int tagsCount = VarInt.peek(buf, varPos3);
/* 105 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/* 106 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/* 107 */       int varIntLen = VarInt.length(buf, varPos3);
/* 108 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/* 109 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/* 110 */       obj.tags = new int[tagsCount];
/* 111 */       for (int i = 0; i < tagsCount; i++) {
/* 112 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 115 */     if ((nullBits & 0x10) != 0) {
/* 116 */       int varPos4 = offset + 46 + buf.getIntLE(offset + 38);
/* 117 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/* 119 */     if ((nullBits & 0x20) != 0) {
/* 120 */       int varPos5 = offset + 46 + buf.getIntLE(offset + 42);
/* 121 */       int statModifiersCount = VarInt.peek(buf, varPos5);
/* 122 */       if (statModifiersCount < 0) throw ProtocolException.negativeLength("StatModifiers", statModifiersCount); 
/* 123 */       if (statModifiersCount > 4096000) throw ProtocolException.dictionaryTooLarge("StatModifiers", statModifiersCount, 4096000); 
/* 124 */       int varIntLen = VarInt.length(buf, varPos5);
/* 125 */       obj.statModifiers = new HashMap<>(statModifiersCount);
/* 126 */       int dictPos = varPos5 + varIntLen;
/* 127 */       for (int i = 0; i < statModifiersCount; i++) {
/* 128 */         int key = buf.getIntLE(dictPos); dictPos += 4;
/* 129 */         float val = buf.getFloatLE(dictPos); dictPos += 4;
/* 130 */         if (obj.statModifiers.put(Integer.valueOf(key), Float.valueOf(val)) != null) {
/* 131 */           throw ProtocolException.duplicateKey("statModifiers", Integer.valueOf(key));
/*     */         }
/*     */       } 
/*     */     } 
/* 135 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 139 */     byte nullBits = buf.getByte(offset);
/* 140 */     int maxEnd = 46;
/* 141 */     if ((nullBits & 0x1) != 0) {
/* 142 */       int fieldOffset0 = buf.getIntLE(offset + 22);
/* 143 */       int pos0 = offset + 46 + fieldOffset0;
/* 144 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 145 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 147 */     if ((nullBits & 0x2) != 0) {
/* 148 */       int fieldOffset1 = buf.getIntLE(offset + 26);
/* 149 */       int pos1 = offset + 46 + fieldOffset1;
/* 150 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 151 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 152 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 154 */     if ((nullBits & 0x4) != 0) {
/* 155 */       int fieldOffset2 = buf.getIntLE(offset + 30);
/* 156 */       int pos2 = offset + 46 + fieldOffset2;
/* 157 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 158 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 160 */     if ((nullBits & 0x8) != 0) {
/* 161 */       int fieldOffset3 = buf.getIntLE(offset + 34);
/* 162 */       int pos3 = offset + 46 + fieldOffset3;
/* 163 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 164 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 166 */     if ((nullBits & 0x10) != 0) {
/* 167 */       int fieldOffset4 = buf.getIntLE(offset + 38);
/* 168 */       int pos4 = offset + 46 + fieldOffset4;
/* 169 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 170 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 172 */     if ((nullBits & 0x20) != 0) {
/* 173 */       int fieldOffset5 = buf.getIntLE(offset + 42);
/* 174 */       int pos5 = offset + 46 + fieldOffset5;
/* 175 */       int dictLen = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5);
/* 176 */       for (int i = 0; i < dictLen; ) { pos5 += 4; pos5 += 4; i++; }
/* 177 */        if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 179 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 185 */     int startPos = buf.writerIndex();
/* 186 */     byte nullBits = 0;
/* 187 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 188 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 189 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 190 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 191 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 192 */     if (this.statModifiers != null) nullBits = (byte)(nullBits | 0x20); 
/* 193 */     buf.writeByte(nullBits);
/*     */     
/* 195 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 196 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 197 */     buf.writeFloatLE(this.runTime);
/* 198 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 199 */     buf.writeIntLE(this.next);
/* 200 */     buf.writeIntLE(this.failed);
/* 201 */     buf.writeByte(this.entityTarget.getValue());
/* 202 */     buf.writeByte(this.valueType.getValue());
/* 203 */     buf.writeByte(this.changeStatBehaviour.getValue());
/*     */     
/* 205 */     int effectsOffsetSlot = buf.writerIndex();
/* 206 */     buf.writeIntLE(0);
/* 207 */     int settingsOffsetSlot = buf.writerIndex();
/* 208 */     buf.writeIntLE(0);
/* 209 */     int rulesOffsetSlot = buf.writerIndex();
/* 210 */     buf.writeIntLE(0);
/* 211 */     int tagsOffsetSlot = buf.writerIndex();
/* 212 */     buf.writeIntLE(0);
/* 213 */     int cameraOffsetSlot = buf.writerIndex();
/* 214 */     buf.writeIntLE(0);
/* 215 */     int statModifiersOffsetSlot = buf.writerIndex();
/* 216 */     buf.writeIntLE(0);
/*     */     
/* 218 */     int varBlockStart = buf.writerIndex();
/* 219 */     if (this.effects != null) {
/* 220 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 221 */       this.effects.serialize(buf);
/*     */     } else {
/* 223 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 225 */     if (this.settings != null)
/* 226 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 227 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 229 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 231 */     if (this.rules != null) {
/* 232 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 233 */       this.rules.serialize(buf);
/*     */     } else {
/* 235 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 237 */     if (this.tags != null) {
/* 238 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 239 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 241 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 243 */     if (this.camera != null) {
/* 244 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 245 */       this.camera.serialize(buf);
/*     */     } else {
/* 247 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 249 */     if (this.statModifiers != null)
/* 250 */     { buf.setIntLE(statModifiersOffsetSlot, buf.writerIndex() - varBlockStart);
/* 251 */       if (this.statModifiers.size() > 4096000) throw ProtocolException.dictionaryTooLarge("StatModifiers", this.statModifiers.size(), 4096000);  VarInt.write(buf, this.statModifiers.size()); for (Map.Entry<Integer, Float> e : this.statModifiers.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); buf.writeFloatLE(((Float)e.getValue()).floatValue()); }
/*     */        }
/* 253 */     else { buf.setIntLE(statModifiersOffsetSlot, -1); }
/*     */     
/* 255 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 261 */     int size = 46;
/* 262 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 263 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 264 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 265 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 266 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 267 */     if (this.statModifiers != null) size += VarInt.size(this.statModifiers.size()) + this.statModifiers.size() * 8;
/*     */     
/* 269 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 273 */     if (buffer.readableBytes() - offset < 46) {
/* 274 */       return ValidationResult.error("Buffer too small: expected at least 46 bytes");
/*     */     }
/*     */     
/* 277 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 280 */     if ((nullBits & 0x1) != 0) {
/* 281 */       int effectsOffset = buffer.getIntLE(offset + 22);
/* 282 */       if (effectsOffset < 0) {
/* 283 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 285 */       int pos = offset + 46 + effectsOffset;
/* 286 */       if (pos >= buffer.writerIndex()) {
/* 287 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 289 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 290 */       if (!effectsResult.isValid()) {
/* 291 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 293 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 296 */     if ((nullBits & 0x2) != 0) {
/* 297 */       int settingsOffset = buffer.getIntLE(offset + 26);
/* 298 */       if (settingsOffset < 0) {
/* 299 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 301 */       int pos = offset + 46 + settingsOffset;
/* 302 */       if (pos >= buffer.writerIndex()) {
/* 303 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 305 */       int settingsCount = VarInt.peek(buffer, pos);
/* 306 */       if (settingsCount < 0) {
/* 307 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 309 */       if (settingsCount > 4096000) {
/* 310 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 312 */       pos += VarInt.length(buffer, pos);
/* 313 */       for (int i = 0; i < settingsCount; i++) {
/* 314 */         pos++;
/*     */         
/* 316 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 321 */     if ((nullBits & 0x4) != 0) {
/* 322 */       int rulesOffset = buffer.getIntLE(offset + 30);
/* 323 */       if (rulesOffset < 0) {
/* 324 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 326 */       int pos = offset + 46 + rulesOffset;
/* 327 */       if (pos >= buffer.writerIndex()) {
/* 328 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 330 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 331 */       if (!rulesResult.isValid()) {
/* 332 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 334 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 337 */     if ((nullBits & 0x8) != 0) {
/* 338 */       int tagsOffset = buffer.getIntLE(offset + 34);
/* 339 */       if (tagsOffset < 0) {
/* 340 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 342 */       int pos = offset + 46 + tagsOffset;
/* 343 */       if (pos >= buffer.writerIndex()) {
/* 344 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 346 */       int tagsCount = VarInt.peek(buffer, pos);
/* 347 */       if (tagsCount < 0) {
/* 348 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 350 */       if (tagsCount > 4096000) {
/* 351 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 353 */       pos += VarInt.length(buffer, pos);
/* 354 */       pos += tagsCount * 4;
/* 355 */       if (pos > buffer.writerIndex()) {
/* 356 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 360 */     if ((nullBits & 0x10) != 0) {
/* 361 */       int cameraOffset = buffer.getIntLE(offset + 38);
/* 362 */       if (cameraOffset < 0) {
/* 363 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 365 */       int pos = offset + 46 + cameraOffset;
/* 366 */       if (pos >= buffer.writerIndex()) {
/* 367 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 369 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 370 */       if (!cameraResult.isValid()) {
/* 371 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 373 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 376 */     if ((nullBits & 0x20) != 0) {
/* 377 */       int statModifiersOffset = buffer.getIntLE(offset + 42);
/* 378 */       if (statModifiersOffset < 0) {
/* 379 */         return ValidationResult.error("Invalid offset for StatModifiers");
/*     */       }
/* 381 */       int pos = offset + 46 + statModifiersOffset;
/* 382 */       if (pos >= buffer.writerIndex()) {
/* 383 */         return ValidationResult.error("Offset out of bounds for StatModifiers");
/*     */       }
/* 385 */       int statModifiersCount = VarInt.peek(buffer, pos);
/* 386 */       if (statModifiersCount < 0) {
/* 387 */         return ValidationResult.error("Invalid dictionary count for StatModifiers");
/*     */       }
/* 389 */       if (statModifiersCount > 4096000) {
/* 390 */         return ValidationResult.error("StatModifiers exceeds max length 4096000");
/*     */       }
/* 392 */       pos += VarInt.length(buffer, pos);
/* 393 */       for (int i = 0; i < statModifiersCount; i++) {
/* 394 */         pos += 4;
/* 395 */         if (pos > buffer.writerIndex()) {
/* 396 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 398 */         pos += 4;
/* 399 */         if (pos > buffer.writerIndex()) {
/* 400 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/* 404 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ChangeStatInteraction clone() {
/* 408 */     ChangeStatInteraction copy = new ChangeStatInteraction();
/* 409 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 410 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 411 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 412 */     copy.runTime = this.runTime;
/* 413 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 414 */     if (this.settings != null) {
/* 415 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 416 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 417 */       copy.settings = m;
/*     */     } 
/* 419 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 420 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 421 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 422 */     copy.next = this.next;
/* 423 */     copy.failed = this.failed;
/* 424 */     copy.entityTarget = this.entityTarget;
/* 425 */     copy.valueType = this.valueType;
/* 426 */     copy.statModifiers = (this.statModifiers != null) ? new HashMap<>(this.statModifiers) : null;
/* 427 */     copy.changeStatBehaviour = this.changeStatBehaviour;
/* 428 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ChangeStatInteraction other;
/* 434 */     if (this == obj) return true; 
/* 435 */     if (obj instanceof ChangeStatInteraction) { other = (ChangeStatInteraction)obj; } else { return false; }
/* 436 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && Objects.equals(this.entityTarget, other.entityTarget) && Objects.equals(this.valueType, other.valueType) && Objects.equals(this.statModifiers, other.statModifiers) && Objects.equals(this.changeStatBehaviour, other.changeStatBehaviour));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 441 */     int result = 1;
/* 442 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 443 */     result = 31 * result + Objects.hashCode(this.effects);
/* 444 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 445 */     result = 31 * result + Float.hashCode(this.runTime);
/* 446 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 447 */     result = 31 * result + Objects.hashCode(this.settings);
/* 448 */     result = 31 * result + Objects.hashCode(this.rules);
/* 449 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 450 */     result = 31 * result + Objects.hashCode(this.camera);
/* 451 */     result = 31 * result + Integer.hashCode(this.next);
/* 452 */     result = 31 * result + Integer.hashCode(this.failed);
/* 453 */     result = 31 * result + Objects.hashCode(this.entityTarget);
/* 454 */     result = 31 * result + Objects.hashCode(this.valueType);
/* 455 */     result = 31 * result + Objects.hashCode(this.statModifiers);
/* 456 */     result = 31 * result + Objects.hashCode(this.changeStatBehaviour);
/* 457 */     return result;
/*     */   }
/*     */   
/*     */   public ChangeStatInteraction() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ChangeStatInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */