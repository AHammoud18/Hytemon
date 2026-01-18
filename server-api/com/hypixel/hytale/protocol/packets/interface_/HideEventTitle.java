/*    */ package com.hypixel.hytale.protocol.packets.interface_;
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
/*    */ public class HideEventTitle
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 215;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 4;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 4;
/*    */   public static final int MAX_SIZE = 4;
/*    */   public float fadeOutDuration;
/*    */   
/*    */   public int getId() {
/* 25 */     return 215;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public HideEventTitle() {}
/*    */ 
/*    */   
/*    */   public HideEventTitle(float fadeOutDuration) {
/* 34 */     this.fadeOutDuration = fadeOutDuration;
/*    */   }
/*    */   
/*    */   public HideEventTitle(@Nonnull HideEventTitle other) {
/* 38 */     this.fadeOutDuration = other.fadeOutDuration;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static HideEventTitle deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     HideEventTitle obj = new HideEventTitle();
/*    */     
/* 45 */     obj.fadeOutDuration = buf.getFloatLE(offset + 0);
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
/* 58 */     buf.writeFloatLE(this.fadeOutDuration);
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
/*    */   public HideEventTitle clone() {
/* 77 */     HideEventTitle copy = new HideEventTitle();
/* 78 */     copy.fadeOutDuration = this.fadeOutDuration;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     HideEventTitle other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof HideEventTitle) { other = (HideEventTitle)obj; } else { return false; }
/* 87 */      return (this.fadeOutDuration == other.fadeOutDuration);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { Float.valueOf(this.fadeOutDuration) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\HideEventTitle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */