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
/*    */ final class IdPropertyModelHolder<I>
/*    */ {
/*    */   private final PropertyModel<I> propertyModel;
/*    */   private final IdGenerator<I> idGenerator;
/*    */   
/*    */   static <T, I> IdPropertyModelHolder<I> create(ClassModel<T> classModel, PropertyModel<I> idPropertyModel) {
/* 28 */     return create(classModel.getType(), idPropertyModel, classModel.getIdPropertyModelHolder().getIdGenerator());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   static <T, I, V> IdPropertyModelHolder<I> create(Class<T> type, PropertyModel<I> idProperty, IdGenerator<V> idGenerator) {
/* 34 */     if (idProperty == null && idGenerator != null)
/* 35 */       throw new CodecConfigurationException(String.format("Invalid IdGenerator. There is no IdProperty set for: %s", new Object[] { type })); 
/* 36 */     if (idGenerator != null && !idProperty.getTypeData().getType().isAssignableFrom(idGenerator.getType())) {
/* 37 */       throw new CodecConfigurationException(String.format("Invalid IdGenerator. Mismatching types, the IdProperty type is: %s but the IdGenerator type is: %s", new Object[] { idProperty
/* 38 */               .getTypeData().getType(), idGenerator.getType() }));
/*    */     }
/* 40 */     return new IdPropertyModelHolder<>(idProperty, idGenerator);
/*    */   }
/*    */   
/*    */   private IdPropertyModelHolder(PropertyModel<I> propertyModel, IdGenerator<I> idGenerator) {
/* 44 */     this.propertyModel = propertyModel;
/* 45 */     this.idGenerator = idGenerator;
/*    */   }
/*    */   
/*    */   PropertyModel<I> getPropertyModel() {
/* 49 */     return this.propertyModel;
/*    */   }
/*    */   
/*    */   IdGenerator<I> getIdGenerator() {
/* 53 */     return this.idGenerator;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 58 */     if (this == o) {
/* 59 */       return true;
/*    */     }
/* 61 */     if (o == null || getClass() != o.getClass()) {
/* 62 */       return false;
/*    */     }
/*    */     
/* 65 */     IdPropertyModelHolder<?> that = (IdPropertyModelHolder)o;
/*    */     
/* 67 */     if ((this.propertyModel != null) ? !this.propertyModel.equals(that.propertyModel) : (that.propertyModel != null)) {
/* 68 */       return false;
/*    */     }
/* 70 */     return (this.idGenerator != null) ? this.idGenerator.equals(that.idGenerator) : ((that.idGenerator == null));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 75 */     int result = (this.propertyModel != null) ? this.propertyModel.hashCode() : 0;
/* 76 */     result = 31 * result + ((this.idGenerator != null) ? this.idGenerator.hashCode() : 0);
/* 77 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\IdPropertyModelHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */