import axios from "axios";

const Axios = axios.create({
  baseURL: `http://${window.location.hostname}:8080/`
  //baseURL: "http://localhost:8000/"
});

export default Axios;