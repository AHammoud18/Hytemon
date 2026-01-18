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
/*    */ 
/*    */ public class SetPage
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 216;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 2;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 2;
/*    */   public static final int MAX_SIZE = 2;
/*    */   
/*    */   public int getId() {
/* 25 */     return 216;
/*    */   }
/*    */   @Nonnull
/* 28 */   public Page page = Page.None;
/*    */ 
/*    */   
/*    */   public boolean canCloseThroughInteraction;
/*    */ 
/*    */   
/*    */   public SetPage(@Nonnull Page page, boolean canCloseThroughInteraction) {
/* 35 */     this.page = page;
/* 36 */     this.canCloseThroughInteraction = canCloseThroughInteraction;
/*    */   }
/*    */   
/*    */   public SetPage(@Nonnull SetPage other) {
/* 40 */     this.page = other.page;
/* 41 */     this.canCloseThroughInteraction = other.canCloseThroughInteraction;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static SetPage deserialize(@Nonnull ByteBuf buf, int offset) {
/* 46 */     SetPage obj = new SetPage();
/*    */     
/* 48 */     obj.page = Page.fromValue(buf.getByte(offset + 0));
/* 49 */     obj.canCloseThroughInteraction = (buf.getByte(offset + 1) != 0);
/*    */ 
/*    */     
/* 52 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 56 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 62 */     buf.writeByte(this.page.getValue());
/* 63 */     buf.writeByte(this.canCloseThroughInteraction ? 1 : 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 69 */     return 2;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 73 */     if (buffer.readableBytes() - offset < 2) {
/* 74 */       return ValidationResult.error("Buffer too small: expected at least 2 bytes");
/*    */     }
/*    */ 
/*    */     
/* 78 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public SetPage clone() {
/* 82 */     SetPage copy = new SetPage();
/* 83 */     copy.page = this.page;
/* 84 */     copy.canCloseThroughInteraction = this.canCloseThroughInteraction;
/* 85 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     SetPage other;
/* 91 */     if (this == obj) return true; 
/* 92 */     if (obj instanceof SetPage) { other = (SetPage)obj; } else { return false; }
/* 93 */      return (Objects.equals(this.page, other.page) && this.canCloseThroughInteraction == other.canCloseThroughInteraction);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 98 */     return Objects.hash(new Object[] { this.page, Boolean.valueOf(this.canCloseThroughInteraction) });
/*    */   }
/*    */   
/*    */   public SetPage() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\SetPage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */