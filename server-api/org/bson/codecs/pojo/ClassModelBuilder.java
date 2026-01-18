/*     */ package org.bson.codecs.pojo;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bson.assertions.Assertions;
/*     */ import org.bson.codecs.configuration.CodecConfigurationException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClassModelBuilder<T>
/*     */ {
/*     */   static final String ID_PROPERTY_NAME = "_id";
/*  47 */   private final List<PropertyModelBuilder<?>> propertyModelBuilders = new ArrayList<>();
/*     */   private IdGenerator<?> idGenerator;
/*     */   private InstanceCreatorFactory<T> instanceCreatorFactory;
/*     */   private Class<T> type;
/*  51 */   private Map<String, TypeParameterMap> propertyNameToTypeParameterMap = Collections.emptyMap();
/*  52 */   private List<Convention> conventions = Conventions.DEFAULT_CONVENTIONS;
/*  53 */   private List<Annotation> annotations = Collections.emptyList();
/*     */   private boolean discriminatorEnabled;
/*     */   private String discriminator;
/*     */   private String discriminatorKey;
/*     */   private String idPropertyName;
/*     */   
/*     */   ClassModelBuilder(Class<T> type) {
/*  60 */     PojoBuilderHelper.configureClassModelBuilder(this, (Class<T>)Assertions.notNull("type", type));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassModelBuilder<T> idGenerator(IdGenerator<?> idGenerator) {
/*  71 */     this.idGenerator = idGenerator;
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IdGenerator<?> getIdGenerator() {
/*  80 */     return this.idGenerator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassModelBuilder<T> instanceCreatorFactory(InstanceCreatorFactory<T> instanceCreatorFactory) {
/*  90 */     this.instanceCreatorFactory = (InstanceCreatorFactory<T>)Assertions.notNull("instanceCreatorFactory", instanceCreatorFactory);
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InstanceCreatorFactory<T> getInstanceCreatorFactory() {
/*  98 */     return this.instanceCreatorFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassModelBuilder<T> type(Class<T> type) {
/* 108 */     this.type = (Class<T>)Assertions.notNull("type", type);
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<T> getType() {
/* 116 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassModelBuilder<T> conventions(List<Convention> conventions) {
/* 126 */     this.conventions = (List<Convention>)Assertions.notNull("conventions", conventions);
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Convention> getConventions() {
/* 134 */     return this.conventions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassModelBuilder<T> annotations(List<Annotation> annotations) {
/* 144 */     this.annotations = (List<Annotation>)Assertions.notNull("annotations", annotations);
/* 145 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Annotation> getAnnotations() {
/* 152 */     return this.annotations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassModelBuilder<T> discriminator(String discriminator) {
/* 162 */     this.discriminator = discriminator;
/* 163 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDiscriminator() {
/* 170 */     return this.discriminator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassModelBuilder<T> discriminatorKey(String discriminatorKey) {
/* 180 */     this.discriminatorKey = discriminatorKey;
/* 181 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDiscriminatorKey() {
/* 188 */     return this.discriminatorKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassModelBuilder<T> enableDiscriminator(boolean discriminatorEnabled) {
/* 198 */     this.discriminatorEnabled = discriminatorEnabled;
/* 199 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean useDiscriminator() {
/* 206 */     return Boolean.valueOf(this.discriminatorEnabled);
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
/*     */   public ClassModelBuilder<T> idPropertyName(String idPropertyName) {
/* 218 */     this.idPropertyName = idPropertyName;
/* 219 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIdPropertyName() {
/* 226 */     return this.idPropertyName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeProperty(String propertyName) {
/* 236 */     return this.propertyModelBuilders.remove(getProperty((String)Assertions.notNull("propertyName", propertyName)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyModelBuilder<?> getProperty(String propertyName) {
/* 246 */     Assertions.notNull("propertyName", propertyName);
/* 247 */     for (PropertyModelBuilder<?> propertyModelBuilder : this.propertyModelBuilders) {
/* 248 */       if (propertyModelBuilder.getName().equals(propertyName)) {
/* 249 */         return propertyModelBuilder;
/*     */       }
/*     */     } 
/* 252 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PropertyModelBuilder<?>> getPropertyModelBuilders() {
/* 259 */     return Collections.unmodifiableList(this.propertyModelBuilders);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassModel<T> build() {
/* 268 */     List<PropertyModel<?>> propertyModels = new ArrayList<>();
/* 269 */     PropertyModel<?> idPropertyModel = null;
/*     */     
/* 271 */     PojoBuilderHelper.stateNotNull("type", this.type);
/* 272 */     for (Convention convention : this.conventions) {
/* 273 */       convention.apply(this);
/*     */     }
/*     */     
/* 276 */     PojoBuilderHelper.stateNotNull("instanceCreatorFactory", this.instanceCreatorFactory);
/* 277 */     if (this.discriminatorEnabled) {
/* 278 */       PojoBuilderHelper.stateNotNull("discriminatorKey", this.discriminatorKey);
/* 279 */       PojoBuilderHelper.stateNotNull("discriminator", this.discriminator);
/*     */     } 
/*     */     
/* 282 */     for (PropertyModelBuilder<?> propertyModelBuilder : this.propertyModelBuilders) {
/* 283 */       boolean isIdProperty = propertyModelBuilder.getName().equals(this.idPropertyName);
/* 284 */       if (isIdProperty) {
/* 285 */         propertyModelBuilder.readName("_id").writeName("_id");
/*     */       }
/*     */       
/* 288 */       PropertyModel<?> model = propertyModelBuilder.build();
/* 289 */       propertyModels.add(model);
/* 290 */       if (isIdProperty) {
/* 291 */         idPropertyModel = model;
/*     */       }
/*     */     } 
/* 294 */     validatePropertyModels(this.type.getSimpleName(), propertyModels);
/* 295 */     return new ClassModel<>(this.type, this.propertyNameToTypeParameterMap, this.instanceCreatorFactory, Boolean.valueOf(this.discriminatorEnabled), this.discriminatorKey, this.discriminator, 
/* 296 */         IdPropertyModelHolder.create(this.type, idPropertyModel, this.idGenerator), Collections.unmodifiableList(propertyModels));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 301 */     return String.format("ClassModelBuilder{type=%s}", new Object[] { this.type });
/*     */   }
/*     */   
/*     */   Map<String, TypeParameterMap> getPropertyNameToTypeParameterMap() {
/* 305 */     return this.propertyNameToTypeParameterMap;
/*     */   }
/*     */   
/*     */   ClassModelBuilder<T> propertyNameToTypeParameterMap(Map<String, TypeParameterMap> propertyNameToTypeParameterMap) {
/* 309 */     this.propertyNameToTypeParameterMap = Collections.unmodifiableMap(new HashMap<>(propertyNameToTypeParameterMap));
/* 310 */     return this;
/*     */   }
/*     */   
/*     */   ClassModelBuilder<T> addProperty(PropertyModelBuilder<?> propertyModelBuilder) {
/* 314 */     this.propertyModelBuilders.add((PropertyModelBuilder)Assertions.notNull("propertyModelBuilder", propertyModelBuilder));
/* 315 */     return this;
/*     */   }
/*     */   
/*     */   private void validatePropertyModels(String declaringClass, List<PropertyModel<?>> propertyModels) {
/* 319 */     Map<String, Integer> propertyNameMap = new HashMap<>();
/* 320 */     Map<String, Integer> propertyReadNameMap = new HashMap<>();
/* 321 */     Map<String, Integer> propertyWriteNameMap = new HashMap<>();
/*     */     
/* 323 */     for (PropertyModel<?> propertyModel : propertyModels) {
/* 324 */       if (propertyModel.hasError()) {
/* 325 */         throw new CodecConfigurationException(propertyModel.getError());
/*     */       }
/* 327 */       checkForDuplicates("property", propertyModel.getName(), propertyNameMap, declaringClass);
/* 328 */       if (propertyModel.isReadable()) {
/* 329 */         checkForDuplicates("read property", propertyModel.getReadName(), propertyReadNameMap, declaringClass);
/*     */       }
/* 331 */       if (propertyModel.isWritable()) {
/* 332 */         checkForDuplicates("write property", propertyModel.getWriteName(), propertyWriteNameMap, declaringClass);
/*     */       }
/*     */     } 
/*     */     
/* 336 */     if (this.idPropertyName != null && !propertyNameMap.containsKey(this.idPropertyName)) {
/* 337 */       throw new CodecConfigurationException(String.format("Invalid id property, property named '%s' can not be found.", new Object[] { this.idPropertyName }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkForDuplicates(String propertyType, String propertyName, Map<String, Integer> propertyNameMap, String declaringClass) {
/* 343 */     if (propertyNameMap.containsKey(propertyName)) {
/* 344 */       throw new CodecConfigurationException(String.format("Duplicate %s named '%s' found in %s.", new Object[] { propertyType, propertyName, declaringClass }));
/*     */     }
/*     */     
/* 347 */     propertyNameMap.put(propertyName, Integer.valueOf(1));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\ClassModelBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */