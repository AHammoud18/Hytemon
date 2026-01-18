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
/*     */ public class PickBlockInteraction
/*     */   extends SimpleBlockInteraction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 20;
/*     */   public static final int VARIABLE_FIELD_COUNT = 5;
/*     */   public static final int VARIABLE_BLOCK_START = 40;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public PickBlockInteraction() {}
/*     */   
/*     */   public PickBlockInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, boolean useLatestTarget) {
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
/*  36 */     this.useLatestTarget = useLatestTarget;
/*     */   }
/*     */   
/*     */   public PickBlockInteraction(@Nonnull PickBlockInteraction other) {
/*  40 */     this.waitForDataFrom = other.waitForDataFrom;
/*  41 */     this.effects = other.effects;
/*  42 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  43 */     this.runTime = other.runTime;
/*  44 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  45 */     this.settings = other.settings;
/*  46 */     this.rules = other.rules;
/*  47 */     this.tags = other.tags;
/*  48 */     this.camera = other.camera;
/*  49 */     this.next = other.next;
/*  50 */     this.failed = other.failed;
/*  51 */     this.useLatestTarget = other.useLatestTarget;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static PickBlockInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  56 */     PickBlockInteraction obj = new PickBlockInteraction();
/*  57 */     byte nullBits = buf.getByte(offset);
/*  58 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  59 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  60 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  61 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  62 */     obj.next = buf.getIntLE(offset + 11);
/*  63 */     obj.failed = buf.getIntLE(offset + 15);
/*  64 */     obj.useLatestTarget = (buf.getByte(offset + 19) != 0);
/*     */     
/*  66 */     if ((nullBits & 0x1) != 0) {
/*  67 */       int varPos0 = offset + 40 + buf.getIntLE(offset + 20);
/*  68 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  70 */     if ((nullBits & 0x2) != 0) {
/*  71 */       int varPos1 = offset + 40 + buf.getIntLE(offset + 24);
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
/*  87 */       int varPos2 = offset + 40 + buf.getIntLE(offset + 28);
/*  88 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/*  90 */     if ((nullBits & 0x8) != 0) {
/*  91 */       int varPos3 = offset + 40 + buf.getIntLE(offset + 32);
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
/* 104 */       int varPos4 = offset + 40 + buf.getIntLE(offset + 36);
/* 105 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/*     */     
/* 108 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 112 */     byte nullBits = buf.getByte(offset);
/* 113 */     int maxEnd = 40;
/* 114 */     if ((nullBits & 0x1) != 0) {
/* 115 */       int fieldOffset0 = buf.getIntLE(offset + 20);
/* 116 */       int pos0 = offset + 40 + fieldOffset0;
/* 117 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 118 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 120 */     if ((nullBits & 0x2) != 0) {
/* 121 */       int fieldOffset1 = buf.getIntLE(offset + 24);
/* 122 */       int pos1 = offset + 40 + fieldOffset1;
/* 123 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 124 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 125 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 127 */     if ((nullBits & 0x4) != 0) {
/* 128 */       int fieldOffset2 = buf.getIntLE(offset + 28);
/* 129 */       int pos2 = offset + 40 + fieldOffset2;
/* 130 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 131 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 133 */     if ((nullBits & 0x8) != 0) {
/* 134 */       int fieldOffset3 = buf.getIntLE(offset + 32);
/* 135 */       int pos3 = offset + 40 + fieldOffset3;
/* 136 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 137 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 139 */     if ((nullBits & 0x10) != 0) {
/* 140 */       int fieldOffset4 = buf.getIntLE(offset + 36);
/* 141 */       int pos4 = offset + 40 + fieldOffset4;
/* 142 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 143 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 145 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 151 */     int startPos = buf.writerIndex();
/* 152 */     byte nullBits = 0;
/* 153 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 154 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 155 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 156 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 157 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 158 */     buf.writeByte(nullBits);
/*     */     
/* 160 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 161 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 162 */     buf.writeFloatLE(this.runTime);
/* 163 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 164 */     buf.writeIntLE(this.next);
/* 165 */     buf.writeIntLE(this.failed);
/* 166 */     buf.writeByte(this.useLatestTarget ? 1 : 0);
/*     */     
/* 168 */     int effectsOffsetSlot = buf.writerIndex();
/* 169 */     buf.writeIntLE(0);
/* 170 */     int settingsOffsetSlot = buf.writerIndex();
/* 171 */     buf.writeIntLE(0);
/* 172 */     int rulesOffsetSlot = buf.writerIndex();
/* 173 */     buf.writeIntLE(0);
/* 174 */     int tagsOffsetSlot = buf.writerIndex();
/* 175 */     buf.writeIntLE(0);
/* 176 */     int cameraOffsetSlot = buf.writerIndex();
/* 177 */     buf.writeIntLE(0);
/*     */     
/* 179 */     int varBlockStart = buf.writerIndex();
/* 180 */     if (this.effects != null) {
/* 181 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 182 */       this.effects.serialize(buf);
/*     */     } else {
/* 184 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 186 */     if (this.settings != null)
/* 187 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 188 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 190 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 192 */     if (this.rules != null) {
/* 193 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 194 */       this.rules.serialize(buf);
/*     */     } else {
/* 196 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 198 */     if (this.tags != null) {
/* 199 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 200 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 202 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 204 */     if (this.camera != null) {
/* 205 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 206 */       this.camera.serialize(buf);
/*     */     } else {
/* 208 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 210 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 216 */     int size = 40;
/* 217 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 218 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 219 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 220 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 221 */     if (this.camera != null) size += this.camera.computeSize();
/*     */     
/* 223 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 227 */     if (buffer.readableBytes() - offset < 40) {
/* 228 */       return ValidationResult.error("Buffer too small: expected at least 40 bytes");
/*     */     }
/*     */     
/* 231 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 234 */     if ((nullBits & 0x1) != 0) {
/* 235 */       int effectsOffset = buffer.getIntLE(offset + 20);
/* 236 */       if (effectsOffset < 0) {
/* 237 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 239 */       int pos = offset + 40 + effectsOffset;
/* 240 */       if (pos >= buffer.writerIndex()) {
/* 241 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 243 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 244 */       if (!effectsResult.isValid()) {
/* 245 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 247 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 250 */     if ((nullBits & 0x2) != 0) {
/* 251 */       int settingsOffset = buffer.getIntLE(offset + 24);
/* 252 */       if (settingsOffset < 0) {
/* 253 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 255 */       int pos = offset + 40 + settingsOffset;
/* 256 */       if (pos >= buffer.writerIndex()) {
/* 257 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 259 */       int settingsCount = VarInt.peek(buffer, pos);
/* 260 */       if (settingsCount < 0) {
/* 261 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 263 */       if (settingsCount > 4096000) {
/* 264 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 266 */       pos += VarInt.length(buffer, pos);
/* 267 */       for (int i = 0; i < settingsCount; i++) {
/* 268 */         pos++;
/*     */         
/* 270 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 275 */     if ((nullBits & 0x4) != 0) {
/* 276 */       int rulesOffset = buffer.getIntLE(offset + 28);
/* 277 */       if (rulesOffset < 0) {
/* 278 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 280 */       int pos = offset + 40 + rulesOffset;
/* 281 */       if (pos >= buffer.writerIndex()) {
/* 282 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 284 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 285 */       if (!rulesResult.isValid()) {
/* 286 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 288 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 291 */     if ((nullBits & 0x8) != 0) {
/* 292 */       int tagsOffset = buffer.getIntLE(offset + 32);
/* 293 */       if (tagsOffset < 0) {
/* 294 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 296 */       int pos = offset + 40 + tagsOffset;
/* 297 */       if (pos >= buffer.writerIndex()) {
/* 298 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 300 */       int tagsCount = VarInt.peek(buffer, pos);
/* 301 */       if (tagsCount < 0) {
/* 302 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 304 */       if (tagsCount > 4096000) {
/* 305 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 307 */       pos += VarInt.length(buffer, pos);
/* 308 */       pos += tagsCount * 4;
/* 309 */       if (pos > buffer.writerIndex()) {
/* 310 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 314 */     if ((nullBits & 0x10) != 0) {
/* 315 */       int cameraOffset = buffer.getIntLE(offset + 36);
/* 316 */       if (cameraOffset < 0) {
/* 317 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 319 */       int pos = offset + 40 + cameraOffset;
/* 320 */       if (pos >= buffer.writerIndex()) {
/* 321 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 323 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 324 */       if (!cameraResult.isValid()) {
/* 325 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 327 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 329 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public PickBlockInteraction clone() {
/* 333 */     PickBlockInteraction copy = new PickBlockInteraction();
/* 334 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 335 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 336 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 337 */     copy.runTime = this.runTime;
/* 338 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 339 */     if (this.settings != null) {
/* 340 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 341 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 342 */       copy.settings = m;
/*     */     } 
/* 344 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 345 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 346 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 347 */     copy.next = this.next;
/* 348 */     copy.failed = this.failed;
/* 349 */     copy.useLatestTarget = this.useLatestTarget;
/* 350 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     PickBlockInteraction other;
/* 356 */     if (this == obj) return true; 
/* 357 */     if (obj instanceof PickBlockInteraction) { other = (PickBlockInteraction)obj; } else { return false; }
/* 358 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && this.useLatestTarget == other.useLatestTarget);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 363 */     int result = 1;
/* 364 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 365 */     result = 31 * result + Objects.hashCode(this.effects);
/* 366 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 367 */     result = 31 * result + Float.hashCode(this.runTime);
/* 368 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 369 */     result = 31 * result + Objects.hashCode(this.settings);
/* 370 */     result = 31 * result + Objects.hashCode(this.rules);
/* 371 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 372 */     result = 31 * result + Objects.hashCode(this.camera);
/* 373 */     result = 31 * result + Integer.hashCode(this.next);
/* 374 */     result = 31 * result + Integer.hashCode(this.failed);
/* 375 */     result = 31 * result + Boolean.hashCode(this.useLatestTarget);
/* 376 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\PickBlockInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */