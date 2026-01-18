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
/*     */ public class AssetEditorAssetListUpdate
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 320;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   
/*     */   public int getId() {
/*  25 */     return 320;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int VARIABLE_BLOCK_START = 13;
/*     */   
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public String pack;
/*     */   
/*     */   public AssetEditorAssetListUpdate(@Nullable String pack, @Nullable AssetEditorFileEntry[] additions, @Nullable AssetEditorFileEntry[] deletions) {
/*  36 */     this.pack = pack;
/*  37 */     this.additions = additions;
/*  38 */     this.deletions = deletions;
/*     */   } @Nullable
/*     */   public AssetEditorFileEntry[] additions; @Nullable
/*     */   public AssetEditorFileEntry[] deletions; public AssetEditorAssetListUpdate() {} public AssetEditorAssetListUpdate(@Nonnull AssetEditorAssetListUpdate other) {
/*  42 */     this.pack = other.pack;
/*  43 */     this.additions = other.additions;
/*  44 */     this.deletions = other.deletions;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorAssetListUpdate deserialize(@Nonnull ByteBuf buf, int offset) {
/*  49 */     AssetEditorAssetListUpdate obj = new AssetEditorAssetListUpdate();
/*  50 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  52 */     if ((nullBits & 0x1) != 0) {
/*  53 */       int varPos0 = offset + 13 + buf.getIntLE(offset + 1);
/*  54 */       int packLen = VarInt.peek(buf, varPos0);
/*  55 */       if (packLen < 0) throw ProtocolException.negativeLength("Pack", packLen); 
/*  56 */       if (packLen > 4096000) throw ProtocolException.stringTooLong("Pack", packLen, 4096000); 
/*  57 */       obj.pack = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  59 */     if ((nullBits & 0x2) != 0) {
/*  60 */       int varPos1 = offset + 13 + buf.getIntLE(offset + 5);
/*  61 */       int additionsCount = VarInt.peek(buf, varPos1);
/*  62 */       if (additionsCount < 0) throw ProtocolException.negativeLength("Additions", additionsCount); 
/*  63 */       if (additionsCount > 4096000) throw ProtocolException.arrayTooLong("Additions", additionsCount, 4096000); 
/*  64 */       int varIntLen = VarInt.length(buf, varPos1);
/*  65 */       if ((varPos1 + varIntLen) + additionsCount * 2L > buf.readableBytes())
/*  66 */         throw ProtocolException.bufferTooSmall("Additions", varPos1 + varIntLen + additionsCount * 2, buf.readableBytes()); 
/*  67 */       obj.additions = new AssetEditorFileEntry[additionsCount];
/*  68 */       int elemPos = varPos1 + varIntLen;
/*  69 */       for (int i = 0; i < additionsCount; i++) {
/*  70 */         obj.additions[i] = AssetEditorFileEntry.deserialize(buf, elemPos);
/*  71 */         elemPos += AssetEditorFileEntry.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*  74 */     if ((nullBits & 0x4) != 0) {
/*  75 */       int varPos2 = offset + 13 + buf.getIntLE(offset + 9);
/*  76 */       int deletionsCount = VarInt.peek(buf, varPos2);
/*  77 */       if (deletionsCount < 0) throw ProtocolException.negativeLength("Deletions", deletionsCount); 
/*  78 */       if (deletionsCount > 4096000) throw ProtocolException.arrayTooLong("Deletions", deletionsCount, 4096000); 
/*  79 */       int varIntLen = VarInt.length(buf, varPos2);
/*  80 */       if ((varPos2 + varIntLen) + deletionsCount * 2L > buf.readableBytes())
/*  81 */         throw ProtocolException.bufferTooSmall("Deletions", varPos2 + varIntLen + deletionsCount * 2, buf.readableBytes()); 
/*  82 */       obj.deletions = new AssetEditorFileEntry[deletionsCount];
/*  83 */       int elemPos = varPos2 + varIntLen;
/*  84 */       for (int i = 0; i < deletionsCount; i++) {
/*  85 */         obj.deletions[i] = AssetEditorFileEntry.deserialize(buf, elemPos);
/*  86 */         elemPos += AssetEditorFileEntry.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  94 */     byte nullBits = buf.getByte(offset);
/*  95 */     int maxEnd = 13;
/*  96 */     if ((nullBits & 0x1) != 0) {
/*  97 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  98 */       int pos0 = offset + 13 + fieldOffset0;
/*  99 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 100 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 102 */     if ((nullBits & 0x2) != 0) {
/* 103 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/* 104 */       int pos1 = offset + 13 + fieldOffset1;
/* 105 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 106 */       for (int i = 0; i < arrLen; ) { pos1 += AssetEditorFileEntry.computeBytesConsumed(buf, pos1); i++; }
/* 107 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 109 */     if ((nullBits & 0x4) != 0) {
/* 110 */       int fieldOffset2 = buf.getIntLE(offset + 9);
/* 111 */       int pos2 = offset + 13 + fieldOffset2;
/* 112 */       int arrLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2);
/* 113 */       for (int i = 0; i < arrLen; ) { pos2 += AssetEditorFileEntry.computeBytesConsumed(buf, pos2); i++; }
/* 114 */        if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 116 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 122 */     int startPos = buf.writerIndex();
/* 123 */     byte nullBits = 0;
/* 124 */     if (this.pack != null) nullBits = (byte)(nullBits | 0x1); 
/* 125 */     if (this.additions != null) nullBits = (byte)(nullBits | 0x2); 
/* 126 */     if (this.deletions != null) nullBits = (byte)(nullBits | 0x4); 
/* 127 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 130 */     int packOffsetSlot = buf.writerIndex();
/* 131 */     buf.writeIntLE(0);
/* 132 */     int additionsOffsetSlot = buf.writerIndex();
/* 133 */     buf.writeIntLE(0);
/* 134 */     int deletionsOffsetSlot = buf.writerIndex();
/* 135 */     buf.writeIntLE(0);
/*     */     
/* 137 */     int varBlockStart = buf.writerIndex();
/* 138 */     if (this.pack != null) {
/* 139 */       buf.setIntLE(packOffsetSlot, buf.writerIndex() - varBlockStart);
/* 140 */       PacketIO.writeVarString(buf, this.pack, 4096000);
/*     */     } else {
/* 142 */       buf.setIntLE(packOffsetSlot, -1);
/*     */     } 
/* 144 */     if (this.additions != null) {
/* 145 */       buf.setIntLE(additionsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 146 */       if (this.additions.length > 4096000) throw ProtocolException.arrayTooLong("Additions", this.additions.length, 4096000);  VarInt.write(buf, this.additions.length); for (AssetEditorFileEntry item : this.additions) item.serialize(buf); 
/*     */     } else {
/* 148 */       buf.setIntLE(additionsOffsetSlot, -1);
/*     */     } 
/* 150 */     if (this.deletions != null) {
/* 151 */       buf.setIntLE(deletionsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 152 */       if (this.deletions.length > 4096000) throw ProtocolException.arrayTooLong("Deletions", this.deletions.length, 4096000);  VarInt.write(buf, this.deletions.length); for (AssetEditorFileEntry item : this.deletions) item.serialize(buf); 
/*     */     } else {
/* 154 */       buf.setIntLE(deletionsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 160 */     int size = 13;
/* 161 */     if (this.pack != null) size += PacketIO.stringSize(this.pack); 
/* 162 */     if (this.additions != null) {
/* 163 */       int additionsSize = 0;
/* 164 */       for (AssetEditorFileEntry elem : this.additions) additionsSize += elem.computeSize(); 
/* 165 */       size += VarInt.size(this.additions.length) + additionsSize;
/*     */     } 
/* 167 */     if (this.deletions != null) {
/* 168 */       int deletionsSize = 0;
/* 169 */       for (AssetEditorFileEntry elem : this.deletions) deletionsSize += elem.computeSize(); 
/* 170 */       size += VarInt.size(this.deletions.length) + deletionsSize;
/*     */     } 
/*     */     
/* 173 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 177 */     if (buffer.readableBytes() - offset < 13) {
/* 178 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */     
/* 181 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 184 */     if ((nullBits & 0x1) != 0) {
/* 185 */       int packOffset = buffer.getIntLE(offset + 1);
/* 186 */       if (packOffset < 0) {
/* 187 */         return ValidationResult.error("Invalid offset for Pack");
/*     */       }
/* 189 */       int pos = offset + 13 + packOffset;
/* 190 */       if (pos >= buffer.writerIndex()) {
/* 191 */         return ValidationResult.error("Offset out of bounds for Pack");
/*     */       }
/* 193 */       int packLen = VarInt.peek(buffer, pos);
/* 194 */       if (packLen < 0) {
/* 195 */         return ValidationResult.error("Invalid string length for Pack");
/*     */       }
/* 197 */       if (packLen > 4096000) {
/* 198 */         return ValidationResult.error("Pack exceeds max length 4096000");
/*     */       }
/* 200 */       pos += VarInt.length(buffer, pos);
/* 201 */       pos += packLen;
/* 202 */       if (pos > buffer.writerIndex()) {
/* 203 */         return ValidationResult.error("Buffer overflow reading Pack");
/*     */       }
/*     */     } 
/*     */     
/* 207 */     if ((nullBits & 0x2) != 0) {
/* 208 */       int additionsOffset = buffer.getIntLE(offset + 5);
/* 209 */       if (additionsOffset < 0) {
/* 210 */         return ValidationResult.error("Invalid offset for Additions");
/*     */       }
/* 212 */       int pos = offset + 13 + additionsOffset;
/* 213 */       if (pos >= buffer.writerIndex()) {
/* 214 */         return ValidationResult.error("Offset out of bounds for Additions");
/*     */       }
/* 216 */       int additionsCount = VarInt.peek(buffer, pos);
/* 217 */       if (additionsCount < 0) {
/* 218 */         return ValidationResult.error("Invalid array count for Additions");
/*     */       }
/* 220 */       if (additionsCount > 4096000) {
/* 221 */         return ValidationResult.error("Additions exceeds max length 4096000");
/*     */       }
/* 223 */       pos += VarInt.length(buffer, pos);
/* 224 */       for (int i = 0; i < additionsCount; i++) {
/* 225 */         ValidationResult structResult = AssetEditorFileEntry.validateStructure(buffer, pos);
/* 226 */         if (!structResult.isValid()) {
/* 227 */           return ValidationResult.error("Invalid AssetEditorFileEntry in Additions[" + i + "]: " + structResult.error());
/*     */         }
/* 229 */         pos += AssetEditorFileEntry.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 233 */     if ((nullBits & 0x4) != 0) {
/* 234 */       int deletionsOffset = buffer.getIntLE(offset + 9);
/* 235 */       if (deletionsOffset < 0) {
/* 236 */         return ValidationResult.error("Invalid offset for Deletions");
/*     */       }
/* 238 */       int pos = offset + 13 + deletionsOffset;
/* 239 */       if (pos >= buffer.writerIndex()) {
/* 240 */         return ValidationResult.error("Offset out of bounds for Deletions");
/*     */       }
/* 242 */       int deletionsCount = VarInt.peek(buffer, pos);
/* 243 */       if (deletionsCount < 0) {
/* 244 */         return ValidationResult.error("Invalid array count for Deletions");
/*     */       }
/* 246 */       if (deletionsCount > 4096000) {
/* 247 */         return ValidationResult.error("Deletions exceeds max length 4096000");
/*     */       }
/* 249 */       pos += VarInt.length(buffer, pos);
/* 250 */       for (int i = 0; i < deletionsCount; i++) {
/* 251 */         ValidationResult structResult = AssetEditorFileEntry.validateStructure(buffer, pos);
/* 252 */         if (!structResult.isValid()) {
/* 253 */           return ValidationResult.error("Invalid AssetEditorFileEntry in Deletions[" + i + "]: " + structResult.error());
/*     */         }
/* 255 */         pos += AssetEditorFileEntry.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 258 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorAssetListUpdate clone() {
/* 262 */     AssetEditorAssetListUpdate copy = new AssetEditorAssetListUpdate();
/* 263 */     copy.pack = this.pack;
/* 264 */     copy.additions = (this.additions != null) ? (AssetEditorFileEntry[])Arrays.<AssetEditorFileEntry>stream(this.additions).map(e -> e.clone()).toArray(x$0 -> new AssetEditorFileEntry[x$0]) : null;
/* 265 */     copy.deletions = (this.deletions != null) ? (AssetEditorFileEntry[])Arrays.<AssetEditorFileEntry>stream(this.deletions).map(e -> e.clone()).toArray(x$0 -> new AssetEditorFileEntry[x$0]) : null;
/* 266 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorAssetListUpdate other;
/* 272 */     if (this == obj) return true; 
/* 273 */     if (obj instanceof AssetEditorAssetListUpdate) { other = (AssetEditorAssetListUpdate)obj; } else { return false; }
/* 274 */      return (Objects.equals(this.pack, other.pack) && Arrays.equals((Object[])this.additions, (Object[])other.additions) && Arrays.equals((Object[])this.deletions, (Object[])other.deletions));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 279 */     int result = 1;
/* 280 */     result = 31 * result + Objects.hashCode(this.pack);
/* 281 */     result = 31 * result + Arrays.hashCode((Object[])this.additions);
/* 282 */     result = 31 * result + Arrays.hashCode((Object[])this.deletions);
/* 283 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorAssetListUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */