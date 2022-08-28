import axios from "axios";

const Axios = axios.create({
  // baseURL: `http://${window.location.hostname}:8080/`
  baseURL: `http://125.134.138.184:8080/`

});

export default Axios;