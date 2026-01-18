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
/*     */ 
/*     */ public class AssetEditorRequestDatasetReply
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 334;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   
/*     */   public int getId() {
/*  25 */     return 334;
/*     */   }
/*     */   public static final int VARIABLE_FIELD_COUNT = 2; public static final int VARIABLE_BLOCK_START = 9; public static final int MAX_SIZE = 1677721600; @Nullable
/*     */   public String name;
/*     */   @Nullable
/*     */   public String[] ids;
/*     */   
/*     */   public AssetEditorRequestDatasetReply() {}
/*     */   
/*     */   public AssetEditorRequestDatasetReply(@Nullable String name, @Nullable String[] ids) {
/*  35 */     this.name = name;
/*  36 */     this.ids = ids;
/*     */   }
/*     */   
/*     */   public AssetEditorRequestDatasetReply(@Nonnull AssetEditorRequestDatasetReply other) {
/*  40 */     this.name = other.name;
/*  41 */     this.ids = other.ids;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorRequestDatasetReply deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     AssetEditorRequestDatasetReply obj = new AssetEditorRequestDatasetReply();
/*  47 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  49 */     if ((nullBits & 0x1) != 0) {
/*  50 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  51 */       int nameLen = VarInt.peek(buf, varPos0);
/*  52 */       if (nameLen < 0) throw ProtocolException.negativeLength("Name", nameLen); 
/*  53 */       if (nameLen > 4096000) throw ProtocolException.stringTooLong("Name", nameLen, 4096000); 
/*  54 */       obj.name = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  56 */     if ((nullBits & 0x2) != 0) {
/*  57 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  58 */       int idsCount = VarInt.peek(buf, varPos1);
/*  59 */       if (idsCount < 0) throw ProtocolException.negativeLength("Ids", idsCount); 
/*  60 */       if (idsCount > 4096000) throw ProtocolException.arrayTooLong("Ids", idsCount, 4096000); 
/*  61 */       int varIntLen = VarInt.length(buf, varPos1);
/*  62 */       if ((varPos1 + varIntLen) + idsCount * 1L > buf.readableBytes())
/*  63 */         throw ProtocolException.bufferTooSmall("Ids", varPos1 + varIntLen + idsCount * 1, buf.readableBytes()); 
/*  64 */       obj.ids = new String[idsCount];
/*  65 */       int elemPos = varPos1 + varIntLen;
/*  66 */       for (int i = 0; i < idsCount; i++) {
/*  67 */         int strLen = VarInt.peek(buf, elemPos);
/*  68 */         if (strLen < 0) throw ProtocolException.negativeLength("ids[" + i + "]", strLen); 
/*  69 */         if (strLen > 4096000) throw ProtocolException.stringTooLong("ids[" + i + "]", strLen, 4096000); 
/*  70 */         int strVarLen = VarInt.length(buf, elemPos);
/*  71 */         obj.ids[i] = PacketIO.readVarString(buf, elemPos);
/*  72 */         elemPos += strVarLen + strLen;
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  80 */     byte nullBits = buf.getByte(offset);
/*  81 */     int maxEnd = 9;
/*  82 */     if ((nullBits & 0x1) != 0) {
/*  83 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  84 */       int pos0 = offset + 9 + fieldOffset0;
/*  85 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  86 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  88 */     if ((nullBits & 0x2) != 0) {
/*  89 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  90 */       int pos1 = offset + 9 + fieldOffset1;
/*  91 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/*  92 */       for (int i = 0; i < arrLen; ) { int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl; i++; }
/*  93 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  95 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 101 */     int startPos = buf.writerIndex();
/* 102 */     byte nullBits = 0;
/* 103 */     if (this.name != null) nullBits = (byte)(nullBits | 0x1); 
/* 104 */     if (this.ids != null) nullBits = (byte)(nullBits | 0x2); 
/* 105 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 108 */     int nameOffsetSlot = buf.writerIndex();
/* 109 */     buf.writeIntLE(0);
/* 110 */     int idsOffsetSlot = buf.writerIndex();
/* 111 */     buf.writeIntLE(0);
/*     */     
/* 113 */     int varBlockStart = buf.writerIndex();
/* 114 */     if (this.name != null) {
/* 115 */       buf.setIntLE(nameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 116 */       PacketIO.writeVarString(buf, this.name, 4096000);
/*     */     } else {
/* 118 */       buf.setIntLE(nameOffsetSlot, -1);
/*     */     } 
/* 120 */     if (this.ids != null) {
/* 121 */       buf.setIntLE(idsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 122 */       if (this.ids.length > 4096000) throw ProtocolException.arrayTooLong("Ids", this.ids.length, 4096000);  VarInt.write(buf, this.ids.length); for (String item : this.ids) PacketIO.writeVarString(buf, item, 4096000); 
/*     */     } else {
/* 124 */       buf.setIntLE(idsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 130 */     int size = 9;
/* 131 */     if (this.name != null) size += PacketIO.stringSize(this.name); 
/* 132 */     if (this.ids != null) {
/* 133 */       int idsSize = 0;
/* 134 */       for (String elem : this.ids) idsSize += PacketIO.stringSize(elem); 
/* 135 */       size += VarInt.size(this.ids.length) + idsSize;
/*     */     } 
/*     */     
/* 138 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 142 */     if (buffer.readableBytes() - offset < 9) {
/* 143 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 146 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 149 */     if ((nullBits & 0x1) != 0) {
/* 150 */       int nameOffset = buffer.getIntLE(offset + 1);
/* 151 */       if (nameOffset < 0) {
/* 152 */         return ValidationResult.error("Invalid offset for Name");
/*     */       }
/* 154 */       int pos = offset + 9 + nameOffset;
/* 155 */       if (pos >= buffer.writerIndex()) {
/* 156 */         return ValidationResult.error("Offset out of bounds for Name");
/*     */       }
/* 158 */       int nameLen = VarInt.peek(buffer, pos);
/* 159 */       if (nameLen < 0) {
/* 160 */         return ValidationResult.error("Invalid string length for Name");
/*     */       }
/* 162 */       if (nameLen > 4096000) {
/* 163 */         return ValidationResult.error("Name exceeds max length 4096000");
/*     */       }
/* 165 */       pos += VarInt.length(buffer, pos);
/* 166 */       pos += nameLen;
/* 167 */       if (pos > buffer.writerIndex()) {
/* 168 */         return ValidationResult.error("Buffer overflow reading Name");
/*     */       }
/*     */     } 
/*     */     
/* 172 */     if ((nullBits & 0x2) != 0) {
/* 173 */       int idsOffset = buffer.getIntLE(offset + 5);
/* 174 */       if (idsOffset < 0) {
/* 175 */         return ValidationResult.error("Invalid offset for Ids");
/*     */       }
/* 177 */       int pos = offset + 9 + idsOffset;
/* 178 */       if (pos >= buffer.writerIndex()) {
/* 179 */         return ValidationResult.error("Offset out of bounds for Ids");
/*     */       }
/* 181 */       int idsCount = VarInt.peek(buffer, pos);
/* 182 */       if (idsCount < 0) {
/* 183 */         return ValidationResult.error("Invalid array count for Ids");
/*     */       }
/* 185 */       if (idsCount > 4096000) {
/* 186 */         return ValidationResult.error("Ids exceeds max length 4096000");
/*     */       }
/* 188 */       pos += VarInt.length(buffer, pos);
/* 189 */       for (int i = 0; i < idsCount; i++) {
/* 190 */         int strLen = VarInt.peek(buffer, pos);
/* 191 */         if (strLen < 0) {
/* 192 */           return ValidationResult.error("Invalid string length in Ids");
/*     */         }
/* 194 */         pos += VarInt.length(buffer, pos);
/* 195 */         pos += strLen;
/* 196 */         if (pos > buffer.writerIndex()) {
/* 197 */           return ValidationResult.error("Buffer overflow reading string in Ids");
/*     */         }
/*     */       } 
/*     */     } 
/* 201 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorRequestDatasetReply clone() {
/* 205 */     AssetEditorRequestDatasetReply copy = new AssetEditorRequestDatasetReply();
/* 206 */     copy.name = this.name;
/* 207 */     copy.ids = (this.ids != null) ? Arrays.<String>copyOf(this.ids, this.ids.length) : null;
/* 208 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorRequestDatasetReply other;
/* 214 */     if (this == obj) return true; 
/* 215 */     if (obj instanceof AssetEditorRequestDatasetReply) { other = (AssetEditorRequestDatasetReply)obj; } else { return false; }
/* 216 */      return (Objects.equals(this.name, other.name) && Arrays.equals((Object[])this.ids, (Object[])other.ids));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 221 */     int result = 1;
/* 222 */     result = 31 * result + Objects.hashCode(this.name);
/* 223 */     result = 31 * result + Arrays.hashCode((Object[])this.ids);
/* 224 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorRequestDatasetReply.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */