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
/*    */ public class AssetEditorModifiedAssetsCount
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 340;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 4;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 4;
/*    */   public static final int MAX_SIZE = 4;
/*    */   public int count;
/*    */   
/*    */   public int getId() {
/* 25 */     return 340;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AssetEditorModifiedAssetsCount() {}
/*    */ 
/*    */   
/*    */   public AssetEditorModifiedAssetsCount(int count) {
/* 34 */     this.count = count;
/*    */   }
/*    */   
/*    */   public AssetEditorModifiedAssetsCount(@Nonnull AssetEditorModifiedAssetsCount other) {
/* 38 */     this.count = other.count;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static AssetEditorModifiedAssetsCount deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     AssetEditorModifiedAssetsCount obj = new AssetEditorModifiedAssetsCount();
/*    */     
/* 45 */     obj.count = buf.getIntLE(offset + 0);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 4;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 58 */     buf.writeIntLE(this.count);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 64 */     return 4;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 68 */     if (buffer.readableBytes() - offset < 4) {
/* 69 */       return ValidationResult.error("Buffer too small: expected at least 4 bytes");
/*    */     }
/*    */ 
/*    */     
/* 73 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public AssetEditorModifiedAssetsCount clone() {
/* 77 */     AssetEditorModifiedAssetsCount copy = new AssetEditorModifiedAssetsCount();
/* 78 */     copy.count = this.count;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     AssetEditorModifiedAssetsCount other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof AssetEditorModifiedAssetsCount) { other = (AssetEditorModifiedAssetsCount)obj; } else { return false; }
/* 87 */      return (this.count == other.count);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { Integer.valueOf(this.count) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorModifiedAssetsCount.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */