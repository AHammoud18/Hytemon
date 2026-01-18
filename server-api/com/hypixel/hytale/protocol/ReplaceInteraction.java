/*     */ package com.hypixel.hytale.protocol;
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
/*     */ public class ReplaceInteraction extends Interaction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 15;
/*     */   public static final int VARIABLE_FIELD_COUNT = 6;
/*     */   public static final int VARIABLE_BLOCK_START = 39;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   public int defaultValue;
/*     */   @Nullable
/*     */   public String variable;
/*     */   
/*     */   public ReplaceInteraction() {}
/*     */   
/*     */   public ReplaceInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int defaultValue, @Nullable String variable) {
/*  27 */     this.waitForDataFrom = waitForDataFrom;
/*  28 */     this.effects = effects;
/*  29 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  30 */     this.runTime = runTime;
/*  31 */     this.cancelOnItemChange = cancelOnItemChange;
/*  32 */     this.settings = settings;
/*  33 */     this.rules = rules;
/*  34 */     this.tags = tags;
/*  35 */     this.camera = camera;
/*  36 */     this.defaultValue = defaultValue;
/*  37 */     this.variable = variable;
/*     */   }
/*     */   
/*     */   public ReplaceInteraction(@Nonnull ReplaceInteraction other) {
/*  41 */     this.waitForDataFrom = other.waitForDataFrom;
/*  42 */     this.effects = other.effects;
/*  43 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  44 */     this.runTime = other.runTime;
/*  45 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  46 */     this.settings = other.settings;
/*  47 */     this.rules = other.rules;
/*  48 */     this.tags = other.tags;
/*  49 */     this.camera = other.camera;
/*  50 */     this.defaultValue = other.defaultValue;
/*  51 */     this.variable = other.variable;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ReplaceInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  56 */     ReplaceInteraction obj = new ReplaceInteraction();
/*  57 */     byte nullBits = buf.getByte(offset);
/*  58 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  59 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  60 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  61 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  62 */     obj.defaultValue = buf.getIntLE(offset + 11);
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
/* 107 */       int variableLen = VarInt.peek(buf, varPos5);
/* 108 */       if (variableLen < 0) throw ProtocolException.negativeLength("Variable", variableLen); 
/* 109 */       if (variableLen > 4096000) throw ProtocolException.stringTooLong("Variable", variableLen, 4096000); 
/* 110 */       obj.variable = PacketIO.readVarString(buf, varPos5, PacketIO.UTF8);
/*     */     } 
/*     */     
/* 113 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 117 */     byte nullBits = buf.getByte(offset);
/* 118 */     int maxEnd = 39;
/* 119 */     if ((nullBits & 0x1) != 0) {
/* 120 */       int fieldOffset0 = buf.getIntLE(offset + 15);
/* 121 */       int pos0 = offset + 39 + fieldOffset0;
/* 122 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 123 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 125 */     if ((nullBits & 0x2) != 0) {
/* 126 */       int fieldOffset1 = buf.getIntLE(offset + 19);
/* 127 */       int pos1 = offset + 39 + fieldOffset1;
/* 128 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 129 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 130 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 132 */     if ((nullBits & 0x4) != 0) {
/* 133 */       int fieldOffset2 = buf.getIntLE(offset + 23);
/* 134 */       int pos2 = offset + 39 + fieldOffset2;
/* 135 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 136 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 138 */     if ((nullBits & 0x8) != 0) {
/* 139 */       int fieldOffset3 = buf.getIntLE(offset + 27);
/* 140 */       int pos3 = offset + 39 + fieldOffset3;
/* 141 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 142 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 144 */     if ((nullBits & 0x10) != 0) {
/* 145 */       int fieldOffset4 = buf.getIntLE(offset + 31);
/* 146 */       int pos4 = offset + 39 + fieldOffset4;
/* 147 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 148 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 150 */     if ((nullBits & 0x20) != 0) {
/* 151 */       int fieldOffset5 = buf.getIntLE(offset + 35);
/* 152 */       int pos5 = offset + 39 + fieldOffset5;
/* 153 */       int sl = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5) + sl;
/* 154 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 156 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 162 */     int startPos = buf.writerIndex();
/* 163 */     byte nullBits = 0;
/* 164 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 165 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 166 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 167 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 168 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 169 */     if (this.variable != null) nullBits = (byte)(nullBits | 0x20); 
/* 170 */     buf.writeByte(nullBits);
/*     */     
/* 172 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 173 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 174 */     buf.writeFloatLE(this.runTime);
/* 175 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 176 */     buf.writeIntLE(this.defaultValue);
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
/* 188 */     int variableOffsetSlot = buf.writerIndex();
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
/* 222 */     if (this.variable != null) {
/* 223 */       buf.setIntLE(variableOffsetSlot, buf.writerIndex() - varBlockStart);
/* 224 */       PacketIO.writeVarString(buf, this.variable, 4096000);
/*     */     } else {
/* 226 */       buf.setIntLE(variableOffsetSlot, -1);
/*     */     } 
/* 228 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 234 */     int size = 39;
/* 235 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 236 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 237 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 238 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 239 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 240 */     if (this.variable != null) size += PacketIO.stringSize(this.variable);
/*     */     
/* 242 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 246 */     if (buffer.readableBytes() - offset < 39) {
/* 247 */       return ValidationResult.error("Buffer too small: expected at least 39 bytes");
/*     */     }
/*     */     
/* 250 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 253 */     if ((nullBits & 0x1) != 0) {
/* 254 */       int effectsOffset = buffer.getIntLE(offset + 15);
/* 255 */       if (effectsOffset < 0) {
/* 256 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 258 */       int pos = offset + 39 + effectsOffset;
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
/* 270 */       int settingsOffset = buffer.getIntLE(offset + 19);
/* 271 */       if (settingsOffset < 0) {
/* 272 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 274 */       int pos = offset + 39 + settingsOffset;
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
/* 295 */       int rulesOffset = buffer.getIntLE(offset + 23);
/* 296 */       if (rulesOffset < 0) {
/* 297 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 299 */       int pos = offset + 39 + rulesOffset;
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
/* 311 */       int tagsOffset = buffer.getIntLE(offset + 27);
/* 312 */       if (tagsOffset < 0) {
/* 313 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 315 */       int pos = offset + 39 + tagsOffset;
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
/* 334 */       int cameraOffset = buffer.getIntLE(offset + 31);
/* 335 */       if (cameraOffset < 0) {
/* 336 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 338 */       int pos = offset + 39 + cameraOffset;
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
/* 350 */       int variableOffset = buffer.getIntLE(offset + 35);
/* 351 */       if (variableOffset < 0) {
/* 352 */         return ValidationResult.error("Invalid offset for Variable");
/*     */       }
/* 354 */       int pos = offset + 39 + variableOffset;
/* 355 */       if (pos >= buffer.writerIndex()) {
/* 356 */         return ValidationResult.error("Offset out of bounds for Variable");
/*     */       }
/* 358 */       int variableLen = VarInt.peek(buffer, pos);
/* 359 */       if (variableLen < 0) {
/* 360 */         return ValidationResult.error("Invalid string length for Variable");
/*     */       }
/* 362 */       if (variableLen > 4096000) {
/* 363 */         return ValidationResult.error("Variable exceeds max length 4096000");
/*     */       }
/* 365 */       pos += VarInt.length(buffer, pos);
/* 366 */       pos += variableLen;
/* 367 */       if (pos > buffer.writerIndex()) {
/* 368 */         return ValidationResult.error("Buffer overflow reading Variable");
/*     */       }
/*     */     } 
/* 371 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ReplaceInteraction clone() {
/* 375 */     ReplaceInteraction copy = new ReplaceInteraction();
/* 376 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 377 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 378 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 379 */     copy.runTime = this.runTime;
/* 380 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 381 */     if (this.settings != null) {
/* 382 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 383 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 384 */       copy.settings = m;
/*     */     } 
/* 386 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 387 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 388 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 389 */     copy.defaultValue = this.defaultValue;
/* 390 */     copy.variable = this.variable;
/* 391 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ReplaceInteraction other;
/* 397 */     if (this == obj) return true; 
/* 398 */     if (obj instanceof ReplaceInteraction) { other = (ReplaceInteraction)obj; } else { return false; }
/* 399 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.defaultValue == other.defaultValue && Objects.equals(this.variable, other.variable));
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
/* 414 */     result = 31 * result + Integer.hashCode(this.defaultValue);
/* 415 */     result = 31 * result + Objects.hashCode(this.variable);
/* 416 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ReplaceInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */