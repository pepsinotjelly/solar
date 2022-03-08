"""

        Author: Sonia
        Desc: Calculator

"""
import numpy as np
from numpy import dot
from numpy.linalg import norm


# import service.RecommendClient

# from models.user_consumption_record import UserConsumptionRecord


class SimilarityCalculator:

    def __init__(self):
        pass

    # 比较列表的向量与一组向量之间的相似度
    def get_multi_cosine_similarity(self, records1, records2):
        similarity_scores = records1.dot(records2) / (np.linalg.norm(records1, axis=1) * np.linalg.norm(records2))
        print(similarity_scores)
        return similarity_scores

    # 比较两向量间相似度，精度更高（点积/向量范数）
    def get_cosine_similarity(self, record1, record2):
        param_1 = dot(record1, record2)
        param_2 = (norm(record1) * norm(record2))
        similarity_scores = param_1 / param_2
        print(similarity_scores, '========similarity_scores======')
        return similarity_scores

    def get_recommend_list(self, user_list, target_list):
        # 隐式反馈 - 非隐式反馈：score(ui, s) = 1 / 评价分数n
        # 用户u与用户ui的相似程度：sim(u, ui)
        sim_u_ui = self.get_multi_cosine_similarity(user_list, target_list)
        print(sim_u_ui, "==================== sim_u_ui 获取用户相似度", )
        # 用户ui对商品s的打分：score(ui, s) 并转换精确度
        score_ui_s = np.asarray(user_list, dtype=np.float)
        # 计算用户u对商品s的喜好程度：sim(u, s) = (连加)sim(u, ui) * score(ui, s)
        for i in range(score_ui_s.shape[0]):
            param = sim_u_ui[i]
            j = 0
            for x in np.nditer(score_ui_s[i], op_flags=['readwrite']):
                score_ui_s[i][j] = x * param
                j = j + 1
        print(score_ui_s, "==================== score_ui_s 获取评分相关度")
        # 连加
        sim_u_s = np.zeros(score_ui_s.shape[1], dtype=np.float)
        for i in range(score_ui_s.shape[0]):
            j = 0
            for x in np.nditer(sim_u_s, op_flags=['readwrite']):
                sim_u_s[j] = x + score_ui_s[i][j]
                j = j + 1
        print(sim_u_s, "===================== sim_u_s 获取预测评分")
        return sim_u_s
        # 根据sim(u, s) 降序排序，推荐top N给用户
