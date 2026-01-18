/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class AssetEditorExportAssetPart
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 344;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 4096006;
/*     */   @Nullable
/*     */   public byte[] part;
/*     */   
/*     */   public int getId() {
/*  25 */     return 344;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorExportAssetPart() {}
/*     */ 
/*     */   
/*     */   public AssetEditorExportAssetPart(@Nullable byte[] part) {
/*  34 */     this.part = part;
/*     */   }
/*     */   
/*     */   public AssetEditorExportAssetPart(@Nonnull AssetEditorExportAssetPart other) {
/*  38 */     this.part = other.part;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorExportAssetPart deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     AssetEditorExportAssetPart obj = new AssetEditorExportAssetPart();
/*  44 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  46 */     int pos = offset + 1;
/*  47 */     if ((nullBits & 0x1) != 0) { int partCount = VarInt.peek(buf, pos);
/*  48 */       if (partCount < 0) throw ProtocolException.negativeLength("Part", partCount); 
/*  49 */       if (partCount > 4096000) throw ProtocolException.arrayTooLong("Part", partCount, 4096000); 
/*  50 */       int partVarLen = VarInt.size(partCount);
/*  51 */       if ((pos + partVarLen) + partCount * 1L > buf.readableBytes())
/*  52 */         throw ProtocolException.bufferTooSmall("Part", pos + partVarLen + partCount * 1, buf.readableBytes()); 
/*  53 */       pos += partVarLen;
/*  54 */       obj.part = new byte[partCount];
/*  55 */       for (int i = 0; i < partCount; i++) {
/*  56 */         obj.part[i] = buf.getByte(pos + i * 1);
/*     */       }
/*  58 */       pos += partCount * 1; }
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     byte nullBits = buf.getByte(offset);
/*  65 */     int pos = offset + 1;
/*  66 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + arrLen * 1; }
/*  67 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  73 */     byte nullBits = 0;
/*  74 */     if (this.part != null) nullBits = (byte)(nullBits | 0x1); 
/*  75 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  78 */     if (this.part != null) { if (this.part.length > 4096000) throw ProtocolException.arrayTooLong("Part", this.part.length, 4096000);  VarInt.write(buf, this.part.length); for (byte item : this.part) buf.writeByte(item);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  83 */     int size = 1;
/*  84 */     if (this.part != null) size += VarInt.size(this.part.length) + this.part.length * 1;
/*     */     
/*  86 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  90 */     if (buffer.readableBytes() - offset < 1) {
/*  91 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  94 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  96 */     int pos = offset + 1;
/*     */     
/*  98 */     if ((nullBits & 0x1) != 0) {
/*  99 */       int partCount = VarInt.peek(buffer, pos);
/* 100 */       if (partCount < 0) {
/* 101 */         return ValidationResult.error("Invalid array count for Part");
/*     */       }
/* 103 */       if (partCount > 4096000) {
/* 104 */         return ValidationResult.error("Part exceeds max length 4096000");
/*     */       }
/* 106 */       pos += VarInt.length(buffer, pos);
/* 107 */       pos += partCount * 1;
/* 108 */       if (pos > buffer.writerIndex()) {
/* 109 */         return ValidationResult.error("Buffer overflow reading Part");
/*     */       }
/*     */     } 
/* 112 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorExportAssetPart clone() {
/* 116 */     AssetEditorExportAssetPart copy = new AssetEditorExportAssetPart();
/* 117 */     copy.part = (this.part != null) ? Arrays.copyOf(this.part, this.part.length) : null;
/* 118 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorExportAssetPart other;
/* 124 */     if (this == obj) return true; 
/* 125 */     if (obj instanceof AssetEditorExportAssetPart) { other = (AssetEditorExportAssetPart)obj; } else { return false; }
/* 126 */      return Arrays.equals(this.part, other.part);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 131 */     int result = 1;
/* 132 */     result = 31 * result + Arrays.hashCode(this.part);
/* 133 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorExportAssetPart.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */