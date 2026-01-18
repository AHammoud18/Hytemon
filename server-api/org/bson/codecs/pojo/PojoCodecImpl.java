/*     */ package org.bson.codecs.pojo;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bson.BsonInvalidOperationException;
/*     */ import org.bson.BsonReader;
/*     */ import org.bson.BsonReaderMark;
/*     */ import org.bson.BsonType;
/*     */ import org.bson.BsonWriter;
/*     */ import org.bson.codecs.Codec;
/*     */ import org.bson.codecs.Decoder;
/*     */ import org.bson.codecs.DecoderContext;
/*     */ import org.bson.codecs.Encoder;
/*     */ import org.bson.codecs.EncoderContext;
/*     */ import org.bson.codecs.configuration.CodecConfigurationException;
/*     */ import org.bson.codecs.configuration.CodecRegistry;
/*     */ import org.bson.diagnostics.Logger;
/*     */ import org.bson.diagnostics.Loggers;
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
/*     */ final class PojoCodecImpl<T>
/*     */   extends PojoCodec<T>
/*     */ {
/*  39 */   private static final Logger LOGGER = Loggers.getLogger("PojoCodec");
/*     */   
/*     */   private final ClassModel<T> classModel;
/*     */   private final CodecRegistry registry;
/*     */   private final PropertyCodecRegistry propertyCodecRegistry;
/*     */   private final DiscriminatorLookup discriminatorLookup;
/*     */   private final boolean specialized;
/*     */   
/*     */   PojoCodecImpl(ClassModel<T> classModel, CodecRegistry codecRegistry, List<PropertyCodecProvider> propertyCodecProviders, DiscriminatorLookup discriminatorLookup) {
/*  48 */     this.classModel = classModel;
/*  49 */     this.registry = codecRegistry;
/*  50 */     this.discriminatorLookup = discriminatorLookup;
/*  51 */     this.propertyCodecRegistry = new PropertyCodecRegistryImpl(this, this.registry, propertyCodecProviders);
/*  52 */     this.specialized = shouldSpecialize(classModel);
/*  53 */     specialize();
/*     */   }
/*     */ 
/*     */   
/*     */   PojoCodecImpl(ClassModel<T> classModel, CodecRegistry codecRegistry, PropertyCodecRegistry propertyCodecRegistry, DiscriminatorLookup discriminatorLookup, boolean specialized) {
/*  58 */     this.classModel = classModel;
/*  59 */     this.registry = codecRegistry;
/*  60 */     this.discriminatorLookup = discriminatorLookup;
/*  61 */     this.propertyCodecRegistry = propertyCodecRegistry;
/*  62 */     this.specialized = specialized;
/*  63 */     specialize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(BsonWriter writer, T value, EncoderContext encoderContext) {
/*  69 */     if (!this.specialized) {
/*  70 */       throw new CodecConfigurationException(String.format("%s contains generic types that have not been specialised.%nTop level classes with generic types are not supported by the PojoCodec.", new Object[] { this.classModel
/*  71 */               .getName() }));
/*     */     }
/*     */     
/*  74 */     if (areEquivalentTypes(value.getClass(), this.classModel.getType())) {
/*  75 */       writer.writeStartDocument();
/*     */       
/*  77 */       encodeIdProperty(writer, value, encoderContext, this.classModel.getIdPropertyModelHolder());
/*     */       
/*  79 */       if (this.classModel.useDiscriminator()) {
/*  80 */         writer.writeString(this.classModel.getDiscriminatorKey(), this.classModel.getDiscriminator());
/*     */       }
/*     */       
/*  83 */       for (PropertyModel<?> propertyModel : this.classModel.getPropertyModels()) {
/*  84 */         if (propertyModel.equals(this.classModel.getIdPropertyModel())) {
/*     */           continue;
/*     */         }
/*  87 */         encodeProperty(writer, value, encoderContext, propertyModel);
/*     */       } 
/*  89 */       writer.writeEndDocument();
/*     */     } else {
/*  91 */       this.registry.get(value.getClass()).encode(writer, value, encoderContext);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public T decode(BsonReader reader, DecoderContext decoderContext) {
/*  97 */     if (decoderContext.hasCheckedDiscriminator()) {
/*  98 */       if (!this.specialized) {
/*  99 */         throw new CodecConfigurationException(String.format("%s contains generic types that have not been specialised.%nTop level classes with generic types are not supported by the PojoCodec.", new Object[] { this.classModel
/* 100 */                 .getName() }));
/*     */       }
/* 102 */       InstanceCreator<T> instanceCreator = this.classModel.getInstanceCreator();
/* 103 */       decodeProperties(reader, decoderContext, instanceCreator);
/* 104 */       return instanceCreator.getInstance();
/*     */     } 
/* 106 */     return (T)getCodecFromDocument(reader, this.classModel.useDiscriminator(), this.classModel.getDiscriminatorKey(), this.registry, this.discriminatorLookup, this)
/* 107 */       .decode(reader, DecoderContext.builder().checkedDiscriminator(true).build());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<T> getEncoderClass() {
/* 113 */     return this.classModel.getType();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 118 */     return String.format("PojoCodec<%s>", new Object[] { this.classModel });
/*     */   }
/*     */   
/*     */   ClassModel<T> getClassModel() {
/* 122 */     return this.classModel;
/*     */   }
/*     */ 
/*     */   
/*     */   private <S> void encodeIdProperty(BsonWriter writer, T instance, EncoderContext encoderContext, IdPropertyModelHolder<S> propertyModelHolder) {
/* 127 */     if (propertyModelHolder.getPropertyModel() != null) {
/* 128 */       if (propertyModelHolder.getIdGenerator() == null) {
/* 129 */         encodeProperty(writer, instance, encoderContext, propertyModelHolder.getPropertyModel());
/*     */       } else {
/* 131 */         S id = propertyModelHolder.getPropertyModel().getPropertyAccessor().get(instance);
/* 132 */         if (id == null && encoderContext.isEncodingCollectibleDocument()) {
/* 133 */           id = propertyModelHolder.getIdGenerator().generate();
/*     */           try {
/* 135 */             propertyModelHolder.getPropertyModel().getPropertyAccessor().set(instance, id);
/* 136 */           } catch (Exception exception) {}
/*     */         } 
/*     */ 
/*     */         
/* 140 */         encodeValue(writer, encoderContext, propertyModelHolder.getPropertyModel(), id);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private <S> void encodeProperty(BsonWriter writer, T instance, EncoderContext encoderContext, PropertyModel<S> propertyModel) {
/* 148 */     if (propertyModel != null && propertyModel.isReadable()) {
/* 149 */       S propertyValue = propertyModel.getPropertyAccessor().get(instance);
/* 150 */       encodeValue(writer, encoderContext, propertyModel, propertyValue);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private <S> void encodeValue(BsonWriter writer, EncoderContext encoderContext, PropertyModel<S> propertyModel, S propertyValue) {
/* 156 */     if (propertyModel.shouldSerialize(propertyValue)) {
/* 157 */       writer.writeName(propertyModel.getReadName());
/* 158 */       if (propertyValue == null) {
/* 159 */         writer.writeNull();
/*     */       } else {
/*     */         try {
/* 162 */           encoderContext.encodeWithChildContext((Encoder)propertyModel.getCachedCodec(), writer, propertyValue);
/* 163 */         } catch (CodecConfigurationException e) {
/* 164 */           throw new CodecConfigurationException(String.format("Failed to encode '%s'. Encoding '%s' errored with: %s", new Object[] { this.classModel
/* 165 */                   .getName(), propertyModel.getReadName(), e.getMessage() }), e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void decodeProperties(BsonReader reader, DecoderContext decoderContext, InstanceCreator<T> instanceCreator) {
/* 173 */     reader.readStartDocument();
/* 174 */     while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/* 175 */       String name = reader.readName();
/* 176 */       if (this.classModel.useDiscriminator() && this.classModel.getDiscriminatorKey().equals(name)) {
/* 177 */         reader.readString(); continue;
/*     */       } 
/* 179 */       decodePropertyModel(reader, decoderContext, instanceCreator, name, getPropertyModelByWriteName(this.classModel, name));
/*     */     } 
/*     */     
/* 182 */     reader.readEndDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <S> void decodePropertyModel(BsonReader reader, DecoderContext decoderContext, InstanceCreator<T> instanceCreator, String name, PropertyModel<S> propertyModel) {
/* 189 */     if (propertyModel != null) {
/*     */       try {
/* 191 */         S value = null;
/* 192 */         if (reader.getCurrentBsonType() == BsonType.NULL) {
/* 193 */           reader.readNull();
/*     */         } else {
/* 195 */           Codec<S> codec = propertyModel.getCachedCodec();
/* 196 */           if (codec == null) {
/* 197 */             throw new CodecConfigurationException(String.format("Missing codec in '%s' for '%s'", new Object[] { this.classModel
/* 198 */                     .getName(), propertyModel.getName() }));
/*     */           }
/* 200 */           value = (S)decoderContext.decodeWithChildContext((Decoder)codec, reader);
/*     */         } 
/* 202 */         if (propertyModel.isWritable()) {
/* 203 */           instanceCreator.set(value, propertyModel);
/*     */         }
/* 205 */       } catch (BsonInvalidOperationException|CodecConfigurationException e) {
/* 206 */         throw new CodecConfigurationException(String.format("Failed to decode '%s'. Decoding '%s' errored with: %s", new Object[] { this.classModel
/* 207 */                 .getName(), name, e.getMessage() }), e);
/*     */       } 
/*     */     } else {
/* 210 */       if (LOGGER.isTraceEnabled()) {
/* 211 */         LOGGER.trace(String.format("Found property not present in the ClassModel: %s", new Object[] { name }));
/*     */       }
/* 213 */       reader.skipValue();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void specialize() {
/* 218 */     if (this.specialized) {
/* 219 */       this.classModel.getPropertyModels().forEach(this::cachePropertyModelCodec);
/*     */     }
/*     */   }
/*     */   
/*     */   private <S> void cachePropertyModelCodec(PropertyModel<S> propertyModel) {
/* 224 */     if (propertyModel.getCachedCodec() == null) {
/*     */       
/* 226 */       Codec<S> codec = (propertyModel.getCodec() != null) ? propertyModel.getCodec() : new LazyPropertyModelCodec<>(propertyModel, this.registry, this.propertyCodecRegistry, this.discriminatorLookup);
/* 227 */       propertyModel.cachedCodec(codec);
/*     */     } 
/*     */   }
/*     */   
/*     */   private <S, V> boolean areEquivalentTypes(Class<S> t1, Class<V> t2) {
/* 232 */     if (t1.equals(t2))
/* 233 */       return true; 
/* 234 */     if (Collection.class.isAssignableFrom(t1) && Collection.class.isAssignableFrom(t2))
/* 235 */       return true; 
/* 236 */     if (Map.class.isAssignableFrom(t1) && Map.class.isAssignableFrom(t2)) {
/* 237 */       return true;
/*     */     }
/* 239 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Codec<T> getCodecFromDocument(BsonReader reader, boolean useDiscriminator, String discriminatorKey, CodecRegistry registry, DiscriminatorLookup discriminatorLookup, Codec<T> defaultCodec) {
/* 246 */     Codec<T> codec = defaultCodec;
/* 247 */     if (useDiscriminator) {
/* 248 */       BsonReaderMark mark = reader.getMark();
/* 249 */       reader.readStartDocument();
/* 250 */       boolean discriminatorKeyFound = false;
/* 251 */       while (!discriminatorKeyFound && reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/* 252 */         String name = reader.readName();
/* 253 */         if (discriminatorKey.equals(name)) {
/* 254 */           discriminatorKeyFound = true;
/*     */           try {
/* 256 */             Class<?> discriminatorClass = discriminatorLookup.lookup(reader.readString());
/* 257 */             if (!codec.getEncoderClass().equals(discriminatorClass)) {
/* 258 */               codec = registry.get(discriminatorClass);
/*     */             }
/* 260 */           } catch (Exception e) {
/* 261 */             throw new CodecConfigurationException(String.format("Failed to decode '%s'. Decoding errored with: %s", new Object[] { this.classModel
/* 262 */                     .getName(), e.getMessage() }), e);
/*     */           }  continue;
/*     */         } 
/* 265 */         reader.skipValue();
/*     */       } 
/*     */       
/* 268 */       mark.reset();
/*     */     } 
/* 270 */     return codec;
/*     */   }
/*     */   
/*     */   private PropertyModel<?> getPropertyModelByWriteName(ClassModel<T> classModel, String readName) {
/* 274 */     for (PropertyModel<?> propertyModel : classModel.getPropertyModels()) {
/* 275 */       if (propertyModel.isWritable() && propertyModel.getWriteName().equals(readName)) {
/* 276 */         return propertyModel;
/*     */       }
/*     */     } 
/* 279 */     return null;
/*     */   }
/*     */   
/*     */   private static <T> boolean shouldSpecialize(ClassModel<T> classModel) {
/* 283 */     if (!classModel.hasTypeParameters()) {
/* 284 */       return true;
/*     */     }
/*     */     
/* 287 */     for (Map.Entry<String, TypeParameterMap> entry : classModel.getPropertyNameToTypeParameterMap().entrySet()) {
/* 288 */       TypeParameterMap typeParameterMap = entry.getValue();
/* 289 */       PropertyModel<?> propertyModel = classModel.getPropertyModel(entry.getKey());
/* 290 */       if (typeParameterMap.hasTypeParameters() && (propertyModel == null || propertyModel.getCodec() == null)) {
/* 291 */         return false;
/*     */       }
/*     */     } 
/* 294 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   DiscriminatorLookup getDiscriminatorLookup() {
/* 299 */     return this.discriminatorLookup;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\PojoCodecImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */