/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class AssetEditorFetchJsonAssetWithParentsReply
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 313;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   
/*     */   public int getId() {
/*  25 */     return 313;
/*     */   }
/*     */   public static final int VARIABLE_FIELD_COUNT = 1; public static final int VARIABLE_BLOCK_START = 5; public static final int MAX_SIZE = 1677721600;
/*     */   public int token;
/*     */   @Nullable
/*     */   public Map<AssetPath, String> assets;
/*     */   
/*     */   public AssetEditorFetchJsonAssetWithParentsReply() {}
/*     */   
/*     */   public AssetEditorFetchJsonAssetWithParentsReply(int token, @Nullable Map<AssetPath, String> assets) {
/*  35 */     this.token = token;
/*  36 */     this.assets = assets;
/*     */   }
/*     */   
/*     */   public AssetEditorFetchJsonAssetWithParentsReply(@Nonnull AssetEditorFetchJsonAssetWithParentsReply other) {
/*  40 */     this.token = other.token;
/*  41 */     this.assets = other.assets;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorFetchJsonAssetWithParentsReply deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     AssetEditorFetchJsonAssetWithParentsReply obj = new AssetEditorFetchJsonAssetWithParentsReply();
/*  47 */     byte nullBits = buf.getByte(offset);
/*  48 */     obj.token = buf.getIntLE(offset + 1);
/*     */     
/*  50 */     int pos = offset + 5;
/*  51 */     if ((nullBits & 0x1) != 0) { int assetsCount = VarInt.peek(buf, pos);
/*  52 */       if (assetsCount < 0) throw ProtocolException.negativeLength("Assets", assetsCount); 
/*  53 */       if (assetsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Assets", assetsCount, 4096000); 
/*  54 */       pos += VarInt.size(assetsCount);
/*  55 */       obj.assets = new HashMap<>(assetsCount);
/*  56 */       for (int i = 0; i < assetsCount; i++) {
/*  57 */         AssetPath key = AssetPath.deserialize(buf, pos);
/*  58 */         pos += AssetPath.computeBytesConsumed(buf, pos);
/*  59 */         int valLen = VarInt.peek(buf, pos);
/*  60 */         if (valLen < 0) throw ProtocolException.negativeLength("val", valLen); 
/*  61 */         if (valLen > 4096000) throw ProtocolException.stringTooLong("val", valLen, 4096000); 
/*  62 */         int valVarLen = VarInt.length(buf, pos);
/*  63 */         String val = PacketIO.readVarString(buf, pos);
/*  64 */         pos += valVarLen + valLen;
/*  65 */         if (obj.assets.put(key, val) != null)
/*  66 */           throw ProtocolException.duplicateKey("assets", key); 
/*     */       }  }
/*     */     
/*  69 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  73 */     byte nullBits = buf.getByte(offset);
/*  74 */     int pos = offset + 5;
/*  75 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  76 */       for (int i = 0; i < dictLen; ) { pos += AssetPath.computeBytesConsumed(buf, pos); int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; i++; }  }
/*  77 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  83 */     byte nullBits = 0;
/*  84 */     if (this.assets != null) nullBits = (byte)(nullBits | 0x1); 
/*  85 */     buf.writeByte(nullBits);
/*     */     
/*  87 */     buf.writeIntLE(this.token);
/*     */     
/*  89 */     if (this.assets != null) { if (this.assets.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Assets", this.assets.size(), 4096000);  VarInt.write(buf, this.assets.size()); for (Map.Entry<AssetPath, String> e : this.assets.entrySet()) { ((AssetPath)e.getKey()).serialize(buf); PacketIO.writeVarString(buf, e.getValue(), 4096000); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/*  94 */     int size = 5;
/*  95 */     if (this.assets != null) {
/*  96 */       int assetsSize = 0;
/*  97 */       for (Map.Entry<AssetPath, String> kvp : this.assets.entrySet()) assetsSize += ((AssetPath)kvp.getKey()).computeSize() + PacketIO.stringSize(kvp.getValue()); 
/*  98 */       size += VarInt.size(this.assets.size()) + assetsSize;
/*     */     } 
/*     */     
/* 101 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 105 */     if (buffer.readableBytes() - offset < 5) {
/* 106 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*     */     }
/*     */     
/* 109 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 111 */     int pos = offset + 5;
/*     */     
/* 113 */     if ((nullBits & 0x1) != 0) {
/* 114 */       int assetsCount = VarInt.peek(buffer, pos);
/* 115 */       if (assetsCount < 0) {
/* 116 */         return ValidationResult.error("Invalid dictionary count for Assets");
/*     */       }
/* 118 */       if (assetsCount > 4096000) {
/* 119 */         return ValidationResult.error("Assets exceeds max length 4096000");
/*     */       }
/* 121 */       pos += VarInt.length(buffer, pos);
/* 122 */       for (int i = 0; i < assetsCount; i++) {
/* 123 */         pos += AssetPath.computeBytesConsumed(buffer, pos);
/*     */         
/* 125 */         int valueLen = VarInt.peek(buffer, pos);
/* 126 */         if (valueLen < 0) {
/* 127 */           return ValidationResult.error("Invalid string length for value");
/*     */         }
/* 129 */         if (valueLen > 4096000) {
/* 130 */           return ValidationResult.error("value exceeds max length 4096000");
/*     */         }
/* 132 */         pos += VarInt.length(buffer, pos);
/* 133 */         pos += valueLen;
/* 134 */         if (pos > buffer.writerIndex()) {
/* 135 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/* 139 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorFetchJsonAssetWithParentsReply clone() {
/* 143 */     AssetEditorFetchJsonAssetWithParentsReply copy = new AssetEditorFetchJsonAssetWithParentsReply();
/* 144 */     copy.token = this.token;
/* 145 */     copy.assets = (this.assets != null) ? new HashMap<>(this.assets) : null;
/* 146 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorFetchJsonAssetWithParentsReply other;
/* 152 */     if (this == obj) return true; 
/* 153 */     if (obj instanceof AssetEditorFetchJsonAssetWithParentsReply) { other = (AssetEditorFetchJsonAssetWithParentsReply)obj; } else { return false; }
/* 154 */      return (this.token == other.token && Objects.equals(this.assets, other.assets));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 159 */     return Objects.hash(new Object[] { Integer.valueOf(this.token), this.assets });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorFetchJsonAssetWithParentsReply.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */