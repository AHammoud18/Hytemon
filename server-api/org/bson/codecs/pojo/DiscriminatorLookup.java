/*    */ package org.bson.codecs.pojo;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.ConcurrentHashMap;
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
/*    */ final class DiscriminatorLookup
/*    */ {
/* 28 */   private final Map<String, Class<?>> discriminatorClassMap = new ConcurrentHashMap<>();
/*    */   private final Set<String> packages;
/*    */   
/*    */   DiscriminatorLookup(Map<Class<?>, ClassModel<?>> classModels, Set<String> packages) {
/* 32 */     for (ClassModel<?> classModel : classModels.values()) {
/* 33 */       if (classModel.getDiscriminator() != null) {
/* 34 */         this.discriminatorClassMap.put(classModel.getDiscriminator(), classModel.getType());
/*    */       }
/*    */     } 
/* 37 */     this.packages = packages;
/*    */   }
/*    */   
/*    */   public Class<?> lookup(String discriminator) {
/* 41 */     if (this.discriminatorClassMap.containsKey(discriminator)) {
/* 42 */       return this.discriminatorClassMap.get(discriminator);
/*    */     }
/*    */     
/* 45 */     Class<?> clazz = getClassForName(discriminator);
/* 46 */     if (clazz == null) {
/* 47 */       clazz = searchPackages(discriminator);
/*    */     }
/*    */     
/* 50 */     if (clazz == null) {
/* 51 */       throw new CodecConfigurationException(String.format("A class could not be found for the discriminator: '%s'.", new Object[] { discriminator }));
/*    */     }
/* 53 */     this.discriminatorClassMap.put(discriminator, clazz);
/*    */     
/* 55 */     return clazz;
/*    */   }
/*    */   
/*    */   void addClassModel(ClassModel<?> classModel) {
/* 59 */     if (classModel.getDiscriminator() != null) {
/* 60 */       this.discriminatorClassMap.put(classModel.getDiscriminator(), classModel.getType());
/*    */     }
/*    */   }
/*    */   
/*    */   private Class<?> getClassForName(String discriminator) {
/* 65 */     Class<?> clazz = null;
/*    */     try {
/* 67 */       clazz = Class.forName(discriminator);
/* 68 */     } catch (ClassNotFoundException classNotFoundException) {}
/*    */ 
/*    */     
/* 71 */     return clazz;
/*    */   }
/*    */   
/*    */   private Class<?> searchPackages(String discriminator) {
/* 75 */     Class<?> clazz = null;
/* 76 */     for (String packageName : this.packages) {
/* 77 */       clazz = getClassForName(packageName + "." + discriminator);
/* 78 */       if (clazz != null) {
/* 79 */         return clazz;
/*    */       }
/*    */     } 
/* 82 */     return clazz;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\DiscriminatorLookup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */