package io.sentry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

@Internal
public interface TransactionFinishedCallback {
  void execute(@NotNull ITransaction paramITransaction);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\TransactionFinishedCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */