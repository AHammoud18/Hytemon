/*    */ package com.hypixel.hytale.protocol.packets.player;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SetGameMode
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 101;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 1;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 1;
/*    */   public static final int MAX_SIZE = 1;
/*    */   
/*    */   public int getId() {
/* 25 */     return 101;
/*    */   }
/*    */   @Nonnull
/* 28 */   public GameMode gameMode = GameMode.Adventure;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SetGameMode(@Nonnull GameMode gameMode) {
/* 34 */     this.gameMode = gameMode;
/*    */   }
/*    */   
/*    */   public SetGameMode(@Nonnull SetGameMode other) {
/* 38 */     this.gameMode = other.gameMode;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static SetGameMode deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     SetGameMode obj = new SetGameMode();
/*    */     
/* 45 */     obj.gameMode = GameMode.fromValue(buf.getByte(offset + 0));
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
/* 58 */     buf.writeByte(this.gameMode.getValue());
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
/*    */   public SetGameMode clone() {
/* 77 */     SetGameMode copy = new SetGameMode();
/* 78 */     copy.gameMode = this.gameMode;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     SetGameMode other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof SetGameMode) { other = (SetGameMode)obj; } else { return false; }
/* 87 */      return Objects.equals(this.gameMode, other.gameMode);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { this.gameMode });
/*    */   }
/*    */   
/*    */   public SetGameMode() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\player\SetGameMode.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */