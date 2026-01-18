/*     */ package com.hypixel.hytale.server.core.io.netty;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFactory;
/*     */ import io.netty.channel.socket.SocketProtocolFamily;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.lang.reflect.Constructor;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReflectiveChannelFactory<T extends Channel>
/*     */   implements ChannelFactory<T>
/*     */ {
/*     */   @Nonnull
/*     */   private final Constructor<? extends T> constructor;
/*     */   private final SocketProtocolFamily family;
/*     */   
/*     */   public ReflectiveChannelFactory(@Nonnull Class<? extends T> clazz, SocketProtocolFamily family) {
/* 215 */     ObjectUtil.checkNotNull(clazz, "clazz");
/*     */     try {
/* 217 */       this.constructor = clazz.getConstructor(new Class[] { SocketProtocolFamily.class });
/* 218 */       this.family = family;
/* 219 */     } catch (NoSuchMethodException e) {
/* 220 */       throw new IllegalArgumentException("Class " + StringUtil.simpleClassName(clazz) + " does not have a public non-arg constructor", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public T newChannel() {
/*     */     try {
/* 229 */       return this.constructor.newInstance(new Object[] { this.family });
/* 230 */     } catch (Throwable t) {
/* 231 */       throw new ChannelException("Unable to create Channel from class " + String.valueOf(this.constructor.getDeclaringClass()), t);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String getSimpleName() {
/* 237 */     return StringUtil.simpleClassName(this.constructor.getDeclaringClass()) + "(" + StringUtil.simpleClassName(this.constructor.getDeclaringClass()) + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 243 */     return StringUtil.simpleClassName(io.netty.channel.ReflectiveChannelFactory.class) + "(" + StringUtil.simpleClassName(io.netty.channel.ReflectiveChannelFactory.class) + ".class, " + 
/* 244 */       StringUtil.simpleClassName(this.constructor.getDeclaringClass()) + ")";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\netty\NettyUtil$ReflectiveChannelFactory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */