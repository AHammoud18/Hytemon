/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.EntityUIComponent;
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
/*     */ public class UpdateEntityUIComponents
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 73;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 6;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  27 */     return 73;
/*     */   }
/*     */   @Nonnull
/*  30 */   public UpdateType type = UpdateType.Init;
/*     */   
/*     */   public int maxId;
/*     */   
/*     */   @Nullable
/*     */   public Map<Integer, EntityUIComponent> components;
/*     */   
/*     */   public UpdateEntityUIComponents(@Nonnull UpdateType type, int maxId, @Nullable Map<Integer, EntityUIComponent> components) {
/*  38 */     this.type = type;
/*  39 */     this.maxId = maxId;
/*  40 */     this.components = components;
/*     */   }
/*     */   
/*     */   public UpdateEntityUIComponents(@Nonnull UpdateEntityUIComponents other) {
/*  44 */     this.type = other.type;
/*  45 */     this.maxId = other.maxId;
/*  46 */     this.components = other.components;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateEntityUIComponents deserialize(@Nonnull ByteBuf buf, int offset) {
/*  51 */     UpdateEntityUIComponents obj = new UpdateEntityUIComponents();
/*  52 */     byte nullBits = buf.getByte(offset);
/*  53 */     obj.type = UpdateType.fromValue(buf.getByte(offset + 1));
/*  54 */     obj.maxId = buf.getIntLE(offset + 2);
/*     */     
/*  56 */     int pos = offset + 6;
/*  57 */     if ((nullBits & 0x1) != 0) { int componentsCount = VarInt.peek(buf, pos);
/*  58 */       if (componentsCount < 0) throw ProtocolException.negativeLength("Components", componentsCount); 
/*  59 */       if (componentsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Components", componentsCount, 4096000); 
/*  60 */       pos += VarInt.size(componentsCount);
/*  61 */       obj.components = new HashMap<>(componentsCount);
/*  62 */       for (int i = 0; i < componentsCount; i++) {
/*  63 */         int key = buf.getIntLE(pos); pos += 4;
/*  64 */         EntityUIComponent val = EntityUIComponent.deserialize(buf, pos);
/*  65 */         pos += EntityUIComponent.computeBytesConsumed(buf, pos);
/*  66 */         if (obj.components.put(Integer.valueOf(key), val) != null)
/*  67 */           throw ProtocolException.duplicateKey("components", Integer.valueOf(key)); 
/*     */       }  }
/*     */     
/*  70 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  74 */     byte nullBits = buf.getByte(offset);
/*  75 */     int pos = offset + 6;
/*  76 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  77 */       for (int i = 0; i < dictLen; ) { pos += 4; pos += EntityUIComponent.computeBytesConsumed(buf, pos); i++; }  }
/*  78 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  84 */     byte nullBits = 0;
/*  85 */     if (this.components != null) nullBits = (byte)(nullBits | 0x1); 
/*  86 */     buf.writeByte(nullBits);
/*     */     
/*  88 */     buf.writeByte(this.type.getValue());
/*  89 */     buf.writeIntLE(this.maxId);
/*     */     
/*  91 */     if (this.components != null) { if (this.components.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Components", this.components.size(), 4096000);  VarInt.write(buf, this.components.size()); for (Map.Entry<Integer, EntityUIComponent> e : this.components.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); ((EntityUIComponent)e.getValue()).serialize(buf); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/*  96 */     int size = 6;
/*  97 */     if (this.components != null) {
/*  98 */       int componentsSize = 0;
/*  99 */       for (Map.Entry<Integer, EntityUIComponent> kvp : this.components.entrySet()) componentsSize += 4 + ((EntityUIComponent)kvp.getValue()).computeSize(); 
/* 100 */       size += VarInt.size(this.components.size()) + componentsSize;
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
/* 116 */       int componentsCount = VarInt.peek(buffer, pos);
/* 117 */       if (componentsCount < 0) {
/* 118 */         return ValidationResult.error("Invalid dictionary count for Components");
/*     */       }
/* 120 */       if (componentsCount > 4096000) {
/* 121 */         return ValidationResult.error("Components exceeds max length 4096000");
/*     */       }
/* 123 */       pos += VarInt.length(buffer, pos);
/* 124 */       for (int i = 0; i < componentsCount; i++) {
/* 125 */         pos += 4;
/* 126 */         if (pos > buffer.writerIndex()) {
/* 127 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 129 */         pos += EntityUIComponent.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 133 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateEntityUIComponents clone() {
/* 137 */     UpdateEntityUIComponents copy = new UpdateEntityUIComponents();
/* 138 */     copy.type = this.type;
/* 139 */     copy.maxId = this.maxId;
/* 140 */     if (this.components != null) {
/* 141 */       Map<Integer, EntityUIComponent> m = new HashMap<>();
/* 142 */       for (Map.Entry<Integer, EntityUIComponent> e : this.components.entrySet()) m.put(e.getKey(), ((EntityUIComponent)e.getValue()).clone()); 
/* 143 */       copy.components = m;
/*     */     } 
/* 145 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateEntityUIComponents other;
/* 151 */     if (this == obj) return true; 
/* 152 */     if (obj instanceof UpdateEntityUIComponents) { other = (UpdateEntityUIComponents)obj; } else { return false; }
/* 153 */      return (Objects.equals(this.type, other.type) && this.maxId == other.maxId && Objects.equals(this.components, other.components));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 158 */     return Objects.hash(new Object[] { this.type, Integer.valueOf(this.maxId), this.components });
/*     */   }
/*     */   
/*     */   public UpdateEntityUIComponents() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UpdateEntityUIComponents.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */