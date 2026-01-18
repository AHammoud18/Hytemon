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
/*    */ public class AssetEditorUpdateWeatherPreviewLock
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 354;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 1;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 1;
/*    */   public static final int MAX_SIZE = 1;
/*    */   public boolean locked;
/*    */   
/*    */   public int getId() {
/* 25 */     return 354;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AssetEditorUpdateWeatherPreviewLock() {}
/*    */ 
/*    */   
/*    */   public AssetEditorUpdateWeatherPreviewLock(boolean locked) {
/* 34 */     this.locked = locked;
/*    */   }
/*    */   
/*    */   public AssetEditorUpdateWeatherPreviewLock(@Nonnull AssetEditorUpdateWeatherPreviewLock other) {
/* 38 */     this.locked = other.locked;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static AssetEditorUpdateWeatherPreviewLock deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     AssetEditorUpdateWeatherPreviewLock obj = new AssetEditorUpdateWeatherPreviewLock();
/*    */     
/* 45 */     obj.locked = (buf.getByte(offset + 0) != 0);
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
/* 58 */     buf.writeByte(this.locked ? 1 : 0);
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
/*    */   public AssetEditorUpdateWeatherPreviewLock clone() {
/* 77 */     AssetEditorUpdateWeatherPreviewLock copy = new AssetEditorUpdateWeatherPreviewLock();
/* 78 */     copy.locked = this.locked;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     AssetEditorUpdateWeatherPreviewLock other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof AssetEditorUpdateWeatherPreviewLock) { other = (AssetEditorUpdateWeatherPreviewLock)obj; } else { return false; }
/* 87 */      return (this.locked == other.locked);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { Boolean.valueOf(this.locked) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorUpdateWeatherPreviewLock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */