/*     */ package org.bson.codecs.pojo;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public final class ClassModel<T>
/*     */ {
/*     */   private final String name;
/*     */   private final Class<T> type;
/*     */   private final boolean hasTypeParameters;
/*     */   private final InstanceCreatorFactory<T> instanceCreatorFactory;
/*     */   private final boolean discriminatorEnabled;
/*     */   private final String discriminatorKey;
/*     */   private final String discriminator;
/*     */   private final IdPropertyModelHolder<?> idPropertyModelHolder;
/*     */   private final List<PropertyModel<?>> propertyModels;
/*     */   private final Map<String, TypeParameterMap> propertyNameToTypeParameterMap;
/*     */   
/*     */   ClassModel(Class<T> clazz, Map<String, TypeParameterMap> propertyNameToTypeParameterMap, InstanceCreatorFactory<T> instanceCreatorFactory, Boolean discriminatorEnabled, String discriminatorKey, String discriminator, IdPropertyModelHolder<?> idPropertyModelHolder, List<PropertyModel<?>> propertyModels) {
/*  47 */     this.name = clazz.getSimpleName();
/*  48 */     this.type = clazz;
/*  49 */     this.hasTypeParameters = ((clazz.getTypeParameters()).length > 0);
/*  50 */     this.propertyNameToTypeParameterMap = Collections.unmodifiableMap(new HashMap<>(propertyNameToTypeParameterMap));
/*     */     
/*  52 */     this.instanceCreatorFactory = instanceCreatorFactory;
/*  53 */     this.discriminatorEnabled = discriminatorEnabled.booleanValue();
/*  54 */     this.discriminatorKey = discriminatorKey;
/*  55 */     this.discriminator = discriminator;
/*  56 */     this.idPropertyModelHolder = idPropertyModelHolder;
/*  57 */     this.propertyModels = Collections.unmodifiableList(new ArrayList<>(propertyModels));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <S> ClassModelBuilder<S> builder(Class<S> type) {
/*  68 */     return new ClassModelBuilder<>(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   InstanceCreator<T> getInstanceCreator() {
/*  75 */     return this.instanceCreatorFactory.create();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<T> getType() {
/*  82 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasTypeParameters() {
/*  89 */     return this.hasTypeParameters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useDiscriminator() {
/*  96 */     return this.discriminatorEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDiscriminatorKey() {
/* 105 */     return this.discriminatorKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDiscriminator() {
/* 114 */     return this.discriminator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyModel<?> getPropertyModel(String propertyName) {
/* 124 */     for (PropertyModel<?> propertyModel : this.propertyModels) {
/* 125 */       if (propertyModel.getName().equals(propertyName)) {
/* 126 */         return propertyModel;
/*     */       }
/*     */     } 
/* 129 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PropertyModel<?>> getPropertyModels() {
/* 138 */     return this.propertyModels;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyModel<?> getIdPropertyModel() {
/* 147 */     return (this.idPropertyModelHolder != null) ? this.idPropertyModelHolder.getPropertyModel() : null;
/*     */   }
/*     */   
/*     */   IdPropertyModelHolder<?> getIdPropertyModelHolder() {
/* 151 */     return this.idPropertyModelHolder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 160 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 165 */     return "ClassModel{type=" + this.type + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 172 */     if (this == o) {
/* 173 */       return true;
/*     */     }
/* 175 */     if (o == null || getClass() != o.getClass()) {
/* 176 */       return false;
/*     */     }
/*     */     
/* 179 */     ClassModel<?> that = (ClassModel)o;
/*     */     
/* 181 */     if (this.discriminatorEnabled != that.discriminatorEnabled) {
/* 182 */       return false;
/*     */     }
/* 184 */     if (!getType().equals(that.getType())) {
/* 185 */       return false;
/*     */     }
/* 187 */     if (!getInstanceCreatorFactory().equals(that.getInstanceCreatorFactory())) {
/* 188 */       return false;
/*     */     }
/* 190 */     if ((getDiscriminatorKey() != null) ? !getDiscriminatorKey().equals(that.getDiscriminatorKey()) : (that
/* 191 */       .getDiscriminatorKey() != null)) {
/* 192 */       return false;
/*     */     }
/* 194 */     if ((getDiscriminator() != null) ? !getDiscriminator().equals(that.getDiscriminator()) : (that.getDiscriminator() != null)) {
/* 195 */       return false;
/*     */     }
/* 197 */     if ((this.idPropertyModelHolder != null) ? !this.idPropertyModelHolder.equals(that.idPropertyModelHolder) : (that.idPropertyModelHolder != null))
/*     */     {
/* 199 */       return false;
/*     */     }
/* 201 */     if (!getPropertyModels().equals(that.getPropertyModels())) {
/* 202 */       return false;
/*     */     }
/* 204 */     if (!getPropertyNameToTypeParameterMap().equals(that.getPropertyNameToTypeParameterMap())) {
/* 205 */       return false;
/*     */     }
/*     */     
/* 208 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 213 */     int result = getType().hashCode();
/* 214 */     result = 31 * result + getInstanceCreatorFactory().hashCode();
/* 215 */     result = 31 * result + (this.discriminatorEnabled ? 1 : 0);
/* 216 */     result = 31 * result + ((getDiscriminatorKey() != null) ? getDiscriminatorKey().hashCode() : 0);
/* 217 */     result = 31 * result + ((getDiscriminator() != null) ? getDiscriminator().hashCode() : 0);
/* 218 */     result = 31 * result + ((getIdPropertyModelHolder() != null) ? getIdPropertyModelHolder().hashCode() : 0);
/* 219 */     result = 31 * result + getPropertyModels().hashCode();
/* 220 */     result = 31 * result + getPropertyNameToTypeParameterMap().hashCode();
/* 221 */     return result;
/*     */   }
/*     */   
/*     */   InstanceCreatorFactory<T> getInstanceCreatorFactory() {
/* 225 */     return this.instanceCreatorFactory;
/*     */   }
/*     */   
/*     */   Map<String, TypeParameterMap> getPropertyNameToTypeParameterMap() {
/* 229 */     return this.propertyNameToTypeParameterMap;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\ClassModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */