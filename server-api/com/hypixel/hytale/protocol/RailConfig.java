/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RailConfig
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 102400006;
/*     */   @Nullable
/*     */   public RailPoint[] points;
/*     */   
/*     */   public RailConfig() {}
/*     */   
/*     */   public RailConfig(@Nullable RailPoint[] points) {
/*  26 */     this.points = points;
/*     */   }
/*     */   
/*     */   public RailConfig(@Nonnull RailConfig other) {
/*  30 */     this.points = other.points;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static RailConfig deserialize(@Nonnull ByteBuf buf, int offset) {
/*  35 */     RailConfig obj = new RailConfig();
/*  36 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  38 */     int pos = offset + 1;
/*  39 */     if ((nullBits & 0x1) != 0) { int pointsCount = VarInt.peek(buf, pos);
/*  40 */       if (pointsCount < 0) throw ProtocolException.negativeLength("Points", pointsCount); 
/*  41 */       if (pointsCount > 4096000) throw ProtocolException.arrayTooLong("Points", pointsCount, 4096000); 
/*  42 */       int pointsVarLen = VarInt.size(pointsCount);
/*  43 */       if ((pos + pointsVarLen) + pointsCount * 25L > buf.readableBytes())
/*  44 */         throw ProtocolException.bufferTooSmall("Points", pos + pointsVarLen + pointsCount * 25, buf.readableBytes()); 
/*  45 */       pos += pointsVarLen;
/*  46 */       obj.points = new RailPoint[pointsCount];
/*  47 */       for (int i = 0; i < pointsCount; i++) {
/*  48 */         obj.points[i] = RailPoint.deserialize(buf, pos);
/*  49 */         pos += RailPoint.computeBytesConsumed(buf, pos);
/*     */       }  }
/*     */     
/*  52 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  56 */     byte nullBits = buf.getByte(offset);
/*  57 */     int pos = offset + 1;
/*  58 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  59 */       for (int i = 0; i < arrLen; ) { pos += RailPoint.computeBytesConsumed(buf, pos); i++; }  }
/*  60 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  65 */     byte nullBits = 0;
/*  66 */     if (this.points != null) nullBits = (byte)(nullBits | 0x1); 
/*  67 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  70 */     if (this.points != null) { if (this.points.length > 4096000) throw ProtocolException.arrayTooLong("Points", this.points.length, 4096000);  VarInt.write(buf, this.points.length); for (RailPoint item : this.points) item.serialize(buf);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  75 */     int size = 1;
/*  76 */     if (this.points != null) size += VarInt.size(this.points.length) + this.points.length * 25;
/*     */     
/*  78 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  82 */     if (buffer.readableBytes() - offset < 1) {
/*  83 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  86 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  88 */     int pos = offset + 1;
/*     */     
/*  90 */     if ((nullBits & 0x1) != 0) {
/*  91 */       int pointsCount = VarInt.peek(buffer, pos);
/*  92 */       if (pointsCount < 0) {
/*  93 */         return ValidationResult.error("Invalid array count for Points");
/*     */       }
/*  95 */       if (pointsCount > 4096000) {
/*  96 */         return ValidationResult.error("Points exceeds max length 4096000");
/*     */       }
/*  98 */       pos += VarInt.length(buffer, pos);
/*  99 */       pos += pointsCount * 25;
/* 100 */       if (pos > buffer.writerIndex()) {
/* 101 */         return ValidationResult.error("Buffer overflow reading Points");
/*     */       }
/*     */     } 
/* 104 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public RailConfig clone() {
/* 108 */     RailConfig copy = new RailConfig();
/* 109 */     copy.points = (this.points != null) ? (RailPoint[])Arrays.<RailPoint>stream(this.points).map(e -> e.clone()).toArray(x$0 -> new RailPoint[x$0]) : null;
/* 110 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     RailConfig other;
/* 116 */     if (this == obj) return true; 
/* 117 */     if (obj instanceof RailConfig) { other = (RailConfig)obj; } else { return false; }
/* 118 */      return Arrays.equals((Object[])this.points, (Object[])other.points);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 123 */     int result = 1;
/* 124 */     result = 31 * result + Arrays.hashCode((Object[])this.points);
/* 125 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\RailConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */