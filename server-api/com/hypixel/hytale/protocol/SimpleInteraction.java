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
/*     */ public class SimpleInteraction extends Interaction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 19;
/*     */   public static final int VARIABLE_FIELD_COUNT = 5;
/*     */   public static final int VARIABLE_BLOCK_START = 39;
/*     */   public static final int MAX_SIZE = 1677721600;
/*  20 */   public int next = Integer.MIN_VALUE;
/*  21 */   public int failed = Integer.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed) {
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
/*     */   }
/*     */   
/*     */   public SimpleInteraction(@Nonnull SimpleInteraction other) {
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
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SimpleInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  56 */     SimpleInteraction obj = new SimpleInteraction();
/*  57 */     byte nullBits = buf.getByte(offset);
/*  58 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  59 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  60 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  61 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  62 */     obj.next = buf.getIntLE(offset + 11);
/*  63 */     obj.failed = buf.getIntLE(offset + 15);
/*     */     
/*  65 */     if ((nullBits & 0x1) != 0) {
/*  66 */       int varPos0 = offset + 39 + buf.getIntLE(offset + 19);
/*  67 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  69 */     if ((nullBits & 0x2) != 0) {
/*  70 */       int varPos1 = offset + 39 + buf.getIntLE(offset + 23);
/*  71 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  72 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  73 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  74 */       int varIntLen = VarInt.length(buf, varPos1);
/*  75 */       obj.settings = new HashMap<>(settingsCount);
/*  76 */       int dictPos = varPos1 + varIntLen;
/*  77 */       for (int i = 0; i < settingsCount; i++) {
/*  78 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  79 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/*  80 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/*  81 */         if (obj.settings.put(key, val) != null)
/*  82 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/*  85 */     if ((nullBits & 0x4) != 0) {
/*  86 */       int varPos2 = offset + 39 + buf.getIntLE(offset + 27);
/*  87 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/*  89 */     if ((nullBits & 0x8) != 0) {
/*  90 */       int varPos3 = offset + 39 + buf.getIntLE(offset + 31);
/*  91 */       int tagsCount = VarInt.peek(buf, varPos3);
/*  92 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/*  93 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/*  94 */       int varIntLen = VarInt.length(buf, varPos3);
/*  95 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/*  96 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/*  97 */       obj.tags = new int[tagsCount];
/*  98 */       for (int i = 0; i < tagsCount; i++) {
/*  99 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 102 */     if ((nullBits & 0x10) != 0) {
/* 103 */       int varPos4 = offset + 39 + buf.getIntLE(offset + 35);
/* 104 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/*     */     
/* 107 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 111 */     byte nullBits = buf.getByte(offset);
/* 112 */     int maxEnd = 39;
/* 113 */     if ((nullBits & 0x1) != 0) {
/* 114 */       int fieldOffset0 = buf.getIntLE(offset + 19);
/* 115 */       int pos0 = offset + 39 + fieldOffset0;
/* 116 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 117 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 119 */     if ((nullBits & 0x2) != 0) {
/* 120 */       int fieldOffset1 = buf.getIntLE(offset + 23);
/* 121 */       int pos1 = offset + 39 + fieldOffset1;
/* 122 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 123 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 124 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 126 */     if ((nullBits & 0x4) != 0) {
/* 127 */       int fieldOffset2 = buf.getIntLE(offset + 27);
/* 128 */       int pos2 = offset + 39 + fieldOffset2;
/* 129 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 130 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 132 */     if ((nullBits & 0x8) != 0) {
/* 133 */       int fieldOffset3 = buf.getIntLE(offset + 31);
/* 134 */       int pos3 = offset + 39 + fieldOffset3;
/* 135 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 136 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 138 */     if ((nullBits & 0x10) != 0) {
/* 139 */       int fieldOffset4 = buf.getIntLE(offset + 35);
/* 140 */       int pos4 = offset + 39 + fieldOffset4;
/* 141 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 142 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 144 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 150 */     int startPos = buf.writerIndex();
/* 151 */     byte nullBits = 0;
/* 152 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 153 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 154 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 155 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 156 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 157 */     buf.writeByte(nullBits);
/*     */     
/* 159 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 160 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 161 */     buf.writeFloatLE(this.runTime);
/* 162 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 163 */     buf.writeIntLE(this.next);
/* 164 */     buf.writeIntLE(this.failed);
/*     */     
/* 166 */     int effectsOffsetSlot = buf.writerIndex();
/* 167 */     buf.writeIntLE(0);
/* 168 */     int settingsOffsetSlot = buf.writerIndex();
/* 169 */     buf.writeIntLE(0);
/* 170 */     int rulesOffsetSlot = buf.writerIndex();
/* 171 */     buf.writeIntLE(0);
/* 172 */     int tagsOffsetSlot = buf.writerIndex();
/* 173 */     buf.writeIntLE(0);
/* 174 */     int cameraOffsetSlot = buf.writerIndex();
/* 175 */     buf.writeIntLE(0);
/*     */     
/* 177 */     int varBlockStart = buf.writerIndex();
/* 178 */     if (this.effects != null) {
/* 179 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 180 */       this.effects.serialize(buf);
/*     */     } else {
/* 182 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 184 */     if (this.settings != null)
/* 185 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 186 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 188 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 190 */     if (this.rules != null) {
/* 191 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 192 */       this.rules.serialize(buf);
/*     */     } else {
/* 194 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 196 */     if (this.tags != null) {
/* 197 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 198 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 200 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 202 */     if (this.camera != null) {
/* 203 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 204 */       this.camera.serialize(buf);
/*     */     } else {
/* 206 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 208 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 214 */     int size = 39;
/* 215 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 216 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 217 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 218 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 219 */     if (this.camera != null) size += this.camera.computeSize();
/*     */     
/* 221 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 225 */     if (buffer.readableBytes() - offset < 39) {
/* 226 */       return ValidationResult.error("Buffer too small: expected at least 39 bytes");
/*     */     }
/*     */     
/* 229 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 232 */     if ((nullBits & 0x1) != 0) {
/* 233 */       int effectsOffset = buffer.getIntLE(offset + 19);
/* 234 */       if (effectsOffset < 0) {
/* 235 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 237 */       int pos = offset + 39 + effectsOffset;
/* 238 */       if (pos >= buffer.writerIndex()) {
/* 239 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 241 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 242 */       if (!effectsResult.isValid()) {
/* 243 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 245 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 248 */     if ((nullBits & 0x2) != 0) {
/* 249 */       int settingsOffset = buffer.getIntLE(offset + 23);
/* 250 */       if (settingsOffset < 0) {
/* 251 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 253 */       int pos = offset + 39 + settingsOffset;
/* 254 */       if (pos >= buffer.writerIndex()) {
/* 255 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 257 */       int settingsCount = VarInt.peek(buffer, pos);
/* 258 */       if (settingsCount < 0) {
/* 259 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 261 */       if (settingsCount > 4096000) {
/* 262 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 264 */       pos += VarInt.length(buffer, pos);
/* 265 */       for (int i = 0; i < settingsCount; i++) {
/* 266 */         pos++;
/*     */         
/* 268 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 273 */     if ((nullBits & 0x4) != 0) {
/* 274 */       int rulesOffset = buffer.getIntLE(offset + 27);
/* 275 */       if (rulesOffset < 0) {
/* 276 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 278 */       int pos = offset + 39 + rulesOffset;
/* 279 */       if (pos >= buffer.writerIndex()) {
/* 280 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 282 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 283 */       if (!rulesResult.isValid()) {
/* 284 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 286 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 289 */     if ((nullBits & 0x8) != 0) {
/* 290 */       int tagsOffset = buffer.getIntLE(offset + 31);
/* 291 */       if (tagsOffset < 0) {
/* 292 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 294 */       int pos = offset + 39 + tagsOffset;
/* 295 */       if (pos >= buffer.writerIndex()) {
/* 296 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 298 */       int tagsCount = VarInt.peek(buffer, pos);
/* 299 */       if (tagsCount < 0) {
/* 300 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 302 */       if (tagsCount > 4096000) {
/* 303 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 305 */       pos += VarInt.length(buffer, pos);
/* 306 */       pos += tagsCount * 4;
/* 307 */       if (pos > buffer.writerIndex()) {
/* 308 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 312 */     if ((nullBits & 0x10) != 0) {
/* 313 */       int cameraOffset = buffer.getIntLE(offset + 35);
/* 314 */       if (cameraOffset < 0) {
/* 315 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 317 */       int pos = offset + 39 + cameraOffset;
/* 318 */       if (pos >= buffer.writerIndex()) {
/* 319 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 321 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 322 */       if (!cameraResult.isValid()) {
/* 323 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 325 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 327 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SimpleInteraction clone() {
/* 331 */     SimpleInteraction copy = new SimpleInteraction();
/* 332 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 333 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 334 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 335 */     copy.runTime = this.runTime;
/* 336 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 337 */     if (this.settings != null) {
/* 338 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 339 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 340 */       copy.settings = m;
/*     */     } 
/* 342 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 343 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 344 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 345 */     copy.next = this.next;
/* 346 */     copy.failed = this.failed;
/* 347 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SimpleInteraction other;
/* 353 */     if (this == obj) return true; 
/* 354 */     if (obj instanceof SimpleInteraction) { other = (SimpleInteraction)obj; } else { return false; }
/* 355 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 360 */     int result = 1;
/* 361 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 362 */     result = 31 * result + Objects.hashCode(this.effects);
/* 363 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 364 */     result = 31 * result + Float.hashCode(this.runTime);
/* 365 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 366 */     result = 31 * result + Objects.hashCode(this.settings);
/* 367 */     result = 31 * result + Objects.hashCode(this.rules);
/* 368 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 369 */     result = 31 * result + Objects.hashCode(this.camera);
/* 370 */     result = 31 * result + Integer.hashCode(this.next);
/* 371 */     result = 31 * result + Integer.hashCode(this.failed);
/* 372 */     return result;
/*     */   }
/*     */   
/*     */   public SimpleInteraction() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\SimpleInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */