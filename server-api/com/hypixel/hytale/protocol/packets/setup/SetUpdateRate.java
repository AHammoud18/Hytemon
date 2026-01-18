/*    */ package com.hypixel.hytale.protocol.packets.setup;
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
/*    */ public class SetUpdateRate
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 29;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 4;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 4;
/*    */   public static final int MAX_SIZE = 4;
/*    */   public int updatesPerSecond;
/*    */   
/*    */   public int getId() {
/* 25 */     return 29;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SetUpdateRate() {}
/*    */ 
/*    */   
/*    */   public SetUpdateRate(int updatesPerSecond) {
/* 34 */     this.updatesPerSecond = updatesPerSecond;
/*    */   }
/*    */   
/*    */   public SetUpdateRate(@Nonnull SetUpdateRate other) {
/* 38 */     this.updatesPerSecond = other.updatesPerSecond;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static SetUpdateRate deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     SetUpdateRate obj = new SetUpdateRate();
/*    */     
/* 45 */     obj.updatesPerSecond = buf.getIntLE(offset + 0);
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
/* 58 */     buf.writeIntLE(this.updatesPerSecond);
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
/*    */   public SetUpdateRate clone() {
/* 77 */     SetUpdateRate copy = new SetUpdateRate();
/* 78 */     copy.updatesPerSecond = this.updatesPerSecond;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     SetUpdateRate other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof SetUpdateRate) { other = (SetUpdateRate)obj; } else { return false; }
/* 87 */      return (this.updatesPerSecond == other.updatesPerSecond);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { Integer.valueOf(this.updatesPerSecond) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\setup\SetUpdateRate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */