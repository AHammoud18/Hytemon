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
/*    */ public class ServerSetPaused
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 159;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 1;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 1;
/*    */   public static final int MAX_SIZE = 1;
/*    */   public boolean paused;
/*    */   
/*    */   public int getId() {
/* 25 */     return 159;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerSetPaused() {}
/*    */ 
/*    */   
/*    */   public ServerSetPaused(boolean paused) {
/* 34 */     this.paused = paused;
/*    */   }
/*    */   
/*    */   public ServerSetPaused(@Nonnull ServerSetPaused other) {
/* 38 */     this.paused = other.paused;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ServerSetPaused deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     ServerSetPaused obj = new ServerSetPaused();
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
/*    */   public ServerSetPaused clone() {
/* 77 */     ServerSetPaused copy = new ServerSetPaused();
/* 78 */     copy.paused = this.paused;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     ServerSetPaused other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof ServerSetPaused) { other = (ServerSetPaused)obj; } else { return false; }
/* 87 */      return (this.paused == other.paused);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { Boolean.valueOf(this.paused) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\ServerSetPaused.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */