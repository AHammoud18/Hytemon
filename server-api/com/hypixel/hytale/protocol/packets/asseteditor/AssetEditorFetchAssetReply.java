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
/*     */ public class AssetEditorFetchAssetReply implements Packet {
/*     */   public static final int PACKET_ID = 312;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 5;
/*     */   public static final int MAX_SIZE = 4096010;
/*     */   public int token;
/*     */   @Nullable
/*     */   public byte[] contents;
/*     */   
/*     */   public int getId() {
/*  25 */     return 312;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorFetchAssetReply() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorFetchAssetReply(int token, @Nullable byte[] contents) {
/*  35 */     this.token = token;
/*  36 */     this.contents = contents;
/*     */   }
/*     */   
/*     */   public AssetEditorFetchAssetReply(@Nonnull AssetEditorFetchAssetReply other) {
/*  40 */     this.token = other.token;
/*  41 */     this.contents = other.contents;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorFetchAssetReply deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     AssetEditorFetchAssetReply obj = new AssetEditorFetchAssetReply();
/*  47 */     byte nullBits = buf.getByte(offset);
/*  48 */     obj.token = buf.getIntLE(offset + 1);
/*     */     
/*  50 */     int pos = offset + 5;
/*  51 */     if ((nullBits & 0x1) != 0) { int contentsCount = VarInt.peek(buf, pos);
/*  52 */       if (contentsCount < 0) throw ProtocolException.negativeLength("Contents", contentsCount); 
/*  53 */       if (contentsCount > 4096000) throw ProtocolException.arrayTooLong("Contents", contentsCount, 4096000); 
/*  54 */       int contentsVarLen = VarInt.size(contentsCount);
/*  55 */       if ((pos + contentsVarLen) + contentsCount * 1L > buf.readableBytes())
/*  56 */         throw ProtocolException.bufferTooSmall("Contents", pos + contentsVarLen + contentsCount * 1, buf.readableBytes()); 
/*  57 */       pos += contentsVarLen;
/*  58 */       obj.contents = new byte[contentsCount];
/*  59 */       for (int i = 0; i < contentsCount; i++) {
/*  60 */         obj.contents[i] = buf.getByte(pos + i * 1);
/*     */       }
/*  62 */       pos += contentsCount * 1; }
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     byte nullBits = buf.getByte(offset);
/*  69 */     int pos = offset + 5;
/*  70 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + arrLen * 1; }
/*  71 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  77 */     byte nullBits = 0;
/*  78 */     if (this.contents != null) nullBits = (byte)(nullBits | 0x1); 
/*  79 */     buf.writeByte(nullBits);
/*     */     
/*  81 */     buf.writeIntLE(this.token);
/*     */     
/*  83 */     if (this.contents != null) { if (this.contents.length > 4096000) throw ProtocolException.arrayTooLong("Contents", this.contents.length, 4096000);  VarInt.write(buf, this.contents.length); for (byte item : this.contents) buf.writeByte(item);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  88 */     int size = 5;
/*  89 */     if (this.contents != null) size += VarInt.size(this.contents.length) + this.contents.length * 1;
/*     */     
/*  91 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  95 */     if (buffer.readableBytes() - offset < 5) {
/*  96 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*     */     }
/*     */     
/*  99 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 101 */     int pos = offset + 5;
/*     */     
/* 103 */     if ((nullBits & 0x1) != 0) {
/* 104 */       int contentsCount = VarInt.peek(buffer, pos);
/* 105 */       if (contentsCount < 0) {
/* 106 */         return ValidationResult.error("Invalid array count for Contents");
/*     */       }
/* 108 */       if (contentsCount > 4096000) {
/* 109 */         return ValidationResult.error("Contents exceeds max length 4096000");
/*     */       }
/* 111 */       pos += VarInt.length(buffer, pos);
/* 112 */       pos += contentsCount * 1;
/* 113 */       if (pos > buffer.writerIndex()) {
/* 114 */         return ValidationResult.error("Buffer overflow reading Contents");
/*     */       }
/*     */     } 
/* 117 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorFetchAssetReply clone() {
/* 121 */     AssetEditorFetchAssetReply copy = new AssetEditorFetchAssetReply();
/* 122 */     copy.token = this.token;
/* 123 */     copy.contents = (this.contents != null) ? Arrays.copyOf(this.contents, this.contents.length) : null;
/* 124 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorFetchAssetReply other;
/* 130 */     if (this == obj) return true; 
/* 131 */     if (obj instanceof AssetEditorFetchAssetReply) { other = (AssetEditorFetchAssetReply)obj; } else { return false; }
/* 132 */      return (this.token == other.token && Arrays.equals(this.contents, other.contents));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 137 */     int result = 1;
/* 138 */     result = 31 * result + Integer.hashCode(this.token);
/* 139 */     result = 31 * result + Arrays.hashCode(this.contents);
/* 140 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorFetchAssetReply.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */