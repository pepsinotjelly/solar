"""

        Author: Sonia
        Desc: Calculator

"""
import numpy as np
from numpy import dot
from numpy.linalg import norm
import service.RecommendClient

from models.user_consumption_record import UserConsumptionRecord


class SimilarityCalculator:

    def __init__(self):
        pass

    # 比较列表的向量与一个向量之间的相似度
    def get_multi_cosine_similarity(self, records1, records2):
        similarity_scores = records1.dot(records2) / (np.linalg.norm(records1, axis=1) * np.linalg.norm(records2))
        print(similarity_scores)
        return similarity_scores

    # 比较两向量间相似度，精度更高（点积/向量范数）
    def get_cosine_similarity(self, record1, record2):
        similarity_scores = dot(record1, record2) / (norm(record1) * norm(record2))
        print(similarity_scores)
        return similarity_scores

    # 将商品分组
    def get_commodity_group(self, commodity_list):
        commodity_group = list()
        for commodity in commodity_list:
            pass
        return commodity_group

    def get_recommend_list(self, user_id):
        # 获取用户信息
        user_info_list = service.RecommendClient().batch_get_user_info(user_id)
        # 获取对比用户信息
        other_info_list = service.RecommendClient().batch_get_user_info(user_id)
        # 获取相似度排序
        pass

        # similarity_scores = self.get_cosine_similarity(user_record, record_list)
