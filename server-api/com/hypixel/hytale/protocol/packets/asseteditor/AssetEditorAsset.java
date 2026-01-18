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
/*     */ public class AssetEditorAsset
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 49152033;
/*     */   @Nullable
/*     */   public String hash;
/*     */   @Nullable
/*     */   public AssetPath path;
/*     */   
/*     */   public AssetEditorAsset() {}
/*     */   
/*     */   public AssetEditorAsset(@Nullable String hash, @Nullable AssetPath path) {
/*  27 */     this.hash = hash;
/*  28 */     this.path = path;
/*     */   }
/*     */   
/*     */   public AssetEditorAsset(@Nonnull AssetEditorAsset other) {
/*  32 */     this.hash = other.hash;
/*  33 */     this.path = other.path;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorAsset deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     AssetEditorAsset obj = new AssetEditorAsset();
/*  39 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  41 */     if ((nullBits & 0x1) != 0) {
/*  42 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  43 */       int hashLen = VarInt.peek(buf, varPos0);
/*  44 */       if (hashLen < 0) throw ProtocolException.negativeLength("Hash", hashLen); 
/*  45 */       if (hashLen > 4096000) throw ProtocolException.stringTooLong("Hash", hashLen, 4096000); 
/*  46 */       obj.hash = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  48 */     if ((nullBits & 0x2) != 0) {
/*  49 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  50 */       obj.path = AssetPath.deserialize(buf, varPos1);
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
/*  62 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  63 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  65 */     if ((nullBits & 0x2) != 0) {
/*  66 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  67 */       int pos1 = offset + 9 + fieldOffset1;
/*  68 */       pos1 += AssetPath.computeBytesConsumed(buf, pos1);
/*  69 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  71 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  76 */     int startPos = buf.writerIndex();
/*  77 */     byte nullBits = 0;
/*  78 */     if (this.hash != null) nullBits = (byte)(nullBits | 0x1); 
/*  79 */     if (this.path != null) nullBits = (byte)(nullBits | 0x2); 
/*  80 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  83 */     int hashOffsetSlot = buf.writerIndex();
/*  84 */     buf.writeIntLE(0);
/*  85 */     int pathOffsetSlot = buf.writerIndex();
/*  86 */     buf.writeIntLE(0);
/*     */     
/*  88 */     int varBlockStart = buf.writerIndex();
/*  89 */     if (this.hash != null) {
/*  90 */       buf.setIntLE(hashOffsetSlot, buf.writerIndex() - varBlockStart);
/*  91 */       PacketIO.writeVarString(buf, this.hash, 4096000);
/*     */     } else {
/*  93 */       buf.setIntLE(hashOffsetSlot, -1);
/*     */     } 
/*  95 */     if (this.path != null) {
/*  96 */       buf.setIntLE(pathOffsetSlot, buf.writerIndex() - varBlockStart);
/*  97 */       this.path.serialize(buf);
/*     */     } else {
/*  99 */       buf.setIntLE(pathOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 105 */     int size = 9;
/* 106 */     if (this.hash != null) size += PacketIO.stringSize(this.hash); 
/* 107 */     if (this.path != null) size += this.path.computeSize();
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
/* 121 */       int hashOffset = buffer.getIntLE(offset + 1);
/* 122 */       if (hashOffset < 0) {
/* 123 */         return ValidationResult.error("Invalid offset for Hash");
/*     */       }
/* 125 */       int pos = offset + 9 + hashOffset;
/* 126 */       if (pos >= buffer.writerIndex()) {
/* 127 */         return ValidationResult.error("Offset out of bounds for Hash");
/*     */       }
/* 129 */       int hashLen = VarInt.peek(buffer, pos);
/* 130 */       if (hashLen < 0) {
/* 131 */         return ValidationResult.error("Invalid string length for Hash");
/*     */       }
/* 133 */       if (hashLen > 4096000) {
/* 134 */         return ValidationResult.error("Hash exceeds max length 4096000");
/*     */       }
/* 136 */       pos += VarInt.length(buffer, pos);
/* 137 */       pos += hashLen;
/* 138 */       if (pos > buffer.writerIndex()) {
/* 139 */         return ValidationResult.error("Buffer overflow reading Hash");
/*     */       }
/*     */     } 
/*     */     
/* 143 */     if ((nullBits & 0x2) != 0) {
/* 144 */       int pathOffset = buffer.getIntLE(offset + 5);
/* 145 */       if (pathOffset < 0) {
/* 146 */         return ValidationResult.error("Invalid offset for Path");
/*     */       }
/* 148 */       int pos = offset + 9 + pathOffset;
/* 149 */       if (pos >= buffer.writerIndex()) {
/* 150 */         return ValidationResult.error("Offset out of bounds for Path");
/*     */       }
/* 152 */       ValidationResult pathResult = AssetPath.validateStructure(buffer, pos);
/* 153 */       if (!pathResult.isValid()) {
/* 154 */         return ValidationResult.error("Invalid Path: " + pathResult.error());
/*     */       }
/* 156 */       pos += AssetPath.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 158 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorAsset clone() {
/* 162 */     AssetEditorAsset copy = new AssetEditorAsset();
/* 163 */     copy.hash = this.hash;
/* 164 */     copy.path = (this.path != null) ? this.path.clone() : null;
/* 165 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorAsset other;
/* 171 */     if (this == obj) return true; 
/* 172 */     if (obj instanceof AssetEditorAsset) { other = (AssetEditorAsset)obj; } else { return false; }
/* 173 */      return (Objects.equals(this.hash, other.hash) && Objects.equals(this.path, other.path));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 178 */     return Objects.hash(new Object[] { this.hash, this.path });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */