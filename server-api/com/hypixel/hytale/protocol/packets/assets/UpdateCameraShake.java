/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.CameraShake;
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
/*     */ public class UpdateCameraShake
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 77;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 2;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  27 */     return 77;
/*     */   }
/*     */   @Nonnull
/*  30 */   public UpdateType type = UpdateType.Init;
/*     */   
/*     */   @Nullable
/*     */   public Map<Integer, CameraShake> profiles;
/*     */ 
/*     */   
/*     */   public UpdateCameraShake(@Nonnull UpdateType type, @Nullable Map<Integer, CameraShake> profiles) {
/*  37 */     this.type = type;
/*  38 */     this.profiles = profiles;
/*     */   }
/*     */   
/*     */   public UpdateCameraShake(@Nonnull UpdateCameraShake other) {
/*  42 */     this.type = other.type;
/*  43 */     this.profiles = other.profiles;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateCameraShake deserialize(@Nonnull ByteBuf buf, int offset) {
/*  48 */     UpdateCameraShake obj = new UpdateCameraShake();
/*  49 */     byte nullBits = buf.getByte(offset);
/*  50 */     obj.type = UpdateType.fromValue(buf.getByte(offset + 1));
/*     */     
/*  52 */     int pos = offset + 2;
/*  53 */     if ((nullBits & 0x1) != 0) { int profilesCount = VarInt.peek(buf, pos);
/*  54 */       if (profilesCount < 0) throw ProtocolException.negativeLength("Profiles", profilesCount); 
/*  55 */       if (profilesCount > 4096000) throw ProtocolException.dictionaryTooLarge("Profiles", profilesCount, 4096000); 
/*  56 */       pos += VarInt.size(profilesCount);
/*  57 */       obj.profiles = new HashMap<>(profilesCount);
/*  58 */       for (int i = 0; i < profilesCount; i++) {
/*  59 */         int key = buf.getIntLE(pos); pos += 4;
/*  60 */         CameraShake val = CameraShake.deserialize(buf, pos);
/*  61 */         pos += CameraShake.computeBytesConsumed(buf, pos);
/*  62 */         if (obj.profiles.put(Integer.valueOf(key), val) != null)
/*  63 */           throw ProtocolException.duplicateKey("profiles", Integer.valueOf(key)); 
/*     */       }  }
/*     */     
/*  66 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  70 */     byte nullBits = buf.getByte(offset);
/*  71 */     int pos = offset + 2;
/*  72 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  73 */       for (int i = 0; i < dictLen; ) { pos += 4; pos += CameraShake.computeBytesConsumed(buf, pos); i++; }  }
/*  74 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  80 */     byte nullBits = 0;
/*  81 */     if (this.profiles != null) nullBits = (byte)(nullBits | 0x1); 
/*  82 */     buf.writeByte(nullBits);
/*     */     
/*  84 */     buf.writeByte(this.type.getValue());
/*     */     
/*  86 */     if (this.profiles != null) { if (this.profiles.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Profiles", this.profiles.size(), 4096000);  VarInt.write(buf, this.profiles.size()); for (Map.Entry<Integer, CameraShake> e : this.profiles.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); ((CameraShake)e.getValue()).serialize(buf); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/*  91 */     int size = 2;
/*  92 */     if (this.profiles != null) {
/*  93 */       int profilesSize = 0;
/*  94 */       for (Map.Entry<Integer, CameraShake> kvp : this.profiles.entrySet()) profilesSize += 4 + ((CameraShake)kvp.getValue()).computeSize(); 
/*  95 */       size += VarInt.size(this.profiles.size()) + profilesSize;
/*     */     } 
/*     */     
/*  98 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 102 */     if (buffer.readableBytes() - offset < 2) {
/* 103 */       return ValidationResult.error("Buffer too small: expected at least 2 bytes");
/*     */     }
/*     */     
/* 106 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 108 */     int pos = offset + 2;
/*     */     
/* 110 */     if ((nullBits & 0x1) != 0) {
/* 111 */       int profilesCount = VarInt.peek(buffer, pos);
/* 112 */       if (profilesCount < 0) {
/* 113 */         return ValidationResult.error("Invalid dictionary count for Profiles");
/*     */       }
/* 115 */       if (profilesCount > 4096000) {
/* 116 */         return ValidationResult.error("Profiles exceeds max length 4096000");
/*     */       }
/* 118 */       pos += VarInt.length(buffer, pos);
/* 119 */       for (int i = 0; i < profilesCount; i++) {
/* 120 */         pos += 4;
/* 121 */         if (pos > buffer.writerIndex()) {
/* 122 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 124 */         pos += CameraShake.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 128 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateCameraShake clone() {
/* 132 */     UpdateCameraShake copy = new UpdateCameraShake();
/* 133 */     copy.type = this.type;
/* 134 */     if (this.profiles != null) {
/* 135 */       Map<Integer, CameraShake> m = new HashMap<>();
/* 136 */       for (Map.Entry<Integer, CameraShake> e : this.profiles.entrySet()) m.put(e.getKey(), ((CameraShake)e.getValue()).clone()); 
/* 137 */       copy.profiles = m;
/*     */     } 
/* 139 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateCameraShake other;
/* 145 */     if (this == obj) return true; 
/* 146 */     if (obj instanceof UpdateCameraShake) { other = (UpdateCameraShake)obj; } else { return false; }
/* 147 */      return (Objects.equals(this.type, other.type) && Objects.equals(this.profiles, other.profiles));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 152 */     return Objects.hash(new Object[] { this.type, this.profiles });
/*     */   }
/*     */   
/*     */   public UpdateCameraShake() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UpdateCameraShake.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */