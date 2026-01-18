/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AmbienceFX
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 18;
/*     */   public static final int VARIABLE_FIELD_COUNT = 6;
/*     */   public static final int VARIABLE_BLOCK_START = 42;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public String id;
/*     */   @Nullable
/*     */   public AmbienceFXConditions conditions;
/*     */   
/*     */   public AmbienceFX(@Nullable String id, @Nullable AmbienceFXConditions conditions, @Nullable AmbienceFXSound[] sounds, @Nullable AmbienceFXMusic music, @Nullable AmbienceFXAmbientBed ambientBed, @Nullable AmbienceFXSoundEffect soundEffect, int priority, @Nullable int[] blockedAmbienceFxIndices, int audioCategoryIndex) {
/*  34 */     this.id = id;
/*  35 */     this.conditions = conditions;
/*  36 */     this.sounds = sounds;
/*  37 */     this.music = music;
/*  38 */     this.ambientBed = ambientBed;
/*  39 */     this.soundEffect = soundEffect;
/*  40 */     this.priority = priority;
/*  41 */     this.blockedAmbienceFxIndices = blockedAmbienceFxIndices;
/*  42 */     this.audioCategoryIndex = audioCategoryIndex; } @Nullable public AmbienceFXSound[] sounds; @Nullable
/*     */   public AmbienceFXMusic music; @Nullable
/*     */   public AmbienceFXAmbientBed ambientBed; @Nullable
/*     */   public AmbienceFXSoundEffect soundEffect; public int priority; @Nullable
/*  46 */   public int[] blockedAmbienceFxIndices; public int audioCategoryIndex; public AmbienceFX() {} public AmbienceFX(@Nonnull AmbienceFX other) { this.id = other.id;
/*  47 */     this.conditions = other.conditions;
/*  48 */     this.sounds = other.sounds;
/*  49 */     this.music = other.music;
/*  50 */     this.ambientBed = other.ambientBed;
/*  51 */     this.soundEffect = other.soundEffect;
/*  52 */     this.priority = other.priority;
/*  53 */     this.blockedAmbienceFxIndices = other.blockedAmbienceFxIndices;
/*  54 */     this.audioCategoryIndex = other.audioCategoryIndex; }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static AmbienceFX deserialize(@Nonnull ByteBuf buf, int offset) {
/*  59 */     AmbienceFX obj = new AmbienceFX();
/*  60 */     byte nullBits = buf.getByte(offset);
/*  61 */     if ((nullBits & 0x20) != 0) obj.soundEffect = AmbienceFXSoundEffect.deserialize(buf, offset + 1); 
/*  62 */     obj.priority = buf.getIntLE(offset + 10);
/*  63 */     obj.audioCategoryIndex = buf.getIntLE(offset + 14);
/*     */     
/*  65 */     if ((nullBits & 0x1) != 0) {
/*  66 */       int varPos0 = offset + 42 + buf.getIntLE(offset + 18);
/*  67 */       int idLen = VarInt.peek(buf, varPos0);
/*  68 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  69 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  70 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  72 */     if ((nullBits & 0x2) != 0) {
/*  73 */       int varPos1 = offset + 42 + buf.getIntLE(offset + 22);
/*  74 */       obj.conditions = AmbienceFXConditions.deserialize(buf, varPos1);
/*     */     } 
/*  76 */     if ((nullBits & 0x4) != 0) {
/*  77 */       int varPos2 = offset + 42 + buf.getIntLE(offset + 26);
/*  78 */       int soundsCount = VarInt.peek(buf, varPos2);
/*  79 */       if (soundsCount < 0) throw ProtocolException.negativeLength("Sounds", soundsCount); 
/*  80 */       if (soundsCount > 4096000) throw ProtocolException.arrayTooLong("Sounds", soundsCount, 4096000); 
/*  81 */       int varIntLen = VarInt.length(buf, varPos2);
/*  82 */       if ((varPos2 + varIntLen) + soundsCount * 27L > buf.readableBytes())
/*  83 */         throw ProtocolException.bufferTooSmall("Sounds", varPos2 + varIntLen + soundsCount * 27, buf.readableBytes()); 
/*  84 */       obj.sounds = new AmbienceFXSound[soundsCount];
/*  85 */       int elemPos = varPos2 + varIntLen;
/*  86 */       for (int i = 0; i < soundsCount; i++) {
/*  87 */         obj.sounds[i] = AmbienceFXSound.deserialize(buf, elemPos);
/*  88 */         elemPos += AmbienceFXSound.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*  91 */     if ((nullBits & 0x8) != 0) {
/*  92 */       int varPos3 = offset + 42 + buf.getIntLE(offset + 30);
/*  93 */       obj.music = AmbienceFXMusic.deserialize(buf, varPos3);
/*     */     } 
/*  95 */     if ((nullBits & 0x10) != 0) {
/*  96 */       int varPos4 = offset + 42 + buf.getIntLE(offset + 34);
/*  97 */       obj.ambientBed = AmbienceFXAmbientBed.deserialize(buf, varPos4);
/*     */     } 
/*  99 */     if ((nullBits & 0x40) != 0) {
/* 100 */       int varPos5 = offset + 42 + buf.getIntLE(offset + 38);
/* 101 */       int blockedAmbienceFxIndicesCount = VarInt.peek(buf, varPos5);
/* 102 */       if (blockedAmbienceFxIndicesCount < 0) throw ProtocolException.negativeLength("BlockedAmbienceFxIndices", blockedAmbienceFxIndicesCount); 
/* 103 */       if (blockedAmbienceFxIndicesCount > 4096000) throw ProtocolException.arrayTooLong("BlockedAmbienceFxIndices", blockedAmbienceFxIndicesCount, 4096000); 
/* 104 */       int varIntLen = VarInt.length(buf, varPos5);
/* 105 */       if ((varPos5 + varIntLen) + blockedAmbienceFxIndicesCount * 4L > buf.readableBytes())
/* 106 */         throw ProtocolException.bufferTooSmall("BlockedAmbienceFxIndices", varPos5 + varIntLen + blockedAmbienceFxIndicesCount * 4, buf.readableBytes()); 
/* 107 */       obj.blockedAmbienceFxIndices = new int[blockedAmbienceFxIndicesCount];
/* 108 */       for (int i = 0; i < blockedAmbienceFxIndicesCount; i++) {
/* 109 */         obj.blockedAmbienceFxIndices[i] = buf.getIntLE(varPos5 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/*     */     
/* 113 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 117 */     byte nullBits = buf.getByte(offset);
/* 118 */     int maxEnd = 42;
/* 119 */     if ((nullBits & 0x1) != 0) {
/* 120 */       int fieldOffset0 = buf.getIntLE(offset + 18);
/* 121 */       int pos0 = offset + 42 + fieldOffset0;
/* 122 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 123 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 125 */     if ((nullBits & 0x2) != 0) {
/* 126 */       int fieldOffset1 = buf.getIntLE(offset + 22);
/* 127 */       int pos1 = offset + 42 + fieldOffset1;
/* 128 */       pos1 += AmbienceFXConditions.computeBytesConsumed(buf, pos1);
/* 129 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 131 */     if ((nullBits & 0x4) != 0) {
/* 132 */       int fieldOffset2 = buf.getIntLE(offset + 26);
/* 133 */       int pos2 = offset + 42 + fieldOffset2;
/* 134 */       int arrLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2);
/* 135 */       for (int i = 0; i < arrLen; ) { pos2 += AmbienceFXSound.computeBytesConsumed(buf, pos2); i++; }
/* 136 */        if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 138 */     if ((nullBits & 0x8) != 0) {
/* 139 */       int fieldOffset3 = buf.getIntLE(offset + 30);
/* 140 */       int pos3 = offset + 42 + fieldOffset3;
/* 141 */       pos3 += AmbienceFXMusic.computeBytesConsumed(buf, pos3);
/* 142 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 144 */     if ((nullBits & 0x10) != 0) {
/* 145 */       int fieldOffset4 = buf.getIntLE(offset + 34);
/* 146 */       int pos4 = offset + 42 + fieldOffset4;
/* 147 */       pos4 += AmbienceFXAmbientBed.computeBytesConsumed(buf, pos4);
/* 148 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 150 */     if ((nullBits & 0x40) != 0) {
/* 151 */       int fieldOffset5 = buf.getIntLE(offset + 38);
/* 152 */       int pos5 = offset + 42 + fieldOffset5;
/* 153 */       int arrLen = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5) + arrLen * 4;
/* 154 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 156 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 161 */     int startPos = buf.writerIndex();
/* 162 */     byte nullBits = 0;
/* 163 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 164 */     if (this.conditions != null) nullBits = (byte)(nullBits | 0x2); 
/* 165 */     if (this.sounds != null) nullBits = (byte)(nullBits | 0x4); 
/* 166 */     if (this.music != null) nullBits = (byte)(nullBits | 0x8); 
/* 167 */     if (this.ambientBed != null) nullBits = (byte)(nullBits | 0x10); 
/* 168 */     if (this.soundEffect != null) nullBits = (byte)(nullBits | 0x20); 
/* 169 */     if (this.blockedAmbienceFxIndices != null) nullBits = (byte)(nullBits | 0x40); 
/* 170 */     buf.writeByte(nullBits);
/*     */     
/* 172 */     if (this.soundEffect != null) { this.soundEffect.serialize(buf); } else { buf.writeZero(9); }
/* 173 */      buf.writeIntLE(this.priority);
/* 174 */     buf.writeIntLE(this.audioCategoryIndex);
/*     */     
/* 176 */     int idOffsetSlot = buf.writerIndex();
/* 177 */     buf.writeIntLE(0);
/* 178 */     int conditionsOffsetSlot = buf.writerIndex();
/* 179 */     buf.writeIntLE(0);
/* 180 */     int soundsOffsetSlot = buf.writerIndex();
/* 181 */     buf.writeIntLE(0);
/* 182 */     int musicOffsetSlot = buf.writerIndex();
/* 183 */     buf.writeIntLE(0);
/* 184 */     int ambientBedOffsetSlot = buf.writerIndex();
/* 185 */     buf.writeIntLE(0);
/* 186 */     int blockedAmbienceFxIndicesOffsetSlot = buf.writerIndex();
/* 187 */     buf.writeIntLE(0);
/*     */     
/* 189 */     int varBlockStart = buf.writerIndex();
/* 190 */     if (this.id != null) {
/* 191 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 192 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 194 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 196 */     if (this.conditions != null) {
/* 197 */       buf.setIntLE(conditionsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 198 */       this.conditions.serialize(buf);
/*     */     } else {
/* 200 */       buf.setIntLE(conditionsOffsetSlot, -1);
/*     */     } 
/* 202 */     if (this.sounds != null) {
/* 203 */       buf.setIntLE(soundsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 204 */       if (this.sounds.length > 4096000) throw ProtocolException.arrayTooLong("Sounds", this.sounds.length, 4096000);  VarInt.write(buf, this.sounds.length); for (AmbienceFXSound item : this.sounds) item.serialize(buf); 
/*     */     } else {
/* 206 */       buf.setIntLE(soundsOffsetSlot, -1);
/*     */     } 
/* 208 */     if (this.music != null) {
/* 209 */       buf.setIntLE(musicOffsetSlot, buf.writerIndex() - varBlockStart);
/* 210 */       this.music.serialize(buf);
/*     */     } else {
/* 212 */       buf.setIntLE(musicOffsetSlot, -1);
/*     */     } 
/* 214 */     if (this.ambientBed != null) {
/* 215 */       buf.setIntLE(ambientBedOffsetSlot, buf.writerIndex() - varBlockStart);
/* 216 */       this.ambientBed.serialize(buf);
/*     */     } else {
/* 218 */       buf.setIntLE(ambientBedOffsetSlot, -1);
/*     */     } 
/* 220 */     if (this.blockedAmbienceFxIndices != null) {
/* 221 */       buf.setIntLE(blockedAmbienceFxIndicesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 222 */       if (this.blockedAmbienceFxIndices.length > 4096000) throw ProtocolException.arrayTooLong("BlockedAmbienceFxIndices", this.blockedAmbienceFxIndices.length, 4096000);  VarInt.write(buf, this.blockedAmbienceFxIndices.length); for (int item : this.blockedAmbienceFxIndices) buf.writeIntLE(item); 
/*     */     } else {
/* 224 */       buf.setIntLE(blockedAmbienceFxIndicesOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 230 */     int size = 42;
/* 231 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 232 */     if (this.conditions != null) size += this.conditions.computeSize(); 
/* 233 */     if (this.sounds != null) size += VarInt.size(this.sounds.length) + this.sounds.length * 27; 
/* 234 */     if (this.music != null) size += this.music.computeSize(); 
/* 235 */     if (this.ambientBed != null) size += this.ambientBed.computeSize(); 
/* 236 */     if (this.blockedAmbienceFxIndices != null) size += VarInt.size(this.blockedAmbienceFxIndices.length) + this.blockedAmbienceFxIndices.length * 4;
/*     */     
/* 238 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 242 */     if (buffer.readableBytes() - offset < 42) {
/* 243 */       return ValidationResult.error("Buffer too small: expected at least 42 bytes");
/*     */     }
/*     */     
/* 246 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 249 */     if ((nullBits & 0x1) != 0) {
/* 250 */       int idOffset = buffer.getIntLE(offset + 18);
/* 251 */       if (idOffset < 0) {
/* 252 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 254 */       int pos = offset + 42 + idOffset;
/* 255 */       if (pos >= buffer.writerIndex()) {
/* 256 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 258 */       int idLen = VarInt.peek(buffer, pos);
/* 259 */       if (idLen < 0) {
/* 260 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 262 */       if (idLen > 4096000) {
/* 263 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 265 */       pos += VarInt.length(buffer, pos);
/* 266 */       pos += idLen;
/* 267 */       if (pos > buffer.writerIndex()) {
/* 268 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 272 */     if ((nullBits & 0x2) != 0) {
/* 273 */       int conditionsOffset = buffer.getIntLE(offset + 22);
/* 274 */       if (conditionsOffset < 0) {
/* 275 */         return ValidationResult.error("Invalid offset for Conditions");
/*     */       }
/* 277 */       int pos = offset + 42 + conditionsOffset;
/* 278 */       if (pos >= buffer.writerIndex()) {
/* 279 */         return ValidationResult.error("Offset out of bounds for Conditions");
/*     */       }
/* 281 */       ValidationResult conditionsResult = AmbienceFXConditions.validateStructure(buffer, pos);
/* 282 */       if (!conditionsResult.isValid()) {
/* 283 */         return ValidationResult.error("Invalid Conditions: " + conditionsResult.error());
/*     */       }
/* 285 */       pos += AmbienceFXConditions.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 288 */     if ((nullBits & 0x4) != 0) {
/* 289 */       int soundsOffset = buffer.getIntLE(offset + 26);
/* 290 */       if (soundsOffset < 0) {
/* 291 */         return ValidationResult.error("Invalid offset for Sounds");
/*     */       }
/* 293 */       int pos = offset + 42 + soundsOffset;
/* 294 */       if (pos >= buffer.writerIndex()) {
/* 295 */         return ValidationResult.error("Offset out of bounds for Sounds");
/*     */       }
/* 297 */       int soundsCount = VarInt.peek(buffer, pos);
/* 298 */       if (soundsCount < 0) {
/* 299 */         return ValidationResult.error("Invalid array count for Sounds");
/*     */       }
/* 301 */       if (soundsCount > 4096000) {
/* 302 */         return ValidationResult.error("Sounds exceeds max length 4096000");
/*     */       }
/* 304 */       pos += VarInt.length(buffer, pos);
/* 305 */       pos += soundsCount * 27;
/* 306 */       if (pos > buffer.writerIndex()) {
/* 307 */         return ValidationResult.error("Buffer overflow reading Sounds");
/*     */       }
/*     */     } 
/*     */     
/* 311 */     if ((nullBits & 0x8) != 0) {
/* 312 */       int musicOffset = buffer.getIntLE(offset + 30);
/* 313 */       if (musicOffset < 0) {
/* 314 */         return ValidationResult.error("Invalid offset for Music");
/*     */       }
/* 316 */       int pos = offset + 42 + musicOffset;
/* 317 */       if (pos >= buffer.writerIndex()) {
/* 318 */         return ValidationResult.error("Offset out of bounds for Music");
/*     */       }
/* 320 */       ValidationResult musicResult = AmbienceFXMusic.validateStructure(buffer, pos);
/* 321 */       if (!musicResult.isValid()) {
/* 322 */         return ValidationResult.error("Invalid Music: " + musicResult.error());
/*     */       }
/* 324 */       pos += AmbienceFXMusic.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 327 */     if ((nullBits & 0x10) != 0) {
/* 328 */       int ambientBedOffset = buffer.getIntLE(offset + 34);
/* 329 */       if (ambientBedOffset < 0) {
/* 330 */         return ValidationResult.error("Invalid offset for AmbientBed");
/*     */       }
/* 332 */       int pos = offset + 42 + ambientBedOffset;
/* 333 */       if (pos >= buffer.writerIndex()) {
/* 334 */         return ValidationResult.error("Offset out of bounds for AmbientBed");
/*     */       }
/* 336 */       ValidationResult ambientBedResult = AmbienceFXAmbientBed.validateStructure(buffer, pos);
/* 337 */       if (!ambientBedResult.isValid()) {
/* 338 */         return ValidationResult.error("Invalid AmbientBed: " + ambientBedResult.error());
/*     */       }
/* 340 */       pos += AmbienceFXAmbientBed.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 343 */     if ((nullBits & 0x40) != 0) {
/* 344 */       int blockedAmbienceFxIndicesOffset = buffer.getIntLE(offset + 38);
/* 345 */       if (blockedAmbienceFxIndicesOffset < 0) {
/* 346 */         return ValidationResult.error("Invalid offset for BlockedAmbienceFxIndices");
/*     */       }
/* 348 */       int pos = offset + 42 + blockedAmbienceFxIndicesOffset;
/* 349 */       if (pos >= buffer.writerIndex()) {
/* 350 */         return ValidationResult.error("Offset out of bounds for BlockedAmbienceFxIndices");
/*     */       }
/* 352 */       int blockedAmbienceFxIndicesCount = VarInt.peek(buffer, pos);
/* 353 */       if (blockedAmbienceFxIndicesCount < 0) {
/* 354 */         return ValidationResult.error("Invalid array count for BlockedAmbienceFxIndices");
/*     */       }
/* 356 */       if (blockedAmbienceFxIndicesCount > 4096000) {
/* 357 */         return ValidationResult.error("BlockedAmbienceFxIndices exceeds max length 4096000");
/*     */       }
/* 359 */       pos += VarInt.length(buffer, pos);
/* 360 */       pos += blockedAmbienceFxIndicesCount * 4;
/* 361 */       if (pos > buffer.writerIndex()) {
/* 362 */         return ValidationResult.error("Buffer overflow reading BlockedAmbienceFxIndices");
/*     */       }
/*     */     } 
/* 365 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AmbienceFX clone() {
/* 369 */     AmbienceFX copy = new AmbienceFX();
/* 370 */     copy.id = this.id;
/* 371 */     copy.conditions = (this.conditions != null) ? this.conditions.clone() : null;
/* 372 */     copy.sounds = (this.sounds != null) ? (AmbienceFXSound[])Arrays.<AmbienceFXSound>stream(this.sounds).map(e -> e.clone()).toArray(x$0 -> new AmbienceFXSound[x$0]) : null;
/* 373 */     copy.music = (this.music != null) ? this.music.clone() : null;
/* 374 */     copy.ambientBed = (this.ambientBed != null) ? this.ambientBed.clone() : null;
/* 375 */     copy.soundEffect = (this.soundEffect != null) ? this.soundEffect.clone() : null;
/* 376 */     copy.priority = this.priority;
/* 377 */     copy.blockedAmbienceFxIndices = (this.blockedAmbienceFxIndices != null) ? Arrays.copyOf(this.blockedAmbienceFxIndices, this.blockedAmbienceFxIndices.length) : null;
/* 378 */     copy.audioCategoryIndex = this.audioCategoryIndex;
/* 379 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AmbienceFX other;
/* 385 */     if (this == obj) return true; 
/* 386 */     if (obj instanceof AmbienceFX) { other = (AmbienceFX)obj; } else { return false; }
/* 387 */      return (Objects.equals(this.id, other.id) && Objects.equals(this.conditions, other.conditions) && Arrays.equals((Object[])this.sounds, (Object[])other.sounds) && Objects.equals(this.music, other.music) && Objects.equals(this.ambientBed, other.ambientBed) && Objects.equals(this.soundEffect, other.soundEffect) && this.priority == other.priority && Arrays.equals(this.blockedAmbienceFxIndices, other.blockedAmbienceFxIndices) && this.audioCategoryIndex == other.audioCategoryIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 392 */     int result = 1;
/* 393 */     result = 31 * result + Objects.hashCode(this.id);
/* 394 */     result = 31 * result + Objects.hashCode(this.conditions);
/* 395 */     result = 31 * result + Arrays.hashCode((Object[])this.sounds);
/* 396 */     result = 31 * result + Objects.hashCode(this.music);
/* 397 */     result = 31 * result + Objects.hashCode(this.ambientBed);
/* 398 */     result = 31 * result + Objects.hashCode(this.soundEffect);
/* 399 */     result = 31 * result + Integer.hashCode(this.priority);
/* 400 */     result = 31 * result + Arrays.hashCode(this.blockedAmbienceFxIndices);
/* 401 */     result = 31 * result + Integer.hashCode(this.audioCategoryIndex);
/* 402 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\AmbienceFX.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */