/*     */ package com.hypixel.hytale.protocol;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChainingInteraction
/*     */   extends Interaction
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 15;
/*     */   public static final int VARIABLE_FIELD_COUNT = 8;
/*     */   public static final int VARIABLE_BLOCK_START = 47;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public ChainingInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, @Nullable String chainId, float chainingAllowance, @Nullable int[] chainingNext, @Nullable Map<String, Integer> flags) {
/*  29 */     this.waitForDataFrom = waitForDataFrom;
/*  30 */     this.effects = effects;
/*  31 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  32 */     this.runTime = runTime;
/*  33 */     this.cancelOnItemChange = cancelOnItemChange;
/*  34 */     this.settings = settings;
/*  35 */     this.rules = rules;
/*  36 */     this.tags = tags;
/*  37 */     this.camera = camera;
/*  38 */     this.chainId = chainId;
/*  39 */     this.chainingAllowance = chainingAllowance;
/*  40 */     this.chainingNext = chainingNext;
/*  41 */     this.flags = flags; } @Nullable
/*     */   public String chainId; public float chainingAllowance; @Nullable
/*     */   public int[] chainingNext; @Nullable
/*     */   public Map<String, Integer> flags; public ChainingInteraction() {} public ChainingInteraction(@Nonnull ChainingInteraction other) {
/*  45 */     this.waitForDataFrom = other.waitForDataFrom;
/*  46 */     this.effects = other.effects;
/*  47 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  48 */     this.runTime = other.runTime;
/*  49 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  50 */     this.settings = other.settings;
/*  51 */     this.rules = other.rules;
/*  52 */     this.tags = other.tags;
/*  53 */     this.camera = other.camera;
/*  54 */     this.chainId = other.chainId;
/*  55 */     this.chainingAllowance = other.chainingAllowance;
/*  56 */     this.chainingNext = other.chainingNext;
/*  57 */     this.flags = other.flags;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ChainingInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  62 */     ChainingInteraction obj = new ChainingInteraction();
/*  63 */     byte nullBits = buf.getByte(offset);
/*  64 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  65 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  66 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  67 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  68 */     obj.chainingAllowance = buf.getFloatLE(offset + 11);
/*     */     
/*  70 */     if ((nullBits & 0x1) != 0) {
/*  71 */       int varPos0 = offset + 47 + buf.getIntLE(offset + 15);
/*  72 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  74 */     if ((nullBits & 0x2) != 0) {
/*  75 */       int varPos1 = offset + 47 + buf.getIntLE(offset + 19);
/*  76 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  77 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  78 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  79 */       int varIntLen = VarInt.length(buf, varPos1);
/*  80 */       obj.settings = new HashMap<>(settingsCount);
/*  81 */       int dictPos = varPos1 + varIntLen;
/*  82 */       for (int i = 0; i < settingsCount; i++) {
/*  83 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  84 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/*  85 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/*  86 */         if (obj.settings.put(key, val) != null)
/*  87 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/*  90 */     if ((nullBits & 0x4) != 0) {
/*  91 */       int varPos2 = offset + 47 + buf.getIntLE(offset + 23);
/*  92 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/*  94 */     if ((nullBits & 0x8) != 0) {
/*  95 */       int varPos3 = offset + 47 + buf.getIntLE(offset + 27);
/*  96 */       int tagsCount = VarInt.peek(buf, varPos3);
/*  97 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/*  98 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/*  99 */       int varIntLen = VarInt.length(buf, varPos3);
/* 100 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/* 101 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/* 102 */       obj.tags = new int[tagsCount];
/* 103 */       for (int i = 0; i < tagsCount; i++) {
/* 104 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 107 */     if ((nullBits & 0x10) != 0) {
/* 108 */       int varPos4 = offset + 47 + buf.getIntLE(offset + 31);
/* 109 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/* 111 */     if ((nullBits & 0x20) != 0) {
/* 112 */       int varPos5 = offset + 47 + buf.getIntLE(offset + 35);
/* 113 */       int chainIdLen = VarInt.peek(buf, varPos5);
/* 114 */       if (chainIdLen < 0) throw ProtocolException.negativeLength("ChainId", chainIdLen); 
/* 115 */       if (chainIdLen > 4096000) throw ProtocolException.stringTooLong("ChainId", chainIdLen, 4096000); 
/* 116 */       obj.chainId = PacketIO.readVarString(buf, varPos5, PacketIO.UTF8);
/*     */     } 
/* 118 */     if ((nullBits & 0x40) != 0) {
/* 119 */       int varPos6 = offset + 47 + buf.getIntLE(offset + 39);
/* 120 */       int chainingNextCount = VarInt.peek(buf, varPos6);
/* 121 */       if (chainingNextCount < 0) throw ProtocolException.negativeLength("ChainingNext", chainingNextCount); 
/* 122 */       if (chainingNextCount > 4096000) throw ProtocolException.arrayTooLong("ChainingNext", chainingNextCount, 4096000); 
/* 123 */       int varIntLen = VarInt.length(buf, varPos6);
/* 124 */       if ((varPos6 + varIntLen) + chainingNextCount * 4L > buf.readableBytes())
/* 125 */         throw ProtocolException.bufferTooSmall("ChainingNext", varPos6 + varIntLen + chainingNextCount * 4, buf.readableBytes()); 
/* 126 */       obj.chainingNext = new int[chainingNextCount];
/* 127 */       for (int i = 0; i < chainingNextCount; i++) {
/* 128 */         obj.chainingNext[i] = buf.getIntLE(varPos6 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 131 */     if ((nullBits & 0x80) != 0) {
/* 132 */       int varPos7 = offset + 47 + buf.getIntLE(offset + 43);
/* 133 */       int flagsCount = VarInt.peek(buf, varPos7);
/* 134 */       if (flagsCount < 0) throw ProtocolException.negativeLength("Flags", flagsCount); 
/* 135 */       if (flagsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Flags", flagsCount, 4096000); 
/* 136 */       int varIntLen = VarInt.length(buf, varPos7);
/* 137 */       obj.flags = new HashMap<>(flagsCount);
/* 138 */       int dictPos = varPos7 + varIntLen;
/* 139 */       for (int i = 0; i < flagsCount; i++) {
/* 140 */         int keyLen = VarInt.peek(buf, dictPos);
/* 141 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/* 142 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/* 143 */         int keyVarLen = VarInt.length(buf, dictPos);
/* 144 */         String key = PacketIO.readVarString(buf, dictPos);
/* 145 */         dictPos += keyVarLen + keyLen;
/* 146 */         int val = buf.getIntLE(dictPos); dictPos += 4;
/* 147 */         if (obj.flags.put(key, Integer.valueOf(val)) != null) {
/* 148 */           throw ProtocolException.duplicateKey("flags", key);
/*     */         }
/*     */       } 
/*     */     } 
/* 152 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 156 */     byte nullBits = buf.getByte(offset);
/* 157 */     int maxEnd = 47;
/* 158 */     if ((nullBits & 0x1) != 0) {
/* 159 */       int fieldOffset0 = buf.getIntLE(offset + 15);
/* 160 */       int pos0 = offset + 47 + fieldOffset0;
/* 161 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 162 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 164 */     if ((nullBits & 0x2) != 0) {
/* 165 */       int fieldOffset1 = buf.getIntLE(offset + 19);
/* 166 */       int pos1 = offset + 47 + fieldOffset1;
/* 167 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 168 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 169 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 171 */     if ((nullBits & 0x4) != 0) {
/* 172 */       int fieldOffset2 = buf.getIntLE(offset + 23);
/* 173 */       int pos2 = offset + 47 + fieldOffset2;
/* 174 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 175 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 177 */     if ((nullBits & 0x8) != 0) {
/* 178 */       int fieldOffset3 = buf.getIntLE(offset + 27);
/* 179 */       int pos3 = offset + 47 + fieldOffset3;
/* 180 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 181 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 183 */     if ((nullBits & 0x10) != 0) {
/* 184 */       int fieldOffset4 = buf.getIntLE(offset + 31);
/* 185 */       int pos4 = offset + 47 + fieldOffset4;
/* 186 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 187 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 189 */     if ((nullBits & 0x20) != 0) {
/* 190 */       int fieldOffset5 = buf.getIntLE(offset + 35);
/* 191 */       int pos5 = offset + 47 + fieldOffset5;
/* 192 */       int sl = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5) + sl;
/* 193 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 195 */     if ((nullBits & 0x40) != 0) {
/* 196 */       int fieldOffset6 = buf.getIntLE(offset + 39);
/* 197 */       int pos6 = offset + 47 + fieldOffset6;
/* 198 */       int arrLen = VarInt.peek(buf, pos6); pos6 += VarInt.length(buf, pos6) + arrLen * 4;
/* 199 */       if (pos6 - offset > maxEnd) maxEnd = pos6 - offset; 
/*     */     } 
/* 201 */     if ((nullBits & 0x80) != 0) {
/* 202 */       int fieldOffset7 = buf.getIntLE(offset + 43);
/* 203 */       int pos7 = offset + 47 + fieldOffset7;
/* 204 */       int dictLen = VarInt.peek(buf, pos7); pos7 += VarInt.length(buf, pos7);
/* 205 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos7); pos7 += VarInt.length(buf, pos7) + sl; pos7 += 4; i++; }
/* 206 */        if (pos7 - offset > maxEnd) maxEnd = pos7 - offset; 
/*     */     } 
/* 208 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 214 */     int startPos = buf.writerIndex();
/* 215 */     byte nullBits = 0;
/* 216 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 217 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 218 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 219 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 220 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 221 */     if (this.chainId != null) nullBits = (byte)(nullBits | 0x20); 
/* 222 */     if (this.chainingNext != null) nullBits = (byte)(nullBits | 0x40); 
/* 223 */     if (this.flags != null) nullBits = (byte)(nullBits | 0x80); 
/* 224 */     buf.writeByte(nullBits);
/*     */     
/* 226 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 227 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 228 */     buf.writeFloatLE(this.runTime);
/* 229 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 230 */     buf.writeFloatLE(this.chainingAllowance);
/*     */     
/* 232 */     int effectsOffsetSlot = buf.writerIndex();
/* 233 */     buf.writeIntLE(0);
/* 234 */     int settingsOffsetSlot = buf.writerIndex();
/* 235 */     buf.writeIntLE(0);
/* 236 */     int rulesOffsetSlot = buf.writerIndex();
/* 237 */     buf.writeIntLE(0);
/* 238 */     int tagsOffsetSlot = buf.writerIndex();
/* 239 */     buf.writeIntLE(0);
/* 240 */     int cameraOffsetSlot = buf.writerIndex();
/* 241 */     buf.writeIntLE(0);
/* 242 */     int chainIdOffsetSlot = buf.writerIndex();
/* 243 */     buf.writeIntLE(0);
/* 244 */     int chainingNextOffsetSlot = buf.writerIndex();
/* 245 */     buf.writeIntLE(0);
/* 246 */     int flagsOffsetSlot = buf.writerIndex();
/* 247 */     buf.writeIntLE(0);
/*     */     
/* 249 */     int varBlockStart = buf.writerIndex();
/* 250 */     if (this.effects != null) {
/* 251 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 252 */       this.effects.serialize(buf);
/*     */     } else {
/* 254 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 256 */     if (this.settings != null)
/* 257 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 258 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 260 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 262 */     if (this.rules != null) {
/* 263 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 264 */       this.rules.serialize(buf);
/*     */     } else {
/* 266 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 268 */     if (this.tags != null) {
/* 269 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 270 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 272 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 274 */     if (this.camera != null) {
/* 275 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 276 */       this.camera.serialize(buf);
/*     */     } else {
/* 278 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 280 */     if (this.chainId != null) {
/* 281 */       buf.setIntLE(chainIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 282 */       PacketIO.writeVarString(buf, this.chainId, 4096000);
/*     */     } else {
/* 284 */       buf.setIntLE(chainIdOffsetSlot, -1);
/*     */     } 
/* 286 */     if (this.chainingNext != null) {
/* 287 */       buf.setIntLE(chainingNextOffsetSlot, buf.writerIndex() - varBlockStart);
/* 288 */       if (this.chainingNext.length > 4096000) throw ProtocolException.arrayTooLong("ChainingNext", this.chainingNext.length, 4096000);  VarInt.write(buf, this.chainingNext.length); for (int item : this.chainingNext) buf.writeIntLE(item); 
/*     */     } else {
/* 290 */       buf.setIntLE(chainingNextOffsetSlot, -1);
/*     */     } 
/* 292 */     if (this.flags != null)
/* 293 */     { buf.setIntLE(flagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 294 */       if (this.flags.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Flags", this.flags.size(), 4096000);  VarInt.write(buf, this.flags.size()); for (Map.Entry<String, Integer> e : this.flags.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*     */        }
/* 296 */     else { buf.setIntLE(flagsOffsetSlot, -1); }
/*     */     
/* 298 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 304 */     int size = 47;
/* 305 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 306 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 307 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 308 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 309 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 310 */     if (this.chainId != null) size += PacketIO.stringSize(this.chainId); 
/* 311 */     if (this.chainingNext != null) size += VarInt.size(this.chainingNext.length) + this.chainingNext.length * 4; 
/* 312 */     if (this.flags != null) {
/* 313 */       int flagsSize = 0;
/* 314 */       for (Map.Entry<String, Integer> kvp : this.flags.entrySet()) flagsSize += PacketIO.stringSize(kvp.getKey()) + 4; 
/* 315 */       size += VarInt.size(this.flags.size()) + flagsSize;
/*     */     } 
/*     */     
/* 318 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 322 */     if (buffer.readableBytes() - offset < 47) {
/* 323 */       return ValidationResult.error("Buffer too small: expected at least 47 bytes");
/*     */     }
/*     */     
/* 326 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 329 */     if ((nullBits & 0x1) != 0) {
/* 330 */       int effectsOffset = buffer.getIntLE(offset + 15);
/* 331 */       if (effectsOffset < 0) {
/* 332 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 334 */       int pos = offset + 47 + effectsOffset;
/* 335 */       if (pos >= buffer.writerIndex()) {
/* 336 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 338 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 339 */       if (!effectsResult.isValid()) {
/* 340 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 342 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 345 */     if ((nullBits & 0x2) != 0) {
/* 346 */       int settingsOffset = buffer.getIntLE(offset + 19);
/* 347 */       if (settingsOffset < 0) {
/* 348 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 350 */       int pos = offset + 47 + settingsOffset;
/* 351 */       if (pos >= buffer.writerIndex()) {
/* 352 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 354 */       int settingsCount = VarInt.peek(buffer, pos);
/* 355 */       if (settingsCount < 0) {
/* 356 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 358 */       if (settingsCount > 4096000) {
/* 359 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 361 */       pos += VarInt.length(buffer, pos);
/* 362 */       for (int i = 0; i < settingsCount; i++) {
/* 363 */         pos++;
/*     */         
/* 365 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 370 */     if ((nullBits & 0x4) != 0) {
/* 371 */       int rulesOffset = buffer.getIntLE(offset + 23);
/* 372 */       if (rulesOffset < 0) {
/* 373 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 375 */       int pos = offset + 47 + rulesOffset;
/* 376 */       if (pos >= buffer.writerIndex()) {
/* 377 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 379 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 380 */       if (!rulesResult.isValid()) {
/* 381 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 383 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 386 */     if ((nullBits & 0x8) != 0) {
/* 387 */       int tagsOffset = buffer.getIntLE(offset + 27);
/* 388 */       if (tagsOffset < 0) {
/* 389 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 391 */       int pos = offset + 47 + tagsOffset;
/* 392 */       if (pos >= buffer.writerIndex()) {
/* 393 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 395 */       int tagsCount = VarInt.peek(buffer, pos);
/* 396 */       if (tagsCount < 0) {
/* 397 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 399 */       if (tagsCount > 4096000) {
/* 400 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 402 */       pos += VarInt.length(buffer, pos);
/* 403 */       pos += tagsCount * 4;
/* 404 */       if (pos > buffer.writerIndex()) {
/* 405 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 409 */     if ((nullBits & 0x10) != 0) {
/* 410 */       int cameraOffset = buffer.getIntLE(offset + 31);
/* 411 */       if (cameraOffset < 0) {
/* 412 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 414 */       int pos = offset + 47 + cameraOffset;
/* 415 */       if (pos >= buffer.writerIndex()) {
/* 416 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 418 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 419 */       if (!cameraResult.isValid()) {
/* 420 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 422 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 425 */     if ((nullBits & 0x20) != 0) {
/* 426 */       int chainIdOffset = buffer.getIntLE(offset + 35);
/* 427 */       if (chainIdOffset < 0) {
/* 428 */         return ValidationResult.error("Invalid offset for ChainId");
/*     */       }
/* 430 */       int pos = offset + 47 + chainIdOffset;
/* 431 */       if (pos >= buffer.writerIndex()) {
/* 432 */         return ValidationResult.error("Offset out of bounds for ChainId");
/*     */       }
/* 434 */       int chainIdLen = VarInt.peek(buffer, pos);
/* 435 */       if (chainIdLen < 0) {
/* 436 */         return ValidationResult.error("Invalid string length for ChainId");
/*     */       }
/* 438 */       if (chainIdLen > 4096000) {
/* 439 */         return ValidationResult.error("ChainId exceeds max length 4096000");
/*     */       }
/* 441 */       pos += VarInt.length(buffer, pos);
/* 442 */       pos += chainIdLen;
/* 443 */       if (pos > buffer.writerIndex()) {
/* 444 */         return ValidationResult.error("Buffer overflow reading ChainId");
/*     */       }
/*     */     } 
/*     */     
/* 448 */     if ((nullBits & 0x40) != 0) {
/* 449 */       int chainingNextOffset = buffer.getIntLE(offset + 39);
/* 450 */       if (chainingNextOffset < 0) {
/* 451 */         return ValidationResult.error("Invalid offset for ChainingNext");
/*     */       }
/* 453 */       int pos = offset + 47 + chainingNextOffset;
/* 454 */       if (pos >= buffer.writerIndex()) {
/* 455 */         return ValidationResult.error("Offset out of bounds for ChainingNext");
/*     */       }
/* 457 */       int chainingNextCount = VarInt.peek(buffer, pos);
/* 458 */       if (chainingNextCount < 0) {
/* 459 */         return ValidationResult.error("Invalid array count for ChainingNext");
/*     */       }
/* 461 */       if (chainingNextCount > 4096000) {
/* 462 */         return ValidationResult.error("ChainingNext exceeds max length 4096000");
/*     */       }
/* 464 */       pos += VarInt.length(buffer, pos);
/* 465 */       pos += chainingNextCount * 4;
/* 466 */       if (pos > buffer.writerIndex()) {
/* 467 */         return ValidationResult.error("Buffer overflow reading ChainingNext");
/*     */       }
/*     */     } 
/*     */     
/* 471 */     if ((nullBits & 0x80) != 0) {
/* 472 */       int flagsOffset = buffer.getIntLE(offset + 43);
/* 473 */       if (flagsOffset < 0) {
/* 474 */         return ValidationResult.error("Invalid offset for Flags");
/*     */       }
/* 476 */       int pos = offset + 47 + flagsOffset;
/* 477 */       if (pos >= buffer.writerIndex()) {
/* 478 */         return ValidationResult.error("Offset out of bounds for Flags");
/*     */       }
/* 480 */       int flagsCount = VarInt.peek(buffer, pos);
/* 481 */       if (flagsCount < 0) {
/* 482 */         return ValidationResult.error("Invalid dictionary count for Flags");
/*     */       }
/* 484 */       if (flagsCount > 4096000) {
/* 485 */         return ValidationResult.error("Flags exceeds max length 4096000");
/*     */       }
/* 487 */       pos += VarInt.length(buffer, pos);
/* 488 */       for (int i = 0; i < flagsCount; i++) {
/* 489 */         int keyLen = VarInt.peek(buffer, pos);
/* 490 */         if (keyLen < 0) {
/* 491 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 493 */         if (keyLen > 4096000) {
/* 494 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 496 */         pos += VarInt.length(buffer, pos);
/* 497 */         pos += keyLen;
/* 498 */         if (pos > buffer.writerIndex()) {
/* 499 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 501 */         pos += 4;
/* 502 */         if (pos > buffer.writerIndex()) {
/* 503 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/* 507 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ChainingInteraction clone() {
/* 511 */     ChainingInteraction copy = new ChainingInteraction();
/* 512 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 513 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 514 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 515 */     copy.runTime = this.runTime;
/* 516 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 517 */     if (this.settings != null) {
/* 518 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 519 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 520 */       copy.settings = m;
/*     */     } 
/* 522 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 523 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 524 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 525 */     copy.chainId = this.chainId;
/* 526 */     copy.chainingAllowance = this.chainingAllowance;
/* 527 */     copy.chainingNext = (this.chainingNext != null) ? Arrays.copyOf(this.chainingNext, this.chainingNext.length) : null;
/* 528 */     copy.flags = (this.flags != null) ? new HashMap<>(this.flags) : null;
/* 529 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ChainingInteraction other;
/* 535 */     if (this == obj) return true; 
/* 536 */     if (obj instanceof ChainingInteraction) { other = (ChainingInteraction)obj; } else { return false; }
/* 537 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && Objects.equals(this.chainId, other.chainId) && this.chainingAllowance == other.chainingAllowance && Arrays.equals(this.chainingNext, other.chainingNext) && Objects.equals(this.flags, other.flags));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 542 */     int result = 1;
/* 543 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 544 */     result = 31 * result + Objects.hashCode(this.effects);
/* 545 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 546 */     result = 31 * result + Float.hashCode(this.runTime);
/* 547 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 548 */     result = 31 * result + Objects.hashCode(this.settings);
/* 549 */     result = 31 * result + Objects.hashCode(this.rules);
/* 550 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 551 */     result = 31 * result + Objects.hashCode(this.camera);
/* 552 */     result = 31 * result + Objects.hashCode(this.chainId);
/* 553 */     result = 31 * result + Float.hashCode(this.chainingAllowance);
/* 554 */     result = 31 * result + Arrays.hashCode(this.chainingNext);
/* 555 */     result = 31 * result + Objects.hashCode(this.flags);
/* 556 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ChainingInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */