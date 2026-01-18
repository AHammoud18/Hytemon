/*    */ package com.hypixel.hytale.protocol.packets.entities;
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
/*    */ public class SetEntitySeed
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 160;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 4;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 4;
/*    */   public static final int MAX_SIZE = 4;
/*    */   public int entitySeed;
/*    */   
/*    */   public int getId() {
/* 25 */     return 160;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SetEntitySeed() {}
/*    */ 
/*    */   
/*    */   public SetEntitySeed(int entitySeed) {
/* 34 */     this.entitySeed = entitySeed;
/*    */   }
/*    */   
/*    */   public SetEntitySeed(@Nonnull SetEntitySeed other) {
/* 38 */     this.entitySeed = other.entitySeed;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static SetEntitySeed deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     SetEntitySeed obj = new SetEntitySeed();
/*    */     
/* 45 */     obj.entitySeed = buf.getIntLE(offset + 0);
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
/* 58 */     buf.writeIntLE(this.entitySeed);
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
/*    */   public SetEntitySeed clone() {
/* 77 */     SetEntitySeed copy = new SetEntitySeed();
/* 78 */     copy.entitySeed = this.entitySeed;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     SetEntitySeed other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof SetEntitySeed) { other = (SetEntitySeed)obj; } else { return false; }
/* 87 */      return (this.entitySeed == other.entitySeed);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { Integer.valueOf(this.entitySeed) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\entities\SetEntitySeed.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */