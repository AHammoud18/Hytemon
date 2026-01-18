/*    */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AssetEditorUpdateSecondsPerGameDay
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 353;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 8;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 8;
/*    */   public static final int MAX_SIZE = 8;
/*    */   public int daytimeDurationSeconds;
/*    */   public int nighttimeDurationSeconds;
/*    */   
/*    */   public int getId() {
/* 25 */     return 353;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AssetEditorUpdateSecondsPerGameDay() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public AssetEditorUpdateSecondsPerGameDay(int daytimeDurationSeconds, int nighttimeDurationSeconds) {
/* 35 */     this.daytimeDurationSeconds = daytimeDurationSeconds;
/* 36 */     this.nighttimeDurationSeconds = nighttimeDurationSeconds;
/*    */   }
/*    */   
/*    */   public AssetEditorUpdateSecondsPerGameDay(@Nonnull AssetEditorUpdateSecondsPerGameDay other) {
/* 40 */     this.daytimeDurationSeconds = other.daytimeDurationSeconds;
/* 41 */     this.nighttimeDurationSeconds = other.nighttimeDurationSeconds;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static AssetEditorUpdateSecondsPerGameDay deserialize(@Nonnull ByteBuf buf, int offset) {
/* 46 */     AssetEditorUpdateSecondsPerGameDay obj = new AssetEditorUpdateSecondsPerGameDay();
/*    */     
/* 48 */     obj.daytimeDurationSeconds = buf.getIntLE(offset + 0);
/* 49 */     obj.nighttimeDurationSeconds = buf.getIntLE(offset + 4);
/*    */ 
/*    */     
/* 52 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 56 */     return 8;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 62 */     buf.writeIntLE(this.daytimeDurationSeconds);
/* 63 */     buf.writeIntLE(this.nighttimeDurationSeconds);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 69 */     return 8;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 73 */     if (buffer.readableBytes() - offset < 8) {
/* 74 */       return ValidationResult.error("Buffer too small: expected at least 8 bytes");
/*    */     }
/*    */ 
/*    */     
/* 78 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public AssetEditorUpdateSecondsPerGameDay clone() {
/* 82 */     AssetEditorUpdateSecondsPerGameDay copy = new AssetEditorUpdateSecondsPerGameDay();
/* 83 */     copy.daytimeDurationSeconds = this.daytimeDurationSeconds;
/* 84 */     copy.nighttimeDurationSeconds = this.nighttimeDurationSeconds;
/* 85 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     AssetEditorUpdateSecondsPerGameDay other;
/* 91 */     if (this == obj) return true; 
/* 92 */     if (obj instanceof AssetEditorUpdateSecondsPerGameDay) { other = (AssetEditorUpdateSecondsPerGameDay)obj; } else { return false; }
/* 93 */      return (this.daytimeDurationSeconds == other.daytimeDurationSeconds && this.nighttimeDurationSeconds == other.nighttimeDurationSeconds);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 98 */     return Objects.hash(new Object[] { Integer.valueOf(this.daytimeDurationSeconds), Integer.valueOf(this.nighttimeDurationSeconds) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorUpdateSecondsPerGameDay.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */