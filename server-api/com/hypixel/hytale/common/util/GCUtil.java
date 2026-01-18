/*    */ package com.hypixel.hytale.common.util;
/*    */ 
/*    */ import com.sun.management.GarbageCollectionNotificationInfo;
/*    */ import java.lang.management.GarbageCollectorMXBean;
/*    */ import java.lang.management.ManagementFactory;
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.management.Notification;
/*    */ import javax.management.NotificationEmitter;
/*    */ import javax.management.openmbean.CompositeData;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GCUtil
/*    */ {
/*    */   public static void register(@Nonnull Consumer<GarbageCollectionNotificationInfo> consumer) {
/* 20 */     List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
/* 21 */     for (GarbageCollectorMXBean gcBean : gcBeans) {
/* 22 */       NotificationEmitter emitter = (NotificationEmitter)gcBean;
/* 23 */       emitter.addNotificationListener((notification, handback) -> { if (notification.getType().equals("com.sun.management.gc.notification")) { GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData)notification.getUserData()); consumer.accept(info); }  }null, null);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\commo\\util\GCUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */