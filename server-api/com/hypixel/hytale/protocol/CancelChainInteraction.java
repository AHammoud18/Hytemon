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
/*     */ public class CancelChainInteraction extends SimpleInteraction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 19;
/*     */   public static final int VARIABLE_FIELD_COUNT = 6;
/*     */   public static final int VARIABLE_BLOCK_START = 43;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public String chainId;
/*     */   
/*     */   public CancelChainInteraction() {}
/*     */   
/*     */   public CancelChainInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, @Nullable String chainId) {
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
/*  37 */     this.chainId = chainId;
/*     */   }
/*     */   
/*     */   public CancelChainInteraction(@Nonnull CancelChainInteraction other) {
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
/*  52 */     this.chainId = other.chainId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CancelChainInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  57 */     CancelChainInteraction obj = new CancelChainInteraction();
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  60 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  61 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  62 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  63 */     obj.next = buf.getIntLE(offset + 11);
/*  64 */     obj.failed = buf.getIntLE(offset + 15);
/*     */     
/*  66 */     if ((nullBits & 0x1) != 0) {
/*  67 */       int varPos0 = offset + 43 + buf.getIntLE(offset + 19);
/*  68 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  70 */     if ((nullBits & 0x2) != 0) {
/*  71 */       int varPos1 = offset + 43 + buf.getIntLE(offset + 23);
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
/*  87 */       int varPos2 = offset + 43 + buf.getIntLE(offset + 27);
/*  88 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/*  90 */     if ((nullBits & 0x8) != 0) {
/*  91 */       int varPos3 = offset + 43 + buf.getIntLE(offset + 31);
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
/* 104 */       int varPos4 = offset + 43 + buf.getIntLE(offset + 35);
/* 105 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/* 107 */     if ((nullBits & 0x20) != 0) {
/* 108 */       int varPos5 = offset + 43 + buf.getIntLE(offset + 39);
/* 109 */       int chainIdLen = VarInt.peek(buf, varPos5);
/* 110 */       if (chainIdLen < 0) throw ProtocolException.negativeLength("ChainId", chainIdLen); 
/* 111 */       if (chainIdLen > 4096000) throw ProtocolException.stringTooLong("ChainId", chainIdLen, 4096000); 
/* 112 */       obj.chainId = PacketIO.readVarString(buf, varPos5, PacketIO.UTF8);
/*     */     } 
/*     */     
/* 115 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 119 */     byte nullBits = buf.getByte(offset);
/* 120 */     int maxEnd = 43;
/* 121 */     if ((nullBits & 0x1) != 0) {
/* 122 */       int fieldOffset0 = buf.getIntLE(offset + 19);
/* 123 */       int pos0 = offset + 43 + fieldOffset0;
/* 124 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 125 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 127 */     if ((nullBits & 0x2) != 0) {
/* 128 */       int fieldOffset1 = buf.getIntLE(offset + 23);
/* 129 */       int pos1 = offset + 43 + fieldOffset1;
/* 130 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 131 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 132 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 134 */     if ((nullBits & 0x4) != 0) {
/* 135 */       int fieldOffset2 = buf.getIntLE(offset + 27);
/* 136 */       int pos2 = offset + 43 + fieldOffset2;
/* 137 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 138 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 140 */     if ((nullBits & 0x8) != 0) {
/* 141 */       int fieldOffset3 = buf.getIntLE(offset + 31);
/* 142 */       int pos3 = offset + 43 + fieldOffset3;
/* 143 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 144 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 146 */     if ((nullBits & 0x10) != 0) {
/* 147 */       int fieldOffset4 = buf.getIntLE(offset + 35);
/* 148 */       int pos4 = offset + 43 + fieldOffset4;
/* 149 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 150 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 152 */     if ((nullBits & 0x20) != 0) {
/* 153 */       int fieldOffset5 = buf.getIntLE(offset + 39);
/* 154 */       int pos5 = offset + 43 + fieldOffset5;
/* 155 */       int sl = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5) + sl;
/* 156 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
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
/* 171 */     if (this.chainId != null) nullBits = (byte)(nullBits | 0x20); 
/* 172 */     buf.writeByte(nullBits);
/*     */     
/* 174 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 175 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 176 */     buf.writeFloatLE(this.runTime);
/* 177 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 178 */     buf.writeIntLE(this.next);
/* 179 */     buf.writeIntLE(this.failed);
/*     */     
/* 181 */     int effectsOffsetSlot = buf.writerIndex();
/* 182 */     buf.writeIntLE(0);
/* 183 */     int settingsOffsetSlot = buf.writerIndex();
/* 184 */     buf.writeIntLE(0);
/* 185 */     int rulesOffsetSlot = buf.writerIndex();
/* 186 */     buf.writeIntLE(0);
/* 187 */     int tagsOffsetSlot = buf.writerIndex();
/* 188 */     buf.writeIntLE(0);
/* 189 */     int cameraOffsetSlot = buf.writerIndex();
/* 190 */     buf.writeIntLE(0);
/* 191 */     int chainIdOffsetSlot = buf.writerIndex();
/* 192 */     buf.writeIntLE(0);
/*     */     
/* 194 */     int varBlockStart = buf.writerIndex();
/* 195 */     if (this.effects != null) {
/* 196 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 197 */       this.effects.serialize(buf);
/*     */     } else {
/* 199 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 201 */     if (this.settings != null)
/* 202 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 203 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 205 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 207 */     if (this.rules != null) {
/* 208 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 209 */       this.rules.serialize(buf);
/*     */     } else {
/* 211 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 213 */     if (this.tags != null) {
/* 214 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 215 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 217 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 219 */     if (this.camera != null) {
/* 220 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 221 */       this.camera.serialize(buf);
/*     */     } else {
/* 223 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 225 */     if (this.chainId != null) {
/* 226 */       buf.setIntLE(chainIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 227 */       PacketIO.writeVarString(buf, this.chainId, 4096000);
/*     */     } else {
/* 229 */       buf.setIntLE(chainIdOffsetSlot, -1);
/*     */     } 
/* 231 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 237 */     int size = 43;
/* 238 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 239 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 240 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 241 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 242 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 243 */     if (this.chainId != null) size += PacketIO.stringSize(this.chainId);
/*     */     
/* 245 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 249 */     if (buffer.readableBytes() - offset < 43) {
/* 250 */       return ValidationResult.error("Buffer too small: expected at least 43 bytes");
/*     */     }
/*     */     
/* 253 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 256 */     if ((nullBits & 0x1) != 0) {
/* 257 */       int effectsOffset = buffer.getIntLE(offset + 19);
/* 258 */       if (effectsOffset < 0) {
/* 259 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 261 */       int pos = offset + 43 + effectsOffset;
/* 262 */       if (pos >= buffer.writerIndex()) {
/* 263 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 265 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 266 */       if (!effectsResult.isValid()) {
/* 267 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 269 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 272 */     if ((nullBits & 0x2) != 0) {
/* 273 */       int settingsOffset = buffer.getIntLE(offset + 23);
/* 274 */       if (settingsOffset < 0) {
/* 275 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 277 */       int pos = offset + 43 + settingsOffset;
/* 278 */       if (pos >= buffer.writerIndex()) {
/* 279 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 281 */       int settingsCount = VarInt.peek(buffer, pos);
/* 282 */       if (settingsCount < 0) {
/* 283 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 285 */       if (settingsCount > 4096000) {
/* 286 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 288 */       pos += VarInt.length(buffer, pos);
/* 289 */       for (int i = 0; i < settingsCount; i++) {
/* 290 */         pos++;
/*     */         
/* 292 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 297 */     if ((nullBits & 0x4) != 0) {
/* 298 */       int rulesOffset = buffer.getIntLE(offset + 27);
/* 299 */       if (rulesOffset < 0) {
/* 300 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 302 */       int pos = offset + 43 + rulesOffset;
/* 303 */       if (pos >= buffer.writerIndex()) {
/* 304 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 306 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 307 */       if (!rulesResult.isValid()) {
/* 308 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 310 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 313 */     if ((nullBits & 0x8) != 0) {
/* 314 */       int tagsOffset = buffer.getIntLE(offset + 31);
/* 315 */       if (tagsOffset < 0) {
/* 316 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 318 */       int pos = offset + 43 + tagsOffset;
/* 319 */       if (pos >= buffer.writerIndex()) {
/* 320 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 322 */       int tagsCount = VarInt.peek(buffer, pos);
/* 323 */       if (tagsCount < 0) {
/* 324 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 326 */       if (tagsCount > 4096000) {
/* 327 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 329 */       pos += VarInt.length(buffer, pos);
/* 330 */       pos += tagsCount * 4;
/* 331 */       if (pos > buffer.writerIndex()) {
/* 332 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 336 */     if ((nullBits & 0x10) != 0) {
/* 337 */       int cameraOffset = buffer.getIntLE(offset + 35);
/* 338 */       if (cameraOffset < 0) {
/* 339 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 341 */       int pos = offset + 43 + cameraOffset;
/* 342 */       if (pos >= buffer.writerIndex()) {
/* 343 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 345 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 346 */       if (!cameraResult.isValid()) {
/* 347 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 349 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 352 */     if ((nullBits & 0x20) != 0) {
/* 353 */       int chainIdOffset = buffer.getIntLE(offset + 39);
/* 354 */       if (chainIdOffset < 0) {
/* 355 */         return ValidationResult.error("Invalid offset for ChainId");
/*     */       }
/* 357 */       int pos = offset + 43 + chainIdOffset;
/* 358 */       if (pos >= buffer.writerIndex()) {
/* 359 */         return ValidationResult.error("Offset out of bounds for ChainId");
/*     */       }
/* 361 */       int chainIdLen = VarInt.peek(buffer, pos);
/* 362 */       if (chainIdLen < 0) {
/* 363 */         return ValidationResult.error("Invalid string length for ChainId");
/*     */       }
/* 365 */       if (chainIdLen > 4096000) {
/* 366 */         return ValidationResult.error("ChainId exceeds max length 4096000");
/*     */       }
/* 368 */       pos += VarInt.length(buffer, pos);
/* 369 */       pos += chainIdLen;
/* 370 */       if (pos > buffer.writerIndex()) {
/* 371 */         return ValidationResult.error("Buffer overflow reading ChainId");
/*     */       }
/*     */     } 
/* 374 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public CancelChainInteraction clone() {
/* 378 */     CancelChainInteraction copy = new CancelChainInteraction();
/* 379 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 380 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 381 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 382 */     copy.runTime = this.runTime;
/* 383 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 384 */     if (this.settings != null) {
/* 385 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 386 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 387 */       copy.settings = m;
/*     */     } 
/* 389 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 390 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 391 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 392 */     copy.next = this.next;
/* 393 */     copy.failed = this.failed;
/* 394 */     copy.chainId = this.chainId;
/* 395 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     CancelChainInteraction other;
/* 401 */     if (this == obj) return true; 
/* 402 */     if (obj instanceof CancelChainInteraction) { other = (CancelChainInteraction)obj; } else { return false; }
/* 403 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && Objects.equals(this.chainId, other.chainId));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 408 */     int result = 1;
/* 409 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 410 */     result = 31 * result + Objects.hashCode(this.effects);
/* 411 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 412 */     result = 31 * result + Float.hashCode(this.runTime);
/* 413 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 414 */     result = 31 * result + Objects.hashCode(this.settings);
/* 415 */     result = 31 * result + Objects.hashCode(this.rules);
/* 416 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 417 */     result = 31 * result + Objects.hashCode(this.camera);
/* 418 */     result = 31 * result + Integer.hashCode(this.next);
/* 419 */     result = 31 * result + Integer.hashCode(this.failed);
/* 420 */     result = 31 * result + Objects.hashCode(this.chainId);
/* 421 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\CancelChainInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */