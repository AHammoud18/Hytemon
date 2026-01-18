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
/*     */ public class UseEntityInteraction
/*     */   extends SimpleInteraction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 19;
/*     */   public static final int VARIABLE_FIELD_COUNT = 5;
/*     */   public static final int VARIABLE_BLOCK_START = 39;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public UseEntityInteraction() {}
/*     */   
/*     */   public UseEntityInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed) {
/*  25 */     this.waitForDataFrom = waitForDataFrom;
/*  26 */     this.effects = effects;
/*  27 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  28 */     this.runTime = runTime;
/*  29 */     this.cancelOnItemChange = cancelOnItemChange;
/*  30 */     this.settings = settings;
/*  31 */     this.rules = rules;
/*  32 */     this.tags = tags;
/*  33 */     this.camera = camera;
/*  34 */     this.next = next;
/*  35 */     this.failed = failed;
/*     */   }
/*     */   
/*     */   public UseEntityInteraction(@Nonnull UseEntityInteraction other) {
/*  39 */     this.waitForDataFrom = other.waitForDataFrom;
/*  40 */     this.effects = other.effects;
/*  41 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  42 */     this.runTime = other.runTime;
/*  43 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  44 */     this.settings = other.settings;
/*  45 */     this.rules = other.rules;
/*  46 */     this.tags = other.tags;
/*  47 */     this.camera = other.camera;
/*  48 */     this.next = other.next;
/*  49 */     this.failed = other.failed;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UseEntityInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  54 */     UseEntityInteraction obj = new UseEntityInteraction();
/*  55 */     byte nullBits = buf.getByte(offset);
/*  56 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  57 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  58 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  59 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  60 */     obj.next = buf.getIntLE(offset + 11);
/*  61 */     obj.failed = buf.getIntLE(offset + 15);
/*     */     
/*  63 */     if ((nullBits & 0x1) != 0) {
/*  64 */       int varPos0 = offset + 39 + buf.getIntLE(offset + 19);
/*  65 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  67 */     if ((nullBits & 0x2) != 0) {
/*  68 */       int varPos1 = offset + 39 + buf.getIntLE(offset + 23);
/*  69 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  70 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  71 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  72 */       int varIntLen = VarInt.length(buf, varPos1);
/*  73 */       obj.settings = new HashMap<>(settingsCount);
/*  74 */       int dictPos = varPos1 + varIntLen;
/*  75 */       for (int i = 0; i < settingsCount; i++) {
/*  76 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  77 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/*  78 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/*  79 */         if (obj.settings.put(key, val) != null)
/*  80 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/*  83 */     if ((nullBits & 0x4) != 0) {
/*  84 */       int varPos2 = offset + 39 + buf.getIntLE(offset + 27);
/*  85 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/*  87 */     if ((nullBits & 0x8) != 0) {
/*  88 */       int varPos3 = offset + 39 + buf.getIntLE(offset + 31);
/*  89 */       int tagsCount = VarInt.peek(buf, varPos3);
/*  90 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/*  91 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/*  92 */       int varIntLen = VarInt.length(buf, varPos3);
/*  93 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/*  94 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/*  95 */       obj.tags = new int[tagsCount];
/*  96 */       for (int i = 0; i < tagsCount; i++) {
/*  97 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 100 */     if ((nullBits & 0x10) != 0) {
/* 101 */       int varPos4 = offset + 39 + buf.getIntLE(offset + 35);
/* 102 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/*     */     
/* 105 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 109 */     byte nullBits = buf.getByte(offset);
/* 110 */     int maxEnd = 39;
/* 111 */     if ((nullBits & 0x1) != 0) {
/* 112 */       int fieldOffset0 = buf.getIntLE(offset + 19);
/* 113 */       int pos0 = offset + 39 + fieldOffset0;
/* 114 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 115 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 117 */     if ((nullBits & 0x2) != 0) {
/* 118 */       int fieldOffset1 = buf.getIntLE(offset + 23);
/* 119 */       int pos1 = offset + 39 + fieldOffset1;
/* 120 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 121 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 122 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 124 */     if ((nullBits & 0x4) != 0) {
/* 125 */       int fieldOffset2 = buf.getIntLE(offset + 27);
/* 126 */       int pos2 = offset + 39 + fieldOffset2;
/* 127 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 128 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 130 */     if ((nullBits & 0x8) != 0) {
/* 131 */       int fieldOffset3 = buf.getIntLE(offset + 31);
/* 132 */       int pos3 = offset + 39 + fieldOffset3;
/* 133 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 134 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 136 */     if ((nullBits & 0x10) != 0) {
/* 137 */       int fieldOffset4 = buf.getIntLE(offset + 35);
/* 138 */       int pos4 = offset + 39 + fieldOffset4;
/* 139 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 140 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 142 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 148 */     int startPos = buf.writerIndex();
/* 149 */     byte nullBits = 0;
/* 150 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 151 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 152 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 153 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 154 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 155 */     buf.writeByte(nullBits);
/*     */     
/* 157 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 158 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 159 */     buf.writeFloatLE(this.runTime);
/* 160 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 161 */     buf.writeIntLE(this.next);
/* 162 */     buf.writeIntLE(this.failed);
/*     */     
/* 164 */     int effectsOffsetSlot = buf.writerIndex();
/* 165 */     buf.writeIntLE(0);
/* 166 */     int settingsOffsetSlot = buf.writerIndex();
/* 167 */     buf.writeIntLE(0);
/* 168 */     int rulesOffsetSlot = buf.writerIndex();
/* 169 */     buf.writeIntLE(0);
/* 170 */     int tagsOffsetSlot = buf.writerIndex();
/* 171 */     buf.writeIntLE(0);
/* 172 */     int cameraOffsetSlot = buf.writerIndex();
/* 173 */     buf.writeIntLE(0);
/*     */     
/* 175 */     int varBlockStart = buf.writerIndex();
/* 176 */     if (this.effects != null) {
/* 177 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 178 */       this.effects.serialize(buf);
/*     */     } else {
/* 180 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 182 */     if (this.settings != null)
/* 183 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 184 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 186 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 188 */     if (this.rules != null) {
/* 189 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 190 */       this.rules.serialize(buf);
/*     */     } else {
/* 192 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 194 */     if (this.tags != null) {
/* 195 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 196 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 198 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 200 */     if (this.camera != null) {
/* 201 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 202 */       this.camera.serialize(buf);
/*     */     } else {
/* 204 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 206 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 212 */     int size = 39;
/* 213 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 214 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 215 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 216 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 217 */     if (this.camera != null) size += this.camera.computeSize();
/*     */     
/* 219 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 223 */     if (buffer.readableBytes() - offset < 39) {
/* 224 */       return ValidationResult.error("Buffer too small: expected at least 39 bytes");
/*     */     }
/*     */     
/* 227 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 230 */     if ((nullBits & 0x1) != 0) {
/* 231 */       int effectsOffset = buffer.getIntLE(offset + 19);
/* 232 */       if (effectsOffset < 0) {
/* 233 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 235 */       int pos = offset + 39 + effectsOffset;
/* 236 */       if (pos >= buffer.writerIndex()) {
/* 237 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 239 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 240 */       if (!effectsResult.isValid()) {
/* 241 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 243 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 246 */     if ((nullBits & 0x2) != 0) {
/* 247 */       int settingsOffset = buffer.getIntLE(offset + 23);
/* 248 */       if (settingsOffset < 0) {
/* 249 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 251 */       int pos = offset + 39 + settingsOffset;
/* 252 */       if (pos >= buffer.writerIndex()) {
/* 253 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 255 */       int settingsCount = VarInt.peek(buffer, pos);
/* 256 */       if (settingsCount < 0) {
/* 257 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 259 */       if (settingsCount > 4096000) {
/* 260 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 262 */       pos += VarInt.length(buffer, pos);
/* 263 */       for (int i = 0; i < settingsCount; i++) {
/* 264 */         pos++;
/*     */         
/* 266 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 271 */     if ((nullBits & 0x4) != 0) {
/* 272 */       int rulesOffset = buffer.getIntLE(offset + 27);
/* 273 */       if (rulesOffset < 0) {
/* 274 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 276 */       int pos = offset + 39 + rulesOffset;
/* 277 */       if (pos >= buffer.writerIndex()) {
/* 278 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 280 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 281 */       if (!rulesResult.isValid()) {
/* 282 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 284 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 287 */     if ((nullBits & 0x8) != 0) {
/* 288 */       int tagsOffset = buffer.getIntLE(offset + 31);
/* 289 */       if (tagsOffset < 0) {
/* 290 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 292 */       int pos = offset + 39 + tagsOffset;
/* 293 */       if (pos >= buffer.writerIndex()) {
/* 294 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 296 */       int tagsCount = VarInt.peek(buffer, pos);
/* 297 */       if (tagsCount < 0) {
/* 298 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 300 */       if (tagsCount > 4096000) {
/* 301 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 303 */       pos += VarInt.length(buffer, pos);
/* 304 */       pos += tagsCount * 4;
/* 305 */       if (pos > buffer.writerIndex()) {
/* 306 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 310 */     if ((nullBits & 0x10) != 0) {
/* 311 */       int cameraOffset = buffer.getIntLE(offset + 35);
/* 312 */       if (cameraOffset < 0) {
/* 313 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 315 */       int pos = offset + 39 + cameraOffset;
/* 316 */       if (pos >= buffer.writerIndex()) {
/* 317 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 319 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 320 */       if (!cameraResult.isValid()) {
/* 321 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 323 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 325 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UseEntityInteraction clone() {
/* 329 */     UseEntityInteraction copy = new UseEntityInteraction();
/* 330 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 331 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 332 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 333 */     copy.runTime = this.runTime;
/* 334 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 335 */     if (this.settings != null) {
/* 336 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 337 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 338 */       copy.settings = m;
/*     */     } 
/* 340 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 341 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 342 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 343 */     copy.next = this.next;
/* 344 */     copy.failed = this.failed;
/* 345 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UseEntityInteraction other;
/* 351 */     if (this == obj) return true; 
/* 352 */     if (obj instanceof UseEntityInteraction) { other = (UseEntityInteraction)obj; } else { return false; }
/* 353 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 358 */     int result = 1;
/* 359 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 360 */     result = 31 * result + Objects.hashCode(this.effects);
/* 361 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 362 */     result = 31 * result + Float.hashCode(this.runTime);
/* 363 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 364 */     result = 31 * result + Objects.hashCode(this.settings);
/* 365 */     result = 31 * result + Objects.hashCode(this.rules);
/* 366 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 367 */     result = 31 * result + Objects.hashCode(this.camera);
/* 368 */     result = 31 * result + Integer.hashCode(this.next);
/* 369 */     result = 31 * result + Integer.hashCode(this.failed);
/* 370 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\UseEntityInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */