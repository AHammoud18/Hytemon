/*    */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AssetEditorSubscribeModifiedAssetsChanges
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 341;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 1;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 1;
/*    */   public static final int MAX_SIZE = 1;
/*    */   public boolean subscribe;
/*    */   
/*    */   public int getId() {
/* 25 */     return 341;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AssetEditorSubscribeModifiedAssetsChanges() {}
/*    */ 
/*    */   
/*    */   public AssetEditorSubscribeModifiedAssetsChanges(boolean subscribe) {
/* 34 */     this.subscribe = subscribe;
/*    */   }
/*    */   
/*    */   public AssetEditorSubscribeModifiedAssetsChanges(@Nonnull AssetEditorSubscribeModifiedAssetsChanges other) {
/* 38 */     this.subscribe = other.subscribe;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static AssetEditorSubscribeModifiedAssetsChanges deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     AssetEditorSubscribeModifiedAssetsChanges obj = new AssetEditorSubscribeModifiedAssetsChanges();
/*    */     
/* 45 */     obj.subscribe = (buf.getByte(offset + 0) != 0);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 58 */     buf.writeByte(this.subscribe ? 1 : 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 64 */     return 1;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 68 */     if (buffer.readableBytes() - offset < 1) {
/* 69 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*    */     }
/*    */ 
/*    */     
/* 73 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public AssetEditorSubscribeModifiedAssetsChanges clone() {
/* 77 */     AssetEditorSubscribeModifiedAssetsChanges copy = new AssetEditorSubscribeModifiedAssetsChanges();
/* 78 */     copy.subscribe = this.subscribe;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     AssetEditorSubscribeModifiedAssetsChanges other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof AssetEditorSubscribeModifiedAssetsChanges) { other = (AssetEditorSubscribeModifiedAssetsChanges)obj; } else { return false; }
/* 87 */      return (this.subscribe == other.subscribe);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { Boolean.valueOf(this.subscribe) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorSubscribeModifiedAssetsChanges.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */