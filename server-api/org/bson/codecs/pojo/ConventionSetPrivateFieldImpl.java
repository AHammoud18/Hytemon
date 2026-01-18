/*    */ package org.bson.codecs.pojo;
/*    */ 
/*    */ import java.lang.reflect.Modifier;
/*    */ import org.bson.codecs.configuration.CodecConfigurationException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ConventionSetPrivateFieldImpl
/*    */   implements Convention
/*    */ {
/*    */   public void apply(ClassModelBuilder<?> classModelBuilder) {
/* 28 */     for (PropertyModelBuilder<?> propertyModelBuilder : classModelBuilder.getPropertyModelBuilders()) {
/* 29 */       if (!(propertyModelBuilder.getPropertyAccessor() instanceof PropertyAccessorImpl)) {
/* 30 */         throw new CodecConfigurationException(String.format("The SET_PRIVATE_FIELDS_CONVENTION is not compatible with propertyModelBuilder instance that have custom implementations of org.bson.codecs.pojo.PropertyAccessor: %s", new Object[] { propertyModelBuilder
/*    */                 
/* 32 */                 .getPropertyAccessor().getClass().getName() }));
/*    */       }
/* 34 */       PropertyAccessorImpl<?> defaultAccessor = (PropertyAccessorImpl)propertyModelBuilder.getPropertyAccessor();
/* 35 */       PropertyMetadata<?> propertyMetaData = defaultAccessor.getPropertyMetadata();
/* 36 */       if (!propertyMetaData.isDeserializable() && propertyMetaData.getField() != null && 
/* 37 */         Modifier.isPrivate(propertyMetaData.getField().getModifiers())) {
/* 38 */         setPropertyAccessor(propertyModelBuilder);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private <T> void setPropertyAccessor(PropertyModelBuilder<T> propertyModelBuilder) {
/* 45 */     propertyModelBuilder.propertyAccessor(new PrivatePropertyAccessor<>((PropertyAccessorImpl)propertyModelBuilder
/* 46 */           .getPropertyAccessor()));
/*    */   }
/*    */   
/*    */   private static final class PrivatePropertyAccessor<T> implements PropertyAccessor<T> {
/*    */     private final PropertyAccessorImpl<T> wrapped;
/*    */     
/*    */     private PrivatePropertyAccessor(PropertyAccessorImpl<T> wrapped) {
/* 53 */       this.wrapped = wrapped;
/*    */       try {
/* 55 */         wrapped.getPropertyMetadata().getField().setAccessible(true);
/* 56 */       } catch (Exception e) {
/* 57 */         throw new CodecConfigurationException(String.format("Unable to make private field accessible '%s' in %s", new Object[] { wrapped
/* 58 */                 .getPropertyMetadata().getName(), wrapped.getPropertyMetadata().getDeclaringClassName() }), e);
/*    */       } 
/*    */     }
/*    */ 
/*    */     
/*    */     public <S> T get(S instance) {
/* 64 */       return this.wrapped.get(instance);
/*    */     }
/*    */ 
/*    */     
/*    */     public <S> void set(S instance, T value) {
/*    */       try {
/* 70 */         this.wrapped.getPropertyMetadata().getField().set(instance, value);
/* 71 */       } catch (Exception e) {
/* 72 */         throw new CodecConfigurationException(String.format("Unable to set value for property '%s' in %s", new Object[] { this.wrapped
/* 73 */                 .getPropertyMetadata().getName(), this.wrapped.getPropertyMetadata().getDeclaringClassName() }), e);
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\ConventionSetPrivateFieldImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */