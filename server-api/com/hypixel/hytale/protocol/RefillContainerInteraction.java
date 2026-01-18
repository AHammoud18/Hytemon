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
/*     */ public class RefillContainerInteraction extends SimpleBlockInteraction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 20;
/*     */   public static final int VARIABLE_FIELD_COUNT = 6;
/*     */   public static final int VARIABLE_BLOCK_START = 44;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public int[] refillFluids;
/*     */   
/*     */   public RefillContainerInteraction() {}
/*     */   
/*     */   public RefillContainerInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, boolean useLatestTarget, @Nullable int[] refillFluids) {
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
/*  38 */     this.refillFluids = refillFluids;
/*     */   }
/*     */   
/*     */   public RefillContainerInteraction(@Nonnull RefillContainerInteraction other) {
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
/*  54 */     this.refillFluids = other.refillFluids;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static RefillContainerInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  59 */     RefillContainerInteraction obj = new RefillContainerInteraction();
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
/* 112 */       int refillFluidsCount = VarInt.peek(buf, varPos5);
/* 113 */       if (refillFluidsCount < 0) throw ProtocolException.negativeLength("RefillFluids", refillFluidsCount); 
/* 114 */       if (refillFluidsCount > 4096000) throw ProtocolException.arrayTooLong("RefillFluids", refillFluidsCount, 4096000); 
/* 115 */       int varIntLen = VarInt.length(buf, varPos5);
/* 116 */       if ((varPos5 + varIntLen) + refillFluidsCount * 4L > buf.readableBytes())
/* 117 */         throw ProtocolException.bufferTooSmall("RefillFluids", varPos5 + varIntLen + refillFluidsCount * 4, buf.readableBytes()); 
/* 118 */       obj.refillFluids = new int[refillFluidsCount];
/* 119 */       for (int i = 0; i < refillFluidsCount; i++) {
/* 120 */         obj.refillFluids[i] = buf.getIntLE(varPos5 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/*     */     
/* 124 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 128 */     byte nullBits = buf.getByte(offset);
/* 129 */     int maxEnd = 44;
/* 130 */     if ((nullBits & 0x1) != 0) {
/* 131 */       int fieldOffset0 = buf.getIntLE(offset + 20);
/* 132 */       int pos0 = offset + 44 + fieldOffset0;
/* 133 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 134 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 136 */     if ((nullBits & 0x2) != 0) {
/* 137 */       int fieldOffset1 = buf.getIntLE(offset + 24);
/* 138 */       int pos1 = offset + 44 + fieldOffset1;
/* 139 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 140 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 141 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 143 */     if ((nullBits & 0x4) != 0) {
/* 144 */       int fieldOffset2 = buf.getIntLE(offset + 28);
/* 145 */       int pos2 = offset + 44 + fieldOffset2;
/* 146 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 147 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 149 */     if ((nullBits & 0x8) != 0) {
/* 150 */       int fieldOffset3 = buf.getIntLE(offset + 32);
/* 151 */       int pos3 = offset + 44 + fieldOffset3;
/* 152 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 153 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 155 */     if ((nullBits & 0x10) != 0) {
/* 156 */       int fieldOffset4 = buf.getIntLE(offset + 36);
/* 157 */       int pos4 = offset + 44 + fieldOffset4;
/* 158 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 159 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 161 */     if ((nullBits & 0x20) != 0) {
/* 162 */       int fieldOffset5 = buf.getIntLE(offset + 40);
/* 163 */       int pos5 = offset + 44 + fieldOffset5;
/* 164 */       int arrLen = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5) + arrLen * 4;
/* 165 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 167 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 173 */     int startPos = buf.writerIndex();
/* 174 */     byte nullBits = 0;
/* 175 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 176 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 177 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 178 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 179 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 180 */     if (this.refillFluids != null) nullBits = (byte)(nullBits | 0x20); 
/* 181 */     buf.writeByte(nullBits);
/*     */     
/* 183 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 184 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 185 */     buf.writeFloatLE(this.runTime);
/* 186 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 187 */     buf.writeIntLE(this.next);
/* 188 */     buf.writeIntLE(this.failed);
/* 189 */     buf.writeByte(this.useLatestTarget ? 1 : 0);
/*     */     
/* 191 */     int effectsOffsetSlot = buf.writerIndex();
/* 192 */     buf.writeIntLE(0);
/* 193 */     int settingsOffsetSlot = buf.writerIndex();
/* 194 */     buf.writeIntLE(0);
/* 195 */     int rulesOffsetSlot = buf.writerIndex();
/* 196 */     buf.writeIntLE(0);
/* 197 */     int tagsOffsetSlot = buf.writerIndex();
/* 198 */     buf.writeIntLE(0);
/* 199 */     int cameraOffsetSlot = buf.writerIndex();
/* 200 */     buf.writeIntLE(0);
/* 201 */     int refillFluidsOffsetSlot = buf.writerIndex();
/* 202 */     buf.writeIntLE(0);
/*     */     
/* 204 */     int varBlockStart = buf.writerIndex();
/* 205 */     if (this.effects != null) {
/* 206 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 207 */       this.effects.serialize(buf);
/*     */     } else {
/* 209 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 211 */     if (this.settings != null)
/* 212 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 213 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 215 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 217 */     if (this.rules != null) {
/* 218 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 219 */       this.rules.serialize(buf);
/*     */     } else {
/* 221 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 223 */     if (this.tags != null) {
/* 224 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 225 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 227 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 229 */     if (this.camera != null) {
/* 230 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 231 */       this.camera.serialize(buf);
/*     */     } else {
/* 233 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 235 */     if (this.refillFluids != null) {
/* 236 */       buf.setIntLE(refillFluidsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 237 */       if (this.refillFluids.length > 4096000) throw ProtocolException.arrayTooLong("RefillFluids", this.refillFluids.length, 4096000);  VarInt.write(buf, this.refillFluids.length); for (int item : this.refillFluids) buf.writeIntLE(item); 
/*     */     } else {
/* 239 */       buf.setIntLE(refillFluidsOffsetSlot, -1);
/*     */     } 
/* 241 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 247 */     int size = 44;
/* 248 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 249 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 250 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 251 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 252 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 253 */     if (this.refillFluids != null) size += VarInt.size(this.refillFluids.length) + this.refillFluids.length * 4;
/*     */     
/* 255 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 259 */     if (buffer.readableBytes() - offset < 44) {
/* 260 */       return ValidationResult.error("Buffer too small: expected at least 44 bytes");
/*     */     }
/*     */     
/* 263 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 266 */     if ((nullBits & 0x1) != 0) {
/* 267 */       int effectsOffset = buffer.getIntLE(offset + 20);
/* 268 */       if (effectsOffset < 0) {
/* 269 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 271 */       int pos = offset + 44 + effectsOffset;
/* 272 */       if (pos >= buffer.writerIndex()) {
/* 273 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 275 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 276 */       if (!effectsResult.isValid()) {
/* 277 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 279 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 282 */     if ((nullBits & 0x2) != 0) {
/* 283 */       int settingsOffset = buffer.getIntLE(offset + 24);
/* 284 */       if (settingsOffset < 0) {
/* 285 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 287 */       int pos = offset + 44 + settingsOffset;
/* 288 */       if (pos >= buffer.writerIndex()) {
/* 289 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 291 */       int settingsCount = VarInt.peek(buffer, pos);
/* 292 */       if (settingsCount < 0) {
/* 293 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 295 */       if (settingsCount > 4096000) {
/* 296 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 298 */       pos += VarInt.length(buffer, pos);
/* 299 */       for (int i = 0; i < settingsCount; i++) {
/* 300 */         pos++;
/*     */         
/* 302 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 307 */     if ((nullBits & 0x4) != 0) {
/* 308 */       int rulesOffset = buffer.getIntLE(offset + 28);
/* 309 */       if (rulesOffset < 0) {
/* 310 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 312 */       int pos = offset + 44 + rulesOffset;
/* 313 */       if (pos >= buffer.writerIndex()) {
/* 314 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 316 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 317 */       if (!rulesResult.isValid()) {
/* 318 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 320 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 323 */     if ((nullBits & 0x8) != 0) {
/* 324 */       int tagsOffset = buffer.getIntLE(offset + 32);
/* 325 */       if (tagsOffset < 0) {
/* 326 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 328 */       int pos = offset + 44 + tagsOffset;
/* 329 */       if (pos >= buffer.writerIndex()) {
/* 330 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 332 */       int tagsCount = VarInt.peek(buffer, pos);
/* 333 */       if (tagsCount < 0) {
/* 334 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 336 */       if (tagsCount > 4096000) {
/* 337 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 339 */       pos += VarInt.length(buffer, pos);
/* 340 */       pos += tagsCount * 4;
/* 341 */       if (pos > buffer.writerIndex()) {
/* 342 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 346 */     if ((nullBits & 0x10) != 0) {
/* 347 */       int cameraOffset = buffer.getIntLE(offset + 36);
/* 348 */       if (cameraOffset < 0) {
/* 349 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 351 */       int pos = offset + 44 + cameraOffset;
/* 352 */       if (pos >= buffer.writerIndex()) {
/* 353 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 355 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 356 */       if (!cameraResult.isValid()) {
/* 357 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 359 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 362 */     if ((nullBits & 0x20) != 0) {
/* 363 */       int refillFluidsOffset = buffer.getIntLE(offset + 40);
/* 364 */       if (refillFluidsOffset < 0) {
/* 365 */         return ValidationResult.error("Invalid offset for RefillFluids");
/*     */       }
/* 367 */       int pos = offset + 44 + refillFluidsOffset;
/* 368 */       if (pos >= buffer.writerIndex()) {
/* 369 */         return ValidationResult.error("Offset out of bounds for RefillFluids");
/*     */       }
/* 371 */       int refillFluidsCount = VarInt.peek(buffer, pos);
/* 372 */       if (refillFluidsCount < 0) {
/* 373 */         return ValidationResult.error("Invalid array count for RefillFluids");
/*     */       }
/* 375 */       if (refillFluidsCount > 4096000) {
/* 376 */         return ValidationResult.error("RefillFluids exceeds max length 4096000");
/*     */       }
/* 378 */       pos += VarInt.length(buffer, pos);
/* 379 */       pos += refillFluidsCount * 4;
/* 380 */       if (pos > buffer.writerIndex()) {
/* 381 */         return ValidationResult.error("Buffer overflow reading RefillFluids");
/*     */       }
/*     */     } 
/* 384 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public RefillContainerInteraction clone() {
/* 388 */     RefillContainerInteraction copy = new RefillContainerInteraction();
/* 389 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 390 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 391 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 392 */     copy.runTime = this.runTime;
/* 393 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 394 */     if (this.settings != null) {
/* 395 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 396 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 397 */       copy.settings = m;
/*     */     } 
/* 399 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 400 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 401 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 402 */     copy.next = this.next;
/* 403 */     copy.failed = this.failed;
/* 404 */     copy.useLatestTarget = this.useLatestTarget;
/* 405 */     copy.refillFluids = (this.refillFluids != null) ? Arrays.copyOf(this.refillFluids, this.refillFluids.length) : null;
/* 406 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     RefillContainerInteraction other;
/* 412 */     if (this == obj) return true; 
/* 413 */     if (obj instanceof RefillContainerInteraction) { other = (RefillContainerInteraction)obj; } else { return false; }
/* 414 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && this.useLatestTarget == other.useLatestTarget && Arrays.equals(this.refillFluids, other.refillFluids));
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
/* 429 */     result = 31 * result + Integer.hashCode(this.next);
/* 430 */     result = 31 * result + Integer.hashCode(this.failed);
/* 431 */     result = 31 * result + Boolean.hashCode(this.useLatestTarget);
/* 432 */     result = 31 * result + Arrays.hashCode(this.refillFluids);
/* 433 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\RefillContainerInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */