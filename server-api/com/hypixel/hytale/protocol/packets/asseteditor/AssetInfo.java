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
/*     */ public class AssetInfo {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 11;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   public static final int VARIABLE_BLOCK_START = 23;
/*     */   public static final int MAX_SIZE = 81920066;
/*     */   @Nullable
/*     */   public AssetPath path;
/*     */   @Nullable
/*     */   public AssetPath oldPath;
/*     */   public boolean isDeleted;
/*     */   public boolean isNew;
/*     */   public long lastModificationDate;
/*     */   @Nullable
/*     */   public String lastModificationUsername;
/*     */   
/*     */   public AssetInfo() {}
/*     */   
/*     */   public AssetInfo(@Nullable AssetPath path, @Nullable AssetPath oldPath, boolean isDeleted, boolean isNew, long lastModificationDate, @Nullable String lastModificationUsername) {
/*  31 */     this.path = path;
/*  32 */     this.oldPath = oldPath;
/*  33 */     this.isDeleted = isDeleted;
/*  34 */     this.isNew = isNew;
/*  35 */     this.lastModificationDate = lastModificationDate;
/*  36 */     this.lastModificationUsername = lastModificationUsername;
/*     */   }
/*     */   
/*     */   public AssetInfo(@Nonnull AssetInfo other) {
/*  40 */     this.path = other.path;
/*  41 */     this.oldPath = other.oldPath;
/*  42 */     this.isDeleted = other.isDeleted;
/*  43 */     this.isNew = other.isNew;
/*  44 */     this.lastModificationDate = other.lastModificationDate;
/*  45 */     this.lastModificationUsername = other.lastModificationUsername;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetInfo deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     AssetInfo obj = new AssetInfo();
/*  51 */     byte nullBits = buf.getByte(offset);
/*  52 */     obj.isDeleted = (buf.getByte(offset + 1) != 0);
/*  53 */     obj.isNew = (buf.getByte(offset + 2) != 0);
/*  54 */     obj.lastModificationDate = buf.getLongLE(offset + 3);
/*     */     
/*  56 */     if ((nullBits & 0x1) != 0) {
/*  57 */       int varPos0 = offset + 23 + buf.getIntLE(offset + 11);
/*  58 */       obj.path = AssetPath.deserialize(buf, varPos0);
/*     */     } 
/*  60 */     if ((nullBits & 0x2) != 0) {
/*  61 */       int varPos1 = offset + 23 + buf.getIntLE(offset + 15);
/*  62 */       obj.oldPath = AssetPath.deserialize(buf, varPos1);
/*     */     } 
/*  64 */     if ((nullBits & 0x4) != 0) {
/*  65 */       int varPos2 = offset + 23 + buf.getIntLE(offset + 19);
/*  66 */       int lastModificationUsernameLen = VarInt.peek(buf, varPos2);
/*  67 */       if (lastModificationUsernameLen < 0) throw ProtocolException.negativeLength("LastModificationUsername", lastModificationUsernameLen); 
/*  68 */       if (lastModificationUsernameLen > 4096000) throw ProtocolException.stringTooLong("LastModificationUsername", lastModificationUsernameLen, 4096000); 
/*  69 */       obj.lastModificationUsername = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  72 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  76 */     byte nullBits = buf.getByte(offset);
/*  77 */     int maxEnd = 23;
/*  78 */     if ((nullBits & 0x1) != 0) {
/*  79 */       int fieldOffset0 = buf.getIntLE(offset + 11);
/*  80 */       int pos0 = offset + 23 + fieldOffset0;
/*  81 */       pos0 += AssetPath.computeBytesConsumed(buf, pos0);
/*  82 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  84 */     if ((nullBits & 0x2) != 0) {
/*  85 */       int fieldOffset1 = buf.getIntLE(offset + 15);
/*  86 */       int pos1 = offset + 23 + fieldOffset1;
/*  87 */       pos1 += AssetPath.computeBytesConsumed(buf, pos1);
/*  88 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  90 */     if ((nullBits & 0x4) != 0) {
/*  91 */       int fieldOffset2 = buf.getIntLE(offset + 19);
/*  92 */       int pos2 = offset + 23 + fieldOffset2;
/*  93 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/*  94 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/*  96 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 101 */     int startPos = buf.writerIndex();
/* 102 */     byte nullBits = 0;
/* 103 */     if (this.path != null) nullBits = (byte)(nullBits | 0x1); 
/* 104 */     if (this.oldPath != null) nullBits = (byte)(nullBits | 0x2); 
/* 105 */     if (this.lastModificationUsername != null) nullBits = (byte)(nullBits | 0x4); 
/* 106 */     buf.writeByte(nullBits);
/*     */     
/* 108 */     buf.writeByte(this.isDeleted ? 1 : 0);
/* 109 */     buf.writeByte(this.isNew ? 1 : 0);
/* 110 */     buf.writeLongLE(this.lastModificationDate);
/*     */     
/* 112 */     int pathOffsetSlot = buf.writerIndex();
/* 113 */     buf.writeIntLE(0);
/* 114 */     int oldPathOffsetSlot = buf.writerIndex();
/* 115 */     buf.writeIntLE(0);
/* 116 */     int lastModificationUsernameOffsetSlot = buf.writerIndex();
/* 117 */     buf.writeIntLE(0);
/*     */     
/* 119 */     int varBlockStart = buf.writerIndex();
/* 120 */     if (this.path != null) {
/* 121 */       buf.setIntLE(pathOffsetSlot, buf.writerIndex() - varBlockStart);
/* 122 */       this.path.serialize(buf);
/*     */     } else {
/* 124 */       buf.setIntLE(pathOffsetSlot, -1);
/*     */     } 
/* 126 */     if (this.oldPath != null) {
/* 127 */       buf.setIntLE(oldPathOffsetSlot, buf.writerIndex() - varBlockStart);
/* 128 */       this.oldPath.serialize(buf);
/*     */     } else {
/* 130 */       buf.setIntLE(oldPathOffsetSlot, -1);
/*     */     } 
/* 132 */     if (this.lastModificationUsername != null) {
/* 133 */       buf.setIntLE(lastModificationUsernameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 134 */       PacketIO.writeVarString(buf, this.lastModificationUsername, 4096000);
/*     */     } else {
/* 136 */       buf.setIntLE(lastModificationUsernameOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 142 */     int size = 23;
/* 143 */     if (this.path != null) size += this.path.computeSize(); 
/* 144 */     if (this.oldPath != null) size += this.oldPath.computeSize(); 
/* 145 */     if (this.lastModificationUsername != null) size += PacketIO.stringSize(this.lastModificationUsername);
/*     */     
/* 147 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 151 */     if (buffer.readableBytes() - offset < 23) {
/* 152 */       return ValidationResult.error("Buffer too small: expected at least 23 bytes");
/*     */     }
/*     */     
/* 155 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 158 */     if ((nullBits & 0x1) != 0) {
/* 159 */       int pathOffset = buffer.getIntLE(offset + 11);
/* 160 */       if (pathOffset < 0) {
/* 161 */         return ValidationResult.error("Invalid offset for Path");
/*     */       }
/* 163 */       int pos = offset + 23 + pathOffset;
/* 164 */       if (pos >= buffer.writerIndex()) {
/* 165 */         return ValidationResult.error("Offset out of bounds for Path");
/*     */       }
/* 167 */       ValidationResult pathResult = AssetPath.validateStructure(buffer, pos);
/* 168 */       if (!pathResult.isValid()) {
/* 169 */         return ValidationResult.error("Invalid Path: " + pathResult.error());
/*     */       }
/* 171 */       pos += AssetPath.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 174 */     if ((nullBits & 0x2) != 0) {
/* 175 */       int oldPathOffset = buffer.getIntLE(offset + 15);
/* 176 */       if (oldPathOffset < 0) {
/* 177 */         return ValidationResult.error("Invalid offset for OldPath");
/*     */       }
/* 179 */       int pos = offset + 23 + oldPathOffset;
/* 180 */       if (pos >= buffer.writerIndex()) {
/* 181 */         return ValidationResult.error("Offset out of bounds for OldPath");
/*     */       }
/* 183 */       ValidationResult oldPathResult = AssetPath.validateStructure(buffer, pos);
/* 184 */       if (!oldPathResult.isValid()) {
/* 185 */         return ValidationResult.error("Invalid OldPath: " + oldPathResult.error());
/*     */       }
/* 187 */       pos += AssetPath.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 190 */     if ((nullBits & 0x4) != 0) {
/* 191 */       int lastModificationUsernameOffset = buffer.getIntLE(offset + 19);
/* 192 */       if (lastModificationUsernameOffset < 0) {
/* 193 */         return ValidationResult.error("Invalid offset for LastModificationUsername");
/*     */       }
/* 195 */       int pos = offset + 23 + lastModificationUsernameOffset;
/* 196 */       if (pos >= buffer.writerIndex()) {
/* 197 */         return ValidationResult.error("Offset out of bounds for LastModificationUsername");
/*     */       }
/* 199 */       int lastModificationUsernameLen = VarInt.peek(buffer, pos);
/* 200 */       if (lastModificationUsernameLen < 0) {
/* 201 */         return ValidationResult.error("Invalid string length for LastModificationUsername");
/*     */       }
/* 203 */       if (lastModificationUsernameLen > 4096000) {
/* 204 */         return ValidationResult.error("LastModificationUsername exceeds max length 4096000");
/*     */       }
/* 206 */       pos += VarInt.length(buffer, pos);
/* 207 */       pos += lastModificationUsernameLen;
/* 208 */       if (pos > buffer.writerIndex()) {
/* 209 */         return ValidationResult.error("Buffer overflow reading LastModificationUsername");
/*     */       }
/*     */     } 
/* 212 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetInfo clone() {
/* 216 */     AssetInfo copy = new AssetInfo();
/* 217 */     copy.path = (this.path != null) ? this.path.clone() : null;
/* 218 */     copy.oldPath = (this.oldPath != null) ? this.oldPath.clone() : null;
/* 219 */     copy.isDeleted = this.isDeleted;
/* 220 */     copy.isNew = this.isNew;
/* 221 */     copy.lastModificationDate = this.lastModificationDate;
/* 222 */     copy.lastModificationUsername = this.lastModificationUsername;
/* 223 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetInfo other;
/* 229 */     if (this == obj) return true; 
/* 230 */     if (obj instanceof AssetInfo) { other = (AssetInfo)obj; } else { return false; }
/* 231 */      return (Objects.equals(this.path, other.path) && Objects.equals(this.oldPath, other.oldPath) && this.isDeleted == other.isDeleted && this.isNew == other.isNew && this.lastModificationDate == other.lastModificationDate && Objects.equals(this.lastModificationUsername, other.lastModificationUsername));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 236 */     return Objects.hash(new Object[] { this.path, this.oldPath, Boolean.valueOf(this.isDeleted), Boolean.valueOf(this.isNew), Long.valueOf(this.lastModificationDate), this.lastModificationUsername });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */