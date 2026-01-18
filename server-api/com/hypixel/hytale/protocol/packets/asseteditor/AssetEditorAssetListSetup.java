/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class AssetEditorAssetListSetup
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 319;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 4;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   
/*     */   public int getId() {
/*  25 */     return 319;
/*     */   }
/*     */   public static final int VARIABLE_BLOCK_START = 12; public static final int MAX_SIZE = 1677721600; @Nullable
/*     */   public String pack; public boolean isReadOnly;
/*     */   public boolean canBeDeleted;
/*     */   @Nonnull
/*  31 */   public AssetEditorFileTree tree = AssetEditorFileTree.Server;
/*     */   
/*     */   @Nullable
/*     */   public AssetEditorFileEntry[] paths;
/*     */ 
/*     */   
/*     */   public AssetEditorAssetListSetup(@Nullable String pack, boolean isReadOnly, boolean canBeDeleted, @Nonnull AssetEditorFileTree tree, @Nullable AssetEditorFileEntry[] paths) {
/*  38 */     this.pack = pack;
/*  39 */     this.isReadOnly = isReadOnly;
/*  40 */     this.canBeDeleted = canBeDeleted;
/*  41 */     this.tree = tree;
/*  42 */     this.paths = paths;
/*     */   }
/*     */   
/*     */   public AssetEditorAssetListSetup(@Nonnull AssetEditorAssetListSetup other) {
/*  46 */     this.pack = other.pack;
/*  47 */     this.isReadOnly = other.isReadOnly;
/*  48 */     this.canBeDeleted = other.canBeDeleted;
/*  49 */     this.tree = other.tree;
/*  50 */     this.paths = other.paths;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorAssetListSetup deserialize(@Nonnull ByteBuf buf, int offset) {
/*  55 */     AssetEditorAssetListSetup obj = new AssetEditorAssetListSetup();
/*  56 */     byte nullBits = buf.getByte(offset);
/*  57 */     obj.isReadOnly = (buf.getByte(offset + 1) != 0);
/*  58 */     obj.canBeDeleted = (buf.getByte(offset + 2) != 0);
/*  59 */     obj.tree = AssetEditorFileTree.fromValue(buf.getByte(offset + 3));
/*     */     
/*  61 */     if ((nullBits & 0x1) != 0) {
/*  62 */       int varPos0 = offset + 12 + buf.getIntLE(offset + 4);
/*  63 */       int packLen = VarInt.peek(buf, varPos0);
/*  64 */       if (packLen < 0) throw ProtocolException.negativeLength("Pack", packLen); 
/*  65 */       if (packLen > 4096000) throw ProtocolException.stringTooLong("Pack", packLen, 4096000); 
/*  66 */       obj.pack = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  68 */     if ((nullBits & 0x2) != 0) {
/*  69 */       int varPos1 = offset + 12 + buf.getIntLE(offset + 8);
/*  70 */       int pathsCount = VarInt.peek(buf, varPos1);
/*  71 */       if (pathsCount < 0) throw ProtocolException.negativeLength("Paths", pathsCount); 
/*  72 */       if (pathsCount > 4096000) throw ProtocolException.arrayTooLong("Paths", pathsCount, 4096000); 
/*  73 */       int varIntLen = VarInt.length(buf, varPos1);
/*  74 */       if ((varPos1 + varIntLen) + pathsCount * 2L > buf.readableBytes())
/*  75 */         throw ProtocolException.bufferTooSmall("Paths", varPos1 + varIntLen + pathsCount * 2, buf.readableBytes()); 
/*  76 */       obj.paths = new AssetEditorFileEntry[pathsCount];
/*  77 */       int elemPos = varPos1 + varIntLen;
/*  78 */       for (int i = 0; i < pathsCount; i++) {
/*  79 */         obj.paths[i] = AssetEditorFileEntry.deserialize(buf, elemPos);
/*  80 */         elemPos += AssetEditorFileEntry.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/*  84 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  88 */     byte nullBits = buf.getByte(offset);
/*  89 */     int maxEnd = 12;
/*  90 */     if ((nullBits & 0x1) != 0) {
/*  91 */       int fieldOffset0 = buf.getIntLE(offset + 4);
/*  92 */       int pos0 = offset + 12 + fieldOffset0;
/*  93 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  94 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  96 */     if ((nullBits & 0x2) != 0) {
/*  97 */       int fieldOffset1 = buf.getIntLE(offset + 8);
/*  98 */       int pos1 = offset + 12 + fieldOffset1;
/*  99 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 100 */       for (int i = 0; i < arrLen; ) { pos1 += AssetEditorFileEntry.computeBytesConsumed(buf, pos1); i++; }
/* 101 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 103 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 109 */     int startPos = buf.writerIndex();
/* 110 */     byte nullBits = 0;
/* 111 */     if (this.pack != null) nullBits = (byte)(nullBits | 0x1); 
/* 112 */     if (this.paths != null) nullBits = (byte)(nullBits | 0x2); 
/* 113 */     buf.writeByte(nullBits);
/*     */     
/* 115 */     buf.writeByte(this.isReadOnly ? 1 : 0);
/* 116 */     buf.writeByte(this.canBeDeleted ? 1 : 0);
/* 117 */     buf.writeByte(this.tree.getValue());
/*     */     
/* 119 */     int packOffsetSlot = buf.writerIndex();
/* 120 */     buf.writeIntLE(0);
/* 121 */     int pathsOffsetSlot = buf.writerIndex();
/* 122 */     buf.writeIntLE(0);
/*     */     
/* 124 */     int varBlockStart = buf.writerIndex();
/* 125 */     if (this.pack != null) {
/* 126 */       buf.setIntLE(packOffsetSlot, buf.writerIndex() - varBlockStart);
/* 127 */       PacketIO.writeVarString(buf, this.pack, 4096000);
/*     */     } else {
/* 129 */       buf.setIntLE(packOffsetSlot, -1);
/*     */     } 
/* 131 */     if (this.paths != null) {
/* 132 */       buf.setIntLE(pathsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 133 */       if (this.paths.length > 4096000) throw ProtocolException.arrayTooLong("Paths", this.paths.length, 4096000);  VarInt.write(buf, this.paths.length); for (AssetEditorFileEntry item : this.paths) item.serialize(buf); 
/*     */     } else {
/* 135 */       buf.setIntLE(pathsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 141 */     int size = 12;
/* 142 */     if (this.pack != null) size += PacketIO.stringSize(this.pack); 
/* 143 */     if (this.paths != null) {
/* 144 */       int pathsSize = 0;
/* 145 */       for (AssetEditorFileEntry elem : this.paths) pathsSize += elem.computeSize(); 
/* 146 */       size += VarInt.size(this.paths.length) + pathsSize;
/*     */     } 
/*     */     
/* 149 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 153 */     if (buffer.readableBytes() - offset < 12) {
/* 154 */       return ValidationResult.error("Buffer too small: expected at least 12 bytes");
/*     */     }
/*     */     
/* 157 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 160 */     if ((nullBits & 0x1) != 0) {
/* 161 */       int packOffset = buffer.getIntLE(offset + 4);
/* 162 */       if (packOffset < 0) {
/* 163 */         return ValidationResult.error("Invalid offset for Pack");
/*     */       }
/* 165 */       int pos = offset + 12 + packOffset;
/* 166 */       if (pos >= buffer.writerIndex()) {
/* 167 */         return ValidationResult.error("Offset out of bounds for Pack");
/*     */       }
/* 169 */       int packLen = VarInt.peek(buffer, pos);
/* 170 */       if (packLen < 0) {
/* 171 */         return ValidationResult.error("Invalid string length for Pack");
/*     */       }
/* 173 */       if (packLen > 4096000) {
/* 174 */         return ValidationResult.error("Pack exceeds max length 4096000");
/*     */       }
/* 176 */       pos += VarInt.length(buffer, pos);
/* 177 */       pos += packLen;
/* 178 */       if (pos > buffer.writerIndex()) {
/* 179 */         return ValidationResult.error("Buffer overflow reading Pack");
/*     */       }
/*     */     } 
/*     */     
/* 183 */     if ((nullBits & 0x2) != 0) {
/* 184 */       int pathsOffset = buffer.getIntLE(offset + 8);
/* 185 */       if (pathsOffset < 0) {
/* 186 */         return ValidationResult.error("Invalid offset for Paths");
/*     */       }
/* 188 */       int pos = offset + 12 + pathsOffset;
/* 189 */       if (pos >= buffer.writerIndex()) {
/* 190 */         return ValidationResult.error("Offset out of bounds for Paths");
/*     */       }
/* 192 */       int pathsCount = VarInt.peek(buffer, pos);
/* 193 */       if (pathsCount < 0) {
/* 194 */         return ValidationResult.error("Invalid array count for Paths");
/*     */       }
/* 196 */       if (pathsCount > 4096000) {
/* 197 */         return ValidationResult.error("Paths exceeds max length 4096000");
/*     */       }
/* 199 */       pos += VarInt.length(buffer, pos);
/* 200 */       for (int i = 0; i < pathsCount; i++) {
/* 201 */         ValidationResult structResult = AssetEditorFileEntry.validateStructure(buffer, pos);
/* 202 */         if (!structResult.isValid()) {
/* 203 */           return ValidationResult.error("Invalid AssetEditorFileEntry in Paths[" + i + "]: " + structResult.error());
/*     */         }
/* 205 */         pos += AssetEditorFileEntry.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 208 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorAssetListSetup clone() {
/* 212 */     AssetEditorAssetListSetup copy = new AssetEditorAssetListSetup();
/* 213 */     copy.pack = this.pack;
/* 214 */     copy.isReadOnly = this.isReadOnly;
/* 215 */     copy.canBeDeleted = this.canBeDeleted;
/* 216 */     copy.tree = this.tree;
/* 217 */     copy.paths = (this.paths != null) ? (AssetEditorFileEntry[])Arrays.<AssetEditorFileEntry>stream(this.paths).map(e -> e.clone()).toArray(x$0 -> new AssetEditorFileEntry[x$0]) : null;
/* 218 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorAssetListSetup other;
/* 224 */     if (this == obj) return true; 
/* 225 */     if (obj instanceof AssetEditorAssetListSetup) { other = (AssetEditorAssetListSetup)obj; } else { return false; }
/* 226 */      return (Objects.equals(this.pack, other.pack) && this.isReadOnly == other.isReadOnly && this.canBeDeleted == other.canBeDeleted && Objects.equals(this.tree, other.tree) && Arrays.equals((Object[])this.paths, (Object[])other.paths));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 231 */     int result = 1;
/* 232 */     result = 31 * result + Objects.hashCode(this.pack);
/* 233 */     result = 31 * result + Boolean.hashCode(this.isReadOnly);
/* 234 */     result = 31 * result + Boolean.hashCode(this.canBeDeleted);
/* 235 */     result = 31 * result + Objects.hashCode(this.tree);
/* 236 */     result = 31 * result + Arrays.hashCode((Object[])this.paths);
/* 237 */     return result;
/*     */   }
/*     */   
/*     */   public AssetEditorAssetListSetup() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorAssetListSetup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */