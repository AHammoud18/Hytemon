/*     */ package com.hypixel.hytale.protocol;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class WorldParticle {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 32;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 32;
/*     */   public static final int MAX_SIZE = 16384037;
/*     */   @Nullable
/*     */   public String systemId;
/*     */   public float scale;
/*     */   @Nullable
/*     */   public Color color;
/*     */   @Nullable
/*     */   public Vector3f positionOffset;
/*     */   @Nullable
/*     */   public Direction rotationOffset;
/*     */   
/*     */   public WorldParticle() {}
/*     */   
/*     */   public WorldParticle(@Nullable String systemId, float scale, @Nullable Color color, @Nullable Vector3f positionOffset, @Nullable Direction rotationOffset) {
/*  30 */     this.systemId = systemId;
/*  31 */     this.scale = scale;
/*  32 */     this.color = color;
/*  33 */     this.positionOffset = positionOffset;
/*  34 */     this.rotationOffset = rotationOffset;
/*     */   }
/*     */   
/*     */   public WorldParticle(@Nonnull WorldParticle other) {
/*  38 */     this.systemId = other.systemId;
/*  39 */     this.scale = other.scale;
/*  40 */     this.color = other.color;
/*  41 */     this.positionOffset = other.positionOffset;
/*  42 */     this.rotationOffset = other.rotationOffset;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static WorldParticle deserialize(@Nonnull ByteBuf buf, int offset) {
/*  47 */     WorldParticle obj = new WorldParticle();
/*  48 */     byte nullBits = buf.getByte(offset);
/*  49 */     obj.scale = buf.getFloatLE(offset + 1);
/*  50 */     if ((nullBits & 0x2) != 0) obj.color = Color.deserialize(buf, offset + 5); 
/*  51 */     if ((nullBits & 0x4) != 0) obj.positionOffset = Vector3f.deserialize(buf, offset + 8); 
/*  52 */     if ((nullBits & 0x8) != 0) obj.rotationOffset = Direction.deserialize(buf, offset + 20);
/*     */     
/*  54 */     int pos = offset + 32;
/*  55 */     if ((nullBits & 0x1) != 0) { int systemIdLen = VarInt.peek(buf, pos);
/*  56 */       if (systemIdLen < 0) throw ProtocolException.negativeLength("SystemId", systemIdLen); 
/*  57 */       if (systemIdLen > 4096000) throw ProtocolException.stringTooLong("SystemId", systemIdLen, 4096000); 
/*  58 */       int systemIdVarLen = VarInt.length(buf, pos);
/*  59 */       obj.systemId = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  60 */       pos += systemIdVarLen + systemIdLen; }
/*     */     
/*  62 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  66 */     byte nullBits = buf.getByte(offset);
/*  67 */     int pos = offset + 32;
/*  68 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  69 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  74 */     byte nullBits = 0;
/*  75 */     if (this.systemId != null) nullBits = (byte)(nullBits | 0x1); 
/*  76 */     if (this.color != null) nullBits = (byte)(nullBits | 0x2); 
/*  77 */     if (this.positionOffset != null) nullBits = (byte)(nullBits | 0x4); 
/*  78 */     if (this.rotationOffset != null) nullBits = (byte)(nullBits | 0x8); 
/*  79 */     buf.writeByte(nullBits);
/*     */     
/*  81 */     buf.writeFloatLE(this.scale);
/*  82 */     if (this.color != null) { this.color.serialize(buf); } else { buf.writeZero(3); }
/*  83 */      if (this.positionOffset != null) { this.positionOffset.serialize(buf); } else { buf.writeZero(12); }
/*  84 */      if (this.rotationOffset != null) { this.rotationOffset.serialize(buf); } else { buf.writeZero(12); }
/*     */     
/*  86 */     if (this.systemId != null) PacketIO.writeVarString(buf, this.systemId, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  91 */     int size = 32;
/*  92 */     if (this.systemId != null) size += PacketIO.stringSize(this.systemId);
/*     */     
/*  94 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  98 */     if (buffer.readableBytes() - offset < 32) {
/*  99 */       return ValidationResult.error("Buffer too small: expected at least 32 bytes");
/*     */     }
/*     */     
/* 102 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 104 */     int pos = offset + 32;
/*     */     
/* 106 */     if ((nullBits & 0x1) != 0) {
/* 107 */       int systemIdLen = VarInt.peek(buffer, pos);
/* 108 */       if (systemIdLen < 0) {
/* 109 */         return ValidationResult.error("Invalid string length for SystemId");
/*     */       }
/* 111 */       if (systemIdLen > 4096000) {
/* 112 */         return ValidationResult.error("SystemId exceeds max length 4096000");
/*     */       }
/* 114 */       pos += VarInt.length(buffer, pos);
/* 115 */       pos += systemIdLen;
/* 116 */       if (pos > buffer.writerIndex()) {
/* 117 */         return ValidationResult.error("Buffer overflow reading SystemId");
/*     */       }
/*     */     } 
/* 120 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public WorldParticle clone() {
/* 124 */     WorldParticle copy = new WorldParticle();
/* 125 */     copy.systemId = this.systemId;
/* 126 */     copy.scale = this.scale;
/* 127 */     copy.color = (this.color != null) ? this.color.clone() : null;
/* 128 */     copy.positionOffset = (this.positionOffset != null) ? this.positionOffset.clone() : null;
/* 129 */     copy.rotationOffset = (this.rotationOffset != null) ? this.rotationOffset.clone() : null;
/* 130 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     WorldParticle other;
/* 136 */     if (this == obj) return true; 
/* 137 */     if (obj instanceof WorldParticle) { other = (WorldParticle)obj; } else { return false; }
/* 138 */      return (Objects.equals(this.systemId, other.systemId) && this.scale == other.scale && Objects.equals(this.color, other.color) && Objects.equals(this.positionOffset, other.positionOffset) && Objects.equals(this.rotationOffset, other.rotationOffset));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 143 */     return Objects.hash(new Object[] { this.systemId, Float.valueOf(this.scale), this.color, this.positionOffset, this.rotationOffset });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\WorldParticle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */