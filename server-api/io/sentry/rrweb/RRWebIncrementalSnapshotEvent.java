/*    */ package io.sentry.rrweb;
/*    */ import io.sentry.ILogger;
/*    */ import io.sentry.JsonDeserializer;
/*    */ import io.sentry.JsonSerializable;
/*    */ import io.sentry.ObjectReader;
/*    */ import io.sentry.ObjectWriter;
/*    */ import io.sentry.util.Objects;
/*    */ import java.io.IOException;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public abstract class RRWebIncrementalSnapshotEvent extends RRWebEvent {
/*    */   private IncrementalSource source;
/*    */   
/*    */   public enum IncrementalSource implements JsonSerializable {
/* 15 */     Mutation,
/* 16 */     MouseMove,
/* 17 */     MouseInteraction,
/* 18 */     Scroll,
/* 19 */     ViewportResize,
/* 20 */     Input,
/* 21 */     TouchMove,
/* 22 */     MediaInteraction,
/* 23 */     StyleSheetRule,
/* 24 */     CanvasMutation,
/* 25 */     Font,
/* 26 */     Log,
/* 27 */     Drag,
/* 28 */     StyleDeclaration,
/* 29 */     Selection,
/* 30 */     AdoptedStyleSheet,
/* 31 */     CustomElement;
/*    */ 
/*    */ 
/*    */     
/*    */     public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 36 */       writer.value(ordinal());
/*    */     }
/*    */     
/*    */     public static final class Deserializer
/*    */       implements JsonDeserializer<IncrementalSource> {
/*    */       @NotNull
/*    */       public RRWebIncrementalSnapshotEvent.IncrementalSource deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 43 */         return RRWebIncrementalSnapshotEvent.IncrementalSource.values()[reader.nextInt()];
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public RRWebIncrementalSnapshotEvent(@NotNull IncrementalSource source) {
/* 51 */     super(RRWebEventType.IncrementalSnapshot);
/* 52 */     this.source = source;
/*    */   }
/*    */   
/*    */   public IncrementalSource getSource() {
/* 56 */     return this.source;
/*    */   }
/*    */   
/*    */   public void setSource(IncrementalSource source) {
/* 60 */     this.source = source;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static final class JsonKeys
/*    */   {
/*    */     public static final String SOURCE = "source";
/*    */   }
/*    */ 
/*    */   
/*    */   public static final class Serializer
/*    */   {
/*    */     public void serialize(@NotNull RRWebIncrementalSnapshotEvent baseEvent, @NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 74 */       writer.name("source").value(logger, baseEvent.source);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final class Deserializer
/*    */   {
/*    */     public boolean deserializeValue(@NotNull RRWebIncrementalSnapshotEvent baseEvent, @NotNull String nextName, @NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 85 */       if (nextName.equals("source")) {
/* 86 */         baseEvent.source = 
/* 87 */           (RRWebIncrementalSnapshotEvent.IncrementalSource)Objects.requireNonNull(reader
/* 88 */             .nextOrNull(logger, new RRWebIncrementalSnapshotEvent.IncrementalSource.Deserializer()), "");
/* 89 */         return true;
/*    */       } 
/* 91 */       return false;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\rrweb\RRWebIncrementalSnapshotEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */