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
/*     */ public class DamageEntityInteraction extends Interaction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 2;
/*     */   public static final int FIXED_BLOCK_SIZE = 24;
/*     */   public static final int VARIABLE_FIELD_COUNT = 9;
/*     */   public static final int VARIABLE_BLOCK_START = 60;
/*     */   public static final int MAX_SIZE = 1677721600;
/*  20 */   public int next = Integer.MIN_VALUE;
/*  21 */   public int failed = Integer.MIN_VALUE;
/*  22 */   public int blocked = Integer.MIN_VALUE; @Nullable
/*     */   public DamageEffects damageEffects;
/*     */   @Nullable
/*     */   public AngledDamage[] angledDamage;
/*     */   @Nullable
/*     */   public Map<String, TargetedDamage> targetedDamage;
/*     */   @Nullable
/*     */   public EntityStatOnHit[] entityStatsOnHit;
/*     */   
/*     */   public DamageEntityInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, int blocked, @Nullable DamageEffects damageEffects, @Nullable AngledDamage[] angledDamage, @Nullable Map<String, TargetedDamage> targetedDamage, @Nullable EntityStatOnHit[] entityStatsOnHit) {
/*  32 */     this.waitForDataFrom = waitForDataFrom;
/*  33 */     this.effects = effects;
/*  34 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  35 */     this.runTime = runTime;
/*  36 */     this.cancelOnItemChange = cancelOnItemChange;
/*  37 */     this.settings = settings;
/*  38 */     this.rules = rules;
/*  39 */     this.tags = tags;
/*  40 */     this.camera = camera;
/*  41 */     this.next = next;
/*  42 */     this.failed = failed;
/*  43 */     this.blocked = blocked;
/*  44 */     this.damageEffects = damageEffects;
/*  45 */     this.angledDamage = angledDamage;
/*  46 */     this.targetedDamage = targetedDamage;
/*  47 */     this.entityStatsOnHit = entityStatsOnHit;
/*     */   }
/*     */   
/*     */   public DamageEntityInteraction(@Nonnull DamageEntityInteraction other) {
/*  51 */     this.waitForDataFrom = other.waitForDataFrom;
/*  52 */     this.effects = other.effects;
/*  53 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  54 */     this.runTime = other.runTime;
/*  55 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  56 */     this.settings = other.settings;
/*  57 */     this.rules = other.rules;
/*  58 */     this.tags = other.tags;
/*  59 */     this.camera = other.camera;
/*  60 */     this.next = other.next;
/*  61 */     this.failed = other.failed;
/*  62 */     this.blocked = other.blocked;
/*  63 */     this.damageEffects = other.damageEffects;
/*  64 */     this.angledDamage = other.angledDamage;
/*  65 */     this.targetedDamage = other.targetedDamage;
/*  66 */     this.entityStatsOnHit = other.entityStatsOnHit;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static DamageEntityInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  71 */     DamageEntityInteraction obj = new DamageEntityInteraction();
/*  72 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/*  73 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 2));
/*  74 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 3);
/*  75 */     obj.runTime = buf.getFloatLE(offset + 7);
/*  76 */     obj.cancelOnItemChange = (buf.getByte(offset + 11) != 0);
/*  77 */     obj.next = buf.getIntLE(offset + 12);
/*  78 */     obj.failed = buf.getIntLE(offset + 16);
/*  79 */     obj.blocked = buf.getIntLE(offset + 20);
/*     */     
/*  81 */     if ((nullBits[0] & 0x1) != 0) {
/*  82 */       int varPos0 = offset + 60 + buf.getIntLE(offset + 24);
/*  83 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  85 */     if ((nullBits[0] & 0x2) != 0) {
/*  86 */       int varPos1 = offset + 60 + buf.getIntLE(offset + 28);
/*  87 */       int settingsCount = VarInt.peek(buf, varPos1);
/*  88 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  89 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  90 */       int varIntLen = VarInt.length(buf, varPos1);
/*  91 */       obj.settings = new HashMap<>(settingsCount);
/*  92 */       int dictPos = varPos1 + varIntLen;
/*  93 */       for (int i = 0; i < settingsCount; i++) {
/*  94 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  95 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/*  96 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/*  97 */         if (obj.settings.put(key, val) != null)
/*  98 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/* 101 */     if ((nullBits[0] & 0x4) != 0) {
/* 102 */       int varPos2 = offset + 60 + buf.getIntLE(offset + 32);
/* 103 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/* 105 */     if ((nullBits[0] & 0x8) != 0) {
/* 106 */       int varPos3 = offset + 60 + buf.getIntLE(offset + 36);
/* 107 */       int tagsCount = VarInt.peek(buf, varPos3);
/* 108 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/* 109 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/* 110 */       int varIntLen = VarInt.length(buf, varPos3);
/* 111 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/* 112 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/* 113 */       obj.tags = new int[tagsCount];
/* 114 */       for (int i = 0; i < tagsCount; i++) {
/* 115 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 118 */     if ((nullBits[0] & 0x10) != 0) {
/* 119 */       int varPos4 = offset + 60 + buf.getIntLE(offset + 40);
/* 120 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/* 122 */     if ((nullBits[0] & 0x20) != 0) {
/* 123 */       int varPos5 = offset + 60 + buf.getIntLE(offset + 44);
/* 124 */       obj.damageEffects = DamageEffects.deserialize(buf, varPos5);
/*     */     } 
/* 126 */     if ((nullBits[0] & 0x40) != 0) {
/* 127 */       int varPos6 = offset + 60 + buf.getIntLE(offset + 48);
/* 128 */       int angledDamageCount = VarInt.peek(buf, varPos6);
/* 129 */       if (angledDamageCount < 0) throw ProtocolException.negativeLength("AngledDamage", angledDamageCount); 
/* 130 */       if (angledDamageCount > 4096000) throw ProtocolException.arrayTooLong("AngledDamage", angledDamageCount, 4096000); 
/* 131 */       int varIntLen = VarInt.length(buf, varPos6);
/* 132 */       if ((varPos6 + varIntLen) + angledDamageCount * 21L > buf.readableBytes())
/* 133 */         throw ProtocolException.bufferTooSmall("AngledDamage", varPos6 + varIntLen + angledDamageCount * 21, buf.readableBytes()); 
/* 134 */       obj.angledDamage = new AngledDamage[angledDamageCount];
/* 135 */       int elemPos = varPos6 + varIntLen;
/* 136 */       for (int i = 0; i < angledDamageCount; i++) {
/* 137 */         obj.angledDamage[i] = AngledDamage.deserialize(buf, elemPos);
/* 138 */         elemPos += AngledDamage.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/* 141 */     if ((nullBits[0] & 0x80) != 0) {
/* 142 */       int varPos7 = offset + 60 + buf.getIntLE(offset + 52);
/* 143 */       int targetedDamageCount = VarInt.peek(buf, varPos7);
/* 144 */       if (targetedDamageCount < 0) throw ProtocolException.negativeLength("TargetedDamage", targetedDamageCount); 
/* 145 */       if (targetedDamageCount > 4096000) throw ProtocolException.dictionaryTooLarge("TargetedDamage", targetedDamageCount, 4096000); 
/* 146 */       int varIntLen = VarInt.length(buf, varPos7);
/* 147 */       obj.targetedDamage = new HashMap<>(targetedDamageCount);
/* 148 */       int dictPos = varPos7 + varIntLen;
/* 149 */       for (int i = 0; i < targetedDamageCount; i++) {
/* 150 */         int keyLen = VarInt.peek(buf, dictPos);
/* 151 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/* 152 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/* 153 */         int keyVarLen = VarInt.length(buf, dictPos);
/* 154 */         String key = PacketIO.readVarString(buf, dictPos);
/* 155 */         dictPos += keyVarLen + keyLen;
/* 156 */         TargetedDamage val = TargetedDamage.deserialize(buf, dictPos);
/* 157 */         dictPos += TargetedDamage.computeBytesConsumed(buf, dictPos);
/* 158 */         if (obj.targetedDamage.put(key, val) != null)
/* 159 */           throw ProtocolException.duplicateKey("targetedDamage", key); 
/*     */       } 
/*     */     } 
/* 162 */     if ((nullBits[1] & 0x1) != 0) {
/* 163 */       int varPos8 = offset + 60 + buf.getIntLE(offset + 56);
/* 164 */       int entityStatsOnHitCount = VarInt.peek(buf, varPos8);
/* 165 */       if (entityStatsOnHitCount < 0) throw ProtocolException.negativeLength("EntityStatsOnHit", entityStatsOnHitCount); 
/* 166 */       if (entityStatsOnHitCount > 4096000) throw ProtocolException.arrayTooLong("EntityStatsOnHit", entityStatsOnHitCount, 4096000); 
/* 167 */       int varIntLen = VarInt.length(buf, varPos8);
/* 168 */       if ((varPos8 + varIntLen) + entityStatsOnHitCount * 13L > buf.readableBytes())
/* 169 */         throw ProtocolException.bufferTooSmall("EntityStatsOnHit", varPos8 + varIntLen + entityStatsOnHitCount * 13, buf.readableBytes()); 
/* 170 */       obj.entityStatsOnHit = new EntityStatOnHit[entityStatsOnHitCount];
/* 171 */       int elemPos = varPos8 + varIntLen;
/* 172 */       for (int i = 0; i < entityStatsOnHitCount; i++) {
/* 173 */         obj.entityStatsOnHit[i] = EntityStatOnHit.deserialize(buf, elemPos);
/* 174 */         elemPos += EntityStatOnHit.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/* 178 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 182 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/* 183 */     int maxEnd = 60;
/* 184 */     if ((nullBits[0] & 0x1) != 0) {
/* 185 */       int fieldOffset0 = buf.getIntLE(offset + 24);
/* 186 */       int pos0 = offset + 60 + fieldOffset0;
/* 187 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 188 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 190 */     if ((nullBits[0] & 0x2) != 0) {
/* 191 */       int fieldOffset1 = buf.getIntLE(offset + 28);
/* 192 */       int pos1 = offset + 60 + fieldOffset1;
/* 193 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 194 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 195 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 197 */     if ((nullBits[0] & 0x4) != 0) {
/* 198 */       int fieldOffset2 = buf.getIntLE(offset + 32);
/* 199 */       int pos2 = offset + 60 + fieldOffset2;
/* 200 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 201 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 203 */     if ((nullBits[0] & 0x8) != 0) {
/* 204 */       int fieldOffset3 = buf.getIntLE(offset + 36);
/* 205 */       int pos3 = offset + 60 + fieldOffset3;
/* 206 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 207 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 209 */     if ((nullBits[0] & 0x10) != 0) {
/* 210 */       int fieldOffset4 = buf.getIntLE(offset + 40);
/* 211 */       int pos4 = offset + 60 + fieldOffset4;
/* 212 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 213 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 215 */     if ((nullBits[0] & 0x20) != 0) {
/* 216 */       int fieldOffset5 = buf.getIntLE(offset + 44);
/* 217 */       int pos5 = offset + 60 + fieldOffset5;
/* 218 */       pos5 += DamageEffects.computeBytesConsumed(buf, pos5);
/* 219 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 221 */     if ((nullBits[0] & 0x40) != 0) {
/* 222 */       int fieldOffset6 = buf.getIntLE(offset + 48);
/* 223 */       int pos6 = offset + 60 + fieldOffset6;
/* 224 */       int arrLen = VarInt.peek(buf, pos6); pos6 += VarInt.length(buf, pos6);
/* 225 */       for (int i = 0; i < arrLen; ) { pos6 += AngledDamage.computeBytesConsumed(buf, pos6); i++; }
/* 226 */        if (pos6 - offset > maxEnd) maxEnd = pos6 - offset; 
/*     */     } 
/* 228 */     if ((nullBits[0] & 0x80) != 0) {
/* 229 */       int fieldOffset7 = buf.getIntLE(offset + 52);
/* 230 */       int pos7 = offset + 60 + fieldOffset7;
/* 231 */       int dictLen = VarInt.peek(buf, pos7); pos7 += VarInt.length(buf, pos7);
/* 232 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos7); pos7 += VarInt.length(buf, pos7) + sl; pos7 += TargetedDamage.computeBytesConsumed(buf, pos7); i++; }
/* 233 */        if (pos7 - offset > maxEnd) maxEnd = pos7 - offset; 
/*     */     } 
/* 235 */     if ((nullBits[1] & 0x1) != 0) {
/* 236 */       int fieldOffset8 = buf.getIntLE(offset + 56);
/* 237 */       int pos8 = offset + 60 + fieldOffset8;
/* 238 */       int arrLen = VarInt.peek(buf, pos8); pos8 += VarInt.length(buf, pos8);
/* 239 */       for (int i = 0; i < arrLen; ) { pos8 += EntityStatOnHit.computeBytesConsumed(buf, pos8); i++; }
/* 240 */        if (pos8 - offset > maxEnd) maxEnd = pos8 - offset; 
/*     */     } 
/* 242 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 248 */     int startPos = buf.writerIndex();
/* 249 */     byte[] nullBits = new byte[2];
/* 250 */     if (this.effects != null) nullBits[0] = (byte)(nullBits[0] | 0x1); 
/* 251 */     if (this.settings != null) nullBits[0] = (byte)(nullBits[0] | 0x2); 
/* 252 */     if (this.rules != null) nullBits[0] = (byte)(nullBits[0] | 0x4); 
/* 253 */     if (this.tags != null) nullBits[0] = (byte)(nullBits[0] | 0x8); 
/* 254 */     if (this.camera != null) nullBits[0] = (byte)(nullBits[0] | 0x10); 
/* 255 */     if (this.damageEffects != null) nullBits[0] = (byte)(nullBits[0] | 0x20); 
/* 256 */     if (this.angledDamage != null) nullBits[0] = (byte)(nullBits[0] | 0x40); 
/* 257 */     if (this.targetedDamage != null) nullBits[0] = (byte)(nullBits[0] | 0x80); 
/* 258 */     if (this.entityStatsOnHit != null) nullBits[1] = (byte)(nullBits[1] | 0x1); 
/* 259 */     buf.writeBytes(nullBits);
/*     */     
/* 261 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 262 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 263 */     buf.writeFloatLE(this.runTime);
/* 264 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 265 */     buf.writeIntLE(this.next);
/* 266 */     buf.writeIntLE(this.failed);
/* 267 */     buf.writeIntLE(this.blocked);
/*     */     
/* 269 */     int effectsOffsetSlot = buf.writerIndex();
/* 270 */     buf.writeIntLE(0);
/* 271 */     int settingsOffsetSlot = buf.writerIndex();
/* 272 */     buf.writeIntLE(0);
/* 273 */     int rulesOffsetSlot = buf.writerIndex();
/* 274 */     buf.writeIntLE(0);
/* 275 */     int tagsOffsetSlot = buf.writerIndex();
/* 276 */     buf.writeIntLE(0);
/* 277 */     int cameraOffsetSlot = buf.writerIndex();
/* 278 */     buf.writeIntLE(0);
/* 279 */     int damageEffectsOffsetSlot = buf.writerIndex();
/* 280 */     buf.writeIntLE(0);
/* 281 */     int angledDamageOffsetSlot = buf.writerIndex();
/* 282 */     buf.writeIntLE(0);
/* 283 */     int targetedDamageOffsetSlot = buf.writerIndex();
/* 284 */     buf.writeIntLE(0);
/* 285 */     int entityStatsOnHitOffsetSlot = buf.writerIndex();
/* 286 */     buf.writeIntLE(0);
/*     */     
/* 288 */     int varBlockStart = buf.writerIndex();
/* 289 */     if (this.effects != null) {
/* 290 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 291 */       this.effects.serialize(buf);
/*     */     } else {
/* 293 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 295 */     if (this.settings != null)
/* 296 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 297 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 299 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 301 */     if (this.rules != null) {
/* 302 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 303 */       this.rules.serialize(buf);
/*     */     } else {
/* 305 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 307 */     if (this.tags != null) {
/* 308 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 309 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 311 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 313 */     if (this.camera != null) {
/* 314 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 315 */       this.camera.serialize(buf);
/*     */     } else {
/* 317 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 319 */     if (this.damageEffects != null) {
/* 320 */       buf.setIntLE(damageEffectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 321 */       this.damageEffects.serialize(buf);
/*     */     } else {
/* 323 */       buf.setIntLE(damageEffectsOffsetSlot, -1);
/*     */     } 
/* 325 */     if (this.angledDamage != null) {
/* 326 */       buf.setIntLE(angledDamageOffsetSlot, buf.writerIndex() - varBlockStart);
/* 327 */       if (this.angledDamage.length > 4096000) throw ProtocolException.arrayTooLong("AngledDamage", this.angledDamage.length, 4096000);  VarInt.write(buf, this.angledDamage.length); for (AngledDamage item : this.angledDamage) item.serialize(buf); 
/*     */     } else {
/* 329 */       buf.setIntLE(angledDamageOffsetSlot, -1);
/*     */     } 
/* 331 */     if (this.targetedDamage != null)
/* 332 */     { buf.setIntLE(targetedDamageOffsetSlot, buf.writerIndex() - varBlockStart);
/* 333 */       if (this.targetedDamage.size() > 4096000) throw ProtocolException.dictionaryTooLarge("TargetedDamage", this.targetedDamage.size(), 4096000);  VarInt.write(buf, this.targetedDamage.size()); for (Map.Entry<String, TargetedDamage> e : this.targetedDamage.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); ((TargetedDamage)e.getValue()).serialize(buf); }
/*     */        }
/* 335 */     else { buf.setIntLE(targetedDamageOffsetSlot, -1); }
/*     */     
/* 337 */     if (this.entityStatsOnHit != null) {
/* 338 */       buf.setIntLE(entityStatsOnHitOffsetSlot, buf.writerIndex() - varBlockStart);
/* 339 */       if (this.entityStatsOnHit.length > 4096000) throw ProtocolException.arrayTooLong("EntityStatsOnHit", this.entityStatsOnHit.length, 4096000);  VarInt.write(buf, this.entityStatsOnHit.length); for (EntityStatOnHit item : this.entityStatsOnHit) item.serialize(buf); 
/*     */     } else {
/* 341 */       buf.setIntLE(entityStatsOnHitOffsetSlot, -1);
/*     */     } 
/* 343 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 349 */     int size = 60;
/* 350 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 351 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 352 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 353 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 354 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 355 */     if (this.damageEffects != null) size += this.damageEffects.computeSize(); 
/* 356 */     if (this.angledDamage != null) {
/* 357 */       int angledDamageSize = 0;
/* 358 */       for (AngledDamage elem : this.angledDamage) angledDamageSize += elem.computeSize(); 
/* 359 */       size += VarInt.size(this.angledDamage.length) + angledDamageSize;
/*     */     } 
/* 361 */     if (this.targetedDamage != null) {
/* 362 */       int targetedDamageSize = 0;
/* 363 */       for (Map.Entry<String, TargetedDamage> kvp : this.targetedDamage.entrySet()) targetedDamageSize += PacketIO.stringSize(kvp.getKey()) + ((TargetedDamage)kvp.getValue()).computeSize(); 
/* 364 */       size += VarInt.size(this.targetedDamage.size()) + targetedDamageSize;
/*     */     } 
/* 366 */     if (this.entityStatsOnHit != null) {
/* 367 */       int entityStatsOnHitSize = 0;
/* 368 */       for (EntityStatOnHit elem : this.entityStatsOnHit) entityStatsOnHitSize += elem.computeSize(); 
/* 369 */       size += VarInt.size(this.entityStatsOnHit.length) + entityStatsOnHitSize;
/*     */     } 
/*     */     
/* 372 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 376 */     if (buffer.readableBytes() - offset < 60) {
/* 377 */       return ValidationResult.error("Buffer too small: expected at least 60 bytes");
/*     */     }
/*     */     
/* 380 */     byte[] nullBits = PacketIO.readBytes(buffer, offset, 2);
/*     */     
/* 382 */     if ((nullBits[0] & 0x1) != 0) {
/* 383 */       int effectsOffset = buffer.getIntLE(offset + 24);
/* 384 */       if (effectsOffset < 0) {
/* 385 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 387 */       int pos = offset + 60 + effectsOffset;
/* 388 */       if (pos >= buffer.writerIndex()) {
/* 389 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 391 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 392 */       if (!effectsResult.isValid()) {
/* 393 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 395 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 398 */     if ((nullBits[0] & 0x2) != 0) {
/* 399 */       int settingsOffset = buffer.getIntLE(offset + 28);
/* 400 */       if (settingsOffset < 0) {
/* 401 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 403 */       int pos = offset + 60 + settingsOffset;
/* 404 */       if (pos >= buffer.writerIndex()) {
/* 405 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 407 */       int settingsCount = VarInt.peek(buffer, pos);
/* 408 */       if (settingsCount < 0) {
/* 409 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 411 */       if (settingsCount > 4096000) {
/* 412 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 414 */       pos += VarInt.length(buffer, pos);
/* 415 */       for (int i = 0; i < settingsCount; i++) {
/* 416 */         pos++;
/*     */         
/* 418 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 423 */     if ((nullBits[0] & 0x4) != 0) {
/* 424 */       int rulesOffset = buffer.getIntLE(offset + 32);
/* 425 */       if (rulesOffset < 0) {
/* 426 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 428 */       int pos = offset + 60 + rulesOffset;
/* 429 */       if (pos >= buffer.writerIndex()) {
/* 430 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 432 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 433 */       if (!rulesResult.isValid()) {
/* 434 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 436 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 439 */     if ((nullBits[0] & 0x8) != 0) {
/* 440 */       int tagsOffset = buffer.getIntLE(offset + 36);
/* 441 */       if (tagsOffset < 0) {
/* 442 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 444 */       int pos = offset + 60 + tagsOffset;
/* 445 */       if (pos >= buffer.writerIndex()) {
/* 446 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 448 */       int tagsCount = VarInt.peek(buffer, pos);
/* 449 */       if (tagsCount < 0) {
/* 450 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 452 */       if (tagsCount > 4096000) {
/* 453 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 455 */       pos += VarInt.length(buffer, pos);
/* 456 */       pos += tagsCount * 4;
/* 457 */       if (pos > buffer.writerIndex()) {
/* 458 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 462 */     if ((nullBits[0] & 0x10) != 0) {
/* 463 */       int cameraOffset = buffer.getIntLE(offset + 40);
/* 464 */       if (cameraOffset < 0) {
/* 465 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 467 */       int pos = offset + 60 + cameraOffset;
/* 468 */       if (pos >= buffer.writerIndex()) {
/* 469 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 471 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 472 */       if (!cameraResult.isValid()) {
/* 473 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 475 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 478 */     if ((nullBits[0] & 0x20) != 0) {
/* 479 */       int damageEffectsOffset = buffer.getIntLE(offset + 44);
/* 480 */       if (damageEffectsOffset < 0) {
/* 481 */         return ValidationResult.error("Invalid offset for DamageEffects");
/*     */       }
/* 483 */       int pos = offset + 60 + damageEffectsOffset;
/* 484 */       if (pos >= buffer.writerIndex()) {
/* 485 */         return ValidationResult.error("Offset out of bounds for DamageEffects");
/*     */       }
/* 487 */       ValidationResult damageEffectsResult = DamageEffects.validateStructure(buffer, pos);
/* 488 */       if (!damageEffectsResult.isValid()) {
/* 489 */         return ValidationResult.error("Invalid DamageEffects: " + damageEffectsResult.error());
/*     */       }
/* 491 */       pos += DamageEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 494 */     if ((nullBits[0] & 0x40) != 0) {
/* 495 */       int angledDamageOffset = buffer.getIntLE(offset + 48);
/* 496 */       if (angledDamageOffset < 0) {
/* 497 */         return ValidationResult.error("Invalid offset for AngledDamage");
/*     */       }
/* 499 */       int pos = offset + 60 + angledDamageOffset;
/* 500 */       if (pos >= buffer.writerIndex()) {
/* 501 */         return ValidationResult.error("Offset out of bounds for AngledDamage");
/*     */       }
/* 503 */       int angledDamageCount = VarInt.peek(buffer, pos);
/* 504 */       if (angledDamageCount < 0) {
/* 505 */         return ValidationResult.error("Invalid array count for AngledDamage");
/*     */       }
/* 507 */       if (angledDamageCount > 4096000) {
/* 508 */         return ValidationResult.error("AngledDamage exceeds max length 4096000");
/*     */       }
/* 510 */       pos += VarInt.length(buffer, pos);
/* 511 */       for (int i = 0; i < angledDamageCount; i++) {
/* 512 */         ValidationResult structResult = AngledDamage.validateStructure(buffer, pos);
/* 513 */         if (!structResult.isValid()) {
/* 514 */           return ValidationResult.error("Invalid AngledDamage in AngledDamage[" + i + "]: " + structResult.error());
/*     */         }
/* 516 */         pos += AngledDamage.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 520 */     if ((nullBits[0] & 0x80) != 0) {
/* 521 */       int targetedDamageOffset = buffer.getIntLE(offset + 52);
/* 522 */       if (targetedDamageOffset < 0) {
/* 523 */         return ValidationResult.error("Invalid offset for TargetedDamage");
/*     */       }
/* 525 */       int pos = offset + 60 + targetedDamageOffset;
/* 526 */       if (pos >= buffer.writerIndex()) {
/* 527 */         return ValidationResult.error("Offset out of bounds for TargetedDamage");
/*     */       }
/* 529 */       int targetedDamageCount = VarInt.peek(buffer, pos);
/* 530 */       if (targetedDamageCount < 0) {
/* 531 */         return ValidationResult.error("Invalid dictionary count for TargetedDamage");
/*     */       }
/* 533 */       if (targetedDamageCount > 4096000) {
/* 534 */         return ValidationResult.error("TargetedDamage exceeds max length 4096000");
/*     */       }
/* 536 */       pos += VarInt.length(buffer, pos);
/* 537 */       for (int i = 0; i < targetedDamageCount; i++) {
/* 538 */         int keyLen = VarInt.peek(buffer, pos);
/* 539 */         if (keyLen < 0) {
/* 540 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 542 */         if (keyLen > 4096000) {
/* 543 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 545 */         pos += VarInt.length(buffer, pos);
/* 546 */         pos += keyLen;
/* 547 */         if (pos > buffer.writerIndex()) {
/* 548 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 550 */         pos += TargetedDamage.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 555 */     if ((nullBits[1] & 0x1) != 0) {
/* 556 */       int entityStatsOnHitOffset = buffer.getIntLE(offset + 56);
/* 557 */       if (entityStatsOnHitOffset < 0) {
/* 558 */         return ValidationResult.error("Invalid offset for EntityStatsOnHit");
/*     */       }
/* 560 */       int pos = offset + 60 + entityStatsOnHitOffset;
/* 561 */       if (pos >= buffer.writerIndex()) {
/* 562 */         return ValidationResult.error("Offset out of bounds for EntityStatsOnHit");
/*     */       }
/* 564 */       int entityStatsOnHitCount = VarInt.peek(buffer, pos);
/* 565 */       if (entityStatsOnHitCount < 0) {
/* 566 */         return ValidationResult.error("Invalid array count for EntityStatsOnHit");
/*     */       }
/* 568 */       if (entityStatsOnHitCount > 4096000) {
/* 569 */         return ValidationResult.error("EntityStatsOnHit exceeds max length 4096000");
/*     */       }
/* 571 */       pos += VarInt.length(buffer, pos);
/* 572 */       for (int i = 0; i < entityStatsOnHitCount; i++) {
/* 573 */         ValidationResult structResult = EntityStatOnHit.validateStructure(buffer, pos);
/* 574 */         if (!structResult.isValid()) {
/* 575 */           return ValidationResult.error("Invalid EntityStatOnHit in EntityStatsOnHit[" + i + "]: " + structResult.error());
/*     */         }
/* 577 */         pos += EntityStatOnHit.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 580 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public DamageEntityInteraction clone() {
/* 584 */     DamageEntityInteraction copy = new DamageEntityInteraction();
/* 585 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 586 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 587 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 588 */     copy.runTime = this.runTime;
/* 589 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 590 */     if (this.settings != null) {
/* 591 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 592 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 593 */       copy.settings = m;
/*     */     } 
/* 595 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 596 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 597 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 598 */     copy.next = this.next;
/* 599 */     copy.failed = this.failed;
/* 600 */     copy.blocked = this.blocked;
/* 601 */     copy.damageEffects = (this.damageEffects != null) ? this.damageEffects.clone() : null;
/* 602 */     copy.angledDamage = (this.angledDamage != null) ? (AngledDamage[])Arrays.<AngledDamage>stream(this.angledDamage).map(e -> e.clone()).toArray(x$0 -> new AngledDamage[x$0]) : null;
/* 603 */     if (this.targetedDamage != null) {
/* 604 */       Map<String, TargetedDamage> m = new HashMap<>();
/* 605 */       for (Map.Entry<String, TargetedDamage> e : this.targetedDamage.entrySet()) m.put(e.getKey(), ((TargetedDamage)e.getValue()).clone()); 
/* 606 */       copy.targetedDamage = m;
/*     */     } 
/* 608 */     copy.entityStatsOnHit = (this.entityStatsOnHit != null) ? (EntityStatOnHit[])Arrays.<EntityStatOnHit>stream(this.entityStatsOnHit).map(e -> e.clone()).toArray(x$0 -> new EntityStatOnHit[x$0]) : null;
/* 609 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     DamageEntityInteraction other;
/* 615 */     if (this == obj) return true; 
/* 616 */     if (obj instanceof DamageEntityInteraction) { other = (DamageEntityInteraction)obj; } else { return false; }
/* 617 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && this.blocked == other.blocked && Objects.equals(this.damageEffects, other.damageEffects) && Arrays.equals((Object[])this.angledDamage, (Object[])other.angledDamage) && Objects.equals(this.targetedDamage, other.targetedDamage) && Arrays.equals((Object[])this.entityStatsOnHit, (Object[])other.entityStatsOnHit));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 622 */     int result = 1;
/* 623 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 624 */     result = 31 * result + Objects.hashCode(this.effects);
/* 625 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 626 */     result = 31 * result + Float.hashCode(this.runTime);
/* 627 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 628 */     result = 31 * result + Objects.hashCode(this.settings);
/* 629 */     result = 31 * result + Objects.hashCode(this.rules);
/* 630 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 631 */     result = 31 * result + Objects.hashCode(this.camera);
/* 632 */     result = 31 * result + Integer.hashCode(this.next);
/* 633 */     result = 31 * result + Integer.hashCode(this.failed);
/* 634 */     result = 31 * result + Integer.hashCode(this.blocked);
/* 635 */     result = 31 * result + Objects.hashCode(this.damageEffects);
/* 636 */     result = 31 * result + Arrays.hashCode((Object[])this.angledDamage);
/* 637 */     result = 31 * result + Objects.hashCode(this.targetedDamage);
/* 638 */     result = 31 * result + Arrays.hashCode((Object[])this.entityStatsOnHit);
/* 639 */     return result;
/*     */   }
/*     */   
/*     */   public DamageEntityInteraction() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\DamageEntityInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */