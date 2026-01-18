/*    */ package com.hypixel.hytale.protocol.packets.world;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.InstantData;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class UpdateTime
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 146;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*    */   public static final int FIXED_BLOCK_SIZE = 13;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 13;
/*    */   public static final int MAX_SIZE = 13;
/*    */   @Nullable
/*    */   public InstantData gameTime;
/*    */   
/*    */   public int getId() {
/* 25 */     return 146;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public UpdateTime() {}
/*    */ 
/*    */   
/*    */   public UpdateTime(@Nullable InstantData gameTime) {
/* 34 */     this.gameTime = gameTime;
/*    */   }
/*    */   
/*    */   public UpdateTime(@Nonnull UpdateTime other) {
/* 38 */     this.gameTime = other.gameTime;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static UpdateTime deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     UpdateTime obj = new UpdateTime();
/* 44 */     byte nullBits = buf.getByte(offset);
/* 45 */     if ((nullBits & 0x1) != 0) obj.gameTime = InstantData.deserialize(buf, offset + 1);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 13;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 57 */     byte nullBits = 0;
/* 58 */     if (this.gameTime != null) nullBits = (byte)(nullBits | 0x1); 
/* 59 */     buf.writeByte(nullBits);
/*    */     
/* 61 */     if (this.gameTime != null) { this.gameTime.serialize(buf); } else { buf.writeZero(12); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 67 */     return 13;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 71 */     if (buffer.readableBytes() - offset < 13) {
/* 72 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*    */     }
/*    */ 
/*    */     
/* 76 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public UpdateTime clone() {
/* 80 */     UpdateTime copy = new UpdateTime();
/* 81 */     copy.gameTime = (this.gameTime != null) ? this.gameTime.clone() : null;
/* 82 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     UpdateTime other;
/* 88 */     if (this == obj) return true; 
/* 89 */     if (obj instanceof UpdateTime) { other = (UpdateTime)obj; } else { return false; }
/* 90 */      return Objects.equals(this.gameTime, other.gameTime);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return Objects.hash(new Object[] { this.gameTime });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\UpdateTime.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */