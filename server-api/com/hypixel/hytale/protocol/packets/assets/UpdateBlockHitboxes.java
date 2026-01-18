/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Hitbox;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.UpdateType;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class UpdateBlockHitboxes implements Packet {
/*     */   public static final int PACKET_ID = 41;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 6;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  27 */     return 41;
/*     */   }
/*     */   @Nonnull
/*  30 */   public UpdateType type = UpdateType.Init;
/*     */   
/*     */   public int maxId;
/*     */   
/*     */   @Nullable
/*     */   public Map<Integer, Hitbox[]> blockBaseHitboxes;
/*     */   
/*     */   public UpdateBlockHitboxes(@Nonnull UpdateType type, int maxId, @Nullable Map<Integer, Hitbox[]> blockBaseHitboxes) {
/*  38 */     this.type = type;
/*  39 */     this.maxId = maxId;
/*  40 */     this.blockBaseHitboxes = blockBaseHitboxes;
/*     */   }
/*     */   
/*     */   public UpdateBlockHitboxes(@Nonnull UpdateBlockHitboxes other) {
/*  44 */     this.type = other.type;
/*  45 */     this.maxId = other.maxId;
/*  46 */     this.blockBaseHitboxes = other.blockBaseHitboxes;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateBlockHitboxes deserialize(@Nonnull ByteBuf buf, int offset) {
/*  51 */     UpdateBlockHitboxes obj = new UpdateBlockHitboxes();
/*  52 */     byte nullBits = buf.getByte(offset);
/*  53 */     obj.type = UpdateType.fromValue(buf.getByte(offset + 1));
/*  54 */     obj.maxId = buf.getIntLE(offset + 2);
/*     */     
/*  56 */     int pos = offset + 6;
/*  57 */     if ((nullBits & 0x1) != 0) { int blockBaseHitboxesCount = VarInt.peek(buf, pos);
/*  58 */       if (blockBaseHitboxesCount < 0) throw ProtocolException.negativeLength("BlockBaseHitboxes", blockBaseHitboxesCount); 
/*  59 */       if (blockBaseHitboxesCount > 4096000) throw ProtocolException.dictionaryTooLarge("BlockBaseHitboxes", blockBaseHitboxesCount, 4096000); 
/*  60 */       pos += VarInt.size(blockBaseHitboxesCount);
/*  61 */       obj.blockBaseHitboxes = (Map)new HashMap<>(blockBaseHitboxesCount);
/*  62 */       for (int i = 0; i < blockBaseHitboxesCount; i++) {
/*  63 */         int key = buf.getIntLE(pos); pos += 4;
/*  64 */         int valLen = VarInt.peek(buf, pos);
/*  65 */         if (valLen < 0) throw ProtocolException.negativeLength("val", valLen); 
/*  66 */         if (valLen > 64) throw ProtocolException.arrayTooLong("val", valLen, 64); 
/*  67 */         int valVarLen = VarInt.length(buf, pos);
/*  68 */         if ((pos + valVarLen) + valLen * 24L > buf.readableBytes())
/*  69 */           throw ProtocolException.bufferTooSmall("val", pos + valVarLen + valLen * 24, buf.readableBytes()); 
/*  70 */         pos += valVarLen;
/*  71 */         Hitbox[] val = new Hitbox[valLen];
/*  72 */         for (int valIdx = 0; valIdx < valLen; valIdx++) {
/*  73 */           val[valIdx] = Hitbox.deserialize(buf, pos);
/*  74 */           pos += Hitbox.computeBytesConsumed(buf, pos);
/*     */         } 
/*  76 */         if (obj.blockBaseHitboxes.put(Integer.valueOf(key), val) != null)
/*  77 */           throw ProtocolException.duplicateKey("blockBaseHitboxes", Integer.valueOf(key)); 
/*     */       }  }
/*     */     
/*  80 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  84 */     byte nullBits = buf.getByte(offset);
/*  85 */     int pos = offset + 6;
/*  86 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  87 */       for (int i = 0; i < dictLen; ) { int al; int j; for (pos += 4, al = VarInt.peek(buf, pos), pos += VarInt.length(buf, pos), j = 0; j < al; ) { pos += Hitbox.computeBytesConsumed(buf, pos); j++; }  i++; }  }
/*  88 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  94 */     byte nullBits = 0;
/*  95 */     if (this.blockBaseHitboxes != null) nullBits = (byte)(nullBits | 0x1); 
/*  96 */     buf.writeByte(nullBits);
/*     */     
/*  98 */     buf.writeByte(this.type.getValue());
/*  99 */     buf.writeIntLE(this.maxId);
/*     */     
/* 101 */     if (this.blockBaseHitboxes != null) { if (this.blockBaseHitboxes.size() > 4096000) throw ProtocolException.dictionaryTooLarge("BlockBaseHitboxes", this.blockBaseHitboxes.size(), 4096000);  VarInt.write(buf, this.blockBaseHitboxes.size()); for (Map.Entry<Integer, Hitbox[]> e : this.blockBaseHitboxes.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); VarInt.write(buf, ((Hitbox[])e.getValue()).length); for (Hitbox arrItem : (Hitbox[])e.getValue()) arrItem.serialize(buf);  }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/* 106 */     int size = 6;
/* 107 */     if (this.blockBaseHitboxes != null) {
/* 108 */       int blockBaseHitboxesSize = 0;
/* 109 */       for (Map.Entry<Integer, Hitbox[]> kvp : this.blockBaseHitboxes.entrySet()) blockBaseHitboxesSize += 4 + VarInt.size(((Hitbox[])kvp.getValue()).length) + ((Hitbox[])kvp.getValue()).length * 24; 
/* 110 */       size += VarInt.size(this.blockBaseHitboxes.size()) + blockBaseHitboxesSize;
/*     */     } 
/*     */     
/* 113 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 117 */     if (buffer.readableBytes() - offset < 6) {
/* 118 */       return ValidationResult.error("Buffer too small: expected at least 6 bytes");
/*     */     }
/*     */     
/* 121 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 123 */     int pos = offset + 6;
/*     */     
/* 125 */     if ((nullBits & 0x1) != 0) {
/* 126 */       int blockBaseHitboxesCount = VarInt.peek(buffer, pos);
/* 127 */       if (blockBaseHitboxesCount < 0) {
/* 128 */         return ValidationResult.error("Invalid dictionary count for BlockBaseHitboxes");
/*     */       }
/* 130 */       if (blockBaseHitboxesCount > 4096000) {
/* 131 */         return ValidationResult.error("BlockBaseHitboxes exceeds max length 4096000");
/*     */       }
/* 133 */       pos += VarInt.length(buffer, pos);
/* 134 */       for (int i = 0; i < blockBaseHitboxesCount; i++) {
/* 135 */         pos += 4;
/* 136 */         if (pos > buffer.writerIndex()) {
/* 137 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 139 */         int valueArrCount = VarInt.peek(buffer, pos);
/* 140 */         if (valueArrCount < 0) {
/* 141 */           return ValidationResult.error("Invalid array count for value");
/*     */         }
/* 143 */         pos += VarInt.length(buffer, pos);
/* 144 */         for (int valueArrIdx = 0; valueArrIdx < valueArrCount; valueArrIdx++) {
/* 145 */           pos += 24;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 150 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateBlockHitboxes clone() {
/* 154 */     UpdateBlockHitboxes copy = new UpdateBlockHitboxes();
/* 155 */     copy.type = this.type;
/* 156 */     copy.maxId = this.maxId;
/* 157 */     if (this.blockBaseHitboxes != null) {
/* 158 */       Map<Integer, Hitbox[]> m = (Map)new HashMap<>();
/* 159 */       for (Map.Entry<Integer, Hitbox[]> e : this.blockBaseHitboxes.entrySet()) m.put(e.getKey(), (Hitbox[])Arrays.<Hitbox>stream(e.getValue()).map(x -> x.clone()).toArray(x$0 -> new Hitbox[x$0])); 
/* 160 */       copy.blockBaseHitboxes = m;
/*     */     } 
/* 162 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateBlockHitboxes other;
/* 168 */     if (this == obj) return true; 
/* 169 */     if (obj instanceof UpdateBlockHitboxes) { other = (UpdateBlockHitboxes)obj; } else { return false; }
/* 170 */      return (Objects.equals(this.type, other.type) && this.maxId == other.maxId && Objects.equals(this.blockBaseHitboxes, other.blockBaseHitboxes));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 175 */     return Objects.hash(new Object[] { this.type, Integer.valueOf(this.maxId), this.blockBaseHitboxes });
/*     */   }
/*     */   
/*     */   public UpdateBlockHitboxes() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UpdateBlockHitboxes.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */