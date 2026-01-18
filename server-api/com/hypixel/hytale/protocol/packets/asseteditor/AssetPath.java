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
/*     */ public class AssetPath
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 32768019;
/*     */   @Nullable
/*     */   public String pack;
/*     */   @Nullable
/*     */   public String path;
/*     */   
/*     */   public AssetPath() {}
/*     */   
/*     */   public AssetPath(@Nullable String pack, @Nullable String path) {
/*  27 */     this.pack = pack;
/*  28 */     this.path = path;
/*     */   }
/*     */   
/*     */   public AssetPath(@Nonnull AssetPath other) {
/*  32 */     this.pack = other.pack;
/*  33 */     this.path = other.path;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetPath deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     AssetPath obj = new AssetPath();
/*  39 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  41 */     if ((nullBits & 0x1) != 0) {
/*  42 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  43 */       int packLen = VarInt.peek(buf, varPos0);
/*  44 */       if (packLen < 0) throw ProtocolException.negativeLength("Pack", packLen); 
/*  45 */       if (packLen > 4096000) throw ProtocolException.stringTooLong("Pack", packLen, 4096000); 
/*  46 */       obj.pack = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  48 */     if ((nullBits & 0x2) != 0) {
/*  49 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  50 */       int pathLen = VarInt.peek(buf, varPos1);
/*  51 */       if (pathLen < 0) throw ProtocolException.negativeLength("Path", pathLen); 
/*  52 */       if (pathLen > 4096000) throw ProtocolException.stringTooLong("Path", pathLen, 4096000); 
/*  53 */       obj.path = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
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
/*  81 */     if (this.pack != null) nullBits = (byte)(nullBits | 0x1); 
/*  82 */     if (this.path != null) nullBits = (byte)(nullBits | 0x2); 
/*  83 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  86 */     int packOffsetSlot = buf.writerIndex();
/*  87 */     buf.writeIntLE(0);
/*  88 */     int pathOffsetSlot = buf.writerIndex();
/*  89 */     buf.writeIntLE(0);
/*     */     
/*  91 */     int varBlockStart = buf.writerIndex();
/*  92 */     if (this.pack != null) {
/*  93 */       buf.setIntLE(packOffsetSlot, buf.writerIndex() - varBlockStart);
/*  94 */       PacketIO.writeVarString(buf, this.pack, 4096000);
/*     */     } else {
/*  96 */       buf.setIntLE(packOffsetSlot, -1);
/*     */     } 
/*  98 */     if (this.path != null) {
/*  99 */       buf.setIntLE(pathOffsetSlot, buf.writerIndex() - varBlockStart);
/* 100 */       PacketIO.writeVarString(buf, this.path, 4096000);
/*     */     } else {
/* 102 */       buf.setIntLE(pathOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 108 */     int size = 9;
/* 109 */     if (this.pack != null) size += PacketIO.stringSize(this.pack); 
/* 110 */     if (this.path != null) size += PacketIO.stringSize(this.path);
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
/* 124 */       int packOffset = buffer.getIntLE(offset + 1);
/* 125 */       if (packOffset < 0) {
/* 126 */         return ValidationResult.error("Invalid offset for Pack");
/*     */       }
/* 128 */       int pos = offset + 9 + packOffset;
/* 129 */       if (pos >= buffer.writerIndex()) {
/* 130 */         return ValidationResult.error("Offset out of bounds for Pack");
/*     */       }
/* 132 */       int packLen = VarInt.peek(buffer, pos);
/* 133 */       if (packLen < 0) {
/* 134 */         return ValidationResult.error("Invalid string length for Pack");
/*     */       }
/* 136 */       if (packLen > 4096000) {
/* 137 */         return ValidationResult.error("Pack exceeds max length 4096000");
/*     */       }
/* 139 */       pos += VarInt.length(buffer, pos);
/* 140 */       pos += packLen;
/* 141 */       if (pos > buffer.writerIndex()) {
/* 142 */         return ValidationResult.error("Buffer overflow reading Pack");
/*     */       }
/*     */     } 
/*     */     
/* 146 */     if ((nullBits & 0x2) != 0) {
/* 147 */       int pathOffset = buffer.getIntLE(offset + 5);
/* 148 */       if (pathOffset < 0) {
/* 149 */         return ValidationResult.error("Invalid offset for Path");
/*     */       }
/* 151 */       int pos = offset + 9 + pathOffset;
/* 152 */       if (pos >= buffer.writerIndex()) {
/* 153 */         return ValidationResult.error("Offset out of bounds for Path");
/*     */       }
/* 155 */       int pathLen = VarInt.peek(buffer, pos);
/* 156 */       if (pathLen < 0) {
/* 157 */         return ValidationResult.error("Invalid string length for Path");
/*     */       }
/* 159 */       if (pathLen > 4096000) {
/* 160 */         return ValidationResult.error("Path exceeds max length 4096000");
/*     */       }
/* 162 */       pos += VarInt.length(buffer, pos);
/* 163 */       pos += pathLen;
/* 164 */       if (pos > buffer.writerIndex()) {
/* 165 */         return ValidationResult.error("Buffer overflow reading Path");
/*     */       }
/*     */     } 
/* 168 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetPath clone() {
/* 172 */     AssetPath copy = new AssetPath();
/* 173 */     copy.pack = this.pack;
/* 174 */     copy.path = this.path;
/* 175 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetPath other;
/* 181 */     if (this == obj) return true; 
/* 182 */     if (obj instanceof AssetPath) { other = (AssetPath)obj; } else { return false; }
/* 183 */      return (Objects.equals(this.pack, other.pack) && Objects.equals(this.path, other.path));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 188 */     return Objects.hash(new Object[] { this.pack, this.path });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetPath.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */