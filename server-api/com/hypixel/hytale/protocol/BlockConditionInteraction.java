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
/*     */ public class BlockConditionInteraction extends SimpleBlockInteraction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 20;
/*     */   public static final int VARIABLE_FIELD_COUNT = 6;
/*     */   public static final int VARIABLE_BLOCK_START = 44;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public BlockMatcher[] matchers;
/*     */   
/*     */   public BlockConditionInteraction() {}
/*     */   
/*     */   public BlockConditionInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, boolean useLatestTarget, @Nullable BlockMatcher[] matchers) {
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
/*  38 */     this.matchers = matchers;
/*     */   }
/*     */   
/*     */   public BlockConditionInteraction(@Nonnull BlockConditionInteraction other) {
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
/*  54 */     this.matchers = other.matchers;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BlockConditionInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  59 */     BlockConditionInteraction obj = new BlockConditionInteraction();
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
/* 112 */       int matchersCount = VarInt.peek(buf, varPos5);
/* 113 */       if (matchersCount < 0) throw ProtocolException.negativeLength("Matchers", matchersCount); 
/* 114 */       if (matchersCount > 4096000) throw ProtocolException.arrayTooLong("Matchers", matchersCount, 4096000); 
/* 115 */       int varIntLen = VarInt.length(buf, varPos5);
/* 116 */       if ((varPos5 + varIntLen) + matchersCount * 3L > buf.readableBytes())
/* 117 */         throw ProtocolException.bufferTooSmall("Matchers", varPos5 + varIntLen + matchersCount * 3, buf.readableBytes()); 
/* 118 */       obj.matchers = new BlockMatcher[matchersCount];
/* 119 */       int elemPos = varPos5 + varIntLen;
/* 120 */       for (int i = 0; i < matchersCount; i++) {
/* 121 */         obj.matchers[i] = BlockMatcher.deserialize(buf, elemPos);
/* 122 */         elemPos += BlockMatcher.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 130 */     byte nullBits = buf.getByte(offset);
/* 131 */     int maxEnd = 44;
/* 132 */     if ((nullBits & 0x1) != 0) {
/* 133 */       int fieldOffset0 = buf.getIntLE(offset + 20);
/* 134 */       int pos0 = offset + 44 + fieldOffset0;
/* 135 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 136 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 138 */     if ((nullBits & 0x2) != 0) {
/* 139 */       int fieldOffset1 = buf.getIntLE(offset + 24);
/* 140 */       int pos1 = offset + 44 + fieldOffset1;
/* 141 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 142 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 143 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 145 */     if ((nullBits & 0x4) != 0) {
/* 146 */       int fieldOffset2 = buf.getIntLE(offset + 28);
/* 147 */       int pos2 = offset + 44 + fieldOffset2;
/* 148 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 149 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 151 */     if ((nullBits & 0x8) != 0) {
/* 152 */       int fieldOffset3 = buf.getIntLE(offset + 32);
/* 153 */       int pos3 = offset + 44 + fieldOffset3;
/* 154 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 155 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 157 */     if ((nullBits & 0x10) != 0) {
/* 158 */       int fieldOffset4 = buf.getIntLE(offset + 36);
/* 159 */       int pos4 = offset + 44 + fieldOffset4;
/* 160 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 161 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 163 */     if ((nullBits & 0x20) != 0) {
/* 164 */       int fieldOffset5 = buf.getIntLE(offset + 40);
/* 165 */       int pos5 = offset + 44 + fieldOffset5;
/* 166 */       int arrLen = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5);
/* 167 */       for (int i = 0; i < arrLen; ) { pos5 += BlockMatcher.computeBytesConsumed(buf, pos5); i++; }
/* 168 */        if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 170 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 176 */     int startPos = buf.writerIndex();
/* 177 */     byte nullBits = 0;
/* 178 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 179 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 180 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 181 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 182 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 183 */     if (this.matchers != null) nullBits = (byte)(nullBits | 0x20); 
/* 184 */     buf.writeByte(nullBits);
/*     */     
/* 186 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 187 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 188 */     buf.writeFloatLE(this.runTime);
/* 189 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 190 */     buf.writeIntLE(this.next);
/* 191 */     buf.writeIntLE(this.failed);
/* 192 */     buf.writeByte(this.useLatestTarget ? 1 : 0);
/*     */     
/* 194 */     int effectsOffsetSlot = buf.writerIndex();
/* 195 */     buf.writeIntLE(0);
/* 196 */     int settingsOffsetSlot = buf.writerIndex();
/* 197 */     buf.writeIntLE(0);
/* 198 */     int rulesOffsetSlot = buf.writerIndex();
/* 199 */     buf.writeIntLE(0);
/* 200 */     int tagsOffsetSlot = buf.writerIndex();
/* 201 */     buf.writeIntLE(0);
/* 202 */     int cameraOffsetSlot = buf.writerIndex();
/* 203 */     buf.writeIntLE(0);
/* 204 */     int matchersOffsetSlot = buf.writerIndex();
/* 205 */     buf.writeIntLE(0);
/*     */     
/* 207 */     int varBlockStart = buf.writerIndex();
/* 208 */     if (this.effects != null) {
/* 209 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 210 */       this.effects.serialize(buf);
/*     */     } else {
/* 212 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 214 */     if (this.settings != null)
/* 215 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 216 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 218 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 220 */     if (this.rules != null) {
/* 221 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 222 */       this.rules.serialize(buf);
/*     */     } else {
/* 224 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 226 */     if (this.tags != null) {
/* 227 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 228 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 230 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 232 */     if (this.camera != null) {
/* 233 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 234 */       this.camera.serialize(buf);
/*     */     } else {
/* 236 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 238 */     if (this.matchers != null) {
/* 239 */       buf.setIntLE(matchersOffsetSlot, buf.writerIndex() - varBlockStart);
/* 240 */       if (this.matchers.length > 4096000) throw ProtocolException.arrayTooLong("Matchers", this.matchers.length, 4096000);  VarInt.write(buf, this.matchers.length); for (BlockMatcher item : this.matchers) item.serialize(buf); 
/*     */     } else {
/* 242 */       buf.setIntLE(matchersOffsetSlot, -1);
/*     */     } 
/* 244 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 250 */     int size = 44;
/* 251 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 252 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 253 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 254 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 255 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 256 */     if (this.matchers != null) {
/* 257 */       int matchersSize = 0;
/* 258 */       for (BlockMatcher elem : this.matchers) matchersSize += elem.computeSize(); 
/* 259 */       size += VarInt.size(this.matchers.length) + matchersSize;
/*     */     } 
/*     */     
/* 262 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 266 */     if (buffer.readableBytes() - offset < 44) {
/* 267 */       return ValidationResult.error("Buffer too small: expected at least 44 bytes");
/*     */     }
/*     */     
/* 270 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 273 */     if ((nullBits & 0x1) != 0) {
/* 274 */       int effectsOffset = buffer.getIntLE(offset + 20);
/* 275 */       if (effectsOffset < 0) {
/* 276 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 278 */       int pos = offset + 44 + effectsOffset;
/* 279 */       if (pos >= buffer.writerIndex()) {
/* 280 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 282 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 283 */       if (!effectsResult.isValid()) {
/* 284 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 286 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 289 */     if ((nullBits & 0x2) != 0) {
/* 290 */       int settingsOffset = buffer.getIntLE(offset + 24);
/* 291 */       if (settingsOffset < 0) {
/* 292 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 294 */       int pos = offset + 44 + settingsOffset;
/* 295 */       if (pos >= buffer.writerIndex()) {
/* 296 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 298 */       int settingsCount = VarInt.peek(buffer, pos);
/* 299 */       if (settingsCount < 0) {
/* 300 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 302 */       if (settingsCount > 4096000) {
/* 303 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 305 */       pos += VarInt.length(buffer, pos);
/* 306 */       for (int i = 0; i < settingsCount; i++) {
/* 307 */         pos++;
/*     */         
/* 309 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 314 */     if ((nullBits & 0x4) != 0) {
/* 315 */       int rulesOffset = buffer.getIntLE(offset + 28);
/* 316 */       if (rulesOffset < 0) {
/* 317 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 319 */       int pos = offset + 44 + rulesOffset;
/* 320 */       if (pos >= buffer.writerIndex()) {
/* 321 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 323 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 324 */       if (!rulesResult.isValid()) {
/* 325 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 327 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 330 */     if ((nullBits & 0x8) != 0) {
/* 331 */       int tagsOffset = buffer.getIntLE(offset + 32);
/* 332 */       if (tagsOffset < 0) {
/* 333 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 335 */       int pos = offset + 44 + tagsOffset;
/* 336 */       if (pos >= buffer.writerIndex()) {
/* 337 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 339 */       int tagsCount = VarInt.peek(buffer, pos);
/* 340 */       if (tagsCount < 0) {
/* 341 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 343 */       if (tagsCount > 4096000) {
/* 344 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 346 */       pos += VarInt.length(buffer, pos);
/* 347 */       pos += tagsCount * 4;
/* 348 */       if (pos > buffer.writerIndex()) {
/* 349 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 353 */     if ((nullBits & 0x10) != 0) {
/* 354 */       int cameraOffset = buffer.getIntLE(offset + 36);
/* 355 */       if (cameraOffset < 0) {
/* 356 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 358 */       int pos = offset + 44 + cameraOffset;
/* 359 */       if (pos >= buffer.writerIndex()) {
/* 360 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 362 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 363 */       if (!cameraResult.isValid()) {
/* 364 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 366 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 369 */     if ((nullBits & 0x20) != 0) {
/* 370 */       int matchersOffset = buffer.getIntLE(offset + 40);
/* 371 */       if (matchersOffset < 0) {
/* 372 */         return ValidationResult.error("Invalid offset for Matchers");
/*     */       }
/* 374 */       int pos = offset + 44 + matchersOffset;
/* 375 */       if (pos >= buffer.writerIndex()) {
/* 376 */         return ValidationResult.error("Offset out of bounds for Matchers");
/*     */       }
/* 378 */       int matchersCount = VarInt.peek(buffer, pos);
/* 379 */       if (matchersCount < 0) {
/* 380 */         return ValidationResult.error("Invalid array count for Matchers");
/*     */       }
/* 382 */       if (matchersCount > 4096000) {
/* 383 */         return ValidationResult.error("Matchers exceeds max length 4096000");
/*     */       }
/* 385 */       pos += VarInt.length(buffer, pos);
/* 386 */       for (int i = 0; i < matchersCount; i++) {
/* 387 */         ValidationResult structResult = BlockMatcher.validateStructure(buffer, pos);
/* 388 */         if (!structResult.isValid()) {
/* 389 */           return ValidationResult.error("Invalid BlockMatcher in Matchers[" + i + "]: " + structResult.error());
/*     */         }
/* 391 */         pos += BlockMatcher.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 394 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BlockConditionInteraction clone() {
/* 398 */     BlockConditionInteraction copy = new BlockConditionInteraction();
/* 399 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 400 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 401 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 402 */     copy.runTime = this.runTime;
/* 403 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 404 */     if (this.settings != null) {
/* 405 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 406 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 407 */       copy.settings = m;
/*     */     } 
/* 409 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 410 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 411 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 412 */     copy.next = this.next;
/* 413 */     copy.failed = this.failed;
/* 414 */     copy.useLatestTarget = this.useLatestTarget;
/* 415 */     copy.matchers = (this.matchers != null) ? (BlockMatcher[])Arrays.<BlockMatcher>stream(this.matchers).map(e -> e.clone()).toArray(x$0 -> new BlockMatcher[x$0]) : null;
/* 416 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BlockConditionInteraction other;
/* 422 */     if (this == obj) return true; 
/* 423 */     if (obj instanceof BlockConditionInteraction) { other = (BlockConditionInteraction)obj; } else { return false; }
/* 424 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && this.useLatestTarget == other.useLatestTarget && Arrays.equals((Object[])this.matchers, (Object[])other.matchers));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 429 */     int result = 1;
/* 430 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 431 */     result = 31 * result + Objects.hashCode(this.effects);
/* 432 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 433 */     result = 31 * result + Float.hashCode(this.runTime);
/* 434 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 435 */     result = 31 * result + Objects.hashCode(this.settings);
/* 436 */     result = 31 * result + Objects.hashCode(this.rules);
/* 437 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 438 */     result = 31 * result + Objects.hashCode(this.camera);
/* 439 */     result = 31 * result + Integer.hashCode(this.next);
/* 440 */     result = 31 * result + Integer.hashCode(this.failed);
/* 441 */     result = 31 * result + Boolean.hashCode(this.useLatestTarget);
/* 442 */     result = 31 * result + Arrays.hashCode((Object[])this.matchers);
/* 443 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BlockConditionInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */