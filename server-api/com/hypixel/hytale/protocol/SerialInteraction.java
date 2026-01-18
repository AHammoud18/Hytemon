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
/*     */ public class SerialInteraction extends Interaction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 11;
/*     */   public static final int VARIABLE_FIELD_COUNT = 6;
/*     */   public static final int VARIABLE_BLOCK_START = 35;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public int[] serialInteractions;
/*     */   
/*     */   public SerialInteraction() {}
/*     */   
/*     */   public SerialInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, @Nullable int[] serialInteractions) {
/*  26 */     this.waitForDataFrom = waitForDataFrom;
/*  27 */     this.effects = effects;
/*  28 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  29 */     this.runTime = runTime;
/*  30 */     this.cancelOnItemChange = cancelOnItemChange;
/*  31 */     this.settings = settings;
/*  32 */     this.rules = rules;
/*  33 */     this.tags = tags;
/*  34 */     this.camera = camera;
/*  35 */     this.serialInteractions = serialInteractions;
/*     */   }
/*     */   
/*     */   public SerialInteraction(@Nonnull SerialInteraction other) {
/*  39 */     this.waitForDataFrom = other.waitForDataFrom;
/*  40 */     this.effects = other.effects;
/*  41 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  42 */     this.runTime = other.runTime;
/*  43 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  44 */     this.settings = other.settings;
/*  45 */     this.rules = other.rules;
/*  46 */     this.tags = other.tags;
/*  47 */     this.camera = other.camera;
/*  48 */     this.serialInteractions = other.serialInteractions;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SerialInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  53 */     SerialInteraction obj = new SerialInteraction();
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  56 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  57 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  58 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*     */     
/*  60 */     if ((nullBits & 0x1) != 0) {
/*  61 */       int varPos0 = offset + 35 + buf.getIntLE(offset + 11);
/*  62 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  64 */     if ((nullBits & 0x2) != 0) {
/*  65 */       int varPos1 = offset + 35 + buf.getIntLE(offset + 15);
/*  66 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  67 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  68 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  69 */       int varIntLen = VarInt.length(buf, varPos1);
/*  70 */       obj.settings = new HashMap<>(settingsCount);
/*  71 */       int dictPos = varPos1 + varIntLen;
/*  72 */       for (int i = 0; i < settingsCount; i++) {
/*  73 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  74 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/*  75 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/*  76 */         if (obj.settings.put(key, val) != null)
/*  77 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/*  80 */     if ((nullBits & 0x4) != 0) {
/*  81 */       int varPos2 = offset + 35 + buf.getIntLE(offset + 19);
/*  82 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/*  84 */     if ((nullBits & 0x8) != 0) {
/*  85 */       int varPos3 = offset + 35 + buf.getIntLE(offset + 23);
/*  86 */       int tagsCount = VarInt.peek(buf, varPos3);
/*  87 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/*  88 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/*  89 */       int varIntLen = VarInt.length(buf, varPos3);
/*  90 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/*  91 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/*  92 */       obj.tags = new int[tagsCount];
/*  93 */       for (int i = 0; i < tagsCount; i++) {
/*  94 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/*  97 */     if ((nullBits & 0x10) != 0) {
/*  98 */       int varPos4 = offset + 35 + buf.getIntLE(offset + 27);
/*  99 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/* 101 */     if ((nullBits & 0x20) != 0) {
/* 102 */       int varPos5 = offset + 35 + buf.getIntLE(offset + 31);
/* 103 */       int serialInteractionsCount = VarInt.peek(buf, varPos5);
/* 104 */       if (serialInteractionsCount < 0) throw ProtocolException.negativeLength("SerialInteractions", serialInteractionsCount); 
/* 105 */       if (serialInteractionsCount > 4096000) throw ProtocolException.arrayTooLong("SerialInteractions", serialInteractionsCount, 4096000); 
/* 106 */       int varIntLen = VarInt.length(buf, varPos5);
/* 107 */       if ((varPos5 + varIntLen) + serialInteractionsCount * 4L > buf.readableBytes())
/* 108 */         throw ProtocolException.bufferTooSmall("SerialInteractions", varPos5 + varIntLen + serialInteractionsCount * 4, buf.readableBytes()); 
/* 109 */       obj.serialInteractions = new int[serialInteractionsCount];
/* 110 */       for (int i = 0; i < serialInteractionsCount; i++) {
/* 111 */         obj.serialInteractions[i] = buf.getIntLE(varPos5 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/*     */     
/* 115 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 119 */     byte nullBits = buf.getByte(offset);
/* 120 */     int maxEnd = 35;
/* 121 */     if ((nullBits & 0x1) != 0) {
/* 122 */       int fieldOffset0 = buf.getIntLE(offset + 11);
/* 123 */       int pos0 = offset + 35 + fieldOffset0;
/* 124 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 125 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 127 */     if ((nullBits & 0x2) != 0) {
/* 128 */       int fieldOffset1 = buf.getIntLE(offset + 15);
/* 129 */       int pos1 = offset + 35 + fieldOffset1;
/* 130 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 131 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 132 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 134 */     if ((nullBits & 0x4) != 0) {
/* 135 */       int fieldOffset2 = buf.getIntLE(offset + 19);
/* 136 */       int pos2 = offset + 35 + fieldOffset2;
/* 137 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 138 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 140 */     if ((nullBits & 0x8) != 0) {
/* 141 */       int fieldOffset3 = buf.getIntLE(offset + 23);
/* 142 */       int pos3 = offset + 35 + fieldOffset3;
/* 143 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 144 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 146 */     if ((nullBits & 0x10) != 0) {
/* 147 */       int fieldOffset4 = buf.getIntLE(offset + 27);
/* 148 */       int pos4 = offset + 35 + fieldOffset4;
/* 149 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 150 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 152 */     if ((nullBits & 0x20) != 0) {
/* 153 */       int fieldOffset5 = buf.getIntLE(offset + 31);
/* 154 */       int pos5 = offset + 35 + fieldOffset5;
/* 155 */       int arrLen = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5) + arrLen * 4;
/* 156 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 158 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 164 */     int startPos = buf.writerIndex();
/* 165 */     byte nullBits = 0;
/* 166 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 167 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 168 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 169 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 170 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 171 */     if (this.serialInteractions != null) nullBits = (byte)(nullBits | 0x20); 
/* 172 */     buf.writeByte(nullBits);
/*     */     
/* 174 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 175 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 176 */     buf.writeFloatLE(this.runTime);
/* 177 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/*     */     
/* 179 */     int effectsOffsetSlot = buf.writerIndex();
/* 180 */     buf.writeIntLE(0);
/* 181 */     int settingsOffsetSlot = buf.writerIndex();
/* 182 */     buf.writeIntLE(0);
/* 183 */     int rulesOffsetSlot = buf.writerIndex();
/* 184 */     buf.writeIntLE(0);
/* 185 */     int tagsOffsetSlot = buf.writerIndex();
/* 186 */     buf.writeIntLE(0);
/* 187 */     int cameraOffsetSlot = buf.writerIndex();
/* 188 */     buf.writeIntLE(0);
/* 189 */     int serialInteractionsOffsetSlot = buf.writerIndex();
/* 190 */     buf.writeIntLE(0);
/*     */     
/* 192 */     int varBlockStart = buf.writerIndex();
/* 193 */     if (this.effects != null) {
/* 194 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 195 */       this.effects.serialize(buf);
/*     */     } else {
/* 197 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 199 */     if (this.settings != null)
/* 200 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 201 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 203 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 205 */     if (this.rules != null) {
/* 206 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 207 */       this.rules.serialize(buf);
/*     */     } else {
/* 209 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 211 */     if (this.tags != null) {
/* 212 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 213 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 215 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 217 */     if (this.camera != null) {
/* 218 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 219 */       this.camera.serialize(buf);
/*     */     } else {
/* 221 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 223 */     if (this.serialInteractions != null) {
/* 224 */       buf.setIntLE(serialInteractionsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 225 */       if (this.serialInteractions.length > 4096000) throw ProtocolException.arrayTooLong("SerialInteractions", this.serialInteractions.length, 4096000);  VarInt.write(buf, this.serialInteractions.length); for (int item : this.serialInteractions) buf.writeIntLE(item); 
/*     */     } else {
/* 227 */       buf.setIntLE(serialInteractionsOffsetSlot, -1);
/*     */     } 
/* 229 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 235 */     int size = 35;
/* 236 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 237 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 238 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 239 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 240 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 241 */     if (this.serialInteractions != null) size += VarInt.size(this.serialInteractions.length) + this.serialInteractions.length * 4;
/*     */     
/* 243 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 247 */     if (buffer.readableBytes() - offset < 35) {
/* 248 */       return ValidationResult.error("Buffer too small: expected at least 35 bytes");
/*     */     }
/*     */     
/* 251 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 254 */     if ((nullBits & 0x1) != 0) {
/* 255 */       int effectsOffset = buffer.getIntLE(offset + 11);
/* 256 */       if (effectsOffset < 0) {
/* 257 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 259 */       int pos = offset + 35 + effectsOffset;
/* 260 */       if (pos >= buffer.writerIndex()) {
/* 261 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 263 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 264 */       if (!effectsResult.isValid()) {
/* 265 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 267 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 270 */     if ((nullBits & 0x2) != 0) {
/* 271 */       int settingsOffset = buffer.getIntLE(offset + 15);
/* 272 */       if (settingsOffset < 0) {
/* 273 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 275 */       int pos = offset + 35 + settingsOffset;
/* 276 */       if (pos >= buffer.writerIndex()) {
/* 277 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 279 */       int settingsCount = VarInt.peek(buffer, pos);
/* 280 */       if (settingsCount < 0) {
/* 281 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 283 */       if (settingsCount > 4096000) {
/* 284 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 286 */       pos += VarInt.length(buffer, pos);
/* 287 */       for (int i = 0; i < settingsCount; i++) {
/* 288 */         pos++;
/*     */         
/* 290 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 295 */     if ((nullBits & 0x4) != 0) {
/* 296 */       int rulesOffset = buffer.getIntLE(offset + 19);
/* 297 */       if (rulesOffset < 0) {
/* 298 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 300 */       int pos = offset + 35 + rulesOffset;
/* 301 */       if (pos >= buffer.writerIndex()) {
/* 302 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 304 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 305 */       if (!rulesResult.isValid()) {
/* 306 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 308 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 311 */     if ((nullBits & 0x8) != 0) {
/* 312 */       int tagsOffset = buffer.getIntLE(offset + 23);
/* 313 */       if (tagsOffset < 0) {
/* 314 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 316 */       int pos = offset + 35 + tagsOffset;
/* 317 */       if (pos >= buffer.writerIndex()) {
/* 318 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 320 */       int tagsCount = VarInt.peek(buffer, pos);
/* 321 */       if (tagsCount < 0) {
/* 322 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 324 */       if (tagsCount > 4096000) {
/* 325 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 327 */       pos += VarInt.length(buffer, pos);
/* 328 */       pos += tagsCount * 4;
/* 329 */       if (pos > buffer.writerIndex()) {
/* 330 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 334 */     if ((nullBits & 0x10) != 0) {
/* 335 */       int cameraOffset = buffer.getIntLE(offset + 27);
/* 336 */       if (cameraOffset < 0) {
/* 337 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 339 */       int pos = offset + 35 + cameraOffset;
/* 340 */       if (pos >= buffer.writerIndex()) {
/* 341 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 343 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 344 */       if (!cameraResult.isValid()) {
/* 345 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 347 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 350 */     if ((nullBits & 0x20) != 0) {
/* 351 */       int serialInteractionsOffset = buffer.getIntLE(offset + 31);
/* 352 */       if (serialInteractionsOffset < 0) {
/* 353 */         return ValidationResult.error("Invalid offset for SerialInteractions");
/*     */       }
/* 355 */       int pos = offset + 35 + serialInteractionsOffset;
/* 356 */       if (pos >= buffer.writerIndex()) {
/* 357 */         return ValidationResult.error("Offset out of bounds for SerialInteractions");
/*     */       }
/* 359 */       int serialInteractionsCount = VarInt.peek(buffer, pos);
/* 360 */       if (serialInteractionsCount < 0) {
/* 361 */         return ValidationResult.error("Invalid array count for SerialInteractions");
/*     */       }
/* 363 */       if (serialInteractionsCount > 4096000) {
/* 364 */         return ValidationResult.error("SerialInteractions exceeds max length 4096000");
/*     */       }
/* 366 */       pos += VarInt.length(buffer, pos);
/* 367 */       pos += serialInteractionsCount * 4;
/* 368 */       if (pos > buffer.writerIndex()) {
/* 369 */         return ValidationResult.error("Buffer overflow reading SerialInteractions");
/*     */       }
/*     */     } 
/* 372 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SerialInteraction clone() {
/* 376 */     SerialInteraction copy = new SerialInteraction();
/* 377 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 378 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 379 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 380 */     copy.runTime = this.runTime;
/* 381 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 382 */     if (this.settings != null) {
/* 383 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 384 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 385 */       copy.settings = m;
/*     */     } 
/* 387 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 388 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 389 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 390 */     copy.serialInteractions = (this.serialInteractions != null) ? Arrays.copyOf(this.serialInteractions, this.serialInteractions.length) : null;
/* 391 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SerialInteraction other;
/* 397 */     if (this == obj) return true; 
/* 398 */     if (obj instanceof SerialInteraction) { other = (SerialInteraction)obj; } else { return false; }
/* 399 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && Arrays.equals(this.serialInteractions, other.serialInteractions));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 404 */     int result = 1;
/* 405 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 406 */     result = 31 * result + Objects.hashCode(this.effects);
/* 407 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 408 */     result = 31 * result + Float.hashCode(this.runTime);
/* 409 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 410 */     result = 31 * result + Objects.hashCode(this.settings);
/* 411 */     result = 31 * result + Objects.hashCode(this.rules);
/* 412 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 413 */     result = 31 * result + Objects.hashCode(this.camera);
/* 414 */     result = 31 * result + Arrays.hashCode(this.serialInteractions);
/* 415 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\SerialInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */