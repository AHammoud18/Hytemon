/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import io.netty.util.internal.StringUtil;
/*    */ import java.lang.reflect.Constructor;
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
/*    */ public class ReflectiveChannelFactory<T extends Channel>
/*    */   implements ChannelFactory<T>
/*    */ {
/*    */   private final Constructor<? extends T> constructor;
/*    */   
/*    */   public ReflectiveChannelFactory(Class<? extends T> clazz) {
/* 32 */     ObjectUtil.checkNotNull(clazz, "clazz");
/*    */     try {
/* 34 */       this.constructor = clazz.getConstructor(new Class[0]);
/* 35 */     } catch (NoSuchMethodException e) {
/* 36 */       throw new IllegalArgumentException("Class " + StringUtil.simpleClassName(clazz) + " does not have a public non-arg constructor", e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public T newChannel() {
/*    */     try {
/* 44 */       return this.constructor.newInstance(new Object[0]);
/* 45 */     } catch (Throwable t) {
/* 46 */       throw new ChannelException("Unable to create Channel from class " + this.constructor.getDeclaringClass(), t);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 52 */     return StringUtil.simpleClassName(ReflectiveChannelFactory.class) + '(' + 
/* 53 */       StringUtil.simpleClassName(this.constructor.getDeclaringClass()) + ".class)";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\ReflectiveChannelFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */