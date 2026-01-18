/*     */ package org.bson;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.UUID;
/*     */ import org.bson.assertions.Assertions;
/*     */ import org.bson.internal.UuidHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BsonBinary
/*     */   extends BsonValue
/*     */ {
/*     */   private final byte type;
/*     */   private final byte[] data;
/*     */   
/*     */   public BsonBinary(byte[] data) {
/*  44 */     this(BsonBinarySubType.BINARY, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonBinary(BsonBinarySubType type, byte[] data) {
/*  56 */     if (type == null) {
/*  57 */       throw new IllegalArgumentException("type may not be null");
/*     */     }
/*  59 */     if (data == null) {
/*  60 */       throw new IllegalArgumentException("data may not be null");
/*     */     }
/*  62 */     this.type = type.getValue();
/*  63 */     this.data = data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonBinary(byte type, byte[] data) {
/*  75 */     if (data == null) {
/*  76 */       throw new IllegalArgumentException("data may not be null");
/*     */     }
/*  78 */     this.type = type;
/*  79 */     this.data = data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonBinary(UUID uuid) {
/*  89 */     this(uuid, UuidRepresentation.STANDARD);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonBinary(UUID uuid, UuidRepresentation uuidRepresentation) {
/* 100 */     if (uuid == null) {
/* 101 */       throw new IllegalArgumentException("uuid may not be null");
/*     */     }
/* 103 */     if (uuidRepresentation == null) {
/* 104 */       throw new IllegalArgumentException("uuidRepresentation may not be null");
/*     */     }
/* 106 */     this.data = UuidHelper.encodeUuidToBinary(uuid, uuidRepresentation);
/* 107 */     this
/*     */       
/* 109 */       .type = (uuidRepresentation == UuidRepresentation.STANDARD) ? BsonBinarySubType.UUID_STANDARD.getValue() : BsonBinarySubType.UUID_LEGACY.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UUID asUuid() {
/* 119 */     if (!BsonBinarySubType.isUuid(this.type)) {
/* 120 */       throw new BsonInvalidOperationException("type must be a UUID subtype.");
/*     */     }
/*     */     
/* 123 */     if (this.type != BsonBinarySubType.UUID_STANDARD.getValue()) {
/* 124 */       throw new BsonInvalidOperationException("uuidRepresentation must be set to return the correct UUID.");
/*     */     }
/*     */     
/* 127 */     return UuidHelper.decodeBinaryToUuid((byte[])this.data.clone(), this.type, UuidRepresentation.STANDARD);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UUID asUuid(UuidRepresentation uuidRepresentation) {
/* 138 */     Assertions.notNull("uuidRepresentation", uuidRepresentation);
/*     */ 
/*     */ 
/*     */     
/* 142 */     byte uuidType = (uuidRepresentation == UuidRepresentation.STANDARD) ? BsonBinarySubType.UUID_STANDARD.getValue() : BsonBinarySubType.UUID_LEGACY.getValue();
/*     */     
/* 144 */     if (this.type != uuidType) {
/* 145 */       throw new BsonInvalidOperationException("uuidRepresentation does not match current uuidRepresentation.");
/*     */     }
/*     */     
/* 148 */     return UuidHelper.decodeBinaryToUuid((byte[])this.data.clone(), this.type, uuidRepresentation);
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonType getBsonType() {
/* 153 */     return BsonType.BINARY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getType() {
/* 162 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getData() {
/* 174 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 179 */     if (this == o) {
/* 180 */       return true;
/*     */     }
/* 182 */     if (o == null || getClass() != o.getClass()) {
/* 183 */       return false;
/*     */     }
/*     */     
/* 186 */     BsonBinary that = (BsonBinary)o;
/*     */     
/* 188 */     if (!Arrays.equals(this.data, that.data)) {
/* 189 */       return false;
/*     */     }
/* 191 */     if (this.type != that.type) {
/* 192 */       return false;
/*     */     }
/*     */     
/* 195 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 200 */     int result = this.type;
/* 201 */     result = 31 * result + Arrays.hashCode(this.data);
/* 202 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 207 */     return "BsonBinary{type=" + this.type + ", data=" + 
/*     */       
/* 209 */       Arrays.toString(this.data) + '}';
/*     */   }
/*     */ 
/*     */   
/*     */   static BsonBinary clone(BsonBinary from) {
/* 214 */     return new BsonBinary(from.type, (byte[])from.data.clone());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonBinary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */