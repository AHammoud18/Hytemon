/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerCameraSettings
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 154;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 154;
/*     */   public static final int MAX_SIZE = 154;
/*  20 */   public float positionLerpSpeed = 1.0F;
/*  21 */   public float rotationLerpSpeed = 1.0F;
/*     */   public float distance;
/*  23 */   public float speedModifier = 1.0F; public boolean allowPitchControls;
/*     */   public boolean displayCursor;
/*     */   public boolean displayReticle;
/*     */   @Nonnull
/*  27 */   public MouseInputTargetType mouseInputTargetType = MouseInputTargetType.Any; public boolean sendMouseMotion;
/*     */   public boolean skipCharacterPhysics;
/*     */   public boolean isFirstPerson = true;
/*     */   @Nonnull
/*  31 */   public MovementForceRotationType movementForceRotationType = MovementForceRotationType.AttachedToHead; @Nullable
/*     */   public Direction movementForceRotation; @Nonnull
/*  33 */   public AttachedToType attachedToType = AttachedToType.LocalPlayer; public int attachedToEntityId; public boolean eyeOffset; @Nullable
/*     */   public Position positionOffset;
/*     */   @Nonnull
/*  36 */   public PositionDistanceOffsetType positionDistanceOffsetType = PositionDistanceOffsetType.DistanceOffset; @Nullable
/*     */   public Direction rotationOffset;
/*     */   @Nonnull
/*  39 */   public PositionType positionType = PositionType.AttachedToPlusOffset; @Nullable
/*     */   public Position position; @Nonnull
/*  41 */   public RotationType rotationType = RotationType.AttachedToPlusOffset; @Nullable
/*     */   public Direction rotation; @Nonnull
/*  43 */   public CanMoveType canMoveType = CanMoveType.AttachedToLocalPlayer; @Nonnull
/*  44 */   public ApplyMovementType applyMovementType = ApplyMovementType.CharacterController; @Nullable
/*     */   public Vector3f movementMultiplier; @Nonnull
/*  46 */   public ApplyLookType applyLookType = ApplyLookType.LocalPlayerLookOrientation; @Nullable
/*     */   public Vector2f lookMultiplier; @Nonnull
/*  48 */   public MouseInputType mouseInputType = MouseInputType.LookAtTarget;
/*     */   
/*     */   @Nullable
/*     */   public Vector3f planeNormal;
/*     */ 
/*     */   
/*     */   public ServerCameraSettings(float positionLerpSpeed, float rotationLerpSpeed, float distance, float speedModifier, boolean allowPitchControls, boolean displayCursor, boolean displayReticle, @Nonnull MouseInputTargetType mouseInputTargetType, boolean sendMouseMotion, boolean skipCharacterPhysics, boolean isFirstPerson, @Nonnull MovementForceRotationType movementForceRotationType, @Nullable Direction movementForceRotation, @Nonnull AttachedToType attachedToType, int attachedToEntityId, boolean eyeOffset, @Nonnull PositionDistanceOffsetType positionDistanceOffsetType, @Nullable Position positionOffset, @Nullable Direction rotationOffset, @Nonnull PositionType positionType, @Nullable Position position, @Nonnull RotationType rotationType, @Nullable Direction rotation, @Nonnull CanMoveType canMoveType, @Nonnull ApplyMovementType applyMovementType, @Nullable Vector3f movementMultiplier, @Nonnull ApplyLookType applyLookType, @Nullable Vector2f lookMultiplier, @Nonnull MouseInputType mouseInputType, @Nullable Vector3f planeNormal) {
/*  55 */     this.positionLerpSpeed = positionLerpSpeed;
/*  56 */     this.rotationLerpSpeed = rotationLerpSpeed;
/*  57 */     this.distance = distance;
/*  58 */     this.speedModifier = speedModifier;
/*  59 */     this.allowPitchControls = allowPitchControls;
/*  60 */     this.displayCursor = displayCursor;
/*  61 */     this.displayReticle = displayReticle;
/*  62 */     this.mouseInputTargetType = mouseInputTargetType;
/*  63 */     this.sendMouseMotion = sendMouseMotion;
/*  64 */     this.skipCharacterPhysics = skipCharacterPhysics;
/*  65 */     this.isFirstPerson = isFirstPerson;
/*  66 */     this.movementForceRotationType = movementForceRotationType;
/*  67 */     this.movementForceRotation = movementForceRotation;
/*  68 */     this.attachedToType = attachedToType;
/*  69 */     this.attachedToEntityId = attachedToEntityId;
/*  70 */     this.eyeOffset = eyeOffset;
/*  71 */     this.positionDistanceOffsetType = positionDistanceOffsetType;
/*  72 */     this.positionOffset = positionOffset;
/*  73 */     this.rotationOffset = rotationOffset;
/*  74 */     this.positionType = positionType;
/*  75 */     this.position = position;
/*  76 */     this.rotationType = rotationType;
/*  77 */     this.rotation = rotation;
/*  78 */     this.canMoveType = canMoveType;
/*  79 */     this.applyMovementType = applyMovementType;
/*  80 */     this.movementMultiplier = movementMultiplier;
/*  81 */     this.applyLookType = applyLookType;
/*  82 */     this.lookMultiplier = lookMultiplier;
/*  83 */     this.mouseInputType = mouseInputType;
/*  84 */     this.planeNormal = planeNormal;
/*     */   }
/*     */   
/*     */   public ServerCameraSettings(@Nonnull ServerCameraSettings other) {
/*  88 */     this.positionLerpSpeed = other.positionLerpSpeed;
/*  89 */     this.rotationLerpSpeed = other.rotationLerpSpeed;
/*  90 */     this.distance = other.distance;
/*  91 */     this.speedModifier = other.speedModifier;
/*  92 */     this.allowPitchControls = other.allowPitchControls;
/*  93 */     this.displayCursor = other.displayCursor;
/*  94 */     this.displayReticle = other.displayReticle;
/*  95 */     this.mouseInputTargetType = other.mouseInputTargetType;
/*  96 */     this.sendMouseMotion = other.sendMouseMotion;
/*  97 */     this.skipCharacterPhysics = other.skipCharacterPhysics;
/*  98 */     this.isFirstPerson = other.isFirstPerson;
/*  99 */     this.movementForceRotationType = other.movementForceRotationType;
/* 100 */     this.movementForceRotation = other.movementForceRotation;
/* 101 */     this.attachedToType = other.attachedToType;
/* 102 */     this.attachedToEntityId = other.attachedToEntityId;
/* 103 */     this.eyeOffset = other.eyeOffset;
/* 104 */     this.positionDistanceOffsetType = other.positionDistanceOffsetType;
/* 105 */     this.positionOffset = other.positionOffset;
/* 106 */     this.rotationOffset = other.rotationOffset;
/* 107 */     this.positionType = other.positionType;
/* 108 */     this.position = other.position;
/* 109 */     this.rotationType = other.rotationType;
/* 110 */     this.rotation = other.rotation;
/* 111 */     this.canMoveType = other.canMoveType;
/* 112 */     this.applyMovementType = other.applyMovementType;
/* 113 */     this.movementMultiplier = other.movementMultiplier;
/* 114 */     this.applyLookType = other.applyLookType;
/* 115 */     this.lookMultiplier = other.lookMultiplier;
/* 116 */     this.mouseInputType = other.mouseInputType;
/* 117 */     this.planeNormal = other.planeNormal;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ServerCameraSettings deserialize(@Nonnull ByteBuf buf, int offset) {
/* 122 */     ServerCameraSettings obj = new ServerCameraSettings();
/* 123 */     byte nullBits = buf.getByte(offset);
/* 124 */     obj.positionLerpSpeed = buf.getFloatLE(offset + 1);
/* 125 */     obj.rotationLerpSpeed = buf.getFloatLE(offset + 5);
/* 126 */     obj.distance = buf.getFloatLE(offset + 9);
/* 127 */     obj.speedModifier = buf.getFloatLE(offset + 13);
/* 128 */     obj.allowPitchControls = (buf.getByte(offset + 17) != 0);
/* 129 */     obj.displayCursor = (buf.getByte(offset + 18) != 0);
/* 130 */     obj.displayReticle = (buf.getByte(offset + 19) != 0);
/* 131 */     obj.mouseInputTargetType = MouseInputTargetType.fromValue(buf.getByte(offset + 20));
/* 132 */     obj.sendMouseMotion = (buf.getByte(offset + 21) != 0);
/* 133 */     obj.skipCharacterPhysics = (buf.getByte(offset + 22) != 0);
/* 134 */     obj.isFirstPerson = (buf.getByte(offset + 23) != 0);
/* 135 */     obj.movementForceRotationType = MovementForceRotationType.fromValue(buf.getByte(offset + 24));
/* 136 */     if ((nullBits & 0x1) != 0) obj.movementForceRotation = Direction.deserialize(buf, offset + 25); 
/* 137 */     obj.attachedToType = AttachedToType.fromValue(buf.getByte(offset + 37));
/* 138 */     obj.attachedToEntityId = buf.getIntLE(offset + 38);
/* 139 */     obj.eyeOffset = (buf.getByte(offset + 42) != 0);
/* 140 */     obj.positionDistanceOffsetType = PositionDistanceOffsetType.fromValue(buf.getByte(offset + 43));
/* 141 */     if ((nullBits & 0x2) != 0) obj.positionOffset = Position.deserialize(buf, offset + 44); 
/* 142 */     if ((nullBits & 0x4) != 0) obj.rotationOffset = Direction.deserialize(buf, offset + 68); 
/* 143 */     obj.positionType = PositionType.fromValue(buf.getByte(offset + 80));
/* 144 */     if ((nullBits & 0x8) != 0) obj.position = Position.deserialize(buf, offset + 81); 
/* 145 */     obj.rotationType = RotationType.fromValue(buf.getByte(offset + 105));
/* 146 */     if ((nullBits & 0x10) != 0) obj.rotation = Direction.deserialize(buf, offset + 106); 
/* 147 */     obj.canMoveType = CanMoveType.fromValue(buf.getByte(offset + 118));
/* 148 */     obj.applyMovementType = ApplyMovementType.fromValue(buf.getByte(offset + 119));
/* 149 */     if ((nullBits & 0x20) != 0) obj.movementMultiplier = Vector3f.deserialize(buf, offset + 120); 
/* 150 */     obj.applyLookType = ApplyLookType.fromValue(buf.getByte(offset + 132));
/* 151 */     if ((nullBits & 0x40) != 0) obj.lookMultiplier = Vector2f.deserialize(buf, offset + 133); 
/* 152 */     obj.mouseInputType = MouseInputType.fromValue(buf.getByte(offset + 141));
/* 153 */     if ((nullBits & 0x80) != 0) obj.planeNormal = Vector3f.deserialize(buf, offset + 142);
/*     */ 
/*     */     
/* 156 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 160 */     return 154;
/*     */   }
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 164 */     byte nullBits = 0;
/* 165 */     if (this.movementForceRotation != null) nullBits = (byte)(nullBits | 0x1); 
/* 166 */     if (this.positionOffset != null) nullBits = (byte)(nullBits | 0x2); 
/* 167 */     if (this.rotationOffset != null) nullBits = (byte)(nullBits | 0x4); 
/* 168 */     if (this.position != null) nullBits = (byte)(nullBits | 0x8); 
/* 169 */     if (this.rotation != null) nullBits = (byte)(nullBits | 0x10); 
/* 170 */     if (this.movementMultiplier != null) nullBits = (byte)(nullBits | 0x20); 
/* 171 */     if (this.lookMultiplier != null) nullBits = (byte)(nullBits | 0x40); 
/* 172 */     if (this.planeNormal != null) nullBits = (byte)(nullBits | 0x80); 
/* 173 */     buf.writeByte(nullBits);
/*     */     
/* 175 */     buf.writeFloatLE(this.positionLerpSpeed);
/* 176 */     buf.writeFloatLE(this.rotationLerpSpeed);
/* 177 */     buf.writeFloatLE(this.distance);
/* 178 */     buf.writeFloatLE(this.speedModifier);
/* 179 */     buf.writeByte(this.allowPitchControls ? 1 : 0);
/* 180 */     buf.writeByte(this.displayCursor ? 1 : 0);
/* 181 */     buf.writeByte(this.displayReticle ? 1 : 0);
/* 182 */     buf.writeByte(this.mouseInputTargetType.getValue());
/* 183 */     buf.writeByte(this.sendMouseMotion ? 1 : 0);
/* 184 */     buf.writeByte(this.skipCharacterPhysics ? 1 : 0);
/* 185 */     buf.writeByte(this.isFirstPerson ? 1 : 0);
/* 186 */     buf.writeByte(this.movementForceRotationType.getValue());
/* 187 */     if (this.movementForceRotation != null) { this.movementForceRotation.serialize(buf); } else { buf.writeZero(12); }
/* 188 */      buf.writeByte(this.attachedToType.getValue());
/* 189 */     buf.writeIntLE(this.attachedToEntityId);
/* 190 */     buf.writeByte(this.eyeOffset ? 1 : 0);
/* 191 */     buf.writeByte(this.positionDistanceOffsetType.getValue());
/* 192 */     if (this.positionOffset != null) { this.positionOffset.serialize(buf); } else { buf.writeZero(24); }
/* 193 */      if (this.rotationOffset != null) { this.rotationOffset.serialize(buf); } else { buf.writeZero(12); }
/* 194 */      buf.writeByte(this.positionType.getValue());
/* 195 */     if (this.position != null) { this.position.serialize(buf); } else { buf.writeZero(24); }
/* 196 */      buf.writeByte(this.rotationType.getValue());
/* 197 */     if (this.rotation != null) { this.rotation.serialize(buf); } else { buf.writeZero(12); }
/* 198 */      buf.writeByte(this.canMoveType.getValue());
/* 199 */     buf.writeByte(this.applyMovementType.getValue());
/* 200 */     if (this.movementMultiplier != null) { this.movementMultiplier.serialize(buf); } else { buf.writeZero(12); }
/* 201 */      buf.writeByte(this.applyLookType.getValue());
/* 202 */     if (this.lookMultiplier != null) { this.lookMultiplier.serialize(buf); } else { buf.writeZero(8); }
/* 203 */      buf.writeByte(this.mouseInputType.getValue());
/* 204 */     if (this.planeNormal != null) { this.planeNormal.serialize(buf); } else { buf.writeZero(12); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 210 */     return 154;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 214 */     if (buffer.readableBytes() - offset < 154) {
/* 215 */       return ValidationResult.error("Buffer too small: expected at least 154 bytes");
/*     */     }
/*     */ 
/*     */     
/* 219 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ServerCameraSettings clone() {
/* 223 */     ServerCameraSettings copy = new ServerCameraSettings();
/* 224 */     copy.positionLerpSpeed = this.positionLerpSpeed;
/* 225 */     copy.rotationLerpSpeed = this.rotationLerpSpeed;
/* 226 */     copy.distance = this.distance;
/* 227 */     copy.speedModifier = this.speedModifier;
/* 228 */     copy.allowPitchControls = this.allowPitchControls;
/* 229 */     copy.displayCursor = this.displayCursor;
/* 230 */     copy.displayReticle = this.displayReticle;
/* 231 */     copy.mouseInputTargetType = this.mouseInputTargetType;
/* 232 */     copy.sendMouseMotion = this.sendMouseMotion;
/* 233 */     copy.skipCharacterPhysics = this.skipCharacterPhysics;
/* 234 */     copy.isFirstPerson = this.isFirstPerson;
/* 235 */     copy.movementForceRotationType = this.movementForceRotationType;
/* 236 */     copy.movementForceRotation = (this.movementForceRotation != null) ? this.movementForceRotation.clone() : null;
/* 237 */     copy.attachedToType = this.attachedToType;
/* 238 */     copy.attachedToEntityId = this.attachedToEntityId;
/* 239 */     copy.eyeOffset = this.eyeOffset;
/* 240 */     copy.positionDistanceOffsetType = this.positionDistanceOffsetType;
/* 241 */     copy.positionOffset = (this.positionOffset != null) ? this.positionOffset.clone() : null;
/* 242 */     copy.rotationOffset = (this.rotationOffset != null) ? this.rotationOffset.clone() : null;
/* 243 */     copy.positionType = this.positionType;
/* 244 */     copy.position = (this.position != null) ? this.position.clone() : null;
/* 245 */     copy.rotationType = this.rotationType;
/* 246 */     copy.rotation = (this.rotation != null) ? this.rotation.clone() : null;
/* 247 */     copy.canMoveType = this.canMoveType;
/* 248 */     copy.applyMovementType = this.applyMovementType;
/* 249 */     copy.movementMultiplier = (this.movementMultiplier != null) ? this.movementMultiplier.clone() : null;
/* 250 */     copy.applyLookType = this.applyLookType;
/* 251 */     copy.lookMultiplier = (this.lookMultiplier != null) ? this.lookMultiplier.clone() : null;
/* 252 */     copy.mouseInputType = this.mouseInputType;
/* 253 */     copy.planeNormal = (this.planeNormal != null) ? this.planeNormal.clone() : null;
/* 254 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ServerCameraSettings other;
/* 260 */     if (this == obj) return true; 
/* 261 */     if (obj instanceof ServerCameraSettings) { other = (ServerCameraSettings)obj; } else { return false; }
/* 262 */      return (this.positionLerpSpeed == other.positionLerpSpeed && this.rotationLerpSpeed == other.rotationLerpSpeed && this.distance == other.distance && this.speedModifier == other.speedModifier && this.allowPitchControls == other.allowPitchControls && this.displayCursor == other.displayCursor && this.displayReticle == other.displayReticle && Objects.equals(this.mouseInputTargetType, other.mouseInputTargetType) && this.sendMouseMotion == other.sendMouseMotion && this.skipCharacterPhysics == other.skipCharacterPhysics && this.isFirstPerson == other.isFirstPerson && Objects.equals(this.movementForceRotationType, other.movementForceRotationType) && Objects.equals(this.movementForceRotation, other.movementForceRotation) && Objects.equals(this.attachedToType, other.attachedToType) && this.attachedToEntityId == other.attachedToEntityId && this.eyeOffset == other.eyeOffset && Objects.equals(this.positionDistanceOffsetType, other.positionDistanceOffsetType) && Objects.equals(this.positionOffset, other.positionOffset) && Objects.equals(this.rotationOffset, other.rotationOffset) && Objects.equals(this.positionType, other.positionType) && Objects.equals(this.position, other.position) && Objects.equals(this.rotationType, other.rotationType) && Objects.equals(this.rotation, other.rotation) && Objects.equals(this.canMoveType, other.canMoveType) && Objects.equals(this.applyMovementType, other.applyMovementType) && Objects.equals(this.movementMultiplier, other.movementMultiplier) && Objects.equals(this.applyLookType, other.applyLookType) && Objects.equals(this.lookMultiplier, other.lookMultiplier) && Objects.equals(this.mouseInputType, other.mouseInputType) && Objects.equals(this.planeNormal, other.planeNormal));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 267 */     return Objects.hash(new Object[] { Float.valueOf(this.positionLerpSpeed), Float.valueOf(this.rotationLerpSpeed), Float.valueOf(this.distance), Float.valueOf(this.speedModifier), Boolean.valueOf(this.allowPitchControls), Boolean.valueOf(this.displayCursor), Boolean.valueOf(this.displayReticle), this.mouseInputTargetType, Boolean.valueOf(this.sendMouseMotion), Boolean.valueOf(this.skipCharacterPhysics), Boolean.valueOf(this.isFirstPerson), this.movementForceRotationType, this.movementForceRotation, this.attachedToType, Integer.valueOf(this.attachedToEntityId), Boolean.valueOf(this.eyeOffset), this.positionDistanceOffsetType, this.positionOffset, this.rotationOffset, this.positionType, this.position, this.rotationType, this.rotation, this.canMoveType, this.applyMovementType, this.movementMultiplier, this.applyLookType, this.lookMultiplier, this.mouseInputType, this.planeNormal });
/*     */   }
/*     */   
/*     */   public ServerCameraSettings() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ServerCameraSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */