import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.google.protobuf.Type.getDescriptor;

@AutoProvideProto(Test.Hello.class)
public class ThisClassIsAnnotated {
//    public static void doStuff(MessageLite messageLite) {
////        getAttribute(messageLite, 'a');
//    }
//
//    public static void what() {
//        doStuff(Test.Hello.getDefaultInstance());
////        Test.Hello.getDescriptor();
//    }

    @AutoProvideProtoEnum
    public enum DaggerProtos {
        TEST_HELLO(Test.Hello.getDefaultInstance());

        private final MessageLite messageLite;

        DaggerProtos(MessageLite messageLite) {
            this.messageLite = messageLite;
        }
    }

//    @AutoProvideProto2
//    public Test.Hello returnHello() {
//        return Test.Hello.getDefaultInstance();
//    }
//
//    ;
//
//    @AutoProvideProto3
//    public Test.Hello receiveAndReturnHello(Test.Hello hello) {
//        return hello;
//    }
}
