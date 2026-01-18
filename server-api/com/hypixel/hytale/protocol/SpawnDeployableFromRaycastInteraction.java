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
/*     */ public class SpawnDeployableFromRaycastInteraction extends SimpleInteraction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 23;
/*     */   public static final int VARIABLE_FIELD_COUNT = 7;
/*     */   public static final int VARIABLE_BLOCK_START = 51;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public DeployableConfig deployableConfig;
/*     */   public float maxDistance;
/*     */   @Nullable
/*     */   public Map<Integer, Float> costs;
/*     */   
/*     */   public SpawnDeployableFromRaycastInteraction() {}
/*     */   
/*     */   public SpawnDeployableFromRaycastInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, @Nullable DeployableConfig deployableConfig, float maxDistance, @Nullable Map<Integer, Float> costs) {
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
/*  39 */     this.deployableConfig = deployableConfig;
/*  40 */     this.maxDistance = maxDistance;
/*  41 */     this.costs = costs;
/*     */   }
/*     */   
/*     */   public SpawnDeployableFromRaycastInteraction(@Nonnull SpawnDeployableFromRaycastInteraction other) {
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
/*  56 */     this.deployableConfig = other.deployableConfig;
/*  57 */     this.maxDistance = other.maxDistance;
/*  58 */     this.costs = other.costs;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SpawnDeployableFromRaycastInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  63 */     SpawnDeployableFromRaycastInteraction obj = new SpawnDeployableFromRaycastInteraction();
/*  64 */     byte nullBits = buf.getByte(offset);
/*  65 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  66 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  67 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  68 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  69 */     obj.next = buf.getIntLE(offset + 11);
/*  70 */     obj.failed = buf.getIntLE(offset + 15);
/*  71 */     obj.maxDistance = buf.getFloatLE(offset + 19);
/*     */     
/*  73 */     if ((nullBits & 0x1) != 0) {
/*  74 */       int varPos0 = offset + 51 + buf.getIntLE(offset + 23);
/*  75 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  77 */     if ((nullBits & 0x2) != 0) {
/*  78 */       int varPos1 = offset + 51 + buf.getIntLE(offset + 27);
/*  79 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  80 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  81 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  82 */       int varIntLen = VarInt.length(buf, varPos1);
/*  83 */       obj.settings = new HashMap<>(settingsCount);
/*  84 */       int dictPos = varPos1 + varIntLen;
/*  85 */       for (int i = 0; i < settingsCount; i++) {
/*  86 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  87 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/*  88 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/*  89 */         if (obj.settings.put(key, val) != null)
/*  90 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/*  93 */     if ((nullBits & 0x4) != 0) {
/*  94 */       int varPos2 = offset + 51 + buf.getIntLE(offset + 31);
/*  95 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/*  97 */     if ((nullBits & 0x8) != 0) {
/*  98 */       int varPos3 = offset + 51 + buf.getIntLE(offset + 35);
/*  99 */       int tagsCount = VarInt.peek(buf, varPos3);
/* 100 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/* 101 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/* 102 */       int varIntLen = VarInt.length(buf, varPos3);
/* 103 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/* 104 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/* 105 */       obj.tags = new int[tagsCount];
/* 106 */       for (int i = 0; i < tagsCount; i++) {
/* 107 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 110 */     if ((nullBits & 0x10) != 0) {
/* 111 */       int varPos4 = offset + 51 + buf.getIntLE(offset + 39);
/* 112 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/* 114 */     if ((nullBits & 0x20) != 0) {
/* 115 */       int varPos5 = offset + 51 + buf.getIntLE(offset + 43);
/* 116 */       obj.deployableConfig = DeployableConfig.deserialize(buf, varPos5);
/*     */     } 
/* 118 */     if ((nullBits & 0x40) != 0) {
/* 119 */       int varPos6 = offset + 51 + buf.getIntLE(offset + 47);
/* 120 */       int costsCount = VarInt.peek(buf, varPos6);
/* 121 */       if (costsCount < 0) throw ProtocolException.negativeLength("Costs", costsCount); 
/* 122 */       if (costsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Costs", costsCount, 4096000); 
/* 123 */       int varIntLen = VarInt.length(buf, varPos6);
/* 124 */       obj.costs = new HashMap<>(costsCount);
/* 125 */       int dictPos = varPos6 + varIntLen;
/* 126 */       for (int i = 0; i < costsCount; i++) {
/* 127 */         int key = buf.getIntLE(dictPos); dictPos += 4;
/* 128 */         float val = buf.getFloatLE(dictPos); dictPos += 4;
/* 129 */         if (obj.costs.put(Integer.valueOf(key), Float.valueOf(val)) != null) {
/* 130 */           throw ProtocolException.duplicateKey("costs", Integer.valueOf(key));
/*     */         }
/*     */       } 
/*     */     } 
/* 134 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 138 */     byte nullBits = buf.getByte(offset);
/* 139 */     int maxEnd = 51;
/* 140 */     if ((nullBits & 0x1) != 0) {
/* 141 */       int fieldOffset0 = buf.getIntLE(offset + 23);
/* 142 */       int pos0 = offset + 51 + fieldOffset0;
/* 143 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 144 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 146 */     if ((nullBits & 0x2) != 0) {
/* 147 */       int fieldOffset1 = buf.getIntLE(offset + 27);
/* 148 */       int pos1 = offset + 51 + fieldOffset1;
/* 149 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 150 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 151 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 153 */     if ((nullBits & 0x4) != 0) {
/* 154 */       int fieldOffset2 = buf.getIntLE(offset + 31);
/* 155 */       int pos2 = offset + 51 + fieldOffset2;
/* 156 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 157 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 159 */     if ((nullBits & 0x8) != 0) {
/* 160 */       int fieldOffset3 = buf.getIntLE(offset + 35);
/* 161 */       int pos3 = offset + 51 + fieldOffset3;
/* 162 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 163 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 165 */     if ((nullBits & 0x10) != 0) {
/* 166 */       int fieldOffset4 = buf.getIntLE(offset + 39);
/* 167 */       int pos4 = offset + 51 + fieldOffset4;
/* 168 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 169 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 171 */     if ((nullBits & 0x20) != 0) {
/* 172 */       int fieldOffset5 = buf.getIntLE(offset + 43);
/* 173 */       int pos5 = offset + 51 + fieldOffset5;
/* 174 */       pos5 += DeployableConfig.computeBytesConsumed(buf, pos5);
/* 175 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 177 */     if ((nullBits & 0x40) != 0) {
/* 178 */       int fieldOffset6 = buf.getIntLE(offset + 47);
/* 179 */       int pos6 = offset + 51 + fieldOffset6;
/* 180 */       int dictLen = VarInt.peek(buf, pos6); pos6 += VarInt.length(buf, pos6);
/* 181 */       for (int i = 0; i < dictLen; ) { pos6 += 4; pos6 += 4; i++; }
/* 182 */        if (pos6 - offset > maxEnd) maxEnd = pos6 - offset; 
/*     */     } 
/* 184 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 190 */     int startPos = buf.writerIndex();
/* 191 */     byte nullBits = 0;
/* 192 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 193 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 194 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 195 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 196 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 197 */     if (this.deployableConfig != null) nullBits = (byte)(nullBits | 0x20); 
/* 198 */     if (this.costs != null) nullBits = (byte)(nullBits | 0x40); 
/* 199 */     buf.writeByte(nullBits);
/*     */     
/* 201 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 202 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 203 */     buf.writeFloatLE(this.runTime);
/* 204 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 205 */     buf.writeIntLE(this.next);
/* 206 */     buf.writeIntLE(this.failed);
/* 207 */     buf.writeFloatLE(this.maxDistance);
/*     */     
/* 209 */     int effectsOffsetSlot = buf.writerIndex();
/* 210 */     buf.writeIntLE(0);
/* 211 */     int settingsOffsetSlot = buf.writerIndex();
/* 212 */     buf.writeIntLE(0);
/* 213 */     int rulesOffsetSlot = buf.writerIndex();
/* 214 */     buf.writeIntLE(0);
/* 215 */     int tagsOffsetSlot = buf.writerIndex();
/* 216 */     buf.writeIntLE(0);
/* 217 */     int cameraOffsetSlot = buf.writerIndex();
/* 218 */     buf.writeIntLE(0);
/* 219 */     int deployableConfigOffsetSlot = buf.writerIndex();
/* 220 */     buf.writeIntLE(0);
/* 221 */     int costsOffsetSlot = buf.writerIndex();
/* 222 */     buf.writeIntLE(0);
/*     */     
/* 224 */     int varBlockStart = buf.writerIndex();
/* 225 */     if (this.effects != null) {
/* 226 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 227 */       this.effects.serialize(buf);
/*     */     } else {
/* 229 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 231 */     if (this.settings != null)
/* 232 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 233 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 235 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 237 */     if (this.rules != null) {
/* 238 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 239 */       this.rules.serialize(buf);
/*     */     } else {
/* 241 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 243 */     if (this.tags != null) {
/* 244 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 245 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 247 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 249 */     if (this.camera != null) {
/* 250 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 251 */       this.camera.serialize(buf);
/*     */     } else {
/* 253 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 255 */     if (this.deployableConfig != null) {
/* 256 */       buf.setIntLE(deployableConfigOffsetSlot, buf.writerIndex() - varBlockStart);
/* 257 */       this.deployableConfig.serialize(buf);
/*     */     } else {
/* 259 */       buf.setIntLE(deployableConfigOffsetSlot, -1);
/*     */     } 
/* 261 */     if (this.costs != null)
/* 262 */     { buf.setIntLE(costsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 263 */       if (this.costs.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Costs", this.costs.size(), 4096000);  VarInt.write(buf, this.costs.size()); for (Map.Entry<Integer, Float> e : this.costs.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); buf.writeFloatLE(((Float)e.getValue()).floatValue()); }
/*     */        }
/* 265 */     else { buf.setIntLE(costsOffsetSlot, -1); }
/*     */     
/* 267 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 273 */     int size = 51;
/* 274 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 275 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 276 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 277 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 278 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 279 */     if (this.deployableConfig != null) size += this.deployableConfig.computeSize(); 
/* 280 */     if (this.costs != null) size += VarInt.size(this.costs.size()) + this.costs.size() * 8;
/*     */     
/* 282 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 286 */     if (buffer.readableBytes() - offset < 51) {
/* 287 */       return ValidationResult.error("Buffer too small: expected at least 51 bytes");
/*     */     }
/*     */     
/* 290 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 293 */     if ((nullBits & 0x1) != 0) {
/* 294 */       int effectsOffset = buffer.getIntLE(offset + 23);
/* 295 */       if (effectsOffset < 0) {
/* 296 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 298 */       int pos = offset + 51 + effectsOffset;
/* 299 */       if (pos >= buffer.writerIndex()) {
/* 300 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 302 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 303 */       if (!effectsResult.isValid()) {
/* 304 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 306 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 309 */     if ((nullBits & 0x2) != 0) {
/* 310 */       int settingsOffset = buffer.getIntLE(offset + 27);
/* 311 */       if (settingsOffset < 0) {
/* 312 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 314 */       int pos = offset + 51 + settingsOffset;
/* 315 */       if (pos >= buffer.writerIndex()) {
/* 316 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 318 */       int settingsCount = VarInt.peek(buffer, pos);
/* 319 */       if (settingsCount < 0) {
/* 320 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 322 */       if (settingsCount > 4096000) {
/* 323 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 325 */       pos += VarInt.length(buffer, pos);
/* 326 */       for (int i = 0; i < settingsCount; i++) {
/* 327 */         pos++;
/*     */         
/* 329 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 334 */     if ((nullBits & 0x4) != 0) {
/* 335 */       int rulesOffset = buffer.getIntLE(offset + 31);
/* 336 */       if (rulesOffset < 0) {
/* 337 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 339 */       int pos = offset + 51 + rulesOffset;
/* 340 */       if (pos >= buffer.writerIndex()) {
/* 341 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 343 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 344 */       if (!rulesResult.isValid()) {
/* 345 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 347 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 350 */     if ((nullBits & 0x8) != 0) {
/* 351 */       int tagsOffset = buffer.getIntLE(offset + 35);
/* 352 */       if (tagsOffset < 0) {
/* 353 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 355 */       int pos = offset + 51 + tagsOffset;
/* 356 */       if (pos >= buffer.writerIndex()) {
/* 357 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 359 */       int tagsCount = VarInt.peek(buffer, pos);
/* 360 */       if (tagsCount < 0) {
/* 361 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 363 */       if (tagsCount > 4096000) {
/* 364 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 366 */       pos += VarInt.length(buffer, pos);
/* 367 */       pos += tagsCount * 4;
/* 368 */       if (pos > buffer.writerIndex()) {
/* 369 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 373 */     if ((nullBits & 0x10) != 0) {
/* 374 */       int cameraOffset = buffer.getIntLE(offset + 39);
/* 375 */       if (cameraOffset < 0) {
/* 376 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 378 */       int pos = offset + 51 + cameraOffset;
/* 379 */       if (pos >= buffer.writerIndex()) {
/* 380 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 382 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 383 */       if (!cameraResult.isValid()) {
/* 384 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 386 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 389 */     if ((nullBits & 0x20) != 0) {
/* 390 */       int deployableConfigOffset = buffer.getIntLE(offset + 43);
/* 391 */       if (deployableConfigOffset < 0) {
/* 392 */         return ValidationResult.error("Invalid offset for DeployableConfig");
/*     */       }
/* 394 */       int pos = offset + 51 + deployableConfigOffset;
/* 395 */       if (pos >= buffer.writerIndex()) {
/* 396 */         return ValidationResult.error("Offset out of bounds for DeployableConfig");
/*     */       }
/* 398 */       ValidationResult deployableConfigResult = DeployableConfig.validateStructure(buffer, pos);
/* 399 */       if (!deployableConfigResult.isValid()) {
/* 400 */         return ValidationResult.error("Invalid DeployableConfig: " + deployableConfigResult.error());
/*     */       }
/* 402 */       pos += DeployableConfig.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 405 */     if ((nullBits & 0x40) != 0) {
/* 406 */       int costsOffset = buffer.getIntLE(offset + 47);
/* 407 */       if (costsOffset < 0) {
/* 408 */         return ValidationResult.error("Invalid offset for Costs");
/*     */       }
/* 410 */       int pos = offset + 51 + costsOffset;
/* 411 */       if (pos >= buffer.writerIndex()) {
/* 412 */         return ValidationResult.error("Offset out of bounds for Costs");
/*     */       }
/* 414 */       int costsCount = VarInt.peek(buffer, pos);
/* 415 */       if (costsCount < 0) {
/* 416 */         return ValidationResult.error("Invalid dictionary count for Costs");
/*     */       }
/* 418 */       if (costsCount > 4096000) {
/* 419 */         return ValidationResult.error("Costs exceeds max length 4096000");
/*     */       }
/* 421 */       pos += VarInt.length(buffer, pos);
/* 422 */       for (int i = 0; i < costsCount; i++) {
/* 423 */         pos += 4;
/* 424 */         if (pos > buffer.writerIndex()) {
/* 425 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 427 */         pos += 4;
/* 428 */         if (pos > buffer.writerIndex()) {
/* 429 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/* 433 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SpawnDeployableFromRaycastInteraction clone() {
/* 437 */     SpawnDeployableFromRaycastInteraction copy = new SpawnDeployableFromRaycastInteraction();
/* 438 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 439 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 440 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 441 */     copy.runTime = this.runTime;
/* 442 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 443 */     if (this.settings != null) {
/* 444 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 445 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 446 */       copy.settings = m;
/*     */     } 
/* 448 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 449 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 450 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 451 */     copy.next = this.next;
/* 452 */     copy.failed = this.failed;
/* 453 */     copy.deployableConfig = (this.deployableConfig != null) ? this.deployableConfig.clone() : null;
/* 454 */     copy.maxDistance = this.maxDistance;
/* 455 */     copy.costs = (this.costs != null) ? new HashMap<>(this.costs) : null;
/* 456 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SpawnDeployableFromRaycastInteraction other;
/* 462 */     if (this == obj) return true; 
/* 463 */     if (obj instanceof SpawnDeployableFromRaycastInteraction) { other = (SpawnDeployableFromRaycastInteraction)obj; } else { return false; }
/* 464 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && Objects.equals(this.deployableConfig, other.deployableConfig) && this.maxDistance == other.maxDistance && Objects.equals(this.costs, other.costs));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 469 */     int result = 1;
/* 470 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 471 */     result = 31 * result + Objects.hashCode(this.effects);
/* 472 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 473 */     result = 31 * result + Float.hashCode(this.runTime);
/* 474 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 475 */     result = 31 * result + Objects.hashCode(this.settings);
/* 476 */     result = 31 * result + Objects.hashCode(this.rules);
/* 477 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 478 */     result = 31 * result + Objects.hashCode(this.camera);
/* 479 */     result = 31 * result + Integer.hashCode(this.next);
/* 480 */     result = 31 * result + Integer.hashCode(this.failed);
/* 481 */     result = 31 * result + Objects.hashCode(this.deployableConfig);
/* 482 */     result = 31 * result + Float.hashCode(this.maxDistance);
/* 483 */     result = 31 * result + Objects.hashCode(this.costs);
/* 484 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\SpawnDeployableFromRaycastInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */