/*    */ package org.bson.codecs.pojo;
/*    */ 
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
/*    */ final class PropertyAccessorImpl<T>
/*    */   implements PropertyAccessor<T>
/*    */ {
/*    */   private final PropertyMetadata<T> propertyMetadata;
/*    */   
/*    */   PropertyAccessorImpl(PropertyMetadata<T> propertyMetadata) {
/* 28 */     this.propertyMetadata = propertyMetadata;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <S> T get(S instance) {
/*    */     try {
/* 35 */       if (this.propertyMetadata.isSerializable()) {
/* 36 */         if (this.propertyMetadata.getGetter() != null) {
/* 37 */           return (T)this.propertyMetadata.getGetter().invoke(instance, new Object[0]);
/*    */         }
/* 39 */         return (T)this.propertyMetadata.getField().get(instance);
/*    */       } 
/*    */       
/* 42 */       throw getError(null);
/*    */     }
/* 44 */     catch (Exception e) {
/* 45 */       throw getError(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public <S> void set(S instance, T value) {
/*    */     try {
/* 52 */       if (this.propertyMetadata.isDeserializable()) {
/* 53 */         if (this.propertyMetadata.getSetter() != null) {
/* 54 */           this.propertyMetadata.getSetter().invoke(instance, new Object[] { value });
/*    */         } else {
/* 56 */           this.propertyMetadata.getField().set(instance, value);
/*    */         } 
/*    */       }
/* 59 */     } catch (Exception e) {
/* 60 */       throw setError(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   PropertyMetadata<T> getPropertyMetadata() {
/* 65 */     return this.propertyMetadata;
/*    */   }
/*    */   
/*    */   private CodecConfigurationException getError(Exception cause) {
/* 69 */     return new CodecConfigurationException(String.format("Unable to get value for property '%s' in %s", new Object[] { this.propertyMetadata.getName(), this.propertyMetadata
/* 70 */             .getDeclaringClassName() }), cause);
/*    */   }
/*    */   
/*    */   private CodecConfigurationException setError(Exception cause) {
/* 74 */     return new CodecConfigurationException(String.format("Unable to set value for property '%s' in %s", new Object[] { this.propertyMetadata.getName(), this.propertyMetadata
/* 75 */             .getDeclaringClassName() }), cause);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\PropertyAccessorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */