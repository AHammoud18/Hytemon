/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class FluidParticle
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 8;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 8;
/*     */   public static final int MAX_SIZE = 16384013;
/*     */   @Nullable
/*     */   public String systemId;
/*     */   @Nullable
/*     */   public Color color;
/*     */   public float scale;
/*     */   
/*     */   public FluidParticle() {}
/*     */   
/*     */   public FluidParticle(@Nullable String systemId, @Nullable Color color, float scale) {
/*  28 */     this.systemId = systemId;
/*  29 */     this.color = color;
/*  30 */     this.scale = scale;
/*     */   }
/*     */   
/*     */   public FluidParticle(@Nonnull FluidParticle other) {
/*  34 */     this.systemId = other.systemId;
/*  35 */     this.color = other.color;
/*  36 */     this.scale = other.scale;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static FluidParticle deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     FluidParticle obj = new FluidParticle();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     if ((nullBits & 0x2) != 0) obj.color = Color.deserialize(buf, offset + 1); 
/*  44 */     obj.scale = buf.getFloatLE(offset + 4);
/*     */     
/*  46 */     int pos = offset + 8;
/*  47 */     if ((nullBits & 0x1) != 0) { int systemIdLen = VarInt.peek(buf, pos);
/*  48 */       if (systemIdLen < 0) throw ProtocolException.negativeLength("SystemId", systemIdLen); 
/*  49 */       if (systemIdLen > 4096000) throw ProtocolException.stringTooLong("SystemId", systemIdLen, 4096000); 
/*  50 */       int systemIdVarLen = VarInt.length(buf, pos);
/*  51 */       obj.systemId = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  52 */       pos += systemIdVarLen + systemIdLen; }
/*     */     
/*  54 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     int pos = offset + 8;
/*  60 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  61 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  66 */     byte nullBits = 0;
/*  67 */     if (this.systemId != null) nullBits = (byte)(nullBits | 0x1); 
/*  68 */     if (this.color != null) nullBits = (byte)(nullBits | 0x2); 
/*  69 */     buf.writeByte(nullBits);
/*     */     
/*  71 */     if (this.color != null) { this.color.serialize(buf); } else { buf.writeZero(3); }
/*  72 */      buf.writeFloatLE(this.scale);
/*     */     
/*  74 */     if (this.systemId != null) PacketIO.writeVarString(buf, this.systemId, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  79 */     int size = 8;
/*  80 */     if (this.systemId != null) size += PacketIO.stringSize(this.systemId);
/*     */     
/*  82 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  86 */     if (buffer.readableBytes() - offset < 8) {
/*  87 */       return ValidationResult.error("Buffer too small: expected at least 8 bytes");
/*     */     }
/*     */     
/*  90 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  92 */     int pos = offset + 8;
/*     */     
/*  94 */     if ((nullBits & 0x1) != 0) {
/*  95 */       int systemIdLen = VarInt.peek(buffer, pos);
/*  96 */       if (systemIdLen < 0) {
/*  97 */         return ValidationResult.error("Invalid string length for SystemId");
/*     */       }
/*  99 */       if (systemIdLen > 4096000) {
/* 100 */         return ValidationResult.error("SystemId exceeds max length 4096000");
/*     */       }
/* 102 */       pos += VarInt.length(buffer, pos);
/* 103 */       pos += systemIdLen;
/* 104 */       if (pos > buffer.writerIndex()) {
/* 105 */         return ValidationResult.error("Buffer overflow reading SystemId");
/*     */       }
/*     */     } 
/* 108 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public FluidParticle clone() {
/* 112 */     FluidParticle copy = new FluidParticle();
/* 113 */     copy.systemId = this.systemId;
/* 114 */     copy.color = (this.color != null) ? this.color.clone() : null;
/* 115 */     copy.scale = this.scale;
/* 116 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     FluidParticle other;
/* 122 */     if (this == obj) return true; 
/* 123 */     if (obj instanceof FluidParticle) { other = (FluidParticle)obj; } else { return false; }
/* 124 */      return (Objects.equals(this.systemId, other.systemId) && Objects.equals(this.color, other.color) && this.scale == other.scale);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 129 */     return Objects.hash(new Object[] { this.systemId, this.color, Float.valueOf(this.scale) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\FluidParticle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */