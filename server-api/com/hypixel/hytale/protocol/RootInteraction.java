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
/*     */ 
/*     */ public class RootInteraction
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 6;
/*     */   public static final int VARIABLE_BLOCK_START = 30;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public String id;
/*     */   @Nullable
/*     */   public int[] interactions;
/*     */   
/*     */   public RootInteraction(@Nullable String id, @Nullable int[] interactions, @Nullable InteractionCooldown cooldown, @Nullable Map<GameMode, RootInteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, float clickQueuingTimeout, boolean requireNewClick) {
/*  33 */     this.id = id;
/*  34 */     this.interactions = interactions;
/*  35 */     this.cooldown = cooldown;
/*  36 */     this.settings = settings;
/*  37 */     this.rules = rules;
/*  38 */     this.tags = tags;
/*  39 */     this.clickQueuingTimeout = clickQueuingTimeout;
/*  40 */     this.requireNewClick = requireNewClick; } @Nullable
/*     */   public InteractionCooldown cooldown; @Nullable
/*     */   public Map<GameMode, RootInteractionSettings> settings; @Nullable
/*     */   public InteractionRules rules; @Nullable
/*  44 */   public int[] tags; public float clickQueuingTimeout; public boolean requireNewClick; public RootInteraction() {} public RootInteraction(@Nonnull RootInteraction other) { this.id = other.id;
/*  45 */     this.interactions = other.interactions;
/*  46 */     this.cooldown = other.cooldown;
/*  47 */     this.settings = other.settings;
/*  48 */     this.rules = other.rules;
/*  49 */     this.tags = other.tags;
/*  50 */     this.clickQueuingTimeout = other.clickQueuingTimeout;
/*  51 */     this.requireNewClick = other.requireNewClick; }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static RootInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  56 */     RootInteraction obj = new RootInteraction();
/*  57 */     byte nullBits = buf.getByte(offset);
/*  58 */     obj.clickQueuingTimeout = buf.getFloatLE(offset + 1);
/*  59 */     obj.requireNewClick = (buf.getByte(offset + 5) != 0);
/*     */     
/*  61 */     if ((nullBits & 0x1) != 0) {
/*  62 */       int varPos0 = offset + 30 + buf.getIntLE(offset + 6);
/*  63 */       int idLen = VarInt.peek(buf, varPos0);
/*  64 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  65 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  66 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  68 */     if ((nullBits & 0x2) != 0) {
/*  69 */       int varPos1 = offset + 30 + buf.getIntLE(offset + 10);
/*  70 */       int interactionsCount = VarInt.peek(buf, varPos1);
/*  71 */       if (interactionsCount < 0) throw ProtocolException.negativeLength("Interactions", interactionsCount); 
/*  72 */       if (interactionsCount > 4096000) throw ProtocolException.arrayTooLong("Interactions", interactionsCount, 4096000); 
/*  73 */       int varIntLen = VarInt.length(buf, varPos1);
/*  74 */       if ((varPos1 + varIntLen) + interactionsCount * 4L > buf.readableBytes())
/*  75 */         throw ProtocolException.bufferTooSmall("Interactions", varPos1 + varIntLen + interactionsCount * 4, buf.readableBytes()); 
/*  76 */       obj.interactions = new int[interactionsCount];
/*  77 */       for (int i = 0; i < interactionsCount; i++) {
/*  78 */         obj.interactions[i] = buf.getIntLE(varPos1 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/*  81 */     if ((nullBits & 0x4) != 0) {
/*  82 */       int varPos2 = offset + 30 + buf.getIntLE(offset + 14);
/*  83 */       obj.cooldown = InteractionCooldown.deserialize(buf, varPos2);
/*     */     } 
/*  85 */     if ((nullBits & 0x8) != 0) {
/*  86 */       int varPos3 = offset + 30 + buf.getIntLE(offset + 18);
/*  87 */       int settingsCount = VarInt.peek(buf, varPos3);
/*  88 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/*  89 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/*  90 */       int varIntLen = VarInt.length(buf, varPos3);
/*  91 */       obj.settings = new HashMap<>(settingsCount);
/*  92 */       int dictPos = varPos3 + varIntLen;
/*  93 */       for (int i = 0; i < settingsCount; i++) {
/*  94 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  95 */         RootInteractionSettings val = RootInteractionSettings.deserialize(buf, dictPos);
/*  96 */         dictPos += RootInteractionSettings.computeBytesConsumed(buf, dictPos);
/*  97 */         if (obj.settings.put(key, val) != null)
/*  98 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/* 101 */     if ((nullBits & 0x10) != 0) {
/* 102 */       int varPos4 = offset + 30 + buf.getIntLE(offset + 22);
/* 103 */       obj.rules = InteractionRules.deserialize(buf, varPos4);
/*     */     } 
/* 105 */     if ((nullBits & 0x20) != 0) {
/* 106 */       int varPos5 = offset + 30 + buf.getIntLE(offset + 26);
/* 107 */       int tagsCount = VarInt.peek(buf, varPos5);
/* 108 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/* 109 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/* 110 */       int varIntLen = VarInt.length(buf, varPos5);
/* 111 */       if ((varPos5 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/* 112 */         throw ProtocolException.bufferTooSmall("Tags", varPos5 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/* 113 */       obj.tags = new int[tagsCount];
/* 114 */       for (int i = 0; i < tagsCount; i++) {
/* 115 */         obj.tags[i] = buf.getIntLE(varPos5 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/*     */     
/* 119 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 123 */     byte nullBits = buf.getByte(offset);
/* 124 */     int maxEnd = 30;
/* 125 */     if ((nullBits & 0x1) != 0) {
/* 126 */       int fieldOffset0 = buf.getIntLE(offset + 6);
/* 127 */       int pos0 = offset + 30 + fieldOffset0;
/* 128 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 129 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 131 */     if ((nullBits & 0x2) != 0) {
/* 132 */       int fieldOffset1 = buf.getIntLE(offset + 10);
/* 133 */       int pos1 = offset + 30 + fieldOffset1;
/* 134 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + arrLen * 4;
/* 135 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 137 */     if ((nullBits & 0x4) != 0) {
/* 138 */       int fieldOffset2 = buf.getIntLE(offset + 14);
/* 139 */       int pos2 = offset + 30 + fieldOffset2;
/* 140 */       pos2 += InteractionCooldown.computeBytesConsumed(buf, pos2);
/* 141 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 143 */     if ((nullBits & 0x8) != 0) {
/* 144 */       int fieldOffset3 = buf.getIntLE(offset + 18);
/* 145 */       int pos3 = offset + 30 + fieldOffset3;
/* 146 */       int dictLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3);
/* 147 */       for (int i = 0; i < dictLen; ) { pos3 = ++pos3 + RootInteractionSettings.computeBytesConsumed(buf, pos3); i++; }
/* 148 */        if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 150 */     if ((nullBits & 0x10) != 0) {
/* 151 */       int fieldOffset4 = buf.getIntLE(offset + 22);
/* 152 */       int pos4 = offset + 30 + fieldOffset4;
/* 153 */       pos4 += InteractionRules.computeBytesConsumed(buf, pos4);
/* 154 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 156 */     if ((nullBits & 0x20) != 0) {
/* 157 */       int fieldOffset5 = buf.getIntLE(offset + 26);
/* 158 */       int pos5 = offset + 30 + fieldOffset5;
/* 159 */       int arrLen = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5) + arrLen * 4;
/* 160 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 162 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 167 */     int startPos = buf.writerIndex();
/* 168 */     byte nullBits = 0;
/* 169 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 170 */     if (this.interactions != null) nullBits = (byte)(nullBits | 0x2); 
/* 171 */     if (this.cooldown != null) nullBits = (byte)(nullBits | 0x4); 
/* 172 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x8); 
/* 173 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x10); 
/* 174 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x20); 
/* 175 */     buf.writeByte(nullBits);
/*     */     
/* 177 */     buf.writeFloatLE(this.clickQueuingTimeout);
/* 178 */     buf.writeByte(this.requireNewClick ? 1 : 0);
/*     */     
/* 180 */     int idOffsetSlot = buf.writerIndex();
/* 181 */     buf.writeIntLE(0);
/* 182 */     int interactionsOffsetSlot = buf.writerIndex();
/* 183 */     buf.writeIntLE(0);
/* 184 */     int cooldownOffsetSlot = buf.writerIndex();
/* 185 */     buf.writeIntLE(0);
/* 186 */     int settingsOffsetSlot = buf.writerIndex();
/* 187 */     buf.writeIntLE(0);
/* 188 */     int rulesOffsetSlot = buf.writerIndex();
/* 189 */     buf.writeIntLE(0);
/* 190 */     int tagsOffsetSlot = buf.writerIndex();
/* 191 */     buf.writeIntLE(0);
/*     */     
/* 193 */     int varBlockStart = buf.writerIndex();
/* 194 */     if (this.id != null) {
/* 195 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 196 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 198 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 200 */     if (this.interactions != null) {
/* 201 */       buf.setIntLE(interactionsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 202 */       if (this.interactions.length > 4096000) throw ProtocolException.arrayTooLong("Interactions", this.interactions.length, 4096000);  VarInt.write(buf, this.interactions.length); for (int item : this.interactions) buf.writeIntLE(item); 
/*     */     } else {
/* 204 */       buf.setIntLE(interactionsOffsetSlot, -1);
/*     */     } 
/* 206 */     if (this.cooldown != null) {
/* 207 */       buf.setIntLE(cooldownOffsetSlot, buf.writerIndex() - varBlockStart);
/* 208 */       this.cooldown.serialize(buf);
/*     */     } else {
/* 210 */       buf.setIntLE(cooldownOffsetSlot, -1);
/*     */     } 
/* 212 */     if (this.settings != null)
/* 213 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 214 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, RootInteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((RootInteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 216 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 218 */     if (this.rules != null) {
/* 219 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 220 */       this.rules.serialize(buf);
/*     */     } else {
/* 222 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 224 */     if (this.tags != null) {
/* 225 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 226 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 228 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 234 */     int size = 30;
/* 235 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 236 */     if (this.interactions != null) size += VarInt.size(this.interactions.length) + this.interactions.length * 4; 
/* 237 */     if (this.cooldown != null) size += this.cooldown.computeSize(); 
/* 238 */     if (this.settings != null) {
/* 239 */       int settingsSize = 0;
/* 240 */       for (Map.Entry<GameMode, RootInteractionSettings> kvp : this.settings.entrySet()) settingsSize += 1 + ((RootInteractionSettings)kvp.getValue()).computeSize(); 
/* 241 */       size += VarInt.size(this.settings.size()) + settingsSize;
/*     */     } 
/* 243 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 244 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4;
/*     */     
/* 246 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 250 */     if (buffer.readableBytes() - offset < 30) {
/* 251 */       return ValidationResult.error("Buffer too small: expected at least 30 bytes");
/*     */     }
/*     */     
/* 254 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 257 */     if ((nullBits & 0x1) != 0) {
/* 258 */       int idOffset = buffer.getIntLE(offset + 6);
/* 259 */       if (idOffset < 0) {
/* 260 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 262 */       int pos = offset + 30 + idOffset;
/* 263 */       if (pos >= buffer.writerIndex()) {
/* 264 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 266 */       int idLen = VarInt.peek(buffer, pos);
/* 267 */       if (idLen < 0) {
/* 268 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 270 */       if (idLen > 4096000) {
/* 271 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 273 */       pos += VarInt.length(buffer, pos);
/* 274 */       pos += idLen;
/* 275 */       if (pos > buffer.writerIndex()) {
/* 276 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 280 */     if ((nullBits & 0x2) != 0) {
/* 281 */       int interactionsOffset = buffer.getIntLE(offset + 10);
/* 282 */       if (interactionsOffset < 0) {
/* 283 */         return ValidationResult.error("Invalid offset for Interactions");
/*     */       }
/* 285 */       int pos = offset + 30 + interactionsOffset;
/* 286 */       if (pos >= buffer.writerIndex()) {
/* 287 */         return ValidationResult.error("Offset out of bounds for Interactions");
/*     */       }
/* 289 */       int interactionsCount = VarInt.peek(buffer, pos);
/* 290 */       if (interactionsCount < 0) {
/* 291 */         return ValidationResult.error("Invalid array count for Interactions");
/*     */       }
/* 293 */       if (interactionsCount > 4096000) {
/* 294 */         return ValidationResult.error("Interactions exceeds max length 4096000");
/*     */       }
/* 296 */       pos += VarInt.length(buffer, pos);
/* 297 */       pos += interactionsCount * 4;
/* 298 */       if (pos > buffer.writerIndex()) {
/* 299 */         return ValidationResult.error("Buffer overflow reading Interactions");
/*     */       }
/*     */     } 
/*     */     
/* 303 */     if ((nullBits & 0x4) != 0) {
/* 304 */       int cooldownOffset = buffer.getIntLE(offset + 14);
/* 305 */       if (cooldownOffset < 0) {
/* 306 */         return ValidationResult.error("Invalid offset for Cooldown");
/*     */       }
/* 308 */       int pos = offset + 30 + cooldownOffset;
/* 309 */       if (pos >= buffer.writerIndex()) {
/* 310 */         return ValidationResult.error("Offset out of bounds for Cooldown");
/*     */       }
/* 312 */       ValidationResult cooldownResult = InteractionCooldown.validateStructure(buffer, pos);
/* 313 */       if (!cooldownResult.isValid()) {
/* 314 */         return ValidationResult.error("Invalid Cooldown: " + cooldownResult.error());
/*     */       }
/* 316 */       pos += InteractionCooldown.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 319 */     if ((nullBits & 0x8) != 0) {
/* 320 */       int settingsOffset = buffer.getIntLE(offset + 18);
/* 321 */       if (settingsOffset < 0) {
/* 322 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 324 */       int pos = offset + 30 + settingsOffset;
/* 325 */       if (pos >= buffer.writerIndex()) {
/* 326 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 328 */       int settingsCount = VarInt.peek(buffer, pos);
/* 329 */       if (settingsCount < 0) {
/* 330 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 332 */       if (settingsCount > 4096000) {
/* 333 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 335 */       pos += VarInt.length(buffer, pos);
/* 336 */       for (int i = 0; i < settingsCount; i++) {
/* 337 */         pos++;
/*     */         
/* 339 */         pos += RootInteractionSettings.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 344 */     if ((nullBits & 0x10) != 0) {
/* 345 */       int rulesOffset = buffer.getIntLE(offset + 22);
/* 346 */       if (rulesOffset < 0) {
/* 347 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 349 */       int pos = offset + 30 + rulesOffset;
/* 350 */       if (pos >= buffer.writerIndex()) {
/* 351 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 353 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 354 */       if (!rulesResult.isValid()) {
/* 355 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 357 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 360 */     if ((nullBits & 0x20) != 0) {
/* 361 */       int tagsOffset = buffer.getIntLE(offset + 26);
/* 362 */       if (tagsOffset < 0) {
/* 363 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 365 */       int pos = offset + 30 + tagsOffset;
/* 366 */       if (pos >= buffer.writerIndex()) {
/* 367 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 369 */       int tagsCount = VarInt.peek(buffer, pos);
/* 370 */       if (tagsCount < 0) {
/* 371 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 373 */       if (tagsCount > 4096000) {
/* 374 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 376 */       pos += VarInt.length(buffer, pos);
/* 377 */       pos += tagsCount * 4;
/* 378 */       if (pos > buffer.writerIndex()) {
/* 379 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/* 382 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public RootInteraction clone() {
/* 386 */     RootInteraction copy = new RootInteraction();
/* 387 */     copy.id = this.id;
/* 388 */     copy.interactions = (this.interactions != null) ? Arrays.copyOf(this.interactions, this.interactions.length) : null;
/* 389 */     copy.cooldown = (this.cooldown != null) ? this.cooldown.clone() : null;
/* 390 */     if (this.settings != null) {
/* 391 */       Map<GameMode, RootInteractionSettings> m = new HashMap<>();
/* 392 */       for (Map.Entry<GameMode, RootInteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((RootInteractionSettings)e.getValue()).clone()); 
/* 393 */       copy.settings = m;
/*     */     } 
/* 395 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 396 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 397 */     copy.clickQueuingTimeout = this.clickQueuingTimeout;
/* 398 */     copy.requireNewClick = this.requireNewClick;
/* 399 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     RootInteraction other;
/* 405 */     if (this == obj) return true; 
/* 406 */     if (obj instanceof RootInteraction) { other = (RootInteraction)obj; } else { return false; }
/* 407 */      return (Objects.equals(this.id, other.id) && Arrays.equals(this.interactions, other.interactions) && Objects.equals(this.cooldown, other.cooldown) && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && this.clickQueuingTimeout == other.clickQueuingTimeout && this.requireNewClick == other.requireNewClick);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 412 */     int result = 1;
/* 413 */     result = 31 * result + Objects.hashCode(this.id);
/* 414 */     result = 31 * result + Arrays.hashCode(this.interactions);
/* 415 */     result = 31 * result + Objects.hashCode(this.cooldown);
/* 416 */     result = 31 * result + Objects.hashCode(this.settings);
/* 417 */     result = 31 * result + Objects.hashCode(this.rules);
/* 418 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 419 */     result = 31 * result + Float.hashCode(this.clickQueuingTimeout);
/* 420 */     result = 31 * result + Boolean.hashCode(this.requireNewClick);
/* 421 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\RootInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */