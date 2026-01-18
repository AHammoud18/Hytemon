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
/*     */ public class AssetEditorExportDeleteAssets
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 346;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public AssetEditorAsset[] asset;
/*     */   
/*     */   public int getId() {
/*  25 */     return 346;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorExportDeleteAssets() {}
/*     */ 
/*     */   
/*     */   public AssetEditorExportDeleteAssets(@Nullable AssetEditorAsset[] asset) {
/*  34 */     this.asset = asset;
/*     */   }
/*     */   
/*     */   public AssetEditorExportDeleteAssets(@Nonnull AssetEditorExportDeleteAssets other) {
/*  38 */     this.asset = other.asset;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorExportDeleteAssets deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     AssetEditorExportDeleteAssets obj = new AssetEditorExportDeleteAssets();
/*  44 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  46 */     int pos = offset + 1;
/*  47 */     if ((nullBits & 0x1) != 0) { int assetCount = VarInt.peek(buf, pos);
/*  48 */       if (assetCount < 0) throw ProtocolException.negativeLength("Asset", assetCount); 
/*  49 */       if (assetCount > 4096000) throw ProtocolException.arrayTooLong("Asset", assetCount, 4096000); 
/*  50 */       int assetVarLen = VarInt.size(assetCount);
/*  51 */       if ((pos + assetVarLen) + assetCount * 1L > buf.readableBytes())
/*  52 */         throw ProtocolException.bufferTooSmall("Asset", pos + assetVarLen + assetCount * 1, buf.readableBytes()); 
/*  53 */       pos += assetVarLen;
/*  54 */       obj.asset = new AssetEditorAsset[assetCount];
/*  55 */       for (int i = 0; i < assetCount; i++) {
/*  56 */         obj.asset[i] = AssetEditorAsset.deserialize(buf, pos);
/*  57 */         pos += AssetEditorAsset.computeBytesConsumed(buf, pos);
/*     */       }  }
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     byte nullBits = buf.getByte(offset);
/*  65 */     int pos = offset + 1;
/*  66 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  67 */       for (int i = 0; i < arrLen; ) { pos += AssetEditorAsset.computeBytesConsumed(buf, pos); i++; }  }
/*  68 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  74 */     byte nullBits = 0;
/*  75 */     if (this.asset != null) nullBits = (byte)(nullBits | 0x1); 
/*  76 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  79 */     if (this.asset != null) { if (this.asset.length > 4096000) throw ProtocolException.arrayTooLong("Asset", this.asset.length, 4096000);  VarInt.write(buf, this.asset.length); for (AssetEditorAsset item : this.asset) item.serialize(buf);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  84 */     int size = 1;
/*  85 */     if (this.asset != null) {
/*  86 */       int assetSize = 0;
/*  87 */       for (AssetEditorAsset elem : this.asset) assetSize += elem.computeSize(); 
/*  88 */       size += VarInt.size(this.asset.length) + assetSize;
/*     */     } 
/*     */     
/*  91 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  95 */     if (buffer.readableBytes() - offset < 1) {
/*  96 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  99 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 101 */     int pos = offset + 1;
/*     */     
/* 103 */     if ((nullBits & 0x1) != 0) {
/* 104 */       int assetCount = VarInt.peek(buffer, pos);
/* 105 */       if (assetCount < 0) {
/* 106 */         return ValidationResult.error("Invalid array count for Asset");
/*     */       }
/* 108 */       if (assetCount > 4096000) {
/* 109 */         return ValidationResult.error("Asset exceeds max length 4096000");
/*     */       }
/* 111 */       pos += VarInt.length(buffer, pos);
/* 112 */       for (int i = 0; i < assetCount; i++) {
/* 113 */         ValidationResult structResult = AssetEditorAsset.validateStructure(buffer, pos);
/* 114 */         if (!structResult.isValid()) {
/* 115 */           return ValidationResult.error("Invalid AssetEditorAsset in Asset[" + i + "]: " + structResult.error());
/*     */         }
/* 117 */         pos += AssetEditorAsset.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 120 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorExportDeleteAssets clone() {
/* 124 */     AssetEditorExportDeleteAssets copy = new AssetEditorExportDeleteAssets();
/* 125 */     copy.asset = (this.asset != null) ? (AssetEditorAsset[])Arrays.<AssetEditorAsset>stream(this.asset).map(e -> e.clone()).toArray(x$0 -> new AssetEditorAsset[x$0]) : null;
/* 126 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorExportDeleteAssets other;
/* 132 */     if (this == obj) return true; 
/* 133 */     if (obj instanceof AssetEditorExportDeleteAssets) { other = (AssetEditorExportDeleteAssets)obj; } else { return false; }
/* 134 */      return Arrays.equals((Object[])this.asset, (Object[])other.asset);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 139 */     int result = 1;
/* 140 */     result = 31 * result + Arrays.hashCode((Object[])this.asset);
/* 141 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorExportDeleteAssets.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */