/*    */ package com.hypixel.hytale.protocol;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AmbienceFXBlockSoundSet
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*    */   public static final int FIXED_BLOCK_SIZE = 13;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 13;
/*    */   public static final int MAX_SIZE = 13;
/*    */   public int blockSoundSetIndex;
/*    */   @Nullable
/*    */   public Rangef percent;
/*    */   
/*    */   public AmbienceFXBlockSoundSet() {}
/*    */   
/*    */   public AmbienceFXBlockSoundSet(int blockSoundSetIndex, @Nullable Rangef percent) {
/* 27 */     this.blockSoundSetIndex = blockSoundSetIndex;
/* 28 */     this.percent = percent;
/*    */   }
/*    */   
/*    */   public AmbienceFXBlockSoundSet(@Nonnull AmbienceFXBlockSoundSet other) {
/* 32 */     this.blockSoundSetIndex = other.blockSoundSetIndex;
/* 33 */     this.percent = other.percent;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static AmbienceFXBlockSoundSet deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     AmbienceFXBlockSoundSet obj = new AmbienceFXBlockSoundSet();
/* 39 */     byte nullBits = buf.getByte(offset);
/* 40 */     obj.blockSoundSetIndex = buf.getIntLE(offset + 1);
/* 41 */     if ((nullBits & 0x1) != 0) obj.percent = Rangef.deserialize(buf, offset + 5);
/*    */ 
/*    */     
/* 44 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 48 */     return 13;
/*    */   }
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 52 */     byte nullBits = 0;
/* 53 */     if (this.percent != null) nullBits = (byte)(nullBits | 0x1); 
/* 54 */     buf.writeByte(nullBits);
/*    */     
/* 56 */     buf.writeIntLE(this.blockSoundSetIndex);
/* 57 */     if (this.percent != null) { this.percent.serialize(buf); } else { buf.writeZero(8); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 63 */     return 13;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 67 */     if (buffer.readableBytes() - offset < 13) {
/* 68 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*    */     }
/*    */ 
/*    */     
/* 72 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public AmbienceFXBlockSoundSet clone() {
/* 76 */     AmbienceFXBlockSoundSet copy = new AmbienceFXBlockSoundSet();
/* 77 */     copy.blockSoundSetIndex = this.blockSoundSetIndex;
/* 78 */     copy.percent = (this.percent != null) ? this.percent.clone() : null;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     AmbienceFXBlockSoundSet other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof AmbienceFXBlockSoundSet) { other = (AmbienceFXBlockSoundSet)obj; } else { return false; }
/* 87 */      return (this.blockSoundSetIndex == other.blockSoundSetIndex && Objects.equals(this.percent, other.percent));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { Integer.valueOf(this.blockSoundSetIndex), this.percent });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\AmbienceFXBlockSoundSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */