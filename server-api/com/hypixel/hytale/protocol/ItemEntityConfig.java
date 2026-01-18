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
/*     */ public class ItemEntityConfig
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 5;
/*     */   public static final int MAX_SIZE = 16384010;
/*     */   @Nullable
/*     */   public String particleSystemId;
/*     */   @Nullable
/*     */   public Color particleColor;
/*     */   public boolean showItemParticles;
/*     */   
/*     */   public ItemEntityConfig() {}
/*     */   
/*     */   public ItemEntityConfig(@Nullable String particleSystemId, @Nullable Color particleColor, boolean showItemParticles) {
/*  28 */     this.particleSystemId = particleSystemId;
/*  29 */     this.particleColor = particleColor;
/*  30 */     this.showItemParticles = showItemParticles;
/*     */   }
/*     */   
/*     */   public ItemEntityConfig(@Nonnull ItemEntityConfig other) {
/*  34 */     this.particleSystemId = other.particleSystemId;
/*  35 */     this.particleColor = other.particleColor;
/*  36 */     this.showItemParticles = other.showItemParticles;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ItemEntityConfig deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     ItemEntityConfig obj = new ItemEntityConfig();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     if ((nullBits & 0x2) != 0) obj.particleColor = Color.deserialize(buf, offset + 1); 
/*  44 */     obj.showItemParticles = (buf.getByte(offset + 4) != 0);
/*     */     
/*  46 */     int pos = offset + 5;
/*  47 */     if ((nullBits & 0x1) != 0) { int particleSystemIdLen = VarInt.peek(buf, pos);
/*  48 */       if (particleSystemIdLen < 0) throw ProtocolException.negativeLength("ParticleSystemId", particleSystemIdLen); 
/*  49 */       if (particleSystemIdLen > 4096000) throw ProtocolException.stringTooLong("ParticleSystemId", particleSystemIdLen, 4096000); 
/*  50 */       int particleSystemIdVarLen = VarInt.length(buf, pos);
/*  51 */       obj.particleSystemId = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  52 */       pos += particleSystemIdVarLen + particleSystemIdLen; }
/*     */     
/*  54 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     int pos = offset + 5;
/*  60 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  61 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  66 */     byte nullBits = 0;
/*  67 */     if (this.particleSystemId != null) nullBits = (byte)(nullBits | 0x1); 
/*  68 */     if (this.particleColor != null) nullBits = (byte)(nullBits | 0x2); 
/*  69 */     buf.writeByte(nullBits);
/*     */     
/*  71 */     if (this.particleColor != null) { this.particleColor.serialize(buf); } else { buf.writeZero(3); }
/*  72 */      buf.writeByte(this.showItemParticles ? 1 : 0);
/*     */     
/*  74 */     if (this.particleSystemId != null) PacketIO.writeVarString(buf, this.particleSystemId, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  79 */     int size = 5;
/*  80 */     if (this.particleSystemId != null) size += PacketIO.stringSize(this.particleSystemId);
/*     */     
/*  82 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  86 */     if (buffer.readableBytes() - offset < 5) {
/*  87 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*     */     }
/*     */     
/*  90 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  92 */     int pos = offset + 5;
/*     */     
/*  94 */     if ((nullBits & 0x1) != 0) {
/*  95 */       int particleSystemIdLen = VarInt.peek(buffer, pos);
/*  96 */       if (particleSystemIdLen < 0) {
/*  97 */         return ValidationResult.error("Invalid string length for ParticleSystemId");
/*     */       }
/*  99 */       if (particleSystemIdLen > 4096000) {
/* 100 */         return ValidationResult.error("ParticleSystemId exceeds max length 4096000");
/*     */       }
/* 102 */       pos += VarInt.length(buffer, pos);
/* 103 */       pos += particleSystemIdLen;
/* 104 */       if (pos > buffer.writerIndex()) {
/* 105 */         return ValidationResult.error("Buffer overflow reading ParticleSystemId");
/*     */       }
/*     */     } 
/* 108 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemEntityConfig clone() {
/* 112 */     ItemEntityConfig copy = new ItemEntityConfig();
/* 113 */     copy.particleSystemId = this.particleSystemId;
/* 114 */     copy.particleColor = (this.particleColor != null) ? this.particleColor.clone() : null;
/* 115 */     copy.showItemParticles = this.showItemParticles;
/* 116 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemEntityConfig other;
/* 122 */     if (this == obj) return true; 
/* 123 */     if (obj instanceof ItemEntityConfig) { other = (ItemEntityConfig)obj; } else { return false; }
/* 124 */      return (Objects.equals(this.particleSystemId, other.particleSystemId) && Objects.equals(this.particleColor, other.particleColor) && this.showItemParticles == other.showItemParticles);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 129 */     return Objects.hash(new Object[] { this.particleSystemId, this.particleColor, Boolean.valueOf(this.showItemParticles) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemEntityConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */