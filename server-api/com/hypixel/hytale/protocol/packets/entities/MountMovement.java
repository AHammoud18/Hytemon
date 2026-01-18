/*     */ package com.hypixel.hytale.protocol.packets.entities;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Direction;
/*     */ import com.hypixel.hytale.protocol.MovementStates;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.Position;
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
/*     */ public class MountMovement
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 166;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 59;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   
/*     */   public int getId() {
/*  27 */     return 166;
/*     */   }
/*     */   public static final int VARIABLE_BLOCK_START = 59; public static final int MAX_SIZE = 59; @Nullable
/*     */   public Position absolutePosition; @Nullable
/*     */   public Direction bodyOrientation;
/*     */   @Nullable
/*     */   public MovementStates movementStates;
/*     */   
/*     */   public MountMovement() {}
/*     */   
/*     */   public MountMovement(@Nullable Position absolutePosition, @Nullable Direction bodyOrientation, @Nullable MovementStates movementStates) {
/*  38 */     this.absolutePosition = absolutePosition;
/*  39 */     this.bodyOrientation = bodyOrientation;
/*  40 */     this.movementStates = movementStates;
/*     */   }
/*     */   
/*     */   public MountMovement(@Nonnull MountMovement other) {
/*  44 */     this.absolutePosition = other.absolutePosition;
/*  45 */     this.bodyOrientation = other.bodyOrientation;
/*  46 */     this.movementStates = other.movementStates;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static MountMovement deserialize(@Nonnull ByteBuf buf, int offset) {
/*  51 */     MountMovement obj = new MountMovement();
/*  52 */     byte nullBits = buf.getByte(offset);
/*  53 */     if ((nullBits & 0x1) != 0) obj.absolutePosition = Position.deserialize(buf, offset + 1); 
/*  54 */     if ((nullBits & 0x2) != 0) obj.bodyOrientation = Direction.deserialize(buf, offset + 25); 
/*  55 */     if ((nullBits & 0x4) != 0) obj.movementStates = MovementStates.deserialize(buf, offset + 37);
/*     */ 
/*     */     
/*  58 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  62 */     return 59;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  67 */     byte nullBits = 0;
/*  68 */     if (this.absolutePosition != null) nullBits = (byte)(nullBits | 0x1); 
/*  69 */     if (this.bodyOrientation != null) nullBits = (byte)(nullBits | 0x2); 
/*  70 */     if (this.movementStates != null) nullBits = (byte)(nullBits | 0x4); 
/*  71 */     buf.writeByte(nullBits);
/*     */     
/*  73 */     if (this.absolutePosition != null) { this.absolutePosition.serialize(buf); } else { buf.writeZero(24); }
/*  74 */      if (this.bodyOrientation != null) { this.bodyOrientation.serialize(buf); } else { buf.writeZero(12); }
/*  75 */      if (this.movementStates != null) { this.movementStates.serialize(buf); } else { buf.writeZero(22); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  81 */     return 59;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  85 */     if (buffer.readableBytes() - offset < 59) {
/*  86 */       return ValidationResult.error("Buffer too small: expected at least 59 bytes");
/*     */     }
/*     */ 
/*     */     
/*  90 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public MountMovement clone() {
/*  94 */     MountMovement copy = new MountMovement();
/*  95 */     copy.absolutePosition = (this.absolutePosition != null) ? this.absolutePosition.clone() : null;
/*  96 */     copy.bodyOrientation = (this.bodyOrientation != null) ? this.bodyOrientation.clone() : null;
/*  97 */     copy.movementStates = (this.movementStates != null) ? this.movementStates.clone() : null;
/*  98 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     MountMovement other;
/* 104 */     if (this == obj) return true; 
/* 105 */     if (obj instanceof MountMovement) { other = (MountMovement)obj; } else { return false; }
/* 106 */      return (Objects.equals(this.absolutePosition, other.absolutePosition) && Objects.equals(this.bodyOrientation, other.bodyOrientation) && Objects.equals(this.movementStates, other.movementStates));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 111 */     return Objects.hash(new Object[] { this.absolutePosition, this.bodyOrientation, this.movementStates });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\entities\MountMovement.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */