import axios from "axios";

const Axios = axios.create({
  //baseURL: "http://125.134.138.184:8080/"
  baseURL: "http://localhost:8000/"
});

export default Axios;