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
/*    */ final class InstanceCreatorFactoryImpl<T>
/*    */   implements InstanceCreatorFactory<T>
/*    */ {
/*    */   private final CreatorExecutable<T> creatorExecutable;
/*    */   
/*    */   InstanceCreatorFactoryImpl(CreatorExecutable<T> creatorExecutable) {
/* 23 */     this.creatorExecutable = creatorExecutable;
/*    */   }
/*    */ 
/*    */   
/*    */   public InstanceCreator<T> create() {
/* 28 */     return new InstanceCreatorImpl<>(this.creatorExecutable);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\InstanceCreatorFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */