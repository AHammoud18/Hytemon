/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class AssetEditorSetupSchemas
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 305;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public SchemaFile[] schemas;
/*     */   
/*     */   public int getId() {
/*  25 */     return 305;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorSetupSchemas() {}
/*     */ 
/*     */   
/*     */   public AssetEditorSetupSchemas(@Nullable SchemaFile[] schemas) {
/*  34 */     this.schemas = schemas;
/*     */   }
/*     */   
/*     */   public AssetEditorSetupSchemas(@Nonnull AssetEditorSetupSchemas other) {
/*  38 */     this.schemas = other.schemas;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorSetupSchemas deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     AssetEditorSetupSchemas obj = new AssetEditorSetupSchemas();
/*  44 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  46 */     int pos = offset + 1;
/*  47 */     if ((nullBits & 0x1) != 0) { int schemasCount = VarInt.peek(buf, pos);
/*  48 */       if (schemasCount < 0) throw ProtocolException.negativeLength("Schemas", schemasCount); 
/*  49 */       if (schemasCount > 4096000) throw ProtocolException.arrayTooLong("Schemas", schemasCount, 4096000); 
/*  50 */       int schemasVarLen = VarInt.size(schemasCount);
/*  51 */       if ((pos + schemasVarLen) + schemasCount * 1L > buf.readableBytes())
/*  52 */         throw ProtocolException.bufferTooSmall("Schemas", pos + schemasVarLen + schemasCount * 1, buf.readableBytes()); 
/*  53 */       pos += schemasVarLen;
/*  54 */       obj.schemas = new SchemaFile[schemasCount];
/*  55 */       for (int i = 0; i < schemasCount; i++) {
/*  56 */         obj.schemas[i] = SchemaFile.deserialize(buf, pos);
/*  57 */         pos += SchemaFile.computeBytesConsumed(buf, pos);
/*     */       }  }
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     byte nullBits = buf.getByte(offset);
/*  65 */     int pos = offset + 1;
/*  66 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  67 */       for (int i = 0; i < arrLen; ) { pos += SchemaFile.computeBytesConsumed(buf, pos); i++; }  }
/*  68 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  74 */     byte nullBits = 0;
/*  75 */     if (this.schemas != null) nullBits = (byte)(nullBits | 0x1); 
/*  76 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  79 */     if (this.schemas != null) { if (this.schemas.length > 4096000) throw ProtocolException.arrayTooLong("Schemas", this.schemas.length, 4096000);  VarInt.write(buf, this.schemas.length); for (SchemaFile item : this.schemas) item.serialize(buf);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  84 */     int size = 1;
/*  85 */     if (this.schemas != null) {
/*  86 */       int schemasSize = 0;
/*  87 */       for (SchemaFile elem : this.schemas) schemasSize += elem.computeSize(); 
/*  88 */       size += VarInt.size(this.schemas.length) + schemasSize;
/*     */     } 
/*     */     
/*  91 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  95 */     if (buffer.readableBytes() - offset < 1) {
/*  96 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  99 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 101 */     int pos = offset + 1;
/*     */     
/* 103 */     if ((nullBits & 0x1) != 0) {
/* 104 */       int schemasCount = VarInt.peek(buffer, pos);
/* 105 */       if (schemasCount < 0) {
/* 106 */         return ValidationResult.error("Invalid array count for Schemas");
/*     */       }
/* 108 */       if (schemasCount > 4096000) {
/* 109 */         return ValidationResult.error("Schemas exceeds max length 4096000");
/*     */       }
/* 111 */       pos += VarInt.length(buffer, pos);
/* 112 */       for (int i = 0; i < schemasCount; i++) {
/* 113 */         ValidationResult structResult = SchemaFile.validateStructure(buffer, pos);
/* 114 */         if (!structResult.isValid()) {
/* 115 */           return ValidationResult.error("Invalid SchemaFile in Schemas[" + i + "]: " + structResult.error());
/*     */         }
/* 117 */         pos += SchemaFile.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 120 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorSetupSchemas clone() {
/* 124 */     AssetEditorSetupSchemas copy = new AssetEditorSetupSchemas();
/* 125 */     copy.schemas = (this.schemas != null) ? (SchemaFile[])Arrays.<SchemaFile>stream(this.schemas).map(e -> e.clone()).toArray(x$0 -> new SchemaFile[x$0]) : null;
/* 126 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorSetupSchemas other;
/* 132 */     if (this == obj) return true; 
/* 133 */     if (obj instanceof AssetEditorSetupSchemas) { other = (AssetEditorSetupSchemas)obj; } else { return false; }
/* 134 */      return Arrays.equals((Object[])this.schemas, (Object[])other.schemas);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 139 */     int result = 1;
/* 140 */     result = 31 * result + Arrays.hashCode((Object[])this.schemas);
/* 141 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorSetupSchemas.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */