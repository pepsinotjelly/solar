import axios from "axios";
import { BACKEND_API } from "./constants";

// instance 请求后端api实例
const API = axios.create({
  baseURL: `${BACKEND_API}`,
  timeout: 5000,
});

export default API;
