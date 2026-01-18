/*    */ package org.bson.codecs.pojo;
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
/*    */ final class ConventionDefaultsImpl
/*    */   implements Convention
/*    */ {
/*    */   public void apply(ClassModelBuilder<?> classModelBuilder) {
/* 22 */     if (classModelBuilder.getDiscriminatorKey() == null) {
/* 23 */       classModelBuilder.discriminatorKey("_t");
/*    */     }
/* 25 */     if (classModelBuilder.getDiscriminator() == null && classModelBuilder.getType() != null) {
/* 26 */       classModelBuilder.discriminator(classModelBuilder.getType().getName());
/*    */     }
/*    */     
/* 29 */     for (PropertyModelBuilder<?> propertyModel : classModelBuilder.getPropertyModelBuilders()) {
/* 30 */       if (classModelBuilder.getIdPropertyName() == null) {
/* 31 */         String propertyName = propertyModel.getName();
/* 32 */         if (propertyName.equals("_id") || propertyName.equals("id"))
/* 33 */           classModelBuilder.idPropertyName(propertyName); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\ConventionDefaultsImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */