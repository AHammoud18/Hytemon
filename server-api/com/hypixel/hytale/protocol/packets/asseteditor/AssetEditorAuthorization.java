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
/*    */ public class AssetEditorAuthorization
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 303;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 1;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 1;
/*    */   public static final int MAX_SIZE = 1;
/*    */   public boolean canUse;
/*    */   
/*    */   public int getId() {
/* 25 */     return 303;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AssetEditorAuthorization() {}
/*    */ 
/*    */   
/*    */   public AssetEditorAuthorization(boolean canUse) {
/* 34 */     this.canUse = canUse;
/*    */   }
/*    */   
/*    */   public AssetEditorAuthorization(@Nonnull AssetEditorAuthorization other) {
/* 38 */     this.canUse = other.canUse;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static AssetEditorAuthorization deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     AssetEditorAuthorization obj = new AssetEditorAuthorization();
/*    */     
/* 45 */     obj.canUse = (buf.getByte(offset + 0) != 0);
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
/* 58 */     buf.writeByte(this.canUse ? 1 : 0);
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
/*    */   public AssetEditorAuthorization clone() {
/* 77 */     AssetEditorAuthorization copy = new AssetEditorAuthorization();
/* 78 */     copy.canUse = this.canUse;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     AssetEditorAuthorization other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof AssetEditorAuthorization) { other = (AssetEditorAuthorization)obj; } else { return false; }
/* 87 */      return (this.canUse == other.canUse);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { Boolean.valueOf(this.canUse) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorAuthorization.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */