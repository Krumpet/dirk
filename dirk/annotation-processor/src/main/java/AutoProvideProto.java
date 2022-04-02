import com.google.protobuf.GeneratedMessageLite;

import java.lang.annotation.Documented;

@Documented
public @interface AutoProvideProto {
        Class<? extends GeneratedMessageLite> value();
}
