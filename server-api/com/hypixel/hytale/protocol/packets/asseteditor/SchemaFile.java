/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
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
/*     */ 
/*     */ public class SchemaFile
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 16384006;
/*     */   @Nullable
/*     */   public String content;
/*     */   
/*     */   public SchemaFile() {}
/*     */   
/*     */   public SchemaFile(@Nullable String content) {
/*  26 */     this.content = content;
/*     */   }
/*     */   
/*     */   public SchemaFile(@Nonnull SchemaFile other) {
/*  30 */     this.content = other.content;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SchemaFile deserialize(@Nonnull ByteBuf buf, int offset) {
/*  35 */     SchemaFile obj = new SchemaFile();
/*  36 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  38 */     int pos = offset + 1;
/*  39 */     if ((nullBits & 0x1) != 0) { int contentLen = VarInt.peek(buf, pos);
/*  40 */       if (contentLen < 0) throw ProtocolException.negativeLength("Content", contentLen); 
/*  41 */       if (contentLen > 4096000) throw ProtocolException.stringTooLong("Content", contentLen, 4096000); 
/*  42 */       int contentVarLen = VarInt.length(buf, pos);
/*  43 */       obj.content = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  44 */       pos += contentVarLen + contentLen; }
/*     */     
/*  46 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  50 */     byte nullBits = buf.getByte(offset);
/*  51 */     int pos = offset + 1;
/*  52 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  53 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  58 */     byte nullBits = 0;
/*  59 */     if (this.content != null) nullBits = (byte)(nullBits | 0x1); 
/*  60 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  63 */     if (this.content != null) PacketIO.writeVarString(buf, this.content, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  68 */     int size = 1;
/*  69 */     if (this.content != null) size += PacketIO.stringSize(this.content);
/*     */     
/*  71 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  75 */     if (buffer.readableBytes() - offset < 1) {
/*  76 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  79 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  81 */     int pos = offset + 1;
/*     */     
/*  83 */     if ((nullBits & 0x1) != 0) {
/*  84 */       int contentLen = VarInt.peek(buffer, pos);
/*  85 */       if (contentLen < 0) {
/*  86 */         return ValidationResult.error("Invalid string length for Content");
/*     */       }
/*  88 */       if (contentLen > 4096000) {
/*  89 */         return ValidationResult.error("Content exceeds max length 4096000");
/*     */       }
/*  91 */       pos += VarInt.length(buffer, pos);
/*  92 */       pos += contentLen;
/*  93 */       if (pos > buffer.writerIndex()) {
/*  94 */         return ValidationResult.error("Buffer overflow reading Content");
/*     */       }
/*     */     } 
/*  97 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SchemaFile clone() {
/* 101 */     SchemaFile copy = new SchemaFile();
/* 102 */     copy.content = this.content;
/* 103 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SchemaFile other;
/* 109 */     if (this == obj) return true; 
/* 110 */     if (obj instanceof SchemaFile) { other = (SchemaFile)obj; } else { return false; }
/* 111 */      return Objects.equals(this.content, other.content);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 116 */     return Objects.hash(new Object[] { this.content });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\SchemaFile.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */