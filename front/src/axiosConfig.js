import axios from "axios";

const Axios = axios.create({
  // baseURL: `http://${window.location.hostname}:8080/`
  baseURL: `http://www.bamb.shop/api/`

});

export default Axios;