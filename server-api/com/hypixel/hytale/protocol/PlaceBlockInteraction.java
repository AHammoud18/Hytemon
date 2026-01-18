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
/*     */ public class PlaceBlockInteraction
/*     */   extends SimpleInteraction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 25;
/*     */   public static final int VARIABLE_FIELD_COUNT = 5;
/*     */   public static final int VARIABLE_BLOCK_START = 45;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   public int blockId;
/*     */   public boolean removeItemInHand;
/*     */   public boolean allowDragPlacement;
/*     */   
/*     */   public PlaceBlockInteraction() {}
/*     */   
/*     */   public PlaceBlockInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, int blockId, boolean removeItemInHand, boolean allowDragPlacement) {
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
/*  39 */     this.blockId = blockId;
/*  40 */     this.removeItemInHand = removeItemInHand;
/*  41 */     this.allowDragPlacement = allowDragPlacement;
/*     */   }
/*     */   
/*     */   public PlaceBlockInteraction(@Nonnull PlaceBlockInteraction other) {
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
/*  56 */     this.blockId = other.blockId;
/*  57 */     this.removeItemInHand = other.removeItemInHand;
/*  58 */     this.allowDragPlacement = other.allowDragPlacement;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static PlaceBlockInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  63 */     PlaceBlockInteraction obj = new PlaceBlockInteraction();
/*  64 */     byte nullBits = buf.getByte(offset);
/*  65 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  66 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  67 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  68 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  69 */     obj.next = buf.getIntLE(offset + 11);
/*  70 */     obj.failed = buf.getIntLE(offset + 15);
/*  71 */     obj.blockId = buf.getIntLE(offset + 19);
/*  72 */     obj.removeItemInHand = (buf.getByte(offset + 23) != 0);
/*  73 */     obj.allowDragPlacement = (buf.getByte(offset + 24) != 0);
/*     */     
/*  75 */     if ((nullBits & 0x1) != 0) {
/*  76 */       int varPos0 = offset + 45 + buf.getIntLE(offset + 25);
/*  77 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  79 */     if ((nullBits & 0x2) != 0) {
/*  80 */       int varPos1 = offset + 45 + buf.getIntLE(offset + 29);
/*  81 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  82 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  83 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  84 */       int varIntLen = VarInt.length(buf, varPos1);
/*  85 */       obj.settings = new HashMap<>(settingsCount);
/*  86 */       int dictPos = varPos1 + varIntLen;
/*  87 */       for (int i = 0; i < settingsCount; i++) {
/*  88 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  89 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/*  90 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/*  91 */         if (obj.settings.put(key, val) != null)
/*  92 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/*  95 */     if ((nullBits & 0x4) != 0) {
/*  96 */       int varPos2 = offset + 45 + buf.getIntLE(offset + 33);
/*  97 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/*  99 */     if ((nullBits & 0x8) != 0) {
/* 100 */       int varPos3 = offset + 45 + buf.getIntLE(offset + 37);
/* 101 */       int tagsCount = VarInt.peek(buf, varPos3);
/* 102 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/* 103 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/* 104 */       int varIntLen = VarInt.length(buf, varPos3);
/* 105 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/* 106 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/* 107 */       obj.tags = new int[tagsCount];
/* 108 */       for (int i = 0; i < tagsCount; i++) {
/* 109 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 112 */     if ((nullBits & 0x10) != 0) {
/* 113 */       int varPos4 = offset + 45 + buf.getIntLE(offset + 41);
/* 114 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/*     */     
/* 117 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 121 */     byte nullBits = buf.getByte(offset);
/* 122 */     int maxEnd = 45;
/* 123 */     if ((nullBits & 0x1) != 0) {
/* 124 */       int fieldOffset0 = buf.getIntLE(offset + 25);
/* 125 */       int pos0 = offset + 45 + fieldOffset0;
/* 126 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 127 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 129 */     if ((nullBits & 0x2) != 0) {
/* 130 */       int fieldOffset1 = buf.getIntLE(offset + 29);
/* 131 */       int pos1 = offset + 45 + fieldOffset1;
/* 132 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 133 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 134 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 136 */     if ((nullBits & 0x4) != 0) {
/* 137 */       int fieldOffset2 = buf.getIntLE(offset + 33);
/* 138 */       int pos2 = offset + 45 + fieldOffset2;
/* 139 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 140 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 142 */     if ((nullBits & 0x8) != 0) {
/* 143 */       int fieldOffset3 = buf.getIntLE(offset + 37);
/* 144 */       int pos3 = offset + 45 + fieldOffset3;
/* 145 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 146 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 148 */     if ((nullBits & 0x10) != 0) {
/* 149 */       int fieldOffset4 = buf.getIntLE(offset + 41);
/* 150 */       int pos4 = offset + 45 + fieldOffset4;
/* 151 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 152 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 154 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 160 */     int startPos = buf.writerIndex();
/* 161 */     byte nullBits = 0;
/* 162 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 163 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 164 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 165 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 166 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 167 */     buf.writeByte(nullBits);
/*     */     
/* 169 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 170 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 171 */     buf.writeFloatLE(this.runTime);
/* 172 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 173 */     buf.writeIntLE(this.next);
/* 174 */     buf.writeIntLE(this.failed);
/* 175 */     buf.writeIntLE(this.blockId);
/* 176 */     buf.writeByte(this.removeItemInHand ? 1 : 0);
/* 177 */     buf.writeByte(this.allowDragPlacement ? 1 : 0);
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
/*     */     
/* 190 */     int varBlockStart = buf.writerIndex();
/* 191 */     if (this.effects != null) {
/* 192 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 193 */       this.effects.serialize(buf);
/*     */     } else {
/* 195 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 197 */     if (this.settings != null)
/* 198 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 199 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 201 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 203 */     if (this.rules != null) {
/* 204 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 205 */       this.rules.serialize(buf);
/*     */     } else {
/* 207 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 209 */     if (this.tags != null) {
/* 210 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 211 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 213 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 215 */     if (this.camera != null) {
/* 216 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 217 */       this.camera.serialize(buf);
/*     */     } else {
/* 219 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 221 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 227 */     int size = 45;
/* 228 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 229 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 230 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 231 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 232 */     if (this.camera != null) size += this.camera.computeSize();
/*     */     
/* 234 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 238 */     if (buffer.readableBytes() - offset < 45) {
/* 239 */       return ValidationResult.error("Buffer too small: expected at least 45 bytes");
/*     */     }
/*     */     
/* 242 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 245 */     if ((nullBits & 0x1) != 0) {
/* 246 */       int effectsOffset = buffer.getIntLE(offset + 25);
/* 247 */       if (effectsOffset < 0) {
/* 248 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 250 */       int pos = offset + 45 + effectsOffset;
/* 251 */       if (pos >= buffer.writerIndex()) {
/* 252 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 254 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 255 */       if (!effectsResult.isValid()) {
/* 256 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 258 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 261 */     if ((nullBits & 0x2) != 0) {
/* 262 */       int settingsOffset = buffer.getIntLE(offset + 29);
/* 263 */       if (settingsOffset < 0) {
/* 264 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 266 */       int pos = offset + 45 + settingsOffset;
/* 267 */       if (pos >= buffer.writerIndex()) {
/* 268 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 270 */       int settingsCount = VarInt.peek(buffer, pos);
/* 271 */       if (settingsCount < 0) {
/* 272 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 274 */       if (settingsCount > 4096000) {
/* 275 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 277 */       pos += VarInt.length(buffer, pos);
/* 278 */       for (int i = 0; i < settingsCount; i++) {
/* 279 */         pos++;
/*     */         
/* 281 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 286 */     if ((nullBits & 0x4) != 0) {
/* 287 */       int rulesOffset = buffer.getIntLE(offset + 33);
/* 288 */       if (rulesOffset < 0) {
/* 289 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 291 */       int pos = offset + 45 + rulesOffset;
/* 292 */       if (pos >= buffer.writerIndex()) {
/* 293 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 295 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 296 */       if (!rulesResult.isValid()) {
/* 297 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 299 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 302 */     if ((nullBits & 0x8) != 0) {
/* 303 */       int tagsOffset = buffer.getIntLE(offset + 37);
/* 304 */       if (tagsOffset < 0) {
/* 305 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 307 */       int pos = offset + 45 + tagsOffset;
/* 308 */       if (pos >= buffer.writerIndex()) {
/* 309 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 311 */       int tagsCount = VarInt.peek(buffer, pos);
/* 312 */       if (tagsCount < 0) {
/* 313 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 315 */       if (tagsCount > 4096000) {
/* 316 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 318 */       pos += VarInt.length(buffer, pos);
/* 319 */       pos += tagsCount * 4;
/* 320 */       if (pos > buffer.writerIndex()) {
/* 321 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 325 */     if ((nullBits & 0x10) != 0) {
/* 326 */       int cameraOffset = buffer.getIntLE(offset + 41);
/* 327 */       if (cameraOffset < 0) {
/* 328 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 330 */       int pos = offset + 45 + cameraOffset;
/* 331 */       if (pos >= buffer.writerIndex()) {
/* 332 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 334 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 335 */       if (!cameraResult.isValid()) {
/* 336 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 338 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 340 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public PlaceBlockInteraction clone() {
/* 344 */     PlaceBlockInteraction copy = new PlaceBlockInteraction();
/* 345 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 346 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 347 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 348 */     copy.runTime = this.runTime;
/* 349 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 350 */     if (this.settings != null) {
/* 351 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 352 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 353 */       copy.settings = m;
/*     */     } 
/* 355 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 356 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 357 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 358 */     copy.next = this.next;
/* 359 */     copy.failed = this.failed;
/* 360 */     copy.blockId = this.blockId;
/* 361 */     copy.removeItemInHand = this.removeItemInHand;
/* 362 */     copy.allowDragPlacement = this.allowDragPlacement;
/* 363 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     PlaceBlockInteraction other;
/* 369 */     if (this == obj) return true; 
/* 370 */     if (obj instanceof PlaceBlockInteraction) { other = (PlaceBlockInteraction)obj; } else { return false; }
/* 371 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && this.blockId == other.blockId && this.removeItemInHand == other.removeItemInHand && this.allowDragPlacement == other.allowDragPlacement);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 376 */     int result = 1;
/* 377 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 378 */     result = 31 * result + Objects.hashCode(this.effects);
/* 379 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 380 */     result = 31 * result + Float.hashCode(this.runTime);
/* 381 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 382 */     result = 31 * result + Objects.hashCode(this.settings);
/* 383 */     result = 31 * result + Objects.hashCode(this.rules);
/* 384 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 385 */     result = 31 * result + Objects.hashCode(this.camera);
/* 386 */     result = 31 * result + Integer.hashCode(this.next);
/* 387 */     result = 31 * result + Integer.hashCode(this.failed);
/* 388 */     result = 31 * result + Integer.hashCode(this.blockId);
/* 389 */     result = 31 * result + Boolean.hashCode(this.removeItemInHand);
/* 390 */     result = 31 * result + Boolean.hashCode(this.allowDragPlacement);
/* 391 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\PlaceBlockInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */