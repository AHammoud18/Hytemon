/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class AssetEditorRenameAsset implements Packet {
/*     */   public static final int PACKET_ID = 328;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 13;
/*     */   public static final int MAX_SIZE = 65536051;
/*     */   public int token;
/*     */   @Nullable
/*     */   public AssetPath path;
/*     */   @Nullable
/*     */   public AssetPath newPath;
/*     */   
/*     */   public int getId() {
/*  25 */     return 328;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorRenameAsset() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorRenameAsset(int token, @Nullable AssetPath path, @Nullable AssetPath newPath) {
/*  36 */     this.token = token;
/*  37 */     this.path = path;
/*  38 */     this.newPath = newPath;
/*     */   }
/*     */   
/*     */   public AssetEditorRenameAsset(@Nonnull AssetEditorRenameAsset other) {
/*  42 */     this.token = other.token;
/*  43 */     this.path = other.path;
/*  44 */     this.newPath = other.newPath;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorRenameAsset deserialize(@Nonnull ByteBuf buf, int offset) {
/*  49 */     AssetEditorRenameAsset obj = new AssetEditorRenameAsset();
/*  50 */     byte nullBits = buf.getByte(offset);
/*  51 */     obj.token = buf.getIntLE(offset + 1);
/*     */     
/*  53 */     if ((nullBits & 0x1) != 0) {
/*  54 */       int varPos0 = offset + 13 + buf.getIntLE(offset + 5);
/*  55 */       obj.path = AssetPath.deserialize(buf, varPos0);
/*     */     } 
/*  57 */     if ((nullBits & 0x2) != 0) {
/*  58 */       int varPos1 = offset + 13 + buf.getIntLE(offset + 9);
/*  59 */       obj.newPath = AssetPath.deserialize(buf, varPos1);
/*     */     } 
/*     */     
/*  62 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  66 */     byte nullBits = buf.getByte(offset);
/*  67 */     int maxEnd = 13;
/*  68 */     if ((nullBits & 0x1) != 0) {
/*  69 */       int fieldOffset0 = buf.getIntLE(offset + 5);
/*  70 */       int pos0 = offset + 13 + fieldOffset0;
/*  71 */       pos0 += AssetPath.computeBytesConsumed(buf, pos0);
/*  72 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  74 */     if ((nullBits & 0x2) != 0) {
/*  75 */       int fieldOffset1 = buf.getIntLE(offset + 9);
/*  76 */       int pos1 = offset + 13 + fieldOffset1;
/*  77 */       pos1 += AssetPath.computeBytesConsumed(buf, pos1);
/*  78 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  80 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  86 */     int startPos = buf.writerIndex();
/*  87 */     byte nullBits = 0;
/*  88 */     if (this.path != null) nullBits = (byte)(nullBits | 0x1); 
/*  89 */     if (this.newPath != null) nullBits = (byte)(nullBits | 0x2); 
/*  90 */     buf.writeByte(nullBits);
/*     */     
/*  92 */     buf.writeIntLE(this.token);
/*     */     
/*  94 */     int pathOffsetSlot = buf.writerIndex();
/*  95 */     buf.writeIntLE(0);
/*  96 */     int newPathOffsetSlot = buf.writerIndex();
/*  97 */     buf.writeIntLE(0);
/*     */     
/*  99 */     int varBlockStart = buf.writerIndex();
/* 100 */     if (this.path != null) {
/* 101 */       buf.setIntLE(pathOffsetSlot, buf.writerIndex() - varBlockStart);
/* 102 */       this.path.serialize(buf);
/*     */     } else {
/* 104 */       buf.setIntLE(pathOffsetSlot, -1);
/*     */     } 
/* 106 */     if (this.newPath != null) {
/* 107 */       buf.setIntLE(newPathOffsetSlot, buf.writerIndex() - varBlockStart);
/* 108 */       this.newPath.serialize(buf);
/*     */     } else {
/* 110 */       buf.setIntLE(newPathOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 116 */     int size = 13;
/* 117 */     if (this.path != null) size += this.path.computeSize(); 
/* 118 */     if (this.newPath != null) size += this.newPath.computeSize();
/*     */     
/* 120 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 124 */     if (buffer.readableBytes() - offset < 13) {
/* 125 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */     
/* 128 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 131 */     if ((nullBits & 0x1) != 0) {
/* 132 */       int pathOffset = buffer.getIntLE(offset + 5);
/* 133 */       if (pathOffset < 0) {
/* 134 */         return ValidationResult.error("Invalid offset for Path");
/*     */       }
/* 136 */       int pos = offset + 13 + pathOffset;
/* 137 */       if (pos >= buffer.writerIndex()) {
/* 138 */         return ValidationResult.error("Offset out of bounds for Path");
/*     */       }
/* 140 */       ValidationResult pathResult = AssetPath.validateStructure(buffer, pos);
/* 141 */       if (!pathResult.isValid()) {
/* 142 */         return ValidationResult.error("Invalid Path: " + pathResult.error());
/*     */       }
/* 144 */       pos += AssetPath.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 147 */     if ((nullBits & 0x2) != 0) {
/* 148 */       int newPathOffset = buffer.getIntLE(offset + 9);
/* 149 */       if (newPathOffset < 0) {
/* 150 */         return ValidationResult.error("Invalid offset for NewPath");
/*     */       }
/* 152 */       int pos = offset + 13 + newPathOffset;
/* 153 */       if (pos >= buffer.writerIndex()) {
/* 154 */         return ValidationResult.error("Offset out of bounds for NewPath");
/*     */       }
/* 156 */       ValidationResult newPathResult = AssetPath.validateStructure(buffer, pos);
/* 157 */       if (!newPathResult.isValid()) {
/* 158 */         return ValidationResult.error("Invalid NewPath: " + newPathResult.error());
/*     */       }
/* 160 */       pos += AssetPath.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 162 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorRenameAsset clone() {
/* 166 */     AssetEditorRenameAsset copy = new AssetEditorRenameAsset();
/* 167 */     copy.token = this.token;
/* 168 */     copy.path = (this.path != null) ? this.path.clone() : null;
/* 169 */     copy.newPath = (this.newPath != null) ? this.newPath.clone() : null;
/* 170 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorRenameAsset other;
/* 176 */     if (this == obj) return true; 
/* 177 */     if (obj instanceof AssetEditorRenameAsset) { other = (AssetEditorRenameAsset)obj; } else { return false; }
/* 178 */      return (this.token == other.token && Objects.equals(this.path, other.path) && Objects.equals(this.newPath, other.newPath));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 183 */     return Objects.hash(new Object[] { Integer.valueOf(this.token), this.path, this.newPath });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorRenameAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */