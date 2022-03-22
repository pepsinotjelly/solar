import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router, Route, Link, Routes, HashRouter, BrowserRouter} from "react-router-dom";
import './index.css';
import App from './App';
import Home from "./pages/home-page";
import reportWebVitals from './reportWebVitals';
import NotFound from "./pages/404";
import Recommend from './pages/user-recommend-page';
import DetailPage from "./pages/movie-detail-page";

const Index = () => {
    return (
        <React.StrictMode>
                <BrowserRouter>
                    <Routes>
                        <Route path="/" element={<App/>}>
                            <Route index element={<Home/>}/>
                            <Route path={"recommend"} element={<Recommend/>}/>
                            <Route path={"comment:id"} element={<DetailPage/>}/>
                        </Route>
                    </Routes>
                </BrowserRouter>
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
