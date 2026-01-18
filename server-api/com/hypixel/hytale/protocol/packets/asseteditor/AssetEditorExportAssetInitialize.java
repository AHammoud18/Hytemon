/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class AssetEditorExportAssetInitialize implements Packet {
/*     */   public static final int PACKET_ID = 343;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 14;
/*     */   public static final int MAX_SIZE = 81920066;
/*     */   @Nullable
/*     */   public AssetEditorAsset asset;
/*     */   @Nullable
/*     */   public AssetPath oldPath;
/*     */   public int size;
/*     */   public boolean failed;
/*     */   
/*     */   public int getId() {
/*  25 */     return 343;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorExportAssetInitialize() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorExportAssetInitialize(@Nullable AssetEditorAsset asset, @Nullable AssetPath oldPath, int size, boolean failed) {
/*  37 */     this.asset = asset;
/*  38 */     this.oldPath = oldPath;
/*  39 */     this.size = size;
/*  40 */     this.failed = failed;
/*     */   }
/*     */   
/*     */   public AssetEditorExportAssetInitialize(@Nonnull AssetEditorExportAssetInitialize other) {
/*  44 */     this.asset = other.asset;
/*  45 */     this.oldPath = other.oldPath;
/*  46 */     this.size = other.size;
/*  47 */     this.failed = other.failed;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorExportAssetInitialize deserialize(@Nonnull ByteBuf buf, int offset) {
/*  52 */     AssetEditorExportAssetInitialize obj = new AssetEditorExportAssetInitialize();
/*  53 */     byte nullBits = buf.getByte(offset);
/*  54 */     obj.size = buf.getIntLE(offset + 1);
/*  55 */     obj.failed = (buf.getByte(offset + 5) != 0);
/*     */     
/*  57 */     if ((nullBits & 0x1) != 0) {
/*  58 */       int varPos0 = offset + 14 + buf.getIntLE(offset + 6);
/*  59 */       obj.asset = AssetEditorAsset.deserialize(buf, varPos0);
/*     */     } 
/*  61 */     if ((nullBits & 0x2) != 0) {
/*  62 */       int varPos1 = offset + 14 + buf.getIntLE(offset + 10);
/*  63 */       obj.oldPath = AssetPath.deserialize(buf, varPos1);
/*     */     } 
/*     */     
/*  66 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  70 */     byte nullBits = buf.getByte(offset);
/*  71 */     int maxEnd = 14;
/*  72 */     if ((nullBits & 0x1) != 0) {
/*  73 */       int fieldOffset0 = buf.getIntLE(offset + 6);
/*  74 */       int pos0 = offset + 14 + fieldOffset0;
/*  75 */       pos0 += AssetEditorAsset.computeBytesConsumed(buf, pos0);
/*  76 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  78 */     if ((nullBits & 0x2) != 0) {
/*  79 */       int fieldOffset1 = buf.getIntLE(offset + 10);
/*  80 */       int pos1 = offset + 14 + fieldOffset1;
/*  81 */       pos1 += AssetPath.computeBytesConsumed(buf, pos1);
/*  82 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  84 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  90 */     int startPos = buf.writerIndex();
/*  91 */     byte nullBits = 0;
/*  92 */     if (this.asset != null) nullBits = (byte)(nullBits | 0x1); 
/*  93 */     if (this.oldPath != null) nullBits = (byte)(nullBits | 0x2); 
/*  94 */     buf.writeByte(nullBits);
/*     */     
/*  96 */     buf.writeIntLE(this.size);
/*  97 */     buf.writeByte(this.failed ? 1 : 0);
/*     */     
/*  99 */     int assetOffsetSlot = buf.writerIndex();
/* 100 */     buf.writeIntLE(0);
/* 101 */     int oldPathOffsetSlot = buf.writerIndex();
/* 102 */     buf.writeIntLE(0);
/*     */     
/* 104 */     int varBlockStart = buf.writerIndex();
/* 105 */     if (this.asset != null) {
/* 106 */       buf.setIntLE(assetOffsetSlot, buf.writerIndex() - varBlockStart);
/* 107 */       this.asset.serialize(buf);
/*     */     } else {
/* 109 */       buf.setIntLE(assetOffsetSlot, -1);
/*     */     } 
/* 111 */     if (this.oldPath != null) {
/* 112 */       buf.setIntLE(oldPathOffsetSlot, buf.writerIndex() - varBlockStart);
/* 113 */       this.oldPath.serialize(buf);
/*     */     } else {
/* 115 */       buf.setIntLE(oldPathOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 121 */     int size = 14;
/* 122 */     if (this.asset != null) size += this.asset.computeSize(); 
/* 123 */     if (this.oldPath != null) size += this.oldPath.computeSize();
/*     */     
/* 125 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 129 */     if (buffer.readableBytes() - offset < 14) {
/* 130 */       return ValidationResult.error("Buffer too small: expected at least 14 bytes");
/*     */     }
/*     */     
/* 133 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 136 */     if ((nullBits & 0x1) != 0) {
/* 137 */       int assetOffset = buffer.getIntLE(offset + 6);
/* 138 */       if (assetOffset < 0) {
/* 139 */         return ValidationResult.error("Invalid offset for Asset");
/*     */       }
/* 141 */       int pos = offset + 14 + assetOffset;
/* 142 */       if (pos >= buffer.writerIndex()) {
/* 143 */         return ValidationResult.error("Offset out of bounds for Asset");
/*     */       }
/* 145 */       ValidationResult assetResult = AssetEditorAsset.validateStructure(buffer, pos);
/* 146 */       if (!assetResult.isValid()) {
/* 147 */         return ValidationResult.error("Invalid Asset: " + assetResult.error());
/*     */       }
/* 149 */       pos += AssetEditorAsset.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 152 */     if ((nullBits & 0x2) != 0) {
/* 153 */       int oldPathOffset = buffer.getIntLE(offset + 10);
/* 154 */       if (oldPathOffset < 0) {
/* 155 */         return ValidationResult.error("Invalid offset for OldPath");
/*     */       }
/* 157 */       int pos = offset + 14 + oldPathOffset;
/* 158 */       if (pos >= buffer.writerIndex()) {
/* 159 */         return ValidationResult.error("Offset out of bounds for OldPath");
/*     */       }
/* 161 */       ValidationResult oldPathResult = AssetPath.validateStructure(buffer, pos);
/* 162 */       if (!oldPathResult.isValid()) {
/* 163 */         return ValidationResult.error("Invalid OldPath: " + oldPathResult.error());
/*     */       }
/* 165 */       pos += AssetPath.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 167 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorExportAssetInitialize clone() {
/* 171 */     AssetEditorExportAssetInitialize copy = new AssetEditorExportAssetInitialize();
/* 172 */     copy.asset = (this.asset != null) ? this.asset.clone() : null;
/* 173 */     copy.oldPath = (this.oldPath != null) ? this.oldPath.clone() : null;
/* 174 */     copy.size = this.size;
/* 175 */     copy.failed = this.failed;
/* 176 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorExportAssetInitialize other;
/* 182 */     if (this == obj) return true; 
/* 183 */     if (obj instanceof AssetEditorExportAssetInitialize) { other = (AssetEditorExportAssetInitialize)obj; } else { return false; }
/* 184 */      return (Objects.equals(this.asset, other.asset) && Objects.equals(this.oldPath, other.oldPath) && this.size == other.size && this.failed == other.failed);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 189 */     return Objects.hash(new Object[] { this.asset, this.oldPath, Integer.valueOf(this.size), Boolean.valueOf(this.failed) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorExportAssetInitialize.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */