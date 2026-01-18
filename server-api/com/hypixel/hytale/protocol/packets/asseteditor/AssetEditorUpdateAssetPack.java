/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AssetEditorUpdateAssetPack
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 315;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   
/*     */   public int getId() {
/*  25 */     return 315;
/*     */   }
/*     */   public static final int VARIABLE_FIELD_COUNT = 2; public static final int VARIABLE_BLOCK_START = 9; public static final int MAX_SIZE = 1677721600; @Nullable
/*     */   public String id;
/*     */   @Nullable
/*     */   public AssetPackManifest manifest;
/*     */   
/*     */   public AssetEditorUpdateAssetPack() {}
/*     */   
/*     */   public AssetEditorUpdateAssetPack(@Nullable String id, @Nullable AssetPackManifest manifest) {
/*  35 */     this.id = id;
/*  36 */     this.manifest = manifest;
/*     */   }
/*     */   
/*     */   public AssetEditorUpdateAssetPack(@Nonnull AssetEditorUpdateAssetPack other) {
/*  40 */     this.id = other.id;
/*  41 */     this.manifest = other.manifest;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorUpdateAssetPack deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     AssetEditorUpdateAssetPack obj = new AssetEditorUpdateAssetPack();
/*  47 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  49 */     if ((nullBits & 0x1) != 0) {
/*  50 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  51 */       int idLen = VarInt.peek(buf, varPos0);
/*  52 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  53 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  54 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  56 */     if ((nullBits & 0x2) != 0) {
/*  57 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  58 */       obj.manifest = AssetPackManifest.deserialize(buf, varPos1);
/*     */     } 
/*     */     
/*  61 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  65 */     byte nullBits = buf.getByte(offset);
/*  66 */     int maxEnd = 9;
/*  67 */     if ((nullBits & 0x1) != 0) {
/*  68 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  69 */       int pos0 = offset + 9 + fieldOffset0;
/*  70 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  71 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  73 */     if ((nullBits & 0x2) != 0) {
/*  74 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  75 */       int pos1 = offset + 9 + fieldOffset1;
/*  76 */       pos1 += AssetPackManifest.computeBytesConsumed(buf, pos1);
/*  77 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  79 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  85 */     int startPos = buf.writerIndex();
/*  86 */     byte nullBits = 0;
/*  87 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/*  88 */     if (this.manifest != null) nullBits = (byte)(nullBits | 0x2); 
/*  89 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  92 */     int idOffsetSlot = buf.writerIndex();
/*  93 */     buf.writeIntLE(0);
/*  94 */     int manifestOffsetSlot = buf.writerIndex();
/*  95 */     buf.writeIntLE(0);
/*     */     
/*  97 */     int varBlockStart = buf.writerIndex();
/*  98 */     if (this.id != null) {
/*  99 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 100 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 102 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 104 */     if (this.manifest != null) {
/* 105 */       buf.setIntLE(manifestOffsetSlot, buf.writerIndex() - varBlockStart);
/* 106 */       this.manifest.serialize(buf);
/*     */     } else {
/* 108 */       buf.setIntLE(manifestOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 114 */     int size = 9;
/* 115 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 116 */     if (this.manifest != null) size += this.manifest.computeSize();
/*     */     
/* 118 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 122 */     if (buffer.readableBytes() - offset < 9) {
/* 123 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 126 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 129 */     if ((nullBits & 0x1) != 0) {
/* 130 */       int idOffset = buffer.getIntLE(offset + 1);
/* 131 */       if (idOffset < 0) {
/* 132 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 134 */       int pos = offset + 9 + idOffset;
/* 135 */       if (pos >= buffer.writerIndex()) {
/* 136 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 138 */       int idLen = VarInt.peek(buffer, pos);
/* 139 */       if (idLen < 0) {
/* 140 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 142 */       if (idLen > 4096000) {
/* 143 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 145 */       pos += VarInt.length(buffer, pos);
/* 146 */       pos += idLen;
/* 147 */       if (pos > buffer.writerIndex()) {
/* 148 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 152 */     if ((nullBits & 0x2) != 0) {
/* 153 */       int manifestOffset = buffer.getIntLE(offset + 5);
/* 154 */       if (manifestOffset < 0) {
/* 155 */         return ValidationResult.error("Invalid offset for Manifest");
/*     */       }
/* 157 */       int pos = offset + 9 + manifestOffset;
/* 158 */       if (pos >= buffer.writerIndex()) {
/* 159 */         return ValidationResult.error("Offset out of bounds for Manifest");
/*     */       }
/* 161 */       ValidationResult manifestResult = AssetPackManifest.validateStructure(buffer, pos);
/* 162 */       if (!manifestResult.isValid()) {
/* 163 */         return ValidationResult.error("Invalid Manifest: " + manifestResult.error());
/*     */       }
/* 165 */       pos += AssetPackManifest.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 167 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorUpdateAssetPack clone() {
/* 171 */     AssetEditorUpdateAssetPack copy = new AssetEditorUpdateAssetPack();
/* 172 */     copy.id = this.id;
/* 173 */     copy.manifest = (this.manifest != null) ? this.manifest.clone() : null;
/* 174 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorUpdateAssetPack other;
/* 180 */     if (this == obj) return true; 
/* 181 */     if (obj instanceof AssetEditorUpdateAssetPack) { other = (AssetEditorUpdateAssetPack)obj; } else { return false; }
/* 182 */      return (Objects.equals(this.id, other.id) && Objects.equals(this.manifest, other.manifest));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 187 */     return Objects.hash(new Object[] { this.id, this.manifest });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorUpdateAssetPack.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */