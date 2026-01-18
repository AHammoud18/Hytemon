/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class StairConnectedBlockRuleSet
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 21;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 21;
/*     */   public static final int MAX_SIZE = 16384026;
/*     */   public int straightBlockId;
/*     */   public int cornerLeftBlockId;
/*     */   public int cornerRightBlockId;
/*     */   public int invertedCornerLeftBlockId;
/*     */   public int invertedCornerRightBlockId;
/*     */   @Nullable
/*     */   public String materialName;
/*     */   
/*     */   public StairConnectedBlockRuleSet() {}
/*     */   
/*     */   public StairConnectedBlockRuleSet(int straightBlockId, int cornerLeftBlockId, int cornerRightBlockId, int invertedCornerLeftBlockId, int invertedCornerRightBlockId, @Nullable String materialName) {
/*  31 */     this.straightBlockId = straightBlockId;
/*  32 */     this.cornerLeftBlockId = cornerLeftBlockId;
/*  33 */     this.cornerRightBlockId = cornerRightBlockId;
/*  34 */     this.invertedCornerLeftBlockId = invertedCornerLeftBlockId;
/*  35 */     this.invertedCornerRightBlockId = invertedCornerRightBlockId;
/*  36 */     this.materialName = materialName;
/*     */   }
/*     */   
/*     */   public StairConnectedBlockRuleSet(@Nonnull StairConnectedBlockRuleSet other) {
/*  40 */     this.straightBlockId = other.straightBlockId;
/*  41 */     this.cornerLeftBlockId = other.cornerLeftBlockId;
/*  42 */     this.cornerRightBlockId = other.cornerRightBlockId;
/*  43 */     this.invertedCornerLeftBlockId = other.invertedCornerLeftBlockId;
/*  44 */     this.invertedCornerRightBlockId = other.invertedCornerRightBlockId;
/*  45 */     this.materialName = other.materialName;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static StairConnectedBlockRuleSet deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     StairConnectedBlockRuleSet obj = new StairConnectedBlockRuleSet();
/*  51 */     byte nullBits = buf.getByte(offset);
/*  52 */     obj.straightBlockId = buf.getIntLE(offset + 1);
/*  53 */     obj.cornerLeftBlockId = buf.getIntLE(offset + 5);
/*  54 */     obj.cornerRightBlockId = buf.getIntLE(offset + 9);
/*  55 */     obj.invertedCornerLeftBlockId = buf.getIntLE(offset + 13);
/*  56 */     obj.invertedCornerRightBlockId = buf.getIntLE(offset + 17);
/*     */     
/*  58 */     int pos = offset + 21;
/*  59 */     if ((nullBits & 0x1) != 0) { int materialNameLen = VarInt.peek(buf, pos);
/*  60 */       if (materialNameLen < 0) throw ProtocolException.negativeLength("MaterialName", materialNameLen); 
/*  61 */       if (materialNameLen > 4096000) throw ProtocolException.stringTooLong("MaterialName", materialNameLen, 4096000); 
/*  62 */       int materialNameVarLen = VarInt.length(buf, pos);
/*  63 */       obj.materialName = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  64 */       pos += materialNameVarLen + materialNameLen; }
/*     */     
/*  66 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  70 */     byte nullBits = buf.getByte(offset);
/*  71 */     int pos = offset + 21;
/*  72 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  73 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  78 */     byte nullBits = 0;
/*  79 */     if (this.materialName != null) nullBits = (byte)(nullBits | 0x1); 
/*  80 */     buf.writeByte(nullBits);
/*     */     
/*  82 */     buf.writeIntLE(this.straightBlockId);
/*  83 */     buf.writeIntLE(this.cornerLeftBlockId);
/*  84 */     buf.writeIntLE(this.cornerRightBlockId);
/*  85 */     buf.writeIntLE(this.invertedCornerLeftBlockId);
/*  86 */     buf.writeIntLE(this.invertedCornerRightBlockId);
/*     */     
/*  88 */     if (this.materialName != null) PacketIO.writeVarString(buf, this.materialName, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  93 */     int size = 21;
/*  94 */     if (this.materialName != null) size += PacketIO.stringSize(this.materialName);
/*     */     
/*  96 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 100 */     if (buffer.readableBytes() - offset < 21) {
/* 101 */       return ValidationResult.error("Buffer too small: expected at least 21 bytes");
/*     */     }
/*     */     
/* 104 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 106 */     int pos = offset + 21;
/*     */     
/* 108 */     if ((nullBits & 0x1) != 0) {
/* 109 */       int materialNameLen = VarInt.peek(buffer, pos);
/* 110 */       if (materialNameLen < 0) {
/* 111 */         return ValidationResult.error("Invalid string length for MaterialName");
/*     */       }
/* 113 */       if (materialNameLen > 4096000) {
/* 114 */         return ValidationResult.error("MaterialName exceeds max length 4096000");
/*     */       }
/* 116 */       pos += VarInt.length(buffer, pos);
/* 117 */       pos += materialNameLen;
/* 118 */       if (pos > buffer.writerIndex()) {
/* 119 */         return ValidationResult.error("Buffer overflow reading MaterialName");
/*     */       }
/*     */     } 
/* 122 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public StairConnectedBlockRuleSet clone() {
/* 126 */     StairConnectedBlockRuleSet copy = new StairConnectedBlockRuleSet();
/* 127 */     copy.straightBlockId = this.straightBlockId;
/* 128 */     copy.cornerLeftBlockId = this.cornerLeftBlockId;
/* 129 */     copy.cornerRightBlockId = this.cornerRightBlockId;
/* 130 */     copy.invertedCornerLeftBlockId = this.invertedCornerLeftBlockId;
/* 131 */     copy.invertedCornerRightBlockId = this.invertedCornerRightBlockId;
/* 132 */     copy.materialName = this.materialName;
/* 133 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     StairConnectedBlockRuleSet other;
/* 139 */     if (this == obj) return true; 
/* 140 */     if (obj instanceof StairConnectedBlockRuleSet) { other = (StairConnectedBlockRuleSet)obj; } else { return false; }
/* 141 */      return (this.straightBlockId == other.straightBlockId && this.cornerLeftBlockId == other.cornerLeftBlockId && this.cornerRightBlockId == other.cornerRightBlockId && this.invertedCornerLeftBlockId == other.invertedCornerLeftBlockId && this.invertedCornerRightBlockId == other.invertedCornerRightBlockId && Objects.equals(this.materialName, other.materialName));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 146 */     return Objects.hash(new Object[] { Integer.valueOf(this.straightBlockId), Integer.valueOf(this.cornerLeftBlockId), Integer.valueOf(this.cornerRightBlockId), Integer.valueOf(this.invertedCornerLeftBlockId), Integer.valueOf(this.invertedCornerRightBlockId), this.materialName });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\StairConnectedBlockRuleSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */