/*     */ package com.hypixel.hytale.protocol.packets.worldmap;
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
/*     */ public class BiomeData
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 17;
/*     */   public static final int MAX_SIZE = 32768027;
/*     */   public int zoneId;
/*     */   @Nullable
/*     */   public String zoneName;
/*     */   @Nullable
/*     */   public String biomeName;
/*     */   public int biomeColor;
/*     */   
/*     */   public BiomeData() {}
/*     */   
/*     */   public BiomeData(int zoneId, @Nullable String zoneName, @Nullable String biomeName, int biomeColor) {
/*  29 */     this.zoneId = zoneId;
/*  30 */     this.zoneName = zoneName;
/*  31 */     this.biomeName = biomeName;
/*  32 */     this.biomeColor = biomeColor;
/*     */   }
/*     */   
/*     */   public BiomeData(@Nonnull BiomeData other) {
/*  36 */     this.zoneId = other.zoneId;
/*  37 */     this.zoneName = other.zoneName;
/*  38 */     this.biomeName = other.biomeName;
/*  39 */     this.biomeColor = other.biomeColor;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BiomeData deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     BiomeData obj = new BiomeData();
/*  45 */     byte nullBits = buf.getByte(offset);
/*  46 */     obj.zoneId = buf.getIntLE(offset + 1);
/*  47 */     obj.biomeColor = buf.getIntLE(offset + 5);
/*     */     
/*  49 */     if ((nullBits & 0x1) != 0) {
/*  50 */       int varPos0 = offset + 17 + buf.getIntLE(offset + 9);
/*  51 */       int zoneNameLen = VarInt.peek(buf, varPos0);
/*  52 */       if (zoneNameLen < 0) throw ProtocolException.negativeLength("ZoneName", zoneNameLen); 
/*  53 */       if (zoneNameLen > 4096000) throw ProtocolException.stringTooLong("ZoneName", zoneNameLen, 4096000); 
/*  54 */       obj.zoneName = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  56 */     if ((nullBits & 0x2) != 0) {
/*  57 */       int varPos1 = offset + 17 + buf.getIntLE(offset + 13);
/*  58 */       int biomeNameLen = VarInt.peek(buf, varPos1);
/*  59 */       if (biomeNameLen < 0) throw ProtocolException.negativeLength("BiomeName", biomeNameLen); 
/*  60 */       if (biomeNameLen > 4096000) throw ProtocolException.stringTooLong("BiomeName", biomeNameLen, 4096000); 
/*  61 */       obj.biomeName = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     byte nullBits = buf.getByte(offset);
/*  69 */     int maxEnd = 17;
/*  70 */     if ((nullBits & 0x1) != 0) {
/*  71 */       int fieldOffset0 = buf.getIntLE(offset + 9);
/*  72 */       int pos0 = offset + 17 + fieldOffset0;
/*  73 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  74 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  76 */     if ((nullBits & 0x2) != 0) {
/*  77 */       int fieldOffset1 = buf.getIntLE(offset + 13);
/*  78 */       int pos1 = offset + 17 + fieldOffset1;
/*  79 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  80 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  82 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  87 */     int startPos = buf.writerIndex();
/*  88 */     byte nullBits = 0;
/*  89 */     if (this.zoneName != null) nullBits = (byte)(nullBits | 0x1); 
/*  90 */     if (this.biomeName != null) nullBits = (byte)(nullBits | 0x2); 
/*  91 */     buf.writeByte(nullBits);
/*     */     
/*  93 */     buf.writeIntLE(this.zoneId);
/*  94 */     buf.writeIntLE(this.biomeColor);
/*     */     
/*  96 */     int zoneNameOffsetSlot = buf.writerIndex();
/*  97 */     buf.writeIntLE(0);
/*  98 */     int biomeNameOffsetSlot = buf.writerIndex();
/*  99 */     buf.writeIntLE(0);
/*     */     
/* 101 */     int varBlockStart = buf.writerIndex();
/* 102 */     if (this.zoneName != null) {
/* 103 */       buf.setIntLE(zoneNameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 104 */       PacketIO.writeVarString(buf, this.zoneName, 4096000);
/*     */     } else {
/* 106 */       buf.setIntLE(zoneNameOffsetSlot, -1);
/*     */     } 
/* 108 */     if (this.biomeName != null) {
/* 109 */       buf.setIntLE(biomeNameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 110 */       PacketIO.writeVarString(buf, this.biomeName, 4096000);
/*     */     } else {
/* 112 */       buf.setIntLE(biomeNameOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 118 */     int size = 17;
/* 119 */     if (this.zoneName != null) size += PacketIO.stringSize(this.zoneName); 
/* 120 */     if (this.biomeName != null) size += PacketIO.stringSize(this.biomeName);
/*     */     
/* 122 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 126 */     if (buffer.readableBytes() - offset < 17) {
/* 127 */       return ValidationResult.error("Buffer too small: expected at least 17 bytes");
/*     */     }
/*     */     
/* 130 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 133 */     if ((nullBits & 0x1) != 0) {
/* 134 */       int zoneNameOffset = buffer.getIntLE(offset + 9);
/* 135 */       if (zoneNameOffset < 0) {
/* 136 */         return ValidationResult.error("Invalid offset for ZoneName");
/*     */       }
/* 138 */       int pos = offset + 17 + zoneNameOffset;
/* 139 */       if (pos >= buffer.writerIndex()) {
/* 140 */         return ValidationResult.error("Offset out of bounds for ZoneName");
/*     */       }
/* 142 */       int zoneNameLen = VarInt.peek(buffer, pos);
/* 143 */       if (zoneNameLen < 0) {
/* 144 */         return ValidationResult.error("Invalid string length for ZoneName");
/*     */       }
/* 146 */       if (zoneNameLen > 4096000) {
/* 147 */         return ValidationResult.error("ZoneName exceeds max length 4096000");
/*     */       }
/* 149 */       pos += VarInt.length(buffer, pos);
/* 150 */       pos += zoneNameLen;
/* 151 */       if (pos > buffer.writerIndex()) {
/* 152 */         return ValidationResult.error("Buffer overflow reading ZoneName");
/*     */       }
/*     */     } 
/*     */     
/* 156 */     if ((nullBits & 0x2) != 0) {
/* 157 */       int biomeNameOffset = buffer.getIntLE(offset + 13);
/* 158 */       if (biomeNameOffset < 0) {
/* 159 */         return ValidationResult.error("Invalid offset for BiomeName");
/*     */       }
/* 161 */       int pos = offset + 17 + biomeNameOffset;
/* 162 */       if (pos >= buffer.writerIndex()) {
/* 163 */         return ValidationResult.error("Offset out of bounds for BiomeName");
/*     */       }
/* 165 */       int biomeNameLen = VarInt.peek(buffer, pos);
/* 166 */       if (biomeNameLen < 0) {
/* 167 */         return ValidationResult.error("Invalid string length for BiomeName");
/*     */       }
/* 169 */       if (biomeNameLen > 4096000) {
/* 170 */         return ValidationResult.error("BiomeName exceeds max length 4096000");
/*     */       }
/* 172 */       pos += VarInt.length(buffer, pos);
/* 173 */       pos += biomeNameLen;
/* 174 */       if (pos > buffer.writerIndex()) {
/* 175 */         return ValidationResult.error("Buffer overflow reading BiomeName");
/*     */       }
/*     */     } 
/* 178 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BiomeData clone() {
/* 182 */     BiomeData copy = new BiomeData();
/* 183 */     copy.zoneId = this.zoneId;
/* 184 */     copy.zoneName = this.zoneName;
/* 185 */     copy.biomeName = this.biomeName;
/* 186 */     copy.biomeColor = this.biomeColor;
/* 187 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BiomeData other;
/* 193 */     if (this == obj) return true; 
/* 194 */     if (obj instanceof BiomeData) { other = (BiomeData)obj; } else { return false; }
/* 195 */      return (this.zoneId == other.zoneId && Objects.equals(this.zoneName, other.zoneName) && Objects.equals(this.biomeName, other.biomeName) && this.biomeColor == other.biomeColor);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 200 */     return Objects.hash(new Object[] { Integer.valueOf(this.zoneId), this.zoneName, this.biomeName, Integer.valueOf(this.biomeColor) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\worldmap\BiomeData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */