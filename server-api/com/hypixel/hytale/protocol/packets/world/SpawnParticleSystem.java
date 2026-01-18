/*     */ package com.hypixel.hytale.protocol.packets.world;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.protocol.Direction;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.Position;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class SpawnParticleSystem
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 152;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 44;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 44;
/*     */   
/*     */   public int getId() {
/*  27 */     return 152;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int MAX_SIZE = 16384049;
/*     */   
/*     */   @Nullable
/*     */   public String particleSystemId;
/*     */   
/*     */   @Nullable
/*     */   public Position position;
/*     */   
/*     */   public SpawnParticleSystem(@Nullable String particleSystemId, @Nullable Position position, @Nullable Direction rotation, float scale, @Nullable Color color) {
/*  40 */     this.particleSystemId = particleSystemId;
/*  41 */     this.position = position;
/*  42 */     this.rotation = rotation;
/*  43 */     this.scale = scale;
/*  44 */     this.color = color;
/*     */   } @Nullable
/*     */   public Direction rotation; public float scale; @Nullable
/*     */   public Color color; public SpawnParticleSystem() {} public SpawnParticleSystem(@Nonnull SpawnParticleSystem other) {
/*  48 */     this.particleSystemId = other.particleSystemId;
/*  49 */     this.position = other.position;
/*  50 */     this.rotation = other.rotation;
/*  51 */     this.scale = other.scale;
/*  52 */     this.color = other.color;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SpawnParticleSystem deserialize(@Nonnull ByteBuf buf, int offset) {
/*  57 */     SpawnParticleSystem obj = new SpawnParticleSystem();
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     if ((nullBits & 0x2) != 0) obj.position = Position.deserialize(buf, offset + 1); 
/*  60 */     if ((nullBits & 0x4) != 0) obj.rotation = Direction.deserialize(buf, offset + 25); 
/*  61 */     obj.scale = buf.getFloatLE(offset + 37);
/*  62 */     if ((nullBits & 0x8) != 0) obj.color = Color.deserialize(buf, offset + 41);
/*     */     
/*  64 */     int pos = offset + 44;
/*  65 */     if ((nullBits & 0x1) != 0) { int particleSystemIdLen = VarInt.peek(buf, pos);
/*  66 */       if (particleSystemIdLen < 0) throw ProtocolException.negativeLength("ParticleSystemId", particleSystemIdLen); 
/*  67 */       if (particleSystemIdLen > 4096000) throw ProtocolException.stringTooLong("ParticleSystemId", particleSystemIdLen, 4096000); 
/*  68 */       int particleSystemIdVarLen = VarInt.length(buf, pos);
/*  69 */       obj.particleSystemId = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  70 */       pos += particleSystemIdVarLen + particleSystemIdLen; }
/*     */     
/*  72 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  76 */     byte nullBits = buf.getByte(offset);
/*  77 */     int pos = offset + 44;
/*  78 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  79 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  85 */     byte nullBits = 0;
/*  86 */     if (this.particleSystemId != null) nullBits = (byte)(nullBits | 0x1); 
/*  87 */     if (this.position != null) nullBits = (byte)(nullBits | 0x2); 
/*  88 */     if (this.rotation != null) nullBits = (byte)(nullBits | 0x4); 
/*  89 */     if (this.color != null) nullBits = (byte)(nullBits | 0x8); 
/*  90 */     buf.writeByte(nullBits);
/*     */     
/*  92 */     if (this.position != null) { this.position.serialize(buf); } else { buf.writeZero(24); }
/*  93 */      if (this.rotation != null) { this.rotation.serialize(buf); } else { buf.writeZero(12); }
/*  94 */      buf.writeFloatLE(this.scale);
/*  95 */     if (this.color != null) { this.color.serialize(buf); } else { buf.writeZero(3); }
/*     */     
/*  97 */     if (this.particleSystemId != null) PacketIO.writeVarString(buf, this.particleSystemId, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/* 102 */     int size = 44;
/* 103 */     if (this.particleSystemId != null) size += PacketIO.stringSize(this.particleSystemId);
/*     */     
/* 105 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 109 */     if (buffer.readableBytes() - offset < 44) {
/* 110 */       return ValidationResult.error("Buffer too small: expected at least 44 bytes");
/*     */     }
/*     */     
/* 113 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 115 */     int pos = offset + 44;
/*     */     
/* 117 */     if ((nullBits & 0x1) != 0) {
/* 118 */       int particleSystemIdLen = VarInt.peek(buffer, pos);
/* 119 */       if (particleSystemIdLen < 0) {
/* 120 */         return ValidationResult.error("Invalid string length for ParticleSystemId");
/*     */       }
/* 122 */       if (particleSystemIdLen > 4096000) {
/* 123 */         return ValidationResult.error("ParticleSystemId exceeds max length 4096000");
/*     */       }
/* 125 */       pos += VarInt.length(buffer, pos);
/* 126 */       pos += particleSystemIdLen;
/* 127 */       if (pos > buffer.writerIndex()) {
/* 128 */         return ValidationResult.error("Buffer overflow reading ParticleSystemId");
/*     */       }
/*     */     } 
/* 131 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SpawnParticleSystem clone() {
/* 135 */     SpawnParticleSystem copy = new SpawnParticleSystem();
/* 136 */     copy.particleSystemId = this.particleSystemId;
/* 137 */     copy.position = (this.position != null) ? this.position.clone() : null;
/* 138 */     copy.rotation = (this.rotation != null) ? this.rotation.clone() : null;
/* 139 */     copy.scale = this.scale;
/* 140 */     copy.color = (this.color != null) ? this.color.clone() : null;
/* 141 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SpawnParticleSystem other;
/* 147 */     if (this == obj) return true; 
/* 148 */     if (obj instanceof SpawnParticleSystem) { other = (SpawnParticleSystem)obj; } else { return false; }
/* 149 */      return (Objects.equals(this.particleSystemId, other.particleSystemId) && Objects.equals(this.position, other.position) && Objects.equals(this.rotation, other.rotation) && this.scale == other.scale && Objects.equals(this.color, other.color));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 154 */     return Objects.hash(new Object[] { this.particleSystemId, this.position, this.rotation, Float.valueOf(this.scale), this.color });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\SpawnParticleSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */