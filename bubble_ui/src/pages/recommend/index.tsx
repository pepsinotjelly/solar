import React from "react";
import ItemInfo from "./components/itemInfo";
import {Layout, Typography} from "@douyinfe/semi-ui";


const Recommend = () => {
    const {Title} = Typography;
    return (
        <div style={{justifyContent: 'center',
            height:'100%'
        }}>
            <Title heading={2}
                   style={{
                       justifyContent: 'center',
                       textAlign: 'center'
                   }}
            > 猜你喜欢 </Title>
            <br/>
            <ItemInfo/>
        </div>
    );
};
export default Recommend