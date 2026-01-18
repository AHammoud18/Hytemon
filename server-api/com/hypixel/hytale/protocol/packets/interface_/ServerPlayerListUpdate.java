/*    */ package com.hypixel.hytale.protocol.packets.interface_;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.io.PacketIO;
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerPlayerListUpdate
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 32;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 32;
/*    */   public static final int MAX_SIZE = 32;
/*    */   @Nonnull
/* 20 */   public UUID uuid = new UUID(0L, 0L); @Nonnull
/* 21 */   public UUID worldUuid = new UUID(0L, 0L);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerPlayerListUpdate(@Nonnull UUID uuid, @Nonnull UUID worldUuid) {
/* 27 */     this.uuid = uuid;
/* 28 */     this.worldUuid = worldUuid;
/*    */   }
/*    */   
/*    */   public ServerPlayerListUpdate(@Nonnull ServerPlayerListUpdate other) {
/* 32 */     this.uuid = other.uuid;
/* 33 */     this.worldUuid = other.worldUuid;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ServerPlayerListUpdate deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     ServerPlayerListUpdate obj = new ServerPlayerListUpdate();
/*    */     
/* 40 */     obj.uuid = PacketIO.readUUID(buf, offset + 0);
/* 41 */     obj.worldUuid = PacketIO.readUUID(buf, offset + 16);
/*    */ 
/*    */     
/* 44 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 48 */     return 32;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 53 */     PacketIO.writeUUID(buf, this.uuid);
/* 54 */     PacketIO.writeUUID(buf, this.worldUuid);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 60 */     return 32;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 64 */     if (buffer.readableBytes() - offset < 32) {
/* 65 */       return ValidationResult.error("Buffer too small: expected at least 32 bytes");
/*    */     }
/*    */ 
/*    */     
/* 69 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public ServerPlayerListUpdate clone() {
/* 73 */     ServerPlayerListUpdate copy = new ServerPlayerListUpdate();
/* 74 */     copy.uuid = this.uuid;
/* 75 */     copy.worldUuid = this.worldUuid;
/* 76 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     ServerPlayerListUpdate other;
/* 82 */     if (this == obj) return true; 
/* 83 */     if (obj instanceof ServerPlayerListUpdate) { other = (ServerPlayerListUpdate)obj; } else { return false; }
/* 84 */      return (Objects.equals(this.uuid, other.uuid) && Objects.equals(this.worldUuid, other.worldUuid));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return Objects.hash(new Object[] { this.uuid, this.worldUuid });
/*    */   }
/*    */   
/*    */   public ServerPlayerListUpdate() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\ServerPlayerListUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */