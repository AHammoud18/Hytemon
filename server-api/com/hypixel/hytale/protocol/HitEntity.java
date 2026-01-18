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
/*     */ public class HitEntity
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 5;
/*     */   public static final int MAX_SIZE = 8192010;
/*     */   public int next;
/*     */   @Nullable
/*     */   public EntityMatcher[] matchers;
/*     */   
/*     */   public HitEntity() {}
/*     */   
/*     */   public HitEntity(int next, @Nullable EntityMatcher[] matchers) {
/*  27 */     this.next = next;
/*  28 */     this.matchers = matchers;
/*     */   }
/*     */   
/*     */   public HitEntity(@Nonnull HitEntity other) {
/*  32 */     this.next = other.next;
/*  33 */     this.matchers = other.matchers;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static HitEntity deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     HitEntity obj = new HitEntity();
/*  39 */     byte nullBits = buf.getByte(offset);
/*  40 */     obj.next = buf.getIntLE(offset + 1);
/*     */     
/*  42 */     int pos = offset + 5;
/*  43 */     if ((nullBits & 0x1) != 0) { int matchersCount = VarInt.peek(buf, pos);
/*  44 */       if (matchersCount < 0) throw ProtocolException.negativeLength("Matchers", matchersCount); 
/*  45 */       if (matchersCount > 4096000) throw ProtocolException.arrayTooLong("Matchers", matchersCount, 4096000); 
/*  46 */       int matchersVarLen = VarInt.size(matchersCount);
/*  47 */       if ((pos + matchersVarLen) + matchersCount * 2L > buf.readableBytes())
/*  48 */         throw ProtocolException.bufferTooSmall("Matchers", pos + matchersVarLen + matchersCount * 2, buf.readableBytes()); 
/*  49 */       pos += matchersVarLen;
/*  50 */       obj.matchers = new EntityMatcher[matchersCount];
/*  51 */       for (int i = 0; i < matchersCount; i++) {
/*  52 */         obj.matchers[i] = EntityMatcher.deserialize(buf, pos);
/*  53 */         pos += EntityMatcher.computeBytesConsumed(buf, pos);
/*     */       }  }
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     byte nullBits = buf.getByte(offset);
/*  61 */     int pos = offset + 5;
/*  62 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  63 */       for (int i = 0; i < arrLen; ) { pos += EntityMatcher.computeBytesConsumed(buf, pos); i++; }  }
/*  64 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  69 */     byte nullBits = 0;
/*  70 */     if (this.matchers != null) nullBits = (byte)(nullBits | 0x1); 
/*  71 */     buf.writeByte(nullBits);
/*     */     
/*  73 */     buf.writeIntLE(this.next);
/*     */     
/*  75 */     if (this.matchers != null) { if (this.matchers.length > 4096000) throw ProtocolException.arrayTooLong("Matchers", this.matchers.length, 4096000);  VarInt.write(buf, this.matchers.length); for (EntityMatcher item : this.matchers) item.serialize(buf);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  80 */     int size = 5;
/*  81 */     if (this.matchers != null) size += VarInt.size(this.matchers.length) + this.matchers.length * 2;
/*     */     
/*  83 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  87 */     if (buffer.readableBytes() - offset < 5) {
/*  88 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*     */     }
/*     */     
/*  91 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  93 */     int pos = offset + 5;
/*     */     
/*  95 */     if ((nullBits & 0x1) != 0) {
/*  96 */       int matchersCount = VarInt.peek(buffer, pos);
/*  97 */       if (matchersCount < 0) {
/*  98 */         return ValidationResult.error("Invalid array count for Matchers");
/*     */       }
/* 100 */       if (matchersCount > 4096000) {
/* 101 */         return ValidationResult.error("Matchers exceeds max length 4096000");
/*     */       }
/* 103 */       pos += VarInt.length(buffer, pos);
/* 104 */       pos += matchersCount * 2;
/* 105 */       if (pos > buffer.writerIndex()) {
/* 106 */         return ValidationResult.error("Buffer overflow reading Matchers");
/*     */       }
/*     */     } 
/* 109 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public HitEntity clone() {
/* 113 */     HitEntity copy = new HitEntity();
/* 114 */     copy.next = this.next;
/* 115 */     copy.matchers = (this.matchers != null) ? (EntityMatcher[])Arrays.<EntityMatcher>stream(this.matchers).map(e -> e.clone()).toArray(x$0 -> new EntityMatcher[x$0]) : null;
/* 116 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     HitEntity other;
/* 122 */     if (this == obj) return true; 
/* 123 */     if (obj instanceof HitEntity) { other = (HitEntity)obj; } else { return false; }
/* 124 */      return (this.next == other.next && Arrays.equals((Object[])this.matchers, (Object[])other.matchers));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 129 */     int result = 1;
/* 130 */     result = 31 * result + Integer.hashCode(this.next);
/* 131 */     result = 31 * result + Arrays.hashCode((Object[])this.matchers);
/* 132 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\HitEntity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */