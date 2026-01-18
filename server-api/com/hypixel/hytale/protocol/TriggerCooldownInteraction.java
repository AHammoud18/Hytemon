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
/*     */ public class TriggerCooldownInteraction extends SimpleInteraction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 19;
/*     */   public static final int VARIABLE_FIELD_COUNT = 6;
/*     */   public static final int VARIABLE_BLOCK_START = 43;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public InteractionCooldown cooldown;
/*     */   
/*     */   public TriggerCooldownInteraction() {}
/*     */   
/*     */   public TriggerCooldownInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, @Nullable InteractionCooldown cooldown) {
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
/*  37 */     this.cooldown = cooldown;
/*     */   }
/*     */   
/*     */   public TriggerCooldownInteraction(@Nonnull TriggerCooldownInteraction other) {
/*  41 */     this.waitForDataFrom = other.waitForDataFrom;
/*  42 */     this.effects = other.effects;
/*  43 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  44 */     this.runTime = other.runTime;
/*  45 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  46 */     this.settings = other.settings;
/*  47 */     this.rules = other.rules;
/*  48 */     this.tags = other.tags;
/*  49 */     this.camera = other.camera;
/*  50 */     this.next = other.next;
/*  51 */     this.failed = other.failed;
/*  52 */     this.cooldown = other.cooldown;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static TriggerCooldownInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  57 */     TriggerCooldownInteraction obj = new TriggerCooldownInteraction();
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  60 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  61 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  62 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  63 */     obj.next = buf.getIntLE(offset + 11);
/*  64 */     obj.failed = buf.getIntLE(offset + 15);
/*     */     
/*  66 */     if ((nullBits & 0x1) != 0) {
/*  67 */       int varPos0 = offset + 43 + buf.getIntLE(offset + 19);
/*  68 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  70 */     if ((nullBits & 0x2) != 0) {
/*  71 */       int varPos1 = offset + 43 + buf.getIntLE(offset + 23);
/*  72 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  73 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  74 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  75 */       int varIntLen = VarInt.length(buf, varPos1);
/*  76 */       obj.settings = new HashMap<>(settingsCount);
/*  77 */       int dictPos = varPos1 + varIntLen;
/*  78 */       for (int i = 0; i < settingsCount; i++) {
/*  79 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  80 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/*  81 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/*  82 */         if (obj.settings.put(key, val) != null)
/*  83 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/*  86 */     if ((nullBits & 0x4) != 0) {
/*  87 */       int varPos2 = offset + 43 + buf.getIntLE(offset + 27);
/*  88 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/*  90 */     if ((nullBits & 0x8) != 0) {
/*  91 */       int varPos3 = offset + 43 + buf.getIntLE(offset + 31);
/*  92 */       int tagsCount = VarInt.peek(buf, varPos3);
/*  93 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/*  94 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/*  95 */       int varIntLen = VarInt.length(buf, varPos3);
/*  96 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/*  97 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/*  98 */       obj.tags = new int[tagsCount];
/*  99 */       for (int i = 0; i < tagsCount; i++) {
/* 100 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 103 */     if ((nullBits & 0x10) != 0) {
/* 104 */       int varPos4 = offset + 43 + buf.getIntLE(offset + 35);
/* 105 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/* 107 */     if ((nullBits & 0x20) != 0) {
/* 108 */       int varPos5 = offset + 43 + buf.getIntLE(offset + 39);
/* 109 */       obj.cooldown = InteractionCooldown.deserialize(buf, varPos5);
/*     */     } 
/*     */     
/* 112 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 116 */     byte nullBits = buf.getByte(offset);
/* 117 */     int maxEnd = 43;
/* 118 */     if ((nullBits & 0x1) != 0) {
/* 119 */       int fieldOffset0 = buf.getIntLE(offset + 19);
/* 120 */       int pos0 = offset + 43 + fieldOffset0;
/* 121 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 122 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 124 */     if ((nullBits & 0x2) != 0) {
/* 125 */       int fieldOffset1 = buf.getIntLE(offset + 23);
/* 126 */       int pos1 = offset + 43 + fieldOffset1;
/* 127 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 128 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 129 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 131 */     if ((nullBits & 0x4) != 0) {
/* 132 */       int fieldOffset2 = buf.getIntLE(offset + 27);
/* 133 */       int pos2 = offset + 43 + fieldOffset2;
/* 134 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 135 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 137 */     if ((nullBits & 0x8) != 0) {
/* 138 */       int fieldOffset3 = buf.getIntLE(offset + 31);
/* 139 */       int pos3 = offset + 43 + fieldOffset3;
/* 140 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 141 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 143 */     if ((nullBits & 0x10) != 0) {
/* 144 */       int fieldOffset4 = buf.getIntLE(offset + 35);
/* 145 */       int pos4 = offset + 43 + fieldOffset4;
/* 146 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 147 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 149 */     if ((nullBits & 0x20) != 0) {
/* 150 */       int fieldOffset5 = buf.getIntLE(offset + 39);
/* 151 */       int pos5 = offset + 43 + fieldOffset5;
/* 152 */       pos5 += InteractionCooldown.computeBytesConsumed(buf, pos5);
/* 153 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 155 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 161 */     int startPos = buf.writerIndex();
/* 162 */     byte nullBits = 0;
/* 163 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 164 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 165 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 166 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 167 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 168 */     if (this.cooldown != null) nullBits = (byte)(nullBits | 0x20); 
/* 169 */     buf.writeByte(nullBits);
/*     */     
/* 171 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 172 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 173 */     buf.writeFloatLE(this.runTime);
/* 174 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 175 */     buf.writeIntLE(this.next);
/* 176 */     buf.writeIntLE(this.failed);
/*     */     
/* 178 */     int effectsOffsetSlot = buf.writerIndex();
/* 179 */     buf.writeIntLE(0);
/* 180 */     int settingsOffsetSlot = buf.writerIndex();
/* 181 */     buf.writeIntLE(0);
/* 182 */     int rulesOffsetSlot = buf.writerIndex();
/* 183 */     buf.writeIntLE(0);
/* 184 */     int tagsOffsetSlot = buf.writerIndex();
/* 185 */     buf.writeIntLE(0);
/* 186 */     int cameraOffsetSlot = buf.writerIndex();
/* 187 */     buf.writeIntLE(0);
/* 188 */     int cooldownOffsetSlot = buf.writerIndex();
/* 189 */     buf.writeIntLE(0);
/*     */     
/* 191 */     int varBlockStart = buf.writerIndex();
/* 192 */     if (this.effects != null) {
/* 193 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 194 */       this.effects.serialize(buf);
/*     */     } else {
/* 196 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 198 */     if (this.settings != null)
/* 199 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 200 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 202 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 204 */     if (this.rules != null) {
/* 205 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 206 */       this.rules.serialize(buf);
/*     */     } else {
/* 208 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 210 */     if (this.tags != null) {
/* 211 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 212 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 214 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 216 */     if (this.camera != null) {
/* 217 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 218 */       this.camera.serialize(buf);
/*     */     } else {
/* 220 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 222 */     if (this.cooldown != null) {
/* 223 */       buf.setIntLE(cooldownOffsetSlot, buf.writerIndex() - varBlockStart);
/* 224 */       this.cooldown.serialize(buf);
/*     */     } else {
/* 226 */       buf.setIntLE(cooldownOffsetSlot, -1);
/*     */     } 
/* 228 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 234 */     int size = 43;
/* 235 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 236 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 237 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 238 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 239 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 240 */     if (this.cooldown != null) size += this.cooldown.computeSize();
/*     */     
/* 242 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 246 */     if (buffer.readableBytes() - offset < 43) {
/* 247 */       return ValidationResult.error("Buffer too small: expected at least 43 bytes");
/*     */     }
/*     */     
/* 250 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 253 */     if ((nullBits & 0x1) != 0) {
/* 254 */       int effectsOffset = buffer.getIntLE(offset + 19);
/* 255 */       if (effectsOffset < 0) {
/* 256 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 258 */       int pos = offset + 43 + effectsOffset;
/* 259 */       if (pos >= buffer.writerIndex()) {
/* 260 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 262 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 263 */       if (!effectsResult.isValid()) {
/* 264 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 266 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 269 */     if ((nullBits & 0x2) != 0) {
/* 270 */       int settingsOffset = buffer.getIntLE(offset + 23);
/* 271 */       if (settingsOffset < 0) {
/* 272 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 274 */       int pos = offset + 43 + settingsOffset;
/* 275 */       if (pos >= buffer.writerIndex()) {
/* 276 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 278 */       int settingsCount = VarInt.peek(buffer, pos);
/* 279 */       if (settingsCount < 0) {
/* 280 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 282 */       if (settingsCount > 4096000) {
/* 283 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 285 */       pos += VarInt.length(buffer, pos);
/* 286 */       for (int i = 0; i < settingsCount; i++) {
/* 287 */         pos++;
/*     */         
/* 289 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 294 */     if ((nullBits & 0x4) != 0) {
/* 295 */       int rulesOffset = buffer.getIntLE(offset + 27);
/* 296 */       if (rulesOffset < 0) {
/* 297 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 299 */       int pos = offset + 43 + rulesOffset;
/* 300 */       if (pos >= buffer.writerIndex()) {
/* 301 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 303 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 304 */       if (!rulesResult.isValid()) {
/* 305 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 307 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 310 */     if ((nullBits & 0x8) != 0) {
/* 311 */       int tagsOffset = buffer.getIntLE(offset + 31);
/* 312 */       if (tagsOffset < 0) {
/* 313 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 315 */       int pos = offset + 43 + tagsOffset;
/* 316 */       if (pos >= buffer.writerIndex()) {
/* 317 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 319 */       int tagsCount = VarInt.peek(buffer, pos);
/* 320 */       if (tagsCount < 0) {
/* 321 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 323 */       if (tagsCount > 4096000) {
/* 324 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 326 */       pos += VarInt.length(buffer, pos);
/* 327 */       pos += tagsCount * 4;
/* 328 */       if (pos > buffer.writerIndex()) {
/* 329 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 333 */     if ((nullBits & 0x10) != 0) {
/* 334 */       int cameraOffset = buffer.getIntLE(offset + 35);
/* 335 */       if (cameraOffset < 0) {
/* 336 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 338 */       int pos = offset + 43 + cameraOffset;
/* 339 */       if (pos >= buffer.writerIndex()) {
/* 340 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 342 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 343 */       if (!cameraResult.isValid()) {
/* 344 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 346 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 349 */     if ((nullBits & 0x20) != 0) {
/* 350 */       int cooldownOffset = buffer.getIntLE(offset + 39);
/* 351 */       if (cooldownOffset < 0) {
/* 352 */         return ValidationResult.error("Invalid offset for Cooldown");
/*     */       }
/* 354 */       int pos = offset + 43 + cooldownOffset;
/* 355 */       if (pos >= buffer.writerIndex()) {
/* 356 */         return ValidationResult.error("Offset out of bounds for Cooldown");
/*     */       }
/* 358 */       ValidationResult cooldownResult = InteractionCooldown.validateStructure(buffer, pos);
/* 359 */       if (!cooldownResult.isValid()) {
/* 360 */         return ValidationResult.error("Invalid Cooldown: " + cooldownResult.error());
/*     */       }
/* 362 */       pos += InteractionCooldown.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 364 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public TriggerCooldownInteraction clone() {
/* 368 */     TriggerCooldownInteraction copy = new TriggerCooldownInteraction();
/* 369 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 370 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 371 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 372 */     copy.runTime = this.runTime;
/* 373 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 374 */     if (this.settings != null) {
/* 375 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 376 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 377 */       copy.settings = m;
/*     */     } 
/* 379 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 380 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 381 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 382 */     copy.next = this.next;
/* 383 */     copy.failed = this.failed;
/* 384 */     copy.cooldown = (this.cooldown != null) ? this.cooldown.clone() : null;
/* 385 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     TriggerCooldownInteraction other;
/* 391 */     if (this == obj) return true; 
/* 392 */     if (obj instanceof TriggerCooldownInteraction) { other = (TriggerCooldownInteraction)obj; } else { return false; }
/* 393 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && Objects.equals(this.cooldown, other.cooldown));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 398 */     int result = 1;
/* 399 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 400 */     result = 31 * result + Objects.hashCode(this.effects);
/* 401 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 402 */     result = 31 * result + Float.hashCode(this.runTime);
/* 403 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 404 */     result = 31 * result + Objects.hashCode(this.settings);
/* 405 */     result = 31 * result + Objects.hashCode(this.rules);
/* 406 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 407 */     result = 31 * result + Objects.hashCode(this.camera);
/* 408 */     result = 31 * result + Integer.hashCode(this.next);
/* 409 */     result = 31 * result + Integer.hashCode(this.failed);
/* 410 */     result = 31 * result + Objects.hashCode(this.cooldown);
/* 411 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\TriggerCooldownInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */