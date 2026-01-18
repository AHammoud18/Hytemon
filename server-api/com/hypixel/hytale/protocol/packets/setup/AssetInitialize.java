/*     */ package com.hypixel.hytale.protocol.packets.setup;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Asset;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AssetInitialize
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 24;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 4;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 4;
/*     */   public static final int MAX_SIZE = 2121;
/*     */   
/*     */   public int getId() {
/*  25 */     return 24;
/*     */   }
/*     */   @Nonnull
/*  28 */   public Asset asset = new Asset();
/*     */ 
/*     */   
/*     */   public int size;
/*     */ 
/*     */   
/*     */   public AssetInitialize(@Nonnull Asset asset, int size) {
/*  35 */     this.asset = asset;
/*  36 */     this.size = size;
/*     */   }
/*     */   
/*     */   public AssetInitialize(@Nonnull AssetInitialize other) {
/*  40 */     this.asset = other.asset;
/*  41 */     this.size = other.size;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetInitialize deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     AssetInitialize obj = new AssetInitialize();
/*     */     
/*  48 */     obj.size = buf.getIntLE(offset + 0);
/*     */     
/*  50 */     int pos = offset + 4;
/*  51 */     obj.asset = Asset.deserialize(buf, pos);
/*  52 */     pos += Asset.computeBytesConsumed(buf, pos);
/*     */     
/*  54 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  58 */     int pos = offset + 4;
/*  59 */     pos += Asset.computeBytesConsumed(buf, pos);
/*  60 */     return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  67 */     buf.writeIntLE(this.size);
/*     */     
/*  69 */     this.asset.serialize(buf);
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  74 */     int size = 4;
/*  75 */     size += this.asset.computeSize();
/*     */     
/*  77 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  81 */     if (buffer.readableBytes() - offset < 4) {
/*  82 */       return ValidationResult.error("Buffer too small: expected at least 4 bytes");
/*     */     }
/*     */ 
/*     */     
/*  86 */     int pos = offset + 4;
/*     */     
/*  88 */     ValidationResult assetResult = Asset.validateStructure(buffer, pos);
/*  89 */     if (!assetResult.isValid()) {
/*  90 */       return ValidationResult.error("Invalid Asset: " + assetResult.error());
/*     */     }
/*  92 */     pos += Asset.computeBytesConsumed(buffer, pos);
/*  93 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetInitialize clone() {
/*  97 */     AssetInitialize copy = new AssetInitialize();
/*  98 */     copy.asset = this.asset.clone();
/*  99 */     copy.size = this.size;
/* 100 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetInitialize other;
/* 106 */     if (this == obj) return true; 
/* 107 */     if (obj instanceof AssetInitialize) { other = (AssetInitialize)obj; } else { return false; }
/* 108 */      return (Objects.equals(this.asset, other.asset) && this.size == other.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     return Objects.hash(new Object[] { this.asset, Integer.valueOf(this.size) });
/*     */   }
/*     */   
/*     */   public AssetInitialize() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\setup\AssetInitialize.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */