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
/*    */ 
/*    */ public class BuilderToolsSetSoundSet
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 418;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 4;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 4;
/*    */   public static final int MAX_SIZE = 4;
/*    */   public int soundSetIndex;
/*    */   
/*    */   public int getId() {
/* 25 */     return 418;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public BuilderToolsSetSoundSet() {}
/*    */ 
/*    */   
/*    */   public BuilderToolsSetSoundSet(int soundSetIndex) {
/* 34 */     this.soundSetIndex = soundSetIndex;
/*    */   }
/*    */   
/*    */   public BuilderToolsSetSoundSet(@Nonnull BuilderToolsSetSoundSet other) {
/* 38 */     this.soundSetIndex = other.soundSetIndex;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static BuilderToolsSetSoundSet deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     BuilderToolsSetSoundSet obj = new BuilderToolsSetSoundSet();
/*    */     
/* 45 */     obj.soundSetIndex = buf.getIntLE(offset + 0);
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
/* 58 */     buf.writeIntLE(this.soundSetIndex);
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
/*    */   public BuilderToolsSetSoundSet clone() {
/* 77 */     BuilderToolsSetSoundSet copy = new BuilderToolsSetSoundSet();
/* 78 */     copy.soundSetIndex = this.soundSetIndex;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     BuilderToolsSetSoundSet other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof BuilderToolsSetSoundSet) { other = (BuilderToolsSetSoundSet)obj; } else { return false; }
/* 87 */      return (this.soundSetIndex == other.soundSetIndex);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { Integer.valueOf(this.soundSetIndex) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolsSetSoundSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */