/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.UpdateType;
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
/*     */ public class UpdateTranslations implements Packet {
/*     */   public static final int PACKET_ID = 64;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 2;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  26 */     return 64;
/*     */   }
/*     */   @Nonnull
/*  29 */   public UpdateType type = UpdateType.Init;
/*     */   
/*     */   @Nullable
/*     */   public Map<String, String> translations;
/*     */ 
/*     */   
/*     */   public UpdateTranslations(@Nonnull UpdateType type, @Nullable Map<String, String> translations) {
/*  36 */     this.type = type;
/*  37 */     this.translations = translations;
/*     */   }
/*     */   
/*     */   public UpdateTranslations(@Nonnull UpdateTranslations other) {
/*  41 */     this.type = other.type;
/*  42 */     this.translations = other.translations;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateTranslations deserialize(@Nonnull ByteBuf buf, int offset) {
/*  47 */     UpdateTranslations obj = new UpdateTranslations();
/*  48 */     byte nullBits = buf.getByte(offset);
/*  49 */     obj.type = UpdateType.fromValue(buf.getByte(offset + 1));
/*     */     
/*  51 */     int pos = offset + 2;
/*  52 */     if ((nullBits & 0x1) != 0) { int translationsCount = VarInt.peek(buf, pos);
/*  53 */       if (translationsCount < 0) throw ProtocolException.negativeLength("Translations", translationsCount); 
/*  54 */       if (translationsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Translations", translationsCount, 4096000); 
/*  55 */       pos += VarInt.size(translationsCount);
/*  56 */       obj.translations = new HashMap<>(translationsCount);
/*  57 */       for (int i = 0; i < translationsCount; i++) {
/*  58 */         int keyLen = VarInt.peek(buf, pos);
/*  59 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/*  60 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/*  61 */         int keyVarLen = VarInt.length(buf, pos);
/*  62 */         String key = PacketIO.readVarString(buf, pos);
/*  63 */         pos += keyVarLen + keyLen;
/*  64 */         int valLen = VarInt.peek(buf, pos);
/*  65 */         if (valLen < 0) throw ProtocolException.negativeLength("val", valLen); 
/*  66 */         if (valLen > 4096000) throw ProtocolException.stringTooLong("val", valLen, 4096000); 
/*  67 */         int valVarLen = VarInt.length(buf, pos);
/*  68 */         String val = PacketIO.readVarString(buf, pos);
/*  69 */         pos += valVarLen + valLen;
/*  70 */         if (obj.translations.put(key, val) != null)
/*  71 */           throw ProtocolException.duplicateKey("translations", key); 
/*     */       }  }
/*     */     
/*  74 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  78 */     byte nullBits = buf.getByte(offset);
/*  79 */     int pos = offset + 2;
/*  80 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  81 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; i++; }  }
/*  82 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  88 */     byte nullBits = 0;
/*  89 */     if (this.translations != null) nullBits = (byte)(nullBits | 0x1); 
/*  90 */     buf.writeByte(nullBits);
/*     */     
/*  92 */     buf.writeByte(this.type.getValue());
/*     */     
/*  94 */     if (this.translations != null) { if (this.translations.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Translations", this.translations.size(), 4096000);  VarInt.write(buf, this.translations.size()); for (Map.Entry<String, String> e : this.translations.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); PacketIO.writeVarString(buf, e.getValue(), 4096000); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/*  99 */     int size = 2;
/* 100 */     if (this.translations != null) {
/* 101 */       int translationsSize = 0;
/* 102 */       for (Map.Entry<String, String> kvp : this.translations.entrySet()) translationsSize += PacketIO.stringSize(kvp.getKey()) + PacketIO.stringSize(kvp.getValue()); 
/* 103 */       size += VarInt.size(this.translations.size()) + translationsSize;
/*     */     } 
/*     */     
/* 106 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 110 */     if (buffer.readableBytes() - offset < 2) {
/* 111 */       return ValidationResult.error("Buffer too small: expected at least 2 bytes");
/*     */     }
/*     */     
/* 114 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 116 */     int pos = offset + 2;
/*     */     
/* 118 */     if ((nullBits & 0x1) != 0) {
/* 119 */       int translationsCount = VarInt.peek(buffer, pos);
/* 120 */       if (translationsCount < 0) {
/* 121 */         return ValidationResult.error("Invalid dictionary count for Translations");
/*     */       }
/* 123 */       if (translationsCount > 4096000) {
/* 124 */         return ValidationResult.error("Translations exceeds max length 4096000");
/*     */       }
/* 126 */       pos += VarInt.length(buffer, pos);
/* 127 */       for (int i = 0; i < translationsCount; i++) {
/* 128 */         int keyLen = VarInt.peek(buffer, pos);
/* 129 */         if (keyLen < 0) {
/* 130 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 132 */         if (keyLen > 4096000) {
/* 133 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 135 */         pos += VarInt.length(buffer, pos);
/* 136 */         pos += keyLen;
/* 137 */         if (pos > buffer.writerIndex()) {
/* 138 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 140 */         int valueLen = VarInt.peek(buffer, pos);
/* 141 */         if (valueLen < 0) {
/* 142 */           return ValidationResult.error("Invalid string length for value");
/*     */         }
/* 144 */         if (valueLen > 4096000) {
/* 145 */           return ValidationResult.error("value exceeds max length 4096000");
/*     */         }
/* 147 */         pos += VarInt.length(buffer, pos);
/* 148 */         pos += valueLen;
/* 149 */         if (pos > buffer.writerIndex()) {
/* 150 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/* 154 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateTranslations clone() {
/* 158 */     UpdateTranslations copy = new UpdateTranslations();
/* 159 */     copy.type = this.type;
/* 160 */     copy.translations = (this.translations != null) ? new HashMap<>(this.translations) : null;
/* 161 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateTranslations other;
/* 167 */     if (this == obj) return true; 
/* 168 */     if (obj instanceof UpdateTranslations) { other = (UpdateTranslations)obj; } else { return false; }
/* 169 */      return (Objects.equals(this.type, other.type) && Objects.equals(this.translations, other.translations));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 174 */     return Objects.hash(new Object[] { this.type, this.translations });
/*     */   }
/*     */   
/*     */   public UpdateTranslations() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UpdateTranslations.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */