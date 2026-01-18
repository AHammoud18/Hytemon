/*     */ package com.hypixel.hytale.protocol.packets.setup;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
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
/*     */ 
/*     */ public class ServerTags
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 34;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   
/*     */   public int getId() {
/*  25 */     return 34;
/*     */   }
/*     */   public static final int VARIABLE_FIELD_COUNT = 1; public static final int VARIABLE_BLOCK_START = 1; public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public Map<String, Integer> tags;
/*     */   
/*     */   public ServerTags() {}
/*     */   
/*     */   public ServerTags(@Nullable Map<String, Integer> tags) {
/*  34 */     this.tags = tags;
/*     */   }
/*     */   
/*     */   public ServerTags(@Nonnull ServerTags other) {
/*  38 */     this.tags = other.tags;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ServerTags deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     ServerTags obj = new ServerTags();
/*  44 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  46 */     int pos = offset + 1;
/*  47 */     if ((nullBits & 0x1) != 0) { int tagsCount = VarInt.peek(buf, pos);
/*  48 */       if (tagsCount < 0) throw ProtocolException.negativeLength("Tags", tagsCount); 
/*  49 */       if (tagsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Tags", tagsCount, 4096000); 
/*  50 */       pos += VarInt.size(tagsCount);
/*  51 */       obj.tags = new HashMap<>(tagsCount);
/*  52 */       for (int i = 0; i < tagsCount; i++) {
/*  53 */         int keyLen = VarInt.peek(buf, pos);
/*  54 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/*  55 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/*  56 */         int keyVarLen = VarInt.length(buf, pos);
/*  57 */         String key = PacketIO.readVarString(buf, pos);
/*  58 */         pos += keyVarLen + keyLen;
/*  59 */         int val = buf.getIntLE(pos); pos += 4;
/*  60 */         if (obj.tags.put(key, Integer.valueOf(val)) != null)
/*  61 */           throw ProtocolException.duplicateKey("tags", key); 
/*     */       }  }
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     byte nullBits = buf.getByte(offset);
/*  69 */     int pos = offset + 1;
/*  70 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  71 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; pos += 4; i++; }  }
/*  72 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  78 */     byte nullBits = 0;
/*  79 */     if (this.tags != null) nullBits = (byte)(nullBits | 0x1); 
/*  80 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  83 */     if (this.tags != null) { if (this.tags.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Tags", this.tags.size(), 4096000);  VarInt.write(buf, this.tags.size()); for (Map.Entry<String, Integer> e : this.tags.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/*  88 */     int size = 1;
/*  89 */     if (this.tags != null) {
/*  90 */       int tagsSize = 0;
/*  91 */       for (Map.Entry<String, Integer> kvp : this.tags.entrySet()) tagsSize += PacketIO.stringSize(kvp.getKey()) + 4; 
/*  92 */       size += VarInt.size(this.tags.size()) + tagsSize;
/*     */     } 
/*     */     
/*  95 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  99 */     if (buffer.readableBytes() - offset < 1) {
/* 100 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/* 103 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 105 */     int pos = offset + 1;
/*     */     
/* 107 */     if ((nullBits & 0x1) != 0) {
/* 108 */       int tagsCount = VarInt.peek(buffer, pos);
/* 109 */       if (tagsCount < 0) {
/* 110 */         return ValidationResult.error("Invalid dictionary count for Tags");
/*     */       }
/* 112 */       if (tagsCount > 4096000) {
/* 113 */         return ValidationResult.error("Tags exceeds max length 4096000");
/*     */       }
/* 115 */       pos += VarInt.length(buffer, pos);
/* 116 */       for (int i = 0; i < tagsCount; i++) {
/* 117 */         int keyLen = VarInt.peek(buffer, pos);
/* 118 */         if (keyLen < 0) {
/* 119 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 121 */         if (keyLen > 4096000) {
/* 122 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 124 */         pos += VarInt.length(buffer, pos);
/* 125 */         pos += keyLen;
/* 126 */         if (pos > buffer.writerIndex()) {
/* 127 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 129 */         pos += 4;
/* 130 */         if (pos > buffer.writerIndex()) {
/* 131 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/* 135 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ServerTags clone() {
/* 139 */     ServerTags copy = new ServerTags();
/* 140 */     copy.tags = (this.tags != null) ? new HashMap<>(this.tags) : null;
/* 141 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ServerTags other;
/* 147 */     if (this == obj) return true; 
/* 148 */     if (obj instanceof ServerTags) { other = (ServerTags)obj; } else { return false; }
/* 149 */      return Objects.equals(this.tags, other.tags);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 154 */     return Objects.hash(new Object[] { this.tags });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\setup\ServerTags.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */