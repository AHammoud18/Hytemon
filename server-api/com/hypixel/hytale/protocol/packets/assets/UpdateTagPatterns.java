/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.TagPattern;
/*     */ import com.hypixel.hytale.protocol.UpdateType;
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
/*     */ public class UpdateTagPatterns
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 84;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 6;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  27 */     return 84;
/*     */   }
/*     */   @Nonnull
/*  30 */   public UpdateType type = UpdateType.Init;
/*     */   
/*     */   public int maxId;
/*     */   
/*     */   @Nullable
/*     */   public Map<Integer, TagPattern> patterns;
/*     */   
/*     */   public UpdateTagPatterns(@Nonnull UpdateType type, int maxId, @Nullable Map<Integer, TagPattern> patterns) {
/*  38 */     this.type = type;
/*  39 */     this.maxId = maxId;
/*  40 */     this.patterns = patterns;
/*     */   }
/*     */   
/*     */   public UpdateTagPatterns(@Nonnull UpdateTagPatterns other) {
/*  44 */     this.type = other.type;
/*  45 */     this.maxId = other.maxId;
/*  46 */     this.patterns = other.patterns;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateTagPatterns deserialize(@Nonnull ByteBuf buf, int offset) {
/*  51 */     UpdateTagPatterns obj = new UpdateTagPatterns();
/*  52 */     byte nullBits = buf.getByte(offset);
/*  53 */     obj.type = UpdateType.fromValue(buf.getByte(offset + 1));
/*  54 */     obj.maxId = buf.getIntLE(offset + 2);
/*     */     
/*  56 */     int pos = offset + 6;
/*  57 */     if ((nullBits & 0x1) != 0) { int patternsCount = VarInt.peek(buf, pos);
/*  58 */       if (patternsCount < 0) throw ProtocolException.negativeLength("Patterns", patternsCount); 
/*  59 */       if (patternsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Patterns", patternsCount, 4096000); 
/*  60 */       pos += VarInt.size(patternsCount);
/*  61 */       obj.patterns = new HashMap<>(patternsCount);
/*  62 */       for (int i = 0; i < patternsCount; i++) {
/*  63 */         int key = buf.getIntLE(pos); pos += 4;
/*  64 */         TagPattern val = TagPattern.deserialize(buf, pos);
/*  65 */         pos += TagPattern.computeBytesConsumed(buf, pos);
/*  66 */         if (obj.patterns.put(Integer.valueOf(key), val) != null)
/*  67 */           throw ProtocolException.duplicateKey("patterns", Integer.valueOf(key)); 
/*     */       }  }
/*     */     
/*  70 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  74 */     byte nullBits = buf.getByte(offset);
/*  75 */     int pos = offset + 6;
/*  76 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  77 */       for (int i = 0; i < dictLen; ) { pos += 4; pos += TagPattern.computeBytesConsumed(buf, pos); i++; }  }
/*  78 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  84 */     byte nullBits = 0;
/*  85 */     if (this.patterns != null) nullBits = (byte)(nullBits | 0x1); 
/*  86 */     buf.writeByte(nullBits);
/*     */     
/*  88 */     buf.writeByte(this.type.getValue());
/*  89 */     buf.writeIntLE(this.maxId);
/*     */     
/*  91 */     if (this.patterns != null) { if (this.patterns.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Patterns", this.patterns.size(), 4096000);  VarInt.write(buf, this.patterns.size()); for (Map.Entry<Integer, TagPattern> e : this.patterns.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); ((TagPattern)e.getValue()).serialize(buf); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/*  96 */     int size = 6;
/*  97 */     if (this.patterns != null) {
/*  98 */       int patternsSize = 0;
/*  99 */       for (Map.Entry<Integer, TagPattern> kvp : this.patterns.entrySet()) patternsSize += 4 + ((TagPattern)kvp.getValue()).computeSize(); 
/* 100 */       size += VarInt.size(this.patterns.size()) + patternsSize;
/*     */     } 
/*     */     
/* 103 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 107 */     if (buffer.readableBytes() - offset < 6) {
/* 108 */       return ValidationResult.error("Buffer too small: expected at least 6 bytes");
/*     */     }
/*     */     
/* 111 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 113 */     int pos = offset + 6;
/*     */     
/* 115 */     if ((nullBits & 0x1) != 0) {
/* 116 */       int patternsCount = VarInt.peek(buffer, pos);
/* 117 */       if (patternsCount < 0) {
/* 118 */         return ValidationResult.error("Invalid dictionary count for Patterns");
/*     */       }
/* 120 */       if (patternsCount > 4096000) {
/* 121 */         return ValidationResult.error("Patterns exceeds max length 4096000");
/*     */       }
/* 123 */       pos += VarInt.length(buffer, pos);
/* 124 */       for (int i = 0; i < patternsCount; i++) {
/* 125 */         pos += 4;
/* 126 */         if (pos > buffer.writerIndex()) {
/* 127 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 129 */         pos += TagPattern.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 133 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateTagPatterns clone() {
/* 137 */     UpdateTagPatterns copy = new UpdateTagPatterns();
/* 138 */     copy.type = this.type;
/* 139 */     copy.maxId = this.maxId;
/* 140 */     if (this.patterns != null) {
/* 141 */       Map<Integer, TagPattern> m = new HashMap<>();
/* 142 */       for (Map.Entry<Integer, TagPattern> e : this.patterns.entrySet()) m.put(e.getKey(), ((TagPattern)e.getValue()).clone()); 
/* 143 */       copy.patterns = m;
/*     */     } 
/* 145 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateTagPatterns other;
/* 151 */     if (this == obj) return true; 
/* 152 */     if (obj instanceof UpdateTagPatterns) { other = (UpdateTagPatterns)obj; } else { return false; }
/* 153 */      return (Objects.equals(this.type, other.type) && this.maxId == other.maxId && Objects.equals(this.patterns, other.patterns));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 158 */     return Objects.hash(new Object[] { this.type, Integer.valueOf(this.maxId), this.patterns });
/*     */   }
/*     */   
/*     */   public UpdateTagPatterns() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UpdateTagPatterns.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */