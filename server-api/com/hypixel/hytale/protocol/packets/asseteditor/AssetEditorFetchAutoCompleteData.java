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
/*     */ public class AssetEditorFetchAutoCompleteData
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 331;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   
/*     */   public int getId() {
/*  25 */     return 331;
/*     */   }
/*     */   public static final int VARIABLE_BLOCK_START = 13; public static final int MAX_SIZE = 32768023; public int token;
/*     */   @Nullable
/*     */   public String dataset;
/*     */   @Nullable
/*     */   public String query;
/*     */   
/*     */   public AssetEditorFetchAutoCompleteData() {}
/*     */   
/*     */   public AssetEditorFetchAutoCompleteData(int token, @Nullable String dataset, @Nullable String query) {
/*  36 */     this.token = token;
/*  37 */     this.dataset = dataset;
/*  38 */     this.query = query;
/*     */   }
/*     */   
/*     */   public AssetEditorFetchAutoCompleteData(@Nonnull AssetEditorFetchAutoCompleteData other) {
/*  42 */     this.token = other.token;
/*  43 */     this.dataset = other.dataset;
/*  44 */     this.query = other.query;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorFetchAutoCompleteData deserialize(@Nonnull ByteBuf buf, int offset) {
/*  49 */     AssetEditorFetchAutoCompleteData obj = new AssetEditorFetchAutoCompleteData();
/*  50 */     byte nullBits = buf.getByte(offset);
/*  51 */     obj.token = buf.getIntLE(offset + 1);
/*     */     
/*  53 */     if ((nullBits & 0x1) != 0) {
/*  54 */       int varPos0 = offset + 13 + buf.getIntLE(offset + 5);
/*  55 */       int datasetLen = VarInt.peek(buf, varPos0);
/*  56 */       if (datasetLen < 0) throw ProtocolException.negativeLength("Dataset", datasetLen); 
/*  57 */       if (datasetLen > 4096000) throw ProtocolException.stringTooLong("Dataset", datasetLen, 4096000); 
/*  58 */       obj.dataset = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  60 */     if ((nullBits & 0x2) != 0) {
/*  61 */       int varPos1 = offset + 13 + buf.getIntLE(offset + 9);
/*  62 */       int queryLen = VarInt.peek(buf, varPos1);
/*  63 */       if (queryLen < 0) throw ProtocolException.negativeLength("Query", queryLen); 
/*  64 */       if (queryLen > 4096000) throw ProtocolException.stringTooLong("Query", queryLen, 4096000); 
/*  65 */       obj.query = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  68 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  72 */     byte nullBits = buf.getByte(offset);
/*  73 */     int maxEnd = 13;
/*  74 */     if ((nullBits & 0x1) != 0) {
/*  75 */       int fieldOffset0 = buf.getIntLE(offset + 5);
/*  76 */       int pos0 = offset + 13 + fieldOffset0;
/*  77 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  78 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  80 */     if ((nullBits & 0x2) != 0) {
/*  81 */       int fieldOffset1 = buf.getIntLE(offset + 9);
/*  82 */       int pos1 = offset + 13 + fieldOffset1;
/*  83 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  84 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  86 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  92 */     int startPos = buf.writerIndex();
/*  93 */     byte nullBits = 0;
/*  94 */     if (this.dataset != null) nullBits = (byte)(nullBits | 0x1); 
/*  95 */     if (this.query != null) nullBits = (byte)(nullBits | 0x2); 
/*  96 */     buf.writeByte(nullBits);
/*     */     
/*  98 */     buf.writeIntLE(this.token);
/*     */     
/* 100 */     int datasetOffsetSlot = buf.writerIndex();
/* 101 */     buf.writeIntLE(0);
/* 102 */     int queryOffsetSlot = buf.writerIndex();
/* 103 */     buf.writeIntLE(0);
/*     */     
/* 105 */     int varBlockStart = buf.writerIndex();
/* 106 */     if (this.dataset != null) {
/* 107 */       buf.setIntLE(datasetOffsetSlot, buf.writerIndex() - varBlockStart);
/* 108 */       PacketIO.writeVarString(buf, this.dataset, 4096000);
/*     */     } else {
/* 110 */       buf.setIntLE(datasetOffsetSlot, -1);
/*     */     } 
/* 112 */     if (this.query != null) {
/* 113 */       buf.setIntLE(queryOffsetSlot, buf.writerIndex() - varBlockStart);
/* 114 */       PacketIO.writeVarString(buf, this.query, 4096000);
/*     */     } else {
/* 116 */       buf.setIntLE(queryOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 122 */     int size = 13;
/* 123 */     if (this.dataset != null) size += PacketIO.stringSize(this.dataset); 
/* 124 */     if (this.query != null) size += PacketIO.stringSize(this.query);
/*     */     
/* 126 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 130 */     if (buffer.readableBytes() - offset < 13) {
/* 131 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */     
/* 134 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 137 */     if ((nullBits & 0x1) != 0) {
/* 138 */       int datasetOffset = buffer.getIntLE(offset + 5);
/* 139 */       if (datasetOffset < 0) {
/* 140 */         return ValidationResult.error("Invalid offset for Dataset");
/*     */       }
/* 142 */       int pos = offset + 13 + datasetOffset;
/* 143 */       if (pos >= buffer.writerIndex()) {
/* 144 */         return ValidationResult.error("Offset out of bounds for Dataset");
/*     */       }
/* 146 */       int datasetLen = VarInt.peek(buffer, pos);
/* 147 */       if (datasetLen < 0) {
/* 148 */         return ValidationResult.error("Invalid string length for Dataset");
/*     */       }
/* 150 */       if (datasetLen > 4096000) {
/* 151 */         return ValidationResult.error("Dataset exceeds max length 4096000");
/*     */       }
/* 153 */       pos += VarInt.length(buffer, pos);
/* 154 */       pos += datasetLen;
/* 155 */       if (pos > buffer.writerIndex()) {
/* 156 */         return ValidationResult.error("Buffer overflow reading Dataset");
/*     */       }
/*     */     } 
/*     */     
/* 160 */     if ((nullBits & 0x2) != 0) {
/* 161 */       int queryOffset = buffer.getIntLE(offset + 9);
/* 162 */       if (queryOffset < 0) {
/* 163 */         return ValidationResult.error("Invalid offset for Query");
/*     */       }
/* 165 */       int pos = offset + 13 + queryOffset;
/* 166 */       if (pos >= buffer.writerIndex()) {
/* 167 */         return ValidationResult.error("Offset out of bounds for Query");
/*     */       }
/* 169 */       int queryLen = VarInt.peek(buffer, pos);
/* 170 */       if (queryLen < 0) {
/* 171 */         return ValidationResult.error("Invalid string length for Query");
/*     */       }
/* 173 */       if (queryLen > 4096000) {
/* 174 */         return ValidationResult.error("Query exceeds max length 4096000");
/*     */       }
/* 176 */       pos += VarInt.length(buffer, pos);
/* 177 */       pos += queryLen;
/* 178 */       if (pos > buffer.writerIndex()) {
/* 179 */         return ValidationResult.error("Buffer overflow reading Query");
/*     */       }
/*     */     } 
/* 182 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorFetchAutoCompleteData clone() {
/* 186 */     AssetEditorFetchAutoCompleteData copy = new AssetEditorFetchAutoCompleteData();
/* 187 */     copy.token = this.token;
/* 188 */     copy.dataset = this.dataset;
/* 189 */     copy.query = this.query;
/* 190 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorFetchAutoCompleteData other;
/* 196 */     if (this == obj) return true; 
/* 197 */     if (obj instanceof AssetEditorFetchAutoCompleteData) { other = (AssetEditorFetchAutoCompleteData)obj; } else { return false; }
/* 198 */      return (this.token == other.token && Objects.equals(this.dataset, other.dataset) && Objects.equals(this.query, other.query));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 203 */     return Objects.hash(new Object[] { Integer.valueOf(this.token), this.dataset, this.query });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorFetchAutoCompleteData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */