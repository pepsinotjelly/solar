import React from "react";
import { Button, Card, CardGroup, Typography } from "@douyinfe/semi-ui";

const App = () => {
  const { Text, Title } = Typography;

  return (
    <div
      style={{
        margin: "5% 15%",
        // 用于调试，完成后请移除border
        border: "1px solid var(--semi-color-border)",
      }}
    >
      <Title>猜您喜欢</Title>

      <div
        style={{
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          alignItems: "center",
          alignContent: "center",
        }}
      >
        <CardGroup type="grid">
          {new Array(6).fill(null).map((v, idx) => (
            <Card
              key={idx}
              shadows="hover"
              title="Card title"
              headerLine={false}
              style={{ width: "33%" }}
              headerExtraContent={<Text link>More</Text>}
            >
              <Text>Card content</Text>
            </Card>
          ))}
        </CardGroup>

        <Button theme="solid" style={{ width: 100 }}>
          换一批
        </Button>
      </div>
    </div>
  );
};

export default App;
