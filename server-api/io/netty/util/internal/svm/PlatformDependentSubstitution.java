package io.netty.util.internal.svm;

import com.oracle.svm.core.annotate.Alias;
import com.oracle.svm.core.annotate.RecomputeFieldValue;
import com.oracle.svm.core.annotate.TargetClass;

@TargetClass(className = "io.netty.util.internal.PlatformDependent")
final class PlatformDependentSubstitution {
  @Alias
  @RecomputeFieldValue(kind = RecomputeFieldValue.Kind.ArrayBaseOffset, declClass = byte[].class)
  private static long BYTE_ARRAY_BASE_OFFSET;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\svm\PlatformDependentSubstitution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */