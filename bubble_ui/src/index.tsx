import React, {useState} from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router, Route, Link, Routes, HashRouter, BrowserRouter} from "react-router-dom";
import './index.css';
import App from './App';
import Home from "./pages/home-page";
import reportWebVitals from './reportWebVitals';
import NotFound from "./pages/404";
import Recommend from './pages/user-recommend-page';
import DetailPage from "./pages/movie-detail-page";
import TagMoviePage from "./pages/tag-movie-page";
import AllTagPage from "./pages/all-tag-page";
import {getEmptyUser, GlobalContext, UserContext} from "./context";
import LoginPage from "./pages/login-page";
import RequireAuth from "./components/require-auth";

const Index = () => {
    const [userContext, setUserContext] = useState<UserContext>(getEmptyUser)

    return (
        <React.StrictMode>
            <GlobalContext.Provider value={{userContext, setUserContext}}>
                <BrowserRouter>
                    <Routes>
                        <Route path="/" element={<App/>}>
                            <Route index element={<Home/>}/>

                            <Route path={"/recommend"} element={
                                <RequireAuth>
                                    <Recommend/>
                                </RequireAuth>
                            }/>
                            <Route path={"comment/:id"} element={<DetailPage/>}/>
                            <Route path={"tag-movie/:id"} element={<TagMoviePage/>}/>
                            <Route path={"/tag-all"} element={<AllTagPage/>}/>
                            <Route path={"/login"} element={<LoginPage/>}/>
                            <Route path={"*"} element={<NotFound/>}/>

                        </Route>
                    </Routes>
                </BrowserRouter>
            </GlobalContext.Provider>
        </React.StrictMode>
    );
};
ReactDOM.render(
    <React.StrictMode>
        <Index/>
    </React.StrictMode>,
    document.getElementById('root')
);

reportWebVitals();
