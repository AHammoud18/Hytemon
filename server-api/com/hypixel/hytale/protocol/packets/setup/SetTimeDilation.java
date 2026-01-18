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
/*    */ public class SetTimeDilation
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 30;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 4;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 4;
/*    */   public static final int MAX_SIZE = 4;
/*    */   public float timeDilation;
/*    */   
/*    */   public int getId() {
/* 25 */     return 30;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SetTimeDilation() {}
/*    */ 
/*    */   
/*    */   public SetTimeDilation(float timeDilation) {
/* 34 */     this.timeDilation = timeDilation;
/*    */   }
/*    */   
/*    */   public SetTimeDilation(@Nonnull SetTimeDilation other) {
/* 38 */     this.timeDilation = other.timeDilation;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static SetTimeDilation deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     SetTimeDilation obj = new SetTimeDilation();
/*    */     
/* 45 */     obj.timeDilation = buf.getFloatLE(offset + 0);
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
/* 58 */     buf.writeFloatLE(this.timeDilation);
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
/*    */   public SetTimeDilation clone() {
/* 77 */     SetTimeDilation copy = new SetTimeDilation();
/* 78 */     copy.timeDilation = this.timeDilation;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     SetTimeDilation other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof SetTimeDilation) { other = (SetTimeDilation)obj; } else { return false; }
/* 87 */      return (this.timeDilation == other.timeDilation);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { Float.valueOf(this.timeDilation) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\setup\SetTimeDilation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */