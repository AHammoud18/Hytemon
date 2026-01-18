/*    */ package io.sentry.internal.gestures;
/*    */ 
/*    */ import io.sentry.util.Objects;
/*    */ import java.lang.ref.WeakReference;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class UiElement {
/*    */   @NotNull
/*    */   final WeakReference<Object> viewRef;
/*    */   @Nullable
/*    */   final String className;
/*    */   @Nullable
/*    */   final String resourceName;
/*    */   @Nullable
/*    */   final String tag;
/*    */   @NotNull
/*    */   final String origin;
/*    */   
/*    */   public UiElement(@Nullable Object view, @Nullable String className, @Nullable String resourceName, @Nullable String tag, @NotNull String origin) {
/* 21 */     this.viewRef = new WeakReference(view);
/* 22 */     this.className = className;
/* 23 */     this.resourceName = resourceName;
/* 24 */     this.tag = tag;
/* 25 */     this.origin = origin;
/*    */   }
/*    */   @Nullable
/*    */   public String getClassName() {
/* 29 */     return this.className;
/*    */   }
/*    */   @Nullable
/*    */   public String getResourceName() {
/* 33 */     return this.resourceName;
/*    */   }
/*    */   @Nullable
/*    */   public String getTag() {
/* 37 */     return this.tag;
/*    */   }
/*    */   @NotNull
/*    */   public String getOrigin() {
/* 41 */     return this.origin;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String getIdentifier() {
/* 46 */     if (this.resourceName != null) {
/* 47 */       return this.resourceName;
/*    */     }
/* 49 */     return (String)Objects.requireNonNull(this.tag, "UiElement.tag can't be null");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 55 */     if (this == o) return true; 
/* 56 */     if (o == null || getClass() != o.getClass()) return false; 
/* 57 */     UiElement uiElement = (UiElement)o;
/*    */     
/* 59 */     return (Objects.equals(this.className, uiElement.className) && 
/* 60 */       Objects.equals(this.resourceName, uiElement.resourceName) && 
/* 61 */       Objects.equals(this.tag, uiElement.tag));
/*    */   }
/*    */   @Nullable
/*    */   public Object getView() {
/* 65 */     return this.viewRef.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 70 */     return Objects.hash(new Object[] { this.viewRef, this.resourceName, this.tag });
/*    */   }
/*    */   
/*    */   public enum Type {
/* 74 */     CLICKABLE,
/* 75 */     SCROLLABLE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\internal\gestures\UiElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */