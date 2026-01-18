/*    */ package com.hypixel.hytale.protocol.packets.world;
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
/*    */ public class SetPaused
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 158;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 1;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 1;
/*    */   public static final int MAX_SIZE = 1;
/*    */   public boolean paused;
/*    */   
/*    */   public int getId() {
/* 25 */     return 158;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SetPaused() {}
/*    */ 
/*    */   
/*    */   public SetPaused(boolean paused) {
/* 34 */     this.paused = paused;
/*    */   }
/*    */   
/*    */   public SetPaused(@Nonnull SetPaused other) {
/* 38 */     this.paused = other.paused;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static SetPaused deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     SetPaused obj = new SetPaused();
/*    */     
/* 45 */     obj.paused = (buf.getByte(offset + 0) != 0);
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
/* 58 */     buf.writeByte(this.paused ? 1 : 0);
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
/*    */   public SetPaused clone() {
/* 77 */     SetPaused copy = new SetPaused();
/* 78 */     copy.paused = this.paused;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     SetPaused other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof SetPaused) { other = (SetPaused)obj; } else { return false; }
/* 87 */      return (this.paused == other.paused);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { Boolean.valueOf(this.paused) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\SetPaused.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */