/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
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
/*     */ public class TimestampedAssetReference
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 49152033;
/*     */   @Nullable
/*     */   public AssetPath path;
/*     */   @Nullable
/*     */   public String timestamp;
/*     */   
/*     */   public TimestampedAssetReference() {}
/*     */   
/*     */   public TimestampedAssetReference(@Nullable AssetPath path, @Nullable String timestamp) {
/*  27 */     this.path = path;
/*  28 */     this.timestamp = timestamp;
/*     */   }
/*     */   
/*     */   public TimestampedAssetReference(@Nonnull TimestampedAssetReference other) {
/*  32 */     this.path = other.path;
/*  33 */     this.timestamp = other.timestamp;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static TimestampedAssetReference deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     TimestampedAssetReference obj = new TimestampedAssetReference();
/*  39 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  41 */     if ((nullBits & 0x1) != 0) {
/*  42 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  43 */       obj.path = AssetPath.deserialize(buf, varPos0);
/*     */     } 
/*  45 */     if ((nullBits & 0x2) != 0) {
/*  46 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  47 */       int timestampLen = VarInt.peek(buf, varPos1);
/*  48 */       if (timestampLen < 0) throw ProtocolException.negativeLength("Timestamp", timestampLen); 
/*  49 */       if (timestampLen > 4096000) throw ProtocolException.stringTooLong("Timestamp", timestampLen, 4096000); 
/*  50 */       obj.timestamp = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  53 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  57 */     byte nullBits = buf.getByte(offset);
/*  58 */     int maxEnd = 9;
/*  59 */     if ((nullBits & 0x1) != 0) {
/*  60 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  61 */       int pos0 = offset + 9 + fieldOffset0;
/*  62 */       pos0 += AssetPath.computeBytesConsumed(buf, pos0);
/*  63 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  65 */     if ((nullBits & 0x2) != 0) {
/*  66 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  67 */       int pos1 = offset + 9 + fieldOffset1;
/*  68 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  69 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  71 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  76 */     int startPos = buf.writerIndex();
/*  77 */     byte nullBits = 0;
/*  78 */     if (this.path != null) nullBits = (byte)(nullBits | 0x1); 
/*  79 */     if (this.timestamp != null) nullBits = (byte)(nullBits | 0x2); 
/*  80 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  83 */     int pathOffsetSlot = buf.writerIndex();
/*  84 */     buf.writeIntLE(0);
/*  85 */     int timestampOffsetSlot = buf.writerIndex();
/*  86 */     buf.writeIntLE(0);
/*     */     
/*  88 */     int varBlockStart = buf.writerIndex();
/*  89 */     if (this.path != null) {
/*  90 */       buf.setIntLE(pathOffsetSlot, buf.writerIndex() - varBlockStart);
/*  91 */       this.path.serialize(buf);
/*     */     } else {
/*  93 */       buf.setIntLE(pathOffsetSlot, -1);
/*     */     } 
/*  95 */     if (this.timestamp != null) {
/*  96 */       buf.setIntLE(timestampOffsetSlot, buf.writerIndex() - varBlockStart);
/*  97 */       PacketIO.writeVarString(buf, this.timestamp, 4096000);
/*     */     } else {
/*  99 */       buf.setIntLE(timestampOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 105 */     int size = 9;
/* 106 */     if (this.path != null) size += this.path.computeSize(); 
/* 107 */     if (this.timestamp != null) size += PacketIO.stringSize(this.timestamp);
/*     */     
/* 109 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 113 */     if (buffer.readableBytes() - offset < 9) {
/* 114 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 117 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 120 */     if ((nullBits & 0x1) != 0) {
/* 121 */       int pathOffset = buffer.getIntLE(offset + 1);
/* 122 */       if (pathOffset < 0) {
/* 123 */         return ValidationResult.error("Invalid offset for Path");
/*     */       }
/* 125 */       int pos = offset + 9 + pathOffset;
/* 126 */       if (pos >= buffer.writerIndex()) {
/* 127 */         return ValidationResult.error("Offset out of bounds for Path");
/*     */       }
/* 129 */       ValidationResult pathResult = AssetPath.validateStructure(buffer, pos);
/* 130 */       if (!pathResult.isValid()) {
/* 131 */         return ValidationResult.error("Invalid Path: " + pathResult.error());
/*     */       }
/* 133 */       pos += AssetPath.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 136 */     if ((nullBits & 0x2) != 0) {
/* 137 */       int timestampOffset = buffer.getIntLE(offset + 5);
/* 138 */       if (timestampOffset < 0) {
/* 139 */         return ValidationResult.error("Invalid offset for Timestamp");
/*     */       }
/* 141 */       int pos = offset + 9 + timestampOffset;
/* 142 */       if (pos >= buffer.writerIndex()) {
/* 143 */         return ValidationResult.error("Offset out of bounds for Timestamp");
/*     */       }
/* 145 */       int timestampLen = VarInt.peek(buffer, pos);
/* 146 */       if (timestampLen < 0) {
/* 147 */         return ValidationResult.error("Invalid string length for Timestamp");
/*     */       }
/* 149 */       if (timestampLen > 4096000) {
/* 150 */         return ValidationResult.error("Timestamp exceeds max length 4096000");
/*     */       }
/* 152 */       pos += VarInt.length(buffer, pos);
/* 153 */       pos += timestampLen;
/* 154 */       if (pos > buffer.writerIndex()) {
/* 155 */         return ValidationResult.error("Buffer overflow reading Timestamp");
/*     */       }
/*     */     } 
/* 158 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public TimestampedAssetReference clone() {
/* 162 */     TimestampedAssetReference copy = new TimestampedAssetReference();
/* 163 */     copy.path = (this.path != null) ? this.path.clone() : null;
/* 164 */     copy.timestamp = this.timestamp;
/* 165 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     TimestampedAssetReference other;
/* 171 */     if (this == obj) return true; 
/* 172 */     if (obj instanceof TimestampedAssetReference) { other = (TimestampedAssetReference)obj; } else { return false; }
/* 173 */      return (Objects.equals(this.path, other.path) && Objects.equals(this.timestamp, other.timestamp));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 178 */     return Objects.hash(new Object[] { this.path, this.timestamp });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\TimestampedAssetReference.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */