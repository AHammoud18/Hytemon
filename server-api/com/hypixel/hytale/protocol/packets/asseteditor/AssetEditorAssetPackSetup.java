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
/*     */ public class AssetEditorAssetPackSetup
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 314;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   
/*     */   public int getId() {
/*  25 */     return 314;
/*     */   }
/*     */   public static final int VARIABLE_FIELD_COUNT = 1; public static final int VARIABLE_BLOCK_START = 1; public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public Map<String, AssetPackManifest> packs;
/*     */   
/*     */   public AssetEditorAssetPackSetup() {}
/*     */   
/*     */   public AssetEditorAssetPackSetup(@Nullable Map<String, AssetPackManifest> packs) {
/*  34 */     this.packs = packs;
/*     */   }
/*     */   
/*     */   public AssetEditorAssetPackSetup(@Nonnull AssetEditorAssetPackSetup other) {
/*  38 */     this.packs = other.packs;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorAssetPackSetup deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     AssetEditorAssetPackSetup obj = new AssetEditorAssetPackSetup();
/*  44 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  46 */     int pos = offset + 1;
/*  47 */     if ((nullBits & 0x1) != 0) { int packsCount = VarInt.peek(buf, pos);
/*  48 */       if (packsCount < 0) throw ProtocolException.negativeLength("Packs", packsCount); 
/*  49 */       if (packsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Packs", packsCount, 4096000); 
/*  50 */       pos += VarInt.size(packsCount);
/*  51 */       obj.packs = new HashMap<>(packsCount);
/*  52 */       for (int i = 0; i < packsCount; i++) {
/*  53 */         int keyLen = VarInt.peek(buf, pos);
/*  54 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/*  55 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/*  56 */         int keyVarLen = VarInt.length(buf, pos);
/*  57 */         String key = PacketIO.readVarString(buf, pos);
/*  58 */         pos += keyVarLen + keyLen;
/*  59 */         AssetPackManifest val = AssetPackManifest.deserialize(buf, pos);
/*  60 */         pos += AssetPackManifest.computeBytesConsumed(buf, pos);
/*  61 */         if (obj.packs.put(key, val) != null)
/*  62 */           throw ProtocolException.duplicateKey("packs", key); 
/*     */       }  }
/*     */     
/*  65 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  69 */     byte nullBits = buf.getByte(offset);
/*  70 */     int pos = offset + 1;
/*  71 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  72 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; pos += AssetPackManifest.computeBytesConsumed(buf, pos); i++; }  }
/*  73 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  79 */     byte nullBits = 0;
/*  80 */     if (this.packs != null) nullBits = (byte)(nullBits | 0x1); 
/*  81 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  84 */     if (this.packs != null) { if (this.packs.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Packs", this.packs.size(), 4096000);  VarInt.write(buf, this.packs.size()); for (Map.Entry<String, AssetPackManifest> e : this.packs.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); ((AssetPackManifest)e.getValue()).serialize(buf); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/*  89 */     int size = 1;
/*  90 */     if (this.packs != null) {
/*  91 */       int packsSize = 0;
/*  92 */       for (Map.Entry<String, AssetPackManifest> kvp : this.packs.entrySet()) packsSize += PacketIO.stringSize(kvp.getKey()) + ((AssetPackManifest)kvp.getValue()).computeSize(); 
/*  93 */       size += VarInt.size(this.packs.size()) + packsSize;
/*     */     } 
/*     */     
/*  96 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 100 */     if (buffer.readableBytes() - offset < 1) {
/* 101 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/* 104 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 106 */     int pos = offset + 1;
/*     */     
/* 108 */     if ((nullBits & 0x1) != 0) {
/* 109 */       int packsCount = VarInt.peek(buffer, pos);
/* 110 */       if (packsCount < 0) {
/* 111 */         return ValidationResult.error("Invalid dictionary count for Packs");
/*     */       }
/* 113 */       if (packsCount > 4096000) {
/* 114 */         return ValidationResult.error("Packs exceeds max length 4096000");
/*     */       }
/* 116 */       pos += VarInt.length(buffer, pos);
/* 117 */       for (int i = 0; i < packsCount; i++) {
/* 118 */         int keyLen = VarInt.peek(buffer, pos);
/* 119 */         if (keyLen < 0) {
/* 120 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 122 */         if (keyLen > 4096000) {
/* 123 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 125 */         pos += VarInt.length(buffer, pos);
/* 126 */         pos += keyLen;
/* 127 */         if (pos > buffer.writerIndex()) {
/* 128 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 130 */         pos += AssetPackManifest.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 134 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorAssetPackSetup clone() {
/* 138 */     AssetEditorAssetPackSetup copy = new AssetEditorAssetPackSetup();
/* 139 */     if (this.packs != null) {
/* 140 */       Map<String, AssetPackManifest> m = new HashMap<>();
/* 141 */       for (Map.Entry<String, AssetPackManifest> e : this.packs.entrySet()) m.put(e.getKey(), ((AssetPackManifest)e.getValue()).clone()); 
/* 142 */       copy.packs = m;
/*     */     } 
/* 144 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorAssetPackSetup other;
/* 150 */     if (this == obj) return true; 
/* 151 */     if (obj instanceof AssetEditorAssetPackSetup) { other = (AssetEditorAssetPackSetup)obj; } else { return false; }
/* 152 */      return Objects.equals(this.packs, other.packs);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 157 */     return Objects.hash(new Object[] { this.packs });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorAssetPackSetup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */