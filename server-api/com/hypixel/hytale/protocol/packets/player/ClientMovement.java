/*     */ package com.hypixel.hytale.protocol.packets.player;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Direction;
/*     */ import com.hypixel.hytale.protocol.HalfFloatPosition;
/*     */ import com.hypixel.hytale.protocol.MovementStates;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.Position;
/*     */ import com.hypixel.hytale.protocol.TeleportAck;
/*     */ import com.hypixel.hytale.protocol.Vector3d;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ClientMovement
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 108;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 2;
/*     */   public static final int FIXED_BLOCK_SIZE = 153;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 153;
/*     */   public static final int MAX_SIZE = 153;
/*     */   @Nullable
/*     */   public MovementStates movementStates;
/*     */   
/*     */   public int getId() {
/*  30 */     return 108;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public HalfFloatPosition relativePosition;
/*     */   
/*     */   @Nullable
/*     */   public Position absolutePosition;
/*     */   
/*     */   @Nullable
/*     */   public Direction bodyOrientation;
/*     */   @Nullable
/*     */   public Direction lookOrientation;
/*     */   @Nullable
/*     */   public TeleportAck teleportAck;
/*     */   
/*     */   public ClientMovement(@Nullable MovementStates movementStates, @Nullable HalfFloatPosition relativePosition, @Nullable Position absolutePosition, @Nullable Direction bodyOrientation, @Nullable Direction lookOrientation, @Nullable TeleportAck teleportAck, @Nullable Position wishMovement, @Nullable Vector3d velocity, int mountedTo, @Nullable MovementStates riderMovementStates) {
/*  48 */     this.movementStates = movementStates;
/*  49 */     this.relativePosition = relativePosition;
/*  50 */     this.absolutePosition = absolutePosition;
/*  51 */     this.bodyOrientation = bodyOrientation;
/*  52 */     this.lookOrientation = lookOrientation;
/*  53 */     this.teleportAck = teleportAck;
/*  54 */     this.wishMovement = wishMovement;
/*  55 */     this.velocity = velocity;
/*  56 */     this.mountedTo = mountedTo;
/*  57 */     this.riderMovementStates = riderMovementStates; } @Nullable
/*     */   public Position wishMovement; @Nullable
/*     */   public Vector3d velocity; public int mountedTo; @Nullable
/*     */   public MovementStates riderMovementStates; public ClientMovement() {} public ClientMovement(@Nonnull ClientMovement other) {
/*  61 */     this.movementStates = other.movementStates;
/*  62 */     this.relativePosition = other.relativePosition;
/*  63 */     this.absolutePosition = other.absolutePosition;
/*  64 */     this.bodyOrientation = other.bodyOrientation;
/*  65 */     this.lookOrientation = other.lookOrientation;
/*  66 */     this.teleportAck = other.teleportAck;
/*  67 */     this.wishMovement = other.wishMovement;
/*  68 */     this.velocity = other.velocity;
/*  69 */     this.mountedTo = other.mountedTo;
/*  70 */     this.riderMovementStates = other.riderMovementStates;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ClientMovement deserialize(@Nonnull ByteBuf buf, int offset) {
/*  75 */     ClientMovement obj = new ClientMovement();
/*  76 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/*  77 */     if ((nullBits[0] & 0x1) != 0) obj.movementStates = MovementStates.deserialize(buf, offset + 2); 
/*  78 */     if ((nullBits[0] & 0x2) != 0) obj.relativePosition = HalfFloatPosition.deserialize(buf, offset + 24); 
/*  79 */     if ((nullBits[0] & 0x4) != 0) obj.absolutePosition = Position.deserialize(buf, offset + 30); 
/*  80 */     if ((nullBits[0] & 0x8) != 0) obj.bodyOrientation = Direction.deserialize(buf, offset + 54); 
/*  81 */     if ((nullBits[0] & 0x10) != 0) obj.lookOrientation = Direction.deserialize(buf, offset + 66); 
/*  82 */     if ((nullBits[0] & 0x20) != 0) obj.teleportAck = TeleportAck.deserialize(buf, offset + 78); 
/*  83 */     if ((nullBits[0] & 0x40) != 0) obj.wishMovement = Position.deserialize(buf, offset + 79); 
/*  84 */     if ((nullBits[0] & 0x80) != 0) obj.velocity = Vector3d.deserialize(buf, offset + 103); 
/*  85 */     obj.mountedTo = buf.getIntLE(offset + 127);
/*  86 */     if ((nullBits[1] & 0x1) != 0) obj.riderMovementStates = MovementStates.deserialize(buf, offset + 131);
/*     */ 
/*     */     
/*  89 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  93 */     return 153;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  98 */     byte[] nullBits = new byte[2];
/*  99 */     if (this.movementStates != null) nullBits[0] = (byte)(nullBits[0] | 0x1); 
/* 100 */     if (this.relativePosition != null) nullBits[0] = (byte)(nullBits[0] | 0x2); 
/* 101 */     if (this.absolutePosition != null) nullBits[0] = (byte)(nullBits[0] | 0x4); 
/* 102 */     if (this.bodyOrientation != null) nullBits[0] = (byte)(nullBits[0] | 0x8); 
/* 103 */     if (this.lookOrientation != null) nullBits[0] = (byte)(nullBits[0] | 0x10); 
/* 104 */     if (this.teleportAck != null) nullBits[0] = (byte)(nullBits[0] | 0x20); 
/* 105 */     if (this.wishMovement != null) nullBits[0] = (byte)(nullBits[0] | 0x40); 
/* 106 */     if (this.velocity != null) nullBits[0] = (byte)(nullBits[0] | 0x80); 
/* 107 */     if (this.riderMovementStates != null) nullBits[1] = (byte)(nullBits[1] | 0x1); 
/* 108 */     buf.writeBytes(nullBits);
/*     */     
/* 110 */     if (this.movementStates != null) { this.movementStates.serialize(buf); } else { buf.writeZero(22); }
/* 111 */      if (this.relativePosition != null) { this.relativePosition.serialize(buf); } else { buf.writeZero(6); }
/* 112 */      if (this.absolutePosition != null) { this.absolutePosition.serialize(buf); } else { buf.writeZero(24); }
/* 113 */      if (this.bodyOrientation != null) { this.bodyOrientation.serialize(buf); } else { buf.writeZero(12); }
/* 114 */      if (this.lookOrientation != null) { this.lookOrientation.serialize(buf); } else { buf.writeZero(12); }
/* 115 */      if (this.teleportAck != null) { this.teleportAck.serialize(buf); } else { buf.writeZero(1); }
/* 116 */      if (this.wishMovement != null) { this.wishMovement.serialize(buf); } else { buf.writeZero(24); }
/* 117 */      if (this.velocity != null) { this.velocity.serialize(buf); } else { buf.writeZero(24); }
/* 118 */      buf.writeIntLE(this.mountedTo);
/* 119 */     if (this.riderMovementStates != null) { this.riderMovementStates.serialize(buf); } else { buf.writeZero(22); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 125 */     return 153;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 129 */     if (buffer.readableBytes() - offset < 153) {
/* 130 */       return ValidationResult.error("Buffer too small: expected at least 153 bytes");
/*     */     }
/*     */ 
/*     */     
/* 134 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ClientMovement clone() {
/* 138 */     ClientMovement copy = new ClientMovement();
/* 139 */     copy.movementStates = (this.movementStates != null) ? this.movementStates.clone() : null;
/* 140 */     copy.relativePosition = (this.relativePosition != null) ? this.relativePosition.clone() : null;
/* 141 */     copy.absolutePosition = (this.absolutePosition != null) ? this.absolutePosition.clone() : null;
/* 142 */     copy.bodyOrientation = (this.bodyOrientation != null) ? this.bodyOrientation.clone() : null;
/* 143 */     copy.lookOrientation = (this.lookOrientation != null) ? this.lookOrientation.clone() : null;
/* 144 */     copy.teleportAck = (this.teleportAck != null) ? this.teleportAck.clone() : null;
/* 145 */     copy.wishMovement = (this.wishMovement != null) ? this.wishMovement.clone() : null;
/* 146 */     copy.velocity = (this.velocity != null) ? this.velocity.clone() : null;
/* 147 */     copy.mountedTo = this.mountedTo;
/* 148 */     copy.riderMovementStates = (this.riderMovementStates != null) ? this.riderMovementStates.clone() : null;
/* 149 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ClientMovement other;
/* 155 */     if (this == obj) return true; 
/* 156 */     if (obj instanceof ClientMovement) { other = (ClientMovement)obj; } else { return false; }
/* 157 */      return (Objects.equals(this.movementStates, other.movementStates) && Objects.equals(this.relativePosition, other.relativePosition) && Objects.equals(this.absolutePosition, other.absolutePosition) && Objects.equals(this.bodyOrientation, other.bodyOrientation) && Objects.equals(this.lookOrientation, other.lookOrientation) && Objects.equals(this.teleportAck, other.teleportAck) && Objects.equals(this.wishMovement, other.wishMovement) && Objects.equals(this.velocity, other.velocity) && this.mountedTo == other.mountedTo && Objects.equals(this.riderMovementStates, other.riderMovementStates));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 162 */     return Objects.hash(new Object[] { this.movementStates, this.relativePosition, this.absolutePosition, this.bodyOrientation, this.lookOrientation, this.teleportAck, this.wishMovement, this.velocity, Integer.valueOf(this.mountedTo), this.riderMovementStates });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\player\ClientMovement.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */