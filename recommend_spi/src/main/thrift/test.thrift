include "base.thrift"
namespace java com.bubble.thrift.test
struct HelloRequest {
    1: string sayWhat = "";
    2: i32 times = 0;
    255: optional base.Base Base;
}
struct HelloResponse {
    1: string answerIs = "";
    255: base.BaseResp BaseResp;
}
service SayHelloService{
    HelloResponse Visit (1: HelloRequest helloRequest)
}