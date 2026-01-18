/*    */ package org.bson;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bson.types.ObjectId;
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
/*    */ 
/*    */ public class LazyBSONCallback
/*    */   extends EmptyBSONCallback
/*    */ {
/*    */   private Object root;
/*    */   
/*    */   public void reset() {
/* 31 */     this.root = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object get() {
/* 36 */     return getRoot();
/*    */   }
/*    */ 
/*    */   
/*    */   public void gotBinary(String name, byte type, byte[] data) {
/* 41 */     setRoot(createObject(data, 0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object createObject(byte[] bytes, int offset) {
/* 52 */     return new LazyBSONObject(bytes, offset, this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List createArray(byte[] bytes, int offset) {
/* 64 */     return new LazyBSONList(bytes, offset, this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object createDBRef(String ns, ObjectId id) {
/* 75 */     return (new BasicBSONObject("$ns", ns)).append("$id", id);
/*    */   }
/*    */   
/*    */   private Object getRoot() {
/* 79 */     return this.root;
/*    */   }
/*    */   
/*    */   private void setRoot(Object root) {
/* 83 */     this.root = root;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\LazyBSONCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */