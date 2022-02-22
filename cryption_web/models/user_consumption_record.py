class UserConsumptionRecord:
    id = 0
    user_id = 0
    number = 0
    commodity_id = 0
    total_price = 0
    user_evaluation = 0
    is_return = 0
    is_exchange = 0

    def __init__(self, record_id, user_evaluation, user_id, number=0, commodity_id=0, total_price=0, is_return=0,
                 is_exchange=0):
        self.id = record_id
        self.user_id = user_id
        self.number = number
        self.commodity_id = commodity_id
        self.total_price = total_price
        self.user_evaluation = user_evaluation
        self.is_return = is_return
        self.is_exchange = is_exchange
