import engin.similarity_calculator as es
import numpy as np

List1 = np.array([[4, 47, 8, 3],
                  [2, 23, 6, 4]])

List2 = np.array([3, 52, 12, 16])
calculator = es.SimilarityCalculator()
calculator.get_multi_cosine_similarity(records1=List1, records2=List2)

List1 = [4, 47, 8, 3]
List2 = [3, 52, 12, 16]
calculator.get_cosine_similarity(record1=List1, record2=List2)


List1 = [2, 23, 6, 4]
List2 = [3, 52, 12, 16]
calculator.get_cosine_similarity(record1=List1, record2=List2)
