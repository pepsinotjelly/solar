namespace java com.bubble.thrift
namespace py com.bubble.thrift
struct Base {
    1: string client = "";
    2: string logId = "";
}
struct BaseResp {
    1: i32 StatusCode = 0;
    2: string StatusMsg = "";
}
