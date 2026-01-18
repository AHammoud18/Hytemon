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
/*     */ public class ChangeActiveSlotInteraction extends Interaction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 15;
/*     */   public static final int VARIABLE_FIELD_COUNT = 5;
/*     */   public static final int VARIABLE_BLOCK_START = 35;
/*     */   public static final int MAX_SIZE = 1677721600;
/*  20 */   public int targetSlot = Integer.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChangeActiveSlotInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int targetSlot) {
/*  26 */     this.waitForDataFrom = waitForDataFrom;
/*  27 */     this.effects = effects;
/*  28 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  29 */     this.runTime = runTime;
/*  30 */     this.cancelOnItemChange = cancelOnItemChange;
/*  31 */     this.settings = settings;
/*  32 */     this.rules = rules;
/*  33 */     this.tags = tags;
/*  34 */     this.camera = camera;
/*  35 */     this.targetSlot = targetSlot;
/*     */   }
/*     */   
/*     */   public ChangeActiveSlotInteraction(@Nonnull ChangeActiveSlotInteraction other) {
/*  39 */     this.waitForDataFrom = other.waitForDataFrom;
/*  40 */     this.effects = other.effects;
/*  41 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  42 */     this.runTime = other.runTime;
/*  43 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  44 */     this.settings = other.settings;
/*  45 */     this.rules = other.rules;
/*  46 */     this.tags = other.tags;
/*  47 */     this.camera = other.camera;
/*  48 */     this.targetSlot = other.targetSlot;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ChangeActiveSlotInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  53 */     ChangeActiveSlotInteraction obj = new ChangeActiveSlotInteraction();
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  56 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  57 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  58 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  59 */     obj.targetSlot = buf.getIntLE(offset + 11);
/*     */     
/*  61 */     if ((nullBits & 0x1) != 0) {
/*  62 */       int varPos0 = offset + 35 + buf.getIntLE(offset + 15);
/*  63 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  65 */     if ((nullBits & 0x2) != 0) {
/*  66 */       int varPos1 = offset + 35 + buf.getIntLE(offset + 19);
/*  67 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  68 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  69 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  70 */       int varIntLen = VarInt.length(buf, varPos1);
/*  71 */       obj.settings = new HashMap<>(settingsCount);
/*  72 */       int dictPos = varPos1 + varIntLen;
/*  73 */       for (int i = 0; i < settingsCount; i++) {
/*  74 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  75 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/*  76 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/*  77 */         if (obj.settings.put(key, val) != null)
/*  78 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/*  81 */     if ((nullBits & 0x4) != 0) {
/*  82 */       int varPos2 = offset + 35 + buf.getIntLE(offset + 23);
/*  83 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/*  85 */     if ((nullBits & 0x8) != 0) {
/*  86 */       int varPos3 = offset + 35 + buf.getIntLE(offset + 27);
/*  87 */       int tagsCount = VarInt.peek(buf, varPos3);
/*  88 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/*  89 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/*  90 */       int varIntLen = VarInt.length(buf, varPos3);
/*  91 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/*  92 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/*  93 */       obj.tags = new int[tagsCount];
/*  94 */       for (int i = 0; i < tagsCount; i++) {
/*  95 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/*  98 */     if ((nullBits & 0x10) != 0) {
/*  99 */       int varPos4 = offset + 35 + buf.getIntLE(offset + 31);
/* 100 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/*     */     
/* 103 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 107 */     byte nullBits = buf.getByte(offset);
/* 108 */     int maxEnd = 35;
/* 109 */     if ((nullBits & 0x1) != 0) {
/* 110 */       int fieldOffset0 = buf.getIntLE(offset + 15);
/* 111 */       int pos0 = offset + 35 + fieldOffset0;
/* 112 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 113 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 115 */     if ((nullBits & 0x2) != 0) {
/* 116 */       int fieldOffset1 = buf.getIntLE(offset + 19);
/* 117 */       int pos1 = offset + 35 + fieldOffset1;
/* 118 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 119 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 120 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 122 */     if ((nullBits & 0x4) != 0) {
/* 123 */       int fieldOffset2 = buf.getIntLE(offset + 23);
/* 124 */       int pos2 = offset + 35 + fieldOffset2;
/* 125 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 126 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 128 */     if ((nullBits & 0x8) != 0) {
/* 129 */       int fieldOffset3 = buf.getIntLE(offset + 27);
/* 130 */       int pos3 = offset + 35 + fieldOffset3;
/* 131 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 132 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 134 */     if ((nullBits & 0x10) != 0) {
/* 135 */       int fieldOffset4 = buf.getIntLE(offset + 31);
/* 136 */       int pos4 = offset + 35 + fieldOffset4;
/* 137 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 138 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 140 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 146 */     int startPos = buf.writerIndex();
/* 147 */     byte nullBits = 0;
/* 148 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 149 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 150 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 151 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 152 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 153 */     buf.writeByte(nullBits);
/*     */     
/* 155 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 156 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 157 */     buf.writeFloatLE(this.runTime);
/* 158 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 159 */     buf.writeIntLE(this.targetSlot);
/*     */     
/* 161 */     int effectsOffsetSlot = buf.writerIndex();
/* 162 */     buf.writeIntLE(0);
/* 163 */     int settingsOffsetSlot = buf.writerIndex();
/* 164 */     buf.writeIntLE(0);
/* 165 */     int rulesOffsetSlot = buf.writerIndex();
/* 166 */     buf.writeIntLE(0);
/* 167 */     int tagsOffsetSlot = buf.writerIndex();
/* 168 */     buf.writeIntLE(0);
/* 169 */     int cameraOffsetSlot = buf.writerIndex();
/* 170 */     buf.writeIntLE(0);
/*     */     
/* 172 */     int varBlockStart = buf.writerIndex();
/* 173 */     if (this.effects != null) {
/* 174 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 175 */       this.effects.serialize(buf);
/*     */     } else {
/* 177 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 179 */     if (this.settings != null)
/* 180 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 181 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 183 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 185 */     if (this.rules != null) {
/* 186 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 187 */       this.rules.serialize(buf);
/*     */     } else {
/* 189 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 191 */     if (this.tags != null) {
/* 192 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 193 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 195 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 197 */     if (this.camera != null) {
/* 198 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 199 */       this.camera.serialize(buf);
/*     */     } else {
/* 201 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 203 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 209 */     int size = 35;
/* 210 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 211 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 212 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 213 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 214 */     if (this.camera != null) size += this.camera.computeSize();
/*     */     
/* 216 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 220 */     if (buffer.readableBytes() - offset < 35) {
/* 221 */       return ValidationResult.error("Buffer too small: expected at least 35 bytes");
/*     */     }
/*     */     
/* 224 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 227 */     if ((nullBits & 0x1) != 0) {
/* 228 */       int effectsOffset = buffer.getIntLE(offset + 15);
/* 229 */       if (effectsOffset < 0) {
/* 230 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 232 */       int pos = offset + 35 + effectsOffset;
/* 233 */       if (pos >= buffer.writerIndex()) {
/* 234 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 236 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 237 */       if (!effectsResult.isValid()) {
/* 238 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 240 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 243 */     if ((nullBits & 0x2) != 0) {
/* 244 */       int settingsOffset = buffer.getIntLE(offset + 19);
/* 245 */       if (settingsOffset < 0) {
/* 246 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 248 */       int pos = offset + 35 + settingsOffset;
/* 249 */       if (pos >= buffer.writerIndex()) {
/* 250 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 252 */       int settingsCount = VarInt.peek(buffer, pos);
/* 253 */       if (settingsCount < 0) {
/* 254 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 256 */       if (settingsCount > 4096000) {
/* 257 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 259 */       pos += VarInt.length(buffer, pos);
/* 260 */       for (int i = 0; i < settingsCount; i++) {
/* 261 */         pos++;
/*     */         
/* 263 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 268 */     if ((nullBits & 0x4) != 0) {
/* 269 */       int rulesOffset = buffer.getIntLE(offset + 23);
/* 270 */       if (rulesOffset < 0) {
/* 271 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 273 */       int pos = offset + 35 + rulesOffset;
/* 274 */       if (pos >= buffer.writerIndex()) {
/* 275 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 277 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 278 */       if (!rulesResult.isValid()) {
/* 279 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 281 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 284 */     if ((nullBits & 0x8) != 0) {
/* 285 */       int tagsOffset = buffer.getIntLE(offset + 27);
/* 286 */       if (tagsOffset < 0) {
/* 287 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 289 */       int pos = offset + 35 + tagsOffset;
/* 290 */       if (pos >= buffer.writerIndex()) {
/* 291 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 293 */       int tagsCount = VarInt.peek(buffer, pos);
/* 294 */       if (tagsCount < 0) {
/* 295 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 297 */       if (tagsCount > 4096000) {
/* 298 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 300 */       pos += VarInt.length(buffer, pos);
/* 301 */       pos += tagsCount * 4;
/* 302 */       if (pos > buffer.writerIndex()) {
/* 303 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 307 */     if ((nullBits & 0x10) != 0) {
/* 308 */       int cameraOffset = buffer.getIntLE(offset + 31);
/* 309 */       if (cameraOffset < 0) {
/* 310 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 312 */       int pos = offset + 35 + cameraOffset;
/* 313 */       if (pos >= buffer.writerIndex()) {
/* 314 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 316 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 317 */       if (!cameraResult.isValid()) {
/* 318 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 320 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 322 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ChangeActiveSlotInteraction clone() {
/* 326 */     ChangeActiveSlotInteraction copy = new ChangeActiveSlotInteraction();
/* 327 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 328 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 329 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 330 */     copy.runTime = this.runTime;
/* 331 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 332 */     if (this.settings != null) {
/* 333 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 334 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 335 */       copy.settings = m;
/*     */     } 
/* 337 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 338 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 339 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 340 */     copy.targetSlot = this.targetSlot;
/* 341 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ChangeActiveSlotInteraction other;
/* 347 */     if (this == obj) return true; 
/* 348 */     if (obj instanceof ChangeActiveSlotInteraction) { other = (ChangeActiveSlotInteraction)obj; } else { return false; }
/* 349 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.targetSlot == other.targetSlot);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 354 */     int result = 1;
/* 355 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 356 */     result = 31 * result + Objects.hashCode(this.effects);
/* 357 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 358 */     result = 31 * result + Float.hashCode(this.runTime);
/* 359 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 360 */     result = 31 * result + Objects.hashCode(this.settings);
/* 361 */     result = 31 * result + Objects.hashCode(this.rules);
/* 362 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 363 */     result = 31 * result + Objects.hashCode(this.camera);
/* 364 */     result = 31 * result + Integer.hashCode(this.targetSlot);
/* 365 */     return result;
/*     */   }
/*     */   
/*     */   public ChangeActiveSlotInteraction() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ChangeActiveSlotInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */