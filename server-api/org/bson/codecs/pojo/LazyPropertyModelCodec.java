/*     */ package org.bson.codecs.pojo;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bson.BsonReader;
/*     */ import org.bson.BsonType;
/*     */ import org.bson.BsonWriter;
/*     */ import org.bson.codecs.Codec;
/*     */ import org.bson.codecs.DecoderContext;
/*     */ import org.bson.codecs.EncoderContext;
/*     */ import org.bson.codecs.RepresentationConfigurable;
/*     */ import org.bson.codecs.configuration.CodecConfigurationException;
/*     */ import org.bson.codecs.configuration.CodecRegistry;
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
/*     */ class LazyPropertyModelCodec<T>
/*     */   implements Codec<T>
/*     */ {
/*     */   private final PropertyModel<T> propertyModel;
/*     */   private final CodecRegistry registry;
/*     */   private final PropertyCodecRegistry propertyCodecRegistry;
/*     */   private final DiscriminatorLookup discriminatorLookup;
/*     */   private Codec<T> codec;
/*     */   
/*     */   LazyPropertyModelCodec(PropertyModel<T> propertyModel, CodecRegistry registry, PropertyCodecRegistry propertyCodecRegistry, DiscriminatorLookup discriminatorLookup) {
/*  43 */     this.propertyModel = propertyModel;
/*  44 */     this.registry = registry;
/*  45 */     this.propertyCodecRegistry = propertyCodecRegistry;
/*  46 */     this.discriminatorLookup = discriminatorLookup;
/*     */   }
/*     */ 
/*     */   
/*     */   public T decode(BsonReader reader, DecoderContext decoderContext) {
/*  51 */     return (T)getPropertyModelCodec().decode(reader, decoderContext);
/*     */   }
/*     */ 
/*     */   
/*     */   public void encode(BsonWriter writer, T value, EncoderContext encoderContext) {
/*  56 */     getPropertyModelCodec().encode(writer, value, encoderContext);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<T> getEncoderClass() {
/*  61 */     return this.propertyModel.getTypeData().getType();
/*     */   }
/*     */   
/*     */   private synchronized Codec<T> getPropertyModelCodec() {
/*  65 */     if (this.codec == null) {
/*  66 */       Codec<T> localCodec = getCodecFromPropertyRegistry(this.propertyModel);
/*  67 */       if (localCodec instanceof PojoCodec) {
/*  68 */         PojoCodec<T> pojoCodec = (PojoCodec<T>)localCodec;
/*  69 */         ClassModel<T> specialized = getSpecializedClassModel(pojoCodec.getClassModel(), this.propertyModel);
/*  70 */         localCodec = new PojoCodecImpl<>(specialized, this.registry, this.propertyCodecRegistry, pojoCodec.getDiscriminatorLookup(), true);
/*     */       } 
/*  72 */       this.codec = localCodec;
/*     */     } 
/*  74 */     return this.codec;
/*     */   }
/*     */ 
/*     */   
/*     */   private Codec<T> getCodecFromPropertyRegistry(PropertyModel<T> propertyModel) {
/*     */     Codec<T> localCodec;
/*     */     try {
/*  81 */       localCodec = this.propertyCodecRegistry.get(propertyModel.getTypeData());
/*  82 */     } catch (CodecConfigurationException e) {
/*  83 */       return new LazyMissingCodec<>(propertyModel.getTypeData().getType(), e);
/*     */     } 
/*  85 */     if (localCodec == null)
/*     */     {
/*  87 */       localCodec = new LazyMissingCodec<>(propertyModel.getTypeData().getType(), new CodecConfigurationException("Unexpected missing codec for: " + propertyModel.getName()));
/*     */     }
/*  89 */     BsonType representation = propertyModel.getBsonRepresentation();
/*  90 */     if (representation != null) {
/*  91 */       if (localCodec instanceof RepresentationConfigurable) {
/*  92 */         return ((RepresentationConfigurable)localCodec).withRepresentation(representation);
/*     */       }
/*  94 */       throw new CodecConfigurationException("Codec must implement RepresentationConfigurable to support BsonRepresentation");
/*     */     } 
/*  96 */     return localCodec;
/*     */   }
/*     */ 
/*     */   
/*     */   private <V> ClassModel<T> getSpecializedClassModel(ClassModel<T> clazzModel, PropertyModel<V> propertyModel) {
/* 101 */     boolean useDiscriminator = (propertyModel.useDiscriminator() == null) ? clazzModel.useDiscriminator() : propertyModel.useDiscriminator().booleanValue();
/* 102 */     boolean validDiscriminator = (clazzModel.getDiscriminatorKey() != null && clazzModel.getDiscriminator() != null);
/* 103 */     boolean changeTheDiscriminator = (useDiscriminator != clazzModel.useDiscriminator() && validDiscriminator);
/*     */     
/* 105 */     if (propertyModel.getTypeData().getTypeParameters().isEmpty() && !changeTheDiscriminator) {
/* 106 */       return clazzModel;
/*     */     }
/*     */     
/* 109 */     ArrayList<PropertyModel<?>> concretePropertyModels = new ArrayList<>(clazzModel.getPropertyModels());
/* 110 */     PropertyModel<?> concreteIdProperty = clazzModel.getIdPropertyModel();
/*     */     
/* 112 */     List<TypeData<?>> propertyTypeParameters = propertyModel.getTypeData().getTypeParameters();
/* 113 */     for (int i = 0; i < concretePropertyModels.size(); i++) {
/* 114 */       PropertyModel<?> model = concretePropertyModels.get(i);
/* 115 */       String propertyName = model.getName();
/* 116 */       TypeParameterMap typeParameterMap = clazzModel.getPropertyNameToTypeParameterMap().get(propertyName);
/* 117 */       if (typeParameterMap.hasTypeParameters()) {
/* 118 */         PropertyModel<?> concretePropertyModel = getSpecializedPropertyModel(model, propertyTypeParameters, typeParameterMap);
/* 119 */         concretePropertyModels.set(i, concretePropertyModel);
/* 120 */         if (concreteIdProperty != null && concreteIdProperty.getName().equals(propertyName)) {
/* 121 */           concreteIdProperty = concretePropertyModel;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     boolean discriminatorEnabled = changeTheDiscriminator ? propertyModel.useDiscriminator().booleanValue() : clazzModel.useDiscriminator();
/* 127 */     return new ClassModel<>(clazzModel.getType(), clazzModel.getPropertyNameToTypeParameterMap(), clazzModel
/* 128 */         .getInstanceCreatorFactory(), Boolean.valueOf(discriminatorEnabled), clazzModel.getDiscriminatorKey(), clazzModel
/* 129 */         .getDiscriminator(), IdPropertyModelHolder.create(clazzModel, concreteIdProperty), concretePropertyModels);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private <V> PropertyModel<V> getSpecializedPropertyModel(PropertyModel<V> propertyModel, List<TypeData<?>> propertyTypeParameters, TypeParameterMap typeParameterMap) {
/* 135 */     TypeData<V> specializedPropertyType = PojoSpecializationHelper.specializeTypeData(propertyModel.getTypeData(), propertyTypeParameters, typeParameterMap);
/* 136 */     if (propertyModel.getTypeData().equals(specializedPropertyType)) {
/* 137 */       return propertyModel;
/*     */     }
/*     */     
/* 140 */     return new PropertyModel<>(propertyModel.getName(), propertyModel.getReadName(), propertyModel.getWriteName(), specializedPropertyType, null, propertyModel
/* 141 */         .getPropertySerialization(), propertyModel.useDiscriminator(), propertyModel
/* 142 */         .getPropertyAccessor(), propertyModel.getError(), propertyModel.getBsonRepresentation());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\LazyPropertyModelCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */