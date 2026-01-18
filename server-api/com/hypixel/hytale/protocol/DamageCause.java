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
/*     */ public class DamageCause
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 32768019;
/*     */   @Nullable
/*     */   public String id;
/*     */   @Nullable
/*     */   public String damageTextColor;
/*     */   
/*     */   public DamageCause() {}
/*     */   
/*     */   public DamageCause(@Nullable String id, @Nullable String damageTextColor) {
/*  27 */     this.id = id;
/*  28 */     this.damageTextColor = damageTextColor;
/*     */   }
/*     */   
/*     */   public DamageCause(@Nonnull DamageCause other) {
/*  32 */     this.id = other.id;
/*  33 */     this.damageTextColor = other.damageTextColor;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static DamageCause deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     DamageCause obj = new DamageCause();
/*  39 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  41 */     if ((nullBits & 0x1) != 0) {
/*  42 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  43 */       int idLen = VarInt.peek(buf, varPos0);
/*  44 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  45 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  46 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  48 */     if ((nullBits & 0x2) != 0) {
/*  49 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  50 */       int damageTextColorLen = VarInt.peek(buf, varPos1);
/*  51 */       if (damageTextColorLen < 0) throw ProtocolException.negativeLength("DamageTextColor", damageTextColorLen); 
/*  52 */       if (damageTextColorLen > 4096000) throw ProtocolException.stringTooLong("DamageTextColor", damageTextColorLen, 4096000); 
/*  53 */       obj.damageTextColor = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     byte nullBits = buf.getByte(offset);
/*  61 */     int maxEnd = 9;
/*  62 */     if ((nullBits & 0x1) != 0) {
/*  63 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  64 */       int pos0 = offset + 9 + fieldOffset0;
/*  65 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  66 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  68 */     if ((nullBits & 0x2) != 0) {
/*  69 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  70 */       int pos1 = offset + 9 + fieldOffset1;
/*  71 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  72 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  74 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  79 */     int startPos = buf.writerIndex();
/*  80 */     byte nullBits = 0;
/*  81 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/*  82 */     if (this.damageTextColor != null) nullBits = (byte)(nullBits | 0x2); 
/*  83 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  86 */     int idOffsetSlot = buf.writerIndex();
/*  87 */     buf.writeIntLE(0);
/*  88 */     int damageTextColorOffsetSlot = buf.writerIndex();
/*  89 */     buf.writeIntLE(0);
/*     */     
/*  91 */     int varBlockStart = buf.writerIndex();
/*  92 */     if (this.id != null) {
/*  93 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/*  94 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/*  96 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/*  98 */     if (this.damageTextColor != null) {
/*  99 */       buf.setIntLE(damageTextColorOffsetSlot, buf.writerIndex() - varBlockStart);
/* 100 */       PacketIO.writeVarString(buf, this.damageTextColor, 4096000);
/*     */     } else {
/* 102 */       buf.setIntLE(damageTextColorOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 108 */     int size = 9;
/* 109 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 110 */     if (this.damageTextColor != null) size += PacketIO.stringSize(this.damageTextColor);
/*     */     
/* 112 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 116 */     if (buffer.readableBytes() - offset < 9) {
/* 117 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 120 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 123 */     if ((nullBits & 0x1) != 0) {
/* 124 */       int idOffset = buffer.getIntLE(offset + 1);
/* 125 */       if (idOffset < 0) {
/* 126 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 128 */       int pos = offset + 9 + idOffset;
/* 129 */       if (pos >= buffer.writerIndex()) {
/* 130 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 132 */       int idLen = VarInt.peek(buffer, pos);
/* 133 */       if (idLen < 0) {
/* 134 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 136 */       if (idLen > 4096000) {
/* 137 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 139 */       pos += VarInt.length(buffer, pos);
/* 140 */       pos += idLen;
/* 141 */       if (pos > buffer.writerIndex()) {
/* 142 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 146 */     if ((nullBits & 0x2) != 0) {
/* 147 */       int damageTextColorOffset = buffer.getIntLE(offset + 5);
/* 148 */       if (damageTextColorOffset < 0) {
/* 149 */         return ValidationResult.error("Invalid offset for DamageTextColor");
/*     */       }
/* 151 */       int pos = offset + 9 + damageTextColorOffset;
/* 152 */       if (pos >= buffer.writerIndex()) {
/* 153 */         return ValidationResult.error("Offset out of bounds for DamageTextColor");
/*     */       }
/* 155 */       int damageTextColorLen = VarInt.peek(buffer, pos);
/* 156 */       if (damageTextColorLen < 0) {
/* 157 */         return ValidationResult.error("Invalid string length for DamageTextColor");
/*     */       }
/* 159 */       if (damageTextColorLen > 4096000) {
/* 160 */         return ValidationResult.error("DamageTextColor exceeds max length 4096000");
/*     */       }
/* 162 */       pos += VarInt.length(buffer, pos);
/* 163 */       pos += damageTextColorLen;
/* 164 */       if (pos > buffer.writerIndex()) {
/* 165 */         return ValidationResult.error("Buffer overflow reading DamageTextColor");
/*     */       }
/*     */     } 
/* 168 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public DamageCause clone() {
/* 172 */     DamageCause copy = new DamageCause();
/* 173 */     copy.id = this.id;
/* 174 */     copy.damageTextColor = this.damageTextColor;
/* 175 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     DamageCause other;
/* 181 */     if (this == obj) return true; 
/* 182 */     if (obj instanceof DamageCause) { other = (DamageCause)obj; } else { return false; }
/* 183 */      return (Objects.equals(this.id, other.id) && Objects.equals(this.damageTextColor, other.damageTextColor));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 188 */     return Objects.hash(new Object[] { this.id, this.damageTextColor });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\DamageCause.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */