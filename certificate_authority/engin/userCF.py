# -*- coding: UTF-8 -*-
"""
@Project ：certificate_authority
@File    ：userCF.py
@Author  ：Sonia.Suen
@Date    ：2022/3/2 3:01 PM 
"""
import math
from numpy import *

NumOfUsers = 1000


def load_data_by_fire(datafile="demo_path"):
    """
        把datafile文件中的数据读出来，返回data对象
        :param datafile: 数据源文件名称
        :return: 一个列表data，每一项是一个元组，元组第一项是用户，第二项是电影
        """
    data = []
    try:
        file = open(datafile)
    except:
        print("No such file named " + datafile)
        return
    for line in file:
        line = line.split('\t')
        try:
            data.append((int(line[0]), int(line[1])))
        except:
            pass
    file.close()
    return data




def user_similarity(user_data):
    """ 计算用户相似度
        @param user_data 数据集Dict
        @return W    记录用户相似度的二维矩阵
    """
    # 建立物品到用户之间的倒查表，降低计算用户相似度的时间复杂性
    item_users = dict()
    for u, items in user_data.items():
        for i in items:
            if (i not in item_users):
                item_users[i] = set()
            item_users[i].add(u)
        C = dict()
        N = dict()
        # 计算用户之间共有的item的数目
        for i, users in item_users.items():
            for u in users:
                if (u not in N):
                    N[u] = 1
                N[u] += 1
                for v in users:
                    if u == v:
                        continue
                    if (u not in C):
                        C[u] = dict()
                    if (v not in C[u]):
                        C[u][v] = 0
                    # 对热门物品进行了惩罚，采用这种方法被称做UserCF-IIF
                    C[u][v] += (1 / math.log(1 + len(users)))
    W = dict()
    for u, related_users in C.items():
        for v, cuv in related_users.items():
            if (u not in W):
                W[u] = dict()
            # 利用余弦相似度计算用户之间的相似度
            W[u][v] = cuv / math.sqrt(N[u] * N[v])

    return W


def ImprovedCosineSimilarity(train):
    """
    :param train:
    :return: 返回用户的相似度矩阵W，W[u][v]表示u和v的相似度
    :return: 返回用户的相关用户user_related_users字典，key为用户id，value为和用户有共同电影的用户集合
    """
    # 建立电影-用户倒排表
    item_users = dict()
    for u, items in train.items():
        for i in items:
            if i not in item_users:
                item_users[i] = set()
            item_users[i].add(u)
    # C[u][v]表示u和v之间的共同电影
    C = zeros([NumOfUsers, NumOfUsers], dtype=float16)
    # N[u]表示u评价的电影数目
    N = zeros([NumOfUsers], dtype=int32)
    # user_relatedusers[u]表示u的相关用户(共同电影不为零的用户)
    user_relatedusers = dict()
    # 对于每个电影，把它对应的用户组合C[u][v]加一
    for item, users in item_users.items():
        for u in users:
            N[u] += 1
            for v in users:
                if u == v:
                    continue
                if u not in user_relatedusers:
                    user_relatedusers[u] = set()
                user_relatedusers[u].add(v)
                C[u][v] = C[u][v] + (1 / math.log(1 + len(users)))
    # 用户相似度矩阵
    W = zeros([NumOfUsers, NumOfUsers], dtype=float16)
    for u in range(1, NumOfUsers):
        if u in user_relatedusers:
            for v in user_relatedusers[u]:
                W[u][v] = C[u][v] / sqrt(N[u] * N[v])
    return W, user_relatedusers
