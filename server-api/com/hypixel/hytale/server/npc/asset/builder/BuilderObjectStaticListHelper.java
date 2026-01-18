/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuilderObjectStaticListHelper<T>
/*    */   extends BuilderObjectListHelper<T>
/*    */ {
/*    */   public BuilderObjectStaticListHelper(Class<?> classType, BuilderContext owner) {
/* 15 */     super(classType, owner);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected BuilderObjectReferenceHelper<T> createReferenceHelper() {
/* 21 */     return new BuilderObjectStaticHelper<>(this.classType, this);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public List<T> staticBuild(@Nonnull BuilderManager manager) {
/* 27 */     if (hasNoElements()) {
/* 28 */       return null;
/*    */     }
/* 30 */     ObjectArrayList<T> objectArrayList = new ObjectArrayList();
/*    */     
/* 32 */     for (BuilderObjectReferenceHelper<T> builder : this.builders) {
/* 33 */       T obj = ((BuilderObjectStaticHelper<T>)builder).staticBuild(manager);
/* 34 */       if (obj != null) objectArrayList.add(obj); 
/*    */     } 
/* 36 */     return (List<T>)objectArrayList;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\BuilderObjectStaticListHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */