/*     */ package io.netty.channel.socket.nio;
/*     */ 
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import java.io.IOException;
/*     */ import java.net.SocketOption;
/*     */ import java.net.StandardSocketOptions;
/*     */ import java.nio.channels.Channel;
/*     */ import java.nio.channels.NetworkChannel;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public final class NioChannelOption<T>
/*     */   extends ChannelOption<T>
/*     */ {
/*     */   private final SocketOption<T> option;
/*     */   
/*     */   private NioChannelOption(SocketOption<T> option) {
/*  41 */     super(option.name());
/*  42 */     this.option = option;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> ChannelOption<T> of(SocketOption<T> option) {
/*  49 */     return new NioChannelOption<>(option);
/*     */   }
/*     */ 
/*     */   
/*     */   static <T> boolean setOption(Channel jdkChannel, NioChannelOption<T> option, T value) {
/*  54 */     NetworkChannel channel = (NetworkChannel)jdkChannel;
/*  55 */     if (!channel.supportedOptions().contains(option.option)) {
/*  56 */       return false;
/*     */     }
/*  58 */     if (channel instanceof java.nio.channels.ServerSocketChannel && option.option == StandardSocketOptions.IP_TOS)
/*     */     {
/*     */       
/*  61 */       return false;
/*     */     }
/*     */     try {
/*  64 */       channel.setOption(option.option, value);
/*  65 */       return true;
/*  66 */     } catch (IOException e) {
/*  67 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   static <T> T getOption(Channel jdkChannel, NioChannelOption<T> option) {
/*  72 */     NetworkChannel channel = (NetworkChannel)jdkChannel;
/*     */     
/*  74 */     if (!channel.supportedOptions().contains(option.option)) {
/*  75 */       return null;
/*     */     }
/*  77 */     if (channel instanceof java.nio.channels.ServerSocketChannel && option.option == StandardSocketOptions.IP_TOS)
/*     */     {
/*     */       
/*  80 */       return null;
/*     */     }
/*     */     try {
/*  83 */       return channel.getOption(option.option);
/*  84 */     } catch (IOException e) {
/*  85 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static ChannelOption<?>[] getOptions(Channel jdkChannel) {
/*  91 */     NetworkChannel channel = (NetworkChannel)jdkChannel;
/*  92 */     Set<SocketOption<?>> supportedOpts = channel.supportedOptions();
/*     */     
/*  94 */     if (channel instanceof java.nio.channels.ServerSocketChannel) {
/*  95 */       List<ChannelOption<?>> extraOpts = new ArrayList<>(supportedOpts.size());
/*  96 */       for (SocketOption<?> opt : supportedOpts) {
/*  97 */         if (opt == StandardSocketOptions.IP_TOS) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 102 */         extraOpts.add(new NioChannelOption(opt));
/*     */       } 
/* 104 */       return (ChannelOption<?>[])extraOpts.<ChannelOption>toArray(new ChannelOption[0]);
/*     */     } 
/* 106 */     ChannelOption[] arrayOfChannelOption = new ChannelOption[supportedOpts.size()];
/*     */     
/* 108 */     int i = 0;
/* 109 */     for (SocketOption<?> opt : supportedOpts) {
/* 110 */       arrayOfChannelOption[i++] = new NioChannelOption(opt);
/*     */     }
/* 112 */     return (ChannelOption<?>[])arrayOfChannelOption;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\socket\nio\NioChannelOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */