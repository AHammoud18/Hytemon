/*     */ package org.bson.codecs.pojo;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
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
/*     */ final class ConventionUseGettersAsSettersImpl
/*     */   implements Convention
/*     */ {
/*     */   public void apply(ClassModelBuilder<?> classModelBuilder) {
/*  30 */     for (PropertyModelBuilder<?> propertyModelBuilder : classModelBuilder.getPropertyModelBuilders()) {
/*  31 */       if (!(propertyModelBuilder.getPropertyAccessor() instanceof PropertyAccessorImpl)) {
/*  32 */         throw new CodecConfigurationException(String.format("The USE_GETTER_AS_SETTER_CONVENTION is not compatible with propertyModelBuilder instance that have custom implementations of org.bson.codecs.pojo.PropertyAccessor: %s", new Object[] { propertyModelBuilder
/*     */                 
/*  34 */                 .getPropertyAccessor().getClass().getName() }));
/*     */       }
/*  36 */       PropertyAccessorImpl<?> defaultAccessor = (PropertyAccessorImpl)propertyModelBuilder.getPropertyAccessor();
/*  37 */       PropertyMetadata<?> propertyMetaData = defaultAccessor.getPropertyMetadata();
/*  38 */       if (!propertyMetaData.isDeserializable() && propertyMetaData.isSerializable() && 
/*  39 */         isMapOrCollection(propertyMetaData.getTypeData().getType())) {
/*  40 */         setPropertyAccessor(propertyModelBuilder);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private <T> boolean isMapOrCollection(Class<T> clazz) {
/*  46 */     return (Collection.class.isAssignableFrom(clazz) || Map.class.isAssignableFrom(clazz));
/*     */   }
/*     */ 
/*     */   
/*     */   private <T> void setPropertyAccessor(PropertyModelBuilder<T> propertyModelBuilder) {
/*  51 */     propertyModelBuilder.propertyAccessor(new PrivatePropertyAccessor<>((PropertyAccessorImpl)propertyModelBuilder
/*  52 */           .getPropertyAccessor()));
/*     */   }
/*     */   
/*     */   private static final class PrivatePropertyAccessor<T>
/*     */     implements PropertyAccessor<T> {
/*     */     private final PropertyAccessorImpl<T> wrapped;
/*     */     
/*     */     private PrivatePropertyAccessor(PropertyAccessorImpl<T> wrapped) {
/*  60 */       this.wrapped = wrapped;
/*     */     }
/*     */ 
/*     */     
/*     */     public <S> T get(S instance) {
/*  65 */       return this.wrapped.get(instance);
/*     */     }
/*     */ 
/*     */     
/*     */     public <S> void set(S instance, T value) {
/*  70 */       if (value instanceof Collection) {
/*  71 */         mutateCollection(instance, (Collection)value);
/*  72 */       } else if (value instanceof Map) {
/*  73 */         mutateMap(instance, (Map)value);
/*     */       } else {
/*  75 */         throwCodecConfigurationException(String.format("Unexpected type: '%s'", new Object[] { value.getClass() }), null);
/*     */       } 
/*     */     }
/*     */     
/*     */     private <S> void mutateCollection(S instance, Collection<?> value) {
/*  80 */       T originalCollection = get(instance);
/*  81 */       Collection<?> collection = (Collection)originalCollection;
/*  82 */       if (collection == null) {
/*  83 */         throwCodecConfigurationException("The getter returned null.", null);
/*  84 */       } else if (!collection.isEmpty()) {
/*  85 */         throwCodecConfigurationException("The getter returned a non empty collection.", null);
/*     */       } else {
/*     */         try {
/*  88 */           collection.addAll(value);
/*  89 */         } catch (Exception e) {
/*  90 */           throwCodecConfigurationException("collection#addAll failed.", e);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private <S> void mutateMap(S instance, Map<?, ?> value) {
/*  96 */       T originalMap = get(instance);
/*  97 */       Map<?, ?> map = (Map<?, ?>)originalMap;
/*  98 */       if (map == null) {
/*  99 */         throwCodecConfigurationException("The getter returned null.", null);
/* 100 */       } else if (!map.isEmpty()) {
/* 101 */         throwCodecConfigurationException("The getter returned a non empty map.", null);
/*     */       } else {
/*     */         try {
/* 104 */           map.putAll(value);
/* 105 */         } catch (Exception e) {
/* 106 */           throwCodecConfigurationException("map#putAll failed.", e);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     private void throwCodecConfigurationException(String reason, Exception cause) {
/* 111 */       throw new CodecConfigurationException(String.format("Cannot use getter in '%s' to set '%s'. %s", new Object[] { this.wrapped
/* 112 */               .getPropertyMetadata().getDeclaringClassName(), this.wrapped.getPropertyMetadata().getName(), reason }), cause);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\ConventionUseGettersAsSettersImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */