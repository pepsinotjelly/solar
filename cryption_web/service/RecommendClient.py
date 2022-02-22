"""
    demo client for recommend service
"""
import multiprocessing
import os
import sys

from com.bubble.thrift.recommend_service import RecommendService
from com.bubble.thrift.recommend_service.ttypes import GetUserInfoRequest
from com.bubble.thrift.test import SayHelloService
from com.bubble.thrift.test.ttypes import HelloRequest
from thrift.protocol import TMultiplexedProtocol

sys.path.append(os.path.abspath(os.path.join(os.path.dirname('__file__'), os.path.pardir)))

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol


# __HOST = '10.71.167.104'  # 通过 ifconfig | grep "inet"命令获取host
# __HOST = '192.168.0.105'
class RecommendClient:
    __HOST = 'localhost'
    __PORT = 7090
    client = None

    def get_user_info_list(self, user_id):
        pass

    def __init__(self, host, port):
        self.__HOST = host
        self.__PORT = port

    def get_client(self):
        try:
            t_socket = TSocket.TSocket(self.__HOST, self.__PORT)
            transport = TTransport.TBufferedTransport(t_socket)
            protocol = TBinaryProtocol.TBinaryProtocol(transport)
            multiplexProtocol = TMultiplexedProtocol.TMultiplexedProtocol(serviceName='RecommendService',
                                                                          protocol=protocol)
            self.client = RecommendService.Client(multiplexProtocol)
        except Thrift.TException as ex:
            print(ex)
        finally:
            transport.close()

    def batch_get_user_info(self, user_id):
        try:
            getUserInfoRequest = GetUserInfoRequest(UserId=list(user_id))
            response = self.client.BatchGetUserInfo(getUserInfoRequest)
            return response.userInfoEntity
        except Thrift.TException as ex:
            print(ex)
