/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class InteractionPriority
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 20480006;
/*     */   @Nullable
/*     */   public Map<PrioritySlot, Integer> values;
/*     */   
/*     */   public InteractionPriority() {}
/*     */   
/*     */   public InteractionPriority(@Nullable Map<PrioritySlot, Integer> values) {
/*  26 */     this.values = values;
/*     */   }
/*     */   
/*     */   public InteractionPriority(@Nonnull InteractionPriority other) {
/*  30 */     this.values = other.values;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static InteractionPriority deserialize(@Nonnull ByteBuf buf, int offset) {
/*  35 */     InteractionPriority obj = new InteractionPriority();
/*  36 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  38 */     int pos = offset + 1;
/*  39 */     if ((nullBits & 0x1) != 0) { int valuesCount = VarInt.peek(buf, pos);
/*  40 */       if (valuesCount < 0) throw ProtocolException.negativeLength("Values", valuesCount); 
/*  41 */       if (valuesCount > 4096000) throw ProtocolException.dictionaryTooLarge("Values", valuesCount, 4096000); 
/*  42 */       pos += VarInt.size(valuesCount);
/*  43 */       obj.values = new HashMap<>(valuesCount);
/*  44 */       for (int i = 0; i < valuesCount; i++) {
/*  45 */         PrioritySlot key = PrioritySlot.fromValue(buf.getByte(pos)); pos++;
/*  46 */         int val = buf.getIntLE(pos); pos += 4;
/*  47 */         if (obj.values.put(key, Integer.valueOf(val)) != null)
/*  48 */           throw ProtocolException.duplicateKey("values", key); 
/*     */       }  }
/*     */     
/*  51 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  55 */     byte nullBits = buf.getByte(offset);
/*  56 */     int pos = offset + 1;
/*  57 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  58 */       for (int i = 0; i < dictLen; ) { pos++; pos += 4; i++; }  }
/*  59 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  64 */     byte nullBits = 0;
/*  65 */     if (this.values != null) nullBits = (byte)(nullBits | 0x1); 
/*  66 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  69 */     if (this.values != null) { if (this.values.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Values", this.values.size(), 4096000);  VarInt.write(buf, this.values.size()); for (Map.Entry<PrioritySlot, Integer> e : this.values.entrySet()) { buf.writeByte(((PrioritySlot)e.getKey()).getValue()); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/*  74 */     int size = 1;
/*  75 */     if (this.values != null) size += VarInt.size(this.values.size()) + this.values.size() * 5;
/*     */     
/*  77 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  81 */     if (buffer.readableBytes() - offset < 1) {
/*  82 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  85 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  87 */     int pos = offset + 1;
/*     */     
/*  89 */     if ((nullBits & 0x1) != 0) {
/*  90 */       int valuesCount = VarInt.peek(buffer, pos);
/*  91 */       if (valuesCount < 0) {
/*  92 */         return ValidationResult.error("Invalid dictionary count for Values");
/*     */       }
/*  94 */       if (valuesCount > 4096000) {
/*  95 */         return ValidationResult.error("Values exceeds max length 4096000");
/*     */       }
/*  97 */       pos += VarInt.length(buffer, pos);
/*  98 */       for (int i = 0; i < valuesCount; i++) {
/*  99 */         pos++;
/*     */         
/* 101 */         pos += 4;
/* 102 */         if (pos > buffer.writerIndex()) {
/* 103 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/* 107 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public InteractionPriority clone() {
/* 111 */     InteractionPriority copy = new InteractionPriority();
/* 112 */     copy.values = (this.values != null) ? new HashMap<>(this.values) : null;
/* 113 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     InteractionPriority other;
/* 119 */     if (this == obj) return true; 
/* 120 */     if (obj instanceof InteractionPriority) { other = (InteractionPriority)obj; } else { return false; }
/* 121 */      return Objects.equals(this.values, other.values);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 126 */     return Objects.hash(new Object[] { this.values });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\InteractionPriority.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */