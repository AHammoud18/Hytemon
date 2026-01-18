/*    */ package org.bson.codecs.pojo;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Function;
/*    */ import org.bson.assertions.Assertions;
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
/*    */ final class Either<L, R>
/*    */ {
/*    */   private final L left;
/*    */   private final R right;
/*    */   
/*    */   public static <L, R> Either<L, R> left(L value) {
/* 28 */     return new Either<>((L)Assertions.notNull("value", value), null);
/*    */   }
/*    */   
/*    */   public static <L, R> Either<L, R> right(R value) {
/* 32 */     return new Either<>(null, (R)Assertions.notNull("value", value));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Either(L l, R r) {
/* 39 */     this.left = l;
/* 40 */     this.right = r;
/*    */   }
/*    */   
/*    */   public <T> T map(Function<? super L, ? extends T> lFunc, Function<? super R, ? extends T> rFunc) {
/* 44 */     return (this.left != null) ? lFunc.apply(this.left) : rFunc.apply(this.right);
/*    */   }
/*    */   
/*    */   public void apply(Consumer<? super L> lFunc, Consumer<? super R> rFunc) {
/* 48 */     if (this.left != null) {
/* 49 */       lFunc.accept(this.left);
/*    */     }
/* 51 */     if (this.right != null) {
/* 52 */       rFunc.accept(this.right);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 58 */     return "Either{left=" + this.left + ", right=" + this.right + '}';
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 66 */     if (this == o) {
/* 67 */       return true;
/*    */     }
/* 69 */     if (o == null || getClass() != o.getClass()) {
/* 70 */       return false;
/*    */     }
/*    */     
/* 73 */     Either<?, ?> either = (Either<?, ?>)o;
/* 74 */     return (Objects.equals(this.left, either.left) && Objects.equals(this.right, either.right));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 79 */     return Objects.hash(new Object[] { this.left, this.right });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\Either.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */