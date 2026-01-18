/*     */ package org.bson.codecs.pojo;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bson.BsonType;
/*     */ import org.bson.codecs.configuration.CodecConfigurationException;
/*     */ import org.bson.codecs.pojo.annotations.BsonCreator;
/*     */ import org.bson.codecs.pojo.annotations.BsonDiscriminator;
/*     */ import org.bson.codecs.pojo.annotations.BsonProperty;
/*     */ import org.bson.codecs.pojo.annotations.BsonRepresentation;
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
/*     */ final class ConventionAnnotationImpl
/*     */   implements Convention
/*     */ {
/*     */   public void apply(ClassModelBuilder<?> classModelBuilder) {
/*  45 */     for (Annotation annotation : classModelBuilder.getAnnotations()) {
/*  46 */       processClassAnnotation(classModelBuilder, annotation);
/*     */     }
/*     */     
/*  49 */     for (PropertyModelBuilder<?> propertyModelBuilder : classModelBuilder.getPropertyModelBuilders()) {
/*  50 */       processPropertyAnnotations(classModelBuilder, propertyModelBuilder);
/*     */     }
/*     */     
/*  53 */     processCreatorAnnotation(classModelBuilder);
/*     */     
/*  55 */     cleanPropertyBuilders(classModelBuilder);
/*     */   }
/*     */   
/*     */   private void processClassAnnotation(ClassModelBuilder<?> classModelBuilder, Annotation annotation) {
/*  59 */     if (annotation instanceof BsonDiscriminator) {
/*  60 */       BsonDiscriminator discriminator = (BsonDiscriminator)annotation;
/*  61 */       String key = discriminator.key();
/*  62 */       if (!key.equals("")) {
/*  63 */         classModelBuilder.discriminatorKey(key);
/*     */       }
/*     */       
/*  66 */       String name = discriminator.value();
/*  67 */       if (!name.equals("")) {
/*  68 */         classModelBuilder.discriminator(name);
/*     */       }
/*  70 */       classModelBuilder.enableDiscriminator(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void processPropertyAnnotations(ClassModelBuilder<?> classModelBuilder, PropertyModelBuilder<?> propertyModelBuilder) {
/*  76 */     for (Annotation annotation : propertyModelBuilder.getReadAnnotations()) {
/*  77 */       if (annotation instanceof BsonProperty) {
/*  78 */         BsonProperty bsonProperty = (BsonProperty)annotation;
/*  79 */         if (!"".equals(bsonProperty.value())) {
/*  80 */           propertyModelBuilder.readName(bsonProperty.value());
/*     */         }
/*  82 */         propertyModelBuilder.discriminatorEnabled(bsonProperty.useDiscriminator());
/*  83 */         if (propertyModelBuilder.getName().equals(classModelBuilder.getIdPropertyName()))
/*  84 */           classModelBuilder.idPropertyName(null);  continue;
/*     */       } 
/*  86 */       if (annotation instanceof org.bson.codecs.pojo.annotations.BsonId) {
/*  87 */         classModelBuilder.idPropertyName(propertyModelBuilder.getName()); continue;
/*  88 */       }  if (annotation instanceof org.bson.codecs.pojo.annotations.BsonIgnore) {
/*  89 */         propertyModelBuilder.readName(null); continue;
/*  90 */       }  if (annotation instanceof BsonRepresentation) {
/*  91 */         BsonRepresentation bsonRepresentation = (BsonRepresentation)annotation;
/*  92 */         BsonType bsonRep = bsonRepresentation.value();
/*  93 */         propertyModelBuilder.bsonRepresentation(bsonRep);
/*     */       } 
/*     */     } 
/*     */     
/*  97 */     for (Annotation annotation : propertyModelBuilder.getWriteAnnotations()) {
/*  98 */       if (annotation instanceof BsonProperty) {
/*  99 */         BsonProperty bsonProperty = (BsonProperty)annotation;
/* 100 */         if (!"".equals(bsonProperty.value()))
/* 101 */           propertyModelBuilder.writeName(bsonProperty.value());  continue;
/*     */       } 
/* 103 */       if (annotation instanceof org.bson.codecs.pojo.annotations.BsonIgnore) {
/* 104 */         propertyModelBuilder.writeName(null);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private <T> void processCreatorAnnotation(ClassModelBuilder<T> classModelBuilder) {
/* 111 */     Class<T> clazz = classModelBuilder.getType();
/* 112 */     CreatorExecutable<T> creatorExecutable = null;
/* 113 */     for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
/* 114 */       if (Modifier.isPublic(constructor.getModifiers()) && !constructor.isSynthetic()) {
/* 115 */         for (Annotation annotation : constructor.getDeclaredAnnotations()) {
/* 116 */           if (annotation.annotationType().equals(BsonCreator.class)) {
/* 117 */             if (creatorExecutable != null) {
/* 118 */               throw new CodecConfigurationException("Found multiple constructors annotated with @BsonCreator");
/*     */             }
/* 120 */             creatorExecutable = new CreatorExecutable<>(clazz, (Constructor)constructor);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 126 */     Class<?> bsonCreatorClass = clazz;
/* 127 */     boolean foundStaticBsonCreatorMethod = false;
/* 128 */     while (bsonCreatorClass != null && !foundStaticBsonCreatorMethod) {
/* 129 */       for (Method method : bsonCreatorClass.getDeclaredMethods()) {
/* 130 */         if (Modifier.isStatic(method.getModifiers()) && !method.isSynthetic() && !method.isBridge()) {
/* 131 */           for (Annotation annotation : method.getDeclaredAnnotations()) {
/* 132 */             if (annotation.annotationType().equals(BsonCreator.class)) {
/* 133 */               if (creatorExecutable != null)
/* 134 */                 throw new CodecConfigurationException("Found multiple constructors / methods annotated with @BsonCreator"); 
/* 135 */               if (!bsonCreatorClass.isAssignableFrom(method.getReturnType()))
/* 136 */                 throw new CodecConfigurationException(
/* 137 */                     String.format("Invalid method annotated with @BsonCreator. Returns '%s', expected %s", new Object[] {
/* 138 */                         method.getReturnType(), bsonCreatorClass
/*     */                       })); 
/* 140 */               creatorExecutable = new CreatorExecutable<>(clazz, method);
/* 141 */               foundStaticBsonCreatorMethod = true;
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 147 */       bsonCreatorClass = bsonCreatorClass.getSuperclass();
/*     */     } 
/*     */     
/* 150 */     if (creatorExecutable != null) {
/* 151 */       List<BsonProperty> properties = creatorExecutable.getProperties();
/* 152 */       List<Class<?>> parameterTypes = creatorExecutable.getParameterTypes();
/* 153 */       List<Type> parameterGenericTypes = creatorExecutable.getParameterGenericTypes();
/*     */       
/* 155 */       if (properties.size() != parameterTypes.size()) {
/* 156 */         throw creatorExecutable.getError(clazz, "All parameters in the @BsonCreator method / constructor must be annotated with a @BsonProperty.");
/*     */       }
/*     */       
/* 159 */       for (int i = 0; i < properties.size(); i++) {
/* 160 */         boolean isIdProperty = (creatorExecutable.getIdPropertyIndex() != null && creatorExecutable.getIdPropertyIndex().equals(Integer.valueOf(i)));
/* 161 */         Class<?> parameterType = parameterTypes.get(i);
/* 162 */         Type genericType = parameterGenericTypes.get(i);
/* 163 */         PropertyModelBuilder<?> propertyModelBuilder = null;
/*     */         
/* 165 */         if (isIdProperty) {
/* 166 */           propertyModelBuilder = classModelBuilder.getProperty(classModelBuilder.getIdPropertyName());
/*     */         } else {
/* 168 */           BsonProperty bsonProperty = properties.get(i);
/*     */ 
/*     */           
/* 171 */           for (PropertyModelBuilder<?> builder : classModelBuilder.getPropertyModelBuilders()) {
/* 172 */             if (bsonProperty.value().equals(builder.getWriteName())) {
/* 173 */               propertyModelBuilder = builder; break;
/*     */             } 
/* 175 */             if (bsonProperty.value().equals(builder.getReadName()))
/*     */             {
/*     */               
/* 178 */               propertyModelBuilder = builder;
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 183 */           if (propertyModelBuilder == null) {
/* 184 */             propertyModelBuilder = classModelBuilder.getProperty(bsonProperty.value());
/*     */           }
/*     */           
/* 187 */           if (propertyModelBuilder == null) {
/* 188 */             propertyModelBuilder = addCreatorPropertyToClassModelBuilder(classModelBuilder, bsonProperty.value(), parameterType);
/*     */           }
/*     */           else {
/*     */             
/* 192 */             if (!bsonProperty.value().equals(propertyModelBuilder.getName())) {
/* 193 */               propertyModelBuilder.writeName(bsonProperty.value());
/*     */             }
/* 195 */             tryToExpandToGenericType(parameterType, propertyModelBuilder, genericType);
/*     */           } 
/*     */         } 
/*     */         
/* 199 */         if (!propertyModelBuilder.getTypeData().isAssignableFrom(parameterType)) {
/* 200 */           throw creatorExecutable.getError(clazz, String.format("Invalid Property type for '%s'. Expected %s, found %s.", new Object[] { propertyModelBuilder
/* 201 */                   .getWriteName(), propertyModelBuilder.getTypeData().getType(), parameterType }));
/*     */         }
/*     */       } 
/* 204 */       classModelBuilder.instanceCreatorFactory(new InstanceCreatorFactoryImpl<>(creatorExecutable));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> void tryToExpandToGenericType(Class<?> parameterType, PropertyModelBuilder<T> propertyModelBuilder, Type genericType) {
/* 211 */     if (parameterType.isAssignableFrom(propertyModelBuilder.getTypeData().getType()))
/*     */     {
/*     */ 
/*     */       
/* 215 */       propertyModelBuilder.typeData(TypeData.newInstance(genericType, (Class)parameterType));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <T, S> PropertyModelBuilder<S> addCreatorPropertyToClassModelBuilder(ClassModelBuilder<T> classModelBuilder, String name, Class<S> clazz) {
/* 223 */     PropertyModelBuilder<S> propertyModelBuilder = PojoBuilderHelper.<S>createPropertyModelBuilder(new PropertyMetadata<>(name, classModelBuilder.getType().getSimpleName(), TypeData.<S>builder(clazz).build())).readName(null).writeName(name);
/* 224 */     classModelBuilder.addProperty(propertyModelBuilder);
/* 225 */     return propertyModelBuilder;
/*     */   }
/*     */   
/*     */   private void cleanPropertyBuilders(ClassModelBuilder<?> classModelBuilder) {
/* 229 */     List<String> propertiesToRemove = new ArrayList<>();
/* 230 */     for (PropertyModelBuilder<?> propertyModelBuilder : classModelBuilder.getPropertyModelBuilders()) {
/* 231 */       if (!propertyModelBuilder.isReadable() && !propertyModelBuilder.isWritable()) {
/* 232 */         propertiesToRemove.add(propertyModelBuilder.getName());
/*     */       }
/*     */     } 
/* 235 */     for (String propertyName : propertiesToRemove)
/* 236 */       classModelBuilder.removeProperty(propertyName); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\ConventionAnnotationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */