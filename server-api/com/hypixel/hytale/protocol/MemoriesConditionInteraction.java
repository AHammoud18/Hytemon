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
/*     */ public class MemoriesConditionInteraction extends Interaction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 15;
/*     */   public static final int VARIABLE_FIELD_COUNT = 6;
/*     */   public static final int VARIABLE_BLOCK_START = 39;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public Map<Integer, Integer> memoriesNext;
/*  21 */   public int failed = Integer.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemoriesConditionInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, @Nullable Map<Integer, Integer> memoriesNext, int failed) {
/*  27 */     this.waitForDataFrom = waitForDataFrom;
/*  28 */     this.effects = effects;
/*  29 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  30 */     this.runTime = runTime;
/*  31 */     this.cancelOnItemChange = cancelOnItemChange;
/*  32 */     this.settings = settings;
/*  33 */     this.rules = rules;
/*  34 */     this.tags = tags;
/*  35 */     this.camera = camera;
/*  36 */     this.memoriesNext = memoriesNext;
/*  37 */     this.failed = failed;
/*     */   }
/*     */   
/*     */   public MemoriesConditionInteraction(@Nonnull MemoriesConditionInteraction other) {
/*  41 */     this.waitForDataFrom = other.waitForDataFrom;
/*  42 */     this.effects = other.effects;
/*  43 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  44 */     this.runTime = other.runTime;
/*  45 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  46 */     this.settings = other.settings;
/*  47 */     this.rules = other.rules;
/*  48 */     this.tags = other.tags;
/*  49 */     this.camera = other.camera;
/*  50 */     this.memoriesNext = other.memoriesNext;
/*  51 */     this.failed = other.failed;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static MemoriesConditionInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  56 */     MemoriesConditionInteraction obj = new MemoriesConditionInteraction();
/*  57 */     byte nullBits = buf.getByte(offset);
/*  58 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  59 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  60 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  61 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  62 */     obj.failed = buf.getIntLE(offset + 11);
/*     */     
/*  64 */     if ((nullBits & 0x1) != 0) {
/*  65 */       int varPos0 = offset + 39 + buf.getIntLE(offset + 15);
/*  66 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  68 */     if ((nullBits & 0x2) != 0) {
/*  69 */       int varPos1 = offset + 39 + buf.getIntLE(offset + 19);
/*  70 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  71 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  72 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  73 */       int varIntLen = VarInt.length(buf, varPos1);
/*  74 */       obj.settings = new HashMap<>(settingsCount);
/*  75 */       int dictPos = varPos1 + varIntLen;
/*  76 */       for (int i = 0; i < settingsCount; i++) {
/*  77 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  78 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/*  79 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/*  80 */         if (obj.settings.put(key, val) != null)
/*  81 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/*  84 */     if ((nullBits & 0x4) != 0) {
/*  85 */       int varPos2 = offset + 39 + buf.getIntLE(offset + 23);
/*  86 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/*  88 */     if ((nullBits & 0x8) != 0) {
/*  89 */       int varPos3 = offset + 39 + buf.getIntLE(offset + 27);
/*  90 */       int tagsCount = VarInt.peek(buf, varPos3);
/*  91 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/*  92 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/*  93 */       int varIntLen = VarInt.length(buf, varPos3);
/*  94 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/*  95 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/*  96 */       obj.tags = new int[tagsCount];
/*  97 */       for (int i = 0; i < tagsCount; i++) {
/*  98 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 101 */     if ((nullBits & 0x10) != 0) {
/* 102 */       int varPos4 = offset + 39 + buf.getIntLE(offset + 31);
/* 103 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/* 105 */     if ((nullBits & 0x20) != 0) {
/* 106 */       int varPos5 = offset + 39 + buf.getIntLE(offset + 35);
/* 107 */       int memoriesNextCount = VarInt.peek(buf, varPos5);
/* 108 */       if (memoriesNextCount < 0) throw ProtocolException.negativeLength("MemoriesNext", memoriesNextCount); 
/* 109 */       if (memoriesNextCount > 4096000) throw ProtocolException.dictionaryTooLarge("MemoriesNext", memoriesNextCount, 4096000); 
/* 110 */       int varIntLen = VarInt.length(buf, varPos5);
/* 111 */       obj.memoriesNext = new HashMap<>(memoriesNextCount);
/* 112 */       int dictPos = varPos5 + varIntLen;
/* 113 */       for (int i = 0; i < memoriesNextCount; i++) {
/* 114 */         int key = buf.getIntLE(dictPos); dictPos += 4;
/* 115 */         int val = buf.getIntLE(dictPos); dictPos += 4;
/* 116 */         if (obj.memoriesNext.put(Integer.valueOf(key), Integer.valueOf(val)) != null) {
/* 117 */           throw ProtocolException.duplicateKey("memoriesNext", Integer.valueOf(key));
/*     */         }
/*     */       } 
/*     */     } 
/* 121 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 125 */     byte nullBits = buf.getByte(offset);
/* 126 */     int maxEnd = 39;
/* 127 */     if ((nullBits & 0x1) != 0) {
/* 128 */       int fieldOffset0 = buf.getIntLE(offset + 15);
/* 129 */       int pos0 = offset + 39 + fieldOffset0;
/* 130 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 131 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 133 */     if ((nullBits & 0x2) != 0) {
/* 134 */       int fieldOffset1 = buf.getIntLE(offset + 19);
/* 135 */       int pos1 = offset + 39 + fieldOffset1;
/* 136 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 137 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 138 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 140 */     if ((nullBits & 0x4) != 0) {
/* 141 */       int fieldOffset2 = buf.getIntLE(offset + 23);
/* 142 */       int pos2 = offset + 39 + fieldOffset2;
/* 143 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 144 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 146 */     if ((nullBits & 0x8) != 0) {
/* 147 */       int fieldOffset3 = buf.getIntLE(offset + 27);
/* 148 */       int pos3 = offset + 39 + fieldOffset3;
/* 149 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 150 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 152 */     if ((nullBits & 0x10) != 0) {
/* 153 */       int fieldOffset4 = buf.getIntLE(offset + 31);
/* 154 */       int pos4 = offset + 39 + fieldOffset4;
/* 155 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 156 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 158 */     if ((nullBits & 0x20) != 0) {
/* 159 */       int fieldOffset5 = buf.getIntLE(offset + 35);
/* 160 */       int pos5 = offset + 39 + fieldOffset5;
/* 161 */       int dictLen = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5);
/* 162 */       for (int i = 0; i < dictLen; ) { pos5 += 4; pos5 += 4; i++; }
/* 163 */        if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 165 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 171 */     int startPos = buf.writerIndex();
/* 172 */     byte nullBits = 0;
/* 173 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 174 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 175 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 176 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 177 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 178 */     if (this.memoriesNext != null) nullBits = (byte)(nullBits | 0x20); 
/* 179 */     buf.writeByte(nullBits);
/*     */     
/* 181 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 182 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 183 */     buf.writeFloatLE(this.runTime);
/* 184 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 185 */     buf.writeIntLE(this.failed);
/*     */     
/* 187 */     int effectsOffsetSlot = buf.writerIndex();
/* 188 */     buf.writeIntLE(0);
/* 189 */     int settingsOffsetSlot = buf.writerIndex();
/* 190 */     buf.writeIntLE(0);
/* 191 */     int rulesOffsetSlot = buf.writerIndex();
/* 192 */     buf.writeIntLE(0);
/* 193 */     int tagsOffsetSlot = buf.writerIndex();
/* 194 */     buf.writeIntLE(0);
/* 195 */     int cameraOffsetSlot = buf.writerIndex();
/* 196 */     buf.writeIntLE(0);
/* 197 */     int memoriesNextOffsetSlot = buf.writerIndex();
/* 198 */     buf.writeIntLE(0);
/*     */     
/* 200 */     int varBlockStart = buf.writerIndex();
/* 201 */     if (this.effects != null) {
/* 202 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 203 */       this.effects.serialize(buf);
/*     */     } else {
/* 205 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 207 */     if (this.settings != null)
/* 208 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 209 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 211 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 213 */     if (this.rules != null) {
/* 214 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 215 */       this.rules.serialize(buf);
/*     */     } else {
/* 217 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 219 */     if (this.tags != null) {
/* 220 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 221 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 223 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 225 */     if (this.camera != null) {
/* 226 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 227 */       this.camera.serialize(buf);
/*     */     } else {
/* 229 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 231 */     if (this.memoriesNext != null)
/* 232 */     { buf.setIntLE(memoriesNextOffsetSlot, buf.writerIndex() - varBlockStart);
/* 233 */       if (this.memoriesNext.size() > 4096000) throw ProtocolException.dictionaryTooLarge("MemoriesNext", this.memoriesNext.size(), 4096000);  VarInt.write(buf, this.memoriesNext.size()); for (Map.Entry<Integer, Integer> e : this.memoriesNext.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*     */        }
/* 235 */     else { buf.setIntLE(memoriesNextOffsetSlot, -1); }
/*     */     
/* 237 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 243 */     int size = 39;
/* 244 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 245 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 246 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 247 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 248 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 249 */     if (this.memoriesNext != null) size += VarInt.size(this.memoriesNext.size()) + this.memoriesNext.size() * 8;
/*     */     
/* 251 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 255 */     if (buffer.readableBytes() - offset < 39) {
/* 256 */       return ValidationResult.error("Buffer too small: expected at least 39 bytes");
/*     */     }
/*     */     
/* 259 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 262 */     if ((nullBits & 0x1) != 0) {
/* 263 */       int effectsOffset = buffer.getIntLE(offset + 15);
/* 264 */       if (effectsOffset < 0) {
/* 265 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 267 */       int pos = offset + 39 + effectsOffset;
/* 268 */       if (pos >= buffer.writerIndex()) {
/* 269 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 271 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 272 */       if (!effectsResult.isValid()) {
/* 273 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 275 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 278 */     if ((nullBits & 0x2) != 0) {
/* 279 */       int settingsOffset = buffer.getIntLE(offset + 19);
/* 280 */       if (settingsOffset < 0) {
/* 281 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 283 */       int pos = offset + 39 + settingsOffset;
/* 284 */       if (pos >= buffer.writerIndex()) {
/* 285 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 287 */       int settingsCount = VarInt.peek(buffer, pos);
/* 288 */       if (settingsCount < 0) {
/* 289 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 291 */       if (settingsCount > 4096000) {
/* 292 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 294 */       pos += VarInt.length(buffer, pos);
/* 295 */       for (int i = 0; i < settingsCount; i++) {
/* 296 */         pos++;
/*     */         
/* 298 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 303 */     if ((nullBits & 0x4) != 0) {
/* 304 */       int rulesOffset = buffer.getIntLE(offset + 23);
/* 305 */       if (rulesOffset < 0) {
/* 306 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 308 */       int pos = offset + 39 + rulesOffset;
/* 309 */       if (pos >= buffer.writerIndex()) {
/* 310 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 312 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 313 */       if (!rulesResult.isValid()) {
/* 314 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 316 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 319 */     if ((nullBits & 0x8) != 0) {
/* 320 */       int tagsOffset = buffer.getIntLE(offset + 27);
/* 321 */       if (tagsOffset < 0) {
/* 322 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 324 */       int pos = offset + 39 + tagsOffset;
/* 325 */       if (pos >= buffer.writerIndex()) {
/* 326 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 328 */       int tagsCount = VarInt.peek(buffer, pos);
/* 329 */       if (tagsCount < 0) {
/* 330 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 332 */       if (tagsCount > 4096000) {
/* 333 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 335 */       pos += VarInt.length(buffer, pos);
/* 336 */       pos += tagsCount * 4;
/* 337 */       if (pos > buffer.writerIndex()) {
/* 338 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 342 */     if ((nullBits & 0x10) != 0) {
/* 343 */       int cameraOffset = buffer.getIntLE(offset + 31);
/* 344 */       if (cameraOffset < 0) {
/* 345 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 347 */       int pos = offset + 39 + cameraOffset;
/* 348 */       if (pos >= buffer.writerIndex()) {
/* 349 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 351 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 352 */       if (!cameraResult.isValid()) {
/* 353 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 355 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 358 */     if ((nullBits & 0x20) != 0) {
/* 359 */       int memoriesNextOffset = buffer.getIntLE(offset + 35);
/* 360 */       if (memoriesNextOffset < 0) {
/* 361 */         return ValidationResult.error("Invalid offset for MemoriesNext");
/*     */       }
/* 363 */       int pos = offset + 39 + memoriesNextOffset;
/* 364 */       if (pos >= buffer.writerIndex()) {
/* 365 */         return ValidationResult.error("Offset out of bounds for MemoriesNext");
/*     */       }
/* 367 */       int memoriesNextCount = VarInt.peek(buffer, pos);
/* 368 */       if (memoriesNextCount < 0) {
/* 369 */         return ValidationResult.error("Invalid dictionary count for MemoriesNext");
/*     */       }
/* 371 */       if (memoriesNextCount > 4096000) {
/* 372 */         return ValidationResult.error("MemoriesNext exceeds max length 4096000");
/*     */       }
/* 374 */       pos += VarInt.length(buffer, pos);
/* 375 */       for (int i = 0; i < memoriesNextCount; i++) {
/* 376 */         pos += 4;
/* 377 */         if (pos > buffer.writerIndex()) {
/* 378 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 380 */         pos += 4;
/* 381 */         if (pos > buffer.writerIndex()) {
/* 382 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/* 386 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public MemoriesConditionInteraction clone() {
/* 390 */     MemoriesConditionInteraction copy = new MemoriesConditionInteraction();
/* 391 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 392 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 393 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 394 */     copy.runTime = this.runTime;
/* 395 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 396 */     if (this.settings != null) {
/* 397 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 398 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 399 */       copy.settings = m;
/*     */     } 
/* 401 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 402 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 403 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 404 */     copy.memoriesNext = (this.memoriesNext != null) ? new HashMap<>(this.memoriesNext) : null;
/* 405 */     copy.failed = this.failed;
/* 406 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     MemoriesConditionInteraction other;
/* 412 */     if (this == obj) return true; 
/* 413 */     if (obj instanceof MemoriesConditionInteraction) { other = (MemoriesConditionInteraction)obj; } else { return false; }
/* 414 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && Objects.equals(this.memoriesNext, other.memoriesNext) && this.failed == other.failed);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 419 */     int result = 1;
/* 420 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 421 */     result = 31 * result + Objects.hashCode(this.effects);
/* 422 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 423 */     result = 31 * result + Float.hashCode(this.runTime);
/* 424 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 425 */     result = 31 * result + Objects.hashCode(this.settings);
/* 426 */     result = 31 * result + Objects.hashCode(this.rules);
/* 427 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 428 */     result = 31 * result + Objects.hashCode(this.camera);
/* 429 */     result = 31 * result + Objects.hashCode(this.memoriesNext);
/* 430 */     result = 31 * result + Integer.hashCode(this.failed);
/* 431 */     return result;
/*     */   }
/*     */   
/*     */   public MemoriesConditionInteraction() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\MemoriesConditionInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */