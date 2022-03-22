import React, { useState }  from "react";
import {Button, Card, CardGroup, Typography} from "@douyinfe/semi-ui";
import {Pagination, Progress, Avatar, Space} from '@douyinfe/semi-ui';

const App = () => {
    const {Meta} = Card;
    const {Text, Title} = Typography;
    const [ spacing] = useState(2);

    return (
        <div
            style={{
                margin: "5% 15%",
                // 用于调试，完成后请移除border
                border: "1px solid var(--semi-color-border)",
            }}
        >
            <br/>
            <Title>猜您喜欢</Title>
            <br/>
            <br/>
            <br/>
            <div
                style={{
                    display: "flex",
                    flexDirection: "column",
                    justifyContent: "center",
                    alignItems: "center",
                    alignContent: "center",
                }}
            >
                <CardGroup spacing={spacing}>
                    {new Array(4).fill(null).map((v, idx) => (
                        <Card
                            key={idx}
                            shadows="hover"
                            title={<Meta
                                title="Semi Doc"
                                description="全面、易用、优质"
                            />}
                            headerLine={false}
                            style={{width: 230}}
                            headerExtraContent={<Text link>More</Text>}
                            cover={
                                <img
                                    alt="image"
                                    src="https://www.themoviedb.org/t/p/w600_and_h900_bestv2/2Z19YpRxntcEQZN02NWWoxbGmAL.jpg"
                                />
                            }
                        >
                            <Text>Card content</Text>

                        </Card>
                    ))}
                </CardGroup>
                <br/>
                <br/>
                <Pagination size={"small"}>
                    <div>
                        <Pagination total={3} showTotal defaultCurrentPage={1}></Pagination>
                    </div>
                </Pagination>
                <br/>
            </div>
        </div>
    );
};

export default App;
