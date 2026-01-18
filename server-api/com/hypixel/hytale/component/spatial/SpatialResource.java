/*    */ package com.hypixel.hytale.component.spatial;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Resource;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class SpatialResource<T, ECS_TYPE>
/*    */   implements Resource<ECS_TYPE>
/*    */ {
/*    */   @Nonnull
/* 23 */   private static final ThreadLocal<ObjectList<Ref<?>>> THREAD_LOCAL_REFERENCE_LIST = ThreadLocal.withInitial(it.unimi.dsi.fastutil.objects.ObjectArrayList::new);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static <ECS_TYPE> ObjectList<Ref<ECS_TYPE>> getThreadLocalReferenceList() {
/* 35 */     ObjectList<Ref<ECS_TYPE>> list = (ObjectList)THREAD_LOCAL_REFERENCE_LIST.get();
/* 36 */     list.clear();
/* 37 */     return list;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 43 */   private final SpatialData<Ref<ECS_TYPE>> spatialData = new SpatialData<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   private final SpatialStructure<T> spatialStructure;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SpatialResource(@Nonnull SpatialStructure<T> spatialStructure) {
/* 58 */     this.spatialStructure = spatialStructure;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SpatialData<Ref<ECS_TYPE>> getSpatialData() {
/* 66 */     return this.spatialData;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SpatialStructure<T> getSpatialStructure() {
/* 74 */     return this.spatialStructure;
/*    */   }
/*    */ 
/*    */   
/*    */   public Resource<ECS_TYPE> clone() {
/* 79 */     throw new UnsupportedOperationException("Not supported yet.");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\spatial\SpatialResource.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */