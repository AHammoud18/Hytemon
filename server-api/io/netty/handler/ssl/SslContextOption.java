/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import io.netty.util.AbstractConstant;
/*    */ import io.netty.util.Constant;
/*    */ import io.netty.util.ConstantPool;
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ public class SslContextOption<T>
/*    */   extends AbstractConstant<SslContextOption<T>>
/*    */ {
/* 33 */   private static final ConstantPool<SslContextOption<Object>> pool = new ConstantPool<SslContextOption<Object>>()
/*    */     {
/*    */       protected SslContextOption<Object> newConstant(int id, String name) {
/* 36 */         return new SslContextOption(id, name);
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> SslContextOption<T> valueOf(String name) {
/* 45 */     return (SslContextOption<T>)pool.valueOf(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> SslContextOption<T> valueOf(Class<?> firstNameComponent, String secondNameComponent) {
/* 53 */     return (SslContextOption<T>)pool.valueOf(firstNameComponent, secondNameComponent);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean exists(String name) {
/* 60 */     return pool.exists(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private SslContextOption(int id, String name) {
/* 67 */     super(id, name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected SslContextOption(String name) {
/* 76 */     this(pool.nextId(), name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void validate(T value) {
/* 84 */     ObjectUtil.checkNotNull(value, "value");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\SslContextOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */