/*    */ package com.hypixel.hytale.protocol.packets.player;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientReady
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 105;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 2;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 2;
/*    */   public static final int MAX_SIZE = 2;
/*    */   public boolean readyForChunks;
/*    */   public boolean readyForGameplay;
/*    */   
/*    */   public int getId() {
/* 25 */     return 105;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ClientReady() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public ClientReady(boolean readyForChunks, boolean readyForGameplay) {
/* 35 */     this.readyForChunks = readyForChunks;
/* 36 */     this.readyForGameplay = readyForGameplay;
/*    */   }
/*    */   
/*    */   public ClientReady(@Nonnull ClientReady other) {
/* 40 */     this.readyForChunks = other.readyForChunks;
/* 41 */     this.readyForGameplay = other.readyForGameplay;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ClientReady deserialize(@Nonnull ByteBuf buf, int offset) {
/* 46 */     ClientReady obj = new ClientReady();
/*    */     
/* 48 */     obj.readyForChunks = (buf.getByte(offset + 0) != 0);
/* 49 */     obj.readyForGameplay = (buf.getByte(offset + 1) != 0);
/*    */ 
/*    */     
/* 52 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 56 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 62 */     buf.writeByte(this.readyForChunks ? 1 : 0);
/* 63 */     buf.writeByte(this.readyForGameplay ? 1 : 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 69 */     return 2;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 73 */     if (buffer.readableBytes() - offset < 2) {
/* 74 */       return ValidationResult.error("Buffer too small: expected at least 2 bytes");
/*    */     }
/*    */ 
/*    */     
/* 78 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public ClientReady clone() {
/* 82 */     ClientReady copy = new ClientReady();
/* 83 */     copy.readyForChunks = this.readyForChunks;
/* 84 */     copy.readyForGameplay = this.readyForGameplay;
/* 85 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     ClientReady other;
/* 91 */     if (this == obj) return true; 
/* 92 */     if (obj instanceof ClientReady) { other = (ClientReady)obj; } else { return false; }
/* 93 */      return (this.readyForChunks == other.readyForChunks && this.readyForGameplay == other.readyForGameplay);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 98 */     return Objects.hash(new Object[] { Boolean.valueOf(this.readyForChunks), Boolean.valueOf(this.readyForGameplay) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\player\ClientReady.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */