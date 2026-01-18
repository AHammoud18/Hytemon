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
/*     */ public class ExtraResources
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public ItemQuantity[] resources;
/*     */   
/*     */   public ExtraResources() {}
/*     */   
/*     */   public ExtraResources(@Nullable ItemQuantity[] resources) {
/*  26 */     this.resources = resources;
/*     */   }
/*     */   
/*     */   public ExtraResources(@Nonnull ExtraResources other) {
/*  30 */     this.resources = other.resources;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ExtraResources deserialize(@Nonnull ByteBuf buf, int offset) {
/*  35 */     ExtraResources obj = new ExtraResources();
/*  36 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  38 */     int pos = offset + 1;
/*  39 */     if ((nullBits & 0x1) != 0) { int resourcesCount = VarInt.peek(buf, pos);
/*  40 */       if (resourcesCount < 0) throw ProtocolException.negativeLength("Resources", resourcesCount); 
/*  41 */       if (resourcesCount > 4096000) throw ProtocolException.arrayTooLong("Resources", resourcesCount, 4096000); 
/*  42 */       int resourcesVarLen = VarInt.size(resourcesCount);
/*  43 */       if ((pos + resourcesVarLen) + resourcesCount * 5L > buf.readableBytes())
/*  44 */         throw ProtocolException.bufferTooSmall("Resources", pos + resourcesVarLen + resourcesCount * 5, buf.readableBytes()); 
/*  45 */       pos += resourcesVarLen;
/*  46 */       obj.resources = new ItemQuantity[resourcesCount];
/*  47 */       for (int i = 0; i < resourcesCount; i++) {
/*  48 */         obj.resources[i] = ItemQuantity.deserialize(buf, pos);
/*  49 */         pos += ItemQuantity.computeBytesConsumed(buf, pos);
/*     */       }  }
/*     */     
/*  52 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  56 */     byte nullBits = buf.getByte(offset);
/*  57 */     int pos = offset + 1;
/*  58 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  59 */       for (int i = 0; i < arrLen; ) { pos += ItemQuantity.computeBytesConsumed(buf, pos); i++; }  }
/*  60 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  65 */     byte nullBits = 0;
/*  66 */     if (this.resources != null) nullBits = (byte)(nullBits | 0x1); 
/*  67 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  70 */     if (this.resources != null) { if (this.resources.length > 4096000) throw ProtocolException.arrayTooLong("Resources", this.resources.length, 4096000);  VarInt.write(buf, this.resources.length); for (ItemQuantity item : this.resources) item.serialize(buf);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  75 */     int size = 1;
/*  76 */     if (this.resources != null) {
/*  77 */       int resourcesSize = 0;
/*  78 */       for (ItemQuantity elem : this.resources) resourcesSize += elem.computeSize(); 
/*  79 */       size += VarInt.size(this.resources.length) + resourcesSize;
/*     */     } 
/*     */     
/*  82 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  86 */     if (buffer.readableBytes() - offset < 1) {
/*  87 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  90 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  92 */     int pos = offset + 1;
/*     */     
/*  94 */     if ((nullBits & 0x1) != 0) {
/*  95 */       int resourcesCount = VarInt.peek(buffer, pos);
/*  96 */       if (resourcesCount < 0) {
/*  97 */         return ValidationResult.error("Invalid array count for Resources");
/*     */       }
/*  99 */       if (resourcesCount > 4096000) {
/* 100 */         return ValidationResult.error("Resources exceeds max length 4096000");
/*     */       }
/* 102 */       pos += VarInt.length(buffer, pos);
/* 103 */       for (int i = 0; i < resourcesCount; i++) {
/* 104 */         ValidationResult structResult = ItemQuantity.validateStructure(buffer, pos);
/* 105 */         if (!structResult.isValid()) {
/* 106 */           return ValidationResult.error("Invalid ItemQuantity in Resources[" + i + "]: " + structResult.error());
/*     */         }
/* 108 */         pos += ItemQuantity.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 111 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ExtraResources clone() {
/* 115 */     ExtraResources copy = new ExtraResources();
/* 116 */     copy.resources = (this.resources != null) ? (ItemQuantity[])Arrays.<ItemQuantity>stream(this.resources).map(e -> e.clone()).toArray(x$0 -> new ItemQuantity[x$0]) : null;
/* 117 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ExtraResources other;
/* 123 */     if (this == obj) return true; 
/* 124 */     if (obj instanceof ExtraResources) { other = (ExtraResources)obj; } else { return false; }
/* 125 */      return Arrays.equals((Object[])this.resources, (Object[])other.resources);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 130 */     int result = 1;
/* 131 */     result = 31 * result + Arrays.hashCode((Object[])this.resources);
/* 132 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ExtraResources.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */