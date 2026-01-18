/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.BlockType;
/*     */ import com.hypixel.hytale.protocol.Packet;
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
/*     */ public class UpdateBlockTypes
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 40;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 10;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 10;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  27 */     return 40;
/*     */   }
/*     */   @Nonnull
/*  30 */   public UpdateType type = UpdateType.Init;
/*     */   
/*     */   public int maxId;
/*     */   
/*     */   @Nullable
/*     */   public Map<Integer, BlockType> blockTypes;
/*     */   public boolean updateBlockTextures;
/*     */   public boolean updateModelTextures;
/*     */   public boolean updateModels;
/*     */   public boolean updateMapGeometry;
/*     */   
/*     */   public UpdateBlockTypes(@Nonnull UpdateType type, int maxId, @Nullable Map<Integer, BlockType> blockTypes, boolean updateBlockTextures, boolean updateModelTextures, boolean updateModels, boolean updateMapGeometry) {
/*  42 */     this.type = type;
/*  43 */     this.maxId = maxId;
/*  44 */     this.blockTypes = blockTypes;
/*  45 */     this.updateBlockTextures = updateBlockTextures;
/*  46 */     this.updateModelTextures = updateModelTextures;
/*  47 */     this.updateModels = updateModels;
/*  48 */     this.updateMapGeometry = updateMapGeometry;
/*     */   }
/*     */   
/*     */   public UpdateBlockTypes(@Nonnull UpdateBlockTypes other) {
/*  52 */     this.type = other.type;
/*  53 */     this.maxId = other.maxId;
/*  54 */     this.blockTypes = other.blockTypes;
/*  55 */     this.updateBlockTextures = other.updateBlockTextures;
/*  56 */     this.updateModelTextures = other.updateModelTextures;
/*  57 */     this.updateModels = other.updateModels;
/*  58 */     this.updateMapGeometry = other.updateMapGeometry;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateBlockTypes deserialize(@Nonnull ByteBuf buf, int offset) {
/*  63 */     UpdateBlockTypes obj = new UpdateBlockTypes();
/*  64 */     byte nullBits = buf.getByte(offset);
/*  65 */     obj.type = UpdateType.fromValue(buf.getByte(offset + 1));
/*  66 */     obj.maxId = buf.getIntLE(offset + 2);
/*  67 */     obj.updateBlockTextures = (buf.getByte(offset + 6) != 0);
/*  68 */     obj.updateModelTextures = (buf.getByte(offset + 7) != 0);
/*  69 */     obj.updateModels = (buf.getByte(offset + 8) != 0);
/*  70 */     obj.updateMapGeometry = (buf.getByte(offset + 9) != 0);
/*     */     
/*  72 */     int pos = offset + 10;
/*  73 */     if ((nullBits & 0x1) != 0) { int blockTypesCount = VarInt.peek(buf, pos);
/*  74 */       if (blockTypesCount < 0) throw ProtocolException.negativeLength("BlockTypes", blockTypesCount); 
/*  75 */       if (blockTypesCount > 4096000) throw ProtocolException.dictionaryTooLarge("BlockTypes", blockTypesCount, 4096000); 
/*  76 */       pos += VarInt.size(blockTypesCount);
/*  77 */       obj.blockTypes = new HashMap<>(blockTypesCount);
/*  78 */       for (int i = 0; i < blockTypesCount; i++) {
/*  79 */         int key = buf.getIntLE(pos); pos += 4;
/*  80 */         BlockType val = BlockType.deserialize(buf, pos);
/*  81 */         pos += BlockType.computeBytesConsumed(buf, pos);
/*  82 */         if (obj.blockTypes.put(Integer.valueOf(key), val) != null)
/*  83 */           throw ProtocolException.duplicateKey("blockTypes", Integer.valueOf(key)); 
/*     */       }  }
/*     */     
/*  86 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  90 */     byte nullBits = buf.getByte(offset);
/*  91 */     int pos = offset + 10;
/*  92 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  93 */       for (int i = 0; i < dictLen; ) { pos += 4; pos += BlockType.computeBytesConsumed(buf, pos); i++; }  }
/*  94 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 100 */     byte nullBits = 0;
/* 101 */     if (this.blockTypes != null) nullBits = (byte)(nullBits | 0x1); 
/* 102 */     buf.writeByte(nullBits);
/*     */     
/* 104 */     buf.writeByte(this.type.getValue());
/* 105 */     buf.writeIntLE(this.maxId);
/* 106 */     buf.writeByte(this.updateBlockTextures ? 1 : 0);
/* 107 */     buf.writeByte(this.updateModelTextures ? 1 : 0);
/* 108 */     buf.writeByte(this.updateModels ? 1 : 0);
/* 109 */     buf.writeByte(this.updateMapGeometry ? 1 : 0);
/*     */     
/* 111 */     if (this.blockTypes != null) { if (this.blockTypes.size() > 4096000) throw ProtocolException.dictionaryTooLarge("BlockTypes", this.blockTypes.size(), 4096000);  VarInt.write(buf, this.blockTypes.size()); for (Map.Entry<Integer, BlockType> e : this.blockTypes.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); ((BlockType)e.getValue()).serialize(buf); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/* 116 */     int size = 10;
/* 117 */     if (this.blockTypes != null) {
/* 118 */       int blockTypesSize = 0;
/* 119 */       for (Map.Entry<Integer, BlockType> kvp : this.blockTypes.entrySet()) blockTypesSize += 4 + ((BlockType)kvp.getValue()).computeSize(); 
/* 120 */       size += VarInt.size(this.blockTypes.size()) + blockTypesSize;
/*     */     } 
/*     */     
/* 123 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 127 */     if (buffer.readableBytes() - offset < 10) {
/* 128 */       return ValidationResult.error("Buffer too small: expected at least 10 bytes");
/*     */     }
/*     */     
/* 131 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 133 */     int pos = offset + 10;
/*     */     
/* 135 */     if ((nullBits & 0x1) != 0) {
/* 136 */       int blockTypesCount = VarInt.peek(buffer, pos);
/* 137 */       if (blockTypesCount < 0) {
/* 138 */         return ValidationResult.error("Invalid dictionary count for BlockTypes");
/*     */       }
/* 140 */       if (blockTypesCount > 4096000) {
/* 141 */         return ValidationResult.error("BlockTypes exceeds max length 4096000");
/*     */       }
/* 143 */       pos += VarInt.length(buffer, pos);
/* 144 */       for (int i = 0; i < blockTypesCount; i++) {
/* 145 */         pos += 4;
/* 146 */         if (pos > buffer.writerIndex()) {
/* 147 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 149 */         pos += BlockType.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 153 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateBlockTypes clone() {
/* 157 */     UpdateBlockTypes copy = new UpdateBlockTypes();
/* 158 */     copy.type = this.type;
/* 159 */     copy.maxId = this.maxId;
/* 160 */     if (this.blockTypes != null) {
/* 161 */       Map<Integer, BlockType> m = new HashMap<>();
/* 162 */       for (Map.Entry<Integer, BlockType> e : this.blockTypes.entrySet()) m.put(e.getKey(), ((BlockType)e.getValue()).clone()); 
/* 163 */       copy.blockTypes = m;
/*     */     } 
/* 165 */     copy.updateBlockTextures = this.updateBlockTextures;
/* 166 */     copy.updateModelTextures = this.updateModelTextures;
/* 167 */     copy.updateModels = this.updateModels;
/* 168 */     copy.updateMapGeometry = this.updateMapGeometry;
/* 169 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateBlockTypes other;
/* 175 */     if (this == obj) return true; 
/* 176 */     if (obj instanceof UpdateBlockTypes) { other = (UpdateBlockTypes)obj; } else { return false; }
/* 177 */      return (Objects.equals(this.type, other.type) && this.maxId == other.maxId && Objects.equals(this.blockTypes, other.blockTypes) && this.updateBlockTextures == other.updateBlockTextures && this.updateModelTextures == other.updateModelTextures && this.updateModels == other.updateModels && this.updateMapGeometry == other.updateMapGeometry);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 182 */     return Objects.hash(new Object[] { this.type, Integer.valueOf(this.maxId), this.blockTypes, Boolean.valueOf(this.updateBlockTextures), Boolean.valueOf(this.updateModelTextures), Boolean.valueOf(this.updateModels), Boolean.valueOf(this.updateMapGeometry) });
/*     */   }
/*     */   
/*     */   public UpdateBlockTypes() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UpdateBlockTypes.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */