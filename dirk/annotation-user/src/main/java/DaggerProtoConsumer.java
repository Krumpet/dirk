public class DaggerProtoConsumer {
    public static void main(String[] args) {
        Test.Hello build = Test.Hello.newBuilder().addNames("Ran").addNames("Danielle").build();
        System.out.println(build);
    }
}
