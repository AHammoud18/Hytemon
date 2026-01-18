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
/*     */ public class EffectConditionInteraction
/*     */   extends SimpleInteraction
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 21;
/*     */   public static final int VARIABLE_FIELD_COUNT = 6;
/*     */   @Nonnull
/*  21 */   public Match match = Match.All; public static final int VARIABLE_BLOCK_START = 45; public static final int MAX_SIZE = 1677721600; @Nullable public int[] entityEffects; @Nonnull
/*  22 */   public InteractionTarget entityTarget = InteractionTarget.User;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EffectConditionInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, @Nullable int[] entityEffects, @Nonnull Match match, @Nonnull InteractionTarget entityTarget) {
/*  28 */     this.waitForDataFrom = waitForDataFrom;
/*  29 */     this.effects = effects;
/*  30 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  31 */     this.runTime = runTime;
/*  32 */     this.cancelOnItemChange = cancelOnItemChange;
/*  33 */     this.settings = settings;
/*  34 */     this.rules = rules;
/*  35 */     this.tags = tags;
/*  36 */     this.camera = camera;
/*  37 */     this.next = next;
/*  38 */     this.failed = failed;
/*  39 */     this.entityEffects = entityEffects;
/*  40 */     this.match = match;
/*  41 */     this.entityTarget = entityTarget;
/*     */   }
/*     */   
/*     */   public EffectConditionInteraction(@Nonnull EffectConditionInteraction other) {
/*  45 */     this.waitForDataFrom = other.waitForDataFrom;
/*  46 */     this.effects = other.effects;
/*  47 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  48 */     this.runTime = other.runTime;
/*  49 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  50 */     this.settings = other.settings;
/*  51 */     this.rules = other.rules;
/*  52 */     this.tags = other.tags;
/*  53 */     this.camera = other.camera;
/*  54 */     this.next = other.next;
/*  55 */     this.failed = other.failed;
/*  56 */     this.entityEffects = other.entityEffects;
/*  57 */     this.match = other.match;
/*  58 */     this.entityTarget = other.entityTarget;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static EffectConditionInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  63 */     EffectConditionInteraction obj = new EffectConditionInteraction();
/*  64 */     byte nullBits = buf.getByte(offset);
/*  65 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  66 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  67 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  68 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  69 */     obj.next = buf.getIntLE(offset + 11);
/*  70 */     obj.failed = buf.getIntLE(offset + 15);
/*  71 */     obj.match = Match.fromValue(buf.getByte(offset + 19));
/*  72 */     obj.entityTarget = InteractionTarget.fromValue(buf.getByte(offset + 20));
/*     */     
/*  74 */     if ((nullBits & 0x1) != 0) {
/*  75 */       int varPos0 = offset + 45 + buf.getIntLE(offset + 21);
/*  76 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  78 */     if ((nullBits & 0x2) != 0) {
/*  79 */       int varPos1 = offset + 45 + buf.getIntLE(offset + 25);
/*  80 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  81 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  82 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  83 */       int varIntLen = VarInt.length(buf, varPos1);
/*  84 */       obj.settings = new HashMap<>(settingsCount);
/*  85 */       int dictPos = varPos1 + varIntLen;
/*  86 */       for (int i = 0; i < settingsCount; i++) {
/*  87 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  88 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/*  89 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/*  90 */         if (obj.settings.put(key, val) != null)
/*  91 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/*  94 */     if ((nullBits & 0x4) != 0) {
/*  95 */       int varPos2 = offset + 45 + buf.getIntLE(offset + 29);
/*  96 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/*  98 */     if ((nullBits & 0x8) != 0) {
/*  99 */       int varPos3 = offset + 45 + buf.getIntLE(offset + 33);
/* 100 */       int tagsCount = VarInt.peek(buf, varPos3);
/* 101 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/* 102 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/* 103 */       int varIntLen = VarInt.length(buf, varPos3);
/* 104 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/* 105 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/* 106 */       obj.tags = new int[tagsCount];
/* 107 */       for (int i = 0; i < tagsCount; i++) {
/* 108 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 111 */     if ((nullBits & 0x10) != 0) {
/* 112 */       int varPos4 = offset + 45 + buf.getIntLE(offset + 37);
/* 113 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/* 115 */     if ((nullBits & 0x20) != 0) {
/* 116 */       int varPos5 = offset + 45 + buf.getIntLE(offset + 41);
/* 117 */       int entityEffectsCount = VarInt.peek(buf, varPos5);
/* 118 */       if (entityEffectsCount < 0) throw ProtocolException.negativeLength("EntityEffects", entityEffectsCount); 
/* 119 */       if (entityEffectsCount > 4096000) throw ProtocolException.arrayTooLong("EntityEffects", entityEffectsCount, 4096000); 
/* 120 */       int varIntLen = VarInt.length(buf, varPos5);
/* 121 */       if ((varPos5 + varIntLen) + entityEffectsCount * 4L > buf.readableBytes())
/* 122 */         throw ProtocolException.bufferTooSmall("EntityEffects", varPos5 + varIntLen + entityEffectsCount * 4, buf.readableBytes()); 
/* 123 */       obj.entityEffects = new int[entityEffectsCount];
/* 124 */       for (int i = 0; i < entityEffectsCount; i++) {
/* 125 */         obj.entityEffects[i] = buf.getIntLE(varPos5 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/*     */     
/* 129 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 133 */     byte nullBits = buf.getByte(offset);
/* 134 */     int maxEnd = 45;
/* 135 */     if ((nullBits & 0x1) != 0) {
/* 136 */       int fieldOffset0 = buf.getIntLE(offset + 21);
/* 137 */       int pos0 = offset + 45 + fieldOffset0;
/* 138 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 139 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 141 */     if ((nullBits & 0x2) != 0) {
/* 142 */       int fieldOffset1 = buf.getIntLE(offset + 25);
/* 143 */       int pos1 = offset + 45 + fieldOffset1;
/* 144 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 145 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 146 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 148 */     if ((nullBits & 0x4) != 0) {
/* 149 */       int fieldOffset2 = buf.getIntLE(offset + 29);
/* 150 */       int pos2 = offset + 45 + fieldOffset2;
/* 151 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 152 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 154 */     if ((nullBits & 0x8) != 0) {
/* 155 */       int fieldOffset3 = buf.getIntLE(offset + 33);
/* 156 */       int pos3 = offset + 45 + fieldOffset3;
/* 157 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 158 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 160 */     if ((nullBits & 0x10) != 0) {
/* 161 */       int fieldOffset4 = buf.getIntLE(offset + 37);
/* 162 */       int pos4 = offset + 45 + fieldOffset4;
/* 163 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 164 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 166 */     if ((nullBits & 0x20) != 0) {
/* 167 */       int fieldOffset5 = buf.getIntLE(offset + 41);
/* 168 */       int pos5 = offset + 45 + fieldOffset5;
/* 169 */       int arrLen = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5) + arrLen * 4;
/* 170 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 172 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 178 */     int startPos = buf.writerIndex();
/* 179 */     byte nullBits = 0;
/* 180 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 181 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 182 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 183 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 184 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 185 */     if (this.entityEffects != null) nullBits = (byte)(nullBits | 0x20); 
/* 186 */     buf.writeByte(nullBits);
/*     */     
/* 188 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 189 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 190 */     buf.writeFloatLE(this.runTime);
/* 191 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 192 */     buf.writeIntLE(this.next);
/* 193 */     buf.writeIntLE(this.failed);
/* 194 */     buf.writeByte(this.match.getValue());
/* 195 */     buf.writeByte(this.entityTarget.getValue());
/*     */     
/* 197 */     int effectsOffsetSlot = buf.writerIndex();
/* 198 */     buf.writeIntLE(0);
/* 199 */     int settingsOffsetSlot = buf.writerIndex();
/* 200 */     buf.writeIntLE(0);
/* 201 */     int rulesOffsetSlot = buf.writerIndex();
/* 202 */     buf.writeIntLE(0);
/* 203 */     int tagsOffsetSlot = buf.writerIndex();
/* 204 */     buf.writeIntLE(0);
/* 205 */     int cameraOffsetSlot = buf.writerIndex();
/* 206 */     buf.writeIntLE(0);
/* 207 */     int entityEffectsOffsetSlot = buf.writerIndex();
/* 208 */     buf.writeIntLE(0);
/*     */     
/* 210 */     int varBlockStart = buf.writerIndex();
/* 211 */     if (this.effects != null) {
/* 212 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 213 */       this.effects.serialize(buf);
/*     */     } else {
/* 215 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 217 */     if (this.settings != null)
/* 218 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 219 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 221 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 223 */     if (this.rules != null) {
/* 224 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 225 */       this.rules.serialize(buf);
/*     */     } else {
/* 227 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 229 */     if (this.tags != null) {
/* 230 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 231 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 233 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 235 */     if (this.camera != null) {
/* 236 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 237 */       this.camera.serialize(buf);
/*     */     } else {
/* 239 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 241 */     if (this.entityEffects != null) {
/* 242 */       buf.setIntLE(entityEffectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 243 */       if (this.entityEffects.length > 4096000) throw ProtocolException.arrayTooLong("EntityEffects", this.entityEffects.length, 4096000);  VarInt.write(buf, this.entityEffects.length); for (int item : this.entityEffects) buf.writeIntLE(item); 
/*     */     } else {
/* 245 */       buf.setIntLE(entityEffectsOffsetSlot, -1);
/*     */     } 
/* 247 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 253 */     int size = 45;
/* 254 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 255 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 256 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 257 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 258 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 259 */     if (this.entityEffects != null) size += VarInt.size(this.entityEffects.length) + this.entityEffects.length * 4;
/*     */     
/* 261 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 265 */     if (buffer.readableBytes() - offset < 45) {
/* 266 */       return ValidationResult.error("Buffer too small: expected at least 45 bytes");
/*     */     }
/*     */     
/* 269 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 272 */     if ((nullBits & 0x1) != 0) {
/* 273 */       int effectsOffset = buffer.getIntLE(offset + 21);
/* 274 */       if (effectsOffset < 0) {
/* 275 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 277 */       int pos = offset + 45 + effectsOffset;
/* 278 */       if (pos >= buffer.writerIndex()) {
/* 279 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 281 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 282 */       if (!effectsResult.isValid()) {
/* 283 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 285 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 288 */     if ((nullBits & 0x2) != 0) {
/* 289 */       int settingsOffset = buffer.getIntLE(offset + 25);
/* 290 */       if (settingsOffset < 0) {
/* 291 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 293 */       int pos = offset + 45 + settingsOffset;
/* 294 */       if (pos >= buffer.writerIndex()) {
/* 295 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 297 */       int settingsCount = VarInt.peek(buffer, pos);
/* 298 */       if (settingsCount < 0) {
/* 299 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 301 */       if (settingsCount > 4096000) {
/* 302 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 304 */       pos += VarInt.length(buffer, pos);
/* 305 */       for (int i = 0; i < settingsCount; i++) {
/* 306 */         pos++;
/*     */         
/* 308 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 313 */     if ((nullBits & 0x4) != 0) {
/* 314 */       int rulesOffset = buffer.getIntLE(offset + 29);
/* 315 */       if (rulesOffset < 0) {
/* 316 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 318 */       int pos = offset + 45 + rulesOffset;
/* 319 */       if (pos >= buffer.writerIndex()) {
/* 320 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 322 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 323 */       if (!rulesResult.isValid()) {
/* 324 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 326 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 329 */     if ((nullBits & 0x8) != 0) {
/* 330 */       int tagsOffset = buffer.getIntLE(offset + 33);
/* 331 */       if (tagsOffset < 0) {
/* 332 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 334 */       int pos = offset + 45 + tagsOffset;
/* 335 */       if (pos >= buffer.writerIndex()) {
/* 336 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 338 */       int tagsCount = VarInt.peek(buffer, pos);
/* 339 */       if (tagsCount < 0) {
/* 340 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 342 */       if (tagsCount > 4096000) {
/* 343 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 345 */       pos += VarInt.length(buffer, pos);
/* 346 */       pos += tagsCount * 4;
/* 347 */       if (pos > buffer.writerIndex()) {
/* 348 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 352 */     if ((nullBits & 0x10) != 0) {
/* 353 */       int cameraOffset = buffer.getIntLE(offset + 37);
/* 354 */       if (cameraOffset < 0) {
/* 355 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 357 */       int pos = offset + 45 + cameraOffset;
/* 358 */       if (pos >= buffer.writerIndex()) {
/* 359 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 361 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 362 */       if (!cameraResult.isValid()) {
/* 363 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 365 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 368 */     if ((nullBits & 0x20) != 0) {
/* 369 */       int entityEffectsOffset = buffer.getIntLE(offset + 41);
/* 370 */       if (entityEffectsOffset < 0) {
/* 371 */         return ValidationResult.error("Invalid offset for EntityEffects");
/*     */       }
/* 373 */       int pos = offset + 45 + entityEffectsOffset;
/* 374 */       if (pos >= buffer.writerIndex()) {
/* 375 */         return ValidationResult.error("Offset out of bounds for EntityEffects");
/*     */       }
/* 377 */       int entityEffectsCount = VarInt.peek(buffer, pos);
/* 378 */       if (entityEffectsCount < 0) {
/* 379 */         return ValidationResult.error("Invalid array count for EntityEffects");
/*     */       }
/* 381 */       if (entityEffectsCount > 4096000) {
/* 382 */         return ValidationResult.error("EntityEffects exceeds max length 4096000");
/*     */       }
/* 384 */       pos += VarInt.length(buffer, pos);
/* 385 */       pos += entityEffectsCount * 4;
/* 386 */       if (pos > buffer.writerIndex()) {
/* 387 */         return ValidationResult.error("Buffer overflow reading EntityEffects");
/*     */       }
/*     */     } 
/* 390 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public EffectConditionInteraction clone() {
/* 394 */     EffectConditionInteraction copy = new EffectConditionInteraction();
/* 395 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 396 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 397 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 398 */     copy.runTime = this.runTime;
/* 399 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 400 */     if (this.settings != null) {
/* 401 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 402 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 403 */       copy.settings = m;
/*     */     } 
/* 405 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 406 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 407 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 408 */     copy.next = this.next;
/* 409 */     copy.failed = this.failed;
/* 410 */     copy.entityEffects = (this.entityEffects != null) ? Arrays.copyOf(this.entityEffects, this.entityEffects.length) : null;
/* 411 */     copy.match = this.match;
/* 412 */     copy.entityTarget = this.entityTarget;
/* 413 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     EffectConditionInteraction other;
/* 419 */     if (this == obj) return true; 
/* 420 */     if (obj instanceof EffectConditionInteraction) { other = (EffectConditionInteraction)obj; } else { return false; }
/* 421 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && Arrays.equals(this.entityEffects, other.entityEffects) && Objects.equals(this.match, other.match) && Objects.equals(this.entityTarget, other.entityTarget));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 426 */     int result = 1;
/* 427 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 428 */     result = 31 * result + Objects.hashCode(this.effects);
/* 429 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 430 */     result = 31 * result + Float.hashCode(this.runTime);
/* 431 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 432 */     result = 31 * result + Objects.hashCode(this.settings);
/* 433 */     result = 31 * result + Objects.hashCode(this.rules);
/* 434 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 435 */     result = 31 * result + Objects.hashCode(this.camera);
/* 436 */     result = 31 * result + Integer.hashCode(this.next);
/* 437 */     result = 31 * result + Integer.hashCode(this.failed);
/* 438 */     result = 31 * result + Arrays.hashCode(this.entityEffects);
/* 439 */     result = 31 * result + Objects.hashCode(this.match);
/* 440 */     result = 31 * result + Objects.hashCode(this.entityTarget);
/* 441 */     return result;
/*     */   }
/*     */   
/*     */   public EffectConditionInteraction() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\EffectConditionInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */