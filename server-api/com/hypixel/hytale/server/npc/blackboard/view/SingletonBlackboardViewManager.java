/*    */ package com.hypixel.hytale.server.npc.blackboard.view;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.blackboard.Blackboard;
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SingletonBlackboardViewManager<View extends IBlackboardView<View>>
/*    */   implements IBlackboardViewManager<View>
/*    */ {
/*    */   private final View view;
/*    */   
/*    */   public SingletonBlackboardViewManager(View view) {
/* 22 */     this.view = view;
/*    */   }
/*    */ 
/*    */   
/*    */   public View get(Ref<EntityStore> ref, Blackboard blackboard, ComponentAccessor<EntityStore> componentAccessor) {
/* 27 */     return this.view;
/*    */   }
/*    */ 
/*    */   
/*    */   public View get(Vector3d position, Blackboard blackboard) {
/* 32 */     return this.view;
/*    */   }
/*    */ 
/*    */   
/*    */   public View get(int chunkX, int chunkZ, Blackboard blackboard) {
/* 37 */     return this.view;
/*    */   }
/*    */ 
/*    */   
/*    */   public View get(long index, Blackboard blackboard) {
/* 42 */     return this.view;
/*    */   }
/*    */ 
/*    */   
/*    */   public View getIfExists(long index) {
/* 47 */     return this.view;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanup() {
/* 52 */     this.view.cleanup();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onWorldRemoved() {
/* 57 */     this.view.onWorldRemoved();
/*    */   }
/*    */ 
/*    */   
/*    */   public void forEachView(@Nonnull Consumer<View> consumer) {
/* 62 */     consumer.accept(this.view);
/*    */   }
/*    */   
/*    */   public void clear() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\blackboard\view\SingletonBlackboardViewManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */