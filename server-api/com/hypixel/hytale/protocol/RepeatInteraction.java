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
/*     */ public class RepeatInteraction
/*     */   extends SimpleInteraction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 27;
/*     */   public static final int VARIABLE_FIELD_COUNT = 5;
/*     */   public static final int VARIABLE_BLOCK_START = 47;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   public int forkInteractions;
/*     */   public int repeat;
/*     */   
/*     */   public RepeatInteraction() {}
/*     */   
/*     */   public RepeatInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, int forkInteractions, int repeat) {
/*  27 */     this.waitForDataFrom = waitForDataFrom;
/*  28 */     this.effects = effects;
/*  29 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  30 */     this.runTime = runTime;
/*  31 */     this.cancelOnItemChange = cancelOnItemChange;
/*  32 */     this.settings = settings;
/*  33 */     this.rules = rules;
/*  34 */     this.tags = tags;
/*  35 */     this.camera = camera;
/*  36 */     this.next = next;
/*  37 */     this.failed = failed;
/*  38 */     this.forkInteractions = forkInteractions;
/*  39 */     this.repeat = repeat;
/*     */   }
/*     */   
/*     */   public RepeatInteraction(@Nonnull RepeatInteraction other) {
/*  43 */     this.waitForDataFrom = other.waitForDataFrom;
/*  44 */     this.effects = other.effects;
/*  45 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  46 */     this.runTime = other.runTime;
/*  47 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  48 */     this.settings = other.settings;
/*  49 */     this.rules = other.rules;
/*  50 */     this.tags = other.tags;
/*  51 */     this.camera = other.camera;
/*  52 */     this.next = other.next;
/*  53 */     this.failed = other.failed;
/*  54 */     this.forkInteractions = other.forkInteractions;
/*  55 */     this.repeat = other.repeat;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static RepeatInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  60 */     RepeatInteraction obj = new RepeatInteraction();
/*  61 */     byte nullBits = buf.getByte(offset);
/*  62 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  63 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  64 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  65 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  66 */     obj.next = buf.getIntLE(offset + 11);
/*  67 */     obj.failed = buf.getIntLE(offset + 15);
/*  68 */     obj.forkInteractions = buf.getIntLE(offset + 19);
/*  69 */     obj.repeat = buf.getIntLE(offset + 23);
/*     */     
/*  71 */     if ((nullBits & 0x1) != 0) {
/*  72 */       int varPos0 = offset + 47 + buf.getIntLE(offset + 27);
/*  73 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  75 */     if ((nullBits & 0x2) != 0) {
/*  76 */       int varPos1 = offset + 47 + buf.getIntLE(offset + 31);
/*  77 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  78 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  79 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  80 */       int varIntLen = VarInt.length(buf, varPos1);
/*  81 */       obj.settings = new HashMap<>(settingsCount);
/*  82 */       int dictPos = varPos1 + varIntLen;
/*  83 */       for (int i = 0; i < settingsCount; i++) {
/*  84 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  85 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/*  86 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/*  87 */         if (obj.settings.put(key, val) != null)
/*  88 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/*  91 */     if ((nullBits & 0x4) != 0) {
/*  92 */       int varPos2 = offset + 47 + buf.getIntLE(offset + 35);
/*  93 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/*  95 */     if ((nullBits & 0x8) != 0) {
/*  96 */       int varPos3 = offset + 47 + buf.getIntLE(offset + 39);
/*  97 */       int tagsCount = VarInt.peek(buf, varPos3);
/*  98 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/*  99 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/* 100 */       int varIntLen = VarInt.length(buf, varPos3);
/* 101 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/* 102 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/* 103 */       obj.tags = new int[tagsCount];
/* 104 */       for (int i = 0; i < tagsCount; i++) {
/* 105 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 108 */     if ((nullBits & 0x10) != 0) {
/* 109 */       int varPos4 = offset + 47 + buf.getIntLE(offset + 43);
/* 110 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/*     */     
/* 113 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 117 */     byte nullBits = buf.getByte(offset);
/* 118 */     int maxEnd = 47;
/* 119 */     if ((nullBits & 0x1) != 0) {
/* 120 */       int fieldOffset0 = buf.getIntLE(offset + 27);
/* 121 */       int pos0 = offset + 47 + fieldOffset0;
/* 122 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 123 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 125 */     if ((nullBits & 0x2) != 0) {
/* 126 */       int fieldOffset1 = buf.getIntLE(offset + 31);
/* 127 */       int pos1 = offset + 47 + fieldOffset1;
/* 128 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 129 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 130 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 132 */     if ((nullBits & 0x4) != 0) {
/* 133 */       int fieldOffset2 = buf.getIntLE(offset + 35);
/* 134 */       int pos2 = offset + 47 + fieldOffset2;
/* 135 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 136 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 138 */     if ((nullBits & 0x8) != 0) {
/* 139 */       int fieldOffset3 = buf.getIntLE(offset + 39);
/* 140 */       int pos3 = offset + 47 + fieldOffset3;
/* 141 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 142 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 144 */     if ((nullBits & 0x10) != 0) {
/* 145 */       int fieldOffset4 = buf.getIntLE(offset + 43);
/* 146 */       int pos4 = offset + 47 + fieldOffset4;
/* 147 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 148 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 150 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 156 */     int startPos = buf.writerIndex();
/* 157 */     byte nullBits = 0;
/* 158 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 159 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 160 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 161 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 162 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 163 */     buf.writeByte(nullBits);
/*     */     
/* 165 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 166 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 167 */     buf.writeFloatLE(this.runTime);
/* 168 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 169 */     buf.writeIntLE(this.next);
/* 170 */     buf.writeIntLE(this.failed);
/* 171 */     buf.writeIntLE(this.forkInteractions);
/* 172 */     buf.writeIntLE(this.repeat);
/*     */     
/* 174 */     int effectsOffsetSlot = buf.writerIndex();
/* 175 */     buf.writeIntLE(0);
/* 176 */     int settingsOffsetSlot = buf.writerIndex();
/* 177 */     buf.writeIntLE(0);
/* 178 */     int rulesOffsetSlot = buf.writerIndex();
/* 179 */     buf.writeIntLE(0);
/* 180 */     int tagsOffsetSlot = buf.writerIndex();
/* 181 */     buf.writeIntLE(0);
/* 182 */     int cameraOffsetSlot = buf.writerIndex();
/* 183 */     buf.writeIntLE(0);
/*     */     
/* 185 */     int varBlockStart = buf.writerIndex();
/* 186 */     if (this.effects != null) {
/* 187 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 188 */       this.effects.serialize(buf);
/*     */     } else {
/* 190 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 192 */     if (this.settings != null)
/* 193 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 194 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 196 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 198 */     if (this.rules != null) {
/* 199 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 200 */       this.rules.serialize(buf);
/*     */     } else {
/* 202 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 204 */     if (this.tags != null) {
/* 205 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 206 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 208 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 210 */     if (this.camera != null) {
/* 211 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 212 */       this.camera.serialize(buf);
/*     */     } else {
/* 214 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 216 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 222 */     int size = 47;
/* 223 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 224 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 225 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 226 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 227 */     if (this.camera != null) size += this.camera.computeSize();
/*     */     
/* 229 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 233 */     if (buffer.readableBytes() - offset < 47) {
/* 234 */       return ValidationResult.error("Buffer too small: expected at least 47 bytes");
/*     */     }
/*     */     
/* 237 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 240 */     if ((nullBits & 0x1) != 0) {
/* 241 */       int effectsOffset = buffer.getIntLE(offset + 27);
/* 242 */       if (effectsOffset < 0) {
/* 243 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 245 */       int pos = offset + 47 + effectsOffset;
/* 246 */       if (pos >= buffer.writerIndex()) {
/* 247 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 249 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 250 */       if (!effectsResult.isValid()) {
/* 251 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 253 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 256 */     if ((nullBits & 0x2) != 0) {
/* 257 */       int settingsOffset = buffer.getIntLE(offset + 31);
/* 258 */       if (settingsOffset < 0) {
/* 259 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 261 */       int pos = offset + 47 + settingsOffset;
/* 262 */       if (pos >= buffer.writerIndex()) {
/* 263 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 265 */       int settingsCount = VarInt.peek(buffer, pos);
/* 266 */       if (settingsCount < 0) {
/* 267 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 269 */       if (settingsCount > 4096000) {
/* 270 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 272 */       pos += VarInt.length(buffer, pos);
/* 273 */       for (int i = 0; i < settingsCount; i++) {
/* 274 */         pos++;
/*     */         
/* 276 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 281 */     if ((nullBits & 0x4) != 0) {
/* 282 */       int rulesOffset = buffer.getIntLE(offset + 35);
/* 283 */       if (rulesOffset < 0) {
/* 284 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 286 */       int pos = offset + 47 + rulesOffset;
/* 287 */       if (pos >= buffer.writerIndex()) {
/* 288 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 290 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 291 */       if (!rulesResult.isValid()) {
/* 292 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 294 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 297 */     if ((nullBits & 0x8) != 0) {
/* 298 */       int tagsOffset = buffer.getIntLE(offset + 39);
/* 299 */       if (tagsOffset < 0) {
/* 300 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 302 */       int pos = offset + 47 + tagsOffset;
/* 303 */       if (pos >= buffer.writerIndex()) {
/* 304 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 306 */       int tagsCount = VarInt.peek(buffer, pos);
/* 307 */       if (tagsCount < 0) {
/* 308 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 310 */       if (tagsCount > 4096000) {
/* 311 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 313 */       pos += VarInt.length(buffer, pos);
/* 314 */       pos += tagsCount * 4;
/* 315 */       if (pos > buffer.writerIndex()) {
/* 316 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 320 */     if ((nullBits & 0x10) != 0) {
/* 321 */       int cameraOffset = buffer.getIntLE(offset + 43);
/* 322 */       if (cameraOffset < 0) {
/* 323 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 325 */       int pos = offset + 47 + cameraOffset;
/* 326 */       if (pos >= buffer.writerIndex()) {
/* 327 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 329 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 330 */       if (!cameraResult.isValid()) {
/* 331 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 333 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 335 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public RepeatInteraction clone() {
/* 339 */     RepeatInteraction copy = new RepeatInteraction();
/* 340 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 341 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 342 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 343 */     copy.runTime = this.runTime;
/* 344 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 345 */     if (this.settings != null) {
/* 346 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 347 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 348 */       copy.settings = m;
/*     */     } 
/* 350 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 351 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 352 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 353 */     copy.next = this.next;
/* 354 */     copy.failed = this.failed;
/* 355 */     copy.forkInteractions = this.forkInteractions;
/* 356 */     copy.repeat = this.repeat;
/* 357 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     RepeatInteraction other;
/* 363 */     if (this == obj) return true; 
/* 364 */     if (obj instanceof RepeatInteraction) { other = (RepeatInteraction)obj; } else { return false; }
/* 365 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && this.forkInteractions == other.forkInteractions && this.repeat == other.repeat);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 370 */     int result = 1;
/* 371 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 372 */     result = 31 * result + Objects.hashCode(this.effects);
/* 373 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 374 */     result = 31 * result + Float.hashCode(this.runTime);
/* 375 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 376 */     result = 31 * result + Objects.hashCode(this.settings);
/* 377 */     result = 31 * result + Objects.hashCode(this.rules);
/* 378 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 379 */     result = 31 * result + Objects.hashCode(this.camera);
/* 380 */     result = 31 * result + Integer.hashCode(this.next);
/* 381 */     result = 31 * result + Integer.hashCode(this.failed);
/* 382 */     result = 31 * result + Integer.hashCode(this.forkInteractions);
/* 383 */     result = 31 * result + Integer.hashCode(this.repeat);
/* 384 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\RepeatInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */