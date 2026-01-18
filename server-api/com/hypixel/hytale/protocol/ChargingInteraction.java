/*     */ package com.hypixel.hytale.protocol;
/*     */ 
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
/*     */ public class ChargingInteraction extends Interaction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 47;
/*     */   public static final int VARIABLE_FIELD_COUNT = 7;
/*     */   public static final int VARIABLE_BLOCK_START = 75;
/*     */   public static final int MAX_SIZE = 1677721600;
/*  20 */   public int failed = Integer.MIN_VALUE;
/*     */   public boolean allowIndefiniteHold;
/*     */   public boolean displayProgress;
/*     */   public boolean cancelOnOtherClick;
/*     */   public boolean failOnDamage;
/*     */   public float mouseSensitivityAdjustmentTarget;
/*     */   public float mouseSensitivityAdjustmentDuration;
/*     */   @Nullable
/*     */   public Map<Float, Integer> chargedNext;
/*     */   @Nullable
/*     */   public Map<InteractionType, Integer> forks;
/*     */   @Nullable
/*     */   public ChargingDelay chargingDelay;
/*     */   
/*     */   public ChargingInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int failed, boolean allowIndefiniteHold, boolean displayProgress, boolean cancelOnOtherClick, boolean failOnDamage, float mouseSensitivityAdjustmentTarget, float mouseSensitivityAdjustmentDuration, @Nullable Map<Float, Integer> chargedNext, @Nullable Map<InteractionType, Integer> forks, @Nullable ChargingDelay chargingDelay) {
/*  35 */     this.waitForDataFrom = waitForDataFrom;
/*  36 */     this.effects = effects;
/*  37 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  38 */     this.runTime = runTime;
/*  39 */     this.cancelOnItemChange = cancelOnItemChange;
/*  40 */     this.settings = settings;
/*  41 */     this.rules = rules;
/*  42 */     this.tags = tags;
/*  43 */     this.camera = camera;
/*  44 */     this.failed = failed;
/*  45 */     this.allowIndefiniteHold = allowIndefiniteHold;
/*  46 */     this.displayProgress = displayProgress;
/*  47 */     this.cancelOnOtherClick = cancelOnOtherClick;
/*  48 */     this.failOnDamage = failOnDamage;
/*  49 */     this.mouseSensitivityAdjustmentTarget = mouseSensitivityAdjustmentTarget;
/*  50 */     this.mouseSensitivityAdjustmentDuration = mouseSensitivityAdjustmentDuration;
/*  51 */     this.chargedNext = chargedNext;
/*  52 */     this.forks = forks;
/*  53 */     this.chargingDelay = chargingDelay;
/*     */   }
/*     */   
/*     */   public ChargingInteraction(@Nonnull ChargingInteraction other) {
/*  57 */     this.waitForDataFrom = other.waitForDataFrom;
/*  58 */     this.effects = other.effects;
/*  59 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  60 */     this.runTime = other.runTime;
/*  61 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  62 */     this.settings = other.settings;
/*  63 */     this.rules = other.rules;
/*  64 */     this.tags = other.tags;
/*  65 */     this.camera = other.camera;
/*  66 */     this.failed = other.failed;
/*  67 */     this.allowIndefiniteHold = other.allowIndefiniteHold;
/*  68 */     this.displayProgress = other.displayProgress;
/*  69 */     this.cancelOnOtherClick = other.cancelOnOtherClick;
/*  70 */     this.failOnDamage = other.failOnDamage;
/*  71 */     this.mouseSensitivityAdjustmentTarget = other.mouseSensitivityAdjustmentTarget;
/*  72 */     this.mouseSensitivityAdjustmentDuration = other.mouseSensitivityAdjustmentDuration;
/*  73 */     this.chargedNext = other.chargedNext;
/*  74 */     this.forks = other.forks;
/*  75 */     this.chargingDelay = other.chargingDelay;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ChargingInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  80 */     ChargingInteraction obj = new ChargingInteraction();
/*  81 */     byte nullBits = buf.getByte(offset);
/*  82 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  83 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  84 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  85 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  86 */     obj.failed = buf.getIntLE(offset + 11);
/*  87 */     obj.allowIndefiniteHold = (buf.getByte(offset + 15) != 0);
/*  88 */     obj.displayProgress = (buf.getByte(offset + 16) != 0);
/*  89 */     obj.cancelOnOtherClick = (buf.getByte(offset + 17) != 0);
/*  90 */     obj.failOnDamage = (buf.getByte(offset + 18) != 0);
/*  91 */     obj.mouseSensitivityAdjustmentTarget = buf.getFloatLE(offset + 19);
/*  92 */     obj.mouseSensitivityAdjustmentDuration = buf.getFloatLE(offset + 23);
/*  93 */     if ((nullBits & 0x80) != 0) obj.chargingDelay = ChargingDelay.deserialize(buf, offset + 27);
/*     */     
/*  95 */     if ((nullBits & 0x1) != 0) {
/*  96 */       int varPos0 = offset + 75 + buf.getIntLE(offset + 47);
/*  97 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  99 */     if ((nullBits & 0x2) != 0) {
/* 100 */       int varPos1 = offset + 75 + buf.getIntLE(offset + 51);
/* 101 */       int settingsCount = VarInt.peek(buf, varPos1);
/* 102 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/* 103 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/* 104 */       int varIntLen = VarInt.length(buf, varPos1);
/* 105 */       obj.settings = new HashMap<>(settingsCount);
/* 106 */       int dictPos = varPos1 + varIntLen;
/* 107 */       for (int i = 0; i < settingsCount; i++) {
/* 108 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/* 109 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/* 110 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/* 111 */         if (obj.settings.put(key, val) != null)
/* 112 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/* 115 */     if ((nullBits & 0x4) != 0) {
/* 116 */       int varPos2 = offset + 75 + buf.getIntLE(offset + 55);
/* 117 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/* 119 */     if ((nullBits & 0x8) != 0) {
/* 120 */       int varPos3 = offset + 75 + buf.getIntLE(offset + 59);
/* 121 */       int tagsCount = VarInt.peek(buf, varPos3);
/* 122 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/* 123 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/* 124 */       int varIntLen = VarInt.length(buf, varPos3);
/* 125 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/* 126 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/* 127 */       obj.tags = new int[tagsCount];
/* 128 */       for (int i = 0; i < tagsCount; i++) {
/* 129 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 132 */     if ((nullBits & 0x10) != 0) {
/* 133 */       int varPos4 = offset + 75 + buf.getIntLE(offset + 63);
/* 134 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/* 136 */     if ((nullBits & 0x20) != 0) {
/* 137 */       int varPos5 = offset + 75 + buf.getIntLE(offset + 67);
/* 138 */       int chargedNextCount = VarInt.peek(buf, varPos5);
/* 139 */       if (chargedNextCount < 0) throw ProtocolException.negativeLength("ChargedNext", chargedNextCount); 
/* 140 */       if (chargedNextCount > 4096000) throw ProtocolException.dictionaryTooLarge("ChargedNext", chargedNextCount, 4096000); 
/* 141 */       int varIntLen = VarInt.length(buf, varPos5);
/* 142 */       obj.chargedNext = new HashMap<>(chargedNextCount);
/* 143 */       int dictPos = varPos5 + varIntLen;
/* 144 */       for (int i = 0; i < chargedNextCount; i++) {
/* 145 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/* 146 */         int val = buf.getIntLE(dictPos); dictPos += 4;
/* 147 */         if (obj.chargedNext.put(Float.valueOf(key), Integer.valueOf(val)) != null)
/* 148 */           throw ProtocolException.duplicateKey("chargedNext", Float.valueOf(key)); 
/*     */       } 
/*     */     } 
/* 151 */     if ((nullBits & 0x40) != 0) {
/* 152 */       int varPos6 = offset + 75 + buf.getIntLE(offset + 71);
/* 153 */       int forksCount = VarInt.peek(buf, varPos6);
/* 154 */       if (forksCount < 0) throw ProtocolException.negativeLength("Forks", forksCount); 
/* 155 */       if (forksCount > 4096000) throw ProtocolException.dictionaryTooLarge("Forks", forksCount, 4096000); 
/* 156 */       int varIntLen = VarInt.length(buf, varPos6);
/* 157 */       obj.forks = new HashMap<>(forksCount);
/* 158 */       int dictPos = varPos6 + varIntLen;
/* 159 */       for (int i = 0; i < forksCount; i++) {
/* 160 */         InteractionType key = InteractionType.fromValue(buf.getByte(dictPos)); dictPos++;
/* 161 */         int val = buf.getIntLE(dictPos); dictPos += 4;
/* 162 */         if (obj.forks.put(key, Integer.valueOf(val)) != null) {
/* 163 */           throw ProtocolException.duplicateKey("forks", key);
/*     */         }
/*     */       } 
/*     */     } 
/* 167 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 171 */     byte nullBits = buf.getByte(offset);
/* 172 */     int maxEnd = 75;
/* 173 */     if ((nullBits & 0x1) != 0) {
/* 174 */       int fieldOffset0 = buf.getIntLE(offset + 47);
/* 175 */       int pos0 = offset + 75 + fieldOffset0;
/* 176 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 177 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 179 */     if ((nullBits & 0x2) != 0) {
/* 180 */       int fieldOffset1 = buf.getIntLE(offset + 51);
/* 181 */       int pos1 = offset + 75 + fieldOffset1;
/* 182 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 183 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 184 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 186 */     if ((nullBits & 0x4) != 0) {
/* 187 */       int fieldOffset2 = buf.getIntLE(offset + 55);
/* 188 */       int pos2 = offset + 75 + fieldOffset2;
/* 189 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 190 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 192 */     if ((nullBits & 0x8) != 0) {
/* 193 */       int fieldOffset3 = buf.getIntLE(offset + 59);
/* 194 */       int pos3 = offset + 75 + fieldOffset3;
/* 195 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 196 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 198 */     if ((nullBits & 0x10) != 0) {
/* 199 */       int fieldOffset4 = buf.getIntLE(offset + 63);
/* 200 */       int pos4 = offset + 75 + fieldOffset4;
/* 201 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 202 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 204 */     if ((nullBits & 0x20) != 0) {
/* 205 */       int fieldOffset5 = buf.getIntLE(offset + 67);
/* 206 */       int pos5 = offset + 75 + fieldOffset5;
/* 207 */       int dictLen = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5);
/* 208 */       for (int i = 0; i < dictLen; ) { pos5 += 4; pos5 += 4; i++; }
/* 209 */        if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 211 */     if ((nullBits & 0x40) != 0) {
/* 212 */       int fieldOffset6 = buf.getIntLE(offset + 71);
/* 213 */       int pos6 = offset + 75 + fieldOffset6;
/* 214 */       int dictLen = VarInt.peek(buf, pos6); pos6 += VarInt.length(buf, pos6);
/* 215 */       for (int i = 0; i < dictLen; ) { pos6++; pos6 += 4; i++; }
/* 216 */        if (pos6 - offset > maxEnd) maxEnd = pos6 - offset; 
/*     */     } 
/* 218 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 224 */     int startPos = buf.writerIndex();
/* 225 */     byte nullBits = 0;
/* 226 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 227 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 228 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 229 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 230 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 231 */     if (this.chargedNext != null) nullBits = (byte)(nullBits | 0x20); 
/* 232 */     if (this.forks != null) nullBits = (byte)(nullBits | 0x40); 
/* 233 */     if (this.chargingDelay != null) nullBits = (byte)(nullBits | 0x80); 
/* 234 */     buf.writeByte(nullBits);
/*     */     
/* 236 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 237 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 238 */     buf.writeFloatLE(this.runTime);
/* 239 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 240 */     buf.writeIntLE(this.failed);
/* 241 */     buf.writeByte(this.allowIndefiniteHold ? 1 : 0);
/* 242 */     buf.writeByte(this.displayProgress ? 1 : 0);
/* 243 */     buf.writeByte(this.cancelOnOtherClick ? 1 : 0);
/* 244 */     buf.writeByte(this.failOnDamage ? 1 : 0);
/* 245 */     buf.writeFloatLE(this.mouseSensitivityAdjustmentTarget);
/* 246 */     buf.writeFloatLE(this.mouseSensitivityAdjustmentDuration);
/* 247 */     if (this.chargingDelay != null) { this.chargingDelay.serialize(buf); } else { buf.writeZero(20); }
/*     */     
/* 249 */     int effectsOffsetSlot = buf.writerIndex();
/* 250 */     buf.writeIntLE(0);
/* 251 */     int settingsOffsetSlot = buf.writerIndex();
/* 252 */     buf.writeIntLE(0);
/* 253 */     int rulesOffsetSlot = buf.writerIndex();
/* 254 */     buf.writeIntLE(0);
/* 255 */     int tagsOffsetSlot = buf.writerIndex();
/* 256 */     buf.writeIntLE(0);
/* 257 */     int cameraOffsetSlot = buf.writerIndex();
/* 258 */     buf.writeIntLE(0);
/* 259 */     int chargedNextOffsetSlot = buf.writerIndex();
/* 260 */     buf.writeIntLE(0);
/* 261 */     int forksOffsetSlot = buf.writerIndex();
/* 262 */     buf.writeIntLE(0);
/*     */     
/* 264 */     int varBlockStart = buf.writerIndex();
/* 265 */     if (this.effects != null) {
/* 266 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 267 */       this.effects.serialize(buf);
/*     */     } else {
/* 269 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 271 */     if (this.settings != null)
/* 272 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 273 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 275 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 277 */     if (this.rules != null) {
/* 278 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 279 */       this.rules.serialize(buf);
/*     */     } else {
/* 281 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 283 */     if (this.tags != null) {
/* 284 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 285 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 287 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 289 */     if (this.camera != null) {
/* 290 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 291 */       this.camera.serialize(buf);
/*     */     } else {
/* 293 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 295 */     if (this.chargedNext != null)
/* 296 */     { buf.setIntLE(chargedNextOffsetSlot, buf.writerIndex() - varBlockStart);
/* 297 */       if (this.chargedNext.size() > 4096000) throw ProtocolException.dictionaryTooLarge("ChargedNext", this.chargedNext.size(), 4096000);  VarInt.write(buf, this.chargedNext.size()); for (Map.Entry<Float, Integer> e : this.chargedNext.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*     */        }
/* 299 */     else { buf.setIntLE(chargedNextOffsetSlot, -1); }
/*     */     
/* 301 */     if (this.forks != null)
/* 302 */     { buf.setIntLE(forksOffsetSlot, buf.writerIndex() - varBlockStart);
/* 303 */       if (this.forks.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Forks", this.forks.size(), 4096000);  VarInt.write(buf, this.forks.size()); for (Map.Entry<InteractionType, Integer> e : this.forks.entrySet()) { buf.writeByte(((InteractionType)e.getKey()).getValue()); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*     */        }
/* 305 */     else { buf.setIntLE(forksOffsetSlot, -1); }
/*     */     
/* 307 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 313 */     int size = 75;
/* 314 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 315 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 316 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 317 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 318 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 319 */     if (this.chargedNext != null) size += VarInt.size(this.chargedNext.size()) + this.chargedNext.size() * 8; 
/* 320 */     if (this.forks != null) size += VarInt.size(this.forks.size()) + this.forks.size() * 5;
/*     */     
/* 322 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 326 */     if (buffer.readableBytes() - offset < 75) {
/* 327 */       return ValidationResult.error("Buffer too small: expected at least 75 bytes");
/*     */     }
/*     */     
/* 330 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 333 */     if ((nullBits & 0x1) != 0) {
/* 334 */       int effectsOffset = buffer.getIntLE(offset + 47);
/* 335 */       if (effectsOffset < 0) {
/* 336 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 338 */       int pos = offset + 75 + effectsOffset;
/* 339 */       if (pos >= buffer.writerIndex()) {
/* 340 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 342 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 343 */       if (!effectsResult.isValid()) {
/* 344 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 346 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 349 */     if ((nullBits & 0x2) != 0) {
/* 350 */       int settingsOffset = buffer.getIntLE(offset + 51);
/* 351 */       if (settingsOffset < 0) {
/* 352 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 354 */       int pos = offset + 75 + settingsOffset;
/* 355 */       if (pos >= buffer.writerIndex()) {
/* 356 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 358 */       int settingsCount = VarInt.peek(buffer, pos);
/* 359 */       if (settingsCount < 0) {
/* 360 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 362 */       if (settingsCount > 4096000) {
/* 363 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 365 */       pos += VarInt.length(buffer, pos);
/* 366 */       for (int i = 0; i < settingsCount; i++) {
/* 367 */         pos++;
/*     */         
/* 369 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 374 */     if ((nullBits & 0x4) != 0) {
/* 375 */       int rulesOffset = buffer.getIntLE(offset + 55);
/* 376 */       if (rulesOffset < 0) {
/* 377 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 379 */       int pos = offset + 75 + rulesOffset;
/* 380 */       if (pos >= buffer.writerIndex()) {
/* 381 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 383 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 384 */       if (!rulesResult.isValid()) {
/* 385 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 387 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 390 */     if ((nullBits & 0x8) != 0) {
/* 391 */       int tagsOffset = buffer.getIntLE(offset + 59);
/* 392 */       if (tagsOffset < 0) {
/* 393 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 395 */       int pos = offset + 75 + tagsOffset;
/* 396 */       if (pos >= buffer.writerIndex()) {
/* 397 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 399 */       int tagsCount = VarInt.peek(buffer, pos);
/* 400 */       if (tagsCount < 0) {
/* 401 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 403 */       if (tagsCount > 4096000) {
/* 404 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 406 */       pos += VarInt.length(buffer, pos);
/* 407 */       pos += tagsCount * 4;
/* 408 */       if (pos > buffer.writerIndex()) {
/* 409 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 413 */     if ((nullBits & 0x10) != 0) {
/* 414 */       int cameraOffset = buffer.getIntLE(offset + 63);
/* 415 */       if (cameraOffset < 0) {
/* 416 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 418 */       int pos = offset + 75 + cameraOffset;
/* 419 */       if (pos >= buffer.writerIndex()) {
/* 420 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 422 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 423 */       if (!cameraResult.isValid()) {
/* 424 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 426 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 429 */     if ((nullBits & 0x20) != 0) {
/* 430 */       int chargedNextOffset = buffer.getIntLE(offset + 67);
/* 431 */       if (chargedNextOffset < 0) {
/* 432 */         return ValidationResult.error("Invalid offset for ChargedNext");
/*     */       }
/* 434 */       int pos = offset + 75 + chargedNextOffset;
/* 435 */       if (pos >= buffer.writerIndex()) {
/* 436 */         return ValidationResult.error("Offset out of bounds for ChargedNext");
/*     */       }
/* 438 */       int chargedNextCount = VarInt.peek(buffer, pos);
/* 439 */       if (chargedNextCount < 0) {
/* 440 */         return ValidationResult.error("Invalid dictionary count for ChargedNext");
/*     */       }
/* 442 */       if (chargedNextCount > 4096000) {
/* 443 */         return ValidationResult.error("ChargedNext exceeds max length 4096000");
/*     */       }
/* 445 */       pos += VarInt.length(buffer, pos);
/* 446 */       for (int i = 0; i < chargedNextCount; i++) {
/* 447 */         pos += 4;
/* 448 */         if (pos > buffer.writerIndex()) {
/* 449 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 451 */         pos += 4;
/* 452 */         if (pos > buffer.writerIndex()) {
/* 453 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 458 */     if ((nullBits & 0x40) != 0) {
/* 459 */       int forksOffset = buffer.getIntLE(offset + 71);
/* 460 */       if (forksOffset < 0) {
/* 461 */         return ValidationResult.error("Invalid offset for Forks");
/*     */       }
/* 463 */       int pos = offset + 75 + forksOffset;
/* 464 */       if (pos >= buffer.writerIndex()) {
/* 465 */         return ValidationResult.error("Offset out of bounds for Forks");
/*     */       }
/* 467 */       int forksCount = VarInt.peek(buffer, pos);
/* 468 */       if (forksCount < 0) {
/* 469 */         return ValidationResult.error("Invalid dictionary count for Forks");
/*     */       }
/* 471 */       if (forksCount > 4096000) {
/* 472 */         return ValidationResult.error("Forks exceeds max length 4096000");
/*     */       }
/* 474 */       pos += VarInt.length(buffer, pos);
/* 475 */       for (int i = 0; i < forksCount; i++) {
/* 476 */         pos++;
/*     */         
/* 478 */         pos += 4;
/* 479 */         if (pos > buffer.writerIndex()) {
/* 480 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/* 484 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ChargingInteraction clone() {
/* 488 */     ChargingInteraction copy = new ChargingInteraction();
/* 489 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 490 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 491 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 492 */     copy.runTime = this.runTime;
/* 493 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 494 */     if (this.settings != null) {
/* 495 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 496 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 497 */       copy.settings = m;
/*     */     } 
/* 499 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 500 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 501 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 502 */     copy.failed = this.failed;
/* 503 */     copy.allowIndefiniteHold = this.allowIndefiniteHold;
/* 504 */     copy.displayProgress = this.displayProgress;
/* 505 */     copy.cancelOnOtherClick = this.cancelOnOtherClick;
/* 506 */     copy.failOnDamage = this.failOnDamage;
/* 507 */     copy.mouseSensitivityAdjustmentTarget = this.mouseSensitivityAdjustmentTarget;
/* 508 */     copy.mouseSensitivityAdjustmentDuration = this.mouseSensitivityAdjustmentDuration;
/* 509 */     copy.chargedNext = (this.chargedNext != null) ? new HashMap<>(this.chargedNext) : null;
/* 510 */     copy.forks = (this.forks != null) ? new HashMap<>(this.forks) : null;
/* 511 */     copy.chargingDelay = (this.chargingDelay != null) ? this.chargingDelay.clone() : null;
/* 512 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ChargingInteraction other;
/* 518 */     if (this == obj) return true; 
/* 519 */     if (obj instanceof ChargingInteraction) { other = (ChargingInteraction)obj; } else { return false; }
/* 520 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.failed == other.failed && this.allowIndefiniteHold == other.allowIndefiniteHold && this.displayProgress == other.displayProgress && this.cancelOnOtherClick == other.cancelOnOtherClick && this.failOnDamage == other.failOnDamage && this.mouseSensitivityAdjustmentTarget == other.mouseSensitivityAdjustmentTarget && this.mouseSensitivityAdjustmentDuration == other.mouseSensitivityAdjustmentDuration && Objects.equals(this.chargedNext, other.chargedNext) && Objects.equals(this.forks, other.forks) && Objects.equals(this.chargingDelay, other.chargingDelay));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 525 */     int result = 1;
/* 526 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 527 */     result = 31 * result + Objects.hashCode(this.effects);
/* 528 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 529 */     result = 31 * result + Float.hashCode(this.runTime);
/* 530 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 531 */     result = 31 * result + Objects.hashCode(this.settings);
/* 532 */     result = 31 * result + Objects.hashCode(this.rules);
/* 533 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 534 */     result = 31 * result + Objects.hashCode(this.camera);
/* 535 */     result = 31 * result + Integer.hashCode(this.failed);
/* 536 */     result = 31 * result + Boolean.hashCode(this.allowIndefiniteHold);
/* 537 */     result = 31 * result + Boolean.hashCode(this.displayProgress);
/* 538 */     result = 31 * result + Boolean.hashCode(this.cancelOnOtherClick);
/* 539 */     result = 31 * result + Boolean.hashCode(this.failOnDamage);
/* 540 */     result = 31 * result + Float.hashCode(this.mouseSensitivityAdjustmentTarget);
/* 541 */     result = 31 * result + Float.hashCode(this.mouseSensitivityAdjustmentDuration);
/* 542 */     result = 31 * result + Objects.hashCode(this.chargedNext);
/* 543 */     result = 31 * result + Objects.hashCode(this.forks);
/* 544 */     result = 31 * result + Objects.hashCode(this.chargingDelay);
/* 545 */     return result;
/*     */   }
/*     */   
/*     */   public ChargingInteraction() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ChargingInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */