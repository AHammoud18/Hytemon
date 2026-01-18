/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class BlockBreakingDecal
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public String[] stageTextures;
/*     */   
/*     */   public BlockBreakingDecal() {}
/*     */   
/*     */   public BlockBreakingDecal(@Nullable String[] stageTextures) {
/*  26 */     this.stageTextures = stageTextures;
/*     */   }
/*     */   
/*     */   public BlockBreakingDecal(@Nonnull BlockBreakingDecal other) {
/*  30 */     this.stageTextures = other.stageTextures;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BlockBreakingDecal deserialize(@Nonnull ByteBuf buf, int offset) {
/*  35 */     BlockBreakingDecal obj = new BlockBreakingDecal();
/*  36 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  38 */     int pos = offset + 1;
/*  39 */     if ((nullBits & 0x1) != 0) { int stageTexturesCount = VarInt.peek(buf, pos);
/*  40 */       if (stageTexturesCount < 0) throw ProtocolException.negativeLength("StageTextures", stageTexturesCount); 
/*  41 */       if (stageTexturesCount > 4096000) throw ProtocolException.arrayTooLong("StageTextures", stageTexturesCount, 4096000); 
/*  42 */       int stageTexturesVarLen = VarInt.size(stageTexturesCount);
/*  43 */       if ((pos + stageTexturesVarLen) + stageTexturesCount * 1L > buf.readableBytes())
/*  44 */         throw ProtocolException.bufferTooSmall("StageTextures", pos + stageTexturesVarLen + stageTexturesCount * 1, buf.readableBytes()); 
/*  45 */       pos += stageTexturesVarLen;
/*  46 */       obj.stageTextures = new String[stageTexturesCount];
/*  47 */       for (int i = 0; i < stageTexturesCount; i++) {
/*  48 */         int strLen = VarInt.peek(buf, pos);
/*  49 */         if (strLen < 0) throw ProtocolException.negativeLength("stageTextures[" + i + "]", strLen); 
/*  50 */         if (strLen > 4096000) throw ProtocolException.stringTooLong("stageTextures[" + i + "]", strLen, 4096000); 
/*  51 */         int strVarLen = VarInt.length(buf, pos);
/*  52 */         obj.stageTextures[i] = PacketIO.readVarString(buf, pos);
/*  53 */         pos += strVarLen + strLen;
/*     */       }  }
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     byte nullBits = buf.getByte(offset);
/*  61 */     int pos = offset + 1;
/*  62 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  63 */       for (int i = 0; i < arrLen; ) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; i++; }  }
/*  64 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  69 */     byte nullBits = 0;
/*  70 */     if (this.stageTextures != null) nullBits = (byte)(nullBits | 0x1); 
/*  71 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  74 */     if (this.stageTextures != null) { if (this.stageTextures.length > 4096000) throw ProtocolException.arrayTooLong("StageTextures", this.stageTextures.length, 4096000);  VarInt.write(buf, this.stageTextures.length); for (String item : this.stageTextures) PacketIO.writeVarString(buf, item, 4096000);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  79 */     int size = 1;
/*  80 */     if (this.stageTextures != null) {
/*  81 */       int stageTexturesSize = 0;
/*  82 */       for (String elem : this.stageTextures) stageTexturesSize += PacketIO.stringSize(elem); 
/*  83 */       size += VarInt.size(this.stageTextures.length) + stageTexturesSize;
/*     */     } 
/*     */     
/*  86 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  90 */     if (buffer.readableBytes() - offset < 1) {
/*  91 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  94 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  96 */     int pos = offset + 1;
/*     */     
/*  98 */     if ((nullBits & 0x1) != 0) {
/*  99 */       int stageTexturesCount = VarInt.peek(buffer, pos);
/* 100 */       if (stageTexturesCount < 0) {
/* 101 */         return ValidationResult.error("Invalid array count for StageTextures");
/*     */       }
/* 103 */       if (stageTexturesCount > 4096000) {
/* 104 */         return ValidationResult.error("StageTextures exceeds max length 4096000");
/*     */       }
/* 106 */       pos += VarInt.length(buffer, pos);
/* 107 */       for (int i = 0; i < stageTexturesCount; i++) {
/* 108 */         int strLen = VarInt.peek(buffer, pos);
/* 109 */         if (strLen < 0) {
/* 110 */           return ValidationResult.error("Invalid string length in StageTextures");
/*     */         }
/* 112 */         pos += VarInt.length(buffer, pos);
/* 113 */         pos += strLen;
/* 114 */         if (pos > buffer.writerIndex()) {
/* 115 */           return ValidationResult.error("Buffer overflow reading string in StageTextures");
/*     */         }
/*     */       } 
/*     */     } 
/* 119 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BlockBreakingDecal clone() {
/* 123 */     BlockBreakingDecal copy = new BlockBreakingDecal();
/* 124 */     copy.stageTextures = (this.stageTextures != null) ? Arrays.<String>copyOf(this.stageTextures, this.stageTextures.length) : null;
/* 125 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BlockBreakingDecal other;
/* 131 */     if (this == obj) return true; 
/* 132 */     if (obj instanceof BlockBreakingDecal) { other = (BlockBreakingDecal)obj; } else { return false; }
/* 133 */      return Arrays.equals((Object[])this.stageTextures, (Object[])other.stageTextures);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 138 */     int result = 1;
/* 139 */     result = 31 * result + Arrays.hashCode((Object[])this.stageTextures);
/* 140 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BlockBreakingDecal.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */