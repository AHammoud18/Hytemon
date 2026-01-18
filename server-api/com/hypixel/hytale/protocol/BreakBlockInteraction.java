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
/*     */ public class BreakBlockInteraction
/*     */   extends SimpleBlockInteraction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 21;
/*     */   public static final int VARIABLE_FIELD_COUNT = 5;
/*     */   public static final int VARIABLE_BLOCK_START = 41;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   public boolean harvest;
/*     */   
/*     */   public BreakBlockInteraction() {}
/*     */   
/*     */   public BreakBlockInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, boolean useLatestTarget, boolean harvest) {
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
/*  37 */     this.useLatestTarget = useLatestTarget;
/*  38 */     this.harvest = harvest;
/*     */   }
/*     */   
/*     */   public BreakBlockInteraction(@Nonnull BreakBlockInteraction other) {
/*  42 */     this.waitForDataFrom = other.waitForDataFrom;
/*  43 */     this.effects = other.effects;
/*  44 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  45 */     this.runTime = other.runTime;
/*  46 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  47 */     this.settings = other.settings;
/*  48 */     this.rules = other.rules;
/*  49 */     this.tags = other.tags;
/*  50 */     this.camera = other.camera;
/*  51 */     this.next = other.next;
/*  52 */     this.failed = other.failed;
/*  53 */     this.useLatestTarget = other.useLatestTarget;
/*  54 */     this.harvest = other.harvest;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BreakBlockInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  59 */     BreakBlockInteraction obj = new BreakBlockInteraction();
/*  60 */     byte nullBits = buf.getByte(offset);
/*  61 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  62 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/*  63 */     obj.runTime = buf.getFloatLE(offset + 6);
/*  64 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/*  65 */     obj.next = buf.getIntLE(offset + 11);
/*  66 */     obj.failed = buf.getIntLE(offset + 15);
/*  67 */     obj.useLatestTarget = (buf.getByte(offset + 19) != 0);
/*  68 */     obj.harvest = (buf.getByte(offset + 20) != 0);
/*     */     
/*  70 */     if ((nullBits & 0x1) != 0) {
/*  71 */       int varPos0 = offset + 41 + buf.getIntLE(offset + 21);
/*  72 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/*  74 */     if ((nullBits & 0x2) != 0) {
/*  75 */       int varPos1 = offset + 41 + buf.getIntLE(offset + 25);
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
/*  91 */       int varPos2 = offset + 41 + buf.getIntLE(offset + 29);
/*  92 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/*  94 */     if ((nullBits & 0x8) != 0) {
/*  95 */       int varPos3 = offset + 41 + buf.getIntLE(offset + 33);
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
/* 108 */       int varPos4 = offset + 41 + buf.getIntLE(offset + 37);
/* 109 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/*     */     
/* 112 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 116 */     byte nullBits = buf.getByte(offset);
/* 117 */     int maxEnd = 41;
/* 118 */     if ((nullBits & 0x1) != 0) {
/* 119 */       int fieldOffset0 = buf.getIntLE(offset + 21);
/* 120 */       int pos0 = offset + 41 + fieldOffset0;
/* 121 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 122 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 124 */     if ((nullBits & 0x2) != 0) {
/* 125 */       int fieldOffset1 = buf.getIntLE(offset + 25);
/* 126 */       int pos1 = offset + 41 + fieldOffset1;
/* 127 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 128 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 129 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 131 */     if ((nullBits & 0x4) != 0) {
/* 132 */       int fieldOffset2 = buf.getIntLE(offset + 29);
/* 133 */       int pos2 = offset + 41 + fieldOffset2;
/* 134 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 135 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 137 */     if ((nullBits & 0x8) != 0) {
/* 138 */       int fieldOffset3 = buf.getIntLE(offset + 33);
/* 139 */       int pos3 = offset + 41 + fieldOffset3;
/* 140 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 141 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 143 */     if ((nullBits & 0x10) != 0) {
/* 144 */       int fieldOffset4 = buf.getIntLE(offset + 37);
/* 145 */       int pos4 = offset + 41 + fieldOffset4;
/* 146 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 147 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 149 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 155 */     int startPos = buf.writerIndex();
/* 156 */     byte nullBits = 0;
/* 157 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 158 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 159 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 160 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 161 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 162 */     buf.writeByte(nullBits);
/*     */     
/* 164 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 165 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 166 */     buf.writeFloatLE(this.runTime);
/* 167 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 168 */     buf.writeIntLE(this.next);
/* 169 */     buf.writeIntLE(this.failed);
/* 170 */     buf.writeByte(this.useLatestTarget ? 1 : 0);
/* 171 */     buf.writeByte(this.harvest ? 1 : 0);
/*     */     
/* 173 */     int effectsOffsetSlot = buf.writerIndex();
/* 174 */     buf.writeIntLE(0);
/* 175 */     int settingsOffsetSlot = buf.writerIndex();
/* 176 */     buf.writeIntLE(0);
/* 177 */     int rulesOffsetSlot = buf.writerIndex();
/* 178 */     buf.writeIntLE(0);
/* 179 */     int tagsOffsetSlot = buf.writerIndex();
/* 180 */     buf.writeIntLE(0);
/* 181 */     int cameraOffsetSlot = buf.writerIndex();
/* 182 */     buf.writeIntLE(0);
/*     */     
/* 184 */     int varBlockStart = buf.writerIndex();
/* 185 */     if (this.effects != null) {
/* 186 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 187 */       this.effects.serialize(buf);
/*     */     } else {
/* 189 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 191 */     if (this.settings != null)
/* 192 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 193 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 195 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 197 */     if (this.rules != null) {
/* 198 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 199 */       this.rules.serialize(buf);
/*     */     } else {
/* 201 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 203 */     if (this.tags != null) {
/* 204 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 205 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 207 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 209 */     if (this.camera != null) {
/* 210 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 211 */       this.camera.serialize(buf);
/*     */     } else {
/* 213 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 215 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 221 */     int size = 41;
/* 222 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 223 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 224 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 225 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 226 */     if (this.camera != null) size += this.camera.computeSize();
/*     */     
/* 228 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 232 */     if (buffer.readableBytes() - offset < 41) {
/* 233 */       return ValidationResult.error("Buffer too small: expected at least 41 bytes");
/*     */     }
/*     */     
/* 236 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 239 */     if ((nullBits & 0x1) != 0) {
/* 240 */       int effectsOffset = buffer.getIntLE(offset + 21);
/* 241 */       if (effectsOffset < 0) {
/* 242 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 244 */       int pos = offset + 41 + effectsOffset;
/* 245 */       if (pos >= buffer.writerIndex()) {
/* 246 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 248 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 249 */       if (!effectsResult.isValid()) {
/* 250 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 252 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 255 */     if ((nullBits & 0x2) != 0) {
/* 256 */       int settingsOffset = buffer.getIntLE(offset + 25);
/* 257 */       if (settingsOffset < 0) {
/* 258 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 260 */       int pos = offset + 41 + settingsOffset;
/* 261 */       if (pos >= buffer.writerIndex()) {
/* 262 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 264 */       int settingsCount = VarInt.peek(buffer, pos);
/* 265 */       if (settingsCount < 0) {
/* 266 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 268 */       if (settingsCount > 4096000) {
/* 269 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 271 */       pos += VarInt.length(buffer, pos);
/* 272 */       for (int i = 0; i < settingsCount; i++) {
/* 273 */         pos++;
/*     */         
/* 275 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 280 */     if ((nullBits & 0x4) != 0) {
/* 281 */       int rulesOffset = buffer.getIntLE(offset + 29);
/* 282 */       if (rulesOffset < 0) {
/* 283 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 285 */       int pos = offset + 41 + rulesOffset;
/* 286 */       if (pos >= buffer.writerIndex()) {
/* 287 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 289 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 290 */       if (!rulesResult.isValid()) {
/* 291 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 293 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 296 */     if ((nullBits & 0x8) != 0) {
/* 297 */       int tagsOffset = buffer.getIntLE(offset + 33);
/* 298 */       if (tagsOffset < 0) {
/* 299 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 301 */       int pos = offset + 41 + tagsOffset;
/* 302 */       if (pos >= buffer.writerIndex()) {
/* 303 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 305 */       int tagsCount = VarInt.peek(buffer, pos);
/* 306 */       if (tagsCount < 0) {
/* 307 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 309 */       if (tagsCount > 4096000) {
/* 310 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 312 */       pos += VarInt.length(buffer, pos);
/* 313 */       pos += tagsCount * 4;
/* 314 */       if (pos > buffer.writerIndex()) {
/* 315 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 319 */     if ((nullBits & 0x10) != 0) {
/* 320 */       int cameraOffset = buffer.getIntLE(offset + 37);
/* 321 */       if (cameraOffset < 0) {
/* 322 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 324 */       int pos = offset + 41 + cameraOffset;
/* 325 */       if (pos >= buffer.writerIndex()) {
/* 326 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 328 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 329 */       if (!cameraResult.isValid()) {
/* 330 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 332 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 334 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BreakBlockInteraction clone() {
/* 338 */     BreakBlockInteraction copy = new BreakBlockInteraction();
/* 339 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 340 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 341 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 342 */     copy.runTime = this.runTime;
/* 343 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 344 */     if (this.settings != null) {
/* 345 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 346 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 347 */       copy.settings = m;
/*     */     } 
/* 349 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 350 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 351 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 352 */     copy.next = this.next;
/* 353 */     copy.failed = this.failed;
/* 354 */     copy.useLatestTarget = this.useLatestTarget;
/* 355 */     copy.harvest = this.harvest;
/* 356 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BreakBlockInteraction other;
/* 362 */     if (this == obj) return true; 
/* 363 */     if (obj instanceof BreakBlockInteraction) { other = (BreakBlockInteraction)obj; } else { return false; }
/* 364 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && this.useLatestTarget == other.useLatestTarget && this.harvest == other.harvest);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 369 */     int result = 1;
/* 370 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 371 */     result = 31 * result + Objects.hashCode(this.effects);
/* 372 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 373 */     result = 31 * result + Float.hashCode(this.runTime);
/* 374 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 375 */     result = 31 * result + Objects.hashCode(this.settings);
/* 376 */     result = 31 * result + Objects.hashCode(this.rules);
/* 377 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 378 */     result = 31 * result + Objects.hashCode(this.camera);
/* 379 */     result = 31 * result + Integer.hashCode(this.next);
/* 380 */     result = 31 * result + Integer.hashCode(this.failed);
/* 381 */     result = 31 * result + Boolean.hashCode(this.useLatestTarget);
/* 382 */     result = 31 * result + Boolean.hashCode(this.harvest);
/* 383 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BreakBlockInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */