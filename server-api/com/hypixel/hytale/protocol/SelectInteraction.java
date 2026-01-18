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
/*     */ 
/*     */ 
/*     */ public class SelectInteraction
/*     */   extends SimpleInteraction
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 25;
/*     */   public static final int VARIABLE_FIELD_COUNT = 7;
/*     */   public static final int VARIABLE_BLOCK_START = 53;
/*     */   @Nonnull
/*  24 */   public FailOnType failOn = FailOnType.Neither; public static final int MAX_SIZE = 1677721600; @Nullable
/*     */   public Selector selector; public boolean ignoreOwner; public int hitEntity;
/*     */   @Nullable
/*     */   public HitEntity[] hitEntityRules;
/*     */   
/*     */   public SelectInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, @Nullable Selector selector, boolean ignoreOwner, int hitEntity, @Nullable HitEntity[] hitEntityRules, @Nonnull FailOnType failOn) {
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
/*  41 */     this.selector = selector;
/*  42 */     this.ignoreOwner = ignoreOwner;
/*  43 */     this.hitEntity = hitEntity;
/*  44 */     this.hitEntityRules = hitEntityRules;
/*  45 */     this.failOn = failOn;
/*     */   }
/*     */   
/*     */   public SelectInteraction(@Nonnull SelectInteraction other) {
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
/*  60 */     this.selector = other.selector;
/*  61 */     this.ignoreOwner = other.ignoreOwner;
/*  62 */     this.hitEntity = other.hitEntity;
/*  63 */     this.hitEntityRules = other.hitEntityRules;
/*  64 */     this.failOn = other.failOn;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SelectInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  69 */     SelectInteraction obj = new SelectInteraction();
/*  70 */     byte nullBits = buf.getByte(offset);
/*  71 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  72 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  73 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  74 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  75 */     obj.next = buf.getIntLE(offset + 11);
/*  76 */     obj.failed = buf.getIntLE(offset + 15);
/*  77 */     obj.ignoreOwner = (buf.getByte(offset + 19) != 0);
/*  78 */     obj.hitEntity = buf.getIntLE(offset + 20);
/*  79 */     obj.failOn = FailOnType.fromValue(buf.getByte(offset + 24));
/*     */     
/*  81 */     if ((nullBits & 0x1) != 0) {
/*  82 */       int varPos0 = offset + 53 + buf.getIntLE(offset + 25);
/*  83 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  85 */     if ((nullBits & 0x2) != 0) {
/*  86 */       int varPos1 = offset + 53 + buf.getIntLE(offset + 29);
/*  87 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  88 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  89 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  90 */       int varIntLen = VarInt.length(buf, varPos1);
/*  91 */       obj.settings = new HashMap<>(settingsCount);
/*  92 */       int dictPos = varPos1 + varIntLen;
/*  93 */       for (int i = 0; i < settingsCount; i++) {
/*  94 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  95 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/*  96 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/*  97 */         if (obj.settings.put(key, val) != null)
/*  98 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/* 101 */     if ((nullBits & 0x4) != 0) {
/* 102 */       int varPos2 = offset + 53 + buf.getIntLE(offset + 33);
/* 103 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/* 105 */     if ((nullBits & 0x8) != 0) {
/* 106 */       int varPos3 = offset + 53 + buf.getIntLE(offset + 37);
/* 107 */       int tagsCount = VarInt.peek(buf, varPos3);
/* 108 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/* 109 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/* 110 */       int varIntLen = VarInt.length(buf, varPos3);
/* 111 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/* 112 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/* 113 */       obj.tags = new int[tagsCount];
/* 114 */       for (int i = 0; i < tagsCount; i++) {
/* 115 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 118 */     if ((nullBits & 0x10) != 0) {
/* 119 */       int varPos4 = offset + 53 + buf.getIntLE(offset + 41);
/* 120 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/* 122 */     if ((nullBits & 0x20) != 0) {
/* 123 */       int varPos5 = offset + 53 + buf.getIntLE(offset + 45);
/* 124 */       obj.selector = Selector.deserialize(buf, varPos5);
/*     */     } 
/* 126 */     if ((nullBits & 0x40) != 0) {
/* 127 */       int varPos6 = offset + 53 + buf.getIntLE(offset + 49);
/* 128 */       int hitEntityRulesCount = VarInt.peek(buf, varPos6);
/* 129 */       if (hitEntityRulesCount < 0) throw ProtocolException.negativeLength("HitEntityRules", hitEntityRulesCount); 
/* 130 */       if (hitEntityRulesCount > 4096000) throw ProtocolException.arrayTooLong("HitEntityRules", hitEntityRulesCount, 4096000); 
/* 131 */       int varIntLen = VarInt.length(buf, varPos6);
/* 132 */       if ((varPos6 + varIntLen) + hitEntityRulesCount * 5L > buf.readableBytes())
/* 133 */         throw ProtocolException.bufferTooSmall("HitEntityRules", varPos6 + varIntLen + hitEntityRulesCount * 5, buf.readableBytes()); 
/* 134 */       obj.hitEntityRules = new HitEntity[hitEntityRulesCount];
/* 135 */       int elemPos = varPos6 + varIntLen;
/* 136 */       for (int i = 0; i < hitEntityRulesCount; i++) {
/* 137 */         obj.hitEntityRules[i] = HitEntity.deserialize(buf, elemPos);
/* 138 */         elemPos += HitEntity.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/* 142 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 146 */     byte nullBits = buf.getByte(offset);
/* 147 */     int maxEnd = 53;
/* 148 */     if ((nullBits & 0x1) != 0) {
/* 149 */       int fieldOffset0 = buf.getIntLE(offset + 25);
/* 150 */       int pos0 = offset + 53 + fieldOffset0;
/* 151 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 152 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 154 */     if ((nullBits & 0x2) != 0) {
/* 155 */       int fieldOffset1 = buf.getIntLE(offset + 29);
/* 156 */       int pos1 = offset + 53 + fieldOffset1;
/* 157 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 158 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 159 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 161 */     if ((nullBits & 0x4) != 0) {
/* 162 */       int fieldOffset2 = buf.getIntLE(offset + 33);
/* 163 */       int pos2 = offset + 53 + fieldOffset2;
/* 164 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 165 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 167 */     if ((nullBits & 0x8) != 0) {
/* 168 */       int fieldOffset3 = buf.getIntLE(offset + 37);
/* 169 */       int pos3 = offset + 53 + fieldOffset3;
/* 170 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 171 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 173 */     if ((nullBits & 0x10) != 0) {
/* 174 */       int fieldOffset4 = buf.getIntLE(offset + 41);
/* 175 */       int pos4 = offset + 53 + fieldOffset4;
/* 176 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 177 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 179 */     if ((nullBits & 0x20) != 0) {
/* 180 */       int fieldOffset5 = buf.getIntLE(offset + 45);
/* 181 */       int pos5 = offset + 53 + fieldOffset5;
/* 182 */       pos5 += Selector.computeBytesConsumed(buf, pos5);
/* 183 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 185 */     if ((nullBits & 0x40) != 0) {
/* 186 */       int fieldOffset6 = buf.getIntLE(offset + 49);
/* 187 */       int pos6 = offset + 53 + fieldOffset6;
/* 188 */       int arrLen = VarInt.peek(buf, pos6); pos6 += VarInt.length(buf, pos6);
/* 189 */       for (int i = 0; i < arrLen; ) { pos6 += HitEntity.computeBytesConsumed(buf, pos6); i++; }
/* 190 */        if (pos6 - offset > maxEnd) maxEnd = pos6 - offset; 
/*     */     } 
/* 192 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 198 */     int startPos = buf.writerIndex();
/* 199 */     byte nullBits = 0;
/* 200 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 201 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 202 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 203 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 204 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 205 */     if (this.selector != null) nullBits = (byte)(nullBits | 0x20); 
/* 206 */     if (this.hitEntityRules != null) nullBits = (byte)(nullBits | 0x40); 
/* 207 */     buf.writeByte(nullBits);
/*     */     
/* 209 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 210 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 211 */     buf.writeFloatLE(this.runTime);
/* 212 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 213 */     buf.writeIntLE(this.next);
/* 214 */     buf.writeIntLE(this.failed);
/* 215 */     buf.writeByte(this.ignoreOwner ? 1 : 0);
/* 216 */     buf.writeIntLE(this.hitEntity);
/* 217 */     buf.writeByte(this.failOn.getValue());
/*     */     
/* 219 */     int effectsOffsetSlot = buf.writerIndex();
/* 220 */     buf.writeIntLE(0);
/* 221 */     int settingsOffsetSlot = buf.writerIndex();
/* 222 */     buf.writeIntLE(0);
/* 223 */     int rulesOffsetSlot = buf.writerIndex();
/* 224 */     buf.writeIntLE(0);
/* 225 */     int tagsOffsetSlot = buf.writerIndex();
/* 226 */     buf.writeIntLE(0);
/* 227 */     int cameraOffsetSlot = buf.writerIndex();
/* 228 */     buf.writeIntLE(0);
/* 229 */     int selectorOffsetSlot = buf.writerIndex();
/* 230 */     buf.writeIntLE(0);
/* 231 */     int hitEntityRulesOffsetSlot = buf.writerIndex();
/* 232 */     buf.writeIntLE(0);
/*     */     
/* 234 */     int varBlockStart = buf.writerIndex();
/* 235 */     if (this.effects != null) {
/* 236 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 237 */       this.effects.serialize(buf);
/*     */     } else {
/* 239 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 241 */     if (this.settings != null)
/* 242 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 243 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 245 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 247 */     if (this.rules != null) {
/* 248 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 249 */       this.rules.serialize(buf);
/*     */     } else {
/* 251 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 253 */     if (this.tags != null) {
/* 254 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 255 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 257 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 259 */     if (this.camera != null) {
/* 260 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 261 */       this.camera.serialize(buf);
/*     */     } else {
/* 263 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 265 */     if (this.selector != null) {
/* 266 */       buf.setIntLE(selectorOffsetSlot, buf.writerIndex() - varBlockStart);
/* 267 */       this.selector.serializeWithTypeId(buf);
/*     */     } else {
/* 269 */       buf.setIntLE(selectorOffsetSlot, -1);
/*     */     } 
/* 271 */     if (this.hitEntityRules != null) {
/* 272 */       buf.setIntLE(hitEntityRulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 273 */       if (this.hitEntityRules.length > 4096000) throw ProtocolException.arrayTooLong("HitEntityRules", this.hitEntityRules.length, 4096000);  VarInt.write(buf, this.hitEntityRules.length); for (HitEntity item : this.hitEntityRules) item.serialize(buf); 
/*     */     } else {
/* 275 */       buf.setIntLE(hitEntityRulesOffsetSlot, -1);
/*     */     } 
/* 277 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 283 */     int size = 53;
/* 284 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 285 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 286 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 287 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 288 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 289 */     if (this.selector != null) size += this.selector.computeSizeWithTypeId(); 
/* 290 */     if (this.hitEntityRules != null) {
/* 291 */       int hitEntityRulesSize = 0;
/* 292 */       for (HitEntity elem : this.hitEntityRules) hitEntityRulesSize += elem.computeSize(); 
/* 293 */       size += VarInt.size(this.hitEntityRules.length) + hitEntityRulesSize;
/*     */     } 
/*     */     
/* 296 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 300 */     if (buffer.readableBytes() - offset < 53) {
/* 301 */       return ValidationResult.error("Buffer too small: expected at least 53 bytes");
/*     */     }
/*     */     
/* 304 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 307 */     if ((nullBits & 0x1) != 0) {
/* 308 */       int effectsOffset = buffer.getIntLE(offset + 25);
/* 309 */       if (effectsOffset < 0) {
/* 310 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 312 */       int pos = offset + 53 + effectsOffset;
/* 313 */       if (pos >= buffer.writerIndex()) {
/* 314 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 316 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 317 */       if (!effectsResult.isValid()) {
/* 318 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 320 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 323 */     if ((nullBits & 0x2) != 0) {
/* 324 */       int settingsOffset = buffer.getIntLE(offset + 29);
/* 325 */       if (settingsOffset < 0) {
/* 326 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 328 */       int pos = offset + 53 + settingsOffset;
/* 329 */       if (pos >= buffer.writerIndex()) {
/* 330 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 332 */       int settingsCount = VarInt.peek(buffer, pos);
/* 333 */       if (settingsCount < 0) {
/* 334 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 336 */       if (settingsCount > 4096000) {
/* 337 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 339 */       pos += VarInt.length(buffer, pos);
/* 340 */       for (int i = 0; i < settingsCount; i++) {
/* 341 */         pos++;
/*     */         
/* 343 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 348 */     if ((nullBits & 0x4) != 0) {
/* 349 */       int rulesOffset = buffer.getIntLE(offset + 33);
/* 350 */       if (rulesOffset < 0) {
/* 351 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 353 */       int pos = offset + 53 + rulesOffset;
/* 354 */       if (pos >= buffer.writerIndex()) {
/* 355 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 357 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 358 */       if (!rulesResult.isValid()) {
/* 359 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 361 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 364 */     if ((nullBits & 0x8) != 0) {
/* 365 */       int tagsOffset = buffer.getIntLE(offset + 37);
/* 366 */       if (tagsOffset < 0) {
/* 367 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 369 */       int pos = offset + 53 + tagsOffset;
/* 370 */       if (pos >= buffer.writerIndex()) {
/* 371 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 373 */       int tagsCount = VarInt.peek(buffer, pos);
/* 374 */       if (tagsCount < 0) {
/* 375 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 377 */       if (tagsCount > 4096000) {
/* 378 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 380 */       pos += VarInt.length(buffer, pos);
/* 381 */       pos += tagsCount * 4;
/* 382 */       if (pos > buffer.writerIndex()) {
/* 383 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 387 */     if ((nullBits & 0x10) != 0) {
/* 388 */       int cameraOffset = buffer.getIntLE(offset + 41);
/* 389 */       if (cameraOffset < 0) {
/* 390 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 392 */       int pos = offset + 53 + cameraOffset;
/* 393 */       if (pos >= buffer.writerIndex()) {
/* 394 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 396 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 397 */       if (!cameraResult.isValid()) {
/* 398 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 400 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 403 */     if ((nullBits & 0x20) != 0) {
/* 404 */       int selectorOffset = buffer.getIntLE(offset + 45);
/* 405 */       if (selectorOffset < 0) {
/* 406 */         return ValidationResult.error("Invalid offset for Selector");
/*     */       }
/* 408 */       int pos = offset + 53 + selectorOffset;
/* 409 */       if (pos >= buffer.writerIndex()) {
/* 410 */         return ValidationResult.error("Offset out of bounds for Selector");
/*     */       }
/* 412 */       ValidationResult selectorResult = Selector.validateStructure(buffer, pos);
/* 413 */       if (!selectorResult.isValid()) {
/* 414 */         return ValidationResult.error("Invalid Selector: " + selectorResult.error());
/*     */       }
/* 416 */       pos += Selector.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 419 */     if ((nullBits & 0x40) != 0) {
/* 420 */       int hitEntityRulesOffset = buffer.getIntLE(offset + 49);
/* 421 */       if (hitEntityRulesOffset < 0) {
/* 422 */         return ValidationResult.error("Invalid offset for HitEntityRules");
/*     */       }
/* 424 */       int pos = offset + 53 + hitEntityRulesOffset;
/* 425 */       if (pos >= buffer.writerIndex()) {
/* 426 */         return ValidationResult.error("Offset out of bounds for HitEntityRules");
/*     */       }
/* 428 */       int hitEntityRulesCount = VarInt.peek(buffer, pos);
/* 429 */       if (hitEntityRulesCount < 0) {
/* 430 */         return ValidationResult.error("Invalid array count for HitEntityRules");
/*     */       }
/* 432 */       if (hitEntityRulesCount > 4096000) {
/* 433 */         return ValidationResult.error("HitEntityRules exceeds max length 4096000");
/*     */       }
/* 435 */       pos += VarInt.length(buffer, pos);
/* 436 */       for (int i = 0; i < hitEntityRulesCount; i++) {
/* 437 */         ValidationResult structResult = HitEntity.validateStructure(buffer, pos);
/* 438 */         if (!structResult.isValid()) {
/* 439 */           return ValidationResult.error("Invalid HitEntity in HitEntityRules[" + i + "]: " + structResult.error());
/*     */         }
/* 441 */         pos += HitEntity.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 444 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SelectInteraction clone() {
/* 448 */     SelectInteraction copy = new SelectInteraction();
/* 449 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 450 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 451 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 452 */     copy.runTime = this.runTime;
/* 453 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 454 */     if (this.settings != null) {
/* 455 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 456 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 457 */       copy.settings = m;
/*     */     } 
/* 459 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 460 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 461 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 462 */     copy.next = this.next;
/* 463 */     copy.failed = this.failed;
/* 464 */     copy.selector = this.selector;
/* 465 */     copy.ignoreOwner = this.ignoreOwner;
/* 466 */     copy.hitEntity = this.hitEntity;
/* 467 */     copy.hitEntityRules = (this.hitEntityRules != null) ? (HitEntity[])Arrays.<HitEntity>stream(this.hitEntityRules).map(e -> e.clone()).toArray(x$0 -> new HitEntity[x$0]) : null;
/* 468 */     copy.failOn = this.failOn;
/* 469 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SelectInteraction other;
/* 475 */     if (this == obj) return true; 
/* 476 */     if (obj instanceof SelectInteraction) { other = (SelectInteraction)obj; } else { return false; }
/* 477 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && Objects.equals(this.selector, other.selector) && this.ignoreOwner == other.ignoreOwner && this.hitEntity == other.hitEntity && Arrays.equals((Object[])this.hitEntityRules, (Object[])other.hitEntityRules) && Objects.equals(this.failOn, other.failOn));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 482 */     int result = 1;
/* 483 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 484 */     result = 31 * result + Objects.hashCode(this.effects);
/* 485 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 486 */     result = 31 * result + Float.hashCode(this.runTime);
/* 487 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 488 */     result = 31 * result + Objects.hashCode(this.settings);
/* 489 */     result = 31 * result + Objects.hashCode(this.rules);
/* 490 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 491 */     result = 31 * result + Objects.hashCode(this.camera);
/* 492 */     result = 31 * result + Integer.hashCode(this.next);
/* 493 */     result = 31 * result + Integer.hashCode(this.failed);
/* 494 */     result = 31 * result + Objects.hashCode(this.selector);
/* 495 */     result = 31 * result + Boolean.hashCode(this.ignoreOwner);
/* 496 */     result = 31 * result + Integer.hashCode(this.hitEntity);
/* 497 */     result = 31 * result + Arrays.hashCode((Object[])this.hitEntityRules);
/* 498 */     result = 31 * result + Objects.hashCode(this.failOn);
/* 499 */     return result;
/*     */   }
/*     */   
/*     */   public SelectInteraction() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\SelectInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */