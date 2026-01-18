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
/*     */ public class MovementConditionInteraction
/*     */   extends SimpleInteraction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 51;
/*     */   public static final int VARIABLE_FIELD_COUNT = 5;
/*     */   public static final int VARIABLE_BLOCK_START = 71;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   public int forward;
/*     */   public int back;
/*     */   public int left;
/*     */   public int right;
/*     */   public int forwardLeft;
/*     */   public int forwardRight;
/*     */   public int backLeft;
/*     */   public int backRight;
/*     */   
/*     */   public MovementConditionInteraction() {}
/*     */   
/*     */   public MovementConditionInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, int forward, int back, int left, int right, int forwardLeft, int forwardRight, int backLeft, int backRight) {
/*  33 */     this.waitForDataFrom = waitForDataFrom;
/*  34 */     this.effects = effects;
/*  35 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  36 */     this.runTime = runTime;
/*  37 */     this.cancelOnItemChange = cancelOnItemChange;
/*  38 */     this.settings = settings;
/*  39 */     this.rules = rules;
/*  40 */     this.tags = tags;
/*  41 */     this.camera = camera;
/*  42 */     this.next = next;
/*  43 */     this.failed = failed;
/*  44 */     this.forward = forward;
/*  45 */     this.back = back;
/*  46 */     this.left = left;
/*  47 */     this.right = right;
/*  48 */     this.forwardLeft = forwardLeft;
/*  49 */     this.forwardRight = forwardRight;
/*  50 */     this.backLeft = backLeft;
/*  51 */     this.backRight = backRight;
/*     */   }
/*     */   
/*     */   public MovementConditionInteraction(@Nonnull MovementConditionInteraction other) {
/*  55 */     this.waitForDataFrom = other.waitForDataFrom;
/*  56 */     this.effects = other.effects;
/*  57 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  58 */     this.runTime = other.runTime;
/*  59 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  60 */     this.settings = other.settings;
/*  61 */     this.rules = other.rules;
/*  62 */     this.tags = other.tags;
/*  63 */     this.camera = other.camera;
/*  64 */     this.next = other.next;
/*  65 */     this.failed = other.failed;
/*  66 */     this.forward = other.forward;
/*  67 */     this.back = other.back;
/*  68 */     this.left = other.left;
/*  69 */     this.right = other.right;
/*  70 */     this.forwardLeft = other.forwardLeft;
/*  71 */     this.forwardRight = other.forwardRight;
/*  72 */     this.backLeft = other.backLeft;
/*  73 */     this.backRight = other.backRight;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static MovementConditionInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  78 */     MovementConditionInteraction obj = new MovementConditionInteraction();
/*  79 */     byte nullBits = buf.getByte(offset);
/*  80 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  81 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  82 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  83 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  84 */     obj.next = buf.getIntLE(offset + 11);
/*  85 */     obj.failed = buf.getIntLE(offset + 15);
/*  86 */     obj.forward = buf.getIntLE(offset + 19);
/*  87 */     obj.back = buf.getIntLE(offset + 23);
/*  88 */     obj.left = buf.getIntLE(offset + 27);
/*  89 */     obj.right = buf.getIntLE(offset + 31);
/*  90 */     obj.forwardLeft = buf.getIntLE(offset + 35);
/*  91 */     obj.forwardRight = buf.getIntLE(offset + 39);
/*  92 */     obj.backLeft = buf.getIntLE(offset + 43);
/*  93 */     obj.backRight = buf.getIntLE(offset + 47);
/*     */     
/*  95 */     if ((nullBits & 0x1) != 0) {
/*  96 */       int varPos0 = offset + 71 + buf.getIntLE(offset + 51);
/*  97 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  99 */     if ((nullBits & 0x2) != 0) {
/* 100 */       int varPos1 = offset + 71 + buf.getIntLE(offset + 55);
/* 101 */       int settingsCount = VarInt.peek(buf, varPos1);
/* 102 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/* 103 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/* 104 */       int varIntLen = VarInt.length(buf, varPos1);
/* 105 */       obj.settings = new HashMap<>(settingsCount);
/* 106 */       int dictPos = varPos1 + varIntLen;
/* 107 */       for (int i = 0; i < settingsCount; i++) {
/* 108 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/* 109 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/* 110 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/* 111 */         if (obj.settings.put(key, val) != null)
/* 112 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/* 115 */     if ((nullBits & 0x4) != 0) {
/* 116 */       int varPos2 = offset + 71 + buf.getIntLE(offset + 59);
/* 117 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/* 119 */     if ((nullBits & 0x8) != 0) {
/* 120 */       int varPos3 = offset + 71 + buf.getIntLE(offset + 63);
/* 121 */       int tagsCount = VarInt.peek(buf, varPos3);
/* 122 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/* 123 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/* 124 */       int varIntLen = VarInt.length(buf, varPos3);
/* 125 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/* 126 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/* 127 */       obj.tags = new int[tagsCount];
/* 128 */       for (int i = 0; i < tagsCount; i++) {
/* 129 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 132 */     if ((nullBits & 0x10) != 0) {
/* 133 */       int varPos4 = offset + 71 + buf.getIntLE(offset + 67);
/* 134 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/*     */     
/* 137 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 141 */     byte nullBits = buf.getByte(offset);
/* 142 */     int maxEnd = 71;
/* 143 */     if ((nullBits & 0x1) != 0) {
/* 144 */       int fieldOffset0 = buf.getIntLE(offset + 51);
/* 145 */       int pos0 = offset + 71 + fieldOffset0;
/* 146 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 147 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 149 */     if ((nullBits & 0x2) != 0) {
/* 150 */       int fieldOffset1 = buf.getIntLE(offset + 55);
/* 151 */       int pos1 = offset + 71 + fieldOffset1;
/* 152 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 153 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 154 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 156 */     if ((nullBits & 0x4) != 0) {
/* 157 */       int fieldOffset2 = buf.getIntLE(offset + 59);
/* 158 */       int pos2 = offset + 71 + fieldOffset2;
/* 159 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 160 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 162 */     if ((nullBits & 0x8) != 0) {
/* 163 */       int fieldOffset3 = buf.getIntLE(offset + 63);
/* 164 */       int pos3 = offset + 71 + fieldOffset3;
/* 165 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 166 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 168 */     if ((nullBits & 0x10) != 0) {
/* 169 */       int fieldOffset4 = buf.getIntLE(offset + 67);
/* 170 */       int pos4 = offset + 71 + fieldOffset4;
/* 171 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 172 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 174 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 180 */     int startPos = buf.writerIndex();
/* 181 */     byte nullBits = 0;
/* 182 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 183 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 184 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 185 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 186 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 187 */     buf.writeByte(nullBits);
/*     */     
/* 189 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 190 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 191 */     buf.writeFloatLE(this.runTime);
/* 192 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 193 */     buf.writeIntLE(this.next);
/* 194 */     buf.writeIntLE(this.failed);
/* 195 */     buf.writeIntLE(this.forward);
/* 196 */     buf.writeIntLE(this.back);
/* 197 */     buf.writeIntLE(this.left);
/* 198 */     buf.writeIntLE(this.right);
/* 199 */     buf.writeIntLE(this.forwardLeft);
/* 200 */     buf.writeIntLE(this.forwardRight);
/* 201 */     buf.writeIntLE(this.backLeft);
/* 202 */     buf.writeIntLE(this.backRight);
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
/*     */     
/* 215 */     int varBlockStart = buf.writerIndex();
/* 216 */     if (this.effects != null) {
/* 217 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 218 */       this.effects.serialize(buf);
/*     */     } else {
/* 220 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 222 */     if (this.settings != null)
/* 223 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 224 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 226 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 228 */     if (this.rules != null) {
/* 229 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 230 */       this.rules.serialize(buf);
/*     */     } else {
/* 232 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 234 */     if (this.tags != null) {
/* 235 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 236 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 238 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 240 */     if (this.camera != null) {
/* 241 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 242 */       this.camera.serialize(buf);
/*     */     } else {
/* 244 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 246 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 252 */     int size = 71;
/* 253 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 254 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 255 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 256 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 257 */     if (this.camera != null) size += this.camera.computeSize();
/*     */     
/* 259 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 263 */     if (buffer.readableBytes() - offset < 71) {
/* 264 */       return ValidationResult.error("Buffer too small: expected at least 71 bytes");
/*     */     }
/*     */     
/* 267 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 270 */     if ((nullBits & 0x1) != 0) {
/* 271 */       int effectsOffset = buffer.getIntLE(offset + 51);
/* 272 */       if (effectsOffset < 0) {
/* 273 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 275 */       int pos = offset + 71 + effectsOffset;
/* 276 */       if (pos >= buffer.writerIndex()) {
/* 277 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 279 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 280 */       if (!effectsResult.isValid()) {
/* 281 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 283 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 286 */     if ((nullBits & 0x2) != 0) {
/* 287 */       int settingsOffset = buffer.getIntLE(offset + 55);
/* 288 */       if (settingsOffset < 0) {
/* 289 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 291 */       int pos = offset + 71 + settingsOffset;
/* 292 */       if (pos >= buffer.writerIndex()) {
/* 293 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 295 */       int settingsCount = VarInt.peek(buffer, pos);
/* 296 */       if (settingsCount < 0) {
/* 297 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 299 */       if (settingsCount > 4096000) {
/* 300 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 302 */       pos += VarInt.length(buffer, pos);
/* 303 */       for (int i = 0; i < settingsCount; i++) {
/* 304 */         pos++;
/*     */         
/* 306 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 311 */     if ((nullBits & 0x4) != 0) {
/* 312 */       int rulesOffset = buffer.getIntLE(offset + 59);
/* 313 */       if (rulesOffset < 0) {
/* 314 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 316 */       int pos = offset + 71 + rulesOffset;
/* 317 */       if (pos >= buffer.writerIndex()) {
/* 318 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 320 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 321 */       if (!rulesResult.isValid()) {
/* 322 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 324 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 327 */     if ((nullBits & 0x8) != 0) {
/* 328 */       int tagsOffset = buffer.getIntLE(offset + 63);
/* 329 */       if (tagsOffset < 0) {
/* 330 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 332 */       int pos = offset + 71 + tagsOffset;
/* 333 */       if (pos >= buffer.writerIndex()) {
/* 334 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 336 */       int tagsCount = VarInt.peek(buffer, pos);
/* 337 */       if (tagsCount < 0) {
/* 338 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 340 */       if (tagsCount > 4096000) {
/* 341 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 343 */       pos += VarInt.length(buffer, pos);
/* 344 */       pos += tagsCount * 4;
/* 345 */       if (pos > buffer.writerIndex()) {
/* 346 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 350 */     if ((nullBits & 0x10) != 0) {
/* 351 */       int cameraOffset = buffer.getIntLE(offset + 67);
/* 352 */       if (cameraOffset < 0) {
/* 353 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 355 */       int pos = offset + 71 + cameraOffset;
/* 356 */       if (pos >= buffer.writerIndex()) {
/* 357 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 359 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 360 */       if (!cameraResult.isValid()) {
/* 361 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 363 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 365 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public MovementConditionInteraction clone() {
/* 369 */     MovementConditionInteraction copy = new MovementConditionInteraction();
/* 370 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 371 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 372 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 373 */     copy.runTime = this.runTime;
/* 374 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 375 */     if (this.settings != null) {
/* 376 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 377 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 378 */       copy.settings = m;
/*     */     } 
/* 380 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 381 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 382 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 383 */     copy.next = this.next;
/* 384 */     copy.failed = this.failed;
/* 385 */     copy.forward = this.forward;
/* 386 */     copy.back = this.back;
/* 387 */     copy.left = this.left;
/* 388 */     copy.right = this.right;
/* 389 */     copy.forwardLeft = this.forwardLeft;
/* 390 */     copy.forwardRight = this.forwardRight;
/* 391 */     copy.backLeft = this.backLeft;
/* 392 */     copy.backRight = this.backRight;
/* 393 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     MovementConditionInteraction other;
/* 399 */     if (this == obj) return true; 
/* 400 */     if (obj instanceof MovementConditionInteraction) { other = (MovementConditionInteraction)obj; } else { return false; }
/* 401 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && this.forward == other.forward && this.back == other.back && this.left == other.left && this.right == other.right && this.forwardLeft == other.forwardLeft && this.forwardRight == other.forwardRight && this.backLeft == other.backLeft && this.backRight == other.backRight);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 406 */     int result = 1;
/* 407 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 408 */     result = 31 * result + Objects.hashCode(this.effects);
/* 409 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 410 */     result = 31 * result + Float.hashCode(this.runTime);
/* 411 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 412 */     result = 31 * result + Objects.hashCode(this.settings);
/* 413 */     result = 31 * result + Objects.hashCode(this.rules);
/* 414 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 415 */     result = 31 * result + Objects.hashCode(this.camera);
/* 416 */     result = 31 * result + Integer.hashCode(this.next);
/* 417 */     result = 31 * result + Integer.hashCode(this.failed);
/* 418 */     result = 31 * result + Integer.hashCode(this.forward);
/* 419 */     result = 31 * result + Integer.hashCode(this.back);
/* 420 */     result = 31 * result + Integer.hashCode(this.left);
/* 421 */     result = 31 * result + Integer.hashCode(this.right);
/* 422 */     result = 31 * result + Integer.hashCode(this.forwardLeft);
/* 423 */     result = 31 * result + Integer.hashCode(this.forwardRight);
/* 424 */     result = 31 * result + Integer.hashCode(this.backLeft);
/* 425 */     result = 31 * result + Integer.hashCode(this.backRight);
/* 426 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\MovementConditionInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */