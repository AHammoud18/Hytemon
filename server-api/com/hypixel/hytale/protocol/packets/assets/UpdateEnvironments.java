/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.UpdateType;
/*     */ import com.hypixel.hytale.protocol.WorldEnvironment;
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
/*     */ public class UpdateEnvironments
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 61;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 7;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 7;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  27 */     return 61;
/*     */   }
/*     */   @Nonnull
/*  30 */   public UpdateType type = UpdateType.Init;
/*     */   
/*     */   public int maxId;
/*     */   
/*     */   @Nullable
/*     */   public Map<Integer, WorldEnvironment> environments;
/*     */   public boolean rebuildMapGeometry;
/*     */   
/*     */   public UpdateEnvironments(@Nonnull UpdateType type, int maxId, @Nullable Map<Integer, WorldEnvironment> environments, boolean rebuildMapGeometry) {
/*  39 */     this.type = type;
/*  40 */     this.maxId = maxId;
/*  41 */     this.environments = environments;
/*  42 */     this.rebuildMapGeometry = rebuildMapGeometry;
/*     */   }
/*     */   
/*     */   public UpdateEnvironments(@Nonnull UpdateEnvironments other) {
/*  46 */     this.type = other.type;
/*  47 */     this.maxId = other.maxId;
/*  48 */     this.environments = other.environments;
/*  49 */     this.rebuildMapGeometry = other.rebuildMapGeometry;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateEnvironments deserialize(@Nonnull ByteBuf buf, int offset) {
/*  54 */     UpdateEnvironments obj = new UpdateEnvironments();
/*  55 */     byte nullBits = buf.getByte(offset);
/*  56 */     obj.type = UpdateType.fromValue(buf.getByte(offset + 1));
/*  57 */     obj.maxId = buf.getIntLE(offset + 2);
/*  58 */     obj.rebuildMapGeometry = (buf.getByte(offset + 6) != 0);
/*     */     
/*  60 */     int pos = offset + 7;
/*  61 */     if ((nullBits & 0x1) != 0) { int environmentsCount = VarInt.peek(buf, pos);
/*  62 */       if (environmentsCount < 0) throw ProtocolException.negativeLength("Environments", environmentsCount); 
/*  63 */       if (environmentsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Environments", environmentsCount, 4096000); 
/*  64 */       pos += VarInt.size(environmentsCount);
/*  65 */       obj.environments = new HashMap<>(environmentsCount);
/*  66 */       for (int i = 0; i < environmentsCount; i++) {
/*  67 */         int key = buf.getIntLE(pos); pos += 4;
/*  68 */         WorldEnvironment val = WorldEnvironment.deserialize(buf, pos);
/*  69 */         pos += WorldEnvironment.computeBytesConsumed(buf, pos);
/*  70 */         if (obj.environments.put(Integer.valueOf(key), val) != null)
/*  71 */           throw ProtocolException.duplicateKey("environments", Integer.valueOf(key)); 
/*     */       }  }
/*     */     
/*  74 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  78 */     byte nullBits = buf.getByte(offset);
/*  79 */     int pos = offset + 7;
/*  80 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  81 */       for (int i = 0; i < dictLen; ) { pos += 4; pos += WorldEnvironment.computeBytesConsumed(buf, pos); i++; }  }
/*  82 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  88 */     byte nullBits = 0;
/*  89 */     if (this.environments != null) nullBits = (byte)(nullBits | 0x1); 
/*  90 */     buf.writeByte(nullBits);
/*     */     
/*  92 */     buf.writeByte(this.type.getValue());
/*  93 */     buf.writeIntLE(this.maxId);
/*  94 */     buf.writeByte(this.rebuildMapGeometry ? 1 : 0);
/*     */     
/*  96 */     if (this.environments != null) { if (this.environments.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Environments", this.environments.size(), 4096000);  VarInt.write(buf, this.environments.size()); for (Map.Entry<Integer, WorldEnvironment> e : this.environments.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); ((WorldEnvironment)e.getValue()).serialize(buf); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/* 101 */     int size = 7;
/* 102 */     if (this.environments != null) {
/* 103 */       int environmentsSize = 0;
/* 104 */       for (Map.Entry<Integer, WorldEnvironment> kvp : this.environments.entrySet()) environmentsSize += 4 + ((WorldEnvironment)kvp.getValue()).computeSize(); 
/* 105 */       size += VarInt.size(this.environments.size()) + environmentsSize;
/*     */     } 
/*     */     
/* 108 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 112 */     if (buffer.readableBytes() - offset < 7) {
/* 113 */       return ValidationResult.error("Buffer too small: expected at least 7 bytes");
/*     */     }
/*     */     
/* 116 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 118 */     int pos = offset + 7;
/*     */     
/* 120 */     if ((nullBits & 0x1) != 0) {
/* 121 */       int environmentsCount = VarInt.peek(buffer, pos);
/* 122 */       if (environmentsCount < 0) {
/* 123 */         return ValidationResult.error("Invalid dictionary count for Environments");
/*     */       }
/* 125 */       if (environmentsCount > 4096000) {
/* 126 */         return ValidationResult.error("Environments exceeds max length 4096000");
/*     */       }
/* 128 */       pos += VarInt.length(buffer, pos);
/* 129 */       for (int i = 0; i < environmentsCount; i++) {
/* 130 */         pos += 4;
/* 131 */         if (pos > buffer.writerIndex()) {
/* 132 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 134 */         pos += WorldEnvironment.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 138 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateEnvironments clone() {
/* 142 */     UpdateEnvironments copy = new UpdateEnvironments();
/* 143 */     copy.type = this.type;
/* 144 */     copy.maxId = this.maxId;
/* 145 */     if (this.environments != null) {
/* 146 */       Map<Integer, WorldEnvironment> m = new HashMap<>();
/* 147 */       for (Map.Entry<Integer, WorldEnvironment> e : this.environments.entrySet()) m.put(e.getKey(), ((WorldEnvironment)e.getValue()).clone()); 
/* 148 */       copy.environments = m;
/*     */     } 
/* 150 */     copy.rebuildMapGeometry = this.rebuildMapGeometry;
/* 151 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateEnvironments other;
/* 157 */     if (this == obj) return true; 
/* 158 */     if (obj instanceof UpdateEnvironments) { other = (UpdateEnvironments)obj; } else { return false; }
/* 159 */      return (Objects.equals(this.type, other.type) && this.maxId == other.maxId && Objects.equals(this.environments, other.environments) && this.rebuildMapGeometry == other.rebuildMapGeometry);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 164 */     return Objects.hash(new Object[] { this.type, Integer.valueOf(this.maxId), this.environments, Boolean.valueOf(this.rebuildMapGeometry) });
/*     */   }
/*     */   
/*     */   public UpdateEnvironments() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UpdateEnvironments.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */