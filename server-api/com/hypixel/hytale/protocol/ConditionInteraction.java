/*     */ package com.hypixel.hytale.protocol;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConditionInteraction
/*     */   extends SimpleInteraction
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 2;
/*     */   public static final int FIXED_BLOCK_SIZE = 26;
/*     */   public static final int VARIABLE_FIELD_COUNT = 5;
/*     */   public static final int VARIABLE_BLOCK_START = 46;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public GameMode requiredGameMode;
/*     */   
/*     */   public ConditionInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, @Nullable GameMode requiredGameMode, @Nullable Boolean jumping, @Nullable Boolean swimming, @Nullable Boolean crouching, @Nullable Boolean running, @Nullable Boolean flying) {
/*  31 */     this.waitForDataFrom = waitForDataFrom;
/*  32 */     this.effects = effects;
/*  33 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  34 */     this.runTime = runTime;
/*  35 */     this.cancelOnItemChange = cancelOnItemChange;
/*  36 */     this.settings = settings;
/*  37 */     this.rules = rules;
/*  38 */     this.tags = tags;
/*  39 */     this.camera = camera;
/*  40 */     this.next = next;
/*  41 */     this.failed = failed;
/*  42 */     this.requiredGameMode = requiredGameMode;
/*  43 */     this.jumping = jumping;
/*  44 */     this.swimming = swimming;
/*  45 */     this.crouching = crouching;
/*  46 */     this.running = running;
/*  47 */     this.flying = flying; } @Nullable public Boolean jumping; @Nullable
/*     */   public Boolean swimming; @Nullable
/*     */   public Boolean crouching; @Nullable
/*     */   public Boolean running; @Nullable
/*  51 */   public Boolean flying; public ConditionInteraction() {} public ConditionInteraction(@Nonnull ConditionInteraction other) { this.waitForDataFrom = other.waitForDataFrom;
/*  52 */     this.effects = other.effects;
/*  53 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  54 */     this.runTime = other.runTime;
/*  55 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  56 */     this.settings = other.settings;
/*  57 */     this.rules = other.rules;
/*  58 */     this.tags = other.tags;
/*  59 */     this.camera = other.camera;
/*  60 */     this.next = other.next;
/*  61 */     this.failed = other.failed;
/*  62 */     this.requiredGameMode = other.requiredGameMode;
/*  63 */     this.jumping = other.jumping;
/*  64 */     this.swimming = other.swimming;
/*  65 */     this.crouching = other.crouching;
/*  66 */     this.running = other.running;
/*  67 */     this.flying = other.flying; }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ConditionInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  72 */     ConditionInteraction obj = new ConditionInteraction();
/*  73 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/*  74 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 2));
/*  75 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 3);
/*  76 */     obj.runTime = buf.getFloatLE(offset + 7);
/*  77 */     obj.cancelOnItemChange = (buf.getByte(offset + 11) != 0);
/*  78 */     obj.next = buf.getIntLE(offset + 12);
/*  79 */     obj.failed = buf.getIntLE(offset + 16);
/*  80 */     if ((nullBits[0] & 0x20) != 0) obj.requiredGameMode = GameMode.fromValue(buf.getByte(offset + 20)); 
/*  81 */     if ((nullBits[0] & 0x40) != 0) obj.jumping = Boolean.valueOf((buf.getByte(offset + 21) != 0)); 
/*  82 */     if ((nullBits[0] & 0x80) != 0) obj.swimming = Boolean.valueOf((buf.getByte(offset + 22) != 0)); 
/*  83 */     if ((nullBits[1] & 0x1) != 0) obj.crouching = Boolean.valueOf((buf.getByte(offset + 23) != 0)); 
/*  84 */     if ((nullBits[1] & 0x2) != 0) obj.running = Boolean.valueOf((buf.getByte(offset + 24) != 0)); 
/*  85 */     if ((nullBits[1] & 0x4) != 0) obj.flying = Boolean.valueOf((buf.getByte(offset + 25) != 0));
/*     */     
/*  87 */     if ((nullBits[0] & 0x1) != 0) {
/*  88 */       int varPos0 = offset + 46 + buf.getIntLE(offset + 26);
/*  89 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  91 */     if ((nullBits[0] & 0x2) != 0) {
/*  92 */       int varPos1 = offset + 46 + buf.getIntLE(offset + 30);
/*  93 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  94 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  95 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  96 */       int varIntLen = VarInt.length(buf, varPos1);
/*  97 */       obj.settings = new HashMap<>(settingsCount);
/*  98 */       int dictPos = varPos1 + varIntLen;
/*  99 */       for (int i = 0; i < settingsCount; i++) {
/* 100 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/* 101 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/* 102 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/* 103 */         if (obj.settings.put(key, val) != null)
/* 104 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/* 107 */     if ((nullBits[0] & 0x4) != 0) {
/* 108 */       int varPos2 = offset + 46 + buf.getIntLE(offset + 34);
/* 109 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/* 111 */     if ((nullBits[0] & 0x8) != 0) {
/* 112 */       int varPos3 = offset + 46 + buf.getIntLE(offset + 38);
/* 113 */       int tagsCount = VarInt.peek(buf, varPos3);
/* 114 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/* 115 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/* 116 */       int varIntLen = VarInt.length(buf, varPos3);
/* 117 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/* 118 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/* 119 */       obj.tags = new int[tagsCount];
/* 120 */       for (int i = 0; i < tagsCount; i++) {
/* 121 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 124 */     if ((nullBits[0] & 0x10) != 0) {
/* 125 */       int varPos4 = offset + 46 + buf.getIntLE(offset + 42);
/* 126 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/*     */     
/* 129 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 133 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/* 134 */     int maxEnd = 46;
/* 135 */     if ((nullBits[0] & 0x1) != 0) {
/* 136 */       int fieldOffset0 = buf.getIntLE(offset + 26);
/* 137 */       int pos0 = offset + 46 + fieldOffset0;
/* 138 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 139 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 141 */     if ((nullBits[0] & 0x2) != 0) {
/* 142 */       int fieldOffset1 = buf.getIntLE(offset + 30);
/* 143 */       int pos1 = offset + 46 + fieldOffset1;
/* 144 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 145 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 146 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 148 */     if ((nullBits[0] & 0x4) != 0) {
/* 149 */       int fieldOffset2 = buf.getIntLE(offset + 34);
/* 150 */       int pos2 = offset + 46 + fieldOffset2;
/* 151 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 152 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 154 */     if ((nullBits[0] & 0x8) != 0) {
/* 155 */       int fieldOffset3 = buf.getIntLE(offset + 38);
/* 156 */       int pos3 = offset + 46 + fieldOffset3;
/* 157 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 158 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 160 */     if ((nullBits[0] & 0x10) != 0) {
/* 161 */       int fieldOffset4 = buf.getIntLE(offset + 42);
/* 162 */       int pos4 = offset + 46 + fieldOffset4;
/* 163 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 164 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 166 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 172 */     int startPos = buf.writerIndex();
/* 173 */     byte[] nullBits = new byte[2];
/* 174 */     if (this.effects != null) nullBits[0] = (byte)(nullBits[0] | 0x1); 
/* 175 */     if (this.settings != null) nullBits[0] = (byte)(nullBits[0] | 0x2); 
/* 176 */     if (this.rules != null) nullBits[0] = (byte)(nullBits[0] | 0x4); 
/* 177 */     if (this.tags != null) nullBits[0] = (byte)(nullBits[0] | 0x8); 
/* 178 */     if (this.camera != null) nullBits[0] = (byte)(nullBits[0] | 0x10); 
/* 179 */     if (this.requiredGameMode != null) nullBits[0] = (byte)(nullBits[0] | 0x20); 
/* 180 */     if (this.jumping != null) nullBits[0] = (byte)(nullBits[0] | 0x40); 
/* 181 */     if (this.swimming != null) nullBits[0] = (byte)(nullBits[0] | 0x80); 
/* 182 */     if (this.crouching != null) nullBits[1] = (byte)(nullBits[1] | 0x1); 
/* 183 */     if (this.running != null) nullBits[1] = (byte)(nullBits[1] | 0x2); 
/* 184 */     if (this.flying != null) nullBits[1] = (byte)(nullBits[1] | 0x4); 
/* 185 */     buf.writeBytes(nullBits);
/*     */     
/* 187 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 188 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 189 */     buf.writeFloatLE(this.runTime);
/* 190 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 191 */     buf.writeIntLE(this.next);
/* 192 */     buf.writeIntLE(this.failed);
/* 193 */     if (this.requiredGameMode != null) { buf.writeByte(this.requiredGameMode.getValue()); } else { buf.writeZero(1); }
/* 194 */      if (this.jumping != null) { buf.writeByte(this.jumping.booleanValue() ? 1 : 0); } else { buf.writeZero(1); }
/* 195 */      if (this.swimming != null) { buf.writeByte(this.swimming.booleanValue() ? 1 : 0); } else { buf.writeZero(1); }
/* 196 */      if (this.crouching != null) { buf.writeByte(this.crouching.booleanValue() ? 1 : 0); } else { buf.writeZero(1); }
/* 197 */      if (this.running != null) { buf.writeByte(this.running.booleanValue() ? 1 : 0); } else { buf.writeZero(1); }
/* 198 */      if (this.flying != null) { buf.writeByte(this.flying.booleanValue() ? 1 : 0); } else { buf.writeZero(1); }
/*     */     
/* 200 */     int effectsOffsetSlot = buf.writerIndex();
/* 201 */     buf.writeIntLE(0);
/* 202 */     int settingsOffsetSlot = buf.writerIndex();
/* 203 */     buf.writeIntLE(0);
/* 204 */     int rulesOffsetSlot = buf.writerIndex();
/* 205 */     buf.writeIntLE(0);
/* 206 */     int tagsOffsetSlot = buf.writerIndex();
/* 207 */     buf.writeIntLE(0);
/* 208 */     int cameraOffsetSlot = buf.writerIndex();
/* 209 */     buf.writeIntLE(0);
/*     */     
/* 211 */     int varBlockStart = buf.writerIndex();
/* 212 */     if (this.effects != null) {
/* 213 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 214 */       this.effects.serialize(buf);
/*     */     } else {
/* 216 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 218 */     if (this.settings != null)
/* 219 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 220 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 222 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 224 */     if (this.rules != null) {
/* 225 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 226 */       this.rules.serialize(buf);
/*     */     } else {
/* 228 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 230 */     if (this.tags != null) {
/* 231 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 232 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 234 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 236 */     if (this.camera != null) {
/* 237 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 238 */       this.camera.serialize(buf);
/*     */     } else {
/* 240 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 242 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 248 */     int size = 46;
/* 249 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 250 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 251 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 252 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 253 */     if (this.camera != null) size += this.camera.computeSize();
/*     */     
/* 255 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 259 */     if (buffer.readableBytes() - offset < 46) {
/* 260 */       return ValidationResult.error("Buffer too small: expected at least 46 bytes");
/*     */     }
/*     */     
/* 263 */     byte[] nullBits = PacketIO.readBytes(buffer, offset, 2);
/*     */     
/* 265 */     if ((nullBits[0] & 0x1) != 0) {
/* 266 */       int effectsOffset = buffer.getIntLE(offset + 26);
/* 267 */       if (effectsOffset < 0) {
/* 268 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 270 */       int pos = offset + 46 + effectsOffset;
/* 271 */       if (pos >= buffer.writerIndex()) {
/* 272 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 274 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 275 */       if (!effectsResult.isValid()) {
/* 276 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 278 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 281 */     if ((nullBits[0] & 0x2) != 0) {
/* 282 */       int settingsOffset = buffer.getIntLE(offset + 30);
/* 283 */       if (settingsOffset < 0) {
/* 284 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 286 */       int pos = offset + 46 + settingsOffset;
/* 287 */       if (pos >= buffer.writerIndex()) {
/* 288 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 290 */       int settingsCount = VarInt.peek(buffer, pos);
/* 291 */       if (settingsCount < 0) {
/* 292 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 294 */       if (settingsCount > 4096000) {
/* 295 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 297 */       pos += VarInt.length(buffer, pos);
/* 298 */       for (int i = 0; i < settingsCount; i++) {
/* 299 */         pos++;
/*     */         
/* 301 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 306 */     if ((nullBits[0] & 0x4) != 0) {
/* 307 */       int rulesOffset = buffer.getIntLE(offset + 34);
/* 308 */       if (rulesOffset < 0) {
/* 309 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 311 */       int pos = offset + 46 + rulesOffset;
/* 312 */       if (pos >= buffer.writerIndex()) {
/* 313 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 315 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 316 */       if (!rulesResult.isValid()) {
/* 317 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 319 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 322 */     if ((nullBits[0] & 0x8) != 0) {
/* 323 */       int tagsOffset = buffer.getIntLE(offset + 38);
/* 324 */       if (tagsOffset < 0) {
/* 325 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 327 */       int pos = offset + 46 + tagsOffset;
/* 328 */       if (pos >= buffer.writerIndex()) {
/* 329 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 331 */       int tagsCount = VarInt.peek(buffer, pos);
/* 332 */       if (tagsCount < 0) {
/* 333 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 335 */       if (tagsCount > 4096000) {
/* 336 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 338 */       pos += VarInt.length(buffer, pos);
/* 339 */       pos += tagsCount * 4;
/* 340 */       if (pos > buffer.writerIndex()) {
/* 341 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 345 */     if ((nullBits[0] & 0x10) != 0) {
/* 346 */       int cameraOffset = buffer.getIntLE(offset + 42);
/* 347 */       if (cameraOffset < 0) {
/* 348 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 350 */       int pos = offset + 46 + cameraOffset;
/* 351 */       if (pos >= buffer.writerIndex()) {
/* 352 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 354 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 355 */       if (!cameraResult.isValid()) {
/* 356 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 358 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 360 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ConditionInteraction clone() {
/* 364 */     ConditionInteraction copy = new ConditionInteraction();
/* 365 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 366 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 367 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 368 */     copy.runTime = this.runTime;
/* 369 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 370 */     if (this.settings != null) {
/* 371 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 372 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 373 */       copy.settings = m;
/*     */     } 
/* 375 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 376 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 377 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 378 */     copy.next = this.next;
/* 379 */     copy.failed = this.failed;
/* 380 */     copy.requiredGameMode = this.requiredGameMode;
/* 381 */     copy.jumping = this.jumping;
/* 382 */     copy.swimming = this.swimming;
/* 383 */     copy.crouching = this.crouching;
/* 384 */     copy.running = this.running;
/* 385 */     copy.flying = this.flying;
/* 386 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ConditionInteraction other;
/* 392 */     if (this == obj) return true; 
/* 393 */     if (obj instanceof ConditionInteraction) { other = (ConditionInteraction)obj; } else { return false; }
/* 394 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && Objects.equals(this.requiredGameMode, other.requiredGameMode) && Objects.equals(this.jumping, other.jumping) && Objects.equals(this.swimming, other.swimming) && Objects.equals(this.crouching, other.crouching) && Objects.equals(this.running, other.running) && Objects.equals(this.flying, other.flying));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 399 */     int result = 1;
/* 400 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 401 */     result = 31 * result + Objects.hashCode(this.effects);
/* 402 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 403 */     result = 31 * result + Float.hashCode(this.runTime);
/* 404 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 405 */     result = 31 * result + Objects.hashCode(this.settings);
/* 406 */     result = 31 * result + Objects.hashCode(this.rules);
/* 407 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 408 */     result = 31 * result + Objects.hashCode(this.camera);
/* 409 */     result = 31 * result + Integer.hashCode(this.next);
/* 410 */     result = 31 * result + Integer.hashCode(this.failed);
/* 411 */     result = 31 * result + Objects.hashCode(this.requiredGameMode);
/* 412 */     result = 31 * result + Objects.hashCode(this.jumping);
/* 413 */     result = 31 * result + Objects.hashCode(this.swimming);
/* 414 */     result = 31 * result + Objects.hashCode(this.crouching);
/* 415 */     result = 31 * result + Objects.hashCode(this.running);
/* 416 */     result = 31 * result + Objects.hashCode(this.flying);
/* 417 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ConditionInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */