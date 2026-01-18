/*     */ package com.hypixel.hytale.protocol;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class InteractionSyncData {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 2;
/*     */   public static final int FIXED_BLOCK_SIZE = 157;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 165;
/*     */   public static final int MAX_SIZE = 237568175;
/*     */   @Nonnull
/*  21 */   public InteractionState state = InteractionState.Finished;
/*     */   public float progress;
/*     */   public int operationCounter;
/*     */   public int rootInteraction;
/*     */   public int totalForks;
/*     */   public int entityId;
/*  27 */   public int enteredRootInteraction = Integer.MIN_VALUE; @Nullable
/*     */   public BlockPosition blockPosition; @Nonnull
/*  29 */   public BlockFace blockFace = BlockFace.None; @Nullable
/*     */   public BlockRotation blockRotation;
/*  31 */   public int placedBlockId = Integer.MIN_VALUE;
/*  32 */   public float chargeValue = -1.0F; @Nullable
/*     */   public Map<InteractionType, Integer> forkCounts;
/*  34 */   public int chainingIndex = -1; @Nullable public SelectedHitEntity[] hitEntities; @Nullable public Position attackerPos; @Nullable
/*  35 */   public Direction attackerRot; public int flagIndex = -1;
/*     */   @Nullable
/*     */   public Position raycastHit;
/*     */   public float raycastDistance;
/*     */   @Nullable
/*     */   public Vector3f raycastNormal;
/*     */   @Nonnull
/*  42 */   public MovementDirection movementDirection = MovementDirection.None; @Nonnull
/*  43 */   public ApplyForceState applyForceState = ApplyForceState.Waiting; public int nextLabel;
/*     */   @Nullable
/*  45 */   public UUID generatedUUID = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InteractionSyncData(@Nonnull InteractionState state, float progress, int operationCounter, int rootInteraction, int totalForks, int entityId, int enteredRootInteraction, @Nullable BlockPosition blockPosition, @Nonnull BlockFace blockFace, @Nullable BlockRotation blockRotation, int placedBlockId, float chargeValue, @Nullable Map<InteractionType, Integer> forkCounts, int chainingIndex, int flagIndex, @Nullable SelectedHitEntity[] hitEntities, @Nullable Position attackerPos, @Nullable Direction attackerRot, @Nullable Position raycastHit, float raycastDistance, @Nullable Vector3f raycastNormal, @Nonnull MovementDirection movementDirection, @Nonnull ApplyForceState applyForceState, int nextLabel, @Nullable UUID generatedUUID) {
/*  51 */     this.state = state;
/*  52 */     this.progress = progress;
/*  53 */     this.operationCounter = operationCounter;
/*  54 */     this.rootInteraction = rootInteraction;
/*  55 */     this.totalForks = totalForks;
/*  56 */     this.entityId = entityId;
/*  57 */     this.enteredRootInteraction = enteredRootInteraction;
/*  58 */     this.blockPosition = blockPosition;
/*  59 */     this.blockFace = blockFace;
/*  60 */     this.blockRotation = blockRotation;
/*  61 */     this.placedBlockId = placedBlockId;
/*  62 */     this.chargeValue = chargeValue;
/*  63 */     this.forkCounts = forkCounts;
/*  64 */     this.chainingIndex = chainingIndex;
/*  65 */     this.flagIndex = flagIndex;
/*  66 */     this.hitEntities = hitEntities;
/*  67 */     this.attackerPos = attackerPos;
/*  68 */     this.attackerRot = attackerRot;
/*  69 */     this.raycastHit = raycastHit;
/*  70 */     this.raycastDistance = raycastDistance;
/*  71 */     this.raycastNormal = raycastNormal;
/*  72 */     this.movementDirection = movementDirection;
/*  73 */     this.applyForceState = applyForceState;
/*  74 */     this.nextLabel = nextLabel;
/*  75 */     this.generatedUUID = generatedUUID;
/*     */   }
/*     */   
/*     */   public InteractionSyncData(@Nonnull InteractionSyncData other) {
/*  79 */     this.state = other.state;
/*  80 */     this.progress = other.progress;
/*  81 */     this.operationCounter = other.operationCounter;
/*  82 */     this.rootInteraction = other.rootInteraction;
/*  83 */     this.totalForks = other.totalForks;
/*  84 */     this.entityId = other.entityId;
/*  85 */     this.enteredRootInteraction = other.enteredRootInteraction;
/*  86 */     this.blockPosition = other.blockPosition;
/*  87 */     this.blockFace = other.blockFace;
/*  88 */     this.blockRotation = other.blockRotation;
/*  89 */     this.placedBlockId = other.placedBlockId;
/*  90 */     this.chargeValue = other.chargeValue;
/*  91 */     this.forkCounts = other.forkCounts;
/*  92 */     this.chainingIndex = other.chainingIndex;
/*  93 */     this.flagIndex = other.flagIndex;
/*  94 */     this.hitEntities = other.hitEntities;
/*  95 */     this.attackerPos = other.attackerPos;
/*  96 */     this.attackerRot = other.attackerRot;
/*  97 */     this.raycastHit = other.raycastHit;
/*  98 */     this.raycastDistance = other.raycastDistance;
/*  99 */     this.raycastNormal = other.raycastNormal;
/* 100 */     this.movementDirection = other.movementDirection;
/* 101 */     this.applyForceState = other.applyForceState;
/* 102 */     this.nextLabel = other.nextLabel;
/* 103 */     this.generatedUUID = other.generatedUUID;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static InteractionSyncData deserialize(@Nonnull ByteBuf buf, int offset) {
/* 108 */     InteractionSyncData obj = new InteractionSyncData();
/* 109 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/* 110 */     obj.state = InteractionState.fromValue(buf.getByte(offset + 2));
/* 111 */     obj.progress = buf.getFloatLE(offset + 3);
/* 112 */     obj.operationCounter = buf.getIntLE(offset + 7);
/* 113 */     obj.rootInteraction = buf.getIntLE(offset + 11);
/* 114 */     obj.totalForks = buf.getIntLE(offset + 15);
/* 115 */     obj.entityId = buf.getIntLE(offset + 19);
/* 116 */     obj.enteredRootInteraction = buf.getIntLE(offset + 23);
/* 117 */     if ((nullBits[0] & 0x1) != 0) obj.blockPosition = BlockPosition.deserialize(buf, offset + 27); 
/* 118 */     obj.blockFace = BlockFace.fromValue(buf.getByte(offset + 39));
/* 119 */     if ((nullBits[0] & 0x2) != 0) obj.blockRotation = BlockRotation.deserialize(buf, offset + 40); 
/* 120 */     obj.placedBlockId = buf.getIntLE(offset + 43);
/* 121 */     obj.chargeValue = buf.getFloatLE(offset + 47);
/* 122 */     obj.chainingIndex = buf.getIntLE(offset + 51);
/* 123 */     obj.flagIndex = buf.getIntLE(offset + 55);
/* 124 */     if ((nullBits[0] & 0x10) != 0) obj.attackerPos = Position.deserialize(buf, offset + 59); 
/* 125 */     if ((nullBits[0] & 0x20) != 0) obj.attackerRot = Direction.deserialize(buf, offset + 83); 
/* 126 */     if ((nullBits[0] & 0x40) != 0) obj.raycastHit = Position.deserialize(buf, offset + 95); 
/* 127 */     obj.raycastDistance = buf.getFloatLE(offset + 119);
/* 128 */     if ((nullBits[0] & 0x80) != 0) obj.raycastNormal = Vector3f.deserialize(buf, offset + 123); 
/* 129 */     obj.movementDirection = MovementDirection.fromValue(buf.getByte(offset + 135));
/* 130 */     obj.applyForceState = ApplyForceState.fromValue(buf.getByte(offset + 136));
/* 131 */     obj.nextLabel = buf.getIntLE(offset + 137);
/* 132 */     if ((nullBits[1] & 0x1) != 0) obj.generatedUUID = PacketIO.readUUID(buf, offset + 141);
/*     */     
/* 134 */     if ((nullBits[0] & 0x4) != 0) {
/* 135 */       int varPos0 = offset + 165 + buf.getIntLE(offset + 157);
/* 136 */       int forkCountsCount = VarInt.peek(buf, varPos0);
/* 137 */       if (forkCountsCount < 0) throw ProtocolException.negativeLength("ForkCounts", forkCountsCount); 
/* 138 */       if (forkCountsCount > 4096000) throw ProtocolException.dictionaryTooLarge("ForkCounts", forkCountsCount, 4096000); 
/* 139 */       int varIntLen = VarInt.length(buf, varPos0);
/* 140 */       obj.forkCounts = new HashMap<>(forkCountsCount);
/* 141 */       int dictPos = varPos0 + varIntLen;
/* 142 */       for (int i = 0; i < forkCountsCount; i++) {
/* 143 */         InteractionType key = InteractionType.fromValue(buf.getByte(dictPos)); dictPos++;
/* 144 */         int val = buf.getIntLE(dictPos); dictPos += 4;
/* 145 */         if (obj.forkCounts.put(key, Integer.valueOf(val)) != null)
/* 146 */           throw ProtocolException.duplicateKey("forkCounts", key); 
/*     */       } 
/*     */     } 
/* 149 */     if ((nullBits[0] & 0x8) != 0) {
/* 150 */       int varPos1 = offset + 165 + buf.getIntLE(offset + 161);
/* 151 */       int hitEntitiesCount = VarInt.peek(buf, varPos1);
/* 152 */       if (hitEntitiesCount < 0) throw ProtocolException.negativeLength("HitEntities", hitEntitiesCount); 
/* 153 */       if (hitEntitiesCount > 4096000) throw ProtocolException.arrayTooLong("HitEntities", hitEntitiesCount, 4096000); 
/* 154 */       int varIntLen = VarInt.length(buf, varPos1);
/* 155 */       if ((varPos1 + varIntLen) + hitEntitiesCount * 53L > buf.readableBytes())
/* 156 */         throw ProtocolException.bufferTooSmall("HitEntities", varPos1 + varIntLen + hitEntitiesCount * 53, buf.readableBytes()); 
/* 157 */       obj.hitEntities = new SelectedHitEntity[hitEntitiesCount];
/* 158 */       int elemPos = varPos1 + varIntLen;
/* 159 */       for (int i = 0; i < hitEntitiesCount; i++) {
/* 160 */         obj.hitEntities[i] = SelectedHitEntity.deserialize(buf, elemPos);
/* 161 */         elemPos += SelectedHitEntity.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/* 165 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 169 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/* 170 */     int maxEnd = 165;
/* 171 */     if ((nullBits[0] & 0x4) != 0) {
/* 172 */       int fieldOffset0 = buf.getIntLE(offset + 157);
/* 173 */       int pos0 = offset + 165 + fieldOffset0;
/* 174 */       int dictLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0);
/* 175 */       for (int i = 0; i < dictLen; ) { pos0++; pos0 += 4; i++; }
/* 176 */        if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 178 */     if ((nullBits[0] & 0x8) != 0) {
/* 179 */       int fieldOffset1 = buf.getIntLE(offset + 161);
/* 180 */       int pos1 = offset + 165 + fieldOffset1;
/* 181 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 182 */       for (int i = 0; i < arrLen; ) { pos1 += SelectedHitEntity.computeBytesConsumed(buf, pos1); i++; }
/* 183 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 185 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 190 */     int startPos = buf.writerIndex();
/* 191 */     byte[] nullBits = new byte[2];
/* 192 */     if (this.blockPosition != null) nullBits[0] = (byte)(nullBits[0] | 0x1); 
/* 193 */     if (this.blockRotation != null) nullBits[0] = (byte)(nullBits[0] | 0x2); 
/* 194 */     if (this.forkCounts != null) nullBits[0] = (byte)(nullBits[0] | 0x4); 
/* 195 */     if (this.hitEntities != null) nullBits[0] = (byte)(nullBits[0] | 0x8); 
/* 196 */     if (this.attackerPos != null) nullBits[0] = (byte)(nullBits[0] | 0x10); 
/* 197 */     if (this.attackerRot != null) nullBits[0] = (byte)(nullBits[0] | 0x20); 
/* 198 */     if (this.raycastHit != null) nullBits[0] = (byte)(nullBits[0] | 0x40); 
/* 199 */     if (this.raycastNormal != null) nullBits[0] = (byte)(nullBits[0] | 0x80); 
/* 200 */     if (this.generatedUUID != null) nullBits[1] = (byte)(nullBits[1] | 0x1); 
/* 201 */     buf.writeBytes(nullBits);
/*     */     
/* 203 */     buf.writeByte(this.state.getValue());
/* 204 */     buf.writeFloatLE(this.progress);
/* 205 */     buf.writeIntLE(this.operationCounter);
/* 206 */     buf.writeIntLE(this.rootInteraction);
/* 207 */     buf.writeIntLE(this.totalForks);
/* 208 */     buf.writeIntLE(this.entityId);
/* 209 */     buf.writeIntLE(this.enteredRootInteraction);
/* 210 */     if (this.blockPosition != null) { this.blockPosition.serialize(buf); } else { buf.writeZero(12); }
/* 211 */      buf.writeByte(this.blockFace.getValue());
/* 212 */     if (this.blockRotation != null) { this.blockRotation.serialize(buf); } else { buf.writeZero(3); }
/* 213 */      buf.writeIntLE(this.placedBlockId);
/* 214 */     buf.writeFloatLE(this.chargeValue);
/* 215 */     buf.writeIntLE(this.chainingIndex);
/* 216 */     buf.writeIntLE(this.flagIndex);
/* 217 */     if (this.attackerPos != null) { this.attackerPos.serialize(buf); } else { buf.writeZero(24); }
/* 218 */      if (this.attackerRot != null) { this.attackerRot.serialize(buf); } else { buf.writeZero(12); }
/* 219 */      if (this.raycastHit != null) { this.raycastHit.serialize(buf); } else { buf.writeZero(24); }
/* 220 */      buf.writeFloatLE(this.raycastDistance);
/* 221 */     if (this.raycastNormal != null) { this.raycastNormal.serialize(buf); } else { buf.writeZero(12); }
/* 222 */      buf.writeByte(this.movementDirection.getValue());
/* 223 */     buf.writeByte(this.applyForceState.getValue());
/* 224 */     buf.writeIntLE(this.nextLabel);
/* 225 */     if (this.generatedUUID != null) { PacketIO.writeUUID(buf, this.generatedUUID); } else { buf.writeZero(16); }
/*     */     
/* 227 */     int forkCountsOffsetSlot = buf.writerIndex();
/* 228 */     buf.writeIntLE(0);
/* 229 */     int hitEntitiesOffsetSlot = buf.writerIndex();
/* 230 */     buf.writeIntLE(0);
/*     */     
/* 232 */     int varBlockStart = buf.writerIndex();
/* 233 */     if (this.forkCounts != null)
/* 234 */     { buf.setIntLE(forkCountsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 235 */       if (this.forkCounts.size() > 4096000) throw ProtocolException.dictionaryTooLarge("ForkCounts", this.forkCounts.size(), 4096000);  VarInt.write(buf, this.forkCounts.size()); for (Map.Entry<InteractionType, Integer> e : this.forkCounts.entrySet()) { buf.writeByte(((InteractionType)e.getKey()).getValue()); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*     */        }
/* 237 */     else { buf.setIntLE(forkCountsOffsetSlot, -1); }
/*     */     
/* 239 */     if (this.hitEntities != null) {
/* 240 */       buf.setIntLE(hitEntitiesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 241 */       if (this.hitEntities.length > 4096000) throw ProtocolException.arrayTooLong("HitEntities", this.hitEntities.length, 4096000);  VarInt.write(buf, this.hitEntities.length); for (SelectedHitEntity item : this.hitEntities) item.serialize(buf); 
/*     */     } else {
/* 243 */       buf.setIntLE(hitEntitiesOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 249 */     int size = 165;
/* 250 */     if (this.forkCounts != null) size += VarInt.size(this.forkCounts.size()) + this.forkCounts.size() * 5; 
/* 251 */     if (this.hitEntities != null) size += VarInt.size(this.hitEntities.length) + this.hitEntities.length * 53;
/*     */     
/* 253 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 257 */     if (buffer.readableBytes() - offset < 165) {
/* 258 */       return ValidationResult.error("Buffer too small: expected at least 165 bytes");
/*     */     }
/*     */     
/* 261 */     byte[] nullBits = PacketIO.readBytes(buffer, offset, 2);
/*     */     
/* 263 */     if ((nullBits[0] & 0x4) != 0) {
/* 264 */       int forkCountsOffset = buffer.getIntLE(offset + 157);
/* 265 */       if (forkCountsOffset < 0) {
/* 266 */         return ValidationResult.error("Invalid offset for ForkCounts");
/*     */       }
/* 268 */       int pos = offset + 165 + forkCountsOffset;
/* 269 */       if (pos >= buffer.writerIndex()) {
/* 270 */         return ValidationResult.error("Offset out of bounds for ForkCounts");
/*     */       }
/* 272 */       int forkCountsCount = VarInt.peek(buffer, pos);
/* 273 */       if (forkCountsCount < 0) {
/* 274 */         return ValidationResult.error("Invalid dictionary count for ForkCounts");
/*     */       }
/* 276 */       if (forkCountsCount > 4096000) {
/* 277 */         return ValidationResult.error("ForkCounts exceeds max length 4096000");
/*     */       }
/* 279 */       pos += VarInt.length(buffer, pos);
/* 280 */       for (int i = 0; i < forkCountsCount; i++) {
/* 281 */         pos++;
/*     */         
/* 283 */         pos += 4;
/* 284 */         if (pos > buffer.writerIndex()) {
/* 285 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 290 */     if ((nullBits[0] & 0x8) != 0) {
/* 291 */       int hitEntitiesOffset = buffer.getIntLE(offset + 161);
/* 292 */       if (hitEntitiesOffset < 0) {
/* 293 */         return ValidationResult.error("Invalid offset for HitEntities");
/*     */       }
/* 295 */       int pos = offset + 165 + hitEntitiesOffset;
/* 296 */       if (pos >= buffer.writerIndex()) {
/* 297 */         return ValidationResult.error("Offset out of bounds for HitEntities");
/*     */       }
/* 299 */       int hitEntitiesCount = VarInt.peek(buffer, pos);
/* 300 */       if (hitEntitiesCount < 0) {
/* 301 */         return ValidationResult.error("Invalid array count for HitEntities");
/*     */       }
/* 303 */       if (hitEntitiesCount > 4096000) {
/* 304 */         return ValidationResult.error("HitEntities exceeds max length 4096000");
/*     */       }
/* 306 */       pos += VarInt.length(buffer, pos);
/* 307 */       pos += hitEntitiesCount * 53;
/* 308 */       if (pos > buffer.writerIndex()) {
/* 309 */         return ValidationResult.error("Buffer overflow reading HitEntities");
/*     */       }
/*     */     } 
/* 312 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public InteractionSyncData clone() {
/* 316 */     InteractionSyncData copy = new InteractionSyncData();
/* 317 */     copy.state = this.state;
/* 318 */     copy.progress = this.progress;
/* 319 */     copy.operationCounter = this.operationCounter;
/* 320 */     copy.rootInteraction = this.rootInteraction;
/* 321 */     copy.totalForks = this.totalForks;
/* 322 */     copy.entityId = this.entityId;
/* 323 */     copy.enteredRootInteraction = this.enteredRootInteraction;
/* 324 */     copy.blockPosition = (this.blockPosition != null) ? this.blockPosition.clone() : null;
/* 325 */     copy.blockFace = this.blockFace;
/* 326 */     copy.blockRotation = (this.blockRotation != null) ? this.blockRotation.clone() : null;
/* 327 */     copy.placedBlockId = this.placedBlockId;
/* 328 */     copy.chargeValue = this.chargeValue;
/* 329 */     copy.forkCounts = (this.forkCounts != null) ? new HashMap<>(this.forkCounts) : null;
/* 330 */     copy.chainingIndex = this.chainingIndex;
/* 331 */     copy.flagIndex = this.flagIndex;
/* 332 */     copy.hitEntities = (this.hitEntities != null) ? (SelectedHitEntity[])Arrays.<SelectedHitEntity>stream(this.hitEntities).map(e -> e.clone()).toArray(x$0 -> new SelectedHitEntity[x$0]) : null;
/* 333 */     copy.attackerPos = (this.attackerPos != null) ? this.attackerPos.clone() : null;
/* 334 */     copy.attackerRot = (this.attackerRot != null) ? this.attackerRot.clone() : null;
/* 335 */     copy.raycastHit = (this.raycastHit != null) ? this.raycastHit.clone() : null;
/* 336 */     copy.raycastDistance = this.raycastDistance;
/* 337 */     copy.raycastNormal = (this.raycastNormal != null) ? this.raycastNormal.clone() : null;
/* 338 */     copy.movementDirection = this.movementDirection;
/* 339 */     copy.applyForceState = this.applyForceState;
/* 340 */     copy.nextLabel = this.nextLabel;
/* 341 */     copy.generatedUUID = this.generatedUUID;
/* 342 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     InteractionSyncData other;
/* 348 */     if (this == obj) return true; 
/* 349 */     if (obj instanceof InteractionSyncData) { other = (InteractionSyncData)obj; } else { return false; }
/* 350 */      return (Objects.equals(this.state, other.state) && this.progress == other.progress && this.operationCounter == other.operationCounter && this.rootInteraction == other.rootInteraction && this.totalForks == other.totalForks && this.entityId == other.entityId && this.enteredRootInteraction == other.enteredRootInteraction && Objects.equals(this.blockPosition, other.blockPosition) && Objects.equals(this.blockFace, other.blockFace) && Objects.equals(this.blockRotation, other.blockRotation) && this.placedBlockId == other.placedBlockId && this.chargeValue == other.chargeValue && Objects.equals(this.forkCounts, other.forkCounts) && this.chainingIndex == other.chainingIndex && this.flagIndex == other.flagIndex && Arrays.equals((Object[])this.hitEntities, (Object[])other.hitEntities) && Objects.equals(this.attackerPos, other.attackerPos) && Objects.equals(this.attackerRot, other.attackerRot) && Objects.equals(this.raycastHit, other.raycastHit) && this.raycastDistance == other.raycastDistance && Objects.equals(this.raycastNormal, other.raycastNormal) && Objects.equals(this.movementDirection, other.movementDirection) && Objects.equals(this.applyForceState, other.applyForceState) && this.nextLabel == other.nextLabel && Objects.equals(this.generatedUUID, other.generatedUUID));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 355 */     int result = 1;
/* 356 */     result = 31 * result + Objects.hashCode(this.state);
/* 357 */     result = 31 * result + Float.hashCode(this.progress);
/* 358 */     result = 31 * result + Integer.hashCode(this.operationCounter);
/* 359 */     result = 31 * result + Integer.hashCode(this.rootInteraction);
/* 360 */     result = 31 * result + Integer.hashCode(this.totalForks);
/* 361 */     result = 31 * result + Integer.hashCode(this.entityId);
/* 362 */     result = 31 * result + Integer.hashCode(this.enteredRootInteraction);
/* 363 */     result = 31 * result + Objects.hashCode(this.blockPosition);
/* 364 */     result = 31 * result + Objects.hashCode(this.blockFace);
/* 365 */     result = 31 * result + Objects.hashCode(this.blockRotation);
/* 366 */     result = 31 * result + Integer.hashCode(this.placedBlockId);
/* 367 */     result = 31 * result + Float.hashCode(this.chargeValue);
/* 368 */     result = 31 * result + Objects.hashCode(this.forkCounts);
/* 369 */     result = 31 * result + Integer.hashCode(this.chainingIndex);
/* 370 */     result = 31 * result + Integer.hashCode(this.flagIndex);
/* 371 */     result = 31 * result + Arrays.hashCode((Object[])this.hitEntities);
/* 372 */     result = 31 * result + Objects.hashCode(this.attackerPos);
/* 373 */     result = 31 * result + Objects.hashCode(this.attackerRot);
/* 374 */     result = 31 * result + Objects.hashCode(this.raycastHit);
/* 375 */     result = 31 * result + Float.hashCode(this.raycastDistance);
/* 376 */     result = 31 * result + Objects.hashCode(this.raycastNormal);
/* 377 */     result = 31 * result + Objects.hashCode(this.movementDirection);
/* 378 */     result = 31 * result + Objects.hashCode(this.applyForceState);
/* 379 */     result = 31 * result + Integer.hashCode(this.nextLabel);
/* 380 */     result = 31 * result + Objects.hashCode(this.generatedUUID);
/* 381 */     return result;
/*     */   }
/*     */   
/*     */   public InteractionSyncData() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\InteractionSyncData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */