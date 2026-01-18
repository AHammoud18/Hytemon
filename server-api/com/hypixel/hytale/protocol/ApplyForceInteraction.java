/*     */ package com.hypixel.hytale.protocol;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ApplyForceInteraction extends SimpleInteraction {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 80;
/*     */   public static final int VARIABLE_FIELD_COUNT = 6;
/*     */   public static final int VARIABLE_BLOCK_START = 104;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public VelocityConfig velocityConfig;
/*     */   @Nonnull
/*  21 */   public ChangeVelocityType changeVelocityType = ChangeVelocityType.Add;
/*     */   
/*     */   @Nullable
/*     */   public AppliedForce[] forces;
/*     */   
/*     */   public float duration;
/*     */   
/*     */   @Nullable
/*     */   public FloatRange verticalClamp;
/*     */   public boolean waitForGround;
/*     */   public boolean waitForCollision;
/*     */   @Nonnull
/*  33 */   public RaycastMode raycastMode = RaycastMode.FollowMotion; public float groundCheckDelay; public float collisionCheckDelay; public int groundNext;
/*     */   public int collisionNext;
/*     */   public float raycastDistance;
/*     */   public float raycastHeightOffset;
/*     */   
/*     */   public ApplyForceInteraction(@Nonnull WaitForDataFrom waitForDataFrom, @Nullable InteractionEffects effects, float horizontalSpeedMultiplier, float runTime, boolean cancelOnItemChange, @Nullable Map<GameMode, InteractionSettings> settings, @Nullable InteractionRules rules, @Nullable int[] tags, @Nullable InteractionCameraSettings camera, int next, int failed, @Nullable VelocityConfig velocityConfig, @Nonnull ChangeVelocityType changeVelocityType, @Nullable AppliedForce[] forces, float duration, @Nullable FloatRange verticalClamp, boolean waitForGround, boolean waitForCollision, float groundCheckDelay, float collisionCheckDelay, int groundNext, int collisionNext, float raycastDistance, float raycastHeightOffset, @Nonnull RaycastMode raycastMode) {
/*  39 */     this.waitForDataFrom = waitForDataFrom;
/*  40 */     this.effects = effects;
/*  41 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  42 */     this.runTime = runTime;
/*  43 */     this.cancelOnItemChange = cancelOnItemChange;
/*  44 */     this.settings = settings;
/*  45 */     this.rules = rules;
/*  46 */     this.tags = tags;
/*  47 */     this.camera = camera;
/*  48 */     this.next = next;
/*  49 */     this.failed = failed;
/*  50 */     this.velocityConfig = velocityConfig;
/*  51 */     this.changeVelocityType = changeVelocityType;
/*  52 */     this.forces = forces;
/*  53 */     this.duration = duration;
/*  54 */     this.verticalClamp = verticalClamp;
/*  55 */     this.waitForGround = waitForGround;
/*  56 */     this.waitForCollision = waitForCollision;
/*  57 */     this.groundCheckDelay = groundCheckDelay;
/*  58 */     this.collisionCheckDelay = collisionCheckDelay;
/*  59 */     this.groundNext = groundNext;
/*  60 */     this.collisionNext = collisionNext;
/*  61 */     this.raycastDistance = raycastDistance;
/*  62 */     this.raycastHeightOffset = raycastHeightOffset;
/*  63 */     this.raycastMode = raycastMode;
/*     */   }
/*     */   
/*     */   public ApplyForceInteraction(@Nonnull ApplyForceInteraction other) {
/*  67 */     this.waitForDataFrom = other.waitForDataFrom;
/*  68 */     this.effects = other.effects;
/*  69 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  70 */     this.runTime = other.runTime;
/*  71 */     this.cancelOnItemChange = other.cancelOnItemChange;
/*  72 */     this.settings = other.settings;
/*  73 */     this.rules = other.rules;
/*  74 */     this.tags = other.tags;
/*  75 */     this.camera = other.camera;
/*  76 */     this.next = other.next;
/*  77 */     this.failed = other.failed;
/*  78 */     this.velocityConfig = other.velocityConfig;
/*  79 */     this.changeVelocityType = other.changeVelocityType;
/*  80 */     this.forces = other.forces;
/*  81 */     this.duration = other.duration;
/*  82 */     this.verticalClamp = other.verticalClamp;
/*  83 */     this.waitForGround = other.waitForGround;
/*  84 */     this.waitForCollision = other.waitForCollision;
/*  85 */     this.groundCheckDelay = other.groundCheckDelay;
/*  86 */     this.collisionCheckDelay = other.collisionCheckDelay;
/*  87 */     this.groundNext = other.groundNext;
/*  88 */     this.collisionNext = other.collisionNext;
/*  89 */     this.raycastDistance = other.raycastDistance;
/*  90 */     this.raycastHeightOffset = other.raycastHeightOffset;
/*  91 */     this.raycastMode = other.raycastMode;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ApplyForceInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  96 */     ApplyForceInteraction obj = new ApplyForceInteraction();
/*  97 */     byte nullBits = buf.getByte(offset);
/*  98 */     obj.waitForDataFrom = WaitForDataFrom.fromValue(buf.getByte(offset + 1));
/*  99 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 2);
/* 100 */     obj.runTime = buf.getFloatLE(offset + 6);
/* 101 */     obj.cancelOnItemChange = (buf.getByte(offset + 10) != 0);
/* 102 */     obj.next = buf.getIntLE(offset + 11);
/* 103 */     obj.failed = buf.getIntLE(offset + 15);
/* 104 */     if ((nullBits & 0x20) != 0) obj.velocityConfig = VelocityConfig.deserialize(buf, offset + 19); 
/* 105 */     obj.changeVelocityType = ChangeVelocityType.fromValue(buf.getByte(offset + 40));
/* 106 */     obj.duration = buf.getFloatLE(offset + 41);
/* 107 */     if ((nullBits & 0x80) != 0) obj.verticalClamp = FloatRange.deserialize(buf, offset + 45); 
/* 108 */     obj.waitForGround = (buf.getByte(offset + 53) != 0);
/* 109 */     obj.waitForCollision = (buf.getByte(offset + 54) != 0);
/* 110 */     obj.groundCheckDelay = buf.getFloatLE(offset + 55);
/* 111 */     obj.collisionCheckDelay = buf.getFloatLE(offset + 59);
/* 112 */     obj.groundNext = buf.getIntLE(offset + 63);
/* 113 */     obj.collisionNext = buf.getIntLE(offset + 67);
/* 114 */     obj.raycastDistance = buf.getFloatLE(offset + 71);
/* 115 */     obj.raycastHeightOffset = buf.getFloatLE(offset + 75);
/* 116 */     obj.raycastMode = RaycastMode.fromValue(buf.getByte(offset + 79));
/*     */     
/* 118 */     if ((nullBits & 0x1) != 0) {
/* 119 */       int varPos0 = offset + 104 + buf.getIntLE(offset + 80);
/* 120 */       obj.effects = InteractionEffects.deserialize(buf, varPos0);
/*     */     } 
/* 122 */     if ((nullBits & 0x2) != 0) {
/* 123 */       int varPos1 = offset + 104 + buf.getIntLE(offset + 84);
/* 124 */       int settingsCount = VarInt.peek(buf, varPos1);
/* 125 */       if (settingsCount < 0) throw ProtocolException.negativeLength("Settings", settingsCount); 
/* 126 */       if (settingsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", settingsCount, 4096000); 
/* 127 */       int varIntLen = VarInt.length(buf, varPos1);
/* 128 */       obj.settings = new HashMap<>(settingsCount);
/* 129 */       int dictPos = varPos1 + varIntLen;
/* 130 */       for (int i = 0; i < settingsCount; i++) {
/* 131 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/* 132 */         InteractionSettings val = InteractionSettings.deserialize(buf, dictPos);
/* 133 */         dictPos += InteractionSettings.computeBytesConsumed(buf, dictPos);
/* 134 */         if (obj.settings.put(key, val) != null)
/* 135 */           throw ProtocolException.duplicateKey("settings", key); 
/*     */       } 
/*     */     } 
/* 138 */     if ((nullBits & 0x4) != 0) {
/* 139 */       int varPos2 = offset + 104 + buf.getIntLE(offset + 88);
/* 140 */       obj.rules = InteractionRules.deserialize(buf, varPos2);
/*     */     } 
/* 142 */     if ((nullBits & 0x8) != 0) {
/* 143 */       int varPos3 = offset + 104 + buf.getIntLE(offset + 92);
/* 144 */       int tagsCount = VarInt.peek(buf, varPos3);
/* 145 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/* 146 */       if (tagsCount > 4096000) throw ProtocolException.arrayTooLong("Tags", tagsCount, 4096000); 
/* 147 */       int varIntLen = VarInt.length(buf, varPos3);
/* 148 */       if ((varPos3 + varIntLen) + tagsCount * 4L > buf.readableBytes())
/* 149 */         throw ProtocolException.bufferTooSmall("Tags", varPos3 + varIntLen + tagsCount * 4, buf.readableBytes()); 
/* 150 */       obj.tags = new int[tagsCount];
/* 151 */       for (int i = 0; i < tagsCount; i++) {
/* 152 */         obj.tags[i] = buf.getIntLE(varPos3 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 155 */     if ((nullBits & 0x10) != 0) {
/* 156 */       int varPos4 = offset + 104 + buf.getIntLE(offset + 96);
/* 157 */       obj.camera = InteractionCameraSettings.deserialize(buf, varPos4);
/*     */     } 
/* 159 */     if ((nullBits & 0x40) != 0) {
/* 160 */       int varPos5 = offset + 104 + buf.getIntLE(offset + 100);
/* 161 */       int forcesCount = VarInt.peek(buf, varPos5);
/* 162 */       if (forcesCount < 0) throw ProtocolException.negativeLength("Forces", forcesCount); 
/* 163 */       if (forcesCount > 4096000) throw ProtocolException.arrayTooLong("Forces", forcesCount, 4096000); 
/* 164 */       int varIntLen = VarInt.length(buf, varPos5);
/* 165 */       if ((varPos5 + varIntLen) + forcesCount * 18L > buf.readableBytes())
/* 166 */         throw ProtocolException.bufferTooSmall("Forces", varPos5 + varIntLen + forcesCount * 18, buf.readableBytes()); 
/* 167 */       obj.forces = new AppliedForce[forcesCount];
/* 168 */       int elemPos = varPos5 + varIntLen;
/* 169 */       for (int i = 0; i < forcesCount; i++) {
/* 170 */         obj.forces[i] = AppliedForce.deserialize(buf, elemPos);
/* 171 */         elemPos += AppliedForce.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/* 175 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 179 */     byte nullBits = buf.getByte(offset);
/* 180 */     int maxEnd = 104;
/* 181 */     if ((nullBits & 0x1) != 0) {
/* 182 */       int fieldOffset0 = buf.getIntLE(offset + 80);
/* 183 */       int pos0 = offset + 104 + fieldOffset0;
/* 184 */       pos0 += InteractionEffects.computeBytesConsumed(buf, pos0);
/* 185 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 187 */     if ((nullBits & 0x2) != 0) {
/* 188 */       int fieldOffset1 = buf.getIntLE(offset + 84);
/* 189 */       int pos1 = offset + 104 + fieldOffset1;
/* 190 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 191 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionSettings.computeBytesConsumed(buf, pos1); i++; }
/* 192 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 194 */     if ((nullBits & 0x4) != 0) {
/* 195 */       int fieldOffset2 = buf.getIntLE(offset + 88);
/* 196 */       int pos2 = offset + 104 + fieldOffset2;
/* 197 */       pos2 += InteractionRules.computeBytesConsumed(buf, pos2);
/* 198 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 200 */     if ((nullBits & 0x8) != 0) {
/* 201 */       int fieldOffset3 = buf.getIntLE(offset + 92);
/* 202 */       int pos3 = offset + 104 + fieldOffset3;
/* 203 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + arrLen * 4;
/* 204 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 206 */     if ((nullBits & 0x10) != 0) {
/* 207 */       int fieldOffset4 = buf.getIntLE(offset + 96);
/* 208 */       int pos4 = offset + 104 + fieldOffset4;
/* 209 */       pos4 += InteractionCameraSettings.computeBytesConsumed(buf, pos4);
/* 210 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 212 */     if ((nullBits & 0x40) != 0) {
/* 213 */       int fieldOffset5 = buf.getIntLE(offset + 100);
/* 214 */       int pos5 = offset + 104 + fieldOffset5;
/* 215 */       int arrLen = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5);
/* 216 */       for (int i = 0; i < arrLen; ) { pos5 += AppliedForce.computeBytesConsumed(buf, pos5); i++; }
/* 217 */        if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 219 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/* 225 */     int startPos = buf.writerIndex();
/* 226 */     byte nullBits = 0;
/* 227 */     if (this.effects != null) nullBits = (byte)(nullBits | 0x1); 
/* 228 */     if (this.settings != null) nullBits = (byte)(nullBits | 0x2); 
/* 229 */     if (this.rules != null) nullBits = (byte)(nullBits | 0x4); 
/* 230 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x8); 
/* 231 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x10); 
/* 232 */     if (this.velocityConfig != null) nullBits = (byte)(nullBits | 0x20); 
/* 233 */     if (this.forces != null) nullBits = (byte)(nullBits | 0x40); 
/* 234 */     if (this.verticalClamp != null) nullBits = (byte)(nullBits | 0x80); 
/* 235 */     buf.writeByte(nullBits);
/*     */     
/* 237 */     buf.writeByte(this.waitForDataFrom.getValue());
/* 238 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 239 */     buf.writeFloatLE(this.runTime);
/* 240 */     buf.writeByte(this.cancelOnItemChange ? 1 : 0);
/* 241 */     buf.writeIntLE(this.next);
/* 242 */     buf.writeIntLE(this.failed);
/* 243 */     if (this.velocityConfig != null) { this.velocityConfig.serialize(buf); } else { buf.writeZero(21); }
/* 244 */      buf.writeByte(this.changeVelocityType.getValue());
/* 245 */     buf.writeFloatLE(this.duration);
/* 246 */     if (this.verticalClamp != null) { this.verticalClamp.serialize(buf); } else { buf.writeZero(8); }
/* 247 */      buf.writeByte(this.waitForGround ? 1 : 0);
/* 248 */     buf.writeByte(this.waitForCollision ? 1 : 0);
/* 249 */     buf.writeFloatLE(this.groundCheckDelay);
/* 250 */     buf.writeFloatLE(this.collisionCheckDelay);
/* 251 */     buf.writeIntLE(this.groundNext);
/* 252 */     buf.writeIntLE(this.collisionNext);
/* 253 */     buf.writeFloatLE(this.raycastDistance);
/* 254 */     buf.writeFloatLE(this.raycastHeightOffset);
/* 255 */     buf.writeByte(this.raycastMode.getValue());
/*     */     
/* 257 */     int effectsOffsetSlot = buf.writerIndex();
/* 258 */     buf.writeIntLE(0);
/* 259 */     int settingsOffsetSlot = buf.writerIndex();
/* 260 */     buf.writeIntLE(0);
/* 261 */     int rulesOffsetSlot = buf.writerIndex();
/* 262 */     buf.writeIntLE(0);
/* 263 */     int tagsOffsetSlot = buf.writerIndex();
/* 264 */     buf.writeIntLE(0);
/* 265 */     int cameraOffsetSlot = buf.writerIndex();
/* 266 */     buf.writeIntLE(0);
/* 267 */     int forcesOffsetSlot = buf.writerIndex();
/* 268 */     buf.writeIntLE(0);
/*     */     
/* 270 */     int varBlockStart = buf.writerIndex();
/* 271 */     if (this.effects != null) {
/* 272 */       buf.setIntLE(effectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 273 */       this.effects.serialize(buf);
/*     */     } else {
/* 275 */       buf.setIntLE(effectsOffsetSlot, -1);
/*     */     } 
/* 277 */     if (this.settings != null)
/* 278 */     { buf.setIntLE(settingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 279 */       if (this.settings.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Settings", this.settings.size(), 4096000);  VarInt.write(buf, this.settings.size()); for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); ((InteractionSettings)e.getValue()).serialize(buf); }
/*     */        }
/* 281 */     else { buf.setIntLE(settingsOffsetSlot, -1); }
/*     */     
/* 283 */     if (this.rules != null) {
/* 284 */       buf.setIntLE(rulesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 285 */       this.rules.serialize(buf);
/*     */     } else {
/* 287 */       buf.setIntLE(rulesOffsetSlot, -1);
/*     */     } 
/* 289 */     if (this.tags != null) {
/* 290 */       buf.setIntLE(tagsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 291 */       if (this.tags.length > 4096000) throw ProtocolException.arrayTooLong("Tags", this.tags.length, 4096000);  VarInt.write(buf, this.tags.length); for (int item : this.tags) buf.writeIntLE(item); 
/*     */     } else {
/* 293 */       buf.setIntLE(tagsOffsetSlot, -1);
/*     */     } 
/* 295 */     if (this.camera != null) {
/* 296 */       buf.setIntLE(cameraOffsetSlot, buf.writerIndex() - varBlockStart);
/* 297 */       this.camera.serialize(buf);
/*     */     } else {
/* 299 */       buf.setIntLE(cameraOffsetSlot, -1);
/*     */     } 
/* 301 */     if (this.forces != null) {
/* 302 */       buf.setIntLE(forcesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 303 */       if (this.forces.length > 4096000) throw ProtocolException.arrayTooLong("Forces", this.forces.length, 4096000);  VarInt.write(buf, this.forces.length); for (AppliedForce item : this.forces) item.serialize(buf); 
/*     */     } else {
/* 305 */       buf.setIntLE(forcesOffsetSlot, -1);
/*     */     } 
/* 307 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 313 */     int size = 104;
/* 314 */     if (this.effects != null) size += this.effects.computeSize(); 
/* 315 */     if (this.settings != null) size += VarInt.size(this.settings.size()) + this.settings.size() * 2; 
/* 316 */     if (this.rules != null) size += this.rules.computeSize(); 
/* 317 */     if (this.tags != null) size += VarInt.size(this.tags.length) + this.tags.length * 4; 
/* 318 */     if (this.camera != null) size += this.camera.computeSize(); 
/* 319 */     if (this.forces != null) size += VarInt.size(this.forces.length) + this.forces.length * 18;
/*     */     
/* 321 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 325 */     if (buffer.readableBytes() - offset < 104) {
/* 326 */       return ValidationResult.error("Buffer too small: expected at least 104 bytes");
/*     */     }
/*     */     
/* 329 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 332 */     if ((nullBits & 0x1) != 0) {
/* 333 */       int effectsOffset = buffer.getIntLE(offset + 80);
/* 334 */       if (effectsOffset < 0) {
/* 335 */         return ValidationResult.error("Invalid offset for Effects");
/*     */       }
/* 337 */       int pos = offset + 104 + effectsOffset;
/* 338 */       if (pos >= buffer.writerIndex()) {
/* 339 */         return ValidationResult.error("Offset out of bounds for Effects");
/*     */       }
/* 341 */       ValidationResult effectsResult = InteractionEffects.validateStructure(buffer, pos);
/* 342 */       if (!effectsResult.isValid()) {
/* 343 */         return ValidationResult.error("Invalid Effects: " + effectsResult.error());
/*     */       }
/* 345 */       pos += InteractionEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 348 */     if ((nullBits & 0x2) != 0) {
/* 349 */       int settingsOffset = buffer.getIntLE(offset + 84);
/* 350 */       if (settingsOffset < 0) {
/* 351 */         return ValidationResult.error("Invalid offset for Settings");
/*     */       }
/* 353 */       int pos = offset + 104 + settingsOffset;
/* 354 */       if (pos >= buffer.writerIndex()) {
/* 355 */         return ValidationResult.error("Offset out of bounds for Settings");
/*     */       }
/* 357 */       int settingsCount = VarInt.peek(buffer, pos);
/* 358 */       if (settingsCount < 0) {
/* 359 */         return ValidationResult.error("Invalid dictionary count for Settings");
/*     */       }
/* 361 */       if (settingsCount > 4096000) {
/* 362 */         return ValidationResult.error("Settings exceeds max length 4096000");
/*     */       }
/* 364 */       pos += VarInt.length(buffer, pos);
/* 365 */       for (int i = 0; i < settingsCount; i++) {
/* 366 */         pos++;
/*     */         
/* 368 */         pos++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 373 */     if ((nullBits & 0x4) != 0) {
/* 374 */       int rulesOffset = buffer.getIntLE(offset + 88);
/* 375 */       if (rulesOffset < 0) {
/* 376 */         return ValidationResult.error("Invalid offset for Rules");
/*     */       }
/* 378 */       int pos = offset + 104 + rulesOffset;
/* 379 */       if (pos >= buffer.writerIndex()) {
/* 380 */         return ValidationResult.error("Offset out of bounds for Rules");
/*     */       }
/* 382 */       ValidationResult rulesResult = InteractionRules.validateStructure(buffer, pos);
/* 383 */       if (!rulesResult.isValid()) {
/* 384 */         return ValidationResult.error("Invalid Rules: " + rulesResult.error());
/*     */       }
/* 386 */       pos += InteractionRules.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 389 */     if ((nullBits & 0x8) != 0) {
/* 390 */       int tagsOffset = buffer.getIntLE(offset + 92);
/* 391 */       if (tagsOffset < 0) {
/* 392 */         return ValidationResult.error("Invalid offset for Tags");
/*     */       }
/* 394 */       int pos = offset + 104 + tagsOffset;
/* 395 */       if (pos >= buffer.writerIndex()) {
/* 396 */         return ValidationResult.error("Offset out of bounds for Tags");
/*     */       }
/* 398 */       int tagsCount = VarInt.peek(buffer, pos);
/* 399 */       if (tagsCount < 0) {
/* 400 */         return ValidationResult.error("Invalid array count for Tags");
/*     */       }
/* 402 */       if (tagsCount > 4096000) {
/* 403 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 405 */       pos += VarInt.length(buffer, pos);
/* 406 */       pos += tagsCount * 4;
/* 407 */       if (pos > buffer.writerIndex()) {
/* 408 */         return ValidationResult.error("Buffer overflow reading Tags");
/*     */       }
/*     */     } 
/*     */     
/* 412 */     if ((nullBits & 0x10) != 0) {
/* 413 */       int cameraOffset = buffer.getIntLE(offset + 96);
/* 414 */       if (cameraOffset < 0) {
/* 415 */         return ValidationResult.error("Invalid offset for Camera");
/*     */       }
/* 417 */       int pos = offset + 104 + cameraOffset;
/* 418 */       if (pos >= buffer.writerIndex()) {
/* 419 */         return ValidationResult.error("Offset out of bounds for Camera");
/*     */       }
/* 421 */       ValidationResult cameraResult = InteractionCameraSettings.validateStructure(buffer, pos);
/* 422 */       if (!cameraResult.isValid()) {
/* 423 */         return ValidationResult.error("Invalid Camera: " + cameraResult.error());
/*     */       }
/* 425 */       pos += InteractionCameraSettings.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 428 */     if ((nullBits & 0x40) != 0) {
/* 429 */       int forcesOffset = buffer.getIntLE(offset + 100);
/* 430 */       if (forcesOffset < 0) {
/* 431 */         return ValidationResult.error("Invalid offset for Forces");
/*     */       }
/* 433 */       int pos = offset + 104 + forcesOffset;
/* 434 */       if (pos >= buffer.writerIndex()) {
/* 435 */         return ValidationResult.error("Offset out of bounds for Forces");
/*     */       }
/* 437 */       int forcesCount = VarInt.peek(buffer, pos);
/* 438 */       if (forcesCount < 0) {
/* 439 */         return ValidationResult.error("Invalid array count for Forces");
/*     */       }
/* 441 */       if (forcesCount > 4096000) {
/* 442 */         return ValidationResult.error("Forces exceeds max length 4096000");
/*     */       }
/* 444 */       pos += VarInt.length(buffer, pos);
/* 445 */       pos += forcesCount * 18;
/* 446 */       if (pos > buffer.writerIndex()) {
/* 447 */         return ValidationResult.error("Buffer overflow reading Forces");
/*     */       }
/*     */     } 
/* 450 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ApplyForceInteraction clone() {
/* 454 */     ApplyForceInteraction copy = new ApplyForceInteraction();
/* 455 */     copy.waitForDataFrom = this.waitForDataFrom;
/* 456 */     copy.effects = (this.effects != null) ? this.effects.clone() : null;
/* 457 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 458 */     copy.runTime = this.runTime;
/* 459 */     copy.cancelOnItemChange = this.cancelOnItemChange;
/* 460 */     if (this.settings != null) {
/* 461 */       Map<GameMode, InteractionSettings> m = new HashMap<>();
/* 462 */       for (Map.Entry<GameMode, InteractionSettings> e : this.settings.entrySet()) m.put(e.getKey(), ((InteractionSettings)e.getValue()).clone()); 
/* 463 */       copy.settings = m;
/*     */     } 
/* 465 */     copy.rules = (this.rules != null) ? this.rules.clone() : null;
/* 466 */     copy.tags = (this.tags != null) ? Arrays.copyOf(this.tags, this.tags.length) : null;
/* 467 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 468 */     copy.next = this.next;
/* 469 */     copy.failed = this.failed;
/* 470 */     copy.velocityConfig = (this.velocityConfig != null) ? this.velocityConfig.clone() : null;
/* 471 */     copy.changeVelocityType = this.changeVelocityType;
/* 472 */     copy.forces = (this.forces != null) ? (AppliedForce[])Arrays.<AppliedForce>stream(this.forces).map(e -> e.clone()).toArray(x$0 -> new AppliedForce[x$0]) : null;
/* 473 */     copy.duration = this.duration;
/* 474 */     copy.verticalClamp = (this.verticalClamp != null) ? this.verticalClamp.clone() : null;
/* 475 */     copy.waitForGround = this.waitForGround;
/* 476 */     copy.waitForCollision = this.waitForCollision;
/* 477 */     copy.groundCheckDelay = this.groundCheckDelay;
/* 478 */     copy.collisionCheckDelay = this.collisionCheckDelay;
/* 479 */     copy.groundNext = this.groundNext;
/* 480 */     copy.collisionNext = this.collisionNext;
/* 481 */     copy.raycastDistance = this.raycastDistance;
/* 482 */     copy.raycastHeightOffset = this.raycastHeightOffset;
/* 483 */     copy.raycastMode = this.raycastMode;
/* 484 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ApplyForceInteraction other;
/* 490 */     if (this == obj) return true; 
/* 491 */     if (obj instanceof ApplyForceInteraction) { other = (ApplyForceInteraction)obj; } else { return false; }
/* 492 */      return (Objects.equals(this.waitForDataFrom, other.waitForDataFrom) && Objects.equals(this.effects, other.effects) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.runTime == other.runTime && this.cancelOnItemChange == other.cancelOnItemChange && Objects.equals(this.settings, other.settings) && Objects.equals(this.rules, other.rules) && Arrays.equals(this.tags, other.tags) && Objects.equals(this.camera, other.camera) && this.next == other.next && this.failed == other.failed && Objects.equals(this.velocityConfig, other.velocityConfig) && Objects.equals(this.changeVelocityType, other.changeVelocityType) && Arrays.equals((Object[])this.forces, (Object[])other.forces) && this.duration == other.duration && Objects.equals(this.verticalClamp, other.verticalClamp) && this.waitForGround == other.waitForGround && this.waitForCollision == other.waitForCollision && this.groundCheckDelay == other.groundCheckDelay && this.collisionCheckDelay == other.collisionCheckDelay && this.groundNext == other.groundNext && this.collisionNext == other.collisionNext && this.raycastDistance == other.raycastDistance && this.raycastHeightOffset == other.raycastHeightOffset && Objects.equals(this.raycastMode, other.raycastMode));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 497 */     int result = 1;
/* 498 */     result = 31 * result + Objects.hashCode(this.waitForDataFrom);
/* 499 */     result = 31 * result + Objects.hashCode(this.effects);
/* 500 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 501 */     result = 31 * result + Float.hashCode(this.runTime);
/* 502 */     result = 31 * result + Boolean.hashCode(this.cancelOnItemChange);
/* 503 */     result = 31 * result + Objects.hashCode(this.settings);
/* 504 */     result = 31 * result + Objects.hashCode(this.rules);
/* 505 */     result = 31 * result + Arrays.hashCode(this.tags);
/* 506 */     result = 31 * result + Objects.hashCode(this.camera);
/* 507 */     result = 31 * result + Integer.hashCode(this.next);
/* 508 */     result = 31 * result + Integer.hashCode(this.failed);
/* 509 */     result = 31 * result + Objects.hashCode(this.velocityConfig);
/* 510 */     result = 31 * result + Objects.hashCode(this.changeVelocityType);
/* 511 */     result = 31 * result + Arrays.hashCode((Object[])this.forces);
/* 512 */     result = 31 * result + Float.hashCode(this.duration);
/* 513 */     result = 31 * result + Objects.hashCode(this.verticalClamp);
/* 514 */     result = 31 * result + Boolean.hashCode(this.waitForGround);
/* 515 */     result = 31 * result + Boolean.hashCode(this.waitForCollision);
/* 516 */     result = 31 * result + Float.hashCode(this.groundCheckDelay);
/* 517 */     result = 31 * result + Float.hashCode(this.collisionCheckDelay);
/* 518 */     result = 31 * result + Integer.hashCode(this.groundNext);
/* 519 */     result = 31 * result + Integer.hashCode(this.collisionNext);
/* 520 */     result = 31 * result + Float.hashCode(this.raycastDistance);
/* 521 */     result = 31 * result + Float.hashCode(this.raycastHeightOffset);
/* 522 */     result = 31 * result + Objects.hashCode(this.raycastMode);
/* 523 */     return result;
/*     */   }
/*     */   
/*     */   public ApplyForceInteraction() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ApplyForceInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */