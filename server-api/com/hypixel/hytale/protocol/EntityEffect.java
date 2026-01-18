/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
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
/*     */ public class EntityEffect
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 25;
/*     */   public static final int VARIABLE_FIELD_COUNT = 6;
/*     */   public static final int VARIABLE_BLOCK_START = 49;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public String id;
/*     */   @Nullable
/*     */   public String name;
/*     */   @Nonnull
/*  30 */   public OverlapBehavior overlapBehavior = OverlapBehavior.Extend; @Nullable public ApplicationEffects applicationEffects; public int worldRemovalSoundEventIndex; public int localRemovalSoundEventIndex; @Nullable public ModelOverride modelOverride; public float duration; public boolean infinite; public boolean debuff; @Nullable
/*     */   public String statusEffectIcon; public double damageCalculatorCooldown; @Nullable
/*     */   public Map<Integer, Float> statModifiers; @Nonnull
/*  33 */   public ValueType valueType = ValueType.Percent;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityEffect(@Nullable String id, @Nullable String name, @Nullable ApplicationEffects applicationEffects, int worldRemovalSoundEventIndex, int localRemovalSoundEventIndex, @Nullable ModelOverride modelOverride, float duration, boolean infinite, boolean debuff, @Nullable String statusEffectIcon, @Nonnull OverlapBehavior overlapBehavior, double damageCalculatorCooldown, @Nullable Map<Integer, Float> statModifiers, @Nonnull ValueType valueType) {
/*  39 */     this.id = id;
/*  40 */     this.name = name;
/*  41 */     this.applicationEffects = applicationEffects;
/*  42 */     this.worldRemovalSoundEventIndex = worldRemovalSoundEventIndex;
/*  43 */     this.localRemovalSoundEventIndex = localRemovalSoundEventIndex;
/*  44 */     this.modelOverride = modelOverride;
/*  45 */     this.duration = duration;
/*  46 */     this.infinite = infinite;
/*  47 */     this.debuff = debuff;
/*  48 */     this.statusEffectIcon = statusEffectIcon;
/*  49 */     this.overlapBehavior = overlapBehavior;
/*  50 */     this.damageCalculatorCooldown = damageCalculatorCooldown;
/*  51 */     this.statModifiers = statModifiers;
/*  52 */     this.valueType = valueType;
/*     */   }
/*     */   
/*     */   public EntityEffect(@Nonnull EntityEffect other) {
/*  56 */     this.id = other.id;
/*  57 */     this.name = other.name;
/*  58 */     this.applicationEffects = other.applicationEffects;
/*  59 */     this.worldRemovalSoundEventIndex = other.worldRemovalSoundEventIndex;
/*  60 */     this.localRemovalSoundEventIndex = other.localRemovalSoundEventIndex;
/*  61 */     this.modelOverride = other.modelOverride;
/*  62 */     this.duration = other.duration;
/*  63 */     this.infinite = other.infinite;
/*  64 */     this.debuff = other.debuff;
/*  65 */     this.statusEffectIcon = other.statusEffectIcon;
/*  66 */     this.overlapBehavior = other.overlapBehavior;
/*  67 */     this.damageCalculatorCooldown = other.damageCalculatorCooldown;
/*  68 */     this.statModifiers = other.statModifiers;
/*  69 */     this.valueType = other.valueType;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static EntityEffect deserialize(@Nonnull ByteBuf buf, int offset) {
/*  74 */     EntityEffect obj = new EntityEffect();
/*  75 */     byte nullBits = buf.getByte(offset);
/*  76 */     obj.worldRemovalSoundEventIndex = buf.getIntLE(offset + 1);
/*  77 */     obj.localRemovalSoundEventIndex = buf.getIntLE(offset + 5);
/*  78 */     obj.duration = buf.getFloatLE(offset + 9);
/*  79 */     obj.infinite = (buf.getByte(offset + 13) != 0);
/*  80 */     obj.debuff = (buf.getByte(offset + 14) != 0);
/*  81 */     obj.overlapBehavior = OverlapBehavior.fromValue(buf.getByte(offset + 15));
/*  82 */     obj.damageCalculatorCooldown = buf.getDoubleLE(offset + 16);
/*  83 */     obj.valueType = ValueType.fromValue(buf.getByte(offset + 24));
/*     */     
/*  85 */     if ((nullBits & 0x1) != 0) {
/*  86 */       int varPos0 = offset + 49 + buf.getIntLE(offset + 25);
/*  87 */       int idLen = VarInt.peek(buf, varPos0);
/*  88 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  89 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  90 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  92 */     if ((nullBits & 0x2) != 0) {
/*  93 */       int varPos1 = offset + 49 + buf.getIntLE(offset + 29);
/*  94 */       int nameLen = VarInt.peek(buf, varPos1);
/*  95 */       if (nameLen < 0) throw ProtocolException.negativeLength("Name", nameLen); 
/*  96 */       if (nameLen > 4096000) throw ProtocolException.stringTooLong("Name", nameLen, 4096000); 
/*  97 */       obj.name = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  99 */     if ((nullBits & 0x4) != 0) {
/* 100 */       int varPos2 = offset + 49 + buf.getIntLE(offset + 33);
/* 101 */       obj.applicationEffects = ApplicationEffects.deserialize(buf, varPos2);
/*     */     } 
/* 103 */     if ((nullBits & 0x8) != 0) {
/* 104 */       int varPos3 = offset + 49 + buf.getIntLE(offset + 37);
/* 105 */       obj.modelOverride = ModelOverride.deserialize(buf, varPos3);
/*     */     } 
/* 107 */     if ((nullBits & 0x10) != 0) {
/* 108 */       int varPos4 = offset + 49 + buf.getIntLE(offset + 41);
/* 109 */       int statusEffectIconLen = VarInt.peek(buf, varPos4);
/* 110 */       if (statusEffectIconLen < 0) throw ProtocolException.negativeLength("StatusEffectIcon", statusEffectIconLen); 
/* 111 */       if (statusEffectIconLen > 4096000) throw ProtocolException.stringTooLong("StatusEffectIcon", statusEffectIconLen, 4096000); 
/* 112 */       obj.statusEffectIcon = PacketIO.readVarString(buf, varPos4, PacketIO.UTF8);
/*     */     } 
/* 114 */     if ((nullBits & 0x20) != 0) {
/* 115 */       int varPos5 = offset + 49 + buf.getIntLE(offset + 45);
/* 116 */       int statModifiersCount = VarInt.peek(buf, varPos5);
/* 117 */       if (statModifiersCount < 0) throw ProtocolException.negativeLength("StatModifiers", statModifiersCount); 
/* 118 */       if (statModifiersCount > 4096000) throw ProtocolException.dictionaryTooLarge("StatModifiers", statModifiersCount, 4096000); 
/* 119 */       int varIntLen = VarInt.length(buf, varPos5);
/* 120 */       obj.statModifiers = new HashMap<>(statModifiersCount);
/* 121 */       int dictPos = varPos5 + varIntLen;
/* 122 */       for (int i = 0; i < statModifiersCount; i++) {
/* 123 */         int key = buf.getIntLE(dictPos); dictPos += 4;
/* 124 */         float val = buf.getFloatLE(dictPos); dictPos += 4;
/* 125 */         if (obj.statModifiers.put(Integer.valueOf(key), Float.valueOf(val)) != null) {
/* 126 */           throw ProtocolException.duplicateKey("statModifiers", Integer.valueOf(key));
/*     */         }
/*     */       } 
/*     */     } 
/* 130 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 134 */     byte nullBits = buf.getByte(offset);
/* 135 */     int maxEnd = 49;
/* 136 */     if ((nullBits & 0x1) != 0) {
/* 137 */       int fieldOffset0 = buf.getIntLE(offset + 25);
/* 138 */       int pos0 = offset + 49 + fieldOffset0;
/* 139 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 140 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 142 */     if ((nullBits & 0x2) != 0) {
/* 143 */       int fieldOffset1 = buf.getIntLE(offset + 29);
/* 144 */       int pos1 = offset + 49 + fieldOffset1;
/* 145 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/* 146 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 148 */     if ((nullBits & 0x4) != 0) {
/* 149 */       int fieldOffset2 = buf.getIntLE(offset + 33);
/* 150 */       int pos2 = offset + 49 + fieldOffset2;
/* 151 */       pos2 += ApplicationEffects.computeBytesConsumed(buf, pos2);
/* 152 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 154 */     if ((nullBits & 0x8) != 0) {
/* 155 */       int fieldOffset3 = buf.getIntLE(offset + 37);
/* 156 */       int pos3 = offset + 49 + fieldOffset3;
/* 157 */       pos3 += ModelOverride.computeBytesConsumed(buf, pos3);
/* 158 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 160 */     if ((nullBits & 0x10) != 0) {
/* 161 */       int fieldOffset4 = buf.getIntLE(offset + 41);
/* 162 */       int pos4 = offset + 49 + fieldOffset4;
/* 163 */       int sl = VarInt.peek(buf, pos4); pos4 += VarInt.length(buf, pos4) + sl;
/* 164 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 166 */     if ((nullBits & 0x20) != 0) {
/* 167 */       int fieldOffset5 = buf.getIntLE(offset + 45);
/* 168 */       int pos5 = offset + 49 + fieldOffset5;
/* 169 */       int dictLen = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5);
/* 170 */       for (int i = 0; i < dictLen; ) { pos5 += 4; pos5 += 4; i++; }
/* 171 */        if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 173 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 178 */     int startPos = buf.writerIndex();
/* 179 */     byte nullBits = 0;
/* 180 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 181 */     if (this.name != null) nullBits = (byte)(nullBits | 0x2); 
/* 182 */     if (this.applicationEffects != null) nullBits = (byte)(nullBits | 0x4); 
/* 183 */     if (this.modelOverride != null) nullBits = (byte)(nullBits | 0x8); 
/* 184 */     if (this.statusEffectIcon != null) nullBits = (byte)(nullBits | 0x10); 
/* 185 */     if (this.statModifiers != null) nullBits = (byte)(nullBits | 0x20); 
/* 186 */     buf.writeByte(nullBits);
/*     */     
/* 188 */     buf.writeIntLE(this.worldRemovalSoundEventIndex);
/* 189 */     buf.writeIntLE(this.localRemovalSoundEventIndex);
/* 190 */     buf.writeFloatLE(this.duration);
/* 191 */     buf.writeByte(this.infinite ? 1 : 0);
/* 192 */     buf.writeByte(this.debuff ? 1 : 0);
/* 193 */     buf.writeByte(this.overlapBehavior.getValue());
/* 194 */     buf.writeDoubleLE(this.damageCalculatorCooldown);
/* 195 */     buf.writeByte(this.valueType.getValue());
/*     */     
/* 197 */     int idOffsetSlot = buf.writerIndex();
/* 198 */     buf.writeIntLE(0);
/* 199 */     int nameOffsetSlot = buf.writerIndex();
/* 200 */     buf.writeIntLE(0);
/* 201 */     int applicationEffectsOffsetSlot = buf.writerIndex();
/* 202 */     buf.writeIntLE(0);
/* 203 */     int modelOverrideOffsetSlot = buf.writerIndex();
/* 204 */     buf.writeIntLE(0);
/* 205 */     int statusEffectIconOffsetSlot = buf.writerIndex();
/* 206 */     buf.writeIntLE(0);
/* 207 */     int statModifiersOffsetSlot = buf.writerIndex();
/* 208 */     buf.writeIntLE(0);
/*     */     
/* 210 */     int varBlockStart = buf.writerIndex();
/* 211 */     if (this.id != null) {
/* 212 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 213 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 215 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 217 */     if (this.name != null) {
/* 218 */       buf.setIntLE(nameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 219 */       PacketIO.writeVarString(buf, this.name, 4096000);
/*     */     } else {
/* 221 */       buf.setIntLE(nameOffsetSlot, -1);
/*     */     } 
/* 223 */     if (this.applicationEffects != null) {
/* 224 */       buf.setIntLE(applicationEffectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 225 */       this.applicationEffects.serialize(buf);
/*     */     } else {
/* 227 */       buf.setIntLE(applicationEffectsOffsetSlot, -1);
/*     */     } 
/* 229 */     if (this.modelOverride != null) {
/* 230 */       buf.setIntLE(modelOverrideOffsetSlot, buf.writerIndex() - varBlockStart);
/* 231 */       this.modelOverride.serialize(buf);
/*     */     } else {
/* 233 */       buf.setIntLE(modelOverrideOffsetSlot, -1);
/*     */     } 
/* 235 */     if (this.statusEffectIcon != null) {
/* 236 */       buf.setIntLE(statusEffectIconOffsetSlot, buf.writerIndex() - varBlockStart);
/* 237 */       PacketIO.writeVarString(buf, this.statusEffectIcon, 4096000);
/*     */     } else {
/* 239 */       buf.setIntLE(statusEffectIconOffsetSlot, -1);
/*     */     } 
/* 241 */     if (this.statModifiers != null)
/* 242 */     { buf.setIntLE(statModifiersOffsetSlot, buf.writerIndex() - varBlockStart);
/* 243 */       if (this.statModifiers.size() > 4096000) throw ProtocolException.dictionaryTooLarge("StatModifiers", this.statModifiers.size(), 4096000);  VarInt.write(buf, this.statModifiers.size()); for (Map.Entry<Integer, Float> e : this.statModifiers.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); buf.writeFloatLE(((Float)e.getValue()).floatValue()); }
/*     */        }
/* 245 */     else { buf.setIntLE(statModifiersOffsetSlot, -1); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 251 */     int size = 49;
/* 252 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 253 */     if (this.name != null) size += PacketIO.stringSize(this.name); 
/* 254 */     if (this.applicationEffects != null) size += this.applicationEffects.computeSize(); 
/* 255 */     if (this.modelOverride != null) size += this.modelOverride.computeSize(); 
/* 256 */     if (this.statusEffectIcon != null) size += PacketIO.stringSize(this.statusEffectIcon); 
/* 257 */     if (this.statModifiers != null) size += VarInt.size(this.statModifiers.size()) + this.statModifiers.size() * 8;
/*     */     
/* 259 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 263 */     if (buffer.readableBytes() - offset < 49) {
/* 264 */       return ValidationResult.error("Buffer too small: expected at least 49 bytes");
/*     */     }
/*     */     
/* 267 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 270 */     if ((nullBits & 0x1) != 0) {
/* 271 */       int idOffset = buffer.getIntLE(offset + 25);
/* 272 */       if (idOffset < 0) {
/* 273 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 275 */       int pos = offset + 49 + idOffset;
/* 276 */       if (pos >= buffer.writerIndex()) {
/* 277 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 279 */       int idLen = VarInt.peek(buffer, pos);
/* 280 */       if (idLen < 0) {
/* 281 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 283 */       if (idLen > 4096000) {
/* 284 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 286 */       pos += VarInt.length(buffer, pos);
/* 287 */       pos += idLen;
/* 288 */       if (pos > buffer.writerIndex()) {
/* 289 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 293 */     if ((nullBits & 0x2) != 0) {
/* 294 */       int nameOffset = buffer.getIntLE(offset + 29);
/* 295 */       if (nameOffset < 0) {
/* 296 */         return ValidationResult.error("Invalid offset for Name");
/*     */       }
/* 298 */       int pos = offset + 49 + nameOffset;
/* 299 */       if (pos >= buffer.writerIndex()) {
/* 300 */         return ValidationResult.error("Offset out of bounds for Name");
/*     */       }
/* 302 */       int nameLen = VarInt.peek(buffer, pos);
/* 303 */       if (nameLen < 0) {
/* 304 */         return ValidationResult.error("Invalid string length for Name");
/*     */       }
/* 306 */       if (nameLen > 4096000) {
/* 307 */         return ValidationResult.error("Name exceeds max length 4096000");
/*     */       }
/* 309 */       pos += VarInt.length(buffer, pos);
/* 310 */       pos += nameLen;
/* 311 */       if (pos > buffer.writerIndex()) {
/* 312 */         return ValidationResult.error("Buffer overflow reading Name");
/*     */       }
/*     */     } 
/*     */     
/* 316 */     if ((nullBits & 0x4) != 0) {
/* 317 */       int applicationEffectsOffset = buffer.getIntLE(offset + 33);
/* 318 */       if (applicationEffectsOffset < 0) {
/* 319 */         return ValidationResult.error("Invalid offset for ApplicationEffects");
/*     */       }
/* 321 */       int pos = offset + 49 + applicationEffectsOffset;
/* 322 */       if (pos >= buffer.writerIndex()) {
/* 323 */         return ValidationResult.error("Offset out of bounds for ApplicationEffects");
/*     */       }
/* 325 */       ValidationResult applicationEffectsResult = ApplicationEffects.validateStructure(buffer, pos);
/* 326 */       if (!applicationEffectsResult.isValid()) {
/* 327 */         return ValidationResult.error("Invalid ApplicationEffects: " + applicationEffectsResult.error());
/*     */       }
/* 329 */       pos += ApplicationEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 332 */     if ((nullBits & 0x8) != 0) {
/* 333 */       int modelOverrideOffset = buffer.getIntLE(offset + 37);
/* 334 */       if (modelOverrideOffset < 0) {
/* 335 */         return ValidationResult.error("Invalid offset for ModelOverride");
/*     */       }
/* 337 */       int pos = offset + 49 + modelOverrideOffset;
/* 338 */       if (pos >= buffer.writerIndex()) {
/* 339 */         return ValidationResult.error("Offset out of bounds for ModelOverride");
/*     */       }
/* 341 */       ValidationResult modelOverrideResult = ModelOverride.validateStructure(buffer, pos);
/* 342 */       if (!modelOverrideResult.isValid()) {
/* 343 */         return ValidationResult.error("Invalid ModelOverride: " + modelOverrideResult.error());
/*     */       }
/* 345 */       pos += ModelOverride.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 348 */     if ((nullBits & 0x10) != 0) {
/* 349 */       int statusEffectIconOffset = buffer.getIntLE(offset + 41);
/* 350 */       if (statusEffectIconOffset < 0) {
/* 351 */         return ValidationResult.error("Invalid offset for StatusEffectIcon");
/*     */       }
/* 353 */       int pos = offset + 49 + statusEffectIconOffset;
/* 354 */       if (pos >= buffer.writerIndex()) {
/* 355 */         return ValidationResult.error("Offset out of bounds for StatusEffectIcon");
/*     */       }
/* 357 */       int statusEffectIconLen = VarInt.peek(buffer, pos);
/* 358 */       if (statusEffectIconLen < 0) {
/* 359 */         return ValidationResult.error("Invalid string length for StatusEffectIcon");
/*     */       }
/* 361 */       if (statusEffectIconLen > 4096000) {
/* 362 */         return ValidationResult.error("StatusEffectIcon exceeds max length 4096000");
/*     */       }
/* 364 */       pos += VarInt.length(buffer, pos);
/* 365 */       pos += statusEffectIconLen;
/* 366 */       if (pos > buffer.writerIndex()) {
/* 367 */         return ValidationResult.error("Buffer overflow reading StatusEffectIcon");
/*     */       }
/*     */     } 
/*     */     
/* 371 */     if ((nullBits & 0x20) != 0) {
/* 372 */       int statModifiersOffset = buffer.getIntLE(offset + 45);
/* 373 */       if (statModifiersOffset < 0) {
/* 374 */         return ValidationResult.error("Invalid offset for StatModifiers");
/*     */       }
/* 376 */       int pos = offset + 49 + statModifiersOffset;
/* 377 */       if (pos >= buffer.writerIndex()) {
/* 378 */         return ValidationResult.error("Offset out of bounds for StatModifiers");
/*     */       }
/* 380 */       int statModifiersCount = VarInt.peek(buffer, pos);
/* 381 */       if (statModifiersCount < 0) {
/* 382 */         return ValidationResult.error("Invalid dictionary count for StatModifiers");
/*     */       }
/* 384 */       if (statModifiersCount > 4096000) {
/* 385 */         return ValidationResult.error("StatModifiers exceeds max length 4096000");
/*     */       }
/* 387 */       pos += VarInt.length(buffer, pos);
/* 388 */       for (int i = 0; i < statModifiersCount; i++) {
/* 389 */         pos += 4;
/* 390 */         if (pos > buffer.writerIndex()) {
/* 391 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 393 */         pos += 4;
/* 394 */         if (pos > buffer.writerIndex()) {
/* 395 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/* 399 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public EntityEffect clone() {
/* 403 */     EntityEffect copy = new EntityEffect();
/* 404 */     copy.id = this.id;
/* 405 */     copy.name = this.name;
/* 406 */     copy.applicationEffects = (this.applicationEffects != null) ? this.applicationEffects.clone() : null;
/* 407 */     copy.worldRemovalSoundEventIndex = this.worldRemovalSoundEventIndex;
/* 408 */     copy.localRemovalSoundEventIndex = this.localRemovalSoundEventIndex;
/* 409 */     copy.modelOverride = (this.modelOverride != null) ? this.modelOverride.clone() : null;
/* 410 */     copy.duration = this.duration;
/* 411 */     copy.infinite = this.infinite;
/* 412 */     copy.debuff = this.debuff;
/* 413 */     copy.statusEffectIcon = this.statusEffectIcon;
/* 414 */     copy.overlapBehavior = this.overlapBehavior;
/* 415 */     copy.damageCalculatorCooldown = this.damageCalculatorCooldown;
/* 416 */     copy.statModifiers = (this.statModifiers != null) ? new HashMap<>(this.statModifiers) : null;
/* 417 */     copy.valueType = this.valueType;
/* 418 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     EntityEffect other;
/* 424 */     if (this == obj) return true; 
/* 425 */     if (obj instanceof EntityEffect) { other = (EntityEffect)obj; } else { return false; }
/* 426 */      return (Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name) && Objects.equals(this.applicationEffects, other.applicationEffects) && this.worldRemovalSoundEventIndex == other.worldRemovalSoundEventIndex && this.localRemovalSoundEventIndex == other.localRemovalSoundEventIndex && Objects.equals(this.modelOverride, other.modelOverride) && this.duration == other.duration && this.infinite == other.infinite && this.debuff == other.debuff && Objects.equals(this.statusEffectIcon, other.statusEffectIcon) && Objects.equals(this.overlapBehavior, other.overlapBehavior) && this.damageCalculatorCooldown == other.damageCalculatorCooldown && Objects.equals(this.statModifiers, other.statModifiers) && Objects.equals(this.valueType, other.valueType));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 431 */     return Objects.hash(new Object[] { this.id, this.name, this.applicationEffects, Integer.valueOf(this.worldRemovalSoundEventIndex), Integer.valueOf(this.localRemovalSoundEventIndex), this.modelOverride, Float.valueOf(this.duration), Boolean.valueOf(this.infinite), Boolean.valueOf(this.debuff), this.statusEffectIcon, this.overlapBehavior, Double.valueOf(this.damageCalculatorCooldown), this.statModifiers, this.valueType });
/*     */   }
/*     */   
/*     */   public EntityEffect() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\EntityEffect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */