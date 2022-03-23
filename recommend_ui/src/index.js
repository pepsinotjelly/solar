import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import {BrowserRouter, Route, Router} from "react-router-dom";
import Home from "./pages/home";

const Index = () => {
    return (
        <React.StrictMode>
            <App></App>
        </React.StrictMode>
    );
};

ReactDOM.render(<Index/>, document.getElementById("root"));