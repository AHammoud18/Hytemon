/*    */ package com.hypixel.hytale.protocol.packets.world;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClearEditorTimeOverride
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 148;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 0;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 0;
/*    */   public static final int MAX_SIZE = 0;
/*    */   
/*    */   public int getId() {
/* 25 */     return 148;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static ClearEditorTimeOverride deserialize(@Nonnull ByteBuf buf, int offset) {
/* 32 */     ClearEditorTimeOverride obj = new ClearEditorTimeOverride();
/*    */ 
/*    */ 
/*    */     
/* 36 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 40 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 51 */     return 0;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 55 */     if (buffer.readableBytes() - offset < 0) {
/* 56 */       return ValidationResult.error("Buffer too small: expected at least 0 bytes");
/*    */     }
/*    */ 
/*    */     
/* 60 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public ClearEditorTimeOverride clone() {
/* 64 */     return new ClearEditorTimeOverride();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 69 */     if (this == obj) return true; 
/* 70 */     if (obj instanceof ClearEditorTimeOverride) { ClearEditorTimeOverride other = (ClearEditorTimeOverride)obj; } else { return false; }
/* 71 */      return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 76 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\ClearEditorTimeOverride.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */