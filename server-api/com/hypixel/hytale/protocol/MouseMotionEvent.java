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
/*     */ public class MouseMotionEvent
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 4096014;
/*     */   @Nullable
/*     */   public MouseButtonType[] mouseButtonType;
/*     */   @Nullable
/*     */   public Vector2i relativeMotion;
/*     */   
/*     */   public MouseMotionEvent() {}
/*     */   
/*     */   public MouseMotionEvent(@Nullable MouseButtonType[] mouseButtonType, @Nullable Vector2i relativeMotion) {
/*  27 */     this.mouseButtonType = mouseButtonType;
/*  28 */     this.relativeMotion = relativeMotion;
/*     */   }
/*     */   
/*     */   public MouseMotionEvent(@Nonnull MouseMotionEvent other) {
/*  32 */     this.mouseButtonType = other.mouseButtonType;
/*  33 */     this.relativeMotion = other.relativeMotion;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static MouseMotionEvent deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     MouseMotionEvent obj = new MouseMotionEvent();
/*  39 */     byte nullBits = buf.getByte(offset);
/*  40 */     if ((nullBits & 0x2) != 0) obj.relativeMotion = Vector2i.deserialize(buf, offset + 1);
/*     */     
/*  42 */     int pos = offset + 9;
/*  43 */     if ((nullBits & 0x1) != 0) { int mouseButtonTypeCount = VarInt.peek(buf, pos);
/*  44 */       if (mouseButtonTypeCount < 0) throw ProtocolException.negativeLength("MouseButtonType", mouseButtonTypeCount); 
/*  45 */       if (mouseButtonTypeCount > 4096000) throw ProtocolException.arrayTooLong("MouseButtonType", mouseButtonTypeCount, 4096000); 
/*  46 */       int mouseButtonTypeVarLen = VarInt.size(mouseButtonTypeCount);
/*  47 */       if ((pos + mouseButtonTypeVarLen) + mouseButtonTypeCount * 1L > buf.readableBytes())
/*  48 */         throw ProtocolException.bufferTooSmall("MouseButtonType", pos + mouseButtonTypeVarLen + mouseButtonTypeCount * 1, buf.readableBytes()); 
/*  49 */       pos += mouseButtonTypeVarLen;
/*  50 */       obj.mouseButtonType = new MouseButtonType[mouseButtonTypeCount];
/*  51 */       for (int i = 0; i < mouseButtonTypeCount; i++) {
/*  52 */         obj.mouseButtonType[i] = MouseButtonType.fromValue(buf.getByte(pos)); pos++;
/*     */       }  }
/*     */     
/*  55 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  59 */     byte nullBits = buf.getByte(offset);
/*  60 */     int pos = offset + 9;
/*  61 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + arrLen * 1; }
/*  62 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  67 */     byte nullBits = 0;
/*  68 */     if (this.mouseButtonType != null) nullBits = (byte)(nullBits | 0x1); 
/*  69 */     if (this.relativeMotion != null) nullBits = (byte)(nullBits | 0x2); 
/*  70 */     buf.writeByte(nullBits);
/*     */     
/*  72 */     if (this.relativeMotion != null) { this.relativeMotion.serialize(buf); } else { buf.writeZero(8); }
/*     */     
/*  74 */     if (this.mouseButtonType != null) { if (this.mouseButtonType.length > 4096000) throw ProtocolException.arrayTooLong("MouseButtonType", this.mouseButtonType.length, 4096000);  VarInt.write(buf, this.mouseButtonType.length); for (MouseButtonType item : this.mouseButtonType) buf.writeByte(item.getValue());  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  79 */     int size = 9;
/*  80 */     if (this.mouseButtonType != null) size += VarInt.size(this.mouseButtonType.length) + this.mouseButtonType.length * 1;
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
/*  94 */     if ((nullBits & 0x1) != 0) {
/*  95 */       int mouseButtonTypeCount = VarInt.peek(buffer, pos);
/*  96 */       if (mouseButtonTypeCount < 0) {
/*  97 */         return ValidationResult.error("Invalid array count for MouseButtonType");
/*     */       }
/*  99 */       if (mouseButtonTypeCount > 4096000) {
/* 100 */         return ValidationResult.error("MouseButtonType exceeds max length 4096000");
/*     */       }
/* 102 */       pos += VarInt.length(buffer, pos);
/* 103 */       pos += mouseButtonTypeCount * 1;
/* 104 */       if (pos > buffer.writerIndex()) {
/* 105 */         return ValidationResult.error("Buffer overflow reading MouseButtonType");
/*     */       }
/*     */     } 
/* 108 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public MouseMotionEvent clone() {
/* 112 */     MouseMotionEvent copy = new MouseMotionEvent();
/* 113 */     copy.mouseButtonType = (this.mouseButtonType != null) ? Arrays.<MouseButtonType>copyOf(this.mouseButtonType, this.mouseButtonType.length) : null;
/* 114 */     copy.relativeMotion = (this.relativeMotion != null) ? this.relativeMotion.clone() : null;
/* 115 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     MouseMotionEvent other;
/* 121 */     if (this == obj) return true; 
/* 122 */     if (obj instanceof MouseMotionEvent) { other = (MouseMotionEvent)obj; } else { return false; }
/* 123 */      return (Arrays.equals((Object[])this.mouseButtonType, (Object[])other.mouseButtonType) && Objects.equals(this.relativeMotion, other.relativeMotion));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 128 */     int result = 1;
/* 129 */     result = 31 * result + Arrays.hashCode((Object[])this.mouseButtonType);
/* 130 */     result = 31 * result + Objects.hashCode(this.relativeMotion);
/* 131 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\MouseMotionEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */