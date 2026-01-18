/*     */ package org.bson.codecs.pojo;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.bson.BsonType;
/*     */ import org.bson.assertions.Assertions;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PropertyModelBuilder<T>
/*     */ {
/*     */   private String name;
/*     */   private String readName;
/*     */   private String writeName;
/*     */   private TypeData<T> typeData;
/*     */   private PropertySerialization<T> propertySerialization;
/*     */   private Codec<T> codec;
/*     */   private PropertyAccessor<T> propertyAccessor;
/*  46 */   private List<Annotation> readAnnotations = Collections.emptyList();
/*  47 */   private List<Annotation> writeAnnotations = Collections.emptyList();
/*     */ 
/*     */   
/*     */   private Boolean discriminatorEnabled;
/*     */ 
/*     */   
/*     */   private String error;
/*     */   
/*     */   private BsonType bsonRepresentation;
/*     */ 
/*     */   
/*     */   public String getName() {
/*  59 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReadName() {
/*  66 */     return this.readName;
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
/*     */   public PropertyModelBuilder<T> readName(String readName) {
/*  78 */     this.readName = readName;
/*  79 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWriteName() {
/*  86 */     return this.writeName;
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
/*     */   public PropertyModelBuilder<T> writeName(String writeName) {
/*  98 */     this.writeName = writeName;
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyModelBuilder<T> codec(Codec<T> codec) {
/* 109 */     this.codec = codec;
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Codec<T> getCodec() {
/* 117 */     return this.codec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyModelBuilder<T> propertySerialization(PropertySerialization<T> propertySerialization) {
/* 127 */     this.propertySerialization = (PropertySerialization<T>)Assertions.notNull("propertySerialization", propertySerialization);
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertySerialization<T> getPropertySerialization() {
/* 135 */     return this.propertySerialization;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Annotation> getReadAnnotations() {
/* 144 */     return this.readAnnotations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyModelBuilder<T> readAnnotations(List<Annotation> annotations) {
/* 154 */     this.readAnnotations = Collections.unmodifiableList((List<? extends Annotation>)Assertions.notNull("annotations", annotations));
/* 155 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Annotation> getWriteAnnotations() {
/* 164 */     return this.writeAnnotations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyModelBuilder<T> writeAnnotations(List<Annotation> writeAnnotations) {
/* 174 */     this.writeAnnotations = writeAnnotations;
/* 175 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWritable() {
/* 184 */     return (this.writeName != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReadable() {
/* 193 */     return (this.readName != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean isDiscriminatorEnabled() {
/* 200 */     return this.discriminatorEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyModelBuilder<T> discriminatorEnabled(boolean discriminatorEnabled) {
/* 210 */     this.discriminatorEnabled = Boolean.valueOf(discriminatorEnabled);
/* 211 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyAccessor<T> getPropertyAccessor() {
/* 220 */     return this.propertyAccessor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyModelBuilder<T> propertyAccessor(PropertyAccessor<T> propertyAccessor) {
/* 230 */     this.propertyAccessor = propertyAccessor;
/* 231 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonType getBsonRepresentation() {
/* 241 */     return this.bsonRepresentation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyModelBuilder<T> bsonRepresentation(BsonType bsonRepresentation) {
/* 252 */     this.bsonRepresentation = bsonRepresentation;
/* 253 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyModel<T> build() {
/* 263 */     if (!isReadable() && !isWritable()) {
/* 264 */       throw new IllegalStateException(String.format("Invalid PropertyModel '%s', neither readable or writable,", new Object[] { this.name }));
/*     */     }
/* 266 */     return new PropertyModel<>(
/* 267 */         PojoBuilderHelper.<String>stateNotNull("propertyName", this.name), this.readName, this.writeName, 
/*     */ 
/*     */         
/* 270 */         PojoBuilderHelper.<TypeData<T>>stateNotNull("typeData", this.typeData), this.codec, 
/*     */         
/* 272 */         PojoBuilderHelper.<PropertySerialization<T>>stateNotNull("propertySerialization", this.propertySerialization), this.discriminatorEnabled, 
/*     */         
/* 274 */         PojoBuilderHelper.<PropertyAccessor<T>>stateNotNull("propertyAccessor", this.propertyAccessor), this.error, this.bsonRepresentation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 281 */     return String.format("PropertyModelBuilder{propertyName=%s, typeData=%s}", new Object[] { this.name, this.typeData });
/*     */   }
/*     */   
/*     */   PropertyModelBuilder<T> propertyName(String propertyName) {
/* 285 */     this.name = (String)Assertions.notNull("propertyName", propertyName);
/* 286 */     return this;
/*     */   }
/*     */   
/*     */   TypeData<T> getTypeData() {
/* 290 */     return this.typeData;
/*     */   }
/*     */   
/*     */   PropertyModelBuilder<T> typeData(TypeData<T> typeData) {
/* 294 */     this.typeData = (TypeData<T>)Assertions.notNull("typeData", typeData);
/* 295 */     return this;
/*     */   }
/*     */   
/*     */   PropertyModelBuilder<T> setError(String error) {
/* 299 */     this.error = error;
/* 300 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\PropertyModelBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */