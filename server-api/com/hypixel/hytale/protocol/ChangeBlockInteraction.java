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
/*     */ public class ChangeBlockInteraction extends SimpleBlockInteraction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 25;
/*     */   public static final int VARIABLE_FIELD_COUNT = 6;
/*     */   public static final int VARIABLE_BLOCK_START = 49;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public Map<Integer, Integer> blockChanges;
/*     */   public int worldSoundEventIndex;
/*     */   public boolean requireNotBroken;
/*     */   
/*     */   public ChangeBlockInteraction() {}
/*     */   
/*     */   public ChangeBlockInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, boolean useLatestTarget, @Nullable Map<Integer, Integer> blockChanges, int worldSoundEventIndex, boolean requireNotBroken) {
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
/*  39 */     this.useLatestTarget = useLatestTarget;
/*  40 */     this.blockChanges = blockChanges;
/*  41 */     this.worldSoundEventIndex = worldSoundEventIndex;
/*  42 */     this.requireNotBroken = requireNotBroken;
/*     */   }
/*     */   
/*     */   public ChangeBlockInteraction(@Nonnull ChangeBlockInteraction other) {
/*  46 */     this.waitForDataFrom = other.waitForDataFrom;
/*  47 */     this.effects = other.effects;
/*  48 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  49 */     this.runTime = other.runTime;
/*  50 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  51 */     this.settings = other.settings;
/*  52 */     this.rules = other.rules;
/*  53 */     this.tags = other.tags;
/*  54 */     this.camera = other.camera;
/*  55 */     this.next = other.next;
/*  56 */     this.failed = other.failed;
/*  57 */     this.useLatestTarget = other.useLatestTarget;
/*  58 */     this.blockChanges = other.blockChanges;
/*  59 */     this.worldSoundEventIndex = other.worldSoundEventIndex;
/*  60 */     this.requireNotBroken = other.requireNotBroken;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ChangeBlockInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  65 */     ChangeBlockInteraction obj = new ChangeBlockInteraction();
/*  66 */     byte nullBits = buf.getByte(offset);
/*  67 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  68 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  69 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  70 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  71 */     obj.next = buf.getIntLE(offset + 11);
/*  72 */     obj.failed = buf.getIntLE(offset + 15);
/*  73 */     obj.useLatestTarget = (buf.getByte(offset + 19) != 0);
/*  74 */     obj.worldSoundEventIndex = buf.getIntLE(offset + 20);
/*  75 */     obj.requireNotBroken = (buf.getByte(offset + 24) != 0);
/*     */     
/*  77 */     if ((nullBits & 0x1) != 0) {
/*  78 */       int varPos0 = offset + 49 + buf.getIntLE(offset + 25);
/*  79 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  81 */     if ((nullBits & 0x2) != 0) {
/*  82 */       int varPos1 = offset + 49 + buf.getIntLE(offset + 29);
/*  83 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  84 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  85 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  86 */       int varIntLen = VarInt.length(buf, varPos1);
/*  87 */       obj.settings = new HashMap<>(settingsCount);
/*  88 */       int dictPos = varPos1 + varIntLen;
/*  89 */       for (int i = 0; i < settingsCount; i++) {
/*  90 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  91 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/*  92 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/*  93 */         if (obj.settings.put(key, val) != null)
/*  94 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/*  97 */     if ((nullBits & 0x4) != 0) {
/*  98 */       int varPos2 = offset + 49 + buf.getIntLE(offset + 33);
/*  99 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/* 101 */     if ((nullBits & 0x8) != 0) {
/* 102 */       int varPos3 = offset + 49 + buf.getIntLE(offset + 37);
/* 103 */       int tagsCount = VarInt.peek(buf, varPos3);
/* 104 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/* 105 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/* 106 */       int varIntLen = VarInt.length(buf, varPos3);
/* 107 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/* 108 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/* 109 */       obj.tags = new int[tagsCount];
/* 110 */       for (int i = 0; i < tagsCount; i++) {
/* 111 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 114 */     if ((nullBits & 0x10) != 0) {
/* 115 */       int varPos4 = offset + 49 + buf.getIntLE(offset + 41);
/* 116 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/* 118 */     if ((nullBits & 0x20) != 0) {
/* 119 */       int varPos5 = offset + 49 + buf.getIntLE(offset + 45);
/* 120 */       int blockChangesCount = VarInt.peek(buf, varPos5);
/* 121 */       if (blockChangesCount < 0) throw ProtocolException.negativeLength("BlockChanges", blockChangesCount); 
/* 122 */       if (blockChangesCount > 4096000) throw ProtocolException.dictionaryTooLarge("BlockChanges", blockChangesCount, 4096000); 
/* 123 */       int varIntLen = VarInt.length(buf, varPos5);
/* 124 */       obj.blockChanges = new HashMap<>(blockChangesCount);
/* 125 */       int dictPos = varPos5 + varIntLen;
/* 126 */       for (int i = 0; i < blockChangesCount; i++) {
/* 127 */         int key = buf.getIntLE(dictPos); dictPos += 4;
/* 128 */         int val = buf.getIntLE(dictPos); dictPos += 4;
/* 129 */         if (obj.blockChanges.put(Integer.valueOf(key), Integer.valueOf(val)) != null) {
/* 130 */           throw ProtocolException.duplicateKey("blockChanges", Integer.valueOf(key));
/*     */         }
/*     */       } 
/*     */     } 
/* 134 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 138 */     byte nullBits = buf.getByte(offset);
/* 139 */     int maxEnd = 49;
/* 140 */     if ((nullBits & 0x1) != 0) {
/* 141 */       int fieldOffset0 = buf.getIntLE(offset + 25);
/* 142 */       int pos0 = offset + 49 + fieldOffset0;
/* 143 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 144 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 146 */     if ((nullBits & 0x2) != 0) {
/* 147 */       int fieldOffset1 = buf.getIntLE(offset + 29);
/* 148 */       int pos1 = offset + 49 + fieldOffset1;
/* 149 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 150 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 151 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 153 */     if ((nullBits & 0x4) != 0) {
/* 154 */       int fieldOffset2 = buf.getIntLE(offset + 33);
/* 155 */       int pos2 = offset + 49 + fieldOffset2;
/* 156 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 157 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 159 */     if ((nullBits & 0x8) != 0) {
/* 160 */       int fieldOffset3 = buf.getIntLE(offset + 37);
/* 161 */       int pos3 = offset + 49 + fieldOffset3;
/* 162 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 163 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 165 */     if ((nullBits & 0x10) != 0) {
/* 166 */       int fieldOffset4 = buf.getIntLE(offset + 41);
/* 167 */       int pos4 = offset + 49 + fieldOffset4;
/* 168 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 169 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 171 */     if ((nullBits & 0x20) != 0) {
/* 172 */       int fieldOffset5 = buf.getIntLE(offset + 45);
/* 173 */       int pos5 = offset + 49 + fieldOffset5;
/* 174 */       int dictLen = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5);
/* 175 */       for (int i = 0; i < dictLen; ) { pos5 += 4; pos5 += 4; i++; }
/* 176 */        if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 178 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 184 */     int startPos = buf.writerIndex();
/* 185 */     byte nullBits = 0;
/* 186 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 187 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 188 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 189 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 190 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 191 */     if (this.blockChanges != null) nullBits = (byte)(nullBits | 0x20); 
/* 192 */     buf.writeByte(nullBits);
/*     */     
/* 194 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 195 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 196 */     buf.writeFloatLE(this.runTime);
/* 197 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 198 */     buf.writeIntLE(this.next);
/* 199 */     buf.writeIntLE(this.failed);
/* 200 */     buf.writeByte(this.useLatestTarget ? 1 : 0);
/* 201 */     buf.writeIntLE(this.worldSoundEventIndex);
/* 202 */     buf.writeByte(this.requireNotBroken ? 1 : 0);
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
/* 214 */     int blockChangesOffsetSlot = buf.writerIndex();
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
/* 248 */     if (this.blockChanges != null)
/* 249 */     { buf.setIntLE(blockChangesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 250 */       if (this.blockChanges.size() > 4096000) throw ProtocolException.dictionaryTooLarge("BlockChanges", this.blockChanges.size(), 4096000);  VarInt.write(buf, this.blockChanges.size()); for (Map.Entry<Integer, Integer> e : this.blockChanges.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*     */        }
/* 252 */     else { buf.setIntLE(blockChangesOffsetSlot, -1); }
/*     */     
/* 254 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 260 */     int size = 49;
/* 261 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 262 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 263 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 264 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 265 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 266 */     if (this.blockChanges != null) size += VarInt.size(this.blockChanges.size()) + this.blockChanges.size() * 8;
/*     */     
/* 268 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 272 */     if (buffer.readableBytes() - offset < 49) {
/* 273 */       return ValidationResult.error("Buffer too small: expected at least 49 bytes");
/*     */     }
/*     */     
/* 276 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 279 */     if ((nullBits & 0x1) != 0) {
/* 280 */       int effectsOffset = buffer.getIntLE(offset + 25);
/* 281 */       if (effectsOffset < 0) {
/* 282 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 284 */       int pos = offset + 49 + effectsOffset;
/* 285 */       if (pos >= buffer.writerIndex()) {
/* 286 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 288 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 289 */       if (!effectsResult.isValid()) {
/* 290 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 292 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 295 */     if ((nullBits & 0x2) != 0) {
/* 296 */       int settingsOffset = buffer.getIntLE(offset + 29);
/* 297 */       if (settingsOffset < 0) {
/* 298 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 300 */       int pos = offset + 49 + settingsOffset;
/* 301 */       if (pos >= buffer.writerIndex()) {
/* 302 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 304 */       int settingsCount = VarInt.peek(buffer, pos);
/* 305 */       if (settingsCount < 0) {
/* 306 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 308 */       if (settingsCount > 4096000) {
/* 309 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 311 */       pos += VarInt.length(buffer, pos);
/* 312 */       for (int i = 0; i < settingsCount; i++) {
/* 313 */         pos++;
/*     */         
/* 315 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 320 */     if ((nullBits & 0x4) != 0) {
/* 321 */       int rulesOffset = buffer.getIntLE(offset + 33);
/* 322 */       if (rulesOffset < 0) {
/* 323 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 325 */       int pos = offset + 49 + rulesOffset;
/* 326 */       if (pos >= buffer.writerIndex()) {
/* 327 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 329 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 330 */       if (!rulesResult.isValid()) {
/* 331 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 333 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 336 */     if ((nullBits & 0x8) != 0) {
/* 337 */       int tagsOffset = buffer.getIntLE(offset + 37);
/* 338 */       if (tagsOffset < 0) {
/* 339 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 341 */       int pos = offset + 49 + tagsOffset;
/* 342 */       if (pos >= buffer.writerIndex()) {
/* 343 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 345 */       int tagsCount = VarInt.peek(buffer, pos);
/* 346 */       if (tagsCount < 0) {
/* 347 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 349 */       if (tagsCount > 4096000) {
/* 350 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 352 */       pos += VarInt.length(buffer, pos);
/* 353 */       pos += tagsCount * 4;
/* 354 */       if (pos > buffer.writerIndex()) {
/* 355 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 359 */     if ((nullBits & 0x10) != 0) {
/* 360 */       int cameraOffset = buffer.getIntLE(offset + 41);
/* 361 */       if (cameraOffset < 0) {
/* 362 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 364 */       int pos = offset + 49 + cameraOffset;
/* 365 */       if (pos >= buffer.writerIndex()) {
/* 366 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 368 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 369 */       if (!cameraResult.isValid()) {
/* 370 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 372 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 375 */     if ((nullBits & 0x20) != 0) {
/* 376 */       int blockChangesOffset = buffer.getIntLE(offset + 45);
/* 377 */       if (blockChangesOffset < 0) {
/* 378 */         return ValidationResult.error("Invalid offset for BlockChanges");
/*     */       }
/* 380 */       int pos = offset + 49 + blockChangesOffset;
/* 381 */       if (pos >= buffer.writerIndex()) {
/* 382 */         return ValidationResult.error("Offset out of bounds for BlockChanges");
/*     */       }
/* 384 */       int blockChangesCount = VarInt.peek(buffer, pos);
/* 385 */       if (blockChangesCount < 0) {
/* 386 */         return ValidationResult.error("Invalid dictionary count for BlockChanges");
/*     */       }
/* 388 */       if (blockChangesCount > 4096000) {
/* 389 */         return ValidationResult.error("BlockChanges exceeds max length 4096000");
/*     */       }
/* 391 */       pos += VarInt.length(buffer, pos);
/* 392 */       for (int i = 0; i < blockChangesCount; i++) {
/* 393 */         pos += 4;
/* 394 */         if (pos > buffer.writerIndex()) {
/* 395 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 397 */         pos += 4;
/* 398 */         if (pos > buffer.writerIndex()) {
/* 399 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/* 403 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ChangeBlockInteraction clone() {
/* 407 */     ChangeBlockInteraction copy = new ChangeBlockInteraction();
/* 408 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 409 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 410 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 411 */     copy.runTime = this.runTime;
/* 412 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 413 */     if (this.settings != null) {
/* 414 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 415 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 416 */       copy.settings = m;
/*     */     } 
/* 418 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 419 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 420 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 421 */     copy.next = this.next;
/* 422 */     copy.failed = this.failed;
/* 423 */     copy.useLatestTarget = this.useLatestTarget;
/* 424 */     copy.blockChanges = (this.blockChanges != null) ? new HashMap<>(this.blockChanges) : null;
/* 425 */     copy.worldSoundEventIndex = this.worldSoundEventIndex;
/* 426 */     copy.requireNotBroken = this.requireNotBroken;
/* 427 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ChangeBlockInteraction other;
/* 433 */     if (this == obj) return true; 
/* 434 */     if (obj instanceof ChangeBlockInteraction) { other = (ChangeBlockInteraction)obj; } else { return false; }
/* 435 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && this.useLatestTarget == other.useLatestTarget && Objects.equals(this.blockChanges, other.blockChanges) && this.worldSoundEventIndex == other.worldSoundEventIndex && this.requireNotBroken == other.requireNotBroken);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 440 */     int result = 1;
/* 441 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 442 */     result = 31 * result + Objects.hashCode(this.effects);
/* 443 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 444 */     result = 31 * result + Float.hashCode(this.runTime);
/* 445 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 446 */     result = 31 * result + Objects.hashCode(this.settings);
/* 447 */     result = 31 * result + Objects.hashCode(this.rules);
/* 448 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 449 */     result = 31 * result + Objects.hashCode(this.camera);
/* 450 */     result = 31 * result + Integer.hashCode(this.next);
/* 451 */     result = 31 * result + Integer.hashCode(this.failed);
/* 452 */     result = 31 * result + Boolean.hashCode(this.useLatestTarget);
/* 453 */     result = 31 * result + Objects.hashCode(this.blockChanges);
/* 454 */     result = 31 * result + Integer.hashCode(this.worldSoundEventIndex);
/* 455 */     result = 31 * result + Boolean.hashCode(this.requireNotBroken);
/* 456 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ChangeBlockInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */