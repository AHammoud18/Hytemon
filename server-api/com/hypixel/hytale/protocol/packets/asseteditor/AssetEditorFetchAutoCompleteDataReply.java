/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class AssetEditorFetchAutoCompleteDataReply implements Packet {
/*     */   public static final int PACKET_ID = 332;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 5;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   public int token;
/*     */   @Nullable
/*     */   public String[] results;
/*     */   
/*     */   public int getId() {
/*  25 */     return 332;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorFetchAutoCompleteDataReply() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorFetchAutoCompleteDataReply(int token, @Nullable String[] results) {
/*  35 */     this.token = token;
/*  36 */     this.results = results;
/*     */   }
/*     */   
/*     */   public AssetEditorFetchAutoCompleteDataReply(@Nonnull AssetEditorFetchAutoCompleteDataReply other) {
/*  40 */     this.token = other.token;
/*  41 */     this.results = other.results;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorFetchAutoCompleteDataReply deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     AssetEditorFetchAutoCompleteDataReply obj = new AssetEditorFetchAutoCompleteDataReply();
/*  47 */     byte nullBits = buf.getByte(offset);
/*  48 */     obj.token = buf.getIntLE(offset + 1);
/*     */     
/*  50 */     int pos = offset + 5;
/*  51 */     if ((nullBits & 0x1) != 0) { int resultsCount = VarInt.peek(buf, pos);
/*  52 */       if (resultsCount < 0) throw ProtocolException.negativeLength("Results", resultsCount); 
/*  53 */       if (resultsCount > 4096000) throw ProtocolException.arrayTooLong("Results", resultsCount, 4096000); 
/*  54 */       int resultsVarLen = VarInt.size(resultsCount);
/*  55 */       if ((pos + resultsVarLen) + resultsCount * 1L > buf.readableBytes())
/*  56 */         throw ProtocolException.bufferTooSmall("Results", pos + resultsVarLen + resultsCount * 1, buf.readableBytes()); 
/*  57 */       pos += resultsVarLen;
/*  58 */       obj.results = new String[resultsCount];
/*  59 */       for (int i = 0; i < resultsCount; i++) {
/*  60 */         int strLen = VarInt.peek(buf, pos);
/*  61 */         if (strLen < 0) throw ProtocolException.negativeLength("results[" + i + "]", strLen); 
/*  62 */         if (strLen > 4096000) throw ProtocolException.stringTooLong("results[" + i + "]", strLen, 4096000); 
/*  63 */         int strVarLen = VarInt.length(buf, pos);
/*  64 */         obj.results[i] = PacketIO.readVarString(buf, pos);
/*  65 */         pos += strVarLen + strLen;
/*     */       }  }
/*     */     
/*  68 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  72 */     byte nullBits = buf.getByte(offset);
/*  73 */     int pos = offset + 5;
/*  74 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  75 */       for (int i = 0; i < arrLen; ) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; i++; }  }
/*  76 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  82 */     byte nullBits = 0;
/*  83 */     if (this.results != null) nullBits = (byte)(nullBits | 0x1); 
/*  84 */     buf.writeByte(nullBits);
/*     */     
/*  86 */     buf.writeIntLE(this.token);
/*     */     
/*  88 */     if (this.results != null) { if (this.results.length > 4096000) throw ProtocolException.arrayTooLong("Results", this.results.length, 4096000);  VarInt.write(buf, this.results.length); for (String item : this.results) PacketIO.writeVarString(buf, item, 4096000);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  93 */     int size = 5;
/*  94 */     if (this.results != null) {
/*  95 */       int resultsSize = 0;
/*  96 */       for (String elem : this.results) resultsSize += PacketIO.stringSize(elem); 
/*  97 */       size += VarInt.size(this.results.length) + resultsSize;
/*     */     } 
/*     */     
/* 100 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 104 */     if (buffer.readableBytes() - offset < 5) {
/* 105 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*     */     }
/*     */     
/* 108 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 110 */     int pos = offset + 5;
/*     */     
/* 112 */     if ((nullBits & 0x1) != 0) {
/* 113 */       int resultsCount = VarInt.peek(buffer, pos);
/* 114 */       if (resultsCount < 0) {
/* 115 */         return ValidationResult.error("Invalid array count for Results");
/*     */       }
/* 117 */       if (resultsCount > 4096000) {
/* 118 */         return ValidationResult.error("Results exceeds max length 4096000");
/*     */       }
/* 120 */       pos += VarInt.length(buffer, pos);
/* 121 */       for (int i = 0; i < resultsCount; i++) {
/* 122 */         int strLen = VarInt.peek(buffer, pos);
/* 123 */         if (strLen < 0) {
/* 124 */           return ValidationResult.error("Invalid string length in Results");
/*     */         }
/* 126 */         pos += VarInt.length(buffer, pos);
/* 127 */         pos += strLen;
/* 128 */         if (pos > buffer.writerIndex()) {
/* 129 */           return ValidationResult.error("Buffer overflow reading string in Results");
/*     */         }
/*     */       } 
/*     */     } 
/* 133 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorFetchAutoCompleteDataReply clone() {
/* 137 */     AssetEditorFetchAutoCompleteDataReply copy = new AssetEditorFetchAutoCompleteDataReply();
/* 138 */     copy.token = this.token;
/* 139 */     copy.results = (this.results != null) ? Arrays.<String>copyOf(this.results, this.results.length) : null;
/* 140 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorFetchAutoCompleteDataReply other;
/* 146 */     if (this == obj) return true; 
/* 147 */     if (obj instanceof AssetEditorFetchAutoCompleteDataReply) { other = (AssetEditorFetchAutoCompleteDataReply)obj; } else { return false; }
/* 148 */      return (this.token == other.token && Arrays.equals((Object[])this.results, (Object[])other.results));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 153 */     int result = 1;
/* 154 */     result = 31 * result + Integer.hashCode(this.token);
/* 155 */     result = 31 * result + Arrays.hashCode((Object[])this.results);
/* 156 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorFetchAutoCompleteDataReply.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */