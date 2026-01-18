/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ItemTranslationProperties
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 32768019;
/*     */   @Nullable
/*     */   public String name;
/*     */   @Nullable
/*     */   public String description;
/*     */   
/*     */   public ItemTranslationProperties() {}
/*     */   
/*     */   public ItemTranslationProperties(@Nullable String name, @Nullable String description) {
/*  27 */     this.name = name;
/*  28 */     this.description = description;
/*     */   }
/*     */   
/*     */   public ItemTranslationProperties(@Nonnull ItemTranslationProperties other) {
/*  32 */     this.name = other.name;
/*  33 */     this.description = other.description;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ItemTranslationProperties deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     ItemTranslationProperties obj = new ItemTranslationProperties();
/*  39 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  41 */     if ((nullBits & 0x1) != 0) {
/*  42 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  43 */       int nameLen = VarInt.peek(buf, varPos0);
/*  44 */       if (nameLen < 0) throw ProtocolException.negativeLength("Name", nameLen); 
/*  45 */       if (nameLen > 4096000) throw ProtocolException.stringTooLong("Name", nameLen, 4096000); 
/*  46 */       obj.name = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  48 */     if ((nullBits & 0x2) != 0) {
/*  49 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  50 */       int descriptionLen = VarInt.peek(buf, varPos1);
/*  51 */       if (descriptionLen < 0) throw ProtocolException.negativeLength("Description", descriptionLen); 
/*  52 */       if (descriptionLen > 4096000) throw ProtocolException.stringTooLong("Description", descriptionLen, 4096000); 
/*  53 */       obj.description = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     byte nullBits = buf.getByte(offset);
/*  61 */     int maxEnd = 9;
/*  62 */     if ((nullBits & 0x1) != 0) {
/*  63 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  64 */       int pos0 = offset + 9 + fieldOffset0;
/*  65 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  66 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  68 */     if ((nullBits & 0x2) != 0) {
/*  69 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  70 */       int pos1 = offset + 9 + fieldOffset1;
/*  71 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  72 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  74 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  79 */     int startPos = buf.writerIndex();
/*  80 */     byte nullBits = 0;
/*  81 */     if (this.name != null) nullBits = (byte)(nullBits | 0x1); 
/*  82 */     if (this.description != null) nullBits = (byte)(nullBits | 0x2); 
/*  83 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  86 */     int nameOffsetSlot = buf.writerIndex();
/*  87 */     buf.writeIntLE(0);
/*  88 */     int descriptionOffsetSlot = buf.writerIndex();
/*  89 */     buf.writeIntLE(0);
/*     */     
/*  91 */     int varBlockStart = buf.writerIndex();
/*  92 */     if (this.name != null) {
/*  93 */       buf.setIntLE(nameOffsetSlot, buf.writerIndex() - varBlockStart);
/*  94 */       PacketIO.writeVarString(buf, this.name, 4096000);
/*     */     } else {
/*  96 */       buf.setIntLE(nameOffsetSlot, -1);
/*     */     } 
/*  98 */     if (this.description != null) {
/*  99 */       buf.setIntLE(descriptionOffsetSlot, buf.writerIndex() - varBlockStart);
/* 100 */       PacketIO.writeVarString(buf, this.description, 4096000);
/*     */     } else {
/* 102 */       buf.setIntLE(descriptionOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 108 */     int size = 9;
/* 109 */     if (this.name != null) size += PacketIO.stringSize(this.name); 
/* 110 */     if (this.description != null) size += PacketIO.stringSize(this.description);
/*     */     
/* 112 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 116 */     if (buffer.readableBytes() - offset < 9) {
/* 117 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 120 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 123 */     if ((nullBits & 0x1) != 0) {
/* 124 */       int nameOffset = buffer.getIntLE(offset + 1);
/* 125 */       if (nameOffset < 0) {
/* 126 */         return ValidationResult.error("Invalid offset for Name");
/*     */       }
/* 128 */       int pos = offset + 9 + nameOffset;
/* 129 */       if (pos >= buffer.writerIndex()) {
/* 130 */         return ValidationResult.error("Offset out of bounds for Name");
/*     */       }
/* 132 */       int nameLen = VarInt.peek(buffer, pos);
/* 133 */       if (nameLen < 0) {
/* 134 */         return ValidationResult.error("Invalid string length for Name");
/*     */       }
/* 136 */       if (nameLen > 4096000) {
/* 137 */         return ValidationResult.error("Name exceeds max length 4096000");
/*     */       }
/* 139 */       pos += VarInt.length(buffer, pos);
/* 140 */       pos += nameLen;
/* 141 */       if (pos > buffer.writerIndex()) {
/* 142 */         return ValidationResult.error("Buffer overflow reading Name");
/*     */       }
/*     */     } 
/*     */     
/* 146 */     if ((nullBits & 0x2) != 0) {
/* 147 */       int descriptionOffset = buffer.getIntLE(offset + 5);
/* 148 */       if (descriptionOffset < 0) {
/* 149 */         return ValidationResult.error("Invalid offset for Description");
/*     */       }
/* 151 */       int pos = offset + 9 + descriptionOffset;
/* 152 */       if (pos >= buffer.writerIndex()) {
/* 153 */         return ValidationResult.error("Offset out of bounds for Description");
/*     */       }
/* 155 */       int descriptionLen = VarInt.peek(buffer, pos);
/* 156 */       if (descriptionLen < 0) {
/* 157 */         return ValidationResult.error("Invalid string length for Description");
/*     */       }
/* 159 */       if (descriptionLen > 4096000) {
/* 160 */         return ValidationResult.error("Description exceeds max length 4096000");
/*     */       }
/* 162 */       pos += VarInt.length(buffer, pos);
/* 163 */       pos += descriptionLen;
/* 164 */       if (pos > buffer.writerIndex()) {
/* 165 */         return ValidationResult.error("Buffer overflow reading Description");
/*     */       }
/*     */     } 
/* 168 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemTranslationProperties clone() {
/* 172 */     ItemTranslationProperties copy = new ItemTranslationProperties();
/* 173 */     copy.name = this.name;
/* 174 */     copy.description = this.description;
/* 175 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemTranslationProperties other;
/* 181 */     if (this == obj) return true; 
/* 182 */     if (obj instanceof ItemTranslationProperties) { other = (ItemTranslationProperties)obj; } else { return false; }
/* 183 */      return (Objects.equals(this.name, other.name) && Objects.equals(this.description, other.description));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 188 */     return Objects.hash(new Object[] { this.name, this.description });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemTranslationProperties.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */