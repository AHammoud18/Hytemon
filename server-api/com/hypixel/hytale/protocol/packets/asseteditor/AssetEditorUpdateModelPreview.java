/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.BlockType;
/*     */ import com.hypixel.hytale.protocol.Model;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AssetEditorUpdateModelPreview
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 355;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 30;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   
/*     */   public int getId() {
/*  26 */     return 355;
/*     */   }
/*     */   public static final int VARIABLE_BLOCK_START = 42; public static final int MAX_SIZE = 1677721600; @Nullable
/*     */   public AssetPath assetPath; @Nullable
/*     */   public Model model; @Nullable
/*     */   public BlockType block;
/*     */   @Nullable
/*     */   public AssetEditorPreviewCameraSettings camera;
/*     */   
/*     */   public AssetEditorUpdateModelPreview() {}
/*     */   
/*     */   public AssetEditorUpdateModelPreview(@Nullable AssetPath assetPath, @Nullable Model model, @Nullable BlockType block, @Nullable AssetEditorPreviewCameraSettings camera) {
/*  38 */     this.assetPath = assetPath;
/*  39 */     this.model = model;
/*  40 */     this.block = block;
/*  41 */     this.camera = camera;
/*     */   }
/*     */   
/*     */   public AssetEditorUpdateModelPreview(@Nonnull AssetEditorUpdateModelPreview other) {
/*  45 */     this.assetPath = other.assetPath;
/*  46 */     this.model = other.model;
/*  47 */     this.block = other.block;
/*  48 */     this.camera = other.camera;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorUpdateModelPreview deserialize(@Nonnull ByteBuf buf, int offset) {
/*  53 */     AssetEditorUpdateModelPreview obj = new AssetEditorUpdateModelPreview();
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     if ((nullBits & 0x8) != 0) obj.camera = AssetEditorPreviewCameraSettings.deserialize(buf, offset + 1);
/*     */     
/*  57 */     if ((nullBits & 0x1) != 0) {
/*  58 */       int varPos0 = offset + 42 + buf.getIntLE(offset + 30);
/*  59 */       obj.assetPath = AssetPath.deserialize(buf, varPos0);
/*     */     } 
/*  61 */     if ((nullBits & 0x2) != 0) {
/*  62 */       int varPos1 = offset + 42 + buf.getIntLE(offset + 34);
/*  63 */       obj.model = Model.deserialize(buf, varPos1);
/*     */     } 
/*  65 */     if ((nullBits & 0x4) != 0) {
/*  66 */       int varPos2 = offset + 42 + buf.getIntLE(offset + 38);
/*  67 */       obj.block = BlockType.deserialize(buf, varPos2);
/*     */     } 
/*     */     
/*  70 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  74 */     byte nullBits = buf.getByte(offset);
/*  75 */     int maxEnd = 42;
/*  76 */     if ((nullBits & 0x1) != 0) {
/*  77 */       int fieldOffset0 = buf.getIntLE(offset + 30);
/*  78 */       int pos0 = offset + 42 + fieldOffset0;
/*  79 */       pos0 += AssetPath.computeBytesConsumed(buf, pos0);
/*  80 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  82 */     if ((nullBits & 0x2) != 0) {
/*  83 */       int fieldOffset1 = buf.getIntLE(offset + 34);
/*  84 */       int pos1 = offset + 42 + fieldOffset1;
/*  85 */       pos1 += Model.computeBytesConsumed(buf, pos1);
/*  86 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  88 */     if ((nullBits & 0x4) != 0) {
/*  89 */       int fieldOffset2 = buf.getIntLE(offset + 38);
/*  90 */       int pos2 = offset + 42 + fieldOffset2;
/*  91 */       pos2 += BlockType.computeBytesConsumed(buf, pos2);
/*  92 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/*  94 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 100 */     int startPos = buf.writerIndex();
/* 101 */     byte nullBits = 0;
/* 102 */     if (this.assetPath != null) nullBits = (byte)(nullBits | 0x1); 
/* 103 */     if (this.model != null) nullBits = (byte)(nullBits | 0x2); 
/* 104 */     if (this.block != null) nullBits = (byte)(nullBits | 0x4); 
/* 105 */     if (this.camera != null) nullBits = (byte)(nullBits | 0x8); 
/* 106 */     buf.writeByte(nullBits);
/*     */     
/* 108 */     if (this.camera != null) { this.camera.serialize(buf); } else { buf.writeZero(29); }
/*     */     
/* 110 */     int assetPathOffsetSlot = buf.writerIndex();
/* 111 */     buf.writeIntLE(0);
/* 112 */     int modelOffsetSlot = buf.writerIndex();
/* 113 */     buf.writeIntLE(0);
/* 114 */     int blockOffsetSlot = buf.writerIndex();
/* 115 */     buf.writeIntLE(0);
/*     */     
/* 117 */     int varBlockStart = buf.writerIndex();
/* 118 */     if (this.assetPath != null) {
/* 119 */       buf.setIntLE(assetPathOffsetSlot, buf.writerIndex() - varBlockStart);
/* 120 */       this.assetPath.serialize(buf);
/*     */     } else {
/* 122 */       buf.setIntLE(assetPathOffsetSlot, -1);
/*     */     } 
/* 124 */     if (this.model != null) {
/* 125 */       buf.setIntLE(modelOffsetSlot, buf.writerIndex() - varBlockStart);
/* 126 */       this.model.serialize(buf);
/*     */     } else {
/* 128 */       buf.setIntLE(modelOffsetSlot, -1);
/*     */     } 
/* 130 */     if (this.block != null) {
/* 131 */       buf.setIntLE(blockOffsetSlot, buf.writerIndex() - varBlockStart);
/* 132 */       this.block.serialize(buf);
/*     */     } else {
/* 134 */       buf.setIntLE(blockOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 140 */     int size = 42;
/* 141 */     if (this.assetPath != null) size += this.assetPath.computeSize(); 
/* 142 */     if (this.model != null) size += this.model.computeSize(); 
/* 143 */     if (this.block != null) size += this.block.computeSize();
/*     */     
/* 145 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 149 */     if (buffer.readableBytes() - offset < 42) {
/* 150 */       return ValidationResult.error("Buffer too small: expected at least 42 bytes");
/*     */     }
/*     */     
/* 153 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 156 */     if ((nullBits & 0x1) != 0) {
/* 157 */       int assetPathOffset = buffer.getIntLE(offset + 30);
/* 158 */       if (assetPathOffset < 0) {
/* 159 */         return ValidationResult.error("Invalid offset for AssetPath");
/*     */       }
/* 161 */       int pos = offset + 42 + assetPathOffset;
/* 162 */       if (pos >= buffer.writerIndex()) {
/* 163 */         return ValidationResult.error("Offset out of bounds for AssetPath");
/*     */       }
/* 165 */       ValidationResult assetPathResult = AssetPath.validateStructure(buffer, pos);
/* 166 */       if (!assetPathResult.isValid()) {
/* 167 */         return ValidationResult.error("Invalid AssetPath: " + assetPathResult.error());
/*     */       }
/* 169 */       pos += AssetPath.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 172 */     if ((nullBits & 0x2) != 0) {
/* 173 */       int modelOffset = buffer.getIntLE(offset + 34);
/* 174 */       if (modelOffset < 0) {
/* 175 */         return ValidationResult.error("Invalid offset for Model");
/*     */       }
/* 177 */       int pos = offset + 42 + modelOffset;
/* 178 */       if (pos >= buffer.writerIndex()) {
/* 179 */         return ValidationResult.error("Offset out of bounds for Model");
/*     */       }
/* 181 */       ValidationResult modelResult = Model.validateStructure(buffer, pos);
/* 182 */       if (!modelResult.isValid()) {
/* 183 */         return ValidationResult.error("Invalid Model: " + modelResult.error());
/*     */       }
/* 185 */       pos += Model.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 188 */     if ((nullBits & 0x4) != 0) {
/* 189 */       int blockOffset = buffer.getIntLE(offset + 38);
/* 190 */       if (blockOffset < 0) {
/* 191 */         return ValidationResult.error("Invalid offset for Block");
/*     */       }
/* 193 */       int pos = offset + 42 + blockOffset;
/* 194 */       if (pos >= buffer.writerIndex()) {
/* 195 */         return ValidationResult.error("Offset out of bounds for Block");
/*     */       }
/* 197 */       ValidationResult blockResult = BlockType.validateStructure(buffer, pos);
/* 198 */       if (!blockResult.isValid()) {
/* 199 */         return ValidationResult.error("Invalid Block: " + blockResult.error());
/*     */       }
/* 201 */       pos += BlockType.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 203 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorUpdateModelPreview clone() {
/* 207 */     AssetEditorUpdateModelPreview copy = new AssetEditorUpdateModelPreview();
/* 208 */     copy.assetPath = (this.assetPath != null) ? this.assetPath.clone() : null;
/* 209 */     copy.model = (this.model != null) ? this.model.clone() : null;
/* 210 */     copy.block = (this.block != null) ? this.block.clone() : null;
/* 211 */     copy.camera = (this.camera != null) ? this.camera.clone() : null;
/* 212 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorUpdateModelPreview other;
/* 218 */     if (this == obj) return true; 
/* 219 */     if (obj instanceof AssetEditorUpdateModelPreview) { other = (AssetEditorUpdateModelPreview)obj; } else { return false; }
/* 220 */      return (Objects.equals(this.assetPath, other.assetPath) && Objects.equals(this.model, other.model) && Objects.equals(this.block, other.block) && Objects.equals(this.camera, other.camera));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 225 */     return Objects.hash(new Object[] { this.assetPath, this.model, this.block, this.camera });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorUpdateModelPreview.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */