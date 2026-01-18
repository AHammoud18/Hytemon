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
/*     */ public class ItemTool
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 5;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public ItemToolSpec[] specs;
/*     */   public float speed;
/*     */   
/*     */   public ItemTool() {}
/*     */   
/*     */   public ItemTool(@Nullable ItemToolSpec[] specs, float speed) {
/*  27 */     this.specs = specs;
/*  28 */     this.speed = speed;
/*     */   }
/*     */   
/*     */   public ItemTool(@Nonnull ItemTool other) {
/*  32 */     this.specs = other.specs;
/*  33 */     this.speed = other.speed;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ItemTool deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     ItemTool obj = new ItemTool();
/*  39 */     byte nullBits = buf.getByte(offset);
/*  40 */     obj.speed = buf.getFloatLE(offset + 1);
/*     */     
/*  42 */     int pos = offset + 5;
/*  43 */     if ((nullBits & 0x1) != 0) { int specsCount = VarInt.peek(buf, pos);
/*  44 */       if (specsCount < 0) throw ProtocolException.negativeLength("Specs", specsCount); 
/*  45 */       if (specsCount > 4096000) throw ProtocolException.arrayTooLong("Specs", specsCount, 4096000); 
/*  46 */       int specsVarLen = VarInt.size(specsCount);
/*  47 */       if ((pos + specsVarLen) + specsCount * 9L > buf.readableBytes())
/*  48 */         throw ProtocolException.bufferTooSmall("Specs", pos + specsVarLen + specsCount * 9, buf.readableBytes()); 
/*  49 */       pos += specsVarLen;
/*  50 */       obj.specs = new ItemToolSpec[specsCount];
/*  51 */       for (int i = 0; i < specsCount; i++) {
/*  52 */         obj.specs[i] = ItemToolSpec.deserialize(buf, pos);
/*  53 */         pos += ItemToolSpec.computeBytesConsumed(buf, pos);
/*     */       }  }
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     byte nullBits = buf.getByte(offset);
/*  61 */     int pos = offset + 5;
/*  62 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  63 */       for (int i = 0; i < arrLen; ) { pos += ItemToolSpec.computeBytesConsumed(buf, pos); i++; }  }
/*  64 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  69 */     byte nullBits = 0;
/*  70 */     if (this.specs != null) nullBits = (byte)(nullBits | 0x1); 
/*  71 */     buf.writeByte(nullBits);
/*     */     
/*  73 */     buf.writeFloatLE(this.speed);
/*     */     
/*  75 */     if (this.specs != null) { if (this.specs.length > 4096000) throw ProtocolException.arrayTooLong("Specs", this.specs.length, 4096000);  VarInt.write(buf, this.specs.length); for (ItemToolSpec item : this.specs) item.serialize(buf);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  80 */     int size = 5;
/*  81 */     if (this.specs != null) {
/*  82 */       int specsSize = 0;
/*  83 */       for (ItemToolSpec elem : this.specs) specsSize += elem.computeSize(); 
/*  84 */       size += VarInt.size(this.specs.length) + specsSize;
/*     */     } 
/*     */     
/*  87 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  91 */     if (buffer.readableBytes() - offset < 5) {
/*  92 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*     */     }
/*     */     
/*  95 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  97 */     int pos = offset + 5;
/*     */     
/*  99 */     if ((nullBits & 0x1) != 0) {
/* 100 */       int specsCount = VarInt.peek(buffer, pos);
/* 101 */       if (specsCount < 0) {
/* 102 */         return ValidationResult.error("Invalid array count for Specs");
/*     */       }
/* 104 */       if (specsCount > 4096000) {
/* 105 */         return ValidationResult.error("Specs exceeds max length 4096000");
/*     */       }
/* 107 */       pos += VarInt.length(buffer, pos);
/* 108 */       for (int i = 0; i < specsCount; i++) {
/* 109 */         ValidationResult structResult = ItemToolSpec.validateStructure(buffer, pos);
/* 110 */         if (!structResult.isValid()) {
/* 111 */           return ValidationResult.error("Invalid ItemToolSpec in Specs[" + i + "]: " + structResult.error());
/*     */         }
/* 113 */         pos += ItemToolSpec.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 116 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemTool clone() {
/* 120 */     ItemTool copy = new ItemTool();
/* 121 */     copy.specs = (this.specs != null) ? (ItemToolSpec[])Arrays.<ItemToolSpec>stream(this.specs).map(e -> e.clone()).toArray(x$0 -> new ItemToolSpec[x$0]) : null;
/* 122 */     copy.speed = this.speed;
/* 123 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemTool other;
/* 129 */     if (this == obj) return true; 
/* 130 */     if (obj instanceof ItemTool) { other = (ItemTool)obj; } else { return false; }
/* 131 */      return (Arrays.equals((Object[])this.specs, (Object[])other.specs) && this.speed == other.speed);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 136 */     int result = 1;
/* 137 */     result = 31 * result + Arrays.hashCode((Object[])this.specs);
/* 138 */     result = 31 * result + Float.hashCode(this.speed);
/* 139 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemTool.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */