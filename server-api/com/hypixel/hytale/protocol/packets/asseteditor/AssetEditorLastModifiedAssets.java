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
/*     */ public class AssetEditorLastModifiedAssets
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 339;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public AssetInfo[] assets;
/*     */   
/*     */   public int getId() {
/*  25 */     return 339;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorLastModifiedAssets() {}
/*     */ 
/*     */   
/*     */   public AssetEditorLastModifiedAssets(@Nullable AssetInfo[] assets) {
/*  34 */     this.assets = assets;
/*     */   }
/*     */   
/*     */   public AssetEditorLastModifiedAssets(@Nonnull AssetEditorLastModifiedAssets other) {
/*  38 */     this.assets = other.assets;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorLastModifiedAssets deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     AssetEditorLastModifiedAssets obj = new AssetEditorLastModifiedAssets();
/*  44 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  46 */     int pos = offset + 1;
/*  47 */     if ((nullBits & 0x1) != 0) { int assetsCount = VarInt.peek(buf, pos);
/*  48 */       if (assetsCount < 0) throw ProtocolException.negativeLength("Assets", assetsCount); 
/*  49 */       if (assetsCount > 4096000) throw ProtocolException.arrayTooLong("Assets", assetsCount, 4096000); 
/*  50 */       int assetsVarLen = VarInt.size(assetsCount);
/*  51 */       if ((pos + assetsVarLen) + assetsCount * 11L > buf.readableBytes())
/*  52 */         throw ProtocolException.bufferTooSmall("Assets", pos + assetsVarLen + assetsCount * 11, buf.readableBytes()); 
/*  53 */       pos += assetsVarLen;
/*  54 */       obj.assets = new AssetInfo[assetsCount];
/*  55 */       for (int i = 0; i < assetsCount; i++) {
/*  56 */         obj.assets[i] = AssetInfo.deserialize(buf, pos);
/*  57 */         pos += AssetInfo.computeBytesConsumed(buf, pos);
/*     */       }  }
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     byte nullBits = buf.getByte(offset);
/*  65 */     int pos = offset + 1;
/*  66 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  67 */       for (int i = 0; i < arrLen; ) { pos += AssetInfo.computeBytesConsumed(buf, pos); i++; }  }
/*  68 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  74 */     byte nullBits = 0;
/*  75 */     if (this.assets != null) nullBits = (byte)(nullBits | 0x1); 
/*  76 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  79 */     if (this.assets != null) { if (this.assets.length > 4096000) throw ProtocolException.arrayTooLong("Assets", this.assets.length, 4096000);  VarInt.write(buf, this.assets.length); for (AssetInfo item : this.assets) item.serialize(buf);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  84 */     int size = 1;
/*  85 */     if (this.assets != null) {
/*  86 */       int assetsSize = 0;
/*  87 */       for (AssetInfo elem : this.assets) assetsSize += elem.computeSize(); 
/*  88 */       size += VarInt.size(this.assets.length) + assetsSize;
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
/* 104 */       int assetsCount = VarInt.peek(buffer, pos);
/* 105 */       if (assetsCount < 0) {
/* 106 */         return ValidationResult.error("Invalid array count for Assets");
/*     */       }
/* 108 */       if (assetsCount > 4096000) {
/* 109 */         return ValidationResult.error("Assets exceeds max length 4096000");
/*     */       }
/* 111 */       pos += VarInt.length(buffer, pos);
/* 112 */       for (int i = 0; i < assetsCount; i++) {
/* 113 */         ValidationResult structResult = AssetInfo.validateStructure(buffer, pos);
/* 114 */         if (!structResult.isValid()) {
/* 115 */           return ValidationResult.error("Invalid AssetInfo in Assets[" + i + "]: " + structResult.error());
/*     */         }
/* 117 */         pos += AssetInfo.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 120 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorLastModifiedAssets clone() {
/* 124 */     AssetEditorLastModifiedAssets copy = new AssetEditorLastModifiedAssets();
/* 125 */     copy.assets = (this.assets != null) ? (AssetInfo[])Arrays.<AssetInfo>stream(this.assets).map(e -> e.clone()).toArray(x$0 -> new AssetInfo[x$0]) : null;
/* 126 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorLastModifiedAssets other;
/* 132 */     if (this == obj) return true; 
/* 133 */     if (obj instanceof AssetEditorLastModifiedAssets) { other = (AssetEditorLastModifiedAssets)obj; } else { return false; }
/* 134 */      return Arrays.equals((Object[])this.assets, (Object[])other.assets);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 139 */     int result = 1;
/* 140 */     result = 31 * result + Arrays.hashCode((Object[])this.assets);
/* 141 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorLastModifiedAssets.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */