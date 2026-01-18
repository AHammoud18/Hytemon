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
/*     */ public class WieldingInteraction
/*     */   extends ChargingInteraction
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 2;
/*     */   public static final int FIXED_BLOCK_SIZE = 58;
/*     */   public static final int VARIABLE_FIELD_COUNT = 8;
/*     */   public static final int VARIABLE_BLOCK_START = 90;
/*     */   
/*     */   public WieldingInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int failed, boolean allowIndefiniteHold, boolean displayProgress, boolean cancelOnOtherClick, boolean failOnDamage, float mouseSensitivityAdjustmentTarget, float mouseSensitivityAdjustmentDuration, @Nullable Map<Float, Integer> chargedNext, @Nullable Map<InteractionType, Integer> forks, @Nullable ChargingDelay chargingDelay, @Nullable DamageEffects blockedEffects, boolean hasModifiers, @Nullable AngledWielding angledWielding) {
/*  28 */     this.waitForDataFrom = waitForDataFrom;
/*  29 */     this.effects = effects;
/*  30 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  31 */     this.runTime = runTime;
/*  32 */     this.cancelOnItemChange = cancelOnItemChange;
/*  33 */     this.settings = settings;
/*  34 */     this.rules = rules;
/*  35 */     this.tags = tags;
/*  36 */     this.camera = camera;
/*  37 */     this.failed = failed;
/*  38 */     this.allowIndefiniteHold = allowIndefiniteHold;
/*  39 */     this.displayProgress = displayProgress;
/*  40 */     this.cancelOnOtherClick = cancelOnOtherClick;
/*  41 */     this.failOnDamage = failOnDamage;
/*  42 */     this.mouseSensitivityAdjustmentTarget = mouseSensitivityAdjustmentTarget;
/*  43 */     this.mouseSensitivityAdjustmentDuration = mouseSensitivityAdjustmentDuration;
/*  44 */     this.chargedNext = chargedNext;
/*  45 */     this.forks = forks;
/*  46 */     this.chargingDelay = chargingDelay;
/*  47 */     this.blockedEffects = blockedEffects;
/*  48 */     this.hasModifiers = hasModifiers;
/*  49 */     this.angledWielding = angledWielding;
/*     */   } public static final int MAX_SIZE = 1677721600; @Nullable
/*     */   public DamageEffects blockedEffects; public boolean hasModifiers; @Nullable
/*     */   public AngledWielding angledWielding; public WieldingInteraction() {} public WieldingInteraction(@Nonnull WieldingInteraction other) {
/*  53 */     this.waitForDataFrom = other.waitForDataFrom;
/*  54 */     this.effects = other.effects;
/*  55 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  56 */     this.runTime = other.runTime;
/*  57 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  58 */     this.settings = other.settings;
/*  59 */     this.rules = other.rules;
/*  60 */     this.tags = other.tags;
/*  61 */     this.camera = other.camera;
/*  62 */     this.failed = other.failed;
/*  63 */     this.allowIndefiniteHold = other.allowIndefiniteHold;
/*  64 */     this.displayProgress = other.displayProgress;
/*  65 */     this.cancelOnOtherClick = other.cancelOnOtherClick;
/*  66 */     this.failOnDamage = other.failOnDamage;
/*  67 */     this.mouseSensitivityAdjustmentTarget = other.mouseSensitivityAdjustmentTarget;
/*  68 */     this.mouseSensitivityAdjustmentDuration = other.mouseSensitivityAdjustmentDuration;
/*  69 */     this.chargedNext = other.chargedNext;
/*  70 */     this.forks = other.forks;
/*  71 */     this.chargingDelay = other.chargingDelay;
/*  72 */     this.blockedEffects = other.blockedEffects;
/*  73 */     this.hasModifiers = other.hasModifiers;
/*  74 */     this.angledWielding = other.angledWielding;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static WieldingInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  79 */     WieldingInteraction obj = new WieldingInteraction();
/*  80 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/*  81 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 2));
/*  82 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 3);
/*  83 */     obj.runTime = buf.getFloatLE(offset + 7);
/*  84 */     obj.cancelOnItemChange = (buf.getByte(offset + 11) != 0);
/*  85 */     obj.failed = buf.getIntLE(offset + 12);
/*  86 */     obj.allowIndefiniteHold = (buf.getByte(offset + 16) != 0);
/*  87 */     obj.displayProgress = (buf.getByte(offset + 17) != 0);
/*  88 */     obj.cancelOnOtherClick = (buf.getByte(offset + 18) != 0);
/*  89 */     obj.failOnDamage = (buf.getByte(offset + 19) != 0);
/*  90 */     obj.mouseSensitivityAdjustmentTarget = buf.getFloatLE(offset + 20);
/*  91 */     obj.mouseSensitivityAdjustmentDuration = buf.getFloatLE(offset + 24);
/*  92 */     if ((nullBits[0] & 0x80) != 0) obj.chargingDelay = ChargingDelay.deserialize(buf, offset + 28); 
/*  93 */     obj.hasModifiers = (buf.getByte(offset + 48) != 0);
/*  94 */     if ((nullBits[1] & 0x2) != 0) obj.angledWielding = AngledWielding.deserialize(buf, offset + 49);
/*     */     
/*  96 */     if ((nullBits[0] & 0x1) != 0) {
/*  97 */       int varPos0 = offset + 90 + buf.getIntLE(offset + 58);
/*  98 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/* 100 */     if ((nullBits[0] & 0x2) != 0) {
/* 101 */       int varPos1 = offset + 90 + buf.getIntLE(offset + 62);
/* 102 */       int settingsCount = VarInt.peek(buf, varPos1);
/* 103 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/* 104 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/* 105 */       int varIntLen = VarInt.length(buf, varPos1);
/* 106 */       obj.settings = new HashMap<>(settingsCount);
/* 107 */       int dictPos = varPos1 + varIntLen;
/* 108 */       for (int i = 0; i < settingsCount; i++) {
/* 109 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/* 110 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/* 111 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/* 112 */         if (obj.settings.put(key, val) != null)
/* 113 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/* 116 */     if ((nullBits[0] & 0x4) != 0) {
/* 117 */       int varPos2 = offset + 90 + buf.getIntLE(offset + 66);
/* 118 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/* 120 */     if ((nullBits[0] & 0x8) != 0) {
/* 121 */       int varPos3 = offset + 90 + buf.getIntLE(offset + 70);
/* 122 */       int tagsCount = VarInt.peek(buf, varPos3);
/* 123 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/* 124 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/* 125 */       int varIntLen = VarInt.length(buf, varPos3);
/* 126 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/* 127 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/* 128 */       obj.tags = new int[tagsCount];
/* 129 */       for (int i = 0; i < tagsCount; i++) {
/* 130 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 133 */     if ((nullBits[0] & 0x10) != 0) {
/* 134 */       int varPos4 = offset + 90 + buf.getIntLE(offset + 74);
/* 135 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/* 137 */     if ((nullBits[0] & 0x20) != 0) {
/* 138 */       int varPos5 = offset + 90 + buf.getIntLE(offset + 78);
/* 139 */       int chargedNextCount = VarInt.peek(buf, varPos5);
/* 140 */       if (chargedNextCount < 0) throw ProtocolException.negativeLength("ChargedNext", chargedNextCount); 
/* 141 */       if (chargedNextCount > 4096000) throw ProtocolException.dictionaryTooLarge("ChargedNext", chargedNextCount, 4096000); 
/* 142 */       int varIntLen = VarInt.length(buf, varPos5);
/* 143 */       obj.chargedNext = new HashMap<>(chargedNextCount);
/* 144 */       int dictPos = varPos5 + varIntLen;
/* 145 */       for (int i = 0; i < chargedNextCount; i++) {
/* 146 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/* 147 */         int val = buf.getIntLE(dictPos); dictPos += 4;
/* 148 */         if (obj.chargedNext.put(Float.valueOf(key), Integer.valueOf(val)) != null)
/* 149 */           throw ProtocolException.duplicateKey("chargedNext", Float.valueOf(key)); 
/*     */       } 
/*     */     } 
/* 152 */     if ((nullBits[0] & 0x40) != 0) {
/* 153 */       int varPos6 = offset + 90 + buf.getIntLE(offset + 82);
/* 154 */       int forksCount = VarInt.peek(buf, varPos6);
/* 155 */       if (forksCount < 0) throw ProtocolException.negativeLength("Forks", forksCount); 
/* 156 */       if (forksCount > 4096000) throw ProtocolException.dictionaryTooLarge("Forks", forksCount, 4096000); 
/* 157 */       int varIntLen = VarInt.length(buf, varPos6);
/* 158 */       obj.forks = new HashMap<>(forksCount);
/* 159 */       int dictPos = varPos6 + varIntLen;
/* 160 */       for (int i = 0; i < forksCount; i++) {
/* 161 */         InteractionType key = InteractionType.fromValue(buf.getByte(dictPos)); dictPos++;
/* 162 */         int val = buf.getIntLE(dictPos); dictPos += 4;
/* 163 */         if (obj.forks.put(key, Integer.valueOf(val)) != null)
/* 164 */           throw ProtocolException.duplicateKey("forks", key); 
/*     */       } 
/*     */     } 
/* 167 */     if ((nullBits[1] & 0x1) != 0) {
/* 168 */       int varPos7 = offset + 90 + buf.getIntLE(offset + 86);
/* 169 */       obj.blockedEffects = DamageEffects.deserialize(buf, varPos7);
/*     */     } 
/*     */     
/* 172 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 176 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/* 177 */     int maxEnd = 90;
/* 178 */     if ((nullBits[0] & 0x1) != 0) {
/* 179 */       int fieldOffset0 = buf.getIntLE(offset + 58);
/* 180 */       int pos0 = offset + 90 + fieldOffset0;
/* 181 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 182 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 184 */     if ((nullBits[0] & 0x2) != 0) {
/* 185 */       int fieldOffset1 = buf.getIntLE(offset + 62);
/* 186 */       int pos1 = offset + 90 + fieldOffset1;
/* 187 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 188 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 189 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 191 */     if ((nullBits[0] & 0x4) != 0) {
/* 192 */       int fieldOffset2 = buf.getIntLE(offset + 66);
/* 193 */       int pos2 = offset + 90 + fieldOffset2;
/* 194 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 195 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 197 */     if ((nullBits[0] & 0x8) != 0) {
/* 198 */       int fieldOffset3 = buf.getIntLE(offset + 70);
/* 199 */       int pos3 = offset + 90 + fieldOffset3;
/* 200 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 201 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 203 */     if ((nullBits[0] & 0x10) != 0) {
/* 204 */       int fieldOffset4 = buf.getIntLE(offset + 74);
/* 205 */       int pos4 = offset + 90 + fieldOffset4;
/* 206 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 207 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 209 */     if ((nullBits[0] & 0x20) != 0) {
/* 210 */       int fieldOffset5 = buf.getIntLE(offset + 78);
/* 211 */       int pos5 = offset + 90 + fieldOffset5;
/* 212 */       int dictLen = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5);
/* 213 */       for (int i = 0; i < dictLen; ) { pos5 += 4; pos5 += 4; i++; }
/* 214 */        if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 216 */     if ((nullBits[0] & 0x40) != 0) {
/* 217 */       int fieldOffset6 = buf.getIntLE(offset + 82);
/* 218 */       int pos6 = offset + 90 + fieldOffset6;
/* 219 */       int dictLen = VarInt.peek(buf, pos6); pos6 += VarInt.length(buf, pos6);
/* 220 */       for (int i = 0; i < dictLen; ) { pos6++; pos6 += 4; i++; }
/* 221 */        if (pos6 - offset > maxEnd) maxEnd = pos6 - offset; 
/*     */     } 
/* 223 */     if ((nullBits[1] & 0x1) != 0) {
/* 224 */       int fieldOffset7 = buf.getIntLE(offset + 86);
/* 225 */       int pos7 = offset + 90 + fieldOffset7;
/* 226 */       pos7 += DamageEffects.computeBytesConsumed(buf, pos7);
/* 227 */       if (pos7 - offset > maxEnd) maxEnd = pos7 - offset; 
/*     */     } 
/* 229 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 235 */     int startPos = buf.writerIndex();
/* 236 */     byte[] nullBits = new byte[2];
/* 237 */     if (this.effects != null) nullBits[0] = (byte)(nullBits[0] | 0x1); 
/* 238 */     if (this.settings != null) nullBits[0] = (byte)(nullBits[0] | 0x2); 
/* 239 */     if (this.rules != null) nullBits[0] = (byte)(nullBits[0] | 0x4); 
/* 240 */     if (this.tags != null) nullBits[0] = (byte)(nullBits[0] | 0x8); 
/* 241 */     if (this.camera != null) nullBits[0] = (byte)(nullBits[0] | 0x10); 
/* 242 */     if (this.chargedNext != null) nullBits[0] = (byte)(nullBits[0] | 0x20); 
/* 243 */     if (this.forks != null) nullBits[0] = (byte)(nullBits[0] | 0x40); 
/* 244 */     if (this.chargingDelay != null) nullBits[0] = (byte)(nullBits[0] | 0x80); 
/* 245 */     if (this.blockedEffects != null) nullBits[1] = (byte)(nullBits[1] | 0x1); 
/* 246 */     if (this.angledWielding != null) nullBits[1] = (byte)(nullBits[1] | 0x2); 
/* 247 */     buf.writeBytes(nullBits);
/*     */     
/* 249 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 250 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 251 */     buf.writeFloatLE(this.runTime);
/* 252 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 253 */     buf.writeIntLE(this.failed);
/* 254 */     buf.writeByte(this.allowIndefiniteHold ? 1 : 0);
/* 255 */     buf.writeByte(this.displayProgress ? 1 : 0);
/* 256 */     buf.writeByte(this.cancelOnOtherClick ? 1 : 0);
/* 257 */     buf.writeByte(this.failOnDamage ? 1 : 0);
/* 258 */     buf.writeFloatLE(this.mouseSensitivityAdjustmentTarget);
/* 259 */     buf.writeFloatLE(this.mouseSensitivityAdjustmentDuration);
/* 260 */     if (this.chargingDelay != null) { this.chargingDelay.serialize(buf); } else { buf.writeZero(20); }
/* 261 */      buf.writeByte(this.hasModifiers ? 1 : 0);
/* 262 */     if (this.angledWielding != null) { this.angledWielding.serialize(buf); } else { buf.writeZero(9); }
/*     */     
/* 264 */     int effectsOffsetSlot = buf.writerIndex();
/* 265 */     buf.writeIntLE(0);
/* 266 */     int settingsOffsetSlot = buf.writerIndex();
/* 267 */     buf.writeIntLE(0);
/* 268 */     int rulesOffsetSlot = buf.writerIndex();
/* 269 */     buf.writeIntLE(0);
/* 270 */     int tagsOffsetSlot = buf.writerIndex();
/* 271 */     buf.writeIntLE(0);
/* 272 */     int cameraOffsetSlot = buf.writerIndex();
/* 273 */     buf.writeIntLE(0);
/* 274 */     int chargedNextOffsetSlot = buf.writerIndex();
/* 275 */     buf.writeIntLE(0);
/* 276 */     int forksOffsetSlot = buf.writerIndex();
/* 277 */     buf.writeIntLE(0);
/* 278 */     int blockedEffectsOffsetSlot = buf.writerIndex();
/* 279 */     buf.writeIntLE(0);
/*     */     
/* 281 */     int varBlockStart = buf.writerIndex();
/* 282 */     if (this.effects != null) {
/* 283 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 284 */       this.effects.serialize(buf);
/*     */     } else {
/* 286 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 288 */     if (this.settings != null)
/* 289 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 290 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 292 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 294 */     if (this.rules != null) {
/* 295 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 296 */       this.rules.serialize(buf);
/*     */     } else {
/* 298 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 300 */     if (this.tags != null) {
/* 301 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 302 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 304 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 306 */     if (this.camera != null) {
/* 307 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 308 */       this.camera.serialize(buf);
/*     */     } else {
/* 310 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 312 */     if (this.chargedNext != null)
/* 313 */     { buf.setIntLE(chargedNextOffsetSlot, buf.writerIndex() - varBlockStart);
/* 314 */       if (this.chargedNext.size() > 4096000) throw ProtocolException.dictionaryTooLarge("ChargedNext", this.chargedNext.size(), 4096000);  VarInt.write(buf, this.chargedNext.size()); for (Map.Entry<Float, Integer> e : this.chargedNext.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*     */        }
/* 316 */     else { buf.setIntLE(chargedNextOffsetSlot, -1); }
/*     */     
/* 318 */     if (this.forks != null)
/* 319 */     { buf.setIntLE(forksOffsetSlot, buf.writerIndex() - varBlockStart);
/* 320 */       if (this.forks.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Forks", this.forks.size(), 4096000);  VarInt.write(buf, this.forks.size()); for (Map.Entry<InteractionType, Integer> e : this.forks.entrySet()) { buf.writeByte(((InteractionType)e.getKey()).getValue()); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*     */        }
/* 322 */     else { buf.setIntLE(forksOffsetSlot, -1); }
/*     */     
/* 324 */     if (this.blockedEffects != null) {
/* 325 */       buf.setIntLE(blockedEffectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 326 */       this.blockedEffects.serialize(buf);
/*     */     } else {
/* 328 */       buf.setIntLE(blockedEffectsOffsetSlot, -1);
/*     */     } 
/* 330 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 336 */     int size = 90;
/* 337 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 338 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 339 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 340 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 341 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 342 */     if (this.chargedNext != null) size += VarInt.size(this.chargedNext.size()) + this.chargedNext.size() * 8; 
/* 343 */     if (this.forks != null) size += VarInt.size(this.forks.size()) + this.forks.size() * 5; 
/* 344 */     if (this.blockedEffects != null) size += this.blockedEffects.computeSize();
/*     */     
/* 346 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 350 */     if (buffer.readableBytes() - offset < 90) {
/* 351 */       return ValidationResult.error("Buffer too small: expected at least 90 bytes");
/*     */     }
/*     */     
/* 354 */     byte[] nullBits = PacketIO.readBytes(buffer, offset, 2);
/*     */     
/* 356 */     if ((nullBits[0] & 0x1) != 0) {
/* 357 */       int effectsOffset = buffer.getIntLE(offset + 58);
/* 358 */       if (effectsOffset < 0) {
/* 359 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 361 */       int pos = offset + 90 + effectsOffset;
/* 362 */       if (pos >= buffer.writerIndex()) {
/* 363 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 365 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 366 */       if (!effectsResult.isValid()) {
/* 367 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 369 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 372 */     if ((nullBits[0] & 0x2) != 0) {
/* 373 */       int settingsOffset = buffer.getIntLE(offset + 62);
/* 374 */       if (settingsOffset < 0) {
/* 375 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 377 */       int pos = offset + 90 + settingsOffset;
/* 378 */       if (pos >= buffer.writerIndex()) {
/* 379 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 381 */       int settingsCount = VarInt.peek(buffer, pos);
/* 382 */       if (settingsCount < 0) {
/* 383 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 385 */       if (settingsCount > 4096000) {
/* 386 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 388 */       pos += VarInt.length(buffer, pos);
/* 389 */       for (int i = 0; i < settingsCount; i++) {
/* 390 */         pos++;
/*     */         
/* 392 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 397 */     if ((nullBits[0] & 0x4) != 0) {
/* 398 */       int rulesOffset = buffer.getIntLE(offset + 66);
/* 399 */       if (rulesOffset < 0) {
/* 400 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 402 */       int pos = offset + 90 + rulesOffset;
/* 403 */       if (pos >= buffer.writerIndex()) {
/* 404 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 406 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 407 */       if (!rulesResult.isValid()) {
/* 408 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 410 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 413 */     if ((nullBits[0] & 0x8) != 0) {
/* 414 */       int tagsOffset = buffer.getIntLE(offset + 70);
/* 415 */       if (tagsOffset < 0) {
/* 416 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 418 */       int pos = offset + 90 + tagsOffset;
/* 419 */       if (pos >= buffer.writerIndex()) {
/* 420 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 422 */       int tagsCount = VarInt.peek(buffer, pos);
/* 423 */       if (tagsCount < 0) {
/* 424 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 426 */       if (tagsCount > 4096000) {
/* 427 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 429 */       pos += VarInt.length(buffer, pos);
/* 430 */       pos += tagsCount * 4;
/* 431 */       if (pos > buffer.writerIndex()) {
/* 432 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 436 */     if ((nullBits[0] & 0x10) != 0) {
/* 437 */       int cameraOffset = buffer.getIntLE(offset + 74);
/* 438 */       if (cameraOffset < 0) {
/* 439 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 441 */       int pos = offset + 90 + cameraOffset;
/* 442 */       if (pos >= buffer.writerIndex()) {
/* 443 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 445 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 446 */       if (!cameraResult.isValid()) {
/* 447 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 449 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 452 */     if ((nullBits[0] & 0x20) != 0) {
/* 453 */       int chargedNextOffset = buffer.getIntLE(offset + 78);
/* 454 */       if (chargedNextOffset < 0) {
/* 455 */         return ValidationResult.error("Invalid offset for ChargedNext");
/*     */       }
/* 457 */       int pos = offset + 90 + chargedNextOffset;
/* 458 */       if (pos >= buffer.writerIndex()) {
/* 459 */         return ValidationResult.error("Offset out of bounds for ChargedNext");
/*     */       }
/* 461 */       int chargedNextCount = VarInt.peek(buffer, pos);
/* 462 */       if (chargedNextCount < 0) {
/* 463 */         return ValidationResult.error("Invalid dictionary count for ChargedNext");
/*     */       }
/* 465 */       if (chargedNextCount > 4096000) {
/* 466 */         return ValidationResult.error("ChargedNext exceeds max length 4096000");
/*     */       }
/* 468 */       pos += VarInt.length(buffer, pos);
/* 469 */       for (int i = 0; i < chargedNextCount; i++) {
/* 470 */         pos += 4;
/* 471 */         if (pos > buffer.writerIndex()) {
/* 472 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 474 */         pos += 4;
/* 475 */         if (pos > buffer.writerIndex()) {
/* 476 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 481 */     if ((nullBits[0] & 0x40) != 0) {
/* 482 */       int forksOffset = buffer.getIntLE(offset + 82);
/* 483 */       if (forksOffset < 0) {
/* 484 */         return ValidationResult.error("Invalid offset for Forks");
/*     */       }
/* 486 */       int pos = offset + 90 + forksOffset;
/* 487 */       if (pos >= buffer.writerIndex()) {
/* 488 */         return ValidationResult.error("Offset out of bounds for Forks");
/*     */       }
/* 490 */       int forksCount = VarInt.peek(buffer, pos);
/* 491 */       if (forksCount < 0) {
/* 492 */         return ValidationResult.error("Invalid dictionary count for Forks");
/*     */       }
/* 494 */       if (forksCount > 4096000) {
/* 495 */         return ValidationResult.error("Forks exceeds max length 4096000");
/*     */       }
/* 497 */       pos += VarInt.length(buffer, pos);
/* 498 */       for (int i = 0; i < forksCount; i++) {
/* 499 */         pos++;
/*     */         
/* 501 */         pos += 4;
/* 502 */         if (pos > buffer.writerIndex()) {
/* 503 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 508 */     if ((nullBits[1] & 0x1) != 0) {
/* 509 */       int blockedEffectsOffset = buffer.getIntLE(offset + 86);
/* 510 */       if (blockedEffectsOffset < 0) {
/* 511 */         return ValidationResult.error("Invalid offset for BlockedEffects");
/*     */       }
/* 513 */       int pos = offset + 90 + blockedEffectsOffset;
/* 514 */       if (pos >= buffer.writerIndex()) {
/* 515 */         return ValidationResult.error("Offset out of bounds for BlockedEffects");
/*     */       }
/* 517 */       ValidationResult blockedEffectsResult = DamageEffects.validateStructure(buffer, pos);
/* 518 */       if (!blockedEffectsResult.isValid()) {
/* 519 */         return ValidationResult.error("Invalid BlockedEffects: " + blockedEffectsResult.error());
/*     */       }
/* 521 */       pos += DamageEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 523 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public WieldingInteraction clone() {
/* 527 */     WieldingInteraction copy = new WieldingInteraction();
/* 528 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 529 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 530 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 531 */     copy.runTime = this.runTime;
/* 532 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 533 */     if (this.settings != null) {
/* 534 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 535 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 536 */       copy.settings = m;
/*     */     } 
/* 538 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 539 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 540 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 541 */     copy.failed = this.failed;
/* 542 */     copy.allowIndefiniteHold = this.allowIndefiniteHold;
/* 543 */     copy.displayProgress = this.displayProgress;
/* 544 */     copy.cancelOnOtherClick = this.cancelOnOtherClick;
/* 545 */     copy.failOnDamage = this.failOnDamage;
/* 546 */     copy.mouseSensitivityAdjustmentTarget = this.mouseSensitivityAdjustmentTarget;
/* 547 */     copy.mouseSensitivityAdjustmentDuration = this.mouseSensitivityAdjustmentDuration;
/* 548 */     copy.chargedNext = (this.chargedNext != null) ? new HashMap<>(this.chargedNext) : null;
/* 549 */     copy.forks = (this.forks != null) ? new HashMap<>(this.forks) : null;
/* 550 */     copy.chargingDelay = (this.chargingDelay != null) ? this.chargingDelay.clone() : null;
/* 551 */     copy.blockedEffects = (this.blockedEffects != null) ? this.blockedEffects.clone() : null;
/* 552 */     copy.hasModifiers = this.hasModifiers;
/* 553 */     copy.angledWielding = (this.angledWielding != null) ? this.angledWielding.clone() : null;
/* 554 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     WieldingInteraction other;
/* 560 */     if (this == obj) return true; 
/* 561 */     if (obj instanceof WieldingInteraction) { other = (WieldingInteraction)obj; } else { return false; }
/* 562 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.failed == other.failed && this.allowIndefiniteHold == other.allowIndefiniteHold && this.displayProgress == other.displayProgress && this.cancelOnOtherClick == other.cancelOnOtherClick && this.failOnDamage == other.failOnDamage && this.mouseSensitivityAdjustmentTarget == other.mouseSensitivityAdjustmentTarget && this.mouseSensitivityAdjustmentDuration == other.mouseSensitivityAdjustmentDuration && Objects.equals(this.chargedNext, other.chargedNext) && Objects.equals(this.forks, other.forks) && Objects.equals(this.chargingDelay, other.chargingDelay) && Objects.equals(this.blockedEffects, other.blockedEffects) && this.hasModifiers == other.hasModifiers && Objects.equals(this.angledWielding, other.angledWielding));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 567 */     int result = 1;
/* 568 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 569 */     result = 31 * result + Objects.hashCode(this.effects);
/* 570 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 571 */     result = 31 * result + Float.hashCode(this.runTime);
/* 572 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 573 */     result = 31 * result + Objects.hashCode(this.settings);
/* 574 */     result = 31 * result + Objects.hashCode(this.rules);
/* 575 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 576 */     result = 31 * result + Objects.hashCode(this.camera);
/* 577 */     result = 31 * result + Integer.hashCode(this.failed);
/* 578 */     result = 31 * result + Boolean.hashCode(this.allowIndefiniteHold);
/* 579 */     result = 31 * result + Boolean.hashCode(this.displayProgress);
/* 580 */     result = 31 * result + Boolean.hashCode(this.cancelOnOtherClick);
/* 581 */     result = 31 * result + Boolean.hashCode(this.failOnDamage);
/* 582 */     result = 31 * result + Float.hashCode(this.mouseSensitivityAdjustmentTarget);
/* 583 */     result = 31 * result + Float.hashCode(this.mouseSensitivityAdjustmentDuration);
/* 584 */     result = 31 * result + Objects.hashCode(this.chargedNext);
/* 585 */     result = 31 * result + Objects.hashCode(this.forks);
/* 586 */     result = 31 * result + Objects.hashCode(this.chargingDelay);
/* 587 */     result = 31 * result + Objects.hashCode(this.blockedEffects);
/* 588 */     result = 31 * result + Boolean.hashCode(this.hasModifiers);
/* 589 */     result = 31 * result + Objects.hashCode(this.angledWielding);
/* 590 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\WieldingInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */