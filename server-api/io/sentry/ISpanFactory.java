package io.sentry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Internal
public interface ISpanFactory {
  @NotNull
  ITransaction createTransaction(@NotNull TransactionContext paramTransactionContext, @NotNull IScopes paramIScopes, @NotNull TransactionOptions paramTransactionOptions, @Nullable CompositePerformanceCollector paramCompositePerformanceCollector);
  
  @NotNull
  ISpan createSpan(@NotNull IScopes paramIScopes, @NotNull SpanOptions paramSpanOptions, @NotNull SpanContext paramSpanContext, @Nullable ISpan paramISpan);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ISpanFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */