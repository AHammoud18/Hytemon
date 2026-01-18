/*     */ package com.hypixel.hytale.protocol;
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
/*     */ public class CameraInteraction extends SimpleInteraction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 26;
/*     */   public static final int VARIABLE_FIELD_COUNT = 5;
/*     */   public static final int VARIABLE_BLOCK_START = 46;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nonnull
/*  20 */   public CameraActionType cameraAction = CameraActionType.ForcePerspective; @Nonnull
/*  21 */   public CameraPerspectiveType cameraPerspective = CameraPerspectiveType.First;
/*     */   
/*     */   public boolean cameraPersist;
/*     */   
/*     */   public float cameraInteractionTime;
/*     */ 
/*     */   
/*     */   public CameraInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, @Nonnull CameraActionType cameraAction, @Nonnull CameraPerspectiveType cameraPerspective, boolean cameraPersist, float cameraInteractionTime) {
/*  29 */     this.waitForDataFrom = waitForDataFrom;
/*  30 */     this.effects = effects;
/*  31 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  32 */     this.runTime = runTime;
/*  33 */     this.cancelOnItemChange = cancelOnItemChange;
/*  34 */     this.settings = settings;
/*  35 */     this.rules = rules;
/*  36 */     this.tags = tags;
/*  37 */     this.camera = camera;
/*  38 */     this.next = next;
/*  39 */     this.failed = failed;
/*  40 */     this.cameraAction = cameraAction;
/*  41 */     this.cameraPerspective = cameraPerspective;
/*  42 */     this.cameraPersist = cameraPersist;
/*  43 */     this.cameraInteractionTime = cameraInteractionTime;
/*     */   }
/*     */   
/*     */   public CameraInteraction(@Nonnull CameraInteraction other) {
/*  47 */     this.waitForDataFrom = other.waitForDataFrom;
/*  48 */     this.effects = other.effects;
/*  49 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  50 */     this.runTime = other.runTime;
/*  51 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  52 */     this.settings = other.settings;
/*  53 */     this.rules = other.rules;
/*  54 */     this.tags = other.tags;
/*  55 */     this.camera = other.camera;
/*  56 */     this.next = other.next;
/*  57 */     this.failed = other.failed;
/*  58 */     this.cameraAction = other.cameraAction;
/*  59 */     this.cameraPerspective = other.cameraPerspective;
/*  60 */     this.cameraPersist = other.cameraPersist;
/*  61 */     this.cameraInteractionTime = other.cameraInteractionTime;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CameraInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  66 */     CameraInteraction obj = new CameraInteraction();
/*  67 */     byte nullBits = buf.getByte(offset);
/*  68 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  69 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  70 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  71 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  72 */     obj.next = buf.getIntLE(offset + 11);
/*  73 */     obj.failed = buf.getIntLE(offset + 15);
/*  74 */     obj.cameraAction = CameraActionType.fromValue(buf.getByte(offset + 19));
/*  75 */     obj.cameraPerspective = CameraPerspectiveType.fromValue(buf.getByte(offset + 20));
/*  76 */     obj.cameraPersist = (buf.getByte(offset + 21) != 0);
/*  77 */     obj.cameraInteractionTime = buf.getFloatLE(offset + 22);
/*     */     
/*  79 */     if ((nullBits & 0x1) != 0) {
/*  80 */       int varPos0 = offset + 46 + buf.getIntLE(offset + 26);
/*  81 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  83 */     if ((nullBits & 0x2) != 0) {
/*  84 */       int varPos1 = offset + 46 + buf.getIntLE(offset + 30);
/*  85 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  86 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  87 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  88 */       int varIntLen = VarInt.length(buf, varPos1);
/*  89 */       obj.settings = new HashMap<>(settingsCount);
/*  90 */       int dictPos = varPos1 + varIntLen;
/*  91 */       for (int i = 0; i < settingsCount; i++) {
/*  92 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  93 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/*  94 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/*  95 */         if (obj.settings.put(key, val) != null)
/*  96 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/*  99 */     if ((nullBits & 0x4) != 0) {
/* 100 */       int varPos2 = offset + 46 + buf.getIntLE(offset + 34);
/* 101 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/* 103 */     if ((nullBits & 0x8) != 0) {
/* 104 */       int varPos3 = offset + 46 + buf.getIntLE(offset + 38);
/* 105 */       int tagsCount = VarInt.peek(buf, varPos3);
/* 106 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/* 107 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/* 108 */       int varIntLen = VarInt.length(buf, varPos3);
/* 109 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/* 110 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/* 111 */       obj.tags = new int[tagsCount];
/* 112 */       for (int i = 0; i < tagsCount; i++) {
/* 113 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 116 */     if ((nullBits & 0x10) != 0) {
/* 117 */       int varPos4 = offset + 46 + buf.getIntLE(offset + 42);
/* 118 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/*     */     
/* 121 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 125 */     byte nullBits = buf.getByte(offset);
/* 126 */     int maxEnd = 46;
/* 127 */     if ((nullBits & 0x1) != 0) {
/* 128 */       int fieldOffset0 = buf.getIntLE(offset + 26);
/* 129 */       int pos0 = offset + 46 + fieldOffset0;
/* 130 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 131 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 133 */     if ((nullBits & 0x2) != 0) {
/* 134 */       int fieldOffset1 = buf.getIntLE(offset + 30);
/* 135 */       int pos1 = offset + 46 + fieldOffset1;
/* 136 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 137 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 138 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 140 */     if ((nullBits & 0x4) != 0) {
/* 141 */       int fieldOffset2 = buf.getIntLE(offset + 34);
/* 142 */       int pos2 = offset + 46 + fieldOffset2;
/* 143 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 144 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 146 */     if ((nullBits & 0x8) != 0) {
/* 147 */       int fieldOffset3 = buf.getIntLE(offset + 38);
/* 148 */       int pos3 = offset + 46 + fieldOffset3;
/* 149 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 150 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 152 */     if ((nullBits & 0x10) != 0) {
/* 153 */       int fieldOffset4 = buf.getIntLE(offset + 42);
/* 154 */       int pos4 = offset + 46 + fieldOffset4;
/* 155 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 156 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
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
/* 171 */     buf.writeByte(nullBits);
/*     */     
/* 173 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 174 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 175 */     buf.writeFloatLE(this.runTime);
/* 176 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 177 */     buf.writeIntLE(this.next);
/* 178 */     buf.writeIntLE(this.failed);
/* 179 */     buf.writeByte(this.cameraAction.getValue());
/* 180 */     buf.writeByte(this.cameraPerspective.getValue());
/* 181 */     buf.writeByte(this.cameraPersist ? 1 : 0);
/* 182 */     buf.writeFloatLE(this.cameraInteractionTime);
/*     */     
/* 184 */     int effectsOffsetSlot = buf.writerIndex();
/* 185 */     buf.writeIntLE(0);
/* 186 */     int settingsOffsetSlot = buf.writerIndex();
/* 187 */     buf.writeIntLE(0);
/* 188 */     int rulesOffsetSlot = buf.writerIndex();
/* 189 */     buf.writeIntLE(0);
/* 190 */     int tagsOffsetSlot = buf.writerIndex();
/* 191 */     buf.writeIntLE(0);
/* 192 */     int cameraOffsetSlot = buf.writerIndex();
/* 193 */     buf.writeIntLE(0);
/*     */     
/* 195 */     int varBlockStart = buf.writerIndex();
/* 196 */     if (this.effects != null) {
/* 197 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 198 */       this.effects.serialize(buf);
/*     */     } else {
/* 200 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 202 */     if (this.settings != null)
/* 203 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 204 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 206 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 208 */     if (this.rules != null) {
/* 209 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 210 */       this.rules.serialize(buf);
/*     */     } else {
/* 212 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 214 */     if (this.tags != null) {
/* 215 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 216 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 218 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 220 */     if (this.camera != null) {
/* 221 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 222 */       this.camera.serialize(buf);
/*     */     } else {
/* 224 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 226 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 232 */     int size = 46;
/* 233 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 234 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 235 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 236 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 237 */     if (this.camera != null) size += this.camera.computeSize();
/*     */     
/* 239 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 243 */     if (buffer.readableBytes() - offset < 46) {
/* 244 */       return ValidationResult.error("Buffer too small: expected at least 46 bytes");
/*     */     }
/*     */     
/* 247 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 250 */     if ((nullBits & 0x1) != 0) {
/* 251 */       int effectsOffset = buffer.getIntLE(offset + 26);
/* 252 */       if (effectsOffset < 0) {
/* 253 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 255 */       int pos = offset + 46 + effectsOffset;
/* 256 */       if (pos >= buffer.writerIndex()) {
/* 257 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 259 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 260 */       if (!effectsResult.isValid()) {
/* 261 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 263 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 266 */     if ((nullBits & 0x2) != 0) {
/* 267 */       int settingsOffset = buffer.getIntLE(offset + 30);
/* 268 */       if (settingsOffset < 0) {
/* 269 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 271 */       int pos = offset + 46 + settingsOffset;
/* 272 */       if (pos >= buffer.writerIndex()) {
/* 273 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 275 */       int settingsCount = VarInt.peek(buffer, pos);
/* 276 */       if (settingsCount < 0) {
/* 277 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 279 */       if (settingsCount > 4096000) {
/* 280 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 282 */       pos += VarInt.length(buffer, pos);
/* 283 */       for (int i = 0; i < settingsCount; i++) {
/* 284 */         pos++;
/*     */         
/* 286 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 291 */     if ((nullBits & 0x4) != 0) {
/* 292 */       int rulesOffset = buffer.getIntLE(offset + 34);
/* 293 */       if (rulesOffset < 0) {
/* 294 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 296 */       int pos = offset + 46 + rulesOffset;
/* 297 */       if (pos >= buffer.writerIndex()) {
/* 298 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 300 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 301 */       if (!rulesResult.isValid()) {
/* 302 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 304 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 307 */     if ((nullBits & 0x8) != 0) {
/* 308 */       int tagsOffset = buffer.getIntLE(offset + 38);
/* 309 */       if (tagsOffset < 0) {
/* 310 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 312 */       int pos = offset + 46 + tagsOffset;
/* 313 */       if (pos >= buffer.writerIndex()) {
/* 314 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 316 */       int tagsCount = VarInt.peek(buffer, pos);
/* 317 */       if (tagsCount < 0) {
/* 318 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 320 */       if (tagsCount > 4096000) {
/* 321 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 323 */       pos += VarInt.length(buffer, pos);
/* 324 */       pos += tagsCount * 4;
/* 325 */       if (pos > buffer.writerIndex()) {
/* 326 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 330 */     if ((nullBits & 0x10) != 0) {
/* 331 */       int cameraOffset = buffer.getIntLE(offset + 42);
/* 332 */       if (cameraOffset < 0) {
/* 333 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 335 */       int pos = offset + 46 + cameraOffset;
/* 336 */       if (pos >= buffer.writerIndex()) {
/* 337 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 339 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 340 */       if (!cameraResult.isValid()) {
/* 341 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 343 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 345 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public CameraInteraction clone() {
/* 349 */     CameraInteraction copy = new CameraInteraction();
/* 350 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 351 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 352 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 353 */     copy.runTime = this.runTime;
/* 354 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 355 */     if (this.settings != null) {
/* 356 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 357 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 358 */       copy.settings = m;
/*     */     } 
/* 360 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 361 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 362 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 363 */     copy.next = this.next;
/* 364 */     copy.failed = this.failed;
/* 365 */     copy.cameraAction = this.cameraAction;
/* 366 */     copy.cameraPerspective = this.cameraPerspective;
/* 367 */     copy.cameraPersist = this.cameraPersist;
/* 368 */     copy.cameraInteractionTime = this.cameraInteractionTime;
/* 369 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     CameraInteraction other;
/* 375 */     if (this == obj) return true; 
/* 376 */     if (obj instanceof CameraInteraction) { other = (CameraInteraction)obj; } else { return false; }
/* 377 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && Objects.equals(this.cameraAction, other.cameraAction) && Objects.equals(this.cameraPerspective, other.cameraPerspective) && this.cameraPersist == other.cameraPersist && this.cameraInteractionTime == other.cameraInteractionTime);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 382 */     int result = 1;
/* 383 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 384 */     result = 31 * result + Objects.hashCode(this.effects);
/* 385 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 386 */     result = 31 * result + Float.hashCode(this.runTime);
/* 387 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 388 */     result = 31 * result + Objects.hashCode(this.settings);
/* 389 */     result = 31 * result + Objects.hashCode(this.rules);
/* 390 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 391 */     result = 31 * result + Objects.hashCode(this.camera);
/* 392 */     result = 31 * result + Integer.hashCode(this.next);
/* 393 */     result = 31 * result + Integer.hashCode(this.failed);
/* 394 */     result = 31 * result + Objects.hashCode(this.cameraAction);
/* 395 */     result = 31 * result + Objects.hashCode(this.cameraPerspective);
/* 396 */     result = 31 * result + Boolean.hashCode(this.cameraPersist);
/* 397 */     result = 31 * result + Float.hashCode(this.cameraInteractionTime);
/* 398 */     return result;
/*     */   }
/*     */   
/*     */   public CameraInteraction() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\CameraInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */