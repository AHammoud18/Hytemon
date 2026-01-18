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
/*     */ public class RemoveEntityInteraction extends SimpleInteraction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 20;
/*     */   public static final int VARIABLE_FIELD_COUNT = 5;
/*     */   public static final int VARIABLE_BLOCK_START = 40;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nonnull
/*  20 */   public InteractionTarget entityTarget = InteractionTarget.User;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RemoveEntityInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, @Nonnull InteractionTarget entityTarget) {
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
/*  37 */     this.entityTarget = entityTarget;
/*     */   }
/*     */   
/*     */   public RemoveEntityInteraction(@Nonnull RemoveEntityInteraction other) {
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
/*  52 */     this.entityTarget = other.entityTarget;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static RemoveEntityInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  57 */     RemoveEntityInteraction obj = new RemoveEntityInteraction();
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  60 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  61 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  62 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  63 */     obj.next = buf.getIntLE(offset + 11);
/*  64 */     obj.failed = buf.getIntLE(offset + 15);
/*  65 */     obj.entityTarget = InteractionTarget.fromValue(buf.getByte(offset + 19));
/*     */     
/*  67 */     if ((nullBits & 0x1) != 0) {
/*  68 */       int varPos0 = offset + 40 + buf.getIntLE(offset + 20);
/*  69 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  71 */     if ((nullBits & 0x2) != 0) {
/*  72 */       int varPos1 = offset + 40 + buf.getIntLE(offset + 24);
/*  73 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  74 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  75 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  76 */       int varIntLen = VarInt.length(buf, varPos1);
/*  77 */       obj.settings = new HashMap<>(settingsCount);
/*  78 */       int dictPos = varPos1 + varIntLen;
/*  79 */       for (int i = 0; i < settingsCount; i++) {
/*  80 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  81 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/*  82 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/*  83 */         if (obj.settings.put(key, val) != null)
/*  84 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/*  87 */     if ((nullBits & 0x4) != 0) {
/*  88 */       int varPos2 = offset + 40 + buf.getIntLE(offset + 28);
/*  89 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/*  91 */     if ((nullBits & 0x8) != 0) {
/*  92 */       int varPos3 = offset + 40 + buf.getIntLE(offset + 32);
/*  93 */       int tagsCount = VarInt.peek(buf, varPos3);
/*  94 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/*  95 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/*  96 */       int varIntLen = VarInt.length(buf, varPos3);
/*  97 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/*  98 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/*  99 */       obj.tags = new int[tagsCount];
/* 100 */       for (int i = 0; i < tagsCount; i++) {
/* 101 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 104 */     if ((nullBits & 0x10) != 0) {
/* 105 */       int varPos4 = offset + 40 + buf.getIntLE(offset + 36);
/* 106 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/*     */     
/* 109 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 113 */     byte nullBits = buf.getByte(offset);
/* 114 */     int maxEnd = 40;
/* 115 */     if ((nullBits & 0x1) != 0) {
/* 116 */       int fieldOffset0 = buf.getIntLE(offset + 20);
/* 117 */       int pos0 = offset + 40 + fieldOffset0;
/* 118 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 119 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 121 */     if ((nullBits & 0x2) != 0) {
/* 122 */       int fieldOffset1 = buf.getIntLE(offset + 24);
/* 123 */       int pos1 = offset + 40 + fieldOffset1;
/* 124 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 125 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 126 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 128 */     if ((nullBits & 0x4) != 0) {
/* 129 */       int fieldOffset2 = buf.getIntLE(offset + 28);
/* 130 */       int pos2 = offset + 40 + fieldOffset2;
/* 131 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 132 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 134 */     if ((nullBits & 0x8) != 0) {
/* 135 */       int fieldOffset3 = buf.getIntLE(offset + 32);
/* 136 */       int pos3 = offset + 40 + fieldOffset3;
/* 137 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 138 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 140 */     if ((nullBits & 0x10) != 0) {
/* 141 */       int fieldOffset4 = buf.getIntLE(offset + 36);
/* 142 */       int pos4 = offset + 40 + fieldOffset4;
/* 143 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 144 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 146 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 152 */     int startPos = buf.writerIndex();
/* 153 */     byte nullBits = 0;
/* 154 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 155 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 156 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 157 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 158 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 159 */     buf.writeByte(nullBits);
/*     */     
/* 161 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 162 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 163 */     buf.writeFloatLE(this.runTime);
/* 164 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 165 */     buf.writeIntLE(this.next);
/* 166 */     buf.writeIntLE(this.failed);
/* 167 */     buf.writeByte(this.entityTarget.getValue());
/*     */     
/* 169 */     int effectsOffsetSlot = buf.writerIndex();
/* 170 */     buf.writeIntLE(0);
/* 171 */     int settingsOffsetSlot = buf.writerIndex();
/* 172 */     buf.writeIntLE(0);
/* 173 */     int rulesOffsetSlot = buf.writerIndex();
/* 174 */     buf.writeIntLE(0);
/* 175 */     int tagsOffsetSlot = buf.writerIndex();
/* 176 */     buf.writeIntLE(0);
/* 177 */     int cameraOffsetSlot = buf.writerIndex();
/* 178 */     buf.writeIntLE(0);
/*     */     
/* 180 */     int varBlockStart = buf.writerIndex();
/* 181 */     if (this.effects != null) {
/* 182 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 183 */       this.effects.serialize(buf);
/*     */     } else {
/* 185 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 187 */     if (this.settings != null)
/* 188 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 189 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 191 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 193 */     if (this.rules != null) {
/* 194 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 195 */       this.rules.serialize(buf);
/*     */     } else {
/* 197 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 199 */     if (this.tags != null) {
/* 200 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 201 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 203 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 205 */     if (this.camera != null) {
/* 206 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 207 */       this.camera.serialize(buf);
/*     */     } else {
/* 209 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 211 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 217 */     int size = 40;
/* 218 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 219 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 220 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 221 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 222 */     if (this.camera != null) size += this.camera.computeSize();
/*     */     
/* 224 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 228 */     if (buffer.readableBytes() - offset < 40) {
/* 229 */       return ValidationResult.error("Buffer too small: expected at least 40 bytes");
/*     */     }
/*     */     
/* 232 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 235 */     if ((nullBits & 0x1) != 0) {
/* 236 */       int effectsOffset = buffer.getIntLE(offset + 20);
/* 237 */       if (effectsOffset < 0) {
/* 238 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 240 */       int pos = offset + 40 + effectsOffset;
/* 241 */       if (pos >= buffer.writerIndex()) {
/* 242 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 244 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 245 */       if (!effectsResult.isValid()) {
/* 246 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 248 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 251 */     if ((nullBits & 0x2) != 0) {
/* 252 */       int settingsOffset = buffer.getIntLE(offset + 24);
/* 253 */       if (settingsOffset < 0) {
/* 254 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 256 */       int pos = offset + 40 + settingsOffset;
/* 257 */       if (pos >= buffer.writerIndex()) {
/* 258 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 260 */       int settingsCount = VarInt.peek(buffer, pos);
/* 261 */       if (settingsCount < 0) {
/* 262 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 264 */       if (settingsCount > 4096000) {
/* 265 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 267 */       pos += VarInt.length(buffer, pos);
/* 268 */       for (int i = 0; i < settingsCount; i++) {
/* 269 */         pos++;
/*     */         
/* 271 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 276 */     if ((nullBits & 0x4) != 0) {
/* 277 */       int rulesOffset = buffer.getIntLE(offset + 28);
/* 278 */       if (rulesOffset < 0) {
/* 279 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 281 */       int pos = offset + 40 + rulesOffset;
/* 282 */       if (pos >= buffer.writerIndex()) {
/* 283 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 285 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 286 */       if (!rulesResult.isValid()) {
/* 287 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 289 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 292 */     if ((nullBits & 0x8) != 0) {
/* 293 */       int tagsOffset = buffer.getIntLE(offset + 32);
/* 294 */       if (tagsOffset < 0) {
/* 295 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 297 */       int pos = offset + 40 + tagsOffset;
/* 298 */       if (pos >= buffer.writerIndex()) {
/* 299 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 301 */       int tagsCount = VarInt.peek(buffer, pos);
/* 302 */       if (tagsCount < 0) {
/* 303 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 305 */       if (tagsCount > 4096000) {
/* 306 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 308 */       pos += VarInt.length(buffer, pos);
/* 309 */       pos += tagsCount * 4;
/* 310 */       if (pos > buffer.writerIndex()) {
/* 311 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 315 */     if ((nullBits & 0x10) != 0) {
/* 316 */       int cameraOffset = buffer.getIntLE(offset + 36);
/* 317 */       if (cameraOffset < 0) {
/* 318 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 320 */       int pos = offset + 40 + cameraOffset;
/* 321 */       if (pos >= buffer.writerIndex()) {
/* 322 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 324 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 325 */       if (!cameraResult.isValid()) {
/* 326 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 328 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 330 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public RemoveEntityInteraction clone() {
/* 334 */     RemoveEntityInteraction copy = new RemoveEntityInteraction();
/* 335 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 336 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 337 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 338 */     copy.runTime = this.runTime;
/* 339 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 340 */     if (this.settings != null) {
/* 341 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 342 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 343 */       copy.settings = m;
/*     */     } 
/* 345 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 346 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 347 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 348 */     copy.next = this.next;
/* 349 */     copy.failed = this.failed;
/* 350 */     copy.entityTarget = this.entityTarget;
/* 351 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     RemoveEntityInteraction other;
/* 357 */     if (this == obj) return true; 
/* 358 */     if (obj instanceof RemoveEntityInteraction) { other = (RemoveEntityInteraction)obj; } else { return false; }
/* 359 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && Objects.equals(this.entityTarget, other.entityTarget));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 364 */     int result = 1;
/* 365 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 366 */     result = 31 * result + Objects.hashCode(this.effects);
/* 367 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 368 */     result = 31 * result + Float.hashCode(this.runTime);
/* 369 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 370 */     result = 31 * result + Objects.hashCode(this.settings);
/* 371 */     result = 31 * result + Objects.hashCode(this.rules);
/* 372 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 373 */     result = 31 * result + Objects.hashCode(this.camera);
/* 374 */     result = 31 * result + Integer.hashCode(this.next);
/* 375 */     result = 31 * result + Integer.hashCode(this.failed);
/* 376 */     result = 31 * result + Objects.hashCode(this.entityTarget);
/* 377 */     return result;
/*     */   }
/*     */   
/*     */   public RemoveEntityInteraction() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\RemoveEntityInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */