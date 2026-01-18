/*     */ package org.bson.codecs.pojo;
/*     */ 
/*     */ import org.bson.BsonType;
/*     */ import org.bson.codecs.Codec;
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
/*     */ public final class PropertyModel<T>
/*     */ {
/*     */   private final String name;
/*     */   private final String readName;
/*     */   private final String writeName;
/*     */   private final TypeData<T> typeData;
/*     */   private final Codec<T> codec;
/*     */   private final PropertySerialization<T> propertySerialization;
/*     */   private final Boolean useDiscriminator;
/*     */   private final PropertyAccessor<T> propertyAccessor;
/*     */   private final String error;
/*     */   private volatile Codec<T> cachedCodec;
/*     */   private final BsonType bsonRepresentation;
/*     */   
/*     */   PropertyModel(String name, String readName, String writeName, TypeData<T> typeData, Codec<T> codec, PropertySerialization<T> propertySerialization, Boolean useDiscriminator, PropertyAccessor<T> propertyAccessor, String error, BsonType bsonRepresentation) {
/*  44 */     this.name = name;
/*  45 */     this.readName = readName;
/*  46 */     this.writeName = writeName;
/*  47 */     this.typeData = typeData;
/*  48 */     this.codec = codec;
/*  49 */     this.cachedCodec = codec;
/*  50 */     this.propertySerialization = propertySerialization;
/*  51 */     this.useDiscriminator = useDiscriminator;
/*  52 */     this.propertyAccessor = propertyAccessor;
/*  53 */     this.error = error;
/*  54 */     this.bsonRepresentation = bsonRepresentation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> PropertyModelBuilder<T> builder() {
/*  63 */     return new PropertyModelBuilder<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  70 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWriteName() {
/*  77 */     return this.writeName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReadName() {
/*  84 */     return this.readName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWritable() {
/*  93 */     return (this.writeName != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReadable() {
/* 102 */     return (this.readName != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeData<T> getTypeData() {
/* 109 */     return this.typeData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Codec<T> getCodec() {
/* 116 */     return this.codec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonType getBsonRepresentation() {
/* 125 */     return this.bsonRepresentation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldSerialize(T value) {
/* 135 */     return this.propertySerialization.shouldSerialize(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyAccessor<T> getPropertyAccessor() {
/* 142 */     return this.propertyAccessor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean useDiscriminator() {
/* 149 */     return this.useDiscriminator;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 154 */     return "PropertyModel{propertyName='" + this.name + "', readName='" + this.readName + "', writeName='" + this.writeName + "', typeData=" + this.typeData + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 164 */     if (this == o) {
/* 165 */       return true;
/*     */     }
/* 167 */     if (o == null || getClass() != o.getClass()) {
/* 168 */       return false;
/*     */     }
/*     */     
/* 171 */     PropertyModel<?> that = (PropertyModel)o;
/*     */     
/* 173 */     if ((getName() != null) ? !getName().equals(that.getName()) : (that.getName() != null)) {
/* 174 */       return false;
/*     */     }
/* 176 */     if ((getReadName() != null) ? !getReadName().equals(that.getReadName()) : (that.getReadName() != null)) {
/* 177 */       return false;
/*     */     }
/* 179 */     if ((getWriteName() != null) ? !getWriteName().equals(that.getWriteName()) : (that.getWriteName() != null)) {
/* 180 */       return false;
/*     */     }
/* 182 */     if ((getTypeData() != null) ? !getTypeData().equals(that.getTypeData()) : (that.getTypeData() != null)) {
/* 183 */       return false;
/*     */     }
/* 185 */     if ((getCodec() != null) ? !getCodec().equals(that.getCodec()) : (that.getCodec() != null)) {
/* 186 */       return false;
/*     */     }
/* 188 */     if ((getPropertySerialization() != null) ? !getPropertySerialization().equals(that.getPropertySerialization()) : (that
/* 189 */       .getPropertySerialization() != null)) {
/* 190 */       return false;
/*     */     }
/* 192 */     if ((this.useDiscriminator != null) ? !this.useDiscriminator.equals(that.useDiscriminator) : (that.useDiscriminator != null)) {
/* 193 */       return false;
/*     */     }
/* 195 */     if ((getPropertyAccessor() != null) ? !getPropertyAccessor().equals(that.getPropertyAccessor()) : (that
/* 196 */       .getPropertyAccessor() != null)) {
/* 197 */       return false;
/*     */     }
/*     */     
/* 200 */     if ((getError() != null) ? !getError().equals(that.getError()) : (that.getError() != null)) {
/* 201 */       return false;
/*     */     }
/*     */     
/* 204 */     if ((getCachedCodec() != null) ? !getCachedCodec().equals(that.getCachedCodec()) : (that.getCachedCodec() != null)) {
/* 205 */       return false;
/*     */     }
/*     */     
/* 208 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 213 */     int result = (getName() != null) ? getName().hashCode() : 0;
/* 214 */     result = 31 * result + ((getReadName() != null) ? getReadName().hashCode() : 0);
/* 215 */     result = 31 * result + ((getWriteName() != null) ? getWriteName().hashCode() : 0);
/* 216 */     result = 31 * result + ((getTypeData() != null) ? getTypeData().hashCode() : 0);
/* 217 */     result = 31 * result + ((getCodec() != null) ? getCodec().hashCode() : 0);
/* 218 */     result = 31 * result + ((getPropertySerialization() != null) ? getPropertySerialization().hashCode() : 0);
/* 219 */     result = 31 * result + ((this.useDiscriminator != null) ? this.useDiscriminator.hashCode() : 0);
/* 220 */     result = 31 * result + ((getPropertyAccessor() != null) ? getPropertyAccessor().hashCode() : 0);
/* 221 */     result = 31 * result + ((getError() != null) ? getError().hashCode() : 0);
/* 222 */     result = 31 * result + ((getCachedCodec() != null) ? getCachedCodec().hashCode() : 0);
/* 223 */     return result;
/*     */   }
/*     */   
/*     */   boolean hasError() {
/* 227 */     return (this.error != null);
/*     */   }
/*     */   
/*     */   String getError() {
/* 231 */     return this.error;
/*     */   }
/*     */   
/*     */   PropertySerialization<T> getPropertySerialization() {
/* 235 */     return this.propertySerialization;
/*     */   }
/*     */   
/*     */   void cachedCodec(Codec<T> codec) {
/* 239 */     this.cachedCodec = codec;
/*     */   }
/*     */   
/*     */   Codec<T> getCachedCodec() {
/* 243 */     return this.cachedCodec;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\PropertyModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */