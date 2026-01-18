/*     */ package org.bson.codecs.pojo;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bson.codecs.configuration.CodecConfigurationException;
/*     */ import org.bson.codecs.pojo.annotations.BsonProperty;
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
/*     */ final class InstanceCreatorImpl<T>
/*     */   implements InstanceCreator<T>
/*     */ {
/*     */   private final CreatorExecutable<T> creatorExecutable;
/*     */   private final Map<PropertyModel<?>, Object> cachedValues;
/*     */   private final Map<String, Integer> properties;
/*     */   private final Object[] params;
/*     */   private T newInstance;
/*     */   
/*     */   InstanceCreatorImpl(CreatorExecutable<T> creatorExecutable) {
/*  35 */     this.creatorExecutable = creatorExecutable;
/*  36 */     if (creatorExecutable.getProperties().isEmpty()) {
/*  37 */       this.cachedValues = null;
/*  38 */       this.properties = null;
/*  39 */       this.params = null;
/*  40 */       this.newInstance = creatorExecutable.getInstance();
/*     */     } else {
/*  42 */       this.cachedValues = new HashMap<>();
/*  43 */       this.properties = new HashMap<>();
/*     */       
/*  45 */       for (int i = 0; i < creatorExecutable.getProperties().size(); i++) {
/*  46 */         if (creatorExecutable.getIdPropertyIndex() != null && creatorExecutable.getIdPropertyIndex().intValue() == i) {
/*  47 */           this.properties.put("_id", creatorExecutable.getIdPropertyIndex());
/*     */         } else {
/*  49 */           this.properties.put(((BsonProperty)creatorExecutable.getProperties().get(i)).value(), Integer.valueOf(i));
/*     */         } 
/*     */       } 
/*     */       
/*  53 */       this.params = new Object[this.properties.size()];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public <S> void set(S value, PropertyModel<S> propertyModel) {
/*  59 */     if (this.newInstance != null) {
/*  60 */       propertyModel.getPropertyAccessor().set(this.newInstance, value);
/*     */     } else {
/*  62 */       if (!this.properties.isEmpty()) {
/*  63 */         String propertyName = propertyModel.getWriteName();
/*     */         
/*  65 */         if (!this.properties.containsKey(propertyName))
/*     */         {
/*  67 */           propertyName = propertyModel.getName();
/*     */         }
/*     */         
/*  70 */         Integer index = this.properties.get(propertyName);
/*  71 */         if (index != null) {
/*  72 */           this.params[index.intValue()] = value;
/*     */         }
/*  74 */         this.properties.remove(propertyName);
/*     */       } 
/*     */       
/*  77 */       if (this.properties.isEmpty()) {
/*  78 */         constructInstanceAndProcessCachedValues();
/*     */       } else {
/*  80 */         this.cachedValues.put(propertyModel, value);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public T getInstance() {
/*  87 */     if (this.newInstance == null) {
/*     */       try {
/*  89 */         for (Map.Entry<String, Integer> entry : this.properties.entrySet()) {
/*  90 */           this.params[((Integer)entry.getValue()).intValue()] = null;
/*     */         }
/*  92 */         constructInstanceAndProcessCachedValues();
/*  93 */       } catch (CodecConfigurationException e) {
/*  94 */         throw new CodecConfigurationException(String.format("Could not construct new instance of: %s. Missing the following properties: %s", new Object[] { this.creatorExecutable
/*     */                 
/*  96 */                 .getType().getSimpleName(), this.properties.keySet() }), e);
/*     */       } 
/*     */     }
/*  99 */     return this.newInstance;
/*     */   }
/*     */   
/*     */   private void constructInstanceAndProcessCachedValues() {
/*     */     try {
/* 104 */       this.newInstance = this.creatorExecutable.getInstance(this.params);
/* 105 */     } catch (Exception e) {
/* 106 */       throw new CodecConfigurationException(e.getMessage(), e);
/*     */     } 
/*     */     
/* 109 */     for (Map.Entry<PropertyModel<?>, Object> entry : this.cachedValues.entrySet()) {
/* 110 */       setPropertyValue(entry.getKey(), entry.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private <S> void setPropertyValue(PropertyModel<S> propertyModel, Object value) {
/* 116 */     set(value, propertyModel);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\InstanceCreatorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */