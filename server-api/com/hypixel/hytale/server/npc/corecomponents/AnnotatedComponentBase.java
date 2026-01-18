/*    */ package com.hypixel.hytale.server.npc.corecomponents;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.util.ComponentInfo;
/*    */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AnnotatedComponentBase
/*    */   implements IAnnotatedComponent
/*    */ {
/*    */   protected IAnnotatedComponent parent;
/*    */   protected int index;
/*    */   
/*    */   public void getInfo(Role role, ComponentInfo holder) {}
/*    */   
/*    */   public void setContext(IAnnotatedComponent parent, int index) {
/* 21 */     this.parent = parent;
/* 22 */     this.index = index;
/*    */   }
/*    */ 
/*    */   
/*    */   public IAnnotatedComponent getParent() {
/* 27 */     return this.parent;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getIndex() {
/* 32 */     return this.index;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\AnnotatedComponentBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */