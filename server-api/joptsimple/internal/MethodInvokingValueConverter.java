/*    */ package joptsimple.internal;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import joptsimple.ValueConverter;
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
/*    */ class MethodInvokingValueConverter<V>
/*    */   implements ValueConverter<V>
/*    */ {
/*    */   private final Method method;
/*    */   private final Class<V> clazz;
/*    */   
/*    */   MethodInvokingValueConverter(Method method, Class<V> clazz) {
/* 43 */     this.method = method;
/* 44 */     this.clazz = clazz;
/*    */   }
/*    */   
/*    */   public V convert(String value) {
/* 48 */     return this.clazz.cast(Reflection.invoke(this.method, new Object[] { value }));
/*    */   }
/*    */   
/*    */   public Class<V> valueType() {
/* 52 */     return this.clazz;
/*    */   }
/*    */   
/*    */   public String valuePattern() {
/* 56 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\internal\MethodInvokingValueConverter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */