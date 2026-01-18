/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class CameraAxis
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 4096014;
/*     */   @Nullable
/*     */   public Rangef angleRange;
/*     */   @Nullable
/*     */   public CameraNode[] targetNodes;
/*     */   
/*     */   public CameraAxis() {}
/*     */   
/*     */   public CameraAxis(@Nullable Rangef angleRange, @Nullable CameraNode[] targetNodes) {
/*  27 */     this.angleRange = angleRange;
/*  28 */     this.targetNodes = targetNodes;
/*     */   }
/*     */   
/*     */   public CameraAxis(@Nonnull CameraAxis other) {
/*  32 */     this.angleRange = other.angleRange;
/*  33 */     this.targetNodes = other.targetNodes;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CameraAxis deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     CameraAxis obj = new CameraAxis();
/*  39 */     byte nullBits = buf.getByte(offset);
/*  40 */     if ((nullBits & 0x1) != 0) obj.angleRange = Rangef.deserialize(buf, offset + 1);
/*     */     
/*  42 */     int pos = offset + 9;
/*  43 */     if ((nullBits & 0x2) != 0) { int targetNodesCount = VarInt.peek(buf, pos);
/*  44 */       if (targetNodesCount < 0) throw ProtocolException.negativeLength("TargetNodes", targetNodesCount); 
/*  45 */       if (targetNodesCount > 4096000) throw ProtocolException.arrayTooLong("TargetNodes", targetNodesCount, 4096000); 
/*  46 */       int targetNodesVarLen = VarInt.size(targetNodesCount);
/*  47 */       if ((pos + targetNodesVarLen) + targetNodesCount * 1L > buf.readableBytes())
/*  48 */         throw ProtocolException.bufferTooSmall("TargetNodes", pos + targetNodesVarLen + targetNodesCount * 1, buf.readableBytes()); 
/*  49 */       pos += targetNodesVarLen;
/*  50 */       obj.targetNodes = new CameraNode[targetNodesCount];
/*  51 */       for (int i = 0; i < targetNodesCount; i++) {
/*  52 */         obj.targetNodes[i] = CameraNode.fromValue(buf.getByte(pos)); pos++;
/*     */       }  }
/*     */     
/*  55 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  59 */     byte nullBits = buf.getByte(offset);
/*  60 */     int pos = offset + 9;
/*  61 */     if ((nullBits & 0x2) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + arrLen * 1; }
/*  62 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  67 */     byte nullBits = 0;
/*  68 */     if (this.angleRange != null) nullBits = (byte)(nullBits | 0x1); 
/*  69 */     if (this.targetNodes != null) nullBits = (byte)(nullBits | 0x2); 
/*  70 */     buf.writeByte(nullBits);
/*     */     
/*  72 */     if (this.angleRange != null) { this.angleRange.serialize(buf); } else { buf.writeZero(8); }
/*     */     
/*  74 */     if (this.targetNodes != null) { if (this.targetNodes.length > 4096000) throw ProtocolException.arrayTooLong("TargetNodes", this.targetNodes.length, 4096000);  VarInt.write(buf, this.targetNodes.length); for (CameraNode item : this.targetNodes) buf.writeByte(item.getValue());  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  79 */     int size = 9;
/*  80 */     if (this.targetNodes != null) size += VarInt.size(this.targetNodes.length) + this.targetNodes.length * 1;
/*     */     
/*  82 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  86 */     if (buffer.readableBytes() - offset < 9) {
/*  87 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/*  90 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  92 */     int pos = offset + 9;
/*     */     
/*  94 */     if ((nullBits & 0x2) != 0) {
/*  95 */       int targetNodesCount = VarInt.peek(buffer, pos);
/*  96 */       if (targetNodesCount < 0) {
/*  97 */         return ValidationResult.error("Invalid array count for TargetNodes");
/*     */       }
/*  99 */       if (targetNodesCount > 4096000) {
/* 100 */         return ValidationResult.error("TargetNodes exceeds max length 4096000");
/*     */       }
/* 102 */       pos += VarInt.length(buffer, pos);
/* 103 */       pos += targetNodesCount * 1;
/* 104 */       if (pos > buffer.writerIndex()) {
/* 105 */         return ValidationResult.error("Buffer overflow reading TargetNodes");
/*     */       }
/*     */     } 
/* 108 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public CameraAxis clone() {
/* 112 */     CameraAxis copy = new CameraAxis();
/* 113 */     copy.angleRange = (this.angleRange != null) ? this.angleRange.clone() : null;
/* 114 */     copy.targetNodes = (this.targetNodes != null) ? Arrays.<CameraNode>copyOf(this.targetNodes, this.targetNodes.length) : null;
/* 115 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     CameraAxis other;
/* 121 */     if (this == obj) return true; 
/* 122 */     if (obj instanceof CameraAxis) { other = (CameraAxis)obj; } else { return false; }
/* 123 */      return (Objects.equals(this.angleRange, other.angleRange) && Arrays.equals((Object[])this.targetNodes, (Object[])other.targetNodes));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 128 */     int result = 1;
/* 129 */     result = 31 * result + Objects.hashCode(this.angleRange);
/* 130 */     result = 31 * result + Arrays.hashCode((Object[])this.targetNodes);
/* 131 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\CameraAxis.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */