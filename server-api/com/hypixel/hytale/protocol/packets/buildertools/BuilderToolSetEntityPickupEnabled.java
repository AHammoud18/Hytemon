/*    */ package com.hypixel.hytale.protocol.packets.buildertools;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuilderToolSetEntityPickupEnabled
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 421;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 5;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 5;
/*    */   public static final int MAX_SIZE = 5;
/*    */   public int entityId;
/*    */   public boolean enabled;
/*    */   
/*    */   public int getId() {
/* 25 */     return 421;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public BuilderToolSetEntityPickupEnabled() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public BuilderToolSetEntityPickupEnabled(int entityId, boolean enabled) {
/* 35 */     this.entityId = entityId;
/* 36 */     this.enabled = enabled;
/*    */   }
/*    */   
/*    */   public BuilderToolSetEntityPickupEnabled(@Nonnull BuilderToolSetEntityPickupEnabled other) {
/* 40 */     this.entityId = other.entityId;
/* 41 */     this.enabled = other.enabled;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static BuilderToolSetEntityPickupEnabled deserialize(@Nonnull ByteBuf buf, int offset) {
/* 46 */     BuilderToolSetEntityPickupEnabled obj = new BuilderToolSetEntityPickupEnabled();
/*    */     
/* 48 */     obj.entityId = buf.getIntLE(offset + 0);
/* 49 */     obj.enabled = (buf.getByte(offset + 4) != 0);
/*    */ 
/*    */     
/* 52 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 56 */     return 5;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 62 */     buf.writeIntLE(this.entityId);
/* 63 */     buf.writeByte(this.enabled ? 1 : 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 69 */     return 5;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 73 */     if (buffer.readableBytes() - offset < 5) {
/* 74 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*    */     }
/*    */ 
/*    */     
/* 78 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public BuilderToolSetEntityPickupEnabled clone() {
/* 82 */     BuilderToolSetEntityPickupEnabled copy = new BuilderToolSetEntityPickupEnabled();
/* 83 */     copy.entityId = this.entityId;
/* 84 */     copy.enabled = this.enabled;
/* 85 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     BuilderToolSetEntityPickupEnabled other;
/* 91 */     if (this == obj) return true; 
/* 92 */     if (obj instanceof BuilderToolSetEntityPickupEnabled) { other = (BuilderToolSetEntityPickupEnabled)obj; } else { return false; }
/* 93 */      return (this.entityId == other.entityId && this.enabled == other.enabled);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 98 */     return Objects.hash(new Object[] { Integer.valueOf(this.entityId), Boolean.valueOf(this.enabled) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolSetEntityPickupEnabled.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */