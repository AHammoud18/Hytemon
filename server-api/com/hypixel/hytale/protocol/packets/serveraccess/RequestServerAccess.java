/*    */ package com.hypixel.hytale.protocol.packets.serveraccess;
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
/*    */ 
/*    */ public class RequestServerAccess
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 250;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 3;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 3;
/*    */   public static final int MAX_SIZE = 3;
/*    */   
/*    */   public int getId() {
/* 25 */     return 250;
/*    */   }
/*    */   @Nonnull
/* 28 */   public Access access = Access.Private;
/*    */ 
/*    */   
/*    */   public short externalPort;
/*    */ 
/*    */   
/*    */   public RequestServerAccess(@Nonnull Access access, short externalPort) {
/* 35 */     this.access = access;
/* 36 */     this.externalPort = externalPort;
/*    */   }
/*    */   
/*    */   public RequestServerAccess(@Nonnull RequestServerAccess other) {
/* 40 */     this.access = other.access;
/* 41 */     this.externalPort = other.externalPort;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static RequestServerAccess deserialize(@Nonnull ByteBuf buf, int offset) {
/* 46 */     RequestServerAccess obj = new RequestServerAccess();
/*    */     
/* 48 */     obj.access = Access.fromValue(buf.getByte(offset + 0));
/* 49 */     obj.externalPort = buf.getShortLE(offset + 1);
/*    */ 
/*    */     
/* 52 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 56 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 62 */     buf.writeByte(this.access.getValue());
/* 63 */     buf.writeShortLE(this.externalPort);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 69 */     return 3;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 73 */     if (buffer.readableBytes() - offset < 3) {
/* 74 */       return ValidationResult.error("Buffer too small: expected at least 3 bytes");
/*    */     }
/*    */ 
/*    */     
/* 78 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public RequestServerAccess clone() {
/* 82 */     RequestServerAccess copy = new RequestServerAccess();
/* 83 */     copy.access = this.access;
/* 84 */     copy.externalPort = this.externalPort;
/* 85 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     RequestServerAccess other;
/* 91 */     if (this == obj) return true; 
/* 92 */     if (obj instanceof RequestServerAccess) { other = (RequestServerAccess)obj; } else { return false; }
/* 93 */      return (Objects.equals(this.access, other.access) && this.externalPort == other.externalPort);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 98 */     return Objects.hash(new Object[] { this.access, Short.valueOf(this.externalPort) });
/*    */   }
/*    */   
/*    */   public RequestServerAccess() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\serveraccess\RequestServerAccess.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */