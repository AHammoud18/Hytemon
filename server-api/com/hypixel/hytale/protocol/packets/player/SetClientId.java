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
/*    */ 
/*    */ public class SetClientId
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 100;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 4;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 4;
/*    */   public static final int MAX_SIZE = 4;
/*    */   public int clientId;
/*    */   
/*    */   public int getId() {
/* 25 */     return 100;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SetClientId() {}
/*    */ 
/*    */   
/*    */   public SetClientId(int clientId) {
/* 34 */     this.clientId = clientId;
/*    */   }
/*    */   
/*    */   public SetClientId(@Nonnull SetClientId other) {
/* 38 */     this.clientId = other.clientId;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static SetClientId deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     SetClientId obj = new SetClientId();
/*    */     
/* 45 */     obj.clientId = buf.getIntLE(offset + 0);
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
/* 58 */     buf.writeIntLE(this.clientId);
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
/*    */   public SetClientId clone() {
/* 77 */     SetClientId copy = new SetClientId();
/* 78 */     copy.clientId = this.clientId;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     SetClientId other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof SetClientId) { other = (SetClientId)obj; } else { return false; }
/* 87 */      return (this.clientId == other.clientId);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { Integer.valueOf(this.clientId) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\player\SetClientId.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */