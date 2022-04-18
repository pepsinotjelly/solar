import {Typography, Layout} from '@douyinfe/semi-ui';
import TagDetail from "../../model/tag-detail";
import AvatarListPanel from "./components/avatar-list";

function AllTagPage() {
    const {Content} = Layout
    const {Title} = Typography;
    const mockDataLine1: TagDetail[] = [
        {tagId: 16, tagName: 'Document', tagColor: 'indigo', tagImgUrl: ''},
        {tagId: 8, tagName: 'Action', tagColor: 'red', tagImgUrl: ''},
        {tagId: 1, tagName: 'Adventure', tagColor: 'amber', tagImgUrl: ''},
        {tagId: 7, tagName: 'Drama', tagColor: 'lime', tagImgUrl: ''},
        {tagId: 2, tagName: 'Animation', tagColor: 'green', tagImgUrl: ''},
        {tagId: 17, tagName: 'IMAX', tagColor: 'teal', tagImgUrl: ''},
    ]
    const mockDataLine2: TagDetail[] = [
        {tagId: 18, tagName: 'Western', tagColor: 'yellow', tagImgUrl: ''},
        {tagId: 5, tagName: 'Fantasy', tagColor: 'violet', tagImgUrl: ''},
        {tagId: 19, tagName: 'Film-Noir', tagColor: 'cyan', tagImgUrl: ''},
        {tagId: 6, tagName: 'Romance', tagColor: 'purple', tagImgUrl: ''},
        {tagId: 9, tagName: 'Crime', tagColor: 'blue', tagImgUrl: ''},
        {tagId: 10, tagName: 'Thriller', tagColor: 'red', tagImgUrl: ''},
        {tagId: 15, tagName: 'Musical', tagColor: 'light-blue', tagImgUrl: ''},

    ]
    const mockDataLine3: TagDetail[] = [
        {tagId: 11, tagName: 'Horror', tagColor: 'blue', tagImgUrl: ''},
        {tagId: 3, tagName: 'Children', tagColor: 'pink', tagImgUrl: ''},
        {tagId: 12, tagName: 'Mystery', tagColor: 'amber', tagImgUrl: ''},
        {tagId: 14, tagName: 'War', tagColor: 'red', tagImgUrl: ''},
        {tagId: 4, tagName: 'Comedy', tagColor: 'orange', tagImgUrl: ''},
        {tagId: 13, tagName: 'Sci-Fi', tagColor: 'lime', tagImgUrl: ''},
    ]
    return (
        <div>
            <Layout>
                <Title style={{
                    textAlign: 'center',
                    color: 'blueviolet',
                    fontSize: '40px',

                }}>点击选择你喜欢的电影流派吧</Title>
                <br/>

                <Content style={{
                    width: '70%',
                    // border: '3px solid var(--semi-color-border)',
                    marginLeft: '15%',
                    textAlign: 'center',
                    justifyContent: 'center',
                }}>
                    <br/>
                    <AvatarListPanel AvatarDataList={mockDataLine1}/>
                    <AvatarListPanel AvatarDataList={mockDataLine2}/>
                    <AvatarListPanel AvatarDataList={mockDataLine3}/>
                    <br/>
                </Content>
            </Layout>

        </div>
    );
}

export default AllTagPage